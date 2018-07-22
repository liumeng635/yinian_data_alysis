$(function(){
	initPortTypeStsList();
	initportTypeSelect();
	doSearch();
})
/**
 * 初始化渠道列表
 * @returns
 */
function initPortTypeStsList(){
	$.ajax({
        type : "POST",  //请求方式
        url : "/initPortTypeStsList",  //请求路径
        async:true,
        success : function(data) {  //异步请求成功执行的回调函数
           var actData = data.data;
           initportTypeSelect(actData);
           var liHead = "<li class='headT'>";
           liHead += "<span class='block'>ID</span>";
           liHead += "<span class='block'>渠道名</span>";
           liHead += "<span class='block'>渠道类型</span>";
           liHead += "<span class='block'>开始时间</span>";
           liHead += "</li>";
           $("#listPortTypeAll").append(liHead);
           var liHtml;
           for(var i in actData){
        	   //渠道列表
        	   liHtml = "";
        	   liHtml += "<li>";
        	   liHtml += "<span class='block'>"+actData[i]['id']+"</span>";
        	   liHtml += "<span class='block'>"+actData[i]['portName']+"</span>";
        	   liHtml += "<span class='block'>"+actData[i]['typeName']+"</span>";
        	   liHtml += "<span class='block'>"+new Date(actData[i]['startTime']).format("yyyy-MM-dd hh:mm:ss")+"</span>";
        	   liHtml += "</li>";
               $("#listPortTypeAll").append(liHtml);
           }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
}

/**
 * 初始化渠道下拉选择列表
 * @returns
 */
function initportTypeSelect(portData){
	initTimeSelect();
       var defualtPortName;
       for(var i in portData){
    	 //活动选择下拉数据
    	   if(i == 0){
    		   $("#portSelect").append("<option selected value='"+portData[i]['portName']+"'>"+portData[i]['portName']+"</option>");
    		   defualtPortName = portData[i]['portName'];
    	   }else{
    		   $("#portSelect").append("<option value='"+portData[i]['portName']+"'>"+portData[i]['portName']+"</option>");
    	   }
       }
        //加载默认活动分析数据======to==do
   		renderAlysisData(defualtPortName,new Date().format("yyyy-MM-dd"));
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
		 var portName = $("#portSelect").val();
		 var time = $("#timeSelect").val();
		 if($.isEmptyObject(portName) || $.isEmptyObject(time)){
			 layer.msg("请选择活动和时间",{icon:2});
			 return false;
		 }
		 renderAlysisData(portName,time);
	});
}

/**
 * @param portType 活动id
 * @param time 查询时间
 * @returns
 */
function renderAlysisData(portName,time){
	console.log(portName+":"+time);
	renderChannelStsData(portName,time);
}

function renderChannelStsData(portName,time){
	$.ajax({
        type : "POST",  //请求方式
        url : "/renderChannelStsData",  //请求路径
        data:{
        	portName:portName,
        	time:time
        },
        async:true,
        success : function(rs) {  //异步请求成功执行的回调函数
        	var rsData = rs.data;
        	if(rsData === undefined){
        		$(".chData").text(0);
        		return;
        	}
        	for(var x in rsData){
        		$("."+x).text(rsData[x]);
        	}
        	$(".sData").each(function(){
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
        	});
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
}




