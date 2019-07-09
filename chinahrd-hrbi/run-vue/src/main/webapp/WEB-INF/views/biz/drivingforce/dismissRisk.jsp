<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<!-- jsp文件头和头部 -->
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>人才流失风险</title>
	<link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"  />
	<link rel="stylesheet" href="${ctx}/assets/css/biz/drivingforce/dismissRisk.css"/>
</head>
<body>
	<div class="page-content" id="main-container">
		<div class="main-container-inner">
			<div class="row column">
				<input type="hidden" id="reqOrganId" value="${reqOrganId}" />
				<input type="hidden" id="reqOrganName" value="${reqOrganName}" />
			</div>

			<div class="row column">
				<div class="col-xs-12">
					<ul class="nav nav-tabs f-white-bg" role="tablist" id="dimissTypeTabs">
						<li role="presentation" class="active">
							<a href="#dimissRiskTab" aria-controls="dimissRiskTab" role="tab" data-toggle="tab">人才流失风险</a>
						</li>
						<li role="presentation">
							<a href="#preDimissRiskTab" aria-controls="preDimissRiskTab" role="tab" data-toggle="tab">
								流失风险回顾
							</a>
						</li>
					</ul>
					
					<!-- Tab panes -->
					<div class="tab-content">
						<!-- 人才流失风险 -->
						<div role="tabpanel" class="tab-pane active" id="dimissRiskTab">
							<!-- 人才离职风险 -->
							<div class="row">
								<div class="col-sm-12 col-xs-12 widget-box transparent f-white-bg ">
									<div class="widget-header widget-header-flat">
										<h4 class="bolder">人才离职风险</h4>
									</div>
									<!-- 人才离职风险tab -->
									<div class="widget-body col-xs-12">
										<ul class="nav nav-tabs" role="tablist" id="subDimissRiskTabs">
											<li role="presentation" class="active">
												<a href="#directEmpTab" aria-controls="directEmpTab" role="tab" data-toggle="tab">
													直接下属
												</a>
											</li>
											<li role="presentation">
												<a href="#subEmpTab" aria-controls="subEmpTab" role="tab" data-toggle="tab">
													全部下属
												</a>
											</li>
										</ul>
										<div class="tab-content">
											<!-- 直接下属tab -->
											<div role="tabpanel" class="tab-pane active " id="directEmpTab">
												<div class="row">
													<div class="col-xs-12" id="talentTypeCheck">
														<label class="">人才类型 :</label>
														<c:forEach var="item" items="${talentType}" varStatus="status">
															<label class="checkbox-inline">
																<input type="checkbox" value="${item.id}" autocomplete="off" checked>
																${item.name}类
															</label>
														</c:forEach>
													</div>
												</div>
												<div class="row">
													<div class="col-md-4 col-sm-5 col-xs-12">
														<div class="row">
															<div class="col-xs-8 nowrap">离职风险点分析：</div>
															<div class="col-xs-4 nowrap">单位：人</div>
														</div>
														<div class="row">
															<div class="col-xs-12 risk-bar" id="directRiskBar"></div>
														</div>
													</div>
													<div class="col-md-8 col-sm-7 col-xs-12">
														<div class="row"><span id="riskTitle">离职风险人数</span>：<span id="riskNum">0</span>人</div>
														<div class="row" id="riskEmpDetail">
														</div>
													</div>
												</div>
											</div>
											<!-- 下级组织员工tab -->
											<div role="tabpanel" class="tab-pane " id="subEmpTab">
												<div class="row">
													<div class="col-md-2 col-sm-2 " id="reportLv">
														<div class="row">
															<div class="col-xs-12">汇报层级</div>
														</div>
														<div class="row report-tree">
															<ul id="subEmpTree" class="ztree"></ul>
														</div>
													</div>
													<div class="col-md-10 col-sm-10" >
														<div class="col-md-12 col-sm-12" id="chartArea">
															<div class="col-md-4 col-sm-4">
																<div class="row">
																	<div class="col-xs-12">离职风险点分析：</div>
																</div>
																<div class="row">
																	<div class="col-xs-12 risk-bar" id="subEmpRiskBar"></div>
																</div>
															</div>
															<div class="col-md-8 col-sm-8" id="subEmpRiskTable">
																<table class="borderless" id="subEmpRiskGrid"></table>
															</div>
														</div>
														<div class="col-md-12 col-sm-12"  id="rigthArea">无离职风险人员</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div><!-- 人才离职风险end -->
							
							<!-- 当前组织离职风险分析 -->
							<div class="row col-row-mt20">
								<div class="col-xs-12 widget-box transparent f-white-bg">
									<div class="widget-header widget-header-flat">
										<h4 class="bolder">直接下属离职风险分析</h4>
									</div>
			
									<div class="widget-body current-organ-boby">
										<div class="row">
<!-- 											<div class="col-md-4 col-xs-12" class="curr-organ-pie"></div> -->
<!-- 											<div class="col-md-4 col-xs-12"></div> -->
<!-- 											<div class="col-md-4 col-xs-12"></div> -->
										</div>
									</div>
								</div>
							</div><!-- 当前组织离职风险分析end -->
						</div>
						<!-- 流失风险回顾 -->
						<div role="tabpanel" class="tab-pane" id="preDimissRiskTab">
							<div class="row">
								<div class="widget-box transparent f-white-bg">
									<div class="widget-header widget-header-flat">
										<h4 class="bolder">离职预测回归分析</h4>
									</div>
			
									<div class="widget-body">
										<div class="row column">
											<div class="col-md-4 col-sm-5 col-xs-12">
												<div id="dimissRiskReviewPie" class="col-xs-12"></div>
												<span class="dimissRiskReviewTitle">说明：“有预警提示”为离职前<span id="riskWarningDesc">半年</span>内有评估过离职预警</span>
											</div>
											<div class="col-md-8 col-sm-7 col-xs-12" id="dimissRiskReviewTable">
												<label>&nbsp;</label>
												<table  class="borderless" id="dimissRiskReviewGrid"></table>
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
	
	<!-- 离职风险详情(Modal) -->
	<div class="modal fade" id="riskDetailModal" tabindex="-1" role="dialog"
		 aria-labelledby="modalLabel" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true"> &times;</span>
					</button>
					<h5 class="modal-title">离职风险详情</h5>
				</div>
				<div class="modal-body">
					<!-- 员工基本信息 -->
					<div class="row base-info">
						<div class="col-xs-12 ">
							<div class="row">
								<!-- 左边头像 -->
								<div class="col-xs-2 ">
									<img class="img-circle head-pic" src="">
								</div>
								<!-- 右边内容 -->
								<div class="col-xs-10">
									<div class="row">
										<div class="col-xs-12">
											姓名 : <span></span>
										</div>
										<div class="col-xs-12">
											离职风险 : <span class="riskFlag"></span>
										</div>
										<div class="col-xs-12">
											状态标签 : <span></span>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-6">
							<div class="row">
								<div class="col-xs-11 ">离职风险详情信息:</div>
								<div class="col-xs-11 risk-detail-info">
									<div></div>
								</div>
							</div>
						</div>
						<div class="col-xs-6">
							<div class="row">
								<div class="col-xs-11">BP建议:</div>
								<div class="col-xs-11 suggest-info">
									<div>&nbsp;</div>
								</div>
							</div>
						</div>
					</div>
					
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/js/biz/drivingforce/dismissRisk.js"></script>
</body>
</html>
