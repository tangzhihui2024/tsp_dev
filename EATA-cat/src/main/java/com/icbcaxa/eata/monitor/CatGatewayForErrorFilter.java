package com.icbcaxa.eata.monitor;

import com.dianping.cat.Cat;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CatGatewayForErrorFilter extends ZuulFilter {

	Logger logger = LoggerFactory.getLogger(CatGatewayForErrorFilter.class);

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {

		RequestContext ctx = RequestContext.getCurrentContext();
		try {
			if(ctx != null){
				Throwable throwable = ctx.getThrowable();
				Cat.getProducer().logError(throwable);
				Cat.logMetricForCount(throwable.toString().split(":")[0]);
			}
		} catch (Exception e) {
			logger.error("CatMonitorForErrorFilter.run", e);
		}
		return null;
	}

	@Override
	public String filterType() {
		return "error";
	}

	@Override
	public int filterOrder() {
		return -2;
	}

}
