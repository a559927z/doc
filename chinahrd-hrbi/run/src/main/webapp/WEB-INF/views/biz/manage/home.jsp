<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>首页</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/manageHome.css"/>
</head>
<body>
<div class="page-content" id="main-container">
    <div class="main-container-inner">
        <div class="row column">
            <div class="col-xs-8">
                <ul class="ztree" id="tree"></ul>
            </div>
            <div class="col-xs-4">
                <div class="update-time">更新时间：&nbsp;<span id="updateTime"><fmt:formatDate value="${updateDate}" pattern="yyyy/MM/dd HH:mm"></fmt:formatDate></span></div>
            </div>
            <input type="hidden" id="reqOrganId" value="${reqOrganId}">
            <input type="hidden" id="reqOrganName" value="${reqOrganName}">
            <input type="hidden" id="entryAnnual" value="${entryAnnual}">
        </div>
		<div id="showWarningModal" class="modal fade" data-backdrop="static" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <input type="hidden" id="searchIndex" >
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title">风险详情</h3>
                </div>
                <div class="modal-body">
                    <div class="row">
                       <div class="col-md-12 col-xs-12">
			                <ul class="nav nav-tabs f-white-bg" role="tablist" id="dimissTypeTabs">
			                    <li role="presentation" class="active">
			                        <a href="#runOffWarnTab" aria-controls="runOffWarnTab" role="tab" data-toggle="tab">离职风险预警</a>
			                    </li>
			                    <li role="presentation">
			                        <a href="#performanceWarnTab" aria-controls="performanceWarnTab" role="tab" data-toggle="tab">绩效风险预警</a>
			                    </li>
			                    <li role="presentation">
			                        <a href="#workLifeWarnTab" aria-controls="workLifeWarnTab" role="tab" data-toggle="tab">工作生活平衡欠佳</a>
			                    </li>
			                </ul>
			
			                <!-- Tab panes -->
			                <div class="tab-content">
			                 <!-- 离职风险预警 -->
			                    <div role="tabpanel" class="tab-pane active" id="runOffWarnTab">
			                        <div class="row">
			                      	  <span class="span_note">注：点击头像或姓名可查看离职风险分析</span>
			                            <!-- 员工基本信息 -->
										<div class="row base-info">
											<div class="col-xs-12 " id="riskEmpDetail">
										
											</div>
										</div>
						
			                        </div>
			                    </div>
			                    <!-- 绩效风险预警 -->
			                    <div role="tabpanel" class="tab-pane" id="performanceWarnTab">
			                        <div class="row">
			                        	  <!-- 连续高绩效未晋升 -->
			                            <div class="text" id="u827">
                      						<p><span style="font-family:'Applied Font Regular', 'Applied Font';font-weight:400;color:#000000;">连续高绩效未晋升（</span>
                      						<span id="highNum" style="font-family:'Applied Font Bold', 'Applied Font';font-weight:700;color:#FF6600;">3</span>
                      						<span style="font-family:'Applied Font Regular', 'Applied Font';font-weight:400;color:#000000;">人）</span>
                      						</p>
                   						 </div>
                   						  <div class="tab-pane col-md-12 col-sm-12 col-xs-12" id="highPerformanceArea">
                   						 	<table  class="borderless" id="highPerformanceGrid"></table>
                   						 </div>
                   						   <!-- 连续低绩效未调整 -->
                   						 <div class="text" id="u827">
                      						<p><span style="font-family:'Applied Font Regular', 'Applied Font';font-weight:400;color:#000000;">连续低绩效未调整（</span>
                      						<span id="lowNum"  style="font-family:'Applied Font Bold', 'Applied Font';font-weight:700;color:#FF6600;">3</span>
                      						<span style="font-family:'Applied Font Regular', 'Applied Font';font-weight:400;color:#000000;">人）</span></p>
                   						 </div>
                   						 <div class="tab-pane col-md-12 col-sm-12 col-xs-12" id="lowPerformanceArea">
                   						 	<table  class="borderless" id="lowPerformanceGrid"></table>
			                        	 </div>
			                         </div>
			                    </div>
			
			                    <!-- 工作生活平衡欠佳 -->
			                    <div role="tabpanel" class="tab-pane" id="workLifeWarnTab">	
			                       <div class="row">
			                       		<span class="span_note">注：点击头像或姓名可查看加班趋势</span>
			                            <!-- 员工基本信息 -->
										<div class="row base-info">
											<div class="col-xs-12 " id="workLifeDetail">

											</div>
										</div>
						
			                        </div>
			                    </div>
			                </div>
			            </div>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>	
        <div class="row column">
            <div class="tab-pane col-sm-4 col-xs-12">
                <div class="widget-box transparent f-white-bg">
                    <div class="widget-header widget-header-flat">
                        <h4 class="bolder"><i class="static_icon"></i>预警</h4>
                    </div>
                    <div class="widget-body">
                        <ul class="wran-ul" style="cursor: pointer;">
                            <li aria-controls="runOffWarnTab"><span  aria-controls="runOffWarnTab">0</span>&nbsp;人可能存在离职风险</li>
                            <li aria-controls="performanceWarnTab" sign="performanceHighWarnTab"><span aria-controls="performanceWarnTab">0</span>&nbsp;人连续高绩效未晋升</li>
                            <li aria-controls="performanceWarnTab" sign="performanceLowWarnTab"><span aria-controls="performanceWarnTab">0</span>&nbsp;人连续低绩效未调整</li>
                            <li aria-controls="workLifeWarnTab"><span aria-controls="workLifeWarnTab">0</span>&nbsp;人工作生活平衡欠佳</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="tab-pane col-sm-4 col-xs-12">
                <div class="widget-box transparent f-white-bg">
                    <div class="widget-header widget-header-flat">
                        <h4 class="bolder"><i class="static_icon"></i>团队画像</h4>
                        <div class="widget-toolbar">
                            <span class="green">共<span id="totalPer">0</span>人</span>
                        </div>
                    </div>
                    <div class="widget-body">
                    	<div class="row">
							<div class="col-xs-12 teamImg-head">
								
									<div id="manPer" class="">
										
									</div>
							
									<div id="yearsMaster" class="">
										
									</div>
								
									<div id="unIsMarry" class="">
										
									</div>
							
							</div>
							
						</div>
						
                    	<div class="row">
							<div class="col-xs-12">
								<a id="toTeamImgView" href="javascript:void(0);">
									<div id="teamImg-funel"></div>
