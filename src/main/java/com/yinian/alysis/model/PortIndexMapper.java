package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface PortIndexMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PortIndex record);

    int insertSelective(PortIndex record);

    PortIndex selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PortIndex record);

    int updateByPrimaryKey(PortIndex record);

    List<PortIndex> findAll();
    
    List<Map<String, Object>> listAllportIndex();
}