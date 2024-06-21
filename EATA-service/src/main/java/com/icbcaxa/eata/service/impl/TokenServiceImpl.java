package com.icbcaxa.eata.service.impl;

import com.alibaba.fastjson.JSON;
import com.dianping.cat.Cat;
import com.icbcaxa.eata.redis.RedisUtil;
import com.icbcaxa.eata.service.TokenService;
import com.icbcaxa.eata.token.TokenHelper;
import com.icbcaxa.eata.util.Response;
import com.icbcaxa.eata.util.constant.CommenConstant;
import com.icbcaxa.eata.util.constant.GlobalConstant;
import com.icbcaxa.eata.util.date.DateUtil;
import com.icbcaxa.eata.util.json.JsonUtils;
import com.icbcaxa.eata.vo.SecretVo;
import com.icbcaxa.eata.vo.TokenVO;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangzh on 2018/5/30.
 * 客户令牌码,生成 验证 刷新 注销
 */
@Service
public class TokenServiceImpl implements TokenService {

    private Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 客户令牌码生成
     * @param tokenVO
     * @return
     */
    @Override
    public Response<String> generate(TokenVO tokenVO){
            long methodStartTime =System.currentTimeMillis();
            logger.info("[TokenServiceImpl.generate方法start]"+"[根据流水号,登录来源,终端类型,系统信息,登录系统用户唯一标示符," +
                    "系统时间]"+"[入参:"+"refGUID ="+tokenVO.getRefGUID()+",loginSource ="+tokenVO.getLoginSource()+
                    ",terminalType ="+tokenVO.getTerminalType()+",systemInfo ="+tokenVO.getSystemInfo()+",loginSystemUserId ="+
                    tokenVO.getLoginSystemUserId()+",systemTime ="+tokenVO.getSystemTime()+"]");
            Map<String, Object> info = new HashMap<>();
            info.put(GlobalConstant.REFGUID,tokenVO.getRefGUID());
            info.put(GlobalConstant.LOGINSOURE,tokenVO.getLoginSource());
            info.put(GlobalConstant.TERMINALTYPE,tokenVO.getTerminalType());
            info.put(GlobalConstant.SYSTEMINFO,tokenVO.getSystemInfo());
            info.put(GlobalConstant.LOGINSYSTEMUSERID,tokenVO.getLoginSystemUserId());
            info.put(GlobalConstant.SYSTEMTIME,tokenVO.getSystemTime());
        try{
            String secretKey = GlobalConstant.EATA_SECRET+tokenVO.getLoginSource()+"_"+GlobalConstant.TOKEN_CODE;
            Response<String> resp = new Response<>(Response.SUCCESS, "令牌码生成成功");
            SecretVo secretVo = new SecretVo();
            if(template.hasKey(secretKey)){
                String str = template.opsForValue().get(secretKey);
                secretVo = JsonUtils.jsonToPojo(str, SecretVo.class);
                if(null ==secretVo){
                    logger.info("[TokenServiceImpl.generate方法]"+"[获取秘钥对象失败secretVo="+JSON.toJSONString(secretVo)+"]");
                    return new Response<>(Response.ERROR,tokenVO.getLoginSource()+"获取秘钥对象失败！");
                }
                String token = tokenHelper.createToken(GlobalConstant.SYSTEM,null,tokenVO.getLoginSource(), info,
                                            secretVo.getSecretKey(),Long.parseLong(secretVo.getValidateTime()));
                try {
                    tokenVO.setValidateTime(secretVo.getValidateTime());
                    tokenVO.setSecretKey(secretVo.getSecretKey());
                    redisUtil.set(token,JsonUtils.objectToJson(tokenVO),Long.parseLong(secretVo.getValidateTime())/1000);//设置redis有效时间
                }catch (Exception e){
                    e.printStackTrace();
                    logger.error("[TokenServiceImpl.generate方法]"+"[捕获异常:"+e.getMessage()+"]");
                    return new Response<>(Response.ERROR, "生成token保存redis异常");
                }
                info.clear();
                resp.setUserToken(token);
                logger.info("[TokenServiceImpl.generate方法]"+"[令牌码生成成功token ="+JSON.toJSONString(token)+"]");
            }else {
                logger.info("[TokenServiceImpl.generate方法]"+"["+tokenVO.getLoginSource()+"不存在秘钥信息"+"]");
                return new Response<>(Response.ERROR,tokenVO.getLoginSource()+"不存在秘钥信息！");
            }
            long methodEndTime = System.currentTimeMillis();
            logger.info("[TokenServiceImpl.generate方法end]"+"[根据流水号,登录来源,终端类型,系统信息,登录系统用户唯一标示符," +
                    "系统时间,客户令牌码生成]"+"[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]"+"[返回值:"+JSON.toJSONString(resp)+"]");
            return resp;
        }catch (Exception e){
            Cat.logError(e);
            Cat.logMetricForCount("Redis Exception");
            e.printStackTrace();
            logger.error("[TokenServiceImpl.generate方法]"+"[捕获异常:"+e.getMessage()+"]");
            return new Response<>(Response.ERROR, "令牌码生成异常");
        }
    }

