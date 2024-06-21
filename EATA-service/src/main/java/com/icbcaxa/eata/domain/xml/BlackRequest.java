package com.icbcaxa.eata.domain.xml;


public class BlackRequest {

	private String customerName;
	
	private String customerSex;
	
	private String customerBirthday;
	
	private String certType;
	
	private String iDNo;

	

	public String getiDNo() {
		return iDNo;
	}

	public void setiDNo(String iDNo) {
		this.iDNo = iDNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerSex() {
		return customerSex;
	}

	public void setCustomerSex(String customerSex) {
		this.customerSex = customerSex;
	}

	public String getCustomerBirthday() {
		return customerBirthday;
	}

	public void setCustomerBirthday(String customerBirthday) {
		this.customerBirthday = customerBirthday;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}
	
}
