package com.icbcaxa.eata.monitor;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.icbcaxa.eata.constant.CatConstant;
import com.icbcaxa.eata.json.JsonMapper;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.stereotype.Component;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * 监控Sql执行状态
 * yangzexu
 */
@Intercepts({
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class })})
@Component
public class CatSQLRespositoryMonitor implements Interceptor {


//	@Value("${spring.datasource.url}")
	private String url = "jdbc:url";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		//得到 类名-方法
		String[] strArr = mappedStatement.getId().split("\\.");
		String class_method = strArr[strArr.length-2] + "." + strArr[strArr.length-1];
		//得到sql语句
		Object parameter = null;
		if(invocation.getArgs().length > 1){
			parameter = invocation.getArgs()[1];
		}
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		Configuration configuration = mappedStatement.getConfiguration();
		String sql = showSql(configuration, boundSql);

		String  type = CatConstant.TYPE_SQL.value();
		Transaction t = Cat.newTransaction(type+".Method", class_method);
		Cat.logEvent(type +".Statement", sql.substring(0, sql.indexOf(" ")).toUpperCase(), Message.SUCCESS, sql);
		Cat.logEvent(type, type + ".Database", Event.SUCCESS, url);
		Cat.logEvent(type, type+".Params", Event.SUCCESS, toJsonString(parameter));
		Object returnObj = null;
		try {

			returnObj = invocation.proceed();
			t.setStatus(Message.SUCCESS);

		} catch(Exception e) {
			Cat.logError(e);
		} finally{
			t.complete();
		}
		return returnObj;

	}

	public String showSql(Configuration configuration, BoundSql boundSql) {
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
		if (parameterMappings.size() > 0 && parameterObject != null) {
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));

			} else {
				MetaObject metaObject = configuration.newMetaObject(parameterObject);
				for (ParameterMapping parameterMapping : parameterMappings) {
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName)) {
						Object obj = metaObject.getValue(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						Object obj = boundSql.getAdditionalParameter(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					}
				}
			}
		}
		return sql;
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

	private String getParameterValue(Object obj) {
		String value = null;
		if (obj instanceof String) {
			value = "'" + obj.toString() + "'";
		} else if (obj instanceof Date) {
			DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
			value = "'" + formatter.format(new Date()) + "'";
		} else {
			if (obj != null) {
				value = obj.toString();
			} else {
				value = "";
			}

		}
		return value;
	}

	@Override
	public Object plugin(Object target) {
		if(target instanceof Executor)
			return Plugin.wrap(target, this);
		return target;
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
