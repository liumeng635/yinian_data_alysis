package com.yinian.alysis.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yinian.alysis.model.BuryPointComm;
import com.yinian.alysis.model.BuryPointCommMapper;
import com.yinian.alysis.model.BuryPointDraw;
import com.yinian.alysis.model.BuryPointDrawMapper;
import com.yinian.alysis.model.BuryPointLogin;
import com.yinian.alysis.model.BuryPointLoginMapper;
import com.yinian.alysis.model.BuryPointPush;
import com.yinian.alysis.model.BuryPointPushMapper;
import com.yinian.alysis.model.BuryPort;
import com.yinian.alysis.model.BuryPortMapper;
import com.yinian.alysis.service.BuryPointService;

@Service
public class BuryPointServiceImpl implements BuryPointService{
    @Autowired
    BuryPointCommMapper bpcMapper;
    
    @Autowired
    BuryPointLoginMapper bplMapper;
    
    @Autowired
    BuryPointDrawMapper bpdMapper;
    
    @Autowired
    BuryPointPushMapper bppMapper;
    
    @Autowired
    BuryPortMapper bpMapper;

	@Override
	public List<BuryPointComm> findBuryPointCommByMapParam(Map<String, Object> params) {
		return bpcMapper.findByMapParam(params);
	}

	@Override
	public List<BuryPointDraw> findBuryPointDrawByMapParam(Map<String, Object> params) {
		return bpdMapper.findByMapParam(params);
	}

	@Override
	public List<BuryPointLogin> findBuryPointLoginByMapParam(Map<String, Object> params) {
		return bplMapper.findByMapParam(params);
	}

	@Override
	public List<BuryPointPush> findBuryPointPushByMapParam(Map<String, Object> params) {
		return bppMapper.findByMapParam(params);
	}

	@Override
	public int insertBuryPointComm(BuryPointComm bean) {
		return bpcMapper.insertSelective(bean);
	}

	@Override
	public int insertBuryPointDraw(BuryPointDraw bean) {
		return bpdMapper.insertSelective(bean);
	}

	@Override
	public int insertBuryPointLogin(BuryPointLogin bean) {
		return bplMapper.insertSelective(bean);
	}

	@Override
	public int insertBuryPointPush(BuryPointPush bean) {
		return bppMapper.insertSelective(bean);
	}
	
	@Override
	public int insertBuryPort(BuryPort bean) {
		return bpMapper.insertSelective(bean);
	}

	@Override
	public void batchDeleteBuryPointComm(int[] ids) {
		bpcMapper.deleteBatch(ids);
	}

	@Override
	public void batchDeleteBuryPointDraw(int[] ids) {
		bpdMapper.deleteBatch(ids);
	}

	@Override
	public void batchDeleteBuryPointLogin(int[] ids) {
		bplMapper.deleteBatch(ids);
	}

	@Override
	public void batchDeleteBuryPointPush(int[] ids) {
		bppMapper.deleteBatch(ids);
	}
	

	@Override
	public void batchDeleteBuryPort(int[] ids) {
		bpMapper.deleteBatch(ids);
	}
	
	@Override
	public BuryPointComm findBuryPointCommByKey(Integer id) {
		return bpcMapper.selectByPrimaryKey(id);
	}

	@Override
	public BuryPointDraw findBuryPointDrawByKey(Integer id) {
		return bpdMapper.selectByPrimaryKey(id);
	}

	@Override
	public BuryPointLogin findBuryPointLoginByKey(Integer id) {
		return bplMapper.selectByPrimaryKey(id);
	}

	@Override
	public BuryPointPush findBuryPointPushByKey(Integer id) {
		return bppMapper.selectByPrimaryKey(id);
	}

	@Override
	public BuryPort findBuryPortByKey(Integer id) {
		return bpMapper.selectByPrimaryKey(id);
	}
	
	
	@Override
	public void alterBuryPointComm(BuryPointComm bean) {
		bpcMapper.updateByPrimaryKeySelective(bean);
	}

	@Override
	public void alterBuryPointDraw(BuryPointDraw bean) {
		bpdMapper.updateByPrimaryKeySelective(bean);
	}

	@Override
	public void alterBuryPointLogin(BuryPointLogin bean) {
		bplMapper.updateByPrimaryKeySelective(bean);
	}

	@Override
	public void alterBuryPointPush(BuryPointPush bean) {
		bppMapper.updateByPrimaryKeySelective(bean);
	}

	@Override
	public void alterBuryPort(BuryPort port) {
		bpMapper.updateByPrimaryKeySelective(port);
	}

	@Override
	public void updateBySql(String ids, String type, int status) {
		String sql = "";
		if(StringUtils.equals("1", type)){//通用表指标
			sql = "update t_bury_point_comm set status = "+status+" where bury_id in ("+ids+")";
			bpcMapper.updateBySql(sql);
		}else if(StringUtils.equals("2", type)){//促活推送
			sql = "update t_bury_point_push set status = "+status+" where bury_id in ("+ids+")";
			bppMapper.updateBySql(sql);
		}else if(StringUtils.equals("3", type)){//地图调取
			sql = "update t_bury_point_draw set status = "+status+" where bury_id in ("+ids+")";
			bpdMapper.updateBySql(sql);
		}else if(StringUtils.equals("4", type)){//用户登录
			sql = "update t_bury_point_login set status = "+status+" where bury_id in ("+ids+")";
			bplMapper.updateBySql(sql);
		}
	}

	@Override
	public List<Map<String, Object>> stageList(String type,String buryType) {
		List<Map<String,Object>> rslist = new ArrayList<Map<String,Object>>();
		if(StringUtils.equals("1", type)){//通用表指标
			rslist = bpcMapper.getStageList(buryType);
		}else if(StringUtils.equals("2", type)){//促活推送
			rslist = bppMapper.getStageList(buryType);
		}else if(StringUtils.equals("3", type)){//地图调取
			rslist = bpdMapper.getStageList(buryType);
		}else if(StringUtils.equals("4", type)){//用户登录
			rslist = bplMapper.getStageList(buryType);
		}
		return rslist;
	}

	@Override
	public List<BuryPort> findBuryPortByMapParam(Map<String, Object> params) {
		return bpMapper.findByMapParam(params);
	}
	
	
}
