package com.icbcaxa.eata.controller;

import com.icbcaxa.eata.service.GatewayPropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by tangzh on 2018/7/19.
 */
@RestController
@RequestMapping(value = "/get")
public class GatewayPropertiesController {

    private static final Logger logger = LoggerFactory.getLogger(GatewayPropertiesController.class);

    @Autowired
    private GatewayPropertiesService gatewayPropertiesServiceImpl;

    @RequestMapping(value = "/getGatewayProperties",produces = { "application/json;charset=utf-8" })
    public String getGatewayProperties (@RequestBody List<String> proKey){
        try{
            return gatewayPropertiesServiceImpl.getGatewayProperties(proKey);
        }catch (Exception e){
            logger.info(e.getMessage());
            e.printStackTrace();
            return "读取Gateway失败";
        }
    }
}
