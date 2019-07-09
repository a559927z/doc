<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>销售看板</title>
    <link rel="stylesheet" href="${ctx}/assets/css/talent-map2.css" />
    <link rel="stylesheet" href="${ctx}/assets/css/datetime/datetimepicker.css" />
    <link rel="stylesheet" href="${ctx}/assets/css/select2/select2.min.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"  />
    <link rel="stylesheet" href="${ctx}/assets/css/jBootsrapPage.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/productivity/salesBoard.css"/>
</head>
<body>
<input type="hidden" id="month" value="${month}" />
<input type="hidden" id="red" value="${red}" />
<input type="hidden" id="yellow" value="${yellow}" />
<input type="hidden" id="red_ywnlkh" value="${red_ywnlkh}" />
<input type="hidden" id="yellow_ywnlkh" value="${yellow_ywnlkh}" />
<input type="hidden" id="num" value="${num}" />
<input type="hidden" id="names" value="${names}" />
<input type="hidden" id="yellowRanges" value="${yellowRanges}" />
<input type="hidden" id="redRanges" value="${redRanges}" />
<input type="hidden" id="notes" value="${notes}" />
<div class="sales-board" id="salesBoard">
	<div class="leftBody">
		<div class="leftListBigDiv">销售看板</div>
		<div page="page-one" class="leftListDiv selectList">总览</div>
		<div page="page-two" class="leftListDiv" id="selectListTwo">维度分析</div>
		<div page="page-three" class="leftListDiv">销售排名地图</div>
		<div page="page-four" class="leftListDiv">销量分析</div>
	</div>
	<div class="rightBody rightBody10">
        <div id="page-one" class="rightBodyPage">
			<div class="col-sm-12 ct-col1">
				<div class="index-common-title">
					<div class="index-common-title-left"></div>
					<div class="index-common-title-text">销售预警</div>
					<div class="index-common-title-img">
						<img class="salesWarningImg" alt="销售预警设置" src="${ctx}/assets/img/index/setUp.png">
					</div>
				</div>
			</div>
			<div class="col-sm-3 ct-line-col">
				<div class="top-div">
					<div class="index-common-title">
						<div class="index-common-title-text">销售额(<span id="salesValMonth">${month}</span>月)</div>
					</div>
					<div class="body-div" id="perfDismissArea">
						<div class="accord-yj-float">
							<span class="accord-yj-float-value" id="salesValNum"></span>
							<span class="accord-yj-float-people">万元</span>
						</div>
						<!-- <div class="accord-bottom-float">
							<div class="accord-bottom-float-text">较上月</div>
							<div class="accord-bottom-float-arrow"></div>
							<div class="accord-bottom-float-value" id="salesValCompare">0</div>
							<div class="accord-bottom-float-people">%</div>
						</div> -->
						<div class="accord-bottom-float">
							<div class="accord-bottom-float-text hide">
								较上月<span class="upDown">下降</span><span id="salesValCompare"></span>%
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-3 ct-line-col">
				<div class="top-div">
					<div class="index-common-title">
						<div class="index-common-title-text">销售额异常组织</div>
					</div>
					<div class="body-div" id="perfDismissArea">
						<div class="accord-yj-float">
							<span class="accord-yj-float-value" id="exctOrganName"></span>
							<span class="accord-yj-float-people this-hide" id="exctUnit">个</span>
						</div>
						<div class="accord-bottom-float">
							<div class="accord-bottom-float-text">
								<div class="this-hide" id="oneCompare">较上月下降<span id="exctOneCompare"></span>%</div>
								<div class="this-hide" id="moreCompare">较上月下降<span id="exctMoreCompare1"></span>%-
									<span id="exctMoreCompare2"></span>%
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-3 ct-line-col">
				<div class="top-div">
					<div class="index-common-title">
						<div class="index-common-title-text">人员流失异常</div>
					</div>
					<div class="body-div" id="perfDismissArea">
						<div class="accord-yj-float">
							<span class="accord-yj-float-value" id="personFlowNum"></span>
							<span class="accord-yj-float-people">%</span>
						</div>
						<div class="accord-bottom-float">
							<div class="accord-bottom-float-text this-hide" id="personFlowInfo">
								流失率偏高
							</div>
							<div class="accord-bottom-float-text this-hide" id="personFlowInfo2">
								流失率严重偏高
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-3 ct-line-col">
				<div class="top-div">
					<div class="index-common-title">
						<div class="index-common-title-text">业务能力考核</div>
					</div>
					<div class="body-div" id="perfDismissArea">
						<div class="accord-yj-float">
							<span class="accord-yj-float-value" id="abilityCheckNum"></span>
							<span class="accord-yj-float-people">%</span>
						</div>
						<div class="accord-bottom-float">
							<div class="accord-bottom-float-text this-hide" id="abilityCheckInfo">
								考核通过率偏低
							</div>
							<div class="accord-bottom-float-text this-hide" id="abilityCheckInfo2">
								考核通过率严重偏低
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-8 ct-line-col" view="chart">
				<div class="index-common-title bottom-title marginLeft-10">
					<div class="index-common-title-left bottom-left"></div>
					<div class="index-common-title-text bottom-text">今日统计</div>
				</div>
				<div class="bottom-div">
					<div class="chart-view">
						<div class="col-xs-12 top-row">
							<div class="top-row-block">
								<div class="top-tab-group pull-left">
									<div class="top-tab-unit pull-left">今日销售量</div>
									<div class="top-tab-num pull-left">
										<span id="todaySalesNumId"></span>
										<span>件</span>
									</div>
								</div>
								<div class="top-tab-group pull-left">
									<div class="top-tab-unit pull-left">今日销售额</div>
									<div class="top-tab-num pull-left">
										<span id="todaySalesValId"></span>
										<span>万元</span>
									</div>
								</div>
								<div class="top-tab-group pull-left marginRight0">
									<div class="top-tab-unit pull-left">今日利润</div>
									<div class="top-tab-num pull-left">
										<span id="todaySalesGainId"></span>
										<span>万元</span>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-12 center-map" id="salesMapChart"></div>
						<div class="col-xs-12">
							<span>统计说明：今日销售量/销售额/利润统计范围均为当前组织</span>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-4 ct-line-col" view="chart">
				<div class="index-common-title bottom-title marginLeft-10">
					<div class="index-common-title-left bottom-left"></div>
					<div class="index-common-title-text bottom-text">团队排行榜
						<img src="${ctx}/assets/img/base/tip.gif" data-toggle="tooltip" 
							data-placement="right" data-original-title="销售额排行榜，统计单位为万元。"/>
					</div>
					<div class="index-common-title-text bottom-timebox" id="teamRankingTimeBox"></div>
				</div>
				<div class="bottom-div-2">
					<div class="col-xs-12 right-center-btn">
						<a class="hide" id="returnOrgan">
							<label class="hide returnOrganText">返回区域</label>
							<label class="hide returnTeamText">返回团队</label>
						</a>
					</div>
					<div class="col-xs-12 right-center" id="teamRankingChart"></div>
				</div>
				<div class="index-common-title bottom-title bottom-title-right marginLeft-10">
					<div class="index-common-title-left bottom-left"></div>
					<div class="index-common-title-text bottom-text">销售排名榜</div>
					<div class="index-common-title-text bottom-timebox" id="salesRankingTimeBox"></div>
				</div>
				<div class="bottom-div-2">
					<div class="right-grid">
						<div class="show-more-div">
							<div id="showMoreDiv">
								<a class="cursorPointer hide" id="showMore">查看更多>></a>
							</div>
							<div class="hide no-div" id="gridNoDatas">暂无数据</div>
						</div>
					</div>
				</div>
			</div>
        </div>

        <div id="page-two" class="rightBodyPage">
			<div class="col-sm-12 ct-container-fluid SetUpBody" view="chart">
				<div class="index-common-title bottom-title">
					<div class="index-common-title-left bottom-left"></div>
					<div class="index-common-title-text bottom-text">销售趋势</div>
					<div class="rightSetUpBtn">
						<div class="rightSetUpBtnDiv rightSetUpLeft icon rightSetUpBtnSelect">
							<div class="rightSetUpBtnTop"></div>
							<div class="rightSetUpLeftShowIcon"></div>
							<div class="rightSetUpLeftHideIcon"></div>
						</div>
						<div class="rightSetUpBtnDiv rightSetUpRight icon">
							<div class="rightSetUpBtnTop"></div>
							<div class="rightSetUpRightShowIcon"></div>
							<div class="rightSetUpRightHideIcon"></div>
						</div>
					</div>
				</div>
				<div class="bottom-div height540">
					<div class="sales-selection" id="salesTime"></div>
					<div class="chart-view">
						<div class="col-xs-12 bottom-div-first">
							<!-- <span id="salesTypeName">销售额</span> -->
						</div>
						<div class="col-xs-12 height485" id="salesTrendChart"></div>
					</div>
					<div class="table-view col-xs-12" id="salesTrendArea">
						<table id="salesTrendGrid"></table>
					</div>
				</div>
			</div>
			<div class="col-sm-12 ct-container-fluid SetUpBody" view="chart">
				<div class="index-common-title bottom-title marginTop20">
					<div class="index-common-title-left bottom-left"></div>
					<div class="index-common-title-text bottom-text">销售明细</div>
				</div>
				<div class="nav-btn" view="team">
					<div class="index-common-title bottom-title">
						<div class="leftSetUpBtn">
							<div class="leftSetUpBtnDiv leftSetUpLeft leftSetUpBtnSelect">
								<div class="leftSetUpBtnTop"></div>
								<div class="text">团队</div>
							</div>
							<div class="leftSetUpBtnDiv leftSetUpRight" id="personSlected">
								<div class="leftSetUpBtnTop"></div>
								<div class="text">个人</div>
							</div>
						</div>
					</div>
					<div class="team-view borderStyle">
						<div class="col-xs-12">
							<div class="left time-div" id="teamTime"></div>
							<div class="more-search-label icon-panel-down borderBottom0" id="teamMoreLabel">更多筛选条件</div>
							<form id="teamConditionSearchForm" class="conditionSearchForm" action="">
								<div class="condition-body" id="employeeCondition" style="display: none;">
									<div class="col-sm-12 ct-line-col conditionBtnListBody">
										<div class="div-bottom-line"></div>
									</div>
									<div class="condition-body-list condition-body-list-too">
										<div class="condition-body-list-title left" id="">组织架构：</div>
										<ul class="condition-input-text left" id="subOrgan"></ul>
									</div>
									<div class="condition-body-list condition-body-list-too">
										<div class="condition-body-list-title left" id="">达标率：</div>
										<select class="condition-input-text left" id="teamStandardRate">
											<option selected="selected" value="0">全部</option>
											<option value="1">已达标</option>
											<option value="2">未达标</option>
										</select>
									</div>
									<div class="condition-body-list">
										<div class="condition-body-list-title left">销售额：</div>
										<input type="text" id="beginSales" class="condition-input-text left" onkeyup="value=value.replace(/[^\d]/g,'')" />
										<span class="condition-text-one left">万元</span>
										<span class="condition-text-one left">-</span>
										<input type="text" id="endSales" class="condition-input-text condition-input-text-too left" onkeyup="value=value.replace(/[^\d]/g,'')" />
										<span class="condition-text-one left">万元</span>
									</div>
									<div class="condition-body-list">
										<div class="condition-body-list-title left">回款额：</div>
										<input type="text" id="beginReturnAmount" class="condition-input-text left" onkeyup="value=value.replace(/[^\d]/g,'')" />
										<span class="condition-text-one left">万元</span>
										<span class="condition-text-one left">-</span>
										<input type="text" id="endReturnAmount" class="condition-input-text condition-input-text-too left" onkeyup="value=value.replace(/[^\d]/g,'')" />
										<span class="condition-text-one left">万元</span>
									</div>
									<div class="condition-body-bottom">
										<div class="condition-body-customBtn" id="teamSearch">搜索</div>
										<div class="condition-body-clearBtn">清除</div>
									</div>
								</div>
							</form>
						</div>
						<div class="clearfix"></div>
						<!-- <div class="col-xs-12 marginTop20" id="teamView"></div> -->
						<div class="cardTabel" id="teamView_tabel"></div>
					</div>
					<div class="person-view borderStyle">
						<div class="col-xs-12">
							<div class="left time-div marginTop14" id="personTime"></div>
							<div class="left marginTop20 marginLeft10">
								<input type="text" placeholder="请输入团队/姓名进行搜索" class="search-input left" id="personText" />
								<div class="search-btn left" id="searchText">搜索</div>
							</div>
							<div class="more-search-label icon-panel-down borderBottom0" id="personMoreLabel">更多筛选条件</div>
							<form id="personConditionSearchForm" class="conditionSearchForm margin0" action="">
								<div class="condition-body" style="display: none;">
									<div class="col-sm-12 ct-line-col conditionBtnListBody">
										<div class="div-bottom-line"></div>
									</div>
									<div class="condition-body-list condition-body-list-too">
										<div class="condition-body-list-title left" id="">达标率：</div>
										<select class="condition-input-text left" id="personStandard">
											<option selected="selected" value="0">全部</option>
											<option value="1">已达标</option>
											<option value="2">未达标</option>
										</select>
									</div>
									<div class="condition-body-list">
										<div class="condition-body-list-title left">销售额：</div>
										<input type="text" id="beginPersonSales" class="condition-input-text left" onkeyup="value=value.replace(/[^\d]+/g,'')" />
										<span class="condition-text-one left">万元</span>
										<span class="condition-text-one left">-</span>
										<input type="text" id="endPersonSales" class="condition-input-text condition-input-text-too left" onkeyup="value=value.replace(/[^\d]/g,'')" />
										<span class="condition-text-one left">万元</span>
									</div>
									<div class="condition-body-list">
										<div class="condition-body-list-title left">回款额：</div>
										<input type="text" id="beginPersonAmount" class="condition-input-text left" onkeyup="value=value.replace(/[^\d]/g,'')" />
										<span class="condition-text-one left">万元</span>
										<span class="condition-text-one left">-</span>
										<input type="text" id="endPersonAmount" class="condition-input-text condition-input-text-too left" onkeyup="value=value.replace(/[^\d]/g,'')" />
										<span class="condition-text-one left">万元</span>
									</div>
									<div class="condition-body-bottom">
										<div class="condition-body-customBtn" id="personSearch">搜索</div>
										<div class="condition-body-clearBtn">清除</div>
									</div>
								</div>
							</form>
						</div>
						<div class="clearfix"></div>
						<div class="cardTabel" id="personView_tabel"></div>
					</div>
				</div>
			</div>
		</div>
        <div id="page-three" class="rightBodyPage">
			<div class="col-sm-12 ct-container-fluid SetUpBody" view="chart-1">
				<div class="index-common-title bottom-title">
					<div class="leftSetUpBtn">
						<div class="leftSetUpBtnDiv leftSetUpLeft leftSetUpBtnSelect">
							<div class="leftSetUpBtnTop"></div>
							<div class="text">团队总览</div>
						</div>
						<div class="leftSetUpBtnDiv leftSetUpRight">
							<div class="leftSetUpBtnTop"></div>
							<div class="text">团队PK</div>
						</div>
					</div>
				</div>
				<div class="bottom-div-three height560">
					<div class="chart-view-1 maps-pk-div">
						<div class="maps-ts hide" id="viewInfo">
							<img src="${ctx}/assets/img/widgets/notify_info.png">
							<span>为保证显示效果，地图隐藏部分人员。如需查看所有人员，请点击
								<span class="maps-ts-btn" id="showFullTeamView">全屏显示</span>
							</span>
						</div>
						<div class="maps-time">
							<div class="time-div marginLeft10" id="teamViewTimeBox"></div>
						</div>
						<div class="row ct-row sales-maps-chart">
							<div class="col-sm-10 col-xs-12 padding0">
								<div class="u-map" id="salesTeamViewChart"></div>
							</div>
							<div class="col-sm-2 col-xs-12 maps-chart-right">
								<div class="loading-img" id="teamViewLoading"></div>
								<div>能力匹配销售排名</div>
								<div class="maps-gird-group">
									<div class="progress matching-progress">
										<div class="progress-bar" role="progressbar"
											aria-valuenow="70" aria-valuemin="0" aria-valuemax="100"
											id="normalBar">
											<span class="sr-only" id="normalBarVal">0人</span>
										</div>
									</div>
									<div class="maps-grid-circle matching-circle-bg">
										<span id="normalPerVal">0</span><span>%</span>
									</div>
								</div>
								<div>能力优于销售排名</div>
								<div class="maps-gird-group">
									<div class="progress nomatching-progress">
										<div class="progress-bar" role="progressbar"
											aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"
											id="highBar">
											<span class="sr-only" id="highBarVal">0人</span>
										</div>
									</div>
									<div class="maps-grid-circle nomatching-circle-bg">
										<span id="highPerVal">0</span><span>%</span>
									</div>
								</div>
								<div>能力低于销售排名</div>
								<div class="maps-gird-group">
									<div class="progress nomatching-progress">
										<div class="progress-bar" role="progressbar"
											aria-valuenow="10" aria-valuemin="0" aria-valuemax="100"
											id="lowBar">
											<span class="sr-only" id="lowBarVal">0人</span>
										</div>
									</div>
									<div class="maps-grid-circle nomatching-circle-bg">
										<span id="lowPerVal">0</span><span>%</span>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="chart-view-2 maps-pk-div">
						<div class="pk-group-div" id="maps-pk-one">
							<div class="maps-pk-title">您可直接选择下级团队查看不同团队的销售排名地图</div>
							<div class="maps-pk-remind">注:&nbsp;最多选择3个团队且总人数不能超过1000人</div>
							<div class="maps-pk-main clearfix" id="organPK"></div>
							<div class="maps-pk-bottom">
								<button class="btn btn-info left" id="organTeamBtn">
									开始PK(<span id="organTeamNum">0</span>)
								</button>
								<a href="javascript:void(0)" id="customTeam" class="checkteam-link left">我要自定义团队</a>
							</div>
						</div>
						<div class="pk-group-div hide" id="maps-pk-two">
							<div class="maps-pk-title">您可点击设置条件组成多个团队查看销售排名地图</div>
							<div class="maps-pk-remind">注:&nbsp;最多选择3个团队且总人数不能超过1000人</div>
							<div class="maps-pk-main clearfix" id="selfPK">
								<div class="team-bottom-div left">
									<div class="circle left">
										<span>1</span>
									</div>
									<div class="bottom-text left">
										<div class="team-name"  data-toggle="tooltip" 
											data-placement="right" data-original-title="团队1"><span>团队1</span></div>
										<a href="javascript:void(0)">设置条件</a>
									</div>
									<div class="bottom-img left close this-hide">
										<i class="icon-remove"></i>
									</div>
								</div>
								<div class="team-bottom-div left">
									<div class="circle left">
										<span>2</span>
									</div>
									<div class="bottom-text left">
										<div class="team-name"  data-toggle="tooltip" 
											data-placement="right" data-original-title="团队2"><span>团队2</span></div>
										<a href="javascript:void(0)">设置条件</a>
									</div>
									<div class="bottom-img left close this-hide">
										<i class="icon-remove"></i>
									</div>
								</div>
								<div class="team-bottom-div left">
									<div class="circle left">
										<span>3</span>
									</div>
									<div class="bottom-text left">
										<div class="team-name"  data-toggle="tooltip" 
											data-placement="right" data-original-title="团队3"><span>团队3</span></div>
										<a href="javascript:void(0)">设置条件</a>
									</div>
									<div class="bottom-img left close this-hide">
										<i class="icon-remove"></i>
									</div>
								</div>
							</div>
							<div class="maps-pk-bottom">
								<button class="btn btn-info left" id="customTeamBtn">开始PK</button>
								<a href="javascript:void(0)" id="checkSuborgan" class="checkteam-link left">选择下级组织快速PK</a>
							</div>
						</div>
						<div class="hide" id="maps-pk-three">
							<div class="pk-title-div cursorPointer" id="returnTeamPK">
								<img src="${ctx}/assets/img/widgets/notify_return.png">
								<div class="maps-return">
									<div>选</div><div>择</div><div>团</div><div>队</div>
								</div>
							</div>
							<div class="maps-ts" id="pkInfo">
								<img src="${ctx}/assets/img/widgets/notify_info.png">
								<span>为保证显示效果，地图隐藏部分人员。如需查看所有人员，请点击
									<span class="maps-ts-btn" id="showFullTeamPK">全屏显示</span>
								</span>
							</div>
							<div class="maps-time">
								<div class="time-div" id="teamPKTimeBox"></div>
							</div>
							<div class="row ct-row sales-maps-chart">
								<%-- <div class="col-sm-1 map-axis-Block" id="mapAxisBlock">
									<div class="map-axis-hide">
										<img src="${ctx}/assets/img/widgets/notify_info.png">
									选择团队</div>
								</div> --%>
								<div class="col-sm-10 col-xs-12 padding0">
									<div class="u-map" id="salesTeamPKChart"></div>
								</div>
								<div class="col-sm-2 col-xs-12 maps-chart-right">
									<div class="loading-img" id="teamPKLoading"></div>
									<div>能力匹配销售排名</div>
									<div class="maps-gird-group">
										<div class="progress matching-progress">
											<div class="progress-bar" role="progressbar"
												aria-valuenow="70" aria-valuemin="0" aria-valuemax="100"
												id="pkNormalBar">
												<span class="sr-only" id="pkNormalBarVal">0人</span>
											</div>
										</div>
										<div class="maps-grid-circle matching-circle-bg">
											<span id="pkNormalPerVal">0</span><span>%</span>
										</div>
									</div>
									<div>能力优于销售排名</div>
									<div class="maps-gird-group">
										<div class="progress nomatching-progress">
											<div class="progress-bar" role="progressbar"
												aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"
												id="pkHighBar">
												<span class="sr-only" id="pkHighBarVal">0人</span>
											</div>
										</div>
										<div class="maps-grid-circle nomatching-circle-bg">
											<span id="pkHighPerVal">0</span><span>%</span>
										</div>
									</div>
									<div>能力低于销售排名</div>
									<div class="maps-gird-group">
										<div class="progress nomatching-progress">
											<div class="progress-bar" role="progressbar"
												aria-valuenow="10" aria-valuemin="0" aria-valuemax="100"
												id="pkLowBar">
												<span class="sr-only" id="pkLowBarVal">0人</span>
											</div>
										</div>
										<div class="maps-grid-circle nomatching-circle-bg">
											<span id="pkLowPerVal">0</span><span>%</span>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
        <div id="page-four" class="rightBodyPage page-four">
			<div class="col-sm-12 ct-container-fluid SetUpBody" view="chart-1">
				<!-- <div class="title-group">
					<div class="page-four-title left selected">人员变动情况</div>
					<div class="page-four-title left">人员/销售影响</div>
				</div> -->
				<div class="leftSetUpBtn">
					<div class="leftSetUpBtnDiv leftSetUpLeft leftSetUpBtnSelect">
						<div class="leftSetUpBtnTop"></div>
						<div class="text">人员变动情况</div>
					</div>
					<div class="leftSetUpBtnDiv leftSetUpRight">
						<div class="leftSetUpBtnTop"></div>
						<div class="text">人员/销售影响</div>
					</div>
				</div>
				<div class="bottom-div-four">
					<div class="chart-view-1">
						<div class="col-xs-12">
							<select class="four-select-1" id="personChangeSelect">
								<option selected="selected" value="0">销售额</option>
								<option value="1">销售量</option>
								<option value="2">利润</option>
							</select>
						</div>
						<div class="col-xs-12 bottom-div-first">
							<span id="personChangeUnit">(万元)</span>
						</div>
						<div class="col-xs-12 four-chart" id="personChangeChart"></div>
					</div>
					<div class="chart-view-2">
						<div class="col-sm-8 col-xs-12">
							<div class="col-xs-12 four-left-chart">
								<div class="four-title-group">
									<div class="left">已选人员</div>
								</div>
							</div>
							<div class="col-xs-12">
								<select class="four-select-1" id="organChangeSelect">
									<option selected="selected" value="0">销售额</option>
									<option value="1">销售量</option>
									<option value="2">利润</option>
								</select>
							</div>
							<div class="col-xs-12 bottom-div-first">
								<span id="organChangeUnit">(万元)</span>
							</div>
							<div class="col-xs-12 four-chart" id="personSalesChart"></div>
						</div>
						<div class="col-sm-4 col-xs-12 paddingLeft0">
							<div class="four-search-group">
								<div class="four-search-input left">
									<input type="text" placeholder="请输入员工姓名" class="search-input" id="userName" />
								</div>
								<div class="four-search-btn left" id="userNameBtn">搜索</div>
							</div>
							<div class="four-info">操作说明：点击“加入对比”添加到左图，最多添加5人。</div>
							<div class="four-right-grid">
								<table id="perSalesGrid"></table>
								<table id="perSalesGridPager"></table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
    </div>
