package com.icbcaxa.eata.web.controller;

import com.icbcaxa.eata.service.EataFaceService;
import com.icbcaxa.eata.util.Response;
import com.icbcaxa.eata.vo.EataFaceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 照片比对 控制层
 *
 * Created by gyas-itwb-fsl01 on 2020/6/19.
 */
@RestController
@RequestMapping("eata/face")
public class EataFaceController {

    @Autowired
    private EataFaceService eataFaceServiceImpl;

    /**
     * 照片比对（工行交互）
     * @param eataFaceVo  照片比对实体
     * @return
     */
    @RequestMapping(value = "/faceKernelAuth",produces = { "application/json;charset=utf-8" },method = RequestMethod.POST)
    public Response<String> faceKernelAuth(@RequestBody EataFaceVo eataFaceVo) throws Exception {
        return eataFaceServiceImpl.checkPicture(eataFaceVo);
    }
}
