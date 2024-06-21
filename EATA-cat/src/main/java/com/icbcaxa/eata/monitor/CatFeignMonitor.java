package com.icbcaxa.eata.monitor;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.icbcaxa.eata.constant.CatConstant;
import com.icbcaxa.eata.context.RemoteContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatFeignMonitor implements RequestInterceptor {

    private String type = CatConstant.TYPE_REMOTE_FEIGN.value();

    public void apply(RequestTemplate requestTemplate){
        RemoteContext ctx = new RemoteContext();
        Transaction t = null;
        try{
            t = Cat.getProducer().newTransaction(type,requestTemplate.url());
            Cat.getProducer().logEvent(type, type+".Url", Event.SUCCESS, requestTemplate.url());
            Cat.getProducer().logEvent(type, type+".Method", Event.SUCCESS, requestTemplate.method());
            Cat.logRemoteCallClient(ctx);
            requestTemplate.header(Cat.Context.ROOT, ctx.getProperty(Cat.Context.ROOT));
            requestTemplate.header(Cat.Context.PARENT, ctx.getProperty(Cat.Context.PARENT));
            requestTemplate.header(Cat.Context.CHILD, ctx.getProperty(Cat.Context.CHILD));
            t.setStatus(Transaction.SUCCESS);
        }catch (Exception e){
            Cat.logError(e);
        }finally {
            if(t != null){
                t.complete();
            }
        }

    }
}