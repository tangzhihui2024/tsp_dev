package com.icbcaxa.eata.service.impl;

import com.icbcaxa.eata.service.GatewayPropertiesService;
import com.icbcaxa.eata.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangzh on 2018/7/19.
 */
@Service
public class GatewayPropertiesServiceImpl implements GatewayPropertiesService {

    @Autowired
    private Environment env;
    @Override
    public String getGatewayProperties(List<String> proKey) {
        List<String> resultKey = new ArrayList<>();
        try{
            if(null != proKey && proKey.size() > 0){
                for (String key : proKey) {
                    String value = env.getProperty(key);
                    resultKey.add(key+"="+value);
                }
            }
            String json = JSONUtil.list2json(resultKey);
            return json;
        }catch (Exception e){
            e.printStackTrace();
            return "读取Gateway失败";
        }
    }
}
