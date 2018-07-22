package com.yinian.alysis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.yinian.alysis.model.UserEventcut;
import com.yinian.alysis.model.UserEventcutMapper;
import com.yinian.alysis.service.UserEventcutService;

@Service
public class UserEventcutServiceImpl implements UserEventcutService{

	@Autowired
	UserEventcutMapper userEventcutMapper;
	
	@Override
	public List<UserEventcut> findAllByPage(int pageNum, int pageSize) {
		 PageHelper.startPage(pageNum,pageSize);
	     return userEventcutMapper.findAll();
	}

	@Override
	public List<UserEventcut> findByMapParam(Map<String, Object> params) {
		return userEventcutMapper.findByMapParam(params);
	}
   
}
