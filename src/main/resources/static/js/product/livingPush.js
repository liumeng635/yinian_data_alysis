var d1;
var d2;
var d3;
$(function(){
	initTimeSelect();
	doSearch();
	$(".tab1").click(function(){
		$(this).addClass("tab1Hv");
		$(".tab1").not(this).removeClass("tab1Hv");
		var type=$(this).attr("type");
		renderPushTable1(d1,type);
    	renderPushTable2(d2,type);
    	renderPushTable3(d3,type);
	});
	
	
	/*$("#paoshu").click(function(){
		$.ajax({
	        type : "POST",  //请求方式
	        url : "/collectData",  //请求路径
	        async:true,
	        success : function(data) {
	        	
	        },
	        error : function(XMLHttpRequest, textStatus, errorThrown) {
	            layer.msg("访问服务器失败！",{icon:2});
	        }
	    });
	});*/
})

function doSearch(){
	$("#searchIndex").click(function(){
		var dtime = $("#timeSelect").val();
		renderGroupsStsData(dtime)
	});
	
}

function initTimeSelect(){
	var time = new Date().getTime();
	time = time - 24*60*60*1000;
	laydate.render({
		  elem: '#timeSelect', //指定元素
		  theme: 'molv',
		  value: new Date(time)
	});
	defaultTime =  new Date(time).format("yyyy-MM-dd");
	renderGroupsStsData(defaultTime);
}

/**
 * 加载相册留存数据统计信息
 * @returns
 */
