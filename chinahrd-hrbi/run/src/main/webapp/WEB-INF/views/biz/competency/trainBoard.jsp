<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>培训看板</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/datetime/datetimepicker.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/competency/trainBoard.css"/>
</head>
<body>
<div class="train-board" id="trainBoard">
    <div class="leftBody">
        <div class="leftListBigDiv">培训看板</div>
        <div page="page-one" class="leftListDiv selectList">培训总览</div>
        <div page="page-two" class="leftListDiv">维度分析</div>
        <div page="page-three" class="leftListDiv">培训记录</div>
    </div>

    <div class="rightBody rightBody10">
        <div id="page-one" class="rightBodyPage">
            <div class="col-sm-3 ct-22 ct-line-col">
                <div class="top-div">
                    <div class="index-common-title">
                        <div class="index-common-title-left"></div>
                        <div class="index-common-title-text"><span id="costYearTxt">2015</span>年培训费用</div>
                    </div>
                    <div class="body-div">
                        <div class="accord-yj-float">
                            <span class="accord-yj-float-value" id="costNumTxt">0</span>
                            <span class="accord-yj-float-people">万元</span>
                        </div>
                        <div class="accord-bottom-float">
                            <div class="accord-bottom-float-text">已花费预算的</div>
                            <%--<div class="accord-bottom-float-arrow accord-bottom-float-arrow-rise"></div>--%>
                            <div class="accord-bottom-float-value accord-bottom-float-value-rise" id="budgetRateTxt">0</div>
                            <div class="accord-bottom-float-people">%</div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-3 ct-22 ct-line-col">
                <div class="top-div">
                    <div class="index-common-title">
                        <div class="index-common-title-left"></div>
                        <div class="index-common-title-text"><span id="completeYearTxt">2015</span>年培训计划完成率</div>
                    </div>
                    <div class="body-div" id="keyDismissArea">
                        <div class="accord-yj-float">
                            <span class="accord-yj-float-value" id="completeRateTxt">0</span>
                            <span class="accord-yj-float-people">%</span>
                        </div>
                        <%--<div class="accord-bottom-float">--%>
                            <%--<div class="accord-bottom-float-text">较上季度</div>--%>
                            <%--<div class="accord-bottom-float-arrow accord-bottom-float-arrow-rise"></div>--%>
                            <%--<div class="accord-bottom-float-value accord-bottom-float-value-rise">0</div>--%>
                            <%--<div class="accord-bottom-float-people">人</div>--%>
                        <%--</div>--%>
                    </div>
                </div>
            </div>

            <div class="col-sm-3 ct-22 ct-line-col">
                <div class="top-div">
                    <div class="index-common-title">
                        <div class="index-common-title-left"></div>
                        <div class="index-common-title-text"><span id="coverageYearTxt">2015</span>年培训覆盖率</div>
                    </div>
                    <div class="body-div" id="perfDismissArea">
                        <div class="accord-yj-float">
                            <span class="accord-yj-float-value" id="coverageRateTxt">0</span>
                            <span class="accord-yj-float-people">%</span>
                        </div>
                        <%--<div class="accord-bottom-float">--%>
                            <%--<div class="accord-bottom-float-text">较上季度</div>--%>
                            <%--<div class="accord-bottom-float-arrow accord-bottom-float-arrow-drop"></div>--%>
                            <%--<div class="accord-bottom-float-value accord-bottom-float-value-drop">0</div>--%>
                            <%--<div class="accord-bottom-float-people">人</div>--%>
                        <%--</div>--%>
                    </div>
                </div>
            </div>

            <div class="col-sm-3 ct-34 ct-line-col">
                <input id="quotaId" type="hidden" value="${quotaId}">
                <div class="top-div" id="timeLine"></div>
            </div>

            <div class="col-sm-6 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">培训费用统计&nbsp;&nbsp; <a href="javascript:void(0);" class="anchor" data-sign="0">更多培训费用统计&gt;</a></div>
                </div>
                <div class="bottom-div clearfix">
                    <div class="chart-view">
                        <div class="col-xs-12 bottom-div-first height20"><span>&nbsp;</span></div>
                        <div class="col-xs-12" id="trainCostChart" ></div>
                    </div>
                </div>
            </div>

            <div class="col-sm-6 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">人均培训费用统计&nbsp;&nbsp;<a href="javascript:void(0);" class="anchor" data-sign="0">更多培训费用统计&gt;</a></div>
                </div>

                <div class="bottom-div clearfix">
                    <div class="chart-view">
                        <div class="col-xs-12 bottom-div-first height20"><span>&nbsp;</span></div>
                        <div class="col-xs-12" id="avgpriceCostChart"></div>
                    </div>
                </div>
            </div>

            <div class="col-sm-6 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">培训计划完成率&nbsp;&nbsp;<a href="javascript:void(0);" class="anchor" data-sign="1">更多培训实施统计&gt;</a></div>
                </div>
                <div class="bottom-div clearfix" style="height:402px;">
                    <div class="chart-view">
                        <div class="col-xs-12 bottom-div-first height20"><span>&nbsp;</span></div>
                        <div class="col-xs-12" id="completionRateChart"></div>
                    </div>
                </div>
            </div>

            <div class="col-sm-6 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">下级组织人均学时对比&nbsp;&nbsp;<a href="javascript:void(0);" class="anchor" data-sign="1">更多培训实施统计&gt;</a></div>
                </div>
                <div class="bottom-div clearfix">
                    <div class="chart-view">
                        <div class="col-xs-12 bottom-div-first height20"><span>&nbsp;</span></div>
                        <div class="col-xs-12" id="subOrganSchoolChart"></div>
                        <div class="col-xs-12 bottom-info"><span class="text-date" id="subOrganSchoolDate"></span></div>
                    </div>
                </div>
            </div>
        </div>

        <div id="page-two" class="rightBodyPage">
            <div class="col-sm-12 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">费用统计<span class="text-date" id="costStatisticsDate"></span></div>
                    <div class="index-common-title-center">
                        <div class="title-center-text"><span>培训费用占人力成本比</span><span id="proportion">0.00</span><small>%</small></div>
                        <div class="title-center-text"><span>人均培训费用</span><span id="avgCost">0.00</span><small>万元</small></div>
                    </div>
                </div>
                <div class="bottom-div bottom-div-two">
                    <div class="row ct-row">
                        <div class="col-md-6 col-xs-12 ct-col1">
                            <div class="col-xs-12 bottom-div-first">费用年度趋势图</div>
                            <div class="col-xs-12 bottom-div-second">(万元)</div>
                            <div class="col-xs-12" id="yearTrendChart"></div>
                        </div>
                        <div class="col-md-6 col-xs-12 ct-col1">
                            <div class="col-xs-12 bottom-div-first">下级组织培训费用对比<span class="text-date" id="subOrganContrastDate"></span></div>
                            <div class="col-xs-12 bottom-div-second">(万元)</div>
                            <div class="bottom-div-right">
                                <div data-toggle="totalCost" class="select">费用总额</div>
                                <div data-toggle="avgCost">人均培训</div>
                            </div>
                            <div class="col-xs-12" id="subOrganContrastChart"></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-12 ct-line-col SetUpBody" id="implementerStatistics">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">实施统计<span class="text-date" id="implementerDate"></span></div>
                    <div class="index-common-title-center">
                        <div class="title-center-text"><span>培训人次</span><span id="trainPassengers">0</span><small>人次</small></div>
                        <div class="title-center-text"><span>人均学时</span><span id="classHour">0</span><small>小时</small></div>
                    </div>
                </div>
                <div class="bottom-div bottom-div-two">
                    <div class="row ct-row">
                        <div class="col-md-6 col-xs-12 ct-col1">
                            <div class="col-xs-12 bottom-div-first">下级组织培训人次对比<span class="text-date" id="peopleCompareDate"></span></div>
                            <div class="col-xs-12 bottom-div-second">(人次)</div>
                            <div class="col-xs-12" id="peopleCompareChart"></div>
                        </div>
                        <div class="col-md-6 col-xs-12 ct-col1">
                            <div class="col-xs-12 bottom-div-first">下级组织培训覆盖率对比<span class="text-date" id="coverageContrastDate"></span></div>
                            <div class="col-xs-12" id="coverageContrastChart"></div>
                        </div>
                    </div>
                </div>
                <div class="bottom-div bottom-div-two" style="border-top: 0;">
                    <div class="row ct-row">
                        <div class="col-md-6 col-xs-12 ct-col1">
                            <div class="col-xs-12 bottom-div-first"><h5 id="trainTypeNumDate">2015年</h5>培训类型次数统计</div>
                            <div class="col-xs-12" id="trainTypeNumChart"></div>
                        </div>
                        <div class="col-md-6 col-xs-12 ct-col1">
                            <div class="col-xs-12 bottom-div-first">培训满意度年度统计</div>
                            <div class="col-xs-12" id="satisfactionChart"></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-12 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">内部讲师统计</div>
                </div>
                <div class="bottom-div bottom-div-two">
                    <div class="row">
                        <div class="col-md-6 col-xs-12">
                            <div class="col-xs-12 bottom-div-first">下级组织内部讲师统计</div>
                            <div class="col-xs-12 bottom-div-second">(人)</div>
                            <div class="col-xs-12" id="teacherChart"></div>
                        </div>
                        <div class="col-md-6 col-xs-12" style="border-left: none;">
                            <div class="col-xs-12 bottom-div-first">内部讲师清单</div>
                            <div class="col-xs-12" id="teacherTable">
                                <table class="borderless" id="teacherGrid"></table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="page-three" class="rightBodyPage">
            <div class="col-sm-12 ct-line-col SetUpBody" >
                <%--<div class="index-common-title bottom-title">--%>
                    <%--<div class="index-common-title-left bottom-left"></div>--%>
                    <%--<div class="index-common-title-text bottom-text">流失人员明细</div>--%>
                <%--</div>--%>
                <div class="bottom-div bottom-div-three">

                    <div class="col-sm-12 ct-line-col conditionBtnListBody" id="searchBox">
                        <div class="dis-search">
                            <input class="dis-search-input" id="searchTxt" type="text" placeholder="请输入员工ID或姓名" >
                            <div class="add-on dis-search-input-btn" id="searchBtn">人员搜索</div>
                        </div>
                        <div class="more-search"></div>
                        <div class="dis-search-tip">* 姓名支持模糊查询</div>
                    </div>

                    <div class="table-body" style="padding-left: 10px;">
                        <div class="clearfix"></div>
                        <div id="trainDeailTable" style="padding-top:14px;">
                            <table class="borderless" id="trainDetailGrid" style="max-height: 400px;overflow-y:auto;"></table>
                            <table id="trainDetailSel"></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 培训项目明细 弹出框 begin -->
<div class="modal fade topWindow popup-modal" id="trainProjectModal" tabindex="-1" role="dialog"
     aria-labelledby="gainOfLossModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-text" id="gainOfLossModalLabel">培训项目明细表</div>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">
                <ul class="nav nav-tabs f-white-bg" role="tablist" id="trainProjectTabs"></ul>

                <div class="tab-content">
                    <div id="tabpanel" role="tabpanel" class="col-xs-12 tab-pane active">
                        <div class="col-xs-12" id="trainTypeTable">
                            <table class="borderless" id="trainTypeGrid"></table>
                            <table id="trainTypePager"></table>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 培训项目明细 弹出框 end -->

<!--遮罩层-->
<div id="shade" class="shade"></div>
<!--遮罩层-->

<script src="${jsRoot}lib/echarts/echarts.js"></script>
<script src="${jsRoot}biz/competency/trainBoard.js"></script>
</body>
</html>
