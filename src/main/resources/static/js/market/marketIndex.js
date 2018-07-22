$(function(){
//	initPortTypeStsList();
//	initportTypeSelect();
	doSearch();
	initTimeSelect();
})

/**
 * 初始化渠道列表
 * @returns
 */
function initPortTypeStsList(){
//	$.ajax({
//        type : "POST",  //请求方式
//        url : "/initPortTypeStsList",  //请求路径
//        async:true,
//        success : function(data) {  //异步请求成功执行的回调函数
//           var actData = data.data;
//           actGroups = actData;
//           var liHead = "<li class='head'>";
//           liHead += "<span class='block'>ID</span>";
//           liHead += "<span class='block'>渠道名</span>";
//           liHead += "<span class='block'>渠道类型</span>";
//           liHead += "<span class='block'>开始时间</span>";
//           liHead += "</li>";
//           $("#listPortTypeAll").append(liHead);
//           var liHtml;
//           for(var i in actData){
//        	   //渠道列表
//        	   liHtml = "";
//        	   liHtml += "<li>";
//        	   liHtml += "<span class='block'>"+actData[i]['id']+"</span>";
//        	   liHtml += "<span class='block'>"+actData[i]['portName']+"</span>";
//        	   liHtml += "<span class='block'>"+actData[i]['typeName']+"</span>";
//        	   liHtml += "<span class='block'>"+new Date(actData[i]['startTime']).format("yyyy-MM-dd hh:mm:ss")+"</span>";
//        	   liHtml += "</li>";
//               $("#listPortTypeAll").append(liHtml);
//           }
//            //加载默认活动分析数据======to==do
////       		renderAlysisData(defualtGroupid,new Date().format("yyyy-MM-dd"));
//        },
//        error : function(XMLHttpRequest, textStatus, errorThrown) {
//            layer.msg("访问服务器失败！",{icon:2});
//        }
//    });
}

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
	var time = $("#timeSelect").val();
	var h = $("#hour").val();
	time = time +" "+h;
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
	renderHotData1(time,'兑换',1);
	renderHotData1(time,'商品抢购分布区间',2);
	renderHotData1(time,'相册获取热度值区间',3);
	renderHotData1(time,'热度值获取方式',4);
}

function renderActOneStsData(time){
	var groupid = 3333;
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
        	hdIndex:"三"
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
        	
        	
//        	if(processData === undefined){
//        		$(".pData").each(function(){
//        			var txt = $(this).text();
//        			var spl = txt.split("：");
//        			$(this).text(spl[0]+"："+0);
//        		});
//        		return;
//        	}
//        	
//        	$(".pData").each(function(){
//        		var data = $(this).attr("data");
//        		var type = $(this).attr("type");
//        		
//        		var rate = $(this).attr("rate");
//        		var c = "";
//    			var d = "";
//        		if(rate){
//        			c = $(this).attr("c");
//        			d = $(this).attr("d");
//        		}
//        		
//        		var text = "";
//        		for(var i in processData){
//        			var item = processData[i];
//        			if(type == item['type']){
//        				text = $(this).text();
//        				var spl = text.split("：");
//                		$("."+x).html(spl[0]+"："+item[data]);
//                		//break;
//        			}
//        			if(c == item['type']){
//        				c = item['users_count'];
//        			}
//        			if(d == item['type']){
//        				d = item['users_count'];
//        			}
//        		}
//        		
//        		if(rate){
//        			eval(c+"/"+d)
//        		}
        		
//        	});
        	
        	/*$(".sData").each(function(){
        		var exp = $(this).attr("val");
        		var fmt = $(this).attr("fmt");
        		var sl = exp.split("/");
        		var x = sl[0];
        		var y = sl[1];
        		x = rsData[x];
        		y = rsData[y];
        		var v;
        		if(fmt){
        			v = eval(x+"/"+y)
        			v = (v*100).toFixed(2)+"%";
        		}else{
        			v = eval(x+"/"+y)
        			v = v.toFixed(2);
        		}
        		v = v=='NaN' ? 0 : v;
        		$(this).text(v);
        	});*/
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
}

function renderHotData1(time,type,dt){
	var date = new Date(Date.parse(time.replace(/-/g,"/")));
	var endTime = date.format("yyyy-MM-dd hh");
	var startTime = new Date(date.getTime()-2*60*60*1000).format("yyyy-MM-dd hh");
	$.ajax({
        type : "POST",  //请求方式
        url : "/renderHotData",  //请求路径
        data:{
        	startTime:startTime,
        	endTime:endTime,
        	type:type
        },
        async:true,
        success : function(rs) {  //异步请求成功执行的回调函数
        	var data = rs.data
        	if(dt == 1){
        		if(item){
        			var item = data[0];
        			$('#dhcs').html(item['count']);
        		}
        	}
        	if(dt == 2){
        		$("#goods tbody").empty();
        		var heads = [];
        		$("#goods thead th:not(:first-child)").each(function(){
        			heads.push($(this).text());
        		});
        		var dates = [];
        		for(var j in data){
        			if(dates.indexOf(data[j].date) == -1){
        				dates.push(data[j].date);
        			}
        		}
        		
        		var html = "";
        		for(var t in dates){
        			html += "<tr>";
        			html += "<td>"+new Date(dates[t]).format("MM-dd")+"</td>";
        			var tt = dates[t];
        			for(var i in heads){
            			var h = heads[i];
            			var count = 0;
            			for(var x in data){
                			var cut = data[x].cut;
                			if(cut == h && tt == data[x].date){
                				count = data[x].count;
                			}
                		}
            			html += "<td>"+count+"</td>";
        			}
            		html += "</tr>";
        		}
        		$("#goods tbody").html(html);
        	}
        	if(dt == 3){
        		$("#groupshot tbody").empty();
        		var hh = "<tr>";
        		$("#groupshot thead th").each(function(index){
        			var count = 0;
        			if(index == 0){
    					hh += "<td>相册数</td>";
    				}else{
    					for(var x in data){
        					if(data[x].cut == $(this).text()){
            					count = data[x].count;
            				}
    					}
    					hh += "<td>"+count+"</td>";
    				}
        			
        		})
        		hh+="</tr>";
        		$("#groupshot tbody").html(hh);
        	}
        	if(dt == 4){
        		$("#getHotFunc tbody").empty();
        		var ss = "<tr>";
        		ss += "<td>数量</td>";
        		$("#getHotFunc thead th:not(:first-child)").each(function(index){
    					var sw = "0";
    					for(var k in data){
    						if(data[k].cut == $(this).attr("val")){
    							sw = data[k].count;
    						}
    					}
    					ss += "<td>"+sw+"</td>"
        		})
        		ss += "</tr>";
        		$("#getHotFunc tbody").html(ss);
        	}
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
}




