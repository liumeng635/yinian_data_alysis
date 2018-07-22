package com.yinian.alysis.service;

import java.util.List;
import java.util.Map;
import com.yinian.alysis.model.PortTarget;

public interface PortTargetService {
    List<PortTarget> findAllByPage(int pageNum, int pageSize);
    List<PortTarget> findTargetByParamMap(Map<String,Object> params);
}
