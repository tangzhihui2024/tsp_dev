package com.icbcaxa.eata.service.impl;

import com.alibaba.fastjson.JSON;
import com.dianping.cat.Cat;
import com.icbcaxa.eata.redis.RedisUtil;
import com.icbcaxa.eata.service.AccreditService;
import com.icbcaxa.eata.token.TokenHelper;
import com.icbcaxa.eata.util.Response;
import com.icbcaxa.eata.util.constant.CommenConstant;
import com.icbcaxa.eata.util.date.DateUtil;
import com.icbcaxa.eata.util.json.JsonUtils;
import com.icbcaxa.eata.vo.AccreditVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by tangzh on 2018/5/25.
 * 访问授权码-验证,更新 功能
 */

@Service
public class AccreditServiceImpl implements AccreditService {
    private Logger logger = LoggerFactory.getLogger(AccreditServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TokenHelper tokenHelper;

    /**
     * 访问授权码校验
     * @param accreditVO
     * @return
     */
    @Override
    public Response<String> validate(AccreditVO accreditVO) {
            long methodStartTime =System.currentTimeMillis();
            logger.info("[AccreditServiceImpl.validate方法start]"+"[根据流水号,调用系统,目标系统,目标系统业务模块,目标系统业务调用入口," +
                        "系统时间，访问授权码,验证访问授权码]"+"[入参:"+"refGUID ="+accreditVO.getRefGUID()+",callSystem ="+accreditVO.getCallSystem()+
                        ",targetSystem ="+accreditVO.getTargetSystem()+",targetBusinessModule ="+accreditVO.getTargetBusinessModule()+",targetBusinessEntrance ="+
                        accreditVO.getTargetBusinessModule()+",systemTime ="+accreditVO.getSystemTime()+",accessToken ="+accreditVO.getAccessToken()+"]");
        try{
            Response<String> resp=new Response<String>(Response.SUCCESS,"访问授权码成功");
            String str = redisUtil.get(accreditVO.getAccessToken()).toString();
            if(null == str ){
                resp.setIsActive(CommenConstant.INVALID);
                resp.setCode(CommenConstant.FAILURE);
                resp.setMessage("缓存获得授权码为空");
                logger.info("[AccreditServiceImpl.validate方法]"+"[缓存获得授权码为空str="+JSON.toJSONString(str)+"]");
            } else {
                AccreditVO value = JsonUtils.jsonToPojo(str,AccreditVO.class);
                SimpleDateFormat sdf= new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                Date startTime = sdf.parse(value.getStartTime());
                Date endTime = sdf.parse(value.getEndTime());
                if(date.getTime() < startTime.getTime()){
                    resp.setIsActive(CommenConstant.INVALID);
                    resp.setCode(CommenConstant.FAILURE);
                    resp.setMessage("访问授权码未生效");
                    logger.info("[AccreditServiceImpl.validate方法]"+"[访问授权码未生效accessToken="+accreditVO.getAccessToken()+"]");
                }
                else if(date.getTime() > endTime.getTime()){
                    resp.setIsActive(CommenConstant.INVALID);
                    resp.setCode(CommenConstant.FAILURE);
                    resp.setMessage("访问授权码已过期");
                    logger.info("[AccreditServiceImpl.validate方法]"+"[访问授权码已过期accessToken="+accreditVO.getAccessToken()+"]");
                }else{
                    if(null == accreditVO.getTargetBusinessEntrance()){
                        accreditVO.setTargetBusinessEntrance("");
                    }
                    if (null == accreditVO.getTargetBusinessModule()){
                        accreditVO.setTargetBusinessModule("");
                    }
                    if (value.equals(accreditVO)) {
                        resp.setIsActive(CommenConstant.VALID);
                        logger.info("[AccreditServiceImpl.validate方法]"+"[访问授权码成功accessToken="+accreditVO.getAccessToken()+"]");
                    } else {
                        resp.setIsActive(CommenConstant.INVALID);
                        resp.setCode(CommenConstant.FAILURE);
                        resp.setMessage("访问授权码失败");
                        logger.info("[AccreditServiceImpl.validate方法]"+"[访问授权码失败accessToken="+accreditVO.getAccessToken()+"]");
                        Cat.logMetricForCount("AccessToken Validation Failed");
                    }
                }
            }
            long methodEndTime = System.currentTimeMillis();
            logger.info("[AccreditServiceImpl.validate方法end]"+"[根据流水号,调用系统,目标系统,目标系统业务模块,目标系统业务调用入口," +
                        "系统时间，访问授权码,验证访问授权码]"+"[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]"+"[返回值:"+JSON.toJSONString(resp)+"]");
            return resp;
        }catch (Exception e){
            Cat.logError(e);
            Cat.logMetricForCount("Redis Exception");
            Cat.logMetricForCount("AccessToken Validation Failed");
            e.printStackTrace();
            logger.error("[AccreditServiceImpl.validate方法]"+"[捕获异常:"+e.getMessage()+"]");
            return new Response<>(Response.ERROR, "访问授权码校验异常");
        }
    }

    /**
     * 访问授权码更新
     * @param accreditVO
     * @return
     */
    @Override
    public Response<String> refresh(AccreditVO accreditVO) {
            long methodStartTime =System.currentTimeMillis();
            logger.info("[AccreditServiceImpl.refresh方法start]"+"[根据流水号,调用系统,目标系统,目标系统业务模块,目标系统业务调用入口," +
                        "访问授权码,更新访问授权码]"+"[入参:"+"refGUID ="+accreditVO.getRefGUID()+",callSystem ="+accreditVO.getCallSystem()+
                        ",targetSystem ="+accreditVO.getTargetSystem()+",targetBusinessModule ="+accreditVO.getTargetBusinessModule()+",targetBusinessEntrance ="+
                        accreditVO.getTargetBusinessModule()+",accessToken ="+accreditVO.getAccessToken()+"]");
        try{
            Response<String> resp=new Response<String>(Response.SUCCESS,"访问授权码更新成功");
            String str = redisUtil.get(accreditVO.getAccessToken()).toString();
            AccreditVO secretVo = new AccreditVO();
            if(str != null){
                secretVo = JsonUtils.jsonToPojo(str,AccreditVO.class);
                if(redisUtil.exists(accreditVO.getAccessToken())){
                    redisUtil.remove(accreditVO.getAccessToken());
                }
                if(null == secretVo){
                    logger.info("[AccreditServiceImpl.refresh方法]"+"[获取秘钥对象失败secretVo="+JSON.toJSONString(secretVo)+"]");
                    return new Response<>(Response.ERROR,accreditVO.getCallSystem()+"获取秘钥对象失败！");
                }
                String token = tokenHelper.updateAccredit(accreditVO.getAccessToken(),
                               Long.parseLong(secretVo.getValidateTime()),secretVo.getSecretKey());//更新redis里token时间
                secretVo.setAccessToken(token);
                secretVo.setValidateTime(secretVo.getValidateTime());
                secretVo.setSystemTime(DateUtil.toSeconds(new Date()));
                redisUtil.set(token,JsonUtils.objectToJson(secretVo),Long.parseLong(secretVo.getValidateTime()));//重新设置redis里的有效日期
                logger.info("[AccreditServiceImpl.refresh方法]"+"[访问授权码更新成功token ="+JSON.toJSONString(token)+"]");
            }
            else{
                logger.info("[AccreditServiceImpl.refresh方法]"+"[accessToken="+accreditVO.getAccessToken()+"不存在秘钥信息"+"]");
                return new Response<>(CommenConstant.FAILURE,accreditVO.getAccessToken()+"不存在秘钥信息！");
            }
            long methodEndTime = System.currentTimeMillis();
            logger.info("[AccreditServiceImpl.refresh方法end]"+"[根据 流水号,调用系统,目标系统,目标系统业务模块,目标系统业务调用入口," +
                        "访问授权码验证访问授权码]"+"[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]"+"[返回值:"+JSON.toJSONString(resp)+"]");
            return resp;
        }catch (Exception e){
            Cat.logError(e);
            Cat.logMetricForCount("Redis Exception");
            e.printStackTrace();
            logger.error("[AccreditServiceImpl.refresh方法]"+"[捕获异常:"+e.getMessage()+"]");
            return new Response<>(Response.ERROR, "访问授权码更新异常");
        }
    }

}
