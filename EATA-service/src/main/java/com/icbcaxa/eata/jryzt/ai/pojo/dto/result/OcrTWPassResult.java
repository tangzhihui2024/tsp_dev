package com.icbcaxa.eata.jryzt.ai.pojo.dto.result;

public class OcrTWPassResult extends BaseResult {
	/**
	 * 卡片编号
	 */
	private String cardNo;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 英文名
	 */
	private String enName;
	/**
	 * 出生日期
	 */
	private String birthday;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 有效期限
	 */
	private String validPeriod;
	/**
	 * 签发地点
	 */
	private String placeOfIssue;
	/**
	 * mrz机读码
	 */
	private String mrz;

	/**
	 * 获取卡片编号
	 * 
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * 设置卡片编号
	 * 
	 * @param cardNo
	 *            the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * 获取姓名
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置姓名
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取英文名
	 * 
	 * @return the enName
	 */
	public String getEnName() {
		return enName;
	}

	/**
	 * 设置英文名
	 * 
	 * @param enName
	 *            the enName to set
	 */
	public void setEnName(String enName) {
		this.enName = enName;
	}

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
	 * 获取有效期限
	 * 
	 * @return the validPeriod
	 */
	public String getValidPeriod() {
		return validPeriod;
	}

	/**
	 * 设置有效期限
	 * 
	 * @param validPeriod
	 *            the validPeriod to set
	 */
	public void setValidPeriod(String validPeriod) {
		this.validPeriod = validPeriod;
	}

	/**
	 * 获取签发地点
	 * 
	 * @return the placeOfIssue
	 */
	public String getPlaceOfIssue() {
		return placeOfIssue;
	}

	/**
	 * 设置签发地点
	 * 
	 * @param placeOfIssue
	 *            the placeOfIssue to set
	 */
	public void setPlaceOfIssue(String placeOfIssue) {
		this.placeOfIssue = placeOfIssue;
	}

	/**
	 * 获取mrz机读码
	 * 
	 * @return the mrz
	 */
	public String getMrz() {
		return mrz;
	}

	/**
	 * 设置mrz机读码
	 * 
	 * @param mrz
	 *            the mrz to set
	 */
	public void setMrz(String mrz) {
		this.mrz = mrz;
	}
}