<%-- 									<img src="${ctx}/assets/img/manage/teamImg-title.png" class="teamImg-title"></img> --%>
								</a>
							</div>
						</div>
						
                    </div>
                </div>
            </div>
            <div class="tab-pane col-sm-4 col-xs-12">
                <div class="widget-box transparent f-white-bg">
                    <div class="widget-header widget-header-flat">
                        <h4 class="bolder"><i class="static_icon"></i>团队提醒</h4>
                        <div class="widget-toolbar">
                            <span class="green">本月</span>
                        </div>
                    </div>
                    <div class="widget-body" id="remindBody">
                        <ul class="remind-ul">
                            <li>
                                <div class="bolder">生日<span class="normal">（<span class="red" id="currMonthNum">0</span>人）</span></div>
                                <div class="hide" id="currBirth"></div>
                                <div class="hide" id="currMonthTxt"></div>
                            </li>
                            <li>
                                <div class="bolder">入司周年<span class="normal">（<span class="red" id="entryYearNum">0</span>人）</span></div>
                                <div class="hide" id="entryYearTxt"></div>
                            </li>
							<!-- TODO 这块产品要求先不做
                            <li>
                                <div class="bolder">可晋升晋级<span class="normal">（<span class="red" id="promotionNum">3</span>人）</span></div>
                                <div class="hide" id="promotionTxt">张宇硕、李华、张琳可能存在晋升晋级资格</div>
                            </li>
							-->
							
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <div class="row column">
            <div class="tab-pane col-sm-6 col-xs-12">
                <div class="widget-box transparent f-white-bg">
                    <div class="widget-header widget-header-flat">
                        <h4 class="bolder"><i class="static_icon"></i>当季人才损益</h4>
                    </div>
                    <div class="widget-body" id="gainOfLossBody">
                        <div class="gainOfLoss-row clearfix">
                            <h4 class="bolder">人才损益值：<span class="light-blue" id="gainOfLossNum">+1</span>人</h4>
                            <ul class="gainOfLoss-ul">
                                <li>
                                    <div class="bolder">编制情况</div>
                                    <div class="row">
                                        <div class="col-xs-3">编制数：<span class="bolder" id="compileNum">0</span></div>
                                        <div class="col-xs-9">可用编制数：<span class="bolder" id="usableCompileNum">0</span></div>
                                    </div>
                                </li>
                                <li>
                                    <div class="bolder">人才损益</div>
                                    <div class="row">
                                        <div class="col-xs-3">入职：<span class="bolder" id="entryEmpsNum">0</span></div>
                                        <div class="col-xs-3">调入：<span class="bolder" id="callinEmpsNum">0</span></div>
                                        <div class="col-xs-3">离职：<span class="bolder" id="dimissionEmpsNum">0</span></div>
                                        <div class="col-xs-3">调出：<span class="bolder" id="calloutEmpsNum">0</span></div>
                                    </div>
                                </li>
                                <li>
                                    <div class="bolder">招聘进程</div>
                                    <div class="row">
                                        <div class="col-xs-3">发布职位数：<span class="bolder" id="publiceJobNum">0</span></div>
                                        <div class="col-xs-3">简历数：<span class="bolder" id="resumeNum">0</span></div>
                                        <div class="col-xs-3">应聘数：<span class="bolder" id="acceptNum">0</span></div>
                                        <div class="col-xs-3">offer数：<span class="bolder" id="offerNum">0</span></div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-pane col-sm-6 col-xs-12">
                <div class="widget-box transparent f-white-bg">
                    <div class="widget-header widget-header-flat" >
                    	<h4 class="bolder" id="subOrgInfoTitle"><i class="static_icon"></i>下属组织信息</h4>
                        <div class="widget-toolbar">
                            <a href="#" target="_blank" id="gotoOrgView">组织架构图</a>
                        </div>
                    </div>
                    <div class="widget-body" id="treegridbody">
                    	<table class="borderless" id="treegrid"></table>
                    </div>
                </div>
            </div>
        </div>

        <div class="row column">
            <div class="tab-pane col-sm-6 col-xs-12">
                <div class="widget-box transparent f-white-bg">
                    <div class="widget-header widget-header-flat">
                        <h4 class="bolder"><i class="static_icon"></i>绩效目标</h4>
                    </div>
                    <div class="widget-body" style="height:480px;">
                        <div class="performance">
                            <div class="tab">
                                <div class="title on" data-index="dep">部门绩效</div>
                                <div class="title" data-index="sub">下属绩效</div>
                                <div class="more">
                                    <a href="#" id="toPerChangeView">过往绩效</a>
                                </div>
                            </div>
                            <div class="body">
                                <table id="performance_dep">
                                    <thead>
                                        <tr>
                                            <th>考核内容</th>
                                            <th class="center width20">权重</th>
                                        </tr>
                                    </thead>
                                    <tbody></tbody>
                                </table>
                                <table id="performance_sub">
                                    <thead>
                                        <tr>
                                            <th class="width20">姓名</th>
                                            <th class="width30">考核内容</th>
                                            <th class="center width10">权重</th>
                                            <th>IDP</th>
                                        </tr>
                                    </thead>

                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-pane col-sm-6 col-xs-12">
                <div class="widget-box transparent f-white-bg">
                    <div class="widget-header widget-header-flat">
                        <h4 class="bolder"><i class="static_icon"></i>人才发展</h4>
                    </div>
                    <div class="widget-body" id="talentDevelBody">
						<div class="row">
							<div class="col-xs-12" >
								<!-- <div style = "height:200px; overflow:auto;"> -->
								<div style = "height:320px; overflow:auto;">
									<div id="talentDevel-bar" ></div>
								</div>
							</div>
						</div>
						
                    	<div class="row">
							<div class="col-xs-3 talentDevel">
								
								<div class="talentDevel-bottom" id="talentDevel-pie1"></div>
								<div class="txtPos backColor" id = "txtPos1">
									<span class="txtPos-l pie-v1"></span><span class="txtPos-r">人</span>
								</div>
							</div>
							<div class="col-xs-3 talentDevel" >
								<div class="talentDevel-bottom" id="talentDevel-pie2"></div>
								<div class="txtPos whiteColor"><span id = "pie-v2" class="txtPos-l pie-v2"></span><span class="txtPos-r">月</span></div>
								
							</div>
							<div class="col-xs-3 talentDevel">
								<div class="talentDevel-bottom" id="talentDevel-pie3"></div>
								<div class="txtPos backColor"><span id = "pie-v3" class="txtPos-l pie-v3"></span><span class="txtPos-r">人</span></div>
								
							</div>
							<div class="col-xs-3 talentDevel">
								<div class="talentDevel-bottom" id="talentDevel-pie4"></div>
								<div class="txtPos whiteColor"><span id = "pie-v4" class="txtPos-l pie-v4"></span><span class="txtPos-r">次</span></div>
							</div>
						</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 团队提醒 -->
