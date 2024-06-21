package com.icbcaxa.eata.monitor;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.icbcaxa.eata.constant.CatConstant;
import com.icbcaxa.eata.context.RemoteContext;
import com.icbcaxa.eata.json.JsonMapper;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

/**
 * 监控controller和service层切面
 * @author fulan
 */
@Component
public class CatBusinessMonitor implements MethodInterceptor {

	private static Logger logger = LoggerFactory.getLogger(CatBusinessMonitor.class);

	ThreadLocal<Boolean> isLogRemoteCall = new ThreadLocal<Boolean>() {
		protected Boolean initialValue() {
			return false;
		};
	};

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Transaction transaction = invokeMethodBefore(methodInvocation);
		Object result = invokeMethod(methodInvocation, transaction);
		if(transaction != null) {
			invokeMethodAfter(transaction,result,methodInvocation);
		}
		return result;
	}

	public Object invokeMethod(MethodInvocation methodInvocation, Transaction t) throws Throwable {
		if (t != null) {
			try {
				Object result = methodInvocation.proceed();
				return result;
			} catch (Exception e) {
				catLogException(t,e);
				throw e;
			}
		} else {
			return methodInvocation.proceed();
		}
	}

	public Transaction invokeMethodBefore(MethodInvocation methodInvocation) {
		long methodStartTime =System.currentTimeMillis();
		String uuid = UUID.randomUUID().toString().replaceAll("-","");
		logger.info("[CatBusinessMonitor.invokeMethodBefore方法]+[流水号 ："+uuid+"]");
		Transaction t = null;
		try {
			Class<?> clazz = methodInvocation.getMethod().getDeclaringClass();
			long clazzEndTime =System.currentTimeMillis();
			logger.info("[CatBusinessMonitor.invokeMethodBefore方法]+[获取clazz: "+clazz+"]+[方法耗时:"+(clazzEndTime-methodStartTime)/1000F+"秒]");
			Method method = methodInvocation.getMethod();
			long meEndTime =System.currentTimeMillis();
			logger.info("[CatBusinessMonitor.invokeMethodBefore方法]+[获取method: "+method+"]+[方法耗时:"+(meEndTime-methodStartTime)/1000F+"秒]");
			Object arguments[] = methodInvocation.getArguments();
			long argumentsEndTime =System.currentTimeMillis();
			logger.info("[CatBusinessMonitor.invokeMethodBefore方法]+[获取arguments: "+arguments+"]+[方法耗时:"+(argumentsEndTime-methodStartTime)/1000F+"秒]");
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			long attributesEndTime =System.currentTimeMillis();
			logger.info("[CatBusinessMonitor.invokeMethodBefore方法]+[获取attributes: "+attributes+"]+[方法耗时:"+(attributesEndTime-methodStartTime)/1000F+"秒]");
			HttpServletRequest request = null;
			if (attributes != null) {
				request = attributes.getRequest();
				long requestEndTime =System.currentTimeMillis();
				logger.info("[CatBusinessMonitor.invokeMethodBefore方法]+[获取request: "+request+"]+[方法耗时:"+(requestEndTime-methodStartTime)/1000F+"秒]");
			}
			String uri = "", type = "", remoteAddr = "", requestMethod = "", methodName = "";
			Integer serverPort = 0;
			if (request != null) {
				uri = request.getRequestURI();
				remoteAddr = request.getRemoteAddr();
				serverPort = request.getServerPort();
			}
			long requestEndTime =System.currentTimeMillis();
			logger.info("[CatBusinessMonitor.invokeMethodBefore方法]+[获取请求地址: "+remoteAddr+"]+[方法耗时:"+(requestEndTime-methodStartTime)/1000F+"秒]");
			if (clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(RestController.class)) {
                String refGUID = request.getHeader("refGUID").toString();
				type = CatConstant.TYPE_URL.value();
                t = Cat.newTransaction(type, uri+"_"+refGUID);
				Cat.logEvent(type, type + ".Server", Event.SUCCESS, remoteAddr + ":" + serverPort);
				logEventByParams(arguments,type,".RequestParams");
			}
			if (clazz.isAnnotationPresent(Service.class)) {
				type = CatConstant.TYPE_SERVICE.value();
				Class<?> interfaces[] = clazz.getInterfaces();
				String serviceName = "";
				if (interfaces == null || interfaces.length == 0) {
					serviceName = clazz.getName();
				} else {
					serviceName = interfaces[0].getName();
				}
				methodName = method.getName();
				t = Cat.newTransaction(type, serviceName);
				Cat.logEvent(type, type + ".Method", Event.SUCCESS, methodName);
				logEventByParams(arguments,type,".Params");
			}
			long methodEndTime = System.currentTimeMillis();
			logger.info("[CatBusinessMonitor.invokeMethodBefore方法end]+[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]");
			return t;
		} catch (Exception e) {
			logger.error("CatBusinessMonitor invokeMethodBefore", e);
			return t;
		}
	}

	public void invokeMethodAfter(Transaction t, Object result, MethodInvocation methodInvocation) {
		long methodStartTime =System.currentTimeMillis();
		try {
			Class<?> clazz = methodInvocation.getMethod().getDeclaringClass();
			logEventByResponse(clazz,result);
			t.setStatus(Transaction.SUCCESS);
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			if (attributes != null) {
				HttpServletRequest request = attributes.getRequest();
				if (request != null) {
					String root = request.getHeader(Cat.Context.ROOT);
					String child = request.getHeader(Cat.Context.CHILD);
					String parent = request.getHeader(Cat.Context.PARENT);
					if (StringUtils.isNotBlank(root) && StringUtils.isNotBlank(parent) && StringUtils.isNotBlank(child)) {
						if (!isLogRemoteCall.get()) {
							isLogRemoteCall.set(true);
							RemoteContext context = new RemoteContext();
							context.addProperty(Cat.Context.ROOT, root);
							context.addProperty(Cat.Context.PARENT, parent);
							context.addProperty(Cat.Context.CHILD, child);
							Cat.logRemoteCallServer(context);
						}
					}
				}
			}
			long methodEndTime = System.currentTimeMillis();
			logger.info("[CatBusinessMonitor.invokeMethodAfter方法end]+[方法耗时:"+(methodEndTime-methodStartTime)/1000F+"秒]");
		} catch (Exception e) {
			logger.error("CatBusinessMonitor invokeMethodAfter", e);
		} finally {
			if (t != null)
				t.complete();
		}
	}

	public void logEventByResponse(Class<?> clazz,Object result){
		String type = CatConstant.TYPE_URL.value();
		try {
			if (clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(RestController.class)) {
				Cat.getProducer().logEvent(type, type+".Response", Event.SUCCESS, toJsonString(result));
			}
		} catch (Exception e) {
			// 不记录Response到cat
			logger.error("CatBusinessMonitor.logEventByResponse", e);
		}
	}

	private void catLogException(Transaction t, Exception e){
		try {
			t.setStatus("-1");
			Cat.getProducer().logError(e);
		} catch (Exception innere) {
			logger.error("CatBusinessMonitor catLogException", innere);
		} finally {
			t.complete();
		}
	}

	public void logEventByParams(Object arguments[],String type,String eventName){
		Cat.logEvent(type, type + eventName, Event.SUCCESS, toJsonString(arguments));
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

}
