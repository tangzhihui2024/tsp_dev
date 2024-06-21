package com.icbcaxa.eata.mapper;

import com.icbcaxa.eata.entity.Account;
import com.icbcaxa.eata.entity.EataLiveSctagCert;
import com.icbcaxa.eata.vo.EataLiveSctagCertVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    Account listTest(String accountName);
}