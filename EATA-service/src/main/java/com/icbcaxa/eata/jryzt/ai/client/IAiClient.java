package com.icbcaxa.eata.jryzt.ai.client;


import com.icbcaxa.eata.jryzt.ai.pojo.dto.request.*;
import com.icbcaxa.eata.jryzt.ai.pojo.dto.result.*;

public interface IAiClient {
	/**
	 * 人脸比对接口
	 * 
	 * @param request
	 * @return
	 */
	FaceCompareResult faceCompare(FaceCompareRequest request);

	/**
	 * 活体识别接口
	 * 
	 * @param request
	 * @return
	 */
	BioDetectResult BioDetect(BioDetectRequest request);

	/**
	 * 去网纹接口
	 * 
	 * @param
	 * @return
	 */
	DescreenResult descreen(BioDetectRequest request);

	/**
	 * 人脸检测接口
	 * 
	 * @param request
	 * @return
	 */
	FaceDetectResult faceDetect(FaceDetectRequest request);
	/**
	 * 人证比对接口
	 * @param request
	 * @return
	 */
	FaceCompareResult idComparison(IdcomparisonRequest request);
	/**
	 * 带红外照片人脸比对接口
	 * @param request
	 * @return
	 */
	FaceCompareResult infraredFaceCompare(InfraredFaceCompareRequest request);
	/**
	 * 新人证比对接口
	 * @param request
	 * @return
	 */
	FaceCompareResult newIdComparison(NewIdcomparisonRequest request);
	/**
	 * ocr银行卡识别接口
	 * @param request
	 * @return
	 */
	OcrBankCardResult ocrBankCard(OcrRequest request);
	/**
	 * ocr名片识别接口
	 * @param request
	 * @return
	 */
	OcrCardResult ocrCard(OcrRequest request);
	/**
	 * ocr驾驶证识别接口
	 * @param request
	 * @return
	 */
	OcrDriverLicenseResult ocrDriverLicense(OcrRequest request);
	/**
	 * ocr港澳通行证识别接口
	 * @param request
	 * @return
	 */
	OcrHKMLPasserResult ocrHKMLPasser(OcrRequest request);
	/**
	 * ocr行驶证识别接口
	 * @param request
	 * @return
	 */
	OcrDrivingLicenseResult ocrDrivingLicense(OcrRequest request);
	/**
	 * ocr身份证背面识别接口
	 * @param request
	 * @return
	 */
	OcrIDCardBackResult ocrIDCardBack(OcrRequest request);
	/**
	 * ocr身份证正面识别接口
	 * @param request
	 * @return
	 */
	OcrIDCardFontResult ocrIDCardFront(OcrRequest request);
	/**
	 * ocr护照识别接口
	 * @param request
	 * @return
	 */
	OcrPassportResult ocrPassport(OcrRequest request);
	/**
	 * ocr台湾通行证识别接口
	 * @param request
	 * @return
	 */
	OcrTWPassResult ocrTWPass(OcrRequest request);
	/**
	 * 两照比对接口
	 * @param request
	 * @return
	 */
	FaceCompareResult twoFaceCompare(FaceCompareRequest request);
	/**
	 * xface活体检测接口
	 * @param request
	 * @return
	 */
	BioDetectResult xFaceBioDetect(BioDetectRequest request);
	/**
	 * 人证比对V6接口
	 * @param request
	 * @return
	 */
	FaceCompareResult idComparisonV6(IdcomparisonV6Request request);
	/**
	 * 两照比对V3接口
	 * @param request
	 * @return
	 */
	FaceCompareResult twoFaceCompareV3(TwoFaceCompareV3Request request);
	/**
	 * 身份验证
	 * @param request
	 * @return
	 */
	FaceCompareResult idVerificationB(IdcomparisonV6Request request);
}
