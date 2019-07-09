<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>劳动力效能</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/select2/select2.min.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/competency/laborEfficiency.css"/>
</head>
<body>
<input type="hidden" id="minMaxTime" value="${minMaxTime }">
<input type="hidden" id="yearHiddenId" value="${curYear }">
<input type="hidden" id="monthHiddenId" value="${curMonth }">
<input type="hidden" id="yearListHiddenId" value="${yearList }">
<input type="hidden" id="pageType" value="${type }">
<div class="labor-efficiency" id="laborEfficiency">
    <div class="leftBody">
        <div class="leftListBigDiv">劳动力效能</div>
        <div page="page-one" class="leftListDiv selectList">整体分析</div>
        <div page="page-two" class="leftListDiv">出勤明细</div>
        <div page="page-three" class="leftListDiv" style="display: none;">数据导入</div>
    </div>

    <div class="rightBody">
        <div id="page-one" class="rightBodyPage">
            <div class="row ct-row">

                <div class="col-sm-3 ct-22 ct-line-col">
                    <div class="top-div">
                        <div class="index-common-title">
                            <div class="index-common-title-left"></div>
                            <div class="index-common-title-text">劳动力效能
                                <i class="icon-question-sign bigger-140" title="劳动力效能=实际出勤工时（不含加班）/应出勤工时"></i>
                                <%-- <img id="capitabeneDx" src="${ctx}/assets/img/base/tip.gif"
                                    data-toggle="tooltip" data-placement="bottom"
                                    data-original-title="劳动力效能=实际出勤工时（不含加班）/应出勤工时"/> --%>
                            </div>
                        </div>
                        <div class="body-div">
                            <div class="accord-yj-float">
                                <span class="accord-yj-float-value" id="countNum">0</span>
                                <span class="accord-yj-float-people">&nbsp;&nbsp;%</span>
                            </div>
                            <div class="accord-bottom-float">
                                <div class="accord-bottom-float-text">
                                    <span id="curYear">${curYear}</span>年
                                    <span id="curMonth">${curMonth}</span>月
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-3 ct-22 ct-line-col">
                    <div class="top-div">
                        <div class="index-common-title">
                            <div class="index-common-title-left"></div>
                            <div class="index-common-title-text">加班时长</div>
                            <div class="index-common-title-right" id="overtimeHoursToolbar">
                                <span data-id="budget" class="select">总数</span>
                                <span data-id="accumulative">人均</span>
                            </div>
                        </div>
                        <div class="body-div">
                            <div class="accord-yj-float">
                                <span class="accord-yj-float-value" id="overtimeHoursNum">0</span>
                                <span class="accord-yj-float-people">&nbsp;&nbsp;小时</span>
                            </div>
                            <div class="accord-bottom-float">
                                <div class="accord-bottom-float-text">
                                    <span id="curYear">${curYear}</span>年
                                    <span id="curMonth">${curMonth}</span>月
                                </div>
                                <div class="accord-bottom-float-text bottom-float-right">
                                    较上月
                                    <span class="bottom-text-span" id="compareHourNum">0</span>
                                    小时
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-3 ct-22 ct-line-col">
                    <div class="top-div">
                        <div class="index-common-title">
                            <div class="index-common-title-left"></div>
                            <div class="index-common-title-text">加班预警
                                <i class="icon-question-sign bigger-140" title="最近两周加班时长超过${otTime }小时"></i>
                                <%-- <img id="capitabeneDx" src="${ctx}/assets/img/base/tip.gif"
                                    data-toggle="tooltip" data-placement="bottom"
                                    data-original-title="最近两周加班时长超过10小时"/> --%>
                            </div>
                        </div>
                        <div class="body-div" id="overtimeWarningDiv" style="cursor: pointer;">
                            <div class="accord-yj-float">
                                <span class="accord-yj-float-value" id="overtimeWarningNum">0</span>
                                <span class="accord-yj-float-people">&nbsp;&nbsp;人</span>
                            </div>
                            <div class="index-yj-div-bottom">
                                <span class="index-yj-div-bottom-value pull-left" id="overtimeWarningPerson"></span>
                                <div class="index-yj-div-bottom-right pull-right"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-3 ct-34 ct-line-col">
                    <input id="quotaId" type="hidden" value="${quotaId}">
                    <div class="top-div" id="timeLine"></div>
                </div>
            </div>

            <div class="row ct-row">
                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">劳动力效能下级组织对比</div>
                        <div class="rightSetUpBtn">
                            <div class="rightSetUpBtnDiv rightSetUpLeft icon rightSetUpBtnSelect">
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
                        <div id="ratioSelect"></div>
                        <div class="chart-view">
                            <div class="col-xs-12 return-back-div">
                                <span id="returnParentOrgan" class="return-back-text">返回上级</span>
                            </div>
                            <div class="col-xs-12" id="laborRatioChart"></div>
                            <div><span class="text-date">操作提示：点击组织可查看下级组织的劳动力效能</span></div>
                        </div>
                        <div class="table-view col-xs-12" id="laborRatioArea"  style="margin-left: 4px;">
                        	<div style="height: 20px;"></div>
                            <table id="laborRatioGrid"></table>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">劳动力效能趋势</div>
                        <div class="rightSetUpBtn">
                            <div class="rightSetUpBtnDiv rightSetUpLeft rightSetUpBtnSelect">
                                <div class="rightSetUpBtnTop"></div>
                                <div class="text">环比</div>
                            </div>
                            <div class="rightSetUpBtnDiv rightSetUpRight">
                                <div class="rightSetUpBtnTop"></div>
                                <div class="text">同比</div>
                            </div>
                        </div>
                    </div>

                    <div class="bottom-div">
                        <div id="trendSelect"></div>
                        <div class="chart-view">
                            <div class="col-xs-12" id="laborEfficiencyRatio"></div>
                            <div><span class="text-date">操作提示：点击同比时间可查看组织下人员的劳动力效能</span></div>
                        </div>
                        <div class="table-view">
                            <div class="col-xs-12" id="laborEfficiencyBasis"></div>
                            <div><span class="text-date">操作提示：点击同比时间可查看组织下人员的劳动力效能</span></div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">下级组织加班时长对比</div>
                        <div class="rightSetUpBtn">
                            <div class="rightSetUpBtnDiv rightSetUpLeft rightSetUpBtnSelect">
                                <div class="rightSetUpBtnTop"></div>
                                <div class="text">人均时长</div>
                            </div>
                            <div class="rightSetUpBtnDiv rightSetUpRight">
                                <div class="rightSetUpBtnTop"></div>
                                <div class="text">总时长</div>
                            </div>
                        </div>
                    </div>
                    <div class="bottom-div">
                        <div id="overtimeSelect"></div>
                        <div class="chart-view">
                            <!-- <div id="overtimeAvgSelect"></div> -->
