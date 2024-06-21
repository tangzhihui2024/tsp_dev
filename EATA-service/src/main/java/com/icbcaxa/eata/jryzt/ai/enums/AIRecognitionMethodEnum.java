/**
 * 
 */
package com.icbcaxa.eata.jryzt.ai.enums;
/**
 * 
 */

public enum AIRecognitionMethodEnum {

	/*=================================保险壹账通=================================*/
	
	/**
	 * 人脸比对
	 */	
	FACE_COMPARE("身份验证","faceCompare"),
	/**
	 * 人脸检测
	 */	
	FACE_DETECT("人脸检测","faceDetect"),
	/**
	 * 活体识别
	 */
	BIO_DETECT("活体识别","bioDetect"),
	/**
	 * 身份证OCR识别
	 */
	OCR("身份证OCR识别","OCR"),
	
	/**
	 * 身份验证--》联网核查
	 */
	IDVERIFICATION("身份验证","idVerification"),
	/**
	 * 身份验证--》联网核查核实身份信息
	 */
	IDVERIFICATION_B("身份验证","idVerificationB"),
	/**
	 * 人证比对
	 */
	IDCOMPARISON("人证对比","idComparison"),
	/**
	 * XFACE人证比对
	 */
	XFACEIDCOMPARISON("人证对比","xFaceIdComparison"),
	/**
	 * 带红外图片的人脸对比
	 */
	INFRARED_FACE_COMPARE("带红外图片的人脸对比","infraredFaceCompare"),
	
	/**
	 * XFACE的活体识别可以使用于H5，算法优于以前的活体识别
	 */
	XFACE_BIO_DETECT("XFACE的活体识别","xFaceBioDetect"),
    /**
     * 去网纹接口
     */
	DESCREEN("去网纹","descreen"),
	/**
	 * 两照比对
	 */
	TWO_FACE_COMPARE("两照比对","twoFaceCompare"),
	/**
	 * XFACE两照比对
	 */
	XFACE_TWO_FACE_COMPARE("XFACE两照比对","xFaceTwoFaceCompare"),
	/**
	 * 人脸采集
	 */
	FACE_COLLECTION("人脸采集","faceCollection"),
	/**
	 * XFACE人脸采集
	 */
	XFACE_COLLECTION("XFACE人脸采集","xFaceCollection"),
	/**
	 * 人证核身新
	 */
	NEW_IDCOMPARISON("人证核身","newIdComparison"),
	/**
	 * 人证核身新(xface 活体识别)
	 */
	NEW_IDCOMPARISON_XFACE("XFACE的人证核身","newIdComparisonXface"),
	/**
	 * 人证V3升级版V6
	 */
	IDCOMPARISON_V6("人证核身V6", "idComparisonV6"),
	/**
	 * 两照比对V3
	 */
	TWO_FACE_COMPARE_V3("两照比对V3","twoFaceCompareV3");
	
	
	private String methodDesc;//方法描述
	private String methodName;//方法名称
	

	
	private AIRecognitionMethodEnum(String methodDesc, String methodName) {
		this.methodDesc = methodDesc;
		this.methodName = methodName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodDesc() {
		return methodDesc;
	}

	public void setMethodDesc(String methodDesc) {
		this.methodDesc = methodDesc;
	}
	
}