package com.yinian.alysis.controller.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yinian.alysis.model.ActivityNewTarget;
import com.yinian.alysis.model.PushAnalysis;
import com.yinian.alysis.service.ActivityNewTargetService;
import com.yinian.alysis.service.PushAnalysisService;
import com.yinian.alysis.util.CalcTool;
import com.yinian.alysis.util.ResultsHelper;


@Controller
public class ActStageController {
	@Autowired
	private ActivityNewTargetService  activityNewTargetService;
	
	@Autowired
	private PushAnalysisService  pushAnalysisService;
	
	
	@RequestMapping("/actStageOne")
	public String actStageOne(Model model) {
		return "product/actStageOne";
	}
	
	@RequestMapping("/actStageTwo")
    public String actStageTwo(Model model) {
    	return "product/actStageTwo";
    }
	
	@RequestMapping("/livingPush")
    public String livingPush(Model model) {
    	return "product/livingPush";
    }
	
    @RequestMapping("/renderActStageData")
    @ResponseBody
    public JSONObject renderActStageData(String groupid,String startTime,String endTime,String hdIndex,String isDefault){
    	groupid = StringUtils.isEmpty(groupid) ? "0" : groupid;
    	Map<String,Object> params = new HashMap<String,Object>();
		params.put("groupid", Integer.valueOf(groupid));
		if(null != isDefault) {
			params.put("isDefault", isDefault);
		}else {
			params.put("startTime", startTime);
			params.put("endTime", endTime);
		}
		ActivityNewTarget rs = null;
		List<ActivityNewTarget> list = activityNewTargetService.findByMapParam(params);
		if(list != null && !list.isEmpty()) {
			rs = list.get(0);
		}
//		DBHelper db = DBHelper.getInstance();
//		String sql = "select * from wuyi_mid_table t where 1=1 and period >= str_to_date('"+endTime+"','%Y-%m-%d %H:%m:%s') and period > str_to_date('"+startTime+"','%Y-%m-%d %H:%m:%s') and t.type like '"+hdIndex+"%'";
//		List<Map<String, Object>> processData = null;
		try {
//			processData = db.query(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		params.clear();
//		params.put("processData", processData);
		params.put("rsData", rs);
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, params, "");
    }
    
