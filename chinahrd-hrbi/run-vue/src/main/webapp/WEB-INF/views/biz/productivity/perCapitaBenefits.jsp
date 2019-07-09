<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<!-- jsp文件头和头部 -->
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>人均效益</title>
	<link rel="stylesheet" href="${ctx}/assets/css/zTreeStyle/zTreeStyle.css"  />
	<link rel="stylesheet" href="${ctx}/assets/css/biz/productivity/perBenefit.css"/>
</head>
<body>

	<div class="page-content" id="main-container">
		<div class="main-container-inner">

			<div class="row perBenefit column">
					<div class="col-md-8 col-sm-12">
						<div class="row">
							<div class="col-sm-3 col-xs-12 col-row-pl0" id="perBenefitValue">
								<div class="widget-box transparent f-white-bg">
									<div class="widget-header widget-header-flat">
										<h4 class="bolder">人均效益</h4>
										<div class="widget-toolbar">
											<span class="blue">万元</span>
										</div>
									</div>
									<div class="widget-body">
										<div class="row column">
											<div class="col-sm-6">
												<div class="currentMonth"></div>
											</div>
											<div class="col-sm-6">
												<div class="position-relative">
													<span class="lastMonth"></span>
												</div>
											</div>
											<div class="col-xs-12">
												<label class="lastLabel">较上月</label>
											</div>
										</div>
									</div>
								</div>
							</div>


							<div class="col-sm-1 col-xs-12">
								<div class="label-operator">=</div>
                            </div>

							<div class="col-sm-3 col-xs-12">
								<div class="widget-box transparent f-white-bg profit">
									<div class="widget-header widget-header-flat">
										<h4 class="bolder">营业利润</h4>
										<div class="widget-toolbar">
											<span class="blue">万元</span>
										</div>
									</div>
									<div class="widget-body">
										<div class="row column">
											<div class="col-sm-6">
												<div class="currentMonth"></div>
											</div>
											<div class="col-sm-6">
												<div class="position-relative">
													<span class="lastMonth"></span>
												</div>
											</div>
											<div class="col-xs-12">
												<label class="lastLabel">较上月</label>
											</div>
										</div>
									</div>
								</div>
							</div>

							<div class="col-sm-1 col-xs-12">
								<div class="label-operator">/</div>
							</div>

							<div class="col-sm-4 col-xs-12">
								<div class="widget-box transparent f-white-bg eqEmpNum">
									<div class="widget-header widget-header-flat">
										<h4 class="bolder">等效全职员工数<img id="capitabeneDx" src="${ctx}/assets/img/base/tip.gif" /> </h4>
										<div class="widget-toolbar">
											<span class="green2">人</span>
										</div>
									</div>
									<div class="widget-body">
										<div class="row column">
											<div class="col-xs-6">
												<div class="currentMonth"></div>
											</div>
											<div class="col-xs-6">
												<div  class="position-relative">
													<span class="lastMonth"></span>
												</div>
											</div>
											<div class="col-xs-12">
												<label class="lastLabel">较上月</label>
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
					<div class="widget-box" id="trendArea">
						<h4 class="tabs-title">人均效益趋势</h4>
						<!-- Nav tabs -->
						<ul class="nav nav-tabs nav-tabs-right" role="tablist">
							<li role="presentation" class="active">
								<a href="#monthTrendArea" aria-controls="monthTrendArea" role="tab" data-toggle="tab"
								data-selected="1">月</a>
							</li>
							<li role="presentation">
								<a href="#yearTrendArea" aria-controls="yearTrendArea" role="tab" data-toggle="tab">年</a>
							</li>
						</ul>
						<div class="clearfix"></div>
						<!-- Tab panes -->
						<div class="tab-content">
							<div role="tabpanel" class="tab-pane active col-md-12 col-sm-12 col-xs-12" id="monthTrendArea">
								<div class="row" >
									<div class="col-md-8 col-sm-12 col-xs-12">
										 <ul class="nav nav-tabs nav-tabs-right" role="tablist">
                              			<li role="presentation" class="active">
			                                  <a href="#trendChartAreaMonth" aria-controls="trendChartAreaMonth" role="tab"
			                                     data-toggle="tab" data-selected="1">
			                                      <span class="glyphicon glyphicon-stats"></span>
			                                  </a>
                              			</li>
			                              <li role="presentation">
			                                  <a href="#trendGridAreaMonth" aria-controls="trendGridAreaMonth" role="tab" data-toggle="tab">
			                                      <span class="glyphicon glyphicon-th"></span>
			                                  </a>
			                              </li>
                         				 </ul>
                         				 <div class="clearfix"></div>
										<!-- Tab panes -->
										<div class="tab-content">
											<div role="tabpanel" class="tab-pane active" id="trendChartAreaMonth">
	                                            <div class="col-md-12 col-sm-12 col-xs-12" id="monthTrendChart" ></div>
	                                        </div>
	                                        <div role="tabpanel" class="tab-pane col-md-12 col-sm-12 col-xs-12" id="trendGridAreaMonth">
	                                            <table class="borderless" id="trendTableGridMonth"></table>
	                                        </div>
	                                     </div>
									</div>
									<div class="col-md-4 col-sm-12 col-xs-12 trend-warn">
										<div class="warn-title">
											<span>预警</span>
										</div>
										<div class="warn-detail trade-warn"></div>
										<div class="horizontal-line"></div>
										<div class="warn-detail recent-warn"></div>
									</div>
								</div>
							</div>
							<div role="tabpanel" class="tab-pane" id="yearTrendArea">
								<div class="row" >
									<div class="col-md-8 col-sm-12 col-xs-12">
										 <ul class="nav nav-tabs nav-tabs-right" role="tablist">
                              			<li role="presentation" class="active">
			                                  <a href="#trendChartAreaYear" aria-controls="trendChartAreaYear" role="tab"
			                                     data-toggle="tab" data-selected="1">
			                                      <span class="glyphicon glyphicon-stats"></span>
			                                  </a>
                              			</li>
			                              <li role="presentation">
			                                  <a href="#trendGridAreaYear" aria-controls="trendGridAreaYear" role="tab" data-toggle="tab">
			                                      <span class="glyphicon glyphicon-th"></span>
			                                  </a>
			                              </li>
                         				 </ul>
                         				 <div class="clearfix"></div>
											<!-- Tab panes -->
											<div class="tab-content">
		                         				 <div role="tabpanel" class="tab-pane active" id="trendChartAreaYear">
		                                            <div class="col-md-12 col-sm-12 col-xs-12" id="yearTrendChart" ></div>
		                                        </div>
		                                        <div role="tabpanel" class="tab-pane col-md-12 col-sm-12 col-xs-12" id="trendGridAreaYear">
		                                            <table class="borderless" id="trendTableGridYear"></table>
		                                        </div>
		                                      </div>
									</div>
									<div class="col-md-4 col-sm-12 col-xs-12 trend-warn">
										<div class="warn-title">
											<span>预警</span>
										</div>
										<div class="warn-detail trade-warn"></div>
										<div class="horizontal-line"></div>
										<div class="warn-detail recent-warn">
										</div>
									</div>
								</div>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
				<!-- 人均效益趋势end -->
				
				<div class="row column">
					<div class="col-md-8 col-xs-12">
						<div class="widget-box transparent f-white-bg" id="orgBeneftArea">
							<h4 class="tabs-title">当前组织人均效益</h4>
							<!-- Nav tabs -->
							<ul class="nav nav-tabs nav-tabs-right" role="tablist">
								<li role="presentation" class="active">
									<a href="#orgBeChartArea" aria-controls="orgBeChartArea" role="tab"
									data-toggle="tab" data-selected="1">
										<span class="glyphicon glyphicon-stats"></span>
									</a>
								</li>
								<li role="presentation">
									<a href="#orgBenefitGridArea" aria-controls="orgBenefitGridArea" role="tab" data-toggle="tab">
										<span class="glyphicon glyphicon-th"></span>
									</a>
								</li>
							</ul>
							<div class="clearfix"></div>
							<!-- Tab panes -->
							<div class="tab-content">
								<div role="tabpanel" class="tab-pane active" id="orgBeChartArea">
									<div class="row">
										<div class="col-md-8 col-sm-8 col-xs-12" id="orgBenefitChart"></div>
										<div class="col-md-4 col-sm-4 col-xs-12" id="recentMonthData"></div>
									</div>
								</div>
								<div role="tabpanel" class="tab-pane" id="orgBenefitGridArea">
										<table id="orgBenefitGrid" class="borderless"></table>
								</div>
							</div>
						</div>
					</div>
					
					<!-- 等效全职员工数(FTE) -->
					<div class="col-md-4 col-xs-12" >
						<div class="widget-box transparent f-white-bg">
							<div class="widget-header widget-header-flat">
								<h4 class="bolder">等效全职员工数(FTE)</h4>
								<div class="widget-toolbar"></div>
							</div>
							<div class="widget-body position-relative" id="fteArea">
								<div class="row">
									<div class="col-md-12 col-xs-12" id="fteChart"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
		</div>
	</div>
	
	
<!-- 人均效益变化幅度(Modal) -->
<div class="modal fade" id="beChangeModal" tabindex="-1" role="dialog"
	 aria-labelledby="beChangeModalLabel" aria-hidden="true">
	<div class="modal-dialog" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true"> &times;</span>
				</button>
				<h5 class="modal-title">人均效益变化幅度</h5>
			</div>
			<div class="modal-body">
				<div class="row" id="modalPanel">
					<div class="col-xs-6">
						<div class="be-change-title"><label class="col-md-pull-1">增幅前3的组织机构 </label></div>
						<div id="increaseChart" class="be-change-chart row">
						</div>
					</div>
					<div class="col-xs-6">
						<div class="be-change-title"><label>降幅前3的组织机构 </label></div>
						<div id="decreaseChart" class="be-change-chart row">
					</div>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/js/biz/productivity/perBenefit.js"></script>
</body>
</html>
