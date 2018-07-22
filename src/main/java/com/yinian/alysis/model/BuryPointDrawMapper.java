package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface BuryPointDrawMapper {
    int deleteByPrimaryKey(Integer buryId);

    int insert(BuryPointDraw record);

    int insertSelective(BuryPointDraw record);

    BuryPointDraw selectByPrimaryKey(Integer buryId);

    int updateByPrimaryKeySelective(BuryPointDraw record);

    int updateByPrimaryKey(BuryPointDraw record);
    
    List<BuryPointDraw> findByMapParam(Map<String, Object> params);
    
    void deleteBatch(int[] ids);
    
    void updateBySql(String sql);
    
    List<Map<String,Object>> getStageList(String buryType);
}