<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="description" content="overview & stats" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
	<title>才报平台</title>
	<link rel="icon" href="${ctx}/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon" />
	<link href="${ctx}/assets/css/base/login.css" rel="stylesheet" type="text/css">
	<script type="text/javascript">
		if(window != top){
			top.location.href = location.href;
		}
	</script>
</head>
<body>
	<div class="header">
		<div class="wrapper">
			<div class="topBar">
				<a href="#" class="logo">才报平台</a>
			</div>
		</div>
	</div>
	<div class="banner">
		<div class="wrapper">
			<div class="slide"></div>
			<div class="loginPanel">
				<div class="loginTitle" id="loginTitle">帐号登录</div>
				<div class="loginTips" id="errTips" style="visibility: hidden;"></div>
				<form class="loginForm" action="_self" method="post">
					<div class="inputOuter">
						<div class="inputInner">
							<input type="text" id="u" name="u" value="${username}"
								placeholder="请输入用户名" tabindex="1">
						</div>
					</div>
					<div class="inputOuter">
						<div class="inputInner">
							<input type="password" id="p" name="p" value=""
								placeholder="请输入密码" tabindex="2">
						</div>
					</div>
					<button type="submit" id="loginBtn" class="btn">登录</button>
				</form>
			</div>
		</div>
	</div>
	<div class="foot">
		<p>Copyright ©2001-<script>document.write((new Date()).getFullYear());</script> ChinaHRD.Net <span>All Rights Reserved.</span></p>
		<p>中人网 版权所有</p>
	</div>
</body>
<script src="${ctx}/assets/js/require.js" js-main="biz/base/login" type="text/javascript" charset="utf-8"></script>
</html>