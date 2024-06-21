package com.icbcaxa.eata.service.impl;

import com.alibaba.fastjson.JSON;
import com.icbcaxa.eata.service.PropertiesService;
import com.icbcaxa.eata.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangzh on 2018/6/15.
 */
@Service
public class PropertiesServiceImpl implements PropertiesService {

    private Logger logger = LoggerFactory.getLogger(PropertiesServiceImpl.class);

    @Autowired
    private Environment env;

    @Override
    public String getProperties(List<String> proKey) {
        long methodStartTime =System.currentTimeMillis();
        logger.info("[PropertiesServiceImpl.getProperties方法start]"+"[根据proKey,查询service配置项]" + "[入参:"+"proKey ="+proKey+"]");
        List<String> resultKey = new ArrayList<>();
        try{
            if(null != proKey && proKey.size() > 0){
                for (String key : proKey) {
                    String value = env.getProperty(key);
                    resultKey.add(key+"="+value);
                }
            }
            String json = JSONUtil.list2json(resultKey);
            long methodEndTime = System.currentTimeMillis();
            logger.info("[PropertiesServiceImpl.getProperties方法end]"+"[根据proKey,查询service配置项]"+"[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]" +
                    "返回值:"+ JSON.toJSONString(json));
            return json;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("[PropertiesServiceImpl.getProperties方法]"+"[捕获异常]"+e.getMessage());
            return "读取失败";
        }
    }
}
