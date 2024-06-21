package com.icbcaxa.eata.handle;
import com.dianping.cat.Cat;
import com.icbcaxa.eata.filter.ResponseFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 降级提供类
 */
@Component
public class ServiceFallbackProvider implements ZuulFallbackProvider {
    private Logger logger = LoggerFactory.getLogger(ServiceFallbackProvider.class);
    @Override
    public String getRoute() {
        return null;
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {


            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                RequestContext ctx = RequestContext.getCurrentContext();
                String refGUID = "";
                HttpServletRequest request = ctx.getRequest();
                String requestMethod  = request.getRequestURI();
                String requestData = ResponseFilter.ReadAsChars(request);
                if(StringUtils.isNotEmpty(requestData)){
                    if(requestData.indexOf("refGUID")>0 && requestData.indexOf("refGUID") <requestData.indexOf(",")) {
                        refGUID =  requestData.substring(requestData.indexOf("refGUID"), requestData.indexOf(","));
                    }
                }
                logger.error("["+requestMethod+"方法]+[[网关熔断开启响应_请求报文:"+requestData+"]]+[捕获异常:{\"code\": \"99\",\"message\": \"The service is unavailable.\"}]");
                Cat.logMetricForCount("Ribbon Count");
                return new ByteArrayInputStream("{\"code\": \"99\",\"message\": \"The service is unavailable.\"}".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                return new HttpHeaders();
            }
        };
    }
}
