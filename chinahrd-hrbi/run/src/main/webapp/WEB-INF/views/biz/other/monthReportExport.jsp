<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="net.chinahrd.entity.dto.pc.talentStructure.TalentStructureDto" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<!-- jsp文件头和头部 -->
<%@include file="/WEB-INF/views/include/top.jsp"%>
<title>月报</title>
<link rel="stylesheet"
	href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet"
	href="${ctx}/assets/css/biz/other/monthReportExport.css">
</head>
<%
    Object talentStructureDto = request.getAttribute("talentStructureDto");
    Object getTalentStuctureByMonth = request.getAttribute("getTalentStuctureByMonth");
    Object changesByOrgan = request.getAttribute("changesByOrgan");
    Object changesByAbility = request.getAttribute("changesByAbility");
    Object getKeyTalentdimissionEmps = request.getAttribute("getKeyTalentdimissionEmps");
    Object getDismissRecord = request.getAttribute("getDismissRecord");
    Object queryItemDetail = request.getAttribute("queryItemDetail");
    Object getAllDetailData = request.getAttribute("getAllDetailData");
    Object getProportionYear = request.getAttribute("getProportionYear");
    Object getOrgBenefitData = request.getAttribute("getOrgBenefitData");
    Object getTrendData = request.getAttribute("getTrendData");
    Object getSalaryOrganRateOfReturn = request.getAttribute("getSalaryOrganRateOfReturn");
    Object getSalaryMonthRateOfReturn = request.getAttribute("getSalaryMonthRateOfReturn");
    Object getLaborEfficiencyRatio = request.getAttribute("getLaborEfficiencyRatio");
    Object queryOvertimeByOrgan = request.getAttribute("queryOvertimeByOrgan");
    Object getPositionMeetRate = request.getAttribute("getPositionMeetRate");
    Object getRecruitChannel = request.getAttribute("getRecruitChannel");
    Object getSubOrganizationCover = request.getAttribute("getSubOrganizationCover");
    Object getTrainingType = request.getAttribute("getTrainingType");
    Object findTrainingTypeRecord = request.getAttribute("findTrainingTypeRecord");
    Object getTrainGeneral = request.getAttribute("getTrainGeneral");
    Object queryPerchangeByOrgan = request.getAttribute("queryPerchangeByOrgan");
    Object getPerfChangeDate = request.getAttribute("getPerfChangeDate");
    Object getPreChangeCountData = request.getAttribute("getPreChangeCountData");
    Object queryPromotionGeneral = request.getAttribute("queryPromotionGeneral");
    Object getInJobEmpCount = request.getAttribute("getInJobEmpCount");
    Object getSalesCount0 = request.getAttribute("getSalesCount0");
    Object getSalesCountByMonth0 = request.getAttribute("getSalesCountByMonth0");
    Object getSalesCount1 = request.getAttribute("getSalesCount1");
    Object getSalesCountByMonth1 = request.getAttribute("getSalesCountByMonth1");
    Object getAccordDismissAnalysis = request.getAttribute("getAccordDismissAnalysis");
    Object accordDismissByYearMonth = request.getAttribute("accordDismissByYearMonth");
    Object accordDismissInYear = request.getAttribute("accordDismissInYear");
    Object getSalaryWageStructure = request.getAttribute("getSalaryWageStructure");
    Object manpowerCostInfo = request.getAttribute("manpowerCostInfo");
%>

