package com.icbcaxa.eata.service.impl;

import com.alibaba.fastjson.JSON;
import com.dianping.cat.Cat;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.icbcaxa.eata.entity.*;
import com.icbcaxa.eata.jryzt.ai.pojo.dto.request.IdcomparisonV6Request;
import com.icbcaxa.eata.jryzt.ai.pojo.dto.result.FaceCompareResult;
import com.icbcaxa.eata.jryzt.ai.utils.string.StringUtil;
import com.icbcaxa.eata.jryzt.sample.IdComparisonV6Sample;
import com.icbcaxa.eata.mapper.EataLiveSctagCertMapper;
import com.icbcaxa.eata.service.EataLiveSctagCertService;
import com.icbcaxa.eata.util.Response;
import com.icbcaxa.eata.util.constant.CommenConstant;
import com.icbcaxa.eata.util.date.DateUtil;
import com.icbcaxa.eata.util.httpclient.HttpInvokerUtils;
import com.icbcaxa.eata.vo.EataLiveSctagCertVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.io.File;
import java.util.*;

/**
 *人证比对服务层接口实现类
 * Created by TanagZhihui on 2020/3/20.
 */
@Service
public class EataLiveSctagCertServiceImpl implements EataLiveSctagCertService{
    private static final Logger logger = LoggerFactory.getLogger(EataLiveSctagCertServiceImpl.class);

    @Autowired
    private EataLiveSctagCertMapper eataLiveSctagCertMapper;

