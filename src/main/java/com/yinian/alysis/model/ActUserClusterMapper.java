package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface ActUserClusterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActUserCluster record);

    int insertSelective(ActUserCluster record);

    ActUserCluster selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActUserCluster record);

    int updateByPrimaryKey(ActUserCluster record);

    List<ActUserCluster> findAll();
    
    List<ActUserCluster> findByMapParam(Map<String, Object> params);
    
    List<Map<String, Object>> findStaticsByMapParam(Map<String, Object> params);
    
    List<Map<String, Object>> findTotalhavByMapParam(Map<String, Object> params);
    List<Map<String, Object>> findCreatehavByMapParam(Map<String, Object> params);
    List<Map<String, Object>> findUploadhavByMapParam(Map<String, Object> params);
    List<Map<String, Object>> findPlayhavByMapParam(Map<String, Object> params);
}