<div class="modal fade" id="remindModal" tabindex="-1" role="dialog"
     aria-labelledby="remindModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title bolder" id="remindModalLabel">团队提醒</h4>
            </div>
            <div class="modal-body">
                <div class="p-grid-title">
                    <span class="birth-img">&nbsp;</span>
                    <span class="font-title">团队生日榜</span>
                </div>
                <div class="l-td-birthday" id="dirthday_dis">
                    <table width="750" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td width="141" align="center" valign="middle" class="bg_default"><span class="white">一月</span></td>
                            <td width="141" align="center" valign="middle" class="bg_default"><span class="white">二月</span></td>
                            <td width="141" align="center" valign="middle" class="bg_default"><span class="white">三月</span></td>
                            <td width="141" align="center" valign="middle" class="bg_default"><span class="white">四月</span></td>
                            <td width="141" align="center" valign="middle" class="bg_default"><span class="white">五月</span></td>
                            <td width="141" align="center" valign="middle" class="bg_default"><span class="white">六月</span></td>
                            <td width="141" align="center" valign="middle" class="bg_default"><span class="white">七月</span></td>
                            <td width="141" align="center" valign="middle" class="bg_default"><span class="white">八月</span></td>
                            <td width="141" align="center" valign="middle" class="bg_default"><span class="white">九月</span></td>
                            <td width="141" align="center" valign="middle" class="bg_default"><span class="white">十月</span></td>
                            <td width="141" align="center" valign="middle" class="bg_default"><span class="white">十一月</span></td>
                            <td width="141" align="center" valign="middle" class="bg_default"><span class="white">十二月</span></td>
                        </tr>
                        <tr>
                            <td align="center" valign="top" class="bg_default_name"></td>
                            <td align="center" valign="top" class="bg_default_name"></td>
                            <td align="center" valign="top" class="bg_default_name"></td>
                            <td align="center" valign="top" class="bg_default_name"></td>
                            <td align="center" valign="top" class="bg_default_name"></td>
                            <td align="center" valign="top" class="bg_default_name"></td>
                            <td align="center" valign="top" class="bg_default_name"></td>
                            <td align="center" valign="top" class="bg_default_name"></td>
                            <td align="center" valign="top" class="bg_default_name"></td>
                            <td align="center" valign="top" class="bg_default_name"></td>
                            <td align="center" valign="top" class="bg_default_name"></td>
                            <td align="center" valign="top" class="bg_default_name"></td>
                        </tr>
                        <tr id="more_dis" class="hide">
                            <td colspan="12" align="center" valign="middle" ><a href="javascript:void(0)" id="smoreBtn" class="smore"> 更多 ↓</a></td>
                        </tr>
                        <tr id="reduce_dis" class="hide">
                            <td colspan="12" align="center" valign="middle" ><a href="javascript:void(0)" id="sreduceBtn" class="sreduce"> 收起 ↑</a></td>
                        </tr>
                    </table>
                </div>
                <div class="p-grid-title mt30 hide">
                    <span class="font-title"><i class="static_icon"></i>晋升晋级提醒人员</span>
                </div>
                <div class="row hide">
                    <div class="col-xs-12" id="promotionTable">
                        <table class="borderless" id="promotionGrid"></table>
                    </div>
                </div>
                <div class="clearfix mt30"></div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!-- 当季人才损益 -->
