package com.icbcaxa.eata.web.controller;

import com.icbcaxa.eata.service.TokenSendService;
import com.icbcaxa.eata.util.Response;
import com.icbcaxa.eata.util.date.DateUtil;
import com.icbcaxa.eata.vo.AccreditVO;
import com.icbcaxa.eata.vo.SecretVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/access_token")
public class TokenSendController {

    private Logger logger = LoggerFactory.getLogger(TokenSendController.class);

    @Autowired
    private TokenSendService tokenSendService;

    @RequestMapping("/send")
    public Response<String> send(@RequestBody AccreditVO accreditVO ){
            long methodStartTime =System.currentTimeMillis();
            logger.info("[业务时间:"+ DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss.hs")+"]"+"[TokenSendController.send方法start]+[根据流水号,调用系统,目标系统,目标系统业务模块," + "目标系统业务调用入口," +
                        "系统时间，访问授权码,推送访问授权码]+[入参:"+"refGUID ="+accreditVO.getRefGUID()+",callSystem ="+accreditVO.getCallSystem()+
                        ",targetSystem ="+accreditVO.getTargetSystem()+",targetBusinessModule ="+accreditVO.getTargetBusinessModule()+",targetBusinessEntrance ="+
                        accreditVO.getTargetBusinessModule()+",accessToken ="+accreditVO.getAccessToken()+"]");
        try{
            Response<String> send = tokenSendService.send(accreditVO);
            long methodEndTime = System.currentTimeMillis();
            logger.info("[TokenSendController.send方法end]+[根据流水号,登录来源,终端类型,系统信息," + "登录系统用户唯一标示符," +
                    "系统时间,验证访问授权码]+[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]");
            return send;
        }catch (Exception e){
            logger.error("[业务时间:"+DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss.hs")+"]"+"[TokenSendController.send方法]+[捕获异常]",e);
            return new Response<>(Response.ERROR,Response.ERROR_MESSAGE);
        }
    }

    @RequestMapping("/saveSecret")
    public String saveSecret(@RequestBody SecretVo secretVo ){
            long methodStartTime =System.currentTimeMillis();
            logger.info("[业务时间:"+ DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss.hs")+"]"+"[TokenSendController.saveSecret方法start]+[根据系统名称,秘钥,有效时间,类型,"+
                        "系统时间,保存秘钥]+[入参:"+"system ="+secretVo.getSystem()+",secretKey ="+secretVo.getSecretKey()+
                        ",type ="+secretVo.getType()+",validateTime ="+secretVo.getValidateTime()+"]");
        try{
            String saveSecret = tokenSendService.saveSecret(secretVo);
            long methodEndTime = System.currentTimeMillis();
            logger.info("[TokenSendController.saveSecret方法end]+[根据系统名称,秘钥,类型,系统时间," + "保存秘钥]+[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]");
            return saveSecret;
        }catch (Exception e){
            logger.error("[业务时间:"+DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss.hs")+"]"+"[TokenSendController.saveSecret方法]+[捕获异常]",e);
            return null;
        }
    }
}
