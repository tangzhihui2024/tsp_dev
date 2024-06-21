package com.icbcaxa.eata.web.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @RequestMapping("/servercheck")
    public String serverCheck(){
        return "1";
    }

}
