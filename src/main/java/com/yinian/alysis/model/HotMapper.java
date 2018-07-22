package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

import com.yinian.alysis.model.Hot;

public interface HotMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Hot record);

    int insertSelective(Hot record);

    Hot selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hot record);

    int updateByPrimaryKey(Hot record);
    
    List<Hot> findHotByMapParam(Map<String, Object> params);
}