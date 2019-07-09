<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<html lang="en">
<head>
	<!-- jsp文件头和头部 -->
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>审核地图</title>
	<link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"  />
	<link rel="stylesheet" href="${ctx}/assets/css/select2/select2.min.css"/>
	<link rel="stylesheet" href="${ctx}/assets/css/talent-map2.css" />
	<link rel="stylesheet" href="${ctx}/assets/css/biz/competency/talentMapsAuditing.css"/>
</head>
<body>
<input type="hidden" id="topId" value="${topId}"/>
<input type="hidden" id="performance" value="${performance}"/>
<input type="hidden" id="ability" value="${ability}"/>
<input type="hidden" id="time" value="${time}"/>
<input type="hidden" id="organName" value="${organName}"/>
<div class="talentmaps-auditing" id="talentMapsAuditing">
	<div class="rightBody">
		<div class="rightTitle">
			<ul class="breadcrumb">
				<li>
					<i class="icon-home home-icon"></i>
					<a href="javascript:void(0)" id="toHomeBtn">人才地图首页</a>
				</li>
				<li class="active">审核地图<span class="subtitle-text">(${time}${organName}人才地图)</span></li>
			</ul>
		</div>
		<div id="page-one" class="rightBodyPage">
			<div class="row ct-row">
				<div class="bottom-div-step">
					<div class="step-container">
				        <div class="step-current left"><div class="step-blue-1"></div></div>
				        <div class="step-normal left"><div class="step-white-2"></div></div>
				        <div class="step-last left"><div class="step-white-3"></div></div>
				    </div>
				</div>
				<div class="clearfix"></div>
				<div class="bottom-div-prompt">
					<div class="u-img"></div>
				</div>
				<div class="bottom-div-body">
					<div class="col-sm-6 col-boder">
						<div class="col-md-12 col-xs-12">
							<div class="fullScreen">
								<i class="icon-fullscreen2" id="fullScreen"></i>
							</div>
						</div>
                   		<div class="col-xs-12 ct-col1">
							<div id="map" class="u-map"></div>
						</div>
                   	</div>
                   	<div class="col-sm-15 col-boder margin-left20">
                   		<div class="rightSetUpBtn"></div>
                   		<div class="clearfix"></div>
                        <div class="table-grid">
                        	<div class="margin-left10">
                        		<input type="text" placeholder="输入员工姓名进行搜索" class="searchText" id="keyName">
                        		<div class="searchBtn" id="searchBtn">搜索</div>
                        	</div>
                        	<div class="clearfix"></div>
                        	<div class="tab-content">
	                            <table id="talentPerformanceAbilityGrid"></table>
	                            <div id="talentPerformanceAbilityPager"></div>
							</div>
                    		<div class="btn-top btn-one">
                    			<div class="next-step-btn" id="next-step">
                    				<span>下一步：确认调整</span>
                    				<span id="hasAuditingNum">(<span id="auditingNum">0</span>人)</span>
                    			</div>
                    		</div>
	                    </div>
                   	</div>
				</div>
			</div>
		</div>
		
		<div id="page-two" class="rightBodyPage" style="display: none;">
			<div class="row ct-row">
				<div class="bottom-div-step">
					<div class="step-container">
				        <div class="step-first left"><div class="step-achieve"></div></div>
				        <div class="step-current left"><div class="step-blue-2"></div></div>
				        <div class="step-last left"><div class="step-white-3"></div></div>
				    </div>
				</div>
				<div class="bottom-div-prompt">
					<div class="u-img2"></div>
				</div>
				<div class="bottom-div-body">
					<div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">填写调整说明</div>
                    </div>
					<div class="content-tab">
               			<table class="table">
							<thead>
								<tr class="table-thead-tr next-tr">
									<th width="350">姓名</th>
									<th width="200">原位置</th>
									<th width="100">&nbsp;</th>
									<th width="200">调整后位置</th>
									<th width="450">调整说明</th>
								</tr>
							</thead>
							<tbody class="next-tbody" id="nextPerformanceAbility"></tbody>
						</table>
					</div>
					
					<div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">选择审核人</div>
                    </div>
					<div class="content-body">
						<div class="select-text"><select id="txtKey"></select></div>
						<div class="clearfix"></div>
						<div class="adjustment-buttom">
							<div>
								<span class="span-bold">共调整：</span>
								<span class="span-text" id="talents"></span>
							</div>
							<div>
								<span class="span-bold">审核人：</span>
								<span class="span-text" id="cp-auditor"></span>
							</div>
							<a href="javascript:void(0)" id="preview"><div class="preview-img"></div>&nbsp;&nbsp;预览地图</a>
						</div>
						<div class="commit-btn">
                   			<div class="commit-step-btn left" id="auditing">发送审核</div>
                   			<div class="commit-step-btn left" id="auditing_ing">审核中...</div>
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
                <div class="modal-header-text" id="mapPreviewModalLabel">地图预览（${time}${organName}人才地图）</div>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">
                <div class="tab-content">
                    <div id="tabpanel" role="tabpanel" class="col-xs-12 tab-pane active">
                    	<div class="checkbox-apan cursor-pointer" id="showSpan">
                    		<input class="checkbox-style cursor-pointer" type="checkbox" id="showAdjustment" value="showAdjustment">
                    		<span>只显示调整人员</span>
                    	</div>
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
                <div>点击确定进入“<span id="nextOrganName"></span>人才地图”审核，取消返回主页</div>
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

<script src="${ctx}/assets/js/biz/competency/talentMapsAuditing.js"></script>
</body>
</html>