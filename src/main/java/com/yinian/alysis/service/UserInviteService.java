package com.yinian.alysis.service;

import java.util.List;
import java.util.Map;
import com.yinian.alysis.model.UserInvite;

public interface UserInviteService {
    List<UserInvite> findAllByPage(int pageNum, int pageSize);
    List<UserInvite> findByMapParam(Map<String,Object> params);
}
