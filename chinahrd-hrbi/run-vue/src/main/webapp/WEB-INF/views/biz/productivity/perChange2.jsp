<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>

<html lang="en">
<head>
	<!-- jsp文件头和头部 -->
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>个人绩效及变化</title>
	<link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"  />
	<link rel="stylesheet" href="${ctx}/assets/css/biz/productivity/perChange2.css"/>
</head>
<body>

<div class="per">
<div class="leftBody">
    <div class="leftListBigDiv">员工绩效
        <%--<div class="leftListSet"></div>--%>
    </div>
    <div page="page-one" class="leftListDiv selectList">个人绩效概况</div>
    <div page="page-two" class="leftListDiv">个人绩效明细</div>
    <input type="hidden" id="yearMonths" value="${yearMonths}">
    <input type="hidden" id="perWeek" value="${perWeek}">
</div>
<div class="rightBody rightBody10">
<div id="page-one" class="rightBodyPage">

    <div class="col-sm-2 ct-18 ct-line-col">
        <div class="top-div showview" id="tooltip1" data-toggle="tooltip" data-placement="bottom">
            <div class="index-common-title">
                <div class="index-common-title-left"></div>
                <div class="index-common-title-text">连续2次低绩效</div>
            </div>
            <div class="body-div">
                <div class="accord-yj-float">
                    <span class="accord-yj-float-value red" id="userCount1">0</span>
                    <span class="accord-yj-float-people">人</span>
                </div>
            </div>
            <div class="index-yj-div-bottom size-12">
                <div class="index-yj-div-bottom-value pull-left" id="userNames1">无</div>
                <div class="index-yj-div-bottom-right pull-right"></div>
            </div>
        </div>
    </div>

    <div class="col-sm-2 ct-18 ct-line-col">
        <div class="top-div showview" id="tooltip2" data-toggle="tooltip" data-placement="bottom">
            <div class="index-common-title">
                <div class="index-common-title-left"></div>
                <div class="index-common-title-text">当期低绩效</div>
            </div>
            <div class="body-div">
                <div class="accord-yj-float">
                    <span class="accord-yj-float-value red" id="userCount2">0</span>
                    <span class="accord-yj-float-people">人</span>
                </div>
            </div>
            <div class="index-yj-div-bottom size-12" >
                <div class="index-yj-div-bottom-value pull-left" id="userNames2">无</div>
                <div class="index-yj-div-bottom-right pull-right"></div>
            </div>
        </div>
    </div>

    <div class="col-sm-2 ct-18 ct-line-col">
        <div class="top-div showview" id="tooltip3" data-toggle="tooltip" data-placement="bottom">
            <div class="index-common-title">
                <div class="index-common-title-left"></div>
                <div class="index-common-title-text">连续2次高绩效</div>
            </div>
            <div class="body-div">
                <div class="accord-yj-float">
                    <span class="accord-yj-float-value green" id="userCount3">0</span>
                    <span class="accord-yj-float-people">人</span>
                </div>
            </div>
            <div class="index-yj-div-bottom size-12">
                <div class="index-yj-div-bottom-value pull-left" id="userNames3">无</div>
                <div class="index-yj-div-bottom-right pull-right"></div>
            </div>
        </div>
    </div>

    <div class="col-sm-2 ct-18 ct-line-col">
        <div class="top-div showview" id="tooltip4" data-toggle="tooltip" data-placement="bottom">
            <div class="index-common-title">
                <div class="index-common-title-left"></div>
                <div class="index-common-title-text">当期高绩效</div>
            </div>
            <div class="body-div">
                <div class="accord-yj-float">
                    <span class="accord-yj-float-value green" id="userCount4">0</span>
                    <span class="accord-yj-float-people">人</span>
                </div>
            </div>
            <div class="index-yj-div-bottom size-12">
                <div class="index-yj-div-bottom-value pull-left" id="userNames4">无</div>
                <div class="index-yj-div-bottom-right pull-right"></div>
            </div>
        </div>
    </div>

    <div class="col-sm-4 ct-28 ct-line-col">
        <input id="quotaId" type="hidden" value="${quotaId}">
        <div class="top-div" id="timeLine"></div>
    </div>

    <div class="col-sm-12 ct-line-col SetUpBody" view="chart">
        <div class="index-common-title bottom-title">
            <div class="index-common-title-left bottom-left"></div>
            <div class="index-common-title-text bottom-text">绩效分布</div>
        </div>

        <%--<div class="index-jxmb-tab">--%>
            <%--<select class="per-select" id="selectYM">--%>
                <%--<option>2015年上半年</option>--%>
            <%--</select>--%>
            <%--<div page="chartview-all" class="index-jxmb-btn index-jxmb-btn-select">全部</div>--%>
            <%--<div page="chartview-g" class="index-jxmb-btn">管理者</div>--%>
            <%--<div page="chartview-y" class="index-jxmb-btn">员工</div>--%>
        <%--</div>--%>


        <div class="bottom-div">
            <div id="preDisSelect"></div>
            <div class="col-md-6 col-sm-12 col-xs-12 leftBarArea noPaddingLeft" >
                <div class="col-sm-12">&nbsp;</div>
                <div  class="col-sm-12 preStarBarChar noPaddingLeft" id="preDisChart"></div>
                <div class=" col-sm-12 preDisTextArea">
                    共&nbsp;<span class="preExceptionNum red" id="joinCount">0</span>&nbsp;人参与了本期绩效评估，
                    未参与&nbsp;<span class="preExceptionNum red" id="notJoinCount">0</span>&nbsp;人
                </div>
                <span class="right-instructions">
                    <span class="right-line"></span>
                    <span class="right-arrow"></span>
                </span>
            </div>
            <div class="col-md-6 col-sm-12 col-xs-12 rightPieArea" >
                <div class="col-sm-12" id="rightPieChart"></div>
            </div>
        </div>
    </div>

    <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
        <div class="index-common-title bottom-title">
            <div class="index-common-title-left bottom-left"></div>
            <div class="index-common-title-text bottom-text">绩效结果变化趋势</div>

            <!-- <div class="rightSetUpBtn">
                <div class="rightSetUpBtnDiv rightSetUpLeft icon rightSetUpBtnSelect">
                    <div class="rightSetUpBtnTop"></div>
                    <div class="rightSetUpLeftShowIcon"></div>
                    <div class="rightSetUpLeftHideIcon"></div>
                </div>
                <div class="rightSetUpBtnDiv rightSetUpRight icon">
                    <div class="rightSetUpBtnTop"></div>
                    <div class="rightSetUpRightShowIcon"></div>
                    <div class="rightSetUpRightHideIcon"></div>
                </div>
            </div> -->
        </div>
        <div class="bottom-div">
            <div id="preChangeSelect"></div>
            <%--<div class="rightdiv titleYearMonth">2015年上半年</div>--%>
            <div class="row ct-row mainArea">
                <div class="col-sm-12 chartArea" >
                    <div class="col-sm-12 " id="preChange"></div>
                </div>
                <div class="col-sm-12 textArea">
                    说明：<br>
                    • 有所进步：与上期绩效相比，提升一级或以上<br>
                    • 出现下滑：与上期绩效相比，下降一级或以上
                </div>
            </div>
        </div>
    </div>

    <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
        <div class="index-common-title bottom-title">
            <div class="index-common-title-left bottom-left"></div>
            <div class="index-common-title-text bottom-text">绩效异常（大起大落）</div>

            <!-- <div class="rightSetUpBtn">
                <div class="rightSetUpBtnDiv rightSetUpLeft icon rightSetUpBtnSelect">
                    <div class="rightSetUpBtnTop"></div>
                    <div class="rightSetUpLeftShowIcon"></div>
                    <div class="rightSetUpLeftHideIcon"></div>
                </div>
                <div class="rightSetUpBtnDiv rightSetUpRight icon">
                    <div class="rightSetUpBtnTop"></div>
                    <div class="rightSetUpRightShowIcon"></div>
                    <div class="rightSetUpRightHideIcon"></div>
                </div>
            </div> -->
        </div>

        <div class="bottom-div">
            <div id="preUnusualSelect"></div>
            <%--<div class="rightdiv titleYearMonth">2015年上半年</div>--%>
            <div class="row ct-row mainArea">
                <div class="col-sm-12 chartArea" >
                    <div class="col-sm-12 " id="preUnusual"></div>
                </div>
                <div class="col-sm-12 textArea">
                    说明：<br>
                    • 飞速提升：与上期绩效相比，提升幅度至少2个星级<br>
                    • 加速跌落：与上期绩效相比，下滑幅度至少2个星级
                </div>
            </div>
        </div>
    </div>


