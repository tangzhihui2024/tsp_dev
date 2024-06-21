package com.icbcaxa.eata.web.controller;

import com.icbcaxa.eata.service.AccreditService;
import com.icbcaxa.eata.util.Response;
import com.icbcaxa.eata.util.date.DateUtil;
import com.icbcaxa.eata.vo.AccreditVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

/**
 * Created by tangzh on 2018/5/25.
 * 访问授权码-验证,更新 功能
 */

@RestController
@RequestMapping(value = "/access_token")
public class AccreditController {
    private static final Logger logger = LoggerFactory.getLogger(AccreditController.class);

    @Autowired
    private AccreditService accreditServiceImpl;

    /**
     * 访问授权码校验
     * @param accreditVO
     * @return
     */
    @RequestMapping(value = "/validate",produces = { "application/json;charset=utf-8" },method = RequestMethod.POST)
    public Response<String> validate(@RequestBody AccreditVO accreditVO) {
            long methodStartTime =System.currentTimeMillis();
            logger.info("[业务时间:"+ DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss.hs")+"]"+"[AccreditController.validate方法start]"+"[根据流水号,调用系统,目标系统,目标系统业务模块," + "目标系统业务调用入口," +
                        "系统时间，访问授权码,验证访问授权码]"+"[入参:"+"refGUID ="+accreditVO.getRefGUID()+",callSystem ="+accreditVO.getCallSystem()+
                        ",targetSystem ="+accreditVO.getTargetSystem()+",targetBusinessModule ="+accreditVO.getTargetBusinessModule()+",targetBusinessEntrance ="+
                        accreditVO.getTargetBusinessModule()+",accessToken ="+accreditVO.getAccessToken()+"]");
        try {
            Response<String> validate = accreditServiceImpl.validate(accreditVO);
            long methodEndTime = System.currentTimeMillis();
            logger.info("[AccreditController.validate方法end]"+"[根据流水号,调用系统,目标系统,目标系统业务模块," + "目标系统业务调用入口," +
                        "系统时间，访问授权码,验证访问授权码]"+"[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]");
            return validate;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[业务时间:"+DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss.hs")+"]"+"[AccreditController.validate方法]"+"[捕获异常:"+e.getMessage()+"]");
            return new Response<>(Response.ERROR,"访问授权码校验失败");
        }
    }
    /**
     * 访问授权码更新
     * @param accreditVO
     * @return
     */
    @RequestMapping(value = "/refresh",produces = { "application/json;charset=utf-8" },method = RequestMethod.POST)
    public Response<String> refresh(@RequestBody AccreditVO accreditVO){
            long methodStartTime =System.currentTimeMillis();
            logger.info("[业务时间:"+ DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss.hs")+"]"+"[AccreditController.refresh方法start]"+"[根据流水号,调用系统,目标系统,目标系统业务模块," + "目标系统业务调用入口," +
                        "访问授权码,更新访问授权码]"+"[入参:"+"refGUID ="+accreditVO.getRefGUID()+",callSystem ="+accreditVO.getCallSystem()+
                        ",targetSystem ="+accreditVO.getTargetSystem()+",targetBusinessModule ="+accreditVO.getTargetBusinessModule()+",targetBusinessEntrance ="+
                        accreditVO.getTargetBusinessModule()+",accessToken ="+accreditVO.getAccessToken()+"]");
        try{
            Response<String> refresh = accreditServiceImpl.refresh(accreditVO);
            long methodEndTime = System.currentTimeMillis();
            logger.info("[AccreditController.refresh方法end]+[根据流水号,调用系统,目标系统,目标系统业务模块," + "目标系统业务调用入口," +
                        "访问授权码,更新访问授权码]"+"[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]");
            return refresh;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("[业务时间:"+DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss.hs")+"]"+"[AccreditController.refresh方法]"+"[捕获异常:"+e.getMessage()+"]");
            return new Response<>(Response.ERROR,"访问授权码更新失败");
        }
    }
}
