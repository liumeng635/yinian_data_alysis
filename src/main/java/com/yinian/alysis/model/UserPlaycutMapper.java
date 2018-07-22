package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface UserPlaycutMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPlaycut record);

    int insertSelective(UserPlaycut record);

    UserPlaycut selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserPlaycut record);

    int updateByPrimaryKey(UserPlaycut record);

    List<UserPlaycut> findAll();
    
    List<UserPlaycut> findByMapParam(Map<String, Object> params);
}