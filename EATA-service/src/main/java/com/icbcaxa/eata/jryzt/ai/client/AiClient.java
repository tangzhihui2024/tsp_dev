package com.icbcaxa.eata.jryzt.ai.client;

import com.alibaba.fastjson.JSONObject;
import com.icbcaxa.eata.jryzt.ai.enums.AIRecognitionMethodEnum;
import com.icbcaxa.eata.jryzt.ai.http.HttpClientFactory;
import com.icbcaxa.eata.jryzt.ai.http.HttpClientUtil;
import com.icbcaxa.eata.jryzt.ai.pojo.dto.request.*;
import com.icbcaxa.eata.jryzt.ai.pojo.dto.result.*;
import com.icbcaxa.eata.jryzt.ai.pojo.vo.response.JKESGResponse;
import com.icbcaxa.eata.jryzt.ai.pojo.vo.response.JKOpenAPIResponse;
import com.icbcaxa.eata.jryzt.ai.utils.crypto.AESCBCUtils;
import com.icbcaxa.eata.jryzt.ai.utils.crypto.RSAUtils;
import com.icbcaxa.eata.jryzt.ai.utils.seq.SeqUtils;
import com.icbcaxa.eata.util.constant.CommenConstant;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHost;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class AiClient implements IAiClient {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取客户配置参数
     */
    private AiClientConfig config;

    /**
     * 返回客户配置参数
     *
     * @return the config
     */
    public AiClientConfig getConfig() {
        return config;
    }

    /**
     * 设置客户配置参数
     *
     * @param config
     *            the config to set
     */
    public void setConfig(AiClientConfig config) {
        this.config = config;
    }

    /**
     * 根据class路径获取图片文件
     *
     * @param imgPath
     * @return
     */
    public String getImageBase64ByClassPath(String imgPath) {
        InputStream inStream = null;
        try {
            Resource res = new ClassPathResource(imgPath);
            inStream = res.getInputStream();
            byte[] imgByte = new byte[inStream.available()];
            IOUtils.readFully(inStream, imgByte);
            inStream.close();
            return Base64.encodeBase64String(imgByte);
        } catch (IOException ex) {
            // logger.error("读取图片异常：", ex);
            System.out.println("读取图片异常：," + ex);
            throw new RuntimeException("读取图片异常");
        } finally {
            try {
                inStream.close();
            } catch (IOException e) {
                System.out.println("根据class路径获取图片文件时流关闭异常");
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据磁盘绝对路径获取文件
     *
     * @param imgPath
     * @return
     */
    public String getImageBase64(String imgPath) {
        InputStream inStream = null;
        try {
            File file = new File(imgPath);
            inStream = new FileInputStream(file);
            byte[] imgByte = new byte[inStream.available()];
            inStream.read(imgByte);
            String res = Base64.encodeBase64String(imgByte);
            return res;
        } catch (IOException ex) {
            System.out.println("读取图片异常：," + ex);
            throw new RuntimeException("读取图片异常");
        } finally {
            try {
                inStream.close();
            } catch (IOException e) {
                System.out.println("根据磁盘绝对路径获取文件时流关闭异常");
                e.printStackTrace();
            }
        }
    }

    /**
     * 将去网纹后的图片base64转为指定路径下的图片
     *
     * @param base64
     *            去网纹后的图片base64
     * @param imgPath
     *            指定磁盘路径
     * @return
     */
    public boolean getDescreenImage(String base64, String imgPath) {
        if (null == base64 || ("").equals(base64)) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        FileOutputStream out = null;
        try {
            byte[] b = decoder.decodeBuffer(base64);
            for (int i = 0; i < b.length; i++) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            Random random = new Random();
            String imgFilePath = imgPath + random.nextInt() + ".jpg";
            out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println("生成图片处理异常" + e);
            e.printStackTrace();
            return false;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                System.out.println("流关闭异常" + e);
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 模拟发送http请求，新建一个baseTest类，拆分类后都继承它
     *
     * @param method
     * @param request
     */
    private JKESGResponse<?> sendHttpRequest(String method, Object request) {
        try {
            // System.out.println(JSON.toJSONString(request));
            String aesPublicKey = CommenConstant.AES_PUBLIC_KEY;
            String clientId = CommenConstant.CLIENT_ID;
            String host = null;
            if(CommenConstant.ENVORMENT_FLAG){
                host = CommenConstant.JRYZT_HOST_PRO;
            }else{
                host = CommenConstant.JRYZT_HOST_PRO;
            }
            String secretKey = "f59ac73c-82b7-11e9-960e-06d9560006f2";
            String secret = "57iFSX5r"; //aicilent , constant
            String uri = CommenConstant.JRYZT_URL;
            Map<String, String> reqMap = new TreeMap<String, String>();
            reqMap.put("method", method);
            reqMap.put("signMethod", "sha256");
            reqMap.put("client_id", clientId);
            reqMap.put("encodeKey", RSAUtils.encrypt(aesPublicKey, CommenConstant.RSA_PUBLIC_KEY));
            reqMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
            reqMap.put("version", "1.0");
            reqMap.put("requestId", SeqUtils.getSeqNo(4));
            reqMap.put("format", "json");
            String requestData = AESCBCUtils.encrypt(JSONObject.toJSONString(request), aesPublicKey);
            reqMap.put("requestData", requestData);
            reqMap.put("secret", secretKey);

            //logger.info("平安一账通reqMap={}" + reqMap.toString());
            String sha256Sign = DigestUtils.sha256Hex(reqMap.toString());
            logger.info("平安一账通sha256Sign={}" + sha256Sign);
            reqMap.put("sign", sha256Sign);
            //logger.info("平安一账通reqMap={}" + JSONObject.toJSONString(reqMap));
            HttpClientUtil httpClient = getHttpClientUtil();

            String getTokenUrl = host + "/oauth/oauth2/access_token";
            logger.info("平安一账通getTokenUrl={}" + getTokenUrl);

            Map<String, String> getParams = new HashMap<String,String>();
            getParams.put("client_id", clientId);
            getParams.put("grant_type", "client_credentials");
            getParams.put("client_secret", secret);
            logger.info("平安一账通getParams={}" + JSONObject.toJSONString(getParams));

            //代理地址
            HttpHost proxy = new HttpHost(CommenConstant.PROXY_IP, CommenConstant.PROXY_PORT);
            logger.info("平安一账通proxy={}" + proxy);
            String tokenResult = httpClient.doGet(getTokenUrl, getParams, proxy);
            logger.info("平安一账通tokenResult={}" + JSONObject.toJSONString(tokenResult));

            String token = JSONObject.parseObject(tokenResult).getJSONObject("data").getString("access_token");
            logger.info("平安一账通access_token={}" + token);

            String aiUrl = host + uri + "?requestId=" + System.currentTimeMillis() + "&access_token=" + token;
            logger.info("平安一账通aiUrl={}" + aiUrl);

            Map<String, String> headers = new HashMap<String,String>();
            headers.put("Content-Type", "application/json");
            headers.put("Accept", "application/json");

            String aiResult = httpClient.httpPostJson(aiUrl, JSONObject.toJSONString(reqMap), headers, proxy);
            logger.info("平安一账通config.getProxy()={}" + aiResult);

            JKESGResponse<?> response = JSONObject.parseObject(aiResult, JKESGResponse.class);
            logger.info("平安一账通接口返回参数response={}" + JSONObject.toJSONString(response));
            // 方法添加返回对象
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取http
     *
     * @return
     */
    private HttpClientUtil getHttpClientUtil() {
        try {
            HttpClientFactory httpFactory = new HttpClientFactory();
            httpFactory.setMaxTotal(200);
            httpFactory.setMaxConnectionsPerRoute(50);
            httpFactory.init();
            HttpClientUtil httpUtils = new HttpClientUtil();
            httpUtils.setHttpClientFactory(httpFactory);
            httpUtils.setConnRequestTimeout(200);
            httpUtils.setConnTimeout(200);
            httpUtils.setSocketTimeout(10000);
            return httpUtils;
        } catch (Exception ex) {
            throw new RuntimeException("初始化http异常");
        }
    }

    /**
     * 封装人脸比对的请求参数与返回参数
     */
    @Override
    public FaceCompareResult faceCompare(FaceCompareRequest request) {

        // 参数的封装，加解密
        String method = AIRecognitionMethodEnum.FACE_COMPARE.getMethodName();
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        FaceCompareResult result = parseObjects(resultRes);
        return result;
    }

    /**
     * 封装活体检测的请求参数与返回参数
     */
    @Override
    public BioDetectResult BioDetect(BioDetectRequest request) {
        String method = AIRecognitionMethodEnum.BIO_DETECT.getMethodName();
        BioDetectResult result = new BioDetectResult();
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);// 得到响应加密报文
        JKOpenAPIResponse<?> response = decrypt(resultRes, config.getAesPublicKey());// 对响应报文进行解密
        if (null != response) {// 把解密后的属性值赋给对应的resultDTO
            if (null != response.getResponseData()) {
                result = JSONObject.parseObject(response.getResponseData().toString(), BioDetectResult.class);
            }
            if (null != response.getResponseCode()) {
                result.setResponseCode(response.getResponseCode());
            }
            if (null != response.getResponseMessage()) {
                result.setResponseMessage(response.getResponseMessage());
            }
        }
        return result;
    }

    /**
     * 封装去网纹的请求参数与返回参数
     */
    @Override
    public DescreenResult descreen(BioDetectRequest request) {
        String method = AIRecognitionMethodEnum.DESCREEN.getMethodName();
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        DescreenResult result = new DescreenResult();
        JKOpenAPIResponse<?> response = decrypt(resultRes, config.getAesPublicKey());// 对响应报文进行解密
        if (null != response) {
            if (null != response.getResponseData()) {
                result = JSONObject.parseObject(response.getResponseData().toString(), DescreenResult.class);
            }
            if (null != response.getResponseCode()) {
                result.setResponseCode(response.getResponseCode());
            }
            if (null != response.getResponseMessage()) {
                result.setResponseMessage(response.getResponseMessage());
            }
        }
        return result;
    }

    /**
     * 封装人脸检测的请求参数与返回参数
     */
    @Override
    public FaceDetectResult faceDetect(FaceDetectRequest request) {
        String method = AIRecognitionMethodEnum.FACE_DETECT.getMethodName();
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        FaceDetectResult result = new FaceDetectResult();
        JKOpenAPIResponse<?> response = decrypt(resultRes, config.getAesPublicKey());// 对响应报文进行解密
        if (null != response) {
            if (null != response.getResponseData()) {
                String respStr = response.getResponseData().toString();
                result = JSONObject.parseObject(respStr.substring(1, respStr.length() - 1), FaceDetectResult.class);
            }
            if (null != response.getResponseCode()) {
                result.setResponseCode(response.getResponseCode());
            }
            if (null != response.getResponseMessage()) {
                result.setResponseMessage(response.getResponseMessage());
            }
        }
        return result;
    }

    /**
     * 封装人证比对的请求参数与返回参数
     */
    @Override
    public FaceCompareResult idComparison(IdcomparisonRequest request) {
        String method = AIRecognitionMethodEnum.IDCOMPARISON.getMethodName();
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        FaceCompareResult result = parseObjects(resultRes);
        return result;
    }

    /**
     * 封装带红外照片人脸比对的请求参数与返回参数
     */
    @Override
    public FaceCompareResult infraredFaceCompare(InfraredFaceCompareRequest request) {
        String method = AIRecognitionMethodEnum.INFRARED_FACE_COMPARE.getMethodName();
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        FaceCompareResult result = parseObjects(resultRes);
        return result;
    }

    /**
     * 封装新人证核身的请求参数与返回参数
     */
    @Override
    public FaceCompareResult newIdComparison(NewIdcomparisonRequest request) {
        String method = AIRecognitionMethodEnum.NEW_IDCOMPARISON.getMethodName();
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        FaceCompareResult result = parseObjects(resultRes);
        return result;
    }

    /**
     * 封装ocr银行卡识别请求参数与返回参数
     */
    @Override
    public OcrBankCardResult ocrBankCard(OcrRequest request) {
        String method = AIRecognitionMethodEnum.OCR.getMethodName();
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        OcrBankCardResult result = new OcrBankCardResult();
        JKOpenAPIResponse<?> response = decrypt(resultRes, config.getAesPublicKey());// 对响应报文进行解密
        if (null != response) {
            if (null != response.getResponseData()) {
                result = JSONObject.parseObject(response.getResponseData().toString(), OcrBankCardResult.class);
            }
            if (null != response.getResponseCode()) {
                result.setResponseCode(response.getResponseCode());
            }
            if (null != response.getResponseMessage()) {
                result.setResponseMessage(response.getResponseMessage());
            }
        }
        return result;
    }

    /**
     * 封装ocr名片识别的请求参数与返回参数
     */
    @Override
    public OcrCardResult ocrCard(OcrRequest request) {
        String method = AIRecognitionMethodEnum.OCR.getMethodName();
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        OcrCardResult result = new OcrCardResult();
        JKOpenAPIResponse<?> response = decrypt(resultRes, config.getAesPublicKey());// 对响应报文进行解密
        if (null != response) {
            if (null != response.getResponseData()) {
                result = JSONObject.parseObject(response.getResponseData().toString(), OcrCardResult.class);
            }
            if (null != response.getResponseCode()) {
                result.setResponseCode(response.getResponseCode());
            }
            if (null != response.getResponseMessage()) {
                result.setResponseMessage(response.getResponseMessage());
            }
        }
        return result;
    }

    /**
     * 封装ocr驾驶证识别的请求参数与返回参数
     */
    @Override
    public OcrDriverLicenseResult ocrDriverLicense(OcrRequest request) {
        String method = AIRecognitionMethodEnum.OCR.getMethodName();
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        OcrDriverLicenseResult result = new OcrDriverLicenseResult();
        JKOpenAPIResponse<?> response = decrypt(resultRes, config.getAesPublicKey());// 对响应报文进行解密
        if (null != response) {
            if (null != response.getResponseData()) {
                result = JSONObject.parseObject(response.getResponseData().toString(), OcrDriverLicenseResult.class);
            }
            if (null != response.getResponseCode()) {
                result.setResponseCode(response.getResponseCode());
            }
            if (null != response.getResponseMessage()) {
                result.setResponseMessage(response.getResponseMessage());
            }
        }
        return result;
    }

    /**
     * 封装ocr行驶证识别的请求参数与返回参数
     */
    @Override
    public OcrDrivingLicenseResult ocrDrivingLicense(OcrRequest request) {
        String method = AIRecognitionMethodEnum.OCR.getMethodName();
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        OcrDrivingLicenseResult result = new OcrDrivingLicenseResult();
        JKOpenAPIResponse<?> response = decrypt(resultRes, config.getAesPublicKey());// 对响应报文进行解密
        if (null != response) {
            if (null != response.getResponseData()) {
                result = JSONObject.parseObject(response.getResponseData().toString(), OcrDrivingLicenseResult.class);
            }
            if (null != response.getResponseCode()) {
                result.setResponseCode(response.getResponseCode());
            }
            if (null != response.getResponseMessage()) {
                result.setResponseMessage(response.getResponseMessage());
            }
        }
        return result;
    }

    /**
     * 封装ocr港澳通行证识别的请求参数与返回参数
     */
    @Override
    public OcrHKMLPasserResult ocrHKMLPasser(OcrRequest request) {
        String method = AIRecognitionMethodEnum.OCR.getMethodName();
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        OcrHKMLPasserResult result = new OcrHKMLPasserResult();
        JKOpenAPIResponse<?> response = decrypt(resultRes, config.getAesPublicKey());// 对响应报文进行解密
        if (null != response) {
            if (null != response.getResponseData()) {
                result = JSONObject.parseObject(response.getResponseData().toString(), OcrHKMLPasserResult.class);
            }
            if (null != response.getResponseCode()) {
                result.setResponseCode(response.getResponseCode());
            }
            if (null != response.getResponseMessage()) {
                result.setResponseMessage(response.getResponseMessage());
            }
        }
        return result;
    }

    /**
     * 封装ocr身份证背面识别的请求参数与返回参数
     */
    @Override
    public OcrIDCardBackResult ocrIDCardBack(OcrRequest request) {
        String method = AIRecognitionMethodEnum.OCR.getMethodName();
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        OcrIDCardBackResult result = new OcrIDCardBackResult();
        JKOpenAPIResponse<?> response = decrypt(resultRes, config.getAesPublicKey());// 对响应报文进行解密
        if (null != response) {
            if (null != response.getResponseData()) {
                result = JSONObject.parseObject(response.getResponseData().toString(), OcrIDCardBackResult.class);
            }
            if (null != response.getResponseCode()) {
                result.setResponseCode(response.getResponseCode());
            }
            if (null != response.getResponseMessage()) {
                result.setResponseMessage(response.getResponseMessage());
            }
        }
        return result;
    }

    /**
     * 封装ocr身份证正面识别的请求参数与返回参数
     */
    @Override
    public OcrIDCardFontResult ocrIDCardFront(OcrRequest request) {
        String method = AIRecognitionMethodEnum.OCR.getMethodName();
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        OcrIDCardFontResult result = new OcrIDCardFontResult();
        JKOpenAPIResponse<?> response = decrypt(resultRes, config.getAesPublicKey());// 对响应报文进行解密
        if (null != response) {
            if (null != response.getResponseData()) {
                result = JSONObject.parseObject(response.getResponseData().toString(), OcrIDCardFontResult.class);
            }
            if (null != response.getResponseCode()) {
                result.setResponseCode(response.getResponseCode());
            }
            if (null != response.getResponseMessage()) {
                result.setResponseMessage(response.getResponseMessage());
            }
        }
        return result;
    }

    /**
     * 封装ocr护照识别的请求参数与返回参数
     */
    @Override
    public OcrPassportResult ocrPassport(OcrRequest request) {
        String method = AIRecognitionMethodEnum.OCR.getMethodName();
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        OcrPassportResult result = new OcrPassportResult();
        JKOpenAPIResponse<?> response = decrypt(resultRes, config.getAesPublicKey());// 对响应报文进行解密
        if (null != response) {
            if (null != response.getResponseData()) {
                result = JSONObject.parseObject(response.getResponseData().toString(), OcrPassportResult.class);
            }
            if (null != response.getResponseCode()) {
                result.setResponseCode(response.getResponseCode());
            }
            if (null != response.getResponseMessage()) {
                result.setResponseMessage(response.getResponseMessage());
            }
        }
        return result;
    }

    /**
     * 封装台湾通行证识别的请求参数与返回参数
     */
    @Override
    public OcrTWPassResult ocrTWPass(OcrRequest request) {
        String method = AIRecognitionMethodEnum.OCR.getMethodName();
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        OcrTWPassResult result = new OcrTWPassResult();
        JKOpenAPIResponse<?> response = decrypt(resultRes, config.getAesPublicKey());// 对响应报文进行解密
        if (null != response) {
            if (null != response.getResponseData()) {
                result = JSONObject.parseObject(response.getResponseData().toString(), OcrTWPassResult.class);
            }
            if (null != response.getResponseCode()) {
                result.setResponseCode(response.getResponseCode());
            }
            if (null != response.getResponseMessage()) {
                result.setResponseMessage(response.getResponseMessage());
            }
        }
        return result;
    }

    /**
     * 封装两照比对的请求参数与返回参数
     */
    @Override
    public FaceCompareResult twoFaceCompare(FaceCompareRequest request) {
        String method = AIRecognitionMethodEnum.TWO_FACE_COMPARE.getMethodName();
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        FaceCompareResult result = parseObjects(resultRes);
        return result;
    }

    /**
     * 封装xface活体检测的请求参数与返回参数
     */
    @Override
    public BioDetectResult xFaceBioDetect(BioDetectRequest request) {
        String method = AIRecognitionMethodEnum.XFACE_BIO_DETECT.getMethodName();
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        BioDetectResult result = new BioDetectResult();
        JKOpenAPIResponse<?> response = decrypt(resultRes, config.getAesPublicKey());// 对响应报文进行解密
        if (null != response) {// 把解密后的属性值赋给对应的resultDTO
            if (null != response.getResponseData()) {
                result = JSONObject.parseObject(response.getResponseData().toString(), BioDetectResult.class);
            }
            if (null != response.getResponseCode()) {
                result.setResponseCode(response.getResponseCode());
            }
            if (null != response.getResponseMessage()) {
                result.setResponseMessage(response.getResponseMessage());
            }
        }
        return result;
    }

    /**
     * 解密ai调用接口后返回的报文
     *
     * @param response
     * @param aesPublicKey
     * @return
     */
    public JKOpenAPIResponse<?> decrypt(JKESGResponse<?> response, String aesPublicKey) {
        try {
            System.out.println("response：" + response);
            String decryptData = "";
            if (null != response.getData()) {
                if (null != response.getData().getResponseData()) {
                    decryptData = AESCBCUtils.decrypt(response.getData().getResponseData().toString(), aesPublicKey);
                    System.out.println("解密结果：" + decryptData);
                } else {
                    decryptData = "{\"responseCode\":" + "\"" + response.getData().getResponseCode() + "\"" + ","
                            + "\"responseMessage\":" + "\"" + response.getData().getResponseMessage() + "\"" + "}";
                }
            }
            if (null != decryptData && !"".equals(decryptData)) {
                JKOpenAPIResponse<?> returnRes = JSONObject.parseObject(decryptData, JKOpenAPIResponse.class);
                return returnRes;
            }
        } catch (Exception e) {
            System.out.println("解密返回报文出错！！");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把解密之后的属性值赋给FaceCompareResult对象，因为多个接口返回报文的属性值是相同的，所以此处有封装
     *
     * @param resultRes
     * @param
     * @return
     */
    private FaceCompareResult parseObjects(JKESGResponse<?> resultRes) {
        JKOpenAPIResponse<?> response = decrypt(resultRes, config.getAesPublicKey());// 对响应报文进行解密
        FaceCompareResult result = new FaceCompareResult();
        if (null != response) {// 把解密后的属性值赋给对应的resultDTO
            if (null != response.getResponseData()) {
                result = JSONObject.parseObject(response.getResponseData().toString(), FaceCompareResult.class);
            }
            if (null != response.getResponseCode()) {
                result.setResponseCode(response.getResponseCode());
            }
            if (null != response.getResponseMessage()) {
                result.setResponseMessage(response.getResponseMessage());
            }
        }
        return result;
    }

    /**
     * 封装人证比对V6的请求参数与返回参数
     */
    @Override
    public FaceCompareResult idComparisonV6(IdcomparisonV6Request request) {
        String method = AIRecognitionMethodEnum.IDCOMPARISON_V6.getMethodName();
        logger.info("===============平安一账通返回结果method={}"+ method);
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        //logger.info("===============平安一账通返回结果resultRes={}"+ JSONObject.toJSONString(resultRes));
        FaceCompareResult result = parseObjects(resultRes);
        //logger.info("==============平安一账通返回结果result={}"+ JSONObject.toJSONString(result));
        return result;
    }

    /**
     * 封装两照比对V3的请求参数与返回参数
     */
    @Override
    public FaceCompareResult twoFaceCompareV3(TwoFaceCompareV3Request request) {
        String method = AIRecognitionMethodEnum.TWO_FACE_COMPARE_V3.getMethodName();
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        FaceCompareResult result = parseObjects(resultRes);
        return result;
    }

    /**
     * 封装身份验证的请求参数与返回参数
     */
    @Override
    public FaceCompareResult idVerificationB(IdcomparisonV6Request request) {
        String method = AIRecognitionMethodEnum.IDVERIFICATION_B.getMethodName();
        logger.info("===============平安一账通返回结果method={}"+ method);
        JKESGResponse<?> resultRes = sendHttpRequest(method, request);
        //logger.info("===============平安一账通返回结果resultRes={}"+ JSONObject.toJSONString(resultRes));
        FaceCompareResult result = parseObjects(resultRes);
        //logger.info("==============平安一账通返回结果result={}"+ JSONObject.toJSONString(result));
        return result;
    }
}
