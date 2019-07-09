<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<html lang="en">
<head>
	<!-- jsp文件头和头部 -->
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>发布地图</title>
	<link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"  />
	<link rel="stylesheet" href="${ctx}/assets/css/talent-map2.css" />
	<link rel="stylesheet" href="${ctx}/assets/css/biz/competency/talentMapsPublish.css"/>
</head>
<body>
<input type="hidden" id="topId" value="${topId}"/>
<input type="hidden" id="adjustmentId" value="${adjustmentId}"/>
<input type="hidden" id="performance" value="${performance}"/>
<input type="hidden" id="ability" value="${ability}"/>
<input type="hidden" id="time" value="${time}"/>
<div class="talentmaps-publish" id="talentMapsPublish">
	<div class="rightBody">
		<div class="row ct-row">
			<div class="col-xs-12 ct-col1 rightSetUpTitle">
				<ul class="breadcrumb">
					<li>
						<i class="icon-home home-icon"></i>
						<a href="javascript:void(0)" id="toHomeBtn">人才地图首页</a>
					</li>
					<li class="active">发布地图<span class="subtitle-text">(${time}${organName}人才地图)</span></li>
				</ul>
			</div>
		</div>
		<div id="page-one" class="rightBodyPage">
			<div class="row ct-row">
				<div class="col-md-12 ct-line-col SetUpBody">
					<div class="bottom-div-step">
						<ul class="step-container">
					        <li class="step-current">第一步、调整地图位置
					            <span class="arrow arrow-current">
					                <span class="arrow-pre"></span>
					                <span class="arrow-next"></span>
					            </span>
					        </li>
					        <li class="step-next">第二步、填写调整说明
					            <span class="arrow arrow-step-next">
					                <span class="arrow-pre"></span>
					                <span class="arrow-next"></span>
					            </span>
					        </li>
					        <li class="step-last">完成</li>
					    </ul>
					</div>
					<div class="clearfix"></div>
					<div class="bottom-div-prompt">
						<div class="u-img"></div>你可在右边列表查看${adjustment }调整人员的地图位置并自行调整人员的地图位置
					</div>
					<div class="bottom-div">
						<div class="col-sm-6">
							<div class="col-md-12 col-xs-12">
								<div class="text-div">${adjustment }调整人员</div>
								<div class="fullScreen">
									<i class="icon-fullscreen" id="fullScreen"></i>
								</div>
							</div>
                    		<div class="col-md-12 col-xs-12">
								<div id="map" class="u-map u-map-hideText"></div>
							</div>
                    	</div>
                    	<div class="col-sm-6">
                    		<div class="nav-div">
                    			<div class="nav-div-text nav-text-selected">${adjustment }调整人员&nbsp;<span id="records"></span></div>
                    			<div class="nav-div-text" id="allEmp">全部人员</div>
                    		</div>
                    		<div class="clearfix"></div>
                    		<div class="rightSetUpBtn mar-left display-none"></div>
	                        <div class="clearfix"></div>
		                    <div class="table-grid" id="talentPublish1">
	                        	<div class="mar-left">
	                        		<input type="text" id="keyName1" placeholder="输入员工姓名进行搜索" class="searchText">
	                        		<div class="searchBtn" id="searchBtn1">搜索</div>
	                        	</div>
		                        <div class="clearfix"></div>
	                        	<div class="tab-content">
		                            <table id="talentPublishGrid"></table>
		                            <div id="talentPublishPager"></div>
								</div>
		                    </div>
		                    <div class="clearfix"></div>
		                    <div class="table-grid display-none" id="talentPublish2">
	                        	<div class="mar-left">
	                        		<input type="text" id="keyName2" placeholder="输入员工姓名进行搜索" class="searchText">
	                        		<div class="searchBtn" id="searchBtn2">搜索</div>
	                        	</div>
		                        <div class="clearfix"></div>
	                        	<div class="tab-content">
		                            <table id="talentPerformanceAbilityGrid"></table>
		                            <div id="talentPerformanceAbilityPager"></div>
								</div>
		                    </div>
		                    <div class="clearfix"></div>
		                    <div class="mar-left">
                    			<div class="next-step-btn" id="next-step">
                    				<span>下一步:确认调整</span>
	                    			<span id="hasPublishNum">(<span id="publishNum">0</span>人)</span>
                    			</div>
                    		</div>
                    	</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="page-two" class="rightBodyPage display-none">
			<div class="row ct-row">
				<div class="col-md-12 ct-line-col SetUpBody">
					<div class="bottom-div-step">
						<ul class="step-container">
					        <li class="step-past">第一步、调整地图位置
					            <span class="arrow arrow-past">
					                <span class="arrow-pre"></span>
					                <span class="arrow-next"></span>
					            </span>
					        </li>
					        <li class="step-current">第二步、填写调整说明
					            <span class="arrow arrow-current">
					                <span class="arrow-pre"></span>
					                <span class="arrow-next"></span>
					            </span>
					        </li>
					        <li class="step-last">完成</li>
					    </ul>
					</div>
					<div class="clearfix"></div>
					<div class="bottom-div-prompt">
						<div class="u-img"></div>请填写调整说明并发布地图
					</div>
					<div class="text-blod">
						填写调整说明
					</div>
					<hr class="hr-line">
					<div>${adjustment }调整人员</div>
					<div class="content-tab">
               			<table class="table">
							<thead>
								<tr class="table-thead-tr">
									<th width="300">姓名</th>
									<th width="200">原位置</th>
									<th width="200">调整后位置</th>
									<th width="415">调整说明</th>
								</tr>
							</thead>
							<tbody id="talentNextGrid"></tbody>
						</table>
					</div>
					<div>本次调整人员</div>
					<div class="content-tab">
               			<table class="table">
							<tbody id="thisTimeTalentGrid"></tbody>
						</table>
					</div>
					<div class="content-body">
						<div class="clearfix"></div>
						<div class="publish-div">
							<div>
								<span class="span-bold">共调整：</span>
								<span class="span-text" id="talents"></span>
							</div>
							<a href="javascript:void(0)" id="preview">预览地图</a>
						</div>
						<div style="margin-top: 20px;">
                   			<div class="next-step-btn left" id="publish">发布地图</div>
                   			<a href="javascript:void(0)" id="previous-step" class="previous-step left">上一步</a>
                   		</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="container-fluid ct-col1 talent-maps hide" id="fullMapBody">
	<div class="row ct-row">
		<div class="col-xs-12 ct-col1 fullScreenBodyHead">
			<span class="fullScreenBodyTitle">${time}${organName}人才地图</span>
			<span class="close" id="fullCloseBtn">
				<i class="icon-remove"></i>
			</span>
		</div>
	</div>
	<div class="row ct-row">
		<div class="col-xs-12">&nbsp;</div>
		<div class="col-md-12 col-xs-12">
			<div id="fullMap" class="u-map"></div>
		</div>
		<div class="col-xs-12">&nbsp;</div>
	</div>
