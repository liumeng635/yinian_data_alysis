package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface ActTargetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActTarget record);

    int insertSelective(ActTarget record);

    ActTarget selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActTarget record);

    int updateByPrimaryKey(ActTarget record);

    List<ActTarget> findAll();
    
    List<ActTarget> findByMapParam(Map<String,Object> params);
    
    List<Map<String,Object>> actlineData(Map<String,Object> params);
}