</div>

<div class="custom_model" id="team_table_model">
	<div class="card-table-td">
		<div class="bottom-detail">
			<div class="bottom-pie">
 				<div class="standard-rate">
					<div class="standard-rate-num" id="standardRate"></div>
					<div class="standard-rate-text">达标率</div>
				</div>
				<div class="competency-card-chart"></div>
				<!-- <div class="pieChart"></div> -->
			</div>
			<div class="bottom-span">
				<div class="model-teamName"></div>
				<div>排名：<span class="rank-num"></span></div>
				<div>负责人：<span class="team-emp"></span></div>
			</div>
			<div class="clearfix"></div>
			<div class="bottom-bar">
				<div class="bar-span left">销售额/目标额</div>
				<div class="progress teamSales myProgress">
					<div class="progress-bar progress-bar-info" role="progressbar"
						 aria-valuenow="60" aria-valuemin="0" aria-valuemax="100">
					</div>
				</div>
				<div class="bar-text">
					<span class="sales"></span>/
					<span class="target"></span>万元，较上月
					<span class="standard"></span>%
				</div>
			</div>
			<div class="bottom-bar">
				<div class="bar-span left">已回款/回款额</div>
				<div class="progress teamSalesBack myProgress">
					<div class="progress-bar progress-bar-info" role="progressbar"
						 aria-valuenow="60" aria-valuemin="0" aria-valuemax="100">
					</div>
				</div>
				<div class="bar-text">
					<span class="payment"></span>/
					<span class="returnAmount"></span>万元 还剩回款
					<span class="raturnBack"></span>万元
				</div>
			</div>
		</div>
	</div>
