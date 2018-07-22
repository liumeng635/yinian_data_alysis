package com.yinian.alysis.controller.market;

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
import com.yinian.alysis.model.PortTarget;
import com.yinian.alysis.model.PortType;
import com.yinian.alysis.service.PortIndexService;
import com.yinian.alysis.service.PortTargetService;
import com.yinian.alysis.service.PortTypeService;
import com.yinian.alysis.util.ResultsHelper;


@Controller
public class QualityOfChannelUsersController {
	@Autowired
	private PortIndexService  portIndexService;
	
	@Autowired
	private PortTypeService  portTypeService;
	
	@Autowired
	private PortTargetService  portTargetService;
	
    @RequestMapping("/qualityOfChannelUsers")
    public String actUserRetain(Model model) {
    	return "market/qualityOfChannelUsers";
    }
    
    @RequestMapping("/initPortTypeStsList")
    @ResponseBody
    public JSONObject initPortTypeStsList(){
    	List<Map<String,Object>> groups = portIndexService.listAllportIndex();
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, groups, "");
    }
    
    @RequestMapping("/getPortTypeAll")
    @ResponseBody
    public JSONObject getPortTypeAll(){
    	List<PortType> portTypes = portTypeService.findAllByPage(0, 0);
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, portTypes, "");
    }
    
    @RequestMapping("/renderChannelStsData")
    @ResponseBody
    public JSONObject renderChannelStsData(String portName,String time){
    	Map<String,Object> params = new HashMap<String,Object>();
		params.put("portName", portName);
		params.put("period", time);
		PortTarget rs = null;
		List<PortTarget> list = portTargetService.findTargetByParamMap(params);
		if(list != null && !list.isEmpty()) {
			rs = list.get(0);
		}
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, rs, "");
    }
}

