package com.icbcaxa.eata.listener;

import com.dianping.cat.Cat;
import com.netflix.appinfo.InstanceInfo;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EurekaStateChangeListener {
    
    @EventListener
    public void listen(EurekaInstanceCanceledEvent eurekaInstanceCanceledEvent) {
        //服务断线事件
        String appName = eurekaInstanceCanceledEvent.getAppName();
        String serverId = eurekaInstanceCanceledEvent.getServerId();
        Cat.logMetricForCount(serverId+"_"+appName+"_DOWN");
        System.out.println(appName);
        System.out.println(serverId);

    }
 
    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        InstanceInfo instanceInfo = event.getInstanceInfo();
        System.out.println(instanceInfo);
    }
 
    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        event.getAppName();
        event.getServerId();
    }
 
    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
 
    }
 
    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        //Server启动
    }
}