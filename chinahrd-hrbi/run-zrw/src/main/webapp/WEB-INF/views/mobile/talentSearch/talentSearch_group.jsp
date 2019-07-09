<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="../include/top.jsp" %>
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/biz/competency/talentStructure.css?v=${v.version}"/>
</head>
<body>
<div class="page-content">
    <div class="main-container-inner">
        <input type="hidden" id="empId" value="${empId }" />
            <div class="chartPanel">
               		<div class="chart" id="chartId"></div>
             </div>
	</div>
</div>
<div class="shade"></div>
<script src="${ctx}/assets/js/require.js"></script>
<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/mobile/js/biz/talentSearch/talentSearch_group.js?v=${v.version}"></script>
</body>
</html>
