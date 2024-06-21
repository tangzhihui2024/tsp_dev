package com.icbcaxa.eata.jryzt.ai.pojo.dto.request;

public class OcrRequest extends BaseRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 标示图片种类：1-银行卡, 2-身份证正面，3-身份证背面,4-行驶证，5-名片
	 */
	private String imgIndex;
	/**
	 * 获取ocr标示图片种类
	 * @return
	 */
	public String getImgIndex() {
		return imgIndex;
	}
	/**
	 * 设置ocr标示图片种类
	 * @param imgIndex
	 */
	public void setImgIndex(String imgIndex) {
		this.imgIndex = imgIndex;
	}
}
