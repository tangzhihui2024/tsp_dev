package com.icbcaxa.eata.jryzt.ai.pojo.dto.result;

public class OcrDrivingLicenseResult extends BaseResult {
	/**
	 * 车牌号码
	 */
	private String plateNum;
	/**
	 * 车辆类型
	 */
	private String vehicleType;
	/**
	 * 所有人
	 */
	private String owner;
	/**
	 * 住址
	 */
	private String address;
	/**
	 * 使用性质
	 */
	private String userCharacter;
	/**
	 * 品牌型号
	 */
	private String model;
	/**
	 * 车辆识别代号
	 */
	private String vin;
	/**
	 * 发动机号码
	 */
	private String engineNo;
	/**
	 * 注册日期
	 */
	private String registerDate;
	/**
	 * 发证日期
	 */
	private String issueDate;
	/**
	 * 获取车牌号码
	 * @return the plateNum
	 */
	public String getPlateNum() {
		return plateNum;
	}
	/**
	 * 设置车牌号码
	 * @param plateNum the plateNum to set
	 */
	public void setPlateNum(String plateNum) {
		this.plateNum = plateNum;
	}
	/**
	 * 获取车辆类型
	 * @return the vehicleType
	 */
	public String getVehicleType() {
		return vehicleType;
	}
	/**
	 * 设置车辆类型
	 * @param vehicleType the vehicleType to set
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	/**
	 * 获取所有人
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * 设置所有人
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	/**
	 * 获取住址
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置住址
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取使用性质
	 * @return the userCharacter
	 */
	public String getUserCharacter() {
		return userCharacter;
	}
	/**
	 * 设置使用性质
	 * @param userCharacter the userCharacter to set
	 */
	public void setUserCharacter(String userCharacter) {
		this.userCharacter = userCharacter;
	}
	/**
	 * 获取品牌型号
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * 设置品牌型号
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * 获取车辆识别代号
	 * @return the vin
	 */
	public String getVin() {
		return vin;
	}
	/**
	 * 设置车辆识别代号
	 * @param vin the vin to set
	 */
	public void setVin(String vin) {
		this.vin = vin;
	}
	/**
	 * 获取发动机号码
	 * @return the engineNo
	 */
	public String getEngineNo() {
		return engineNo;
	}
	/**
	 * 设置发动机号码
	 * @param engineNo the engineNo to set
	 */
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	/**
	 * 获取注册日期
	 * @return the registerDate
	 */
	public String getRegisterDate() {
		return registerDate;
	}
	/**
	 * 设置注册日期
	 * @param registerDate the registerDate to set
	 */
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	/**
	 * 获取发证日期
	 * @return the issueDate
	 */
	public String getIssueDate() {
		return issueDate;
	}
	/**
	 * 设置发证日期
	 * @param issueDate the issueDate to set
	 */
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
}
