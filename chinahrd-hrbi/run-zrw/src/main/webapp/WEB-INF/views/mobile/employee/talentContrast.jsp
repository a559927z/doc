<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <meta charset="UTF-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="description" content="overview & stats"/>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
	<link rel="icon" href="${ctx}/favicon.ico" type="image/x-icon"/>
	<link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon"/>
    <title>人才PK</title>
    <link href="${ctx}/assets/css/jgGrid/ui.jqgrid.css" rel="stylesheet"/>
  <link rel="stylesheet" href="${ctx}/assets/mobile/css/slide.css"/>
  <link rel="stylesheet" href="${ctx}/assets/mobile/css/appBase.css?v=${v.version}"/>
   <link rel="stylesheet" href="${ctx}/assets/mobile/css/biz/employee/talentContrast.css?v=${v.version}"/>
<link rel="stylesheet" href="${ctx}/assets/mobile/css/sweetalert.css"/>
</head>

<body>
<%-- 	    <input type="hidden" id="empIds" value="${empIds}"> --%>
<%-- <input type="hidden" id="empIds" value="${empIds }"> --%>
<input type="hidden" value="${performanceStr }" id="performanceStr">
<input type="hidden" value="${performanceValue }" id="performanceValue">
	<section class="wrapper contrast">
		<main>
		<section class="vs-cartype fn-mt compareEmp">
			<div class="box">
				<div class="tit">
					<h1>对比人员</h1>
				</div>
				<div class="cont">
					<div  id="contrastObj" class="slide" style="transform: translate3d(0px, 0px, 0px);">
						<table cellpadding="0" cellspacing="0">
							<tbody>
								<tr>
									<td class="added"> 
										<div class="col-xs-3 ct-col1 top-div"></div>
									</td>
									<td class="added">
										<div class="col-xs-3 ct-col1 top-div"></div>
									</td>
									<td class="added">
										<div class="col-xs-3 ct-col1 top-div"></div>
									</td>
									<td class="added">
									<div class="col-xs-3 ct-col1 top-div"></div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</section>
		<section class="vs-cartype fn-mt scroll-heard hide">
			<div class="box fixedTop">
				<div class="tit">
					<h1>对比人员</h1>
				</div>
				<div class="cont">
					<div id="scrollObj" class="slide" style="transform: translate3d(0px, 0px, 0px);">
						<table cellpadding="0" cellspacing="0">
							<tbody>
								<tr>
									<td class="added"> 
										<div class="col-xs-3 ct-col1 top-div"></div>
									</td>
									<td class="added">
										<div class="col-xs-3 ct-col1 top-div"></div>
									</td>
									<td class="added">
										<div class="col-xs-3 ct-col1 top-div"></div>
									</td>
									<td class="added">
									<div class="col-xs-3 ct-col1 top-div"></div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</section>
		<section class="vs-cardata">
			<div class="box" id="accordion">
				<div class="tit" type="vs-title">
					<dl>
						<dt>
							<h1>
								个人信息
							</h1>
						</dt>
						<dd>
							<span>性别</span>
						</dd>
						<dd>
							<span>年龄</span>
						</dd>
						<dd>
							<span>婚否</span>
						</dd>
						<dd class="js-subsidy fn-hide">
							<span>学历</span>
						</dd>
					</dl>
					<dl>
						<dt>
							<h1>职务信息</h1>
						</dt>
						<dd>
							<span>(分)公司</span>
						</dd>
						<dd>
							<span>部门</span>
						</dd>
						<dd>
							<span>入职时间</span>
						</dd>
						<dd>
							<span>职位主序列</span>
						</dd>
						<dd>
							<span>职位子序列</span>
						</dd>
						<dd>
							<span>职位层级</span>
						</dd>
						<dd>
							<span>职级</span>
						</dd>
						<dd>
							<span>现岗位任职时间</span>
						</dd>
						<dd>
							<span>现岗位</span>
						</dd>
					</dl>
					<dl>
						<dt>
							<h1>
								个人绩效
							</h1>
						</dt>
						<dd >
							<span style="line-height: 90px">绩效轨迹</span>
						</dd>
					</dl>
					<dl>
						<dt>
							<h1>
								个人能力
							</h1>
						</dt>
						<dd>
							<span>测评年度</span>
						</dd>
						<dt>
							<span>
								能力维度
							</span>
						</dt>
						<div id="dimensionDivId">