</div>
<div id="page-two" class="rightBodyPage">
    <div class="col-sm-12 ct-line-col SetUpBody" >
        <div class="index-common-title bottom-title">
            <div class="index-common-title-left bottom-left"></div>
            <div class="index-common-title-text bottom-text">个人绩效明细</div>
        </div>
        <div class="bottom-div bottom-div-three">

            <div class="col-sm-12 ct-line-col conditionBtnListBody"  id="searchBox">

                <div class="dis-search">
                    <input class="dis-search-input" id="searchTxt" type="text" placeholder="请输入员工ID或姓名" >
                    <div class="add-on dis-search-input-btn" id="searchBtn">人员搜索</div>
                </div>
                <div class="more-search"></div>
                <%--<div id="conditionBtn" class="conditionBtn" switch="true">--%>
                    <%--<div class="condition-icon"></div>--%>
                    <%--精简筛选条件</div>--%>

                <%--<div class="condition-body">--%>
                    <%--<div class="condition-body-list" style="margin-bottom: 18px;">--%>
                        <%--<div class="condition-body-list-title left">时间：</div>--%>
                        <%--<select class="per-select2">--%>
                            <%--<option>2015年上半年</option>--%>
                        <%--</select>--%>
                    <%--</div>--%>
                    <%--<div class="condition-body-list">--%>
                        <%--<div class="condition-body-list-title left">职位序列：</div>--%>
                        <%--<div class="condition-btn condition-btn-selected">全部--%>
                        <%--</div>--%>
                        <%--<div class="condition-btn condition-btn-too">管理序列--%>
                        <%--</div>--%>
                        <%--<div class="condition-btn condition-btn-too">业务序列</div>--%>
                        <%--<div class="condition-btn condition-btn-too">专业序列</div>--%>
                        <%--<div class="condition-btn condition-btn-too">职能序列</div>--%>

                    <%--</div>--%>
                    <%--<div class="condition-body-list condition-body-list-too">--%>
                        <%--<div class="condition-body-list-title left">职位层级：</div>--%>
                        <%--<div class="condition-btn condition-btn-selected">全部--%>
                        <%--</div>--%>
                        <%--<div class="condition-btn condition-btn-too">行业专家--%>
                        <%--</div>--%>
                        <%--<div class="condition-btn condition-btn-too">公司专家</div>--%>
                        <%--<div class="condition-btn condition-btn-too">独立工作者</div>--%>
                        <%--<div class="condition-btn condition-btn-too">辅助工作者</div>--%>

                    <%--</div>--%>
                    <%--<div class="condition-body-list condition-body-list-too">--%>
                        <%--<div class="condition-body-list-title left">绩效：</div>--%>
                        <%--<div class="condition-btn condition-btn-selected">全部</div>--%>
                        <%--<div class="condition-btn condition-btn-too">5星</div>--%>
                        <%--<div class="condition-btn condition-btn-too">4星</div>--%>
                        <%--<div class="condition-btn condition-btn-too">3星</div>--%>
                        <%--<div class="condition-btn condition-btn-too">2星</div>--%>
                        <%--<div class="condition-btn condition-btn-too">1星</div>--%>
                    <%--</div>--%>
                <%--</div>--%>

                <%--<div class="condition-bottom">--%>
                    <%--<div class="condition-btn-two left">搜索</div>--%>
                    <%--<div class="condition-btn-one left">重置</div>--%>
                <%--</div>--%>
            </div>

            <div class="table-body">
                <div class="col-md-12 col-xs-12" id="performanceDetailTable">
                    <label>&nbsp;</label>
                    <table class="borderless" id="performanceDetailGrid"></table>
                    <table id="performanceDetailSel"></table>
                </div>
            </div>
        </div>
    </div>
