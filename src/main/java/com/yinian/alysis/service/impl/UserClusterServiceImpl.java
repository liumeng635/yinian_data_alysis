package com.yinian.alysis.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.yinian.alysis.model.ActUserCluster;
import com.yinian.alysis.model.ActUserClusterMapper;
import com.yinian.alysis.service.UserClusterService;

@Service
public class UserClusterServiceImpl implements UserClusterService{

	@Autowired
	ActUserClusterMapper actUserClusterMapper;
	
	@Override
	public List<ActUserCluster> findAllByPage(int pageNum, int pageSize) {
		 PageHelper.startPage(pageNum,pageSize);
	        return actUserClusterMapper.findAll();
	}

	@Override
	public List<ActUserCluster> findByMapParam(Map<String, Object> params) {
		return actUserClusterMapper.findByMapParam(params);
	}

	@Override
	public List<Map<String, Object>> findStaticsByMapParam(Map<String, Object> params) {
		return actUserClusterMapper.findStaticsByMapParam(params);
	}

	@Override
	public List<Map<String, Object>> findTotalhavByMapParam(Map<String, Object> params) {
		return actUserClusterMapper.findTotalhavByMapParam(params);
	}

	@Override
	public List<Map<String, Object>> findCreatehavByMapParam(Map<String, Object> params) {
		return actUserClusterMapper.findCreatehavByMapParam(params);
	}

	@Override
	public List<Map<String, Object>> findUploadhavByMapParam(Map<String, Object> params) {
		return actUserClusterMapper.findUploadhavByMapParam(params);
	}

	@Override
	public List<Map<String, Object>> findPlayhavByMapParam(Map<String, Object> params) {
		return actUserClusterMapper.findPlayhavByMapParam(params);
	}
	
	
	
	
	
   
}
