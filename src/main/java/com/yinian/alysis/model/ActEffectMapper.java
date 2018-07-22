package com.yinian.alysis.model;

import java.util.List;

public interface ActEffectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActEffect record);

    int insertSelective(ActEffect record);

    ActEffect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActEffect record);

    int updateByPrimaryKey(ActEffect record);

    List<ActEffect> findAll();
}