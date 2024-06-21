package com.icbcaxa.eata.web.controller;

import com.icbcaxa.eata.service.TokenService;
import com.icbcaxa.eata.util.Response;
import com.icbcaxa.eata.util.date.DateUtil;
import com.icbcaxa.eata.vo.TokenVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by tangzh on 2018/5/30.
 */
@RestController
@RequestMapping(value = "/user_token")
public class TokenController {
    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private TokenService tokenServiceImpl;

    /**
     * 客户令牌码生成
     * @param tokenVO
     * @return
     */
    @RequestMapping(value = "/generate",produces = { "application/json;charset=utf-8" },method = RequestMethod.POST)
    public Response<String> generate(@RequestBody TokenVO tokenVO){
            long methodStartTime =System.currentTimeMillis();
            logger.info("[业务时间:"+ DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss.hs")+"]"+"[TokenController.generate方法start]"+"[根据流水号,登录来源,终端类型,系统信息,登录系统用户唯一标示符," +
                        "系统时间,生成客户令牌码]"+"[入参:"+"refGUID ="+tokenVO.getRefGUID()+",loginSource ="+tokenVO.getLoginSource()+
                        ",terminalType ="+tokenVO.getTerminalType()+",systemInfo ="+tokenVO.getSystemInfo()+",loginSystemUserId ="+
                        tokenVO.getLoginSystemUserId()+",systemTime ="+tokenVO.getSystemTime()+"]");
        try{
            Response<String> generate = tokenServiceImpl.generate(tokenVO);
            long methodEndTime = System.currentTimeMillis();
            logger.info("[TokenController.generate方法end]"+"[根据流水号,登录来源,终端类型,系统信息,登录系统用户唯一标示符," +
                        "系统时间,生成客户令牌码]"+"[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]");
            return generate;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("[业务时间:"+DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss.hs")+"]"+"[TokenController.generate方法]"+"[捕获异常:"+e.getMessage()+"]");
            return new Response<>(Response.ERROR,"客户令牌码生成失败");
        }
    }
    /**
     * 客户令牌码验证
     * @param tokenVO 流水号
     * @return
     */
    @RequestMapping(value = "/validate",produces = { "application/json;charset=utf-8" },method = RequestMethod.POST)
    public Response<String> validate(@RequestBody TokenVO tokenVO){
            long methodStartTime =System.currentTimeMillis();
            logger.info("[业务时间:"+ DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss.hs")+"]"+"[TokenController.validate方法start]"+"[根据流水号,客户令牌码," +
                        "客户令牌码验证]"+"[入参:"+"refGUID ="+tokenVO.getRefGUID()+",userToken ="+tokenVO.getUserToken()+ "]");
        try{
            Response<String> validate = tokenServiceImpl.validate(tokenVO.getRefGUID(), tokenVO.getUserToken());
            long methodEndTime = System.currentTimeMillis();
            logger.info("[TokenController.validate方法end]"+"[根据流水号,客户令牌码,客户令牌码验证]"+"[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]");
            return validate;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("[业务时间:"+DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss.hs")+"]"+"[TokenController.validate方法]"+"[捕获异常:"+e.getMessage()+"]");
            return new Response<>(Response.ERROR,"客户令牌码验证失败");
        }
    }
    /**
     * 刷新客户令牌码
     * @param tokenVO
     * @return
     */
    @RequestMapping(value = "/refresh",produces = { "application/json;charset=utf-8" },method = RequestMethod.POST)
    public Response<String> refresh(@RequestBody TokenVO tokenVO){
            long methodStartTime =System.currentTimeMillis();
            logger.info("[业务时间:"+ DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss.hs")+"]"+"[TokenController.refresh方法start]"+"[根据流水号,客户令牌码," +
                        "刷新客户令牌码]"+"[入参:"+"refGUID ="+tokenVO.getRefGUID()+",userToken ="+tokenVO.getUserToken()+ "]");
        try{
            Response<String> refresh = tokenServiceImpl.refresh(tokenVO.getRefGUID(), tokenVO.getUserToken());
            long methodEndTime = System.currentTimeMillis();
            logger.info("[TokenController.refresh方法end]"+"[根据流水号,客户令牌码,刷新客户令牌码]"+"[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]");
            return refresh;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("[业务时间:"+DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss.hs")+"]"+"[TokenController.refresh方法]"+"[捕获异常:"+e.getMessage()+"]");
            return new Response<>(Response.ERROR,"刷新客户令牌码失败");
        }
    }
    /**
     * 注销客户令牌码
     * @param tokenVO
     * @return
     */
    @RequestMapping(value = "/cancel",produces = { "application/json;charset=utf-8" },method = RequestMethod.POST)
    public Response<String> cancel(@RequestBody TokenVO tokenVO){
            long methodStartTime =System.currentTimeMillis();
            logger.info("[业务时间:"+ DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss.hs")+"]"+"[TokenController.cancel方法start]"+"[根据流水号,客户令牌码," +
                        "注销客户令牌码]"+"[入参:"+"refGUID ="+tokenVO.getRefGUID()+",userToken ="+tokenVO.getUserToken()+ "]");
        try{
            Response<String> cancel = tokenServiceImpl.cancel(tokenVO.getRefGUID(), tokenVO.getUserToken());
            long methodEndTime = System.currentTimeMillis();
            logger.info("[TokenController.cancel方法end]"+"[根据流水号,客户令牌码,注销客户令牌码]"+"[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]");
            return cancel;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("[业务时间:"+DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss.hs")+"]"+"[TokenController.cancel方法]"+"[捕获异常:"+e.getMessage()+"]");
            return new Response<>(Response.ERROR,"注销客户令牌码失败");
        }
    }

}
