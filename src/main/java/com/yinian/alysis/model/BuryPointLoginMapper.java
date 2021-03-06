package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface BuryPointLoginMapper {
    int deleteByPrimaryKey(Integer buryId);

    int insert(BuryPointLogin record);

    int insertSelective(BuryPointLogin record);

    BuryPointLogin selectByPrimaryKey(Integer buryId);

    int updateByPrimaryKeySelective(BuryPointLogin record);

    int updateByPrimaryKey(BuryPointLogin record);
    
    List<BuryPointLogin> findByMapParam(Map<String, Object> params);
    
    void deleteBatch(int[] ids);
    
    void updateBySql(String sql);
    
    List<Map<String,Object>> getStageList(String buryType);
}