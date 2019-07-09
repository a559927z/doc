<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>主动流失率</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/datetime/datetimepicker.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/drivingforce/accordDismiss.css"/>
</head>
<body>
<input type="hidden" id="quarterLastDay" value="${quarterLastDay}"/>
<div class="accordDismiss custiomize-content">
    <div class="leftBody">
        <div class="leftListBigDiv">主动流失率
            <div class="leftListSet"></div>
        </div>
        <div page="page-one" class="leftListDiv selectList">主动流失率</div>
        <div page="page-two" class="leftListDiv">流失原因及去向</div>
        <div page="page-three" class="leftListDiv">流失人员明细</div>
    </div>

    <div class="rightBody rightBody10">
        <div id="page-one" class="rightBodyPage">
        	<div group="true"> 
	            <div id="page-one-group1-1" class="col-sm-3 ct-30 ct-line-col ct-dissmiss">
	                <div class="top-div">
	                    <div class="index-common-title">
	                        <div class="index-common-title-left"></div>
	                        <div class="index-common-title-text">主动流失率</div>
	                        <div class="LSLSetUp"></div>
	                    </div>
	                    <div class="body-div">
	                        <div id="LSLDismissChart" class="col-sm-6 column" style="float: left;height: 120px;"></div>
	                        <div class="col-sm-6 column accord-dismiss-normal">
	                            <div><span class="accord-main-number"></span><span id="unit"></span></div>
	                            <div class="accord-main-content"></div>
