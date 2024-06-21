package com.icbcaxa.eata.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Cat监控开启注解
 * @author fulan
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface EnableCat {
	
	/** 
	 * <pre>
	 * 配置的包里面的类将会被监控.
	 * 支持*(一级目录),**(多级目录)通配符
	 *  条件：
	 * 包里面的类需要被spring容器管理. 
	 * 目前带有@Controller,@RestController,@Service
	 * 注解的类被纳入监控范围.
	 * </pre>
	 *
	 */
	String[] basePackages() default {};
	
	/** 排除某些包 */
	String[] exclusions() default {};
	
	/**
	 * <pre>
	 * 远程调用监控 
	 * 默认值为"no"
	 * no: 不开启远程调用监控
	 * rest: 开启restTemplate监控
	 * feign: 开启feign监控
	 * </pre>
	 * @return
	 */
	String remote() default "no";

	/**
	 * <pre>
	 * 持久层监控 
	 * 默认值为"no"
	 * no: 不开启持久层监控
	 * jpa: 开启spring data jpa监控
	 * </pre>
	 * @return
	 */
	String dao() default "no";
}
