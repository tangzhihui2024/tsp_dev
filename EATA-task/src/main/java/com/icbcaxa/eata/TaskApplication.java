package com.icbcaxa.eata;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class TaskApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }
}
