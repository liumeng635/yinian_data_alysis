package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface GroupPlaycutMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupPlaycut record);

    int insertSelective(GroupPlaycut record);

    GroupPlaycut selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupPlaycut record);

    int updateByPrimaryKey(GroupPlaycut record);

    List<GroupPlaycut> findAll();
    
    List<GroupPlaycut> findByMapParam(Map<String, Object> params);
}