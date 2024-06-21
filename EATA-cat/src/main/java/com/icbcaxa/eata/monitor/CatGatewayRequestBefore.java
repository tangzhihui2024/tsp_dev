package com.icbcaxa.eata.monitor;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.icbcaxa.eata.context.RemoteContext;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.site.lookup.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class CatGatewayRequestBefore extends ZuulFilter{

	Logger logger = LoggerFactory.getLogger(CatGatewayRequestBefore.class);


	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		long methodStartTime =System.currentTimeMillis();
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String requestURL = "";
		Transaction t = null;
		String refGUID =null;
		try {
			if(request != null) {
				requestURL = request.getRequestURL()!=null?request.getRequestURL().toString():"";
			}
			if(!"".equals(requestURL)){
				String requestData = ReadAsChars(request);
				if(StringUtils.isNotEmpty(requestData)){
					if(requestData.indexOf("refGUID")>0 && requestData.indexOf("refGUID") <requestData.indexOf(",")) {
						refGUID =  requestData.substring(requestData.indexOf("refGUID"), requestData.indexOf(","));
					}
				}
				t = Cat.newTransaction("GateWay.Request.URL",requestURL+"_"+refGUID);
				Cat.logMetricForCount(requestURL);
				request.setAttribute("catTransaction", t);
				request.setAttribute("beginTime", methodStartTime);
				RemoteContext remoteContextZuul = new RemoteContext();
				Cat.logRemoteCallClient(remoteContextZuul);
				ctx.addZuulRequestHeader(Cat.Context.ROOT,remoteContextZuul.getProperty(Cat.Context.ROOT));
				ctx.addZuulRequestHeader(Cat.Context.PARENT,remoteContextZuul.getProperty(Cat.Context.PARENT));
				ctx.addZuulRequestHeader(Cat.Context.CHILD,remoteContextZuul.getProperty(Cat.Context.CHILD));
				ctx.addZuulRequestHeader("refGUID",refGUID);
			}
		} catch (Exception e) {
			logger.error("CatMonitorRequestBefore.run", e);
		}
		long clazzEndTime =System.currentTimeMillis();
		logger.info("[CatGatewayRequestBefore.run]+[方法耗时:"+(clazzEndTime-methodStartTime)/1000F+"秒]");
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return -1;
	}

	private static String ReadAsChars(HttpServletRequest request)
	{

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder("");
		try
		{
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String str;
			while ((str = br.readLine()) != null)
			{
				sb.append(str);
			}
			br.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (null != br)
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

}
