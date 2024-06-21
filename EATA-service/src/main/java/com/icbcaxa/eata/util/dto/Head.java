package com.icbcaxa.eata.util.dto;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

public class Head implements Serializable {
	
	private static final long serialVersionUID = -9221279795899978776L;

	@NotEmpty(message = "参数错误：userId")
	private String userId;
	private String now;
	private String userToken;
	private String token;
	private String sourceSys;
	private String userType;
	private String agentName;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNow() {
		return now;
	}

	public void setNow(String now) {
		this.now = now;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSourceSys() {
		return sourceSys;
	}

	public void setSourceSys(String sourceSys) {
		this.sourceSys = sourceSys;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
}
