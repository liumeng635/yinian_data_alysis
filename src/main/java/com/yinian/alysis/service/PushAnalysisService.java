package com.yinian.alysis.service;

import java.util.List;
import java.util.Map;

import com.yinian.alysis.model.PushAnalysis;

public interface PushAnalysisService {
	List<PushAnalysis> getUserByMapParam(Map<String,Object> params);
	List<Map<String,Object>> listAllDate();
	List<Map<String,Object>> listAllInvite(Map<String,Object> params);
	void insertUserIn(Map<String,Object> params);
    List<Map<String,Object>> listAllUser();
}
