var dfType;
var dfBuryType;
var index;

$(function(){
	buryClick();//点击查询埋点
	queryBuryPoint(1,"通用");//默认记载通用埋点
	queryPort("通用");//默认加载通用渠道
	addBuryClick();//点击添加埋点
	
	
	$(".addPointCfg").click(function(){
		var forId = $(this).attr("for");
		var html = $("#"+forId).html();
		$(this).parent().find(".addul").append(html);
	});
	
	$(document).on("click",".deleteSef",function(){
		$(this).parent().remove();
	});
	
	savePointAdd();//新增埋点数据提交
	
	/**
	 * 行点击checkbox切换选择状态
	 */
	$(document).on("click",".table tbody tr",function(){
		var check = $(this).find("input[type=checkbox]").prop("checked");
		if(check){
			$(this).find("input[type=checkbox]").prop("checked",false);
		}else{
			$(this).find("input[type=checkbox]").prop("checked",true);
		}
	});
	
	/**
	 * 阻止行点击事件冒泡
	 */
	$(document).on("click",".table tbody tr input[type=checkbox]",function(e){
		e.stopPropagation();
	});
	
	//埋点删除
	$("#deleteBuryPoint").click(function(){
		var checks = $("#buryTable").find("tbody input[type=checkbox]:checked");
		if(checks.length == 0){
			layer.msg("请选择要删除的埋点！",{icon:2});
			return;
		}else{
			var ids = "";
			checks.each(function(){
				ids += $(this).attr("buryId")+",";
			});
			ids = ids.substring(0,ids.lastIndexOf(","));
			layer.confirm('确认删除埋点？', {
				  btn: ['是','否'] //按钮
				}, function(){
					$.ajax({
				        type : "POST",  //请求方式
				        url : "/deletePointData",  //请求路径
				        data:{
				        	ids:ids,
				        	type:dfType
				        },
				        async:true,
				        success : function(rs) {
				        	var code = rs.code;
				        	if(code == '200'){
				        		layer.msg("删除成功！",{icon:1});
				        		layer.close(index);
				        		queryBuryPoint(dfType,dfBuryType);
				        	}else{
				        		layer.msg("删除失败！",{icon:2});
				        		return;
				        	}
				        },
				        error : function(XMLHttpRequest, textStatus, errorThrown) {
				            layer.msg("访问服务器失败！",{icon:2});
				        }
				    });
				}, function(i){
				  layer.close(i);
			});
		}
	});

	
	//添加或删除渠道信息
	addOrDeletePort();
})

/**
 * 渠道新增或删除
 * @returns
 */
function addOrDeletePort(){
	//添加
	$("#addPort").click(function(){
		index = layer.open({
            type: 1,
            title:"新增渠道-"+dfBuryType,
            area: ['100%', '100%'],
            shift: 2,
            skin: 'layui-layer-molv',
            shadeClose: true,
            content: $("#addPortDiv")
        });
	});
	
	$(".portSave").click(function(){
		var saveData = [];
		$(this).parent().parent().find(".addul li").each(function(){
			var item = {};
			$(this).find("input").each(function(){
				var name = $(this).attr("name");
				var value = $(this).val();
				item[name] = value;
			});
			saveData.push(item);
		});
		
		var sendData = JSON.stringify(saveData);
		//向服务端提交数据		
		$.ajax({
	        type : "POST",  //请求方式
	        url : "/savePortData",  //请求路径
	        data:{
	        	buryType:dfBuryType,
	        	pointData:sendData
	        },
	        async:true,
	        success : function(rs) {
	        	var code = rs.code;
	        	if(code == '200'){
	        		layer.msg("新增成功！",{icon:1});
	        		layer.close(index);
	        		queryPort(dfBuryType);
	        	}else{
	        		layer.msg("新增失败！",{icon:2});
	        		return;
	        	}
	        },
	        error : function(XMLHttpRequest, textStatus, errorThrown) {
	            layer.msg("访问服务器失败！",{icon:2});
	        }
	    });
	});
	
	//删除
	$("#deletePort").click(function(){
		var checks = $("#portTable").find("tbody input[type=checkbox]:checked");
		if(checks.length == 0){
			layer.msg("请选择要删除的渠道！",{icon:2});
			return;
		}else{
			var ids = "";
			checks.each(function(){
				ids += $(this).val()+",";
			});
			ids = ids.substring(0,ids.lastIndexOf(","));
			layer.confirm('确认删除渠道？', {
				  btn: ['是','否'] //按钮
				}, function(){
					$.ajax({
				        type : "POST",  //请求方式
				        url : "/deletePortData",  //请求路径
				        data:{
				        	ids:ids
				        },
				        async:true,
				        success : function(rs) {
				        	var code = rs.code;
				        	if(code == '200'){
				        		layer.msg("删除成功！",{icon:1});
				        		layer.close(index);
				        		queryPort(dfBuryType);
				        	}else{
				        		layer.msg("删除失败！",{icon:2});
				        		return;
				        	}
				        },
				        error : function(XMLHttpRequest, textStatus, errorThrown) {
				            layer.msg("访问服务器失败！",{icon:2});
				        }
				    });
				}, function(i){
				  layer.close(i);
			});
		}
	});
}


