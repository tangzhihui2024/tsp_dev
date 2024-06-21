package com.icbcaxa.eata.web.controller;

import com.icbcaxa.eata.service.EataCardVerificationService;
import com.icbcaxa.eata.util.Response;
import com.icbcaxa.eata.vo.EataCardVerificationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 身份验证 访问层
 * Created by gyas-itwb-fsl01 on 2020/7/15.
 */
@RestController
@RequestMapping("eata/card")
public class EataCardVerificationController {

    @Autowired
    private EataCardVerificationService eataCardVerificationServiceImpl;

    /**
     * 身份验证（平安交互）
     * @param eataCardVerificationVo  身份验证实体
     * @return
     */
    @RequestMapping(value = "/verification",produces = { "application/json;charset=utf-8" },method = RequestMethod.POST)
    public Response<String> verification(@RequestBody EataCardVerificationVo eataCardVerificationVo) throws Exception {
        return eataCardVerificationServiceImpl.verification(eataCardVerificationVo);
    }
}
