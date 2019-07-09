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

<div class="perbenefit-board" id="perbenefitBoard">
	<div class="rightBody">
		<div id="page-one" class="rightBodyPage">
			<div class="row ct-row">

				<div class="col-sm-3 ct-22 ct-line-col">
					<div class="top-div">
						<div class="index-common-title">
							<div class="index-common-title-left"></div>
							<div class="index-common-title-text">人均效益</div>
							<div class="index-common-title-right" id="benefitsToolbar">
								<span data-id="income" class="select">收入</span>
								<span data-id="gain">毛利</span>
							</div>
						</div>
						<div class="body-div" id="benefitsArea">
							<div class="accord-yj-float">
								<span class="accord-yj-float-value">0</span>
								<span class="accord-yj-float-people">万元</span>
							</div>
							<div class="accord-bottom-float">
								<div class="accord-bottom-float-text">较上月</div>
								<div class="accord-bottom-float-value">0%</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-sm-3 ct-22 ct-line-col">
					<div class="top-div">
						<div class="index-common-title">
							<div class="index-common-title-left"></div>
							<div class="index-common-title-text">万元薪资</div>
							<div class="index-common-title-right" id="payToolbar">
								<span data-id="income">收入</span>
								<span data-id="gain">毛利</span>
							</div>
						</div>
						<div class="body-div" id="payArea">
							<div class="accord-yj-float">
								<span class="accord-yj-float-value">0</span>
							</div>
							<div class="accord-bottom-float">
								<div class="accord-bottom-float-value accord-value-gray">2016年</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-sm-3 ct-22 ct-line-col">
					<div class="top-div">
						<div class="index-common-title">
							<div class="index-common-title-left"></div>
							<div class="index-common-title-text">人力资金执行率</div>
						</div>
						<div class="body-div" id="perBenefitsArea">
							<div class="accord-yj-float">
								<span class="accord-yj-float-value">0</span>
								<span class="accord-yj-float-people">%</span>
							</div>
							<div class="accord-bottom-float">
								<div class="accord-bottom-float-value accord-value-gray">2016年人力资金执行率</div>
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
				<div class="col-md-6 ct-line-col SetUpBody" view="chart">
					<div class="index-common-title bottom-title">
						<div class="index-common-title-left bottom-left"></div>
						<div class="index-common-title-text bottom-text">人均效益趋势</div>

						<div class="rightSetUpBtn">
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

					<div class="bottom-div clearfix">
						<div class="bottom-div-search">
							<div id="profitDate"></div>
						</div>
						<div class="chart-view">
							<div class="col-xs-12 padding0">
								<div class="data height350" id="profitChart"></div>
							</div>
						</div>
						<div class="table-view">
							<div class="col-xs-12 padding0 height350">
								<div class="height40"></div>
								<div class="data" id="profit">
									<table id="profitGrid"></table>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-sm-6 ct-line-col SetUpBody" view="chart">
					<div class="index-common-title bottom-title">
						<div class="index-common-title-left bottom-left"></div>
						<div class="index-common-title-text bottom-text">下级组织人均效益</div>

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

					<div class="bottom-div clearfix">
						<div class="bottom-div-search">
							<div id="orgBenefitDate"></div>
						</div>
						<div class="chart-view">
							<div class="col-xs-12 padding0">
								<div class="data height350" id="orgBenefitChart"></div>
							</div>
						</div>
						<div class="table-view">
							<div class="col-xs-12 padding0 height350">
								<div class="height40"></div>
								<div class="data" id="orgBenefit">
									<table id="orgBenefitGrid"></table>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-6 ct-line-col SetUpBody" view="chart">
					<div class="index-common-title bottom-title">
						<div class="index-common-title-left bottom-left"></div>
						<div class="index-common-title-text bottom-text">万元薪资趋势分析</div>
					</div>

					<div class="bottom-div clearfix">
						<div class="bottom-div-search">
							<div id="payDate"></div>
						</div>
						<div class="col-xs-12 padding0">
							<div class="data height350" id="payChart"></div>
						</div>
					</div>
				</div>

				<div class="col-sm-6 ct-line-col SetUpBody" view="chart">
					<div class="index-common-title bottom-title">
						<div class="index-common-title-left bottom-left"></div>
						<div class="index-common-title-text bottom-text">万元薪资下级组织对比</div>
					</div>

					<div class="bottom-div clearfix">
						<div class="bottom-div-search">
							<div id="paySubOrganDate"></div>
						</div>
						<div class="col-xs-12 padding0">
							<div class="data height350" id="paySubOrganChart"></div>
						</div>
					</div>
				</div>

				<div class="col-md-6 ct-line-col SetUpBody" view="chart">
					<div class="index-common-title bottom-title">
						<div class="index-common-title-left bottom-left"></div>
						<div class="index-common-title-text bottom-text">人力资金执行率趋势分析</div>
					</div>

					<div class="bottom-div clearfix">
						<div class="bottom-div-search">
							<div id="executeRateDate"></div>
						</div>
						<div class="col-xs-12 padding0">
							<div class="data height350" id="executeRateChart"></div>
						</div>
					</div>
				</div>

				<div class="col-sm-6 ct-line-col SetUpBody" view="chart">
					<div class="index-common-title bottom-title">
						<div class="index-common-title-left bottom-left"></div>
						<div class="index-common-title-text bottom-text">人力资金执行率下级组织对比</div>
					</div>

					<div class="bottom-div clearfix">
						<div class="bottom-div-search">
							<div id="executeRateSubOrganDate"></div>
						</div>
						<div class="col-xs-12 padding0">
							<div class="data height350" id="executeRateSubOrganChart"></div>
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

<script type="text/javascript"src="${ctx}/assets/js/require.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/lib/plugins/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/biz/productivity/perBenefit.js"></script>
</body>
</html>
