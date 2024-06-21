package com.icbcaxa.eata.jryzt.ai.pojo.dto.result;


public class FaceDetectResult extends BaseResult {
	/**
	 * 单个人脸属性集
	 */
	private Face face;

	/**
	 * 获取单个人脸属性集
	 * 
	 * @return the face
	 */
	public Face getFace() {
		return face;
	}

	/**
	 * 设置单个人脸属性集
	 * 
	 * @param face
	 *            the face to set
	 */
	public void setFace(Face face) {
		this.face = face;
	}
}
