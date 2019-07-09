<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>月报</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css">
    <link rel="stylesheet" href="${ctx}/assets/css/select2/select2.min.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/jquery-gridly/jquery.gridly.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/other/monthReport.css">
</head>
<body>

<div class="month-report">
    <div class="leftBody">
        <div class="leftListBigDiv">月报
            <input type="hidden" id="quotaId" value="${quotaId}">
            <input type="hidden" id="yearMonth" value="${yearMonth}">
        </div>
        <div page="page-one" class="leftListDiv selectList">最新月报</div>
        <div page="page-two" class="leftListDiv">我的收藏</div>
        <div page="page-three" class="leftListDiv">分享给我的</div>
    </div>
    <div class="rightDiv">
        <div id="page-one" class="rightBodyPage">
            <div class="menu">
                <ul class="menu-list" id="menuBlock">
                    <li position="float0" class="menu-line menu-line1 menu-line-select">
                        <div class="menu-line-spor menu-line-spor1"></div>
                        <span class="menu-line-text">回顶部</span>
                    </li>
                    <li position="float1" class="menu-line menu-line2">
                        <div class="menu-line-spor"></div>
                        <span class="menu-line-text">人员结构分析</span>
                    </li>
                    <li position="float2" class="menu-line menu-line2">
                        <div class="menu-line-spor"></div>
                        <span class="menu-line-text">人员流动分析</span>
                    </li>
                    <li position="float3" class="menu-line menu-line2">
                        <div class="menu-line-spor"></div>
                        <span class="menu-line-text">人力成本分析</span>
                    </li>
                    <li position="float4" class="menu-line menu-line2">
                        <div class="menu-line-spor"></div>
                        <span class="menu-line-text">投入产出分析</span>
                    </li>
                    <li position="float5" class="menu-line menu-line2">
                        <div class="menu-line-spor"></div>
                        <span class="menu-line-text">劳动力效能分析</span>
                    </li>
                    <li position="float6" class="menu-line menu-line2">
                        <div class="menu-line-spor"></div>
                        <span class="menu-line-text">招聘分析</span>
                    </li>
                    <li position="float7" class="menu-line menu-line2">
                        <div class="menu-line-spor"></div>
                        <span class="menu-line-text">培训分析</span>
                    </li>
                    <li position="float8" class="menu-line menu-line2">
                        <div class="menu-line-spor"></div>
                        <span class="menu-line-text">绩效分析</span>
                    </li>
                    <li position="float9" class="menu-line menu-line2">
                        <div class="menu-line-spor"></div>
                        <span class="menu-line-text">晋级分析</span>
                    </li>
                    <li position="float10" class="menu-line menu-line3">
                        <div class="menu-line-spor menu-line-spor2"></div>
                        <span class="menu-line-text">销售分析</span>
                    </li>
                </ul>
            </div>

            <div class="col-xs-12 right-header">
                <div class="right-header-title"><span id="pageTitle"></span>月报</div>
                <span class="right-header-patch"></span>
                <div class="right-header-group">
                    <span class="icon print-icon" id="exportBtn"></span>
                    <span class="icon favorite-icon" id="favoritesBtn"></span>
                    <span class="icon share-icon" id="shareSendingBtn"></span>
                    <span class="icon setting-icon" id="settingBtn"></span>
                </div>
            </div>

            <div class="col-xs-12" id="pagePanels">
	            <shiro:hasPermission name="ShengRenLi_RenLiJieGou">
	                <div class="panel panel-default ShengRenLi_RenLiJieGou" data-action="1">
	                    <div class="panel-heading">
	                        <span class="panel-title">一、人员结构分析</span>
	                        <a class="panel-link" href="javascript:void(0)"
	                           data-link="/talentStructure/toTalentStructureView">查看更多&gt;&gt;</a>
	                    </div>
	                    <div class="panel-body">
	                        <div class="index-jxmb-tab">
	                            <div page="view-structure-all" class="index-jxmb-btn index-jxmb-btn-select">编制与人数
	                            </div>
	                            <div page="view-structure-organ" class="index-jxmb-btn">各架构及管理层级分布</div>
	                            <div page="view-structure-sequece" class="index-jxmb-btn">各序列及职级人数分布</div>
	                        </div>
	                        <div class="bottom-div">
	
	                            <div class="col-xs-12 ct-col2 view-structure-all">
	                                <div class="col-xs-3 ct-col1">
	                                    <div class="top-div">
	                                        <div class="index-common-title">
	                                            <div class="index-common-title-left"></div>
	                                            <div class="index-common-title-text">全年编制使用率</div>
	                                                <%--<div class="LSLSetUp"></div>--%>
	                                        </div>
	                                        <div class="body-div">
	                                            <div class="height120" id="structureRateChart"></div>
	                                        </div>
	                                    </div>
	                                </div>
	
	                                    <%--<div class="col-xs-3 ct-col1">--%>
	                                    <%--<div class="top-div">--%>
	                                    <%--<div class="index-common-title">--%>
	                                    <%--<div class="index-common-title-left"></div>--%>
	                                    <%--<div class="index-common-title-text">全年编制使用率</div>--%>
	                                    <%--&lt;%&ndash;<div class="LSLSetUp"></div>&ndash;%&gt;--%>
	                                    <%--</div>--%>
	                                    <%--<div class="body-div">--%>
	                                    <%--<div id="budgetChart"></div>--%>
	                                    <%--<div id="structureRateText">--%>
	                                    <%--<div class="rate"></div>--%>
	                                    <%--<div class="text"></div>--%>
	                                    <%--</div>--%>
	                                    <%--</div>--%>
	                                    <%--</div>--%>
	                                    <%--&lt;%&ndash;<div class="widget-box">&ndash;%&gt;--%>
	                                    <%--&lt;%&ndash;<div class="widget-header widget-header-flat">&ndash;%&gt;--%>
	                                    <%--&lt;%&ndash;<h3>全年编制使用率</h3>&ndash;%&gt;--%>
	                                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
	                                    <%--&lt;%&ndash;<div class="widget-body">&ndash;%&gt;--%>
	                                    <%--&lt;%&ndash;<div class="widget-main" id="BudgetBlock">&ndash;%&gt;--%>
	                                    <%--&lt;%&ndash;<div class="progress progress-striped active">&ndash;%&gt;--%>
	                                    <%--&lt;%&ndash;<div class="progress-bar" style="width: 0%"></div>&ndash;%&gt;--%>
	                                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
	                                    <%--&lt;%&ndash;<div class="tooltip fade top in" role="tooltip" style="">&ndash;%&gt;--%>
	                                    <%--&lt;%&ndash;<div class="tooltip-arrow" style="left: 50%;"></div>&ndash;%&gt;--%>
	                                    <%--&lt;%&ndash;<div class="tooltip-inner">3.37%</div>&ndash;%&gt;--%>
	                                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
	                                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
	                                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
	                                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
	                                    <%--</div>--%>
	                                <div class="col-xs-9 ct-colr">
	
	                                    <div class="top-div">
	                                        <div class="index-common-title">
	                                            <div class="index-common-title-left"></div>
	                                            <div class="index-common-title-text">编制使用情况</div>
	                                                <%--<div class="LSLSetUp"></div>--%>
	                                        </div>
	                                        <div class="body-div">
	                                            <div class="widget-table col-xs-12 ct-col1">
	                                                <div class="widget-table-bold">
	                                                    <span class="col-xs-3">&nbsp;</span>
	                                                    <span class="col-xs-3">可用编制<span
	                                                            class="widget-table-icon">=</span></span>
	                                                    <span class="col-xs-3">编制数<span
	                                                            class="widget-table-icon">-</span></span>
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
	
	                            <div class="col-xs-12 view-structure-organ hide">
	                                <div id="managerTable" class="ct-mCustomScrollBar">
	                                    <div class='loadingmessage'>数据加载中...</div>
	                                    <table class="table hide">
	                                        <thead></thead>
	                                        <tbody></tbody>
	                                    </table>
	                                </div>
	                            </div>
	                            <div class="col-xs-12 view-structure-sequece hide">
	                                <div id="positionSequenceTable" class="ct-mCustomScrollBar">
	                                    <div class='loadingmessage'>数据加载中...</div>
	                                    <table class="table minwidthpc100 hide">
	                                        <thead></thead>
	                                        <tbody></tbody>
	                                    </table>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </shiro:hasPermission>
	
	            <shiro:hasPermission name="QuDongLi_ZhuDongLiuShiLv">
	                <div class="panel panel-default QuDongLi_ZhuDongLiuShiLv" data-action="2">
	                    <div class="panel-heading">
	                        <span class="panel-title">二、人员流动分析</span>
	                        <a class="panel-link" href="javascript:void(0)"
	                           data-link="/accordDismiss/toAccordDismissView">查看更多&gt;&gt;</a>
	                    </div>
	                    <div class="panel-body">
	                        <div class="index-jxmb-tab">
	                            <div page="view-dismiss-generalize" class="index-jxmb-btn index-jxmb-btn-select">
	                                流动情况概览
	                            </div>
	                            <div page="view-dismiss-talent" class="index-jxmb-btn">关键人才离职情况</div>
	                            <div page="view-dismiss-rate" class="index-jxmb-btn">流失率分析</div>
	                            <div page="view-dismiss-dimension" class="index-jxmb-btn">流失率维度分析</div>
	                        </div>
	                        <div class="bottom-div clearfix">
	                            <div class="col-xs-12 view-dismiss-generalize">
	                                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
	                                    <div class="index-common-title bottom-title">
	                                        <div class="index-common-title-left bottom-left"></div>
	                                        <div class="index-common-title-text bottom-text">人员异动情况（按职级）</div>
	                                    </div>
	                                    <div class="bottom-div2">
	                                        <table id="changesAbilityGrid"></table>
	                                    </div>
	                                </div>
	
	                                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
	                                    <div class="index-common-title bottom-title">
	                                        <div class="index-common-title-left bottom-left"></div>
	                                        <div class="index-common-title-text bottom-text">人员异动情况（按组织）</div>
	                                    </div>
	                                    <div class="bottom-div2" id="changesOrganTable">
	                                        <table id="changesOrganGrid"></table>
	                                    </div>
	                                </div>
	                            </div>
	                            <div class="col-xs-12 view-dismiss-talent hide">
	                                <div class="col-xs-12 ct-col1" id="dimissionTable">
	                                    <table id="dimissionGrid"></table>
	                                    <table id="dimissionPaper"></table>
	                                </div>
	                            </div>
	
	                            <div class="col-xs-12 view-dismiss-rate hide">
									<div class="col-xs-12 ct-col1" id="accordDismissByOrganArea">
	                                    <table id="accordDismissByOrganGrid"></table>
	                                </div>
	                                <div class="clearfix"></div>
	                                <div class="col-xs-12 ct-col1">
	                                	<div class="accord-dismiss-year">
	                                		<i class="icon-exclamation-sign bigger-120 blue"></i>
	                                		全年累计流失率：<span id="accordDismissInYear">0</span>
	                                	</div>
	                                	<!-- <div class="alert-success" id="manpowerOccupyAll">
	                                        <i class="icon-exclamation-sign bigger-120 blue"></i>
											全年累计流失率：<span id="accordDismissInYear">0</span>
	                                    </div> -->
	                                </div>
	                                <div class="col-xs-12 ct-col1 height300" id="dimissionByYearMonthBar"></div>
	                            </div>
	
	                            <div class="col-xs-12 view-dismiss-dimension hide">
	                                <div class="col-xs-6 ct-col1">
	                                    <div class="">
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
	                                    <div class="">
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
	                </div>
	            </shiro:hasPermission>
	
	            <shiro:hasPermission name="ShengChanLi_RenLiChengBen">
	                <div class="panel panel-default ShengChanLi_RenLiChengBen" data-action="3">
	                    <div class="panel-heading">
	                        <span class="panel-title">三、人力成本分析</span>
	                        <a class="panel-link" href="javascript:void(0)"
	                           data-link="/manpowerCost/toManpowerCostView">查看更多&gt;&gt;</a>
	                    </div>
	                    <div class="panel-body">
	                        <div class="index-jxmb-tab">
	                            <div page="view-manpower-generalize" class="index-jxmb-btn index-jxmb-btn-select">概况</div>
	                            <div page="view-manpower-structure" class="index-jxmb-btn">结构</div>
	                            <div page="view-manpower-trend" class="index-jxmb-btn">趋势分析</div>
	                        </div>
	                        <div class="bottom-div">
								<div class="col-xs-12 ct-col1 view-manpower-generalize" id="manpowerCostArea">
                                    <table id="manpowerCostGrid"></table>
	                            </div>
	                            <div class="col-xs-12 view-manpower-structure hide">
	                                <div class="col-xs-6 ct-col1">
	                                    <div class="col-xs-12 padding0 height330" id="contrastDetailPie"></div>
	                                </div>
	                                <div class="col-xs-6 ct-colr">
	                                	<div class="col-xs-12 padding0 height330" id="salaryStructureChart"></div>
	                                </div>
	                            </div>
	
	                            <div class="col-xs-12 view-manpower-trend hide">
	                                <div class="col-xs-12"><span class="pull-left">(万元)</span>
	                                    <div class="alert-success" id="manpowerOccupyAll">
	                                        <i class="icon-exclamation-sign bigger-120 blue"></i>
											人力成本占总成本比：<span id="manpowerOccupyAllVal">50%</span>
	                                    </div>
	                                </div>
	                                <div style="height: 320px;" id="manpowerTrendChart"></div>
	                                <div class="row">&nbsp;</div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </shiro:hasPermission>
	
	            <shiro:hasPermission name="ShengRenLi_RenCaiSunYi">
	                <div class="panel panel-default ShengRenLi_RenCaiSunYi" data-action="4">
	                    <div class="panel-heading">
	                        <span class="panel-title">四、投入产出分析</span>
	                        <a class="panel-link" href="javascript:void(0)"
	                           data-link="/perBenefit/toPerBenefitView">查看更多&gt;&gt;</a>
	                    </div>
	                    <div class="panel-body">
	                        <div class="index-jxmb-tab">
	                            <div page="view-per-benefit" class="index-jxmb-btn index-jxmb-btn-select">人均效益分析
	                            </div>
	                            <div page="view-per-report" class="index-jxmb-btn">投资回报率分析</div>
	                        </div>
	                        <div class="bottom-div">
	                            <div class="col-xs-12 view-per-benefit">
	                                <div class="col-xs-4 ct-coll" id="organBenefitTable">
	                                    <div class="row">&nbsp;</div>
	                                    <table id="organBenefitGrid"></table>
	                                </div>
	                                <div class="col-xs-8 ct-col1">
	                                    <div class="height330" id="benefitsTrendChart"></div>
	                                </div>
	                            </div>
	                            <div class="col-xs-12 view-per-report hide">
	                                <div class="col-xs-4 ct-coll" id="salaryBringBackTable">
	                                    <div class="row">&nbsp;</div>
	                                    <table id="salaryBringBackGrid"></table>
	                                </div>
	                                <div class="col-xs-8 ct-col1">
	                                    <div class="height330" id="salaryBringBackChart"></div>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </shiro:hasPermission>
	
	            <shiro:hasPermission name="ShengRenLi_LaoDongLiXiaoNeng">
	                <div class="panel panel-default ShengRenLi_LaoDongLiXiaoNeng" data-action="5">
	                    <div class="panel-heading">
	                        <span class="panel-title">五、劳动力效能分析</span>
	                        <a class="panel-link" href="javascript:void(0)"
	                           data-link="/laborEfficiency/toLaborEfficiencyView">查看更多&gt;&gt;</a>
	                    </div>
	                    <div class="panel-body">
	                        <div class="bottom-div">
	                            <div class="col-xs-12" id="laborRatioTable">
	                                <div class="bottom-div-first"><span>统计说明：出勤时间及加班时间统计单位为小时</span></div>
	                                <table id="laborRatioGrid"></table>
	                            </div>
	                        </div>
	
	                    </div>
	                </div>
	            </shiro:hasPermission>
	
	            <shiro:hasPermission name="ShengChanLi_recruitBoard">
	                <div class="panel panel-default ShengChanLi_recruitBoard" data-action="6">
	                    <div class="panel-heading">
	                        <span class="panel-title">六、招聘分析</span>
	                        <a class="panel-link" href="javascript:void(0)"
	                           data-link="/recruitBoard/toRecruitBoardView">查看更多&gt;&gt;</a>
	                    </div>
	                    <div class="panel-body">
	                        <div class="index-jxmb-tab">
	                            <div page="view-recruit-demand" class="index-jxmb-btn index-jxmb-btn-select">需求分析
	                            </div>
	                            <div page="view-recruit-channel" class="index-jxmb-btn">渠道分析</div>
	                        </div>
	                        <div class="bottom-div">
	                            <div class="col-xs-12 view-recruit-demand" id="demandTable">
	                                <table id="demandGrid"></table>
	                            </div>
	                            <div class="col-xs-12 view-recruit-channel hide">
	                                <div class="dis-search clearfix">
	                                    <input class="dis-search-input" id="channelTxt" type="text"
	                                           placeholder="请输入岗位名称">
	                                    <div class="add-on dis-search-input-btn" id="channelBtn">搜索</div>
	                                </div>
	                                <div class="col-xs-12 ct-col1" id="channelTable"></div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </shiro:hasPermission>
	
	            <shiro:hasPermission name="ShengRenLi_PeiXunKanBan">
	                <div class="panel panel-default ShengRenLi_PeiXunKanBan" data-action="7">
	                    <div class="panel-heading">
	                        <span class="panel-title">七、培训分析</span>
	                        <a class="panel-link" href="javascript:void(0)"
	                           data-link="/trainBoard/toTrainBoardView">查看更多&gt;&gt;</a>
	                    </div>
	                    <div class="panel-body">
	                        <div class="index-jxmb-tab">
	                            <div page="view-train-generalize" class="index-jxmb-btn index-jxmb-btn-select">概况
	                            </div>
	                            <div page="view-train-structure" class="index-jxmb-btn">结构</div>
	                        </div>
	                        <div class="bottom-div">
	                            <div class="col-xs-12 view-train-generalize" id="trainGeneralTable">
	                            	<div class="row">&nbsp;</div>
	                                <table class="borderless" id="trainGeneralGrid"></table>
	                            </div>
	                            <div class="col-xs-12 view-train-structure hide">
	                                <div class="col-xs-4 ct-col1">
	                                    <div class="col-xs-12 height320" id="trainTypeNumChart"></div>
	                                </div>
	                                <div class="col-xs-8 ct-col1" id="trainTypeTable">
	                                    <table class="borderless" id="trainTypeGrid"></table>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </shiro:hasPermission>
	
	            <shiro:hasPermission name="ShengChanLi_GeRenJiXiaoJiBianHua">
	                <div class="panel panel-default ShengChanLi_GeRenJiXiaoJiBianHua" data-action="8">
	                    <div class="panel-heading">
	                        <span class="panel-title">八、绩效分析</span>
	                        <a class="panel-link" href="javascript:void(0)" data-link="/perChange/toPerChangeView">查看更多&gt;&gt;</a>
	                    </div>
	                    <div class="panel-body">
	                        <div class="index-jxmb-tab">
	                            <div page="view-change-generalize" class="index-jxmb-btn index-jxmb-btn-select">概况
	                            </div>
	                            <div page="view-change-condition" class="index-jxmb-btn">变化情况</div>
	                        </div>
	                        <div class="bottom-div">
	                            <div class="col-xs-12 view-change-generalize" id="perchangeByOrganTable">
									<table id="perchangeByOrganGrid"></table>
								</div>
	                            <div class="col-xs-12 view-change-condition hide">
	                                <div class="col-xs-6 ct-col1 mainArea">
	                                    <div class="col-sm-12 titleArea">绩效结果变化趋势</div>
	                                    <div class="col-sm-12 chartArea">
	                                        <div class="col-sm-12 " id="preChange"></div>
	                                    </div>
	                                    <div class="col-sm-12 textArea">
											说明：<br>
	                                        • 有所进步：与上期绩效相比，提升一级或以上<br>
	                                        • 出现下滑：与上期绩效相比，下降一级或以上
	                                    </div>
	                                </div>
	                                <div class="col-xs-6 ct-colr mainArea">
	                                    <div class="col-sm-12 titleArea">绩效异常（大起大落）</div>
	                                    <div class="col-sm-12 chartArea">
	                                        <div class="col-sm-12 " id="preUnusual"></div>
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
	            </shiro:hasPermission>
	
	            <shiro:hasPermission name="QuDongLi_JinShengKanBan">
	                <div class="panel panel-default QuDongLi_JinShengKanBan" data-action="9">
	                    <div class="panel-heading">
	                        <span class="panel-title">九、晋级分析</span>
	                        <a class="panel-link" href="javascript:void(0)"
	                           data-link="/promotionBoard/toPromotionBoardView">查看更多&gt;&gt;</a>
	                    </div>
	                    <div class="panel-body">
	                        <div class="index-jxmb-tab">
	                            <div page="view-promotion-generalize" class="index-jxmb-btn index-jxmb-btn-select">
	                                概况
	                            </div>
	                            <div page="view-promotion-sequence" class="index-jxmb-btn">人员序列及职级分布</div>
	                        </div>
	                        <div class="bottom-div">
	                            <div class="col-xs-12 view-promotion-generalize" id="promotionGeneralizeTable">
	                            	<div class="row">&nbsp;</div>
									<table id="promotionGeneralizeGrid"></table>
								</div>
	                            <div class="col-xs-12 view-promotion-sequence hide" id="promotionInJobsTables">
	                                <div class="row">&nbsp;</div>
	                                <table id="promotionInJobsGrid"></table>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </shiro:hasPermission>
	
	            <shiro:hasPermission name="ShengChanLi_XiaoShouKanBan">
	                <div class="panel panel-default ShengChanLi_XiaoShouKanBan" data-action="10">
	                    <div class="panel-heading">
	                        <span class="panel-title">十、销售分析</span>
	                        <a class="panel-link" href="javascript:void(0)"
	                           data-link="/salesBoard/toSalesBoardView">查看更多&gt;&gt;</a>
	                    </div>
	                    <div class="panel-body">
	                        <div class="index-jxmb-tab">
	                            <div page="view-sales-product" class="index-jxmb-btn index-jxmb-btn-select">
	                                各品种销售情况
	                            </div>
	                            <div page="view-sales-oragn" class="index-jxmb-btn">各部门销售情况</div>
	                        </div>
	                        <div class="bottom-div">
	                            <div class="col-xs-12 ct-col2 view-sales-product" id="salesByProductTable">
	                                <table id="salesByProductGrid"></table>
	                                <div class="row clearfix salesMargin30">
	                                    <div class="col-xs-2 btn-group">
	                                        <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
	                                            Action
	                                            <i class="icon-angle-down icon-on-right"></i>
	                                        </button>
	                                        <ul class="dropdown-menu ct-mCustomScrollBar overflowauto height350" id="salesByProductSelector">
	                                            <%--<li>--%>
	                                                <%--<a href="#">Action</a>--%>
	                                            <%--</li>--%>
	
	                                            <%--<li>--%>
	                                                <%--<a href="#">Another action</a>--%>
	                                            <%--</li>--%>
	
	                                            <%--<li>--%>
	                                                <%--<a href="#">Something else here</a>--%>
	                                            <%--</li>--%>
	
	                                            <%--<li class="divider"></li>--%>
	
	                                            <%--<li>--%>
	                                                <%--<a href="#">Separated link</a>--%>
	                                            <%--</li>--%>
	                                        </ul>
	                                    </div>
	                                    <div class="col-xs-10 clearfix">
	                                        <div class="height350" id="salesByProductChart"></div>
	                                    </div>
	                                </div>
	                            </div>
	                            <div class="col-xs-12 ct-col2 view-sales-oragn hide" id="salesByOrganTable">
	                                <table id="salesByOrganGrid"></table>
	                                <div class="row clearfix salesMargin30">
	                                    <div class="col-xs-2 btn-group">
	                                        <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
	                                            Action
	                                            <i class="icon-angle-down icon-on-right"></i>
	                                        </button>
	                                        <ul class="dropdown-menu ct-mCustomScrollBar overflowauto height350" id="salesByOrganSelector">
	                                            <%--<li>--%>
	                                                <%--<a href="#">Action</a>--%>
	                                            <%--</li>--%>
	
	                                            <%--<li>--%>
	                                                <%--<a href="#">Another action</a>--%>
	                                            <%--</li>--%>
	
	                                            <%--<li>--%>
	                                                <%--<a href="#">Something else here</a>--%>
	                                            <%--</li>--%>
	
	                                            <%--<li class="divider"></li>--%>
	
	                                            <%--<li>--%>
	                                                <%--<a href="#">Separated link</a>--%>
	                                            <%--</li>--%>
	                                        </ul>
	                                    </div>
	                                    <div class="col-xs-10 clearfix">
	                                        <div class="height350" id="salesByOrganChart"></div>
	                                    </div>
	                                </div>
	                            </div>
	                            <div class="row">&nbsp;</div>
	                        </div>
	                    </div>
	                </div>
	            </shiro:hasPermission>
            </div>
        </div>

        <div id="page-two" class="rightBodyPage ct-col2">
            <input id="remotePath" type="hidden" value="${remotePath}">
            <div class="col-sm-12 SetUpBody" id="favoritesBlock">
                <%--<div class="widget-box">--%>
                <%--<div class="widget-header header-color-blue4">--%>
                <%--<h5 class="bolder smaller">Choose Categories</h5>--%>
                <%--</div>--%>

                <%--<div class="widget-body">--%>
                <%--<div class="widget-main padding-both-30">--%>
                <%--<ul class="list-unstyled spaced3">--%>
                <%--<li><i class="zrw-circle"></i>200 GB Disk Space<span class="li-remove">取消收藏</span><span class="li-link">查看</span></li>--%>

                <%--<li><i class="zrw-circle"></i>Unlimited Bandwidth<span class="li-remove">取消收藏</span><span class="li-link">查看</span></li>--%>

                <%--<li><i class="zrw-circle"></i>1000 Email Accounts<span class="li-remove">取消收藏</span><span class="li-link">查看</span></li>--%>

                <%--<li><i class="zrw-circle"></i>200 MySQL Databases<span class="li-remove">取消收藏</span><span class="li-link">查看</span></li>--%>

                <%--<li><i class="zrw-circle"></i>$25 Ad Credit<span class="li-remove">取消收藏</span><span class="li-link">查看</span></li>--%>

                <%--<li><i class="zrw-circle"></i>Free Domain<span class="li-remove">取消收藏</span><span class="li-link">查看</span></li>--%>
                <%--</ul>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>
            </div>
        </div>

        <div id="page-three" class="rightBodyPage ct-col2">
            <div class="col-sm-12 SetUpBody" id="shareBlock">
                <%--<div class="widget-box">--%>
                <%--<div class="widget-header header-color-blue4">--%>
                <%--<h5 class="bolder smaller">Choose Categories</h5>--%>
                <%--</div>--%>

                <%--<div class="widget-body">--%>
                <%--<div class="widget-main padding-both-30">--%>
                <%--<ul class="list-unstyled spaced3">--%>
                <%--<li><i class="zrw-circle"></i>200 GB Disk Space<span class="li-remove">取消收藏</span><span class="li-link">查看</span></li>--%>

                <%--<li><i class="zrw-circle"></i>Unlimited Bandwidth<span class="li-remove">取消收藏</span><span class="li-link">查看</span></li>--%>

                <%--<li><i class="zrw-circle"></i>1000 Email Accounts<span class="li-remove">取消收藏</span><span class="li-link">查看</span></li>--%>

                <%--<li><i class="zrw-circle"></i>200 MySQL Databases<span class="li-remove">取消收藏</span><span class="li-link">查看</span></li>--%>

                <%--<li><i class="zrw-circle"></i>$25 Ad Credit<span class="li-remove">取消收藏</span><span class="li-link">查看</span></li>--%>

                <%--<li><i class="zrw-circle"></i>Free Domain<span class="li-remove">取消收藏</span><span class="li-link">查看</span></li>--%>
                <%--</ul>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>
            </div>
        </div>
    </div>

    <!-- 第一次设置弹出层 start -->
    <div class="modal fade popup-modal" id="firstSettingModal" tabindex="-1" role="dialog"
         aria-labelledby="settingModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-header-text">月报设置</div>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="row ct-row"><h4>欢迎进入月报统计，请您设置关注的统计内容：</h4></div>
                    <div class="row ct-col" id="fisrtSettingBlock"></div>
                </div>
                <div class="modal-footer">
                    <div class="modal-btn success-btn" id="firstSettingBtn">确定</div>
                    <div class="modal-btn default-btn" data-dismiss="modal">取消</div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    <!-- 第一次设置弹出层 end -->

    <!-- 设置弹出层 start -->
    <div class="modal fade popup-modal" id="settingModal" tabindex="-1" role="dialog"
         aria-labelledby="settingModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-header-text">设置</div>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class=""><h5>统计内容：</h5><span>已显示<!-- （左右拖拽可以排序） --></span></div>
                    <div class="row16">&nbsp;</div>
                    <div class="modal-body-main gridly" id="modalShowBlock">
                    	<!-- <div class="setting-panel">
                    		<div class="setting-panel-remove-icon"></div>
                    		<span class="setting-panel-text">人员结构分析</span>
                    	</div> -->
                    </div>
                    <div class="row30">&nbsp;</div>
                    <div class=""><span>未显示</span></div>
                    <div class="row16">&nbsp;</div>
                    <div class="modal-body-main gridly" id="modalHideBlock">
                    	<!-- <div class="setting-panel">
                    		<div class="setting-panel-add-icon"></div>
                    		<span class="setting-panel-text">人员结构分析</span>
                    	</div> -->
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="modal-btn success-btn" id="settingSubmitBtn">确定</div>
                    <div class="modal-btn default-btn" data-dismiss="modal">取消</div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    <!-- 设置弹出层 end -->
