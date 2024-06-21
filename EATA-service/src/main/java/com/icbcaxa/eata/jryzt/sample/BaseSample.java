package com.icbcaxa.eata.jryzt.sample;

import com.alibaba.fastjson.JSONObject;
import com.icbcaxa.eata.jryzt.ai.client.AiClientConfig;
import com.icbcaxa.eata.util.constant.CommenConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取客户配置文件里的参数设置
 * 
 * @author EX-MAQINGNI002
 *
 */
public class BaseSample {
    /**
     * 获取客户配置参数
     */
    private AiClientConfig config;

    /**
     * 返回config
     * 
     * @return the config
     */
    public AiClientConfig getConfig() {
        return config;
    }

    /**
     * 设置config
     * 
     * @param config
     *            the config to set
     */
    public void setConfig(AiClientConfig config) {
        this.config = config;
    }

    public BaseSample() {
        init();
    }
    Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 初始化方法
     */
    public void init() {
        /*InputStream inStream = null;
        //inStream = ClassLoader.getSystemResourceAsStream("application.development.properties");
        inStream = BaseSample.class.getResourceAsStream("application.openApiDemo.properties");
        logger.info("平安一账通inStream={}"+ JSONObject.toJSONString(inStream));*/
        try {
            /*Properties pros = new Properties();
            pros.load(inStream);*/
            AiClientConfig config = new AiClientConfig();
           /* config.setHost(pros.getProperty("openApiDemo.host"));
            config.setUri(pros.getProperty("openApiDemo.url"));
            config.setClientId(pros.getProperty("openApiDemo.client_id"));
            config.setSecret(pros.getProperty("openApiDemo.client_secret"));
            config.setRsaPublicKey(pros.getProperty("openApiDemo.rsa_public_key"));
            config.setAesPublicKey(pros.getProperty("openApiDemo.aes_public_key"));
            config.setChannelId(pros.getProperty("openApiDemo.channel_id"));
            config.setSecretKey(pros.getProperty("openApiDemo.secret_key"));*/
           
            if(CommenConstant.ENVORMENT_FLAG){
                config.setHost(CommenConstant.JRYZT_HOST_PRO);
            }else{
                config.setHost(CommenConstant.JRYZT_HOST_PRO);
            }
            config.setUri(CommenConstant.JRYZT_URL);
            config.setClientId(CommenConstant.CLIENT_ID);
            config.setSecret(CommenConstant.CLIENT_SECRET);
            config.setRsaPublicKey(CommenConstant.RSA_PUBLIC_KEY);
            config.setAesPublicKey(CommenConstant.AES_PUBLIC_KEY);
            config.setChannelId(CommenConstant.CHANNEL_ID);
            config.setSecretKey(CommenConstant.SECRET_KEY);

            this.config = config;
            logger.info("平安一账通config={}"+ JSONObject.toJSONString(config));
            //initProxy(pros);
        } catch (Exception e) {
            logger.info("获取资源文件失败", e);
        } finally {
            // org.apache.commons.io.IOUtils.closeQuietly(inStream);
            try {
                //inStream.close();
            } catch (Exception e) {
                logger.info("读取用户参数配置时关闭流异常！！");
                e.printStackTrace();
            }
        }
    }

  /*  private void initProxy(Properties pros) {
        String pacUrl = pros.getProperty("openApiDemo.autoConfigUrl");
        String host = pros.getProperty("openApiDemo.httpProxyHost");
        String port = pros.getProperty("openApiDemo.httpProxyPort");
        if (StringUtils.isNotEmpty(pacUrl)) {
            BrowserProxyInfo b = new BrowserProxyInfo();
            b.setType(ProxyType.AUTO);
            b.setAutoConfigURL(pacUrl);
            SunAutoProxyHandler handler = new SunAutoProxyHandler();
            try {
                handler.init(b);
            } catch (ProxyConfigException e1) {
                e1.printStackTrace();
            }
            try {
                // need add socket
                URL url = new URL(config.getHost());
                String proxyHost = null;
                Integer proxyPort = 0;
                ProxyInfo[] ps = handler.getProxyInfo(url);
                logger.info("代理数量:" + ps.length);
                for (ProxyInfo p : ps) {
                    String proxyString = p.toString();
                    System.out.println("proxy  = " + proxyString);
                    if ("DIRECT".equals(proxyString)) {
                        logger.info(proxyString);
                    } else {
                        String[] info = proxyString.split(":");
                        // System.out.println(info);
                        proxyHost = info[0];
                        proxyPort = Integer.parseInt(info[1]);
                        break;
                    }
                }
                HttpHost proxy = new HttpHost(proxyHost, proxyPort);
                config.setProxy(proxy);

            } catch (MalformedURLException e) {
                logger.info(config.getHost() + " 不是合法的URL");
            } catch (ProxyUnavailableException e) {
                e.printStackTrace();
                logger.info(pacUrl + "无法通过代理访问" + config.getHost());
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("从自动代理url中获取代理信息失败:" + pacUrl);
            }
        } else if (StringUtils.isNotEmpty(host) && StringUtils.isNotEmpty(port)) {
            try {
                int proxyPort = Integer.parseInt(host);
                HttpHost proxy = new HttpHost(port, proxyPort);
                config.setProxy(proxy);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("从自动代理url中获取代理信息失败:" + pacUrl);
            }
        }

    }*/
}
