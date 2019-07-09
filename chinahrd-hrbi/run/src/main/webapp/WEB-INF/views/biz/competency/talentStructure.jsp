<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>人力结构</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/datetime/datetimepicker.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/competency/talentStructure.css"/>
</head>
<body>
<div class="talentStructure">
    <div class="rightBody">
        <div id="page-one" class="rightBodyPage">
            <div class="row ct-row">

                <div class="col-sm-4 ct-line-col">
                    <div class="top-div">
                        <div class="index-common-title">
                            <div class="index-common-title-left"></div>
                            <div class="index-common-title-text">编制使用率</div>
                            <div class="LSLSetUp"></div>
                        </div>
                        <div class="body-div">
                            <div id="structureRateChart"><div class="loading">数据读取中…</div></div>
                            <div id="structureRateText">
                                <div class="rate"></div>
                                <div class="text"></div>
                            </div>
                            <div id="structureRateNoData" class="structurenodata hide">当前组织无编制信息</div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-4 ct-line-col">
                    <div class="top-div">
                        <div class="index-common-title">
                            <div class="index-common-title-left"></div>
                            <div class="index-common-title-text">可用编制分析</div>
                        </div>
                        <div class="body-div">
                            <table id="talentStructureTable" class="center talentStructureTable">
                                <tr>
                                    <th>可用编制</th>
                                    <th>=</th>
                                    <th>编制数</th>
                                    <th>-</th>
                                    <th>在岗人数</th>
                                </tr>
                                <tr>
                                    <td class="usableEmpCount">-</td>
                                    <td>=</td>
                                    <td class="number">-</td>
                                    <td>-</td>
                                    <td class="empCount">-</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>

                <div class="col-sm-4 ct-line-col">
                    <input id="quotaId" type="hidden" value="${quotaId}">
                    <div class="top-div" id="timeLine"></div>
                </div>

            </div>

            <div class="row ct-row">

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">管理者员工分布</div>

                        <div class="rightSetUpBtn">
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
                        </div>
                    </div>
                    <div class="bottom-div">
                        <div class="chart-view clearfix">
                            <div id="managerChart" class="height300 col-xs-12"><div class="loadingmessage">数据读取中…</div></div>
                            <div class="chartTxt">管理者与员工比例&nbsp;<span id="managerChartTxt"></span></div>
                        </div>
                        <div class="table-view ct-col3">
                            <div id="managerTable" class="ct-mCustomScrollBar">
                                <table id="managerGrid"></table>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">职级分布</div>

                        <div class="rightSetUpBtn">
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
                        </div>
                    </div>

                    <div class="bottom-div">
                        <div class="chart-view">
                            <div id="rankChart" class="height300 col-xs-12"><div class="loadingmessage">数据读取中…</div></div>
                        </div>
                        <div class="table-view ct-col3">
                            <div id="rankTable" class="ct-mCustomScrollBar">
                                <table id="rankGrid"></table>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-12 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">职位序列分布</div>
                        <div class="index-common-title-tooltip">操作提示：点击职位序列可查看该序列下的职级分布，再次点击则可返回默认统计</div>
                        <div class="rightSetUpBtn">
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
                        </div>
                    </div>
                    <div class="bottom-div bottom-div-two">
                        <div class="chart-view">
                            <div id="positionSequenceChart" class="height320 col-sm-6"><div class="loadingmessage">数据读取中…</div></div>
                            <div id="positionRankChart" class="height320 col-sm-6"><div class="loadingmessage">数据读取中…</div></div>
                        </div>
                        <div class="table-view ct-col3">
                             <div id="positionSequenceTable" class="ct-mCustomScrollBar">
                                <table id="positionSequenceGrid"></table>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">组织分布</div>
                    </div>
                    <div class="bottom-div">
                        <div class="chart-view">
                            <div id="organDistributionChart" class="height320 col-sm-12"><div class="loadingmessage">数据读取中…</div></div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">工作地分布</div>
                    </div>
                    <div class="bottom-div">
                        <div class="chart-view">
                              <div id="workplaceChart" class="height320 col-sm-12"><div class="loadingmessage">数据读取中…</div></div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">学历分布</div>

                        <div class="rightSetUpBtn">
                            <div class="rightSetUpBtnDiv rightSetUpLeft icon rightSetUpBtnSelect" role="degree">
                                <div class="rightSetUpBtnTop"></div>
                                <div class="rightSetUpLeftShowIcon"></div>
                                <div class="rightSetUpLeftHideIcon"></div>
                            </div>
                            <div class="rightSetUpBtnDiv rightSetUpRight icon" role="degree">
                                <div class="rightSetUpBtnTop"></div>
                                <div class="rightSetUpRightShowIcon"></div>
                                <div class="rightSetUpRightHideIcon"></div>
                            </div>
                        </div>
                    </div>
                    <div class="bottom-div">
                        <div class="chart-view">
                            <div id="degreeChart" class="height300 col-xs-12"><div class="loadingmessage">数据读取中…</div></div>
                        </div>

                        <div class="table-view ct-col3">
                            <div id="degreeTable" class="ct-mCustomScrollBar">
                                <table id="degreeGrid"></table>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">司龄分布</div>

                        <div class="rightSetUpBtn">
                            <div class="rightSetUpBtnDiv rightSetUpLeft icon rightSetUpBtnSelect" role="compangAge">
                                <div class="rightSetUpBtnTop"></div>
                                <div class="rightSetUpLeftShowIcon"></div>
                                <div class="rightSetUpLeftHideIcon"></div>
                            </div>
                            <div class="rightSetUpBtnDiv rightSetUpRight icon" role="compangAge">
                                <div class="rightSetUpBtnTop"></div>
                                <div class="rightSetUpRightShowIcon"></div>
                                <div class="rightSetUpRightHideIcon"></div>
                            </div>
                        </div>
                    </div>
                    <div class="bottom-div">
                        <div class="chart-view">
                            <div id="compangAgeChart" class="height300 col-xs-12"><div class="loadingmessage">数据读取中…</div></div>
                        </div>
                        <div class="table-view ct-col3">
                            <div id="compangAgeTable" class="ct-mCustomScrollBar">
                                <table id="compangAgeGrid"></table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <!--人才结构指标设置 弹出框 begin-->
    <div class="modal fade popup-modal" id="settingModal" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-header-text">编制使用率预警设置</div>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                </div>

                <div class="modal-body">
                    <div class="row ct-row">
                        <div class="col-sm-6 BSetUp-col">
                            <div class="BSetUp-line">
                                <div class="color-div green-color-div pull-left"></div>
                                <span class="BSetUp-text-one pull-left">正常值：</span>
                                <input id="zdWindowNormal" class="BSetUp-input pull-left" type="text">
                                <span class="BSetUp-text-two pull-left">%</span>
                            </div>
                        </div>
                        <div class="col-sm-6 BSetUp-col">
                            <div class="BSetUp-line">
                                <div class="color-div orange-color-div pull-left"></div>
                                <span class="BSetUp-text-one pull-left">风险值：</span>
                                <input id="zdWindowRisk" class="BSetUp-input pull-left" type="text">
                                <span class="BSetUp-text-two pull-left">%</span>
                            </div>
                        </div>
                        <div class="col-xs-12 BSetUp-col">
                            <div class="BSetUp-text-line">
                                绿色区域：等于或低于正常值
                            </div>
                        </div>
                        <div class="col-xs-12 BSetUp-col">
                            <div class="BSetUp-text-line">
                                黄色区域：高于正常值，等于或低于风险值
                            </div>
                        </div>
                        <div class="col-xs-12 BSetUp-col">
                            <div class="BSetUp-text-line BSetUp-text-last-line">
                                红色区域：高于风险值
                            </div>
                        </div>
                        <div class="col-xs-12 BSetUp-col">
                            <div class="BSetUp-text-line">&nbsp;
                            <span class="errmsg hide">正常值必须小于风险值！</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer"> 
                    <div id="btnSaveRate" class="modal-btn success-btn">确定</div>
                     
                    <div id="cancelSetting" class="modal-btn default-btn" data-dismiss="modal">取消</div>
                     
                </div>
            </div>
        </div>
    </div>
    <!--人才结构指标设置 弹出框 end-->
</div>
<script src="${jsRoot}lib/echarts/echarts.js"></script>
<script src="${jsRoot}biz/competency/talentStructure2.js"></script>
</body>
</html>
