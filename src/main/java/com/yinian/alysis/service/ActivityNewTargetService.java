package com.yinian.alysis.service;

import java.util.List;
import java.util.Map;
import com.yinian.alysis.model.ActivityNewTarget;

public interface ActivityNewTargetService {
    List<ActivityNewTarget> findByMapParam(Map<String,Object> params);
}
