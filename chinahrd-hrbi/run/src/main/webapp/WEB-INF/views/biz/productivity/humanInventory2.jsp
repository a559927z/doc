<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>项目人力盘点</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/datetime/datetimepicker.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/productivity/humanInventory.css"/>
</head>
<body>
<input type="hidden" id="projectFeeType" value="${projectFeeType}"/>
<input type="hidden" id="inventoryDateType" value="${inventoryDateType}"/>
<div class="human-inventory" id="trainBoard">
    <div class="leftBody">
        <div class="leftListBigDiv">项目人力盘点</div>
        <div page="page-one" class="leftListDiv selectList">项目总览</div>
        <div page="page-two" class="leftListDiv">明细分析</div>
        <div page="page-three" class="leftListDiv" style="display: none;">数据导入</div>
    </div>

    <div class="rightBody rightBody10">
        <div id="page-one" class="rightBodyPage">
			<div class="col-sm-3 ct-22 ct-line-col">
				<div class="top-div">
					<div class="index-common-title">
						<div class="index-common-title-left"></div>
						<div class="index-common-title-text">项目数</div>
						<div class="index-common-title-right" id="projectNum_toolbar">
							<span data-id="budget" class="select">总数</span>
							<span data-id="accumulative">人均</span>
						</div>
					</div>
					<div class="body-div" id="perfDismissArea">
						<div class="accord-yj-float">
							<span class="accord-yj-float-value" id="projectNum">0</span>
							<span class="accord-yj-float-people">个</span>
						</div>
						<div class="accord-bottom-float">
							<div class="accord-bottom-float-text">
								<span id="projectNumCurrentYear"></span>
								<span id="projectNumText"></span>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="col-sm-3 ct-22 ct-line-col">
				<div class="top-div">
					<div class="index-common-title">
						<div class="index-common-title-left"></div>
						<div class="index-common-title-text">投入产出</div>
						<div class="index-common-title-right" id="investment_toolbar">
							<span data-id="budget" class="select">投入</span>
							<span data-id="accumulative">产出</span>
						</div>
					</div>
					<div class="body-div" id="keyDismissArea">
						<div class="accord-yj-float">
							<span class="accord-yj-float-value" id="projectInputOutputNum">0</span>
							<span class="accord-yj-float-people">万元</span>
						</div>
						<div class="accord-bottom-float">
							<div class="accord-bottom-float-text">
								<span id="inputOutputCurrentYear"></span>
								<span id="inputOutputText"></span>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="col-sm-3 ct-22 ct-line-col">
				<div class="top-div">
					<div class="index-common-title">
						<div class="index-common-title-left"></div>
						<div class="index-common-title-text">项目负荷</div>
					</div>
					<div class="body-div">
						<div class="accord-yj-float">
							<span class="accord-yj-float-value" id="projectLoadNum">0</span>
							<span class="accord-yj-float-people">%</span>
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
					<div class="index-common-title-text bottom-text">投入产出分析</div>

					<div class="rightSetUpBtn">
						<div class="rightSetUpBtnDiv rightSetUpLeft rightSetUpBtnSelect">
							<div class="rightSetUpBtnTop"></div>
							<div class="text">金额分析</div>
						</div>
						<div class="rightSetUpBtnDiv rightSetUpRight">
							<div class="rightSetUpBtnTop"></div>
							<div class="text">产出投入比</div>
						</div>
					</div>
				</div>
				<div class="bottom-div">
					<div class="chart-view">
						<div class="col-xs-12 marginTop20 height12"><span>(万元)</span></div>
						<div class="col-xs-12 height350" id="amountChart"></div>
					</div>
					<div class="table-view">
						<div class="col-xs-12 bottom-div-first"></div>
						<div class="col-xs-12 " id="outputInputPercentChart"></div>
					</div>
				</div>
			</div>

			<div class="col-sm-6 ct-line-col SetUpBody">
				<div class="index-common-title bottom-title">
					<div class="index-common-title-left bottom-left"></div>
					<div class="index-common-title-text bottom-text">投入产出地图</div>
				</div>
				<div class="bottom-div">
					<div class="chart-view">
						<div class="col-xs-12 marginTop20 height12"><span>产出（万元）</span></div>
						<div class="col-xs-12 height350" id="inputOutputMapChart"></div>
					</div>
				</div>
			</div>

			<div class="col-sm-6 ct-line-col SetUpBody">
				<div class="index-common-title bottom-title">
					<div class="index-common-title-left bottom-left"></div>
					<div class="index-common-title-text bottom-text">盈亏项目数分析</div>
				</div>
				<div class="bottom-div">
					<div class="chart-view">
						<div class="col-xs-12 marginTop20 height12"><span>(个)</span></div>
						<div class="col-xs-12 height320" id="profitLossProjectChart"></div>
						<div class="col-xs-12">
							<span class="text-date">操作提示：点击产品数可查看产品的投入产出</span>
						</div>
					</div>
				</div>
			</div>

			<div class="col-sm-6 ct-line-col SetUpBody">
				<div class="index-common-title bottom-title">
					<div class="index-common-title-left bottom-left"></div>
					<div class="index-common-title-text bottom-text">盈亏金额分析</div>
				</div>
				<div class="bottom-div">
					<div class="chart-view">
						<div class="col-xs-12 marginTop20 height12"><span>(万元)</span></div>
						<div class="col-xs-12 height320" id="profitLossAmountChart"></div>
						<div class="col-xs-12">
							<span class="text-date">操作提示：点击金额数可查看产品的投入产出</span>
						</div>
					</div>
				</div>
			</div>

			<div class="col-sm-6 ct-line-col SetUpBody" view="chart">
				<div class="index-common-title bottom-title">
					<div class="index-common-title-left bottom-left"></div>
					<div class="index-common-title-text bottom-text">项目投入统计</div>

					<div class="rightSetUpBtn">
						<div class="rightSetUpBtnDiv rightSetUpLeft rightSetUpBtnSelect">
							<div class="rightSetUpBtnTop"></div>
							<div class="text">费用统计</div>
						</div>
						<div class="rightSetUpBtnDiv rightSetUpRight">
							<div class="rightSetUpBtnTop"></div>
							<div class="text">人力统计</div>
						</div>
					</div>
				</div>
				<div class="bottom-div">
					<div class="chart-view">
						<div class="col-xs-12 marginTop20 height12"><span>(万元)</span></div>
						<div class="col-xs-12 height350" id="trainCostChart" ></div>
					</div>
					<div class="table-view">
						<div class="col-xs-12 marginTop20 height12"><span>(人天)</span></div>
						<div class="col-xs-12 height350" id="projectManpowerChart" ></div>
					</div>
				</div>
			</div>

			<div class="col-sm-6 ct-line-col SetUpBody">
				<div class="index-common-title bottom-title">
					<div class="index-common-title-left bottom-left"></div>
					<div class="index-common-title-text bottom-text">项目类型分析</div>
				</div>

				<div class="bottom-div">
					<div class="chart-view">
						<div class="col-xs-12" id="projectTypeLegend" ></div>
						<div class="col-xs-12" id="projectTypeChart" ></div>
						<div class="col-xs-12 imgLegend" id="projectTypeLegend2" ></div>
					</div>
				</div>
			</div>
        </div>
		<div id="page-two" class="rightBodyPage">
			<div class="col-sm-12 SetUpBody padding0" view="employee">
				<div class="index-jxmb-tab">
					<div page="project" class="index-jxmb-btn index-jxmb-btn-select">项目列表</div>
					<div page="employee" class="index-jxmb-btn">人员统计表</div>
				</div>
				<div class="bottom-div">
					<div class="project-view" view="participate">
						<div class="index-proj-tab">
							<table width="100%" height="100%" border="0" cellspacing="0"
								cellpadding="0">
								<tr>
									<td align="center" valign="middle">
										<table width="120px" border="0" align="center"
											cellpadding="0" cellspacing="0">
											<tr>
												<td>
													<div page="dominant" class="index-proj-btn index-proj-btn-select"
														data-toggle="tooltip" data-placement="bottom" 
														data-original-title="主导项目：由当前组织引导全局并推动全局发展的项目">主导项目</div>
												</td>
												<td>
													<div page="participate" class="index-proj-btn"
														data-toggle="tooltip" data-placement="bottom" 
														data-original-title="参与项目：当前组织只提供人员支持的项目">参与项目</div>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</div>
						<div class="dominant-view">
							<div class="col-sm-12 marginBottom20" id="projectSearchBox">
								<div class="dis-search">
									<input class="dis-search-input paddingLeft10"
										id="projectSearchTxt" type="text" placeholder="请输入项目名称">
									<div class="add-on dis-search-input-btn" id="projectSearchBtn">搜索</div>
								</div>
								<div class="row" id="moreSearch">
									<label class="more-search-label icon-panel-down"
										id="moreLabel">更多筛选条件</label>
								</div>
								<form id="dominantConditionSearchForm" action="">
									<div class="condition-body hide" id="dominantCondition">
										<div class="condition-body-list">
											<div class="condition-body-list-title pull-left">负责人：</div>
											<input type="text" id="principal"
												class="condition-input-text pull-left textInput margin0 paddingLeft10 width248">
										</div>
										<div class="condition-body-list">
											<div class="condition-body-list-title pull-left textInput">人力投入：</div>
											<select class="condition-input-text pull-left textInput width60"
												id="manpowerInputSelect" name="">
												<option selected="selected" value="thisMonth">本月</option>
												<option value="thisYear">本年</option>
												<option value="lastMonth">上月</option>
											</select> <input type="text" id="startManpowerInput"
												class="condition-input-text pull-left textInput width80 marginLeft10"> <span
												class="condition-text-one pull-left textInput">-</span> <input
												type="text" id="endManpowerInput"
												class="condition-input-text condition-input-text-too pull-left textInput width80">
											<div class="left textInput">&nbsp;人天</div>
											<div class=" pull-left">&nbsp;&nbsp;&nbsp;&nbsp;</div>
											<div class="condition-body-list-title pull-left textInput">费用投入：</div>
											<select class="condition-input-text pull-left textInput width60"
												id="feeInputSelect" name="">
												<option selected="selected" value="thisMonth">本月</option>
												<option value="thisYear">本年</option>
												<option value="lastMonth">上月</option>
											</select> <input type="text" id="startFeeInput"
												class="condition-input-text pull-left textInput width80 marginLeft10"> <span
												class="condition-text-one pull-left textInput">-</span> <input
												type="text" id="endFeeInput"
												class="condition-input-text condition-input-text-too pull-left textInput width80">
											<div class="left textInput">&nbsp;万元</div>
										</div>
										<div class="condition-body-list">
											<div class="condition-body-list-title pull-left textInput">项目利润：</div>
											<select class="condition-input-text pull-left textInput width60"
												id="projectGainSelect" name="">
												<option selected="selected" value="thisMonth">本月</option>
												<option value="thisYear">本年</option>
												<option value="lastMonth">上月</option>
											</select> <input type="text" id="startProjectGain"
												class="condition-input-text pull-left textInput width80 marginLeft10"> <span
												class="condition-text-one pull-left textInput">-</span> <input
												type="text" id="endProjectGain"
												class="condition-input-text condition-input-text-too pull-left textInput width80">
											<div class="left textInput">&nbsp;万元</div>
											<div class=" pull-left">&nbsp;&nbsp;&nbsp;&nbsp;</div>
											<div class="condition-body-list-title pull-left textInput">项目时间：</div>
											<div class="form-group">
												<div class="input-group date form_date col-md-1 pull-left"
													data-date="" data-date-format="dd MM yyyy"
													data-link-field="dtp_input" data-link-format="yyyy-mm-dd">
													<input id="startProjectTime"
														class="form-control textInput width90" type="text" value="" readonly>
													<span class="input-group-addon">
														<span class="clearText fontSize8">╳</span>
													</span> 
													<span class="input-group-addon">
														<span class="glyphicon glyphicon-calendar"></span>
													</span>
												</div>
												<span class="condition-text-one pull-left textInput">-</span>
												<div class="input-group date form_date col-md-1 pull-let"
													data-date="" data-date-format="dd MM yyyy"
													data-link-field="dtp_input" data-link-format="yyyy-mm-dd">
													<input id="endProjectTime" class="form-control textInput width90"
														type="text" value="" readonly>
													<span class="input-group-addon">
														<span class="clearText fontSize8">╳</span>
													</span> 
													<span class="input-group-addon">
														<span class="glyphicon glyphicon-calendar"></span>
													</span>
												</div>
											</div>
										</div>
										<div class="condition-body-list">
											<select class="condition-input-text pull-left textInput marginLeft30"
												id="projectProgressSelect" name="">
												<option selected="selected" value="">项目进度</option>
												<c:forEach items="${listProgress}" var="item"
													varStatus="status">
													<option value="${item.id}">${item.projectProgress}</option>
												</c:forEach>
											</select>
										</div>
										<div class="condition-bottom">
											<div id="dominantConditionSearch" class="customBtn">搜索</div>
											<div id="dominantConditionReset" class="clearBtn">清除</div>
										</div>
									</div>
								</form>
							</div>
							<div class="table-body">
								<div class="clearfix"></div>
								<div class="col-md-12 col-xs-12 marginBottom20">说明：费用投入及利润统计单位为：万元；点击“人力总投入”及“费用总投入”可查看明细分析</div>
								<div class="col-md-12 col-xs-12" id="leadingProjectTable">
									<table class="borderless" id="leadingProjectGrid"></table>
									<table id="leadingProjectSel"></table>
								</div>
							</div>
						</div>
						<div class="participate-view hide">
							<div class="col-sm-12 marginBottom20"
								id="participateSearchBox">
								<div class="dis-search">
									<input class="dis-search-input paddingLeft10" id="participateSearchTxt"
										type="text" placeholder="请输入项目名称">
									<div class="add-on dis-search-input-btn" id="participateSearchBtn">搜索</div>
								</div>
								<div class="row" id="moreSearch">
									<label class="more-search-label icon-panel-down"
										id="participateMoreLabel">更多筛选条件</label>
								</div>
								<form id="participateConditionSearchForm" action="">
									<div class="condition-body hide" id="participateCondition">
										<div class="col-sm-12 ct-line-col conditionBtnListBody">
											<div class="div-bottom-line"></div>
										</div>
										<div class="condition-body-list textInput">
											<div class="condition-body-list-title pull-left textInput">人力投入：</div>
											<select class="condition-input-text pull-left textInput"
												id="participateManpowerInputSelect">
												<option selected="selected" value="thisMonth">本月</option>
												<option value="thisYear">本年</option>
												<option value="lastMonth">上月</option>
											</select> <input type="text" id="participateStartInput"
												class="condition-input-text pull-left textInput marginLeft10"> <span
												class="condition-text-one pull-left textInput">-</span> <input
												type="text" id="participateEndInput"
												class="condition-input-text condition-input-text-too pull-left textInput">
											<div class="left textInput">&nbsp;人天</div>
										</div>
										<div class="condition-body-list">
											<div class="condition-body-list-title pull-left textInput">项目时间：</div>
											<div class="form-group">
												<div class="input-group date form_date col-md-1 pull-left"
													data-date="" data-date-format="dd MM yyyy"
													data-link-field="dtp_input" data-link-format="yyyy-mm-dd">
													<input id="participateStartTime"
														class="form-control textInput width120" type="text" value="" readonly>
													<span class="input-group-addon"><span
														class="clearText fontSize8">╳</span></span> <span
														class="input-group-addon"><span
														class="glyphicon glyphicon-calendar"></span></span>
												</div>
												<span class="condition-text-one pull-left textInput">-</span>
												<div class="input-group date form_date col-md-1 pull-left"
													data-date="" data-date-format="dd MM yyyy"
													data-link-field="dtp_input" data-link-format="yyyy-mm-dd">
													<input id="participateEndTime"
														class="form-control textInput width120" type="text" value="" readonly>
													<span class="input-group-addon"><span
														class="clearText fontSize8">╳</span></span> <span
														class="input-group-addon"><span
														class="glyphicon glyphicon-calendar"></span></span>
												</div>
											</div>
											<select class="condition-input-text pull-left textInput marginLeft10"
												id="participateProjectProgressSelect" name="">
												<option selected="selected" value="">项目进度</option>
												<c:forEach items="${listProgress}" var="item"
													varStatus="status">
													<option value="${item.id}">${item.projectProgress}</option>
												</c:forEach>
											</select>
										</div>
										<div class="condition-bottom">
											<div id="participateConditionSearch" class="customBtn">搜索</div>
											<div id="participateConditionReset" class="clearBtn">清除</div>
										</div>
									</div>
								</form>
							</div>
							<div class="table-body">
								<div class="clearfix"></div>
								<div class="col-md-12 col-xs-12 marginBottom20">说明：点击”人力总投入“可查看明细分析</div>
								<div class="col-md-12 col-xs-12" id="participateDeailTable">
									<table class="borderless" id="participateDetailGrid"></table>
									<table id="participateDetailSel"></table>
								</div>
							</div>
						</div>
					</div>

					<div class="employee-view hide">
						<div class="col-sm-12 marginTop20 marginBottom20"
							id="employeeSearchBox">
							<div class="dis-search">
								<input class="dis-search-input paddingLeft10" id="employeeSearchTxt"
									type="text" placeholder="请输入员工ID或姓名">
								<div class="add-on dis-search-input-btn" id="employeeSearchBtn">搜索</div>
							</div>
							<div class="row" id="moreSearch">
								<label class="more-search-label icon-panel-down"
									id="employeeMoreLabel">更多筛选条件</label>
							</div>
							<form id="employeeConditionSearchForm" action="">
								<div class="condition-body hide" id="employeeCondition">
									<div class="col-sm-12 ct-line-col conditionBtnListBody">
										<div class="div-bottom-line"></div>
									</div>
									<div class="condition-body-list textInput">
										<div class="condition-body-list-title pull-left textInput width120">累计参与项目数：</div>
										<input type="text" id="startProjectNumInYear"
											class="condition-input-text pull-left textInput"> <span
											class="condition-text-one pull-left textInput">-</span> <input
											type="text" id="endProjectNumInYear"
											class="condition-input-text condition-input-text-too pull-left textInput">
										<div class="condition-body-list-title pull-left"></div>
										<div class="condition-body-list-title pull-left textInput width120">当前参与项目数：</div>
										<input type="text" id="startProjectNum"
											class="condition-input-text pull-left textInput"> <span
											class="condition-text-one pull-left textInput">-</span> <input
											type="text" id="endProjectNum"
											class="condition-input-text condition-input-text-too pull-left textInput">
									</div>
									<div
										class="condition-body-list condition-body-list-too textInput">
										<div class="condition-body-list-title pull-left textInput width120"
											id="isPrincipal1">是否担任过负责人：</div>
										<div class="condition-btn condition-btn-selected textInput"
											value="" id="allChecked">全部</div>
										<div class="condition-btn condition-btn-too textInput"
											value="1">是</div>
										<div class="condition-btn condition-btn-too textInput"
											value="0">否</div>
									</div>
									<div class="condition-bottom">
										<div id="employeeConditionSearch" class="customBtn">搜索</div>
										<div id="employeeConditionReset" class="clearBtn">清除</div>
									</div>
								</div>
							</form>
						</div>

						<div class="table-body">
							<div class="col-md-12 col-xs-12 marginBottom20">说明：击“姓名”可查看项目明细</div>
							<div class="col-md-12  col-xs-12" id="employeeDetailTable">
								<table class="borderless" id="employeeDetailGrid"></table>
								<table id="employeeDetailSel"></table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="page-three" class="rightBodyPage">
			<div class="col-sm-12 ct-col-line SetUpBody" >
				<div class="div-three-title title-font-weight">项目人力盘点数据导入</div>
				<div class="bottom-div">
					<form id="importProjectModelForm" enctype="multipart/form-data" method="post">
						<input type="text" name="organId" id="organIdHidden" class="display-none"/>
						<div class="col-sm-12 ct-col-line conditionBtnListBody" id="uploadData">
							<div class="col-sm-1 ct-col-line conditionBtnListBody">
								导入数据：
							</div>
							<div class="col-sm-11 ct-col-line conditionBtnListBody">
								<div id="CostOrPersonRadio">
									<label class="checkbox-inline upload-radio-padding" id="optionsRadiosCost">
										<input type="radio" name="optionsRadiosinline"
											value="optionCost" checked> 项目信息及费用数据
									</label>
									<label class="checkbox-inline" id="optionsRadiosPerson">
										<input type="radio" name="optionsRadiosinline"
											value="optionPerson"> 项目人员数据
									</label>
								</div>
								<div class="explain-style">
									<div id="downLoadProjectCostModel" class="hyperlink-style">
										下载《项目信息及费用数据》导入模板
									</div>
									<div id="downLoadProjectPersonModel" class="hyperlink-style display-none">
										下载《项目人员数据》导入模板
									</div>
								</div>
							</div>
							<div class="col-sm-12 ct-col-line conditionBtnListBody">
								<div class="div-bottom-line"></div>
							</div>
							<div class="col-sm-1 ct-col-line conditionBtnListBody">
								盘点时间：
							</div>
							<div class="col-sm-11 ct-col-line conditionBtnListBody">
								<div>
									<%-- <select class="checkbox-date-first" id="selectTotalId" name="selectTotalName">
									   <option selected="selected" value="month">月</option>
									   <option value="quarter">季度</option>
									   <option value="halfYear">半年</option>
									   <option value="year">年</option>
									</select> --%>
									<select class="checkbox-date-second" id="selectDetailId" name="selectDetailName"></select>
								</div>
								<%-- <div class="explain-style">
									说明：选择数据的时间范围。如果盘点数据为一个月，则结束时间选择空
								</div> --%>
							</div>
							<div class="col-sm-12 ct-col-line conditionBtnListBody">
								<div class="div-bottom-line"></div>
							</div>
							<div class="col-sm-1 ct-col-line conditionBtnListBody">
								选择文件：
							</div>
							<div class="col-sm-11 ct-col-line conditionBtnListBody">
								<input type="file" name="projectModelFile" id="projectModelFile">
							</div>
							<div class="col-sm-12 ct-col-line conditionBtnListBody">
								<div class="div-bottom-line"></div>
							</div>
							<div class="col-sm-12 ct-col-line conditionBtnListBody btn-style-center">
								<button type="button" class="btn btn-primary btn-lg btn-block btn-style-width" id="btn-form">
									<span id="btnImportText">导&nbsp;&nbsp;入</span>
									<span id="btnNowImportText" class="display-none">正&nbsp;在&nbsp;导&nbsp;入...</span>
								</button>
							</div>
						</div>
					</form>
				</div>
				<div class="div-three-title">导入状态:</div>
				<div id="importState" class="bottom-div-four display-none">
					<div id="templateInfo" class="col-sm-12 ct-col-line conditionBtnListBody display-none">
						<span class="span-font-warning">导入模板有误！</span>
						点击
						<span id="costType" class="hyperlink-style">下载《项目信息及费用数据》导入模板</span>
						<span id="personType" class="hyperlink-style display-none">下载《项目人员数据》导入模板</span>
						或选择正确的导入数据类型
					</div>
					<div id="fileInfo" class="col-sm-12 ct-col-line conditionBtnListBody display-none">
						<span id="fileErrorsId"></span>
					</div>
					<div id="importError" class="col-sm-12 ct-col-line conditionBtnListBody display-none">
						<span class="span-font-warning">数据导入失败，共检测到</span>
						<span class="span-font-warning" id="importErrorNum"></span>
						<span class="span-font-warning">条错误：</span>
						<div id="formalErrorTable">
							<%-- <table id="formalErrorGrid"></table> --%>
							<table class="table">
								<thead>
									<tr class="table-thead-tr">
										<th>序号</th>
										<th>错误位置</th>
										<th>错误信息</th>
									</tr>
								</thead>
								<tbody id="formalErrorGrid"></tbody>
							</table>
						</div>
					</div>
					<div id="importInfo" class="col-sm-12 ct-col-line conditionBtnListBody display-none">
						<div class="fontSize15" >
							已存在
							<span id="import-info-year">2016</span>
							年
							<span id="import-info-month">05</span>
							月项目信息数据，系统将执行以下操作：
						</div>
						<div id="updateAndDeleteTipsTable">
							<%-- <table id="updateAndDeleteTipsGrid"></table> --%>
							<table class="table">
								<thead>
									<tr class="table-thead-tr">
										<th>序号</th>
										<th>项目名称</th>
										<th>执行动作</th>
									</tr>
								</thead>
								<tbody id="updateAndDeleteTipsGrid"></tbody>
							</table>
						</div>
						<div class="condition-bottom" id="continueImportBtn">
							<div class="float-left">如果你已确认以上信息，请点击&nbsp;&nbsp;</div>
							<button id="continueImport" class="continue-btn">继续导入</button>
							<div class="float-left">&nbsp;&nbsp;或&nbsp;&nbsp;</div>
							<button id="cancelImport" class="continue-btn">取消导入</button>
						</div>
					</div>
					<div id="importSuccess" class="col-sm-12 ct-col-line conditionBtnListBody display-none">
						成功导入
						<span class="span-font-success" id="totalNum"></span>
						条数据！
						<span class="hyperlink-style" id="showDatas">点击查看</span>
					</div>
				</div>
			</div>
        </div>
    </div>
