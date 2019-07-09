<%
	String pathh = request.getContextPath();
	String basePathh = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+pathh+"/";
%>
<link rel="stylesheet" href="${ctx}/assets/css/zTreeStyle/zTreeStyle.css"  />
<style type="text/css">
.mark-talent-type{
	position : absolute;
	right : 5px;
	top : 4px;
	background-color : #DB3700;
	color : #fff;
	font-weight : bold;
	width : 14px;
	height : 14px;
	line-height : 14px;
	font-size : 12px;
}


.u-dropdown-input{
	float: left;
	margin-top: 6px;
	margin-left: 40px;
	border: 0px !important;
	color: #fff;
}

.ct-logo{
    float: left;
    margin-top: 7px;
    margin-left: -2px;
    cursor: pointer;
}
.ct-logo-text{
    float: left;
    color: #ffffff;
    font-size: 20px;
    margin-top: 5px;
    margin-left: 8px;
}
/* #tidings{ */
/* width: 400px; */
/* } */
</style>
<input type="hidden" id="topOrganId" value="${topOrganId}">
<input type="hidden" id="topOrganName" value="${topOrganName}">
<div class="navbar navbar-default" id="navbar">
	<script type="text/javascript">
		try{ace.settings.check('navbar' , 'fixed')}catch(e){}
	</script>

	<div class="navbar-container" id="navbar-container">
		<div class="navbar-header pull-left">
			<!--<a href="#" class="navbar-brand">-->
				<!--<small>-->
					<!--<i class="icon-leaf"></i>-->
					<!--<span id="test">中人网才报平台</span>-->
				<!--</small>-->
			<!--</a>-->
            <img class="ct-logo" width="28" height="28" src="${ctx}/assets/img/base/logo.png">
            <span class="ct-logo-text">才报平台演示版</span>
            <!-- /.brand -->
		</div><!-- /.navbar-header -->

        <!--组织架构 begin-->
        <!--<div class="col-xs-8">-->
            <ul class="ztree" id="tree"></ul>
        <!--</div>-->
        <!--组织架构 begin-->

		<div class="navbar-header pull-right" role="navigation">
			<ul class="nav ace-nav">
<!-- 				<li class="grey"> -->
<!-- 					<a data-toggle="dropdown" class="dropdown-toggle" href="javascript:alert('预留功能,待开发');"> -->
<!-- 						<i class="icon-tasks"></i> -->
<!-- 						<span class="badge badge-grey">4</span> -->
<!-- 					</a> -->

<!-- 					<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close"> -->
<!-- 						<li class="dropdown-header"> -->
<!-- 							<i class="icon-ok"></i> -->
<!-- 							还有4个任务需要完成 -->
<!-- 						</li> -->

<!-- 						<li> -->
<!-- 							<a href="javascript:alert('预留功能,待开发');"> -->
<!-- 								<div class="clearfix"> -->
<!-- 									<span class="pull-left">软件更新</span> -->
<!-- 									<span class="pull-right">65%</span> -->
<!-- 								</div> -->

<!-- 								<div class="progress progress-mini"> -->
<!-- 									<div style="width:65%" class="progress-bar"></div> -->
<!-- 								</div> -->
<!-- 							</a> -->
<!-- 						</li> -->

<!-- 						<li> -->
<!-- 							<a href="javascript:alert('预留功能,待开发');"> -->
<!-- 								<div class="clearfix"> -->
<!-- 									<span class="pull-left">硬件更新</span> -->
<!-- 									<span class="pull-right">35%</span> -->
<!-- 								</div> -->

<!-- 								<div class="progress progress-mini"> -->
<!-- 									<div style="width:35%" class="progress-bar progress-bar-danger"></div> -->
<!-- 								</div> -->
<!-- 							</a> -->
<!-- 						</li> -->

<!-- 						<li> -->
<!-- 							<a href="javascript:alert('预留功能,待开发');"> -->
<!-- 								<div class="clearfix"> -->
<!-- 									<span class="pull-left">单元测试</span> -->
<!-- 									<span class="pull-right">15%</span> -->
<!-- 								</div> -->

