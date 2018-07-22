package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface PushAnalysisMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PushAnalysis record);

    int insertSelective(PushAnalysis record);

    PushAnalysis selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PushAnalysis record);

    int updateByPrimaryKey(PushAnalysis record);
    
    List<PushAnalysis> findByMapParam(Map<String,Object> params);
    
    List<Map<String,Object>> listAllDate();
    List<Map<String,Object>> listAllInvite(Map<String,Object> params);
    List<Map<String,Object>> listAllUser();
    void insertUserIn(Map<String, Object> params);
}