<!--                             <div class="col-xs-12" style="height: 30px;">单位：小时</div> -->
                            <div class="col-xs-12 return-back-div">
                                <span id="returnParentAvgOvertime" class="return-back-text">返回上级</span>
                            </div>
                            <div class="col-xs-12" id="overtimeAvgnumChart"></div>
                            <div><span class="text-date">操作提示：点击组织可查看下级组织的加班情况</span></div>
                        </div>
                        <div class="table-view">
                            <!-- <div id="overtimeTotalSelect"></div> -->
<!--                             <div class="col-xs-12" style="height: 30px;">单位：小时</div> -->
                            <div class="col-xs-12 return-back-div">
                                <span id="returnParentTotalOvertime" class="return-back-text">返回上级</span>
                            </div>
                            <div class="col-xs-12" id="overtimeTotalChart"></div>
                            <div><span class="text-date">操作提示：点击组织可查看下级组织的加班情况</span></div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">加班时长趋势</div>
                        <div class="rightSetUpBtn">
                            <div class="rightSetUpBtnDiv rightSetUpLeft rightSetUpBtnSelect">
                                <div class="rightSetUpBtnTop"></div>
                                <div class="text">人均时长</div>
                            </div>
                            <div class="rightSetUpBtnDiv rightSetUpRight">
                                <div class="rightSetUpBtnTop"></div>
                                <div class="text">总时长</div>
                            </div>
                        </div>
                    </div>
                    <div class="bottom-div">
                        <div id="overtimeHoursSelect"></div>
                        <div class="chart-view">