</div>
<div id="circle_model" class="custom_model">
	<div class="wrap">
		<canvas class="competency-level-canvas" width="100px" height="100px"></canvas>
	</div>
</div>
<div class="custom_model" id="person_table_model">
	<div class="card-table-td">
		<div class="bottom-detail">
			<div class="bottom-pie">
 				<div class="standard-rate">
					<div class="standard-rate-num" id="personStandardRate"></div>
					<div class="standard-rate-text">达标率</div>
				</div>
				<div class="competency-card-chart"></div>
			</div>
			<div class="bottom-span">
				<div class="left model-userName"></div>
				<div class="sales-track"></div>
				<div class="clearfix"></div>
				<div>团队：<span class="rank-teamName"></span></div>
				<div>排名：<span class="rank-num"></span></div>
			</div>
			<div class="clearfix"></div>
			<div class="bottom-bar">
				<div class="bar-span left">销售额/目标额</div>
				<div class="progress personSales myProgress">
					<div class="progress-bar progress-bar-info" role="progressbar"
						 aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
						 >
					</div>
				</div>
				<div class="bar-text">
					<span class="sales"></span>/
					<span class="target"></span>万元，较上月
					<span class="standard"></span>%
				</div>
			</div>
			<div class="bottom-bar">
				<div class="bar-span left">已回款/回款额</div>
				<div class="progress personSalesBack myProgress">
					<div class="progress-bar progress-bar-info" role="progressbar"
						 aria-valuenow="60" aria-valuemin="0" aria-valuemax="100">
					</div>
				</div>
				<div class="bar-text">
					<span class="payment"></span>/
					<span class="returnAmount"></span>万元 还剩回款
					<span class="raturnBack"></span>万元
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 销售预警-预警设置 弹出框 begin -->
<div class="modal fade topWindow popup-modal" id="salesWarningEditModal"
	tabindex="-1" role="dialog" aria-labelledby="salesWarningEditModalLabel"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-header-text" id="salesWarningEditModalLabel">预警设置</div>
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<div class="tab-content" style="padding-left:0px;">
					<div role="tabpanel" class="tab-pane active">
						<div class="row ct-row">
							<div class="col-xs-6 paddingLeft20" id="salesValTable">
								<div class="warning-title" id="salesNumTitle">销售额</div>
								<div class="warning-block">
									<div class="warning-group">
										<div class="warning-flag yellow left"></div>
										<div class="warning-edit left">黄色预警：较上月下降
											<input type="text" id="salesNumYel1" onkeyup="value=value.replace(/[^\d]/g,'')" />% - 
											<input type="text" id="salesNumYel2" onkeyup="value=value.replace(/[^\d]/g,'')"/>%
										</div>
									</div>
									<div class="warning-group">
										<div class="warning-flag red left"></div>
										<div class="warning-edit left">红色预警：较上月下降
											<input type="text" id="salesNumRed" disabled="disabled" />%以上
										</div>
									</div>
								</div>
							</div>
							<div class="col-xs-6 paddingRight20" id="exceptionOrganTable">
								<div class="warning-title" id="salesOrganTitle">销售额异常组织</div>
								<div class="warning-block">
									<div class="warning-group">
										<div class="warning-flag yellow left"></div>
										<div class="warning-edit left">黄色预警：较上月下降
											<input type="text" id="salesOrganYel1" onkeyup="value=value.replace(/[^\d]/g,'')"/>% - 
											<input type="text" id="salesOrganYel2" onkeyup="value=value.replace(/[^\d]/g,'')"/>%
										</div>
									</div>
									<div class="warning-group">
										<div class="warning-flag red left"></div>
										<div class="warning-edit left">红色预警：较上月下降
											<input type="text" id="salesOrganRed" disabled="disabled"/>%以上
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row ct-row">
							<div class="col-xs-6 paddingLeft20" id="inputOutputExceptionTable">
								<div class="warning-title" id="personFlowTitle">人员流失异常</div>
								<div class="warning-block">
									<div class="warning-group">
										<div class="warning-flag yellow left"></div>
										<div class="warning-edit left">黄色预警：流失率
											<input type="text" id="personFlowYel1" onkeyup="value=value.replace(/[^\d]/g,'')"/>% - 
											<input type="text" id="personFlowYel2" onkeyup="value=value.replace(/[^\d]/g,'')"/>%
										</div>
									</div>
									<div class="warning-group">
										<div class="warning-flag red left"></div>
										<div class="warning-edit left">红色预警：流失率
											<input type="text" id="personFlowRed" disabled="disabled"/>%以上
										</div>
									</div>
								</div>
							</div>
							<div class="col-xs-6 paddingRight20" id="abilityCheckTable">
								<div class="warning-title" id="abilityCheckTitle">业务能力考核</div>
								<div class="warning-block">
									<div class="warning-group">
										<div class="warning-flag yellow left"></div>
										<div class="warning-edit left">黄色预警：通过率
											<input type="text" id="abilityCheckYel1" onkeyup="value=value.replace(/[^\d]/g,'')"/>% - 
											<input type="text" id="abilityCheckYel2" onkeyup="value=value.replace(/[^\d]/g,'')"/>%
										</div>
									</div>
									<div class="warning-group">
										<div class="warning-flag red left"></div>
										<div class="warning-edit left">红色预警：通过率
											<input type="text" id="abilityCheckRed" disabled="disabled"/>%以下
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="btn-groups right">
							<div class="btn-ok left" id="warningOkId">确定</div>
							<div class="btn-remove left" id="warningRemoveId">取消</div>
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
<!-- 销售预警-预警设置 弹出框 end -->
<!-- 销售趋势 弹出框 begin -->
<div class="modal fade topWindow popup-modal" id="personSalesTrendModal"
	tabindex="-1" role="dialog" aria-labelledby="personSalesTrendModalLabel"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-header-text" id="personSalesTrendModalLabel">销售趋势</div>
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<div class="" style="padding-left:0px;">
					<div role="tabpanel" class="tab-pane active">
						<div class="row ct-row">
							<div class="table-body">
								<div class="row position-relative">
									<div class="bottom-div-first" style="margin-left: 38px;"><span>能力轨迹</span></div>
		                            <div class="track-line" id="abilityTrack"></div>
		                        </div>
							</div>
						</div>
						<hr>
						<div class="row ct-row">
							<div class="table-body">
								<div class="row position-relative">
									<div class="bottom-div-first" style="margin-left: 25px;"><span>销售排名轨迹</span></div>
		                            <div class="track-line" id="salesBankingTrack"></div>
		                        </div>
							</div>
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
<!-- 销售趋势 弹出框 end -->
<!-- 团队设置搜索弹出框 start -->
<div class="modal fade popup-modal" id="customTeamModal" tabindex="-1" role="dialog"
	 aria-labelledby="versionModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-header-text" >团队设置：</div>
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<div class="form-horizontal">
					<div class="form-group">
						<label class="col-sm-2 control-label">团队命名：</label>
						<div class="col-sm-10 ct-col1"><input id="teamName" class="input-large" type="text" placeholder="请输入团队名称" /></div>
					</div>
					<div class="form-group">
						<input type="hidden" id="topOrganId" />
                        <input type="hidden" id="topOrganName" />
						<label class="col-sm-2 control-label">组织架构：	</label>
						<div class="col-sm-10 ct-col1"><div class="col-sm-6 ct-col1 organBoard"><ul class="ztree" id="organZtree"></ul><i id="organRemovebtn" class="icon-remove hide"></i></div></div>
					</div>
					<!-- <div class="form-group">
						<label class="col-sm-2 control-label">主序列：	</label>
						<div class="col-sm-10 ct-col1" id="sequenceId"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">子序列：	</label>
						<div class="col-sm-10 ct-col1" id="sequenceSubId"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">职位层级：	</label>
						<div class="col-sm-10 ct-col1" id="abilityId"></div>
					</div> -->
					<div class="form-group">
						<label class="col-sm-2 control-label">绩效：	</label>
						<div class="col-sm-10 ct-col1" id="performanceKey"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">年龄：	</label>
						<div class="col-sm-10 ct-col1" id="ageId"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">性别：	</label>
						<div class="col-sm-10 ct-col1" id="sexId"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">学历：	</label>
						<div class="col-sm-10 ct-col1" id="eduId"></div>
					</div>
					<!-- <div class="form-group" id="continueAddBlock">
						<label class="col-sm-2 control-label"></label>
						<div class="col-sm-10">
							<label class="middle" id="continueAddTeamBtn">
								<input class="ace" type="checkbox" id="continueAddTeam" />
								<span class="lbl"> 继续添加团队</span>
							</label>
						</div>
					</div> -->
					<div class="form-group">
						<label class="col-sm-2 control-label"></label>
						<div class="col-sm-10 ct-col1">
							<button class="btn btn-info" id="customTeamSubmitBtn">确定</button><button class="btn btn-white" data-dismiss="modal" aria-hidden="true">取消</button>
							<label class="form-teamNum">当前团队人数<span id="teamNum">...</span>人</label>
						</div>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<!-- 团队设置搜索弹出框 end -->
