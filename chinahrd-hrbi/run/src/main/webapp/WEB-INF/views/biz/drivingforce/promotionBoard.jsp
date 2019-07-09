<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <link rel="stylesheet" href="${ctx}/assets/css/jqueryui/jquery-ui.min.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/font-awesome/font-awesome.min.css"/>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>晋级看板</title>
	<link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/select2/select2.min.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/drivingforce/promotionBoard.css"/>
</head>
<body>
<input id="maxDate" type="hidden" value="${maxDate}">
<input id="minDate" type="hidden" value="${minDate}">
<input id="conditionProp" type="hidden" value="${conditionProp}">
<div class="promote-board" id="promoteboard">
    <div class="leftBody">
        <div class="leftListBigDiv">晋级看板</div>
        <div page="page-one" class="leftListDiv selectList">晋级速度及轨迹</div>
        <div page="page-two" class="leftListDiv">人员晋级状态</div>
        <div page="page-three" class="leftListDiv">晋级薪酬变化模拟器</div>
    </div>

    <div class="rightBody rightBody10">
        <div id="page-one" class="rightBodyPage">
            <div id="applicationConformityNum" class="col-sm-3 ct-22 ct-line-col">
                <div class="top-div">
                    <div class="index-common-title">
                        <div class="index-common-title-left"></div>
                        <div class="index-common-title-text">符合条件</div>
                    </div>
                    <div class="body-div">
                        <div class="data loadingtext">数据加载中</div>
                        <div class="data hide">
                            <div class="accord-yj-float" style="margin: 0 auto">
                                <span class="accord-yj-float-value">0</span>
                                <span class="accord-yj-float-people">人</span>
                            </div>
                            <div class="accord-yj-float-datetime"></div>
                            <div class="index-yj-div-bottom">
                                <div class="index-yj-div-bottom-value pull-left"></div>
                                <div class="index-yj-div-bottom-right pull-right"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="applicationUnConformityNum" class="col-sm-3 ct-22 ct-line-col">
                <div class="top-div">
                    <div class="index-common-title">
                        <div class="index-common-title-left"></div>
                        <div class="index-common-title-text">符合条件未申请</div>
                    </div>
                    <div class="body-div">
                        <div class="data loadingtext">数据加载中</div>
                        <div class="data hide">
                            <div class="accord-yj-float" style="margin: 0 auto">
                                <span class="accord-yj-float-value">0</span>
                                <span class="accord-yj-float-people">人</span>
                            </div>
                            <div class="accord-yj-float-datetime"></div>
                            <div class="index-yj-div-bottom">
                                <div class="index-yj-div-bottom-value pull-left"></div>
                                <div class="index-yj-div-bottom-right pull-right"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="applicationSomeConformityNum" class="col-sm-3 ct-22 ct-line-col">
                <div class="top-div">
                    <div class="index-common-title">
                        <div class="index-common-title-left"></div>
                        <div class="index-common-title-text">部分符合条件
                        <li class="icon-question-sign" data-html='true' data-toggle="tooltip" title="说明：<br><li>符合条件70.0%以上人员</li><li>包含申请和未申请人员</li><li>不包含符合条件人员</li>"></li></div>
                    </div>
                    <div class="body-div">
                        <div class="data loadingtext">数据加载中</div>
                        <div class="data hide">
                            <div class="accord-yj-float" style="margin: 0 auto">
                                <span class="accord-yj-float-value">0</span>
                                <span class="accord-yj-float-people">人</span>
                            </div>
                            <div class="accord-yj-float-datetime"></div>
                        </div>
                    </div>
                </div>
            </div>


            <div class="col-sm-3 ct-34 ct-line-col">
                <input id="quotaId" type="hidden" value="${quotaId}">
                <div class="top-div" id="timeLine"></div>
            </div>

            <div class="col-sm-12 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">晋级速度</div>
                </div>
                <div class="bottom-div bottom-div-two borderbottom0 borderright0">
                    <div class="row">
                        <div class="col-md-6 col-sm-12 promoteSpeed height440">
                            <div class="fontMainMargin">趋势分析</div>
                            <div class="paddingbottom0">
                                <div id="promoteTrendSelect" class="fontChildMargin"></div>
                                <div id="promoteTrend" class="content"></div>
                            </div>
                        </div>
                        <div class="col-md-6 col-sm-12 promoteSpeed height440">
                            <div class="fontMainMargin">下级组织分析</div>
                            <div class="col-xs-12" style="width: 100%; text-align: center;height: 30px;line-height: 30px;">
                            <a class="hide" id="returnOrgan">
                                <label class="hide returnOrganText" style="margin-bottom: -10px;margin-left:30px;">返回上级</label>
                                <!-- <label class="hide returnTeamText">返回团队</label> -->
                            </a>
                            </div>
                            <div class="paddingbottom0">
                                <div id="promoteSubOrgSelect" class="fontChildMargin"></div>
                                <div id="promoteSubOrg" class="content"></div>
                            </div>
                        </div>
                        <div class="col-md-6 col-sm-12 promoteSpeed height440">
                            <div class="fontMainMargin">序列统计</div>
                            <div class="paddingbottom0">
                                <div id="promoteSequenceSelect" class="fontChildMargin"></div>
                                <div id="promoteSequence" class="content"></div>
                            </div>
                        </div>
                        <div class="col-md-6 col-sm-12 promoteSpeed height440">
                            <div class="fontMainMargin">人群统计</div>
                            <div class="paddingbottom0">
                                <div id="promoteCrowdSelect" class="fontChildMargin"></div>
                                <div id="promoteCrowdCatagory" class="clearfix">
                                    <div class="pull-left unselectable selected" data-categoryId="0">关键人才</div>
                                    <div class="pull-left unselectable" data-categoryId="1">绩效</div>
                                </div>
                                <div id="promoteCrowd" class="content"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-12 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">晋级轨迹</div>
                </div>
                <div class="bottom-div bottom-div-two borderbottom0">
                    <div class="row">
                        <div class="col-md-6 col-sm-12 promoteTrack">
                            <div class="pthead clearfix">
                                <div class="name">已选对比人员</div>
                                <div class="value">
                                    <%--<span class="unselectable">刘宇凡<i data-id="0" class="icon-remove-sign" title="删除"></i></span>--%>
                                    <%--<span class="unselectable">张漠然<i data-id="1" class="icon-remove-sign" title="删除"></i></span>--%>
                                </div>
                            </div>
                            <div class="ptbody" id="promoteTrackChart" style="height:479px"></div>
                        </div>
                        <div class="col-sm-6 promoteTrack">
                          <div class="pthead clearfix" style="margin-top:20px">
         				     <div class="dis-search">
                              <input class="dis-search-input" id="promoteTrackSelect" type="text" placeholder="输入员工姓名" >
                              <div class="add-on dis-search-input-btn" id="promoteTrackSearch">搜索</div>
                             </div>   
                          </div>
                          	 <div class="note">操作说明：点击“对比轨迹”添加到晋级轨迹对比，最多添加5人。</div>
                            <div class="ptbody" id="promoteTrack" style="height:460px;margin-top:5px;">
                                <table id="promoteTrackGrid"></table>
                                <table id="promoteTrackGridPage"></table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        
           <div id="page-two" class="rightBodyPage">
            <div class="col-sm-12 ct-line-col SetUpBody" id="promoteStatus">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">人员晋级状态</div>
                </div>
                <div class="bottom-div bottom-div-three">

                    <div class="col-sm-12 ct-line-col conditionBtnListBody" id="searchBox">
                        <div class="dis-search">
                            <input class="dis-search-input" id="promoteStatusText" type="text" placeholder="输入员工姓名" >
                            <div class="add-on dis-search-input-btn" id="promoteStatusSearch">搜索</div>
                        </div>
                         
                         <div class="pull-left selectMore" style="margin-left:25px;margin-top:10px;">
                        <span>更多筛选条件<i class="icon-chevron-down"></i></span>
                        <span class="hide">精简筛选条件<i class="icon-chevron-up"></i></span>
                       </div>
                    </div>
                    
				 <div class="moreSearch hide" style="padding-left: 10px;">
                    <div class="org clearfix">
                        <label class="name orgName">组织架构：</label>
                        <span id="organization"></span>
                    </div>
                    <div>
                        <label class="name">职级：</label>
                        <select id="positionRank">
                            <option value="0">当前职级</option>
                            <option value="1">目标职级</option>
                        </select>
                        <input type="text" id="positionRankText" placeholder="请输入职级" style="width:100px;text-align:left;"/>
                    </div>
                    <div>
                        <label class="name">条件：</label>
                        <select id="condition">
                            <option value="-1">全部</option>
                            <option value="0">全部符合条件</option>
                            <option value="1">部分符合条件</option>
                        </select>
                        <em>
                            <input id="conditionBegin" type="text"/><label>&nbsp;% - </label>
                            <input id="conditionEnd" type="text"/>
                            <label>%</label>
                        </em>
                    </div>
                    <div>
                        <span class="name"></span>
                        <button id="promoteStatusSearchMore" type="button">搜索</button>
                        <button id="promoteStatusClear" class="msclear" type="button">清除</button>
                    </div>
                </div>
                
                    <div class="table-body" style="padding-left: 10px;padding-bottom:10px">
                        <div class="clearfix"></div>
                        <div  id="proStatus" style="padding-top:14px;">
                            <table class="borderless" id="promoteStatusGrid"></table>
                            <table id="promoteStatusGridPage"></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="page-three" class="rightBodyPage">
            <div id="salarySimulation">
                <div class="sstitle clearfix">
                    <div class="pull-left">
                        <span>晋级薪酬模拟器</span>
                        <i class="icon-question-sign" data-html='true' data-toggle="tooltip" title="说明：<br><li>默认统计符合条件人员的薪酬变化，您可选择不同晋级人员查看薪酬变化情况；</li><li>晋级后薪酬默认为职级的平均薪酬，您可自定义编辑；</li>"></i>
                    </div>
                    <div class="pull-right">
                        <a id="setttingRank" href="javascript:;">设置职级</a>
                    </div>
                </div>
                <div class="ssbody">
                    <table id="eligibilityGrid">
                        <thead>
                        <tr>
                            <th class="cell1">职级</th>
                            <th class="cell2">符合条件人员</th>
                            <th class="cell3">部分符合条件人员</th>
                            <th class="cell4">小计<br><span class="colorccc">当前职级新增薪酬</span></th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td class="cell" colspan="4">
                                <div id="eligibilityTotal">
                                    预计新增薪酬<span>...</span>万元<label>（当前选中<em>...</em>人）<label></label></div>
                            </td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


