package com.icbcaxa.eata.jryzt.ai.pojo.dto.request;


public class TwoFaceCompareV3Request extends BaseRequest {
	private static final long serialVersionUID = 1L;
	/**
	 * Base64图片编码
	 */
	private String imgData1;
	/**
	 * Base64图片编码
	 */
	private String imgData2;
	/**
	 * 标示图片1类别
	 */
	private String imgType1;
	/**
	 * 标示图片2类别
	 */
	private String imgType2;
	/**
	 * 图片1格式
	 */
	private String imgFormat1;
	/**
	 * 图片2格式
	 */
	private String imgFormat2;
	/**
	 * 新增活体选择参数（1、无活体、2、H2.0活体、3、xface活体）  
	 */
	private String bioChoose;
	/**
	 * 获取标示图片1的Base64图片编码
	 * @return
	 */
	public String getImgData1() {
		return imgData1;
	}
	/**
	 * 设置标示图片1的Base64图片编码
	 */
	public void setImgData1(String imgData1) {
		this.imgData1 = imgData1;
	}
	/**
	 * 获取标示图片2的Base64图片编码
	 * @return
	 */
	public String getImgData2() {
		return imgData2;
	}
	/**
	 * 设置标示图片2的Base64图片编码
	 * @param imgData2
	 */
	public void setImgData2(String imgData2) {
		this.imgData2 = imgData2;
	}
	/**
	 * 获取标示图片1类别
	 * @return
	 */
	public String getImgType1() {
		return imgType1;
	}
	/**
	 * 设置标示图片1类别
	 * @param imgType1
	 */
	public void setImgType1(String imgType1) {
		this.imgType1 = imgType1;
	}
	/**
	 * 获取标示图片2类别
	 * @return
	 */
	public String getImgType2() {
		return imgType2;
	}
	/**
	 * 设置标示图片2类别
	 * @param imgType2
	 */
	public void setImgType2(String imgType2) {
		this.imgType2 = imgType2;
	}
	/**
	 * 获取图片1格式
	 * @return
	 */
	public String getImgFormat1() {
		return imgFormat1;
	}
	/**
	 * 设置图片1格式
	 * @param imgFormat1
	 */
	public void setImgFormat1(String imgFormat1) {
		this.imgFormat1 = imgFormat1;
	}
	/**
	 * 获取图片2格式
	 * @return
	 */
	public String getImgFormat2() {
		return imgFormat2;
	}
	/**
	 * 设置图片2格式
	 * @param imgFormat2
	 */
	public void setImgFormat2(String imgFormat2) {
		this.imgFormat2 = imgFormat2;
	}
	/**
	 * 获取活体选择
	 * @return
	 */
	public String getBioChoose() {
		return bioChoose;
	}
	/**
	 * 设置活体选择
	 * @param bioChoose
	 */
	public void setBioChoose(String bioChoose) {
		this.bioChoose = bioChoose;
	}
}
