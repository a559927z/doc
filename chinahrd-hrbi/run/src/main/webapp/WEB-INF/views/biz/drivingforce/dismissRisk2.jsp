<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>人才流失风险</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/drivingforce/dismissRisk2.css"/>
</head>
<body>
<div class="dismissRisk">
    <div class="leftBody">
        <div class="leftListBigDiv">人才稳定性
            <div class="leftListSet"></div>
        </div>
        <div page="page-one" class="leftListDiv selectList">人才稳定性</div>
        <div page="page-two" class="leftListDiv">流失风险回顾</div>
        <input type="hidden" id="reqOrganId" value="${reqOrganId}"/>
    </div>

    <div class="rightBody rightBody10">
        <div id="page-one" class="rightBodyPage">
            <div class="col-sm-12 ct-line-col SetUpBody" view="chart">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">人才流失风险</div>
                </div>

                <div class="index-jxmb-tab">
                    <div page="chartview-direct" class="index-jxmb-btn index-jxmb-btn-select">直接下属</div>
                    <div page="chartview-all" class="index-jxmb-btn">全部下属</div>
                </div>

                <div class="bottom-div">
                    <div id="chartview-direct" class="chartview active">
                        <div class="row ct-col">
                            <div class="col-xs-12" id="talentTypeCheck">
                                <label class="">关键人才类型 :&nbsp;&nbsp;</label>
                                <c:forEach var="item" items="${talentType}" varStatus="status">
                                    <label class="checkbox-inline">
                                        <input type="checkbox" value="${item.id}" checked>
                                            ${item.name}
                                    </label>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="col-sm-5 col-xs-12">
                            <div class="col-sm-12 ct-col1">
                                <div class="row ct-row">离职风险点分析：</div>
                                <div class="col-xs-12 ct-col1 risk-bar" id="directRiskBar"></div>
                                <div class="row ct-row risk-note">说明：点击数字查看风险点的高风险人员</div>
                            </div>
                            <span class="right-instructions">
                                <span class="right-line"></span>
                                <span class="right-arrow"></span>
                            </span>
                        </div>
                        <div class="col-sm-7 col-xs-12 ct-col1 rightPieArea">
                            <div class="row ct-row"><span id="riskTitle">离职风险人数</span>：<span id="riskNum">0</span>人</div>
                            <div class="row ct-row ct-mCustomScrollBar mCustomScrollbar" id="riskEmpDetail"></div>
                        </div>
                    </div>
                    <div id="chartview-all" class="chartview">
                        <div class="col-sm-2 ct-col1" id="reportLv">
                            <div class="col-sm-12">&nbsp;</div>
                            <div class="col-sm-12">汇报层级</div>
                            <div class="col-sm-12 ct-col1 ct-mCustomScrollBar mCustomScrollbar">
                                <ul id="subEmpTree" class="ztree"></ul>
                            </div>
                            <span class="right-instructions" style="margin-top: 23px;height: 300px;">
                                <span class="right-line"></span>
                                <span class="right-arrow"></span>
                            </span>
                        </div>
                        <div class="col-sm-10">
                            <div class="col-sm-12">&nbsp;</div>
                            <div class="col-sm-12 ct-col1" id="chartArea">
                                <div class="col-sm-4" style="padding-left: 0;">
                                    <div class="col-xs-12 ct-col1">离职风险点分析：</div>
                                    <div class="col-xs-12 ct-col1 risk-bar" id="subEmpRiskBar"></div>
                                    <div class="col-xs-12 ct-col1 risk-note">说明：点击数字查看风险点的高风险人员</div>
                                    <span class="right-instructions" style="height: 300px;right:1px;">
                                        <span class="right-line"></span>
                                        <span class="right-arrow"></span>
                                    </span>
                                </div>
                                <div class="col-sm-8 ct-col1" id="subEmpRiskTable">
                                    <table id="subEmpRiskGrid"></table>
                                </div>
                            </div>
                            <div class="col-md-12 col-sm-12" id="rigthArea">无离职风险人员</div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-12 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">当前组织离职风险分析</div>
                </div>
                <div class="bottom-div" id="currOrganDimission"></div>
            </div>

        </div>

        <div id="page-two" class="rightBodyPage">
            <div class="col-sm-12 ct-line-col SetUpBody">
                <div class="index-common-title bottom-title">
                    <div class="index-common-title-left bottom-left"></div>
                    <div class="index-common-title-text bottom-text">离职预测回归分析</div>
                </div>
                <div class="bottom-div">
                    <div class="row">
                        <div class="col-sm-4 col-xs-12 ct-container-fluid">
                            <div id="dimissRiskReviewPie" class="col-xs-12 ct-col1"></div>
                            <span class="col-xs-12 ct-col1  risk-note">说明：“有预警提示”为离职前<span id="riskWarningDesc">半年</span>内有评估过离职预警</span>
                        </div>
                        <div class="col-sm-8 col-xs-12" id="dimissRiskReviewTable">
                            <div>&nbsp;</div>
                            <table  class="borderless" id="dimissRiskReviewGrid"></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--人才流失风险指标设置 弹出框 begin-->
    <div class="modal fade popup-modal" id="riskConfigModal" tabindex="-1" role="dialog"
         aria-labelledby="modalLabel" aria-hidden="true"> 
        <div class="modal-dialog"> 
            <div class="modal-content">
                <div class="modal-header"> 
                    <div class="modal-header-text">人才流失风险指标设置</div> 
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button> 
                </div> 
                <div class="modal-body">
                    <div class="row ct-row"> 
                        <div class="col-xs-12 BSetUp-col">
                            <div class="BSetUp-line-four BSetUp-line-three">
                                <span class="left">“有预警提示”定义为离职人员离职前
                                <select class="CSetUp-select" id="hasmessage"> 
                                    <option value="1">一年</option> 
                                    <option value="2">半年</option> 
                                    <option value="3">季度</option> 
                                </select>&nbsp;内出现过离职预警提示。</span> 
                            </div>
                        </div> 
                        <div class="col-xs-12 BSetUp-col">
                            <div class="BSetUp-line-two BSetUp-line-three">
                                <span class="CSetUp-text-one left">预警条件：</span> 
                                <select class="CSetUp-select left" id="notifyConfig"> 
                                    <option value="1">红灯</option> 
                                    <option value="2">黄灯</option> 
                                    <option value="3">红黄灯</option> 
                                </select>
                                 <div class="CSetUp-text-two left">说明：当主动流失率达到黄灯或者红灯值时则发送预警通知</div> 
                            </div>
                        </div> 
                        <div class="col-xs-12 BSetUp-col">
                             <div class="BSetUp-line-two BSetUp-line-three">
                                <span class="CSetUp-text-one left">预警对象：</span> 
                                <label class="CSetUp-text-checkbox left">
                                    <input class="CSetUp-text-checkbox-input" name="YJObject" type="checkbox" value="2" />
                                    <span class="CSetUp-text-checkbox-text">架构负责人</span>
                                </label> 
                                <label class="CSetUp-text-checkbox left">
                                    <input class="CSetUp-text-checkbox-input" name="YJObject" type="checkbox" value="3" />
                                    <span class="CSetUp-text-checkbox-text">BP</span>
                                </label> 
                                <div class="CSetUp-text-two left">说明："当主动流失率达到黄灯或者红灯值时"</div> 
                            </div> 
                        </div>
                         <div class="col-xs-12 BSetUp-col"> 
                            <div class="BSetUp-line-two"> 
                                <span class="CSetUp-text-one left">预警方式：</span> 
                                <label class="CSetUp-text-checkbox left">
                                    <input class="CSetUp-text-checkbox-input" name="YJMode" type="checkbox" value="1" />
                                    <span class="CSetUp-text-checkbox-text">邮件</span>
                                </label> 
                                <label class="CSetUp-text-checkbox left">
                                    <input class="CSetUp-text-checkbox-input" name="YJMode" type="checkbox" value="2" />
                                    <span class="CSetUp-text-checkbox-text">手机短信</span>
                                </label> 
                                <div class="CSetUp-text-two left">说明：当有人员离职风险异常时通知方式</div> 
                            </div> 
                        </div> 
                    </div>
                </div> 
                <div class="modal-footer"> 
                    <div class="modal-btn success-btn">确定</div>
                     <div class="modal-btn default-btn" data-dismiss="modal">取消</div> 
                </div>
             </div><!-- /.modal-content --> 
        </div><!-- /.modal-dialog -->
     </div><!-- /.modal -->
    <!--人才流失风险指标设置 弹出框 end-->

    <!-- 离职风险详情(Modal) -->
    <div class="modal fade popup-modal" id="riskDetailModal" tabindex="-1" role="dialog"
         aria-labelledby="modalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-header-text" id="gainOfLossModalLabel">离职风险详情</div>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <!-- 员工基本信息 -->
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
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <!--遮罩层 begin-->
    <div class="shade"></div>
    <!--遮罩层 end-->
</div>
<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/js/biz/drivingforce/dismissRisk2.js"></script>

</body>
</html>