/**
 * 添加提交新增的埋点
 * @returns
 */
function savePointAdd(){
	$(".dataSave").click(function(){
		var saveData = [];
		$(this).parent().parent().find(".addul li").each(function(){
			var item = {};
			$(this).find("input").each(function(){
				var name = $(this).attr("name");
				var value = $(this).val();
				item[name] = value;
			});
			saveData.push(item);
		});
		
		var sendData = JSON.stringify(saveData);
		//向服务端提交数据		
		$.ajax({
	        type : "POST",  //请求方式
	        url : "/savePointData",  //请求路径
	        data:{
	        	type:dfType,
	        	pointData:sendData
	        },
	        async:true,
	        success : function(rs) {
	        	var code = rs.code;
	        	if(code == '200'){
	        		layer.msg("新增成功！",{icon:1});
	        		layer.close(index);
	        		queryBuryPoint(dfType,dfBuryType);
	        	}else{
	        		layer.msg("新增失败！",{icon:2});
	        		return;
	        	}
	        },
	        error : function(XMLHttpRequest, textStatus, errorThrown) {
	            layer.msg("访问服务器失败！",{icon:2});
	        }
	    });
		
		
	});
}

/**
 * 埋点新增点击
 * @returns
 */
function addBuryClick(){
	$("#addBuryPoint").click(function(){
		if(dfType == '1'){//通用/主题相册埋点
			index = layer.open({
	            type: 1,
	            title:"新增埋点-"+dfBuryType,
	            area: ['100%', '100%'],
	            shift: 2,
	            skin: 'layui-layer-molv',
	            shadeClose: true,
	            content: $("#pointDiv")
	        });
		}else if(dfType == '2'){//促活
			index = layer.open({
	            type: 1,
	            title:"新增埋点-"+dfBuryType,
	            area: ['100%', '100%'],
	            shift: 2,
	            skin: 'layui-layer-molv',
	            shadeClose: true,
	            content: $("#pointPushDiv")
	        });
		}else if(dfType == '3'){//底图调取
			index = layer.open({
	            type: 1,
	            title:"新增埋点-"+dfBuryType,
	            area: ['100%', '100%'],
	            shift: 2,
	            skin: 'layui-layer-molv',
	            shadeClose: true,
	            content: $("#pointDrawDiv")
	        });
		}else if(dfType == '4'){//登录信息统计
			index = layer.open({
	            type: 1,
	            title:"新增埋点-"+dfBuryType,
	            area: ['100%', '100%'],
	            shift: 2,
	            skin: 'layui-layer-molv',
	            shadeClose: true,
	            content: $("#pointLoginDiv")
	        });
		}
	});
}

/**
 * 埋点查询点击
 * @returns
 */
