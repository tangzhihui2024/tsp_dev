package com.icbcaxa.eata.vo;

import io.swagger.annotations.Api;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 身份验证入参 实体类
 * Created by gyas-itwb-fsl01 on 2020/6/19.
 */
@Data
@Api(tags = "EataCardVerificationVo",description = "身份验证入参实体")
public class EataCardVerificationVo implements Serializable{

    public String callSystem;//来源系统

    public String functionModule;//业务功能模块

    public String creditSwiftNumber;//身份验证订单号(调用系统流水号)

    public String cardOrderNo;//唯一标识(身份验证流水号)

    public String realName;//客户姓名

    public String clientCardNo;//客户身份证
}
