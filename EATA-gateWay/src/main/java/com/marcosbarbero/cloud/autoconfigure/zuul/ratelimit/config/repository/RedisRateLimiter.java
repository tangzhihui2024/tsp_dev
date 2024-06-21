package com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.Rate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import java.beans.ConstructorProperties;
import java.util.concurrent.TimeUnit;

public class RedisRateLimiter extends AbstractCacheRateLimiter {
    private static final Logger log = LoggerFactory.getLogger(RedisRateLimiter.class);
    private final RateLimiterErrorHandler rateLimiterErrorHandler;
    private final RedisTemplate redisTemplate;

    protected void calcRemainingLimit(Long limit, Long refreshInterval, Long requestTime, String key, Rate rate) {
        if (limit != null) {
            //long begTime = System.currentTimeMillis();
            if (limit != null) {
                // 原方案每次都检查过期时间
                // handleExpiration(key, refreshInterval, rate);
                long usage = requestTime == null ? 1L : 0L;
                Long current = 0L;
                try {
                    current = redisTemplate.opsForValue().increment(key,  usage);
//                current = this.redisTemplate.boundValueOps(key).increment(usage);
                    // 优化思路：当第一次对key进行自增操作后redis返回1，此时设置过期时间，
                    // 此方法比原有方案减少了一次redis判断key过期的操作
                    if (1 == current) {
                        this.redisTemplate.expire(key, refreshInterval, TimeUnit.SECONDS);
                        rate.setReset(TimeUnit.SECONDS.toMillis(refreshInterval == null ? 0L : refreshInterval));
                    }
                } catch (RuntimeException e) {
                  //  System.err.println("increment error ====>" + e.getMessage());
                    String msg = "Failed retrieving rate for " + key + ", will return limit";
                    rateLimiterErrorHandler.handleError(msg, e);
                }
            //    System.err.println("current===>" + current);
                rate.setRemaining(Math.max(-1, limit - current));
            }
            //System.err.println("use time:" + (System.currentTimeMillis() - begTime) + "ms");
        }

    }

    protected void calcRemainingQuota(Long quota, Long refreshInterval, Long requestTime, String key, Rate rate) {
        if (quota != null) {
            String quotaKey = key + "-quota";
            this.handleExpiration(quotaKey, refreshInterval, rate);
            Long usage = requestTime != null ? requestTime : 0L;
            Long current = 0L;

            try {
                current = this.redisTemplate.boundValueOps(quotaKey).increment(usage);
            } catch (RuntimeException var11) {
                String msg = "Failed retrieving rate for " + quotaKey + ", will return quota limit";
                this.rateLimiterErrorHandler.handleError(msg, var11);
            }

            rate.setRemainingQuota(Math.max(-1L, quota - current));
        }

    }

    private void handleExpiration(String key, Long refreshInterval, Rate rate) {
        Long expire = null;

        try {
            expire = this.redisTemplate.getExpire(key);
            if (expire == null || expire == -1L) {
                this.redisTemplate.expire(key, refreshInterval, TimeUnit.SECONDS);
                expire = refreshInterval;
            }
        } catch (RuntimeException var7) {
            String msg = "Failed retrieving expiration for " + key + ", will reset now";
            this.rateLimiterErrorHandler.handleError(msg, var7);
        }

        rate.setReset(TimeUnit.SECONDS.toMillis(expire == null ? 0L : expire));
    }

    @ConstructorProperties({"rateLimiterErrorHandler", "redisTemplate"})
    public RedisRateLimiter(RateLimiterErrorHandler rateLimiterErrorHandler, RedisTemplate redisTemplate) {
        this.rateLimiterErrorHandler = rateLimiterErrorHandler;
        this.redisTemplate = redisTemplate;
    }
}
