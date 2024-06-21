package com.icbcaxa.eata.entity;

import io.swagger.annotations.Api;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 身份验证 实体类
 * Created by gyas-itwb-fsl01 on 2020/7/15.
 */
@Data
@Api(tags = "EataCardVerification",description = "身份验证")
public class EataCardVerification implements Serializable {

    public Long id;

    public String callSystem;//来源系统

    public String functionModule;//业务功能模块

    public String identifier;//功能标识

    public String cardOrderNo;//活体检测唯一标识

    public String creditSwiftNumber;//唯一标识

    public String realName;

    public String clientCardNo;

    public String responseCode;

    public String responseMessage;

    public Date createTime;

    public String remark1;

    public String remark2;

    public String remark3;
}