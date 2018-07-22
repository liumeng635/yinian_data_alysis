package com.yinian.alysis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.yinian.alysis.model.ActGroups;
import com.yinian.alysis.model.ActGroupsMapper;
import com.yinian.alysis.service.GroupsService;

@Service
public class GroupsServiceImpl implements GroupsService{
    @Autowired
    ActGroupsMapper actGroupsMapper;

    @Override
    public List<ActGroups> findAllByPage(int pageNum, int pageSize) {
    	List<ActGroups> rs = new ArrayList<ActGroups>();
    	if(pageNum == 0 && pageSize == 0) {
    		rs = actGroupsMapper.findAll();
    	}else {
    		PageHelper.startPage(pageNum,pageSize);
    		rs = actGroupsMapper.findAll();
    	}
        return rs;
    }
}
