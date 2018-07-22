package com.yinian.alysis.service;

import java.util.List;
import java.util.Map;

import com.yinian.alysis.model.ActTarget;

public interface TargetService {
    List<ActTarget> findAllByPage(int pageNum, int pageSize);
    List<ActTarget> findByMapParam(Map<String,Object> params);
    List<Map<String,Object>> actlineData(Map<String,Object> params);
}
