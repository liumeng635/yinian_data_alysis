package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface GroupUsercutMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupUsercut record);

    int insertSelective(GroupUsercut record);

    GroupUsercut selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupUsercut record);

    int updateByPrimaryKey(GroupUsercut record);

    List<GroupUsercut> findAll();
    
    List<GroupUsercut> findByMapParam(Map<String, Object> params);
}