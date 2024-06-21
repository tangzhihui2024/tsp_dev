package com.icbcaxa.eata.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class Response<T> implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(Response.class);

	private static final long serialVersionUID = 1L;

	public static final String SUCCESS = "00";
	public static final String SUCCESS_MESSAGE = "操作成功！";
	public static final String ERROR = "99";
	public static final String ERROR_MESSAGE = "服务异常！";
	private String code = "00";
	private String message;
	private Boolean isTrue;
	private Double sim;
	private Double defaultSim;
	private transient T data;
	private T encrpyData;

	public String loginSource;//登录来源
	public String terminalType;//终端类型
	public String systemInfo;//系统信息
	public String loginSystemUserId;//登录系统用户唯一标示符
	public String isActive ;//是否有效
	public String validateTime ;//有效时间
	public String userToken;//令牌码
	public String ipAddr;//客户端IP地址
	public String browserVersion;//浏览器版本号

	public String name;

	public Response(String resultCode, String resultMsg) {
		super();
		this.setCode(resultCode);
		this.setMessage(resultMsg);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Response() {

	}


	public Response<Object> resultData(String res, Response<Object> response) {
		Response<Object> bean = new Response<Object>(SUCCESS, SUCCESS_MESSAGE);
		try {
			JSONObject resObj = JSON.parseObject(res);
			if (resObj != null) {
				bean.setCode(resObj.getString("code"));
				bean.setMessage(StringUtils.isNotBlank(resObj.getString("message")) ? resObj.getString("message") : "");
				bean.setData(resObj.getJSONObject("responseBody"));
				return bean;
			}
			return response;
		} catch (Exception e) {
			logger.error("", e);
			bean.setCode(ERROR);
			bean.setMessage(ERROR_MESSAGE);
			bean.setData(e.getLocalizedMessage());
			return bean;
		}
		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getIsTrue() {
		return isTrue;
	}

	public void setIsTrue(Boolean isTrue) {
		this.isTrue = isTrue;
	}

	public Double getSim() {
		return sim;
	}

	public void setSim(Double sim) {
		this.sim = sim;
	}

	public Double getDefaultSim() {
		return defaultSim;
	}

	public void setDefaultSim(Double defaultSim) {
		this.defaultSim = defaultSim;
	}

	public String getIsActive() {return isActive;}

	public void setIsActive(String isActive) {this.isActive = isActive;}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getValidateTime() {

		return validateTime;
	}
	public void setValidateTime(String validateTime) {
		this.validateTime = validateTime;
	}

	public String getLoginSource() {
		return loginSource;
	}

	public void setLoginSource(String loginSource) {
		this.loginSource = loginSource;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public String getSystemInfo() {
		return systemInfo;
	}

	public void setSystemInfo(String systemInfo) {
		this.systemInfo = systemInfo;
	}

	public String getLoginSystemUserId() {
		return loginSystemUserId;
	}

	public void setLoginSystemUserId(String loginSystemUserId) {
		this.loginSystemUserId = loginSystemUserId;
	}

	public String getIpAddr() {return ipAddr;}

	public void setIpAddr(String ipAddr) {this.ipAddr = ipAddr;}

	public String getBrowserVersion() {return browserVersion;}

	public void setBrowserVersion(String browserVersion) {this.browserVersion = browserVersion;}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getEncrpyData() {
		return encrpyData;
	}

	public void setEncrpyData(T encrpyData) {
		try {
			this.encrpyData = encrpyData==null?encrpyData:(T) AESUtil.aesEncrypt( JSON.toJSONString(encrpyData), AESUtil.getEncodeKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
//		this.encrpyData = encrpyData;
//		this.data = encrpyData;
	}

}
