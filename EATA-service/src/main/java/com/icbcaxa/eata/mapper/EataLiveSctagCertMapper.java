package com.icbcaxa.eata.mapper;

import com.icbcaxa.eata.entity.EataLiveSctagCert;
import com.icbcaxa.eata.entity.EataLiveSctagCertLog;
import com.icbcaxa.eata.vo.EataLiveSctagCertVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by gyas-itwb-fsl01 on 2020/3/20.
 */
@Mapper
public interface EataLiveSctagCertMapper {

    int insert(EataLiveSctagCert eataLiveSctagCert);
}
