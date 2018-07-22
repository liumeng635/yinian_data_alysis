package com.yinian.alysis.service;

import java.util.List;
import java.util.Map;
import com.yinian.alysis.model.GroupEventcut;

public interface GroupEventCutService {
    List<GroupEventcut> findAllByPage(int pageNum, int pageSize);
    List<GroupEventcut> findByMapParam(Map<String,Object> params);
}
