package com.icbcaxa.eata.filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;

import javax.servlet.http.HttpServletRequest;

@Component
@RefreshScope
public class AccessFilter extends ZuulFilter {

	private Logger logger = LoggerFactory.getLogger(AccessFilter.class);

	@Value("${versionToggle}")
	public boolean versionToggle;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		// 执行顺序
		return 1;
	}

	public boolean shouldFilter() {
		return true;
	}

	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		logger.info("["+request.getRequestURI()+"方法]+[网关拦截请求信息:requestUrl+"+request.getRequestURI()+"]+[versionToggle:"+versionToggle+"]");
		if(versionToggle){
			String version = ctx.getRequest().getHeader("version");
			if(null != version) {
				RibbonFilterContextHolder.getCurrentContext().add("version", version);
			}else{
				RibbonFilterContextHolder.getCurrentContext().remove("version");
			}
		}else{
			RibbonFilterContextHolder.getCurrentContext().remove("version");
		}
		return null;
	}
}