</div>

</div>


<!--主动流失率指标设置 弹出框 begin-->
<div class="ct-drag sz-window">
    <div class="drag-body">
        <div class="drag-content">
            <div class="drag-title">
                <div class="drag-title-blue"></div>
                <div class="drag-title-text">员工绩效指标设置</div>
                <div class="cancelDragBtnEvent closeIcon"></div>
            </div>
            <div class="drag-middle">
                <div class="row ct-row">
                    <div class="col-xs-12 BSetUp-col">
                        <div class="BSetUp-line-two BSetUp-line-three">
                            <span class="CSetUp-text-one pull-left">绩效周期：</span>
                            <select class="CSetUp-select pull-left" id="perfmanWeekObj">
                                <option value="1">一年</option>
                                <option value="2">半年</option>
                                <option value="3">季度</option>
                            </select>
                            <div class="CSetUp-text-two pull-left">说明：设置企业绩效评估周期</div>
                        </div>
                    </div>
                    <div class="col-xs-12 BSetUp-col">
                        <div class="BSetUp-line-two BSetUp-line-three">
                            <span class="CSetUp-text-one pull-left">高绩效定义为：</span>
                            <input type="number" id="minHeightPerObj" class="condition-input-text pull-left">
                            <span class="condition-text-one pull-left">-</span>
                            <input type="number" id="maxHeightPerObj" class="condition-input-text condition-input-text-too pull-left">
                            <div class="CSetUp-text-two pull-left">说明：定义企业高绩效范围</div>
                        </div>
                    </div>
                    <div class="col-xs-12 BSetUp-col">
                        <div class="BSetUp-line-two">
                            <span class="CSetUp-text-one pull-left">低绩效定义为：</span>
                            <input type="number" id="minLowPerObj" class="condition-input-text pull-left">
                            <span class="condition-text-one pull-left">-</span>
                            <input type="number" id="maxLowPerObj"class="condition-input-text condition-input-text-too pull-left">
                            <div class="CSetUp-text-two pull-left">说明：定义企业低绩效范围</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="drag-bottom">
                <div class="drag-btn initDragBtn">确定</div>
                <div class="drag-btn cancelDragBtnEvent cancelDragBtn">取消</div>
            </div>
        </div>
    </div>
