package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface GroupRetentionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupRetention record);

    int insertSelective(GroupRetention record);

    GroupRetention selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupRetention record);

    int updateByPrimaryKey(GroupRetention record);
    
    List<GroupRetention> findByMapParam(Map<String, Object> params);
    
    List<Map<String,Object>> listMap1(Map<String, Object> params);
    List<Map<String,Object>> listMap2(Map<String, Object> params);
    
    
    List<Map<String,Object>> listMap11(Map<String, Object> params);
    List<Map<String,Object>> listMap22(Map<String, Object> params);
    
    List<Map<String, Object>> listAllDate();
    
    List<GroupRetention> listAllNewestRcd(Map<String,Object> params);

}