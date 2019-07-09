<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<html>
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>人才剖像</title>
	<link rel="stylesheet" href="${ctx}/assets/css/biz/employee/talentProfile.css"/>
</head>
<body>
	<input type="hidden" id="keyName" value="${keyName}">
	<div class="page-content">
		<div class="main-container-inner">
			<div class="row column" id="firstRows">
				<div class="profile-header">
					<div class="col-md-3 col-sm-2 col-xs-0"></div>
					<div class="col-md-6 col-sm-8 col-xs-12">
						<div class="row column">
							<div class="col-sm-10 col-xs-12">
								<span class="profile-title">人才剖像</span>
							</div>
						</div>
						<div class="row column">
							<div class="col-sm-10 col-xs-12">
								<div class="input-group">
									<input type="text" class="form-control" id="firstSearchTxt" placeholder="请输入员工ID/姓名">
									<span class="input-group-btn">
										<button class="btn btn-search" id="firstSearchBtn" type="button"><i class="icon-search"></i>搜索</button>
									</span>
								</div><!-- /input-group -->
							</div>
						</div>
						<div class="row column">
							<div class="col-sm-10 col-xs-12">
								<span class="hint">
									* 姓名支持模糊查询
								</span>
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-2 col-xs-0"></div>
				</div>
			</div>
			<div class="row column hide" id="lastRows">
				<div class="col-xs-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">
								人才明细
							</h3>
						</div>
						<div class="panel-body">
							<div class="row column">
								<div class="col-md-4 col-sm-8 col-xs-12">
									<div class="input-group">
										<input type="text" class="form-control" id="lastSearchTxt" placeholder="请输入员工ID/姓名">
										<span class="input-group-btn">
											<button class="btn btn-light btn-search" id="lastSearchBtn" type="button"><i class="icon-search"></i>搜索</button>
										</span>
									</div>
								</div>
								<div class="col-md-4 col-sm-4 col-xs-12">
									<span class="hint">
										* 姓名支持模糊查询
									</span>
								</div>
							</div>
							<div class="row column">
								<div class="col-xs-12" id="profileGridPanel">
									<table id="profileGridTable" class="borderless"></table>
									<div id="profileGridPager"></div>
								</div>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" js-main="biz/employee/talentProfile" src="${jsRoot}require.js"></script>
</body>
</html>