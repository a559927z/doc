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
			<div class="rightTitle">
				<ul class="breadcrumb">
					<li>
						<i class="icon-home home-icon"></i>
						<a href="javascript:void(0)" id="toHomeBtn">人才地图首页</a>
					</li>
					<li class="active">查看历史</li>
				</ul>
			</div>
			<div class="row ct-row">
				<div class="bottom-div-one">
					<div class="row panel-main">
						<div class="col-xs-3" id="myNav_div">
							<div class="ui-step-two left">
								<div class="content-one">
									<div class="ui-step-cont-text"><span class="name-font" id="auditingName"></span>审核地图</div>
									<div class="ui-step-cont-time" id="auditingTime"></div>
								</div>
								<div class="content-two">
									<div class="ui-step-cont-text"><span class="name-font" id="releaseName"></span>发布地图</div>
									<div class="ui-step-cont-time" id="releaseTime"></div>
								</div>
							</div>
							<div class="ui-step-cont left">
								<div class="ui-step-cont-text">生成原地图</div>
								<div class="ui-step-cont-time" id="createTime"></div>
								<div class="ui-step-cont-circle"></div>
								<div class="ui-step-line"></div>
								<div class="ui-step-cont-circle" id="ui_step_cicle_2"></div>
								<div class="ui-step-line" id="step_line_2"></div>
								<div class="ui-step-cont-circle" id="ui_step_cicle_3"></div>
							</div>
						</div>
						<div class="col-xs-9 col-style">
							<div class="panel-default" id="section-1-">
								<div class="panel-body"></div>
							</div>
							<div class="panel-default" id="section-2-">
								<div class="panel-body">
									<div class="col-xs-12">
			                            <table id="talentAdjustmentGrid"></table>
			                            <div id="talentAdjustmentPager"></div>
									</div>
								</div>
							</div>
							<div class="panel-default" id="section-3-">
								<div class="panel-body">
									<div class="col-xs-12" id="">
			                            <table id="talentPublishGrid"></table>
			                            <div id="talentPublishPager"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="no-conten">&nbsp;</div>
					
				</div>
			</div>
		</div>
	</div>
</div>

<script src="${ctx}/assets/js/biz/competency/talentMapsHistoricalReview.js"></script>
</body>
</html>