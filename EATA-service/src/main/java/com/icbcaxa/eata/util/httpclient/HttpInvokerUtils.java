package com.icbcaxa.eata.util.httpclient;

import com.icbcaxa.eata.util.constant.CommenConstant;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.*;

public class HttpInvokerUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpInvokerUtils.class);

	/**
	 * 获取header里面的值
	 */
	public static Map<String, String> getHeadersInfo(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<?> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		return map;
	}

	public static String requestToString(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = 0;
		byte[] b = new byte[1024];
		while ((len = is.read(b, 0, b.length)) != -1) {
			baos.write(b, 0, len);
		}
		byte[] buffer = baos.toByteArray();
		String retValue = new String(buffer, "UTF-8");
		return retValue;
	}

	/**
	 * 获取UUID
	 */
	public static String getUUID(String uid) {
		return UUID.nameUUIDFromBytes(uid.getBytes()).toString().replace("-", "");
	}

	/**
	 * post请求（用于请求json格式的参数）
	 * @param url
	 * @param jsonParams
	 * @return
	 */
	public static String doPostJson(String url, String jsonParams) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		String charSet = "UTF-8";
		StringEntity entity = new StringEntity(jsonParams, charSet);
		httpPost.setEntity(entity);
		return invoke(httpPost);
	}

	public static String doGet(String url) {
		HttpGet get = new HttpGet(url);
		return invoke(get);
	}

	public static String doGet(String url,Map<String, String> params) {
		URIBuilder builder = null;
		try {
			builder = new URIBuilder(url);

			for (String key : params.keySet()) {
				builder.setParameter(key, params.get(key));
			}
			HttpGet get = new HttpGet(builder.build());
			return invoke(get);
		} catch (URISyntaxException e) {
			logger.error("URL编码异常(" + url + ")", e);
			return  null;
		}
	}



	private static String invoke(HttpUriRequest request) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String url = request.getURI().getPath();
		try {
			response = httpclient.execute(request);
			StatusLine status = response.getStatusLine();
			HttpEntity responseEntity = response.getEntity();
			String result =  EntityUtils.toString(responseEntity);
			logger.info("(" + request.toString() + ")返回：");
			logger.info(result);
			return  result;
		} catch (IOException e) {
			logger.error("请求异常:" + url, e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("关闭response异常:" + url, e);
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error("关闭httpclient异常:" + url, e);
			}
		}
		return null;
	}

	/**
	 * HTTP Get 获取内容
	 * url请求的url地址
	 * params请求的参数
	 * charset编码格式
	 * PROXY_ENABLED 是否走代理
	 * @return 页面内容
	 */
	public static String sendGet_FaceKernel(String url, Map<String, Object> params, boolean PROXY_ENABLED) throws ParseException, UnsupportedEncodingException, IOException ,UnknownHostException {
		CloseableHttpClient httpclient;
		RequestConfig requestConfig;
		if (PROXY_ENABLED) {
			//设置代理IP、端口、协议（请分别替换）
			HttpHost proxy = new HttpHost(CommenConstant.PROXY_IP, CommenConstant.PROXY_PORT, "http");
			//把代理设置到请求配置
			requestConfig = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(3000)
					.setProxy(proxy)
					.build();
			//实例化CloseableHttpClient对象
			httpclient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		} else {
			requestConfig = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(3000).build();
			httpclient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
		}
		if (params != null && !params.isEmpty()) {

			List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());

			for (String key : params.keySet()) {
				pairs.add(new BasicNameValuePair(key, params.get(key).toString()));
			}
			url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs), "UTF-8");
		}
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			httpGet.abort();
			throw new RuntimeException("HttpClient,error status code :" + statusCode);
		}
		HttpEntity entity = response.getEntity();
		String result = null;
		if (entity != null) {
			result = EntityUtils.toString(entity, "utf-8");
			EntityUtils.consume(entity);
			response.close();
			return result;
		} else {
			return null;
		}
	}

	/**
	 * HTTP Post 获取内容
	 * url请求的url地址 ?之前的地址
	 * requestBody  请求body信息( JSON.toJSONString(new Object[] {  new HashMap<String, String> }))
	 * PROXY_ENABLED 是否走代理
	 * @return 页面内容
	 * @throws IOException
	 * @throws ClientProtocolException
	 *  Update by  Liu Guo Dian on 2019/7/2.
	 */
	public static String sendPostBody(String url, String  requestBody, boolean PROXY_ENABLED) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient;
		RequestConfig requestConfig;
		if (PROXY_ENABLED) {
			//设置代理IP、端口、协议（请分别替换）
			HttpHost proxy = new HttpHost(CommenConstant.PROXY_IP, CommenConstant.PROXY_PORT, "http");
			//把代理设置到请求配置
			requestConfig = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(3000)
					.setProxy(proxy)
					.build();
			//实例化CloseableHttpClient对象
			httpclient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		} else {
			requestConfig = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(3000).build();
			httpclient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
		}
		HttpPost httpPost = new HttpPost(url);
		if (requestBody != null ) {
			httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
			// 解决中文乱码问题
			StringEntity stringEntity = new StringEntity(requestBody, "UTF-8");
			stringEntity.setContentEncoding("UTF-8");
			httpPost.setEntity(stringEntity);
		}
		CloseableHttpResponse response = httpclient.execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			httpPost.abort();
			throw new RuntimeException("HttpClient,error status code :" + statusCode);
		}
		HttpEntity entity = response.getEntity();
		String result = null;
		if (entity != null) {
			result = EntityUtils.toString(entity, "utf-8");
			EntityUtils.consume(entity);
			response.close();
			return result;
		} else {
			return null;
		}
	}


}
