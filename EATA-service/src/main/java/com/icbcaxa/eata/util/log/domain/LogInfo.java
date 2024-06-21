package com.icbcaxa.eata.util.log.domain;

import lombok.Data;

@Data
public class LogInfo {

    private String system;//系统名称/版本

    private String createUser;//操作用户（可选）

    private String serviceTypeAndGUID;//业务类型+业务流水号  中间用空格隔开

    private String module;//功能模块

    private String function;//功能（方法）

    private Object[] obj;//参数对象（可选）

    private String errorMsg;//错误信息（可选）
 }
