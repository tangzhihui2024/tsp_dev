package com.icbcaxa.eata.mapper;

import com.icbcaxa.eata.entity.EataFace;
import org.apache.ibatis.annotations.Mapper;

/**
 * 人证比对 接口
 * Created by gyas-itwb-fsl01 on 2020/6/22.
 */
@Mapper
public interface EataFaceMapper {

    int insert(EataFace eataFace);
}
