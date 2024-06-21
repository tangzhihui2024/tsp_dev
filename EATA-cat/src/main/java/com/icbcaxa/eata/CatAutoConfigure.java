package com.icbcaxa.eata;

import com.icbcaxa.eata.properties.CatServiceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(ServerStarterListenerForCat.class)
@EnableConfigurationProperties(CatServiceProperties.class)
public class CatAutoConfigure {

    @Autowired
    private CatServiceProperties properties;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "cat",value = "enabled",havingValue = "true")
    ServerStarterListenerForCat serverStarterListenerForCat (){
        return  new ServerStarterListenerForCat(properties.getPrefix(),properties.getSuffix());
    }

}