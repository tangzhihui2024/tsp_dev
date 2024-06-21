package com.icbcaxa.eata.jryzt.ai.pojo.dto.request;

public class InfraredFaceCompareRequest extends BaseRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * BASE64编码图片字符串
	 */
	private String imgData1;
	/**
	 * BASE64编码图片字符串
	 */
	private String imgData2;
		
	/**
	 * 图片1类别：1自拍照片  默认为1
	 */
	private String imgType1;
	/**
	 * 图片2类别：1自拍照片  默认为1
	 */
	private String imgType2;
		
	/**
	 * 图片1格式：jpg,jpeg...
	 */
	private String imgFormat1;
	/**
	 * 图片2格式：jpg,jpeg...
	 */
	private String imgFormat2;
	/**
	 * 红外图片类别：1自拍照片  默认为1
	 */
	private String infraredImgType;		
	/**
	 * 红外图片格式：jpg,jpeg...
	 */
	private String infraredImgFormat;		
	/**
	 * 红外BASE64编码图片字符串
	 */
	private String infraredImgData;
	/**
	 * 获取红外图片类别：1自拍照片  默认为1
	 * @return
	 */
	public String getInfraredImgType() {
		return infraredImgType;
	}
	/**
	 * 设置红外图片类别：1自拍照片  默认为1
	 * @param infraredImgType
	 */
	public void setInfraredImgType(String infraredImgType) {
		this.infraredImgType = infraredImgType;
	}
	/**
	 * 获取红外图片格式：jpg,jpeg...
	 * @return
	 */
	public String getInfraredImgFormat() {
		return infraredImgFormat;
	}
	/**
	 * 设置红外图片格式：jpg,jpeg...
	 * @param infraredImgFormat
	 */
	public void setInfraredImgFormat(String infraredImgFormat) {
		this.infraredImgFormat = infraredImgFormat;
	}
	/**
	 * 获取红外BASE64编码图片字符串
	 * @return
	 */
	public String getInfraredImgData() {
		return infraredImgData;
	}
	/**
	 * 设置红外BASE64编码图片字符串
	 * @param infraredImgData
	 */
	public void setInfraredImgData(String infraredImgData) {
		this.infraredImgData = infraredImgData;
	}
	/**
	 * 获取标示图片1的BASE64编码图片字符串
	 * @return
	 */
	public String getImgData1() {
		return imgData1;
	}
	/**
	 * 设置标示图片1的BASE64编码图片字符串
	 * @param imgData1
	 */
	public void setImgData1(String imgData1) {
		this.imgData1 = imgData1;
	}
	/**
	 * 获取标示图片2的BASE64编码图片字符串
	 * @return
	 */
	public String getImgData2() {
		return imgData2;
	}
	/**
	 * 设置标示图片2的BASE64编码图片字符串
	 * @param imgData2
	 */
	public void setImgData2(String imgData2) {
		this.imgData2 = imgData2;
	}
	/**
	 * 获取图片1类别：1自拍照片  默认为1
	 * @return
	 */
	public String getImgType1() {
		return imgType1;
	}
	/**
	 * 设置图片1类别：1自拍照片  默认为1
	 * @param imgType1
	 */
	public void setImgType1(String imgType1) {
		this.imgType1 = imgType1;
	}
	/**
	 * 获取图片2类别：1自拍照片  默认为1
	 * @return
	 */
	public String getImgType2() {
		return imgType2;
	}
	/**
	 * 设置图片2类别：1自拍照片  默认为1
	 * @param imgType2
	 */
	public void setImgType2(String imgType2) {
		this.imgType2 = imgType2;
	}
	/**
	 * 获取图片1格式：jpg,jpeg...
	 * @return
	 */
	public String getImgFormat1() {
		return imgFormat1;
	}
	/**
	 * 设置图片1格式：jpg,jpeg...
	 * @param imgFormat1
	 */
	public void setImgFormat1(String imgFormat1) {
		this.imgFormat1 = imgFormat1;
	}
	/**
	 * 获取图片2格式：jpg,jpeg...
	 * @return
	 */
	public String getImgFormat2() {
		return imgFormat2;
	}
	/**
	 * 设置图片2格式：jpg,jpeg...
	 * @param imgFormat2
	 */
	public void setImgFormat2(String imgFormat2) {
		this.imgFormat2 = imgFormat2;
	}
}
