package com.icbcaxa.eata.jryzt.ai.pojo.dto.result;

public class Rect {
	/**
	 * 人脸左上角横坐标
	 */
	private String x;
	/**
	 * 人脸左上角纵坐标
	 */
	private String y;
	/**
	 * 宽度
	 */
	private String width;
	/**
	 * 高度
	 */
	private String height;

	/**
	 * 获取人脸左上角横坐标
	 * @return the x
	 */
	public String getX() {
		return x;
	}

	/**
	 * 设置人脸左上角横坐标
	 * @param x the x to set
	 */
	public void setX(String x) {
		this.x = x;
	}


	/**
	 * 获取人脸左上角纵坐标
	 * @return the y
	 */
	public String getY() {
		return y;
	}

	/**
	 * 设置人脸左上角纵坐标
	 * @param y
	 *            the y to set
	 */
	public void setY(String y) {
		this.y = y;
	}

	/**
	 * 获取宽度
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * 设置宽度
	 * @param width
	 *            the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * 获取高度
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * 设置高度
	 * @param height
	 *            the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}
}