<!--                             <div class="col-xs-12 bottom-div-first">单位：小时</div> -->
                            <div class="col-xs-12" id="overtimeAvgHoursChart"></div>
                            <div><span class="text-date">操作提示：点击时间可查看人员的加班时长趋势</span></div>
                        </div>
                        <div class="table-view">
<!--                             <div class="col-xs-12 bottom-div-first">单位：小时</div> -->
                            <div class="col-xs-12" id="overtimeTotalHoursChart"></div>
                            <div><span class="text-date">操作提示：点击时间可查看人员的加班时长趋势</span></div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-12 ct-line-col SetUpBody">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">考勤类型分布</div>
                    </div>
                    <div class="bottom-div">
                        <div class="col-sm-6">
                            <div class="col-xs-12 chart-left" id="checkWorkTypeChart"></div>
                        </div>
                        <div class="col-sm-6">
                            <div class="right-title-first">
                                <div class="type-float-left type-select-height">时&nbsp;&nbsp;间：</div>
                                <div class="modal-style-margin">
                                    <select class="select-first type-select-height" id="year-select"></select>
                                </div>
                                <div class="modal-style-margin">
                                    <select class="select-second type-select-height" id="month-select"></select>
                                </div>
                                <div id="okBtnId" class="searchBtn btn-margin">确定</div>
                            </div>
                            <div class="right-title-second">
                                <span>统计说明：“工时”统计单位为小时</span>
                            </div>
                            <div class="content-tab">
                            	<div class="" id="checkWorkTypeArea">
		                            <table id="checkWorkTypeGrid"></table>
		                        </div>
                                <!-- <table class="table">
                                    <thead>
                                    <tr class="table-thead-tr">
                                        <th>考勤类型</th>
                                        <th>工时</th>
                                        <th>占比</th>
                                    </tr>
                                    </thead>
                                    <tbody id="checkWorkTypeGrid"></tbody>
                                </table> -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="page-two" class="rightBodyPage">
            <div class="row ct-row">
                <div class="col-sm-12 ct-line-col SetUpBody">
                    <div class="bottom-div-two">
                        <div class="col-sm-12 ct-line-col conditionBtnListBody" id="searchBox">
                            <div class="dis-search">
                                <input class="dis-search-input btn-margin" id="searchUserNameTxt" type="text"
                                       placeholder="输入员工姓名" style="float: left;">
                                <div class="dis-search-text">时&nbsp;&nbsp;间：</div>
                                <div class="dis-search-select"><select id="detailYear">
                                    <option>2015</option>
                                </select></div>
                                <div class="dis-search-select"><select id="detailMonth"></select></div>
                                <div class="add-on dis-search-input-btn" id="searchBtnId">搜索</div>
                                <div class="clear-on dis-search-input-btn" id="clearBtnId">清除</div>
                            </div>
                        </div>
                        <div class="col-sm-12 lable">
                            统计说明：“实出勤”、“应出勤”、“加班”、“年假”、“病假”及“事假”统计单位为小时
                        </div>
                        <div class="col-sm-12 tab-content">
                            <div class="col-xs-12">
                                <table id="attendanceDetailGrid"></table>
                                <div id="attendanceDetailGridPager"></div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="page-three" class="rightBodyPage">
            <div class="row ct-row">
                <div class="col-sm-12 ct-line-col SetUpBody">
                    <div class="bottom-div-three">
                        <div class="col-sm-12">
                            <div class="nav-div">
                                <div class="nav-div-text nav-text-selected" id="dataAuditing">数据审核</div>
                                <div class="nav-div-text" id="dataImport">数据导入</div>
                            </div>
                        </div>
                        <div class="col-sm-12" id="dataAuditing-view">
                            <div class="step" id="auditingState"></div>
                            <div class="right-center">
                                <a id="toAuditingHistoryView" class="index-jxmb-href">全部历史审核&gt;</a>
                            </div>
                        </div>
                        <div class="col-sm-12" id="dataImport-view" style="display: none;">
                            <div class="import-div">
                                <form id="importForm" enctype="multipart/form-data" method="post">
                                    <div class="col-sm-2 ct-col-line">
                                        考勤时间：
                                    </div>
                                    <div class="col-sm-10 ct-col-line">
                                        <select id="attendanceTime" name="attendanceTime">
                                            <!-- <option selected="selected" value="6">2016年6月</option>
                                            <option value="5">2016年5月</option>
                                            <option value="4">2016年4月</option> -->
                                        </select>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="line"></div>
                                    <div class="col-sm-2 ct-col-line">
                                        导入数据：
                                    </div>
                                    <div class="col-sm-10 ct-col-line">
                                        <input type="file" value="选择" name="attendanceFile" id="attendanceFile">
                                        <div class="explain-style">
                                            <div class="hyperlink-style">
                                                下载《员工考勤数据》导入模板
                                            </div>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="line"></div>
                                    <div class="col-sm-2 ct-col-line">
                                        审核人：
                                    </div>
                                    <div class="col-sm-10 ct-col-line">
                                        <!-- <input type="text" id="empKey" name="empKey"> -->
                                        <div class="row padding10">
                                            <div class="col-xs-9 col-sm-10"><select id="txtKey"></select></div>
                                        </div>
                                        <div class="explain-style">
                                            说明：导入后将进入审核，审核成功后数据才会发布
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="line"></div>
                                    <div class="col-sm-12 ct-col-line">
                                        <button type="button" class="btn btn-primary btn-style" id="btn-form">
                                            <span id="btnImportText">导&nbsp;&nbsp;入</span>
                                            <span id="btnNowImportText" class="display-none">正在导入...</span>
                                        </button>
                                    </div>
                                </form>
                                <div class="clearfix"></div>
                                <div class="bottom-state">
                                    <div class="div-three-title">导入状态:</div>
                                    <div id="importState" class="bottom-div-four">
                                        <div id="templateInfo" class="col-sm-12 ct-col-line display-none">
                                            <span class="span-font-warning">导入模板有误！</span>
                                            点击
                                            <span id="personType" class="hyperlink-style">下载《员工考勤数据》导入模板</span>
                                        </div>
                                        <div id="importError" class="col-sm-12 ct-col-line display-none">
                                            <span class="span-font-warning">数据导入失败，共检测到</span>
                                            <span class="span-font-warning" id="importErrorNum"></span>
                                            <span class="span-font-warning">条错误：</span>
                                            <div id="formalErrorTable">
                                                <table class="table">
                                                    <thead>
                                                    <tr class="table-thead-tr">
                                                        <th>序号</th>
                                                        <th>错误位置</th>
                                                        <th>错误信息</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody id="formalErrorGrid"></tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <div id="importInfo" class="col-sm-12 ct-col-line display-none">
                                            <div style="font-size: 15px;">
                                                <!-- <span id="import-info-year">2016</span>
                                                年
                                                <span id="import-info-month">05</span>
                                                月 -->
                                                已存在<span id="importYearMonth"></span>项目信息数据，系统将执行以下操作：
                                            </div>
                                            <div id="updateTipsTable">
                                                <table class="table">
                                                    <thead>
                                                    <tr class="table-thead-tr">
                                                        <th>序号</th>
                                                        <th>帐号</th>
                                                        <th>时间</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody id="updateTipsGrid"></tbody>
                                                </table>
                                            </div>
                                            <div class="condition-bottom" id="continueImportBtn">
                                                <div class="float-left">如果你已确认以上信息，请点击&nbsp;&nbsp;</div>
                                                <button id="continueImport" class="continue-btn">继续导入</button>
                                                <div class="float-left">&nbsp;&nbsp;或&nbsp;&nbsp;</div>
                                                <button id="cancelImport" class="continue-btn">取消导入</button>
                                            </div>
                                        </div>
                                        <div id="importSuccess"
                                             class="col-sm-12 ct-col-line conditionBtnListBody display-none">
                                            <span>成功导入，请等待审核</span>
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

