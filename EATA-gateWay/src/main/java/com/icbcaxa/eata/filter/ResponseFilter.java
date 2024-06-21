package com.icbcaxa.eata.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.Charset;

@Component
public class ResponseFilter  extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger(ResponseFilter.class);
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestMethod  = request.getRequestURI();
        String requestData = ReadAsChars(request);
        if(StringUtils.isNotEmpty(requestData)){
            if(requestData.indexOf("refGUID")>0 && requestData.indexOf("refGUID") <requestData.indexOf(",")){
                logger.info("["+requestMethod+"方法]+[网关拦截器]+[请求流水号: "+requestData.substring(requestData.indexOf("refGUID"),requestData.indexOf(","))+"]");
                logger.debug("["+requestMethod+"方法]+[网关拦截器]+[请求流水号: "+requestData.substring(requestData.indexOf("refGUID"),requestData.indexOf(","))+" 请求报文:"+requestData+"]");
            }
            logger.debug("["+requestMethod+"方法]+[网关拦截器]+[请求报文:"+requestData+"]");
        }
        return true;
    }
    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        String refGUID = "";
        InputStream stream = context.getResponseDataStream();
        HttpServletRequest request = context.getRequest();
        String requestMethod  = request.getRequestURI();
        try
        {
            String requestData = ReadAsChars(request);
            if(StringUtils.isNotEmpty(requestData)){
                if(requestData.indexOf("refGUID")>0 && requestData.indexOf("refGUID") <requestData.indexOf(",")) {
                    refGUID =  requestData.substring(requestData.indexOf("refGUID"), requestData.indexOf(","));
                }
            }
            String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            logger.info("["+requestMethod+"方法]+[网关拦截器响应]+[请求流水号:"+refGUID+"]");
            logger.debug("["+requestMethod+"方法]+[网关拦截器]+[请求流水号:"+refGUID+" 响应报文:"+body+"]");
            context.setResponseBody(body);
        }
        catch (Exception e)
        {
            logger.error("["+requestMethod+"方法]+[网关拦截器_请求流水号:"+refGUID+"]+[捕获异常:"+e.getMessage()+"]");
        }
        return null;
    }

    public static String ReadAsChars(HttpServletRequest request)
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