    /**
     * 客户令牌码验证
     * @param refGUID 流水号
     * @param userToken 访问令牌码（授权码）
     * @return
     */
    @Override
    public Response<String> validate(String refGUID, String userToken){
            long methodStartTime =System.currentTimeMillis();
            logger.info("[TokenServiceImpl.validate方法start]"+"[根据流水号,客户令牌码,验证客户令牌码]" + "[入参:"+"refGUID ="+refGUID+",userToken ="+userToken+ "]");
        try {
            String str = "";
            try{
                str = template.opsForValue().get(userToken);
            }catch (Exception e){
                Cat.logError(e);
                Cat.logMetricForCount("Redis Exception");
                e.printStackTrace();
                logger.error("[TokenServiceImpl.validate]"+"[捕获异常:"+e.getMessage()+"]");
                return new Response<>(CommenConstant.INVALID, "令牌码验证异常");
            }
            TokenVO tokenVO = new TokenVO();
            Response<String> resp = new Response <String>(Response.SUCCESS, "令牌码验证成功");
        if(str != null){
            tokenVO = JsonUtils.jsonToPojo(str, TokenVO.class);
        if (null == tokenVO) {
            logger.info("[TokenServiceImpl.validate]"+"["+userToken+"获取秘钥对象失败"+"]");
            return new Response<>(Response.ERROR, userToken + "获取秘钥对象失败！");
        }
            Claims claims = tokenHelper.parseToken(userToken, tokenVO.getSecretKey());
            logger.info("[TokenServiceImpl.validate]"+"[令牌码验证出参 claims="+ JSON.toJSONString(claims)+"]");
            if(null == claims) {
                resp.setCode(CommenConstant.FAILURE);
                resp.setIsActive(CommenConstant.INVALID);
                resp.setMessage("令牌码验证失败");
                return resp;
            } else{
                tokenVO.setUserToken(tokenVO.getUserToken());
                resp.setLoginSource(tokenVO.getLoginSource());
                resp.setTerminalType(tokenVO.getTerminalType());
                resp.setSystemInfo(tokenVO.getSystemInfo());
                resp.setLoginSystemUserId(tokenVO.getLoginSystemUserId());
                resp.setIsActive(CommenConstant.VALID);//有效
                long methodEndTime = System.currentTimeMillis();
                logger.info("[TokenServiceImpl.validate方法end]"+"[根据流水号,userToken,客户令牌码校验]"+"[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]" +
                                "[返回值:"+JSON.toJSONString(resp)+"]");
                return resp;
            }
        }else{
                logger.info("[TokenServiceImpl.validate]"+"["+userToken+"令牌码验证失败"+"]");
                resp.setCode(CommenConstant.FAILURE);
                resp.setIsActive(CommenConstant.INVALID);
                resp.setMessage("令牌码验证失败");
                return resp;
            }
        }catch (Exception e){
            e.printStackTrace();
            Cat.logError(e);
            Cat.logMetricForCount("Redis Exception");
            logger.error("[TokenServiceImpl.validate方法]"+"[捕获异常:"+e.getMessage()+"]");
            return new Response<>(Response.ERROR, "令牌码验证异常");
        }
    }