<!-- 劳动力效能明细 弹出框 begin -->
<div class="modal fade topWindow popup-modal" id="laborEfficiencyModal" tabindex="-1" role="dialog"
     aria-labelledby="laborEfficiencyModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-text">劳动力效能</div>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">
                <div class="condition-body" style="margin-left: 20px;">
                    <div class="condition-body-text left">组织架构：</div>
                    <div class="modal-title-tree left" style="width: 120px; background-color: white;">
                        <ul id="organTree" style="background-color: white;"></ul>
                    </div>
                    <!-- <div><select class="condition-input-text left" id="organTree"></select></div> -->
                    <div class="condition-body-text left">时间范围：</div>
                    <div><select class="condition-input-text left" id="beginYear"></select></div>
                    <div><select class="condition-input-text left" id="beginMonth"></select></div>
                    <span class="left">-</span>
                    <div><select class="condition-input-text-too left" id="endYear"></select></div>
                    <div><select class="condition-input-text-too left" id="endMonth"></select></div>
                    <div id="searchBtn" class="searchBtn" style="float: left; margin-left: 55px;">确定</div>
                </div>
                <div style="clear: left;"></div>
                <div class="warning-div">
                    <span class="text-date left" style="margin-left: 20px;">统计说明：“实出勤”、“应出勤”统计单位为小时</span>
                    <div class="time-warning-div icon-warning-sign left">
                    	<span id="timeWarning" class="time-warning"></span>
                    </div>
                </div>
                <div class="clearfix"></div>
                <div class="tab-content tab-content_height">
                    <div id="tabpanel" role="tabpanel" class="tab-pane active">
                        <div class="col-xs-12" id="laborEfficiencyTable">
                            <table class="borderless" id="laborEfficiencyGrid"></table>
                            <div id="laborEfficiencySel"></div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 劳动力效能明细 弹出框 end -->

