package com.icbcaxa.eata.jryzt.ai.pojo.dto.result;

public class BaseResult {
	/**
	 * 返回码（000000为成功，其他为失败）
	 */
	private String responseCode;
	/**
	 * 返回信息
	 */
	private String responseMessage;
	/**
	 * 获取返回码
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}
	/**
	 * 设置返回码
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	/**
	 * 获取返回信息
	 * @return the responseMessage
	 */
	public String getResponseMessage() {
		return responseMessage;
	}
	/**
	 * 设置返回信息
	 * @param responseMessage the responseMessage to set
	 */
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
}
