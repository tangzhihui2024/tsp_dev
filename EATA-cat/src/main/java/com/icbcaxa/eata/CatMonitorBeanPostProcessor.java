package com.icbcaxa.eata;

import com.icbcaxa.eata.annotation.EnableCat;
import com.icbcaxa.eata.constant.Constant;
import com.icbcaxa.eata.monitor.CatBusinessMonitor;
import com.icbcaxa.eata.monitor.CatRemoteRestTemplateMonitor;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CatMonitorBeanPostProcessor implements BeanPostProcessor {

	private static Logger logger = LoggerFactory.getLogger(CatMonitorBeanPostProcessor.class);

	@Autowired
	private CatRemoteRestTemplateMonitor catRemoteRestTemplateMonitor;

//	@Autowired
//	private CatSQLRespositoryMonitor catSQLRespositoryMonitor;

	@Autowired
	private CatBusinessMonitor catBusinessMonitor;

	/** 需要拦截的包 */
	private static String BASE_PACKAGES[]; // 持久层以及远程调用监控不受此约束

	/** 过滤的包 */
	private static String EXCLUDING[]; // 持久层以及远程调用监控不受此约束

	/** 是否开启远程调用监控 */
	private static String REMOTE;

	/** 是否开启持久层监控 */
	private static String DAO;

	/** 一层目录匹配 */
	private static final String ONE_REGEX = "[a-zA-Z0-9_]+";

	/** 多层目录匹配 */
	private static final String ALL_REGEX = ".*";

	private static final String END_ALL_REGEX = "*";

	/** 是否开启了CAT监控 */
	private static boolean ENABLED_CAT = false;

	private static Class<? extends Object> catBootClass = null;

	private static LoadCatDefinition.CatDefinition catDefinition;

	private ThreadLocal<Boolean> isEnhanceForDaoJpa = new ThreadLocal<Boolean>(){
		@Override
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}
	};

	static {
		if (CatBeanFactoryPostProcessor.getInstance().isLoaded()) {
			catBootClass = CatBeanFactoryPostProcessor.getInstance().getCatBootClass();
		}
		if (catBootClass != null) {
			EnableCat cat = catBootClass.getAnnotation(EnableCat.class);
			BASE_PACKAGES = cat.basePackages();
			EXCLUDING = cat.exclusions();
			REMOTE = cat.remote();
			DAO = cat.dao();
		} else {
			init();
		}
	}

	private static void init() {
		catDefinition = LoadCatDefinition.getCatDefinition();
		ENABLED_CAT = catDefinition.isEnabledCat();
		BASE_PACKAGES = catDefinition.getBasePackages();
		EXCLUDING = catDefinition.getExcluding();
		REMOTE = catDefinition.getRemote();
		DAO = catDefinition.getDao();
	}
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		try {
			if(ENABLED_CAT || CatBeanFactoryPostProcessor.getInstance().isLoaded()){
				// 开启持久层监控
//				if(!Constant.DAO_DEFAULT.equals(DAO)){
//					if(Constant.DAO_JPA.equals(DAO)){
//						Object proxyObj = doEnhanceForDaoJpa(bean);
//						if(proxyObj != null) {
//							return proxyObj;
//						}
//					}
//				}
				// 开启Rest监控
				if (!Constant.REMOTE_DEFAULT.equals(REMOTE)) {
					if (Constant.REMOTE_REST.equals(REMOTE)) {
						Object proxyObj = doEnhanceForRemoteRest(bean);
						if(proxyObj != null) {
							return proxyObj;
						}
					}
				}

				// 根据注解配置的包名监控对应的控制层和其他业务层
				if (BASE_PACKAGES != null && BASE_PACKAGES.length > 0) {
					Object proxyObj = doEnhanceForControllerAndBusiness(bean);
					if(proxyObj != null) {
						return proxyObj;
					}
				}
			}
		} catch (Exception e) {
			logger.error("postProcessAfterInitialization() Exception", e);
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	private static boolean matchingPackage(String basePackage, String currentPackage) {
		if (StringUtils.isEmpty(basePackage) || StringUtils.isEmpty(currentPackage)) {
			return false;
		}
		if (basePackage.indexOf("*") != -1) {
			String patterns[] = StringUtils.split(basePackage, ".");
			for (int i = 0; i < patterns.length; i++) {
				String patternNode = patterns[i];
				if (patternNode.equals("*")) {
					patterns[i] = ONE_REGEX;
				}
				if (patternNode.equals("**")) {
					if(i == patterns.length-1) {
						patterns[i] = END_ALL_REGEX;
					} else {
						patterns[i] = ALL_REGEX;
					}
				}
			}
			String basePackageRegex = StringUtils.join(patterns, "\\.");
			Pattern r = Pattern.compile(basePackageRegex);
			Matcher m = r.matcher(currentPackage);
			return m.find();
		} else {
			return basePackage.equals(currentPackage);
		}
	}

	private String getBeanPackageName(Object bean){
		String beanPackageName = "";
		if (bean != null) {
			Class<?> beanClass = bean.getClass();
			if (beanClass != null) {
				Package beanPackage = beanClass.getPackage();
				if (beanPackage != null) {
					beanPackageName = beanPackage.getName();
				}
			}
		}
		return beanPackageName;
	}

	private Object doEnhanceForRemoteRest(Object bean){
		if (bean instanceof RestTemplate) {
			ProxyFactory proxy = new ProxyFactory();
			proxy.setTarget(bean);
			proxy.addAdvice(catRemoteRestTemplateMonitor);
			return proxy.getProxy();
		}
		return null;
	}

//	private Object doEnhanceForDaoJpa(Object bean) throws Exception{
//		if (bean instanceof Repository) {
//			if(!isEnhanceForDaoJpa.get()){
//				Class<?> superclass = bean.getClass().getSuperclass();
//				if(superclass != null){
//					Field h = superclass.getDeclaredField("h");
//					if(h != null){
//						h.setAccessible(true);
//						AopProxy aopProxy = (AopProxy) h.get(bean);
//						Field advised = aopProxy.getClass().getDeclaredField("advised");
//						if(advised != null){
//							advised.setAccessible(true);
//							AdvisedSupport support = ((AdvisedSupport) advised.get(aopProxy));
//							Advisor[] advisors = support.getAdvisors();
//							if(advisors != null && advisors.length >= 5){
//								support.addAdvice(5, catSQLRespositoryMonitor);
//							} else if(advisors != null){
//								support.addAdvice(catSQLRespositoryMonitor);
//							}
//							isEnhanceForDaoJpa.set(Boolean.TRUE);
//							return aopProxy.getProxy();
//						}
//					}
//				}
//			} else {
//				return bean;
//			}
//		}
//		return null;
//	}

	private Object doEnhanceForControllerAndBusiness(Object bean){
		String beanPackageName = getBeanPackageName(bean);
		if(StringUtils.isNotBlank(beanPackageName)){
			for (String basePackage : BASE_PACKAGES) {
				if (matchingPackage(basePackage, beanPackageName)) {
					if (EXCLUDING != null && EXCLUDING.length > 0) {
						boolean isExclude = false;
						for (String excluding : EXCLUDING) {
							if (matchingPackage(excluding, beanPackageName)) {
								isExclude = true;
							}
						}
						if (!isExclude) {
							ProxyFactory proxy = new ProxyFactory();
							proxy.setTarget(bean);
							proxy.addAdvice(catBusinessMonitor);
							return proxy.getProxy();
						}
					} else {
						ProxyFactory proxy = new ProxyFactory();
						proxy.setTarget(bean);
						proxy.addAdvice(catBusinessMonitor);
						return proxy.getProxy();
					}
				}
			}
		}
		return null;
	}

}
