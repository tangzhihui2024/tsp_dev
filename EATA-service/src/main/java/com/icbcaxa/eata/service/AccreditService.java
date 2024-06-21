package com.icbcaxa.eata.service;


import com.icbcaxa.eata.util.Response;
import com.icbcaxa.eata.vo.AccreditVO;

/**
 * Created by tangzh on 2018/5/25.
 */
public interface AccreditService {

     Response<String> validate(AccreditVO accreditVO);

     Response<String> refresh(AccreditVO accreditVO);

}
