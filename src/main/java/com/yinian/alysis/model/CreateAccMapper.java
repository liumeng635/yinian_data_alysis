package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface CreateAccMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CreateAcc record);

    int insertSelective(CreateAcc record);

    CreateAcc selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CreateAcc record);

    int updateByPrimaryKey(CreateAcc record);

    List<CreateAcc> findAll();
    
    List<CreateAcc> findByMapParam(Map<String, Object> params);
    
    List<Map<String,Object>> initAllGroupType();
    
    List<Map<String,Object>> createAccMap(Map<String, Object> params);
}