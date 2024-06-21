package com.icbcaxa.eata.util.constant;

public class CommenConstant {
	

	public static final String  VALID = "00";//有效

	public static final String INVALID = "01";//无效

	public static final String FAILURE = "01";//code无效

	//代理服务器地址
	public static final boolean PROXY_ENABLED = true ;
	public static final String PROXY_IP ="84.239.65.50";
	public static final Integer PROXY_PORT =8080;

	//wechat.proxy_enabled = true;
	//wechat.proxy_ip = 84.239.65.50;
	//wechat.proxy_port = 8080;

	/**
	 * * 慧眼人脸核身公共参数
	 * *
	 * */
	public static final String PERSON_ABSENCE = "3";//证件不存在

	public static final String WBAPPID = "IDAAn2rs" ;

	public static final String SECRET = "rLEOQpgqMtrPnbnvpbZGjeQba1j6YEiPKQuJ3OxWdPwnV3wepqeM7lnDsrrjkAhR" ;

	public static final String VERSION = "1.0.0" ;

	// 腾讯慧眼接口第一步换取token地址
	public static final String ACCESS_TOKEN_URL = "https://idasc.webank.com/api/oauth2/access_token" ;

	// 腾讯慧眼接口第二步换取ticket地址
	public static final String TICKET_URL = "https://idasc.webank.com/api/oauth2/api_ticket" ;

	// 腾讯慧眼接口 最后一步调用2要素（姓名，自拍照）+ 自拍照认证模式
	public static final String FACEKERNEL_URL = "https://ida.webank.com/api/paas/easyface" ;

	//aes密钥,秘钥为16位长度的字母加数组随机组合，建议随机生成
	public static final String AES_PUBLIC_KEY="abcdefghabcdefgh";

	//客户端ID
	public static final String CLIENT_ID="P_JKOPEN_GYAS_I";

	/**
	 * 环境切换标识
	 */
	public static boolean ENVORMENT_FLAG;

	//开放平台智能认证主机地址（https://api.jryzt.com为生产，https://api-stg1.jryzt.com:11443为测试）
	public static final String JRYZT_HOST_TEST="https://api-stg1.jryzt.com:11443";

	public static final String JRYZT_HOST_PRO="https://api.jryzt.com";

	//URI
	public static final String JRYZT_URL="/open/appsvr/oneconnect/openapi/v3/recognition";

	//rsa公钥
	public static final String RSA_PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDE1Vr+M/9slwV6SzRGlE82diQS3W3e3lffJdAgIRnj2ENnzySxABb/Iz0PTlArrwYoFlU8YkYCZYEbLqDYAK0wlDDLTKyr1XKjhDHEavxZU/GZa8PzGSXd2A1mlXmiiMbhv544i2FAdJqm5w0RB/4J9a0eXgsZti5N52qlOMHsJQIDAQAB";

	public static final String CLIENT_SECRET= "3rQp9mr6";

	public static final String CHANNEL_ID="1000193";

	public static final String SECRET_KEY="7b9edff4-82b2-11e9-960e-06d9560006f2";

	/**
	 * 工银人脸比对配置
	 */
	public static final String ASYNC_MAX_CONNECTIO="50";//异步请求连接池的最大连接数
	public static final String SOCKET_TIMEOUT="20000";//SDK发送请求以后的等待服务超时时间
	public static final String CONNECTION_TIMEOUT="8000";//SDK与API开放平台tcp三次握手的超时时间
	public static final String CONNECTION_IDLE_TIME="3000";//连接空闲时间（毫秒），连接池中连接如果超过空闲时间将被回收
	public static final String SYNC_MAX_CONNECTION="50";//同步请求连接池的最大连接数
	public static final String ASYNC_THREAD_POOL_SIZE="20";//异步请求线程池大小
	public static final String AUTH_MAX_TIME_STEP="3000000000000";//TOKEN获取过程中，当前系统与api服务器之间允许的最大时间差（毫秒）
	public static final String DEFAULT_CHARSET="UTF-8";//默认编码
	public static final String SIGNATURE_ALGO="RSA2048";//使用的加密算法类型
	//public static final String OAUTH_API_BASE_URI="http://122.64.61.115:8081/api";//SIT基本地址
	public static final String OAUTH_API_BASE_URI="http://83.28.33.232:8081/api";//UAT基本地址
	//public static final String OAUTH_API_BASE_URI="http://gw.open.icbc.com.cn:8081/api";//PRO基本地址
	//public static final String OAUTH_APP_ID="10000000000004095258";//SIT
	public static final String OAUTH_APP_ID="10000000000000192505";//UAT
	//public static final String OAUTH_APP_ID="10000000000001270500";//PRO
	public static final String OAUTH_PUBKEY_LOC="/eata/key/config/";//公钥
	public static final String OAUTH_APP_PRIKEY_LOCATION="/eata/key/config/";//私钥
	public static final String CUSTOMER_AGREEMENT="1";
	public static final String STRING_ZERO="0";
	public static final String DICT_VALUE_URI="/bas/face/facescompare/V2";
	public static final Double DEFAULT_SIM=80.0;
	public static final String PROGRAM_NAME="1001";
}