<body>
<script>
    var talentStructureDto = <%=talentStructureDto%>;
    var getTalentStuctureByMonth = <%=getTalentStuctureByMonth%>;
    var changesByOrgan = <%=changesByOrgan%>;
    var changesByAbility = <%=changesByAbility%>;
    var getKeyTalentdimissionEmps = <%=getKeyTalentdimissionEmps%>;
    var getDismissRecord = <%=getDismissRecord%>;
    var queryItemDetail = <%=queryItemDetail%>;
    var getAllDetailData = <%=getAllDetailData%>;
    var getProportionYear = <%=getProportionYear%>;
    var getOrgBenefitData = <%=getOrgBenefitData%>;
    var getTrendData = <%=getTrendData%>;
    var getSalaryOrganRateOfReturn = <%=getSalaryOrganRateOfReturn%>;
    var getSalaryMonthRateOfReturn = <%=getSalaryMonthRateOfReturn%>;
    var getLaborEfficiencyRatio = <%=getLaborEfficiencyRatio%>;
    var queryOvertimeByOrgan = <%=queryOvertimeByOrgan%>;
    var getPositionMeetRate = <%=getPositionMeetRate%>;
    var getRecruitChannel = <%=getRecruitChannel%>;
    var getSubOrganizationCover = <%=getSubOrganizationCover%>;
    var getTrainingType = <%=getTrainingType%>;
    var findTrainingTypeRecord = <%=findTrainingTypeRecord%>;
    var getTrainGeneral = <%=getTrainGeneral%>;
    var queryPerchangeByOrgan = <%=queryPerchangeByOrgan%>;
    var getPerfChangeDate = <%=getPerfChangeDate%>;
    var getPreChangeCountData = <%=getPreChangeCountData%>;
    var queryPromotionGeneral = <%=queryPromotionGeneral%>;
    var getInJobEmpCount = <%=getInJobEmpCount%>;
    var getSalesCount0 = <%=getSalesCount0%>;
    var getSalesCountByMonth0 = <%=getSalesCountByMonth0%>;
    var getSalesCount1 = <%=getSalesCount1%>;
    var getSalesCountByMonth1 = <%=getSalesCountByMonth1%>;
    var getAccordDismissAnalysis = <%=getAccordDismissAnalysis%>;
    var accordDismissByYearMonth = <%=accordDismissByYearMonth%>;
    var accordDismissInYear = <%=accordDismissInYear%>;
    var getSalaryWageStructure = <%=getSalaryWageStructure%>;
    var manpowerCostInfo = <%=manpowerCostInfo%>;
