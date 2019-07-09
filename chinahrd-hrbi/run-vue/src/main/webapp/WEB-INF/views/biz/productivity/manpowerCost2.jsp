<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp"%>
    <title>人力成本</title>
    <link rel="stylesheet" href="${ctx}/assets/css/zTreeStyle/zTreeStyle.css"  />
    <link rel="stylesheet" href="${ctx}/assets/css/biz/productivity/manpowerCost2.css"/>
</head>
<body>
<input type="hidden" id="curdate" value="${curdate}">
<div class="manpowercost-board" id="manpowercostBoard">
    <div class="rightBody">
        <div id="page-one" class="rightBodyPage">
            <div class="row ct-row">

                <div class="col-sm-3 ct-23 ct-line-col">
                    <div class="top-div">
                        <div class="index-common-title">
                            <div class="index-common-title-left"></div>
                            <div class="index-common-title-text" title="人力成本(万元)">人力成本<span>(万元)</span></div>
                            <div id="manpowertotalTab" class="index-common-title-right">
                                <span data-id="totalbudget">上月</span>
                                <span data-id="totalaccumulative" class="select"><i class="year"></i>年累计</span>
                            </div>
                        </div>
                        <div id="manpowertotalContent" class="body-div">
                            <div class="content hide" id="totalbudget">
                                <div class="data nodata hide">暂无数据</div>
                                <div class="data hide">
                                    <div class="accord-yj-float num"></div>
                                    <div class="accord-bottom-float">
                                        <div class="rate"></div>
                                        <div>较上月</div>
                                    </div>
                                </div>
                            </div>
                            <div class="content" id="totalaccumulative">
                                <div class="data nodata hide">暂无数据</div>
                                <div class="data hide">
                                    <div class="accord-yj-float num"></div>
                                    <div class="accord-bottom-float">
                                        <div class="rate"></div>
                                        <div>较上一年</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-3 ct-23 ct-line-col">
                    <div class="top-div">
                        <div class="index-common-title">
                            <div class="index-common-title-left"></div>
                            <div class="index-common-title-text" title="人均成本(万元)">人均成本<span>(万元)</span></div>
                            <div id="manpoweravgTab" class="index-common-title-right">
                                <span data-id="avgbudget">上月</span>
                                <span data-id="avgaccumulative" class="select"><i class="year"></i>年累计</span>
                            </div>
                        </div>
                        <div id="manpoweravgContent" class="body-div">
                            <div class="content hide" id="avgbudget">
                                <div class="data nodata hide">暂无数据</div>
                                <div class="data hide">
                                    <div class="accord-yj-float num"></div>
                                    <div class="accord-bottom-float">
                                        <div class="rate"></div>
                                        <div>较上月</div>
                                    </div>
                                </div>
                            </div>
                            <div class="content" id="avgaccumulative">
                                <div class="data nodata hide">暂无数据</div>
                                <div class="data hide">
                                    <div class="accord-yj-float num"></div>
                                    <div class="accord-bottom-float">
                                        <div class="rate"></div>
                                        <div>较上一年</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-3 ct-20 ct-line-col">
                    <div class="top-div">
                        <div class="index-common-title">
                            <div class="index-common-title-left"></div>
                            <div class="index-common-title-text">人力成本执行率<img id="capitabeneDx" src="${ctx}/assets/img/base/tip.gif" data-toggle="tooltip" data-placement="bottom" data-original-title="执行率=已使用成本/预算成本"/></div>
                            <div class="index-common-title-right">
                                <span class="select"><i class="year"></i>年</span>
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

                <div class="col-sm-3 ct-34 ct-line-col">
                    <input id="quotaId" type="hidden" value="${quotaId}">
                    <div class="top-div" id="timeLine"></div>
                </div>
            </div>

            <div class="row ct-row">

                <div class="col-sm-6 ct-line-col SetUpBody">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">人力成本月度趋势<span>(<label class="year"></label>年)</span></div>
                    </div>
                    <div class="bottom-div">
                        <div class="chart-view">
                            <div class="col-xs-12 bottom-div-first hide"><span>(万元)</span></div>
                            <div class="col-xs-12 padding0 height350" id="trendByMonth" ></div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">人均成本月度趋势<span>(<label class="year"></label>年)</span></div>
                    </div>

                    <div class="bottom-div">
                        <div class="chart-view">
                            <div class="col-xs-12 bottom-div-first hide"><span>(万元)</span></div>
                            <div class="col-xs-12 padding0 height350" id="manpowerTrend"></div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">各架构人力成本<span>(<label class="year"></label>年)</span></div>
                    </div>
                    <div class="bottom-div">
                        <div class="chart-view">
                            <div class="col-xs-12 bottom-div-first hide"><span>(万元)</span></div>
                            <div class="col-xs-12 padding0 height350" id="contrastOrgChart"></div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">人力成本结构<span>(<label class="year"></label>年)</span></div>
                    </div>
                    <div class="bottom-div">
                        <div class="chart-view">
                            <div class="col-xs-12 bottom-div-first hide"><span>(万元)</span></div>
                            <div class="col-xs-12 padding0 height350" id="contrastDetailChart"></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row ct-row">
                <div class="col-sm-12 ct-line-col SetUpBody">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">人力成本趋势<span>(万元)</span></div>
                    </div>
                    <div class="bottom-div bottom-div-two">

                        <div class="col-xs-12 col-sm-4 margintop20 marginbottom20">
                            <div class="panel panel-default border0">
                                <div class="widget-header widget-header-flat topborderradius">
                                    <h4 class="bolder color555 fontsize14">人力成本占比</h4>
                                    <div class="widget-toolbar" id="manpowerCostTrends_toolbar">
                                        <span class="white pointer">上月</span>
                                        <span class="green pointer" id="manpowerCostYear"><i class="year"></i>年累计</span>
                                    </div>
                                </div>
                                <div class="widget-body minheight112 bottomborderradius" id="manpowerCostTrends">
                                    <div class="row column hide costratenodata">暂无数据</div>
                                    <div class="row column minheight112 hide">
                                        <div class="col-sm-12">
                                            <div class="currentMonth"></div>
                                        </div>
                                        <div class="col-sm-12">
                                            <div class="position-relative margintop0">
                                                <span class="lastMonth lastMonthBlue"></span>
                                            </div>
                                        </div>
                                        <div class="col-xs-12">
                                            <label class="lastLabel">较上一年</label>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="panel panel-default border0">
                                <div class="widget-header widget-header-flat topborderradius">
                                    <h4 class="bolder color555 fontsize14">人均成本预警</h4>
                                </div>
                                <div class="widget-body bottomborderradius paddingtop0 paddingbottom0" id="manpowerCostearlyWarning">
                                    <div class="row column minheight112">
                                        <div class="col-xs-4">
                                            <div class="col-sm-12 warn-icon hide"></div>
                                            <div class="col-sm-12 smile-face hide"></div>
                                        </div>
                                        <div class="col-xs-8">
                                            <div class="position-relative">
                                                <span class="average">行业均值</span>
                                                <span class="average change" id="industryAverage"></span>
                                                <span class="average">万元</span>
                                            </div>
                                        </div>
                                        <div class="col-xs-8">
                                            <span class="average change" id="averageCompare"></span>
                                            <span class="arrow-icon"></span>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-12 col-sm-8" >

                            <div class="col-xs-12 bottom-div-first hide color999">(万元)</div>
                            <div class="height350"  id="manpowerCostTrendsChars"></div>

                        </div>

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
<script src="${ctx}/assets/js/biz/productivity/manpowerCost2.js"></script>
</body>
</html>
