<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<html>
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>关键人才库</title>
	<link rel="stylesheet" href="${ctx}/assets/css/zTreeStyle/zTreeStyle.css"/>

	<link rel="stylesheet" href="${ctx}/assets/css/select2/select2.min.css"/>
	<link rel="stylesheet" href="${ctx}/assets/css/biz/employee/keyTalents.css"/>
</head>
<body>

<div class="page-content pageopadding">
		<div class="row column">
			<div class="col-xs-12 colnopadding">
			<div id="talentLeft" class="col-xs-4 col-sm-3 col-md-2 background-f9f9f9 scroll">
				<div class="row padding10">
					<div class="input-group widthpc100">
						<input id="txtSearchName" type="text" class="form-control" placeholder="输入员工姓名/ID进行搜索" data-toggle="tooltip" data-placement="bottom" title="输入后回车进行搜索">
					</div>
				</div>
				<div class="row" id="talentTab">
					<div id="talentRisk" data-index="0" class="clearfix tabli active">
						<div class="name textoverflowellipsis">
							<img class="icon" src="${ctx}/assets/img/talent/talent_bell.png"><span title="离职风险预警">离职风险预警</span>
						</div>
						<div class="num textoverflowellipsis" title=""></div>
					</div>
					<div id="talentAttention" data-index="1" class="clearfix tabli">
						<div class="name textoverflowellipsis">
							<img class="icon" src="${ctx}/assets/img/talent/talent_star.png"><span title="我关注的人员">我关注的人员</span>
						</div>
						<div class="num textoverflowellipsis" title=""></div>
					</div>
					<div id="talentNew" data-index="2" class="clearfix tabli">
						<div class="name textoverflowellipsis">
							<img class="icon" src="${ctx}/assets/img/talent/talent_new.png"><span title="最近更新人员">最近更新人员</span>
							<img class="help" src="${ctx}/assets/img/talent/talent_help.png" data-toggle="tooltip" data-placement="bottom" title="本月标签、激励要素、跟踪有更新的人员">
						</div>
						<div class="num textoverflowellipsis" title=""></div>
					</div>
				</div>
				<div id="talentCate" class="row iconli">
					<div class="clearfix avatar">
						<div class="col"><img data-index="0" src="${ctx}/assets/img/talent/talent_man_selected.png" data-toggle="tooltip" data-placement="bottom" title="按人才分类查看"></div>
						<div class="col"><img data-index="1" src="${ctx}/assets/img/talent/talent_org.png" data-toggle="tooltip" data-placement="bottom" title="按组织架构查看"></div>
						<div class="col"><img data-index="2" src="${ctx}/assets/img/talent/talent_cup.png" data-toggle="tooltip" data-placement="bottom" title="按激励要素查看"></div>
					</div>
					<div class="clearfix direct">
						<div class="active0"><img src="${ctx}/assets/img/talent/talent_corner.png"></div>
					</div>
				</div>
				<div id="talentCateDetail">
					<div class="row man">
						<!--循环开始-->
						<!--
						<div data-id="qq11" class="clearfix cate">
							<div class="name" title="">A类人才</div>
							<div class="num" title="">8</div>
						</div>
						-->
					</div>
					<div class="row org hide">
						<ul id="tree" class="ztree overflowauto"></ul>
					</div>
					<div class="row cup hide">
						<!--循环开始-->
						<!--
						<div data-id="aa22" class="clearfix cate ctive">
							<div class="name" title="">管理职级晋升</div>
							<div class="num" title="">25</div>
						</div>
						-->
					</div>
				</div>
			</div>
			<div id="talentRight" class="col-xs-8 col-sm-9 col-md-10 background-fff padding0 paddingtop6 paddingbottom18 scroll">
				<!--搜索-->
				<div id="tSearchContent" class="row no-margin hide"></div>
				<!--Tab-->
				<div id="tTabContent">
					<!--预警-->
					<div id="tWarning" class="row no-margin"></div>
					<!--关注-->
					<div id="tAttention" class="row no-margin"></div>
					<!--最新-->
					<div id="tNew" class="row no-margin"></div>
				</div>
				<!--分类-->
				<div id="tCategoryContent" class="row no-margin hide"></div>
				<!--组织架构-->
				<div id="tOrgContent" class="row no-margin hide"></div>
				<!--激励-->
				<div id="tCupContent" class="row no-margin hide"></div>
				<div id="tTitleTemplate" class="row no-margin hide">
					<div class="col-xs-12 paddingleft12 paddingtop12">
						<span class="fontsize16 marginright18 name"></span>
						<label class="num"></label>
					</div>
				</div>
				<div id="tMenuTemlate" class="row no-margin hide">
					<div class="col-xs-12 paddingleft12 paddingtop12">
						<div class="pull-left">
							<img src="${ctx}/assets/img/talent/talent_grid_selected.png" data-type="grid" class="cursorpointer showtype showtypegrid" data-toggle="tooltip" data-placement="bottom" title="网格查看">
							<img src="${ctx}/assets/img/talent/talent_list.png" data-type="list" class="cursorpointer showtype showtypelist" data-toggle="tooltip" data-placement="bottom" title="列表查看">
							<div class="orderBy btn-group marginleft18 hide">
								<select>
									<option value="warn">按离职风险排序</option>
									<option value="refresh">按信息更新时间排序</option>
									<option value="ability">按职级排序</option>
									<option value="createTime">按添加时间排序</option>
								</select>
							</div>
						</div>
						<div class="addTalent pull-right hide">
							<button type="button" class="btn btn-primary btn-sm" value="">添加关键人才</button>
						</div>
					</div>
				</div>
				<div id="tTemplate" class="row no-margin hide">
					<!--网格-->
					<div class="tgrid">
						<!--循环-->
						<div data-id="{StaffEmpId}" data-talentid="{StaffKeyTalentId}" data-controlid="{ControlId}" class="gridDiv col-xs-12 col-sm-6 col-md-4 paddingleft12 paddingtop12">
							<div class="border paddingbottom12">
								<div class="row no-margin padding0 paddingtop6 paddingleft6">
									<div class=" col-xs-3 col-sm-2 no-padding">
										<img src="{StaffUrl}" onerror="this.src='${ctx}/assets/photo.jpg'" class="img-circle img-responsive portrait">
									</div>
									<div class="col-xs-9 col-sm-10">
										<div class="row">
											<span class="col-xs-3 col-sm-2 col-sm-offset-1 no-padding textoverflowellipsis staffname">{StaffName}</span>
											<label class="col-xs-4 col-sm-4 no-padding textoverflowellipsis red">{StaffCategory}</label>
											<span class="col-xs-5 col-sm-5 no-padding clearfix key-talent-icon">
												<img src="${ctx}/assets/img/talent/talent_attention_selected.png" class="attention attentionSelected no-border img-responsive pull-left cursorpointer margintop2 hide" data-toggle="tooltip" data-placement="bottom" title="取消关注">
												<img src="${ctx}/assets/img/talent/talent_attention.png" class="attention attentionUnSelected no-border img-responsive pull-left cursorpointer margintop2 hide" data-toggle="tooltip" data-placement="bottom" title="加入关注">
												<img src="${ctx}/assets/img/talent/talent_px.png" class="person no-border img-responsive pull-left cursorpointer margintop2 hide" data-toggle="tooltip" data-placement="bottom" title="人才剖像">
												<img src="${ctx}/assets/img/talent/talent_pk.png" class="pk no-border img-responsive pull-left cursorpointer margintop2 hide" data-toggle="tooltip" data-placement="bottom" title="加入对比">
												<img src="${ctx}/assets/img/talent/talent_del.png" class="delete no-border img-responsive pull-left cursorpointer margintop2 hide" data-sychron="{StaffSychron}" data-toggle="tooltip" data-placement="bottom" title="删除">
											</span>
										</div>
										<div class="row">
											<span class="col-xs-12 col-sm-11 col-sm-offset-1 no-padding textoverflowellipsis">{StaffDep}</span>
										</div>
									</div>
								</div>
								<div class="row margintop5">
									<div class="col-xs-4 text-center no-padding-right textoverflowellipsis" title="离职风险">离职风险：</div>
									<div class="col-xs-8 text-left no-padding-left">
										<i class="circle riskClick cursorpointer {StaffRisk}" data-riskflag="{StaffRiskFlag}"></i>
									</div>
								</div>
								<div class="row margintop5">
									<div class="col-xs-4 text-center no-padding-right textoverflowellipsis" title="优势标签">优势标签：</div>
									<div class="col-xs-8 text-left no-padding-left">{StaffGoodnessNum} <span class="c999">{StaffGoodnessDate}</span></div>
								</div>
								<div class="row margintop5">
									<div class="col-xs-4 text-center no-padding-right textoverflowellipsis" title="劣势标签">劣势标签：</div>
									<div class="col-xs-8 text-left no-padding-left">{StaffInferiorNum} <span class="c999">{StaffInferiorDate}</span></div>
								</div>
								<div class="row margintop5">
									<div class="col-xs-4 text-center no-padding-right textoverflowellipsis" title="激励要素">激励要素：</div>
									<div class="col-xs-8 text-left no-padding-left">{StaffEncourageTitle} <span class="c999">{StaffEncourageDate}</span></div>
								</div>
								<div class="row margintop5">
									<div class="col-xs-4 text-center no-padding-right textoverflowellipsis" title="跟踪日志">跟踪日志：</div>
									<div class="col-xs-8 text-left no-padding-left">{StaffFollowNum} <span class="c999">{StaffFollowDate}</span></div>
								</div>
							</div>
						</div>
					</div>

					<!--列表-->
					<div class="tlist">
					<div class="col-xs-12 padding12">
						<table class="table fontsize12 borderleft bordertop">
							<thead>
							<tr class="borderright">
								<th class="width-25">员工基本信息</th>
								<th class="width-10 center">离职风险</th>
								<th class="width-50">跟踪与评价</th>
								<th class="width-15">操作</th>
							</tr>
							</thead>
							<tbody>
							<!--循环-->
							<tr data-id="{StaffEmpId}" data-talentid="{StaffKeyTalentId}" data-controlid="{ControlId}" class="borderbottom borderright">
								<td class="verticalalignmiddle">
									<div class="row no-margin">
										<div class="col-xs-4 no-padding">
											<img src="{StaffUrl}" onerror="this.src='${ctx}/assets/photo.jpg'" class="img-circle img-responsive portrait">
										</div>
										<div class="col-xs-8">
											<div class="row">
												<span class="col-xs-5 col-sm-4 col-sm-offset-1 no-padding staffname">{StaffName}</span>
												<label class="col-xs-6 col-sm-7 no-padding red">{StaffCategory}</label>
											</div>
											<div class="row">
												<span class="col-xs-12 col-sm-11 col-sm-offset-1 no-padding">{StaffDep}</span>
											</div>
										</div>
									</div>
								</td>
								<td class="verticalalignmiddle textaligncenter">
									<i class="circle riskClick cursorpointer {StaffRisk}" data-riskflag="{StaffRiskFlag}"></i>
								</td>
								<td>
									<div class="row no-margin">
										<div class="row no-margin">
											<div class="row margintop8">
												<div class="col-xs-4 col-md-3 col-lg-2 text-left no-padding-right textoverflowellipsis c999" title="优势标签">优势标签：</div>
												<div class="col-xs-8 col-md-7 col-lg-10 text-left no-padding-left">{StaffGoodnessNum} <span class="c999">{StaffGoodnessDate}</span></div>
											</div>
											<div class="row margintop8">
												<div class="col-xs-4 col-md-3 col-lg-2 text-left no-padding-right textoverflowellipsis c999" title="劣势标签">劣势标签：</div>
												<div class="col-xs-8 col-md-7 col-lg-10 text-left no-padding-left">{StaffInferiorNum} <span class="c999">{StaffInferiorDate}</span></div>
											</div>
											<div class="row margintop8">
												<div class="col-xs-4 col-md-3 col-lg-2 text-left no-padding-right textoverflowellipsis c999" title="激励要素">激励要素：</div>
												<div class="col-xs-8 col-md-7 col-lg-10 text-left no-padding-left">{StaffEncourageTitle} <span class="c999">{StaffEncourageDate}</span></div>
											</div>
											<div class="row margintop8">
												<div class="col-xs-4 col-md-3 col-lg-2 text-left no-padding-right textoverflowellipsis c999" title="跟踪日志">跟踪日志：</div>
												<div class="col-xs-8 col-md-7 col-lg-10 text-left no-padding-left">{StaffFollowNum} <span class="c999">{StaffFollowDate}</span></div>
											</div>
										</div>
									</div>
								</td>
								<td class="verticalalignmiddle listoprationstyle">
									<div class="attention row no-margin listopration attentiontext" title="加入关注">加入关注</div>
									<div class="person row no-margin listopration" title="人才剖像">人才剖像</div>
									<div class="pk row no-margin listopration" title="加入对比">加入对比</div>
									<div class="delete row no-margin listopration" title="删除" data-sychron="{StaffSychron}">删除</div>
								</td>
							</tr>

							</tbody>
						</table>
					</div>
					</div>
				</div>
			</div>
			</div>
		</div>
	</div>

	<!-- 添加关键人才 -->
	<div class="modal fade" id="addDialog" role="dialog"
		 aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×
					</button>
					<h4 class="modal-title">
						添加关键人才
					</h4>
				</div>
				<div class="modal-body page-content">
					<div class="row padding10">
						<div class="col-xs-3 col-sm-2 colnopadding margintop5 text-right">姓名：</div>
						<div class="col-xs-9 col-sm-10 colnopadding"><select id="txtKey"></select></div>
					</div>
					<div class="row padding10">
						<div class="col-xs-3 col-sm-2 colnopadding margintop5 text-right">人才分类：</div>
						<div class="col-xs-9 col-sm-10 colnopadding">
							<select id="talentCategory" class="form-control"></select>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button id="btnAddTalent" type="button" class="btn btn-primary">
						确定
					</button>
					<button id="btnCancelTalent" type="button" class="btn btn-default"
							data-dismiss="modal">
						取消
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

	<!-- 删除确定 -->
	<div class="modal fade" id="delDialog" tabindex="-1" role="dialog"
		 aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×
					</button>
					<h4 class="modal-title">
						移除关键人才
					</h4>
				</div>
				<div class="modal-body">
					<div>您确定要将“<span>-</span>”从关键人才库中移除吗？</div>
					<div>删除信息会通知添加者以及“<span>-</span>”的直接上级</div>
				</div>
				<div class="modal-footer">
					<button id="btnOk" type="button" class="btn btn-primary">
						确定
					</button>
					<button id="btnCancel" type="button" class="btn btn-default"
							data-dismiss="modal">
						取消
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	
	<!-- 离职风险 -->
	<div class="modal fade" id="riskDetailModal" tabindex="-1" role="dialog"
     				aria-labelledby="abInfoModalLable" aria-hidden="true">
		    <div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                    &times;
		                </button>
						<h4 class="modal-title bolder" id="abInfoModalLable">离职风险评估</h4>
		            </div>
		            <div class="modal-body">
						<div class="row" id="riskInfo">
			
						</div>
		            </div>
		             <div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		            </div>
		        </div><!-- /.modal-content -->
		    </div><!-- /.modal -->
		</div>
	<script type="text/javascript" src="${jsRoot}require.js"></script>
	<script type="text/javascript" src="${ctx}/assets/js/lib/plugins/echarts/echarts.js"></script>
	<script type="text/javascript" src="${jsRoot}biz/employee/keyTalents.js"></script>
</body>
</html>