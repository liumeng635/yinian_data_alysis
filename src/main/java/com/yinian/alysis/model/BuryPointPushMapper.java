package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface BuryPointPushMapper {
    int deleteByPrimaryKey(Integer buryId);

    int insert(BuryPointPush record);

    int insertSelective(BuryPointPush record);

    BuryPointPush selectByPrimaryKey(Integer buryId);

    int updateByPrimaryKeySelective(BuryPointPush record);

    int updateByPrimaryKey(BuryPointPush record);
    
    List<BuryPointPush> findByMapParam(Map<String, Object> params);
    
    void deleteBatch(int[] ids);
    
    void updateBySql(String sql);
    
    List<Map<String,Object>> getStageList(String buryType);
}