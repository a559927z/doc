<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>薪酬看板</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/datetime/datetimepicker.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/drivingforce/salaryBoard.css"/>
</head>
<body>
<div class="salary-board" id="trainBoard">
    <div class="leftBody">
        <div class="leftListBigDiv">薪酬看板</div>
        <div page="page-one" class="leftListDiv selectList">薪酬总览</div>
        <div page="page-two" class="leftListDiv">工资分析</div>
        <div page="page-three" class="leftListDiv">福利分析</div>
        <div page="page-four" class="leftListDiv">持股分析</div>
    </div>

    <div class="rightBody rightBody10">
        <div id="page-one" class="rightBodyPage">
            <div class="col-sm-3 ct-26 ct-line-col">
                <div class="top-div">
                    <div class="index-common-title">
                        <div class="index-common-title-left"></div>
                        <div class="index-common-title-text"><span id="salarytotal"></span>年薪酬总额</div>
                        <div id="salarytotalTab" class="index-common-title-right">
                            <span data-id="budget" class="select">预算</span>
                            <span data-id="accumulative">累计</span>
                        </div>
                    </div>
                    <div id="salarytotalContent" class="body-div">
                        <div class="content" id="budget">
                            <div class="data nodata hide">暂无数据</div>
                            <div class="data hide">
                                <div class="accord-yj-float">
                                    <span class="accord-yj-float-value" >0</span>
                                    <span class="accord-yj-float-people">万</span>
                                </div>
                                <div class="accord-bottom-float">
                                    <div class="accord-bottom-float-text">较上一年薪酬总额</div>
                                    <div class="accord-bottom-float-arrow"></div>
                                    <div class="accord-bottom-float-value">0</div>
                                    <div class="accord-bottom-float-people">%</div>
                                </div>
                            </div>
                        </div>
                        <div class="content hide" id="accumulative">
                            <div class="data nodata hide">暂无数据</div>
                            <div class="data hide">
                                <div class="accord-yj-float">
                                    <span class="accord-yj-float-value" >0</span>
                                    <span class="accord-yj-float-people">万</span>
                                </div>
                                <div class="accord-bottom-float">
                                    <div class="accord-bottom-float-text">较上一年同期</div>
                                    <div class="accord-bottom-float-arrow"></div>
                                    <div class="accord-bottom-float-value">0</div>
                                    <div class="accord-bottom-float-people">%</div>
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
                        <div class="index-common-title-text">薪酬占人力成本比</div>
                    </div>
                    <div class="body-div" id="salarystractureContent">
                        <div class="data nodata hide">暂无数据</div>
                        <div class="data hide">
                            <div class="accord-yj-float">
                                <span class="accord-yj-float-value" >0</span>
                                <span class="accord-yj-float-people">%</span>
                            </div>
                            <div class="accord-bottom-float">
                                <div class="accord-bottom-float-text"><span id="salarystracture"></span>年</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-3 ct-20 ct-line-col">
                <div class="top-div">
                    <div class="index-common-title">
                        <div class="index-common-title-left"></div>
                        <div class="index-common-title-text">人力资本投资回报率</div>
                    </div>
                    <div class="body-div" id="salaryrateContent">
                        <div class="data nodata hide">暂无数据</div>
                        <div class="data hide">
                            <div class="accord-yj-float">
                                <span class="accord-yj-float-value">0</span>
                            </div>
                            <div class="accord-bottom-float">
                                <div class="accord-bottom-float-text"><span id="salaryrate"></span>年</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-3 ct-34 ct-line-col">
                <input id="quotaId" type="hidden" value="${quotaId}">
                <div class="top-div" id="timeLine"></div>
            </div>

            <div class="col-md-6 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">薪酬总额统计</div>
                </div>
                <div class="bottom-div clearfix">
                    <div class="chart-view">
                        <div class="col-xs-12 bottom-div-first height20"><span>&nbsp;</span></div>
                        <div class="col-xs-12 padding0Only height350" id="salaryCostChart" ></div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 ct-line-col SetUpBody" view="chart">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">下级组织薪酬统计</div>

                    <div class="rightSetUpBtn">
                        <div class="rightSetUpBtnDiv rightSetUpLeft rightSetUpBtnSelect">
                            <div class="rightSetUpBtnTop"></div>
                            <div class="text">薪酬总额</div>
                        </div>
                        <div class="rightSetUpBtnDiv rightSetUpRight">
                            <div class="rightSetUpBtnTop"></div>
                            <div class="text">平均薪酬</div>
                        </div>
                    </div>

                </div>

                <div class="bottom-div clearfix">
                    <div class="chart-view">
                        <div class="col-xs-12 bottom-div-first height20"><span>&nbsp;</span></div>
                        <div class="col-xs-12 padding0Only height320" id="salaryCostSubTotalChart"></div>
                        <div class="col-xs-12 bottom-info"><span id="salaryTotalYear"></span>年</div>
                    </div>
                    <div class="table-view">
                        <div class="col-xs-12 bottom-div-first height20"><span></span></div>
                        <div class="col-xs-12 padding0Only height320" id="salaryCostSubAvgChart"></div>
                        <div class="col-xs-12 bottom-info"><span id="salaryAvgYear"></span>年</div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">组织KPI达标率、人力成本、薪酬总额的年度趋势</div>
                </div>
                <div class="bottom-div clearfix">
                    <div class="chart-view">
                        <div class="col-xs-12 bottom-div-first height20"><span></span></div>
                        <div class="col-xs-12 padding0Only height350" id="salarycompletionYearRateChart"></div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">营业额、利润、人力成本及薪酬总额的月度趋势</div>
                </div>
                <div class="bottom-div clearfix">
                    <div class="chart-view">
                        <div class="col-xs-12 bottom-div-first height20"><span></span></div>
                        <div class="col-xs-12 padding0Only height350" id="salarycompletionMonthRateChart"></div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">人力资本投资回报率月度趋势</div>
                </div>
                <div class="bottom-div clearfix">
                    <div class="chart-view">
                        <div class="col-xs-12 bottom-div-first height20"><span></span></div>
                        <div class="col-xs-12 padding0Only height350" id="salaryBringBackMonthChart"></div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">行业分位值年度趋势</div>
                </div>
                <div class="bottom-div clearfix">
                    <div class="chart-view">
                        <div class="col-xs-12 bottom-div-first height20"><span>&nbsp;</span></div>
                        <div class="col-xs-12 padding0Only height350" id="salaryBringBackYearChart"></div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text"><span id="salaryDiffListDate"></span>薪酬差异度岗位表</div>
                </div>
                <div class="bottom-div clearfix height458">
                    <div class="chart-view" id="salaryDiff">
                        <table class="padding0Only" id="salaryDiffList"></table>
                        <table id="salaryDiffPager"></table>
                    </div>
                </div>
            </div>

            <div class="col-md-6 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text width100">员工CR值
                        <span class="floatright paddingright20"><a id="salaryCrMore" href="javascript:;">更多员工CR值&gt;</a></span>
                    </div>
                </div>
                <div class="bottom-div clearfix">
                    <div class="chart-view">
                        <div class="col-xs-12 padding0Only height426" id="salaryCrChart"></div>
                        <div class="col-xs-12 bottom-info">注：统计时间为<span id="salaryCrChartDate"></span>，图上只显示最新一期绩效为五星的人员</div>
                    </div>
                </div>
            </div>
        </div>

        <div id="page-two" class="rightBodyPage">
            <div class="col-sm-12 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">工资统计</div>
                    <div class="index-common-title-center">
                        <div class="title-center-text"><span>工资总额</span><span id="salaryWageTotal">-</span><small>万元</small></div>
                        <div class="title-center-text"><span>工资占薪酬总额比</span><span id="salaryWageRate">-</span><small>%</small></div>
                    </div>
                </div>
                <div class="bottom-div bottom-div-two borderbottom0 borderright0 clearfix">
                    <div class="">
                        <div class="col-md-6 col-xs-12 borderleft0 borderbottom1 borderright1 padding0">
                            <div class="col-xs-12 bottom-div-first">工资总额月度趋势</div>
                            <div class="col-xs-12 padding0 height350" id="salaryTotalMonthChart"></div>
                        </div>
                        <div class="col-md-6 col-xs-12 borderleft0 borderbottom1 borderright1 padding0">
                            <div class="col-xs-12 bottom-div-first">工资总额及占薪酬比年度趋势</div>
                            <div class="col-xs-12 padding0 height350" id="salaryTotalRateChart"></div>
                        </div>
                        <div class="col-md-6 col-xs-12 borderleft0 borderbottom1 borderright1 padding0">
                            <div class="col-xs-12 bottom-div-first">下级组织工资对比<span id="salaryTotalChartDate"></span></div>
                            <div class="bottom-div-right chartselect">
                                <div data-id="salaryTotalChart" class="select">工资总额</div>
                                <div data-id="salaryAvgChart">平均工资</div>
                            </div>
                            <div class="col-xs-12 padding0 height350 chartselectcontent" id="salaryTotalChart"></div>
                            <div class="col-xs-12 padding0 height350 chartselectcontent hide" id="salaryAvgChart"></div>
                        </div>
                        <div class="col-md-6 col-xs-12 borderleft0 borderbottom1 borderright1 padding0">
                            <div class="col-xs-12 bottom-div-first">工资结构<span id="salaryStructureChartDate"></span></div>
                            <div class="col-xs-12 padding0 height350" id="salaryStructureChart"></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-12 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">固定与浮动薪酬分析</div>
                    <div class="index-common-title-center" id="positionRate">
                        <div class="title-center-text"><span>固定与浮动薪酬比</span><span id="salaryFixedProportion">-</span><small>&nbsp;</small></div>
                    </div>
                </div>
                <div class="bottom-div bottom-div-three">
                    <div>
                        <div class="col-md-6 col-xs-12 padding0">
                            <div class="col-xs-12 bottom-div-first">职位序列固浮比统计<span id="positionChartDate"></span></div>
                            <div class="col-xs-12 padding0 height350" id="positionChart"></div>
                        </div>
                        <div class="col-md-6 col-xs-12 padding0" style="border-left: none;">
                            <div class="col-xs-12 bottom-div-first">&nbsp;</div>
							<div class="col-xs-12 padding0 height350" id="positionSubChart"></div>
                        </div>
                    </div>
