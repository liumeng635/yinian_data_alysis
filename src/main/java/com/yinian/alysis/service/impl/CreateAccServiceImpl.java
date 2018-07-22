package com.yinian.alysis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.yinian.alysis.model.CreateAcc;
import com.yinian.alysis.model.CreateAccMapper;
import com.yinian.alysis.service.CreateAccService;

@Service
public class CreateAccServiceImpl implements CreateAccService{
    @Autowired
    CreateAccMapper createAccMapper;

    @Override
    public List<CreateAcc> findAllByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return createAccMapper.findAll();
    }

	@Override
	public List<CreateAcc> findByMapParam(Map<String, Object> params) {
		return createAccMapper.findByMapParam(params);
	}

	@Override
	public List<Map<String, Object>> initAllGroupType() {
		return createAccMapper.initAllGroupType();
	}

	@Override
	public List<Map<String, Object>> createAccMap(Map<String, Object> params) {
		return createAccMapper.createAccMap(params);
	}
    
    
}