</div>

<!-- 预览地图 弹出框 begin -->
<div class="modal fade topWindow popup-modal" id="mapPreviewModal" tabindex="-1" role="dialog"
     aria-labelledby="mapPreviewModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-text" id="mapPreviewModalLabel">地图预览</div>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">
                <div class="tab-content">
                    <div id="tabpanel" role="tabpanel" class="col-xs-12 tab-pane active">
                   		<div class="col-xs-12 ct-col1">
							<div id="mapPreview" class="u-map"></div>
						</div>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 预览地图 弹出框 end -->

<div class="modal fade popup-modal" id="riskConfigModal" tabindex="-1" role="dialog"
         aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <i class="icon-ok icon-3x green"></i><span>提交成功！</span>
                <div>点击确定进入“<span id="nextOrganName"></span>人才地图”发布，取消返回主页</div>
            </div>
            <div class="modal-footer">
                <div class="modal-btn success-btn" id="confirmOk">确定</div>
                <div class="modal-btn default-btn" data-dismiss="modal" id="cancel">取消</div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="modal fade popup-modal" id="successModal" tabindex="-1" role="dialog"
         aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <i class="icon-ok icon-3x green"></i><span>提交成功！</span>
            </div>
            <div class="modal-footer">
                <div class="modal-btn success-btn" id="successConfirm">确定</div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!--遮罩层-->
<div id="shade" class="shade"></div>
<!--遮罩层-->

<script src="${ctx}/assets/js/require.js"></script>
<script src="${ctx}/assets/js/biz/competency/talentMapsPublish.js"></script>
</body>
</html>