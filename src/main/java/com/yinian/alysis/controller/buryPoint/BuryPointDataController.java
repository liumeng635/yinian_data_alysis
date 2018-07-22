package com.yinian.alysis.controller.buryPoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yinian.alysis.model.ThemeAlbumAnalysis;
import com.yinian.alysis.service.BuryPointDataService;
import com.yinian.alysis.util.ResultsHelper;


@Controller
public class BuryPointDataController {
	
	@Autowired
	private BuryPointDataService  buryPointDataService;
	
    @RequestMapping("/buryPointPage")
    public String buryPointPage(Model model) {
    	return "buryPointData/buryPointData";
    }

   /**
    * @Title: queryStsData 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param @param type
    * @param @param ids
    * @param @return    设定文件 
    * @return JSONObject    返回类型 
    * @throws
    */
    @RequestMapping("/queryStsData")
    @ResponseBody
    public JSONObject queryStsData(String exp,String isnewuser,String period,String themeType,String groupName){
    	JSONObject expJson = JSONObject.parseObject(exp);
    	period = period + ":00:00";
    	Map<String,Object> rsMap = new HashMap<String,Object>();
    	
    	Map<String,Object> params = null;
    	
    	try {
			for(String key:expJson.keySet()){
				params = new HashMap<String,Object>();
				String expJ = expJson.getString(key);
				String[] sarrs = expJ.split("-");
				params.put("isnewuser", isnewuser);
				params.put("period", period);
				params.put("themeType", themeType);
				params.put("operation", sarrs[0]);
				params.put("groupName", groupName);
				
				String col = sarrs[1];
				
				List<ThemeAlbumAnalysis> beanList = buryPointDataService.findByMapParam(params);
				
				if(beanList == null || beanList.isEmpty()){
					rsMap.put(key, 0);
				}else{
					ThemeAlbumAnalysis bean = beanList.get(0);
					if(StringUtils.equals("users_count", col)){
						rsMap.put(key, bean.getUsersCount());
					}else if(StringUtils.equals("operation_count", col)){
						rsMap.put(key, bean.getOperationCount());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultsHelper.putResults(ResultsHelper.SUC_CODE, null, "");
		}
    	
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, rsMap, "");
    }
    
    @RequestMapping("/queryIndexData")
    @ResponseBody
    public JSONObject queryIndexData(String expIndex,String isnewuser,String period,String themeType,String groupName){
    	JSONObject expJson = JSONObject.parseObject(expIndex);
    	Map<String,Object> rsMap = new HashMap<String,Object>();
    	Map<String,Object> params = null;
    	period = period + ":00:00";
    	try {
    		for(String key:expJson.keySet()){
    			params = new HashMap<String,Object>();
    			String expJ = expJson.getString(key);
    			String[] sarrs = expJ.split("-");
    			params.put("isnewuser", isnewuser);
    			params.put("period", period);
    			params.put("themeType", themeType);
    			params.put("operation", sarrs[0]);
    			params.put("groupName", groupName);
    			
    			List<ThemeAlbumAnalysis> beanList = buryPointDataService.findByMapParam(params);
    			
    			if(beanList == null || beanList.isEmpty()){
    				rsMap.put(key, 0);
    			}else{
    				ThemeAlbumAnalysis bean = beanList.get(0);
    				rsMap.put(key, bean.getUsersCount());
    			}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResultsHelper.putResults(ResultsHelper.SUC_CODE, null, "");
    	}
    	
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, rsMap, "");
    }
    
    
}
