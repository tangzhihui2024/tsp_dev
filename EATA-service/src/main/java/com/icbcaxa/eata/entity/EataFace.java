package com.icbcaxa.eata.entity;

import io.swagger.annotations.Api;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 照片比对 实体类
 *
 * Created by gyas-itwb-fsl01 on 2020/6/19.
 */
@Data
@Api(tags = "EataFace",description = "照片比对")
public class EataFace implements Serializable {

    public Long id;

    public String channel;

    public String trCode;

    public String appName;

    public String appInfo;

    public Double sim;

    public Double defaultSim;

    public String faceCode;

    public Date createTime;

    public String img1;

    public String img2;

    public String remark1;

    public String remark2;

    public String remark3;


}
