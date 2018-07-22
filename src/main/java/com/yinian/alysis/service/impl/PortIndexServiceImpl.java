package com.yinian.alysis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.yinian.alysis.model.Hot;
import com.yinian.alysis.model.HotMapper;
import com.yinian.alysis.model.PortIndex;
import com.yinian.alysis.model.PortIndexMapper;
import com.yinian.alysis.service.PortIndexService;

@Service
public class PortIndexServiceImpl implements PortIndexService{

	@Autowired
	PortIndexMapper portIndexMapper;
	
	@Autowired
	HotMapper hotMapper;
	
	@Override
	public List<PortIndex> findAllByPage(int pageNum, int pageSize) {
		 PageHelper.startPage(pageNum,pageSize);
	        return portIndexMapper.findAll();
	}

	@Override
	public List<Map<String, Object>> listAllportIndex() {
		return portIndexMapper.listAllportIndex();
	}

	@Override
	public List<Hot> findHotByMapParam(Map<String, Object> params) {
		return hotMapper.findHotByMapParam(params);
	}
	
	
   
}
