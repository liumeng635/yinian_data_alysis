var actGroups;//活动信息缓存
var dfTime;
$(function(){
	initActGroupsSelect();
	doSearch();
	initAllGroupType();
	$("#groupType").change(function(){
		var groupid = $("#actSelect").val();
   		var time = $("#timeSelect").val();
   		renderStaticDataAcc(groupid,time);
	});
	actListClick();
	
	$("#paoshu").click(function(){
		var beginDate = $("#beginDate").val();
		var endDate = $("#endDate").val();
		$.ajax({
	        type : "POST",  //请求方式
	        url : "/loadGroupsAlyData",  //请求路径
	        async:true,
	        data:{
	        	beginDate:beginDate,
	        	endDate:endDate
	        },
	        success : function(data) {
	        	
	        },
	        error : function(XMLHttpRequest, textStatus, errorThrown) {
	            layer.msg("访问服务器失败！",{icon:2});
	        }
	    });
	});
})

function actListClick(){
	 $(".ullst").on("click","li",function(){
		 var groupid = $(this).find("span:nth-child(1)").text();
    	 var time = $("#timeSelect").val();
    	 renderAlysisData(groupid,time);
	 });
}

function intActline(groupid){
	$.ajax({
        type : "POST",  //请求方式
        url : "/actlineData",  //请求路径
        async:true,
        data:{
        	groupid:groupid
        },
        success : function(rs) {  //异步请求成功执行的回调函数
        	var xData = [];
        	var yData = [];
        	var data = rs.data;
        	var nb;
        	for(var x in data){
        		xData.push(new Date(data[x].aa).format("yyyy-MM-dd"));
        		nb = eval(data[x].bb);
        		yData.push(nb.toFixed(2));
        	}
        	
        	var myChart = echarts.init(document.getElementById("actline"));
            var option = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {//鼠标滑过的线条样式
                        type: 'line',
                        lineStyle: {
                            color: 'red',//颜色
                            width: 1,//宽度
                            type: 'cross'//实线
                        },
                        label:{
                        	show:true,
                        	formatter:"{value}"
                        }	
                    }
                },
                calculable: true,
                xAxis: [{//x轴的数据
                    type: 'category',
                    boundaryGap: true,//若为true,则x轴的值不在刻度上.
                    data: xData,
                    axisLabel: {//y轴的内容格式化,很有用的属性
                        formatter: '{value}'
                    }
                }],
                yAxis: [{
                    type: 'value',
                    axisLabel: {//y轴的内容格式化,很有用的属性
                        formatter: '{value}%'
                    }
                }],
                series: [{
                    itemStyle: {
                        normal: {
                            lineStyle: {
                                color: '#3399ff'//控制折线颜色
                            }
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'top',
                            formatter: '{c}%'
                        }
                    },
                    name: '活跃度',
                    type: 'line',
                    data: yData
                }]
            };
            // 为echarts对象加载数据
            myChart.setOption(option);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
}

/**
 * 初始化活动选择下拉
 * @returns
 */
function initActGroupsSelect(){
	$.ajax({
        type : "POST",  //请求方式
        url : "/initActAll",  //请求路径
        async:true,
        success : function(data) {  //异步请求成功执行的回调函数
           var actData = data.data;
           actGroups = actData;
           var defualtGroupid;
           for(var i in actData){
        	 //活动选择下拉数据
        	   if(i == 0){
        		   $("#actSelect").append("<option selected value='"+actData[i]['groupid']+"'>"+actData[i]['gname']+"</option>");
        		   defualtGroupid = actData[i]['groupid'];
        		   dfTime = actData[i]['endTime'];
        		   initTimeSelect(dfTime,defualtGroupid);
        	   }else{
        		   $("#actSelect").append("<option value='"+actData[i]['groupid']+"'>"+actData[i]['gname']+"</option>");
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
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
}

function initAllGroupType(){
	$.ajax({
        type : "POST",  //请求方式
        url : "/initAllGroupType",  //请求路径
        async:true,
        success : function(data) {  //异步请求成功执行的回调函数
           var types = data.data;
           for(var i in types){
        	   if(i == 0){
        		   $("#groupType").append("<option selected value='"+types[i]['type']+"'>"+types[i]['name']+"</option>");
        	   }else{
        		   $("#groupType").append("<option value='"+types[i]['type']+"'>"+types[i]['name']+"</option>");
        	   }
           }
           var groupid = $("#actSelect").val();
           var time = $("#timeSelect").val();
           renderStaticDataAcc(groupid,time);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
}

/**
 * 初始化日期控件
 * @returns
 */
function initTimeSelect(dfTime,groupid){
	dfTime = dfTime+24*60*60*1000;
	var nowTime = new Date().getTime();
	var date;
	if(nowTime>dfTime){//当前时间大于结束时间取结束时间
		date = new Date(dfTime);
	}else{
		date = new Date();
	}
	laydate.render({
		  elem: '#timeSelect', //指定元素
		  theme: 'molv',
		  value: date
	});	
	//加载默认活动分析数据======to==do
	renderAlysisData(groupid,new Date(date).format("yyyy-MM-dd"));
	/*laydate.render({
		elem: '#beginDate', //指定元素
		theme: 'molv',
		value: new Date()
	});	
	laydate.render({
		elem: '#endDate', //指定元素
		theme: 'molv',
		value: new Date()
	});	*/
}

/**
 * 数据分析查询
 * @returns
 */
function doSearch(){
	$("#searchIndex").click(function(){
		 var groupid = $("#actSelect").val();
		 var time = $("#timeSelect").val();
		 if($.isEmptyObject(groupid) || $.isEmptyObject(time)){
			 layer.msg("请选择活动和时间",{icon:2});
			 return false;
		 }
		 renderAlysisData(groupid,time);
	});
}

/**
 * @param groupid 活动id
 * @param time 查询时间
 * @returns
 */
function renderAlysisData(groupid,time){
	//绘制用户活跃度折线图
	intActline(groupid);
	//加载相册信息
	renderGroupInfo(groupid);
	//加载活动状态
	renderActStsInfo(groupid,time);
	//用户数:（按创建相册数区间）
	renderStaticData1(groupid,time);
	//相册数:（按相册成员数区间）
	renderStaticData2(groupid,time);
	//相册数:（按相册动态数区间）
	renderStaticData3(groupid,time);
	//
	renderStaticDataAcc(groupid,time);
	//
	renderGroupsPicUser(groupid,time);
	//相册数:（按相册互动数区间）
	renderStaticData4(groupid,time);
	//用户数:（按上传动态数区间）
	renderStaticData5(groupid,time);
	//用户数:（按上传动态数区间）
	renderStaticData6(groupid,time);
	
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

/**
 * 加载活动状态
 * @param groupid
 * @returns
 */
function renderActStsInfo(groupid,time){
	$.ajax({
        type : "POST",  //请求方式
        url : "/renderActStsInfo",  //请求路径
        data:{
        	groupid:groupid,
        	time:time
        },
        async:true,
        success : function(rs) {  //异步请求成功执行的回调函数
           var data = rs.data.target;
           $("#userTarget i").empty();
           $("#userTarget i").each(function(){
        	   var id=$(this).attr("id");
        	   for(var key in data){
        		   if(id == 'usersActiviteAll'){
        			   var userTotal = data['usersTotal'];
        			   var usersActiviteAll = data['usersActiviteAll'];
        	           var perct = (usersActiviteAll*100/userTotal).toFixed(2);
        	           $("#usersActiviteAll").html(usersActiviteAll+"（整体活跃度:"+perct+"%）");
        		   }else{
        			   if(id == key){
                		   $(this).html(data[key]);
                		   break;
                	   } 
        		   }
               }
           });
           
           var invite = rs.data.invite;
           $("#invite i").empty();
           $("#invite i").each(function(){
        	   var id = $(this).attr("id");
        	   for(var key in invite){
            	   if(id == key){
            		   $(this).html(invite[key]);
            		   break;
            	   }
               }
           });
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
}

/**
 * 用户数:（按创建相册数区间）
 * @returns
 */
function renderStaticData1(groupid,time){
	$.ajax({
        type : "POST",  //请求方式
        url : "/renderStaticData1",  //请求路径
        data:{
        	groupid:groupid,
        	time:time
        },
        async:true,
        success : function(rs) {
        	var data = rs.data;
        	var data1 = [];
        	var data2 = [];
        	var data3 = [];
        	var data4 = [];
        	
        	var  sts1 = {};
    		var  sts2 = {};
    		var  sts3 = {};
    		var  sts4 = {};
    		
    		//活动开始前老用户
    		 var cg1 = 0;//创建相册数:
    		 var t1 = 0;//总创建人数
    		 var cgu1 = 0;//创建相册的人数
    		 
    		 
    		 //活动开始中老用户
    		 var cg2 = 0;//创建相册数:
    		 var t2 = 0;//总创建人数
    		 var cgu2 = 0;//创建相册的人数
    		 
    		 
    		 //活动开始中新用户
    		 var cg3 = 0;//创建相册数:
    		 var t3 = 0;//总创建人数
    		 var cgu3 = 0;//创建相册的人数
    		 
    		 //活动开始中新用户汇总
    		 var cg4 = 0;//创建相册数:
    		 var t4 = 0;//总创建人数
    		 var cgu4 = 0;//创建相册的人数
    		 
        	for(var x in data){
        		var item = data[x];
        		//活动开始之前的老用户
        		var cut1 = {
        			groupsCut:item['groupsCut'],
        			cnt:item['usersCountOldPre']
        		};
        		data1.push(cut1);
        		t1 += "+"+item['usersCountOldPre'];
        		if(item['groupsCut'] != 0){
        			cgu1 += "+"+item['usersCountOldPre'];
        		}
        		cg1 += "+"+item['groupsCountOldPre'];
        		
        		//活动期间的老用户
        		var cut2 = {
        			groupsCut:item['groupsCut'],
        			cnt:item['usersCountOld']
        		};
        		data2.push(cut2);
        		t2 += "+"+item['usersCountOld'];
        		if(item['groupsCut'] != 0){
        			cgu2 += "+"+item['usersCountOld'];
        		}
        		cg2 += "+"+item['groupsCountOld'];
        		
        		//活动期间的新用户
        		var cut3 = {
        			groupsCut:item['groupsCut'],
        			cnt:item['usersCountNew']
        		};
        		data3.push(cut3);
        		t3 += "+"+item['usersCountNew'];
        		if(item['groupsCut'] != 0){
        			cgu3 += "+"+item['usersCountNew'];
        		}
        		cg3 += "+"+item['groupsCountNew'];
        		
        		//活动期间总体的用户情况
        		var total = eval(item['usersCountOld']+"+"+item['usersCountNew']);
        		var cut4 = {
        				groupsCut:item['groupsCut'],
        				cnt:total
        		};
        		data4.push(cut4);
        	}
        	
        	
        	cg1 = eval(cg1);
        	t1 = eval(t1);
        	cgu1 = eval(cgu1);
        	sts1 = {
        		index1:(cgu1*100/t1).toFixed(2)+"%",
        		index2:isNaN(cg1/cgu1)?0:(cg1/cgu1).toFixed(2),
        		index3:cg1
        	}
        	for(var x in sts1){
        		$("#groupCnt1").find("."+x).html(sts1[x]);
        	}
        	
        	cg2 = eval(cg2);
        	t2 = eval(t2);
        	cgu2 = eval(cgu2);
        	sts2 = {
        			index1:(cgu2*100/t2).toFixed(2)+"%",
        			index2:isNaN(cg2/cgu2)?0:(cg2/cgu2).toFixed(2),
        			index3:cg2
        	}
        	for(var x in sts2){
        		$("#groupCnt2").find("."+x).html(sts2[x]);
        	}
        	cg3 = eval(cg3);
        	t3 = eval(t3);
        	cgu3 = eval(cgu3);
        	sts3 = {
        			index1:(cgu3*100/t3).toFixed(2)+"%",
        			index2:isNaN(cg3/cgu3)?0:(cg3/cgu3).toFixed(2),
        			index3:t3,
        			index4:cg3
        	}
        	for(var x in sts3){
        		$("#groupCnt3").find("."+x).html(sts3[x]);
        	}
        	
        	var cjb = (cgu3*100/cgu2).toFixed(2)+"%";
        	sts4 = {
        			index1:((cgu2+cgu3)*100/(t2+t3)).toFixed(2)+"%",
        			index2:isNaN((cg2+cg3)/(cgu2+cgu3))?0:((cg2+cg3)/(cgu2+cgu3)).toFixed(2),
        			index3:(t2+t3),
        			index4:cjb,
        	}
        	
        	for(var x in sts4){
        		$("#groupCnt4").find("."+x).html(sts4[x]);
        	}
        	
    		var dataArr = [];
    		dataArr.push(data1);
    		dataArr.push(data2);
    		dataArr.push(data3);
    		dataArr.push(data4);
    		
    		
    		$(".userGroupCnt").each(function(index){
    			var data = dataArr[index];
    			var headHtml = "<tr>";
    			var bodyHtml = "<tr>";
    			for(var i in data){
    				headHtml += "<th>"+data[i].groupsCut+"</th>";
    				bodyHtml += "<td>"+data[i].cnt+"</td>";
    			}
    			headHtml += "</tr>";
    			bodyHtml += "</tr>";
    			$(this).find("thead").html(headHtml);
    			$(this).find("tbody").html(bodyHtml);
    		});
        	
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
}

/**
 * 相册数:（按相册成员数区间）
 * @param groupid
 * @param time
 * @returns
 */
function renderStaticData2(groupid,time){
	$.ajax({
		type : "POST",  //请求方式
		url : "/renderStaticData2",  //请求路径
		data:{
			groupid:groupid,
			time:time
		},
		async:true,
		success : function(rs) {
			var data = rs.data;
			var data1 = [];
			var data2 = [];
			var data3 = [];
			var data4 = [];
			
			//活动开始前老用户
			var cg1 = 0;//创建相册数:
			
			
			//活动开始中老用户
			var cg2 = 0;//创建相册数:
			
			
			//活动开始中新用户
			var cg3 = 0;//创建相册数:
			
			//活动开始中新用户汇总
			var cg4 = 0;//创建相册数:
				
				for(var x in data){
					var item = data[x];
					//活动开始之前的老用户
					var cut1 = {
							groupsCut:item['usersCut'],
							cnt:item['groupsCountOldPre']
					};
					data1.push(cut1);
					cg1 += "+"+item['groupsCountOldPre'];
					
					//活动期间的老用户
					var cut2 = {
							groupsCut:item['usersCut'],
							cnt:item['groupsCountOld']
					};
					data2.push(cut2);
					cg2 += "+"+item['groupsCountOld'];
					
					//活动期间的新用户
					var cut3 = {
							groupsCut:item['usersCut'],
							cnt:item['groupsCountNew']
					};
					data3.push(cut3);
					cg3 += "+"+item['groupsCountNew'];
					
					//活动期间总体的用户情况
					var total = eval(item['groupsCountOld']+"+"+item['groupsCountNew']);
					var cut4 = {
							groupsCut:item['usersCut'],
							cnt:total
					};
					data4.push(cut4);
				}
				
				
				cg1 = eval(cg1);
				cg2 = eval(cg2);
				cg3 = eval(cg3);
				cg4 = cg2+cg3;
				var stsData = [cg1,cg2,cg3,cg4];
				$(".groupUserCntDiv .index").each(function(i){
					$(this).text(stsData[i]);
				});
				
				
				
				
				var dataArr = [];
				dataArr.push(data1);
				dataArr.push(data2);
				dataArr.push(data3);
				dataArr.push(data4);
				
				
				$(".groupUserCnt").each(function(index){
					var data = dataArr[index];
					var headHtml = "<tr>";
					var bodyHtml = "<tr>";
					for(var i in data){
						headHtml += "<th>"+data[i].groupsCut+"</th>";
						bodyHtml += "<td>"+data[i].cnt+"</td>";
					}
					headHtml += "</tr>";
					bodyHtml += "</tr>";
					$(this).find("thead").html(headHtml);
					$(this).find("tbody").html(bodyHtml);
				});
				
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg("访问服务器失败！",{icon:2});
		}
	});
}

/**
 * 相册数:按相册动态数区间
 * @param groupid
 * @param time
 * @returns
 */
function renderStaticData3(groupid,time){
	$.ajax({
		type : "POST",  //请求方式
		url : "/renderStaticData3",  //请求路径
		data:{
			groupid:groupid,
			time:time
		},
		async:true,
		success : function(rs) {
			var data = rs.data;
			var data1 = [];
			var data2 = [];
			var data3 = [];
			var data4 = [];
			
			//活动开始前老用户
			var usp1 = 0;//有效相册动态数:
			var tg1 = 0;//总相册数
			
			
			
			//活动开始中老用户
			var usp2 = 0;//有效相册动态数:
			var tg2 = 0;//总相册数
			
			
			//活动开始中新用户
			var usp3 = 0;//有效相册动态数:
			var tg3 = 0;//总相册数
			
			//活动开始中新用户汇总
			var usp4 = 0;//有效相册动态数:
			var tg4 = 0;//总相册数
			
			for(var x in data){
				var item = data[x];
				//活动开始之前的老用户
				var cut1 = {
						groupsCut:item['eventsCut'],
						cnt:item['groupsCountOldPre']
				};
				data1.push(cut1);
				if(item['eventsCut'] != 0){
					usp1 += "+"+item['groupsCountOldPre'];
        		}
				tg1 += "+"+item['groupsCountOldPre'];
				
				//活动期间的老用户
				var cut2 = {
						groupsCut:item['eventsCut'],
						cnt:item['groupsCountOld']
				};
				data2.push(cut2);
				if(item['eventsCut'] != 0){
					usp2 += "+"+item['groupsCountOld'];
        		}
				tg2 += "+"+item['groupsCountOld'];
				
				//活动期间的新用户
				var cut3 = {
						groupsCut:item['eventsCut'],
						cnt:item['groupsCountNew']
				};
				data3.push(cut3);
				if(item['eventsCut'] != 0){
					usp3 += "+"+item['groupsCountNew'];
        		}
				tg3 += "+"+item['groupsCountNew'];
				
				//活动期间总体的用户情况
				var total = eval(item['groupsCountOld']+"+"+item['groupsCountNew']);
				var cut4 = {
						groupsCut:item['eventsCut'],
						cnt:total
				};
				data4.push(cut4);
			}
			usp1 = eval(usp1);
			tg1  = eval(tg1);
			
			usp2 = eval(usp2);
			tg2  = eval(tg2);
			
			usp3 = eval(usp3);
			tg3  = eval(tg3);
			
			usp4 = usp2+usp3;
			tg4  = tg2+tg3;
			
			var stsData = [toPercent(usp1,tg1),toPercent(usp2,tg2),toPercent(usp3,tg3),toPercent(usp4,tg4)];
			$(".groupEventCntDiv .index").each(function(i){
				$(this).text(stsData[i]);
			});
			
			var dataArr = [];
			dataArr.push(data1);
			dataArr.push(data2);
			dataArr.push(data3);
			dataArr.push(data4);
			
			
			$(".groupEventCnt").each(function(index){
				var data = dataArr[index];
				var headHtml = "<tr>";
				var bodyHtml = "<tr>";
				for(var i in data){
					headHtml += "<th>"+data[i].groupsCut+"</th>";
					bodyHtml += "<td>"+data[i].cnt+"</td>";
				}
				headHtml += "</tr>";
				bodyHtml += "</tr>";
				$(this).find("thead").html(headHtml);
				$(this).find("tbody").html(bodyHtml);
			});
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg("访问服务器失败！",{icon:2});
		}
	});
}

/**
 * 相册数:（按相册互动数区间）
 * @param groupid
 * @param time
 * @returns
 */
function renderStaticData4(groupid,time){
	$.ajax({
		type : "POST",  //请求方式
		url : "/renderStaticData4",  //请求路径
		data:{
			groupid:groupid,
			time:time
		},
		async:true,
		success : function(rs) {
			var data = rs.data;
			var data1 = [];
			var data2 = [];
			var data3 = [];
			var data4 = [];
			
			//活动开始前老用户
			var usp1 = 0;//有效相册互动数:
			var tg1 = 0;//总相册数
			
			//活动开始中老用户
			var usp2 = 0;//有效相册互动数:
			var tg2 = 0;//总相册数
			
			
			//活动开始中新用户
			var usp3 = 0;//有效相册互动数:
			var tg3 = 0;//总相册数
			
			//活动开始中新用户汇总
			var usp4 = 0;//有效相册互动数:
			var tg4 = 0;//总相册数
			
			for(var x in data){
				var item = data[x];
				//活动开始之前的老用户
				var cut1 = {
						groupsCut:item['playCut'],
						cnt:item['groupsCountOldPre']
				};
				data1.push(cut1);
				if(item['playCut'] != 0){
					usp1 += "+"+item['groupsCountOldPre'];
				}
				tg1 += "+"+item['groupsCountOldPre'];
				
				//活动期间的老用户
				var cut2 = {
						groupsCut:item['playCut'],
						cnt:item['groupsCountOld']
				};
				data2.push(cut2);
				if(item['playCut'] != 0){
					usp2 += "+"+item['groupsCountOld'];
				}
				tg2 += "+"+item['groupsCountOld'];
				
				//活动期间的新用户
				var cut3 = {
						groupsCut:item['playCut'],
						cnt:item['groupsCountNew']
				};
				data3.push(cut3);
				if(item['playCut'] != 0){
					usp3 += "+"+item['groupsCountNew'];
				}
				tg3 += "+"+item['groupsCountNew'];
				
				//活动期间总体的用户情况
				var total = eval(item['groupsCountOld']+"+"+item['groupsCountNew']);
				var cut4 = {
						groupsCut:item['playCut'],
						cnt:total
				};
				data4.push(cut4);
			}
			usp1 = eval(usp1);
			tg1  = eval(tg1);
			
			usp2 = eval(usp2);
			tg2  = eval(tg2);
			
			usp3 = eval(usp3);
			tg3  = eval(tg3);
			
			usp4 = usp2+usp3;
			tg4  = tg2+tg3;
			
			var stsData = [toPercent(usp1,tg1),toPercent(usp2,tg2),toPercent(usp3,tg3),toPercent(usp4,tg4)];
			$(".groupPlayCntDiv .index").each(function(i){
				$(this).text(stsData[i]);
			});
			
			var dataArr = [];
			dataArr.push(data1);
			dataArr.push(data2);
			dataArr.push(data3);
			dataArr.push(data4);
			
			
			$(".groupPlayCnt").each(function(index){
				var data = dataArr[index];
				var headHtml = "<tr>";
				var bodyHtml = "<tr>";
				for(var i in data){
					headHtml += "<th>"+data[i].groupsCut+"</th>";
					bodyHtml += "<td>"+data[i].cnt+"</td>";
				}
				headHtml += "</tr>";
				bodyHtml += "</tr>";
				$(this).find("thead").html(headHtml);
				$(this).find("tbody").html(bodyHtml);
			});
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg("访问服务器失败！",{icon:2});
		}
	});
}

/**
 * 用户数:（按上传动态数区间）
 * @param groupid
 * @param time
 * @returns
 */
function renderStaticData5(groupid,time){
	$.ajax({
		type : "POST",  //请求方式
		url : "/renderStaticData5",  //请求路径
		data:{
			groupid:groupid,
			time:time
		},
		async:true,
		success : function(rs) {
			var data = rs.data;
			//活动相册
			var data1 = [];
			var data2 = [];
			var data3 = [];
			
			
			//私密相册
			var sdata1 = [];
			var sdata2 = [];
			var sdata3 = [];
			var sdata4 = [];
			
			
	   		 //活动开始中老用户
	   		 var cg1 = 0;//有效上传人总数:
	   		 var t1 = 0;//用户总数
	   		 var cgu1 = 0;//总上传动态数
	   		 
	   		 
	   		 //活动开始中新用户
	   		 var cg2 = 0;//有效上传人总数:
	   		 var t2 = 0;//用户总数
	   		 var cgu2 = 0;//总上传动态数
	   		 
	   		 //活动开始中新用户汇总
	   		 var cg3 = 0;//有效上传人总数:
	   		 var t3 = 0;//用户总数
	   		 var cgu3 = 0;//总上传动态数
	   		 
	   		 //===================
	   		 
	   		 //活动开始前老用户
	   		 var scg1 = 0;//有效上传人总数:
	   		 var st1 = 0;//用户总数
	   		 var scgu1 = 0;//总上传动态数
	   		 
	   		 
	   		 //活动开始中老用户
	   		 var scg2 = 0;//有效上传人总数:
	   		 var st2 = 0;//用户总数
	   		 var scgu2 = 0;//总上传动态数
	   		 
	   		 
	   		 //活动开始中新用户
	   		 var scg3 = 0;//有效上传人总数:
	   		 var st3 = 0;//用户总数
	   		 var scgu3 = 0;//总上传动态数
	   		 
	   		 //活动开始中新用户汇总
	   		 var scg4 = 0;//有效上传人总数:
	   		 var st4 = 0;//用户总数
	   		 var scgu4 = 0;//总上传动态数
	   		 
	   		 
			
			for(var x in data){
				var item = data[x];
				//活动开始之前的老用户
				var scut1 = {
						groupsCut:item['eventsCut'],
						cnt:item['usersCountOldPreSm']
				};
				sdata1.push(scut1);
				if(item['eventsCut'] != 0){
					scg1 += "+"+item['usersCountOldPreSm'];
				}
				st1 += "+"+item['usersCountOldPreSm'];
				scgu1 += "+"+item['eventsCountOldPreSm'];
				
				
				//活动期间的老用户
				var cut2 = {
						groupsCut:item['eventsCut'],
						cnt:item['usersCountOldHd']
				};
				data1.push(cut2);
				if(item['eventsCut'] != 0){
					cg1 += "+"+item['usersCountOldHd'];
				}
				t1 += "+"+item['usersCountOldHd'];
				cgu1 += "+"+item['eventsCountOldHd'];
				
				
				var scut2 = {
						groupsCut:item['eventsCut'],
						cnt:item['usersCountOldSm']
				};
				sdata2.push(scut2);
				if(item['eventsCut'] != 0){
					scg2 += "+"+item['usersCountOldSm'];
				}
				st2 += "+"+item['usersCountOldSm'];
				scgu2 += "+"+item['eventsCountOldSm'];
				
				
				//活动期间的新用户
				var cut3 = {
						groupsCut:item['eventsCut'],
						cnt:item['usersCountNewHd']
				};
				data2.push(cut3);
				if(item['eventsCut'] != 0){
					cg2 += "+"+item['usersCountNewHd'];
				}
				t2 += "+"+item['usersCountNewHd'];
				cgu2 += "+"+item['eventsCountNewHd'];
				
				var scut3 = {
						groupsCut:item['eventsCut'],
						cnt:item['usersCountNewSm']
				};
				sdata3.push(scut3);
				if(item['eventsCut'] != 0){
					scg3 += "+"+item['usersCountNewSm'];
				}
				st3 += "+"+item['usersCountNewSm'];
				scgu3 += "+"+item['eventsCountNewSm'];
				
				//活动期间总体的用户情况
				var total = eval(item['usersCountOldHd']+"+"+item['usersCountNewHd']);
				var cut4 = {
						groupsCut:item['eventsCut'],
						cnt:total
				};
				data3.push(cut4);
				
				var stotal = eval(item['usersCountOldSm']+"+"+item['usersCountNewSm']);
				var scut4 = {
						groupsCut:item['eventsCut'],
						cnt:stotal
				};
				sdata4.push(scut4);
			}
			
			
			cg1 = eval(cg1);//有效上传人总数:
			t1 = eval(t1);//用户总数
			cgu1 = eval(cgu1)
			var sts1 = calcUsereventSts(cg1,t1,cgu1);
			
			cg2 = eval(cg2);//有效上传人总数:
			t2 = eval(t2);//用户总数
			cgu2 = eval(cgu2)
			var sts2 = calcUsereventSts(cg2,t2,cgu2);
			
			cg3 = cg1+cg2;//有效上传人总数:
			t3 = t1+t2;//用户总数
			cgu3 = cgu1+cgu2;
			var sts3 = calcUsereventSts(cg3,t3,cgu3);
			
			var stsArr = [sts1,sts2,sts3];
			for(var i=1;i<=stsArr.length;i++){
				var inData = stsArr[i-1];
				$(".userEventCutDiv"+i).find(".index1").html(inData["index1"]);
				$(".userEventCutDiv"+i).find(".index2").html(inData["index2"]);
				$(".userEventCutDiv"+i).find(".index3").html(inData["index3"]);
			}
			
			$(".userEventCutDiv3").find(".index4").html((cg2*100/cg1).toFixed(2)+"%");
			
			scg1 = eval(scg1);//有效上传人总数:
			st1 = eval(st1);//用户总数
			scgu1 = eval(scgu1);//总上传动态数
			var smSts1 = calcUsereventSts(scg1,st1,scgu1);
			
			scg2 = eval(scg2);//有效上传人总数:
			st2 = eval(st2);//用户总数
			scgu2 = eval(scgu2);//总上传动态数
			var smSts2 = calcUsereventSts(scg2,st2,scgu2);
			
			scg3 = eval(scg3);//有效上传人总数:
			st3 = eval(st3);//用户总数
			scgu3 = eval(scgu3);//总上传动态数
			var smSts3 = calcUsereventSts(scg3,st3,scgu3);
			
			scg4 = scg2+scg3;//有效上传人总数:
			st4 = st2+st3;//用户总数
			scgu4 = scgu2+scgu3;//总上传动态数
			var smSts4 = calcUsereventSts(scg4,st4,scgu4);
			
			var stsArr1 = [smSts1,smSts2,smSts3,smSts4];
			for(var i=1;i<=stsArr1.length;i++){
				var inData = stsArr1[i-1];
				$(".userEventCutDiv-"+i).find(".index1").html(inData["index1"]);
				$(".userEventCutDiv-"+i).find(".index2").html(isNaN(inData["index2"]) ? 0 : inData["index2"]);
				$(".userEventCutDiv-"+i).find(".index3").html(isNaN(inData["index3"]) ? 0 : inData["index3"]);
			}
			$(".userEventCutDiv-4").find(".index4").html((scgu3*100/scgu2).toFixed(2)+"%");
			
			
			var dataArr = [];
			dataArr.push(data1);
			dataArr.push(data2);
			dataArr.push(data3);
			
			var sdataArr = [];
			sdataArr.push(sdata1);
			sdataArr.push(sdata2);
			sdataArr.push(sdata3);
			sdataArr.push(sdata4);
			
			
			$(".userEventCut1").each(function(index){
				var data = dataArr[index];
				var headHtml = "<tr>";
				var bodyHtml = "<tr>";
				for(var i in data){
					headHtml += "<th>"+data[i].groupsCut+"</th>";
					bodyHtml += "<td>"+data[i].cnt+"</td>";
				}
				headHtml += "</tr>";
				bodyHtml += "</tr>";
				$(this).find("thead").html(headHtml);
				$(this).find("tbody").html(bodyHtml);
			});
			
			$(".userEventCut2").each(function(index){
				var data = sdataArr[index];
				var headHtml = "<tr>";
				var bodyHtml = "<tr>";
				for(var i in data){
					headHtml += "<th>"+data[i].groupsCut+"</th>";
					bodyHtml += "<td>"+data[i].cnt+"</td>";
				}
				headHtml += "</tr>";
				bodyHtml += "</tr>";
				$(this).find("thead").html(headHtml);
				$(this).find("tbody").html(bodyHtml);
			});
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg("访问服务器失败！",{icon:2});
		}
	});
}


function calcUsereventSts(v1,v2,v3){
	var s1 = (v1*100/v2).toFixed(2)+"%";
	var s2 = (v3/v1).toFixed(2);
	var s3 = v3;
	return {
		index1:s1,
		index2:s2,
		index3:s3
	};
}


function toPercent(v,d){
	var cs = eval(v+"/"+d);
	return (cs*100).toFixed(2)+"%";
}


/**
 * 用户数:（按上传动态数区间）
 * @param groupid
 * @param time
 * @returns
 */
function renderStaticData6(groupid,time){
	$.ajax({
		type : "POST",  //请求方式
		url : "/renderStaticData6",  //请求路径
		data:{
			groupid:groupid,
			time:time
		},
		async:true,
		success : function(rs) {
			var data = rs.data;
			//活动相册
			var data1 = [];
			var data2 = [];
			var data3 = [];
			
			
			//私密相册
			var sdata1 = [];
			var sdata2 = [];
			var sdata3 = [];
			var sdata4 = [];
			
			
	   		 //活动开始中老用户
	   		 var cg1 = 0;//有效互动总数:
	   		 var t1 = 0;//用户总数
	   		 var cgu1 = 0;//总互动数
	   		 
	   		 
	   		 //活动开始中新用户
	   		 var cg2 = 0;//有效互动总数:
	   		 var t2 = 0;//用户总数
	   		 var cgu2 = 0;//总互动数
	   		 
	   		 //活动开始中新用户汇总
	   		 var cg3 = 0;//有效互动总数:
	   		 var t3 = 0;//用户总数
	   		 var cgu3 = 0;//总互动数
	   		 
	   		 //===================
	   		 
	   		 //活动开始前老用户
	   		 var scg1 = 0;//有效互动总数:
	   		 var st1 = 0;//用户总数
	   		 var scgu1 = 0;//总互动数
	   		 
	   		 
	   		 //活动开始中老用户
	   		 var scg2 = 0;//有效互动总数:
	   		 var st2 = 0;//用户总数
	   		 var scgu2 = 0;//总互动数
	   		 
	   		 
	   		 //活动开始中新用户
	   		 var scg3 = 0;//有效互动总数:
	   		 var st3 = 0;//用户总数
	   		 var scgu3 = 0;//总互动数
	   		 
	   		 //活动开始中新用户汇总
	   		 var scg4 = 0;//有效互动总数:
	   		 var st4 = 0;//用户总数
	   		 var scgu4 = 0;//总互动数
	   		 
	   		 
			
			for(var x in data){
				var item = data[x];
				//活动开始之前的老用户
				var scut1 = {
						groupsCut:item['playsCut'],
						cnt:item['usersCountOldPreSm']
				};
				sdata1.push(scut1);
				if(item['playsCut'] != 0){
					scg1 += "+"+item['usersCountOldPreSm'];
				}
				st1 += "+"+item['usersCountOldPreSm'];
				scgu1 += "+"+item['playsCountOldPreSm'];
				
				
				//活动期间的老用户
				var cut2 = {
						groupsCut:item['playsCut'],
						cnt:item['usersCountOldHd']
				};
				data1.push(cut2);
				if(item['playsCut'] != 0){
					cg1 += "+"+item['usersCountOldHd'];
				}
				t1 += "+"+item['usersCountOldHd'];
				cgu1 += "+"+item['playsCountOldHd'];
				
				
				var scut2 = {
						groupsCut:item['playsCut'],
						cnt:item['usersCountOldSm']
				};
				sdata2.push(scut2);
				if(item['playsCut'] != 0){
					scg2 += "+"+item['usersCountOldSm'];
				}
				st2 += "+"+item['usersCountOldSm'];
				scgu2 += "+"+item['playsCountOldSm'];
				
				
				//活动期间的新用户
				var cut3 = {
						groupsCut:item['playsCut'],
						cnt:item['usersCountNewHd']
				};
				data2.push(cut3);
				if(item['playsCut'] != 0){
					cg2 += "+"+item['usersCountNewHd'];
				}
				t2 += "+"+item['usersCountNewHd'];
				cgu2 += "+"+item['playsCountNewHd'];
				
				var scut3 = {
						groupsCut:item['playsCut'],
						cnt:item['usersCountNewSm']
				};
				sdata3.push(scut3);
				if(item['playsCut'] != 0){
					scg3 += "+"+item['usersCountNewSm'];
				}
				st3 += "+"+item['usersCountNewSm'];
				scgu3 += "+"+item['playsCountNewSm'];
				
				//活动期间总体的用户情况
				var total = eval(item['usersCountOldHd']+"+"+item['usersCountNewHd']);
				var cut4 = {
						groupsCut:item['playsCut'],
						cnt:total
				};
				data3.push(cut4);
				
				var stotal = eval(item['usersCountOldSm']+"+"+item['usersCountNewSm']);
				var scut4 = {
						groupsCut:item['playsCut'],
						cnt:stotal
				};
				sdata4.push(scut4);
			}
			
			
			cg1 = eval(cg1);//有效动态总数:
			t1 = eval(t1);//用户总数
			cgu1 = eval(cgu1)
			var sts1 = calcUsereventSts(cg1,t1,cgu1);
			
			cg2 = eval(cg2);//有效动态总数:
			t2 = eval(t2);//用户总数
			cgu2 = eval(cgu2)
			var sts2 = calcUsereventSts(cg2,t2,cgu2);
			
			cg3 = cg1+cg2;//有效动态总数:
			t3 = t1+t2;//用户总数
			cgu3 = cgu1+cgu2;
			var sts3 = calcUsereventSts(cg3,t3,cgu3);
			
			var stsArr = [sts1,sts2,sts3];
			for(var i=1;i<=stsArr.length;i++){
				var inData = stsArr[i-1];
				$(".userGroupsCutDiv"+i).find(".index1").html(inData["index1"]);
				$(".userGroupsCutDiv"+i).find(".index2").html(inData["index2"]);
				$(".userGroupsCutDiv"+i).find(".index3").html(inData["index3"]);
			}
			
			$(".userGroupsCutDiv3").find(".index4").html((cgu2*100/cgu1).toFixed(2)+"%");
			
			
			scg1 = eval(scg1);//有效互动总数:
			st1 = eval(st1);//用户总数
			scgu1 = eval(scgu1);//总互动数
			var smSts1 = calcUsereventSts(scg1,st1,scgu1);
			
			scg2 = eval(scg2);//有效互动总数:
			st2 = eval(st2);//用户总数
			scgu2 = eval(scgu2);//总互动数
			var smSts2 = calcUsereventSts(scg2,st2,scgu2);
			
			scg3 = eval(scg3);//有效互动总数:
			st3 = eval(st3);//用户总数
			scgu3 = eval(scgu3);//总互动数
			var smSts3 = calcUsereventSts(scg3,st3,scgu3);
			
			scg4 = scg2+scg3;//有有效互动总数:
			st4 = st2+st3;//用户总数
			scgu4 = scgu2+scgu3;//总互动数
			var smSts4 = calcUsereventSts(scg4,st4,scgu4);
			
			var stsArr1 = [smSts1,smSts2,smSts3,smSts4];
			for(var i=1;i<=stsArr1.length;i++){
				var inData = stsArr1[i-1];
				$(".userGroupsCutDiv-"+i).find(".index1").html(inData["index1"]);
				$(".userGroupsCutDiv-"+i).find(".index2").html(isNaN(inData["index2"]) ? 0 : inData["index2"]);
				$(".userGroupsCutDiv-"+i).find(".index3").html(isNaN(inData["index3"]) ? 0 : inData["index3"]);
			}
			$(".userGroupsCutDiv-4").find(".index4").html((scgu3*100/scgu2).toFixed(2)+"%");
			
			var dataArr = [];
			dataArr.push(data1);
			dataArr.push(data2);
			dataArr.push(data3);
			
			var sdataArr = [];
			sdataArr.push(sdata1);
			sdataArr.push(sdata2);
			sdataArr.push(sdata3);
			sdataArr.push(sdata4);
			
			
			$(".userGroupCut1").each(function(index){
				var data = dataArr[index];
				var headHtml = "<tr>";
				var bodyHtml = "<tr>";
				for(var i in data){
					headHtml += "<th>"+data[i].groupsCut+"</th>";
					bodyHtml += "<td>"+data[i].cnt+"</td>";
				}
				headHtml += "</tr>";
				bodyHtml += "</tr>";
				$(this).find("thead").html(headHtml);
				$(this).find("tbody").html(bodyHtml);
			});
			
			$(".userGroupCut2").each(function(index){
				var data = sdataArr[index];
				var headHtml = "<tr>";
				var bodyHtml = "<tr>";
				for(var i in data){
					headHtml += "<th>"+data[i].groupsCut+"</th>";
					bodyHtml += "<td>"+data[i].cnt+"</td>";
				}
				headHtml += "</tr>";
				bodyHtml += "</tr>";
				$(this).find("thead").html(headHtml);
				$(this).find("tbody").html(bodyHtml);
			});
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg("访问服务器失败！",{icon:2});
		}
	});
}

/**
 * 相册数:（按相册成员数区间）
 * @param groupid
 * @param time
 * @returns
 */
function renderStaticDataAcc(groupid,time){
	var groupType = $("#groupType").val();
	$("#createAccCutTb1").find("tbody").empty();
	$.ajax({
		type : "POST",  //请求方式
		url : "/renderStaticDataAcc",  //请求路径
		data:{
			groupid:groupid,
			time:time,
			groupType:groupType
		},
		async:true,
		success : function(rs) {
			var data = rs.data;
			bodyHtml = "";
			var tt = {};
			var t1 = 0;
			var t2 = 0;
			var t3 = 0;
			
			for(var x in data){
				var item = data[x];
				t1 += "+"+item.b;
				t2 += "+"+item.c;
				t3 += "+"+item.d;
				bodyHtml += "<tr>";
				bodyHtml += "<td>"+item.a+"</td>";
				bodyHtml += "<td>"+item.b+"</td>";
				bodyHtml += "<td>"+item.c+"</td>";
				bodyHtml += "<td>"+item.d+"</td>";
				bodyHtml += "</tr>";
			}
			t1 = eval(t1);
			t2 = eval(t2);
			t3 = eval(t3);
			var ttHtml = "<tr>";
			ttHtml += "<td>总计</td>";
			ttHtml += "<td>"+t1+"</td>";
			ttHtml += "<td>"+t2+"</td>";
			ttHtml += "<td>"+t3+"</td>";
			ttHtml += "</tr>";
			$("#createAccCutTb1").find("tbody").html(bodyHtml);
			$("#createAccCutTb1").find("tbody").append(ttHtml);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg("访问服务器失败！",{icon:2});
		}
	});
}


function renderGroupsPicUser(groupid,time){
	$.ajax({
		type : "POST",  //请求方式
		url : "/renderGroupsPicUser",  //请求路径
		data:{
			groupid:groupid,
			time:time
		},
		async:true,
		success : function(rs) {
			$("#groupsUserCut").find("tbody").empty();
			var data = rs.data;
			var arr = [];
			for(var x in data){
				var userCut = data[x].usersCut;
				var pictureCut = data[x].pictureCut;
				var showData = data[x].groupsCount+"("+data[x].picturesCount+")";
				var json = {
					userCut:userCut,
					pictureCut:pictureCut,
					showData:showData
				};
				arr.push(json);
			}
			
		
			
			//找出用户分组去重
			var userCuts = [];
			for(var t in arr){
				var base = arr[t];
				if(userCuts.indexOf(base.userCut) == -1){
					userCuts.push(base.userCut);
				}
			}
			
			
			var head = [];
			$("#groupsUserCut").find("thead tr th").each(function(i){
				head.push($(this).text());
			});
			var cData;
			for(var h in userCuts){
				var bodyHtml = "<tr>";
				var item = userCuts[h];
				for(var j in head){
					cData = findItem(head[j],item,arr);
					if(j == 0){
						bodyHtml += "<td>"+item+"</td>";
					}else{
						if(cData === undefined){
							bodyHtml += "<td>0</td>";
						}else{
							bodyHtml += "<td>"+cData.showData+"</td>";
						}
					}
				}
				bodyHtml += "</tr>";
				$("#groupsUserCut").find("tbody").append(bodyHtml);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg("访问服务器失败！",{icon:2});
		}
	});
}
//用户数:（按上传动态数区间）

function findItem(x,y,arr){
	for(var i in arr){
		var item = arr[i];
		if(y == item.userCut && x == item.pictureCut){
			return item;
		}
	}
}


