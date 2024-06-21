package com.icbcaxa.eata.jryzt.ai.pojo.dto.request;

import java.io.Serializable;

public class BaseRequest implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 请求唯一标识
	 */
	private String requestId;
	/**
	 * 设备ID
	 */
	private String deviceId;
	/**
	 * 渠道号
	 */
	private String channelId;
	/**
	 * sdk版本号
	 */
	private String sdkVersion;
	/**
	 * 设备机型
	 */
	private String deviceModel;
	/**
	 * 客户端平台类型与版本
	 */
	private String deviceOSAndVersion;
	/**
	 * 客户端编号，如JK_OPEN_AI_DEMO
	 */
	private String clientId;
	/**
	 * 用户所在渠道的唯一编号
	 */
	private String personId;
	/**
	 * 标示图片类别(1.手机自拍照片 2.身份证照片 3.护照照片 默认为1。)
	 */
	private String imgType;
	/**
	 * 标示图片格式
	 */
	private String imgFormat;
	/**
	 * Base64图片编码
	 */
	private String imgData;
	
	/**
	 * 二级渠道号
	 */
	private String secondChannel;

	/**
	 * 二级渠道名
	 */
	private String secondChannelName;

	/**
	 * 备注
	 */
	private String description;
	
	/**
	 * 是否保存照片
	 */
	private String isSave;

	/**
	 * 获取设备ID
	 * 
	 * @return
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * 设置设备ID
	 * 
	 * @param deviceId
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * 获取渠道号
	 * 
	 * @return
	 */
	public String getChannelId() {
		return channelId;
	}

	/**
	 * 设置渠道号
	 * 
	 * @param channelId
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	/**
	 * 获取sdk版本号
	 * 
	 * @return
	 */
	public String getSdkVersion() {
		return sdkVersion;
	}

	/**
	 * 设置sdk版本号
	 * 
	 * @param sdkVersion
	 */
	public void setSdkVersion(String sdkVersion) {
		this.sdkVersion = sdkVersion;
	}

	/**
	 * 获取设备机型
	 * 
	 * @return
	 */
	public String getDeviceModel() {
		return deviceModel;
	}

	/**
	 * 设置设备机型
	 * 
	 * @param deviceModel
	 */
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	/**
	 * 获取客户端平台类型与版本
	 * 
	 * @return
	 */
	public String getDeviceOSAndVersion() {
		return deviceOSAndVersion;
	}

	/**
	 * 设置客户端平台类型与版本
	 * 
	 * @param deviceOSAndVersion
	 */
	public void setDeviceOSAndVersion(String deviceOSAndVersion) {
		this.deviceOSAndVersion = deviceOSAndVersion;
	}

	/**
	 * 获取客户端编号，如JK_OPEN_AI_DEMO
	 * 
	 * @return
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * 设置客户端编号，如JK_OPEN_AI_DEMO
	 * 
	 * @param clientId
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * 获取Base64图片编码
	 * 
	 * @return
	 */
	public String getImgData() {
		return imgData;
	}

	/**
	 * 设置Base64图片编码
	 * 
	 * @param imgData
	 */
	public void setImgData(String imgData) {
		this.imgData = imgData;
	}

	/**
	 * 获取标示图片类别
	 * 
	 * @return
	 */
	public String getImgType() {
		return imgType;
	}

	/**
	 * 设置标示图片类别
	 * 
	 * @param imgType
	 */
	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	/**
	 * 获取标示图片格式
	 * 
	 * @return
	 */
	public String getImgFormat() {
		return imgFormat;
	}

	/**
	 * 设置标示图片格式
	 * 
	 * @param imgFormat
	 */
	public void setImgFormat(String imgFormat) {
		this.imgFormat = imgFormat;
	}

	/**
	 * 获取请求唯一标识
	 * 
	 * @return
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * 设置请求唯一标识
	 * 
	 * @param requestId
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * 获取用户所在渠道的唯一编号
	 * 
	 * @return
	 */
	public String getPersonId() {
		return personId;
	}

	/**
	 * 设置用户所在渠道的唯一编号
	 * 
	 * @param personId
	 */
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	/**
	 * 获取二级渠道号
	 * @return
	 */
	public String getSecondChannel() {
		return secondChannel;
	}
	
	/**
	 * 设置二级渠道号
	 * @param secondChannel
	 */
	public void setSecondChannel(String secondChannel) {
		this.secondChannel = secondChannel;
	}
	
	/**
	 * 获取二级渠道名
	 * @return
	 */
	public String getSecondChannelName() {
		return secondChannelName;
	}
	
	/**
	 * 设置二级渠道名
	 * @param secondChannelName
	 */
	public void setSecondChannelName(String secondChannelName) {
		this.secondChannelName = secondChannelName;
	}
	
	/**
	 * 获取备注
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * 设置备注
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 获取是否保存图片参数
	 * @return
	 */
	public String getIsSave() {
		return isSave;
	}
	
	/**
	 * 设置是否保存图片参数
	 * @param isSave
	 */
	public void setIsSave(String isSave) {
		this.isSave = isSave;
	}
}
