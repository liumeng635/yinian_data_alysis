package com.yinian.alysis.model;

import java.util.List;

public interface PortTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PortType record);

    int insertSelective(PortType record);

    PortType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PortType record);

    int updateByPrimaryKey(PortType record);

    List<PortType> findAll();
}