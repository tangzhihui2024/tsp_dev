package com.icbcaxa.eata.redis;

import com.icbcaxa.eata.token.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

	@Autowired
	private StringRedisTemplate template;

	@Autowired
	private TokenHelper tokenHelper;

	/**
	 * 批量删除对应的value
	 *
	 * @param keys
	 */
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 批量删除key
	 *
	 * @param pattern
	 */
	@SuppressWarnings("unchecked")
	public void removePattern(final String pattern) {
		Set<String> keys = template.keys(pattern);
		if (keys.size() > 0)
			template.delete(keys);
	}

	/**
	 * 删除对应的value
	 *
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	public void remove(final String key) {
		if (exists(key)) {
			template.delete(key);
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 *
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean exists(final String key) {
		return template.hasKey(key);
	}

	/**
	 * 读取缓存
	 *
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object get(final String key) {
		Object result = null;
		ValueOperations<String, String> operations = template.opsForValue();
		result = operations.get(key);
		return result;
	}

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean set(final String key, String value) {
		boolean result = false;
		try {
			ValueOperations<String, String> operations = template.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean set(final String key, String value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<String, String> operations = template.opsForValue();
			operations.set(key, value);
			template.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	 @SuppressWarnings("unchecked")
	 public Object getEataAccessTokenVO(String accessToken) {
		 Object result = null;
		 try {
			 ValueOperations<String, String> operations =  template.opsForValue();
			 result = operations.get(accessToken);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 return result;
	 }

}
