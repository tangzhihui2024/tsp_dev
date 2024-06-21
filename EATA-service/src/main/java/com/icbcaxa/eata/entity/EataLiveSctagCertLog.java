package com.icbcaxa.eata.entity;

import java.io.Serializable;


/**
 * Created by gyas-itwb-fsl01 on 2020/4/23.
 */
public class EataLiveSctagCertLog implements Serializable{

    public String id;

    public String certId;//身份证号

    public String name; //姓名

    public String callSystem;//来源系统

    public String functionModule;//业务功能模块

    public String identifier;//功能标识

    public String liveOrderNo;//活体检测唯一标识

    public String liveRate;//风险等级

    public String liveCode;//活体状态

    public String creditScore;//相似度

    public String creditCode;//比对的code

    public String createTime;//创建时间

    public String requestTime;//请求时间

    public String creditSwiftNumber;//唯一标识

    public String livePicUrl;//图片路径

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCallSystem() {
        return callSystem;
    }

    public void setCallSystem(String callSystem) {
        this.callSystem = callSystem;
    }

    public String getFunctionModule() {
        return functionModule;
    }

    public void setFunctionModule(String functionModule) {
        this.functionModule = functionModule;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getLiveOrderNo() {
        return liveOrderNo;
    }

    public void setLiveOrderNo(String liveOrderNo) {
        this.liveOrderNo = liveOrderNo;
    }

    public String getLiveRate() {
        return liveRate;
    }

    public void setLiveRate(String liveRate) {
        this.liveRate = liveRate;
    }

    public String getLiveCode() {
        return liveCode;
    }

    public void setLiveCode(String liveCode) {
        this.liveCode = liveCode;
    }

    public String getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getCreditSwiftNumber() {
        return creditSwiftNumber;
    }

    public void setCreditSwiftNumber(String creditSwiftNumber) {
        this.creditSwiftNumber = creditSwiftNumber;
    }

    public String getLivePicUrl() {
        return livePicUrl;
    }

    public void setLivePicUrl(String livePicUrl) {
        this.livePicUrl = livePicUrl;
    }

   /* public EataLiveSctagCertLog(String id,String certId, String name, String callSystem, String functionModule, String identifier, String liveOrderNo, String liveRate, String liveCode, String creditScore, String creditCode, String createTime, String requestTime, String creditSwiftNumber, String livePicUrl) {
        this.id = id;
        this.certId = certId;
        this.name = name;
        this.callSystem = callSystem;
        this.functionModule = functionModule;
        this.identifier = identifier;
        this.liveOrderNo = liveOrderNo;
        this.liveRate = liveRate;
        this.liveCode = liveCode;
        this.creditScore = creditScore;
        this.creditCode = creditCode;
        this.createTime = createTime;
        this.requestTime = requestTime;
        this.creditSwiftNumber = creditSwiftNumber;
        this.livePicUrl = livePicUrl;
    }*/
}
