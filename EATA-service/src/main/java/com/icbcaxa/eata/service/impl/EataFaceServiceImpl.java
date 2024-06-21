package com.icbcaxa.eata.service.impl;

import com.dianping.cat.Cat;
import com.icbcaxa.eata.entity.EataFace;
import com.icbcaxa.eata.entity.EataLiveSctagCertEnum;
import com.icbcaxa.eata.mapper.EataFaceMapper;
import com.icbcaxa.eata.redis.FaceUtil;
import com.icbcaxa.eata.service.EataFaceService;
import com.icbcaxa.eata.util.Response;
import com.icbcaxa.eata.util.constant.CommenConstant;
import com.icbcaxa.eata.vo.EataFaceVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 照片比对 服务实现类
 * Created by gyas-itwb-fsl01 on 2020/6/19.
 */
@Service
public class EataFaceServiceImpl implements EataFaceService{

    private static final Logger logger = LoggerFactory.getLogger(EataFaceServiceImpl.class);

    @Autowired
    FaceUtil faceUtil;

    @Autowired
    EataFaceMapper eataFaceMapper;

    @Override
    public  Response<String> checkPicture(EataFaceVo eataFaceVo) {
        logger.info("[EataFaceServiceImpl.checkPicture人证比对方法start]" + "[根据业务渠道编号,交易编号,应用名,客户端APP信息,img1编码,img2编码]" +
                "[入参:" + "channel =" + eataFaceVo.getChannel() + ",trCode =" + eataFaceVo.getTrCode() + ",appName =" + eataFaceVo.getAppName() +
                ",appInfo=" + eataFaceVo.getAppInfo() + ",img1=" + eataFaceVo.getImg1() + "img2 =" + eataFaceVo.getImg2() + "]");
        long methodStartTime = System.currentTimeMillis();
        Response<String> resp=new Response<>(Response.SUCCESS,"人证比对成功");
        try{
            Map<String, Object> map = new HashMap<>();
            Boolean flag=true;
            if(null == eataFaceVo.getImg1() && "".equals(eataFaceVo.getImg1()) && null == eataFaceVo.getImg2() && "".equals(eataFaceVo.getImg2())){
                flag =false;
                resp.setIsTrue(flag);
                resp.setCode(CommenConstant.CUSTOMER_AGREEMENT);
                resp.setMessage("没有认证信息,请重试！");
            }else{
                map.put("img1", eataFaceVo.getImg1());
                map.put("img2", eataFaceVo.getImg2());
            }
            //调用人脸比较接口
            Map<String, Object> faceMap =null;
            Double faceSim=0.0;
            Double defaultSim=CommenConstant.DEFAULT_SIM;//系统标准相似度
           // String trCode =UUID.randomUUID().toString().replace("-","");
            if(flag){
                map.put("channel",eataFaceVo.getChannel());//业务渠道编号
                map.put("trCode", eataFaceVo.getTrCode());//交易编号
                map.put("appName",eataFaceVo.getAppName());//应用名
                map.put("appInfo",eataFaceVo.getAppInfo());//客户端APP信息
                faceMap = faceUtil.getCheckPicture(map);
                String returnCode = String.valueOf(faceMap.get("return_code"));
                String returnMsg = String.valueOf( faceMap.get("return_msg")) ;
                Object sim = faceMap.get("sim");
                if(!CommenConstant.STRING_ZERO.equals(returnCode) || null == sim){
                    flag=false;
                    resp.setIsTrue(flag);
                    resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000028.getCode());
                    resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000028.getMag());
                    returnMsg = "认证未通过";
                    //人证比对异常记录入库
                    int insertFace = insertFace(eataFaceVo.getChannel(), eataFaceVo.getTrCode(), eataFaceVo.getAppName(), eataFaceVo.getAppInfo(),
                            faceSim, defaultSim, returnCode,returnMsg, eataFaceVo.getImg1(), eataFaceVo.getImg2());
                    logger.info("[EataFaceServiceImpl.insertFace方法end 人证比对认证未通过记录入库成功]"+insertFace);
                }else{
                    faceSim = Double.parseDouble(String.valueOf(sim));//人脸准确度
                    boolean faceFlag = faceSim < defaultSim;
                    if (faceFlag) { //比对人脸相似度
                        flag=false;
                        resp.setIsTrue(flag);
                        resp.setCode(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000028.getCode());
                        resp.setMessage(EataLiveSctagCertEnum.GYAS_LANCER_ERROR_000028.getMag());
                        returnMsg = "认证未通过";
                        //人证比对异常记录入库
                        int insertFace1 = insertFace(eataFaceVo.getChannel(), eataFaceVo.getTrCode(), eataFaceVo.getAppName(), eataFaceVo.getAppInfo(),
                                faceSim, defaultSim, returnCode,returnMsg, eataFaceVo.getImg1(), eataFaceVo.getImg2());
                        logger.info("[EataFaceServiceImpl.insertFace1方法end 人证比对未通过记录入库成功]"+insertFace1);
                    }else{
                        resp.setIsTrue(flag);
                        resp.setCode(returnCode);
                        resp.setMessage(returnMsg);
                        resp.setSim(faceSim);
                        resp.setDefaultSim(defaultSim);//系统标准相似度
                        //人证比对记录入库
                        int insertFace = insertFace(eataFaceVo.getChannel(), eataFaceVo.getTrCode(), eataFaceVo.getAppName(), eataFaceVo.getAppInfo(),
                                faceSim, defaultSim, returnCode,returnMsg, eataFaceVo.getImg1(), eataFaceVo.getImg2());
                        logger.info("[EataFaceServiceImpl.insertFace方法end 人证比对入库成功]"+insertFace);
                    }
                    Long methodEndTime = System.currentTimeMillis();
                    logger.info("[EataFaceServiceImpl.checkPicture人证比对方法end]" + "[根据业务渠道编号,交易编号,应用名,客户端APP信息,img1编码,img2编码,工行人证成功！]" +
                            "[方法耗时:" + (methodEndTime - methodStartTime) / 1000F + "秒]"+"[返回值:"+"相似度="+sim+"返回编码="+returnCode+"相似度阈值="+faceSim+"]");
                }
            }
        }catch (Exception e){
            boolean isTrue = false;
            Double faceSim =0.0;
            Double defaultSim =0.0;
            String returnCode ="1";
            String returnMag = "服务处理失败";
            Cat.logError(e);
            Cat.logMetricForCount("Sql Exception");
            Cat.logMetricForCount("AccessToken Validation Failed");
            e.printStackTrace();
            resp.setIsTrue(isTrue);
            resp.setCode(returnCode);
            resp.setMessage(returnMag);
            logger.error("[EataFaceServiceImpl.checkPicture方法]"+"[捕获异常:"+e.getMessage()+"]",e);
            int insertFace = insertFace(eataFaceVo.getChannel(), eataFaceVo.getTrCode(), eataFaceVo.getAppName(), eataFaceVo.getAppInfo(),
                    faceSim, defaultSim, returnCode,returnMag, eataFaceVo.getImg1(), eataFaceVo.getImg2());
            logger.info("[EataFaceServiceImpl.insertFace方法end 人证比对异常数据入库]"+insertFace);
        }
        return resp;
    }

    private int insertFace(String channel,String trCode,String appName,String appInfo,Double faceSim,Double defaultSim,String returnCode,String returnMsg,String img1,String img2) {
        try{
            EataFace eataFace = new EataFace();
            eataFace.setChannel(channel);
            eataFace.setTrCode(trCode);
            eataFace.setAppName(appName);
            eataFace.setAppInfo(appInfo);
            eataFace.setSim(faceSim);
            eataFace.setDefaultSim(defaultSim);
            eataFace.setFaceCode(returnCode);
            eataFace.setRemark1(returnMsg);
            eataFace.setCreateTime(new Date());
            eataFace.setImg1(img1);
            eataFace.setImg2(img2);
            int insert = eataFaceMapper.insert(eataFace);//人证比对记录入库
            return insert;
        }catch (Exception e){
            logger.error("人证比对记录入库异常：" + e);
            return 0;
        }
    }

}
