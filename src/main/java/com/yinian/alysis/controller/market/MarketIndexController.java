package com.yinian.alysis.controller.market;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yinian.alysis.model.Hot;
import com.yinian.alysis.service.PortIndexService;
import com.yinian.alysis.util.ResultsHelper;


@Controller
public class MarketIndexController {
	@Autowired
	private PortIndexService  portIndexService;
	
	
    @RequestMapping("/marketIndex")
    public String marketIndex(Model model) {
    	return "market/marketIndex";
    }
    
    
    @RequestMapping("/renderHotData")
    @ResponseBody
    public JSONObject renderHotData(String type,String startTime,String endTime){
    	Map<String,Object> params = new HashMap<String,Object>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("type", type);
    	List<Hot> list = portIndexService.findHotByMapParam(params);
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, list, "");
    }
}

