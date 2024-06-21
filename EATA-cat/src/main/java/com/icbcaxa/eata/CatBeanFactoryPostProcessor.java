package com.icbcaxa.eata;

import com.icbcaxa.eata.annotation.EnableCat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

@Component
public class CatBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	
	private static Logger logger = LoggerFactory.getLogger(CatBeanFactoryPostProcessor.class);

	private Class<? extends Object> springBootClass = null;
	private boolean isLoaded = false;

	private CatBeanFactoryPostProcessor() {
	}

	private static CatBeanFactoryPostProcessor single = null;
	public static CatBeanFactoryPostProcessor getInstance() {
		if (single == null) {
			single = new CatBeanFactoryPostProcessor();
		}
		return single;
	}
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		try {
			Map<String, Object> beansWithAnnotation = beanFactory.getBeansWithAnnotation(EnableCat.class);
			if (beansWithAnnotation != null) {
				Iterator<Entry<String, Object>> iterator = beansWithAnnotation.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, Object> entry = iterator.next();
					if(entry != null){
						Object bootObj = entry.getValue();
						if (bootObj != null) {
							Class<? extends Object> bootClass = bootObj.getClass();
							if (bootClass.isAnnotationPresent(EnableCat.class)) {
								this.springBootClass = bootClass;
								this.isLoaded = true;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("postProcessBeanFactory() Exception", e);
		}
	}

	public boolean isLoaded() {
		return this.isLoaded;
	}

	public Class<? extends Object> getCatBootClass() {
		return this.springBootClass;
	}

	/**
	 * @return the springBootClass
	 */
	public Class<? extends Object> getSpringBootClass() {
		return springBootClass;
	}

	/**
	 * @param springBootClass
	 *            the springBootClass to set
	 */
	public void setSpringBootClass(Class<? extends Object> springBootClass) {
		this.springBootClass = springBootClass;
	}

	/**
	 * @param isLoaded
	 *            the isLoaded to set
	 */
	public void setLoaded(boolean isLoaded) {
		this.isLoaded = isLoaded;
	}

}