<!-- 								<div class="progress progress-mini "> -->
<!-- 									<div style="width:15%" class="progress-bar progress-bar-warning"></div> -->
<!-- 								</div> -->
<!-- 							</a> -->
<!-- 						</li> -->

<!-- 						<li> -->
<!-- 							<a href="javascript:alert('预留功能,待开发');"> -->
<!-- 								<div class="clearfix"> -->
<!-- 									<span class="pull-left">错误修复</span> -->
<!-- 									<span class="pull-right">90%</span> -->
<!-- 								</div> -->

<!-- 								<div class="progress progress-mini progress-striped active"> -->
<!-- 									<div style="width:90%" class="progress-bar progress-bar-success"></div> -->
<!-- 								</div> -->
<!-- 							</a> -->
<!-- 						</li> -->

<!-- 						<li> -->
<!-- 							<a href="javascript:alert('预留功能,待开发');"> -->
<!-- 								查看任务详情 -->
<!-- 								<i class="icon-arrow-right"></i> -->
<!-- 							</a> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 				</li> -->

<!-- 				<li class="purple"> -->
<!-- 					<a data-toggle="dropdown" class="dropdown-toggle" href="javascript:alert('预留功能,待开发');"> -->
<!-- 						<i class="icon-bell-alt icon-animated-bell"></i> -->
<!-- 						<span class="badge badge-important">8</span> -->
<!-- 					</a> -->

<!-- 					<ul class="pull-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close"> -->
<!-- 						<li class="dropdown-header"> -->
<!-- 							<i class="icon-warning-sign"></i> -->
<!-- 							8条通知 -->
<!-- 						</li> -->

<!-- 						<li> -->
<!-- 							<a href="javascript:alert('预留功能,待开发');"> -->
<!-- 								<div class="clearfix"> -->
<!-- 									<span class="pull-left"> -->
<!-- 										<i class="btn btn-xs no-hover btn-pink icon-comment"></i> -->
<!-- 										新闻评论 -->
<!-- 									</span> -->
<!-- 									<span class="pull-right badge badge-info">+12</span> -->
<!-- 								</div> -->
<!-- 							</a> -->
<!-- 						</li> -->

<!-- 						<li> -->
<!-- 							<a href="javascript:alert('预留功能,待开发');"> -->
<!-- 								<i class="btn btn-xs btn-primary icon-user"></i> -->
<!-- 								切换为编辑登录.. -->
<!-- 							</a> -->
<!-- 						</li> -->

<!-- 						<li> -->
<!-- 							<a href="javascript:alert('预留功能,待开发');"> -->
<!-- 								<div class="clearfix"> -->
<!-- 									<span class="pull-left"> -->
<!-- 										<i class="btn btn-xs no-hover btn-success icon-shopping-cart"></i> -->
<!-- 										新订单 -->
<!-- 									</span> -->
<!-- 									<span class="pull-right badge badge-success">+8</span> -->
<!-- 								</div> -->
<!-- 							</a> -->
<!-- 						</li> -->

<!-- 						<li> -->
<!-- 							<a href="javascript:alert('预留功能,待开发');"> -->
<!-- 								<div class="clearfix"> -->
<!-- 									<span class="pull-left"> -->
<!-- 										<i class="btn btn-xs no-hover btn-info icon-twitter"></i> -->
<!-- 										粉丝 -->
<!-- 									</span> -->
<!-- 									<span class="pull-right badge badge-info">+11</span> -->
<!-- 								</div> -->
<!-- 							</a> -->
<!-- 						</li> -->

<!-- 						<li> -->
<!-- 							<a href="javascript:alert('预留功能,待开发');"> -->
<!-- 								查看所有通知 -->
<!-- 								<i class="icon-arrow-right"></i> -->
<!-- 							</a> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 				</li> -->

				<li class="blueTitle" id="message">
