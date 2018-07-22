package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface ActivityNewTargetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityNewTarget record);

    int insertSelective(ActivityNewTarget record);

    ActivityNewTarget selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityNewTarget record);

    int updateByPrimaryKey(ActivityNewTarget record);
    
    List<ActivityNewTarget> findByMapParam(Map<String,Object> params);
}