    public Response<String> faceKernelAuth(EataLiveSctagCertVo eataLiveSctagCertVo){
        long methodStartTime = System.currentTimeMillis();
        logger.info("[EataLiveSctagCertServiceImpl.faceKernelAuth方法start]" + "[根据唯一标识,身份证号,姓名,来源系统,业务功能模块,base64图片编码,请求时间,风险等级]" +
                "[入参:" + "creditSwiftNumber =" + eataLiveSctagCertVo.getCreditSwiftNumber() + ",certId =" + eataLiveSctagCertVo.getCertId() + ",name =" + eataLiveSctagCertVo.getName() +
                ",callSystem=" + eataLiveSctagCertVo.getCallSystem() + ",functionModule=" + eataLiveSctagCertVo.getFunctionModule() + "livePicUrl =" + eataLiveSctagCertVo.getLivePicUrl() +
                ",requestTime=" + eataLiveSctagCertVo.getRequestTime() + ",liveRate=" + eataLiveSctagCertVo.getLiveRate() + "]");
        Response<String> resp = new Response<>(EataLiveSctagCertEnum.GYAS_LANCER_SUCCESS_000000.getCode(), "");
        FaceCompareResult faceCompareResult = null;
        try {
            if (!StringUtils.isEmpty(eataLiveSctagCertVo.getCreditSwiftNumber()) && !StringUtils.isEmpty(eataLiveSctagCertVo.getCertId())
                    && !StringUtils.isEmpty(eataLiveSctagCertVo.getName()) && !StringUtils.isEmpty(eataLiveSctagCertVo.getCallSystem())
                    && !StringUtil.isEmpty(eataLiveSctagCertVo.getFunctionModule()) && !StringUtils.isEmpty(eataLiveSctagCertVo.getLivePicUrl())
                    && !StringUtil.isEmpty(eataLiveSctagCertVo.getRequestTime())) {
                File file = new File(eataLiveSctagCertVo.getLivePicUrl());
                Long size = file.length() / 1024; // 图片大小
                Long maxImageSize = 2 * 1024L;// 图片最大不能超过2MB
                if (size > maxImageSize) {
                    resp.setIsActive(CommenConstant.INVALID);
                    resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000014.getCode());
                    resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000014.getMag());
                    logger.info("[EataLiveSctagCertServiceImpl.faceKernelAuth方法size]" + "[返回值:size" + size + "高级认证失败，请稍后再试！" + "]");
                } else {
                    /**
                     * 人证比对接口 调用壹帐通.2020-03-20
                     * 壹帐通如果通过就不用再调用腾讯云了，如壹帐通识别失败，再调用腾讯云，如腾讯云通过则认证通过，如不通过则认证失败
                     */
                    //第一步调用壹帐通
                    //String picPath = Base64Utils.ImageToBase64ByLocal(eataLiveSctagCertVo.getLivePicUrl()).replaceAll("\\n", "");
                    IdcomparisonV6Request idcomparisonV6Request = new IdcomparisonV6Request();
                    idcomparisonV6Request.setClientCardNo(eataLiveSctagCertVo.getCertId());
                    idcomparisonV6Request.setRealName(eataLiveSctagCertVo.getName());
                    idcomparisonV6Request.setPersonId("customerId_PersonId" + eataLiveSctagCertVo.getCallSystem() + "_" + DateUtil.toSeconds(new Date()));
                    idcomparisonV6Request.setImgData(eataLiveSctagCertVo.getLivePicUrl());
                    //logger.info("平安一账通getParams={}" + com.alibaba.fastjson.JSONObject.toJSONString(idcomparisonV6Request));
                    faceCompareResult = IdComparisonV6Sample.IdcomparisonV6(idcomparisonV6Request);
                    logger.info("平安一账通faceCompareResult={}" + com.alibaba.fastjson.JSONObject.toJSONString(faceCompareResult));
                    if (!StringUtils.isEmpty(faceCompareResult)) {
                        Double riskRating = null;//风险等级
                        Double similarity1 = Double.parseDouble(faceCompareResult.getSimilarity());//设定相似度
                        String liveOrderNo = eataLiveSctagCertVo.getCallSystem() + "_" + DateUtil.toSeconds(new Date());
                        String creditCode = faceCompareResult.getResponseCode() + "-" + faceCompareResult.getResponseMessage();
                        if ("0".equals(eataLiveSctagCertVo.getLiveRate())) {
                            riskRating = 0.79;
                        } else if ("1".equals(eataLiveSctagCertVo.getLiveRate())) {
                            riskRating = 0.85;
                        } else if ("2".equals(eataLiveSctagCertVo.getLiveRate())) {
                            riskRating = 0.92;
                        } else {
                            riskRating = 0.79;
                        }
                        if (StringUtils.isEmpty(similarity1)) {
                            similarity1 = 0.00;
                        }
                        if (similarity1 > riskRating && faceCompareResult.getResponseCode().equals(EataLiveSctagCertEnum.GYAS_LANCER_SUCCESS_000000.getCode())) {
                            int faceKernelAuth = insertFaceKernelAuth(eataLiveSctagCertVo.getCertId(), eataLiveSctagCertVo.getName(),eataLiveSctagCertVo.getCallSystem(), eataLiveSctagCertVo.getFunctionModule(),
                                                                      liveOrderNo,String.valueOf(riskRating),faceCompareResult.getResponseCode(),String.valueOf(similarity1),creditCode,eataLiveSctagCertVo.getRequestTime(),
                                                                      eataLiveSctagCertVo.getCreditSwiftNumber(), eataLiveSctagCertVo.getLivePicUrl());
                            resp.setIsActive(CommenConstant.VALID);
                            resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_SUCCESS_000000.getCode());
                            resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_SUCCESS_000000.getMag());
                            long methodEndTime = System.currentTimeMillis();
                            logger.info("[EataLiveSctagCertServiceImpl.faceKernelAuth平安壹帐通认证方法end]" + "[根据唯一标识,身份证号,姓名,来源系统,业务功能模块,base64图片编码,请求时间,风险等级,平安壹帐通认证成功！]" +
                                    "[方法耗时:" + (methodEndTime - methodStartTime) / 1000F + "秒,认证入库成功]"+faceKernelAuth);
                        }else{
                            int faceKernelAuth = insertFaceKernelAuth(eataLiveSctagCertVo.getCertId(), eataLiveSctagCertVo.getName(),eataLiveSctagCertVo.getCallSystem(), eataLiveSctagCertVo.getFunctionModule(),
                                    liveOrderNo,String.valueOf(riskRating),faceCompareResult.getResponseCode(),String.valueOf(similarity1),creditCode,eataLiveSctagCertVo.getRequestTime(),
                                    eataLiveSctagCertVo.getCreditSwiftNumber(), eataLiveSctagCertVo.getLivePicUrl());
                            resp.setIsActive(CommenConstant.INVALID);
                            resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_999901.getCode());
                            resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_999901.getMag());
                            logger.info("[EataLiveSctagCertServiceImpl.faceKernelAuth方法end]" + "[根据唯一标识,身份证号,姓名,来源系统,业务功能模块,base64图片编码,请求时间,风险等级,人脸检测失败，照片质量检测不通过!"+"人脸认证异常记录入库成功"+faceKernelAuth+"]");
                        }
                    }
                }
            } else {
                resp.setIsActive(CommenConstant.INVALID);
                resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000012.getCode());
                resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000012.getMag());
                long methodEndTime = System.currentTimeMillis();
                logger.info("[EataLiveSctagCertServiceImpl.faceKernelAuth方法end]" + "[根据唯一标识,身份证号,姓名,来源系统,业务功能模块,base64图片编码,请求时间,风险等级,认证比对失败！]" +
                        "[方法耗时:" + (methodEndTime - methodStartTime) / 1000F + "秒]" + "[返回值:高级认证失败，参数缺失！" + "]");
            }
        } catch (Exception e) {
            String responseCode = faceCompareResult.getResponseCode();
            if(responseCode.equals(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000016.getCode())){
                resp.setIsActive(CommenConstant.INVALID);
                resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000016.getCode());
                resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000016.getMag());
            }else if(responseCode.equals(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000017.getCode())
                    ||faceCompareResult.getResponseCode().equals(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000032.getCode())
                    ||faceCompareResult.getResponseCode().equals(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000057.getCode())
                    ||faceCompareResult.getResponseCode().equals(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000058.getCode())
                    ||faceCompareResult.getResponseCode().equals(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000059.getCode())){
                resp.setIsActive(CommenConstant.INVALID);
                resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000017.getCode());
                resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000017.getMag());
            }else if(responseCode.equals(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000023.getCode())){
                resp.setIsActive(CommenConstant.INVALID);
                resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000023.getCode());
                resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000023.getMag());
            }else if(responseCode.equals(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000025.getCode())
                    ||faceCompareResult.getResponseCode().equals(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000028.getCode())){
                resp.setIsActive(CommenConstant.INVALID);
                resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000025.getCode());
                resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000025.getMag());
            }else if(responseCode.equals(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000029.getCode())){
                resp.setIsActive(CommenConstant.INVALID);
                resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000029.getCode());
                resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000029.getMag());
            }else if(responseCode.equals(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000065.getCode())
                    ||faceCompareResult.getResponseCode().equals(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000077.getCode())
                    ||faceCompareResult.getResponseCode().equals(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000078.getCode())){
                resp.setIsActive(CommenConstant.INVALID);
                resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000065.getCode());
                resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000065.getMag());
            }else if(responseCode.equals(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000074.getCode())){
                resp.setIsActive(CommenConstant.INVALID);
                resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000074.getCode());
                resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000074.getMag());
            }else if(responseCode.equals(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_999901.getCode())){
                resp.setIsActive(CommenConstant.INVALID);
                resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_999901.getCode());
                resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_999901.getMag());
            }else{
                resp.setIsActive(CommenConstant.INVALID);
                resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_999999.getCode());
                resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_999999.getMag());
            }
            e.printStackTrace();
            Cat.logError(e);
            Cat.logMetricForCount("Redis Exception");
            logger.error("[EataLiveSctagCertServiceImpl.faceKernelAuth方法]" + "[捕获异常:" + e.getMessage() +e+"]");
            Double riskRating = null;//风险等级
            String creditScore = faceCompareResult.getSimilarity();//相似度
            String liveOrderNo = eataLiveSctagCertVo.getCallSystem() + "_" + DateUtil.toSeconds(new Date());
            String creditCode = faceCompareResult.getResponseCode() + "-" + faceCompareResult.getResponseMessage();
            if ("0".equals(eataLiveSctagCertVo.getLiveRate())) {
                riskRating = 0.79;
            } else if ("1".equals(eataLiveSctagCertVo.getLiveRate())) {
                riskRating = 0.85;
            } else if ("2".equals(eataLiveSctagCertVo.getLiveRate())) {
                riskRating = 0.92;
            } else {
                riskRating = 0.79;
            }
            if(StringUtil.isEmpty(creditScore)){
                creditScore = "0.0";
            }
            int faceKernelAuth = insertFaceKernelAuth(eataLiveSctagCertVo.getCertId(), eataLiveSctagCertVo.getName(),eataLiveSctagCertVo.getCallSystem(), eataLiveSctagCertVo.getFunctionModule(),
                    liveOrderNo,String.valueOf(riskRating),faceCompareResult.getResponseCode(),String.valueOf(creditScore),creditCode,eataLiveSctagCertVo.getRequestTime(),
                    eataLiveSctagCertVo.getCreditSwiftNumber(), eataLiveSctagCertVo.getLivePicUrl());
            long methodEndTime = System.currentTimeMillis();
            logger.info("[EataLiveSctagCertServiceImpl.faceKernelAuth方法end]" + "[根据唯一标识,身份证号,姓名,来源系统,业务功能模块,base64图片编码,请求时间,风险等级,认证比对失败！]" +
                    "[方法耗时:" + (methodEndTime - methodStartTime) / 1000F + "秒]" + "[返回值:平安壹账通认证失败！"+faceKernelAuth);
        }
        return resp;
    }
    /**
     *人证比对数据入库
     * @param
     */
    private int insertFaceKernelAuth( String certId,String name,String callSystem, String functionModule, String liveOrderNo,String riskRating, String responseCode,
                                      String similarity1, String creditCode,String RequestTime,String creditSwiftNumber, String livePicUrl) {
        try{
            EataLiveSctagCert eataLiveSctagCert = new EataLiveSctagCert();
            eataLiveSctagCert.setCertId(certId);
            eataLiveSctagCert.setName(name);
            eataLiveSctagCert.setCallSystem(callSystem);
            eataLiveSctagCert.setFunctionModule(functionModule);
            eataLiveSctagCert.setIdentifier("PEACE");
            eataLiveSctagCert.setLiveOrderNo(liveOrderNo);
            eataLiveSctagCert.setLiveRate(String.valueOf(riskRating));//风险等级
            eataLiveSctagCert.setLiveCode(responseCode);
            eataLiveSctagCert.setCreditScore(String.valueOf(similarity1));//相似度（照片公安网比对分数）
            eataLiveSctagCert.setCreditCode(creditCode);
            eataLiveSctagCert.setCreateTime(DateUtil.toSeconds(new Date()));
            eataLiveSctagCert.setRequestTime(RequestTime);
            eataLiveSctagCert.setCreditSwiftNumber(creditSwiftNumber);
            eataLiveSctagCert.setLivePicUrl(livePicUrl);
            int insert = eataLiveSctagCertMapper.insert(eataLiveSctagCert);
            return insert;
        }catch (Exception e){
            logger.error("平安壹账通认证记录入库异常：" + e);
            return 0;
        }
    }

    /**
     * 慧眼 人脸核身
     * 第一步 用 WBAPPID 和 SECRET 调用腾讯服务换取access_token （GET请求）
     * @param WBAPPID
     * @param SECRET
     * @return facekernelMsg_token
     */
    public String getFaceKernel_AccessToken(String WBAPPID,String SECRET,String callSystem){
        String accessToken_facekernel = null ;
        try{
            //accessToken_facekernel=redisUtil.get(GlobalConstant.EATA_SECRET+callSystem).toString();
            if(StringUtil.isEmpty(accessToken_facekernel)) {
                HashMap<String, Object> tempMap = new HashMap<>();
                tempMap.put("app_id", WBAPPID);
                tempMap.put("secret", SECRET);
                tempMap.put("grant_type", "client_credential");
                tempMap.put("version", CommenConstant.VERSION);
                logger.info("1.1===============慧眼人脸核身第一步请求参数：" + JSON.toJSON(tempMap) + "，调用地址：" + CommenConstant.ACCESS_TOKEN_URL);
                String accessToken_service = HttpInvokerUtils.sendGet_FaceKernel(CommenConstant.ACCESS_TOKEN_URL, tempMap, true);
                FacekernelMsg_AccessToken facekernelMsg_token = JSON.parseObject(accessToken_service, FacekernelMsg_AccessToken.class);
                logger.info("1.2===============慧眼人脸核身第一步返回参数：facekernelMsg_token={}", JSON.toJSON(facekernelMsg_token));
                if(facekernelMsg_token.getCode().equals("0")){
                    accessToken_facekernel = facekernelMsg_token.getAccess_token();
                    //redisUtil.set(accessToken_facekernel, JsonUtils.objectToJson(tempMap) ,1800000L);
                }
            }else{
                logger.info("1.2.1===============慧眼人脸核身第一步从redis，6库返回参数：accessToken_facekernel={}",accessToken_facekernel);
            }
        }catch (Exception e){
            accessToken_facekernel = null ;
            logger.info("1.3===============慧眼人脸核身第一步请求失败:" + e);
        }
        return accessToken_facekernel ;
    }


    /**
     * 慧眼 人脸核身
     * 第二步 换取ticket  （GET请求）
     * @param facekernelMsg_accessToken
     * @param WBAPPID
     * @return facekernelMsg_tickets
     */
    public FacekernelMsg_Tickets getFaceKernel_Ticket(String facekernelMsg_accessToken , String WBAPPID){
        FacekernelMsg_Tickets facekernelMsg_tickets = null ;
        try{
            logger.info("2.1===============慧眼人脸核身第二步请求参数：facekernelMsg_token={},WBAPPID={},URL={}",
                    JSON.toJSON(facekernelMsg_accessToken), WBAPPID, CommenConstant.TICKET_URL);
            HashMap<String,Object> tempMap = new HashMap<>() ;
            tempMap.put("app_id",WBAPPID);
            tempMap.put("access_token",facekernelMsg_accessToken);
            tempMap.put("type","SIGN");
            tempMap.put("version", CommenConstant.VERSION);
            String sign_ticket_service = HttpInvokerUtils.sendGet_FaceKernel(CommenConstant.TICKET_URL, tempMap, true);
            String replaceTemp = sign_ticket_service.replace("[","").replace("]", "");
            facekernelMsg_tickets = JSON.parseObject(replaceTemp, FacekernelMsg_Tickets.class);
            logger.info("2.2===============慧眼人脸核身第二步返回参数：facekernelMsg_tickets={}",JSON.toJSON(facekernelMsg_tickets));
        }catch (Exception e){
            facekernelMsg_tickets = null ;
            logger.info("2.3===============慧眼人脸核身第二步返回错误:" + e );
        }

        return facekernelMsg_tickets;
    }

    /**
     * 慧眼 人脸核身
     * 第三步 请求接口
     * @param paramsMap
     * @param url 请求地址
     * @return
     */
    public String sendFaceKernel_Request(HashMap<String,String> paramsMap,String url){
        try{
            String JsonParamsMaps = JSON.toJSONString(paramsMap) ;
            logger.info("3.1===============上送慧眼请求地址={}==========上送慧眼参数={}" ,url, JsonParamsMaps) ;
            String jsonOutStr = HttpInvokerUtils.sendPostBody(url, JsonParamsMaps, true);
            logger.info("3.2===============慧眼返回参数:" + jsonOutStr) ;
            return jsonOutStr;
        }catch (Exception e){
            logger.info("3.3===============慧眼请求失败:" + e) ;
            return null;
        }
    }

    /**
     * 封装 慧眼请求参数
     * @param webankAppId 请添加小助手微信 faceid001，进行线下对接获取
     * @param nonce 32位随机数
     * @param version 固定值 1.0.0
     * @param sign 生成算法签名（上面方法 getHashSignTicket）
     * @param orderNo  订单号，由合作方上送，每次唯一
     * @param name 二要素之一 姓名
     * @param idNo 二要素之一 证件号码
     * @param photoStr 照片文件，注意：原始图片不能超过2MB，且必须为 JPG 或 PNG 格式 Base64 String
     * @param photoType 比对源照片类型：
     *                    参数值为 1 时：photoStr 为身份证正面照，逻辑处理会去抠身份证头像后再去比对
     *                    参数值为 null 或其他：photoStr 为其他类型的照片，直接使用上送的图片去做比对
     * @return
     */
    public HashMap<String,String> faceKernelParams(String webankAppId,String nonce,String version,String sign,String orderNo,
                                                   String name,String idNo ,String photoStr,String photoType){
        HashMap<String,String> paramsMap = new HashMap<>() ;
        paramsMap.put("webankAppId",webankAppId);
        paramsMap.put("nonce",nonce);
        paramsMap.put("version",version);
        paramsMap.put("sign",sign);
        paramsMap.put("orderNo",orderNo);
        paramsMap.put("name",name);
        paramsMap.put("idNo", idNo);
        //paramsMap.put("userId", userId);
        paramsMap.put("photoStr", photoStr);
        paramsMap.put("photoType", photoType);
        return paramsMap;
    }

    /**
     * 慧眼 人脸核身
     * 第三步 获得加密后的签名串
     * @param orderNo 随机字符 （和下一步请求保持一致）
     * @param nonce uuid （和下一步请求保持一致）
     * @param ticket
     * @return
     */
    public String getHashSignTicket(String ticket,String orderNo, String nonce){
        ArrayList<String> values = new ArrayList<>() ;
        values.add(CommenConstant.WBAPPID) ;
        values.add(orderNo);
        values.add(nonce);
        values.add(CommenConstant.VERSION);
        String hashSign = this.sign(values,ticket) ;
        logger.info("3.0===============慧眼人脸核身第三步加密后的签名={},长度={}",hashSign,hashSign.length());
        return hashSign;
    }

    /**
     * 加密算法
     * @param values
     * @param ticket
     * @return
     */
    public String sign(List<String> values, String ticket) {
        if (values == null) {
            throw new NullPointerException("values is null");
        }
        values.removeAll(Collections.singleton(null));// remove null
        values.add(ticket);
        Collections.sort(values);
        logger.info("排序后：" + values);
        StringBuilder sb = new StringBuilder();
        for (String s : values) {
            sb.append(s);
        }
        logger.info("拼接后：" + sb);
        return Hashing.sha1().hashString(sb, Charsets.UTF_8).toString().toUpperCase();
    }


}
