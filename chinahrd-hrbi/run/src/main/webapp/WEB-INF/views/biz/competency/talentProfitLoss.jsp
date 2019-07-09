<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>人才损益</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/datetime/datetimepicker.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/competency/talentProfitLoss.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/competency/talentProfitLoss_pieGrid.css"/>
    <%--<link rel="stylesheet" href="${ctx}/assets/css/global.css"/>--%>
</head>
<body>
<div class="talentProfitLoss">
    <div class="rightBody rightBody10">
        <div class="col-sm-3 ct-22 ct-line-col">
            <div class="top-div">
                <div class="index-common-title">
                    <div class="index-common-title-left"></div>
                    <div class="index-common-title-text">人才损益值</div>
                    <div class="index-common-title-right"
                         id="talentProfitLossVal_toolbar">
                        <span data-id="budget" class="select">本月</span> <span
                            data-id="accumulative">本年</span>
                    </div>
                </div>
                <div class="body-div">
                    <div class="accord-yj-float">
                        <span class="accord-yj-float-value" id="profitLossNum">0</span>
                    </div>
                    <div class="accord-bottom-float">
                        <div class="accord-bottom-float-text">
                            <span>人才损益值 = 流入人数 - 流出人数</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-sm-3 ct-22 ct-line-col">
            <div class="top-div">
                <div class="index-common-title">
                    <div class="index-common-title-left"></div>
                    <div class="index-common-title-text">流入统计</div>
                    <div class="index-common-title-right" id="inflowCount_toolbar">
                        <span data-id="budget" class="select">本月</span> <span
                            data-id="accumulative">本年</span>
                    </div>
                </div>
                <div class="body-div" id="inflowCountDetail">
                    <div class="accord-yj-float cursorPointer">
                        <span class="accord-yj-float-value" id="inflowCountNum">0</span>
                        <span class="accord-yj-float-people">人</span>
                    </div>
                </div>
                <div class="index-yj-div-bottom">
                    <div class="index-yj-div-bottom-value pull-left" id="inflowPersonText">无</div>
                    <div class="index-yj-div-bottom-right pull-right"></div>
                </div>
            </div>
        </div>

        <div class="col-sm-3 ct-22 ct-line-col">
            <div class="top-div">
                <div class="index-common-title">
                    <div class="index-common-title-left"></div>
                    <div class="index-common-title-text">流出统计</div>
                    <div class="index-common-title-right" id="outflowCount_toolbar">
                        <span data-id="budget" class="select">本月</span> <span
                            data-id="accumulative">本年</span>
                    </div>
                </div>
                <div class="body-div" id="outflowCountDetail">
                    <div class="accord-yj-float cursorPointer">
                        <span class="accord-yj-float-value" id="outflowCountNum">0</span>
                        <span class="accord-yj-float-people">人</span>
                    </div>
                </div>
                <div class="index-yj-div-bottom">
                    <div class="index-yj-div-bottom-value pull-left" id="outflowPersonText">无</div>
                    <div class="index-yj-div-bottom-right pull-right"></div>
                </div>
            </div>
        </div>

        <div class="col-sm-3 ct-34 ct-line-col">
            <input id="quotaId" type="hidden" value="${quotaId}">
            <div class="top-div" id="timeLine"></div>
        </div>


        <div class="col-sm-12 ct-line-col SetUpBody" view="chart">
            <div class="index-common-title bottom-title">
                <div class="index-common-title-left bottom-left"></div>
                <div class="index-common-title-text bottom-text">
                    人员分布 <span class="text-date">（操作提示：点击地图各省右边显示该省的人员分布）</span>
                </div>
            </div>
            <div class="bottom-div">
                <div class="chart-view">
                    <div class="col-xs-12 bottom-div-one">
                        <div id="populationTimecrowd"></div>
                    </div>
                    <div class="col-xs-12">
                        <div class="col-xs-6" id="populationMapChart"
                             style="height: 350px;"></div>
                        <div class="col-xs-6" id="populationPieChart"
                             style="height: 350px;"></div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
            <div class="index-common-title bottom-title">
                <div class="index-common-title-left bottom-left"></div>
                <div class="index-common-title-text bottom-text">人才损益环比</div>
            </div>
            <div class="bottom-div">
                <div class="chart-view">
                    <div class="col-xs-12 bottom-div-one">
                        <span id="ringTimecrowd"></span> <br/> <span
                            class="bottom-div-text">损益值</span>
                    </div>
                    <div id="talentProfitLossRingChart" class="col-xs-12"></div>
                </div>
            </div>
        </div>

        <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
            <div class="index-common-title bottom-title">
                <div class="index-common-title-left bottom-left"></div>
                <div class="index-common-title-text bottom-text">人才损益同比</div>
            </div>
            <div class="bottom-div">
                <div class="chart-view">
                    <div class="col-xs-12 bottom-div-one">
                        <span id="sameTimecrowd"></span> <br/>
                        <span class="bottom-div-text pull-left">损益值</span>
                        <label></label>
                        <div class="chart-legend">
                            <div class="old-legend pull-left"></div>
                            <div class="left text" id="oldLegend"></div>
                            <div class="cur-legend pull-left"></div>
                            <div class="left text" id="curLegend"></div>
                        </div>
                    </div>
                    <div id="talentProfitLossSameChart" class="col-xs-12"></div>
                </div>
            </div>
        </div>

        <div class="col-sm-12 ct-line-col SetUpBody" view="chart"
             id="profitLossChangeDiv">
            <input type="hidden" value="${startDate}" id="changeStartDate"/>
            <input type="hidden" value="${endDate}" id="changeEndDate"/>
            <div class="index-common-title bottom-title">
                <div class="index-common-title-left bottom-left"></div>
                <div class="index-common-title-text bottom-text">异动统计</div>
            </div>
            <div class="bottom-div-change">
                <div class="table-view">
                    <div class="col-xs-12 bottom-div-count">
                        <div class="count-title-first pull-left">统计时间：</div>
                        <div class="title-float date-input">
                            <div class="input-group date startDate">
                                <input type="text" class="form-control form_datetime"
                                       readonly="readonly" value="${startDate}"> <span
                                    class="input-group-addon"><span
                                    class="glyphicon glyphicon-calendar icon-calendar"></span></span>
                            </div>
                        </div>
                        <div class="title-float">&nbsp;—&nbsp;</div>
                        <div class="title-float date-input">
                            <div class="input-group date endDate">
                                <input type="text" class="form-control form_datetime"
                                       readonly="readonly" value="${endDate}"> <span class="input-group-addon"><span
                                    class="glyphicon glyphicon-calendar icon-calendar"></span></span>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 bottom-div-count"
                         id="profitLossChangePerson">
                        <span class="count-title-first title-float">人员类别：</span>
                        <ul>
                            <li class="left div-btn-style li-full" value="p0">全部</li>
                            <li class="left li-line-style">&nbsp;</li>
                            <li class="left div-btn-style" value="p1">非管理人员</li>
                            <li class="left div-btn-style" value="p2">管理人员</li>
                        </ul>
                    </div>
                    <div class="col-xs-12 bottom-div-count"
                         id="profitLossChangePersonChild">
                        <span class="count-title-first title-float">统计人群：</span>
                    </div>
                    <div class="col-xs-12 bottom-div-count btn-group-1" id="btnGroups">
                        <div class="div-btns">
                            <div class="ok-btn div-btn-style pull-left" id="changeOk">确认</div>
                            <div class="clear-btn div-btn-style pull-left" id="changeClear">清除</div>
                        </div>
                    </div>
                    <div class="col-xs-12 bodyRowHeight" id="positionSequenceChart">
                        <div class="grid">
                            <input type="hidden" id="initFormatDate" value="${date}">
                            <input type="hidden" id="initDate" value="${date}">
                            <input type="hidden" id="mainSeqId" value="${fn:escapeXml(mainSeqId)}">
                            <input type="hidden" id="parentType" value="p0">
                            <input type="hidden" id="childType" value="">
                            <input type="hidden" id="uniqueId" value="">
                            <input type="hidden" id="deptName" value="">
                            <div class="row column">
                                <div class=" col-sm-12 col-xs-24">
                                    <!-- left -->
                                    <div class="span10" id="seqchart">
                                        <div class="left-chart" id="seq"></div>
                                    </div>
                                    <div class="span12">
                                        <div id="seqtable"></div>
                                    </div>
                                    <!-- left -->
                                    <!-- right -->
                                    <div class="span16 detailseq_area">
                                            <span class="span-first">
                                                <a class="seqdetailclose" id="titleLvClose"
                                                   href="javascript:void(0);"></a>
                                            </span>
                                        <div class="seqdetail">
                                            <div class="childseq">
                                                <div class="childseq_con">
                                                    <div class="span12">
                                                        <div class="title">
                                                            <span>序列分布</span>
                                                        </div>
                                                        <div class="child-right-chart" id="sequenceBar"></div>
                                                    </div>
                                                    <div class="span12">
                                                        <div class="title">
                                                            <span>职级分布</span>
                                                        </div>
                                                        <div class="child-right-chart" id="abilityBar"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="clearfix"></div>
                                            <div class="childseq_con" id="subSeq">
                                                <div class="span24">
                                                    <div class="title">
                                                        <span><span id="childseqText"></span>名单</span>
                                                    </div>
                                                    <div id="entryDiv" class="child-right-grid right-tab">
                                                        <table class="borderless" id="entryGrid"></table>
                                                        <div id="entryGridPager"></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                    <!-- right -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 异动统计 弹出框 begin -->
    <div class="modal fade topWindow popup-modal"
         id="inflowOutflowDetailModal" tabindex="-1" role="dialog"
         aria-labelledby="inflowOutflowDetailModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-header-text" id="inflowOutflowDetailModalLabel">异动统计</div>
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <ul class="nav nav-tabs f-white-bg" role="tablist"
                        id="inflowOutflowDetailTabs">
                        <li role="presentation" class="active"><a href="#inflowTab"
                                                                  aria-controls="inflowTab" role="tab"
                                                                  data-toggle="tab">流入&nbsp;<span
                                id="inflowNum">0</span></a></li>
                        <li role="presentation"><a href="#outflowTab"
                                                   aria-controls="outflowTab" role="tab" data-toggle="tab">
                            流出&nbsp;<span id="outflowNum">0</span>
                        </a></li>
                    </ul>
                    <br/>

                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane active" id="inflowTab">
                            <div class="col-xs-12">
                                <table class="btn-groups" id="inflowBtnGroups">
                                </table>
                            </div>
                            <br/>
                            <div class="col-xs-12 tab-div" id="inflowTable">
                                <table class="borderless" id="inflowGrid"></table>
                                <div id="inflowGridPager"></div>
                            </div>
                        </div>

                        <div role="tabpanel" class="tab-pane" id="outflowTab">
                            <div class="col-xs-12">
                                <table class="btn-groups" id="outflowBtnGroups">
                                </table>
                            </div>
                            <br/>
                            <div class="col-xs-12 tab-div" id="outflowTable">
                                <table class="borderless" id="outflowGrid"></table>
                                <div id="outflowGridPager"></div>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal -->
    </div>
    <!-- 异动统计 弹出框 end -->
    <!--遮罩层 begin-->
    <div class="shade"></div>
    <!--遮罩层 end-->
</div>

<script src="${jsRoot}lib/echarts/echarts.js"></script>
<script src="${jsRoot}biz/competency/talentProfitLoss_pieGrid.js" charset="utf-8"></script>
<script src="${jsRoot}biz/competency/talentProfitLoss.js"></script>
</body>
</html>
