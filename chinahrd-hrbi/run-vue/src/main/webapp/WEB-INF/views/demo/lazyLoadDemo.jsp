<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>图片延时预加载效果</title>
<style>
	img{
		display : block;
		margin : 30px;
	}
</style>
</head>
<body>
	<!-- src为图片加载完成前显示的图片，data-scr为需要显示的图片 -->
	<img src="${ctx}/assets/img/base/loader.gif" data-src="${ctx}/assets/img/base/arrow-down.jpg">
	
<script>
require(['jquery','unveil'],function(){
		var imgDom = $('img').eq(0);
		for(var i=0;i<100;i++){
			$('body').append(imgDom.clone());
		}
		$('img').unveil();
	});
</script>	
</body>
</html>