</div>
<!-- 数据导入 上传文件大小验证弹窗 began -->
<div class="modal fade popup-modal" id="importDataTemplate" tabindex="-1" role="dialog" 
     aria-labelledby="importDataTemplateLabel" aria-hidden="true">
     <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
            	<div class="modal-title" id="importDataTemplateLabel">警告</div>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">
            	<p id="importFileSizeError" class="modal-body-p"></p>
            </div>
        </div>
     </div>
</div>
<!-- 数据导入 上传文件大小验证弹窗 end -->

<!-- 本年人力 弹出框 begin -->
<div class="modal fade popup-modal" id="manpowerInYearModal" tabindex="-1" role="dialog" 
     aria-labelledby="manpowerInYearLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-text" id="manpowerInYearLabel">主导项目人力盘点</div>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">
            	<table border="0" class="marginBottom20">
               		<tr>
               			<td align="right"><label>时间：</label></td><td align="left"><label id="manpowerBeginDate"></label><label id="manpowerLine">-</label><label id="manpowerEndDate"></label></td>
               			<td align="right"><label>&nbsp;&nbsp;&nbsp;&nbsp;项目名称：</label></td><td align="left"><label id="manpowerProjectName"></label></td>
               			<td align="right"><label>&nbsp;&nbsp;&nbsp;&nbsp;人力总投入：</label></td><td align="left"><label id="manpowerInputNum"></label><label>人</label></td>
               		</tr>
               	</table>
                <ul class="nav nav-tabs f-white-bg" role="tablist" id="manpowerTabs">
                	<li role="presentation" class="active"><a href="#tabpanel" aria-controls="tabpanel1" role="tab" data-toggle="tab">当前人力投入</a></li>
                	<li role="presentation"><a href="#tabpanel" aria-controls="tabpanel2" role="tab" data-toggle="tab">人力投入环比趋势</a></li>
                	<li role="presentation"><a href="#tabpanel" aria-controls="tabpanel3" role="tab" data-toggle="tab">人力结构分析</a></li>
                	<li role="presentation"><a href="#tabpanel" aria-controls="tabpanel4" role="tab" data-toggle="tab">子项目明细</a></li>
                </ul>
                <div class="tab-content board0 padding0 marginTop20">
                    <div id="tabpanel1" role="tabpanel" class="tab-pane fade in active">
                        <div class="col-xs-12 padding0" id="currentEmployeeDeailTable">
                            <table class="currentEmployeeDetail-grid" id="currentEmployeeDetailGrid"></table>
                        </div>
                    </div>
                    <div id="tabpanel2" role="tabpanel" class="tab-pane fade">
                        <div class="manpowerInput-line col-md-12 padding0" id="manpowerInput"></div>
                    </div>
                    <div id="tabpanel3" role="tabpanel" class="tab-pane fade">
						<div class="piepanel">
							<div class="col-sm-6 SetUpBody" view="chart">
								<div class="index-common-title bottom-title">
								    <p class="index-common-title-text bottom-text">各部门人力投入：</p>
								</div>
								<div class="bord-style">
							    	<div class="chart-view textAlignCenter height334">
							            <div id="departmentInputChart" class="pieChart col-xs-7"></div>
							            <div id="departmentInputText" class="pieChartText col-xs-5 hide">
							            	<div class="pieChartTextContent">
					                            <div>部门</div>
					                            <div class="legend">-</div>
							            	</div>
							            </div>
							        </div>
							    </div>
							</div>
					
			                <div class="col-sm-6 SetUpBody" view="chart">
			                    <div class="index-common-title bottom-title">
			                        <p class="index-common-title-text bottom-text">职位序列人力投入：</p>
			                    </div>
			                    <div class="bord-style">
							    	<div class="chart-view textAlignCenter height334">
			                            <div id="jobSeqInputChart" class="pieChart col-xs-7"></div>
			                            <div id="jobSeqInputText" class="pieChartText col-xs-5 hide">
			                            	<div class="pieChartTextContent">
	                                            <div>职位序列</div>
	                                            <div class="legend">-</div>
			                            	</div>
			                            </div>
			                        </div>
			                    </div>
			                </div>
					                
			                <div class="col-sm-6 SetUpBody" view="chart">
			                    <div class="index-common-title bottom-title">
			                        <p class="index-common-title-text bottom-text">职级人力投入：</p>
			                    </div>
			                    <div class="bord-style">
							    	<div class="chart-view textAlignCenter height334">
			                            <div id="rankInputChart" class="pieChart col-xs-7"></div>
			                            <div id="rankInputText" class="pieChartText col-xs-5 hide">
			                            	<div class="pieChartTextContent">
	                                            <div>职级</div>
	                                            <div class="legend">-</div>
			                            	</div>
			                            </div>
			                        </div>
			                    </div>
			                </div>
					                
			                <div class="col-sm-6 SetUpBody" view="chart">
			                    <div class="index-common-title bottom-title">
			                        <p class="index-common-title-text bottom-text">工作地人力投入：</p>
			                    </div>
			                    <div class="bord-style">
							    	<div class="chart-view textAlignCenter height334">
			                            <div id="workplaceInputChart" class="pieChart col-xs-7"></div>
			                            <div id="workplaceInputText" class="pieChartText col-xs-5 hide">
			                            	<div class="pieChartTextContent">
	                                            <div>工作地</div>
	                                            <div class="legend">-</div>
			                            	</div>
			                            </div>
			                        </div>
			                    </div>
			                </div>
						</div>
                    </div>
                    <div id="tabpanel4" role="tabpanel" class="tab-pane fade ">
	                    <div id="subprojectTreegridbody" class="col-xs-12 padding0">
	                        <table class="col-xs-12 padding0" id="subprojectTreegrid"></table>
	                    </div>
                    </div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 本年人力 弹出框 end -->

