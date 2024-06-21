package com.icbcaxa.eata;

import com.icbcaxa.eata.annotation.EnableCat;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class
})
@EnableDiscoveryClient
//@EnableCat(basePackages = {"com.icbcaxa.eata.web.controller","com.icbcaxa.eata.service.**"})
@MapperScan(basePackages = {"com.icbcaxa.eata.mapper"})
public class ServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		try{
			SpringApplication.run(ServiceApplication.class, args);
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ServiceApplication.class);
	}

}
