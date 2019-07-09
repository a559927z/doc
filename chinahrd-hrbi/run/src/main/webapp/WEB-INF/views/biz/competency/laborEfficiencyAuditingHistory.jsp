<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>劳动力效能</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/competency/laborEfficiencyAuditingHistory.css"/>
</head>
<body>
<div class="labor-history" id="laborHistory">
    <div class="rightBody">
        <div id="page-one" class="rightBodyPage">
        	<div class="row ct-row">
				<div class="col-xs-12 ct-col1 rightSetUpTitle">
					<ul class="breadcrumb">
						<li>
							<i class="icon-home home-icon"></i>
							<a href="javascript:void(0)" id="toHomeBtn">数据导入</a>
						</li>
						<li class="active">查看历史</li>
					</ul>
				</div>
			</div>
			<div class="row ct-row">
                <div class="col-sm-12 ct-line-col SetUpBody" >
					<div class="col-sm-12" id="dataAuditing-view">
						<div class="step" id="auditingState"></div>
					</div>
				</div>
			</div>
        </div>
    </div>
</div>

<script src="${jsRoot}biz/competency/laborEfficiencyAuditingHistory.js"></script>
</body>
</html>
