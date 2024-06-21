package com.icbcaxa.eata.service.impl;

import com.dianping.cat.Cat;
import com.icbc.api.internal.util.internal.util.fastjson.JSONObject;
import com.icbcaxa.eata.entity.EataCardVerification;
import com.icbcaxa.eata.entity.EataLiveSctagCertEnum;
import com.icbcaxa.eata.jryzt.ai.pojo.dto.request.IdcomparisonV6Request;
import com.icbcaxa.eata.jryzt.ai.pojo.dto.result.FaceCompareResult;
import com.icbcaxa.eata.jryzt.ai.utils.string.StringUtil;
import com.icbcaxa.eata.jryzt.sample.IdComparisonV6Sample;
import com.icbcaxa.eata.mapper.EataCardVerificationMapper;
import com.icbcaxa.eata.service.EataCardVerificationService;
import com.icbcaxa.eata.util.Response;
import com.icbcaxa.eata.util.date.DateUtil;
import com.icbcaxa.eata.vo.EataCardVerificationVo;
import com.icbcaxa.eata.web.controller.EataLiveSctagCertController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 身份验证 接口实现类
 * Created by gyas-itwb-fsl01 on 2020/7/15.
 */
@Service
public class EataCardVerificationServiceImpl implements EataCardVerificationService {

    private static final Logger logger = LoggerFactory.getLogger(EataLiveSctagCertController.class);

    @Autowired
    private EataCardVerificationMapper eataCardVerificationMapper;

