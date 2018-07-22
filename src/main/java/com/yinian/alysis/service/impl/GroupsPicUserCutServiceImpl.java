package com.yinian.alysis.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.yinian.alysis.model.GroupsPicUserCut;
import com.yinian.alysis.model.GroupsPicUserCutMapper;
import com.yinian.alysis.service.GroupsPicUserCutService;

@Service
public class GroupsPicUserCutServiceImpl implements GroupsPicUserCutService{
    @Autowired
    GroupsPicUserCutMapper groupsPicUserCutMapper;

    @Override
    public List<GroupsPicUserCut> findAllByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return groupsPicUserCutMapper.findAll();
    }

	@Override
	public List<GroupsPicUserCut> findByMapParam(Map<String, Object> params) {
		return groupsPicUserCutMapper.findByMapParam(params);
	}
    
    
}
