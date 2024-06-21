package com.icbcaxa.eata.jryzt.ai.pojo.dto.request;

public class FaceDetectRequest extends BaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 是否返回多人脸数组
	 */
	private String faceSet;
	/**
	 * 获取是否返回多人脸数组
	 * @return
	 */
	public String isFaceSet() {
		return faceSet;
	}
	/**
	 * 设置是否返回多人脸数组
	 * @param faceSet
	 */
	public void setFaceSet(String faceSet) {
		this.faceSet = faceSet;
	}
}
