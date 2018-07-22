package com.yinian.alysis.service;

import java.util.List;
import java.util.Map;
import com.yinian.alysis.model.GroupPlaycut;

public interface GroupPlayCutService {
    List<GroupPlaycut> findAllByPage(int pageNum, int pageSize);
    List<GroupPlaycut> findByMapParam(Map<String,Object> params);
}
