package com.icbcaxa.eata.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @RequestMapping("/servercheck")
    public String serverCheck(){
        return "1";
    }

}
