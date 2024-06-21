package com.icbcaxa.eata.jryzt.ai.http;

import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;

import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author YANGCHANGPENG869
 * @version $Id: HttpClientFactory.java, v 0.1 下午5:12:49 YANGCHANGPENG869 Exp $
 */
public class HttpClientFactory {

    // static private final Logger logger = LoggerFactory.getLogger(HttpClientFactory.class);

    private static final int DEFAULT_CONN_TIMEOUT = 20 * 1000;

    private static final int DEFAULT_CONN_REQUEST_TIMEOUT = 20 * 1000;

    private static final int DEFAULT_SOCKET_TIMEOUT = 60 * 1000;

    private int maxTotal = 100;

    private int maxConnectionsPerRoute = 20;

    private PoolingHttpClientConnectionManager connManager;

    public void init() throws Exception {
        this.buildConnManager();
    }

    public void buildConnManager() {
        // logger.info("+++++++++++++++++初始化连接池+++++++++++++++");
        System.out.println("+++++++++++++++++初始化连接池+++++++++++++++");
        // Use custom message parser / writer to customize the way HTTP
        // messages are parsed from and written out to the data stream.
        HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory();
        HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();

        // Use a custom connection factory to customize the process of
        // initialization of outgoing HTTP connections. Beside standard connection
        // configuration parameters HTTP connection factory can define message
        // parser / writer routines to be employed by individual connections.
        HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
            requestWriterFactory, responseParserFactory);
        // Create a connection manager with custom configuration.
        connManager = new PoolingHttpClientConnectionManager(connFactory);

        // Create message constraints
        MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200)
            .setMaxLineLength(2000).build();

        // Create connection configuration
        ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
            .setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
            .setMessageConstraints(messageConstraints).build();

        // Configure the connection manager to use connection configuration either
        // by default or for a specific host.
        connManager.setDefaultConnectionConfig(connectionConfig);
        connManager.setDefaultMaxPerRoute(maxConnectionsPerRoute);
        connManager.setMaxTotal(maxTotal);
    }

    public CloseableHttpClient build(HttpHost proxy) {
        long startTime = System.currentTimeMillis();
        CloseableHttpClient httpclient = build(DEFAULT_CONN_TIMEOUT, DEFAULT_CONN_REQUEST_TIMEOUT,
            DEFAULT_SOCKET_TIMEOUT, proxy);
        long costTime = System.currentTimeMillis() - startTime;

        System.out.println("Build HttpClient cost time [{}]," + costTime);
        return httpclient;
    }

    public CloseableHttpClient build() {
        long startTime = System.currentTimeMillis();
        CloseableHttpClient httpclient = build(DEFAULT_CONN_TIMEOUT, DEFAULT_CONN_REQUEST_TIMEOUT,
            DEFAULT_SOCKET_TIMEOUT);
        long costTime = System.currentTimeMillis() - startTime;

        // logger.debug("Build HttpClient cost time [{}]", costTime);
        System.out.println("Build HttpClient cost time [{}]," + costTime);
        return httpclient;
    }

    public CloseableHttpClient build(int connTimeout) {
        return build(connTimeout, DEFAULT_CONN_REQUEST_TIMEOUT, DEFAULT_SOCKET_TIMEOUT);
    }

    public CloseableHttpClient build(int connTimeout, int reqTimeout, int socketTimeout) {
        return build(connTimeout, reqTimeout, socketTimeout, null);
    }

    public CloseableHttpClient build(int connTimeout, int reqTimeout, int socketTimeout, HttpHost proxy) {
        if (connManager == null) {
            buildConnManager();
        }

        // Create global request configuration
        Builder builder = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH).setExpectContinueEnabled(true)
            .setStaleConnectionCheckEnabled(true)
            .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
            .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).setSocketTimeout(socketTimeout)
            .setConnectTimeout(connTimeout).setConnectionRequestTimeout(reqTimeout);
        // Create global request configuration
        if (proxy != null) {
            builder.setProxy(proxy);
        }
        RequestConfig defaultRequestConfig = builder.build();

        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connManager)
            .setDefaultRequestConfig(defaultRequestConfig).build();

        return httpclient;
    }

    public void setMaxTotal(int max) {
        this.maxTotal = max;
    }

    public void setMaxConnectionsPerRoute(int maxConnectionsPerRoute) {
        this.maxConnectionsPerRoute = maxConnectionsPerRoute;
    }

}
