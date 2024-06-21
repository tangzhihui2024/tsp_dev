package com.icbcaxa.eata.jryzt.ai.pojo.dto.request;

public class IdcomparisonRequest extends BaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 客户姓名
	 */
	private String clientName;
	/**
	 * 客户身份证号
	 */
	private String clientCardNo;
	/**
	 * 获取客户姓名
	 * @return
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * 设置客户姓名
	 * @param clientName
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	/**
	 * 获取客户身份证号
	 * @return
	 */
	public String getClientCardNo() {
		return clientCardNo;
	}
	/**
	 * 设置客户身份证号
	 * @param clientCardNo
	 */
	public void setClientCardNo(String clientCardNo) {
		this.clientCardNo = clientCardNo;
	}

}
