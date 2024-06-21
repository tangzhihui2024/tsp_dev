
package com.icbcaxa.eata.jryzt.ai.enums;

public enum RespCodePreEnum {
	
	LM_CODE("1000","用户"),
	USER_CODE("1100","用户"),
	CASHIER_CODE("1200", "收银台"),
	TRADE_CODE("1300", "商城"),
	FACE_DETECT("1400", "人脸识别"),
	RECHARGE_CODE("1500", "充值服务"),
	SMS_CODE("1600", "短信服务"),
	FINANCE_CODE("1700", "短信服务"),
	INSURANCE_CODE("1800", "保险壹账通服务"),
	PAY_CODE("1900", "支付服务"),
	COUPON_CODE("2000", "优惠卷服务");

	private String code;
	
	private String desc;
	
	private RespCodePreEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
