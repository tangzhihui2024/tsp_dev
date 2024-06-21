package com.icbcaxa.eata.jryzt.ai.client;

import org.apache.http.HttpHost;

public class AiClientConfig {
	/**
	 * 资源路径
	 */
	private String uri;
	/**
	 * rsa公钥
	 */
	private String rsaPublicKey;
	/**
	 * aes公钥
	 */
	private String aesPublicKey;
	/**
	 * 加密验签盐值
	 */
	private String secretKey;
	/**
	 * CLIENTID---应用编号
	 */
	private String clientId;
	/**
	 * 应用编号密码
	 */
	private String secret;
	/**
	 * 开放平台智能认证主机地址
	 */
	private String host;
	/**
	 * 渠道号
	 */
	private String channelId;
	
	/**
	 * 代理
	 */
	private HttpHost proxy;

	/**
	 * 返回资源路径
	 * 
	 * @return the 资源路径
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * 设置资源路径
	 * 
	 * @param
	 *
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * 返回rsa公钥
	 * 
	 * @return the rsaPublicKey
	 */
	public String getRsaPublicKey() {
		return rsaPublicKey;
	}

	/**
	 * 设置rsa公钥
	 * 
	 * @param rsaPublicKey
	 *            the rsaPublicKey to set
	 */
	public void setRsaPublicKey(String rsaPublicKey) {
		this.rsaPublicKey = rsaPublicKey;
	}

	/**
	 * 返回aes公钥
	 * 
	 * @return the aesPublicKey
	 */
	public String getAesPublicKey() {
		return aesPublicKey;
	}

	/**
	 * 设置aes公钥
	 * 
	 * @param aesPublicKey
	 *            the aesPublicKey to set
	 */
	public void setAesPublicKey(String aesPublicKey) {
		this.aesPublicKey = aesPublicKey;
	}

	/**
	 * 返回加密验签盐值
	 * 
	 * @return the secretKey
	 */
	public String getSecretKey() {
		return secretKey;
	}

	/**
	 * 设置加密验签盐值
	 * 
	 * @param secretKey
	 *            the secretKey to set
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * 返回CLIENTID---应用编号
	 * 
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * 设置CLIENTID---应用编号
	 * 
	 * @param clientId
	 *            the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * 返回应用编号密码
	 * 
	 * @return the secret
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * 设置应用编号密码
	 * 
	 * @param secret
	 *            the secret to set
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * 返回开放平台智能认证主机地址
	 * 
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * 设置开放平台智能认证主机地址
	 * 
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 返回渠道号
	 * 
	 * @return the channelId
	 */
	public String getChannelId() {
		return channelId;
	}

	/**
	 * 设置渠道号
	 * 
	 * @param channelId
	 *            the channelId to set
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

    public HttpHost getProxy() {
        return proxy;
    }

    public void setProxy(HttpHost proxy) {
        this.proxy = proxy;
    }	
}
