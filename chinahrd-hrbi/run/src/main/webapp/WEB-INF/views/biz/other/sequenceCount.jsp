<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<!-- jsp文件头和头部 -->
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>职位序列统计</title>
	<link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"  />
	<link rel="stylesheet" href="${ctx}/assets/css/biz/other/sequenceCount.css"/>
</head>
<body>
	<div class="page-content" id="main-container">
		<div class="main-container-inner">
			<!-- 流失原因及去向 -->
			<div class="row column">
				<div class="tab-pane col-xs-12">
					<div class="widget-box transparent f-white-bg">
						<div class="widget-header widget-header-flat">
							<h4 class="bolder">职位序列统计</h4>
						</div>
						<div class="widget-body">
							<div class="row column">
								<div class="col-lg-8 col-md-7 col-sm-6 col-xs-12 column">
									<div class="count" id="">
										<h3>职位层级</h3>
										<span class="arrow_btn" id="arrowBtn"></span>

										<div class="left" id="countLeft"></div>
										<div class="right">
											<div class="main clearfix">
												<div id="boxes"></div>
											</div>
											<div class="title">
												<div class="hide" id="titleNav">
													<a href="javascript:void(0)" class="pre"></a>
													<a href="javascript:void(0)" class="next"></a>
												</div>
												<ul class="clearfix" id="mainSequence">
													<%--<li class="fl">管理序列</li>--%>
													<%--<li class="fm">职能序列</li>--%>
													<%--<li class="fr">业务序列</li>--%>
												</ul>
											</div>
										</div>
									</div>
									<div id="hiddren"></div>
								</div>
								<div class="col-lg-4 col-md-5 col-sm-6 col-xs-12 right_main_panel">
									<div class="panel panel-default">
										<div class="panel-heading">
											<h3 class="panel-title">
												<a class="accordion-toggle" data-toggle="collapse"
												   href="#collapseSearch"> <i
														class="icon-search bigger-110"></i> </a>&nbsp;<span id="organVal"></span>
													<span id="sequenceNav">
														&nbsp;&nbsp;<i class="icon-caret-right"></i>&nbsp;&nbsp;
														<span class="dropdown" id="sequenceVal">
															<a class="dropdown-toggle" href="javascript:void(0)" data-toggle="dropdown"></a>
															<ul class="dropdown-menu"></ul>
														</span>
													</span>
													<span class="hide" id="subSequenceNav">
														&nbsp;&nbsp;<i class="icon-caret-right"></i>&nbsp;&nbsp;
														<span class="dropdown" id="subSequenceVal">
															<a class="dropdown-toggle" href="javascript:void(0)" data-toggle="dropdown"></a>
															<ul class="dropdown-menu"></ul>
														</span>
													</span>
											</h3>
										</div>
										<div class="panel-collapse collapse" id="collapseSearch">
											<div class="panel-body">
												<div class="row">
													<div class="col-xs-3"><label class="fr">职位序列</label></div>
													<div class="col-xs-9" id="sequenceData"></div>
												</div>
												<div class="row hide" id="subSequenceRow">
													<div class="col-xs-3"><label class="fr">职位子序列</label></div>
													<div class="col-xs-9" id="subSequenceData"></div>
												</div>
												<div class="row">&nbsp;</div>
												<div class="row">
													<button class="btn btn-white" id="removeSearchBtn" >取消</button>
													<button class="btn btn-primary" id="submitSearchBtn">确定</button>
												</div>
											</div>
										</div>
										<div class="panel-collapse collapse in" id="collapseMain">
											<div class="panel-body">
												<div class="row" id="count"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

<script src="${ctx}/assets/js/biz/other/sequenceCount.js"></script>
</body>
</html>
