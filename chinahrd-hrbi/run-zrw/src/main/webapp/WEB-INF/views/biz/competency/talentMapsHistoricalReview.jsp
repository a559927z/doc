<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<!-- jsp文件头和头部 -->
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>查看地图发布历史</title>
	<link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"  />
	<link rel="stylesheet" href="${ctx}/assets/css/biz/competency/talentMapsHistoricalReview.css"/>
</head>
<body>
<input type="hidden" id="mapId" value="${id }">
<input type="hidden" id="topId" value="${topId }">
<input type="hidden" id="adjustmentId" value="${adjustmentId }">
<input type="hidden" id="empId" value="${empId }">
<input type="hidden" id="yearMonth" value="${yearMonth }">
<div class="talentmaps-history" id="talentMapsHistoty">
	<div class="rightBody">
		<div id="page-one" class="rightBodyPage">
			<div class="row ct-row">
				<div class="col-xs-12 ct-col1 rightSetUpTitle">
					<ul class="breadcrumb">
						<li>
							<i class="icon-home home-icon"></i>
							<a href="javascript:void(0)" id="toHomeBtn">人才地图首页</a>
						</li>
						<li class="active">查看历史</li>
						<li class="active">
							<select id="halfTime">
								<!-- <option>2016年上半年</option> -->
							</select>
							<select id="organization">
								<!-- <option>广州公司</option> -->
							</select>
						</li>
					</ul>
				</div>
			</div>
			<div class="row ct-row">
				<div class="bottom-div">
					<div class="row column panel-main-header" id="myScrollspy">
						<div class="col-xs-2" id="myNav_div">
							<ol class="ui-step ui-step" id="myNav">
								<li class="#section-1">
									<div class="ui-step-cont">
										<span class="ui-step-cont-circle"></span>
										<span class="ui-step-cont-text" id="createTime"></span>
										<span class="ui-step-cont-time"><span class="name-font" id="adjustmentName"></span>生成原地图</span>
									</div>
									<div class="ui-step-line"></div>
								</li>
								<li class="#section-2">
									<div class="ui-step-cont">
										<span class="ui-step-cont-circle"></span>
										<span class="ui-step-cont-text" id="auditingTime"></span>
										<span class="ui-step-cont-time"><span class="name-font" id="auditingName"></span>审核地图</span>
									</div>
									<div class="ui-step-line" id="uiStepLineThree"></div>
								</li>
								<li class="#section-3" id="sectionThree">
									<div class="ui-step-cont">
										<span class="ui-step-cont-circle"></span>
										<span class="ui-step-cont-text" id="releaseTime"></span>
										<span class="ui-step-cont-time"><span class="name-font" id="releaseName"></span>发布地图</span>
									</div>
								</li>
							</ol>
						</div>
						<div class="col-xs-10">
							<div id="accordion" class="accordion-style1 panel-group">
								<div class="panel-default" id="section-1-">
									<div class="panel-collapse collapse in" id="collapseOne">
										<div class="panel-body">
										</div>
									</div>
								</div>
								<div class="panel-default" id="section-2-">
									<div class="panel-collapse collapse in" id="collapseTwo">
										<div class="panel-body">
											<div class="row">
												<div class="col-xs-12">
							                        <div class="clearfix"></div>
						                        	<div class="tab-content">
							                            <table id="talentAdjustmentGrid"></table>
							                            <div id="talentAdjustmentPager"></div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="panel-default" id="section-3-">
									<div class="panel-collapse collapse in" id="collapseThree">
										<div class="panel-body">
											<div class="row">
												<div class="col-xs-12" id="">
													<div class="clearfix"></div>
						                        	<div class="tab-content">
							                            <table id="talentPublishGrid"></table>
							                            <div id="talentPublishPager"></div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="${ctx}/assets/js/require.js"></script>
<script src="${ctx}/assets/js/biz/competency/talentMapsHistoricalReview.js"></script>
</body>
</html>