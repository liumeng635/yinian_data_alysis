package com.yinian.alysis.util;

import com.alibaba.fastjson.JSONObject;

public class ResultsHelper {
	public final static String ERROR_CODE = "500"; 
	public final static String SUC_CODE = "200";
	
	public static JSONObject putResults(String code,Object data,String errMsg) {
		JSONObject rsJson = new JSONObject();
		rsJson.put("data", data);
		rsJson.put("code", code);
		rsJson.put("msg", errMsg);
		return rsJson;
	}
	
	public static String covertType2Name(Integer type) {
    	if(type == null) {
    		return "";
    	}
    	switch (type) {
    		case 1 : return "朋友";
    		case 2 : return "聚会";
    		case 3 : return "个人";
    		case 4 : return "家人";
    		case 5 : return "宝宝";
    		case 6 : return "出游";
    		case 7 : return "团体";
    		case 8 : return "兴趣";
    		case 9 : return "同学";
    		case 10 : return "同事";
    		case 11 : return "情侣";
    		case 12 : return "活动";
    		case 13 : return "其它";
    		case 14 : return "宠物";
    		case 15 : return "组织";
    		case 20 : return "摄影";
    		case 25 : return "粉丝";
    		default : return "";
    	}
    }
}