<!-- 加班预警 弹出框 begin -->
<div class="modal fade topWindow popup-modal" id="overtimeWarningDetailModal"
     tabindex="-1" role="dialog" aria-labelledby="overtimeWarningDetailModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-text" id="overtimeWarningDetailModalLabel">加班预警</div>
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body" id="overtimeWarning_body">
                <div class="modal-title">
                    <div class="modal-title-first">组织架构：</div>
                    <div class="modal-title-tree">
                        <ul id="overtimeOrganTree"></ul>
                    </div>
                </div>
                <div class="modal-title-point">
                    （操作提示：点击姓名查看加班趋势）
                </div>
                <div class="tab-content" id="overtimeWarningScroll">
                    <div role="tabpanel" class="tab-pane active" id="overtimeWarningTab">
                        <div class="col-xs-12" id="overtimeWarningTable">
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <br>
            </div>
            <div class="modal-body" id="overtimeWarningPersonInfo_body" style="display: none;">
			    <div class="row base-info">
			        <div class="cursor_div" id="return_warn">
			        	<i class="pre_img"></i>
			            <span class="span_text">返回</span>
			        </div>
			        <div class="col-xs-12 text_center">
			        	<span id="empName"></span>
						近两周加班：
						<span id="overtimeCountHours">0</span>
						小时
			        </div>
			    </div>
			    <div class="row">
			        <div class="col-xs-12" id="overtimeChart">
			        </div>
			    </div>
			</div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<!-- 加班预警 弹出框 end -->
<!-- 加班预警弹出框中点击人员头像弹窗 began -->
<!-- <div class="row tooltipModal" id="overtimeWarningPersonInfo"> -->
<!--     <div class="row base-info"> -->
<!--         <div class="col-xs-12"> -->
<!--             <span style="font-weight: bold;">近两周加班趋势</span> -->
<!--         </div> -->
<!--         <div class="col-xs-12 text_center"> -->
<!--         	<span id="empName"></span> -->
<!-- 			近两周加班： -->
<!-- 			<span id="overtimeCountHours">0</span> -->
<!-- 			小时 -->
<!--         </div> -->
<!--     </div> -->
<!--     <div class="row"> -->
<!--         <div class="col-xs-12 tooltipModal-chart" id="overtimeChart"> -->
<!--         </div> -->
<!--     </div> -->
<!-- </div> -->
<!-- 加班预警弹出框中点击人员头像弹窗 end -->