</script>
	<input type="hidden" id="quotaId" value="${quotaId}" />
	<input type="hidden" id="yearMonth" value="${yearMonth}" />
	<input type="hidden" id="organId" value="${organId}" />
	<input type="hidden" id="organName" value="${organName}" />
	<div class="month-report" style="width: 900px;">
		<div class="rightbody">
			<div id="page-one" class="rightbodypage">
				<div class="row ct-row">
					<div class="right-header">
						<div class="right-header-title">
							<span id="pageTitle"></span>月报
						</div>
					</div>
				</div>
				<div class="row ct-row panel-default">
					<div class="panel-heading">
						<span class="panel-title">一、人员结构分析</span>
						<div class="arrow"></div>
					</div>
					
					<div class="margintop10">
						<div class="little-title">
							<span>1.1编制与人数</span>
						</div>
						<div class="col-xs-3 ct-col1">
							<div class="top-div">
								<div class="index-common-title">
									<div class="index-common-title-left"></div>
									<div class="index-common-title-text">全年编制使用率</div>
								</div>
								<div class="body-div">
									<div class="height120" id="structureRateChart"></div>
								</div>
							</div>
						</div>
						<div class="col-xs-9 ct-colr">
							<div class="top-div">
								<div class="index-common-title">
									<div class="index-common-title-left"></div>
									<div class="index-common-title-text">编制使用情况</div>
								</div>
								<div class="body-div">
									<div class="widget-table col-xs-12 ct-col1">
										<div class="widget-table-bold">
											<span class="col-xs-3">&nbsp;</span>
											<span class="col-xs-3">可用编制
												<span class="widget-table-icon">=</span>
											</span>
											<span class="col-xs-3">编制数
												<span class="widget-table-icon">-</span>
											</span>
											<span class="col-xs-3">在岗人数</span>
										</div>
										<div class="widget-table-row" id="budgetYear">
											<span class="col-xs-3">全年</span>
											<span class="col-xs-3 usableEmpCount">0</span>
											<span class="col-xs-3 number">0</span>
											<span class="col-xs-3 empCount">0</span>
										</div>
									</div>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="margintop10">
						<div class="little-title">
							<span>1.2各架构及管理层级分布</span>
						</div>
						<div id="managerTable">
							<div class='loadingmessage'>数据加载中...</div>
							<table class="table minwidthpc100">
								<thead></thead>
								<tbody></tbody>
							</table>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="">
						<div class="little-title">
							<span class="">1.3各序列及职级人数分布</span>
						</div>
						<div id="positionSequenceTable">
							<div class='loadingmessage'>数据加载中...</div>
							<table class="table minwidthpc100">
								<thead></thead>
								<tbody></tbody>
							</table>
						</div>
					</div>
				</div>
				
				<div class="row ct-row panel-default">
					<div class="panel-heading">
						<span class="panel-title">二、人员流动分析</span>
						<div class="arrow"></div>
					</div>
					
					<div class="margintop10">
						<div class="little-title">
							<span>2.1流动情况概览</span>
						</div>
						<div class="clearfix"></div>
						<div class="view-dismiss-generalize">
						    <div class="col-sm-6 ct-col1" view="chart">
						        <div class="index-common-title bottom-title">
						            <div class="index-common-title-left bottom-left"></div>
						            <div class="index-common-title-text bottom-text">人员异动情况（按职级）</div>
						        </div>
						        <div class="" id="changesAbilityTable">
						            <table id="changesAbilityGrid"></table>
						        </div>
						    </div>
						
						    <div class="col-sm-6 ct-colr" view="chart">
						        <div class="index-common-title bottom-title">
						            <div class="index-common-title-left bottom-left"></div>
						            <div class="index-common-title-text bottom-text">人员异动情况（按组织）</div>
						        </div>
						        <div class="" id="changesOrganTable">
						            <table id="changesOrganGrid"></table>
						        </div>
						    </div>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="margintop10">
						<div class="little-title">
							<span>2.2关键人才离职情况</span>
						</div>
						<div class="view-dismiss-talent">
						    <div class="col-xs-12 ct-col1" id="dimissionTable">
						        <table id="dimissionGrid"></table>
						        <table id="dimissionPaper"></table>
						    </div>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="margintop10">
						<div class="little-title">
							<span>2.3流失率分析</span>
						</div>
						
						<div class="col-xs-12 view-dismiss-rate">
							<div class="col-xs-12 ct-col1" id="accordDismissByOrganArea">
								<table id="accordDismissByOrganGrid"></table>
							</div>
							<div class="clearfix"></div>
                            <div class="col-xs-12 ct-col1">
                            	<div class="accord-dismiss-year">
                            		<i class="icon-exclamation-sign bigger-120 blue"></i>
                            		全年累计流失率：<span id="accordDismissInYear"></span>
                            	</div>
                            </div>
							<div class="col-xs-12 ct-col1 height300" id="dimissionByYearMonthBar"></div>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="margintop10">
						<div class="little-title">
							<span>2.4流失率维度分析</span>
						</div>
						<div class="view-dismiss-dimension">
						    <div class="col-xs-6 ct-col1">
						        <div class="top-div">
						            <div class="index-common-title">
						                <div class="index-common-title-left"></div>
						                <div class="index-common-title-text">离职原因统计</div>
						            </div>
						            <div class="body-div clearfix">
						                <div class="col-xs-12 ct-col1 height300"
						                     id="dimissionCauseBar"></div>
						            </div>
						        </div>
						    </div>
						    <div class="col-xs-6 ct-colr">
						        <div class="top-div">
						            <div class="index-common-title">
						                <div class="index-common-title-left"></div>
						                <div class="index-common-title-text">离职去向统计</div>
						            </div>
						            <div class="body-div clearfix">
						                <div class="col-xs-12 ct-col1 height300"
						                     id="dimissionWhereaboutsBar"></div>
						            </div>
						        </div>
						    </div>
						</div>
					</div>
				</div>
				
				<div class="row ct-row panel-default">
					<div class="panel-heading">
						<span class="panel-title">三、人力成本分析</span>
						<div class="arrow"></div>
					</div>
					
					<div class="margintop10">
						<div class="little-title">
							<span>3.1概况</span>
						</div>
						<div class="col-xs-12 ct-col1 view-manpower-generalize" id="manpowerCostArea">
                               <table id="manpowerCostGrid"></table>
                        </div>
					</div>
					<div class="clearfix"></div>
					<div class="margintop10">
						<div class="little-title">
							<span>3.2结构</span>
						</div>
						<div class="col-xs-12 view-manpower-structure">
						    <div class="col-xs-6 ct-col1">
						        <div class="col-xs-12 padding0 height350" id="contrastDetailPie"></div>
						    </div>
						    <div class="col-xs-6 ct-colr">
						    	<div class="col-xs-12 padding0 height350" id="salaryStructureChart"></div>
						    </div>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="margintop10">
						<div class="little-title">
							<span>3.3趋势分析</span>
						</div>
						<div class="col-xs-12 view-manpower-trend">
						    <div class="col-xs-12 alert-success">
						    	<span class="pull-left">(万元)</span>
						        <div class="alert-success" id="manpowerOccupyAll">
						            <i class="icon-exclamation-sign bigger-120 blue"></i>人力成本占总成本比：<span id="manpowerOccupyAllVal">50%</span>
						        </div>
						    </div>
						    <div class="height350" id="manpowerTrendChart"></div>
						    <div class="row">&nbsp;</div>
						</div>
					</div>
				</div>
				
				<div class="row ct-row panel-default">
					<div class="panel-heading">
						<span class="panel-title">四、投入产出分析</span>
						<div class="arrow"></div>
					</div>

					<div class="margintop10">
						<div class="little-title">
							<span>4.1人均效益分析</span>
						</div>
						<div class="col-xs-12 view-per-benefit">
						    <div class="col-xs-4 ct-coll" id="organBenefitTable">
						        <div class="row">&nbsp;</div>
						        <table id="organBenefitGrid"></table>
						    </div>
						    <div class="col-xs-8 ct-colr">
						        <div class="height350" id="benefitsTrendChart"></div>
						    </div>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="margintop10">
						<div class="little-title">
							<span>4.2投资回报率分析</span>
						</div>
						<div class="col-xs-12 view-per-report">
						    <div class="col-xs-4 ct-coll" id="salaryBringBackTable">
						        <div class="row">&nbsp;</div>
						        <table id="salaryBringBackGrid"></table>
						    </div>
						    <div class="col-xs-8 ct-colr">
						        <div class="height350" id="salaryBringBackChart"></div>
						    </div>
						</div>
					</div>
				</div>
					
				<div class="row ct-row panel-default">
					<div class="panel-heading">
						<span class="panel-title">五、劳动力效能分析</span>
						<div class="arrow"></div>
					</div>

					<div class="margintop10">
						<div class="col-xs-12" id="laborRatioTable">
						    <div class="bottom-div-first"><span>统计说明：出勤时间及加班时间统计单位为小时</span></div>
						    <table id="laborRatioGrid"></table>
						</div>
					</div>
				</div>
				
				<div class="row ct-row panel-default">
					<div class="panel-heading">
						<span class="panel-title">六、招聘分析</span>
						<div class="arrow"></div>
					</div>

					<div class="margintop10">
						<div class="little-title">
							<span>6.1需求分析</span>
						</div>
						<div class="col-xs-12 view-recruit-demand" id="demandTable">
						    <table id="demandGrid"></table>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="margintop10">
						<div class="little-title">
							<span>6.2渠道分析</span>
						</div>
						<div class="col-xs-12 view-recruit-channel">
						    <!-- <div class="dis-search clearfix">
						        <input class="dis-search-input" id="channelTxt" type="text" placeholder="请输入岗位名称">
						        <div class="add-on dis-search-input-btn" id="channelBtn">搜索</div>
						    </div> -->
						    <div class="col-xs-12 ct-col1" id="channelTable"></div>
						</div>
					</div>
				</div>
				
				<div class="row ct-row panel-default">
					<div class="panel-heading">
						<span class="panel-title">七、培训分析</span>
						<div class="arrow"></div>
					</div>

					<div class="margintop10">
						<div class="little-title">
							<span>7.1概况</span>
						</div>
						<div class="col-xs-12 view-train-generalize" id="trainGeneralTable">
						    <table id="trainGeneralGrid"></table>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="margintop10">
						<div class="little-title">
							<span>7.2结构</span>
						</div>
						<div class="col-xs-12 view-train-structure">
						    <div class="col-xs-4 ct-col1">
						        <div class="col-xs-12 height350" id="trainTypeNumChart"></div>
						    </div>
						    <div class="col-xs-8 ct-colr" id="trainTypeTable">
						        <table class="borderless" id="trainTypeGrid"></table>
						    </div>
						</div>
					</div>
				</div>
				
				<div class="row ct-row panel-default">
					<div class="panel-heading">
						<span class="panel-title">八、绩效分析</span>
						<div class="arrow"></div>
					</div>

					<div class="margintop10">
						<div class="little-title">
							<span>8.1概况</span>
						</div>
						<div class="col-xs-12 view-change-generalize" id="perchangeByOrganTable">
							<table id="perchangeByOrganGrid"></table>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="margintop10">
						<div class="little-title">
							<span>8.2变化情况</span>
						</div>
						<div class="col-xs-12 view-change-condition">
						    <div class="col-xs-6 ct-col1 mainArea">
						        <div class="col-xs-12 titleArea">绩效结果变化趋势</div>
						        <div class="col-sm-12 chartArea">
						            <div class="col-sm-12 " id="preChange"></div>
						        </div>
						        <div class="col-sm-12 textArea">说明：<br>
						            • 有所进步：与上期绩效相比，提升一级或以上<br>
						            • 出现下滑：与上期绩效相比，下降一级或以上
						        </div>
						    </div>
						    <div class="col-xs-6 ct-colr mainArea">
						        <div class="col-xs-12 titleArea">绩效异常（大起大落）</div>
						        <div class="col-sm-12 chartArea">
						            <div class="col-sm-12 " id="preUnusual"></div>
						        </div>
						        <div class="col-sm-12 textArea">说明：<br>
						            • 飞速提升：与上期绩效相比，提升幅度至少2个星级<br>
						            • 加速跌落：与上期绩效相比，下滑幅度至少2个星级
						        </div>
						    </div>
						</div>
					</div>
				</div>
				
				<div class="row ct-row panel-default">
					<div class="panel-heading">
						<span class="panel-title">九、晋级分析</span>
						<div class="arrow"></div>
					</div>

					<div class="margintop10">
						<div class="little-title">
							<span>9.1概况</span>
						</div>
						<div class="col-xs-12 view-promotion-generalize" id="promotionGeneralizeTable">
							<table id="promotionGeneralizeGrid"></table>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="margintop10">
						<div class="little-title">
							<span>9.2人员序列及职级分布</span>
						</div>
						<div class="col-xs-12 view-promotion-sequence" id="promotionInJobsTables">
						    <table id="promotionInJobsGrid"></table>
						</div>
					</div>
				</div>
				
				<div class="row ct-row panel-default">
					<div class="panel-heading">
						<span class="panel-title">十、销售分析</span>
						<div class="arrow"></div>
					</div>

					<div class="margintop10">
						<div class="little-title">
							<span>10.1各品种销售情况</span>
						</div>
						<div class="col-xs-12 view-sales-product" id="salesByProductTable">
						    <table id="salesByProductGrid"></table>
						    <div class="row clearfix salesMargin30">
						        <div class="col-xs-2 btn-group">
						            <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
						                Action
						                <i class="icon-angle-down icon-on-right"></i>
						            </button>
						            <ul class="dropdown-menu ct-mCustomScrollBar overflowauto height350" id="salesByProductSelector">
						            </ul>
						        </div>
						        <div class="col-xs-10 clearfix">
						            <div class="height350" id="salesByProductChart"></div>
						        </div>
						    </div>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="margintop10">
						<div class="little-title">
							<span>10.2各部门销售情况</span>
						</div>
						<div class="col-xs-12 view-sales-oragn" id="salesByOrganTable">
						    <table id="salesByOrganGrid"></table>
						    <div class="row clearfix salesMargin30">
						        <div class="col-xs-2 btn-group">
						            <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
						                Action
						                <i class="icon-angle-down icon-on-right"></i>
						            </button>
						            <ul class="dropdown-menu ct-mCustomScrollBar overflowauto height350" id="salesByOrganSelector">
						            </ul>
						        </div>
						        <div class="col-xs-10 clearfix">
						            <div class="height350" id="salesByOrganChart"></div>
						        </div>
						    </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
	<script src="${ctx}/assets/js/biz/other/monthReportExport.js"></script>
</body>
</html>