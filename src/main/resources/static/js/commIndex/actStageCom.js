$(function(){
	doSearch();
	initActGroupsSelect();
	actListClick();
	$("#actNormalSelect").change(function(){
		$("#timequery").text("时间查询>>");
		$("#timeBlock").removeAttr("show");
		$("#timeBlock").hide();
		$("#timeBlock").animate({left:'15%'}).fadeOut("fast");
		var gid = $(this).val();
		var time = new Date().format("yyyy-MM-dd hh:mm:ss");
		renderAlysisData(gid,time,1);
		var startTime = $(this).find('option:selected').attr("sTime");
		var endTime = $(this).find('option:selected').attr("eTime");
		var show = new Date(eval(startTime)).format("yyyy-MM-dd hh:mm:ss")+"~"+new Date(eval(endTime)).format("yyyy-MM-dd hh:mm:ss");
		$("#actTime").html(show);
	});
	
	$("#timeBlock").hide();
	$("#timequery").click(function(){
		var show = $("#timeBlock").attr("show");
		if(show){//隐藏
			$(this).text("时间查询(默认最新时间)>>");
			$("#timeBlock").removeAttr("show");
			$("#timeBlock").hide();
			$("#timeBlock").animate({left:'15%'}).fadeOut("fast");
		}else{//显示
			$(this).text("时间查询(默认最新时间)<<");
			$("#timeBlock").attr("show","true");
			$("#timeBlock").show();
			$("#timeBlock").animate({left:'27%'});
			$("#timeBlock").css({display:'inline-block'});
		}
	});
})
var defualtGroupid;
var defualtTime;

/**
 * 初始化日期控件
 * @returns
 */
function initTimeSelect(){
	//设置默认最新时间
	var date = new Date().format("hh");
	var nd = eval(date);
	if(nd%2 != 0 ){
		nd = nd-1;
	}
	nd = ""+nd;
	if(nd.length == 1){
		nd = "0"+nd;
	}
	nd = nd+":00:00"
	$("#hour").val(nd);
	laydate.render({
		  elem: '#timeSelect', //指定元素
		  theme: 'molv',
		  value: new Date(),
//		  type: 'datetime'
	});	
	var time = new Date().format("yyyy-MM-dd")+" "+nd;
	defualtTime = time;
}

/**
 * 数据分析查询
 * @returns
 */
function doSearch(){
	$("#searchIndex").click(function(){
		 var groupId = $("#actNormalSelect").val();
		 var time = $("#timeSelect").val();
		 var h = $("#hour").val();
		 time = time +" "+h;
		 if($.isEmptyObject(time)){
			 layer.msg("请选时间",{icon:2});
			 return false;
		 }
		 renderAlysisData(groupId,time);
	});
}

/**
 * @param portType 活动id
 * @param time 查询时间
 * @returns
 */
function renderAlysisData(groupid,time,isDefault){
	//加载相册信息
	renderGroupInfo(groupid);
	renderActOneStsData(groupid,time,isDefault);
}

