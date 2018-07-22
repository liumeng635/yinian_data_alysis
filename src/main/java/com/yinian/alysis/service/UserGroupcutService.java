package com.yinian.alysis.service;

import java.util.List;
import java.util.Map;
import com.yinian.alysis.model.UserGroupcut;

public interface UserGroupcutService {
    List<UserGroupcut> findAllByPage(int pageNum, int pageSize);
    List<UserGroupcut> findByMapParam(Map<String,Object> params);
}
