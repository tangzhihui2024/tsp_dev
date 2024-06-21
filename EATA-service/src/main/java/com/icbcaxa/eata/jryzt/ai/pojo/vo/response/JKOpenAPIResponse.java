/**
 * 
 */
package com.icbcaxa.eata.jryzt.ai.pojo.vo.response;

import com.alibaba.fastjson.JSONObject;
import com.icbcaxa.eata.jryzt.ai.enums.BaseResponseEnum;
import com.icbcaxa.eata.jryzt.ai.enums.RespCodePreEnum;
import com.icbcaxa.eata.jryzt.ai.pojo.vo.BaseVo;
import com.icbcaxa.eata.jryzt.ai.utils.crypto.AESCBCUtils;


public class JKOpenAPIResponse<T> extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 响应码
	 */
	private String responseCode;
	
	/**
	 * 响应码说明
	 */
	private String responseMessage;
	
	/**
	 * 响应业务数据
	 */
	private T responseData;
	
	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public T getResponseData() {
		return responseData;
	}

	public void setResponseData(T responseData) {
		this.responseData = responseData;
	}
	
	@Override
	public String toString() {
		return "[responseCode=" + responseCode + ", responseMsg="
				+ responseMessage + ", responseData=" + responseData + "]";
	}

	/** 基本构造函数 **/

    public static <T> JKOpenAPIResponse<T> ok() {
        JKOpenAPIResponse<T> br = build();
        br.setResponseCode(BaseResponseEnum.SUCCESS.getCode());
        br.setResponseMessage(BaseResponseEnum.SUCCESS.getMsg());
        return br;
    }

    public static <T> JKOpenAPIResponse<T> ok(T data) {
        JKOpenAPIResponse<T> br = build();
        br.setResponseCode(BaseResponseEnum.SUCCESS.getCode());
        br.setResponseMessage(BaseResponseEnum.SUCCESS.getMsg());
        br.setResponseData(data);
        return br;
    }
    
    public static JKOpenAPIResponse<String> ok(Object data, String aesKey) {
    	JKOpenAPIResponse<String> br = build();
        br.setResponseCode(BaseResponseEnum.SUCCESS.getCode());
        br.setResponseMessage(BaseResponseEnum.SUCCESS.getMsg());
        if (data == null || "".equals(data.toString())) {
        	br.setResponseData("{}");
        } else {
        	br.setResponseData(AESCBCUtils.encrypt(JSONObject.toJSONString(data), aesKey));
        }
        
        return br;
    }
    
    public static JKOpenAPIResponse<String> buildFailResponse(String errorCode, String errMsg, Object data, String aesKey) {
    	JKOpenAPIResponse<String> br = build();
        br.setResponseCode(errorCode);
        br.setResponseMessage(errMsg);
        if (data == null || "".equals(data.toString())) {
        	br.setResponseData("{}");
        } else {
        	br.setResponseData(AESCBCUtils.encrypt(JSONObject.toJSONString(data), aesKey));
        }
        
        return br;
    }

    public static <T> JKOpenAPIResponse<T> build() {
        return new JKOpenAPIResponse<T>();
    }

    public static <T> JKOpenAPIResponse<T> buildFailResponse(String errorCode, String errMsg) {
        JKOpenAPIResponse<T> br = build();
        br.setResponseCode(errorCode);
        br.setResponseMessage(errMsg);
        return br;
    }

    public static <T> JKOpenAPIResponse<T> buildFailResponse(BaseResponseEnum responseEnum, T data) {
        JKOpenAPIResponse<T> br = build();
        br.setResponseCode(responseEnum.getCode());
        br.setResponseMessage(responseEnum.getMsg());
        return br;
    }

	public static <T> JKOpenAPIResponse<T> failRequest() {
		JKOpenAPIResponse<T> br = build();
        br.setResponseCode(RespCodePreEnum.LM_CODE.getCode()+ BaseResponseEnum.ILLEGAL_REQUEST.getCode());
        br.setResponseMessage(BaseResponseEnum.ILLEGAL_REQUEST.getMsg());
        return br;
	}
	
	public static <T> JKOpenAPIResponse<T> fail(){
		JKOpenAPIResponse<T> br = build();
        br.setResponseCode(RespCodePreEnum.LM_CODE.getCode()+ BaseResponseEnum.FAILURE.getCode());
        br.setResponseMessage(BaseResponseEnum.FAILURE.getMsg());
        return br;
	}
	
	public static <T> JKOpenAPIResponse<T> fail(T data){
		JKOpenAPIResponse<T> br = build();
        br.setResponseCode(RespCodePreEnum.LM_CODE.getCode()+ BaseResponseEnum.FAILURE.getCode());
        br.setResponseMessage(BaseResponseEnum.FAILURE.getMsg());
        br.setResponseData(data);
        return br;
	}
}
