<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<html>
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>人才剖像详情</title>
	<link rel="stylesheet" href="${ctx}/assets/css/biz/employee/talentDetail.css">
</head>
<body>
	<div class="page-content" >
		<div class="row talent-detail-header">
			<div class="panel panel-default">
				<div class="panel-body header-row">
					<div class="row">
						<div class="col-sm-2 col-xs-2 normal-row"></div>
						<div class="col-sm-5 col-xs-5 normal-row">
							<div class="col-xs-12">
								<label>${empDto.userName}</label> &nbsp;
								<span>${empDto.positionName}</span> - 
								<span>${empDto.organName}</span> &nbsp;
								<span>${empDto.workPlace}</span>
							</div>
						</div>
						<div class="col-sm-5 col-xs-12 normal-row">
							<div class="col-xs-12 inputTime">
								<label>入职时间：</label>
								<label><fmt:formatDate value="${empDto.entryDate}" pattern="yyyy-MM-dd" /></label>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2 col-xs-12 normal-row">
							<div class="talent-detail-photo">
								<img id="empImgPath" src="${empDto.imgPath}" onerror="this.src='${ctx}/assets/img/base/u-default.png'" alt="${empDto.userName}" class="img-circle" />
							</div>
							<input type="hidden" id="empId" value="${empDto.empId}">
						</div>
						<div class="col-sm-5 col-xs-12 normal-row">
							<div class="col-xs-12"><label>直接上级：</label><span>${empDto.empHfName}</span></div>
							<div class="col-xs-12"><label>手机：</label><span>${empDto.telPhone}</span></div>
							<div class="col-xs-12"><label>QQ号：</label><span>${empDto.qq}</span></div>
						</div>
						<div class="col-sm-5 col-xs-12 normal-row">
							<div class="col-xs-12"><label>职位序列：</label><span>${empDto.sequenceName}<c:if test="${empDto.sequenceSubName != null}">-</c:if>${empDto.sequenceSubName}<c:if test="${empDto.rankName != null}">-</c:if>${empDto.rankName}</span></div>
							<div class="col-xs-12"><label>邮箱：</label><span>${empDto.email}</span></div>
							<div class="col-xs-12"><label>微信：</label><span>${empDto.wxCode}</span></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row column panel-main-header" id="myScrollspy">
			<div class="col-xs-2" id="myNav_div">
				<ul class="nav u-affix-nav" id="myNav">
					<li class="menu-line1 active">
						<div class="menu-line-spor1"></div>
						<a class="menu-line-select" href="#section-1">回顶部</a>
					</li>
					<li class="menu-line2">
						<div class="menu-line-spor"></div>
						<a class="menu-line-select" href="#section-1">基本信息</a>
					</li>
					<li class="menu-line2">
						<div class="menu-line-spor"></div>
						<a class="menu-line-select" href="#section-2">任职信息</a>
					</li>
					<li class="menu-line2">
						<div class="menu-line-spor"></div>
						<a class="menu-line-select" href="#section-3">绩效信息</a>
					</li>
					<li class="menu-line2">
						<div class="menu-line-spor"></div>
						<a class="menu-line-select" href="#section-4">能力信息</a>
					</li>
					<li class="menu-line2">
						<div class="menu-line-spor"></div>
						<a class="menu-line-select" href="#section-5">奖惩信息</a>
					</li>
					<li class="menu-line2">
						<div class="menu-line-spor"></div>
						<a class="menu-line-select" href="#section-6">培训经历</a>
					</li>
					<li class="menu-line2">
						<div class="menu-line-spor"></div>
						<a class="menu-line-select" href="#section-7">过往履历</a>
					</li>
					<li class="menu-line2">
						<div class="menu-line-spor"></div>
						<a class="menu-line-select" href="#section-8">教育背景</a>
					</li>
					<li class="menu-line2">
						<div class="menu-line-spor"></div>
						<a class="menu-line-select" href="#section-9">所获职称</a>
					</li>
					<li class="menu-line2">
						<div class="menu-line-spor"></div>
						<a class="menu-line-select" href="#section-10">家庭关系</a>
					</li>
					<li class="menu-line3">
						<div class="menu-line-spor"></div>
						<a class="menu-line-select" href="#section-11">其它技能/特长</a>
					</li>
				</ul>
			</div>
			<div class="col-xs-10">
					<div id="accordion" class="accordion-style1 panel-group">
				<div class="panel panel-default">
					<div class="panel-heading" id="section-1-">
						<h3 class="panel-title">
							<a class="accordion-toggle" data-toggle="collapse"
								href="#collapseOne"> <i class="icon-angle-down bigger-110"
								data-icon-hide="icon-angle-down"
								data-icon-show="icon-angle-right"></i> &nbsp;基本信息
							</a>
						</h3>
					</div>
					<div class="panel-collapse collapse in" id="collapseOne">
						<div class="panel-body page-main-header">
							<div class="row">
								<div class="col-sm-4 col-xs-12 normal-row">
									<div class="col-xs-12"><label>姓名：</label><label>${empDto.userName}</label></div>
									<div class="col-xs-12"><label>性别：</label><label>${empDto.sex == 'm' ? '男' : '女'}</label></div>
									<div class="col-xs-12"><label>出生日期：</label><label><fmt:formatDate value="${empDto.birthDate}" pattern="yyyy-MM-dd" /></label></div>
									<div class="col-xs-12"><label>学历：</label><label>${empDto.degree}</label></div>
									<div class="col-xs-12"><label>婚姻状况：</label><label>${empDto.marryStatus == 1 ? '已婚' : '未婚'}</label></div>
									<div class="col-xs-12"><label>子女数量：</label><label id="childNum"></label></div>
								</div>
								<div class="col-sm-4 col-xs-12 normal-row">
									<div class="col-xs-12"><label>籍贯：</label><label>${empDto.nativePlace}</label></div>
									<div class="col-xs-12"><label>民族：</label><label>${empDto.nation}</label></div>
									<div class="col-xs-12"><label>政治面貌：</label><label>${empDto.pattyName}</label></div>
									<div class="col-xs-12"><label>证件类型：</label><label>${empDto.nationalType}</label></div>
									<div class="col-xs-12"><label>证件号码：</label><label>${empDto.nationalId}</label></div>
									<div class="col-xs-12"><label>户口所在地：</label><label>${empDto.birthPlace}</label></div>
								</div>
								<div class="col-sm-4 col-xs-12 normal-row">
									<div class="col-xs-12"><label>现住址：</label><label>${empDto.address}</label></div>
									<div class="col-xs-12"><label>紧急联系人：</label><label>${empDto.contactPersonName}</label></div>
									<div class="col-xs-12"><label>联系方式：</label><label>${empDto.contactTelPhone}</label></div>
									<div class="col-xs-12"><label>关系：</label><label>${empDto.contactCall}</label></div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading" id="section-2-">
						<h3 class="panel-title">
							<a class="accordion-toggle" data-toggle="collapse"
								href="#collapseTwo"> <i class="icon-angle-down bigger-110"
								data-icon-hide="icon-angle-down"
								data-icon-show="icon-angle-right"></i> &nbsp;任职信息
							</a>
						</h3>
					</div>
					<div class="panel-collapse collapse in" id="collapseTwo">
						<div class="panel-body page-main-header">
							<div class="row">
								<div class="col-sm-4 col-xs-12 normal-row">
									<div class="col-xs-12"><label>合同单位：</label><label>${empDto.contractUnit}</label></div>
									<div class="col-xs-12"><label>工作地点：</label><label>${empDto.workPlace}</label></div>
									<div class="col-xs-12"><label>工作部门：</label><label>${empDto.organName}</label></div>
									<div class="col-xs-12"><label>任职岗位：</label><label>${empDto.positionName}</label></div>
									<div class="col-xs-12"><label>合同性质：</label><label>${empDto.contract}</label></div>
									<div class="col-xs-12"><label>员工状态：</label><label>${empDto.passtimeOrFulltime}</label></div>
								</div>
								<div class="col-sm-4 col-xs-12 normal-row">
									<div class="col-xs-12"><label>工号：</label><label>${empDto.empKey}</label></div>
									<div class="col-xs-12"><label>职位主序列：</label><label>${empDto.sequenceName}</label></div>
									<div class="col-xs-12"><label>职位子序列：</label><label>${empDto.sequenceSubName}</label></div>
									<div class="col-xs-12"><label>职位层级：</label><label>${empDto.abilityName}</label></div>
									<div class="col-xs-12"><label>职级：</label><label>${empDto.rankName}</label></div>
									 <div class="col-xs-12"><label>职衔：</label><label>${empDto.jobTitleName}</label></div>
								</div>
								<div class="col-sm-4 col-xs-12 normal-row">
									<div class="col-xs-12"><label>入职日期：</label><label><fmt:formatDate value="${empDto.entryDate}" pattern="yyyy-MM-dd" /></label></div>
									<div class="col-xs-12"><label>转正日期：</label><label>
										<c:choose>
											<c:when test="${empDto.regularDate == null}">-</c:when>
											<c:otherwise><fmt:formatDate value="${empDto.regularDate}" pattern="yyyy-MM-dd" /></c:otherwise>
										</c:choose>
									</label></div>
									<!--
									<div class="col-xs-12"><label>离职日期</label><label>
										<c:choose>
											<c:when test="${empDto.runOffDate == null}">-</c:when>
											<c:otherwise><fmt:formatDate value="${empDto.runOffDate}" pattern="yyyy-MM-dd" /></c:otherwise>
										</c:choose>
									</label></div>
									-->
									<div class="col-xs-12"><label>应聘渠道：</label><label>${empDto.applyChannel}</label></div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12 page-main-title-par"><span class="page-main-title">工作异动</span></div>
								<div class="col-xs-12 page-main-line-par"><label class="page-main-line"></label></div>
								<div class="col-xs-12" id="jobChangeTable">
									<table class="borderless" id="jobChangeGrid"></table>
								</div>
							</div>
							<div class="row" id="growUpDiv">
								<div class="col-xs-12 page-main-title-par"><span class="page-main-title">成长轨迹</span></div>
								<div class="col-xs-12 page-main-line-par"><label class="page-main-line"></label></div>
								<div class="col-xs-12" id="jobChangeChart"></div>
							</div>
						</div>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading" id="section-3-">
						<h3 class="panel-title">
							<a class="accordion-toggle" data-toggle="collapse"
								href="#collapseThree"> <i
								class="icon-angle-down bigger-110"
								data-icon-hide="icon-angle-down"
								data-icon-show="icon-angle-right"></i> &nbsp;绩效信息
							</a>
						</h3>
					</div>
					<div class="panel-collapse collapse in" id="collapseThree">
						<div class="panel-body">
							<div class="row">
								<input type="hidden" value="${performanceStr}" id="performanceStr">
								<div class="col-xs-12" id="prefTable">
									<table class="borderless" id="perfGrid"></table>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12 page-main-title-par"><span class="page-main-title">绩效轨迹</span></div>
								<div class="col-xs-12 page-main-line-par"><label class="page-main-line"></label></div>
								<div class="col-xs-12" id="perfChart"></div>
							</div>
						</div>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading" id="section-4-">
						<h3 class="panel-title">
							<a class="accordion-toggle" data-toggle="collapse"
								href="#collapseFour"> <i
								class="icon-angle-down bigger-110"
								data-icon-hide="icon-angle-down"
								data-icon-show="icon-angle-right"></i> &nbsp;能力信息
							</a>
						</h3>
					</div>
					<div class="panel-collapse collapse in" id="collapseFour">
						<div class="panel-body">
							<div class="row">
								<div class="col-xs-12 page-main-title-par"><span class="page-main-title col-row-mt0">360领导力测评报告</span></div>
								<div class="col-xs-12 page-main-line-par"><label class="page-main-line"></label></div>
								<div class="col-xs-12" id="evaluationTable">
									<table class="borderless" id="evaluationGrid"></table>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12 page-main-title-par"><span class="page-main-title">其它测评报告</span></div>
								<div class="col-xs-12 page-main-line-par"><label class="page-main-line"></label></div>
								<div class="col-xs-12" id="otherEvaluation">&nbsp;</div>
							</div>
						</div>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading" id="section-5-">
						<h3 class="panel-title">
							<a class="accordion-toggle" data-toggle="collapse"
								href="#collapseFive"> <i
								class="icon-angle-down bigger-110"
								data-icon-hide="icon-angle-down"
								data-icon-show="icon-angle-right"></i> &nbsp;奖惩信息
							</a>
						</h3>
					</div>
					<div class="panel-collapse collapse in" id="collapseFive">
						<div class="panel-body">
							<div class="col-xs-12" >
								<table class="borderless" id="bonusGrid"></table>
							</div>
						</div>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading" id="section-6-">
						<h3 class="panel-title">
							<a class="accordion-toggle" data-toggle="collapse"
							   href="#collapseSix"> <i
									class="icon-angle-down bigger-110"
									data-icon-hide="icon-angle-down"
									data-icon-show="icon-angle-right"></i> &nbsp;培训经历
							</a>
						</h3>
					</div>
					<div class="panel-collapse collapse in" id="collapseSix">
						<div class="panel-body">
							<div class="col-xs-12" >
								<table class="borderless" id="trainExpGrid"></table>
							</div>
						</div>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading" id="section-7-">
						<h3 class="panel-title">
							<a class="accordion-toggle" data-toggle="collapse"
							   href="#collapseSeven"> <i
									class="icon-angle-down bigger-110"
									data-icon-hide="icon-angle-down"
									data-icon-show="icon-angle-right"></i> &nbsp;过往履历
							</a>
						</h3>
					</div>
					<div class="panel-collapse collapse in" id="collapseSeven">
						<div class="panel-body">
							<div class="col-xs-12" >
								<table class="borderless" id="resumeGrid"></table>
							</div>
						</div>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading" id="section-8-">
						<h3 class="panel-title">
							<a class="accordion-toggle" data-toggle="collapse"
							   href="#collapseEight"> <i
									class="icon-angle-down bigger-110"
									data-icon-hide="icon-angle-down"
									data-icon-show="icon-angle-right"></i> &nbsp;教育背景
							</a>
						</h3>
					</div>
					<div class="panel-collapse collapse in" id="collapseEight">
						<div class="panel-body">
							<div class="col-xs-12" >
								<table class="borderless" id="eduGrid"></table>
							</div>
						</div>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading" id="section-9-">
						<h3 class="panel-title">
							<a class="accordion-toggle" data-toggle="collapse"
							   href="#collapseNine"> <i
									class="icon-angle-down bigger-110"
									data-icon-hide="icon-angle-down"
									data-icon-show="icon-angle-right"></i> &nbsp;所获职称
							</a>
						</h3>
					</div>
					<div class="panel-collapse collapse in" id="collapseNine">
						<div class="panel-body">
							<div class="col-xs-12" >
								<table class="borderless" id="profTitleGrid"></table>
							</div>
						</div>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading" id="section-10-">
						<h3 class="panel-title">
							<a class="accordion-toggle" data-toggle="collapse"
							   href="#collapseTen"> <i
									class="icon-angle-down bigger-110"
									data-icon-hide="icon-angle-down"
									data-icon-show="icon-angle-right"></i> &nbsp;家庭关系
							</a>
						</h3>
					</div>
					<div class="panel-collapse collapse in" id="collapseTen">
						<div class="panel-body">
							<div class="col-xs-12" >
								<table class="borderless" id="familyGrid"></table>
							</div>
						</div>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading" id="section-11-">
						<h3 class="panel-title">
							<a class="accordion-toggle" data-toggle="collapse"
							   href="#collapseEleven"> <i
									class="icon-angle-down bigger-110"
									data-icon-hide="icon-angle-down"
									data-icon-show="icon-angle-right"></i> &nbsp;其它技能/特长
							</a>
						</h3>
					</div>
					<div class="panel-collapse collapse in" id="collapseEleven">
						<div class="panel-body">
							${empDto.speciality}
						</div>
					</div>
				</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		var anchor = '${anchor}';
	</script>
	<script src="${jsRoot}lib/echarts/echarts.js"></script>
	<script src="${jsRoot}biz/employee/talentDetail.js"></script>
</body>
</html>