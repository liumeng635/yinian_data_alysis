$(function(){
	login();
	$(document).keypress(function(e) {
	       var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
	        if (eCode == 13){
	        	toLogin();
	        }
	});
	
	
	$("#ceshiBtn").click(function(){
		//登录
		$.ajax({
	        type : "POST",  //请求方式
	        url : "http://127.0.0.1:5445/producer/produceBuryData",  //请求路径
	        data:{
	        	processData:"{'userId':'947399','fromUserId':'0','createUserId':'uid_1085479411667091','groupId':'0','port':'sharePage','operation':'pv','remark':'pages/viewscoll/viewscoll?scene=port=spaceQR%26groupid=7914678','userRegisterTime':'2017-10-24 12:48:28','userLastLoginTime':'2018-06-28 18:55:15'}",
	        },
	        async:true,
	        success : function(rs) {  //异步请求成功执行的回调函数
	        	layer.msg(rs.msg,{icon:2});
	        },
	        error : function(XMLHttpRequest, textStatus, errorThrown) {
	            layer.msg("访问服务器失败！",{icon:2});
	        }
	    });
	});
	
})

function login(){
	$("#submitLogin").click(function(){
		toLogin();
	});
}

function toLogin(){
	var uName = $("#loginForm .uName").val();
	var uPwd = $("#loginForm .uPwd").val();
	if($.trim(uName) == ""){
		layer.msg("请输入用户名！",{icon:2});
		$("#loginForm .uName").focus();
		return;
	}
	if($.trim(uPwd) == ""){
		layer.msg("请输入密码！",{icon:2});
		$("#loginForm .uPwd").focus();
		return;
	}
	
	//登录
	$.ajax({
        type : "POST",  //请求方式
        url : "/loginUser",  //请求路径
        data:{
        	uName:uName,
        	uPwd:uPwd
        },
        async:true,
        success : function(rs) {  //异步请求成功执行的回调函数
        	var data = rs.data;
        	if(data){
        		window.location.href = "/main";
        	}else{
        		layer.msg(rs.msg,{icon:2});
        	}
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("访问服务器失败！",{icon:2});
        }
    });

}