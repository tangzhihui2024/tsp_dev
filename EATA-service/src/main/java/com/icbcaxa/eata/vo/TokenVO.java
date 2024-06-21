package com.icbcaxa.eata.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by tangzh on 2018/5/28.
 *
 * 生成令牌码入参VO类
 */
@Data
public class TokenVO implements Serializable{

    private String userToken;

    private String refGUID;

    private String loginSource;

    private String terminalType;

    private String systemInfo;

    private String loginSystemUserId;

    private String systemTime;

    private String validateTime ;

    private String secretKey;


}