<!-- 							<dd> -->
<!-- 								<span>战略导向</span> -->
<!-- 							</dd> -->
<!-- 							<dd> -->
<!-- 								<span>组织学习</span> -->
<!-- 							</dd> -->
<!-- 							<dd> -->
<!-- 								<span>全局观</span> -->
<!-- 							</dd> -->
						</div>
					</dl>
					<dl>
						<dt>
							<h1>工作经历</h1>
						</dt>
						<dd>
<!-- 							<span style="line-height: 144px;">本公司经历</span> -->
							 <span id="departChangePanel">本公司经历</span>
						</dd>
						<dd>
							 <span  id="pastResumePanel">过往履历</span>
<!-- 							<span  style="line-height: 144px;">过往履历</span> -->
						</dd>
					</dl>
					<dl>
						<dt>
							<h1>成长轨迹</h1>
						</dt>
						<dd>
							<span style="line-height: 90px">成长轨迹</span>
						</dd>
					</dl>
					
				</div>
				<div class="cont">
					<div class="slide" style="transform: translate3d(0px, 0px, 0px);">
						<table cellpadding="0" cellspacing="0">
							<tbody>
								<tr>
									<th>&nbsp;</th>
								</tr>
								<tr id="sexRow">
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr id="ageRow">
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr id="marryStatusRow">
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr id="degreeRow">
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<th>&nbsp;</th>
								</tr>
								<tr id="organParentRow">
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr id="organRow">
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr id="entryDateRow">
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr id="sequenceRow">
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr id="sequenceSubRow">
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr id="abilityRow">
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr id="rankRow">
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr id="postionAssumeDateRow">
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr id="positionRow">
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<th>&nbsp;</th>
								</tr>
								<tr class="previewTr">            
									<td class="previewChart" id="perfTrackChart0"></td>
									<td class="previewChart" id="perfTrackChart1"></td>
									<td class="previewChart" id="perfTrackChart2"></td>
									<td class="previewChart" id="perfTrackChart3"></td>
								</tr>
								<tr>
									<th>&nbsp;</th>
								</tr>
								<tr id="evalYearId">
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<th>&nbsp;</th>
								</tr>
								<tbody id="dimensionId">

								</tbody>
								<tr>
									<th>&nbsp;</th>
								</tr>
								<tbody id="departChange">
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</tbody>
								<tbody id="pastResume">
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</tbody>
								<tr>
									<th>&nbsp;</th>
								</tr>
								<tr class="previewTr">
									<td class="previewChart" id="growthChart0"></td>
									<td class="previewChart" id="growthChart1"></td>
									<td class="previewChart" id="growthChart2"></td>
									<td class="previewChart" id="growthChart3"></td>
								</tr>
								
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</section>
		</main>
	</section>
  <div id="img-window" class="hide">
        <div class="top-ct-circle">
            <div class="top-div-btn-add">+</div>
        </div>

    </div>
    <div id="img-window-scroll" class="hide">
        <div class=" top-ct-circle ">
            <div class="top-div-btn-add scrollImg">+</div>
        </div>

    </div>
        <!-- 成长轨迹弹出框 -->
    <div id="growModal" class="modal fade popup-modal"  tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-header-text"></div>
                    <button type="button" class="close app-close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                </div>
                <div class="modal-body">
                    <div class="col-xs-12 ct-col1">
                        <div id="jobChangeChart" class="col-xs-12"></div>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
    
        <!-- 个人绩效轨迹弹出框 -->
    <div id="perModal" class="modal fade popup-modal"  tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close app-close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                </div>
                <div class="modal-body">
                    <div class="col-xs-12 ct-col1">
                        <div id="perChart" class="col-xs-12"></div>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
    <!--个人绩效轨迹 弹出框 end-->
    
     <!-- 搜索弹出框 -->
    <div id="search-modal" class="modal fade"  tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <input type="hidden" id="search-index" >
                    <button type="button" class="close app-close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title">添加人员</h3>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="form-group">
                                <input type="text" class="form-control search-query" id="search-txt" placeholder="请输入员工ID/姓名" />

                            </div>
                        </div>
                   
                    </div>
                    <div class="row">
                        <div class="col-xs-12 column" id="searchEmpTable">
                            <table class="borderless" id="searchEmpGrid"></table>
                        </div>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
<script>
G_WEB_ROOT = "<%=basePath%>";
</script>
<script src="${jsRoot}require.js"></script>
<script src="${jsRoot}lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/mobile/js/biz/employee/talentContrast.js?v=${v.version}"></script>
</body>
</html>
