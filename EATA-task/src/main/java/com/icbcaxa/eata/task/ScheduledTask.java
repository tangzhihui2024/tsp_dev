package com.icbcaxa.eata.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.Charset;
import java.util.*;
import java.text.SimpleDateFormat;

@Component
public class ScheduledTask {


    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");

    private static final SimpleDateFormat dateFormatt = new SimpleDateFormat("YYYY-MM-DD");

    private String eurekaUrl="http://84.238.29.217:9080/checkserverstate";

    private String configUrl="http://84.238.29.218:9080/checkserverstate";

    private String eataServerUrl="http://84.238.29.219:9080/checkserverstate";

    private String eataServerUrlT="http://84.238.29.220:9080/checkserverstate";

    private String gateWayUrl="http://84.238.29.221:9080/checkserverstate";

    private String turbineUrl="http://84.238.29.222:9080/checkserverstate";

    @Scheduled(fixedRate = 30000)
    public void requestTest(){
        logger.info("-------------------job start");
        requestGenerate();
        logger.info("-------------------job end");
    }

    public void  requestGenerate(){

        try {
            String eurekaServerState = restTemplate().postForObject(eurekaUrl, "", String.class);
            logger.info("eurekaServer On-line");
            }catch (Exception ex){
             logger.info("eurekaServersfail:"+ ex.getMessage());
        }

        try {
            String configServerState = restTemplate().postForObject(configUrl, "", String.class);
            logger.info("configServer On-line");
        }catch (Exception ex){
            logger.info("configServer fail :"+ ex.getMessage());
        }

        try {
            String eataServerState = restTemplate().postForObject(eataServerUrl, "", String.class);
            logger.info("eataServer On-line");
        }catch (Exception ex){
            logger.info("eataServer fail:"+ ex.getMessage());
        }


        try {
            String eataServerStates = restTemplate().postForObject(eataServerUrlT, "", String.class);
            logger.info("eataServer2 On-line");
        }catch (Exception ex){
            logger.info("eataServer2 fail:"+ ex.getMessage());
        }


        try {
            String gateWayServerState = restTemplate().postForObject(gateWayUrl, "", String.class);
            logger.info("gateWayServer On-line");
        }catch (Exception ex){
            logger.info("gateWayServer fail :"+ ex.getMessage());
        }

        try {
            String turbineServerState = restTemplate().postForObject(turbineUrl, "", String.class);
            logger.info("turbineServer On-line");
        }catch (Exception ex){
            logger.info("turbineServer  fail :"+ ex.getMessage());
        }

    }

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(10000);
        requestFactory.setConnectTimeout(10000);

        // 转换器
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(new MappingJackson2HttpMessageConverter());

        RestTemplate restTemplate = new RestTemplate(messageConverters);
        restTemplate.setRequestFactory(requestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        return restTemplate;
    }


}
