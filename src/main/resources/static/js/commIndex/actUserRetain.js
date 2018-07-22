var actGroups;//活动信息缓存
$(function(){
	initActGroupsSelect();
	doSearch();
})


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
           var dGroupId;
           for(var i in actData){
        	 //活动选择下拉数据
        	   if(i == 0){
        		   $("#actSelect").append("<option selected value='"+actData[i]['groupid']+"'>"+actData[i]['gname']+"</option>");
        		   dGroupId = actData[i]['groupid'];
        	   }else{
        		   $("#actSelect").append("<option value='"+actData[i]['groupid']+"'>"+actData[i]['gname']+"</option>");
        	   }
        	   
        	 //记载活动相册列表
        	   var liHtml = "";
        	   liHtml += "<li>";
        	   liHtml += "<span class='block'>"+actData[i]['groupid']+"</span>";
        	   liHtml += "<span class='block'>"+actData[i]['gname']+"</span>";
        	   liHtml += "<span class='block'>"+new Date(actData[i]['startTime']).format("yyyy-MM-dd hh:mm:ss")+"</span>";
        	   liHtml += "<span class='block'>"+new Date(actData[i]['endTime']).format("yyyy-MM-dd hh:mm:ss")+"</span>";
        	   liHtml += "<span class='block'>"+actData[i]['type']+"</span>";
        	   liHtml += "</li>";
               $("#listActAll").append(liHtml);
           }
            //加载默认活动分析数据======to==do
       		var groupid = $("#actSelect").val();
       		var time = $("#timeSelect").val();
       		renderAlysisData(dGroupId,new Date().format("yyyy-MM-dd"));
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
function initTimeSelect(){
	laydate.render({
		  elem: '#timeSelect', //指定元素
		  theme: 'molv',
		  value: new Date()
	});	
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
	//加载相册信息
	renderGroupInfo(groupid);
	//加载用户留存情况表格
	renderUserRetainTable(groupid,time);
	
	renderUserRetainLine(groupid,time);
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
 * 加载用户留存情况表格
 * @param groupid
 * @param time
 * @returns
 */
function renderUserRetainTable(groupid,time){
	$.ajax({
        type : "POST",  //请求方式
        url : "/renderUserRetainTable",  //请求路径
        async:true,
        data:{
        	groupid:groupid,
        	time:time
        },
        success : function(rs) {
        	$("#actUserClusterTb").find("tbody").empty();
        	var tbData = [];
        	var data = rs.data;
        	//获取去重列名
        	var userLevelCut = ["超级用户","高级用户","中级用户","低级用户","流失用户","总计"];
        	/*for(var t in data){
				var base = data[t];
				if(userLevelCut.indexOf(base.b) == -1){
					userLevelCut.push(base.b);
				}
			}*/
        	
        	var head = [];
        	$("#actUserClusterTb thead th").each(function(){
        		head.push($(this).text());
        	});
        	
        	for(var h in userLevelCut){
				var bodyHtml = "<tr>";
				var item = userLevelCut[h];
				for(var j in head){
					var cData = findUrTbData(item,head[j],data);
					if(j == 0){
						bodyHtml += "<td>"+item+"</td>";
					}else{
						if(cData === undefined){
							bodyHtml += "<td>0</td>";
						}else{
							bodyHtml += "<td>"+cData.c+"</td>";
						}
					}
				}
				bodyHtml += "</tr>";
				$("#actUserClusterTb").find("tbody").append(bodyHtml);
			}
        	
        	$("#actUserClusterTb").find("tbody tr:not(:last-child)").each(function(i){
				$(this).find("td").each(function(j){
					if(j != 0){
						var txt = $(this).text();
						var total = $("#actUserClusterTb tbody tr:last-child").find("td:nth-child("+(j+1)+")").text();
						if(!isNaN(txt) && total !=0 && txt !=0 ){
							var d = eval(txt+"/"+total);
							d = (d*100).toFixed(2)+"%";
							txt = txt+"<font color='#8679ea'>("+d+")</font>";
							$(this).html(txt);
						}
					}
				});
			});
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
}

/**
 * 绘制统计折线图
 * @param groupid
 * @param time
 * @returns
 */
function renderUserRetainLine(groupid,time){
	$.ajax({
        type : "POST",  //请求方式
        url : "/getUserRetainData",  //请求路径
        async:true,
        data:{
        	groupid:groupid,
        	time:time
        },
        success : function(rs) {
        	var data = rs.data;
        	var total = data.total;
        	var create = data.create;
        	var upload = data.upload;
        	var play = data.play;
        	
        	intActline("line1","平均总行为次数",total);
        	intActline("line2","平均创建行为次数",create);
        	intActline("line3","平均上传行为次数",upload);
        	intActline("line4","平均互动行为次数",play);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
}

var colors = ['#000033','#0000CC','#00CC33','#00FFCC','#FF0099','#397b29', '#8abb6f', '#759c6a', '#bfd3b7'];
function intActline(domId,title,listData){
	var legend = listData.legend;
	var newlegend = [];
	for(var n in legend){
		var itm = legend[n];
		var jn = {
			name:itm,
			textStyle:{color:colors[n]}
		};
		newlegend.push(jn);
	}
	
	var xAxis = listData.xAxis;
	var series = listData.seris;
	var seriesNew = [];
	for(var i in series){
		var map = series[i];
		var name = map["name"];
		var list = map["list"];
		var seriesItem = {
	            name:name,
	            type:'line',
	            stack: '总量',
	            data:list,
	            label: {
                    normal: {
                        show: true,
                        position: 'top',
                        formatter: '{c}'
                    }
                },
                itemStyle : {  
                    normal : {  
                        lineStyle:{  
                            color:colors[i] 
                        }  
                    }  
                }
	        };
		
		seriesNew.push(seriesItem);
	}
	
	var myChart = echarts.init(document.getElementById(domId),curTheme);
	option = {
		    title: {
		        text: title
		    },
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		        data:newlegend,
		        x: 'right'
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		   /* toolbox: {
		        feature: {
		            saveAsImage: {}
		        }
		    },*/
		   /* dataZoom: {
		    	dataBackgroundColor: '#eee',            // 数据背景颜色
		    	fillerColor: 'rgba(64,136,41,0.2)',   // 填充颜色
		    	handleColor: '#408829'     // 手柄颜色
		    	},*/
		    xAxis: {
		        type: 'category',
		        boundaryGap: true,
		        data: xAxis
		    },
		    yAxis: {
		        type: 'value'
		    },
		    series:seriesNew
		};
	 myChart.setOption(option);
}

/**
 * 根据行列查出对应的数据
 * @param x
 * @param y
 * @param arr
 * @returns
 */
function findUrTbData(x,y,arr){
	for(var i in arr){
		if(arr[i].b == x && arr[i].a == y){
			return arr[i];
		}
	}
}

