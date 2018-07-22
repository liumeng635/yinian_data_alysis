package com.yinian.alysis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.yinian.alysis.model.PortTarget;
import com.yinian.alysis.model.PortTargetMapper;
import com.yinian.alysis.service.PortTargetService;

@Service
public class PortTargetServiceImpl implements PortTargetService{

	@Autowired
	PortTargetMapper portTargetMapper;
	
	@Override
	public List<PortTarget> findAllByPage(int pageNum, int pageSize) {
		 PageHelper.startPage(pageNum,pageSize);
	        return portTargetMapper.findAll();
	}

	@Override
	public List<PortTarget> findTargetByParamMap(Map<String, Object> params) {
		return portTargetMapper.findTargetByParamMap(params);
	}
   
}
