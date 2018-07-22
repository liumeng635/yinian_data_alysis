package com.yinian.alysis.controller.buryPoint;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yinian.alysis.model.BuryPointComm;
import com.yinian.alysis.model.BuryPointDraw;
import com.yinian.alysis.model.BuryPointLogin;
import com.yinian.alysis.model.BuryPointPush;
import com.yinian.alysis.model.BuryPort;
import com.yinian.alysis.service.BuryPointService;
import com.yinian.alysis.util.ResultsHelper;


@Controller
public class BuryPointController {
	
	@Autowired
	private BuryPointService  buryPointService;
	
    @RequestMapping("/buryPoint")
    public String buryPoint(Model model) {
    	return "buryPoint/buryPointMgr";
    }
    
    @RequestMapping("/devPoint")
    public String devPoint(Model model) {
    	return "buryPoint/devPointMgr";
    }

    /**
     * @param groupid
     * @param time
     * @return
     */
    @RequestMapping("/buryPointData")
    @ResponseBody
    public JSONObject buryPointData(String type,String buryType,String stage){
    	Map<String,Object> params = new HashMap<String,Object>();
    	Object rs = null;
    	try {
			if(StringUtils.equals("1", type)){//通用表指标
				params.put("buryType", buryType);
				params.put("bstage", stage);
				rs = buryPointService.findBuryPointCommByMapParam(params);
			}else if(StringUtils.equals("2", type)){//促活推送
				params.put("bstage", stage);
				rs = buryPointService.findBuryPointPushByMapParam(params);
			}else if(StringUtils.equals("3", type)){//地图调取
				params.put("bstage", stage);
				rs = buryPointService.findBuryPointDrawByMapParam(params);
			}else if(StringUtils.equals("4", type)){//用户登录
				params.put("bstage", stage);
				rs = buryPointService.findBuryPointLoginByMapParam(params);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultsHelper.putResults(ResultsHelper.ERROR_CODE, null, "");
		}
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, rs, "");
    }
    
    /**
     * 新增埋点
     * @Title: savePointData 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param type
     * @param @param pointData
     * @param @return    设定文件 
     * @return JSONObject    返回类型 
     * @throws
     */
    @RequestMapping("/savePointData")
    @ResponseBody
    public JSONObject savePointData(String type,String buryType,String pointData,String oper){
    	JSONArray jArr = JSONArray.parseArray(pointData);
    	try {
    		if(StringUtils.equals("add", oper)){//新增
    			if(StringUtils.equals("1", type)){//通用表指标
        			for(int i=0;i<jArr.size();i++){
        				JSONObject o = jArr.getJSONObject(i);
        				BuryPointComm bean = JSONObject.toJavaObject(o, BuryPointComm.class);
        				bean.setStatus(1);
        				bean.setBindex(i+1);
        				bean.setBuryTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        				bean.setBuryType(buryType);
        				buryPointService.insertBuryPointComm(bean);
        			}
    			}else if(StringUtils.equals("2", type)){//促活推送
    				for(int i=0;i<jArr.size();i++){
        				JSONObject o = jArr.getJSONObject(i);
        				BuryPointPush bean = JSONObject.toJavaObject(o, BuryPointPush.class);
        				bean.setStatus(1);
        				bean.setBindex(i+1);
        				bean.setBuryTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        				buryPointService.insertBuryPointPush(bean);
        			}
    			}else if(StringUtils.equals("3", type)){//地图调取
    				for(int i=0;i<jArr.size();i++){
        				JSONObject o = jArr.getJSONObject(i);
        				BuryPointDraw bean = JSONObject.toJavaObject(o, BuryPointDraw.class);
        				bean.setStatus(1);
        				bean.setBindex(i+1);
        				bean.setBuryTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        				buryPointService.insertBuryPointDraw(bean);
        			}
    			}else if(StringUtils.equals("4", type)){//用户登录
    				for(int i=0;i<jArr.size();i++){
        				JSONObject o = jArr.getJSONObject(i);
        				BuryPointLogin bean = JSONObject.toJavaObject(o, BuryPointLogin.class);
        				bean.setStatus(1);
        				bean.setBuryTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        				buryPointService.insertBuryPointLogin(bean);
        			}
    			}
    		}else{//修改
    			if(StringUtils.equals("1", type)){//通用表指标
        			for(int i=0;i<jArr.size();i++){
        				JSONObject o = jArr.getJSONObject(i);
        				BuryPointComm bean = JSONObject.toJavaObject(o, BuryPointComm.class);
        				buryPointService.alterBuryPointComm(bean);
        			}
    			}else if(StringUtils.equals("2", type)){//促活推送
    				for(int i=0;i<jArr.size();i++){
        				JSONObject o = jArr.getJSONObject(i);
        				BuryPointPush bean = JSONObject.toJavaObject(o, BuryPointPush.class);
        				buryPointService.alterBuryPointPush(bean);
        			}
    			}else if(StringUtils.equals("3", type)){//地图调取
    				for(int i=0;i<jArr.size();i++){
        				JSONObject o = jArr.getJSONObject(i);
        				BuryPointDraw bean = JSONObject.toJavaObject(o, BuryPointDraw.class);
        				buryPointService.alterBuryPointDraw(bean);
        			}
    			}else if(StringUtils.equals("4", type)){//用户登录
    				for(int i=0;i<jArr.size();i++){
        				JSONObject o = jArr.getJSONObject(i);
        				BuryPointLogin bean = JSONObject.toJavaObject(o, BuryPointLogin.class);
        				buryPointService.alterBuryPointLogin(bean);
        			}
    			}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResultsHelper.putResults(ResultsHelper.ERROR_CODE, null, "");
    	}
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, null, "");
    }
    
