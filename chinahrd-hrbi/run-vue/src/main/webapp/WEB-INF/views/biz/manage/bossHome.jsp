<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>首页</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <!--JqueryDrag css-->
    <link rel="stylesheet" href="${ctx}/assets/css/jquery-drag/jquery-drag.css" />
    <link rel="stylesheet" href="${ctx}/assets/css/biz/bossHome.css"/>
</head>
<body>
<div class="container-fluid ct-container-fluid ct-ancestors index">

    <div class="row ct-row">

        <div class="col-xs-12 ct-line-col">
            <div class="index-time left">
                <div class="index-time-icon left"></div>
                <span class="index-time-text left">更新时间：&nbsp;<span id="updateTime"><fmt:formatDate value="${updateDate}" pattern="yyyy/MM/dd HH:mm"></fmt:formatDate></span></span>
            </div>
            <%--<div id="showDragBtn" class="index-set right">--%>
                <%--<div class="index-set-icon left"></div>--%>
                <%--<div class="index-set-text left">首页设置</div>--%>
            <%--</div>--%>
            <input type="hidden" id="entryAnnual" value="${entryAnnual}">
        </div>

    </div>

    <div class="index-jy-body">

        <div class="index-common-title">
            <div class="index-common-title-left"></div>
            <div class="index-common-title-text">预警</div>
        </div>

        <!--预警 排序 模块 begin-->
        <div name="预警" class="row ct-row index-jy-middle">
            <div name="离职风险" aria-controls="runOffWarnTab" showIndex="1" classStyle="lzfx-icon" class="DRAG-DIV YJ-DIV col-sm-3 ct-col1 index-yj-div">
                <div class="index-yj-div-top">
                    <div class="left">离职风险</div>
                    <%--<div class="right">60%</div>--%>
                </div>
                <div class="index-yj-float">
                    <span id="runOffWarnTabSpan" class="index-yj-float-value">0</span>
                    <span class="index-yj-float-people">人</span>
                </div>
                <div class="index-yj-float-text">可能存在离职风险</div>
                <%--<div class="index-yj-div-bottom">--%>
                    <%--<div id="runOffWarnTabDiv" class="index-yj-div-bottom-value left"></div>--%>
                    <%--<div class="index-yj-div-bottom-right right"></div>--%>
                <%--</div>--%>
            </div>
            <div name="人均效益"  showIndex="2" class="DRAG-DIV YJ-DIV col-sm-3 ct-col1 index-yj-div">
                <div class="index-yj-div-top">
                    <div class="left">人均效益</div>
                </div>
                <div class="index-yj-float">
                    <span class="index-yj-float-value index-yj-warn-value">0.3</span>
                    <span class="index-yj-float-people">万元</span>
                </div>
                <div class="index-yj-float-text">3月低于行业均值10%</div>
                <%--<div class="index-yj-div-bottom">--%>
                    <%--<div class="index-yj-div-bottom-value left">3月低于行业均值10%</div>--%>
                    <%--&lt;%&ndash;<div class="index-yj-div-bottom-right right"></div>&ndash;%&gt;--%>
                <%--</div>--%>
            </div>
            <div name="人才成本"  showIndex="3" class="DRAG-DIV YJ-DIV col-sm-3 ct-col1 index-yj-div">
                <div class="index-yj-div-top">
                    <div class="left">人才成本</div>
                </div>
                <div class="index-yj-float">
                    <span class="index-yj-float-value index-yj-risk-value">1.45</span>
                    <span class="index-yj-float-people" style="margin-right: -15px;">万元</span>
                </div>
                <div class="index-yj-float-text">3月高于行业均值15%</div>
                <%--<div class="index-yj-div-bottom">--%>
                    <%--<div class="index-yj-div-bottom-value left">3月高于行业均值15%</div>--%>
                <%--</div>--%>
            </div>
            <div name="组织KPI达标率" showIndex="4" class="DRAG-DIV YJ-DIV col-sm-3 ct-col1 index-yj-div">
                <div class="index-yj-div-top">
                    <div class="left">组织KPI达标率</div>
                    <%--<div class="right">60%</div>--%>
                </div>
                <div class="index-yj-float">
                    <span class="index-yj-float-value index-yj-warn-value">80</span>
                    <span class="index-yj-float-people">%</span>
                </div>
                <div class="index-yj-float-text">2015年KPI未达标</div>
                <%--<div class="index-yj-div-bottom">--%>
                    <%--<div class="index-yj-div-bottom-value left">2015年KPI未达标</div>--%>
                <%--</div>--%>
            </div>
        </div>
        <!--预警 排序 模块 end-->
    </div>

    <div name="首页" class="row ct-row">

        <div name="当季人才损益" showIndex="1" class="DRAG-DIV col-sm-6 ct-col index-yj-div-djrcsy">
            <div id="personnel" class="index-son-body">
                <div class="index-common-title">
                    <div class="index-common-title-left"></div>
                    <div class="index-common-title-text">当季人才损益</div>
                </div>

                <div class="index-son-middle">

                    <div class="index-djrcsy-table-left">
                        <div id="gainOfLossNum" class="index-djrcsy-left-value">+1</div>
                        <div class="index-djrcsy-left-text">人才损益值</div>
                    </div>

                    <div class="index-djrcsy-table-right">
                        <div class="index-djrcsy-table-title bolder">编制情况</div>
                        <div class="row ct-row">
                            <div class="col-xs-3 ct-col1">编制数：<span id="compileNum" class="blue-text bolder">0</span></div>
                            <div class="col-xs-9 ct-col1">可用编制数：<span id="usableCompileNum" class="blue-text bolder">0</span></div>
                        </div>
                    </div>

                    <div class="index-djrcsy-table-right">
                        <div class="index-djrcsy-table-title bolder">人才损益</div>
                        <div class="row ct-row">
                            <div class="col-xs-3 ct-col1">入职：<span id="entryEmpsNum" class="blue-text bolder">0</span></div>
                            <div class="col-xs-3 ct-col1">调入：<span id="callinEmpsNum" class="blue-text bolder">0</span></div>
                            <div class="col-xs-3 ct-col1">离职：<span id="dimissionEmpsNum" class="blue-text bolder">0</span></div>
                            <div class="col-xs-3 ct-col1">调出：<span id="calloutEmpsNum" class="blue-text bolder">0</span></div>
                        </div>
                    </div>

                    <div class="index-djrcsy-table-right">
                        <div class="index-djrcsy-table-title bolder">招聘进程</div>
                        <div class="row ct-row">
                            <div class="col-xs-3 ct-col1">发布职位数：<span id="publiceJobNum" class="blue-text bolder">0</span></div>
                            <div class="col-xs-3 ct-col1">简历数：<span id="resumeNum" class="blue-text bolder">0</span></div>
                            <div class="col-xs-3 ct-col1">应聘数：<span id="acceptNum" class="blue-text bolder">0</span></div>
                            <div class="col-xs-3 ct-col1">offer数：<span id="offerNum" class="blue-text bolder">0</span></div>
                        </div>
                    </div>

                </div>

            </div>
        </div>

        <div name="组织共识度" showIndex="2" class="DRAG-DIV col-sm-6 ct-col">
            <div class="index-son-body">
                <div class="index-common-title">
                    <div class="index-common-title-left"></div>
                    <div class="index-common-title-text">组织共识度</div>
                </div>
                <div class="index-son-middle">
                    <div class="col-xs-6 ct-col1 index-yj-div">
                        <div class="index-yj-div-top">
                            <div class="left">价值观共识度</div>
                        </div>
                        <div class="index-yj-float">
                            <span class="index-yj-float-value">80</span>
                            <span class="index-yj-float-people">%</span>
                        </div>
                        <div class="index-yj-float-text">2015年价值观共识指数</div>
                        <%--<div class="index-yj-div-bottom">--%>
                            <%--<div id="birName" class="index-yj-div-bottom-value left"></div>--%>
                            <%--<div class="index-yj-div-bottom-right right"></div>--%>
                        <%--</div>--%>
                    </div>

                    <div class="col-xs-6 ct-col1 index-yj-div index-yj-div-tdtx">
                        <div class="index-yj-div-top">
                            <div class="left">战略共识度</div>
                        </div>
                        <div class="index-yj-float">
                            <span class="index-yj-float-value">90</span>
                            <span class="index-yj-float-people">%</span>
                        </div>
                        <div class="index-yj-float-text">2015年战略共识指数</div>
                        <%--<div class="index-yj-div-bottom">--%>
                            <%--<div id="yearNameList" class="index-yj-div-bottom-value left"></div>--%>
                            <%--<div class="index-yj-div-bottom-right right"></div>--%>
                        <%--</div>--%>
                    </div>

                </div>
            </div>
        </div>

        <div name="人才地图" showIndex="3" class="DRAG-DIV col-sm-6 ct-col">
            <div class="index-son-body">
                <div class="index-common-title">
                    <div class="index-common-title-left"></div>
                    <div class="index-common-title-text">人才地图</div>
                </div>
                <div class="index-son-middle">
                    <div style="width: 100%;margin: 0;text-align: center;cursor: pointer;">
                        <img src="${ctx}/assets/img/temp/talent-map.png" style="margin-top: 30px;" />
                    </div>
                </div>
            </div>
        </div>

        <div name="关键人才库" showIndex="4" class="DRAG-DIV col-sm-6 ct-col">
            <div class="index-son-body">
                <div class="index-common-title">
                    <div class="index-common-title-left"></div>
                    <div class="index-common-title-text">关键人才库</div>
                </div>
                <div class="index-son-middle">
                    <div class="col-xs-6 ct-col1 index-yj-div">
                        <div class="index-yj-div-top">
                            <div class="left">关键人才总数</div>
                        </div>
                        <div class="index-yj-float">
                            <span class="index-yj-float-value">66</span>
                            <span class="index-yj-float-people">人</span>
                        </div>
                        <%--<div class="index-yj-float-text">2015年共适度指数</div>--%>
                        <div class="index-yj-div-bottom">
                        <div class="index-yj-div-bottom-value left">占比总员工数5%</div>
                        <%--<div class="index-yj-div-bottom-right right"></div>--%>
                        </div>
                    </div>

                    <div class="col-xs-6 ct-col1 index-yj-div index-yj-div-tdtx">
                        <div class="index-yj-div-top">
                            <div class="left">预警人数</div>
                        </div>
                        <div class="index-yj-float">
                            <span class="index-yj-float-value">7</span>
                            <span class="index-yj-float-people">人</span>
                        </div>
                        <%--<div class="index-yj-float-text">2015年价值观指数</div>--%>
                        <div class="index-yj-div-bottom">
                        <div class="index-yj-div-bottom-value left">李飞、方亮亮、张鑫、张欣怡...</div>
                        <div class="index-yj-div-bottom-right right"></div>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <div name="员工管理" showIndex="5" class="DRAG-DIV col-sm-6 ct-col">
            <div class="index-son-body">
                <div class="index-common-title">
                    <div class="index-common-title-left"></div>
                    <div class="index-common-title-text">员工管理</div>
                </div>
                <div class="index-son-middle">
                    <div class="col-xs-6 ct-col1 index-yj-div index-yj-div-rc index-yj-div-update">
                        <div class="index-yj-div-top">
                            <div class="left">人才剖像</div>
                        </div>

                        <div class="index-yj-div-body">

                            <div class="index-rc-text">人才剖像</div>

                            <div class="yj-search">
                                <input class="yj-search-input" id="yjSearchTxt" type="text" placeholder="请输入员工ID或姓名">
                                <div class="add-on yj-search-input-btn" id="yjSearchBtn" style="width: 22%;"><i class="yj-search-icon"></i></div>
                            </div>

                            <div class="size-12 index-rc-tips">姓名支持模糊查询</div>
                        </div>



                        <!-- <div class="index-yj-div-bottom">
                            <div class="index-yj-div-bottom-value left">查询记录：<span>无</span></div>
                            <div class="index-yj-div-bottom-right right"></div>
                        </div> -->
                    </div>

                    <div class="col-xs-6 ct-col1 index-yj-div index-yj-div-tdtx index-yj-div-update">
                        <div class="index-yj-div-top">
                            <div class="left">人才PK</div>
                            <div class="index-tdtx-right">马上PK</div>
                        </div>
                        <!--<img src="${ctx}/assets/img/logo.png" style="width: 80px;height: 80px;" class="img-circle img-responsive" alt="Responsive image">-->

                        <div class="index-rc-middle">
                            <div class="row ct-row" id="talentContrast">
                                <div class="col-xs-6 ct-col-md-3 col-md-3 ct-circle-col">
                                    <div class="ct-circle-add">
                                        <div class="ct-circle-add-img"></div>
                                    </div>
                                    <div class="size-12 img-rc-head-name">搜索添加</div>
                                </div>

                                <div class="col-xs-6 ct-col-md-3 col-md-3 ct-circle-col">
                                    <div class="ct-circle-add">
                                        <div class="ct-circle-add-img"></div>
                                    </div>
                                    <div class="size-12 img-rc-head-name">搜索添加</div>
                                </div>
                                <div class="col-xs-6 ct-col-md-3 col-md-3 ct-circle-col">
                                    <div class="ct-circle-add">
                                        <div class="ct-circle-add-img"></div>
                                    </div>
                                    <div class="size-12 img-rc-head-name">搜索添加</div>
                                </div>
                                <div class="col-xs-6 ct-col-md-3 col-md-3 ct-circle-col">
                                    <div class="ct-circle-add">
                                        <div class="ct-circle-add-img"></div>
                                    </div>
                                    <div class="size-12 img-rc-head-name">搜索添加</div>
                                </div>
                            </div>
                            <div class="index-rc-btn">马上PK</div>
                        </div>
                        <!-- <div class="index-yj-div-bottom">
                            <div class="index-yj-div-bottom-value left">PK记录：<span>张小明</span></div>
                            <div class="index-yj-div-bottom-right right"></div>
                        </div> -->
                    </div>

                </div>
            </div>
        </div>

    </div>



    <!-- 当季人才损益 弹出框 begin -->
    <div class="modal fade topWindow" id="gainOfLossModal" tabindex="-1" role="dialog"
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
                                <div class="index-common-title">
                                    <div class="index-common-title-left"></div>
                                    <div class="index-common-title-text">入职（<span class="red" id="entryEmpsNum2">0</span>人）</div>
                                </div>
                            </div>
                            <div class="col-xs-12" id="entryEmpsTable">
                                <table class="borderless" id="entryEmpsGrid"></table>
                            </div>
                            <div class="col-xs-12">
                                <div class="index-common-title">
                                    <div class="index-common-title-left"></div>
                                    <div class="index-common-title-text">调入（<span class="red" id="callinEmpsNum2">0</span>人）</div>
                                </div>
                            </div>
                            <div class="col-xs-12" id="callinEmpsTable">
                                <table class="borderless" id="callinEmpsGrid"></table>
                            </div>
                            <div class="col-xs-12">
                                <div class="index-common-title">
                                    <div class="index-common-title-left"></div>
                                    <div class="index-common-title-text">离职（<span class="red" id="dimissionEmpsNum2">0</span>人）</div>
                                </div>
                            </div>
                            <div class="col-xs-12" id="dimissionEmpsTable">
                                <table class="borderless" id="dimissionEmpsGrid"></table>
                            </div>
                            <div class="col-xs-12">
                                <div class="index-common-title">
                                    <div class="index-common-title-left"></div>
                                    <div class="index-common-title-text">调出（<span class="red" id="calloutEmpsNum2">0</span>人）</div>
                                </div>
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
    <!-- 当季人才损益 弹出框 end -->

    <!-- 预警 弹出框 begin -->
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
                                <%--<li role="presentation">--%>
                                    <%--<a href="#performanceWarnTab" aria-controls="performanceWarnTab" role="tab" data-toggle="tab">绩效风险预警</a>--%>
                                <%--</li>--%>
                                <%--<li role="presentation">--%>
                                    <%--<a href="#workLifeWarnTab" aria-controls="workLifeWarnTab" role="tab" data-toggle="tab">工作生活平衡欠佳</a>--%>
                                <%--</li>--%>
                            </ul>

                            <!-- Tab panes -->
                            <div class="tab-content">
                                <!-- 离职风险预警 -->
                                <div role="tabpanel" class="tab-pane active" id="runOffWarnTab">
                                    <div class="row">
                                        <span class="span_note">注：点击头像或姓名可查看离职风险分析</span>
                                        <!-- 员工基本信息 -->
                                        <div class="row base-info">
                                            <div class="col-xs-12 " id="riskEmpDetail"></div>
                                        </div>
                                    </div>
                                </div>
                                <!-- 绩效风险预警 -->
                                <div role="tabpanel" class="tab-pane" id="performanceWarnTab">
                                    <div class="row">
                                        <!-- 连续高绩效未晋升 -->
                                        <div class="text">
                                            <p><span style="font-family:'Applied Font Regular', 'Applied Font';font-weight:400;color:#000000;">连续高绩效未晋升（</span>
                                                <span id="highNum" style="font-family:'Applied Font Bold', 'Applied Font';font-weight:700;color:#FF6600;">3</span>
                                                <span style="font-family:'Applied Font Regular', 'Applied Font';font-weight:400;color:#000000;">人）</span>
                                            </p>
                                        </div>
                                        <div class="tab-pane col-md-12 col-sm-12 col-xs-12" id="highPerformanceArea">
                                            <table  class="borderless" id="highPerformanceGrid"></table>
                                        </div>
                                        <!-- 连续低绩效未调整 -->
                                        <div class="text">
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
                                            <div class="col-xs-12 " id="workLifeDetail"></div>
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
        <div class="work-over-time-h1">
            <p><span class="span_note">近两周加班趋势</span></p>
        </div>
        <div class="work-over-time-h2">
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
    <!-- 预警 弹出框 end -->

    <!-- 人员PK 弹出框 begin -->
    <div id="contrastSearchModal" class="modal fade" data-backdrop="static" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <input type="hidden" id="contrastSearchIndex" >
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title">添加人员</h3>
                </div>
                <div class="modal-body">
                    <div class="row ">
                        <div class="col-xs-7">
                            <div class="input-group">
                                <input type="text" class="form-control search-query" id="contrastSearchTxt" placeholder="请输入员工ID/姓名" />
                                <span class="input-group-btn">
                                    <button type="button" class="btn btn-white" id="contrastSearchBtn">
                                        查询
                                        <i class="icon-search icon-on-right bigger-110"></i>
                                    </button>
                                </span>
                            </div>
                        </div>
                        <div class="col-xs-5"><label>* 姓名支持模糊查询</label></div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">&nbsp;</div>
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
    <!-- 人员PK 弹出框 end -->

    <!--拖拽弹窗 begin-->
    <div id="ct-drag" class="ct-drag">
        <div id="drag-body" class="drag-body">

        </div>
    </div>
    <!--拖拽弹窗 end-->
</div>

<!--遮罩层--><div id="shade" class="shade"></div>

<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/js/biz/bossHome.js"></script>
</body>
</html>