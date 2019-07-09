<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<!-- jsp文件头和头部 -->
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>中人网才报平台</title>
	<style>
		.tooltip .tooltip-inner{
			text-align: left;
		}
		.tooltip .tooltip-inner .tooltip-ul{
			list-style-type: decimal;
		}
	</style>
</head>
<body class="login-layout">
<div class="main-container">
	<div class="main-content">
		<div class="row">
			<div class="col-sm-10 col-sm-offset-1">
				<div class="login-container">
					<div class="center">
						<h1>
							<i class="icon-leaf green"></i>
							<span class="red">中人网</span>
							<span class="white">才报平台</span>
						</h1>
					</div>

					<div class="space-6"></div>

					<div class="position-relative">
						<div id="signup-box" class="signup-box visible widget-box no-border">
							<div class="widget-body">
								<div class="widget-main">
									<h4 class="header green lighter bigger">
										<i class="icon-group blue"></i>修改新密码
									</h4>

									<div class="space-6"></div>
									<p>您当前的登陆密码过于简单，请输入您的新密码&nbsp;: </p>
									<form name="updateForm" action="_this" method="post">
										<fieldset>
											<label class="block clearfix">
												<span class="block input-icon input-icon-right" id="pd_block">
													<input type="password" id="pw" name="pw" class="form-control" placeholder="请输入密码" />
													<i class="icon-lock"></i>
												</span>
											</label>

											<label class="block clearfix">
												<span class="block input-icon input-icon-right">
													<input type="password" id="rpw" name="rpw" class="form-control" placeholder="请输入确认密码" />
													<i class="icon-retweet"></i>
												</span>
											</label>

											<div class="space-24"></div>

											<div class="clearfix">
												<button type="reset" class="width-30 pull-left btn btn-sm">
													<i class="icon-refresh"></i>
													重置
												</button>

												<button type="button" id="submitBtn" class="width-65 pull-right btn btn-sm btn-success">
													提交
													<i class="icon-arrow-right icon-on-right"></i>
												</button>
											</div>
										</fieldset>
									</form>
								</div>

								<div class="toolbar center">
									<a href="#"  class="back-to-login-link">&nbsp;</a>
								</div>
							</div><!-- /widget-body -->
						</div><!-- /signup-box -->
					</div><!-- /position-relative -->
				</div>
			</div><!-- /.col -->
		</div><!-- /.row -->
	</div>
</div><!-- /.main-container -->
<script type="text/javascript" js-main="biz/base/updatePasswd" src="${ctx}/assets/js/require.js"></script>
</body>
</html>