<!-- 					<div ></div> -->
					
<!-- 					<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close"> -->
<!-- <!-- 						<li class="dropdown-header"> --> -->
<!-- 							<i class="icon-envelope-alt"></i> -->
<!-- 							5条消息 -->
<!-- 						</li> -->
<!-- 						<li><div id="tidings"></div></li> -->
<!-- 						<li> -->
<!-- 							<a href="#"> -->
<%-- 								<img src="${ctx}/assets/img/user.jpg" class="msg-photo" alt="Alex's Avatar" /> --%>
<!-- 								<span class="msg-body"> -->
<!-- 									<span class="msg-title"> -->
<!-- 										<span class="blue">Alex:</span> -->
<!-- 										不知道写啥 ... -->
<!-- 									</span> -->

<!-- 									<span class="msg-time"> -->
<!-- 										<i class="icon-time"></i> -->
<!-- 										<span>1分钟以前</span> -->
<!-- 									</span> -->
<!-- 								</span> -->
<!-- 							</a> -->
<!-- 						</li> -->

<!-- 						<li> -->
<!-- 							<a href="#"> -->
<%-- 								<img src="${ctx}/assets/img/user.jpg" class="msg-photo" alt="Susan's Avatar" /> --%>
<!-- 								<span class="msg-body"> -->
<!-- 									<span class="msg-title"> -->
<!-- 										<span class="blue">Susan:</span> -->
<!-- 										不知道翻译... -->
<!-- 									</span> -->

<!-- 									<span class="msg-time"> -->
<!-- 										<i class="icon-time"></i> -->
<!-- 										<span>20分钟以前</span> -->
<!-- 									</span> -->
<!-- 								</span> -->
<!-- 							</a> -->
								
<!-- 						</li> -->

<!-- 						<li> -->
<!-- 							<a href="#"> -->
<%-- 								<img src="${ctx}/assets/img/user.jpg" class="msg-photo" alt="Bob's Avatar" /> --%>
<!-- 								<span class="msg-body"> -->
<!-- 									<span class="msg-title"> -->
<!-- 										<span class="blue">Bob:</span> -->
<!-- 										到底是不是英文 ... -->
<!-- 									</span> -->

<!-- 									<span class="msg-time"> -->
<!-- 										<i class="icon-time"></i> -->
<!-- 										<span>下午3:15</span> -->
<!-- 									</span> -->
<!-- 								</span> -->
<!-- 							</a> -->
<!-- 						</> -->

<!-- 						<li> -->
<!-- 							<a href="javascript:alert('预留功能,待开发');"> -->
<!-- 								查看所有消息 -->
<!-- 								<i class="icon-arrow-right"></i> -->
<!-- 							</a> -->
<!-- 						</li> -->
<!-- 					</ul> -->
				</li>

				<li class="light-blue">
					<a data-toggle="dropdown" href="#" class="dropdown-toggle">
						<img class="nav-user-photo" src="assets/img/user.jpg" alt="Jason's Photo" />
						<span class="user-info">
							<small>Welcome ${username},</small>
							${userRoles}
						</span>

						<i class="icon-caret-down"></i>
					</a>

					<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
						<li>
							<a href="#">
								<i class="icon-cog"></i>
								设置
							</a>
						</li>

						<li>
							<a href="#">
								<i class="icon-user"></i>
								个人信息
							</a>
						</li>
						
						<li class="divider"></li>
						<li>
							<a href="${ctx }/empAttendance/toEmpAttendanceView">
								<i class="icon-refresh"></i>
								切换至员工版
							</a>
						</li>
						<li>
							<a href="logout">
								<i class="icon-off"></i>
								退出
							</a>
						</li>
					</ul>
				</li>
			</ul><!-- /.ace-nav -->
		</div><!-- /.navbar-header -->
	</div><!-- /.container -->
</div>
<script type="text/javascript" src="${ctx}/assets/js/require.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/biz/include/header.js"></script>