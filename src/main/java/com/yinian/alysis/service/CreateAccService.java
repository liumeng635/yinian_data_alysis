package com.yinian.alysis.service;

import java.util.List;
import java.util.Map;
import com.yinian.alysis.model.CreateAcc;

public interface CreateAccService {
    List<CreateAcc> findAllByPage(int pageNum, int pageSize);
    List<CreateAcc> findByMapParam(Map<String,Object> params);
    List<Map<String,Object>> initAllGroupType();
    List<Map<String,Object>> createAccMap(Map<String, Object> params);
}
