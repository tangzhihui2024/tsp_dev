package com.icbcaxa.eata.jryzt.ai.pojo.dto.result;

public class OcrDriverLicenseResult extends BaseResult {
	/**
	 * 出生日期
	 */
	private String birthday;
	/**
	 * 初次领证日期
	 */
	private String issuingDate;
	/**
	 * 驾照类型（例如：C）
	 */
	private String type;
	/**
	 * 证件号
	 */
	private String cardNo;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 有效年限
	 */
	private String validPeriod;
	/**
	 * 驾驶员名字
	 */
	private String name;
	/**
	 * 家庭住址
	 */
	private String address;
	/**
	 * 获取出生日期
	 * 
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * 设置出生日期
	 * 
	 * @param birthday
	 *            the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * 获取初次领证日期
	 * 
	 * @return the issuingDate
	 */
	public String getIssuingDate() {
		return issuingDate;
	}

	/**
	 * 设置初次领证日期
	 * 
	 * @param issuingDate
	 *            the issuingDate to set
	 */
	public void setIssuingDate(String issuingDate) {
		this.issuingDate = issuingDate;
	}

	/**
	 * 获取驾照类型（例如：C）
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置驾照类型（例如：C）
	 * 
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取证件号
	 * 
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * 设置证件号
	 * 
	 * @param cardNo
	 *            the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * 获取性别
	 * 
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * 设置性别
	 * 
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 获取有效年限
	 * 
	 * @return the validPeriod
	 */
	public String getValidPeriod() {
		return validPeriod;
	}

	/**
	 * 设置有效年限
	 * 
	 * @param validPeriod
	 *            the validPeriod to set
	 */
	public void setValidPeriod(String validPeriod) {
		this.validPeriod = validPeriod;
	}

	/**
	 * 获取驾驶员名字
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置驾驶员名字
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *返回家庭住址
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 *设置家庭住址
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
}