<div class="modal fade" id="gainOfLossModal" tabindex="-1" role="dialog"
     aria-labelledby="gainOfLossModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title bolder" id="gainOfLossModalLabel">人才损益</h4>
            </div>
            <div class="modal-body">
                <ul class="nav nav-tabs f-white-bg" role="tablist" id="gainOfLossTabs">
                    <li role="presentation" class="active">
                        <a href="#changeEmpTab" aria-controls="changeEmpTab" role="tab" data-toggle="tab">异动人员</a>
                    </li>
                    <li role="presentation">
                        <a href="#teamEmpTab" aria-controls="teamEmpTab" role="tab" data-toggle="tab">
                            团队成员
                        </a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="changeEmpTab">
                        <div class="col-xs-12">
                            <h5><i class="static_icon"></i>入职（<span class="red" id="entryEmpsNum2">0</span>人）</h5>
                        </div>
                        <div class="col-xs-12" id="entryEmpsTable">
                            <table class="borderless" id="entryEmpsGrid"></table>
                        </div>
                        <div class="col-xs-12">
                            <h5><i class="static_icon"></i>调入（<span class="red" id="callinEmpsNum2">0</span>人）</h5>
                        </div>
                        <div class="col-xs-12" id="callinEmpsTable">
                            <table class="borderless" id="callinEmpsGrid"></table>
                        </div>
                        <div class="col-xs-12">
                            <h5><i class="static_icon"></i>离职（<span class="red" id="dimissionEmpsNum2">0</span>人）</h5>
                        </div>
                        <div class="col-xs-12" id="dimissionEmpsTable">
                            <table class="borderless" id="dimissionEmpsGrid"></table>
                        </div>
                        <div class="col-xs-12">
                            <h5><i class="static_icon"></i>调出（<span class="red" id="calloutEmpsNum2">0</span>人）</h5>
                        </div>
                        <div class="col-xs-12" id="calloutEmpsTable">
                            <table class="borderless" id="calloutEmpsGrid"></table>
                        </div>
                    </div>

                    <div role="tabpanel" class="tab-pane" id="teamEmpTab">
                        <div class="col-xs-12" id="teamEmpTable">
                            <table class="borderless" id="teamEmpGrid"></table>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 离职风险预警 tooltip -->