<!--遮罩层 begin-->
<div class="shade"></div>
<!--遮罩层 end-->

<!-- 符合条件人员 -->
<div class="modal fade" id="applicationConformityModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="eligibilityAll">
                    符合条件人员
                </h4>
            </div>

            <div class="modal-body page-content" id="acm">
                <div class="content clearfix">
                    <div class="acmselect">
                        <div class="acmselect1 clearfix">
                            <div class="pull-left unselectable selected" data-index="0">全部</div>
                            <div class="pull-left unselectable" data-index="1">已申请</div>
                            <div class="pull-left unselectable" data-index="2">未申请</div>
                        </div>
                    </div>
                    <div class="acmbody" style="padding:18px;">
                        <div id="acmtab0" class="acmtab">
                            <table id="applicationConformityGrid0"></table>
                            <table id="applicationConformityGridPage0"></table>
                        </div>
                        <div id="acmtab1" class="acmtab hide">
                            <table id="applicationConformityGrid1"></table>
                            <table id="applicationConformityGridPage1"></table>
                        </div>
                        <div id="acmtab2" class="acmtab hide">
                            <table id="applicationConformityGrid2"></table>
                            <table id="applicationConformityGridPage2"></table>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 下级组织分析 弹出框-->
<div class="modal fade popup-modal" id="applicationOrganModal" role="dialog" aria-labelledby="applicationOrganLabel" aria-hidden="true" data-backdrop="static" >
    <div class="modal-dialog" style="width:735px;">
        <div class="modal-content">
            <div class="modal-header">
            	<div class="modal-header-text">个人晋级速度</div>
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
            </div>
			<input type="hidden" id="applicationOrganModal-childOrId"/>
            <div class="modal-body" id="acmOrgan">
               <div class="acmbody ">
                   <div id="acmtabOrgan" class="acmtab clearfix">
                       <table id="applicationOrganGrid0" ></table>
                       <table id="applicationOrganGridPage0"></table>
                   </div>
               </div>
            </div>
        </div>
    </div>
