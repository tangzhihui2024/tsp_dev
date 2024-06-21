package com.icbcaxa.eata.jryzt.ai.pojo.dto.result;

public class DescreenResult extends BaseResult {
	/**
	 * 去网纹后的照片（base64image）
	 */
	private String iamge;

	/**
	 *返回去网纹后的照片（base64image）
	 * @return the iamge
	 */
	public String getIamge() {
		return iamge;
	}

	/**
	 *设置去网纹后的照片（base64image）
	 * @param iamge the iamge to set
	 */
	public void setIamge(String iamge) {
		this.iamge = iamge;
	}
}
