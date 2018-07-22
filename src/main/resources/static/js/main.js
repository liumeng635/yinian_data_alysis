$(document).ready(function(){
//    nav-li hover e
    var num;
    $('.nav-main>li[id]').hover(function(){
       /*图标向上旋转*/
        $(this).children().removeClass().addClass('hover-up');
        /*下拉框出现*/
        var Obj = $(this).attr('id');
        num = Obj.substring(3, Obj.length);
        $('#box-'+num).slideDown(300);
    },function(){
        /*图标向下旋转*/
        $(this).children().removeClass().addClass('hover-down');
        /*下拉框消失*/
        $('#box-'+num).hide();
    });
//    hidden-box hover e
    $('.hidden-box').hover(function(){
        /*保持图标向上*/
        $('#li-'+num).children().removeClass().addClass('hover-up');
        $(this).show();
    },function(){
        $(this).slideUp(200);
        $('#li-'+num).children().removeClass().addClass('hover-down');
    });
    
    setMenuTabStyle();
	$("#mainFrame").attr("src","/actStageCom");
	$(".default").addClass("alinkHover");
	$(".alink").click(function(){
		$(this).addClass("alinkHover");
		var parent = $("#"+$(this).attr("for"));
		$(parent[0]).addClass("alinkHover");
		if(parent){
			$(".alink").not(this).not(parent).removeClass("alinkHover");
		}else{
			$(".alink").not(this).removeClass("alinkHover");
		}
		var url = $(this).attr("url");
		$("#mainFrame").attr("src",url);
	});
    
});


function setMenuTabStyle(){
	var size = $("#nav-main li").length;
	var baseWH = 156;
	var wh = 0;
	for(var i=0;i<size;i++){
		wh = baseWH*i;
		$("#box-"+(i+1)).css({"left":wh+"px"});
	}
}