<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<!-- jsp文件头和头部 -->
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>人均效益</title>
	<link rel="stylesheet" href="${ctx}/assets/css/zTreeStyle/zTreeStyle.css"  />
	<link rel="stylesheet" href="${ctx}/assets/css/biz/productivity/perBenefit2.css"/>
</head>
<body>

<div class="perbenefit-board" id="perbenefitBoard">
	<div class="rightBody">
		<div id="page-one" class="rightBodyPage">
			<div class="row ct-row">

				<div class="col-sm-2 ct-20 ct-line-col">
					<div class="top-div">
						<div class="index-common-title">
							<div class="index-common-title-left"></div>
							<div class="index-common-title-text">人均效益</div>
							<div class="index-common-title-right">
								<span class="select">万元</span>
							</div>
						</div>
						<div class="body-div">
							<div id="benefit" class="content">
								<div class="data nodata">数据加载中</div>
								<div class="data hide cursorpointer">
									<div class="accord-yj-float">
										<span class="accord-yj-float-value" >0</span>
									</div>
									<div class="accord-bottom-float">
										<div class="accord-bottom-float-text">较上月</div>
										<div class="accord-bottom-float-value">0%</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-sm-1 ct-3 padding0 textaligncenter">
					<div class="height180 padding0 paddingtop10 paddingbottom10 symbol"><div class="margintop55 fontsize30">=</div></div>
				</div>

				<div class="col-sm-2 ct-20 ct-line-col">
					<div class="top-div">
						<div class="index-common-title">
							<div class="index-common-title-left"></div>
							<div class="index-common-title-text">营业利润</div>
							<div class="index-common-title-right">
								<span class="select">万元</span>
							</div>
						</div>
						<div class="body-div">
							<div id="profit" class="content">
								<div class="data nodata">数据加载中</div>
								<div class="data hide">
									<div class="accord-yj-float">
										<span class="accord-yj-float-value" >0</span>
									</div>
									<div class="accord-bottom-float">
										<div class="accord-bottom-float-text">较上月</div>
										<div class="accord-bottom-float-value">0%</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-sm-1 ct-3 padding0 textaligncenter">
					<div class="height180 padding0 paddingtop10 paddingbottom10 symbol"><div class="margintop55 fontsize30">/</div></div>
				</div>

				<div class="col-sm-3 ct-20 ct-line-col">
					<div class="top-div">
						<div class="index-common-title">
							<div class="index-common-title-left"></div>
							<div class="index-common-title-text">等效全职员工数<img id="capitabeneDx" src="${ctx}/assets/img/base/tip.gif" data-toggle="tooltip" data-placement="bottom" data-original-title="一种人力投入衡量方法，把全职员工加班时数及兼职员工工作时数折算为全职员工人数"/></div>
							<div class="index-common-title-right">
								<span class="select">人</span>
							</div>
						</div>
						<div class="body-div">
							<div id="employeenumber" class="content">
								<div class="data nodata">数据加载中</div>
								<div class="data hide">
									<div class="accord-yj-float">
										<span class="accord-yj-float-value" >0</span>
									</div>
									<div class="accord-bottom-float">
										<div class="accord-bottom-float-text">较上月</div>
										<div class="accord-bottom-float-value">0%</div>
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
				<div class="col-md-12 ct-line-col SetUpBody" view="chart">
					<div class="index-common-title bottom-title">
						<div class="index-common-title-left bottom-left"></div>
						<div class="index-common-title-text bottom-text">人均效益趋势</div>

						<div class="rightSetUpBtn">
							<div class="rightSetUpBtnDiv rightSetUpLeft width45 rightSetUpBtnSelect" data-key="profit">
								<div class="rightSetUpBtnTop"></div>
								<div class="text">月</div>
							</div>
							<div class="rightSetUpBtnDiv rightSetUpRight width45" data-key="profit">
								<div class="rightSetUpBtnTop"></div>
								<div class="text">年</div>
							</div>
						</div>

					</div>

					<div class="bottom-div clearfix">
						<div class="chart-view">
							<div class="col-xs-12 padding0 paddingtop10" id="benefitTrendMonth">
								<div class="col-sm-8 col-xs-12 marginbottom10"  view="chart">

									<div class="index-common-title bottom-title borderbottom">
										<div class="rightSetUpBtn top0">
											<div class="rightSetUpBtnDiv rightSetUpLeft width45 rightSetUpBtnSelect" data-key="profit">
												<div class="rightSetUpBtnTop"></div>
												<div class="glyphicon glyphicon-stats"></div>
											</div>
											<div class="rightSetUpBtnDiv rightSetUpRight width45" data-key="profit">
												<div class="rightSetUpBtnTop"></div>
												<div class="glyphicon glyphicon-th"></div>
											</div>
										</div>
									</div>
									<div class="bottom-div clearfix bordertop0 padding5 height350">
										<div class="chart-view">
											<div class="loadingmessage">数据加载中</div>
											<div class="data hide" id="profitMonthChart"></div>
										</div>
										<div class="table-view">
											<div class="loadingmessage">数据加载中</div>
											<div class="data hide" id="profitMonth">
												<table id="profitMonthGrid"></table>
											</div>
										</div>
									</div>

								</div>
								<div class="col-sm-4 col-xs-12 marginbottom10">
									<div id="warnMonth">
										<div class="warn-title">
											<span>预警</span>
										</div>
										<div class="warn-detail trade-warn clearfix">
											<div class="loadingmessage">数据加载中</div>
										</div>
										<div class="horizontal-line"></div>
										<div class="warn-detail recent-warn">
											<div class="loadingmessage">数据加载中</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="table-view">
							<div class="col-xs-12 padding0 paddingtop10" id="benefitTrendYear">
								<div class="col-sm-8 col-xs-12 marginbottom10" view="chart">

									<div class="index-common-title bottom-title borderbottom">
										<div class="rightSetUpBtn top0">
											<div class="rightSetUpBtnDiv rightSetUpLeft width45 rightSetUpBtnSelect" data-key="profit">
												<div class="rightSetUpBtnTop"></div>
												<div class="glyphicon glyphicon-stats"></div>
											</div>
											<div class="rightSetUpBtnDiv rightSetUpRight width45" data-key="profit">
												<div class="rightSetUpBtnTop"></div>
												<div class="glyphicon glyphicon-th"></div>
											</div>
										</div>
									</div>
									<div class="bottom-div clearfix bordertop0 padding5 height350">
										<div class="chart-view">
											<div class="loadingmessage">数据加载中</div>
											<div class="data hide" id="profitYearChart"></div>
										</div>
										<div class="table-view">
											<div class="loadingmessage">数据加载中</div>
											<div class="data hide" id="profitYear">
												<table id="profitYearGrid"></table>
											</div>
										</div>
									</div>

								</div>
								<div class="col-sm-4 col-xs-12 marginbottom10">
									<div id="warnYear">
										<div class="warn-title">
											<span>预警</span>
										</div>
										<div class="warn-detail trade-warn clearfix"></div>
										<div class="horizontal-line"></div>
										<div class="warn-detail recent-warn">
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row ct-row">

				<div class="col-sm-8 ct-line-col SetUpBody" view="chart">
					<div class="index-common-title bottom-title">
						<div class="index-common-title-left bottom-left"></div>
						<div class="index-common-title-text bottom-text">当前组织人均效益</div>

						<div class="rightSetUpBtn">
							<div class="rightSetUpBtnDiv rightSetUpLeft width45 rightSetUpBtnSelect" data-key="orgBenefit">
								<div class="rightSetUpBtnTop"></div>
								<div class="glyphicon glyphicon-stats"></div>
							</div>
							<div class="rightSetUpBtnDiv rightSetUpRight width45" data-key="orgBenefit">
								<div class="rightSetUpBtnTop"></div>
								<div class="glyphicon glyphicon-th"></div>
							</div>
						</div>

					</div>

					<div class="bottom-div clearfix padding5">
						<div class="chart-view">
							<div class="col-xs-12 padding0">
								<div class="col-sm-8 col-xs-12 padding0 height350">
									<div class="data loadingmessage">数据加载中</div>
									<div class="data height350 hide" id="orgBenefitChart"></div>
								</div>
								<div class="col-sm-4 col-xs-12 padding0">
									<div class="data loadingmessage">数据加载中</div>
									<div class="data hide" id="orgBenefitText"></div>
								</div>
							</div>
						</div>
						<div class="table-view">
							<div class="col-xs-12 padding0 height350">
								<div class="data loadingmessage">数据加载中</div>
								<div class="data height350 hide" id="orgBenefit">
									<table id="orgBenefitGrid"></table>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-sm-4 ct-line-col SetUpBody" view="chart">
					<div class="index-common-title bottom-title">
						<div class="index-common-title-left bottom-left"></div>
						<div class="index-common-title-text bottom-text">等效全职员工数(FTE)</div>
					</div>

					<div class="bottom-div clearfix padding5">
						<div class="chart-view">
							<div class="col-xs-12 padding0 height350">
								<div class="data loadingmessage">数据加载中</div>
								<div class="data height350 hide" id="fteChart"></div>
							</div>
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
						<div id="increaseChart" class="be-change-chart"></div>
					</div>
					<div class="col-xs-6">
						<div class="be-change-title"><label>降幅前3的组织机构 </label></div>
						<div id="decreaseChart" class="be-change-chart"></div>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/js/biz/productivity/perBenefit2.js"></script>
</body>
</html>
