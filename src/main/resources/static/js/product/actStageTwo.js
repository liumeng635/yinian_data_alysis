$(function(){
//	initPortTypeStsList();
//	initportTypeSelect();
	doSearch();
	initTimeSelect();
})


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
	renderAlysisData(time);
}

/**
 * 数据分析查询
 * @returns
 */
function doSearch(){
	$("#searchIndex").click(function(){
		 var time = $("#timeSelect").val();
		 var h = $("#hour").val();
		 time = time +" "+h;
		 if($.isEmptyObject(time)){
			 layer.msg("请选时间",{icon:2});
			 return false;
		 }
		 renderAlysisData(time);
	});
}

/**
 * @param portType 活动id
 * @param time 查询时间
 * @returns
 */
function renderAlysisData(time){
	renderActOneStsData(time);
}

function renderActOneStsData(time){
	var groupid = 2222;
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
        	hdIndex:"二"
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
	
	//私密相册
	//上传
	var usersUploadCountSm = data['usersUploadCountSm'];
	var p2 = (usersUploadCountSm*100/usersCount).toFixed(2)+"%";
	var eventsCountSm = data['eventsCountSm'];
	var pu = (eventsCountSm/usersUploadCountSm).toFixed(2);
	var tb2 = [usersUploadCountSm,p2,eventsCountSm,pu];
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
	var tb5 = [usersCreateCount,p5,groupsCount,pg];
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



