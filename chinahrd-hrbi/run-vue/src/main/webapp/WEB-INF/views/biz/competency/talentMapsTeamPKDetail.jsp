<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>人才地图-团队PK</title>
    <link rel="stylesheet" href="${ctx}/assets/css/talent-map2.css">
    <link rel="stylesheet" href="${ctx}/assets/css/select2/select2.min.css">
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css">
    <link rel="stylesheet" href="${ctx}/assets/css/biz/competency/talentMaps.css">
    <link rel="stylesheet" href="${ctx}/assets/css/biz/competency/talentMapsTeamPKDetail.css">
    <link rel="stylesheet" href="${ctx}/assets/css/jBootsrapPage.css">
</head>
<body>

<div class="container-fluid ct-container-fluid talent-maps" id="talentMapBody">
    <div class="row ct-row">
        <div class="col-xs-12 ct-col1 rightSetUpTitle">
            <input type="hidden" id="cycleDate" value="${cycleDate}">
            <ul class="breadcrumb">
                <li>
                    <i class="icon-home home-icon"></i>
                    <a href="javascript:void(0)" id="toHomeBtn">人才地图首页</a>
                </li>
                <li class="active">团队PK</li>
                <li class="active">
                    <select id="dateSelect">
                        <c:forEach items="${kvItemDtos}" var="dto">
                            <option value="${dto.k}">${dto.v}</option>
                        </c:forEach>
                    </select>
                    <div class="team-block" id="teamBlock"></div>
                </li>
            </ul><!-- .breadcrumb -->
        </div>
    </div>

    <div class="row ct-row">
        <div class="col-xs-12 ct-col1 rightBodyHead">
            <span class="rightBodyHeadTitle"><span class="teamMapsTitle">2016年上半年</span><span>团队PK地图</span></span>
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
            <div class="nav-search">
                <select id="navSearchEmp" style="width: 100%"></select>
                <div class="nav-search-block" id="navSearchBlock"></div>
            </div>
        </div>
    </div>
    <div class="row ct-row" id="mapsBlock">
        <div class="col-xs-12">&nbsp;</div>
        <div class="col-md-12 col-xs-12">
            <div id="map" class="u-map"></div>
            <div id="grid-group" class="hide"></div>
        </div>
        <div class="col-xs-12">&nbsp;</div>
    </div>

    <div class="row ct-row content-div grid-view hide" id="gridBlock">
        <div class="col-xs-12 SetUpBody">
            <div class="bottom-div-grid">
                <div class="view-grid-title">
                    <ul id="gridViewTitleId"></ul>
                </div>
                <div class="view-grid-content" id="gridViewContentId"></div>
                <div class="view-grid-pager">
                    <div class="left pager-total">
                        共<span class="pager-total-num" id="pagerCount">0</span>条
                    </div>
                    <div class="right pager-operate">
                        <ul class="jPagination" id="pagerId"></ul>
                    </div>
                </div>
            </div>
        </div>
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
            <span class="fullScreenBodyTitle"><span class="teamMapsTitle">2016年上半年</span>团队PK地图</span>
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
                    <select id="selectEmp" style="width: 100%">
                        <option value="-1">请输入名称</option>
                    </select>
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

<!-- 搜索弹出框 start -->
<div class="modal fade popup-modal" id="customTeamModal" tabindex="-1" role="dialog"
     aria-labelledby="customTeamModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-text">团队设置：</div>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">团队命名：</label>
                        <div class="col-sm-10 ct-col1">
                            <input id="teamName" class="input-large" type="text" placeholder="请输入团队名称"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <input type="hidden" id="topOrganId" value="${topOrgan.organizationId}">
                        <input type="hidden" id="topOrganName" value="${topOrgan.organizationName}">
                        <label class="col-sm-2 control-label">组织架构： </label>
                        <div class="col-sm-10 ct-col1">
                            <div class="col-sm-4 ct-col1">
                                <ul class="ztree" id="organZtree"></ul>
                                <i id="organRemovebtn" class="icon-remove hide"></i></div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">主序列： </label>
                        <div class="col-sm-10 ct-col1" id="sequenceId"></div>
                    </div>
                    <div class="form-group hide">
                        <label class="col-sm-2 control-label">子序列： </label>
                        <div class="col-sm-10 ct-col1" id="sequenceSubId"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">职位层级： </label>
                        <div class="col-sm-10 ct-col1" id="abilityId"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">绩效： </label>
                        <div class="col-sm-10 ct-col1" id="performanceKey"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">年龄： </label>
                        <div class="col-sm-10 ct-col1" id="ageId"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">性别： </label>
                        <div class="col-sm-10 ct-col1" id="sexId"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">学历： </label>
                        <div class="col-sm-10 ct-col1" id="eduId"></div>
                    </div>
                    <div class="form-group" id="teamAddBlock">
                        <label class="col-sm-2 control-label"></label>
                        <div class="col-sm-10">
                            <label class="middle" id="teamAddBtn">
                                <input class="ace" type="checkbox" id="addTeamInput"/>
                                <span class="lbl">
									保存为默认团队
                                    <i class="icon-question-sign bigger-120" data-toggle="tooltip"
                                       title="勾选后下次将根据此次所选团队直接PK"></i>
								</span>
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label"></label>
                        <div class="col-sm-10 ct-col1">
                            <button class="btn btn-info" id="teamSubmitBtn">确定</button>
                            <button class="btn btn-white" data-dismiss="modal" aria-hidden="true">取消</button>
                            <label class="form-teamNum">当前团队人数<span id="teamNum">...</span>人</label>
                        </div>
                    </div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 搜索弹出框 end -->

<!-- 删除确认弹出框 start -->
<div class="modal fade popup-modal" id="removeTeamModal" tabindex="-1" role="dialog"
     aria-labelledby="removeTeamModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-text">确定删除吗？</div>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label class="middle" id="delRemoteBlock">
                        <input class="ace" type="checkbox" id="delRemoteInput" checked/>
                        <span class="lbl">&nbsp;同时移除该团队保存数据</span>
                    </label>
                </div>
            </div>
            <div class="modal-footer"> 
                <div class="modal-btn success-btn">确定</div>
                 
                <div class="modal-btn default-btn" data-dismiss="modal">取消</div>
                 
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 删除确认弹出框 end -->

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
<script src="${ctx}/assets/js/biz/competency/talentMapsTeamPKDetail.js"></script>
</body>
</html>