<!-- 本年投入 弹出框 begin -->
<div class="modal fade popup-modal" id="feeInputInYearModal" tabindex="-1" role="dialog"
     aria-labelledby="feeInputInYearLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-text" id="feeInputInYearLabel">主导项目费用投入产出</div>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">
            	<table border="0" class="marginBottom20">
            		<tr>
               			<td align="right"><label>时间：</label></td><td align="left"><label id="feeBeginDate"></label> <label id="feeLine">-</label> <label id="feeEndDate"></label></td>
               			<td align="right"><label>&nbsp;&nbsp;&nbsp;&nbsp;项目名称：</label></td><td align="left"><label id="feeProjectName"></label></td>
               			<td align="right"><label>&nbsp;&nbsp;&nbsp;&nbsp;费用总投入：</label></td><td align="left"><label id="feeInputNum"></label><label>万元</label></td>
               		</tr>
               	</table>
                <ul class="nav nav-tabs f-white-bg" role="tablist" id="feeInputTabs">
                	<li role="presentation" class="active"><a href="#tabpanel" aria-controls="feeInput1" role="tab" data-toggle="tab">投入产出金额</a></li>
                	<li role="presentation"><a href="#tabpanel" aria-controls="feeInput2" role="tab" data-toggle="tab">产出投入比</a></li>
                	<li role="presentation"><a href="#tabpanel" aria-controls="feeInput3" role="tab" data-toggle="tab">费用明细</a></li>
                </ul>
                <div class="tab-content board0 padding0 marginTop20">
                    <div id="feeInput1" role="tabpanel" class="col-xs-12 tab-pane fade in active padding0">
                        <div class="col-xs-12">(万元)</div>
						<div class="inputOutputFeeChart-line col-md-12 padding0" id="inputOutputFeeChart"></div>
                    </div>
                    <div id="feeInput2" role="tabpanel" class="col-xs-12 tab-pane fade padding0">
                        <div class="inputOutputRatioChart-line col-md-12 padding0" id="inputOutputRatioChart"></div>
                    </div>
                    <div id="feeInput3" role="tabpanel" class="col-xs-12 tab-pane fade padding0">
		                <div id="index-son-feedetailgrid-body" class="col-xs-12 padding0">
	                        <table class="col-xs-12 padding0" id="feeDetailTreegrid"></table>
		                </div>
                    </div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 本年投入 弹出框 end -->

