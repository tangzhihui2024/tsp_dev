package com.icbcaxa.eata.util.log;


import com.allianity.common.util.SimpleLogger;
import com.icbcaxa.eata.util.json.JsonUtils;
import com.icbcaxa.eata.util.log.domain.LogInfo;

import java.util.logging.Level;

/**
 * 只有当LEVEL常量的值大于或等于对应日志级别值的时候，才会打印日志。
 * 开发阶段，将LEVEL赋值为VERBOSE，上线阶段将LEVEL赋值为NOTHING
 */
public class LogUtil {



    public static void main(String[] args) throws Exception{
        LogInfo logInfo = new LogInfo();
        logInfo.setSystem("REL");
        logInfo.setCreateUser("user");
        logInfo.setServiceTypeAndGUID("WeChat 654sdfg654df654dfg6sd54fg");
        logInfo.setFunction("BOUND");
        logMsg(LogUtil.class,logInfo,Level.INFO,new Exception("异常信息"),new String[]{"11111","5654654"});
    }

    /**
     * *  日志输出规范：
     *【时间戳】+【业务系统/版本号】+【操作用户（可选）】+【业务类型 业务流水号】+【功能模块】+【功能（方法）】+【当前入参（可选）】+【错误信息（可选）】。
     * 当前入参：根据操作功能，不一定有请求入参，所以为可选输出。（输出内容例如：请求业务流水号、保单号等）
     * 错误信息：异常错误信息有的情况下输出，所以为可选
     * 以业务类型+业务流水号串联
     *
     * @param tClass   需要打印的日志类
     * @param logInfo   日志规范类
     * @param level     日志输出等级
     */
    public static <T> T logMsg(Class<T> tClass, LogInfo logInfo, Level level,Throwable throwable,Object[] parameter){
        SimpleLogger simpleLogger = SimpleLogger.getLogger(tClass);
        StringBuilder stringBuilder = new StringBuilder();
        if(logInfo != null){
            if(logInfo.getSystem() != null){
                stringBuilder.append(logInfo.getSystem());
            }
            if(logInfo.getCreateUser() != null){
                stringBuilder.append("  "+logInfo.getCreateUser());
            }
            if(logInfo.getServiceTypeAndGUID() != null){
                stringBuilder.append("  "+logInfo.getServiceTypeAndGUID());
            }
            if(logInfo.getModule() != null){
                stringBuilder.append("  "+logInfo.getModule());
            }
            if(logInfo.getFunction() != null){
                stringBuilder.append("  "+logInfo.getFunction());
            }
            if(parameter != null && parameter.length > 0){
                stringBuilder.append("  请求参数：[");
                int i = 1;
                for (Object obj : parameter) {
                    if(i == parameter.length){
                        stringBuilder.append(JsonUtils.objectToJson(obj));
                    }else{
                        stringBuilder.append(JsonUtils.objectToJson(obj)+", ");
                    }
                    i++;
                }
                stringBuilder.append("]");
            }
        }
        if(throwable != null){
            simpleLogger.error(stringBuilder.toString(),throwable);
        }else{
            simpleLogger.info(stringBuilder.toString());
        }
        return null;
    }



}
