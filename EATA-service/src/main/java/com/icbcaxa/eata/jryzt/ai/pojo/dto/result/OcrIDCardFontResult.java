package com.icbcaxa.eata.jryzt.ai.pojo.dto.result;

public class OcrIDCardFontResult extends BaseResult {
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 民族
	 */
	private String nation;
	/**
	 * 出生日期
	 */
	private String birthday;
	/**
	 * 住址
	 */
	private String address;
	/**
	 * 身份证号码
	 */
	private String cardNo;
	/**
	 * 获取姓名
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置姓名
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取性别
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * 设置性别
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 获取民族
	 * @return the nation
	 */
	public String getNation() {
		return nation;
	}
	/**
	 * 设置民族
	 * @param nation the nation to set
	 */
	public void setNation(String nation) {
		this.nation = nation;
	}
	/**
	 * 获取出生日期
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * 设置出生日期
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * 获取住址
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置住址
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取身份证号码
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}
	/**
	 * 设置身份证号码
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
}
