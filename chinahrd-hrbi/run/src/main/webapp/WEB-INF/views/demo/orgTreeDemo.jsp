<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<html>
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<style>
	.grid {
		margin-left: 30px;
	}
	</style>
</head>
<body>
<div class="page-content">
	<div class="grid">
		<div class="row">
			<input type="button" id="testVal" value="单选的值" class="u-btn">
			<input type="button" id="setVal" value="设置值" class="u-btn">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
		<div class="row">
			<input type="button" id="testVal2" value="同一级别多选" class="u-btn">
			<ul id="multiTree"></ul>
		</div>
	</div>
</div>
<script src="${ctx}/assets/js/demo/orgTreeDemo.js"></script>
</body>
</html>