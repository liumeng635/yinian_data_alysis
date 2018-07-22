package com.yinian.alysis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.yinian.alysis.model.PortType;
import com.yinian.alysis.model.PortTypeMapper;
import com.yinian.alysis.service.PortTypeService;

@Service
public class PortTypeServiceImpl implements PortTypeService{

	@Autowired
	PortTypeMapper portypeMapper;
	
	@Override
	public List<PortType> findAllByPage(int pageNum, int pageSize) {
		 if(pageNum != 0 && pageSize != 0 ) {
			 PageHelper.startPage(pageNum,pageSize);
		 }
	     return portypeMapper.findAll();
	}
   
}