</div>

<!-- 薪酬变化模拟器向导 -->
<div class="modal fade" id="messageDialog" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
                </button>
                <h4 class="modal-title">
                    提示信息
                </h4>
            </div>

            <div class="modal-body page-content">
                <div class="content clearfix">
                    <div style="padding:18px;">当前页面的选择数据将会丢失，请问确定离开吗？</div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="msgConfirm">
                    确认
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>

<!-- 薪酬变化模拟器向导 -->
<div class="modal fade" id="emulatorModal" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true">
    <div class="modal-dialog"  style="width:825px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
                </button>
                <h4 class="modal-title">
                    薪酬变化模拟器向导
                </h4>
            </div>

            <div class="modal-body page-content">
                <div class="content clearfix" style="padding:27px;margin-top:-8px;">
                    <div>薪酬变化模拟器帮助您计算各职级待晋升人员带来的薪酬总额变化。请选择您要模拟的职级（最多10个）：</div>
                    <div id="emulator" style="margin-top:10px;"></div>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="emSubmit">提交 (<span>0</span>)</button>
            </div>

        </div>
    </div>
</div>

<script src="${jsRoot}lib/echarts/echarts.js"></script>
<script src="${jsRoot}biz/drivingforce/promotionBoard.js"></script>
</body>
</html>
