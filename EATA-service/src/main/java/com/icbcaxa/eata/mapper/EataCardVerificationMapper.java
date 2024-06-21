package com.icbcaxa.eata.mapper;

import com.icbcaxa.eata.entity.EataCardVerification;
import org.apache.ibatis.annotations.Mapper;

/**
 * 身份验证 接口
 * Created by gyas-itwb-fsl01 on 2020/7/15.
 */
@Mapper
public interface EataCardVerificationMapper {

    int insert(EataCardVerification eataCardVerification);
}
