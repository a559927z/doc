<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>

<html lang="en">
<head>
<!-- jsp文件头和头部 -->
<%@include file="/WEB-INF/views/include/top.jsp"%>
<title>岗位胜任度</title>
<link rel="stylesheet"
	href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" href="${ctx}/assets/css/select2/select2.min.css" />
<link rel="stylesheet"
	href="${ctx}/assets/css/biz/competency/positionCompetency.css" />
</head>
<body>

	<div class="position">
		<div class="leftBody">
			<div class="leftListBigDiv">岗位胜任度</div>
			<div page="page-one" class="leftListDiv selectList">胜任度总览</div>
			<div page="page-two" class="leftListDiv">明细分析</div>
			<div page="page-three" class="leftListDiv">胜任度PK</div>
			<input type="hidden" id="time" value="${time}">
			<input type="hidden" id="timeValue" value="${timeValue}">
			<input type="hidden" id="times" value="${times}">
			<input type="hidden" id="timeValues" value="${timeValues}">

		</div>
		<div class="rightBody rightBody10">
			<div id="page-one" class="rightBodyPage">
				<div class="col-sm-3 ct-22 ct-line-col">
					<div class="top-div showview" id="tooltip1" data-toggle="tooltip"
						data-placement="bottom">
						<div class="index-common-title">
							<div class="index-common-title-left"></div>
							<div class="index-common-title-text">团队平均胜任度</div>
							<div class="index-common-title-right"><div class="icon"></div></div>
						</div>
						<div class="body-div">
							<div class="top-chart1" id="top-chart"></div>
							<div class=top-bottom>${timeValue}</div>
						</div>
					</div>
				</div>

				<div class="col-sm-3 ct-22 ct-line-col">
					<div class="top-div showview" id="tooltip2" data-toggle="tooltip"
						data-placement="bottom">
						<div class="index-common-title">
							<div class="index-common-title-left"></div>
							<div class="index-common-title-text">胜任度最高岗位</div>
						</div>
						<div class="body-div">
							<div class=top-chart2>
								<canvas id="high-chart" width="320px" height="180px"></canvas>
							</div>
							<div class=top-bottom>产品经理</div>
						</div>
					</div>
				</div>

				<div class="col-sm-3 ct-22 ct-line-col">
					<div class="top-div showview" id="tooltip3" data-toggle="tooltip"
						data-placement="bottom">
						<div class="index-common-title">
							<div class="index-common-title-left"></div>
							<div class="index-common-title-text">胜任度最低岗位</div>
						</div>
						<div class="body-div">
							<div class=top-chart2>
								<canvas id="low-chart" width="320px" height="180px"></canvas>
							</div>
							<div class=top-bottom>产品经理</div>
						</div>
					</div>
				</div>
				<div class="col-sm-4 ct-34 ct-line-col">
					<input id="quotaId" type="hidden" value="${quotaId}">
					<div class="top-div" id="timeLine"></div>
				</div>

				<div class="col-sm-6 ct-line-col SetUpBody" view="chart">
					<div class="index-common-title bottom-title">
						<div class="index-common-title-left bottom-left"></div>
						<div class="index-common-title-text bottom-text">团队平均胜任度趋势</div>
					</div>
					<div class="bottom-div">
						<div class="panelBtn rightdiv teamAvgComBtn">
							<div class="panelBtn_context active">
								<div class="pull-right">
                       			 <ul class="ztree" id="teamAvg_organZtree"></ul>
                   			 	</div>
							</div>
							<div class="panelBtn_context">
								<div>
									<span id="teamAvg-select-showText">岗位</span>
									<div class="icon_search1"></div>
								</div>
								<div class="posion_expand panelBtn_expand-search">
									<div class="col-sm-12 select-panel">
										<div class="col-sm-9  select-panel">
											<select class="dis-search-input" id="teamAvg-select"
											placeholder="请输入岗位"></select>
										</div>
										<div class="col-sm-3  select-panel">
										<div class="add-on dis-search-input-btn"
										id="teamAvgCom_search">搜索</div>
										</div>
									</div>


								</div>
							</div>
						</div>
						<div class="row ct-row mainArea">
							<div class="chartArea" id="teamAvgComThrendChart"></div>
						</div>
					</div>
				</div>
				<div class="col-sm-6 ct-line-col SetUpBody" view="chart">
					<div class="index-common-title bottom-title">
						<div class="index-common-title-left bottom-left"></div>
						<div class="index-common-title-text bottom-text">下级组织团队平均胜任度对比</div>
					</div>
					<div class="bottom-div clearfix">
						<div class="leftdiv">
							<div class="context select-time-div">
								<div class="show_context">
									<span></span>
									<div class="icon_select"></div>
								</div>
								<div class="expand_div">
									<div>
										<span>时间</span> <select class="select-time"></select>
									</div>
									<div class="btns">
										<div class="btn btn-primary" id="teamAvg—btn">确认</div>
										<div class="btn btn-default">取消</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row ct-row mainArea">
							<div class="chartArea-back" id="teamAvgComContrast_back">返回上级</div>
							<div class="chartArea2" id="teamAvgComContrastChart"></div>
						</div>
						<div class="col-sm-12 operation_declaration">操作提示：点击组织可查看该下级组织的平均岗位胜任度</div>
					</div>
				</div>
				<div class="col-sm-12 ct-line-col SetUpBody" view="chart">
					<div class="index-common-title bottom-title">
						<div class="index-common-title-left bottom-left"></div>
						<div class="index-common-title-text bottom-text">序列/职级团队平均胜任度对比分析</div>
					</div>
					<div class="bottom-div">
						<div class="leftdiv">
							<div class="context select-time-div">
								<div class="show_context">
									<span></span>
									<div class="icon_select"></div>
								</div>
								<div class="expand_div">
									<div>
										<span>时间</span> <select class="select-time"></select>
									</div>
									<div class="btns">
										<div class="btn btn-primary" id="ability—btn">确认</div>
										<div class="btn btn-default">取消</div>
									</div>
								</div>
							</div>
						</div>

						<div
							class="col-md-6 col-sm-12 col-xs-12 leftBarArea noPaddingLeft">
							<div class="chartArea-back" id="sequence_back">返回全部序列</div>
							<div class="col-sm-12 chartArea noPaddingLeft"
								id="sequenceChart"></div>
						</div>
						<div class="col-md-6 col-sm-12 col-xs-12 rightPieArea">
							<div class="col-sm-12 chartArea" id="abilityChart"></div>
						</div>
						<div class=" col-sm-12 operation_declaration">操作提示：点击职位序列可查看该序列下的职级分布，再次点击则可返回默认统计</div>
					</div>
				</div>
			</div>
			<div id="page-two" class="rightBodyPage">
				<div class="col-sm-12 ct-line-col">
					<div class="index-jxmb-tab">
						<div page="chartview-all"
							class="index-jxmb-btn index-jxmb-btn-select">人员</div>
						<div page="chartview-g" class="index-jxmb-btn">岗位</div>
					</div>
					<div class="bottom-div clearfix">
						<div id="chartview-all" class="col-sm-12 ct-line-col two-body-right hide select_tab">
							<div class="col-sm-12 ct-line-col conditionBtnListBody"
								 id="searchBox">

								<div class="dis-search">
									<select id="emp_searchTime" class="dis-search-select select-time">

									</select> <input class="dis-search-input search_right" id="emp_searchTxt" type="text"
													 placeholder="请输入员工ID或姓名">
									<div class="add-on dis-search-input-btn" id="emp_detail_search">搜索</div>

								</div>
								<div class="more-search"></div>
							</div>

							<div class="cardTabel" id="emp_tabel"></div>
						</div>
						<div id="chartview-g"
							 class="col-sm-12 ct-line-col two-body-right hide">
							<div class="col-sm-12 ct-line-col conditionBtnListBody">
								<div class="dis-search">
									<select id="position_searchTime" class="dis-search-select select-time">
									</select> <input class="dis-search-input search_right" id="position_searchTxt"
													 type="text" placeholder="请输入岗位">
									<div class="add-on dis-search-input-btn"
										 id="position_detail_search">搜索</div>

								</div>
							</div>

							<div class="cardTabel" id="position_tabel"></div>
						</div>
					</div>
				</div>
			</div>
			<div id="page-three" class="rightBodyPage">
				<div class="contrast" id="contrast">
					<div class="menu">
						<input type="hidden" id="empIds" value="${empIds}">
						<ul class="menu-list">
							<li position="float0"
								class="menu-line menu-line1 menu-line-select">
								<div class="menu-line-spor menu-line-spor1"></div> <span
								class="menu-line-text">回顶部</span>
							</li>
							<li position="float1" class="menu-line menu-line2">
								<div class="menu-line-spor"></div> <span class="menu-line-text">能力维度</span>
							</li>
							<li position="float2" class="menu-line menu-line2">
								<div class="menu-line-spor"></div> <span class="menu-line-text">高绩效员工特征</span>
							</li>
							<li position="float3" class="menu-line menu-line2">
								<div class="menu-line-spor"></div> <span class="menu-line-text">关键词检索</span>
							</li>
						</ul>
					</div>
					<div class="contrast-right">
						<div class="row ct-row">
							<div class="row ct-row top-fa">
								<div class="col-xs-2 ct-col1 top-obj top-left">
									<div class="top-left-text1">
										<span class="top-left-label" id="contrast-position">岗位</span>
										<div class="top-left-expend">
											<div class="col-sm-12 guide_position_select_panel">
												<div class="top-left-expend-label">岗位</div>
