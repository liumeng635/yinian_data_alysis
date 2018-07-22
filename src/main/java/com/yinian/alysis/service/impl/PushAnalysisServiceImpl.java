package com.yinian.alysis.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yinian.alysis.model.PushAnalysis;
import com.yinian.alysis.model.PushAnalysisMapper;
import com.yinian.alysis.service.PushAnalysisService;

@Service
public class PushAnalysisServiceImpl implements PushAnalysisService{

	 @Autowired
	 PushAnalysisMapper pushAnalysisMapper;
	
	@Override
	public List<PushAnalysis> getUserByMapParam(Map<String, Object> params) {
		return pushAnalysisMapper.findByMapParam(params);
	}

	@Override
	public List<Map<String, Object>> listAllDate() {
		return pushAnalysisMapper.listAllDate();
	}

	@Override
	public List<Map<String, Object>> listAllInvite(Map<String, Object> params) {
		return pushAnalysisMapper.listAllInvite(params);
	}

	@Override
	public List<Map<String, Object>> listAllUser() {
		return pushAnalysisMapper.listAllUser();
	}

	@Override
	public void insertUserIn(Map<String, Object> params) {
		pushAnalysisMapper.insertUserIn(params);
	}
	
	

}