<!-- 加班情况 弹出框 begin -->
<div class="modal fade topWindow popup-modal" id="overtimeConditionModal"
     tabindex="-1" role="dialog" aria-labelledby="overtimeConditionModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-text" id="overtimeConditionModalLabel">加班情况</div>
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body" id="overtimeConditionTable_body">
                <div class="modal-title" style="display: inline-block;">
                    <div class="modal-title-first">组织架构：</div>
                    <div class="modal-title-tree modal-style-margin">
                        <ul id="overtimeConditionTree"></ul>
                    </div>
                    <div class="modal-title-text">时&nbsp;&nbsp;间：</div>
                    <div class="modal-style-margin">
                        <select class="select-first select-height" id="overtimeConditionYearSelect"></select>
                    </div>
                    <div class="modal-style-margin">
                        <select class="select-second select-height" id="overtimeConditionMonthSelect"></select>
                    </div>
                    <div id="overtimeConditionOkBtnId" class="okBtn btn-margin">确定</div>
                </div>
                <div class="modal-title-point">
                    （操作提示：点击姓名查看加班趋势）
                </div>
                <div class="tab-content" id="overtimeConditionScroll">
                    <div role="tabpanel" class="tab-pane active" id="overtimeConditionTab">
                        <div class="col-xs-12" id="overtimeConditionTable">
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <br>
            </div>
            <div class="modal-body" id="overtimeConditionPersonInfo" style="display: none;">
			    <div class="row base-info">
			        <div class="cursor_div" id="return_cond">
			        	<i class="pre_img"></i>
			            <span class="span_text">返回</span>
			        </div>
			        <div class="col-xs-12 text_center">
			        	<span id="empName_con"></span>
						加班：
						<span id="overtimeConditionCountHours">0</span>
						小时
			        </div>
			        <div class="col-xs-12">
			            加班时长&nbsp;&nbsp;&nbsp;&nbsp;
			            合计：<span id="overtimeConditionCountHours">0</span>
			            小时
			        </div>
			    </div>
			    <div class="row">
			        <div class="col-xs-12 tooltipModal-chart" id="overtimeConditionChart">
			        </div>
			    </div>
			</div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<!-- 加班情况 弹出框 end -->
<!-- 加班情况弹出框中点击人员头像弹窗 began -->
<!-- <div class="row tooltipModal" id="overtimeConditionPersonInfo"> -->
<!--     <div class="row base-info"> -->
<!--         <div class="col-xs-12"> -->
<!--             <span style="font-weight: bold;">加班趋势</span> -->
<!--         </div> -->
<!--         <div class="col-xs-12"> -->
<!--             加班时长&nbsp;&nbsp;&nbsp;&nbsp; -->
<!--             合计：<span id="overtimeConditionCountHours">0</span> -->
<!--             小时 -->
<!--         </div> -->
<!--     </div> -->
<!--     <div class="row"> -->
<!--         <div class="col-xs-12 tooltipModal-chart" id="overtimeConditionChart"> -->
<!--         </div> -->
<!--     </div> -->
<!-- </div> -->
<!-- 加班情况弹出框中点击人员头像弹窗 end -->

<!-- 个人出勤明细 弹出框 begin -->
<div class="modal fade topWindow popup-modal" id="onePersonDetailModal"
     tabindex="-1" role="dialog" aria-labelledby="onePersonDetailModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-text" id="onePersonDetailModalLabel">个人出勤明细</div>
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
                        <select class="select-first select-height" id="onePersonDetailYearSelect"></select>
                    </div>
                    <div class="modal-style-margin">
                        <select class="select-second select-height" id="onePersonDetailMonthSelect"></select>
                    </div>
                    <div id="onePersonDetailOkBtnId" class="okBtn btn-margin">确定</div>
                </div>
                <div class="modal-title-point">
                    统计说明：“实出勤”、“应出勤”、“加班”、“年假”、“婚假”、“事假”、“病假”等统计单位为小时
                </div>
                <div class="tab-content y-hide">
                    <div role="tabpanel" class="tab-pane active" id="onePersonDetailTab">
                        <table id="onePersonDetailGrid"></table>
                        <div id="onePersonDetailGridPager"></div>
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

<!-- 数据导入 上传文件大小验证弹窗 began -->
<div class="modal fade popup-modal" id="importDataTemplate" tabindex="-1" role="dialog"
     aria-labelledby="importDataTemplateLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title" id="importDataTemplateLabel">警告</div>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">
                <p id="importFileSizeError" class="modal-body-p"></p>
            </div>
        </div>
    </div>
</div>
<!-- 数据导入 上传文件大小验证弹窗 end -->

<!--遮罩层-->
<div id="shade" class="shade"></div>
<!--遮罩层-->

<script src="${jsRoot}lib/echarts/echarts.js"></script>
<script src="${jsRoot}biz/competency/laborEfficiency.js"></script>
</body>
</html>