<!-- 												<div class="top-left-expend-icon"></div> -->
											</div>
											<div class="col-sm-12 guide_position_select_panel">
												<div class="dis-search-position col-sm-12 select-panel">
													<div class="col-sm-9  select-panel">
													<select  id="guide_position2" ></select>
													</div>
													<div class="col-sm-3  select-panel">
													<div class="col-sm-4 add-on dis-search-input-btn" id="guide_position2_btn">搜索</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="col-xs-10 ct-col1 top-right" id="contrastObj">
									<div class="col-xs-3 ct-col1 top-obj top-div"></div>
									<div class="col-xs-3 ct-col1 top-obj top-div"></div>
									<div class="col-xs-3 ct-col1 top-obj top-div"></div>
									<div class="col-xs-3 ct-col1 top-obj top-div"></div>
								</div>
							</div>
						</div>

						<div class="row ct-row" id="accordion">
							<div class="top-left-text2">注：红色为低于岗位条件</div>
							<div class="col-xs-12 ct-col1 ct-col-contrast">
								<div class="contrast-table-top contrast-table-top-too float1">
<!-- 										<div class="contrast-table-switch contrast-table-reduce"></div> -->
									<div class="contrast-table-switch contrast-table-add"></div>
									<span class="contrast-table-title">能力维度</span>
								</div>
								<table class="contrast-table" id="dimensionTableId">

								</table>
								<div class="contrast-table-top contrast-table-top-too float2">
