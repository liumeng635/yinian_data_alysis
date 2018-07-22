package com.yinian.alysis.service;

import java.util.List;
import java.util.Map;

import com.yinian.alysis.model.BuryPointComm;
import com.yinian.alysis.model.BuryPointDraw;
import com.yinian.alysis.model.BuryPointLogin;
import com.yinian.alysis.model.BuryPointPush;
import com.yinian.alysis.model.BuryPort;

public interface BuryPointService {
    List<BuryPointComm> findBuryPointCommByMapParam(Map<String,Object> params);
    List<BuryPointDraw> findBuryPointDrawByMapParam(Map<String,Object> params);
    List<BuryPointLogin> findBuryPointLoginByMapParam(Map<String,Object> params);
    List<BuryPointPush> findBuryPointPushByMapParam(Map<String,Object> params);
    List<BuryPort> findBuryPortByMapParam(Map<String,Object> params);
    
    int insertBuryPointComm(BuryPointComm bean);
    int insertBuryPointDraw(BuryPointDraw bean);
    int insertBuryPointLogin(BuryPointLogin bean);
    int insertBuryPointPush(BuryPointPush bean);
    int insertBuryPort(BuryPort bean);
    
    void alterBuryPointComm(BuryPointComm bean);
    void alterBuryPointDraw(BuryPointDraw bean);
    void alterBuryPointLogin(BuryPointLogin bean);
    void alterBuryPointPush(BuryPointPush bean);
    void alterBuryPort(BuryPort port);
     
    BuryPointComm findBuryPointCommByKey(Integer id);
    BuryPointDraw findBuryPointDrawByKey(Integer id);
    BuryPointLogin findBuryPointLoginByKey(Integer id);
    BuryPointPush findBuryPointPushByKey(Integer id);
    BuryPort findBuryPortByKey(Integer id);
    
    void batchDeleteBuryPointComm(int[] ids);
    void batchDeleteBuryPointDraw(int[] ids);
    void batchDeleteBuryPointLogin(int[] ids);
    void batchDeleteBuryPointPush(int[] ids);
    void batchDeleteBuryPort(int[] ids);
    
    void updateBySql(String ids,String type,int status);
    
    List<Map<String,Object>> stageList(String type,String buryType);
    
}