<!--                         <div class="marginleft20 paddingBottom20">操作提示：点击职位序列可查看该序列下的职级分布，再次点击则可返回默认统计</div> -->
                </div>

                <div class="bottom-div bottom-div-two clearfix bordertop0">
                    <div>
                        <div class="col-md-6 col-xs-12 padding0">
                            <div class="col-xs-12 bottom-div-first">下级组织固浮比<span id="subOrgChartDate"></span></div>
                            <div style="height: 360px;" id="subOrgChart"></div>
                        </div>
                        <div class="col-md-6 col-xs-12 padding0" style="border-left: none;">
                            <div class="col-xs-12 bottom-div-first">&nbsp;</div>
                            <div class="col-xs-12 padding0" id="subOrgFixedFloat">
                                <table class="borderless" id="subOrgList"></table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-12 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">年终奖金总额与利润的年度趋势</div>
                </div>
                <div class="bottom-div bottom-div-two">
                    <div class="row">
                        <div class="col-md-6 col-xs-12 padding0">
                            <div class="col-xs-12 bottom-div-first">&nbsp;</div>
                            <div class="col-xs-12 padding0 height350" id="yearEndBonusChart"></div>
                        </div>
                        <div class="col-md-6 col-xs-12 padding0" style="border-left: none;">
                            <div class="col-xs-12 bottom-div-first">&nbsp;</div>
                            <div class="col-xs-12 padding0" id="yearEndBonus">
                                <table class="borderless" id="yearEndBonusList" style="margin-left:11px;"></table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="page-three" class="rightBodyPage">
            <div class="col-sm-12 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">福利费用统计</div>
                    <div class="index-common-title-center" id="salaryWelfare">
                        <div class="title-center-text"><span>福利费用总额</span><span id="salaryWelfareTotal">-</span><small>万元</small></div>
                        <div class="title-center-text"><span>福利费用占薪酬总额比</span><span id="salaryWelfareRate">-</span><small>%</small></div>
                    </div>
                </div>
                <div class="bottom-div bottom-div-two clearfix borderbottom0 borderright0">
                    <div class="">
                        <div class="col-md-6 col-xs-12 padding0 borderleft0 borderbottom1 borderright1">
                            <div class="col-xs-12 bottom-div-first">福利费用总额月度趋势</div>
                            <div class="col-xs-12 padding0 height350" id="welfareTotalMonthChart"></div>
                        </div>
                        <div class="col-md-6 col-xs-12 padding0 borderleft0 borderbottom1 borderright1">
                            <div class="col-xs-12 bottom-div-first">福利费用及占薪酬比年度趋势</div>
                            <div class="col-xs-12 padding0 height350" id="welfareTotalRateChart"></div>
                        </div>
                        <div class="col-md-6 col-xs-12 padding0 borderleft0 borderbottom1 borderright1">
                            <div class="col-xs-12 bottom-div-first">下级组织福利费用总额对比<span id="welfareSubOrgTotalChartDate"></span></div>
                            <div class="col-xs-12 padding0 height350" id="welfareSubOrgTotalChart"></div>
                        </div>
                        <div class="col-md-6 col-xs-12 padding0 borderleft0 borderbottom1 borderright1">
                            <div class="col-xs-12 bottom-div-first">下级组织平均福利费用对比<span id="welfareSubOrgAvgChartDate"></span></div>
                            <div class="col-xs-12 padding0 height350" id="welfareSubOrgAvgChart"></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-12 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">福利种类统计</div>
                </div>
                <div class="bottom-div bottom-div-two clearfix">
                    <div class="col-md-5 col-xs-12 ct-col1">
                        <div class="col-xs-12 bottom-div-first margin10">国家固定福利（五险一金）</div>
                        <div class="col-xs-12 ct-col1" id="insureChart"></div>
                    </div>
                    <div class="col-md-7 col-xs-12" style="border-left: none; margin-bottom 20px;">
                        <div class="col-sm-12 ct-line-col conditionBtnListBody">
                            <div class="dis-insure-search">
                                <select class="dis-search-select" id="insureTime"></select>
                                <select class="dis-search-select" id="insureType"></select>
                                <input class="dis-search-input" id="insureTxt" type="text" placeholder="请输入员工ID或姓名" />
                                <div class="add-on dis-search-input-btn" id="insureSearchBtn">人员搜索</div>
                            </div>
                        </div>
                        <div class="col-xs-12" id="insureTable">
                            <table class="borderless" id="insureList"></table>
                            <table id="insurePager"></table>
                        </div>
                    </div>
                </div>

                <div class="bottom-div bottom-div-two clearfix bordertop0">
                    <div class="col-md-5 col-xs-12 ct-col1">
                        <div class="col-xs-12 bottom-div-first margin10">企业福利（货币）</div>
                        <div class="col-xs-12 ct-col1" id="insureMoneyChart"></div>
                    </div>
                    <div class="col-md-7 col-xs-12" style="border-left: none;">
                        <div class="col-sm-12 ct-line-col conditionBtnListBody">
                            <div class="dis-insure-search">
                                <select class="dis-search-select" id="insureMoneyTime"></select>
                                <select class="dis-search-select" id="insureMoneyType"></select>
                                <input class="dis-search-input" id="insureMoneyTxt" type="text" placeholder="请输入员工ID或姓名" />
                                <div class="add-on dis-search-input-btn" id="insureMoneySearchBtn">人员搜索</div>
                            </div>
                        </div>
                        <div class="col-xs-12" id="insureMoneyTable">
                            <table class="borderless" id="insureMoneyList"></table>
                            <table id="insureMoneyPager"></table>
                        </div>
                    </div>
                </div>

                <div class="bottom-div bottom-div-two clearfix bordertop0">
                    <div class="col-md-5 col-xs-12 ct-col1">
                        <div class="col-xs-12 bottom-div-first margin10">企业福利（非货币）</div>
                        <div class="col-xs-12 ct-col1" id="insureNoMoneyChart"></div>
                    </div>
                    <div class="col-md-7 col-xs-12" style="border-left: none;">
                        <div class="col-sm-12 ct-line-col conditionBtnListBody">
                            <div class="dis-insure-search">
                                <select class="dis-search-select" id="insureNoMoneyTime"></select>
                                <select class="dis-search-select" id="insureNoMoneyType"></select>
                                <input class="dis-search-input" id="insureNoMoneyTxt" type="text" placeholder="请输入员工ID或姓名" />
                                <div class="add-on dis-search-input-btn" id="insureNoMoneySearchBtn">人员搜索</div>
                            </div>
                        </div>
                        <div class="col-xs-12"  id="insureNoMoneyTable">
                            <table class="borderless" id="insureNoMoneyList"></table>
                            <table id="insureNoMoneyPager"></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="page-four" class="rightBodyPage">
            <div class="col-sm-12 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">持股统计</div>
                    <div class="index-common-title-center">
                        <div class="title-center-text"><span>持股数量</span><span id="sumShares">-</span><small>万股</small></div>
                        <div class="title-center-text"><span>持股员工总数</span><span id="empSharesCount">-</span><small>人</small></div>
                        <div class="title-center-text"><span>持股覆盖率</span><span id="sharesCover">-</span><small>%</small></div>
                    </div>
                </div>
                <div class="bottom-div bottom-div-two clearfix borderbottom0 borderright0">
                    <div class="">
                        <div class="col-md-6 col-xs-12 borderleft0 borderbottom1 borderright1 padding0Only">
                            <div class="col-xs-12 chart-title marginBottom10">持股员工总数年度趋势</div>
                            <!-- <div class="col-xs-12 chart-unit">(人)</div> -->
                            <div class="col-xs-12 padding0Only height350" id="shareTotalYearChart"></div>
                        </div>
                        <div class="col-md-6 col-xs-12 borderleft0 borderbottom1 borderright1 padding0Only">
                            <div class="col-xs-12 chart-title marginBottom10">持股数量年度趋势</div>
                            <!-- <div class="col-xs-12 chart-unit">(万股)</div> -->
                            <div class="col-xs-12 padding0Only height350" id="shareTendencyYearChart"></div>
                        </div>
                        <div class="col-md-6 col-xs-12 borderleft0 borderbottom1 borderright1 padding0Only">
                            <div class="col-xs-12 chart-title marginBottom10">下级组织持股员工数</div>
                            <!-- <div class="col-xs-12 chart-unit">(人)</div> -->
                            <div class="col-xs-12 padding0Only height350" id="shareStaffTotalChart"></div>
                            <div class="col-xs-12 bottom-info"><span id="staffTotalDate"></span>年</div>
                        </div>
                        <div class="col-md-6 col-xs-12 borderleft0 borderbottom1 borderright1 padding0Only">
                            <div class="col-xs-12 chart-title marginBottom10">下级组织持股数量</div>
                            <!-- <div class="col-xs-12 chart-unit">(万股)</div> -->
                            <div class="col-xs-12 padding0Only height350" id="shareSubOrgTotalChart"></div>
                            <div class="col-xs-12 bottom-info"><span id="subOrgTotalDate"></span>年</div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-12 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">员工股票期权</div>
                </div>
                <div class="bottom-div bottom-div-four">
                    <div class="col-sm-12 ct-line-col conditionBtnListBody" id="searchBox">
                        <div class="dis-search">
                            <input class="dis-search-input" id="searchTxt" type="text" placeholder="请输入员工ID或姓名" >
                            <div class="add-on dis-search-input-btn" id="searchBtn">人员搜索</div>
                        </div>
                        <div class="more-search"></div>
                        <div class="dis-search-tip">* 姓名支持模糊查询</div>
                    </div>

                    <div class="table-body">
                        <div class="clearfix"></div>
                        <div class="col-md-12  col-xs-12 paddingTop20" id="sharesDeailTable" >
                            <!-- <label>&nbsp;</label> -->
                            <table class="borderless" id="sharesDetailGrid"></table>
                            <table id="sharesDetailPager"></table>
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

<!-- 员工CR值 -->
<div class="modal fade popup-modal" id="moreCr" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-text" id="myModalLabel">员工CR值</div>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body page-content" id="crMoreGrid">
                <div id="moreCrGrid">
                    <table id="CrGrid"></table>
                    <table id="CrGridPager"></table>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script src="${jsRoot}lib/echarts/echarts.js"></script>
<script src="${jsRoot}biz/drivingforce/salaryBoard.js"></script>
</body>
</html>
