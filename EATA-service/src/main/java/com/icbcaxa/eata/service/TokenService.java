package com.icbcaxa.eata.service;


import com.icbcaxa.eata.util.Response;
import com.icbcaxa.eata.vo.TokenVO;

/**
 * Created by tangzh on 2018/5/30.
 */
public interface TokenService {

    Response<String> generate(TokenVO tokenVO);

    Response<String> validate(String refGUID, String userToken);

    Response<String> refresh(String refGUID, String userToken);

    Response<String> cancel(String refGUID, String userToken);

}
