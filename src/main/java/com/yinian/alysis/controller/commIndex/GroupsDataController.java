package com.yinian.alysis.controller.commIndex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import com.yinian.alysis.model.GroupRetention;
import com.yinian.alysis.service.GroupRetentionService;
import com.yinian.alysis.util.CalcTool;
import com.yinian.alysis.util.DateUtils;
import com.yinian.alysis.util.ResultsHelper;


@Controller
public class GroupsDataController {
	@Autowired
	private GroupRetentionService groupRetentionService;
	
	
	@RequestMapping("/groupsRetain")
    public String groupsRetain(Model model) {
    	return "commIndex/groupsRetain";
    }
	
    @RequestMapping("/renderGroupsStsData")
    @ResponseBody
    public JSONObject renderGroupsStsData(String time){
    	Map<String,Object> params = new HashMap<String,Object>();
    	String[] dataType = new String[]{"当天","3天内","4-7天内","7天内"}; 
    	params.put("time", time);
    	//去新增留存数据
    	params.put("dataType", 0);
    	List<GroupRetention> ridList = groupRetentionService.findByMapParam(params);
    	//对去留存数据进行加工
    	Map<String,Object> ridData =  hanlderGroupsData(ridList, dataType);
    	//非去新增留存数据
    	params.put("dataType", 1);
    	List<GroupRetention> notRidList = groupRetentionService.findByMapParam(params);
    	Map<String,Object> notRidData =  hanlderGroupsData(notRidList, dataType);
    	JSONObject rs = new JSONObject();
    	rs.put("ridData", JSONObject.toJSON(ridData));
    	rs.put("notRidData", JSONObject.toJSON(notRidData));
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, rs, "");
    }
    
    
    
    @RequestMapping("/renderGroupsStsDataAll")
    @ResponseBody
    public JSONObject renderGroupsStsDataAll(){
    	Map<String,Object> params = new HashMap<String,Object>();
    	String[] dataType = new String[]{"当天","3天内","4-7天内","7天内"}; 
    	JSONObject rs = new JSONObject();
    	//统计全部相册的活跃数据
    	List<Map<String,Object>> dateList = groupRetentionService.listAllDate();
		//去新增
		params.clear();
		params.put("dataType", 0);
//		List<GroupRetention> totolRidlst = groupRetentionService.listAllNewestRcd(params);
		List<GroupRetention> totolRidlst = groupRetentionService.findByMapParam(params);
		List<Map<String,Object>> ridTotal = hanlderGroupsTotData(totolRidlst, dataType, dateList,0);
		Collections.sort(ridTotal,new Comparator<Map<String,Object>>(){
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				String key1 = (String)o1.get("date");
				long dateTime1 = DateUtils.str2Date(key1, "yyyy-MM-dd").getTime();
				
				String key2 = (String)o2.get("date");;
				long dateTime2 = DateUtils.str2Date(key2, "yyyy-MM-dd").getTime();
				
				if(dateTime1>dateTime2) {
					return -1;
				}else if(dateTime1<dateTime2) {
					return 1;
				}else {
					return 0;
				}
			}
			
		});
		
		
		//非去新增
		params.clear();
		params.put("dataType", 1);
		List<GroupRetention> totolNotRidlst = groupRetentionService.findByMapParam(params);
		List<Map<String,Object>> notRidTotal = hanlderGroupsTotData(totolNotRidlst, dataType, dateList,1);
		
		Collections.sort(notRidTotal,new Comparator<Map<String,Object>>(){
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				String key1 = (String)o1.get("date");
				long dateTime1 = DateUtils.str2Date(key1, "yyyy-MM-dd").getTime();
				
				String key2 = (String)o2.get("date");;
				long dateTime2 = DateUtils.str2Date(key2, "yyyy-MM-dd").getTime();
				
				if(dateTime1>dateTime2) {
					return -1;
				}else if(dateTime1<dateTime2) {
					return 1;
				}else {
					return 0;
				}
			}
			
		});
		
		rs.put("ridTotal", ridTotal);
		rs.put("notRidTotal", notRidTotal);
    	
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, rs, "");
    }
    
    
    public List<Map<String,Object>> hanlderGroupsTotData(List<GroupRetention> list,String[] dataType,List<Map<String,Object>> dateList,int type){
    	List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
    	CalcTool tool = new CalcTool(1);
		tool.setScale(2);
    	Map<String,Object> rsMap = null;//每天的数据汇总
    	String date = null;
    	List<GroupRetention> lst = null;
    	rsMap = new HashMap<String,Object>();
    	for(Map map : dateList) {
			Date d = (Date)map.get("date");
    		lst = new ArrayList<GroupRetention>();
    		date = DateUtils.getSpecifyDate(d, "yyyy-MM-dd");
    		for(GroupRetention bean : list){
    			if(StringUtils.equals(date, DateUtils.getSpecifyDate(bean.getDate(), "yyyy-MM-dd")) /*&& bean.getDataType() == type*/) {
    				lst.add(bean);
    			}
    		}
    		rsMap.put(date, lst);
    	}
    	
    	Map<String,Object> stsMap = null;
		for(String key : rsMap.keySet()) {
			List<GroupRetention> dlist = (List<GroupRetention>)rsMap.get(key);
			int dang = 0;
			int day3 = 0;
			int day47 = 0;
			int day7 = 0;
			for(GroupRetention re : dlist) {
				if(StringUtils.equals(re.getPeriod(), dataType[0])) {//当天
					dang = dang+re.getCount();
				}
				if(StringUtils.equals(re.getPeriod(), dataType[1])) {//3天内
					day3 = day3+re.getCount();
				}
				if(StringUtils.equals(re.getPeriod(), dataType[2])) {//4-7天内
					day47 = day47+re.getCount();
				}
				if(StringUtils.equals(re.getPeriod(), dataType[3])) {//7天内
					day7 = day7+re.getCount();
				}
			}
			
			stsMap = new HashMap<String,Object>();
			stsMap.put("date", key);
			stsMap.put("count1", dang);
			stsMap.put("count2", dang == 0 ? "0%" : tool.div(Long.valueOf(day3*100), Long.valueOf(dang)) + "%");
			stsMap.put("count3", dang == 0 ? "0%" : tool.div(Long.valueOf(day47*100), Long.valueOf(dang)) + "%");
			stsMap.put("count4", dang == 0 ? "0%" : tool.div(Long.valueOf(day7*100), Long.valueOf(dang)) + "%");
			rsList.add(stsMap);
		}
    	
    	return rsList;
    }
    
    
    public Map<String,Object> hanlderGroupsData(List<GroupRetention> list,String[] dataType) {
    	Map<String,Object> rsMap = new HashMap<String,Object>();
    	CalcTool tool = new CalcTool(1);
		tool.setScale(2);
    	//对去留存数据进行加工
    	List<GroupRetention> ridTbList1 = new ArrayList<GroupRetention>();
    	List<GroupRetention> ridTbList2 = new ArrayList<GroupRetention>();
    	List<GroupRetention> ridTbList3 = new ArrayList<GroupRetention>();
    	List<GroupRetention> ridTbList4 = new ArrayList<GroupRetention>();
    	
    	for(GroupRetention bean : list) {
    		String period = bean.getPeriod();
    		if(StringUtils.equals(period, dataType[0])) {
    			ridTbList1.add(bean);
    		}
    		if(StringUtils.equals(period, dataType[1])) {
    			ridTbList2.add(bean);
    		}
    		if(StringUtils.equals(period, dataType[2])) {
    			ridTbList3.add(bean);
    		}
    		if(StringUtils.equals(period, dataType[3])) {
    			ridTbList4.add(bean);
    		}
    	}
    	
    	//获取相册类型的最大个数值
    	List<Integer> ridGroupsTypes = new ArrayList<Integer>();
    	
    	List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
    	Map<String,Object> map1 = new HashMap<String,Object>();
    	Map<String,Object> map2 = new HashMap<String,Object>();
    	Map<String,Object> map3 = new HashMap<String,Object>();
    	Map<String,Object> map4 = new HashMap<String,Object>();
    	
    	map1.put("size",ridTbList1.size());
    	map1.put("list", ridTbList1);
    	mapList.add(map1);
    	
    	map2.put("size",ridTbList2.size());
    	map2.put("list", ridTbList2);
    	mapList.add(map2);
    	
    	map3.put("size",ridTbList3.size());
    	map3.put("list", ridTbList3);
    	mapList.add(map3);
    	
    	map4.put("size",ridTbList4.size());
    	map4.put("list", ridTbList4);
    	mapList.add(map4);
    	
    	//降序
    	Collections.sort(mapList, new Comparator<Map<String,Object>>() {
			@Override
			public int compare(Map<String,Object> o1, Map<String,Object> o2) {
				int key1 = (int)o1.get("size");
				int key2 = (int)o2.get("size");
				if(key1 > key2) {
					return -1;
				}else if(key1 == key2) {
					return 0;
				}else {
					return 1;
				}
			}
		});
    	
    	//得到最大
    	for(GroupRetention rt : (List<GroupRetention>)mapList.get(0).get("list")) {
    		ridGroupsTypes.add(rt.getGroupType());
    	}
    	
    	List<Map<String,Object>> mapList1 = new ArrayList<Map<String,Object>>();
    	List<Map<String,Object>> mapList2 = new ArrayList<Map<String,Object>>();
    	List<Map<String,Object>> mapList3 = new ArrayList<Map<String,Object>>();
    	List<Map<String,Object>> mapList4 = new ArrayList<Map<String,Object>>();
    	
    	
    	Map<String,Object> mapp1 = null;
    	Map<String,Object> mapp2 = null;
    	Map<String,Object> mapp3 = null;
    	Map<String,Object> mapp4 = null;
    	
    	for(Integer type : ridGroupsTypes) {//根据相册类型统计
    		String groupTypeName = ResultsHelper.covertType2Name(type);
    		//当天
    		GroupRetention data1 = getGroupRetentionByType(type, ridTbList1);
    		mapp1 = new HashMap<String,Object>();
    		mapp1.put("groupType", groupTypeName);
    		if(data1 == null) {
    			mapp1.put("create", 0);
    			mapp1.put("upload", 0);
    			mapp1.put("comment", 0);
    			mapp1.put("like", 0);
    			mapp1.put("invite", 0);
    			mapp1.put("total", 0);
    		}else {
    			mapp1.put("create", data1.getCreateCount());
    			mapp1.put("upload", data1.getUploadCount());
    			mapp1.put("comment", data1.getCommentCount());
    			mapp1.put("like", data1.getLikeCount());
    			mapp1.put("invite", data1.getInviteCount());
    			mapp1.put("total", data1.getCount());
    		}
    		mapList1.add(mapp1);
    		
    		
    		//3天内
    		GroupRetention data2 = getGroupRetentionByType(type, ridTbList2);
    		mapp2 = new HashMap<String,Object>();
    		mapp2.put("groupType", groupTypeName);
    		if(data2 == null) {
    			mapp2.put("create", 0);
    			mapp2.put("upload", 0);
    			mapp2.put("comment", 0);
    			mapp2.put("like", 0);
    			mapp2.put("invite", 0);
    			mapp2.put("total", 0);
    			mapp2.put("rate", "0%");
    		}else {
    			mapp2.put("create", data2.getCreateCount());
    			mapp2.put("upload", data2.getUploadCount());
    			mapp2.put("comment", data2.getCommentCount());
    			mapp2.put("like", data2.getLikeCount());
    			mapp2.put("invite", data2.getInviteCount());
    			int count = data2.getCount();
    			mapp2.put("total",count);
    			//留存率
    			int countData = data1.getCount();
    			String rate = countData == 0 ?  "0%" : tool.div(Long.valueOf(count*100), Long.valueOf(countData)) + "%";
    			mapp2.put("rate", rate);
    		}
    		mapList2.add(mapp2);
    		
    		//4-7天内
    		GroupRetention data3 = getGroupRetentionByType(type, ridTbList3);
    		mapp3 = new HashMap<String,Object>();
    		mapp3.put("groupType", groupTypeName);
    		if(data3 == null) {
    			mapp3.put("create", 0);
    			mapp3.put("upload", 0);
    			mapp3.put("comment", 0);
    			mapp3.put("like", 0);
    			mapp3.put("invite", 0);
    			mapp3.put("total", 0);
    			mapp3.put("rate", "0%");
    		}else {
    			mapp3.put("create", data3.getCreateCount());
    			mapp3.put("upload", data3.getUploadCount());
    			mapp3.put("comment", data3.getCommentCount());
    			mapp3.put("like", data3.getLikeCount());
    			mapp3.put("invite", data3.getInviteCount());
    			int count = data3.getCount();
    			mapp3.put("total",count);
    			//留存率
    			int countData = data1.getCount();
    			String rate = countData == 0 ?  "0%" :  tool.div(Long.valueOf(count*100), Long.valueOf(countData)) + "%";
    			mapp3.put("rate", rate);
    		}
    		mapList3.add(mapp3);
    		
    		//7天内
    		GroupRetention data4 = getGroupRetentionByType(type, ridTbList4);
    		mapp4 = new HashMap<String,Object>();
    		mapp4.put("groupType", groupTypeName);
    		if(data4 == null) {
    			mapp4.put("create", 0);
    			mapp4.put("upload", 0);
    			mapp4.put("comment", 0);
    			mapp4.put("like", 0);
    			mapp4.put("invite", 0);
    			mapp4.put("total", 0);
    			mapp4.put("rate", "0%");
    		}else {
    			mapp4.put("create", data4.getCreateCount());
    			mapp4.put("upload", data4.getUploadCount());
    			mapp4.put("comment", data4.getCommentCount());
    			mapp4.put("like", data4.getLikeCount());
    			mapp4.put("invite", data4.getInviteCount());
    			int count = data4.getCount();
    			mapp4.put("total",count);
    			//留存率
    			int countData = data1.getCount();
    			String rate = countData == 0 ?  "0%" :  tool.div(Long.valueOf(count*100), Long.valueOf(countData)) + "%";
    			mapp4.put("rate", rate);
    		}
    		mapList4.add(mapp4);
    	}
    	
    	rsMap.put("data1", mapList1);
    	rsMap.put("data2", mapList2);
    	rsMap.put("data3", mapList3);
    	rsMap.put("data4", mapList4);
    	
    	return rsMap;
    	
    }
    
    public GroupRetention getGroupRetentionByType(Integer type,List<GroupRetention> list) {
    	GroupRetention rs = null;
    	for(GroupRetention bean : list) {
    		if(type == bean.getGroupType()) {
    			rs = bean;
    			break;
    		}
    	}
    	return rs;
    }
    
    public boolean typeInData(Integer type,List<GroupRetention> list) {
    	boolean is = false;
    	for(GroupRetention bean : list) {
    		if(type == bean.getGroupType()) {
    			is = true;
    			break;
    		}
    	}
    	return is;
    }
    
    public static void main(String[] args) {
    	System.out.println(DateUtils.str2Date("2015-08-09", "yyyy-MM-dd"));
	}
}

