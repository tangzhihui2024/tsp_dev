package com.icbcaxa.eata.service;


import com.icbcaxa.eata.util.Response;
import com.icbcaxa.eata.vo.AccreditVO;
import com.icbcaxa.eata.vo.SecretVo;

/**
 * 推送授权码
 */
public interface TokenSendService {
    /**
     * 推送
     * @return
     */
    public Response<String> send(AccreditVO accreditVO);

    /**
     * 生成秘钥
     * @param secretVo
     * @return
     */
    public String saveSecret(SecretVo secretVo);
}
