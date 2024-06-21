package com.icbcaxa.eata.service.impl;

import com.alibaba.fastjson.JSON;
import com.icbcaxa.eata.redis.RedisUtil;
import com.icbcaxa.eata.service.TokenSendService;
import com.icbcaxa.eata.token.TokenHelper;
import com.icbcaxa.eata.util.Response;
import com.icbcaxa.eata.util.constant.GlobalConstant;
import com.icbcaxa.eata.util.json.JsonUtils;
import com.icbcaxa.eata.vo.AccreditVO;
import com.icbcaxa.eata.vo.SecretVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenSendServiceImpl implements TokenSendService {

    private Logger logger = LoggerFactory.getLogger(TokenSendServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TokenHelper tokenHelper;

    /**
     * 推送
     *
     * @return
     */
    @Override
    public Response<String> send(AccreditVO accreditVO) {
            long methodStartTime =System.currentTimeMillis();
            logger.info("[TokenSendServiceImpl.send方法start]+[根据流水号,调用系统,目标系统,目标系统业务模块," + "目标系统业务调用入口," +
                    "访问授权码,更新访问授权码]+[入参:"+"refGUID ="+accreditVO.getRefGUID()+",callSystem ="+accreditVO.getCallSystem()+
                    ",targetSystem ="+accreditVO.getTargetSystem()+",targetBusinessModule ="+accreditVO.getTargetBusinessModule()+",targetBusinessEntrance ="+
                    accreditVO.getTargetBusinessModule()+",accessToken ="+accreditVO.getAccessToken()+"]");
                Map<String, Object> info = new HashMap<>();
                info.put(GlobalConstant.CALL_SYSTEM,accreditVO.getCallSystem());
                info.put(GlobalConstant.TARGET_SYSTEM,accreditVO.getTargetSystem());
                info.put(GlobalConstant.TARGET_BUSINESS_MODULE,accreditVO.getTargetBusinessModule());
                info.put(GlobalConstant.TARGET_BUSINESS_ENTRANCE,accreditVO.getTargetBusinessEntrance());
        try{
            String secretKey = GlobalConstant.EATA_SECRET+accreditVO.getCallSystem()+"_"+GlobalConstant.AUTH_CODE;
            SecretVo secretVo = new SecretVo();
            try{
                if(redisUtil.exists(secretKey)){
                    String secretJson = redisUtil.get(secretKey).toString();
                    secretVo = JsonUtils.jsonToPojo(secretJson,SecretVo.class);
                    if(null == secretVo){
                        logger.info("["+accreditVO.getCallSystem()+"获取秘钥对象失败"+"]");
                        return new Response<>(Response.ERROR,accreditVO.getCallSystem()+"获取秘钥对象失败！");
                    }
                }else{
                    logger.info("["+accreditVO.getCallSystem()+"秘钥已过期，请重新配置！"+"]");
                    return new Response<>(Response.ERROR,accreditVO.getCallSystem()+"秘钥已过期，请重新配置！");
                }
            }catch (Exception e){
                e.printStackTrace();
                logger.error("[TokenSendServiceImpl.send方法]+[捕获异常]",e);
                return new Response<>(Response.ERROR,"获取秘钥信息失败！");
            }
            String token = tokenHelper.createToken(GlobalConstant.SYSTEM, null,accreditVO.getCallSystem(),info,secretVo.getSecretKey(),
                            Long.parseLong(secretVo.getValidateTime()));//创建token
            //访问远程 发送授权码
            accreditVO.setAccessToken(token);
            accreditVO.setValidateTime(secretVo.getValidateTime());
            accreditVO.setSecretKey(secretVo.getSecretKey());
            try {
                if(redisUtil.exists(token)){
                    redisUtil.remove(token);
                }
                redisUtil.set(token, JsonUtils.objectToJson(accreditVO),Long.parseLong(secretVo.getValidateTime())/1000);
            }catch (Exception e){
                e.printStackTrace();
                logger.error("[TokenSendServiceImpl.send方法]+[token信息放入缓存异常捕获异常]",e);
                return new Response<>(Response.ERROR, "授权码保存redis异常");
            }
            Response<String> resp = new Response<>(Response.SUCCESS, "授权码生成成功!");
            //info.clear();
            //info.put("token",token);
            resp.setUserToken(token);
            resp.setValidateTime(secretVo.getValidateTime());
            long methodEndTime = System.currentTimeMillis();
            logger.info("[TokenSendServiceImpl.send方法end]+[根据 流水号,调用系统,目标系统,目标系统业务模块," + "目标系统业务调用入口," +
                    "访问授权码,推送访问授权码]+[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]返回值:"+JSON.toJSONString(resp));
            return resp;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("[TokenSendServiceImpl.send方法]+[授权码生成异常]",e);
            return new Response<>(Response.ERROR, "令牌码生成异常!");
        }
    }

    /**
     * 生成秘钥
     *
     * @param secretVo
     * @return
     */
    @Override
    public String saveSecret(SecretVo secretVo) {
            long methodStartTime =System.currentTimeMillis();
            logger.info("[TokenSendServiceImpl.saveSecret方法start]+[根据系统名称,秘钥,有效时间,类型," + "系统时间," +
                    "生成秘钥]+[入参:"+"system ="+secretVo.getSystem()+",secretKey ="+secretVo.getSecretKey()+
                    ",type ="+secretVo.getType()+",validateTime ="+secretVo.getValidateTime()+"]");
            String secretStr = "";
        try{
            secretStr = tokenHelper.createSecretBytes(secretVo.getSystem());
            logger.info("[生成秘钥成功"+"]"+secretStr);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("[TokenSendServiceImpl.saveSecret方法]+[生成秘钥异常]",e);
            return null;
        }
        try {
            secretVo.setSecretKey(secretStr);
            String secretKey = GlobalConstant.EATA_SECRET+secretVo.getSystem()+"_"+secretVo.getType();
            //如果 redis中已存在该key  则删掉
            if(redisUtil.exists(secretKey)){
                redisUtil.remove(secretKey);
            }
            redisUtil.set(secretKey,JsonUtils.objectToJson(secretVo),3600L);
            logger.info("秘钥的key ： "+GlobalConstant.EATA_SECRET+secretVo.getSystem()+"_"+secretVo.getType());
            long methodEndTime = System.currentTimeMillis();
            logger.info("[TokenSendServiceImpl.saveSecret方法end]+[根据系统名称,秘钥,有效时间,类型," + "系统时间," +
                    ",生成秘钥]+[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]返回值:"+JSON.toJSONString(secretVo));
            logger.info("[秘钥信息放入redis成功"+"]"+JSON.toJSON(secretVo));
        }catch (Exception e){
            logger.error("[TokenSendServiceImpl.saveSecret方法]+[秘钥信息放入缓存异常]",e);
            e.printStackTrace();
            return null;
        }
        return secretStr;
    }
}
