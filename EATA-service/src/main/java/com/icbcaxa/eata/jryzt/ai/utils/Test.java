package com.icbcaxa.eata.jryzt.ai.utils; /**
 * 
 *//*

package com.vincce.axa.wechat.core.entity.jryzt.ai.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.vincce.axa.wechat.core.entity.jryzt.ai.utils.seq.SeqUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.alibaba.fastjson.JSONObject;

public class Test {

	*/
/**
	 * access_token需要根据实际情况填写
	 * request_id为随机序列
	 *//*

	protected static final String URI_PREFIX = "http://api-stg1.jryzt.com:11080/open/appsvr/financetech/openapi/user?access_token=81D8151D5AB74616A63B3302956E01A0&request_id=1231312312";
	
	private static final String rsa_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC635J11qovVVFF3NAsU4gqJaHppu8j+f3wBvWF3DuY++t6gpXFOVKPeuHARfIzlB57WhuiURnbfZCAUK46zZ0WuO2pVCDnGGRIkVCFzg1roNJSPszAbf9lyd+FCEKw2XziapIIN93mvovOK5GpTs4rwKwjzuQdqqeZ4z9TEzpByQIDAQAB";	
	
	private static final String aes_public_key = "abcdefghabcdefgh";	
	
	private static final String secretKey = "563fc0e9-44b4-475e-90eb-d0b72414d7e1";
	
	//通过用户注册接口获得
	private static final String OPEN_ID = "369311499846751784";
	//为第三方分配的渠道
	private static final String SELL_CHANNEL = "xxxx";
	
	private static void sendHttpRequest(String method, Map<String, String> requestParams) {
		try {
			
			TreeMap<String, String> reqMap = new TreeMap<String, String>();
			reqMap.put("method", method);
			reqMap.put("signMethod", "sha256");
			//为第三方分配的client_id
			reqMap.put("client_id", "P_JKOPEN_DMZ_TEST");
			reqMap.put("encodeKey", RSAUtils.encrypt(aes_public_key, rsa_public_key));
			reqMap.put("timestamp", "12313131231");
			reqMap.put("version", "1.0");
			reqMap.put("requestId", SeqUtils.getSeqNo(4));
			reqMap.put("format", "json");
			
			String requestData = AESCBCUtils.encrypt(JSONObject.toJSONString(requestParams), aes_public_key);
			
			reqMap.put("requestData", requestData);
			reqMap.put("secret", secretKey);
			System.out.println("reqMap = "+reqMap.toString());
	    	String sha256Sign = DigestUtils.sha256Hex(reqMap.toString());
	    	System.out.println("sha256Sign====="+sha256Sign);
	    	reqMap.put("sign", sha256Sign);
	    	//不能把secret作为请求入参传输，否则会泄漏
	    	reqMap.remove("secret");
			
	    	String requestStr = JSONObject.toJSONString(reqMap);
			System.out.println(requestStr);
			HttpPost httpPost = new HttpPost(URI_PREFIX);
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.addHeader("Accept", "application/json;charset=UTF-8");
			
			HttpEntity entity = new StringEntity(requestStr, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			
			CloseableHttpClient httpCLient = HttpClients.createDefault();
			CloseableHttpResponse httpResponse = httpCLient.execute(httpPost);
			entity = httpResponse.getEntity();
			String result = IOUtils.toString(entity.getContent());
			System.out.println(result);
			
			JSONObject response = JSONObject.parseObject(result);
			if (response != null) {
				System.out.println(AESCBCUtils.decrypt(response.getString("responseData"), aes_public_key));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String method = "register";
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("openId", OPEN_ID);
		requestParams.put("mobileNo", "151xxxx5157");
		requestParams.put("sellChannel", SELL_CHANNEL);
		
		sendHttpRequest(method, requestParams);
	}
	
}
*/