<div class="row tooltipModal" id="runoffInfo">
		<div class="row base-info">
			<div class="col-xs-12 ">
				<div class="row">
					<!-- 左边头像 -->
					<div class="col-xs-2 ">
						<img class="img-circle head-pic" src="">
					</div>
					<!-- 右边内容 -->
					<div class="col-xs-10">
						<div class="row">
							<div class="col-xs-12">
								姓名 : <span></span>
							</div>
							<div class="col-xs-12">
								离职风险 : <span class="riskFlag"></span>
							</div>
							<div class="col-xs-12">
								状态标签 : <span></span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="row">
					<div class="col-xs-11 ">离职风险详情信息:</div>
					<div class="col-xs-11 risk-detail-info">
						<div></div>
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="row">
					<div class="col-xs-11">BP建议:</div>
					<div class="col-xs-11 suggest-info">
						<div>&nbsp;</div>
					</div>
				</div>
			</div>
		</div>
</div>

<!-- 工作生活 tooltip -->
<div class="row tooltipModal" id="workLifeInfo">
		<div class="workOvertime_h1">
			<p><span class="span_note">近两周加班趋势</span></p>
		</div>
		<div class="workOvertime_h2">
			<p class="span_note"><span  style="margin-left: 30px;">加班时长</span>
			<span style="margin-left: 30px;font-weight: 400;">合计</span>
			<span style="font-weight: 400;" id="total"></span>
			<span style="font-weight: 400;">小时</span>
			</p>
		</div>
		 <div class="tab-pane col-md-12 col-sm-12 col-xs-12" id="workLifeArea">
			<div class="col-md-12 col-sm-12 col-xs-12" style="height: 300px;" id="workLifeChart" ></div>
		</div>
</div>

