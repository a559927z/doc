<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>劳动力效能导入</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/competency/laborEfficiencyAuditing.css"/>
</head>
<body>
<input type="hidden" id="attId" value="${attId }">
<div class="labor-auditing" id="laborAuditing">
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
				<div class="bottom-div">
					<div class="col-sm-12">
						<div class="bottom-div-one">
							<div style="margin-left: 20px; margin-top: 15px; float: left;">
								<div style="font-size: 28px; font-weight: bold;">2016年6月考勤数据审核</div>
								<div>上传者：${empName } | 上传时间：${createTime }</div>
							</div>
							<div class="auditing-button">
								审核通过
							</div>
						</div>
					</div>
					<div class="col-sm-12" id="attendance-view">
                    	<table id="attendanceGrid"></table>
                        <div class="clearfix"></div>
					</div>
                </div>
            </div>
        </div>;
    </div>
</div>

<!-- 个人出勤明细 弹出框 begin -->
<div class="modal fade topWindow popup-modal" id="personDetailModal"
	tabindex="-1" role="dialog" aria-labelledby="personDetailModalLabel"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-header-text" id="personDetailModalLabel">个人出勤明细</div>
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<div class="modal-title" style="display: inline-block;">
					<div class="modal-title-first">姓&nbsp;&nbsp;名：</div>
					<div class="modal-title-first modal-style-margin">
						<span id="userNameId"></span>
					</div>
					<div class="modal-title-text">时&nbsp;&nbsp;间：</div>
                 	<div class="modal-style-margin">
						<select class="select-first select-height" id="personDetailYearSelect"></select>
					</div>
					<div class="modal-style-margin">
						<select class="select-second select-height" id="personDetailMonthSelect"></select>
					</div>
					<div id="personDetailOkBtnId" class="okBtn btn-margin">确定</div>
				</div>
				<div class="modal-title-point">
					统计说明：“实出勤”、“应出勤”、“加班”、“假期统计”等统计单位为小时
				</div>
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane active" id="personDetailTab">
						<table id="personDetailGrid"></table>
						<!-- <div id="personDetailGridPager"></div> -->
					</div>
					<div class="clearfix"></div>
				</div>
				<br>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
</div>
<!-- 个人出勤明细 弹出框 end -->

<!--遮罩层-->
<div id="shade" class="shade"></div>
<!--遮罩层-->

<script src="${jsRoot}biz/competency/laborEfficiencyAuditing.js"></script>
</body>
</html>
