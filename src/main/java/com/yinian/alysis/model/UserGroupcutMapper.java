package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface UserGroupcutMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserGroupcut record);

    int insertSelective(UserGroupcut record);

    UserGroupcut selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserGroupcut record);

    int updateByPrimaryKey(UserGroupcut record);

    List<UserGroupcut> findAll();
    
    List<UserGroupcut> findByMapParam(Map<String, Object> params);
}