<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>人力结构</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/datetime/datetimepicker.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/drivingforce/empSatisfaction.css"/>

    <link rel="stylesheet" media="screen" href="${ctx}/assets/css/jqueryui/jquery-ui.min.css" />
</head>
<body>
<div class="empSatisfaction">
    <div class="leftBody">
        <div class="leftListBigDiv">员工满意度</div>
        <div page="page-one" class="leftListDiv selectList">敬业度分析</div>
        <div page="page-two" class="leftListDiv">满意度分析</div>
    </div>

    <div class="rightBody rightBody10">
        <div id="page-one" class="rightBodyPage">
            <div class="col-sm-5 ct-33 ct-line-col">
                <div class="top-div">
                    <div class="index-common-title">
                        <div class="index-common-title-left"></div>
                        <div class="index-common-title-text"><span id="dedicateyear"></span>年敬业度得分</div>
                    </div>
                    <div class="body-div">
                        <div class="scoreloadingmsg">数据读取中...</div>
                        <div class="raterow hide paddingtop35 paddingleft12 paddingright12 fontsize13">
                            <div id="dedicaterate" class="col-xs-6 text-left fontsize28"></div>
                            <div class="col-xs-6 text-right">
                                <div id="dedicateincrease" class="margintop7 marginbottom10 fontweightbold colorincrease"></div>
                                <div>较上一年</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-4 ct-33 ct-line-col">
                <div class="top-div">
                    <div class="index-common-title">
                        <div class="index-common-title-left"></div>
                        <div class="index-common-title-text"><span id="satisfactionyear"></span>年满意度得分</div>
                    </div>
                    <div class="body-div">
                        <div class="scoreloadingmsg">数据读取中...</div>
                        <div class="raterow hide paddingtop35 paddingleft12 paddingright12 fontsize13">
                            <div id="satisfactionrate" class="col-xs-6 text-left fontsize28"></div>
                            <div class="col-xs-6 text-right">
                                <div id="satisfactionincrease" class="margintop7 marginbottom10 fontweightbold colorreduce"></div>
                                <div>较上一年</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-3 ct-34 ct-line-col">
                <input id="quotaId" type="hidden" value="${quotaId}">
                <div class="top-div" id="timeLine"></div>
            </div>


            <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">年度敬业度对比</div>
                </div>
                <div class="bottom-div">
                    <div class="chart-view text-center">
                        <div id="dedicateYearChart" class="chart height350"></div>
                    </div>
                </div>
            </div>

            <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">下级组织敬业度对比</div>
                </div>

                <div class="bottom-div">
                    <div class="chart-view text-center">
                        <div id="dedicateSubChart" class="chart height350"></div>
                    </div>
                </div>
            </div>

            <div class="col-sm-12 ct-line-col SetUpBody" view="chart">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">敬业度题目分析</div>

                </div>
                <div class="bottom-div bottom-div-two" style="text-align: center;">
                    <div class="chart-view text-center padding15">
                        <div id="dedicatelist"></div>
                    </div>
                </div>
            </div>
        </div>

        <div id="page-two" class="rightBodyPage">
            <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">年度满意度对比</div>
                </div>
                <div class="bottom-div">
                    <div class="chart-view text-center">
                        <div id="satisfactionYearChart" class="chart height350"></div>
                    </div>
                </div>
            </div>

            <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">下级组织满意度对比</div>
                </div>

                <div class="bottom-div">
                    <div class="chart-view text-center">
                        <div id="satisfactionSubChart" class="chart height350"></div>
                    </div>
                </div>
            </div>

            <div class="col-sm-12 ct-line-col SetUpBody" view="chart">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">满意度题目分析</div>

                </div>
                <div class="bottom-div bottom-div-two" style="text-align: center;">
                    <div class="chart-view text-center padding15">
                        <div id="satisfactionlist"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--遮罩层 begin-->
    <div class="shade"></div>
    <!--遮罩层 end-->
</div>

<script src="${jsRoot}lib/echarts/echarts.js"></script>
<script src="${jsRoot}biz/drivingforce/empSatisfaction.js"></script>
</body>
</html>
