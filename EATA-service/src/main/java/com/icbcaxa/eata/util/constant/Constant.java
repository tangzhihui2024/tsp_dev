package com.icbcaxa.eata.util.constant;

public class Constant {
	
	public static final String REMOTE_REST = "rest";
	public static final String REMOTE_FEIGN = "feign";
	public static final String REMOTE_DEFAULT = "no";
	public static final String DAO_JPA = "jpa";
	public static final String DAO_MYBATIS = "mybatis";
	public static final String DAO_DEFAULT = "no";
	public static final String GATEWAY_NO = "false";
	public static final String GATEWAY_YES = "true";

	/**
	 * 登录信息存入缓存KEY
	 */
	public static final String LOGIN_ACCOUNT = "login_account_";
	/**
	 * 登录来源存入缓存KEY
	 */
	public static final String LOGINSOURE = "loginSource";
	/**
	 * 远程ip  白名单
	 */
	public static final String REMOTE_IP = "REMOTE_IP";

	/**
	 * 秘钥
	 */
	public static final String EATA_SECRET = "eata_secret_";

	/**
	 * 授权码推送
	 */
	public static final String EATA_TOKEN_SEND = "eata_token_send_";
}
