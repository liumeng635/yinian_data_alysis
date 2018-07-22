package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface BuryPointCommMapper {
    int deleteByPrimaryKey(Integer buryId);

    int insert(BuryPointComm record);

    int insertSelective(BuryPointComm record);

    BuryPointComm selectByPrimaryKey(Integer buryId);

    int updateByPrimaryKeySelective(BuryPointComm record);

    int updateByPrimaryKey(BuryPointComm record);
    
    List<BuryPointComm> findByMapParam(Map<String, Object> params);
    
    void deleteBatch(int[] ids);
    
    void updateBySql(String sql);
    
    List<Map<String,Object>> getStageList(String buryType);
}