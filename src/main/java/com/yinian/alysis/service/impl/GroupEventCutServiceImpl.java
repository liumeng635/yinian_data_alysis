package com.yinian.alysis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.yinian.alysis.model.GroupEventcut;
import com.yinian.alysis.model.GroupEventcutMapper;
import com.yinian.alysis.service.GroupEventCutService;

@Service
public class GroupEventCutServiceImpl implements GroupEventCutService{
	@Autowired
	GroupEventcutMapper groupEventcutMapper;
	
	@Override
	public List<GroupEventcut> findAllByPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
        return groupEventcutMapper.findAll();
	}

	@Override
	public List<GroupEventcut> findByMapParam(Map<String, Object> params) {
		return groupEventcutMapper.findByMapParam(params);
	}

	
}
