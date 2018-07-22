package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface GroupEventcutMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupEventcut record);

    int insertSelective(GroupEventcut record);

    GroupEventcut selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupEventcut record);

    int updateByPrimaryKey(GroupEventcut record);

    List<GroupEventcut> findAll();
    
    List<GroupEventcut> findByMapParam(Map<String, Object> params);
}