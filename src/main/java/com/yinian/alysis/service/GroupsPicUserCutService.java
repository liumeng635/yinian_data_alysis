package com.yinian.alysis.service;

import java.util.List;
import java.util.Map;

import com.yinian.alysis.model.GroupsPicUserCut;

public interface GroupsPicUserCutService {
    List<GroupsPicUserCut> findAllByPage(int pageNum, int pageSize);
    
    List<GroupsPicUserCut> findByMapParam(Map<String,Object> params);
}
