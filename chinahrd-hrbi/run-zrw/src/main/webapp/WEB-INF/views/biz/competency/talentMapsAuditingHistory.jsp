<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<!-- jsp文件头和头部 -->
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>查看全部待发布地图</title>
	<link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"  />
	<link rel="stylesheet" href="${ctx}/assets/css/biz/competency/talentMapsAuditingHistory.css"/>
</head>
<body>

<div class="talentmaps-history" id="talentMapsHistory">
	<div class="rightBody">
		<div id="page-one" class="rightBodyPage">
			<div class="row ct-row">
				<div class="col-xs-12 ct-col1 rightSetUpTitle">
					<ul class="breadcrumb">
						<li>
							<i class="icon-home home-icon"></i>
							<a href="javascript:void(0)" id="toHomeBtn">人才地图首页</a>
						</li>
						<li class="active">查看全部待发布地图</li>
					</ul>
				</div>
			</div>
			<div class="row ct-row">
				<div class="col-md-12 ct-line-col SetUpBody">
					<hr class="hr-class">
					<div class="bottom-div" id="auditing"></div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="${ctx}/assets/js/require.js"></script>
<script src="${ctx}/assets/js/biz/competency/talentMapsAuditingHistory.js"></script>
</body>
</html>
