<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<!-- jsp文件头和头部 -->
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>个人绩效及变化</title>
	<link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"  />
	<link rel="stylesheet" href="${ctx}/assets/css/biz/productivity/perChange.css"/>
</head>
<body>

<div class="page-content" id="main-container">
	<div class="main-container-inner">
		<div class="row column">
			<!-- 		<a id="tabBtn" class="u-nMenu-a-btn" href="#">navMenuDemo -->
			<!-- 		<span class="caret"></span></a> -->
			<div class="col-xs-3">
				<ul class="ztree" id="tree"></ul>
			</div>
			<input type="hidden" id="reqOrganId" value="${reqOrganId}">
			<input type="hidden" id="reqOrganName" value="${reqOrganName}">
			<input type="hidden" id="yearMonths" value="${yearMonths}">
			<input type="hidden" id="perWeek" value="${perWeek}">
<%-- 			<input type="hidden" id="empCount" value="${empCount}"> --%>
		</div>
		<ul class="nav nav-tabs f-white-bg" role="tablist" id="performanceTabs">
			<li role="presentation" class="active">
				<a href="#performanceSummaryTab" aria-controls="performanceSummaryTab" role="tab" data-toggle="tab">个人绩效概况</a>
			</li>
			<li role="presentation">
				<a href="#performanceDetailTab" aria-controls="performanceDetailTab" role="tab" data-toggle="tab">
					个人绩效明细
				</a>
			</li>
		</ul>
		<!-- Tab panes -->
		<div class="tab-content">
			<!-- 个人绩效概况 -->
			<div role="tabpanel" class="tab-pane active" id="performanceSummaryTab">
				<div class="row perChange column">
					<div class="col-md-8 col-sm-12">
						<div class="row">
							<div class="col-sm-3 col-xs-12  col-row-pl0">
								<div class="widget-box transparent f-white-bg">
									<div class="widget-header widget-header-flat">
										<h4 class="bolder">连续2次低绩效</h4>
										<div class="widget-toolbar">
											<span class="green2">人</span>
										</div>
									</div>
									<div class="widget-body">
										<div class="row column" id="tooltip1" data-toggle="tooltip" data-placement="bottom">
											<div class="col-sm-8">
												<div class="userCount" id="userCount1"></div>
											</div>
											<div class="col-sm-4 warn-icon"></div>
											<div class="col-sm-12">
												<label id="userNames1"></label>
											</div>
										</div>
									</div>
								</div>
							</div>

							<div class="col-sm-3 col-xs-12">
								<div class="widget-box transparent f-white-bg profit">
									<div class="widget-header widget-header-flat">
										<h4 class="bolder">当期低绩效</h4>
										<div class="widget-toolbar">
											<span class="green2">人</span>
										</div>
									</div>
									<div class="widget-body">
										<div class="row column" id="tooltip2" data-toggle="tooltip" data-placement="bottom">
											<div class="col-sm-8">
												<div class="userCount" id="userCount2"></div>
											</div>
											<div class="col-sm-4 warn-icon">
											</div>
											<div class="col-sm-12">
												<label id="userNames2"></label>
											</div>
										</div>
									</div>
								</div>
							</div>

							<div class="col-sm-3 col-xs-12">
								<div class="widget-box transparent f-white-bg eqEmpNum">
									<div class="widget-header widget-header-flat">
										<h4 class="bolder">连续2次高绩效</h4>
										<div class="widget-toolbar">
											<span class="green2">人</span>
										</div>
									</div>
									<div class="widget-body">
										<div class="row column" id="tooltip3" data-toggle="tooltip" data-placement="bottom">
											<div class="col-xs-8">
												<div class="userCount" id="userCount3"></div>
											</div>
											<div class="col-xs-4 smile-face"></div>
											<div class="col-sm-12">
												<label id="userNames3"></label>
											</div>
										</div>
									</div>
								</div>
							</div>

							<div class="col-sm-3 col-xs-12">
								<div class="widget-box transparent f-white-bg eqEmpNum">
									<div class="widget-header widget-header-flat">
										<h4 class="bolder">当期高绩效</h4>
										<div class="widget-toolbar">
											<span class="green2">人</span>
										</div>
									</div>
									<div class="widget-body">
										<div class="row column" id="tooltip4" data-toggle="tooltip" data-placement="bottom">
											<div class="col-xs-8">
												<div class="userCount" id="userCount4"></div>
											</div>
											<div class="col-xs-4 smile-face"></div>
											<div class="col-sm-12">
												<label id="userNames4"></label>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-12 col-xs-12">
						<input id="quotaId" type="hidden" value="${quotaId}">
						<div class="widget-box" id="timeLine"></div>
					</div>
				</div>

				<!-- 人均效益趋势 begin -->
				<div class="row column">
					<div class="widget-box" id="preArea">
						<h4 class="tabs-title">绩效分布</h4><select id="selectYM"></select>
						<!-- Nav tabs -->
						<!--
                        <ul class="nav nav-tabs nav-tabs-right">
                            <li role="presentation" class="active">
                                <a href="#preDistributionChartArea" aria-controls="preDistributionChartArea" role="tab" data-toggle="tab"
                                data-selected="1">
                                    <span class="glyphicon glyphicon-stats"></span>
                                </a>
                            </li>
                            <li role="presentation">
                                <a href="#preDistributionChartArea" aria-controls="preDistributionGrid" role="tab" data-toggle="tab">
                                    <span class="glyphicon glyphicon-th"></span>
                                </a>
                            </li>
                        </ul>
                        -->
						<!-- Tab panes -->
						<div class="tab-content">
							<div role="tabpanel" class="tab-pane active col-md-12 col-sm-12 col-xs-12" id="preDistributionChartArea">
								<div class="row">
									<div class="col-md-12 col-sm-12 col-xs-12" id="preDistributionChart">
										<ul class="nav nav-tabs "role="tablist">
											<li role="presentation" class="active"><a href="#chartContent" data-toggle="tab" id="all">全部</a></li>
											<li role="presentation"><a href="#chartContent" data-toggle="tab" id="manager">管理者</a></li>
											<li role="presentation"><a href="#chartContent" data-toggle="tab" id="emp">员工</a></li>
										</ul>
										<div class="tab-content noPaddingLeft">
											<div role="tabpanel" class="tab-pane fade in active noPaddingLeft" id="chartContent">
												<div class="col-md-5 col-sm-12 col-xs-12 leftBarArea noPaddingLeft" >
													<!-- <div  class="col-sm-12 preStarBarChar noPaddingLeft">
                                                        <div class="col-xs-5 preDisContent noPaddingLeft" id="preDisStar_old">
                                                        </div>
                                                         <div class="col-xs-7 preDisContent noPaddingLeft" id="preDisChart">
                                                        bbb
                                                        </div>
                                                    </div> -->
													<div  class="col-sm-12 preStarBarChar noPaddingLeft" id="preDisChart">
													</div>
													<div class=" col-sm-12 preDisTextArea">
														共<span class="preExceptionNum" id="joinCount"></span>人参与了本期绩效评估，
														未参与 <span class="preExceptionNum" id="notJoinCount"></span> 人
													</div>
												</div>
												<div class="col-md-7 col-sm-12 col-xs-12 rightPieArea" >
													<div class="col-sm-12" id="rightPieChart"></div>
													<div class="col-sm-12"  id="noData">暂无数据</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>

							<div role="tabpanel"  class="tab-pane" id="preDistributionGridArea">
								<div class="row" >
									<div class="col-md-5 col-sm-12 col-xs-12">
										<div  class="col-md-12 col-sm-12 col-xs-12" id="preDistributionGrid">
											这块没有界面，先空着
										</div>
									</div>

									<div class="col-md-7 col-sm-12 col-xs-12 trend-warn">

									</div>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
				<!-- 人均效益趋势end -->

				<div class="row column">
					<!-- 绩效结果变化趋势-->
					<div class="col-md-6 col-xs-12">
						<div class="widget-box transparent f-white-bg">
							<div class="widget-header widget-header-flat">
								<h4 class="tabs-title">绩效结果变化趋势</h4>
								<div class="widget-toolbar">
									<span class="blue2 titleYearMonth"></span>
								</div>
							</div>
							<div class="widget-body position-relative mainArea">
								<div class="row">
									<div class="col-sm-12 chartArea" >
										<div class="col-sm-12 " id="preChange"></div>
									</div>
									<div class="col-sm-12 textArea">
										说明：<br>
										• 有所进步：与上期绩效相比，提升一级或以上<br>
										• 出现下滑：与上期绩效相比，下降一级或以上
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- 绩效异常（大起大落） -->
					<div class="col-md-6 col-xs-12">
						<div class="widget-box transparent f-white-bg">
							<div class="widget-header widget-header-flat">
								<h4 class="bolder">绩效异常（大起大落）</h4>
								<div class="widget-toolbar">
									<span class="blue2  titleYearMonth"></span>
								</div>
							</div>
							<div class="widget-body position-relative mainArea">
								<div class="row">
									<div class="col-sm-12 chartArea">
										<div class="col-sm-12  preException">
											<div class="col-sm-5 preContext" id="preRise">0%</div>
											<div class="col-sm-2"> </div>
											<div class="col-sm-5 preContext"  id="preDown">0%</div>
										</div>
										<div class="col-sm-12 preExceptionLable">
											<div class="col-sm-6">飞速提升
												<span class="preExceptionNum" id="preRiseNum">0</span>人
											</div>
											<div class="col-sm-6">加速跌落
												<span class="preExceptionNum" id="preDownNum">0</span>人
											</div>
										</div>
									</div>
									<div class="col-sm-12 textArea">
										说明：<br>
										• 飞速提升：与上期绩效相比，提升幅度至少2个星级<br>
										• 加速跌落：与上期绩效相比，下滑幅度至少2个星级
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

			</div>

			<div role="tabpanel" class="tab-pane" id="performanceDetailTab">
				<div class="row">
					<div class="widget-box transparent f-white-bg">
						<div class="widget-header widget-header-flat">
							<h4 class="bolder"></h4>
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
								<div class="col-md-12  col-xs-12" id="performanceDetailTable">
									<label>&nbsp;</label>
									<table class="borderless" id="performanceDetailGrid"></table>
									<table id="performanceDetailSel"></table>
								</div>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>

	</div>
</div>

<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/js/biz/productivity/perChange.js"></script>
</body>
</html>