    /**
     * 刷新客户令牌码
     * @param refGUID 流水号
     * @param userToken 访问授权码
     * @return
     */
    @Override
    public Response<String> refresh(String refGUID,String userToken){
            long methodStartTime =System.currentTimeMillis();
            logger.info("[TokenServiceImpl.refresh方法start]"+"[根据流水号,客户令牌码,刷新客户令牌码]" + "[入参:"+"refGUID ="+refGUID+",userToken ="+userToken+ "]");
        try{
            String str = "";
            try{
                str = template.opsForValue().get(userToken);
            }catch (Exception e){
                Cat.logError(e);
                Cat.logMetricForCount("Redis Exception");
                e.printStackTrace();
                logger.error("[TokenServiceImpl.refresh方法]"+"[捕获异常:"+e.getMessage()+"]");
                return new Response<>(CommenConstant.INVALID, "刷新客户令牌码异常");
            }
            Response<String> resp =new  Response<String>(Response.SUCCESS,"刷新客户令牌码成功");
            TokenVO tokenVO = new TokenVO();
            if(str != null){
                tokenVO = JsonUtils.jsonToPojo(str,TokenVO.class);
                if(null ==tokenVO){
                    logger.info("[TokenServiceImpl.refresh方法]"+"["+userToken+"获取秘钥对象失败"+"]");
                    return new Response<>(Response.ERROR,userToken+"获取对象失败");
                }
                tokenVO.setUserToken(userToken);
                tokenVO.setValidateTime(tokenVO.getValidateTime());
                tokenVO.setSystemTime(DateUtil.toSeconds(new Date()));
                redisUtil.set(userToken,JsonUtils.objectToJson(tokenVO),Long.parseLong(tokenVO.getValidateTime())/1000);
                Claims claims = tokenHelper.parseToken(userToken,tokenVO.getSecretKey());
                if(claims !=  null){
                    resp.setIsActive(CommenConstant.VALID);
                    resp.setLoginSource(tokenVO.getLoginSource());
                    resp.setTerminalType(tokenVO.getTerminalType());
                    resp.setSystemInfo(tokenVO.getSystemInfo());
                    resp.setLoginSystemUserId(tokenVO.getLoginSystemUserId());
                    resp.setUserToken(userToken);
                    logger.info("[TokenServiceImpl.refresh方法]"+"[刷新客户令牌码成功 claims ="+JSON.toJSONString(claims),"userToken="+JSON.toJSONString(str)+"]");
                }else{
                    resp.setCode(CommenConstant.FAILURE);
                    resp.setIsActive(CommenConstant.INVALID);
                    resp.setMessage("刷新客户令牌码失败");
                }
            }else{
                logger.info("[TokenServiceImpl.refresh]"+"["+userToken+"令牌码验证已失效"+"]");
                resp.setCode(CommenConstant.FAILURE);
                resp.setIsActive(CommenConstant.INVALID);
                resp.setMessage("刷新客户令牌码失败");
                return resp;
            }
            long methodEndTime = System.currentTimeMillis();
            logger.info("[TokenServiceImpl.refresh方法end]"+"[根据流水号,客户令牌码,刷新客户令牌码]"+"[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]" +
                        "[返回值:"+JSON.toJSONString(resp)+"]");
            return resp;
        }catch (Exception e){
            Cat.logError(e);
            Cat.logError(e);
            Cat.logMetricForCount("Redis Exception");
            e.printStackTrace();
            logger.error("[TokenServiceImpl.refresh方法]"+"[捕获异常:"+e.getMessage()+"]");
            return new Response<>(CommenConstant.INVALID, "刷新客户令牌码异常");
        }
    }

    /**
     * 注销客户令牌码
     * @param refGUID 流水号
     * @param userToken 访问授权码
     * @return
     */
    @Override
    public Response<String> cancel(String refGUID,String userToken){
            long methodStartTime =System.currentTimeMillis();
            logger.info("[TokenServiceImpl.cancel方法start]"+"[根据流水号,客户令牌码,注销客户令牌码]" + "[入参:"+"refGUID ="+refGUID+",userToken ="+userToken+ "]");
        try{
            Response<String> resp = new Response <String>(Response.SUCCESS, "注销令牌码成功");
            if(redisUtil.exists(userToken)){
                redisUtil.remove(userToken);
            }
            long methodEndTime = System.currentTimeMillis();
            logger.info("[TokenServiceImpl.cancel方法end]"+"[根据流水号,客户令牌码,注销客户令牌码]"+"[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]" +
                        "[返回值:"+JSON.toJSONString(resp)+"]");
            return resp;
        }catch (Exception e){
            Cat.logMetricForCount("Redis Exception");
            Cat.logError(e);
            e.printStackTrace();
            logger.error("[TokenServiceImpl.cancel方法]"+"[捕获异常:"+e.getMessage()+"]");
            return new Response<>(CommenConstant.INVALID, "注销令牌码异常");
        }
    }
}
