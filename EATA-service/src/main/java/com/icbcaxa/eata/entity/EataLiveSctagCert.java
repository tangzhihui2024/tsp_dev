package com.icbcaxa.eata.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * Created by gyas-itwb-fsl01 on 2020/3/20.
 */
public class EataLiveSctagCert implements Serializable {

    public String id;

    public String callSystem;//来源系统

    public String identifier;//功能标识

    public String certId;//身份证号

    public String name; //姓名

    public String creditSwiftNumber;//唯一标识

    public String liveOrderNo;//活体检测唯一标识

    public String livePicUrl;//图片路径

    public String liveRate;//设置相似度

    public String liveCode;//活体状态

    public String creditScore;//相似度

    public String creditCode;//比对的code

    public String remark1;//备份字段1

    public String remark2;//备份字段2

    public String requestTime;//请求时间

    public String functionModule;//业务功能模块

    public String createTime;

    public String updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCallSystem() {
        return callSystem;
    }

    public void setCallSystem(String callSystem) {
        this.callSystem = callSystem;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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

    public String getCreditSwiftNumber() {
        return creditSwiftNumber;
    }

    public void setCreditSwiftNumber(String creditSwiftNumber) {
        this.creditSwiftNumber = creditSwiftNumber;
    }

    public String getLiveOrderNo() {
        return liveOrderNo;
    }

    public void setLiveOrderNo(String liveOrderNo) {
        this.liveOrderNo = liveOrderNo;
    }

    public String getLivePicUrl() {
        return livePicUrl;
    }

    public void setLivePicUrl(String livePicUrl) {
        this.livePicUrl = livePicUrl;
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

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getFunctionModule() {
        return functionModule;
    }

    public void setFunctionModule(String functionModule) {
        this.functionModule = functionModule;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