<!-- 	                            <div class="accord-main-desc"> -->
<!-- 	                                <span class="accord-desc-title">说明：</span> -->
<!-- 	                                <span class="accord-desc-content"> -->
<!-- 	                                    <span class="accord-desc-warn">▅</span><span class="accord-desc-warn-txt">5%~10%</span> -->
<!-- 	                                    <span class="accord-desc-over">▅</span><span class="accord-desc-over-txt">10%以上</span> -->
<!-- 	                                </span> -->
<!-- 	                            </div> -->
	                        </div>
	                    </div>
	                </div>
	            </div>
	
	            <div id="page-one-group1-2" class="col-sm-3 ct-18 ct-line-col">
	                <div class="top-div">
	                    <div class="index-common-title">
	                        <div class="index-common-title-left"></div>
	                        <div class="index-common-title-text">关键人才流失</div>
	                    </div>
	                    <div class="body-div" id="keyDismissArea">
	                        <div class="accord-yj-float">
	                            <span class="accord-yj-float-value" >0</span>
	                            <span class="accord-yj-float-people">人</span>
	                        </div>
	                        <div class="accord-bottom-float">
	                            <div class="accord-bottom-float-text">较上季度</div>
	                            <div class="accord-bottom-float-arrow accord-bottom-float-arrow-rise"></div>
	                            <div class="accord-bottom-float-value accord-bottom-float-value-rise">0</div>
	                            <div class="accord-bottom-float-people">人</div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	
	            <div id="page-one-group1-3" class="col-sm-3 ct-18 ct-line-col">
	                <div class="top-div">
	                    <div class="index-common-title">
	                        <div class="index-common-title-left"></div>
	                        <div class="index-common-title-text">高绩效流失</div>
	                    </div>
	                    <div class="body-div" id="perfDismissArea">
	                        <div class="accord-yj-float">
	                            <span class="accord-yj-float-value">0</span>
	                            <span class="accord-yj-float-people">人</span>
	                        </div>
	                        <div class="accord-bottom-float">
	                            <div class="accord-bottom-float-text">较上季度</div>
	                            <div class="accord-bottom-float-arrow accord-bottom-float-arrow-drop"></div>
	                            <div class="accord-bottom-float-value accord-bottom-float-value-drop">0</div>
	                            <div class="accord-bottom-float-people">人</div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	
	            <div id="page-one-group1-4" class="col-sm-3 ct-34 ct-line-col">
	                <input id="quotaId" type="hidden" value="${quotaId}">
	                <div class="top-div" id="timeLine"></div>
	            </div>
			</div>
			<div group="true"> 
	            <div id="page-one-group2-1" class="col-sm-6 ct-line-col SetUpBody" view="chart">
	                <div class="index-common-title bottom-title">
	                    <div class="index-common-title-left bottom-left"></div>
	                    <div class="index-common-title-text bottom-text">主动流失率趋势</div>
	
	                    <div class="rightSetUpBtn">
	                        <div class="rightSetUpBtnDiv rightSetUpLeft rightSetUpBtnSelect icon">
	                            <div class="rightSetUpBtnTop"></div>
	                            <div class="rightSetUpLeftShowIcon"></div>
	                            <div class="rightSetUpLeftHideIcon"></div>
	                        </div>
	                        <div class="rightSetUpBtnDiv rightSetUpRight icon">
	                            <div class="rightSetUpBtnTop"></div>
	                            <div class="rightSetUpRightShowIcon"></div>
	                            <div class="rightSetUpRightHideIcon"></div>
	                        </div>
	                    </div>
	                </div>
	                <div class="bottom-div">
	                    <div class="chart-view">
	                        <div class="col-xs-12" id="trendChart" ></div>
	                    </div>
	                    <div class="table-view" id="trendGridArea">
	                        <table id="trendTableGrid"></table>
	                    </div>
	                </div>
	            </div>
	
	            <div id="page-one-group2-2" class="col-sm-6 ct-line-col SetUpBody" view="chart">
	                <div class="index-common-title bottom-title">
	                    <div class="index-common-title-left bottom-left"></div>
	                    <div class="index-common-title-text bottom-text">下级组织主动流失率对比</div>
	
	                    <div class="rightSetUpBtn">
	                        <div class="rightSetUpBtnDiv rightSetUpLeft rightSetUpBtnSelect icon">
	                            <div class="rightSetUpBtnTop"></div>
	                            <div class="rightSetUpLeftShowIcon"></div>
	                            <div class="rightSetUpLeftHideIcon"></div>
	                        </div>
	                        <div class="rightSetUpBtnDiv rightSetUpRight icon">
	                            <div class="rightSetUpBtnTop"></div>
	                            <div class="rightSetUpRightShowIcon"></div>
	                            <div class="rightSetUpRightHideIcon"></div>
	                        </div>
	                    </div>
	                </div>
	
	                <div class="bottom-div">
	                    <div class="chart-view">
	                        <div class="col-xs-12" id="contrastChart"></div>
	                    </div>
	                    <div class="table-view " id="contrastGridArea">
	                        <table class="summary" id="contrastGrid"></table>
	                    </div>
	                </div>
	            </div>
	
	            <div id="page-one-group2-3" class="col-sm-6 ct-line-col SetUpBody" view="chart">
	                <div class="index-common-title bottom-title">
	                    <div class="index-common-title-left bottom-left"></div>
	                    <div class="index-common-title-text bottom-text">人才主动流失绩效分布</div>
	
	                    <div class="rightSetUpBtn">
	                        <div class="rightSetUpBtnDiv rightSetUpLeft rightSetUpBtnSelect icon">
	                            <div class="rightSetUpBtnTop"></div>
	                            <div class="rightSetUpLeftShowIcon"></div>
	                            <div class="rightSetUpLeftHideIcon"></div>
	                        </div>
	                        <div class="rightSetUpBtnDiv rightSetUpRight icon">
	                            <div class="rightSetUpBtnTop"></div>
	                            <div class="rightSetUpRightShowIcon"></div>
	                            <div class="rightSetUpRightHideIcon"></div>
	                        </div>
	                    </div>
	                </div>
	                <div class="bottom-div">
	                    <div class="chart-view">
	                        <div class="col-xs-12" id="perfChart"></div>
	                    </div>
	                    <div class="table-view " id="perfGridArea">
	                        <table class="summary" id="perfTableGrid"></table>
	                    </div>
	                </div>
	            </div>
	
	            <div id="page-one-group2-4" class="col-sm-6 ct-line-col SetUpBody" view="chart">
	                <div class="index-common-title bottom-title">
	                    <div class="index-common-title-left bottom-left"></div>
	                    <div class="index-common-title-text bottom-text">人才主动流失层级分布</div>
	
	                    <div class="rightSetUpBtn">
	                        <div class="rightSetUpBtnDiv rightSetUpLeft rightSetUpBtnSelect icon">
	                            <div class="rightSetUpBtnTop"></div>
	                            <div class="rightSetUpLeftShowIcon"></div>
	                            <div class="rightSetUpLeftHideIcon"></div>
	                        </div>
	                        <div class="rightSetUpBtnDiv rightSetUpRight icon">
	                            <div class="rightSetUpBtnTop"></div>
	                            <div class="rightSetUpRightShowIcon"></div>
	                            <div class="rightSetUpRightHideIcon"></div>
	                        </div>
	                    </div>
	                </div>
	                <div class="bottom-div">
	                    <div class="chart-view" >
	                        <div class="col-xs-12" id="abilityChart"></div>
	                    </div>
	                    <div class="table-view " id="abilityGridArea">
	                        <table class="summary" id="abilityTableGrid"></table>
	                    </div>
	                </div>
	            </div>
	
	            <div id="page-one-group2-5" class="col-sm-6 ct-line-col SetUpBody" view="chart">
	                <div class="index-common-title bottom-title">
	                    <div class="index-common-title-left bottom-left"></div>
	                    <div class="index-common-title-text bottom-text">人才主动流失司龄分布</div>
	
	                    <div class="rightSetUpBtn">
	                        <div class="rightSetUpBtnDiv rightSetUpLeft rightSetUpBtnSelect icon">
	                            <div class="rightSetUpBtnTop"></div>
	                            <div class="rightSetUpLeftShowIcon"></div>
	                            <div class="rightSetUpLeftHideIcon"></div>
	                        </div>
	                        <div class="rightSetUpBtnDiv rightSetUpRight icon">
	                            <div class="rightSetUpBtnTop"></div>
	                            <div class="rightSetUpRightShowIcon"></div>
	                            <div class="rightSetUpRightHideIcon"></div>
	                        </div>
	                    </div>
	                </div>
	                <div class="bottom-div">
	                    <div class="chart-view">
	                        <div class="col-xs-12" id="ageChart"></div>
	                    </div>
	                    <div class="table-view " id="ageGridArea">
	                        <table class="summary" id="ageTableGrid"></table>
	                    </div>
	                </div>
	            </div>
            </div>
        </div>

        <div id="page-two" class="rightBodyPage">
        	<div group="true"> 
	            <div id="page-two-group1-1" class="col-sm-12 ct-line-col SetUpBody">
	                <div class="index-common-title bottom-title">
	                    <div class="index-common-title-left bottom-left"></div>
	                    <div class="index-common-title-text bottom-text">流失原因分布</div>
	                </div>
	                <div class="bottom-div bottom-div-two">
	                    <div class="row">
	                        <div class="col-md-6 col-xs-12" id="dismissReasonPie" style="height: 400px;"></div>
	                        <div class="col-md-6 col-xs-12" id="dismissReasonBar" style="height: 400px;"></div>
	                    </div>
	                </div>
	            </div>
	
	            <div id="page-two-group1-2" class="col-sm-12 ct-line-col SetUpBody">
	                <div class="index-common-title bottom-title">
	                    <div class="index-common-title-left bottom-left"></div>
	                    <div class="index-common-title-text bottom-text">流失去向分布</div>
	                </div>
	                <div class="bottom-div bottom-div-two">
	                    <div class="row" id="dismissCauseMain">
	                        <div class="col-md-6 col-xs-12" id="dismissGonePie" style="height: 420px;"></div>
	                        <div class="col-md-6 col-xs-12" id="dismissGoneBar" style="height: 420px;"></div>
	                    </div>
	                </div>
	            </div>
            </div>
        </div>

        <div id="page-three" class="rightBodyPage">
        	<div group="true"> 
	            <div id="page-three-group1-1" class="col-sm-12 ct-line-col SetUpBody" >
	                <div class="index-common-title bottom-title">
	                    <div class="index-common-title-left bottom-left"></div>
	                    <div class="index-common-title-text bottom-text">流失人员明细</div>
	                </div>
	                <div class="bottom-div bottom-div-three">
	
	                    <div class="conditionBtnListBody" id="searchBox">
	                        <div class="dis-search">
	                            <input class="dis-search-input" id="searchTxt" type="text" placeholder="请输入员工ID或姓名" >
	                            <div class="add-on dis-search-input-btn" id="searchBtn">人员搜索</div>
	                        </div>
	                        <div class="more-search"></div>
	
	                        <%--<div id="conditionBtn" class="conditionBtn" switch="true">--%>
	                            <%--<div class="condition-icon"></div>--%>
	                            <%--精简筛选条件</div>--%>
	
	                        <%--<div class="condition-body">--%>
	                            <%--<div class="condition-body-list" style="margin-bottom: 18px;">--%>
	                                <%--<div class="condition-body-list-title left">流失时间：</div>--%>
	                                <%--<input type="text" class="condition-input-text left">--%>
	                                <%--<span class="condition-text-one left">-</span>--%>
	                                <%--<input type="text" class="condition-input-text condition-input-text-too left">--%>
	                            <%--</div>--%>
	                            <%--<div class="condition-body-list">--%>
	                                <%--<div class="condition-body-list-title left">流失类型：</div>--%>
	                                <%--<div class="condition-btn condition-btn-selected">全部--%>
	                                <%--</div>--%>
	                                <%--<div class="condition-btn condition-btn-too">被动离职--%>
	                                <%--</div>--%>
	                                <%--<div class="condition-btn condition-btn-too">主动离职</div>--%>
	                                <%--<div class="condition-btn condition-btn-too">调出组织</div>--%>
	
	                            <%--</div>--%>
	                            <%--<div class="condition-body-list">--%>
	                                <%--<div class="condition-body-list-title left">职位序列：</div>--%>
	                                <%--<div class="condition-btn condition-btn-selected">全部</div>--%>
	                                <%--<div class="condition-btn condition-btn-too">管理序列</div>--%>
	                                <%--<div class="condition-btn condition-btn-too">业务序列</div>--%>
	                                <%--<div class="condition-btn condition-btn-too">专业序列</div>--%>
	                                <%--<div class="condition-btn condition-btn-too">只能序列</div>--%>
	                            <%--</div>--%>
	                            <%--<div class="condition-body-list condition-body-list-too">--%>
	                                <%--<div class="condition-body-list-title left">职位层级：</div>--%>
	                                <%--<div class="condition-btn condition-btn-selected">全部</div>--%>
	                                <%--<div class="condition-btn condition-btn-too">行业专家</div>--%>
	                                <%--<div class="condition-btn condition-btn-too">公司专家</div>--%>
	                                <%--<div class="condition-btn condition-btn-too">独立工作者</div>--%>
	                                <%--<div class="condition-btn condition-btn-too">辅助工作者</div>--%>
	                            <%--</div>--%>
	                            <%--<div class="condition-body-list condition-body-list-too">--%>
	                                <%--<div class="condition-body-list-title left">绩效：</div>--%>
	                                <%--<div class="condition-btn condition-btn-selected">全部</div>--%>
	                                <%--<div class="condition-btn condition-btn-too">5星</div>--%>
	                                <%--<div class="condition-btn condition-btn-too">4星</div>--%>
	                                <%--<div class="condition-btn condition-btn-too">3星</div>--%>
	                                <%--<div class="condition-btn condition-btn-too">2星</div>--%>
	                                <%--<div class="condition-btn condition-btn-too">1星</div>--%>
	                            <%--</div>--%>
	                        <%--</div>--%>
	
	                        <%--<div class="condition-bottom">--%>
	                            <%--<div class="condition-btn-two left">搜索</div>--%>
	                            <%--<div class="condition-btn-one left">重置</div>--%>
	                        <%--</div>--%>
	                    </div>
	
	                    <div class="table-body">
	                        <div class="clearfix"></div>
	                        <div class="col-md-12  col-xs-12" id="runOffDeailTable" >
	                            <label>&nbsp;</label>
	                            <table  id="runOffDetailGrid" ></table>
	                            <table id="runOffDetailSel"></table>
	                        </div>
	                    </div>
	                </div>
	            </div>
            </div>
        </div>
    </div>

