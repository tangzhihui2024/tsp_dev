package com.icbcaxa.eata.jryzt.ai.pojo.dto.request;

public class IdcomparisonV6Request extends BaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 身份证号
	 */
	private String clientCardNo;
	/**
	 * 身份证照片
	 */
	private String photo;
	/**
	 * 获取身份证照片
	 * @return
	 */
	
	/**
	 * 新增活体选择参数（1、无活体、2、H2.0活体、3、xface活体）  
	 */
	private String bioChoose;
	public String getPhoto() {
		return photo;
	}
	/**
	 * 设置身份证照片
	 * @param photo
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	/**
	 * 获取真实姓名
	 * @return
	 */
	public String getRealName() {
		return realName;
	}
	/**
	 * 设置真实姓名
	 * @param realName
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/**
	 * 获取身份证号
	 * @return
	 */
	public String getClientCardNo() {
		return clientCardNo;
	}
	/**
	 * 设置身份证号
	 * @param clientCardNo
	 */
	public void setClientCardNo(String clientCardNo) {
		this.clientCardNo = clientCardNo;
	}
	/**
	 * 获取活体选择
	 * @return
	 */
	public String getBioChoose() {
		return bioChoose;
	}
	/**
	 * 设置活体选择
	 * @param bioChoose
	 */
	public void setBioChoose(String bioChoose) {
		this.bioChoose = bioChoose;
	}
}
