package com.icbcaxa.eata.web.controller;

import com.icbcaxa.eata.service.PropertiesService;
import com.icbcaxa.eata.util.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by tangzh on 2018/6/15.
 */
@RestController
@RequestMapping(value = "/get")
public class PropertiesController {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesController.class);

    @Autowired
    private PropertiesService propertiesServiceImpl;

    @RequestMapping(value = "/getProperties",produces = { "application/json;charset=utf-8" })
    public String getProperties (@RequestBody List<String> proKey){
        long methodStartTime =System.currentTimeMillis();
        logger.info("[业务时间:"+ DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss.hs")+"]"+"[PropertiesController.getProperties方法start]"+"[根据proKey," +
                "查询service配置项]"+"[入参:"+"proKey ="+proKey+"]");
        try{
            String properties = propertiesServiceImpl.getProperties(proKey);
            long methodEndTime = System.currentTimeMillis();
            logger.info("[PropertiesController.getProperties方法end]"+"[根据proKey" + "验证访问授权码]"+"[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]");
            return properties;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("[业务时间:"+DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss.hs")+"]"+"[PropertiesController.getProperties方法]"+"[捕获异常]"+e.getMessage());
            return "读取Service失败";
        }
    }
}
