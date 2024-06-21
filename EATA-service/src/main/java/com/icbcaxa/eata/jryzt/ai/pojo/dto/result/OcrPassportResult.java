package com.icbcaxa.eata.jryzt.ai.pojo.dto.result;

public class OcrPassportResult extends BaseResult {
	/**
	 * 护照类型
	 */
	private String type;
	/**
	 * 国家码
	 */
	private String countryCode;
	/**
	 * 护照号
	 */
	private String passportNo;
	/**
	 * 中文姓名
	 */
	private String name;
	/**
	 * 英文名
	 */
	private String enName;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 出生地点
	 */
	private String birthplace;
	/**
	 * 出生地点拼音
	 */
	private String birthplacePinyin;
	/**
	 * 出生日期
	 */
	private String birthday;
	/**
	 * 签发地点
	 */
	private String placeOfIssue;
	/**
	 * 签发地点拼音
	 */
	private String issueplacePinyin;
	/**
	 * 签发日期
	 */
	private String issueDate;
	/**
	 * 有效期至
	 */
	private String expiryDates;
	/**
	 * 第一行mrz机读码
	 */
	private String mrz1;
	/**
	 * 第二行mrz机读码
	 */
	private String mrz2;

	/**
	 * 获取护照类型
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置护照类型
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取国家码
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * 设置国家码
	 * @param countryCode
	 *            the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * 获取护照号
	 * @return the passportNo
	 */
	public String getPassportNo() {
		return passportNo;
	}

	/**
	 * 设置护照号
	 * @param passportNo
	 *            the passportNo to set
	 */
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	/**
	 * 获取中文姓名
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置中文姓名
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取英文名
	 * @return the enName
	 */
	public String getEnName() {
		return enName;
	}

	/**
	 * 设置英文名
	 * @param enName
	 *            the enName to set
	 */
	public void setEnName(String enName) {
		this.enName = enName;
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
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 获取出生地点
	 * @return the birthplace
	 */
	public String getBirthplace() {
		return birthplace;
	}

	/**
	 * 设置出生地点
	 * @param birthplace
	 *            the birthplace to set
	 */
	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	/**
	 * 获取出生地点拼音
	 * @return the birthplacePinyin
	 */
	public String getBirthplacePinyin() {
		return birthplacePinyin;
	}

	/**
	 * 出生出生地点拼音
	 * @param birthplacePinyin
	 *            the birthplacePinyin to set
	 */
	public void setBirthplacePinyin(String birthplacePinyin) {
		this.birthplacePinyin = birthplacePinyin;
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
	 * @param birthday
	 *            the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * 获取签发地点
	 * @return the placeOfIssue
	 */
	public String getPlaceOfIssue() {
		return placeOfIssue;
	}

	/**
	 * 设置签发地点
	 * @param placeOfIssue
	 *            the placeOfIssue to set
	 */
	public void setPlaceOfIssue(String placeOfIssue) {
		this.placeOfIssue = placeOfIssue;
	}

	/**
	 * 获取签发地点拼音
	 * @return the issueplacePinyin
	 */
	public String getIssueplacePinyin() {
		return issueplacePinyin;
	}

	/**
	 * 设置签发地点拼音
	 * @param issueplacePinyin
	 *            the issueplacePinyin to set
	 */
	public void setIssueplacePinyin(String issueplacePinyin) {
		this.issueplacePinyin = issueplacePinyin;
	}

	/**
	 * 获取签发日期
	 * @return the issueDate
	 */
	public String getIssueDate() {
		return issueDate;
	}

	/**
	 * 设置签发日期
	 * @param issueDate
	 *            the issueDate to set
	 */
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * 获取有效期至
	 * @return the expiryDates
	 */
	public String getExpiryDates() {
		return expiryDates;
	}

	/**
	 * 设置有效期至
	 * @param expiryDates
	 *            the expiryDates to set
	 */
	public void setExpiryDates(String expiryDates) {
		this.expiryDates = expiryDates;
	}

	/**
	 * 获取第一行mrz机读码
	 * @return the mrz1
	 */
	public String getMrz1() {
		return mrz1;
	}

	/**
	 * 设置第一行mrz机读码
	 * @param mrz1
	 *            the mrz1 to set
	 */
	public void setMrz1(String mrz1) {
		this.mrz1 = mrz1;
	}

	/**
	 * 获取第二行mrz机读码
	 * @return the mrz2
	 */
	public String getMrz2() {
		return mrz2;
	}

	/**
	 * 设置第二行mrz机读码
	 * @param mrz2
	 *            the mrz2 to set
	 */
	public void setMrz2(String mrz2) {
		this.mrz2 = mrz2;
	}
}
