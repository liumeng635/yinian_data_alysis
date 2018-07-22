package com.yinian.alysis.service;

import java.util.List;

import com.yinian.alysis.model.PortType;

public interface PortTypeService {
    List<PortType> findAllByPage(int pageNum, int pageSize);
}
