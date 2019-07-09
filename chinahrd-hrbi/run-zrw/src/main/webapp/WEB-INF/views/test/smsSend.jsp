<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
	<title>手机短信发送调用演示用例</title>
	<script src="${ctx}/assets/js/lib/jquery/jquery-1.7.2.js"></script>
	<script src="${ctx}/assets/js/lib/jquery-validation/1.11.1/jquery.validate.min.js"></script>
	<script src="${ctx}/assets/js/lib/jquery-validation/1.11.1/messages_bs_zh.js"></script>
	<link href="${ctx}/assets/js/lib/jquery-validation/1.11.1/validate.css" rel="stylesheet">
</head>

<body>
	<h1>发送手机短信-测试</h1>
	<div>
		<div>
			<b>短信网关提供商：</b>${providerName}
		</div>
		<div>
			<b>剩余短信条数：</b>${remain}
		</div>
	</div>
	<br />

	<form id="smsSendForm" modelAttribute="user" action="${ctx}/test/sms/send" method="post" class="form-horizontal">
		<div class="control-group">
			<label for="mobiles" class="control-label">手机号码：</label>
			<div class="controls">
				<input type="text" id="mobiles" name="mobiles" class="input-large" value="13316861772" />
			</div>
		</div>
		<div class="control-group">
			<label for="smsText" class="control-label">消息内容:</label>
			<div class="controls">
				<textarea id="smsText" name="smsText" value="" class="input-large" rows=6 cols=25>测试C123短信网关</textarea>
			</div>
		</div>
		<div class="form-actions">
			<input id="submit_btn" class="btn btn-primary" type="submit"
				value="提交" />
		</div>
	</form>

	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>
			${message}
		</div>
	</c:if>

	<br />
	<h3>发送状态查询</h3>

</body>
</html>
