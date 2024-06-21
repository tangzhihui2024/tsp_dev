package com.icbcaxa.eata.service;

import com.icbcaxa.eata.util.Response;
import com.icbcaxa.eata.vo.EataFaceVo;


/**
 * 照片比对 服务
 * Created by gyas-itwb-fsl01 on 2020/6/19.
 */
public interface EataFaceService {

    Response<String> checkPicture(EataFaceVo eataFaceVo);
}
