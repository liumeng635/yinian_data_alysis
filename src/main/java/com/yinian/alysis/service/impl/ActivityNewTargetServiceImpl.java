package com.yinian.alysis.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yinian.alysis.model.ActivityNewTarget;
import com.yinian.alysis.model.ActivityNewTargetMapper;
import com.yinian.alysis.service.ActivityNewTargetService;

@Service
public class ActivityNewTargetServiceImpl implements ActivityNewTargetService{

	 @Autowired
	 ActivityNewTargetMapper activityNewTargetMapper;
	
	@Override
	public List<ActivityNewTarget> findByMapParam(Map<String, Object> params) {
		return activityNewTargetMapper.findByMapParam(params);
	}

}
