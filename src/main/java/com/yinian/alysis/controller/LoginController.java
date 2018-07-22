package com.yinian.alysis.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.yinian.alysis.model.User;
import com.yinian.alysis.service.UserService;
import com.yinian.alysis.util.EncrypDES;
import com.yinian.alysis.util.ResultsHelper;


@Controller
public class LoginController {
	
	@Autowired
	private UserService  userService;
	
    @RequestMapping("/login")
    public String actUserRetain(Model model) {
    	return "login/login";
    }
    
    @RequestMapping("/cancel")
    public String cancel(HttpServletRequest request) {
    	request.getSession().invalidate();
    	return "login/login";
    }
    
    @RequestMapping("/loginUser")
    @ResponseBody
    public JSONObject userLogin(String uName,String uPwd,HttpServletRequest request,HttpServletResponse response) throws IOException{
    	if(uName != null) {
    		uName = uName.trim();
    	}
    	Map<String,Object> params = new HashMap<String,Object>(); 
    	params.put("uName", uName);
    	List<User> users = userService.getUserByMapParam(params);
    	if(users == null || users.size() == 0) {
    		return ResultsHelper.putResults(ResultsHelper.ERROR_CODE, null, "您输入的用户不存在！");
    	}else {
    		User user = users.get(0);
    		int sts = user.getStatus();//启用状态
    		if(sts == 0){
    			return ResultsHelper.putResults(ResultsHelper.ERROR_CODE, null, "该用户已禁用！");
    		}else {
    			if(!StringUtils.equals(user.getuPwd(), EncrypDES.encrypt(uPwd))) {//密码不对
        			return ResultsHelper.putResults(ResultsHelper.ERROR_CODE, null, "您输入的密码不正确！");
        		}else {//进行登录操作
        			HttpSession session = request.getSession();
        			session.setMaxInactiveInterval(60*30);//超时时间为5分钟
        			session.setAttribute("user", user);
//        			response.sendRedirect("/main");
        			return ResultsHelper.putResults(ResultsHelper.SUC_CODE, true,"登录成功!");
        		}
    		}
    	}
    }
    
}
