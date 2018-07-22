package com.yinian.alysis.controller.commIndex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.yinian.alysis.service.UserClusterService;
import com.yinian.alysis.util.ResultsHelper;


@Controller
public class UserRetainController {
	
	@Autowired
	private UserClusterService  userClusterService;
	
    @RequestMapping("/actUserRetain")
    public String actUserRetain(Model model) {
    	return "commIndex/actUserRetain";
    }

    /**
     * @param groupid
     * @param time
     * @return
     */
    @RequestMapping("/renderUserRetainTable")
    @ResponseBody
    public JSONObject renderUserRetainTable(String groupid,String time){
    	groupid = StringUtils.isEmpty(groupid) ? "0" : groupid;
    	Map<String,Object> params = new HashMap<String,Object>();
		params.put("groupid", Integer.valueOf(groupid));
		params.put("period", time);
		List<Map<String,Object>> list = userClusterService.findStaticsByMapParam(params);
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, list, "");
    }
    
    @RequestMapping("/getUserRetainData")
    @ResponseBody
    public JSONObject getUserRetainData(String groupid,String time){
    	groupid = StringUtils.isEmpty(groupid) ? "0" : groupid;
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("groupid", Integer.valueOf(groupid));
    	params.put("period", time);
    	List<Map<String,Object>> list1 = userClusterService.findTotalhavByMapParam(params);//用户总计频次
    	List<Map<String,Object>> list2 = userClusterService.findCreatehavByMapParam(params);//用户创建频次
    	List<Map<String,Object>> list3 = userClusterService.findUploadhavByMapParam(params);//用户上传频次
    	List<Map<String,Object>> list4 = userClusterService.findPlayhavByMapParam(params);//用户互动频次
    	params.clear();
    	params.put("total", handleRetainDatas(list1));
    	params.put("create", handleRetainDatas(list2));
    	params.put("upload", handleRetainDatas(list3));
    	params.put("play", handleRetainDatas(list4));
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, params, "");
    }
    
    private Map<String,Object> handleRetainDatas(List<Map<String,Object>> list){
    	if(list == null || list.isEmpty())return null;
    	/**
    	 * legend 用户等级
		   xAxis 周期
		        每等级用户对应的数据  用户等级对应频次
     	 */
    	Map<String,Object> rsMap = new HashMap<String,Object>();
    	List<String> legend = new ArrayList<String>();
    	legend.add("超级用户");
    	legend.add("高级用户");
    	legend.add("中级用户");
    	legend.add("低级用户");
    	legend.add("流失用户");
    	List<String> xAxis = new ArrayList<String>();
    	
    	String period = "";
    	for(Map<String,Object> item : list) {
    		period = (String)item.get("a");
    		if(xAxis.indexOf(period) == -1) {
    			xAxis.add(period);
    		}
    	}
    	
    	List<Map<String,Object>> seris = new ArrayList<Map<String,Object>>();
    	
    	for(String bean : legend) {
    		Map<String,Object> legendMap = new HashMap<String,Object>();
    		List<Object> stsList = new ArrayList<Object>();
    		for(String x : xAxis) {
    			stsList.add(findItemData(x, bean, list));
    		}
    		legendMap.put("name", bean);
    		legendMap.put("list", stsList);
    		seris.add(legendMap);
    	}
    	rsMap.put("legend", legend);
    	rsMap.put("xAxis", xAxis);
    	rsMap.put("seris", seris);
    	return rsMap;
    }
    
    /**
     * @param x 统计周期
     * @param y 用户等级
     * @param list
     * @return
     */
    private double findItemData(String x,String y,List<Map<String,Object>> list) {
    	double rs = 0D;
    	for(Map<String,Object> map : list){
    		if(StringUtils.equals(x, (String)map.get("a")) && StringUtils.equals(y, (String)map.get("b"))) {
    			BigDecimal bb = ((BigDecimal)map.get("c"));
    			bb = bb.setScale(2, BigDecimal.ROUND_HALF_UP);
    			rs = Double.valueOf(bb.toString());
    			break;
    		}
    	}
    	return rs;
    }
    
    
}
