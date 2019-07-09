<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<!-- jsp文件头和头部 -->
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>团队能力/绩效趋势图</title>
	<link rel="stylesheet" href="${ctx}/assets/css/talent-map2.css" />
	<link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"  />
	<link rel="stylesheet" href="${ctx}/assets/css/select2/select2.min.css"/>
	<link rel="stylesheet" href="${ctx}/assets/css/biz/competency/talentMaps.css"/>
	<link rel="stylesheet" href="${ctx}/assets/css/biz/competency/talentMapsTeamCP.css"/>
	<link rel="stylesheet" href="${ctx}/assets/css/jBootsrapPage.css"/>
</head>
<body>
<input id="minTime" value="${map.minTime}" type="hidden" />
<input id="maxTime" value="${map.maxTime}" type="hidden" />
<input id="times" value="${map.times}" type="hidden" />
<div class="teamCPChart talent-maps" id="teamCPChartId">
	<div class="rightBody">
		<div class="row ct-row row-title-div">
			<div class="SetUpBody">
				<div class="bottom-div">
					<div class="bottom-div-first">
						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="javascript:void(0)" id="topView">人才地图首页</a>
							</li>
							<li class="active">团队能力/绩效趋势图</li>
							<li class="active"></li>
						</ul>
						<span class="span-padding">
							<select id="selectChartId">
								<option selected="selected">团队能力趋势图</option>
         						<option>团队绩效趋势图</option>
							</select>
						</span>
					</div>
					<div class="padding-title left">
						<ul id="organTree"></ul>
					</div>
					<div class="left time-select bord-style" id="selectTimeId"></div>
					<div class="search-box" id="searchBox">
                        <div class="more-search"></div>
					</div>
					<div class="bottom-div-second" id="bottomDivSecond">
						<div class="col-xs-12">
							<span class="font-style-parent">
								<span class="font-style-child" id="changeOrganId">广州中心</span>
								<span class="font-style-child" id="changeChartId">团队能力趋势图</span>
							</span>
							<span class="span-padding">
								<select class="select-view" id="changeViewTypeId">
									<option selected="selected">地图显示</option>
									<option>列表显示</option>
								</select>
							</span>
							<span class="span-padding cursor-pointer" id="showDetailSpan">
								<input class="checkbox-style cursor-pointer" type="checkbox" id="showDetail" value="showDetail">显示明细
							</span>
							<span class="span-padding cursor-pointer" id="hiddenGridSpan">
								<input class="checkbox-style cursor-pointer" type="checkbox" id="hiddenGridBorder" value="hiddenGrid">隐藏网格
							</span>
							<span class="span-padding cursor-pointer" id="showFullViewId">
								<i class="icon-fullscreen"></i>
								全屏显示
							</span>
							<div class="nav-search" id="nav-search">
				                <select id="navSearchEmp" class="js-example-basic-single" style="width: 100%"></select>
				                <div class="nav-search-block" id="navSearchBlock"></div>
				            </div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row ct-row content-div map-view">
			<div class="col-xs-12 SetUpBody">
				<div class="bottom-div-map">
					<div class="chart-view" id="mapChartId"></div>
				</div>
			</div>
		</div>
		
		<div class="row ct-row content-div grid-view closed">
			<div class="col-xs-12 SetUpBody">
				<div class="bottom-div-grid" id="gridViewId"></div>
			</div>
		</div>
		
		<div class="row ct-row content-div detial-view closed">
			<div class="col-xs-12 SetUpBody">
				<div class="bottom-div-detail">
					<div class="chart-view u-map" id="detailChartId"></div>
				</div>
			</div>
		</div>
			
	</div>
</div>
<!-- 全屏显示 began -->
<div class="container-fluid ct-col1 talent-maps hide" id="fullMapBody">
	<div class="row ct-row">
		<div class="col-xs-12 ct-col1 fullScreenBodyHead">
			<span class="full-span-title">
				<span class="fullScreenBodyTitle" id="fullOrganText">广州中心</span>
				<span class="fullScreenBodyTitle" id="fullTeamText">团队能力趋势图</span>
			</span>
			<span class="close" id="fullCloseBtn">
				<i class="icon-remove"></i>
			</span>
		</div>
	</div>

	<div class="row ct-row">
		<div class="col-xs-12">&nbsp;</div>
		<div class="col-md-12 col-xs-12">
			<div id="fullMap" class="u-map hide"></div>
			<div id="fullGrid" class="u-map hide"></div>
		</div>
		<div class="col-xs-12">&nbsp;</div>
	</div>
</div>
<!-- 全屏显示 end -->
<!-- 明细全屏显示 began -->
<div class="container-fluid ct-col1 talent-maps hide" id="fullMapDetailBody">
    <div class="row ct-row">
        <div class="col-xs-12 ct-col1 fullScreenBodyHead">
            <span class="full-span-title">
				<span class="fullScreenBodyTitle" id="fullDetailOrganText">广州中心</span>
				<span class="fullScreenBodyTitle" id="fullDetailTeamText">团队能力趋势图</span>
			</span>
            <span class="close" id="fullDetailCloseBtn">
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
            <div id="fullDetail" class="u-map"></div>
            <div class="col-xs-12">&nbsp;</div>
        </div>
        <div id="shade" class="shade"></div>
    </div>
</div>
<!-- 明细全屏显示 end -->
<!-- 人员明细弹窗 began -->
<div class="modal fade topWindow popup-modal" id="personDetailModal"
	tabindex="-1" role="dialog" aria-labelledby="personDetailModalLabel"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-header-text" id="personDetailModalLabel">人员明细1</div>
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body detail-body">
				<div class="col-xs-12 ct-col1" id="maps-person-table">
                    <div class="col-xs-12 cardTabel ct-col1" id="person_tabel"></div>
                </div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
</div>
<!-- 人员明细弹窗 end -->
<!-- 人才地图小弹窗 start -->
<div class="modal fade popup-modal" id="talentMapsModal" tabindex="-1" role="dialog"
     aria-labelledby="removeTeamModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-text"></div>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body" style="margin-left: -18px;">
                <div class="col-xs-12 ct-col1" id="maps-emp-table">
                    <div class="col-xs-12 ct-col1 cardTabel u-map" id="emp_tabel"></div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 人才地图小弹窗 end -->
<!--遮罩层-->
<!-- <div id="shade" class="shade"></div> -->
<!--遮罩层-->
<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/js/biz/competency/talentMapsTeamCP.js"></script>
</body>
</html>