function renderActOneStsData(groupid,time,isDefault){
	var date = new Date(Date.parse(time.replace(/-/g,"/")));
	var endTime = date.format("yyyy-MM-dd hh");
	var startTime = new Date(date.getTime()-2*60*60*1000).format("yyyy-MM-dd hh");
	$.ajax({
        type : "POST",  //请求方式
        url : "/renderActStageData",  //请求路径
        data:{
        	startTime:startTime,
        	endTime:endTime,
        	groupid:groupid,
        	hdIndex:"一",
        	isDefault:isDefault
        },
        async:true,
        success : function(rs) {  //异步请求成功执行的回调函数
        	var rsData = rs.data.rsData;
        	var processData = rs.data.processData;
        	if(rsData === undefined){
        		$(".sData").each(function(){
        			var txt = $(this).text();
        			var spl = txt.split("：");
        			$(this).text(spl[0]+"："+0);
        		});
        		$(".greenHead tbody").empty();
        		return;
        	}
        	var text = "";
        	for(var x in rsData){
        		text = $("."+x).text();
        		var spl = text.split("：");
        		$("."+x).text(spl[0]+"："+rsData[x]);
        	}
        	processTableData(rsData);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
}


function processTableData(data){
	//活动总览
	var usersCount = data['usersCount'];
	var usersHavaction = data['usersHavaction'];
	var p1 = (usersHavaction*100/usersCount).toFixed(2)+"%";
	var tb1 = [usersCount,usersHavaction,p1];
	var html1 = "<tr>";
	for(var i in tb1){
		html1 += "<td>"+tb1[i]+"</td>";
	}
	html1 += "</tr>";
	$("#actTb1 tbody").html(html1);
	
	//==============活动相册
	//上传
	var usersUploadCountHd = data['usersUploadCountHd'];
	var phd1 = (usersUploadCountHd*100/usersCount).toFixed(2)+"%";
	if(phd1 == 'NaN'){phd1 = '--%'}
	var eventsCountHd = data['eventsCountHd'];
	var puhd1 = (eventsCountHd/usersUploadCountHd).toFixed(2);
	if(puhd1 == 'NaN'){puhd1 = '-.-'}
	var picturesCountHd = data['picturesCountHd'];
	var ppuhd1 = (picturesCountHd/usersUploadCountHd).toFixed(2);
	if(ppuhd1 == 'NaN'){ppuhd1 = '-.-'}
	var tbhd1 = [usersUploadCountHd,phd1,eventsCountHd,puhd1,picturesCountHd,ppuhd1];
	var htmlHd1 = "<tr>";
	htmlHd1 += "<td class='white'></td>";
	for(var i in tbhd1){
		htmlHd1 += "<td>"+tbhd1[i]+"</td>";
	}
	htmlHd1 += "</tr>";
	$("#actHdTb1 tbody").html(htmlHd1);
	
	//互动
	var usersPlayCountHd = data['usersPlayCountHd'];
	var phd2 = (usersPlayCountHd*100/usersCount).toFixed(2)+"%";
	if(phd2 == 'NaN'){phd2 = '--%'}
	var playsCountHd = data['playsCountHd'];
	var puhd2 = (playsCountHd/usersPlayCountHd).toFixed(2);
	if(puhd2 == 'NaN'){puhd2 = '-.-'}
	var tbhd2 = [usersPlayCountHd,phd2,playsCountHd,puhd2];
	var htmlHd2 = "<tr>";
	htmlHd2 += "<td class='white'></td>";
	for(var i in tbhd2){
		htmlHd2 += "<td>"+tbhd2[i]+"</td>";
	}
	htmlHd2 += "</tr>";
	$("#actHdTb2 tbody").html(htmlHd2);
	
	//邀请
	var usersInviteCountHd = data['usersInviteCountHd'];
	var phd3 = (usersInviteCountHd*100/usersCount).toFixed(2)+"%";
	if(phd3 == 'NaN'){phd3 = '--%'}
	var usersBeinviteCountHd = data['usersBeinviteCountHd'];
	var puhd3 = (usersBeinviteCountHd/usersInviteCountHd).toFixed(2);
	if(puhd3 == 'NaN'){puhd3 = '-.-'}
	var tbhd3 = [usersInviteCountHd,phd3,usersBeinviteCountHd,puhd3];
	var htmlHd3 = "<tr>";
	htmlHd3 += "<td class='white'></td>";
	for(var i in tbhd3){
		htmlHd3 += "<td>"+tbhd3[i]+"</td>";
	}
	htmlHd3 += "</tr>";
	$("#actHdTb3 tbody").html(htmlHd3);
	
	//============私密相册
	//上传
	var usersUploadCountSm = data['usersUploadCountSm'];
	var p2 = (usersUploadCountSm*100/usersCount).toFixed(2)+"%";
	var eventsCountSm = data['eventsCountSm'];
	var pu = (eventsCountSm/usersUploadCountSm).toFixed(2);
	var picturesCountSm = data['picturesCountSm'];
	var pusm = (picturesCountSm/usersUploadCountSm).toFixed(2);
	var tb2 = [usersUploadCountSm,p2,eventsCountSm,pu,picturesCountSm,pusm];
	var html2 = "<tr>";
	html2 += "<td class='white'></td>";
	for(var i in tb2){
		html2 += "<td>"+tb2[i]+"</td>";
	}
	html2 += "</tr>";
	$("#actTb2 tbody").html(html2);
	//互动
	var usersPlayCountSm = data['usersPlayCountSm'];
	var p3 = (usersPlayCountSm*100/usersCount).toFixed(2)+"%";
	var playsCountSm = data['playsCountSm'];
	var pp = (playsCountSm/usersPlayCountSm).toFixed(2);
	var tb3 = [usersPlayCountSm,p3,playsCountSm,pp];
	var html3 = "<tr>";
	html3 += "<td class='white'></td>";
	for(var i in tb3){
		html3 += "<td>"+tb3[i]+"</td>";
	}
	html3 += "</tr>";
	$("#actTb3 tbody").html(html3);
	//邀请
	var usersInviteCountSm = data['usersInviteCountSm'];
	var p4 = (usersInviteCountSm*100/usersCount).toFixed(2)+"%";
	var usersBeinviteCountSm = data['usersBeinviteCountSm'];
	var pi = (usersBeinviteCountSm/usersInviteCountSm).toFixed(2);
	var tb4 = [usersInviteCountSm,p4,usersBeinviteCountSm,pi];
	var html4 = "<tr>";
	html4 += "<td class='white'></td>";
	for(var i in tb4){
		html4 += "<td>"+tb4[i]+"</td>";
	}
	html4 += "</tr>";
	$("#actTb4 tbody").html(html4);
	//创建
	var usersCreateCount = data['usersCreateCount'];
	var p5 = (usersCreateCount*100/usersCount).toFixed(2)+"%";
	var groupsCount = data['groupsCount'];
	var pg = (groupsCount/usersCreateCount).toFixed(2);
	var usersJoinCount = data['usersJoinCount'];
	var pJoin = (usersJoinCount/groupsCount).toFixed(2);
	var tb5 = [usersCreateCount,p5,groupsCount,pg,usersJoinCount,pJoin];
	var html5 = "<tr>";
	html5 += "<td class='white'></td>";
	for(var i in tb5){
		html5 += "<td>"+tb5[i]+"</td>";
	}
	html5 += "</tr>";
	$("#actTb5 tbody").html(html5);
	
	//创建的相册
	var createUploadGroupsCount = data['createUploadGroupsCount'];
	var lv1 = (createUploadGroupsCount*100/data['groupsCount']).toFixed(2)+"%";
	var createPlayGroupsCount = data['createPlayGroupsCount'];
	var lv2 = (createPlayGroupsCount*100/data['groupsCount']).toFixed(2)+"%";
	var createInviteGroupsCount = data['createInviteGroupsCount'];
	var lv3 = (createInviteGroupsCount*100/data['groupsCount']).toFixed(2)+"%";
	
	var tb6 = [createUploadGroupsCount,lv1,createPlayGroupsCount,lv2,createInviteGroupsCount,lv3];
	var html6 = "<tr>";
	html6 += "<td class='white'></td>";
	for(var i in tb6){
		html6 += "<td>"+tb6[i]+"</td>";
	}
	html6 += "</tr>";
	$("#actTb6 tbody").html(html6);
	
	//创建的用户
	var createUploadUsersCount = data['createUploadUsersCount'];
	var p6 = (createUploadUsersCount*100/usersCreateCount).toFixed(2)+"%";
	var createPlayUsersCount = data['createPlayUsersCount'];
	var ppb = (createPlayUsersCount*100/usersCreateCount).toFixed(2)+"%";
	var createInviteUsersCount = data['createInviteUsersCount'];
	var invb = (createInviteUsersCount*100/usersCreateCount).toFixed(2)+"%";
	var tb7 = [createUploadUsersCount,p6,createPlayUsersCount,ppb,createInviteUsersCount,invb];
	var html7 = "<tr>";
	html7 += "<td class='white'></td>";
	for(var i in tb7){
		html7 += "<td>"+tb7[i]+"</td>";
	}
	html7 += "</tr>";
	$("#actTb7 tbody").html(html7);
	
	
	//被邀请的用户
	var beinviteCreateUsersCountSm = data['beinviteCreateUsersCountSm'];
	var p7 = (beinviteCreateUsersCountSm*100/data['usersBeinviteCountSm']).toFixed(2)+"%";
	var beinviteUploadUsersCountSm = data['beinviteUploadUsersCountSm'];
	var ppuu = (beinviteUploadUsersCountSm*100/data['usersBeinviteCountSm']).toFixed(2)+"%";
	var beinvitePlayUsersCountSm = data['beinvitePlayUsersCountSm'];
	var ppb1 = (beinvitePlayUsersCountSm*100/data['usersBeinviteCountSm']).toFixed(2)+"%";
	var beinviteInviteUsersCountSm = data['beinviteInviteUsersCountSm'];
	var invb1 = (beinviteInviteUsersCountSm*100/data['usersBeinviteCountSm']).toFixed(2)+"%";
	var tb8 = [beinviteCreateUsersCountSm,p7,beinviteUploadUsersCountSm,ppuu,beinvitePlayUsersCountSm,ppb1,beinviteInviteUsersCountSm,invb1];
	var html8 = "<tr>";
	html8 += "<td class='white' style='width: 120px;'></td>";
	for(var i in tb8){
		html8 += "<td>"+tb8[i]+"</td>";
	}
	html8 += "</tr>";
	$("#actTb8 tbody").html(html8);
	
	
	$("table tr td").each(function(){
		var txt = $(this).text();
		if(txt.indexOf("%") >= 0){
			$(this).css({"background":"#c7dff4"});
		}
		if(txt.indexOf(".") >= 0){
			$(this).css({"background":"#c7dff4"});
		}
	});
}

/**
 * 初始化活动选择下拉
 * @returns
 */
function initActGroupsSelect(){
	initTimeSelect();
	$.ajax({
        type : "POST",  //请求方式
        url : "/initActAll",  //请求路径
        async:true,
        success : function(data) {  //异步请求成功执行的回调函数
           var actData = data.data;
           actGroups = actData;
           for(var i in actData){
        	 //活动选择下拉数据
        	   if(i == 0){
        		   $("#actNormalSelect").append("<option sTime='"+actData[i]['startTime']+"' eTime='"+actData[i]['endTime']+"' selected value='"+actData[i]['groupid']+"'>"+actData[i]['gname']+"</option>");
        		   defualtGroupid = actData[i]['groupid'];
        		   var show = new Date(actData[i]['startTime']).format("yyyy-MM-dd hh:mm:ss")+"~"+new Date(actData[i]['endTime']).format("yyyy-MM-dd hh:mm:ss");
        		   $("#actTime").html(show);
        	   }else{
        		   $("#actNormalSelect").append("<option sTime='"+actData[i]['startTime']+"' eTime='"+actData[i]['endTime']+"' value='"+actData[i]['groupid']+"'>"+actData[i]['gname']+"</option>");
        	   }
        	   
        	 //记载活动相册列表
        	   var liHtml = "";
        	   liHtml += "<li>";
        	   liHtml += "<span class='block' title='"+actData[i]['groupid']+"'>"+actData[i]['groupid']+"</span>";
        	   liHtml += "<span class='block' title='"+actData[i]['gname']+"'>"+actData[i]['gname']+"</span>";
        	   liHtml += "<span class='block' title='"+new Date(actData[i]['startTime']).format("yyyy-MM-dd hh:mm:ss")+"'>"+new Date(actData[i]['startTime']).format("yyyy-MM-dd hh:mm:ss")+"</span>";
        	   liHtml += "<span class='block' title='"+new Date(actData[i]['endTime']).format("yyyy-MM-dd hh:mm:ss")+"'>"+new Date(actData[i]['endTime']).format("yyyy-MM-dd hh:mm:ss")+"</span>";
        	   liHtml += "<span class='block' title='"+actData[i]['type']+"'>"+actData[i]['type']+"</span>";
        	   liHtml += "</li>";
               $("#listActAll").append(liHtml);
        	   
           }
           
            //加载默认活动分析数据======to==do
       		renderAlysisData(defualtGroupid,defualtTime,1);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
}

function actListClick(){
	 $(".ullst").on("click","li",function(){
		 var groupid = $(this).find("span:nth-child(1)").text();
		 $("#actNormalSelect").val(groupid);
		 var time = new Date().format("yyyy-MM-dd hh:mm:ss");
		 renderAlysisData(groupid,time,1);
	 });
}


/**
 * 加载相册信息
 * @param groupid
 * @returns
 */
function renderGroupInfo(groupid){
	var info;
	for(var x in actGroups){
		var data = actGroups[x];
		if(data['groupid'] == groupid){
			info = data;
			break;
		}
	}
	$("#groupid").text(info.groupid);
	$("#gname").text(info.gname);
	$("#startTime").text(new Date(info.startTime).format("yyyy-MM-dd hh:mm:ss"));
	$("#endTime").text(new Date(info.endTime).format("yyyy-MM-dd hh:mm:ss"));
	$("#type").text(info.type);
	var v = (info.endTime - info.startTime)/(24*60*60*1000);
	$("#actCyc").text(v.toFixed(0)+"天");
	var sts = "";
	var curT = new Date().getTime();
	if(curT>=info.startTime && curT<=info.endTime){
		sts = "进行中";
	}else{
		sts = "已结束";
	}
	$("#actStatus").text(sts);
}



