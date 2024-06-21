package com.icbcaxa.eata.monitor;

import com.dianping.cat.message.Transaction;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CatGatewayRequestAfter extends ZuulFilter{

	Logger logger = LoggerFactory.getLogger(CatGatewayRequestAfter.class);

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		long methodStartTime =System.currentTimeMillis();
		Transaction t = null;
		try {
			RequestContext ctx = RequestContext.getCurrentContext();
			if(ctx != null){
				HttpServletRequest request = ctx.getRequest();
				if(request != null){
					Object attr = request.getAttribute("catTransaction");
					Object beginTime = request.getAttribute("beginTime");
					if(attr != null){
						t = (Transaction) attr;
						t.setStatus(Transaction.SUCCESS);
					}
					long methodEndTime = System.currentTimeMillis();
					logger.info("[CatGatewayRequestAfter.run方法end]+[方法耗时:"+(methodEndTime-Long.parseLong(beginTime.toString()))/1000F+"秒]");
				}
			}
		} catch (Exception e) {
			logger.error("CatMonitorRequestAfter exception",e);
		} finally {
			if(t != null){
				t.complete();
			}
		}
		long clazzEndTime =System.currentTimeMillis();
		logger.info("[CatMonitorRequestAfter.run]+[方法耗时:"+(clazzEndTime-methodStartTime)/1000F+"秒]");
		return null;
	}

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
