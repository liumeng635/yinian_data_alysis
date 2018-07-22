package com.yinian.alysis.service;

import java.util.List;
import java.util.Map;
import com.yinian.alysis.model.UserEventcut;

public interface UserEventcutService {
    List<UserEventcut> findAllByPage(int pageNum, int pageSize);
    List<UserEventcut> findByMapParam(Map<String,Object> params);
}
