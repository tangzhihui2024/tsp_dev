package com.icbcaxa.eata;

import com.dianping.cat.Cat;
import com.icbcaxa.eata.LoadCatDefinition.CatDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

public class ServerStarterListenerForCat implements ApplicationListener<ContextRefreshedEvent> {

	private static Logger logger = LoggerFactory.getLogger(ServerStarterListenerForCat.class);

	private String prefix;
	private String suffix;

	public ServerStarterListenerForCat(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			CatDefinition catDefinition = LoadCatDefinition.getCatDefinition();
			if(catDefinition != null && catDefinition.isEnabledCat()){
				Cat.getProducer().logTrace("Application.start", "success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("无法连接到cat监控服务器...");
		}
	}

}
