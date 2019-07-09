<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp"%>
    <title>人均效益</title>
    <link rel="stylesheet" href="${ctx}/assets/css/zTreeStyle/zTreeStyle.css"  />
    <link rel="stylesheet" href="${ctx}/assets/css/biz/productivity/manpowerCost.css"/>
</head>
<body>

<div class="page-content" id="main-container">
    <input type="hidden" id="curdate" value="${curdate}">
    <div class="main-container-inner">
        <div class="row manpower column">
            <div class="col-md-8 col-sm-12">
                <div class="row">
                    <div class="col-sm-4 col-xs-12 col-row-pl0">
                        <div class="widget-box transparent f-white-bg">
                            <div class="widget-header widget-header-flat">
                                <h4 class="bolder">人力成本<small>（万元）</small></h4>
                                 <div class="widget-toolbar" id="manpowerCostProportion_toolbar">
		                            <span class="white pointer">上月</span>
		                            <span class="green pointer" id="costYear">年累计</span>
		                        </div>
                            </div>
                            <div class="widget-body" id="manpowerCostProportion">
                                <div class="row column">
                                    <div class="col-sm-6">
                                        <div class="currentMonth"></div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="position-relative">
                                            <span class="lastMonth lastMonthBlue"></span>
                                        </div>
                                    </div>
                                    <div class="col-xs-12">
                                        <label class="lastLabel">较上一年</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-4 col-xs-12 col-row-pl0">
                        <div class="widget-box transparent f-white-bg profit">
                            <div class="widget-header widget-header-flat">
                                <h4 class="bolder">人均成本<small>（万元）</small></h4>
                            <div class="widget-toolbar"  id="avgCostProportion_toolbar">
		                            <span class="white pointer">上月</span>
		                            <span class="green pointer" id="avgCostYear">年累计</span>
		                        </div>
                            </div>
                            <div class="widget-body"  id="avgCostProportion">
                                <div class="row column">
                                    <div class="col-sm-6">
                                        <div class="currentMonth"></div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="position-relative">
                                            <span class="lastMonth lastMonthBlue"></span>
                                        </div>
                                    </div>
                                    <div class="col-xs-12">
                                        <label class="lastLabel">较上一年</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-4 col-xs-12">
                        <div class="widget-box transparent f-white-bg eqEmpNum">
                            <div class="widget-header widget-header-flat">
                                <h4 class="bolder">人力成本执行率<img id="capitabeneDx" src="${ctx}/assets/img/base/tip.gif" data-toggle="tooltip" data-placement="bottom" data-original-title="执行率=已使用成本/预算成本"/> </h4>
                                <div class="widget-toolbar">
                                    <span class="green"  id="barlineYearLable">年</span>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="row column">
							    	<div class="col-sm-12 barlineObject">
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
                </div>
            </div>
            <div class="col-md-4 col-sm-12 col-xs-12">
                <input id="quotaId" type="hidden" value="${quotaId}">
                <div class="widget-box col-xs-12" id="timeLine"></div>
            </div>
        </div>

        <div class="row column">
        	<div class="col-md-6 col-xs-12 col-row-mt20">
                <div class="widget-box transparent f-white-bg">
                    <h4 class="tabs-title">人力成本月度趋势<small>(<span id="trendByMonth_title"></span>年)</small></h4>
                    <div class="clearfix"></div>
                    <!-- Tab panes -->
                    <div class="tab-content">
                        <div class="row position-relative">
                            <div class="col-xs-12">(万元)</div>
                            <div class="trendByMonth-bar"  id="trendByMonth"> </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 col-xs-12 col-row-mt20">
                <div class="widget-box transparent f-white-bg">
                    <h4 class="tabs-title">人均成本月度趋势<small>(2015年)</small></h4>
                    <div class="clearfix"></div>
                    <!-- Tab panes -->
                    <div class="tab-content">
                        <div class="row position-relative">
                            <div class="col-xs-12">(万元)</div>
                                <div class="manpowerTrend-line"  id="manpowerTrend"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row column">
            <div class="col-md-6 col-xs-12 col-row-mt20">
                <div class="widget-box transparent f-white-bg">
                    <h4 class="tabs-title">各架构人力成本<small>(2015年)</small></h4>
                    <div class="clearfix"></div>
                    <!-- Tab panes -->
                    <div class="tab-content">
                    <div class="col-xs-12">(万元)</div>
                        <div class="rowContentHeight"  id="contrastOrgChart"></div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 col-xs-12 col-row-mt20">
                <div class="widget-box transparent f-white-bg">
                    <h4 class="tabs-title">人力成本结构<small>(2015年)</small></h4>
                    <div class="clearfix"></div>
                    <!-- Tab panes -->
                    <div class="tab-content">
                    	<div class="col-xs-12">(万元)</div>
                        <div class="rowContentHeight" id="contrastDetailChart"></div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row manpower column">
            <div class="col-xs-12 col-row-mt20">
                <div class="widget-box transparent f-white-bg">
                    <h4 class="tabs-title">人力成本趋势<small>（万元）</small></h4>
                    <div class="clearfix"></div>
                    <!-- Tab panes -->
                    <div class="tab-content">
                        <div class="col-md-4 col-sm-12 col-xs-12" >
                            <div class="panel panel-default">
                                <div class="widget-header widget-header-flat">
	                                <h4 class="bolder">人力成本占比</h4>
	                                 <div class="widget-toolbar" id="manpowerCostTrends_toolbar">
			                            <span class="white pointer">上月</span>
			                            <span class="green pointer" id="manpowerCostYear">年累计</span>
			                        </div>
	                            </div>
	                            <div class="widget-body" id="manpowerCostTrends">
	                                <div class="row column">
	                                    <div class="col-sm-6">
	                                        <div class="currentMonth"></div>
	                                    </div>
	                                    <div class="col-sm-6">
	                                        <div class="position-relative">
	                                            <span class="lastMonth lastMonthBlue"></span>
	                                        </div>
	                                    </div>
	                                    <div class="col-xs-12">
	                                        <label class="lastLabel">较上一年</label>
	                                    </div>
	                                </div>
	                            </div>
                            </div>

                            <div class="panel panel-default">
                            	<div class="widget-header widget-header-flat">
                                    <h4 class="bolder">人均成本预警</h4>
	                            </div>
                                <div class="widget-body" id="manpowerCostearlyWarning">
                                	 <div class="row column">
	                                    <div class="col-sm-4">
	                                        <div class="col-sm-12 warn-icon"></div>
	                                        <div class="col-sm-12 smile-face"></div>
	                                    </div>
	                                    <div class="col-sm-8">
	                                    	<div class="position-relative">
	                                            <span class="average">行业均值</span>	
	                                            <span class="average change" id="industryAverage"></span>
	                                            <span class="average">万元</span>
                                            </div>
	                                    </div>
	                                    <div class="col-sm-8">
                                    		<span class="average change" id="averageCompare"></span>
                                    		<span class="arrow-icon"></span>
                                        	
	                                    </div>
	                                </div>
								</div>
                            </div>
                        </div>
                        <div class="col-md-8 col-sm-12 col-xs-12" >
 
                    	<div class="col-xs-12">(万元)</div>
                        <div class="rowContentHeight"  id="manpowerCostTrendsChars"></div>
                   
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript"src="${ctx}/assets/js/require.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/lib/plugins/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/biz/productivity/manpowerCost.js"></script>
</body>
</html>
