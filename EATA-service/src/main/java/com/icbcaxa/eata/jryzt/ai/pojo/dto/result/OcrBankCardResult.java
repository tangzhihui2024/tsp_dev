package com.icbcaxa.eata.jryzt.ai.pojo.dto.result;

public class OcrBankCardResult extends BaseResult {
	/**
	 * 银行卡名（例如：借记卡(银联卡)）
	 */
	private String cardName;
	/**
	 * 所属银行（例如：农业银行）
	 */
	private String bankName;
	/**
	 * 银行卡类型（例如：借记卡）
	 */
	private String type;
	/**
	 * 银行卡号（例如：9559980210010631815）
	 */
	private String cardNo;
	/**
	 * 银行编号（例如：01030000）
	 */
	private String bankNo;
	/**
	 * 卡片类型（例如：借记卡）
	 */
	private String cardType;

	/**
	 * 获取银行卡名
	 * 
	 * @return the cardName
	 */
	public String getCardName() {
		return cardName;
	}

	/**
	 * 设置银行卡名
	 * 
	 * @param cardName
	 *            the cardName to set
	 */
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	/**
	 * 获取卡片所属银行
	 * 
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * 设置卡片所属银行
	 * 
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * 获取银行卡类型
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置银行卡类型
	 * 
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取银行卡号
	 * 
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * 设置银行卡号
	 * 
	 * @param cardNo
	 *            the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * 获取银行编号
	 * 
	 * @return the bankNo
	 */
	public String getBankNo() {
		return bankNo;
	}

	/**
	 * 设置银行编号
	 * 
	 * @param bankNo
	 *            the bankNo to set
	 */
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	/**
	 * 获取卡片类型
	 * 
	 * @return the cardType
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * 设置卡片类型
	 * 
	 * @param cardType
	 *            the cardType to set
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
}
