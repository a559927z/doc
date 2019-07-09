<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.chinahrd.net/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
	<title>综合演示用例</title>
	<script src="${ctx}/assets/js/lib/jquery/jquery-1.7.2.js"></script>
	<script src="${ctx}/assets/js/lib/jquery-validation/1.11.1/jquery.validate.min.js"></script>
	<script src="${ctx}/assets/js/lib/jquery-validation/1.11.1/messages_bs_zh.js" ></script>
	<link rel="stylesheet" href="${ctx}/assets/js/lib/jquery-validation/1.11.1/validate.css" />
	<script>
		$(document).ready(function() {
			$("#account-tab").addClass("active");
			
			//为inputForm注册validate函数
			$("#inputForm").validate({
				rules: {
					loginName: {
						required: true,
						remote: "${ctx}/test/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')
					},
					name: {
						required: true
					},
					plainPassword: {
						required: false,
						minlength: 5
					},
					email: {
						required: true,
						email: true
					},
					roleList:"required"
				},
				messages: {
					loginName: {
						required: "请输入用户登录名",
						remote: "用户登录名已存在"
					},
				    name: "请输入用户名",
				    plainPassword: {
						minlength: "密码不能小于5个字符"
					},
					email: {
						required: "请输入邮件地址",
						email: "请输入正确的邮件地址"
					}
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					if ( element.is(":checkbox") )
						error.appendTo(element.parent().next());
					else
						error.insertAfter(element);
				}
			});
		});
	</script>
</head>

<body>
	<h1>综合演示用例</h1>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/test/user/update" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${user.id}"/>
		<fieldset>
			<legend><small>管理用户</small></legend>
			<div id="messageBox" class="alert alert-error input-large controls" style="display:none">输入有误，请先更正。</div>
			<div class="control-group">
				<label for="loginName" class="control-label">登录名:</label>
				<div class="controls">
					<input type="text" id="loginName" name="loginName" value="${user.loginName}" class="input-large"/>
				</div>
			</div>
			<div class="control-group">
				<label for="name" class="control-label">用户名:</label>
				<div class="controls">
					<input type="text" id="name" name="name"  value="${user.name}" class="input-large"/>
				</div>
			</div>
			<div class="control-group">
				<label for="plainPassword" class="control-label">密码:</label>
				<div class="controls">
					<input type="password" id="plainPassword" name="plainPassword" class="input-large" placeholder="...未变请留空"/>
				</div>
			</div>
			<div class="control-group">
				<label for="email" class="control-label">邮件:</label>
				<div class="controls">
					<input type="text" id="email" name="email"  value="${user.email}" class="input-large"/>
				</div>
			</div>
			<div class="control-group">
				<label for="groupList" class="control-label">角色:</label>
				<div class="controls">
					<form:bscheckboxes path="roleList" items="${allRoles}" itemLabel="name" itemValue="id" />
				</div>
			</div>	
			<div class="control-group">
				<label for="status" class="control-label">状态:</label>
				<div class="controls">
					<form:bsradiobuttons path="status" items="${allStatus}" labelCssClass="inline"/>
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
				<p class="help-block">(提示信息)</p>			
			</div>
		</fieldset>
	</form:form>
</body>
</html>
