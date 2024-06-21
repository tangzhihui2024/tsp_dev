package com.icbcaxa.eata.vo;

import io.swagger.annotations.Api;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 照片比对入参 实体类
 * Created by gyas-itwb-fsl01 on 2020/6/19.
 */
@Data
@Api(tags = "EataFaceVo",description = "照片比对入参实体")
public class EataFaceVo implements Serializable{

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
}