    @RequestMapping("/renderLivingPushData")
    @ResponseBody
    public JSONObject renderLivingPushData(String time){
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("time", time);
    	List<PushAnalysis> pushList = pushAnalysisService.getUserByMapParam(params);
    	
    	System.out.println(pushList.size());
    	
    	String[] dataTypes1 = new String[] {"推送总量","推送失败(无formid)","推送成功"};
    	String[] dataTypes2 = new String[] {"点击通知","进入精准相册","进入关联相册","进入相册","进入精准相册有行为","进入关联相册有行为","有行为"};
    	String[] havTypes = new String[] {"Event","Comment","Like","Invite","Join","小计"};
    	
    	Map<String,Object> map1 = handleGrid1(pushList, dataTypes1, havTypes);
    	
    	//推送表格2=======================华丽的分割线=================
    	Map<String,Object> map2 = handleGrid2(pushList, dataTypes2, havTypes,map1);
    	
    	//推送表格3
    	List<Map<String,Object>> dateAll = pushAnalysisService.listAllDate();
    		//得到所有的数据
    	params.clear();
    	params.put("operType","小计");
    	List<PushAnalysis> pushListAll = pushAnalysisService.getUserByMapParam(params);
    	
    	List<Map<String,Object>> allList = new ArrayList<Map<String,Object>>();
    	String osDate = null;
    	Map<String,Object> hdMap = null;
    	Map<String,Object> hdMap1 = null;
    	List<PushAnalysis>  tempL = null;
    	for(Map<String,Object> mm : dateAll) {
    		osDate = DateFormatUtils.format((Date)mm.get("date"),"yyyy-MM-dd");
    		tempL = getDataGroupList(osDate, pushListAll);
    		hdMap = handleGridAll1(osDate,tempL,dataTypes1);
    		hdMap1 = handleGridAll2(tempL, dataTypes2, hdMap);
    		hdMap1.putAll((Map)hdMap.get("map"));
    		allList.add(hdMap1);
    	}
    	
    	params.clear();
    	params.put("data1",JSONObject.toJSON(map1));
    	params.put("data2",JSONObject.toJSON(map2));
    	params.put("data3",allList);
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, params, "");
    }
    
    /**
     * 根据日期获取统计数据
     * @param date
     * @param pushListAll
     * @return
     */
    public List<PushAnalysis>  getDataGroupList(String date,List<PushAnalysis> pushListAll){
    	List<PushAnalysis> list = new ArrayList<PushAnalysis>();
    	for(PushAnalysis bean : pushListAll) {
    		if(StringUtils.equals(DateFormatUtils.format(bean.getPushTime(), "yyyy-MM-dd"), date)){
    			list.add(bean);
    		}
    	}
    	return list;
    }
    
    
    /**
     * 表格1数据处理
     * @param list
     * @param dateType
     * @param havTypes
     * @return
     */
    public Map<String,Object> handleGrid1(List<PushAnalysis> list,String[] dateType,String[] havTypes){
    	Map<String,Object> rsMap =new HashMap<String,Object>();
    	CalcTool tool = new CalcTool(1);
		tool.setScale(2);
    	//推送表格1 =="推送总量","推送失败(无formid)","推送成功"
    	List<PushAnalysis> lst1 = new ArrayList<PushAnalysis>();
    	List<PushAnalysis> lst2 = new ArrayList<PushAnalysis>();
    	List<PushAnalysis> lst3 = new ArrayList<PushAnalysis>();
    	for(PushAnalysis bean : list) {
    		if(StringUtils.equals(dateType[0], bean.getOperation())) {
    			lst1.add(bean);
    		}
    		if(StringUtils.equals(dateType[1], bean.getOperation())) {
    			lst2.add(bean);
    		}
    		if(StringUtils.equals(dateType[2], bean.getOperation())) {
    			lst3.add(bean);
    		}
    	}
    	
    	List<Map<String,Object>> rsMapList1 = new ArrayList<Map<String,Object>>();
    	Map<String,Object> tempMap = null;
    	List<Integer> pushTotTypes = returnMaxGroupTypes(lst1);
    	List<Integer> pushTotTypes1 = returnMaxGroupTypes(lst3);
    	
    	
    	for(String oper : havTypes) {
    		tempMap = new HashMap<String,Object>();
    		tempMap.put("data0", returnHavName(oper));
    		tempMap.put("data_0", returnHavName(oper));
    		
    		//每种类型的小计
    		int cnt1 = 0;
    		int cnt_1 = 0;
    		for(int i = 0;i<pushTotTypes.size();i++) {
    			PushAnalysis p = getPushAnalysis(pushTotTypes.get(i), oper, lst1);
    			if(p == null) {
    				tempMap.put("data"+(i+1), 0);
    				tempMap.put("data_"+(i+1), 0);
    				cnt1 += 0;
    				cnt_1 += 0;
    			}else {
    				tempMap.put("data"+(i+1), p.getUsersCount());//人数
    				tempMap.put("data_"+(i+1), p.getOperationCount());//操作次数
    				cnt1 += p.getUsersCount();
    				cnt_1 += p.getOperationCount();
    			}
    		}
    		int beginIndex = pushTotTypes.size()+1;
    		tempMap.put("data"+beginIndex,cnt1);
    		tempMap.put("data_"+beginIndex,cnt_1);
    		beginIndex = beginIndex +1;
    		//无fromId推送失败
    		PushAnalysis p1 = getPushAnalysis1(oper, lst2);
    		
    		int falilCnt1 = 0;
    		int falilCnt_1 = 0;
    		if(p1 != null) {
    			falilCnt1 = p1.getUsersCount();
    			falilCnt_1 = p1.getOperationCount();
    		}
    		tempMap.put("data"+beginIndex,falilCnt1);
    		tempMap.put("data_"+beginIndex,falilCnt_1);
    		
    		
    		beginIndex = beginIndex +1;
    		//失败率
    		tempMap.put("data"+beginIndex, cnt1 == 0 ? "0%" : tool.div(Long.valueOf(falilCnt1*100), Long.valueOf(cnt1)) + "%");
    		tempMap.put("data_"+beginIndex, cnt_1 == 0 ? "0%" : tool.div(Long.valueOf(falilCnt_1*100), Long.valueOf(cnt_1)) + "%");
    		
    		beginIndex = beginIndex +1;
    		
    		//推送成功
    		
    		//每种类型的小计
    		int cnt21 = 0;
    		int cnt2_1 = 0;
    		for(int i = 0;i<pushTotTypes1.size();i++) {
    			PushAnalysis p_ = getPushAnalysis(pushTotTypes1.get(i), oper, lst3);
    			if(p_ == null) {
    				tempMap.put("data"+(i+beginIndex), 0);
    				tempMap.put("data_"+(i+beginIndex), 0);
    				cnt21 += 0;
    				cnt2_1 += 0;
    			}else {
    				tempMap.put("data"+(i+beginIndex), p_.getUsersCount());//人数
    				tempMap.put("data_"+(i+beginIndex), p_.getOperationCount());//操作次数
    				cnt21 += p_.getUsersCount();
    				cnt2_1 += p_.getOperationCount();
    			}
    		}
    		beginIndex += pushTotTypes1.size();
    		tempMap.put("data"+beginIndex,cnt21);
    		tempMap.put("data_"+beginIndex,cnt2_1);
    		beginIndex = beginIndex + 1;
    		
    		//推送成功率
    		tempMap.put("data"+beginIndex, cnt1 == 0 ? "0%" : tool.div(Long.valueOf(cnt21*100), Long.valueOf(cnt1)) + "%");
    		tempMap.put("data_"+beginIndex, cnt_1 == 0 ? "0%" : tool.div(Long.valueOf(cnt2_1*100), Long.valueOf(cnt_1)) + "%");
    		
    		rsMapList1.add(tempMap);
    		
    		rsMap.put(oper,cnt21);
    		rsMap.put(oper+"_",cnt2_1);
    	}
    	rsMap.put("groupsType1",getTypeList(pushTotTypes));
		rsMap.put("groupsType2",getTypeList(pushTotTypes1));
		rsMap.put("list",rsMapList1);
    	return rsMap;
    }
    
    
    public Map<String,Object> handleGridAll1(String date,List<PushAnalysis> list,String[] dateType){
    	Map<String,Object> rsMap =new HashMap<String,Object>();
    	CalcTool tool = new CalcTool(1);
		tool.setScale(2);
    	//推送表格1 =="推送总量","推送失败(无formid)","推送成功"
    	List<PushAnalysis> lst1 = new ArrayList<PushAnalysis>();
    	List<PushAnalysis> lst2 = new ArrayList<PushAnalysis>();
    	List<PushAnalysis> lst3 = new ArrayList<PushAnalysis>();
    	for(PushAnalysis bean : list) {
    		if(StringUtils.equals(dateType[0], bean.getOperation())) {
    			lst1.add(bean);
    		}
    		if(StringUtils.equals(dateType[1], bean.getOperation())) {
    			lst2.add(bean);
    		}
    		if(StringUtils.equals(dateType[2], bean.getOperation())) {
    			lst3.add(bean);
    		}
    	}
    	
    	Map<String,Object> tempMap = null;
    	List<Integer> pushTotTypes = returnMaxGroupTypes(lst1);
    	List<Integer> pushTotTypes1 = returnMaxGroupTypes(lst3);
    	String oper = "小计";
    	
		tempMap = new HashMap<String,Object>();
		tempMap.put("data0", date);
		tempMap.put("data_0", date);
		
		//每种类型的小计
		int cnt1 = 0;
		int cnt_1 = 0;
		for(int i = 0;i<pushTotTypes.size();i++) {
			PushAnalysis p = getPushAnalysis(pushTotTypes.get(i), oper, lst1);
			if(p == null) {
				cnt1 += 0;
				cnt_1 += 0;
			}else {
				cnt1 += p.getUsersCount();
				cnt_1 += p.getOperationCount();
			}
		}
		tempMap.put("data1",cnt1);
		tempMap.put("data_1",cnt_1);
		int beginIndex =2;
		//无fromId推送失败
		PushAnalysis p1 = getPushAnalysis1(oper, lst2);
		
		int falilCnt1 = 0;
		int falilCnt_1 = 0;
		if(p1 != null) {
			falilCnt1 = p1.getUsersCount();
			falilCnt_1 = p1.getOperationCount();
		}
		tempMap.put("data"+beginIndex,falilCnt1);
		tempMap.put("data_"+beginIndex,falilCnt_1);
		
		
		beginIndex = beginIndex +1;
		//失败率
		tempMap.put("data"+beginIndex, cnt1 == 0 ? "0%" : tool.div(Long.valueOf(falilCnt1*100), Long.valueOf(cnt1)) + "%");
		tempMap.put("data_"+beginIndex, cnt_1 == 0 ? "0%" : tool.div(Long.valueOf(falilCnt_1*100), Long.valueOf(cnt_1)) + "%");
		
		beginIndex = beginIndex +1;
		
		//推送成功
		
		//每种类型的小计
		int cnt21 = 0;
		int cnt2_1 = 0;
		for(int i = 0;i<pushTotTypes1.size();i++) {
			PushAnalysis p_ = getPushAnalysis(pushTotTypes1.get(i), oper, lst3);
			if(p_ == null) {
				cnt21 += 0;
				cnt2_1 += 0;
			}else {
				cnt21 += p_.getUsersCount();
				cnt2_1 += p_.getOperationCount();
			}
		}
		tempMap.put("data"+beginIndex,cnt21);
		tempMap.put("data_"+beginIndex,cnt2_1);
		beginIndex = beginIndex + 1;
		
		//推送成功率
		tempMap.put("data"+beginIndex, cnt1 == 0 ? "0%" : tool.div(Long.valueOf(cnt21*100), Long.valueOf(cnt1)) + "%");
		tempMap.put("data_"+beginIndex, cnt_1 == 0 ? "0%" : tool.div(Long.valueOf(cnt2_1*100), Long.valueOf(cnt_1)) + "%");
		rsMap.put("map", tempMap);
		rsMap.put(oper,cnt21);
		rsMap.put(oper+"_",cnt2_1);
		rsMap.put("index",beginIndex+1);
    	return rsMap;
    }
    
    /**
     * 表格2数据处理
     * @param list
     * @param dateType
     * @param havTypes
     * @return
     */
    public Map<String,Object> handleGrid2(List<PushAnalysis> list,String[] dateType,String[] havTypes,Map<String,Object> oneTbMap){
    	
    	Map<String,Object> rsMap = new HashMap<String,Object>();
    	
    	//{"点击通知","进入精准相册","进入关联相册","进入相册","有行为","进入精准相册有行为","进入关联相册有行为"}
    	CalcTool tool = new CalcTool(1);
    	tool.setScale(2);
    	//推送表格1 =="推送总量","推送失败(无formid)","推送成功"
    	List<PushAnalysis> lst1 = new ArrayList<PushAnalysis>();
    	List<PushAnalysis> lst2 = new ArrayList<PushAnalysis>();
    	List<PushAnalysis> lst3 = new ArrayList<PushAnalysis>();
    	List<PushAnalysis> lst4 = new ArrayList<PushAnalysis>();
    	List<PushAnalysis> lst5 = new ArrayList<PushAnalysis>();
    	List<PushAnalysis> lst6 = new ArrayList<PushAnalysis>();
    	List<PushAnalysis> lst7 = new ArrayList<PushAnalysis>();
    	for(PushAnalysis bean : list) {
    		if(StringUtils.equals(dateType[0], bean.getOperation())) {
    			lst1.add(bean);
    		}
    		if(StringUtils.equals(dateType[1], bean.getOperation())) {
    			lst2.add(bean);
    		}
    		if(StringUtils.equals(dateType[2], bean.getOperation())) {
    			lst3.add(bean);
    		}
    		if(StringUtils.equals(dateType[3], bean.getOperation())) {
    			lst4.add(bean);
    		}
    		if(StringUtils.equals(dateType[4], bean.getOperation())) {
    			lst5.add(bean);
    		}
    		if(StringUtils.equals(dateType[5], bean.getOperation())) {
    			lst6.add(bean);
    		}
    		if(StringUtils.equals(dateType[6], bean.getOperation())) {
    			lst7.add(bean);
    		}
    	}
    	
    	List<Map<String,Object>> rsMapList1 = new ArrayList<Map<String,Object>>();
    	Map<String,Object> tempMap = null;
    	List<Integer> pushTotTypes = returnMaxGroupTypes(lst2);
    	List<Integer> pushTotTypes1 = returnMaxGroupTypes(lst3);
    	
    	
    	for(String oper : havTypes) {
    		Integer cnt1 = (Integer)oneTbMap.get(oper);//人数总计
    		Integer cnt_1 = (Integer)oneTbMap.get(oper+"_");//操作总计
    		
    		tempMap = new HashMap<String,Object>();
    		tempMap.put("data0", returnHavName(oper));
    		tempMap.put("data_0", returnHavName(oper));
    		
    		//进入落地页
    		int inPage1 = 0;
    		int inPage_1 = 0;
    		PushAnalysis pa = getPushAnalysis1(oper, lst1);
    		if(pa == null) {
    			tempMap.put("data1", 0);
        		tempMap.put("data_1", 0);
    		}else {
    			tempMap.put("data1", pa.getUsersCount());
        		tempMap.put("data_1", pa.getOperationCount());
        		inPage1 = pa.getUsersCount();
        		inPage_1 = pa.getOperationCount();
    		}
    		
    		
    		tempMap.put("data2", cnt1 == 0 ? "0%" : tool.div(Long.valueOf(inPage1*100), Long.valueOf(cnt1)) + "%");
    		tempMap.put("data_2", cnt_1 == 0 ? "0%" : tool.div(Long.valueOf(inPage_1*100), Long.valueOf(cnt_1)) + "%");
    		
    		//进入相册
    		//每种类型的小计===精准相册
    		int cntt1 = 0;
    		int cntt2 = 0;
    		for(int i = 0;i<pushTotTypes.size();i++) {
    			PushAnalysis p = getPushAnalysis(pushTotTypes.get(i), oper, lst2);
    			if(p == null) {
    				tempMap.put("data"+(i+3), 0);
    				tempMap.put("data_"+(i+3), 0);
    			}else {
    				tempMap.put("data"+(i+3), p.getUsersCount());//人数
    				cntt1 += p.getUsersCount();
    				tempMap.put("data_"+(i+3), p.getOperationCount());//操作次数
    				cntt2 += p.getOperationCount();
    			}
    		}
    		int beginIndex = pushTotTypes.size() + 3;
    		tempMap.put("data"+beginIndex, cntt1);
    		tempMap.put("data_"+beginIndex, cntt2);
    		
    		beginIndex = beginIndex +1;
    		//每种类型的小计==关联相册
    		int cntt_1 = 0;
    		int cntt_2 = 0;
    		for(int i = 0;i<pushTotTypes1.size();i++) {
    			PushAnalysis p = getPushAnalysis(pushTotTypes1.get(i), oper, lst3);
    			if(p == null) {
    				tempMap.put("data"+(i+beginIndex), 0);
    				tempMap.put("data_"+(i+beginIndex), 0);
    			}else {
    				tempMap.put("data"+(i+beginIndex), p.getUsersCount());//人数
    				tempMap.put("data_"+(i+beginIndex), p.getOperationCount());//操作次数
    				cntt_1 += p.getUsersCount();
    				cntt_2 += p.getOperationCount();
    			}
    		}
    		beginIndex = beginIndex + pushTotTypes1.size();
    		
    		tempMap.put("data"+beginIndex, cntt_1);
    		tempMap.put("data_"+beginIndex, cntt_2);
    		
    		beginIndex = beginIndex +1;
    		
    		
    		//进入相册的小计
    		
    		PushAnalysis p1 = getPushAnalysis1(oper, lst4);
    		
    		int inCnt1 = 0;
    		int inCnt_1 = 0;
    		if(p1 != null) {
    			inCnt1 = p1.getUsersCount();
    			inCnt_1 = p1.getOperationCount();
    		}
    		
    		//合计
    		tempMap.put("data"+beginIndex,inCnt1);
    		tempMap.put("data_"+beginIndex,inCnt_1);
    		
    		beginIndex = beginIndex +1;
    		
    		//进入率
    		tempMap.put("data"+beginIndex, inPage1 == 0 ? "0%" : tool.div(Long.valueOf(inCnt1*100), Long.valueOf(inPage1)) + "%");
    		tempMap.put("data_"+beginIndex, inPage_1 == 0 ? "0%" : tool.div(Long.valueOf(inCnt_1*100), Long.valueOf(inPage_1)) + "%");
    		
    		beginIndex = beginIndex +1;
    		
    		//产生行为
    		//精准
    		PushAnalysis p2 = getPushAnalysis1(oper, lst5);
    		if(p2 == null) {
    			tempMap.put("data"+beginIndex, 0);
        		tempMap.put("data_"+beginIndex, 0);
    		}else {
    			tempMap.put("data"+beginIndex, p2.getUsersCount());
        		tempMap.put("data_"+beginIndex, p2.getOperationCount());
    		}
    		
    		beginIndex = beginIndex +1;
    		
    		//关联
    		PushAnalysis p3 = getPushAnalysis1(oper, lst6);
    		if(p3 == null) {
    			tempMap.put("data"+beginIndex, 0);
        		tempMap.put("data_"+beginIndex, 0);
    		}else {
    			tempMap.put("data"+beginIndex, p3.getUsersCount());
        		tempMap.put("data_"+beginIndex, p3.getOperationCount());
    		}
    		beginIndex = beginIndex +1;
    		//小计
    		PushAnalysis p4 = getPushAnalysis1(oper, lst7);
    		
    		int tc1 = 0;
    		int tc2 = 0;
    		if(p4 != null) {
    			tc1 =  p4.getUsersCount();
        		tc2 = p4.getOperationCount();
    		}
    		tempMap.put("data"+beginIndex,tc1);
    		tempMap.put("data_"+beginIndex,tc2);
    		
    		beginIndex = beginIndex +1;
    		
    		//推送成功率
    		tempMap.put("data"+beginIndex, inCnt1 == 0 ? "0%" : tool.div(Long.valueOf(tc1*100), Long.valueOf(inCnt1)) + "%");
    		tempMap.put("data_"+beginIndex, inCnt_1 == 0 ? "0%" : tool.div(Long.valueOf(tc2*100), Long.valueOf(inCnt_1)) + "%");
    		rsMapList1.add(tempMap);
    	}
    	
    	
    	
    	rsMap.put("groupsType1",getTypeList(pushTotTypes));
    	rsMap.put("groupsType2",getTypeList(pushTotTypes1));
    	rsMap.put("list", rsMapList1);
    	return rsMap;
    }
    
    public Map<String,Object> handleGridAll2(List<PushAnalysis> list,String[] dateType,Map<String,Object> oneTbMap){
    	int index = (Integer)oneTbMap.get("index");
    	//{"点击通知","进入精准相册","进入关联相册","进入相册","有行为","进入精准相册有行为","进入关联相册有行为"}
    	CalcTool tool = new CalcTool(1);
    	tool.setScale(2);
    	//推送表格1 =="推送总量","推送失败(无formid)","推送成功"
    	List<PushAnalysis> lst1 = new ArrayList<PushAnalysis>();
    	List<PushAnalysis> lst2 = new ArrayList<PushAnalysis>();
    	List<PushAnalysis> lst3 = new ArrayList<PushAnalysis>();
    	List<PushAnalysis> lst4 = new ArrayList<PushAnalysis>();
    	List<PushAnalysis> lst5 = new ArrayList<PushAnalysis>();
    	List<PushAnalysis> lst6 = new ArrayList<PushAnalysis>();
    	List<PushAnalysis> lst7 = new ArrayList<PushAnalysis>();
    	for(PushAnalysis bean : list) {
    		if(StringUtils.equals(dateType[0], bean.getOperation())) {
    			lst1.add(bean);
    		}
    		if(StringUtils.equals(dateType[1], bean.getOperation())) {
    			lst2.add(bean);
    		}
    		if(StringUtils.equals(dateType[2], bean.getOperation())) {
    			lst3.add(bean);
    		}
    		if(StringUtils.equals(dateType[3], bean.getOperation())) {
    			lst4.add(bean);
    		}
    		if(StringUtils.equals(dateType[4], bean.getOperation())) {
    			lst5.add(bean);
    		}
    		if(StringUtils.equals(dateType[5], bean.getOperation())) {
    			lst6.add(bean);
    		}
    		if(StringUtils.equals(dateType[6], bean.getOperation())) {
    			lst7.add(bean);
    		}
    	}
    	
    	Map<String,Object> tempMap = null;
    	List<Integer> pushTotTypes = returnMaxGroupTypes(lst2);
    	List<Integer> pushTotTypes1 = returnMaxGroupTypes(lst3);
    	String oper = "小计";
    	
    		Integer cnt1 = (Integer)oneTbMap.get(oper);//人数总计
    		Integer cnt_1 = (Integer)oneTbMap.get(oper+"_");//操作总计
    		
    		tempMap = new HashMap<String,Object>();
    		//进入落地页
    		PushAnalysis pa = getPushAnalysis1(oper, lst1);
    		int inPage1 = 0;
    		int inPage_1 = 0;
    		if(pa == null) {
    			tempMap.put("data"+index, 0);
    			tempMap.put("data_"+index, 0);
    		}else {
    			inPage1 = pa.getUsersCount();
    			inPage_1 = pa.getOperationCount();
    			tempMap.put("data"+index, pa.getUsersCount());
    			tempMap.put("data_"+index, pa.getOperationCount());
    		}
    		
    		tempMap.put("data"+(index+1), cnt1 == 0 ? "0%" : tool.div(Long.valueOf(inPage1*100), Long.valueOf(cnt1)) + "%");
    		tempMap.put("data_"+(index+1), cnt_1 == 0 ? "0%" : tool.div(Long.valueOf(inPage_1*100), Long.valueOf(cnt_1)) + "%");
    		
    		//进入相册
    		//每种类型的小计===精准相册
    		int cntt1 = 0;
    		int cntt2 = 0;
    		for(int i = 0;i<pushTotTypes.size();i++) {
    			PushAnalysis p = getPushAnalysis(pushTotTypes.get(i), oper, lst2);
    			if(p != null) {
    				cntt1 += p.getUsersCount();
    				cntt2 += p.getOperationCount();
    			}
    		}
    		int beginIndex = index+2;
    		tempMap.put("data"+beginIndex, cntt1);
    		tempMap.put("data_"+beginIndex, cntt2);
    		
    		beginIndex = beginIndex +1;
    		//每种类型的小计==关联相册
    		int cntt_1 = 0;
    		int cntt_2 = 0;
    		for(int i = 0;i<pushTotTypes1.size();i++) {
    			PushAnalysis p = getPushAnalysis(pushTotTypes1.get(i), oper, lst3);
    			if(p != null) {
    				cntt_1 += p.getUsersCount();
    				cntt_2 += p.getOperationCount();
    			}
    		}
    		
    		tempMap.put("data"+beginIndex, cntt_1);
    		tempMap.put("data_"+beginIndex, cntt_2);
    		
    		beginIndex = beginIndex +1;
    		
    		
    		//进入相册的小计
    		
    		PushAnalysis p1 = getPushAnalysis1(oper, lst4);
    		
    		int inCnt1 = 0;
    		int inCnt_1 = 0;
    		if(p1 != null) {
    			inCnt1 = p1.getUsersCount();
    			inCnt_1 = p1.getOperationCount();
    			tempMap.put("data"+beginIndex,inCnt1);
    			tempMap.put("data_"+beginIndex,inCnt_1);
    		}else {
    			tempMap.put("data"+beginIndex,inCnt1);
    			tempMap.put("data_"+beginIndex,inCnt_1);
    		}
    		beginIndex = beginIndex +1;
    		
    		
    		//进入率
    		tempMap.put("data"+beginIndex, inPage1 == 0 ? "0%" : tool.div(Long.valueOf(inCnt1*100), Long.valueOf(inPage1)) + "%");
    		tempMap.put("data_"+beginIndex, inPage_1 == 0 ? "0%" : tool.div(Long.valueOf(inCnt_1*100), Long.valueOf(inPage_1)) + "%");
    		
    		beginIndex = beginIndex +1;
    		
    		//产生行为
    		//精准
    		PushAnalysis p2 = getPushAnalysis1(oper, lst5);
    		if(p2 == null) {
    			tempMap.put("data"+beginIndex, 0);
    			tempMap.put("data_"+beginIndex, 0);
    		}else {
    			tempMap.put("data"+beginIndex, p2.getUsersCount());
    			tempMap.put("data_"+beginIndex, p2.getOperationCount());
    		}
    		
    		beginIndex = beginIndex +1;
    		
    		//关联
    		PushAnalysis p3 = getPushAnalysis1(oper, lst6);
    		if(p3 == null) {
    			tempMap.put("data"+beginIndex, 0);
    			tempMap.put("data_"+beginIndex, 0);
    		}else {
    			tempMap.put("data"+beginIndex, p3.getUsersCount());
    			tempMap.put("data_"+beginIndex, p3.getOperationCount());
    		}
    		beginIndex = beginIndex +1;
    		//小计
    		PushAnalysis p4 = getPushAnalysis1(oper, lst7);
    		
    		int tc1 = 0;
    		int tc2 = 0;
    		if(p4 != null) {
    			tc1 =  p4.getUsersCount();
    			tc2 = p4.getOperationCount();
    		}
    		tempMap.put("data"+beginIndex,tc1);
    		tempMap.put("data_"+beginIndex,tc2);
    		
    		beginIndex = beginIndex +1;
    		
    		//产生行为比
    		tempMap.put("data"+beginIndex, inCnt1 == 0 ? "0%" : tool.div(Long.valueOf(tc1*100), Long.valueOf(inCnt1)) + "%");
    		tempMap.put("data_"+beginIndex, inCnt_1 == 0 ? "0%" : tool.div(Long.valueOf(tc2*100), Long.valueOf(inCnt_1)) + "%");
    		return tempMap;
    }
    
    
    public List<String> getTypeList(List<Integer> list){
    	List<String> rsList = new ArrayList<String>();
    	for(Integer i : list) {
    		rsList.add(ResultsHelper.covertType2Name(i));
    	}
    	return rsList;
    }
    
    /**
     * 根据相册类型和行为得到具体的数据
     * @param groupType
     * @param oper
     * @param list
     * @return
     */
    public PushAnalysis getPushAnalysis(Integer groupType,String havType,List<PushAnalysis> list) {
    	PushAnalysis rs = null;
    	for(PushAnalysis bean : list) {
    		if(bean.getGroupType() == groupType && StringUtils.equalsIgnoreCase(havType, bean.getOperationType())) {
    			rs = bean;
    			break;
    		}
    	}
    	return rs;
    }
    
    public PushAnalysis getPushAnalysis1(String havType,List<PushAnalysis> list) {
    	PushAnalysis rs = null;
    	for(PushAnalysis bean : list) {
    		if(StringUtils.equalsIgnoreCase(havType, bean.getOperationType())) {
    			rs = bean;
    			break;
    		}
    	}
    	return rs;
    }
    
    /**
     * 返回最大限度相册类型
     * @param list
     * @return
     */
    public List<Integer> returnMaxGroupTypes(List<PushAnalysis> list){
    	List<Integer> rsList = new  ArrayList<Integer>();
    	for(PushAnalysis bean : list) {
    		if(StringUtils.equals(bean.getOperationType(), "小计")){
    			rsList.add(bean.getGroupType());
    		}
    	}
    	return  rsList;
    }
    
    
    /**
     * 返回行为中文名
     * @param type
     * @return
     */
    public String returnHavName(String type) {
    	switch (type) {
    		case "Event":return "上传";
    		case "Comment":return "评论";
    		case "Like":return "点赞";
    		case "Invite":return "邀请";
    		case "Join":return "加入";
    		case "小计":return "总计";
    		default:return type;
    	}
    }
    
   /* @RequestMapping("/collectData")
    @ResponseBody
    public JSONObject collectData(){
    	Map<String,Object> params = new HashMap<String,Object>();
    	List<Map<String,Object>> mapl = pushAnalysisService.listAllUser();    	
    	System.out.println(mapl.size());
    	for(Map<String,Object> map : mapl) {
    		Object userid = map.get("userid");
    		params.put("userid", userid);
    		List<Map<String,Object>> list =  pushAnalysisService.listAllInvite(params);
    		for(Map<String,Object> mm : list) {
    			pushAnalysisService.insertUserIn(mm);
    		}
    	}
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, params, "");
    }*/
}

