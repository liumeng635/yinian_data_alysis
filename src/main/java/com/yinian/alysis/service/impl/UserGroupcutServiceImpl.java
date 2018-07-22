package com.yinian.alysis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.yinian.alysis.model.UserGroupcut;
import com.yinian.alysis.model.UserGroupcutMapper;
import com.yinian.alysis.service.UserGroupcutService;

@Service
public class UserGroupcutServiceImpl implements UserGroupcutService{

	@Autowired
	UserGroupcutMapper userGroupcutMapper;
	
	@Override
	public List<UserGroupcut> findAllByPage(int pageNum, int pageSize) {
		 PageHelper.startPage(pageNum,pageSize);
	        return userGroupcutMapper.findAll();
	}

	@Override
	public List<UserGroupcut> findByMapParam(Map<String, Object> params) {
		return userGroupcutMapper.findByMapParam(params);
	}
   
}
