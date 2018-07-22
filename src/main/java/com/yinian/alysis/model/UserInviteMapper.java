package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface UserInviteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInvite record);

    int insertSelective(UserInvite record);

    UserInvite selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInvite record);

    int updateByPrimaryKey(UserInvite record);

    List<UserInvite> findAll();
    
    List<UserInvite> findByMapParam(Map<String, Object> params);
}