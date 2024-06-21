package com.icbcaxa.eata.service;

import com.icbcaxa.eata.util.Response;
import com.icbcaxa.eata.vo.EataCardVerificationVo;

/**
 * 身份验证 接口
 * Created by gyas-itwb-fsl01 on 2020/7/15.
 */
public interface EataCardVerificationService {
    Response<String> verification(EataCardVerificationVo eataCardVerificationVo);
}
