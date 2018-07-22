package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

import com.yinian.alysis.model.PortTarget;

public interface PortTargetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PortTarget record);

    int insertSelective(PortTarget record);

    PortTarget selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PortTarget record);

    int updateByPrimaryKey(PortTarget record);
    
    List<PortTarget> findTargetByParamMap(Map<String,Object> params);
    
    List<PortTarget> findAll();
}