<!--主动流失率预警设置 弹出框 begin-->
<div class="modal fade popup-modal" id="warnSettingModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-text">主动流失率预警设置</div>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            </div>

            <div class="modal-body">
                <div class="row ct-row">
                    <div class="col-sm-6 BSetUp-col">
                        <div class="BSetUp-line">
                            <div class="color-div green-color-div pull-left"></div>
                            <span class="BSetUp-text-one pull-left">正常值：</span>
                            <input id="normalDismiss" class="BSetUp-input pull-left" type="number">
                            <span class="BSetUp-text-two pull-left">%</span>
                        </div>
                    </div>
                    <div class="col-sm-6 BSetUp-col">
                        <div class="BSetUp-line">
                            <div class="color-div orange-color-div pull-left"></div>
                            <span class="BSetUp-text-one pull-left">风险值：</span>
                            <input id="riskDismiss" class="BSetUp-input pull-left" type="number">
                            <span class="BSetUp-text-two pull-left">%</span>
                        </div>
                    </div>
                    <div class="col-xs-12 BSetUp-col">
                        <div class="BSetUp-text-line">
                            绿色区域：等于或低于正常值
                        </div>
                    </div>
                    <div class="col-xs-12 BSetUp-col">
                        <div class="BSetUp-text-line">
                            黄色区域：高于正常值，等于或低于风险值
                        </div>
                    </div>
                    <div class="col-xs-12 BSetUp-col">
                        <div class="BSetUp-text-line BSetUp-text-last-line">
                            红色区域：高于风险值
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer"> 
                <div class="modal-btn success-btn initDragBtn">确定</div>
                <div class="modal-btn default-btn" data-dismiss="modal">取消</div>
            </div>
        </div>
    </div>
