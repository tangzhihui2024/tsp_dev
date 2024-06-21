package com.icbcaxa.eata.monitor;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.icbcaxa.eata.constant.CatConstant;
import com.icbcaxa.eata.context.RemoteContext;
import com.icbcaxa.eata.json.JsonMapper;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * cat远程调用监控切面
 * @author yangzexu
 */
@Component
public class CatRemoteRestTemplateMonitor implements MethodInterceptor {

	private static Logger logger = LoggerFactory.getLogger(CatRemoteRestTemplateMonitor.class);

	private String type = CatConstant.TYPE_REMOTE.value();

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		String methodName = method.getName();
		if(isGetOrPostMethod(methodName)){
			Object[] arguments = invocation.getArguments();
			// 制作一个参数副本
			Object[] clone = clone(arguments);

			Map<String, Object> resultMap = invokeMethodBefore(invocation);

			Object result = invokeMethod(invocation,resultMap,clone);
			Object tObj = resultMap.get("t");
			if(tObj != null){
				try {
					invokeMethodAfter((Transaction)tObj,result,invocation);
				} catch (Exception e) {
					logger.error("CatBusinessMonitor invokeMethodAfter", e);
				}
			}
			return result;
		}
		return invocation.proceed();
	}

	public boolean isGetOrPostMethod(String methodName){
		if(StringUtils.isBlank(methodName)) return false;
		if(methodName.startsWith("postFor") || methodName.startsWith("getFor")){
			return true;
		}
		return false;
	}

	public boolean isGet(String methodName){
		return methodName.startsWith("getFor");
	}

	/**
	 * 远程调用监控,如果需要在同一个Transaction串联调用方和被调用方
	 * 需要通过header传递RemoteContext对象。这里对get或者post类型的
	 * 请求监控，需要往header设置RemoteContext
	 * @param arg1
	 * @param args
	 * @return
	 */
	public String bulidRemoteCall(Object arg1,Object args[]){
		String requestParams = "";
		try {
			if(arg1 == null || args == null || args.length==0){
				return requestParams;
			}
			if (arg1 instanceof HttpEntity) {
				HttpEntity<?> httpEntity = ((HttpEntity<?>) arg1);
				RemoteContext ctx = new RemoteContext();
		        Cat.logRemoteCallClient(ctx);
		        // 此headers不可改变
		        HttpHeaders headers = httpEntity.getHeaders();
		        // 创建一个新的headers
		        HttpHeaders headersNew = new HttpHeaders();
		        // 将headers里面的属性放入headersNew
		        if(headers != null){
		        	Iterator<Entry<String, List<String>>> iterator = headers.entrySet().iterator();
					while(iterator.hasNext()){
						Entry<String, List<String>> entry = (Entry<String, List<String>>)iterator.next();
						headersNew.put(entry.getKey(), entry.getValue());
					}
		        }

				Object body = httpEntity.getBody();
				// 在header中添加cat的context节点传递到被调用的方法
				headersNew.add(Cat.Context.ROOT, ctx.getProperty(Cat.Context.ROOT));
				headersNew.add(Cat.Context.PARENT, ctx.getProperty(Cat.Context.PARENT));
				headersNew.add(Cat.Context.CHILD, ctx.getProperty(Cat.Context.CHILD));
				// 用旧httpEntity的body和headers创建一个新的httpEntity
				HttpEntity<Object> httpEntityNew = new HttpEntity<Object>(body, headersNew);
				// 改变拦截方法的第2个入参
				args[1] = httpEntityNew;
				requestParams = toJsonString(body);
			} else {
				RemoteContext ctx = new RemoteContext();
		        Cat.logRemoteCallClient(ctx);
		        HttpHeaders headersNew = new HttpHeaders();
				headersNew.add(Cat.Context.ROOT, ctx.getProperty(Cat.Context.ROOT));
				headersNew.add(Cat.Context.PARENT, ctx.getProperty(Cat.Context.PARENT));
				headersNew.add(Cat.Context.CHILD, ctx.getProperty(Cat.Context.CHILD));
				HttpEntity<Object> httpEntityNew = new HttpEntity<Object>(arg1, headersNew);
				args[1] = httpEntityNew;
				requestParams = toJsonString(arg1);
			}
		} catch (Exception e) {
			logger.error("CatRemoteRestTemplateMonitor.bulidRemoteCall()", e);
		}
		return requestParams;
	}

	public Map<String, Object> invokeMethodBefore(MethodInvocation invocation){
		Transaction t = null;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Class<?> clazz = invocation.getMethod().getDeclaringClass();
			Method method = invocation.getMethod();
			String methodName = method.getName();
			Object[] args = invocation.getArguments();
			String url = "",requestParams = "";
			String methodStr = clazz.getName() + "." + methodName;

			if(args != null && args.length > 0){
	 			t = Cat.newTransaction(type, CatConstant.TYPE_REMOTE_REST.value());
				int argsLength = args.length;
				if(argsLength >= 2){
					Object arg0 = args[0],arg1 = args[1];
		 			if(arg0 instanceof String){
		 				url = (String)arg0;
		 			}
		 			else if(arg0 instanceof URI){
		 				url = ((URI)arg0).toString();
		 			}
		 			Cat.logEvent(type, type+".Url", Event.SUCCESS, url);
					Cat.logEvent(type, type+".Method", Event.SUCCESS, methodStr);
					if(!isGet(methodName)) {
						requestParams = bulidRemoteCall(arg1,args);
						Cat.logEvent(type, type+".Params", Event.SUCCESS, requestParams);
					}
				}
			}
			result.put("status", "1");
			result.put("t", t);
			return result;
		} catch (Exception e) {
			logger.error("CatRemoteRestTemplateMonitor invokeMethodBefore", e);
			result.put("status", "-1");
			result.put("t", t);
			return result;
		}
	}

	public Object invokeMethod(MethodInvocation methodInvocation, Map<String, Object> paramMap, Object[] clone) throws Throwable{
		Transaction t = null;
		Object tObj = paramMap.get("t");
		Object status = paramMap.get("status");
		if(tObj != null){
			t = (Transaction) tObj;
		}
		if(status != null && "1".equals(status.toString())){
			try {
				Object result = methodInvocation.getMethod().invoke(methodInvocation.getThis(), methodInvocation.getArguments());
				return result;
			} catch (Exception e) {
				if(t != null ) {
					catLogException(t,e);
				}
				throw e;
			}
		} else {
			return methodInvocation.getMethod().invoke(methodInvocation.getThis(), clone);
		}

	}

	public void invokeMethodAfter(Transaction t,Object result,MethodInvocation methodInvocation){
		try {
			if (t != null) {
				Class<?> clazz = methodInvocation.getMethod().getDeclaringClass();
				logEventByResponse(clazz,result);
				t.setStatus(Transaction.SUCCESS);
			}
		} catch (Exception e) {
			logger.error("CatBusinessMonitor invokeMethodAfter", e);
		} finally {
			if (t != null)
				t.complete();
		}

	}

	public void logEventByResponse(Class<?> clazz,Object result){
		try {
			Cat.getProducer().logEvent(type, type+".Response", Event.SUCCESS, toJsonString(result));
		} catch (Exception e) {
			// 不记录Response到cat
			logger.error("CatBusinessMonitor.logEventByResponse", e);
		}

	}

	private void catLogException(Transaction t,Exception e){
		try {
			t.setStatus("-1");
			Cat.getProducer().logError(e);
		} catch (Exception innere) {
			logger.error("CatRemoteRestTemplateMonitor catLogException", innere);
		} finally {
			t.complete();
		}
	}

	public String toJsonString(Object obj){
		String result = "";
		if(obj != null) {
			try {
				result = JsonMapper.getInstance().writeValueAsString(obj);
			} catch (Exception e) {
				result = obj.toString();
			}
		}
		return result;
	}

	/**
	 * 克隆数组
	 * @param array
	 * @return
	 */
	private <T> T[] clone(T[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
	 }

}