</div>
<!--主动流失率指标设置 弹出框 end-->

<!-- 绩效人员详情 弹出框 start -->
<div class="modal fade popup-modal" id="performanceModal" tabindex="-1" role="dialog"
     aria-labelledby="versionModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-text" >员工绩效信息<span class="modal-title-small"></span></div>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">
                <table  class="borderless" id="performanceGrid"></table>
                <div id="performanceGridPage"></div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 绩效人员详情 弹出框 end -->
<%--<div id="showModal" class="modal fade" data-backdrop="static" tabindex="-1" role="dialog">--%>
    <%--<div class="modal-dialog">--%>
        <%--<div class="modal-content">--%>
            <%--<div class="modal-header">--%>
                <%--<input type="hidden" id="searchIndex" >--%>
                <%--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>--%>
                <%--<h3 class="modal-title"></h3>--%>
            <%--</div>--%>
            <%--<div class="modal-body">--%>
                <%--<div class="row">--%>
                    <%--<div class="tab-pane col-md-12 col-sm-12 col-xs-12" id="performanceArea">--%>
                        <%--<table  class="borderless" id="performanceGrid"></table>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div><!-- /.modal-content -->--%>
    <%--</div><!-- /.modal-dialog -->--%>
<%--</div>--%>
<!-- 绩效预警 弹出框 end -->

<!--遮罩层 begin-->
<div class="shade"></div>
<!--遮罩层 end-->

</div>
<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/js/biz/productivity/perChange2.js"></script>
</body>
</html>