    /**
     * 删除埋点
     * @Title: deletePointData 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param ids
     * @param @param type
     * @param @return    设定文件 
     * @return JSONObject    返回类型 
     * @throws
     */
    @RequestMapping("/deletePointData")
    @ResponseBody
    public JSONObject deletePointData(String ids,String type){
    	String[] idStrings = ids.split(",");
    	int[] idIns = new int[idStrings.length];
    	for(int i = 0;i<idStrings.length;i++){
    		idIns[i] = Integer.valueOf(idStrings[i]);
    	}
    	
    	try {
    		if(StringUtils.equals("1", type)){//通用表指标
    			buryPointService.batchDeleteBuryPointComm(idIns);
    		}else if(StringUtils.equals("2", type)){//促活推送
    			buryPointService.batchDeleteBuryPointPush(idIns);
    		}else if(StringUtils.equals("3", type)){//地图调取
    			buryPointService.batchDeleteBuryPointDraw(idIns);
    		}else if(StringUtils.equals("4", type)){//用户登录
    			buryPointService.batchDeleteBuryPointLogin(idIns);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResultsHelper.putResults(ResultsHelper.ERROR_CODE, null, "");
    	}
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, null, "");
    }
    
    @RequestMapping("/changePointDataSts")
    @ResponseBody
    public JSONObject changePointDataSts(String ids,String type,int status){
    	try {
    		buryPointService.updateBySql(ids, type, status);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResultsHelper.putResults(ResultsHelper.ERROR_CODE, null, "");
    	}
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, null, "");
    }
    
    /**
     * 获取所有阶段
     * @Title: stageList 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param type
     * @param @return    设定文件 
     * @return JSONObject    返回类型 
     * @throws
     */
    @RequestMapping("/stageList")
    @ResponseBody
    public JSONObject stageList(String type,String buryType){
    	List<Map<String,Object>> rslist = null;
    	try {
    		rslist = buryPointService.stageList(type,buryType);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResultsHelper.putResults(ResultsHelper.ERROR_CODE, null, "");
    	}
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, rslist, "");
    }
    
    @RequestMapping("/queryPort")
    @ResponseBody
    public JSONObject queryPort(String buryType){
    	List<BuryPort> rslist = null;
    	try {
    		Map<String,Object> params = new HashMap<String,Object>();
    		params.put("type", buryType);
    		rslist = buryPointService.findBuryPortByMapParam(params);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResultsHelper.putResults(ResultsHelper.ERROR_CODE, null, "");
    	}
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, rslist, "");
    }
    
    @RequestMapping("/deletePortData")
    @ResponseBody
    public JSONObject deletePortData(String ids){
    	String[] idStrings = ids.split(",");
    	int[] idIns = new int[idStrings.length];
    	for(int i = 0;i<idStrings.length;i++){
    		idIns[i] = Integer.valueOf(idStrings[i]);
    	}
    	try {
    		buryPointService.batchDeleteBuryPort(idIns);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResultsHelper.putResults(ResultsHelper.ERROR_CODE, null, "");
    	}
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, null, "");
    }
    
    @RequestMapping("/savePortData")
    @ResponseBody
    public JSONObject savePortData(String buryType,String pointData){
    	JSONArray jArr = JSONArray.parseArray(pointData);
    	try {
    			for(int i=0;i<jArr.size();i++){
    				JSONObject o = jArr.getJSONObject(i);
    				BuryPort bean = JSONObject.toJavaObject(o, BuryPort.class);
    				bean.setType(buryType);
    				buryPointService.insertBuryPort(bean);
    			}
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResultsHelper.putResults(ResultsHelper.ERROR_CODE, null, "");
    	}
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, null, "");
    }
    
    @RequestMapping("/alterPortData")
    @ResponseBody
    public JSONObject alterPortData(String id){
    	BuryPort port = null;
    	try {
    		 port = buryPointService.findBuryPortByKey(Integer.valueOf(id));
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResultsHelper.putResults(ResultsHelper.ERROR_CODE, null, "");
    	}
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, port, "");
    }
    
    @RequestMapping("/saveAlterPortData")
    @ResponseBody
    public JSONObject saveAlterPortData(BuryPort port){
    	try {
    		buryPointService.alterBuryPort(port);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResultsHelper.putResults(ResultsHelper.ERROR_CODE, null, "");
    	}
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, null, "");
    }
    
    @RequestMapping("/queyBuryPointData")
    @ResponseBody
    public JSONObject queyBuryPointData(String type,String ids){
    	List<Object> rsList = new ArrayList<Object>();
    	String[] idStrings = ids.split(",");
    	try {
    		if(StringUtils.equals("1", type)){//通用表指标
    			for(String id : idStrings){
    				rsList.add(buryPointService.findBuryPointCommByKey(Integer.valueOf(id)));
    			}
    		}else if(StringUtils.equals("2", type)){//促活推送
    			for(String id : idStrings){
    				rsList.add(buryPointService.findBuryPointPushByKey(Integer.valueOf(id)));
    			}
    		}else if(StringUtils.equals("3", type)){//地图调取
    			for(String id : idStrings){
    				rsList.add(buryPointService.findBuryPointDrawByKey(Integer.valueOf(id)));
    			}
    		}else if(StringUtils.equals("4", type)){//用户登录
    			for(String id : idStrings){
    				rsList.add(buryPointService.findBuryPointLoginByKey(Integer.valueOf(id)));
    			}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResultsHelper.putResults(ResultsHelper.ERROR_CODE, null, "");
    	}
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, rsList, "");
    }
    
    
}
