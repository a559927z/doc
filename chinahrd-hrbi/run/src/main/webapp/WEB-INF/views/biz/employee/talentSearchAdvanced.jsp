<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<html>
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>人才高级搜索</title>
	<link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
	<link rel="stylesheet" href="${ctx}/assets/css/biz/employee/talentSearchAdvanced.css"/>
</head>
<body data-resizeeven="1">
<div id="tsa">
	<div class="body">
		<div class="bodyc">
			<div class="bodytitle clearfix">
				<div class="row column clearfix" id="firstRows">
					<div class="col-xs-5 pull-left">
						<span class="search-title-left">高级搜索</span>
						<span class="search-title-left-return"><a id="searchReturn">返回</a></span>
					</div>
					<div class="col-xs-7 pull-right title-right">
						<span class="search-title-right-clear" id="clearCondition">清空搜索条件</span>
						<span class="search-title-right" id="firstSearchBtn">搜索</span>
					</div>
				</div>
				<div class="row column hide" id="lastRows">
					<div class="col-xs-12">
						<div>
							<div class="row column">
								<div class="col-xs-5 title-left">
									<span class="search-title-left">高级搜索</span>
								</div>
								<div class="col-xs-7 title-right"> 
									<a id="resultExport"><span class="search-title-right">导出结果</span></a>
									<span class="search-title-right" id="editCondition">编辑条件</span>
								</div>
							</div>
							
						</div>
					</div>
				</div>
			</div>
			<div class="bodycontent clearfix">
				<div id="contentRows" class="hide">
					<div class="panel-heading">
						<h3 class="panel-title">
							搜索结果
						</h3>
					</div>
					<div class="panel-body">
						<div class="row column">
							<div class="col-xs-12" id="searchGridPanel">
								<table id="searchGridTable" class="borderless"></table>
								<div id="searchGridPager"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="row column clearfix accordion-style1 panel-group"  id="conditionRows">
					<div class="panel panel-default">
						<div class="panel-heading" id="section-1-">
							<h3 class="panel-title">
								<a class="accordion-toggle" data-toggle="collapse"
									href="#collapseOne"> <i class="icon-angle-down bigger-110"
									data-icon-hide="icon-angle-down"
									data-icon-show="icon-angle-right"></i> &nbsp;个人信息
								</a>
							</h3>
						</div>
						<div class="panel-collapse collapse in" id="collapseOne">
							<div class="panel-body page-main-header pageheader">
								<div class="row">
									<div class="col-xs-12 border-bottom"><label class="rowtitle">员工ID/姓名：</label><input class="search-input" id="searchTxt" type="text" placeholder="请输入员工ID或姓名" >&nbsp;* 姓名支持模糊查询</div>
								</div>
								<div id="personalInformation">
									
								</div>
							</div>
						</div>
					</div>
					
					<div class="panel panel-default">
						<div class="panel-heading" id="section-2-">
							<h3 class="panel-title">
								<a class="accordion-toggle" data-toggle="collapse"
									href="#collapseTwo"> <i class="icon-angle-down bigger-110"
									data-icon-hide="icon-angle-down"
									data-icon-show="icon-angle-right"></i> &nbsp;职务信息
								</a>
							</h3>
						</div>
						<div class="panel-collapse collapse in" id="collapseTwo">
							<div class="panel-body page-main-header pageheader">
								<div id="positionInformation">
									
								</div>
							</div>
						</div>
					</div>
					
					<div class="panel panel-default">
						<div class="panel-heading" id="section-3-">
							<h3 class="panel-title">
								<a class="accordion-toggle" data-toggle="collapse"
									href="#collapseThree"> <i class="icon-angle-down bigger-110"
									data-icon-hide="icon-angle-down"
									data-icon-show="icon-angle-right"></i> &nbsp;绩效
								</a>
							</h3>
						</div>
						<div class="panel-collapse collapse in" id="collapseThree">
							<div class="panel-body page-main-header pageheader">
								<div class="row optional" >
									<div class="col-xs-12 border-bottom"><label class="rowtitle">时间：</label>
										<select id="selectTimeOne">
										</select>
										<span class="condition" id="selectTimeUpOne">上半年</span><span class="condition"  id="selectTimeDownOne">下半年</span>
										<label>&nbsp;&nbsp;-&nbsp;&nbsp;</label>
										<select id="selectTimeTwo">
										</select>
										<span class="condition"  id="selectTimeUpTwo">上半年</span><span class="condition"  id="selectTimeDownTwo">下半年</span>
									</div>
								</div>
								<div id="achievements">
									
								</div>
							</div>
						</div>
					</div>
					
					<div class="panel panel-default">
						<div class="panel-heading" id="section-4-">
							<h3 class="panel-title">
								<a class="accordion-toggle" data-toggle="collapse"
									href="#collapseFour"> <i class="icon-angle-down bigger-110"
									data-icon-hide="icon-angle-down"
									data-icon-show="icon-angle-right"></i> &nbsp;其他
								</a>
							</h3>
						</div>
						<div class="panel-collapse collapse in" id="collapseFour">
							<div class="panel-body page-main-header">
								<div class="row">
									<div class="col-xs-12 border-bottom"><label class="rowtitle">过往履历：</label><input id="pastHistory" placeholder="可输入关键字进行搜索"/></div>
								</div>
								<div class="row hide">
									<div class="col-xs-12 border-bottom"><label class="rowtitle">项目经历：</label><input id="projectExperience" placeholder="可输入关键字进行搜索"/></div>
								</div>
								<div class="row">
									<div class="col-xs-12 border-bottom-no"><label class="rowtitle">培训经历：</label><input id="trainingExperience" placeholder="可输入关键字进行搜索"/></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

	<div class="page-content">
		<div class="main-container-inner">
			
		</div>
	</div>
	<script src="${jsRoot}biz/employee/talentSearchAdvanced.js"></script>
</body>
</html>