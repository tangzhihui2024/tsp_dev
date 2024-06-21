package com.icbcaxa.eata.jryzt.ai.enums;


public class BaseResponseEnum {

    final private String                       code;
    final private String                       msg;

    //final private static transient Set<String> codes = Sets.newHashSet();

    public BaseResponseEnum(String code, String msg) {

        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String formatMsg(Object... objects) {
        return String.format(msg, objects);
    }

    public static final BaseResponseEnum SUCCESS                 = new BaseResponseEnum("000000",
                                                                     "success");
    
    public static final BaseResponseEnum FAILURE                 = new BaseResponseEnum("999999",
                                                                     "系统繁忙，请稍候再试");
    
    public static final BaseResponseEnum BUS_CUSTOMER_ERROR 	= new BaseResponseEnum("999998",
    																 "业务处理异常");
    
    public static final BaseResponseEnum ILLEGAL_PARAMETER       = new BaseResponseEnum("700001",
                                                                     "illegal parameters");
    
    public static final BaseResponseEnum NOT_TOALOGIN            = new BaseResponseEnum("700002",
                                                                     "not login as toa user");
    
    public static final BaseResponseEnum ILLEGAL_REQUEST         = new BaseResponseEnum("700003",
                                                                     "illegal request");
    
    public static final BaseResponseEnum VALIDATE_SIGN_FAILED    = new BaseResponseEnum("2",
                                                                     "validate sign failed");
    // 非法参数
    public static final BaseResponseEnum ILLEGAL_ARG             = new BaseResponseEnum("910001",
                                                                     "非法参数 [%s]");
    
    public static final BaseResponseEnum ILLEGAL_NUMBER          = new BaseResponseEnum("910001",
    		                                                         "非法数字 [%s]");

    public static final BaseResponseEnum ILLEGAL_DATA            = new BaseResponseEnum("910002",
                                                                     "非法数据");

    public static final BaseResponseEnum ILLEGAL_DATA_WITH_FIELD = new BaseResponseEnum("910002",
                                                                     "非法数据[%s]");

    public static final BaseResponseEnum EMPTY_ARG               = new BaseResponseEnum("910004",
                                                                     "参数为空 [%s]");

    public static final BaseResponseEnum DULPICATE_DATA          = new BaseResponseEnum("910005",
                                                                     "数据重复");

    public static final BaseResponseEnum DATA_NOT_FOUND          = new BaseResponseEnum("910006",
                                                                     "数据不存在");

    public static final BaseResponseEnum EXECUTE_ERROR = new BaseResponseEnum("900010", "程序运行异常 [%s]");
    
    public static final BaseResponseEnum POST_KIT_API_ERROR = new BaseResponseEnum("900011", "请求kitAPI接口异常 [%s]");
    
    public static final BaseResponseEnum SIGN_COUNT_ERROR = new BaseResponseEnum("900013", "签名验证失败");
    
    public static final BaseResponseEnum RSA_SECURITY_ERROR = new BaseResponseEnum("900014", "渠道[%s]未分配RSA私钥");
    
    public static final BaseResponseEnum CLIENT_CHANNEL_ERROR = new BaseResponseEnum("900015", "渠道[%s]未初始化相关的业务渠道");
    
    public static final BaseResponseEnum SIGN_SECURITY_ERROR = new BaseResponseEnum("900016", "渠道[%s]未分配SIGN密钥");
    
    public static final BaseResponseEnum RSA_SECURITY_COUNT_ERROR = new BaseResponseEnum("900017", "RSA解密失败");
    
    public static final BaseResponseEnum AES_SECURITY_COUNT_ERROR = new BaseResponseEnum("900018", "AES解密失败");
    
    public static final BaseResponseEnum SELL_CHANNEL_ERROR = new BaseResponseEnum("900019", "渠道[%s]对应的的业务渠道[%s]不存在");
    
    public static final BaseResponseEnum UM2_AUTH_ERROR = new BaseResponseEnum("900020", "UM2 Authorization fail");
    
    public static final BaseResponseEnum USER_REGISTER_ERROR = new BaseResponseEnum("900021", "用户注册失败");
    
    public static final BaseResponseEnum USER_GET_MAX_SIZE_ERROR = new BaseResponseEnum("900022", "用户数量超过最大值:[%s]");
    
    public static final BaseResponseEnum HTTP_CONN_TIME_OUT_ERROR = new BaseResponseEnum("000001", "连接超时");
}
