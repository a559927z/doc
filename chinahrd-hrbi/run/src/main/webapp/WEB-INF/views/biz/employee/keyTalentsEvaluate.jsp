<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<link rel="stylesheet" href="${ctx}/assets/css/biz/employee/keyTalentsEvaluate.css"/>
	<div class="container">
		<div class="row column">
			<input type="hidden" id="keyTalentId" name="keyTalentId" value="${keyTalentId}" />
			<input type="hidden" id="customerId" name="customerId" value="${customerId}" />
		</div>
		<!-- titile -->
		<div id="toptitle" class="row no-margin paddingtop6 floatleft width-100" data-id="0">
			<div class="col-xs-9 paddingleft12 paddingtop12">
				<span class="marginright18 name title">关键人才跟踪与评价</span>
				<img src="${ctx}/assets/img/talent/talent_person.png">
				<span class="fontsize14 paddingleft12 paddingtop12 " id="empName"></span>
			</div>
			<div class="col-xs-2 backbtn floatRight paddingleft12 paddingtop12 cursorpointer">
				<img src="${ctx}/assets/img/talent/talent_eval_back.png" class="pull-right">
				<span class="fontsize16 marginright18 name title pull-right">返回</span>
			</div>
		</div>
		<div class="autocoretag-follow width-100">
			<div class="autotag width-50 floatLeft bgc243246246">
				<div class="title-follow-left">
				
					<div class="tag-follow">
						<img src="${ctx}/assets/img/talent/talent_tag.png">
					</div>
					<span>自动标签</span>
				</div>
				<div class="content-follow-left">
					<div class="col-xs-12">
						<span>自动标签 : 根据员工档案自动识别的标签</span>
					</div>
					<div class="col-xs-12 Contentlabel" id="contentLabel">
						<div id="more_dis" class="hide more"><a href="javascript:void(0)" id="smoreBtn" class="smore"> 全部 </a></div>
						<div id="reduce_dis" class="hide more"><a href="javascript:void(0)" id="sreduceBtn" class="sreduce"> 隐藏 </a></div>
					</div>
				</div>
			</div>
			<div class="coretag width-48 floatRight bgc243246246 incentiveFactors">
				<div class="title-follow-right  floatRight">
					<div class="tag-follow">
						<img src="${ctx}/assets/img/talent/talent_cup_min.png">
					</div>
					<span>核心激励要素</span>
					<img class="help" src="${ctx}/assets/img/talent/talent_help.png" data-toggle="tooltip" data-placement="bottom" title="核心激励要素为该人才最重要的核心需求点;">
					
	  				<div class="operat-follow-btn floatRight cursorpointer ">
	  					<img src="${ctx}/assets/img/talent/talent_eval_core_edit.png">
	  				</div>
				</div>
				<div class="content-follow-right floatRight labelContent">
					<div class="Contentlabel">
						<div id="incentiveFactors"></div>
						<div id="remarks"><span></span></div>
					</div>
					<div id="operationInformation"><span></span></div>
				</div>
			</div>
		</div>
		<div class="manultag-follow width-100 manualLabel">
			<div class="manultag-main width-50 floatLeft bgc243246246">
				<div class="title-follow-left floatLeft">
					<div class="tag-follow">
						<img src="${ctx}/assets/img/talent/talent_handMake.png">
					</div>
					<span>手工标签</span>
					<img class="help" src="${ctx}/assets/img/talent/talent_help.png" data-toggle="tooltip" data-placement="bottom" title="手工标签分为优势和短板两类，可以添加多个;">
					
				</div>
				<div class="content-follow-left excellentLabelContent">
					<div class="excellent">
						<div class="excellentNum">
							<span>优势</span><span id="excellentLabelNum"></span>个
						</div>
						<div class="addExcellent" id="addExcellentLable">
							<a>添加优势标签</a>
						</div>
					</div>
					<div id="myExcellentLabelContent">
						<div id="myMore_excellent" class="hide more"><a href="javascript:void(0)" id="smoreBtnMy" class="smore"> 展开 </a></div>
						<div id="myReduce_excellent" class="hide more"><a href="javascript:void(0)" id="sreduceBtnMy" class="sreduce"> 隐藏 </a></div>
					</div>
					<div id="othersExcellentLabelContent">
						<div class="hide more"><a href="javascript:void(0)" id="smoreBtnOthers" class="smore"> 展开 </a></div>
						<div class="hide more"><a href="javascript:void(0)" id="sreduceBtnOthers" class="sreduce"> 隐藏 </a></div>
					</div>
				</div>
			</div>
			<div class="manultag width-48 floatRight bgc243246246">
				<div class="title-follow-right  floatRight">
	  				<div class="operat-follow-btn floatRight labelRight cursorpointer ">
	  					<img src="${ctx}/assets/img/talent/talent_eval_manul_sort.png">
	  				</div>
				</div>
				<div class="inferioritylabelContent content-follow-right floatRight">
					<div class="inferiority">
						<div class="inferiorityNum">
							<span>短板</span><span id="inferiorityLableNum"></span>个
						</div>
						<div class="addInferiority" id="addInferiorityLable">
							<a>添加短板标签</a>
						</div>
					</div>
					<div id="myInferiorityLabelContent">
						<div id="myMore_inferiority" class="hide more"><a href="javascript:void(0)" id="imoreBtnMy" class="smore"> 展开 </a></div>
						<div id="myReduce_inferiority" class="hide more"><a href="javascript:void(0)" id="ireduceBtnMy" class="sreduce"> 隐藏 </a></div>
					</div>
					<div id="othersInferiorityLableContent">
						<div id="othersMore_inferiority" class="hide more"><a href="javascript:void(0)" id="imoreBtnOthers" class="smore"> 展开 </a></div>
						<div id="othersReduce_inferiority" class="hide more"><a href="javascript:void(0)" id="ireduceBtnOthers" class="sreduce"> 隐藏 </a></div>
					</div>
				</div>
			<div style="clear: both;"></div>
			</div>
		</div>
		<!-- 头像 -->
		<div class="img-follow width-20">
			
			<div class="clearfix"></div>
		    <div id="circle_bot">  
		    </div>  
		    <div id="circle_bot2">  
		    </div>  
		    <div id="circle_mid">  
		    </div>  
		    <img src="{StaffUrl}" id="circle_top" onerror="this.src='<%=basePath%>/assets/img/man.png'"   class="img-circle" >
		</div>
		<div class="log-follow bgc243246246">
			<div class="title-follow-left" style="">
					<div class="tag-follow">
						<img src="${ctx}/assets/img/talent/talent_log.png">
					</div>
					<span>跟踪日志</span>		
					<img class="help" src="${ctx}/assets/img/talent/talent_help.png" data-toggle="tooltip" data-placement="bottom" title="记录人才的各种情况 如工作，家庭，性格等方面">
					<div class="operat-follow-btn floatRight cursorpointer ">
	  					<img src="${ctx}/assets/img/talent/talent_eval_log_add.png">
	  				</div>
					
			</div>
			<div id="trackingLog" class="content-follow-left" style="width: 90%">
			<div style="clear: both;"></div>
				<div id="logMore" class="hide more"><a href="javascript:void(0)" id="logMoreBtn" class="smore"> 更多 </a></div>
				<div id="logReduce" class="hide more"><a href="javascript:void(0)" id="logReduceBtn" class="sreduce"> 隐藏 </a></div>
				<div class="logconter hide row"><!-- 单个的人才跟踪日志 -->
					<div class="row marginbuttom10">
						<div class="img-circle col-lg-2 col-xs-2 col-md-2" style="border: none;">
							<img id ="empHeadPic" src="{StaffUrl}" onerror="this.src='<%=basePath%>/assets/img/man.png'">
						</div>
						<div class="info-follow col-lg-10 col-xs-8 col-md-8">
							<div class="row">
								<p class="logname">{logname}</p>
							</div>
							<div class="row">
								<p class="logdate">{logdate}</p>
							</div>
						</div>
					</div>
						<div class="encourage-follow floatLeft width-100">
							
							<div class="">
								<p class="logcontent" {workbreak}>{logcontent}</p>
							</div>
						</div>
  						<div class="floatRight">
  						{updatedate}
  						</div>
				</div>
			</div>
			
			<div style="clear: both;"></div>
		</div>
	</div>

	<!-- 手工标签记录 -->
	<div class="modal fade" id="manualLabelDetailModal" tabindex="-1" role="dialog"
		 aria-labelledby="modalLabel" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true"> &times;</span>
					</button>
					<h5 class="modal-title">手工标签记录-<span></span></h5>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-6">
							<div class="row">
								<div class="col-xs-11"><span class="advantageLable">优势</span>标签记录</div>
								<div class="col-xs-11 risk-detail-info">
									<div>
										<table width="100%" border="0" cellpadding="0" cellspacing="0">

										</table>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-6">
							<div class="row">
								<div class="col-xs-11"><span class="shortBoardLable">短板</span>标签记录</div>
								<div class="col-xs-11 suggest-info">
									<div>
										<table width="100%" border="0" cellpadding="0" cellspacing="0">

										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- 添加优势、短板标签 -->
	<div class="modal fade" id="addLabelDetailModal" tabindex="-1" role="dialog"
		 aria-labelledby="modalLabel" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true"> &times;</span>
					</button>
					<h5 class="modal-title"></h5>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-6">
							<div class="row">
								<div class="col-xs-11"><span id="description"></span><br><span>注：可添加多个，用逗号或回车键隔开</span></div>
								<div class="col-xs-11 add-risk-detail-info">
									<div>
										<textarea name="lableName" id="lableName" rows="8" cols="50" class="text"></textarea>
									</div>
								</div>
								<div class="descriptionButton">
									<button id="addDetermine" class="btn btn-primary">确定</button>
									<button id="addCancel" class="btn btn-default" data-dismiss="modal">取消</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 修改核心激励要素 -->
	<div class="modal fade" id="coreLabelDetailModal" tabindex="-1" role="dialog"
		 aria-labelledby="modalLabel" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true"> &times;</span>
					</button>
					<h5 class="modal-title"></h5>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-6">
							<div class="row">
								<div class="col-xs-11"><span id="descriptionCore"></span><br><span></span></div>
								<div id="checkboxCore">

								</div>
								<div class="descriptionButton">
									<button id="updateDetermine" class="btn btn-primary">确定</button>
									<button id="updateCancel"  class="btn btn-default" data-dismiss="modal">取消</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 增加、修改跟踪日志 -->
	<div class="modal fade" id="logLabelDetailModal" tabindex="-1" role="dialog"
		 aria-labelledby="modalLabel" aria-hidden="true">
		<div class="modal-dialog" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true"> &times;</span>
					</button>
					<h5 class="modal-title"></h5>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-6">
							<div class="row">
								<div class="col-xs-11"><span id="descriptionLog"></span><br><span>如：个人诉求、对团队的诉求等信息跟踪</span></div>
								<div class="col-xs-11 add-risk-detail-info">
									<div>
										<textarea name="increase" id="increase" rows="8" cols="50" class="text"></textarea>
									</div>
								</div>
								<div class="descriptionButton">
									<button id="increaseDetermine" class="btn btn-primary">确定</button>
									<button id="increaseCancel"  class="btn btn-default" data-dismiss="modal">取消</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

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
						删除确认对话框
					</h4>
				</div>
				<div class="modal-body">
					<div>您确定要删除<span>-</span>吗？</div>
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
<script src="${jsRoot}lib/echarts/echarts.js"></script>
<script src="${jsRoot}biz/employee/keyTalentsEvaluate.js?v_20170510"></script>
