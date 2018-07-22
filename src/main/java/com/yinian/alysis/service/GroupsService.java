package com.yinian.alysis.service;

import com.yinian.alysis.model.ActGroups;

import java.util.List;

public interface GroupsService {
    List<ActGroups> findAllByPage(int pageNum, int pageSize);
}
