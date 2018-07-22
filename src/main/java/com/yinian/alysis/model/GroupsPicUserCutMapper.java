package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface GroupsPicUserCutMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupsPicUserCut record);

    int insertSelective(GroupsPicUserCut record);

    GroupsPicUserCut selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupsPicUserCut record);

    int updateByPrimaryKey(GroupsPicUserCut record);

    List<GroupsPicUserCut> findAll();
    
    List<GroupsPicUserCut> findByMapParam(Map<String,Object> params);
}