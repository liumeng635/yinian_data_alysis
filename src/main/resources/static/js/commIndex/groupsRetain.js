$(function(){
	initTimeSelect();
	renderGroupsStsDataAll();
	doSearch();
})

function doSearch(){
	$("#searchIndex").click(function(){
		var dtime = $("#timeSelect").val();
		renderGroupsStsData(dtime)
	});
	
}

function initTimeSelect(){
	var time = new Date().getTime()-24*60*60*1000*8;
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
function renderGroupsStsData(time){
	$.ajax({
        type : "POST",  //请求方式
        url : "/renderGroupsStsData",  //请求路径
        async:true,
        data:{
        	time:time
        },
        success : function(data) {
        	//去留存数据
        	var ridData = data.data.ridData;
        	var c1 = renderGridData("#rid1",ridData.data1,1);
        	var c2 = renderGridData("#rid2",ridData.data2,0);
        	var c3 = renderGridData("#rid3",ridData.data3,0);
        	var c4 = renderGridData("#rid4",ridData.data4,0);
        	var t = 4 - c1 - c2 - c3 - c4;
        	var percent = (100/t).toFixed(2)-0.2;
        	//非去留存数据
        	var notRidData = data.data.notRidData;
        	renderGridData("#notRid1",notRidData.data1,1);
        	renderGridData("#notRid2",notRidData.data2,0);
        	renderGridData("#notRid3",notRidData.data3,0);
        	renderGridData("#notRid4",notRidData.data4,0);
        	$(".gridTb").css({"width":percent+"%"});
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
}

function renderGroupsStsDataAll(){
	$.ajax({
        type : "POST",  //请求方式
        url : "/renderGroupsStsDataAll",  //请求路径
        async:true,
        success : function(data) {
        	//去新增总体
        	var ridTotal = data.data.ridTotal;
        	renderTotalGrid("#ridTotal",ridTotal);
        	
        	//非去新增整体
        	var notRidTotal = data.data.notRidTotal;
        	renderTotalGrid("#notRidTotal",notRidTotal);
        	
        	
        	$("#ridTotal tbody tr,#notRidTotal tbody tr").each(function(){
        		$(this).find("td").each(function(i){
        			if(i != 0 && i != 1){
        				var txt = $(this).text();
        				txt = txt.replace("%","");
        				txt = eval(txt);
        				if(txt == 0){
        					$(this).html("");
        				}
        				var color = returnColor(txt);
        				$(this).css({"background":color});
        			}
        		});
        	});
        	
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
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
		var date = bean['date']+" 00:00:00";
		var diff = new Date(new Date().format("yyyy-MM-dd")+" 00:00:00").getTime() - new Date(date).getTime();
		var d = 8*24*60*60*1000;
		var d1 = 4*24*60*60*1000;
		
		
		
		if(diff < d && diff >= d1){
			html += "<td>"+bean['date']+"</td>";
			html += "<td>"+bean['count1']+"</td>";
			html += "<td>"+bean['count2']+"</td>";
			html += "<td></td>";
			html += "<td></td>";
			html += "</tr>";
		}else if(diff < d1){
			html += "<td>"+bean['date']+"</td>";
			html += "<td>"+bean['count1']+"</td>";
			html += "<td></td>";
			html += "<td></td>";
			html += "<td></td>";
			html += "</tr>";
		}else{
			html += "<td>"+bean['date']+"</td>";
			html += "<td>"+bean['count1']+"</td>";
			html += "<td>"+bean['count2']+"</td>";
			html += "<td>"+bean['count3']+"</td>";
			html += "<td>"+bean['count4']+"</td>";
			html += "</tr>";
		}
		
	}
	$(dom).find("tbody").html(html);
}


function renderGridData(dom,data,type){
	$(dom).find("tbody").empty();
	if(data.length == 0){return};
	if(allZero(data)){
		$(dom).hide();return 1;
	}else{
		$(dom).show();
	}
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
	return 0;
}

function allZero(data){
	var rs = true;
	for(var i in data){
		var bean = data[i];
		if(bean['create'] !=0 || bean['upload'] !=0 || bean['comment'] !=0 || bean['like'] !=0 || bean['invite'] !=0 || bean['total'] !=0 ){
			rs = false;
			break;
		}
	}
	return rs;
}