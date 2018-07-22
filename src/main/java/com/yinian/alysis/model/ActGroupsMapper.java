package com.yinian.alysis.model;

import java.util.List;

public interface ActGroupsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActGroups record);

    int insertSelective(ActGroups record);

    ActGroups selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActGroups record);

    int updateByPrimaryKey(ActGroups record);

    List<ActGroups> findAll();
}