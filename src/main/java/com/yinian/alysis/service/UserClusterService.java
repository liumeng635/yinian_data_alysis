package com.yinian.alysis.service;

import java.util.List;
import java.util.Map;
import com.yinian.alysis.model.ActUserCluster;

public interface UserClusterService {
    List<ActUserCluster> findAllByPage(int pageNum, int pageSize);
    List<ActUserCluster> findByMapParam(Map<String,Object> params);
    List<Map<String,Object>> findStaticsByMapParam(Map<String,Object> params);
    List<Map<String, Object>> findTotalhavByMapParam(Map<String, Object> params);
    List<Map<String, Object>> findCreatehavByMapParam(Map<String, Object> params);
    List<Map<String, Object>> findUploadhavByMapParam(Map<String, Object> params);
    List<Map<String, Object>> findPlayhavByMapParam(Map<String, Object> params);
}
