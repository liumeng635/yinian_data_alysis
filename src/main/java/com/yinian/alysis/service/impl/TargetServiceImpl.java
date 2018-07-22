package com.yinian.alysis.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.yinian.alysis.model.ActTarget;
import com.yinian.alysis.model.ActTargetMapper;
import com.yinian.alysis.service.TargetService;

@Service
public class TargetServiceImpl implements TargetService{

	@Autowired
	ActTargetMapper actTargetMapper;
	
	@Override
	public List<ActTarget> findAllByPage(int pageNum, int pageSize) {
		 PageHelper.startPage(pageNum,pageSize);
	        return actTargetMapper.findAll();
	}

	@Override
	public List<ActTarget> findByMapParam(Map<String, Object> params) {
		return actTargetMapper.findByMapParam(params);
	}

	@Override
	public List<Map<String, Object>> actlineData(Map<String, Object> params) {
		return actTargetMapper.actlineData(params);
	}
   
	
}
