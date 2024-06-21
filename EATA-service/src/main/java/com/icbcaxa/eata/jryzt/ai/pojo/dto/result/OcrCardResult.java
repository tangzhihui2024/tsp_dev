package com.icbcaxa.eata.jryzt.ai.pojo.dto.result;

public class OcrCardResult extends BaseResult {
	/**
	 * 公司名称
	 */
	private String company;
	/**
	 * 公司地址
	 */
	private String address;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 座机号码
	 */
	private String telephone;
	/**
	 * 人名
	 */
	private String name;
	/**
	 * 部门职位
	 */
	private String departmentTitle;

	/**
	 * 获取公司名称
	 * 
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * 设置公司名称
	 * 
	 * @param company
	 *            the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * 获取公司地址
	 * 
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置公司地址
	 * 
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取手机号码
	 * 
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置手机号码
	 * 
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取座机号码
	 * 
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * 设置座机号码
	 * 
	 * @param telephone
	 *            the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * 获取人名
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置人名
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *返回部门职位
	 * @return the departmentTitle
	 */
	public String getDepartmentTitle() {
		return departmentTitle;
	}

	/**
	 *设置部门职位
	 * @param departmentTitle the departmentTitle to set
	 */
	public void setDepartmentTitle(String departmentTitle) {
		this.departmentTitle = departmentTitle;
	}
}
