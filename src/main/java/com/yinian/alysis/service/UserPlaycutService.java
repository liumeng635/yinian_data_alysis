package com.yinian.alysis.service;

import java.util.List;
import java.util.Map;
import com.yinian.alysis.model.UserPlaycut;

public interface UserPlaycutService {
    List<UserPlaycut> findAllByPage(int pageNum, int pageSize);
    List<UserPlaycut> findByMapParam(Map<String,Object> params);
}