<!-- 参与本年人力 弹出框 begin -->
<div class="modal fade popup-modal" id="manpowerPartInYearModal" tabindex="-1" role="dialog"
     aria-labelledby="manpowerPartInYearLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-text" id="manpowerPartInYearLabel">参与项目人力盘点</div>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">
            	<table border="0" class="marginBottom20">
            		<tr>
               			<td align="right"><label>时间：</label></td><td align="left"><label id="partManpowerBeginDate"></label> <label id="partManpowerLine">-</label> <label id="partManpowerEndDate"></label></td>
               			<td align="right"><label>&nbsp;&nbsp;&nbsp;&nbsp;项目名称：</label></td><td align="left"><label id="partManpowerProjectName"></label></td>
               			<td align="right"><label>&nbsp;&nbsp;&nbsp;&nbsp;人力总投入：</label></td><td align="left"><label id="partManpowerInputNum"></label><label>人</label></td>
               		</tr>
               	</table>
                <ul class="nav nav-tabs f-white-bg" role="tablist" id="partManpoerInputTabs">
                	<li role="presentation" class="active"><a href="#tabpanel" aria-controls="part1" role="tab" data-toggle="tab">当前人力投入</a></li>
                	<li role="presentation"><a href="#tabpanel" aria-controls="part2" role="tab" data-toggle="tab">人力投入环比趋势</a></li>
                	<li role="presentation"><a href="#tabpanel" aria-controls="part3" role="tab" data-toggle="tab">人力结构分析</a></li>
                </ul>
                <div class="tab-content board0 padding0 marginTop20">
                    <div id="part1" role="tabpanel" class="tab-pane fade in active">
                        <div class="col-xs-12 padding0" id="currentEmployeeTable">
                            <table class="currentEmployee-grid" id="currentEmployeeGrid"></table>
                        </div>
                    </div>
                    <div id="part2" role="tabpanel" class="tab-pane fade">
                        <div class="manpowerInput-line col-md-12 padding0" id="partManpowerInput"></div>
                    </div>
                    <div id="part3" role="tabpanel" class="tab-pane fade">
						<div class="piepanel">
							<div class="col-sm-6 SetUpBody" view="chart">
								<div class="index-common-title bottom-title">
								    <p class="index-common-title-text bottom-text">各部门人力投入：</p>
								</div>
								<div class="bord-style">
							    	<div class="chart-view textAlignCenter height334">
							            <div id="partDeprtmentInputChart" class="pieChart col-xs-7"></div>
							            <div id="partDeprtmentInputText" class="pieChartText col-xs-5 hide">
							            	<div class="pieChartTextContent">
					                            <div>部门</div>
					                            <div class="legend">-</div>
							            	</div>
							            </div>
							        </div>
							    </div>
							</div>
					
			                <div class="col-sm-6 SetUpBody" view="chart">
			                    <div class="index-common-title bottom-title">
			                        <p class="index-common-title-text bottom-text">职位序列人力投入：</p>
			                    </div>
			                    <div class="bord-style">
			                        <div class="chart-view textAlignCenter height334">
			                            <div id="partJobSeqInputChart" class="pieChart col-xs-7"></div>
			                            <div id="partJobSeqInputText" class="pieChartText col-xs-5 hide">
			                            	<div class="pieChartTextContent">
	                                            <div>职位序列</div>
	                                            <div class="legend">-</div>
			                            	</div>
			                            </div>
			                        </div>
			                    </div>
			                </div>
					                
			                <div class="col-sm-6 SetUpBody" view="chart">
			                    <div class="index-common-title bottom-title">
			                        <p class="index-common-title-text bottom-text">职级人力投入：</p>
			                    </div>
			                    <div class="bord-style">
			                        <div class="chart-view textAlignCenter height334">
			                            <div id="partRankInputChart" class="pieChart col-xs-7"></div>
			                            <div id="partRankInputText" class="pieChartText col-xs-5 hide">
			                            	<div class="pieChartTextContent">
	                                            <div>职级</div>
	                                            <div class="legend">-</div>
			                            	</div>
			                            </div>
			                        </div>
			                    </div>
			                </div>
					                
			                <div class="col-sm-6 SetUpBody" view="chart">
			                    <div class="index-common-title bottom-title">
			                        <p class="index-common-title-text bottom-text">工作地人力投入：</p>
			                    </div>
			                    <div class="bord-style">
			                        <div class="chart-view textAlignCenter height334">
			                            <div id="partWorkspaceInputChart" class="pieChart col-xs-7"></div>
			                            <div id="partWorkspaceInputText" class="pieChartText col-xs-5 hide">
			                            	<div class="pieChartTextContent">
	                                            <div>工作地</div>
	                                            <div class="legend">-</div>
			                            	</div>
			                            </div>
			                        </div>
			                    </div>
			                </div>
						</div>
                    </div>
                    
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 参与本年人力 弹出框 end -->

