<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<html>
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>时间轴 DEMO</title>
	<link href="${ctx}/assets/css/global.css" rel="stylesheet" />
</head>
<body>
	<div class="page-content">
	<div id="new" style="width: 369px;margin: 50px 450px;position: absolute;"></div>
	</div>
	<%--<div id="new1" style="width: 369px;height:500px;margin: 50px;border:1px solid #000;">11111111</div>--%>
	<script src="${ctx}/assets/js/demo/timeLineDemo.js"></script>
</body>
</html>