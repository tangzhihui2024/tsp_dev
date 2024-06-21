package com.icbcaxa.eata.domain.xml;



public class BlackHead {
	
	private String serviceID;
	
	private String version;
	
	private String uuid;
	
	private String from;
	
	private String transactionTime;
	
	private BlackCtrl ctrl;

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	public BlackCtrl getCtrl() {
		return ctrl;
	}

	public void setCtrl(BlackCtrl ctrl) {
		this.ctrl = ctrl;
	}

	
	
}
