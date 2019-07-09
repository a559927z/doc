<%
	String pathl = request.getContextPath();
	String basePathl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+pathl+"/";
%>

<!-- 本页面涉及的js函数，都在head.jsp页面中     -->
<div class="sidebar" id="sidebar">
	<script>
		try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
	</script>
	<%--<div class="sidebar-shortcuts" id="sidebar-shortcuts">--%>
 		<%--<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">--%>
 			<%--<button class="btn btn-success" title="数据字典">--%>
 				<%--<i class="icon-signal"></i>--%>
 			<%--</button>--%>

 			<%--<button class="btn btn-info" title="组件demo">--%>
 				<%--<i class="icon-pencil"></i>--%>
 			<%--</button>--%>

 			<%--<button class="btn btn-warning" title="人员交际" >--%>
 				<%--<i class="icon-group"></i>--%>
 			<%--</button>--%>

 			<%--<button class="btn btn-danger" title="菜单管理" >--%>
 				<%--<i class="icon-cogs"></i>--%>
 			<%--</button>--%>
		<%--</div>--%>

		<%--<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">--%>
			<%--<span class="btn btn-success"></span>--%>

			<%--<span class="btn btn-info"></span>--%>

			<%--<span class="btn btn-warning"></span>--%>

			<%--<span class="btn btn-danger"></span>--%>
		<%--</div>--%>
	<%--</div>--%>
	<div class="sidebar-collapse" id="sidebar-collapse">
		<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
	</div>

	<ul class="nav nav-list" id="navList">
		<li class="active">
			<a href="#" class="dropdown-toggle">
				<i class="icon-desktop"></i>
				<span class="menu-text">首页</span>
				<b class="arrow icon-angle-down"></b>
			</a>
			<ul class="submenu" style="display: none;">
				<li class="active"><a href="manageHome/index" target="mainFrame"><i class="icon-double-angle-right"></i>首页</a></li></li>
				<li><a href="manageHome/bossIndex" target="mainFrame"><i class="icon-double-angle-right"></i>首页(老板)</a></li>
				<li><a href="manageHome/lineIndex" target="mainFrame"><i class="icon-double-angle-right"></i>首页(直线)</a></li>
				<li><a href="manageHome/hrIndex" target="mainFrame"><i class="icon-double-angle-right"></i>首页(HR)</a></li>
			</ul>
		</li>

		<%--<li>--%>
			<%--<a href="#" class="dropdown-toggle">--%>
				<%--<i class="icon-th-large"></i>--%>
				<%--<span class="menu-text">新UI</span>--%>
				<%--<b class="arrow icon-angle-down"></b>--%>
			<%--</a>--%>
			<%--<ul class="submenu" style="display: none;">--%>
				<%--<li><a href="dismissRisk/toDismissRiskView2" target="mainFrame"><i class="icon-double-angle-right"></i>人才流失风险</a></li></li>--%>
			<%--</ul>--%>
		<%--</li>--%>

		<c:forEach items="${funDtos}" var="parentDto">
			<c:choose>
			<c:when test="${fn:length(parentDto.childs) > 0 && parentDto.isMenu == 1}">
				<li>
					<a href="#" class="dropdown-toggle">
						<c:choose>
							<c:when test="${parentDto.functionName == '系统管理'}">
								<i class="icon-gears"></i><span class="menu-text">${parentDto.functionName}</span>
							</c:when>
							<c:when test="${parentDto.functionName == '驱动力'}">
								<i class="icon-dashboard"></i><span class="menu-text">${parentDto.functionName}</span>
							</c:when>
							<c:when test="${parentDto.functionName == '组织统计'}">
								<i class="icon-sitemap"></i><span class="menu-text">${parentDto.functionName}</span>
							</c:when>
							<c:when test="${parentDto.functionName == '生产力'}">
								<i class="icon-bar-chart"></i><span class="menu-text">${parentDto.functionName}</span>
							</c:when>
							<c:when test="${parentDto.functionName == '综合功能'}">
								<i class="icon-group"></i><span class="menu-text">${parentDto.functionName}</span>
							</c:when>
							<c:when test="${parentDto.functionName == '元动力'}">
								<i class="icon-lightbulb"></i><span class="menu-text">${parentDto.functionName}</span>
							</c:when>
							<c:when test="${parentDto.functionName == '胜任力'}">
								<i class="icon-suitcase"></i><span class="menu-text">${parentDto.functionName}</span>
							</c:when>
							<c:otherwise>
								<i class="icon-group"></i><span class="menu-text">${parentDto.functionName}</span>
							</c:otherwise>
						</c:choose>
						<b class="arrow icon-angle-down"></b>
					</a>
					<ul class="submenu">
						<c:forEach items="${parentDto.childs}" var="childDto">
							<c:choose>
								<%-- <c:when test="${childDto.pathUrl == 'emp/list'}">

								</c:when> --%>
								<c:when test="${childDto.pathUrl == 'talentContrast/toTalentContrastView'||childDto.pathUrl == 'talentProfile/toTalentProfileView'}">
									<li><a href="${childDto.pathUrl}" target="mainFrame"><i class="icon-double-angle-right"></i>${childDto.functionName}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${childDto.pathUrl}" target="mainFrame"><i class="icon-double-angle-right"></i>${childDto.functionName}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</ul>
				</li>
			</c:when>
			<c:when test="${parentDto.isMenu != 1 && parentDto.pathUrl != ''}">
			<li>
				<a href="${parentDto.pathUrl}" target="mainFrame">
					<c:choose>
					<c:when test="${parentDto.functionName == '四力指数'}"><i class="icon-th-large"></i>${parentDto.functionName}</c:when>
					<c:when test="${parentDto.functionName == '月报'}"><i class="icon-rss"></i>${parentDto.functionName}</c:when>
					<c:otherwise><i class="icon-file-alt"></i>${parentDto.functionName}</c:otherwise>
					</c:choose>
				</a>
			</li>
			</c:when>
			<c:otherwise>
				<%-- 展示其它根节点 --%>
			</c:otherwise>
			</c:choose>
		</c:forEach>

		<c:if test = "${username == 'superAdmin'}">
			<li>
				<a href="#" class="dropdown-toggle">
					<i class="icon-file-alt"></i>
					<span class="menu-text">组件管理</span>
					<b class="arrow icon-angle-down"></b>
				</a>
				<ul class="submenu">
					<li><a href="demo/compDemo" target="mainFrame"><i class="icon-double-angle-right"></i>vue组件Demo</a></li></li>
					<li><a href="demo/pieTableDemo" target="mainFrame"><i class="icon-double-angle-right"></i>饼图、表格互动Demo</a></li></li>
					<li><a href="demo/riskTreeDemo2" target="mainFrame"><i class="icon-double-angle-right"></i>离职风险树组件2</a></li></li>
					<li><a href="demo/messengerDemo" target="mainFrame"><i class="icon-double-angle-right"></i>消息组件</a></li>
					<li><a href="demo/sequenceCountDemo" target="mainFrame"><i class="icon-double-angle-right"></i>职位序列Demo</a></li>
					<li><a href="demo/importExcelDemo" target="mainFrame"><i class="icon-double-angle-right"></i>解析Excel</a></li>
					<li><a href="demo/talentMapDemo" target="mainFrame"><i class="icon-double-angle-right"></i> 人才地图</a></li>
					<li><a href="demo/affixNavDemo" target="mainFrame"><i class="icon-double-angle-right"></i>附加导航</a></li>
					<li><a href="demo/barDemo" target="mainFrame"><i class="icon-double-angle-right"></i>barDemo</a></li>
					<li><a href="demo/dateTimeDemo" target="mainFrame"><i class="icon-double-angle-right"></i>日期组件</a></li>
					<li><a href="demo/gridDemo" target="mainFrame"><i class="icon-double-angle-right"></i>表格组件</a></li>
					<li><a href="demo/navMenuDemo" target="mainFrame"><i class="icon-double-angle-right"></i>导航菜单组件</a></li>
					<li><a href="demo/searchBoxDemo" target="mainFrame"><i class="icon-double-angle-right"></i>筛选条件组件</a></li>
					<li><a href="demo/timeLineDemo" target="mainFrame"><i class="icon-double-angle-right"></i>时间线组件</a></li>
					<li><a href="demo/tooltipDemo" target="mainFrame"><i class="icon-double-angle-right"></i>提示信息组件</a></li>
					<li><a href="demo/treeDemo" target="mainFrame"><i class="icon-double-angle-right"></i>树组件</a></li>
					<li><a href="demo/orgTreeDemo" target="mainFrame"><i class="icon-double-angle-right"></i>机构树组件</a></li>
					<li><a href="demo/vernierCursorDemo" target="mainFrame"><i class="icon-double-angle-right"></i>游标尺组件</a></li>
					<li><a href="demo/tabsDemo" target="mainFrame"><i class="icon-double-angle-right"></i>标签页组件</a></li>
					<li><a href="demo/riskTreeDemo" target="mainFrame"><i class="icon-double-angle-right"></i>离职风险树组件</a></li>
					<li><a href="demo/lazyLoadDemo" target="mainFrame"><i class="icon-double-angle-right"></i>图片懒加载组件</a></li>
				</ul>
			</li>
		</c:if>
	</ul><!--/.nav-list-->

	<script>
		try{ace.settings.check('sidebar', 'collapsed')}catch(e){}
	</script>
</div><!--/#sidebar-->

