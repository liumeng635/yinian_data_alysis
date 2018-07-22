package com.yinian.alysis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.yinian.alysis.model.GroupPlaycut;
import com.yinian.alysis.model.GroupPlaycutMapper;
import com.yinian.alysis.service.GroupPlayCutService;

@Service
public class GroupPlayCutServiceImpl implements GroupPlayCutService{
	@Autowired
	GroupPlaycutMapper groupPlaycutMapper;
	
	@Override
	public List<GroupPlaycut> findAllByPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
        return groupPlaycutMapper.findAll();
	}

	@Override
	public List<GroupPlaycut> findByMapParam(Map<String, Object> params) {
		return  groupPlaycutMapper.findByMapParam(params);
	}

	
}
