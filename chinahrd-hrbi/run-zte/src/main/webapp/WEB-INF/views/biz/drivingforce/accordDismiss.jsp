<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>主动流失率</title>
    <link rel="stylesheet" href="${ctx}/assets/css/zTreeStyle/zTreeStyle.css"/>
     <link rel="stylesheet" href="${ctx}/assets/css/datetime/datetimepicker.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/drivingforce/accordDismiss.css"/>
</head>
<body>
<input type="hidden" id="quarterLastDay" value="${quarterLastDay}"/>
<div class="page-content" id="main-container">
    <div class="main-container-inner">
        <div class="row column">
            <div class="col-xs-offset-4">
                <div class="update-time">
                    更新时间：&nbsp;<span>${quarterLastDay}</span>
                </div>
            </div>
        </div>
        <div class="row column">
            <div class="col-md-12 col-xs-12">
                <ul class="nav nav-tabs f-white-bg" role="tablist" id="dimissTypeTabs">
                    <li role="presentation" class="active">
                        <a href="#accordDismissTab" aria-controls="accordDismissTab" role="tab" data-toggle="tab">主动流失率</a>
                    </li>
                    <li role="presentation">
                        <a href="#dismissCauseTab" aria-controls="dismissCauseTab" role="tab" data-toggle="tab">
                            流失原因及去向
                        </a>
                    </li>
                    <li role="presentation">
                        <a href="#dismissDetailTab" aria-controls="dismissDetailTab" role="tab" data-toggle="tab">
                            流失人员明细
                        </a>
                    </li>
                </ul>

                <!-- Tab panes -->
                <div class="tab-content">
                    <!-- 人才流失风险 -->
                    <div role="tabpanel" class="tab-pane active" id="accordDismissTab">
                        <div class="row">
                            <div class="col-md-2 col-sm-4 col-xs-12">
                                <div id="accordDismissCursor" class="widget-box transparent f-white-bg"></div>
                            </div>
                            <div class="col-md-3 col-sm-4 col-xs-12">
                                <div class="widget-box transparent f-white-bg">
                                    <div class="widget-header widget-header-flat">
                                        <h4 class="bolder">关键人才流失人数</h4>
                                        <div class="widget-toolbar">
                                            <span class="green">人</span>
                                        </div>
                                    </div>

                                    <div class="widget-body" id="keyDismissArea">
                                        <div class="col-sm-7">
                                            <div class="currentQuarter"></div>
                                        </div>
                                        <div class="col-sm-5">
                                            <div class="position-relative">
                                                <span class="lastQuarter"></span>
                                            </div>
                                        </div>
                                        <div class="col-sm-12">
                                            <label class="lastLabel">较上季度</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3 col-sm-4 col-xs-12">
                                <div class="widget-box transparent f-white-bg">
                                    <div class="widget-header widget-header-flat">
                                        <h4 class="bolder">高绩效流失人数</h4>
                                        <div class="widget-toolbar">
                                            <span class="green">人</span>
                                        </div>
                                    </div>

                                    <div class="widget-body" id="perfDismissArea">
                                        <div class="col-sm-7">
                                            <div class="currentQuarter"></div>
                                        </div>
                                        <div class="col-sm-5">
                                            <div class="position-relative">
                                                <span class="lastQuarter"></span>
                                            </div>
                                        </div>
                                        <div class="col-sm-12">
                                            <label class="lastLabel">较上季度</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4 col-sm-12 col-xs-12">
                                <input id="quotaId" type="hidden" value="${quotaId}">
                                <div class="widget-box" id="timeLine"></div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 col-xs-12 col-row-mt20">
                                <div class="widget-box transparent f-white-bg">
                                    <h4 class="tabs-title">主动流失率趋势</h4>
                                    <ul class="nav nav-tabs nav-tabs-right" role="tablist">
                                        <li role="presentation" class="active">
                                            <a href="#trendChartArea" aria-controls="trendChartArea" role="tab"
                                               data-toggle="tab" data-selected="1">
                                                <span class="glyphicon glyphicon-stats"></span>
                                            </a>
                                        </li>
                                        <li role="presentation">
                                            <a href="#trendGridArea" aria-controls="trendGridArea" role="tab" data-toggle="tab">
                                                <span class="glyphicon glyphicon-th"></span>
                                            </a>
                                        </li>
                                    </ul>
                                    <div class="clearfix"></div>
                                    <!-- Tab panes -->
                                    <div class="tab-content">
                                        <div role="tabpanel" class="tab-pane active" id="trendChartArea">
                                            <div class="col-md-12 col-sm-12 col-xs-12" id="trendChart" ></div>
                                        </div>
                                        <div role="tabpanel" class="tab-pane col-md-12 col-sm-12 col-xs-12" id="trendGridArea">
                                            <table class="borderless" id="trendTableGrid"></table>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-6 col-xs-12 col-row-mt20">
                                <div class="widget-box transparent f-white-bg">
                                    <h4 class="tabs-title">本部与下属部门主动流失率对比</h4>
                                    <ul class="nav nav-tabs nav-tabs-right" id="contrastTabs" role="tablist">
                                        <li role="presentation" class="active">
                                            <a href="#contrastChartArea" aria-controls="contrastChartArea" role="tab"
                                               data-toggle="tab" data-selected="1">
                                                <span class="glyphicon glyphicon-stats"></span>
                                            </a>
                                        </li>
                                        <li role="presentation">
                                            <a href="#contrastGridArea" aria-controls="contrastGridArea" role="tab" data-toggle="tab">
                                                <span class="glyphicon glyphicon-th"></span>
                                            </a>
                                        </li>
                                    </ul>
                                    <div class="clearfix"></div>
                                    <!-- Tab panes -->
                                    <div class="tab-content">
                                        <div role="tabpanel" class="tab-pane active" id="contrastChartArea">
                                          	<div class="row position-relative">
                                          		<div class="col-xs-12" id="contrastChart"></div>
                                          	</div>
                                        </div>
                                        <div role="tabpanel" class="tab-pane" id="contrastGridArea">
                                           	<table class="borderless summary" id="contrastGrid"></table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 col-xs-12 col-row-mt20">
                                <div class="widget-box transparent f-white-bg">
                                    <h4 class="tabs-title">人才主动流失绩效分布</h4>
                                    <ul class="nav nav-tabs nav-tabs-right" role="tablist">
                                        <li role="presentation" class="active">
                                            <a href="#perfChartArea" aria-controls="orgBeChartArea" role="tab"
                                               data-toggle="tab" data-selected="1">
                                                <span class="glyphicon glyphicon-stats"></span>
                                            </a>
                                        </li>
                                        <li role="presentation">
                                            <a href="#perfGridArea" aria-controls="orgBenefitGridArea" role="tab" data-toggle="tab">
                                                <span class="glyphicon glyphicon-th"></span>
                                            </a>
                                        </li>
                                    </ul>
                                    <div class="clearfix"></div>
                                    <!-- Tab panes -->
                                    <div class="tab-content">
                                        <div role="tabpanel" class="tab-pane active" id="perfChartArea">
                                            <div class="col-md-12 col-sm-12 col-xs-12" id="perfChart"></div>
                                        </div>
                                        <div role="tabpanel" class="tab-pane col-md-12 col-sm-12 col-xs-12" id="perfGridArea">
                                            <table class="borderless summary" id="perfTableGrid"></table>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-6 col-xs-12 col-row-mt20">
                                <div class="widget-box transparent f-white-bg">
                                    <h4 class="tabs-title">人才主动流失层级分布</h4>
                                    <ul class="nav nav-tabs nav-tabs-right" role="tablist">
                                        <li role="presentation" class="active">
                                            <a href="#abilityChartArea" aria-controls="abilityChartArea" role="tab"
                                               data-toggle="tab" data-selected="1">
                                                <span class="glyphicon glyphicon-stats"></span>
                                            </a>
                                        </li>
                                        <li role="presentation">
                                            <a href="#abilityGridArea" aria-controls="abilityGridArea" role="tab" data-toggle="tab">
                                                <span class="glyphicon glyphicon-th"></span>
                                            </a>
                                        </li>
                                    </ul>
                                    <div class="clearfix"></div>
                                    <!-- Tab panes -->
                                    <div class="tab-content">
                                        <div role="tabpanel" class="tab-pane active" id="abilityChartArea">
                                            <div class="col-md-12 col-sm-12 col-xs-12" id="abilityChart"></div>
                                        </div>
                                        <div role="tabpanel" class="tab-pane col-md-12 col-sm-12 col-xs-12" id="abilityGridArea">
                                            <table class="borderless summary" id="abilityTableGrid"></table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 col-xs-12 col-row-mt20">
                                <div class="widget-box transparent f-white-bg">
                                    <h4 class="tabs-title">人才主动流失司龄分布</h4>
                                    <ul class="nav nav-tabs nav-tabs-right" role="tablist">
                                        <li role="presentation" class="active">
                                            <a href="#ageChartArea" aria-controls="orgBeChartArea" role="tab"
                                               data-toggle="tab" data-selected="1">
                                                <span class="glyphicon glyphicon-stats"></span>
                                            </a>
                                        </li>
                                        <li role="presentation">
                                            <a href="#ageGridArea" aria-controls="orgBenefitGridArea" role="tab" data-toggle="tab">
                                                <span class="glyphicon glyphicon-th"></span>
                                            </a>
                                        </li>
                                    </ul>
                                    <div class="clearfix"></div>
                                    <!-- Tab panes -->
                                    <div class="tab-content">
                                        <div role="tabpanel" class="tab-pane active" id="ageChartArea">
                                            <div class="col-md-12 col-sm-12 col-xs-12" id="ageChart"></div>
                                        </div>
                                        <div role="tabpanel" class="tab-pane col-md-12 col-sm-12 col-xs-12" id="ageGridArea">
                                            <table class="borderless summary" id="ageTableGrid"></table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>


                    <!-- 流失原因及去向 -->
                    <div role="tabpanel" class="tab-pane" id="dismissCauseTab">
                        <div class="row">
                            <div class="widget-box transparent f-white-bg">
                                <div class="widget-header widget-header-flat">
                                    <h4 class="bolder">流失原因分布</h4>
                                </div>

                                <div class="widget-body">
                                    <div class="row">
										<div class="col-md-6 col-xs-12" id="dismissReasonPie"></div>
										<div class="col-md-6 col-xs-12" id="dismissReasonBar"></div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row ">
                            <div class="widget-box transparent f-white-bg" style="height: 420px;">
                                <div class="widget-header widget-header-flat">
                                    <h4 class="bolder">流失去向分析</h4>
                                </div>

                                <div class="widget-body"  style="height: 420px;">
                                    <div class="row">
										<div class="col-md-6 col-xs-12" id="dismissGonePie" style="height: 420px;"></div>
										<div class="col-md-6 col-xs-12" id="dismissGoneBar" style="height: 420px;"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 流失人员明细 -->
                    <div role="tabpanel" class="tab-pane" id="dismissDetailTab">
                        <div class="row">
                            <div class="widget-box transparent f-white-bg"  style="height: 1000px;">
                                <div class="widget-header widget-header-flat">
                                    <h4 class="bolder">流失人员明细</h4>
                                </div>
                                <div class="widget-body">
                                	<div class="row" id="searchBox">
				                        <div class="col-xs-4">
				                            <div class="input-group">
				                                <input type="text" class="form-control search-query" id="searchTxt" placeholder="可输入员工姓名查询" />
				                                <span class="input-group-btn">
				                                    <button type="button" class="btn btn-white" id="searchBtn">
				                                        	人员搜索
				                                        <i class="icon-search icon-on-right bigger-110"></i>
				                                    </button>
				                                </span>
				                            </div>
				                        </div>
				                        <div class="col-xs-5 more-search"></div>
				                    </div>
                                    <div class="row column">
                           				<div class="clearfix"></div>
                           				<div class="col-md-12  col-xs-12" id="runOffDeailTable" >
												<label>&nbsp;</label>
												<table class="borderless" id="runOffDetailGrid" style="max-height: 400px;overflow-y:auto "></table>
												<table id="runOffDetailSel"></table>
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

<script type="text/javascript" src="${jsRoot}require.js"></script>
<script type="text/javascript" src="${jsRoot}lib/plugins/echarts/echarts.js"></script>
<script type="text/javascript" src="${jsRoot}biz/drivingforce/accordDismiss.js"></script>
</body>
</html>