function renderGroupsStsData(time,type){
	$.ajax({
        type : "POST",  //请求方式
        url : "/renderLivingPushData",  //请求路径
        async:true,
        data:{
        	time:time
        },
        success : function(data) {
        	if(type == undefined){
        		type = "0";
        	}
        	d1 = data.data.data1;
        	d2 = data.data.data2;
        	d3 = data.data.data3;
        	renderPushTable1(data.data.data1,type);
        	renderPushTable2(data.data.data2,type);
        	renderPushTable3(data.data.data3,type);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
}

function renderPushTable1(data,type){
	$("#pushTable1").find("tbody").empty();
	var groups1 = data['groupsType1'];
	var groups2 = data['groupsType2'];
	
	var size1 = groups1.length;
	var size2 = groups2.length;
	
	$("#pushTable1").find(".size1").attr("colspan",size1+1);
	$("#pushTable1").find(".size2").attr("colspan",size2+1);
	
	var headHml = "";
	for(var x in groups1){
		headHml += "<th>"+groups1[x]+"</th>";
	}
	headHml += "<th>小计</th>";
	for(var i in groups2){
		headHml += "<th>"+groups2[i]+"</th>";
	}
	headHml += "<th>小计</th>";
	$("#pushTable1").find(".groupsType").html(headHml);
	
	var gridData = data['list'];
	
	var bodyHtml = "";
	var tbLength = 0;
	
	var lData = gridData[0];
	for(var t in lData){
		tbLength += 1;
	}
	
	var pName = "";
	for(var i in gridData){
		var trData = gridData[i];
		bodyHtml += "<tr>";
		
		for(var j=0;j<tbLength/2;j++){
			if(type=='0'){
				pName = "data"+j;
			}else{
				pName = "data_"+j;
			}
			bodyHtml += "<td>"+trData[pName]+"</td>";
		}
		bodyHtml += "</tr>";
	}
	$("#pushTable1").find("tbody").html(bodyHtml);
	
	
	$("#pushTable1").find("tbody tr").each(function(){
		$(this).find("td").each(function(i){
			if(i == 0){
				$(this).css({"background":"#b5b0a8","color":"#FFF","font-weight":"bold"});
			}
		});
		
	});
	
	
	$("#pushTable1").find("tbody td").each(function(){
		var txt = $(this).text();
		if(txt.indexOf("%")>-1){
			$(this).css({"background":"#e6f2fd"});
		}
	});
	
	$("#pushTable1").find("tbody tr:last-child").find("td").each(function(){
		$(this).css({"background":"#cdcbcb"});
});
	
}

function renderPushTable2(data,type){
	$("#pushTable2").find("tbody").empty();
	var groups1 = data['groupsType1'];
	var groups2 = data['groupsType2'];
	
	var size1 = groups1.length;
	var size2 = groups2.length;
	
	$("#pushTable2").find(".size1").attr("colspan",size1+1);
	$("#pushTable2").find(".size2").attr("colspan",size2+1);
	$("#pushTable2").find(".sizeAll").attr("colspan",size1+size2+3);
	
	var headHml = "";
	for(var x in groups1){
		headHml += "<th>"+groups1[x]+"</th>";
	}
	headHml += "<th>小计</th>";
	for(var i in groups2){
		headHml += "<th>"+groups2[i]+"</th>";
	}
	headHml += "<th>小计</th>";
	$("#pushTable2").find(".groupsType").html(headHml);
	
	var gridData = data['list'];
	
	var bodyHtml = "";
	var tbLength = 0;
	
	var lData = gridData[0];
	for(var t in lData){
		tbLength += 1;
	}
	
	var pName = "";
	for(var i in gridData){
		var trData = gridData[i];
		bodyHtml += "<tr>";
		
		for(var j=0;j<tbLength/2;j++){
			if(type=='0'){
				pName = "data"+j;
			}else{
				pName = "data_"+j;
			}
			
			bodyHtml += "<td>"+trData[pName]+"</td>";
		}
		bodyHtml += "</tr>";
	}
	$("#pushTable2").find("tbody").html(bodyHtml);
	
	$("#pushTable2").find("tbody tr").each(function(){
		$(this).find("td").each(function(i){
			if(i == 0){
				$(this).css({"background":"#b5b0a8","color":"#FFF","font-weight":"bold"});
			}
		});
		
	});
	
	$("#pushTable2").find("tbody td").each(function(){
		var txt = $(this).text();
		if(txt.indexOf("%")>-1){
			$(this).css({"background":"#e6f2fd"});
		}
	});
	
	$("#pushTable2").find("tbody tr:last-child").find("td").each(function(){
			$(this).css({"background":"#cdcbcb"});
	});
	
}

function renderPushTable3(data,type){
	$("#pushTable3").find("tbody").empty();
	var ldata = data[0];
	var map = null;
	for(var i in data){
		var html = "<tr>";
		map = data[i];
		for(var x=0;x<=15;x++){
			if(type == '0'){
				html += "<td>"+map["data"+x]+"</td>";
			}else{
				html += "<td>"+map["data_"+x]+"</td>";
			}
		}
		html += "</tr>";
		$("#pushTable3").find("tbody").append(html);
	}
	$("#pushTable3").find("tbody td").each(function(){
		var txt = $(this).text();
		if(txt.indexOf("%")>-1){
			$(this).css({"background":"#e6f2fd"});
		}
	});
}

function returnColor(n){
	if(n>0 && n<20){
		return "#adf9b7";
	}else if(n>=20 && n<40){
		return "#6cf096";
	}else if(n>=40 && n<60){
		return "#8be486";
	}else if(n>=60 && n<80){
		return "#8dd46f";
	}else if(n>=80 && n<100){
		return "#7bcf77";
	}else if(n == 0){
		return "#FFF";
	}
}

function renderTotalGrid(dom,data){
	$(dom).find("tbody").empty();
	if(data.length == 0)return;
	var html = "";
	for(var i in data){
		html += "<tr>";
		var bean = data[i];
		html += "<td>"+bean['date']+"</td>";
		html += "<td>"+bean['count1']+"</td>";
		html += "<td>"+bean['count2']+"</td>";
		html += "<td>"+bean['count3']+"</td>";
		html += "<td>"+bean['count4']+"</td>";
		html += "</tr>";
	}
	$(dom).find("tbody").html(html);
}


function renderGridData(dom,data,type){
	$(dom).find("tbody").empty();
	if(data.length == 0)return;
	var html = "";
	if(type == 1){//加载当天
		for(var i in data){
			html += "<tr>";
			var bean = data[i];
			html += "<td>"+bean['groupType']+"</td>";
			html += "<td>"+bean['create']+"</td>";
			html += "<td>"+bean['upload']+"</td>";
			html += "<td>"+bean['comment']+"</td>";
			html += "<td>"+bean['like']+"</td>";
			html += "<td>"+bean['invite']+"</td>";
			html += "<td>"+bean['total']+"</td>";
			html += "</tr>";
		}
	}else{//加载其他
		for(var i in data){
			html += "<tr>";
			var bean = data[i];
			html += "<td>"+bean['groupType']+"</td>";
			html += "<td>"+bean['upload']+"</td>";
			html += "<td>"+bean['comment']+"</td>";
			html += "<td>"+bean['like']+"</td>";
			html += "<td>"+bean['invite']+"</td>";
			html += "<td>"+bean['total']+"</td>";
			html += "<td>"+bean['rate']+"</td>";
			html += "</tr>";
		}
	}
	$(dom).find("tbody").html(html);
}