</div>
<!--主动流失率预警设置 弹出框 end-->

<!--主动流失率基础设置 弹出框 begin-->
<div class="modal fade popup-modal" id="baseSettingModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-text">主动流失率基础设置</div>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            </div>

            <div class="modal-body">
                <div class="row ct-row">
                    <div class="col-xs-12 BSetUp-col">
                        <div class="BSetUp-line-two BSetUp-line-three">
                            <span class="CSetUp-text-one pull-left">预警条件：</span>
                            <select class="CSetUp-select pull-left" id="notifyConfig">
                                <option value="1">红灯</option>
                                <option value="2">黄灯</option>
                                <option value="3">红黄灯</option>
                            </select>
                            <div class="CSetUp-text-two pull-left">说明：当主动流失率达到黄灯或者红灯值时则发送预警通知</div>
                        </div>
                    </div>
                    <div class="col-xs-12 BSetUp-col">
                        <div class="BSetUp-line-two BSetUp-line-three">
                            <span class="CSetUp-text-one pull-left">预警对象：</span>
                            <label class="CSetUp-text-checkbox pull-left"><input class="CSetUp-text-checkbox-input" name="YJObject" type="checkbox" value="2" /><span class="CSetUp-text-checkbox-text">架构负责人</span></label>
                            <label class="CSetUp-text-checkbox pull-left"><input class="CSetUp-text-checkbox-input" name="YJObject" type="checkbox" value="3" /><span class="CSetUp-text-checkbox-text">BP</span></label>
                            <div class="CSetUp-text-two pull-left">说明："当主动流失率达到黄灯或者红灯值时"</div>
                        </div>
                    </div>
                    <div class="col-xs-12 BSetUp-col">
                        <div class="BSetUp-line-two">
                            <span class="CSetUp-text-one pull-left">预警方式：</span>
                            <label class="CSetUp-text-checkbox pull-left"><input class="CSetUp-text-checkbox-input" name="YJMode" type="checkbox" value="1" /><span class="CSetUp-text-checkbox-text">邮件</span></label>
                            <label class="CSetUp-text-checkbox pull-left"><input class="CSetUp-text-checkbox-input" name="YJMode" type="checkbox" value="2" /><span class="CSetUp-text-checkbox-text">手机短信</span></label>
                            <div class="CSetUp-text-two pull-left">说明：当有人员离职风险异常时通知方式</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer"> 
                <div class="modal-btn success-btn initDragBtn">确定</div>
                <div class="modal-btn default-btn" data-dismiss="modal">取消</div>
            </div>
        </div>
    </div>
</div>
<!--主动流失率基础设置 弹出框 end-->

</div>

<script src="${jsRoot}lib/echarts/echarts.js"></script>
<script src="${jsRoot}biz/drivingforce/accordDismiss.js"></script>
</body>
</html>
