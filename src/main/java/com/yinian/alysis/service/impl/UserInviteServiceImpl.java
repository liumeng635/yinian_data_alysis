package com.yinian.alysis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.yinian.alysis.model.UserInvite;
import com.yinian.alysis.model.UserInviteMapper;
import com.yinian.alysis.service.UserInviteService;

@Service
public class UserInviteServiceImpl implements UserInviteService{

	@Autowired
	UserInviteMapper userInviteMapper;
	
	@Override
	public List<UserInvite> findAllByPage(int pageNum, int pageSize) {
		 PageHelper.startPage(pageNum,pageSize);
	        return userInviteMapper.findAll();
	}

	@Override
	public List<UserInvite> findByMapParam(Map<String, Object> params) {
		return userInviteMapper.findByMapParam(params);
	}
	
	
   
}
