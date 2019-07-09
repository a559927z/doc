<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>人力成本</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/productivity/manpowerCost.css"/>
</head>
<body>
<input type="hidden" id="curdate" value="${curdate}">
<div class="manpowercost-board" id="manpowercostBoard">
    <div class="rightBody rightBody10">
        <div id="page-one" class="rightBodyPage">
            <div class="col-sm-3 ct-line-col">
                <div class="top-div">
                    <div class="index-common-title">
                        <div class="index-common-title-left"></div>
                        <div class="index-common-title-text">人力成本</div>
                        <div id="manpowertotalTab" class="index-common-title-right">
                            <span data-id="totalbudget">上月</span>
                            <span data-id="totalaccumulative" class="select"><span class="year"></span>年累计</span>
                        </div>
                    </div>
                    <div id="manpowertotalContent" class="body-div">
                        <div class="content hide" id="totalbudget">
                            <div class="data nodata hide">暂无数据</div>
                            <div class="data hide">
                                <div class="accord-yj-float">
                                    <span class="accord-yj-float-value">0</span>
                                    <span class="accord-yj-float-people">万</span>
                                </div>
                                <div class="accord-bottom-float">
                                    <div class="accord-bottom-float-text">较上月</div>
                                    <div class="accord-bottom-float-arrow"></div>
                                    <div class="accord-bottom-float-value">0</div>
                                    <div class="accord-bottom-float-people">%</div>
                                </div>
                            </div>
                        </div>
                        <div class="content" id="totalaccumulative">
                            <div class="data nodata hide">暂无数据</div>
                            <div class="data hide">
                                <div class="accord-yj-float">
                                    <span class="accord-yj-float-value">0</span>
                                    <span class="accord-yj-float-people">万</span>
                                </div>
                                <div class="accord-bottom-float">
                                    <div class="accord-bottom-float-text">较上一年</div>
                                    <div class="accord-bottom-float-arrow"></div>
                                    <div class="accord-bottom-float-value">0</div>
                                    <div class="accord-bottom-float-people">%</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-3 ct-line-col">
                <div class="top-div">
                    <div class="index-common-title">
                        <div class="index-common-title-left"></div>
                        <div class="index-common-title-text">人均成本</div>
                        <div id="manpoweravgTab" class="index-common-title-right">
                            <span data-id="avgbudget">上月</span>
                            <span data-id="avgaccumulative" class="select"><span class="year"></span>年累计</span>
                        </div>
                    </div>
                    <div id="manpoweravgContent" class="body-div">
                        <div class="content hide" id="avgbudget">
                            <div class="data nodata hide">暂无数据</div>
                            <div class="data hide">
                                <div class="accord-yj-float">
                                    <span class="accord-yj-float-value">0</span>
                                    <span class="accord-yj-float-people">万</span>
                                </div>
                                <div class="accord-bottom-float">
                                    <div class="accord-bottom-float-text">较上月</div>
                                    <div class="accord-bottom-float-arrow"></div>
                                    <div class="accord-bottom-float-value">0</div>
                                    <div class="accord-bottom-float-people">%</div>
                                </div>
                            </div>
                        </div>
                        <div class="content" id="avgaccumulative">
                            <div class="data nodata hide">暂无数据</div>
                            <div class="data hide">
                                <div class="accord-yj-float">
                                    <span class="accord-yj-float-value">0</span>
                                    <span class="accord-yj-float-people">万</span>
                                </div>
                                <div class="accord-bottom-float">
                                    <div class="accord-bottom-float-text">较上一年</div>
                                    <div class="accord-bottom-float-arrow"></div>
                                    <div class="accord-bottom-float-value">0</div>
                                    <div class="accord-bottom-float-people">%</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-3 ct-line-col">
                <div class="top-div">
                    <div class="index-common-title">
                        <div class="index-common-title-left"></div>
                        <div class="index-common-title-text">人力成本执行率<img id="capitabeneDx"
                                                                         src="${ctx}/assets/img/base/tip.gif"
                                                                         data-toggle="tooltip"
                                                                         data-placement="bottom"
                                                                         data-original-title="执行率=已使用成本/预算成本"/>
                        </div>
                        <div class="index-common-title-right">
                            <span class="select"><span class="year"></span>年</span>
                        </div>
                    </div>
                    <div class="body-div" id="salaryrateContent">
                        <div class="data nodata hide">暂无数据</div>
                        <div class="data hide">
                            <div class="padding10 margintop60">
                                <div class="barline" id="probar">
                                    <div id="budgetValue" class="barLineValue"></div>
                                    <div id="percent"></div>
                                    <div id="line" w="0"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-3 ct-line-col">
                <input id="quotaId" type="hidden" value="${quotaId}">
                <div class="top-div" id="timeLine"></div>
            </div>

            <div class="col-sm-6 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">人力成本月度趋势<span>(<label
                            class="year"></label>年)</span></div>
                </div>
                <div class="bottom-div">
                    <div class="chart-view">
                        <div class="col-xs-12 bottom-div-first hide"><span>(万元)</span></div>
                        <div class="col-xs-12 padding0 height320" id="trendByMonth"></div>
                    </div>
                </div>
            </div>

            <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">人均成本月度趋势<span>(<label
                            class="year"></label>年)</span></div>
                </div>

                <div class="bottom-div">
                    <div class="chart-view">
                        <div class="col-xs-12 bottom-div-first hide"><span>(万元)</span></div>
                        <div class="col-xs-12 padding0 height320" id="manpowerTrend"></div>
                    </div>
                </div>
            </div>

            <div class="col-sm-6 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">各架构人力成本<span>(<label
                            class="year"></label>年)</span></div>
                </div>
                <div class="bottom-div">
                    <div class="chart-view">
                        <div class="col-xs-12 bottom-div-first hide"><span>(万元)</span></div>
                        <div class="col-xs-12 padding0 height320" id="contrastOrgChart"></div>
                    </div>
                </div>
            </div>

            <div class="col-sm-6 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">人力成本结构<span>(<label
                            class="year"></label>年)</span></div>
                </div>
                <div class="bottom-div">
                    <div class="chart-view">
                        <div class="col-xs-12 bottom-div-first hide"><span>(万元)</span></div>
                        <div class="col-xs-12 padding0 height320" id="contrastDetailChart"></div>
                    </div>
                </div>
            </div>

            <div class="col-sm-12 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">人力成本趋势</div>
                </div>
                <div class="bottom-div height380">
                    <div class="col-xs-12 col-sm-4 ct-col2">

                        <div class="top-div">
                            <div class="index-common-title">
                                <div class="index-common-title-left"></div>
                                <div class="index-common-title-text">人力成本占比</div>
                                <div id="manpowerCostTrends_toolbar" class="index-common-title-right">
                                    <span data-id="costTrendsMonth">上月</span>
                                    <span data-id="costTrendsYear" class="select"><span id="manpowerCostYear" class="year"></span>年累计</span>
                                </div>
                            </div>
                            <div id="manpowerCostTrends" class="body-div">
                                <div class="content hide" id="costTrendsMonth">
                                    <div class="data nodata hide">暂无数据</div>
                                    <div class="data hide">
                                        <div class="accord-yj-float">
                                            <span class="accord-yj-float-value">0</span>
                                            <span class="accord-yj-float-people">%</span>
                                        </div>
                                        <div class="accord-bottom-float">
                                            <div class="accord-bottom-float-text">较上月</div>
                                            <div class="accord-bottom-float-arrow"></div>
                                            <div class="accord-bottom-float-value">0</div>
                                            <div class="accord-bottom-float-people">%</div>
                                        </div>
                                    </div>
                                </div>
                                <div class="content" id="costTrendsYear">
                                    <div class="data nodata hide">暂无数据</div>
                                    <div class="data hide">
                                        <div class="accord-yj-float">
                                            <span class="accord-yj-float-value">0</span>
                                            <span class="accord-yj-float-people">%</span>
                                        </div>
                                        <div class="accord-bottom-float">
                                            <div class="accord-bottom-float-text">较上一年</div>
                                            <div class="accord-bottom-float-arrow"></div>
                                            <div class="accord-bottom-float-value">0</div>
                                            <div class="accord-bottom-float-people">%</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="top-div" style="margin-top: 20px;">
                            <div class="index-common-title">
                                <div class="index-common-title-left"></div>
                                <div class="index-common-title-text">人均成本预警</div>
                            </div>
                            <div id="manpowerCostearlyWarning" class="body-div">
                                <div class="col-xs-4">
                                    <div class="col-sm-12 warn-icon hide"></div>
                                    <div class="col-sm-12 smile-face hide"></div>
                                </div>
                                <div class="col-xs-8 ct-col1">
                                    <div class="position-relative">
                                        <span class="average">行业均值</span>
                                        <span class="average change" id="industryAverage"></span>
                                        <span class="average">万元</span>
                                    </div>
                                </div>
                                <div class="col-xs-8 ct-col1">
                                    <span class="average change" id="averageCompare"></span>
                                    <span class="arrow-icon"></span>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-8">
                        <div class="col-xs-12 bottom-div-first hide color999">(万元)</div>
                        <div class="height350" id="manpowerCostTrendsChars"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--遮罩层 begin-->
<div class="shade"></div>
<!--遮罩层 end-->

<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/js/biz/productivity/manpowerCost.js"></script>
</body>
</html>
