<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<!-- jsp文件头和头部 -->
<%@include file="/WEB-INF/views/include/top.jsp"%>
<title>组织架构(编制和空缺)</title>
<link rel="stylesheet"
	href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet"
	href="${ctx}/assets/css/biz/other/orgChart.css" />
</head>
<body>

	<div class="page-content" id="main-container">
		<div class="main-container-inner">
			<div class="row column">
				<div class="tab-pane col-xs-12">
					<div class="widget-box transparent f-white-bg">
						<div class="widget-header widget-header-flat">
							<h4 class="bolder">组织架构(编制和空缺)</h4>
						</div>

						<div class="widget-body">
							<div id="organArea" class="organArea">
								<!-- 空白区域，作用是占一块区域，让拓扑图收缩时没有闪动的效果 -->
								<div id="placeHolder"></div>
								<!-- 组织机构职数拓扑图 -->
								<div class="clearfix" id="organDiv"></div>
							</div>
							<div id="showUnResultMsg" class="hidden span24">很抱歉，没有找到相关的记录</div>

							<!-- 拓扑图弹出框 -->
							<div id="showDetail" class=""></div>
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="${ctx}/assets/js/biz/other/orgChart.js"></script>
</body>
</html>