<!-- 参与项目明细 弹出框 begin -->
<div class="modal fade topWindow popup-modal" id="participateProjectModal" tabindex="-1" role="dialog"
     aria-labelledby="participateProjectModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-text" id="participateProjectModalLabel">参与项目明细表</div>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">
                <div class="col-xs-12 padding0" id="participatejProjectTable">
                    <table class="col-xs-12 padding0" id="participatejProjectGrid"></table>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 参与项目明细 弹出框 end -->
<!-- 盈亏项目明细 弹出框 begin -->
<div class="modal fade topWindow popup-modal" id="profitLossProjectDetailModal"
	tabindex="-1" role="dialog" aria-labelledby="profitLossProjectDetailModalLabel"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-header-text" id="profitLossProjectDetailModalLabel">盈亏项目明细</div>
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<div>
					时间：<span id="profitLossProjectDetailYear"> </span>年/<span id="profitLossProjectDetailMonth"> </span>月&nbsp;&nbsp;
					盈利总金额：<span class="span-weight" id="profitCountAmount">0</span>万元&nbsp;&nbsp;
					亏损总金额：<span class="span-weight" id="lossCountAmount">0</span>万元&nbsp;&nbsp;
				</div>
				<br>
				<ul class="nav nav-tabs f-white-bg" role="tablist"
					id="profitLossProjectTabs">
					<li role="presentation" class="active"><a
						href="#profitProjectTab" aria-controls="profitProjectTab" role="tab"
						data-toggle="tab">盈利项目 &nbsp;<span id="profitProjectNum">0</span></a></li>
					<li role="presentation"><a href="#lossProjectTab"
						aria-controls="lossProjectTab" role="tab" data-toggle="tab"> 亏损项目
					 &nbsp;<span id="lossProjectNum">0</span></a></li>
				</ul>

				<div class="tab-content">
					<div role="tabpanel" class="tab-pane active" id="profitProjectTab">
						<div class="col-xs-12" id="profitProjectTable">
							<table class="table table-striped" id="profitProjectGrid">
								<thead>
									<tr class="table-thead-tr">
										<th class="thead-tr-th-first">项目名称</th>
										<th class="thead-tr-th">当月投入（万元）</th>
										<th class="thead-tr-th">当月产出（万元）</th>
										<th class="thead-tr-th">当月盈利（万元）</th>
										<th class="thead-tr-th-last">累计盈利/亏损数额（万元）</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
					</div>

					<div role="tabpanel" class="tab-pane" id="lossProjectTab">
						<div class="col-xs-12" id="lossProjectTable">
							<table class="table table-striped" id="lossProjectGrid">
								<thead>
									<tr class="table-thead-tr">
										<th class="thead-tr-th-first">项目名称</th>
										<th class="thead-tr-th">当月投入（万元）</th>
										<th class="thead-tr-th">当月产出（万元）</th>
										<th class="thead-tr-th">当月亏损（万元）</th>
										<th class="thead-tr-th-last">累计盈利/亏损数额（万元）</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
</div>
<!-- 盈亏项目明细 弹出框 end -->

<!--遮罩层-->
<div id="shade" class="shade"></div>
<!--遮罩层-->
<script src="${jsRoot}lib/echarts/echarts.js"></script>
<script src="${jsRoot}biz/productivity/humanInventory.js"></script>
</body>
</html>
