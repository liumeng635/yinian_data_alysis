package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

import com.yinian.alysis.model.BuryPort;

public interface BuryPortMapper {
    int deleteByPrimaryKey(Integer pid);

    int insert(BuryPort record);

    int insertSelective(BuryPort record);

    BuryPort selectByPrimaryKey(Integer pid);

    int updateByPrimaryKeySelective(BuryPort record);

    int updateByPrimaryKey(BuryPort record);
    
    List<BuryPort> findByMapParam(Map<String, Object> params);
    
    void deleteBatch(int[] ids);
}