function buryClick(){
	$(".bury").click(function(){
		$(this).addClass("pactive");
		$(".bury").not(this).removeClass("pactive");
		var type = $(this).attr("dtype");
		var buryType = $(this).attr("buryType");
		queryBuryPoint(type,buryType);//埋点查询
		//渠道查询
		queryPort(buryType);
		
	});
}

/**
 * 根据不同类型的埋点查询埋点数据
 * @param type
 * @param buryType
 * @returns
 */
function queryBuryPoint(type,buryType){
	dfType = type;
	dfBuryType = buryType;
	//首先加载阶段
	$("#stageSelect").text("");
	$("#stageSelect").parent().find("ul").empty();
	$.ajax({
        type : "POST",  //请求方式
        url : "/stageList",  //请求路径
        data:{
        	type:type,
        	buryType:buryType
        },
        async:true,
        success : function(rs) {
        	var data = rs.data;
        	var stage = data[0]['stage'];
        	$("#stageSelect").text(stage);
        	for(var x in data){
        		$("#stageSelect").parent().find("ul").append("<li onclick='setVal(this,\""+data[x]['stage']+"\")'><a href='javascript:void(0);'>"+data[x]['stage']+"</a></li>");
        	}
        	queryBuryRcd(type,buryType,stage);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
	
}

/**
 * 埋点查询
 * @param type
 * @param buryType
 * @param stage
 * @returns
 */
function queryBuryRcd(type,buryType,stage){
	var sendData = {};
	if(stage){
		sendData = {
        	type:type,
        	buryType:buryType,
        	stage:stage
        }
	}else{
		sendData = {
	        	type:type,
	        	buryType:buryType
	        }
	}
	
	$.ajax({
        type : "POST",  //请求方式
        url : "/buryPointData",  //请求路径
        data:sendData,
        async:true,
        success : function(rs) {  //异步请求成功执行的回调函数
        	var data = rs.data;
        	var colums = [];
        	var columNames = [];
        	
        	if(type == '1'){
        		colums = ['buryTable','buryTheme','buryPage','buryOper','buryRemark','joinColumn','status','bstage'];
        		columNames = [{"name":'埋点表',"width":"13"},{"name":'埋点主题',"width":"10"},{"name":'埋点页面',"width":"10"},{"name":'埋点操作',"width":"15"},{"name":'操作备注',"width":"15"},{"name":'伴随字段',"width":"27"},{"name":'状态',"width":"5"},{"name":'阶段',"width":"5"}];
        	}else if(type == '2'){
        		colums = ['buryPage','buryColumn','buryRemark','buryDesc','status','bstage'];
        		columNames = [{"name":'所处页面'},{"name":'数据表字段'},{"name":'数据表字段备注'},{"name":'字段值说明'},{"name":'状态'},{"name":'阶段'}];
        	}else if(type == '3'){
        		colums = ['buryOper','buryRemark','joinColumn','status','bstage'];
        		columNames = [{'name':'埋点操作'},{'name':'操作备注'},{'name':'伴随字段'},{'name':'状态'},{'name':'阶段'}];
        	}else if(type == '4'){
        		colums = ['buryColumn','buryRemark','status','bstage'];
        		columNames = [{'name':'数据表字段'},{'name':'数据表字段备注'},{'name':'状态'},{'name':'阶段'}];
        	}
        	renderGridData(colums,columNames,data);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
}


/**
 * 通用加载表格
 * @param colums
 * @param columNames
 * @param data
 * @param tabid
 * @returns
 */
function renderGridData(colums,columNames,data){
	$("#buryTable").find("thead").empty();
	$("#buryTable").find("tbody").empty();
	var head = "<tr>";
	head += "<th width='7px'><input type='checkbox' onClick='checkAll(this)'/></th>";
	for(var x in columNames){
		var c = columNames[x];
		head += "<th width='"+c.width+"%'>"+c.name+"</th>";
	}
	head += "</tr>";
	$("#buryTable").find("thead").html(head);
	
	if(data.length == 0){
		$("#buryTable tbody").html("<tr><td style='text-align: center;color:red;'>暂无数据</td></tr>");
		return;
	}
	
	var body = "";
	for(var x in data){
		body = "<tr>";
		body += "<td width='7px'><input type='checkbox' buryId='"+data[x]['buryId']+"'/></td>";
		for(var i in colums){
			var width = columNames[i]["width"];
			var name = colums[i];
			if(name == 'status'){
				var sts = data[x][name];
				if(sts == '0'){
					sts = "废弃";
				}else if(sts == '1'){
					sts = "使用中";
				}else if(sts == '2'){
					sts = "研发中";
				}
				body += "<td width='"+width+"%'>"+sts+"</td>";
			}else{
				body += "<td width='"+width+"%'>"+data[x][name]+"</td>";
			}
		}
		body += "</tr>";
		$("#buryTable").find("tbody").append(body);
		$("#buryTable").find("tbody td").addClass("esis");
		$("#buryTable").find("td,th").css({"text-align":"center"});
		$("#buryTable").find("td,th").each(function(){
			$(this).attr("title",$(this).text())
		});
	}
}

function checkAll(dom){
	var check = $(dom).prop("checked");
	if(check == false){
		$(dom).parents('.table').find("input[type=checkbox]").prop("checked",false);
	}else{
		$(dom).parents('.table').find("input[type=checkbox]").prop("checked",true);
	}
}

/**
 * change埋点状态
 * @param sts
 * @param dom
 * @returns
 */
function changeStatus(sts,oper,dom){
	var checks = $("#buryTable").find("tbody input[type=checkbox]:checked");
	if(checks.length == 0){
		layer.msg("请选择要修改为"+oper+"的埋点！",{icon:2});
		return;
	}else{
		var ids = "";
		checks.each(function(){
			ids += $(this).attr("buryId")+",";
		});
		ids = ids.substring(0,ids.lastIndexOf(","));
		layer.confirm('确认修改埋点状态？', {
			  btn: ['是','否'] //按钮
			}, function(){
				$.ajax({
			        type : "POST",  //请求方式
			        url : "/changePointDataSts",  //请求路径
			        data:{
			        	ids:ids,
			        	type:dfType,
			        	status:sts
			        },
			        async:true,
			        success : function(rs) {
			        	var code = rs.code;
			        	if(code == '200'){
			        		layer.msg("变更成功！",{icon:1});
			        		layer.close(index);
			        		queryBuryPoint(dfType,dfBuryType);
			        	}else{
			        		layer.msg("变更失败！",{icon:2});
			        		return;
			        	}
			        },
			        error : function(XMLHttpRequest, textStatus, errorThrown) {
			            layer.msg("访问服务器失败！",{icon:2});
			        }
			    });
			}, function(i){
			  layer.close(i);
		});
	}
}

function setVal(dom,val){
	$(dom).parent().parent().find("button").text(val);
	if(val.indexOf("全部")>-1){
		queryBuryRcd(dfType,dfBuryType);
		queryPort(dfBuryType);
	}else{
		queryBuryRcd(dfType,dfBuryType,val);
		queryPort(dfBuryType);
	}
}

/**
 * 渠道查询
 * @returns
 */
function queryPort(buryType){
	$.ajax({
        type : "POST",  //请求方式
        url : "/queryPort",  //请求路径
        data:{
        	buryType:buryType
        },
        async:true,
        success : function(rs) {
        	$("#portTable tbody").empty();
        	var data = rs.data;
        	if(data.length == 0){
        		$("#portTable tbody").html("<tr><td style='text-align: center;color:red;'>暂无数据</td></tr>");
        		return;
        	}
        	for(var x in data){
        		var bodyHtml = "";
        		bodyHtml+="<tr>";
        		var item = data[x];
        		bodyHtml += "<td><input type='checkbox' value='"+item['pid']+"'/></td>";
        		bodyHtml += "<td>"+item['port']+"</td>";
        		bodyHtml += "<td>"+item['portType']+"</td>";
        		bodyHtml += "<td>"+item['remark']+"</td>";
        		bodyHtml+="</tr>";
        		$("#portTable tbody").append(bodyHtml);
        	}
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
}