<!-- 下属组织机构信息-->
<div class="modal fade" id="orgTreeGridModal" tabindex="-1" role="dialog"
     aria-labelledby="orgTreeGridModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title bolder" id="remindModalLabel">组织架构</h4>
            </div>
            <div class="modal-body" >
<!--             		<input type="checkbox" id="isShowParent" />显示父架构节点 -->
 					<table class="borderless" id="modalTreeGrid"></table>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!-- 	<div id="showPerformanceWarningModal" class="modal fade" data-backdrop="static" tabindex="-1" role="dialog"> -->
<!--         <div class="modal-dialog" style="width:1250px;" > -->
<!--             <div class="modal-content"> -->
<!--                 <div class="modal-header"> -->
<!--                     <input type="hidden" id="searchIndex" > -->
<!--                     <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
<!--                     <h3 class="modal-title">ccc</h3> -->
<!--                 </div> -->
<!--                 <div class="modal-body"> -->
<!--                     <div class="row" > -->
<!-- <!--                     <div id="showPerformanceWarningDiv"></div> --> 
<!--                     <iframe name="showPerformanceWarningFrm" id="showPerformanceWarningFrm" style="width:1200px;height:800px;overflow-y: scroll;"  scrolling="no" frameborder="0" src="index"></iframe> -->
<!--                     </div> -->
<!--                 </div> -->
<!--             </div>/.modal-content -->
<!--         </div>/.modal-dialog -->
<!--     </div>	 -->


<!-- 人才发展 -->
<div class="modal fade" id="talentDevelModal" tabindex="-1" role="dialog"
     aria-labelledby="talentDevelModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title bolder" id="talentDevelModalLabel">人才发展</h4>
            </div>
            <div class="modal-body">
                <ul class="nav nav-tabs f-white-bg" role="tablist" id="talentDevelTabs">
                    <li role="presentation" id="promoteTabName" class="active">
                        <a href="#promoteTab" aria-controls="promoteTab" role="tab" data-toggle="tab">晋升晋级</a>
                    </li>
                    <li role="presentation" id="trainTabName">
                        <a href="#trainTab" aria-controls="trainTab" role="tab" data-toggle="tab">培训</a>
                    </li>
                    <li role="presentation" id="360ExamTabName">
                        <a href="#360ExamTab" aria-controls="360ExamTab" role="tab" data-toggle="tab">能力测评</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="promoteTab">
                        <div class="col-xs-12">
                            <span><i class="static_icon"></i>当季晋升（<span class="red pie-v1" id="t"></span>人）</span>
                            <span>人均晋升周期（<span class="red pie-v2" id="entryEmpsNum2"></span>月）</span>
                        </div>
                        <div class="col-xs-12" id="promoteTable">
                            <table class="borderless" id="promoteGrid"></table>
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="trainTab">
					    <div class="col-xs-12">
                            <span><i class="static_icon"></i>当季培训（<span class="red pie-v3" id="entryEmpsNum2"></span>人）</span>
                            <span>人均培训次数（<span class="red pie-v4" id="entryEmpsNum2"></span>次）</span>
                        </div>
                        <div class="col-xs-12" id="trainTable">
                          <!--   <table style="width:790px"class="borderless" id="trainGrid"></table> --> 
                            <table class="borderless" id="trainGrid"></table>
                        </div>
                    </div>
					
					<div role="tabpanel" class="tab-pane" id="360ExamTab">
					    <div class="col-xs-12">
                            <span><i class="static_icon"></i>测评时间（<span class="red" id="examDate"></span>年）</span>
                        </div>
                        <div class="col-xs-12" id="360ExamTabTable">
                            <table class="borderless" id="360ExamGrid"></table>
                        </div>
                    </div>
					
                    <div class="clearfix"></div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!-- 人才发展-能力状况 -->
<div class="modal fade" id="abInfoModal" tabindex="-1" role="dialog"
     aria-labelledby="abInfoModalLable" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
				<h4 class="modal-title bolder" id="abInfoModalLable"></h4>
            </div>
            <div class="modal-body">
				<div style="width:350px; height:300px;" id="abilityInfo"></div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>





<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/js/biz/manageHome.js"></script>
</body>
</html>
