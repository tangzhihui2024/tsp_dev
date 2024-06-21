package com.icbcaxa.eata;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
@EnableTurbine
@EnableHystrix
@EnableHystrixDashboard
public class TurbinApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

    public static void main(String[] args) {
        try{
            System.out.println("=====================System true====================");

            SpringApplication.run(TurbinApplication.class, args);
        }catch (Exception e){
            System.out.println("=====================System false====================");
            e.printStackTrace();
        }

    }
}
