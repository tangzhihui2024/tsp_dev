package com.icbcaxa.eata.web.controller;
import com.icbcaxa.eata.service.EataLiveSctagCertService;
import com.icbcaxa.eata.util.Response;
import com.icbcaxa.eata.vo.EataLiveSctagCertVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Tangzh
 * 2020/3/20.
 * 接口文档地址：https://cloud.tencent.com/document/product/1007/35918
 * 人证比对控制层接口
 */
@RestController
@RequestMapping("eata/sctag")
public class EataLiveSctagCertController {

    @Autowired
    private EataLiveSctagCertService eataLiveSctagCertServiceImpl;

    /**
     * 慧眼人脸核身 提交（平安壹账通,腾讯交互）
     * @param eataLiveSctagCertVo  人证比对实体
     * @return
     */
    @RequestMapping(value = "/faceKernelAuth",produces = { "application/json;charset=utf-8" },method = RequestMethod.POST)
    public Response<String> faceKernelAuth(@RequestBody EataLiveSctagCertVo eataLiveSctagCertVo) throws Exception {
        return eataLiveSctagCertServiceImpl.faceKernelAuth(eataLiveSctagCertVo);
    }
}
