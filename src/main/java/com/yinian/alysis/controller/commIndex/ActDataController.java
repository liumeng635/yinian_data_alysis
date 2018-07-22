package com.yinian.alysis.controller.commIndex;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.yinian.alysis.model.ActGroups;
import com.yinian.alysis.model.ActTarget;
import com.yinian.alysis.model.CreateAcc;
import com.yinian.alysis.model.GroupEventcut;
import com.yinian.alysis.model.GroupPlaycut;
import com.yinian.alysis.model.GroupRetention;
import com.yinian.alysis.model.GroupUsercut;
import com.yinian.alysis.model.GroupsPicUserCut;
import com.yinian.alysis.model.User;
import com.yinian.alysis.model.UserEventcut;
import com.yinian.alysis.model.UserGroupcut;
import com.yinian.alysis.model.UserInvite;
import com.yinian.alysis.model.UserPlaycut;
import com.yinian.alysis.service.CreateAccService;
import com.yinian.alysis.service.GroupEventCutService;
import com.yinian.alysis.service.GroupPlayCutService;
import com.yinian.alysis.service.GroupRetentionService;
import com.yinian.alysis.service.GroupUserCutService;
import com.yinian.alysis.service.GroupsPicUserCutService;
import com.yinian.alysis.service.GroupsService;
import com.yinian.alysis.service.TargetService;
import com.yinian.alysis.service.UserEventcutService;
import com.yinian.alysis.service.UserGroupcutService;
import com.yinian.alysis.service.UserInviteService;
import com.yinian.alysis.service.UserPlaycutService;
import com.yinian.alysis.util.DateUtils;
import com.yinian.alysis.util.Numutils;
import com.yinian.alysis.util.ResultsHelper;


@Controller
public class ActDataController {

   /* @Autowired
    private UserService userService;*/
	
	@Autowired
	private GroupsService groupsService;
	
	@Autowired
	private TargetService targetService;
	
	@Autowired
	private UserInviteService userInviteService;
	
	@Autowired
	private UserGroupcutService userGroupcutService;
	
	@Autowired
	private GroupUserCutService groupUserCutService;
	
	@Autowired
	private GroupEventCutService groupEventCutService;
	
	@Autowired
	private GroupPlayCutService groupPlayCutService;
	
	@Autowired
	private UserEventcutService userEventcutService;
	
	@Autowired
	private UserPlaycutService userPlaycutService;
	
	@Autowired
    private CreateAccService createAccService;
	
	@Autowired
	private GroupsPicUserCutService groupsPicUserCutService;
	
	@Autowired
	private GroupRetentionService groupRetentionService;
	
    @RequestMapping("/index")
    public String index(Model model) {
        return "commIndex/index";
    }
    
    @RequestMapping("/actStageCom")
    public String actStageCom(Model model) {
    	return "commIndex/actStageCom";
    }
    
    @RequestMapping("/main")
    public String main(HttpServletRequest rs,Model model) {
//    	String port = rs.getLocalPort()+"";
//    	String serverIp = rs.getLocalAddr();
//    	serverIp = serverIp.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":serverIp;
//    	String context = "http://"+serverIp+":"+port;
//    	model.addAttribute("context", context);
    	User user = (User)rs.getSession().getAttribute("user");
//    	User user = new User();
//    	user.setStatus(1);
//    	user.setType(0);
//    	user.setuName("刘猛");
    	user.setuPwd(null);
    	model.addAttribute("user",user);
    	return "main";
    }
    
