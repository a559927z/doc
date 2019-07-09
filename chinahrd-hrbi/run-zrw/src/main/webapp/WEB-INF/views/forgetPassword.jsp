<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="description" content="overview & stats" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<%@include file="/WEB-INF/views/include/top.jsp"%>
<title>才报平台</title>
<link rel="icon" href="${ctx}/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon" />
<link href="${ctx}/assets/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<style>
body {
	margin-left: auto;
	margin-right: auto;
	margin-top: 100PX;
	width: 20em;
}
</style>
	<!-- 	<div class="header"> -->
	<!-- 		<div class="wrapper"> -->
	<!-- 			<div class="topBar"> -->
	<!-- 				<a href="#" class="logo">才报平台</a> -->
	<!-- 			</div> -->
	<!-- 		</div> -->
	<!-- 	</div> -->

	<a href="${ctx }/login">返回登录</a>

	<div class="banner">
		<div class="input-group">
			<span class="input-group-addon"> <span
				class="glyphicon glyphicon-user"></span>
			</span> <input id="fp-userKey" type="text" class="form-control"
				placeholder="账号" aria-describedby="basic-addon1">
		</div>
		<br>
		<div class="input-group">
			<span class="input-group-addon"> <span
				class="glyphicon glyphicon-envelope"></span>
			</span> <input id="fp-email" type="text" class="form-control"
				placeholder="邮箱" aria-describedby="basic-addon1">
		</div>
		<br>
		<div class="input-group">
			<span class="input-group-addon"> <span
				class="glyphicon glyphicon-barcode"></span>
			</span> <input id="fp-verification-code" type="text" class="form-control"
				placeholder="邮箱提取验证码" aria-describedby="basic-addon1"> <span
				class=" btn btn-default input-group-addon" id="seadVerificationCode">激
				活</span>
		</div>
		<br>
		<button type="button" style="width: 280px;" class="btn btn-default">提
			交</button>

	</div>
	<!-- 	<div class="foot"> -->
	<!-- 		<h6> -->
	<!-- 			Copyright ©2001- -->
	<!-- 			<script> -->
	<!-- // 				document.write((new Date()).getFullYear()); -->
	<!-- 			</script> -->
	<!-- 			ChinaHRD.Net <span>All Rights Reserved.</span> -->
	<!-- 		</h6> -->
	<!-- 		<h6>中人网 版权所有</h6> -->
	<!-- 	</div> -->

</body>
<script charset="utf-8">
	require([ 'jquery', 'layer' ], function() {
		var win = (window != top) ? top : window;
		var webRoot = G_WEB_ROOT;
		var urls = {
			forgetPassword : webRoot + '/forgetPassword/opt.do',
			authVerifyCode : webRoot + '/forgetPassword/authVerifyCode.do',
		}
		layer.config({
			path : webRoot + '/assets/plugIn/layer-v3.0.3/layer/' //layer.js所在的目录，可以是绝对目录，也可以是相对目录
		});
		$("#seadVerificationCode").unbind().bind('click', function() {
			var userKey = $("#fp-userKey").val();
			var email = $("#fp-email").val();
			if (userKey.length < 1 || email.length < 1) {
				layer.msg('代码君：账号与密码不能为空。', {
					icon : 5
				});
			} else {
				$.post(urls.forgetPassword, {
					'userKey' : userKey,
					'email' : email
				}, function(rs) {
					if (rs.type) {
						layer.msg(rs.msg, {
							time : 0,
							btn : [ '关闭' ],
							icon : 6
						});
					} else {
						layer.msg(rs.msg, {
							icon : 5
						});
					}
					console.log(rs);
				});
			}
		});

		$('button').addClass('disabled'); // Disables visually
		$('button').prop('disabled', true); // Disables visually + functionally

		$("#fp-verification-code").bind("input propertychange", function() {
			var verifyCode = $("#fp-verification-code").val();
			if (verifyCode.length > 0) {
				$('button').removeClass('disabled');
				$('button').prop('disabled', false);
			} else {
				$('button').addClass('disabled');
				$('button').prop('disabled', true);
			}
		});

		$("button").bind('click', function() {
			var userKey = $("#fp-userKey").val();
			var verifyCode = $("#fp-verification-code").val();
			$.post(urls.authVerifyCode, {
				'userKey' : userKey,
				'verifyCode' : verifyCode
			}, function(rs) {
				if (rs.type) {
// 					var url = rs.t.split("!")[0];
// 					var verifyCode = rs.t.split("!")[1];
					win.location.href = rs.t;
					return;
				}
				if (!(rs.type)) {
					layer.msg(rs.msg, {
						icon : 5
					});
				}
				console.log(rs);
			});
		});
	});
</script>
</html>