</div>

    <!-- 分享发送弹出层 start -->
    <div class="modal fade popup-modal" id="shareSendingModal" role="dialog"
         aria-labelledby="settingModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-header-text">月报分享</div>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                   	<table>
                   		<tr>
                   			<td><span class="span-text">分享内容：</span></td>
                   			<td><span class="span-text" id="shareContent"></span></td>
                   		</tr>
                   		<tr>
                   			<td><span class="span-text">分享给谁：</span></td>
                   			<td><select id="txtKey"></select></td>
                   		</tr>
                   		<tr>
                   			<td><span class="vertical-align-top span-text">备注：</span></td>
                   			<td><textarea id="note"></textarea></td>
                   		</tr>
                   		<tr>
                   			<td>&nbsp;</td>
                   			<td><input type="checkbox" class="vertical-align-middle" id="sendEmail">
                   				<span class="text-send vertical-align-middle">同时发送邮件</span>
                   			</td>
                   		</tr>
                   	</table>
                </div>
                <div class="modal-footer">
                    <div class="modal-btn success-btn" id="shareSubmit">确定</div>
                    <div class="modal-btn default-btn" data-dismiss="modal">取消</div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    <!-- 分享发送弹出层 end -->


    <!-- 异动详情弹出层 start -->
    <div class="modal fade popup-modal" id="changesDetailModal" tabindex="-1" role="dialog"
         aria-labelledby="settingModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-header-text">人员名单</div>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="index-jxmb-tab">
                        <div page="1" class="index-jxmb-btn index-jxmb-btn-select">入职</div>
                        <div page="2" class="index-jxmb-btn">调入</div>
                        <div page="3" class="index-jxmb-btn">调出</div>
                        <div page="4" class="index-jxmb-btn">离职</div>
                    </div>
                    <div class="bottom-div bottom-div2">
                        <div class="row">&nbsp;</div>
                        <div class="col-xs-12 ct-col1" id="changesDetailsTable">
                            <table id="changesDetailsGrid"></table>
                            <table id="changesDetailsPager"></table>
                        </div>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    <!-- 第一次设置弹出层 end -->

    <script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
 	<script type="text/javascript" src="${jsRoot}biz/other/monthReport.js"></script>
<%--     <script src="${ctx}/assets/js/biz/other/monthReport.js"></script> --%>
</body>
</html>