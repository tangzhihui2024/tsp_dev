package com.icbcaxa.eata.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class ConfigRefreshController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${bus.url}")
    public String url;

    @RequestMapping("/config/refresh")
    public String refresh(){
        try {
            restTemplate.postForObject(url, "", String.class);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "success";
    }
}
