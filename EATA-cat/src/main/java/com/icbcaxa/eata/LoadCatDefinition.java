package com.icbcaxa.eata;

import com.icbcaxa.eata.annotation.EnableCat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

public class LoadCatDefinition {
	
	private static Logger logger = LoggerFactory.getLogger(LoadCatDefinition.class);
	private static CatDefinition catDefinition = new CatDefinition();
	
	static {
		init();
	}
	
	public static CatDefinition getCatDefinition(){
		return catDefinition;
	}
	
	private static void init() {
		Resource[] resources = null;
		try {
			ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
			MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
			// 加载系统所有类资源
			resources = resourcePatternResolver.getResources("classpath*:com/icbcaxa/eata/**/*.class");
			// 把每一个class文件找出来
			for (Resource r : resources) {
				MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(r);
				Class<?> clazz = ClassUtils.forName(metadataReader.getClassMetadata().getClassName(), null);
				if (clazz.isAnnotationPresent(EnableCat.class)) {
					catDefinition.setEnabledCat(true);
					EnableCat cat = clazz.getAnnotation(EnableCat.class);
					catDefinition.setBasePackages(cat.basePackages());
					catDefinition.setExcluding(cat.exclusions());
					catDefinition.setRemote(cat.remote());
					catDefinition.setDao(cat.dao());
					break;
				}
			}
		} catch (Exception e) {
			logger.error("LoadCatDefinition.init", e);
		} finally {
			resources = null;
		}
	}
	
	public static class CatDefinition {
		private boolean enabledCat = false;
		private String basePackages[];
		private String excluding[];
		private String remote;
		private String dao;
		private String enableGateWay;
		public boolean isEnabledCat() {
			return enabledCat;
		}
		private void setEnabledCat(boolean enabledCat) {
			this.enabledCat = enabledCat;
		}
		public String[] getBasePackages() {
			return basePackages;
		}
		private void setBasePackages(String basePackages[]) {
			this.basePackages = basePackages;
		}
		public String[] getExcluding() {
			return excluding;
		}
		private void setExcluding(String excluding[]) {
			this.excluding = excluding;
		}
		public String getRemote() {
			return remote;
		}
		private void setRemote(String remote) {
			this.remote = remote;
		}
		public String getDao() {
			return dao;
		}
		private void setDao(String dao) {
			this.dao = dao;
		}
		public String getEnableGateWay() {
			return enableGateWay;
		}
		private void setEnableGateWay(String enableGateWay) {
			this.enableGateWay = enableGateWay;
		}
	}
}