<!-- 										<div class="contrast-table-switch contrast-table-reduce"></div> -->
									<div class="contrast-table-switch contrast-table-add"></div>
									<div class="contrast-table-title">高绩效员工特征</div>
									<div class="config_div"><div class="config"></div></div>
									<div class="config_note">统计说明：高绩效员工为近<span>3</span>年有过<span>2</span>次<span>4</span>星以上人员</div>
								</div>
								<table class="contrast-table">
									<tr id="sexRow">
										<td class="contrast-td-one">性别 <span></span><span></span></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr id="degreeRow">
										<td class="contrast-td-one">学历 <span></span><span></span></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
<!-- 										<tr id="entryDateRow"> -->
<!-- 											<td class="contrast-td-one">职称 <span>一级</span><span>70%</span></td> -->
<!-- 											<td></td> -->
<!-- 											<td></td> -->
<!-- 											<td></td> -->
<!-- 											<td></td> -->
<!-- 										</tr> -->
									<tr id="seniorityRow">
										<td class="contrast-td-one">司龄 <span></span><span></span></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr id="ageRow">
										<td class="contrast-td-one">年龄 <span></span><span></span></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</table>

								<div class="contrast-table-top contrast-table-top-too float3">
<!-- 										<div class="contrast-table-switch contrast-table-reduce"></div> -->
									<div class="contrast-table-switch contrast-table-add"></div>
									<span class="contrast-table-title keyword-search">关键词检索</span>
									<div class="expand_div">
										<div class="col-sm-12 dis-search">
											<input class="dis-search-input" id="keyword-search" type="text"
												placeholder="请输入关键词搜索">
											<div class="add-on dis-search-input-btn" id="keyword-search-btn">搜索</div>
										</div>
										<div class="col-sm-12 top-left-text2">说明：系统从过往履历和培训经历维度进行匹配检索</div>
									</div>

								</div>
								<table class="contrast-table" id="keywordTable">

								</table>
							</div>
						</div>
					</div>

					<div id="img-window" class="hide">
						<div class="top-ct-circle">
							<img class="img-circle img-rc-head"
								src="${ctx}/assets/img/index/head2.png">
						</div>
						<div class="top-div-float">
							<div class="top-div-btn">
								搜索添加
								<div class="top-div-btn-add"></div>
							</div>
						</div>
					</div>

					<!--添加员工 弹出框 begin-->
					<div id="search-modal" class="modal fade popup-modal"
						data-backdrop="static" tabindex="-1" role="dialog">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<div class="modal-header-text">
										添加人员<input type="hidden" id="search-index">
									</div>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">
									<div class="row ct-row">
										<div class="yj-search">
											<input type="text" class="yj-search-input" id="search-txt"
												placeholder="请输入员工ID/姓名" />
											<div class="yj-search-input-btn" id="search-btn">人员搜索</div>
										</div>
										<span class="search-text">*&nbsp;&nbsp;姓名支持模糊查询</span>
									</div>
									<div class="col-xs-12 ct-col1 add-middle" id="searchEmpTable">
										<table class="borderless" id="searchEmpGrid"></table>
									</div>
								</div>
							</div>
							<!-- /.modal-content -->
						</div>
						<!-- /.modal-dialog -->
					</div>
					<!--添加员工 弹出框 end-->
				</div>
			</div>
		</div>

		<!--遮罩层 begin-->
		<div class="shade"></div>
		<!--遮罩层 end-->

	</div>
	<div class="custom_model" id="position_table_model">
		<div class="card-table-td">
			<div class="competency-card">
				<div class="competency-card-top-panel">
					<div class="detail">
						<div class="detail_position_big">产品经理</div>
					</div>
				</div>
				<div class="competency-card-chart"></div>
				<div class="index-common-title bottom-title">
					<div class="index-common-title-left bottom-left"></div>
					<div class="index-common-title-text bottom-text">能力维度</div>
				</div>
				<div class="competency-card-panel">
					<div class="position-panel high_dimension_div">
						<div class="position-label">匹配度最高</div> 
						<div class="position-label-name rate"></div> 
						<div class="position-label-name name"></div>
						
					</div>
					<div class="position-panel low_dimension_div">
						<div class="position-label">匹配度最低</div> 
						<div class="position-label-name rate"></div> 
						<div class="position-label-name name"></div>
					</div>
				</div>
				<div class="index-common-title bottom-title">
					<div class="index-common-title-left bottom-left"></div>
					<div class="index-common-title-text bottom-text">人员</div>
				</div>
				<div class="competency-card-panel">
					<div class="position-panel high_emp_div">
						<div class="position-label">胜任度最高</div> 
						<div class="position-label-name rate"></div> 
						<div class="position-label-name name"></div>
					</div>
					<div class="position-panel low_emp_div">
						<div class="position-label">胜任度最低</div> 
						<div class="position-label-name rate"></div> 
						<div class="position-label-name name"></div>
					
					</div>
				</div>
				<div class="competency-card-all competency-card-all-positon">
					全部人员(<span class="total_emp"></span>)
				</div>
			</div>
		</div>
	</div>

	<div class="custom_model" id="emp_table_model">
		<div class="card-table-td">
			<div class="competency-card">
				<div class="competency-card-top-panel">
					<div class="detail">
						<div class="detail_username"></div>
						<div class="detail_position"></div>
					</div>
				</div>
				<div class="competency-card-chart"></div>
				<div class="index-common-title bottom-title">
					<div class="index-common-title-left bottom-left"></div>
					<div class="index-common-title-text bottom-text">能力维度</div>
				</div>
				<div class="competency-card-panel">
					<div class="position-panel high_div">
						<div class="position-label">最高能力</div>
						<div class="position-label-name rate"></div> 
						<div class="position-label-name name"></div>
					</div>
					<div class="position-panel low_div">
						<div class="position-label">最低能力</div> 
						<div class="position-label-name rate"></div> 
						<div class="position-label-name name"></div>
					</div>
				</div>
				<div class="index-common-title bottom-title">
					<div class="index-common-title-left bottom-left"></div>
					<div class="index-common-title-text bottom-text">未达标能力</div>
				</div>
				<div class="competency-card-panel">
					<div class="position-panel wdb"></div>
				</div>
				<div class="competency-card-all">全部能力</div>
			</div>
		</div>
	</div>

	<div id="circle_model"  class="custom_model">
		<div class="wrap">
			<canvas class="competency-level-canvas" width="160px" height="160px"></canvas>
		</div>
	</div>
	<!--配置信息 弹出框 begin-->
	<div id="config-modal" class="modal fade popup-modal"
		data-backdrop="static" tabindex="-1" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<div class="modal-header-text">岗位胜任度指标设置</div>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="form-inline config-modal-row">
						<div class="form-group">
							<div class="config-modal-img img-high"></div>
						</div>
						<div class="form-group">
							<label  for="config_high1">胜任度高：</label> 
							<input type="text" class="form-control" maxlength="3" id="config_high_show" readonly="readonly" placeholder=""></input>
						</div>
						<div class="form-group">
							<label>%以下</label> 
						</div>
					</div>
					<div class="form-inline config-modal-row">
						<div class="form-group">
							<div class="config-modal-img img-middle"></div>
						</div>
						<div class="form-group">
							<label for="config_middle1">胜任度中：</label>
							 <input type="text" class="form-control" maxlength="3" id="config_low" placeholder=""></input>
						</div>
						<div class="form-group">
							<label>%-</label> 
							<input type="text" class="form-control" maxlength="3" id="config_high" placeholder=""></input>
						</div>
						<div class="form-group">
							<label>%</label>
						</div>
					</div>
					<div class="form-inline config-modal-row">
						<div class="form-group">
							<div class="config-modal-img img-low"></div>
						</div>
						<div class="form-group">
							<label for="config_low1">胜任度低：</label> 
							<input type="text" class="form-control" maxlength="3" id="config_low_show" readonly="readonly" placeholder=""></input>
						</div>
						<div class="form-group">
							<label>%以下</label> 
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="btns">
						<div class="btn btn-primary">确认</div>
						<div class="btn btn-default">取消</div>
					</div>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!--配置信息 弹出框 end-->

	<!--PK胜任度向导 弹出框 begin-->
	<div id="guide-modal" class="modal fade popup-modal"
		data-backdrop="static" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<div class="modal-header-text">胜任度PK向导</div>
				</div>
				<div class="modal-body">
					<div>
						<h4>
							<b>胜任度PK可以帮助您将候选人跟岗位进行多维度匹配分析：</b>
						</h4>
					</div>
					<div class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label re" for="guide_position"><span class="require">*</span>岗位：</label>
							<div class="col-sm-10">
								<select id="guide_position" name="guide_position"></select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="guide_emp1">候选人1:</label>
							<div class="col-sm-10">
								<select id="guide_emp1" name="guide_emp1" ></select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="guide_emp2">候选人2:</label>
							<div class="col-sm-10">
									<select id="guide_emp2" name="guide_emp2" ></select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="guide_emp3">候选人3:</label>
							<div class="col-sm-10">
									<select id="guide_emp3" name="guide_emp3" ></select>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="btns">
						<div class="btn btn-primary" id="guide-btn">确认</div>
					</div>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!--PK胜任度向导 弹出框 end-->

	<!--高绩效配置 弹出框 begin-->
	<div id="high-custom-modal" class="modal fade popup-modal"
		data-backdrop="static" tabindex="-1" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<div class="modal-header-text">修改设置</div>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="form-inline config-modal-row">
						<div class="form-group">
							<label>高绩效员工定义：近</label>
							 <select id="yearNumSelect">
								<option>1</option>
								<option>2</option>
								<option selected="selected">3</option>
								<option>4</option>
								<option>5</option>
							</select>
						</div>
						<div class="form-group">
							<label>年有过</label> 
							<select id="continueNumSelect">
								<option>1</option>
								<option  selected="selected">2</option>
								<option>3</option>
								<option>4</option>
								<option>5</option>
							</select>
						</div>
						<div class="form-group">
							<label>次</label> 
							<select id="starSelect">
								<option>1</option>
								<option>2</option>
								<option>3</option>
								<option  selected="selected">4</option>
								<option>5</option>
							</select>
						</div>
						<div class="form-group">
							<label>星以上员工</label>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="btns">
						<div class="btn btn-primary">确认</div>
						<div class="btn btn-default">取消</div>
					</div>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!--高绩效配置 弹出框 end-->
	<!--能力维度 弹出框 begin-->
	<div id="ability-dimension-modal" class="modal fade popup-modal"
		data-backdrop="static" tabindex="-1" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<div class="modal-header-text">
						能力维度<input type="hidden" id="search-index">
					</div>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="row ct-row mainArea">
					<div class="col-sm-12 chart">
						<div id="ability-dimension-chart"></div>
					</div>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!--能力维度 弹出框 end-->
	<div class="custom_model" id="dimension_model">
		<div class="content_tr">
			<div class="col-sm-4 custom_model_name"></div>
			<div class="col-sm-8 progress_bar_widget">
				<div class="col-sm-8 progress_bar ">
					<div class="progress_bar_fill "></div>
				</div>
				<div class="col-sm-4 progress_bar_value"></div>
			</div>
		</div>
	</div>
	<div class="custom_model" id="dimension_emp_model">
		<div class="content_tr">
			<div class="progress_bar_widget">
				<div class="col-sm-8 progress_bar">
					<div class="progress_bar_fill "></div>
				</div>
				<div class="col-sm-4 progress_bar_value"></div>
			</div>
		</div>
	</div>
	<!--胜任度 弹出框 begin-->
	<div id="competency-level-modal" class="modal fade popup-modal"
		data-backdrop="static" tabindex="-1" role="dialog">
		<div class="modal-dialog competency-level-modal">
			<div class="modal-content competency-level-modal">
				<div class="modal-header">
					<div class="modal-header-text">
						胜任度<input type="hidden" id="search-index">
					</div>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="dis-search competency-level-modal-search">
						<input class="dis-search-input" id="competency-level-modal-text" type="text"
							placeholder="输入姓名进行搜索">
						<div class="add-on dis-search-input-btn" id="competency-level-modal-btn">搜索</div>
					</div>
					<div class="col-sm-12">
						<div
							class="col-sm-4 ct-line-col competency-level-modal-panel competency-level-modal-panel-left">
							<div class="competency-level-modal-panel-title">团队能力概貌</div>
							<div class="content_table">
								<div class="head">
									<div class="col-sm-3">能力维度</div>
									<div class="col-sm-9 text_center">平均胜任度</div>
								</div>
								<div class="content">

								</div>
							</div>
						</div>
						<div
							class="col-sm-8 ct-line-col competency-level-modal-panel competency-level-modal-panel-right">
							<div class="competency-level-modal-panel-title">岗位能力分析</div>
							<div class="cardTabel" id="postion-competency-tabel"></div>

						</div>
					</div>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!--能力维度 弹出框 end-->

	<div class="custom_model" id="cardTab-tr-horizontal-model">
		<div class="card-table-tr cardTab-tr-horizontal">
			<div class="content_table competency-level-modal_col">
					<div class="head competency-level-head text_center">
						<div class="competency-level-chart">
							<canvas class="competency-level-canvas" width="120px"
								height="60px"></canvas>
						</div>
						<div class="user-name"></div>
					</div>
					<div class="content">
						
						
					</div>

				</div>
		</div>
	</div>
	
	
	<!--关键词  弹出框 begin-->
	<div id="keyword-modal" class="modal fade popup-modal"
		data-backdrop="static" tabindex="-1" role="dialog">
		<div class="modal-dialog click-show-modal">
			<div class="modal-content">
				<div class="modal-header">
					<div class="modal-header-text">
						关键词检索
					</div>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="col-xs-12 ct-col1 add-middle click-show-table-panel">
						<div class="row ct-row two-body">
							<div class="index-jxmb-tab" id="keyword-tab">
								<div page="keyword-resume-tab" class="index-jxmb-btn index-jxmb-btn-select">过往履历<span class="keyword-tab-span"></span></div>
								<div page="keyword-train-tab" class="index-jxmb-btn">培训经历<span class="keyword-tab-span"></span></div>
								<div page="keyword-file-tab" class="index-jxmb-btn">测评报告<span class="keyword-tab-span"></span></div>
							</div>
							<div id="keyword-resume-tab" class="col-sm-12 ct-line-col two-body-right hide select_tab">
	
								<table class="click-show-table" id="keyword-resume-table"></table>
							</div>
							<div id="keyword-train-tab"
								class="col-sm-12 ct-line-col two-body-right hide">
							<table class="click-show-table" id="keyword-train-table"></table>
							</div>
							<div id="keyword-file-tab"
								class="col-sm-12 ct-line-col two-body-right hide">
							<table class="click-show-table" id="keyword-file-table"></table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!---关键词   弹出框 end-->
	
	<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
	<script src="${ctx}/assets/js/biz/competency/positionCompetency.js"></script>
</body>
</html>