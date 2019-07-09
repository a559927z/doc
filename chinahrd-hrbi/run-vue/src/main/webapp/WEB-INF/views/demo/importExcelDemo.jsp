<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE HTML> 
<html> 
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>解析Excel组件</title>
	<link href="${ctx}/assets/css/global.css" rel="stylesheet" />
</head>
<body>
<div class="page-content">
	<div class="container">
		<div class="row">&nbsp;</div>
		<form action="${ctx}/demo/doImportExcel" class="form-horizontal"  role="form" enctype="multipart/form-data" method="post">
			<div class="form-group">
			<label>文件输入：</label>
			<input type="file" name="inputfile">
			</div>
			<button type="submit" class="btn btn-success">提交</button>
		</form>
	</div>
</div>
</body>
</html>