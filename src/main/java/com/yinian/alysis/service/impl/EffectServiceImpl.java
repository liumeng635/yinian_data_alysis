package com.yinian.alysis.service.impl;

import com.github.pagehelper.PageHelper;
import com.yinian.alysis.model.ActEffect;
import com.yinian.alysis.model.ActEffectMapper;
import com.yinian.alysis.service.EffectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EffectServiceImpl implements EffectService{
    @Autowired
    ActEffectMapper actEffectMapper;

    @Override
    public List<ActEffect> findAllByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return actEffectMapper.findAll();
    }
}
