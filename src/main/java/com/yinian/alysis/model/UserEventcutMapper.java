package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface UserEventcutMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserEventcut record);

    int insertSelective(UserEventcut record);

    UserEventcut selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserEventcut record);

    int updateByPrimaryKey(UserEventcut record);

    List<UserEventcut> findAll();
    
    List<UserEventcut> findByMapParam(Map<String, Object> params);
}