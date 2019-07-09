<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<html>
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>游标尺DEMO</title>
	<link href="${ctx}/assets/css/global.css" rel="stylesheet" />
</head>
<body>
	<div class="page-content">
		<div class="container">
			<div id="cursor" style="margin-top: 100px;position: absolute;border-top: 3px solid #E7EAEC;"></div>
		</div>
	</div>
	<%--<div id="new1" style="width: 369px;height:500px;margin: 50px;border:1px solid #000;">11111111</div>--%>
	<script js-main="demo/vernierCursorDemo" src="${ctx}/assets/js/require.js"></script>
</body>
</html>