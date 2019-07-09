<%
	String pathh = request.getContextPath();
	String basePathh = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+pathh+"/";
%>
<%-- <link rel="stylesheet" href="${ctx}/assets/css/zTreeStyle/zTreeStyle.css"  /> --%>
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
    background-color: #438eb9;
    border: 0px !important;
    color: #ffffff;
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
.navbar-content-left{
	margin-left: 183px;
}
.navbar-content-right{
	margin-right: 183px;
}
</style>
<input type="hidden" id="topOrganId" value="${topOrganId}">
<input type="hidden" id="topOrganName" value="${topOrganName}">
<div class="navbar navbar-default" id="navbar">
	<script type="text/javascript">
		try{ace.settings.check('navbar' , 'fixed')}catch(e){}
	</script>

	<div class="navbar-container" id="navbar-container">
		<div class="navbar-content-left">
            <img class="ct-logo" width="28" height="28" src="${ctx}/assets/img/base/logo.png" />
            <span class="ct-logo-text">才报数据平台</span>
		</div><!-- /.navbar-header -->
		<div class="pull-right navbar-content-right" role="navigation">
			<ul class="nav ace-nav">

				<li class="blueTitle" id="message"></li>

				<li class="light-blue">
					<a data-toggle="dropdown" href="#" class="dropdown-toggle">
						<img class="nav-user-photo" src="${ctx}/assets/img/user.jpg" alt="Jason's Photo" />
						<span class="user-info">
							<small>Welcome ${username},</small>
							${userRoles}
						</span>

						<i class="icon-caret-down"></i>
					</a>
					<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
						<c:if test="${multiRoles}">
						<li>
							<a href="${ctx }/">
								<i class="icon-refresh"></i>
								切换至管理人员版
							</a>
						</li>
						<li class="divider"></li>
						</c:if>
						<li>
							<a href="${ctx }/logout">
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
<script type="text/javascript" src="${ctx}/assets/js/biz/include/attendanceHeader.js"></script>