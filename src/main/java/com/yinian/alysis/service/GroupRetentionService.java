package com.yinian.alysis.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import com.yinian.alysis.model.GroupRetention;

public interface GroupRetentionService {
    List<GroupRetention> findByMapParam(Map<String,Object> params);
    List<GroupRetention> listAllNewestRcd(Map<String,Object> params);
    List<Map<String,Object>> queryRetention(String time) throws ParseException ;
    List<Map<String,Object>> queryRetention1(String time) throws ParseException ;
    void insert(GroupRetention bean);
    List<Map<String,Object>> listAllDate();
    
    
}