    @Override
    public Response<String> verification(EataCardVerificationVo eataCardVerificationVo) {
        long methodStartTime = System.currentTimeMillis();
        logger.info("[EataCardVerificationServiceImpl.verification方法start]" + "[根据客户姓名,客户身份证号]" +
                "[入参:" + "realName =" + eataCardVerificationVo.getRealName() + ",clientCardNo =" + eataCardVerificationVo.getClientCardNo()+ "]");
        Response<String> resp = new Response<>(EataLiveSctagCertEnum.GYAS_LANCER_SUCCESS_000000.getCode(), "");
        FaceCompareResult faceCompareResult = null;
        String cardOrderNo = eataCardVerificationVo.getCallSystem() + "_" + DateUtil.toSeconds(new Date());
        try{
            if (!StringUtils.isEmpty(eataCardVerificationVo.getCreditSwiftNumber())&&!StringUtils.isEmpty(eataCardVerificationVo.getRealName())
                    && !StringUtils.isEmpty(eataCardVerificationVo.getClientCardNo()) && !StringUtils.isEmpty(eataCardVerificationVo.getCallSystem())
                    && !StringUtil.isEmpty(eataCardVerificationVo.getFunctionModule())) {
                //第一步调用壹帐通
                IdcomparisonV6Request idcomparisonV6Request = new IdcomparisonV6Request();
                idcomparisonV6Request.setRealName(eataCardVerificationVo.getRealName());
                idcomparisonV6Request.setClientCardNo(eataCardVerificationVo.getClientCardNo());
                //logger.info("平安一账通getParams={}" + JSONObject.toJSONString(idcomparisonV6Request));
                faceCompareResult = IdComparisonV6Sample.IdVerificationB(idcomparisonV6Request);
                logger.info("平安一账通faceCompareResult={}" + JSONObject.toJSONString(faceCompareResult));
                if (!StringUtils.isEmpty(faceCompareResult)) {
                    if (faceCompareResult.getResponseCode().equals(EataLiveSctagCertEnum.GYAS_LANCER_SUCCESS_000000.getCode())) {
                        int insertVerification = insertVerification(eataCardVerificationVo.getCreditSwiftNumber(),eataCardVerificationVo.getCallSystem(),
                                eataCardVerificationVo.getFunctionModule(),eataCardVerificationVo.getRealName(), eataCardVerificationVo.getClientCardNo(),
                                cardOrderNo,faceCompareResult.getResponseCode(), faceCompareResult.getResponseMessage());
                        resp.setCode(faceCompareResult.getResponseCode());
                        resp.setMessage(faceCompareResult.getResponseMessage());
                        long methodEndTime = System.currentTimeMillis();
                        logger.info("[EataCardVerificationServiceImpl.verification平安壹帐通身份验证方法end]" + "[根据客户姓名,客户身份证号,平安壹帐通身份验证成功！]" +
                                "[方法耗时:" + (methodEndTime - methodStartTime) / 1000F + "秒,身份验证入库成功]"+insertVerification);
                    } else {
                        int insertVerification = insertVerification(eataCardVerificationVo.getCreditSwiftNumber(),eataCardVerificationVo.getCallSystem(),
                                eataCardVerificationVo.getFunctionModule(),eataCardVerificationVo.getRealName(), eataCardVerificationVo.getClientCardNo(),
                                cardOrderNo,faceCompareResult.getResponseCode(), faceCompareResult.getResponseMessage());
                        resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000028.getCode());
                        resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000028.getMag());
                        logger.info("EataCardVerificationServiceImpl.verification方法end]" + "[根据客户姓名,客户身份证号,身份验证未通过!"+"身份验证异常入库成功]"+insertVerification);
                    }
                }
            }else{
                resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000012.getCode());
                resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000012.getMag());
                long methodEndTime = System.currentTimeMillis();
                logger.info("[EataCardVerificationServiceImpl.verification方法end]" + "[根据客户姓名,客户身份证号,身份验证失败！]" +
                        "[方法耗时:" + (methodEndTime - methodStartTime) / 1000F + "秒]"+ "[返回值:身份验证失败，参数缺失！" + "]");
            }
        }catch (Exception e){
            String responseCode = faceCompareResult.getResponseCode();
            if(responseCode.equals(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_910001.getCode())){
                resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_910001.getCode());
                resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_910001.getMag());
            }else if(responseCode.equals(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000099.getCode())){
                resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000099.getCode());
                resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000099.getMag());
            }else {
                resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_999999.getCode());
                resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_999999.getMag());
            }
            e.printStackTrace();
            Cat.logError(e);
            Cat.logMetricForCount("Redis Exception");
            logger.error("[EataCardVerificationServiceImpl.verification方法]" + "[捕获异常:" + e.getMessage() +e+ "]");
            String resultCode = faceCompareResult.getResponseCode();
            if(StringUtil.isEmpty(resultCode)){
                resultCode = "999999";
            }
            String resultMes = faceCompareResult.getResponseMessage();
            if(StringUtil.isEmpty(resultMes)){
                resultMes = "系统繁忙，请稍候再试";
            }
            int insertVerificationLog = insertVerification(eataCardVerificationVo.getCreditSwiftNumber(),eataCardVerificationVo.getCallSystem(),
                    eataCardVerificationVo.getFunctionModule(),eataCardVerificationVo.getRealName(), eataCardVerificationVo.getClientCardNo(),
                    cardOrderNo,resultCode,resultMes);
            logger.info("[EataCardVerificationServiceImpl.verification方法end 平安壹账通身份验证异常入库]"+insertVerificationLog);
            long methodEndTime = System.currentTimeMillis();
            logger.info("[EataCardVerificationServiceImpl.verification方法end]" + "[根据客户姓名,客户身份证号,身份验证失败！]" +
                    "[方法耗时:" + (methodEndTime - methodStartTime) / 1000F + "秒]");
        }
        return resp;
    }

    private int insertVerification(String creditSwiftNumber,String callSystem,String functionModule,String realName,String clientCardNo,
                                   String cardOrderNo,String responseCode,String responseMessage) {
        try{
            EataCardVerification eataCardVerification = new EataCardVerification();
            eataCardVerification.setCreditSwiftNumber(creditSwiftNumber);
            eataCardVerification.setCallSystem(callSystem);
            eataCardVerification.setFunctionModule(functionModule);
            eataCardVerification.setIdentifier("PEACE");
            eataCardVerification.setRealName(realName);
            eataCardVerification.setClientCardNo(clientCardNo);
            eataCardVerification.setCardOrderNo(cardOrderNo);
            eataCardVerification.setResponseCode(responseCode);
            eataCardVerification.setResponseMessage(responseMessage);
            eataCardVerification.setCreateTime(new Date());
            int insert = eataCardVerificationMapper.insert(eataCardVerification);
            return insert;
        }catch (Exception e){
            logger.error("身份验证记录入库异常：" + e);
            return 0;
        }
    }
}
