package com.yinian.alysis.service;

import java.util.List;
import java.util.Map;

import com.yinian.alysis.model.Hot;
import com.yinian.alysis.model.PortIndex;

public interface PortIndexService {
    List<PortIndex> findAllByPage(int pageNum, int pageSize);
    List<Map<String,Object>> listAllportIndex();
    List<Hot> findHotByMapParam(Map<String,Object> params);
}
