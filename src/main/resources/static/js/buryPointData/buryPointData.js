var dfType;
var dfBuryType = "通用";
var index;
var dfIsNewUser = "总体";


$(function(){
	initTimeSelect();
	buryClick();//点击查询埋点
	queryPortData(dfIsNewUser,new Date().format("yyyy-MM-dd hh"),"通用",$("#stageSelect").text());
	
	/**
	 * 点击查询
	 */
	$("#searchIndex").click(function(){
		queryPortData(dfIsNewUser,$("#timeSelect").val(),dfBuryType,$("#stageSelect").text());
	});
	
	$(".navtab").click(function(){
		$(this).addClass("tabActive");
		$(".navtab").not(this).removeClass("tabActive");
		var isNewUser = $(this).attr("val");
		dfIsNewUser = isNewUser;
		queryPortData(dfIsNewUser,$("#timeSelect").val(),dfBuryType,$("#stageSelect").text());
	});
	
})
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
		dfType = type;
		dfBuryType = buryType;
		queryPortData(dfIsNewUser,$("#timeSelect").val(),buryType,$("#stageSelect").text());
	});
}

/**
 * 初始化日期控件
 * @returns
 */
function initTimeSelect(){
	laydate.render({
		  elem: '#timeSelect', //指定元素
		  format:"yyyy-MM-dd HH",
		  theme: 'molv',
		  type:"datetime",
		  value: new Date()
	});	
}

function queryPortData(type,date,themeType,sch){
	//统计
	var expJson = {};
	$(".val").each(function(){
		$(this).removeAttr("id");
		var uuid = guid();
		$(this).attr("id",uuid);
		expJson[uuid] = $(this).attr("val");
	});
	var exp = "";
	exp = JSON.stringify(expJson);
	$.ajax({
        type : "POST",  //请求方式
        url : "/queryStsData",  //请求路径
        data:{
        	exp:exp,
        	isnewuser:type,
        	period:date,
        	themeType:themeType,
        	groupName:sch
        },
        async:true,
        success : function(rs) {
        	var data = rs.data;
        	for(var x in data){
        		$("#"+x).html(data[x]);
        	}
        	
        	$(".stsTable").each(function(){
        		var tot = $(this).find(".total").text();
        		$(this).find(".userItem").each(function(){
        			var html = "";
        			var v = $(this).text();
        			if(tot == '0'){
        				html = v+"<font color='blue'>(0%)</font>";
        			}else{
            			var p = eval(v+"/"+tot);
            			var showP = (p*100).toFixed(2)+"%";
            			html = v+"<font color='blue'>("+showP+")</font>";
        			}
        			$(this).html(html);
        		});
        	});
        	
        	
        	$(".percent").each(function(){
        		var exp = $(this).attr("val");
        		var exps = exp.split("/");
        		var devide = $(this).parent().parent().find("."+exps[1]).text();
        		if(eval(devide) == 0){
        			$(this).text("0%");
        		}else{
        			var devider = $(this).parent().parent().find("."+exps[0]).text();
            		var v = eval(devider+"/"+devide);
            		var text = (v*100).toFixed(2)+"%";
            		$(this).text(text);
        		}
        	});
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
	
	//指标
	var expIndexJson = {};
	$(".indexV").each(function(){
		$(this).removeAttr("id");
		var uuid = guid();
		$(this).attr("id",uuid);
		expIndexJson[uuid] = $(this).attr("val");
	});
	var expIndex = "";
	expIndex = JSON.stringify(expIndexJson);
	$.ajax({
        type : "POST",  //请求方式
        url : "/queryIndexData",  //请求路径
        data:{
        	expIndex:expIndex,
        	isnewuser:"新用户",
        	period:date,
        	themeType:themeType,
        	groupName:sch
        },
        async:true,
        success : function(rs) {
        	var data = rs.data;
        	for(var x in data){
        		$("#"+x).html(data[x]);
        	}
        	caclIndex();
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });
}


function caclIndex(){
	$(".indexPercent").each(function(){
		$(this).empty();
		var exp = $(this).attr("val");
		var exps = exp.split("/");
		var v1 = $("."+exps[0]).text();
		var v2 = $("."+exps[1]).text();
		if(v2 == '0' || v2 == ''){
			$(this).text("【0%】");
			return;
		}
		var p = eval(v1+"/"+v2);
		var text = (p*100).toFixed(2)+"%";
		$(this).text("【"+text+"】");
	});
}

function querySelectData(groupName){
	$("#stageSelect").html(groupName);
	queryPortData(dfIsNewUser,$("#timeSelect").val(),dfBuryType,groupName);
}
