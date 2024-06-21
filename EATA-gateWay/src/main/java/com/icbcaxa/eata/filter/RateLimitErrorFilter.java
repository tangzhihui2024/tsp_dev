package com.icbcaxa.eata.filter;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@Component
public class RateLimitErrorFilter extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger(RateLimitErrorFilter.class);
    @Override
    public String filterType() {
        return "error";
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
        HttpServletResponse response =  ctx.getResponse();
         if(response.getStatus() == 429){
             String refGUID = "";
             HttpServletRequest request = ctx.getRequest();
             String requestMethod  = request.getRequestURI();
             String requestData = ReadAsChars(request);
             if(StringUtils.isNotEmpty(requestData)){
                 if(requestData.indexOf("refGUID")>0 && requestData.indexOf("refGUID") <requestData.indexOf(",")) {
                     refGUID =  requestData.substring(requestData.indexOf("refGUID"), requestData.indexOf(","));
                 }
             }
             Cat.logMetricForCount("RateLimit Count");
             logger.info("["+requestMethod+"方法]+[网关限流开启]+[请求流水号"+refGUID+"]");
             logger.debug("["+requestMethod+"方法]+[网关限流开启]+[请求报文:"+requestData+"]");
            for (String name : response.getHeaderNames()) {
                logger.info("["+requestMethod+"方法]+[限流客户端信息:" + name + ":" + response.getHeader(name)+"]+[-]");
                Cat.logEvent("RateLimit.Head", name, Event.SUCCESS, response.getHeader(name));
            }

         }

        return null;
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
