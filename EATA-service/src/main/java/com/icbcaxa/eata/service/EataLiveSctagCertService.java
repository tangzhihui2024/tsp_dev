package com.icbcaxa.eata.service;

import com.icbcaxa.eata.entity.FacekernelMsg_Tickets;
import com.icbcaxa.eata.util.Response;
import com.icbcaxa.eata.vo.EataLiveSctagCertVo;

import java.util.HashMap;

/**
 * Created by gyas-itwb-fsl01 on 2020/3/20.
 */
public interface EataLiveSctagCertService {

     Response<String> faceKernelAuth(EataLiveSctagCertVo eataLiveSctagCertVo);

     String getFaceKernel_AccessToken(String WBAPPID, String SECRET, String callSystem);

     FacekernelMsg_Tickets getFaceKernel_Ticket(String facekernelMsg_accessToken , String WBAPPID);

     String getHashSignTicket(String ticket,String orderNo, String nonce);

     HashMap<String,String> faceKernelParams(String webankAppId,String nonce,String version,String sign,String orderNo,
                                                   String name,String idNo ,String photoStr,String photoType);
     String sendFaceKernel_Request(HashMap<String,String> paramsMap, String url);
}
