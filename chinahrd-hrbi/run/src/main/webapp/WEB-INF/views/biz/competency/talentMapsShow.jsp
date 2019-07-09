<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>人才地图展示</title>
    <link rel="stylesheet" href="${ctx}/assets/css/talent-map2.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/select2/select2.min.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/competency/talentMaps.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/competency/talentMapsShow.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/jBootsrapPage.css"/>
</head>
<body>

<div class="container-fluid ct-container-fluid talent-maps" id="talentMapBody">
    <div class="row ct-row">
        <input type="hidden" id="reqOrganId" value="${reqOrganId}">
        <input type="hidden" id="reqOrganName" value="${reqOrganName}">
        <input type="hidden" id="cycleDate" value="${cycleDate}">
        <div class="col-xs-12 ct-col1 rightSetUpTitle">
            <ul class="breadcrumb">
                <li>
                    <i class="icon-home home-icon"></i>
                    <a href="javascript:void(0)" id="toHomeBtn">人才地图首页</a>
                </li>
                <li class="active">人才地图</li>
                <li class="active">
                    <select id="dateSelect">
                        <c:forEach items="${kvItemDtos}" var="dto">
                            <option value="${dto.k}">${dto.v}</option>
                        </c:forEach>
                    </select>
                    <div class="pull-right">
                        <ul class="ztree" id="organZtree"></ul>
                    </div>
                </li>
            </ul><!-- .breadcrumb -->
        </div>
    </div>

    <div class="row ct-row">
        <div class="col-xs-12">
            <div class="search-box" id="searchBox">
                <div class="more-search"></div>
            </div>
        </div>
        <div class="col-xs-12 ct-col1 rightBodyHead">
            <span class="rightBodyHeadTitle"><span class="mapsDateTitle">2016年上半年</span><span
                    class="mapsOrganTitle"></span><span>人才地图</span></span>
            <div class="rightBodyHeadSelect">
                <select id="mapsTypeSelected">
                    <option value="0">地图显示</option>
                    <option value="1">列表显示</option>
                </select>
            </div>
            <div class="help-inline rightBodyHeadCheckbox">
                <label class="middle">
                    <input class="ace" type="checkbox" id="toggleGroupMode"/>
                    <span class="lbl"> 显示总数</span>
                </label>
            </div>
            <div class="help-inline rightBodyHeadCheckbox">
                <label class="middle">
                    <input class="ace" type="checkbox" id="toggleGrids"/>
                    <span class="lbl"> 隐藏网格</span>
                </label>
            </div>
            <div class="help-inline rightBodyHeadCheckbox">
                <label class="middle" id="fullScreen">
                    <i class="icon-fullscreen"></i>
                    <span class="lbl"> 全屏显示</span>
                </label>
            </div>
            <div class="nav-search" id="nav-search">
                <select id="navSearchEmp" style="width: 100%"></select>
                <div class="nav-search-block" id="navSearchBlock"></div>
            </div>
        </div>
    </div>
    <div class="row ct-row">
        <div class="col-xs-12">&nbsp;</div>
        <div class="col-md-12 col-xs-12">
            <div id="map" class="u-map"></div>
            <div id="grid" class="grid-view hide"></div>
        </div>
        <div class="col-xs-12">&nbsp;</div>
    </div>
    <div class="map-axis-Block" id="mapAxisBlock">
        <div class="map-axis-hide">团队PK地图分布统计</div>
        <div class="map-axis-show">
            <div class="index-common-title bottom-title">
                <div class="index-common-title-left bottom-left"></div>
                <div class="index-common-title-text bottom-text">团队PK地图分布统计</div>
                <div class="rightSetUpBtn"><i class="icon-remove"></i></div>
            </div>
            <div class="map-axis-main"></div>
        </div>
    </div>
</div>

<div class="container-fluid ct-col1 talent-maps hide" id="fullMapBody">
    <div class="row ct-row">
        <div class="col-xs-12 ct-col1 fullScreenBodyHead">
            <span class="fullScreenBodyTitle"><span class="mapsDateTitle">2016年上半年</span><span
                    class="mapsOrganTitle"></span><span>人才地图</span></span>
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
                    <select id="selectEmp" style="width: 100%"></select>
                </label>
                <span class="search-instructions">使用上面的搜索框在地图中查找人员</span>
                <div class="search-content" id="searchContent"></div>
            </div>
        </div>
        <div class="col-xs-11" id="fullMapBlock">
            <div class="col-xs-12">&nbsp;</div>
            <div id="fullMap" class="u-map"></div>
            <div class="col-xs-12">&nbsp;</div>
        </div>
        <div id="shade" class="shade"></div>
    </div>
</div>

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

<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/js/biz/competency/talentMapsShow.js"></script>
</body>
</html>