    /**
          * 获取服务器IP地址
          * @return
          */
         public static String  getServerIp(HttpServletRequest request){
             // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
             String ip = request.getHeader("X-Forwarded-For");
             if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                     ip = request.getHeader("Proxy-Client-IP");
                 }
                 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                     ip = request.getHeader("WL-Proxy-Client-IP");
                 }
                 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                     ip = request.getHeader("HTTP_CLIENT_IP");
                 }
                 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                     ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                 }
                 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                     ip = request.getRemoteAddr();
                 }
             } else if (ip.length() > 15) {
                 String[] ips = ip.split(",");
                 for (int index = 0; index < ips.length; index++) {
                     String strIp = (String) ips[index];
                     if (!("unknown".equalsIgnoreCase(strIp))) {
                         ip = strIp;
                         break;
                     }
                 }
             }
             return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
        
       }
    
    /*@ResponseBody
    @RequestMapping(value = "api/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        return userService.findAllUser(pageNum,pageSize);
    }*/

    @RequestMapping(value = "findAll/{pageNum}/{pageSize}")
    public String findAll(Model model,@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
    	 groupsService.findAllByPage(pageSize, pageSize);
        List<CreateAcc> accs = createAccService.findAllByPage(pageNum, pageSize);
        model.addAttribute("accs", accs);
        return "users";
    }
    
    @RequestMapping("/initActAll")
    @ResponseBody
    public JSONObject initActAll(){
    	List<ActGroups> groups = groupsService.findAllByPage(0, 0);
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, groups, "");
    }
    
    @RequestMapping("/renderActStsInfo")
    @ResponseBody
    public JSONObject renderActStsInfo(String groupid,String time){
    	groupid = StringUtils.isEmpty(groupid) ? "0" : groupid;
    	Map<String,Object> params = new HashMap<String,Object>();
    	List<ActTarget> lstTarget;
    	List<UserInvite> lstInvt;
    	JSONObject rsJ = new JSONObject();
    	try {
			params.put("groupid", Integer.valueOf(groupid));
			params.put("period", time);
			lstTarget = targetService.findByMapParam(params);
			lstInvt = userInviteService.findByMapParam(params);
			rsJ.put("target", lstTarget != null && lstTarget.size()>0 ? lstTarget.get(0) : new ArrayList<ActTarget>());
			rsJ.put("invite", lstInvt != null && lstInvt.size()>0 ? lstInvt.get(0) : new ArrayList<UserInvite>());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return ResultsHelper.putResults(ResultsHelper.ERROR_CODE, null, "数据查询出错！");
		}
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, rsJ, "");
    }
    
    /**
     * 用户数:（按创建相册数区间）
     * @param groupid
     * @param time
     * @return
     */
    @RequestMapping("/renderStaticData1")
    @ResponseBody
    public JSONObject renderStaticData1(String groupid,String time){
    	groupid = StringUtils.isEmpty(groupid) ? "0" : groupid;
    	Map<String,Object> params = new HashMap<String,Object>();
		params.put("groupid", Integer.valueOf(groupid));
		params.put("period", time);
		List<UserGroupcut> list = userGroupcutService.findByMapParam(params);
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, list, "");
    }
    
    /**
     * 相册数:（按相册成员数区间）
     * @param groupid
     * @param time
     * @return
     */
    @RequestMapping("/renderStaticData2")
    @ResponseBody
    public JSONObject renderStaticData2(String groupid,String time){
    	groupid = StringUtils.isEmpty(groupid) ? "0" : groupid;
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("groupid", Integer.valueOf(groupid));
    	params.put("period", time);
    	List<GroupUsercut> list = groupUserCutService.findByMapParam(params);
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, list, "");
    }
    
    @RequestMapping("/renderStaticData3")
    @ResponseBody
    public JSONObject renderStaticData3(String groupid,String time){
    	groupid = StringUtils.isEmpty(groupid) ? "0" : groupid;
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("groupid", Integer.valueOf(groupid));
    	params.put("period", time);
    	List<GroupEventcut> list = groupEventCutService.findByMapParam(params);
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, list, "");
    }
    
    @RequestMapping("/renderStaticData4")
    @ResponseBody
    public JSONObject renderStaticData4(String groupid,String time){
    	groupid = StringUtils.isEmpty(groupid) ? "0" : groupid;
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("groupid", Integer.valueOf(groupid));
    	params.put("period", time);
    	List<GroupPlaycut> list = groupPlayCutService.findByMapParam(params);
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, list, "");
    }
    
    @RequestMapping("/renderStaticData5")
    @ResponseBody
    public JSONObject renderStaticData5(String groupid,String time){
    	groupid = StringUtils.isEmpty(groupid) ? "0" : groupid;
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("groupid", Integer.valueOf(groupid));
    	params.put("period", time);
    	List<UserEventcut> list = userEventcutService.findByMapParam(params);
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, list, "");
    }
    
    @RequestMapping("/renderStaticData6")
    @ResponseBody
    public JSONObject renderStaticData6(String groupid,String time){
    	groupid = StringUtils.isEmpty(groupid) ? "0" : groupid;
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("groupid", Integer.valueOf(groupid));
    	params.put("period", time);
    	List<UserPlaycut> list = userPlaycutService.findByMapParam(params);
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, list, "");
    }
    
    @RequestMapping("/renderStaticDataAcc")
    @ResponseBody
    public JSONObject renderStaticDataAcc(String groupid,String time,String groupType){
    	groupid = StringUtils.isEmpty(groupid) ? "0" : groupid;
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("groupid", Integer.valueOf(groupid));
    	params.put("period", time);
    	params.put("groupType", groupType);
    	List<Map<String,Object>> list = createAccService.createAccMap(params);
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, list, "");
    }
    
    @RequestMapping("/initAllGroupType")
    @ResponseBody
    public JSONObject initAllGroupType(){
    	List<Map<String,Object>> list = createAccService.initAllGroupType();
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, list, "");
    }
    
    @RequestMapping("/actlineData")
    @ResponseBody
    public JSONObject actlineData(String groupid){
    	groupid = StringUtils.isEmpty(groupid) ? "0" : groupid;
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("groupid", Integer.valueOf(groupid));
    	List<Map<String,Object>> list = targetService.actlineData(params);
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, list, "");
    }

    @RequestMapping("/renderGroupsPicUser")
    @ResponseBody
    public JSONObject renderGroupsPicUser(String groupid,String time){
    	groupid = StringUtils.isEmpty(groupid) ? "0" : groupid;
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("groupid", Integer.valueOf(groupid));
    	params.put("period", time);
    	List<GroupsPicUserCut> list = groupsPicUserCutService.findByMapParam(params);
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, list, "");
    }
    
    
    /**
     * 数据跑批（相册活跃度统计）
     * @return
     * @throws ParseException 
     */
    @RequestMapping("/loadGroupsAlyData")
    @ResponseBody
    public JSONObject loadGroupsAlyData(String beginDate,String endDate) throws ParseException{
    	//2018.01.01-2018-05-07
//    	String beginDate = "2018-02-10";
//    	String endDate = "2018-05-02";
    	int days = DateUtils.getDayCount(beginDate,endDate);
    	String queryDate = "";
    	List<Map<String,Object>> rsListmap = null;
    	GroupRetention bean = null;
    	for(int i = 0 ; i<=days;i++) {
    		queryDate = DateUtils.getPreviousOrNextDaysOfNow(beginDate, i);
    		rsListmap = groupRetentionService.queryRetention(queryDate);
    		rsListmap.addAll(groupRetentionService.queryRetention1(queryDate));
    		for(Map bmp : rsListmap) {
    			bean = new GroupRetention();
    			bean.setDate(DateUtils.str2Date(queryDate, "yyyy-MM-dd"));
    			
    			bean.setCount(Numutils.parse2num(bmp.get("汇总")));
    			bean.setCommentCount(Numutils.parse2num(bmp.get("评论")));
    			bean.setCreateCount(Numutils.parse2num(bmp.get("创建")));
    			bean.setDataType(Numutils.parse2num(bmp.get("dataType")));
    			int groupsNewType = Numutils.parse2num(bmp.get("groupNewType"));
    			if(groupsNewType > 0) {
    				bean.setGroupType(groupsNewType);
    			}
    			bean.setInviteCount(Numutils.parse2num(bmp.get("邀请")));
    			bean.setLikeCount(Numutils.parse2num(bmp.get("点赞")));
    			bean.setPeriod((String)bmp.get("period"));
    			bean.setUploadCount(Numutils.parse2num(bmp.get("上传")));
    			
    			groupRetentionService.insert(bean);
    			
    		}
    	}
    	return ResultsHelper.putResults(ResultsHelper.SUC_CODE, null, "");
    }
    
    public static void main(String[] args) {
//		System.out.println(Integer.valueOf(String.valueOf(1212.0)));
		
	}
    
}
