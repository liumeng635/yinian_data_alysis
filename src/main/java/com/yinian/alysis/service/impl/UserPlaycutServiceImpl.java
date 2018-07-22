package com.yinian.alysis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.yinian.alysis.model.UserPlaycut;
import com.yinian.alysis.model.UserPlaycutMapper;
import com.yinian.alysis.service.UserPlaycutService;

@Service
public class UserPlaycutServiceImpl implements UserPlaycutService{

	@Autowired
	UserPlaycutMapper userPlaycutMapper;
	
	@Override
	public List<UserPlaycut> findAllByPage(int pageNum, int pageSize) {
		 PageHelper.startPage(pageNum,pageSize);
	        return userPlaycutMapper.findAll();
	}

	@Override
	public List<UserPlaycut> findByMapParam(Map<String, Object> params) {
		return userPlaycutMapper.findByMapParam(params);
	}
   
	
}
