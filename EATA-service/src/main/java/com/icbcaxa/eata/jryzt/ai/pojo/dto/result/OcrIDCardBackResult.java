package com.icbcaxa.eata.jryzt.ai.pojo.dto.result;

public class OcrIDCardBackResult extends BaseResult {
	/**
	 * 签发机关
	 */
	private String issuingOrganization;
	/**
	 * 有效期限
	 */
	private String validPeriod;
	/**
	 * 签发日期
	 */
	private String issuingDate;
	/**
	 * 有效期
	 */
	private String expiryDates;

	/**
	 * 获取签发机关
	 * @return the issuingOrganization
	 */
	public String getIssuingOrganization() {
		return issuingOrganization;
	}

	/**
	 * 设置签发机关
	 * @param issuingOrganization
	 *            the issuingOrganization to set
	 */
	public void setIssuingOrganization(String issuingOrganization) {
		this.issuingOrganization = issuingOrganization;
	}

	/**
	 * 获取有效期限
	 * @return the validPeriod
	 */
	public String getValidPeriod() {
		return validPeriod;
	}

	/**
	 * 设置有效期限
	 * @param validPeriod
	 *            the validPeriod to set
	 */
	public void setValidPeriod(String validPeriod) {
		this.validPeriod = validPeriod;
	}

	/**
	 * 获取签发日期
	 * @return the issuingDate
	 */
	public String getIssuingDate() {
		return issuingDate;
	}

	/**
	 * 设置签发日期
	 * @param issuingDate
	 *            the issuingDate to set
	 */
	public void setIssuingDate(String issuingDate) {
		this.issuingDate = issuingDate;
	}

	/**
	 * 获取有效期
	 * @return the expiryDates
	 */
	public String getExpiryDates() {
		return expiryDates;
	}

	/**
	 * 设置有效期
	 * @param expiryDates
	 *            the expiryDates to set
	 */
	public void setExpiryDates(String expiryDates) {
		this.expiryDates = expiryDates;
	}
}
