package com.yinian.alysis.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yinian.alysis.model.GroupRetention;
import com.yinian.alysis.model.GroupRetentionMapper;
import com.yinian.alysis.service.GroupRetentionService;
import com.yinian.alysis.util.DateUtils;

@Service
public class GroupRetentionServiceImpl implements GroupRetentionService{

	 @Autowired
	 GroupRetentionMapper groupRetentionMapper;
	
	@Override
	public List<GroupRetention> findByMapParam(Map<String, Object> params) {
		return groupRetentionMapper.findByMapParam(params);
	}

	@Override
	public List<Map<String, Object>> queryRetention(String time) throws ParseException {
		Map<String,Object> params = new HashMap<>();
		String startTime = "";
		String endTime = "";
		params.put("time", time);
		List<Map<String,Object>> listMap1 = groupRetentionMapper.listMap1(params);
		for(Map map : listMap1) {
			map.put("period", "当天");
			map.put("dataType", "1");
		}
		
		startTime = DateUtils.getPreviousOrNextDaysOfNow(time, 1)+" 00:00:00";
		endTime = DateUtils.getPreviousOrNextDaysOfNow(time, 3)+" 23:59:59";
		params.clear();
		params.put("time", time);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		List<Map<String,Object>> listMap2 = groupRetentionMapper.listMap2(params);
		for(Map map : listMap2) {
			map.put("period", "3天内");
			map.put("dataType", "1");
		}
		startTime = DateUtils.getPreviousOrNextDaysOfNow(time, 4)+" 00:00:00";
		endTime = DateUtils.getPreviousOrNextDaysOfNow(time, 7)+" 23:59:59";
		params.clear();
		params.put("time", time);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		List<Map<String,Object>> listMap3 = groupRetentionMapper.listMap2(params);
		for(Map map : listMap3) {
			map.put("period", "4-7天内");
			map.put("dataType", "1");
		}
		startTime = DateUtils.getPreviousOrNextDaysOfNow(time, 1)+" 00:00:00";
		endTime = DateUtils.getPreviousOrNextDaysOfNow(time, 7)+" 23:59:59";
		params.clear();
		params.put("time", time);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		List<Map<String,Object>> listMap4 = groupRetentionMapper.listMap2(params);
		for(Map map : listMap4) {
			map.put("period", "7天内");
			map.put("dataType", "1");
		}
		List<Map<String,Object>> all = new ArrayList<Map<String,Object>>();
		all.addAll(listMap1);
		all.addAll(listMap2);
		all.addAll(listMap3);
		all.addAll(listMap4);
		return all;
	}
	
	@Override
	public List<Map<String, Object>> queryRetention1(String time) throws ParseException {
		Map<String,Object> params = new HashMap<>();
		String startTime = "";
		String endTime = "";
		params.put("time", time);
		List<Map<String,Object>> listMap1 = groupRetentionMapper.listMap11(params);
		for(Map map : listMap1) {
			map.put("date", time);
			map.put("period", "当天");
			map.put("dataType", "0");
		}
		
		startTime = DateUtils.getPreviousOrNextDaysOfNow(time, 1)+" 00:00:00";
		endTime = DateUtils.getPreviousOrNextDaysOfNow(time, 3)+" 23:59:59";
		params.clear();
		params.put("time", time);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		List<Map<String,Object>> listMap2 = groupRetentionMapper.listMap22(params);
		for(Map map : listMap2) {
			map.put("period", "3天内");
			map.put("dataType", "0");
		}
		startTime = DateUtils.getPreviousOrNextDaysOfNow(time, 4)+" 00:00:00";
		endTime = DateUtils.getPreviousOrNextDaysOfNow(time, 7)+" 23:59:59";
		params.clear();
		params.put("time", time);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		List<Map<String,Object>> listMap3 = groupRetentionMapper.listMap22(params);
		for(Map map : listMap3) {
			map.put("period", "4-7天内");
			map.put("dataType", "0");
		}
		startTime = DateUtils.getPreviousOrNextDaysOfNow(time, 1)+" 00:00:00";
		endTime = DateUtils.getPreviousOrNextDaysOfNow(time, 7)+" 23:59:59";
		params.clear();
		params.put("time", time);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		List<Map<String,Object>> listMap4 = groupRetentionMapper.listMap22(params);
		for(Map map : listMap4) {
			map.put("period", "7天内");
			map.put("dataType", "0");
		}
		List<Map<String,Object>> all = new ArrayList<Map<String,Object>>();
		all.addAll(listMap1);
		all.addAll(listMap2);
		all.addAll(listMap3);
		all.addAll(listMap4);
		return all;
	}

	@Override
	public void insert(GroupRetention bean) {
		groupRetentionMapper.insert(bean);
	}

	@Override
	public List<Map<String, Object>> listAllDate() {
		return groupRetentionMapper.listAllDate();
	}

	@Override
	public List<GroupRetention> listAllNewestRcd(Map<String,Object> params) {
		return groupRetentionMapper.listAllNewestRcd(params);
	}

	
	
}