<!-- 全屏显示 began -->
<div class="container-fluid ct-col1 sales-maps hide" id="fullMapBody">
	<div class="row ct-row">
		<div class="col-xs-12 ct-col1 fullScreenBodyHead">
			<span class="full-span-title">
				<span class="fullScreenBodyTitle hide" id="fullTeamViewTitle">团队总览</span>
				<span class="fullScreenBodyTitle hide" id="fullTeamPKTitle">团队PK</span>
			</span>
			<span class="close" id="fullCloseBtn">
				<i class="icon-remove"></i>
			</span>
		</div>
	</div>

	<div class="row ct-row">
        <div class="col-xs-1 ct-col1 search-block">
            <button class="btn btn-info" id="searchEmpBtn">查找</button>
            <div class="search-main-block" id="searchEmpBlock">
				<span class="close" id="searchCloseBtn">
					<i class="icon-remove"></i>
				</span>
                <label for="selectEmp">
                    <select id="selectEmp" class="js-example-basic-single" style="width: 100%"></select>
                </label>
                <span class="search-instructions">使用上面的搜索框在地图中查找人员</span>
                <div class="search-content" id="searchContent"></div>
            </div>
        </div>
        <div class="col-xs-11" id="fullMapBlock">
            <div class="col-xs-12">&nbsp;</div>
			<div id="fullTeamView" class="u-map hide"></div>
			<div id="fullTeamPK" class="u-map hide"></div>
            <div class="col-xs-12">&nbsp;</div>
        </div>
    </div>

</div>
<!-- 全屏显示 end -->

<!--遮罩层-->
<div id="shade" class="shade"></div>
<!--遮罩层-->
<script src="${jsRoot}lib/echarts/echarts.js"></script>
<script src="${jsRoot}biz/productivity/salesBoard.js"></script>
</body>
</html>