package com.yinian.alysis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.yinian.alysis.model.GroupUsercut;
import com.yinian.alysis.model.GroupUsercutMapper;
import com.yinian.alysis.service.GroupUserCutService;

@Service
public class GroupUserCutServiceImpl implements GroupUserCutService{
	@Autowired
	GroupUsercutMapper groupUsercutMapper;
	
	@Override
	public List<GroupUsercut> findAllByPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
        return groupUsercutMapper.findAll();
	}

	@Override
	public List<GroupUsercut> findByMapParam(Map<String, Object> params) {
		return groupUsercutMapper.findByMapParam(params);
	}
	
	

}
