package com.yinian.alysis.service;

import java.util.List;
import java.util.Map;
import com.yinian.alysis.model.GroupUsercut;

public interface GroupUserCutService {
    List<GroupUsercut> findAllByPage(int pageNum, int pageSize);
    List<GroupUsercut> findByMapParam(Map<String,Object> params);
}
