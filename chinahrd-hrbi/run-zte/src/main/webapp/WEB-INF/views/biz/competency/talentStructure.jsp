<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>人力结构</title>
    <link rel="stylesheet" href="${ctx}/assets/css/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/datetime/datetimepicker.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/jquery-multipleselect/multiple-select.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/competency/talentStructure.css"/>
</head>
<body>
<input id="minDay" type="hidden" value="${minDay}">
<input id="maxDay" type="hidden" value="${maxDay}">
<div class="personnelStructure">
    <div class="leftBody">
        <div class="leftListBigDiv">人员结构</div>
        <div page="page-one" class="leftListDiv selectList">团队结构</div>
        <div page="page-two" class="leftListDiv">团队画像</div>
    </div>

    <div class="rightBody">
        <div id="page-one" class="rightBodyPage">
            <div class="row ct-row">

                <div class="col-sm-4 ct-30 ct-line-col">
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
                            <div id="structureRateNoData" class='structurenodata hide'>当前组织无编制信息</div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-5 ct-36 ct-line-col">
                    <div class="top-div">
                        <div class="index-common-title">
                            <div class="index-common-title-left"></div>
                            <div class="index-common-title-text">可用编制分析</div>
                        </div>
                        <div class="body-div">
                            <table id="talentStructureTable" class="center personnelStructureTable">
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

                <div class="col-sm-3 ct-34 ct-line-col">
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
                            <div class="rightSetUpBtnDiv rightSetUpLeft rightSetUpBtnSelect">
                                <div class="rightSetUpBtnTop"></div>
                                <div class="rightSetUpLeftShowIcon"></div>
                                <div class="rightSetUpLeftHideIcon"></div>
                            </div>
                            <div class="rightSetUpBtnDiv rightSetUpRight">
                                <div class="rightSetUpBtnTop"></div>
                                <div class="rightSetUpRightShowIcon"></div>
                                <div class="rightSetUpRightHideIcon"></div>
                            </div>
                        </div>
                    </div>
                    <div class="bottom-div">
                        <div id="managerSelect"></div>
                        <div class="chart-view whpc100 managerOpt" style="text-align: center;">
                            <div class="chartloadingmsg hide"></div>
                            <div class="hide">
                                <div id="managerChart" class="pieChart col-xs-7"><div class="loadingmessage">数据读取中…</div></div>
                                <div id="managerText" class="pieChartText col-xs-5 ct-mCustomScrollBar hide">
                                    <table>
                                        <tr>
                                            <td>
                                                <div>管理层级</div>
                                                <div class="legend">-</div>
                                                <div>管理者与员工比例 <span class="proportion">-</span></div>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="table-view managerOpt padding8 heightpc100">
                            <div class="chartloadingmsg hide"></div>
                            <div class="hide">
                                <div id="managerTable" class="ct-mCustomScrollBar overflowauto heightpc100">
                                    <table class="table table-condensed minwidthpc100">
                                        <thead></thead>
                                        <tbody></tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">职级分布</div>

                        <div class="rightSetUpBtn">
                            <div class="rightSetUpBtnDiv rightSetUpLeft rightSetUpBtnSelect">
                                <div class="rightSetUpBtnTop"></div>
                                <div class="rightSetUpLeftShowIcon"></div>
                                <div class="rightSetUpLeftHideIcon"></div>
                            </div>
                            <div class="rightSetUpBtnDiv rightSetUpRight">
                                <div class="rightSetUpBtnTop"></div>
                                <div class="rightSetUpRightShowIcon"></div>
                                <div class="rightSetUpRightHideIcon"></div>
                            </div>
                        </div>
                    </div>

                    <div class="bottom-div">
                        <div id="rankSelect"></div>
                        <div class="chart-view whpc100 rankOpt" style="text-align: center;">
                            <div class="chartloadingmsg hide"></div>
                            <div class="hide">
                                <div id="rankChart" class="pieChart col-xs-7"><div class="loadingmessage">数据读取中…</div></div>
                                <div id="rankText" class="pieChartText col-xs-5 ct-mCustomScrollBar mCustomScrollbar hide" data-mcs-theme="minimal-dark">
                                    <table>
                                        <tr>
                                            <td>
                                                <div>职级</div>
                                                <div class="legend">-</div>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="table-view rankOpt padding8 heightpc100">
                            <div class="chartloadingmsg hide"></div>
                            <div class="hide">
                                <div id="rankTable" class="ct-mCustomScrollBar overflowauto heightpc100">
                                    <table class="table table-condensed minwidthpc100">
                                        <thead></thead>
                                        <tbody></tbody>
                                    </table>
                                </div>
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
                            <div class="rightSetUpBtnDiv rightSetUpLeft rightSetUpBtnSelect">
                                <div class="rightSetUpBtnTop"></div>
                                <div class="rightSetUpLeftShowIcon"></div>
                                <div class="rightSetUpLeftHideIcon"></div>
                            </div>
                            <div class="rightSetUpBtnDiv rightSetUpRight">
                                <div class="rightSetUpBtnTop"></div>
                                <div class="rightSetUpRightShowIcon"></div>
                                <div class="rightSetUpRightHideIcon"></div>
                            </div>
                        </div>
                    </div>
                    <div class="bottom-div bottom-div-two" style="text-align: center; padding-top:20px;">
                        <div id="positionSequenceSelect"></div>
                        <div class="chart-view whpc100 positionOpt" style="text-align: center;">
                            <div class="chartloadingmsg hide"></div>
                            <div class="hide">
                                <div id="positionSequenceChart" class="col-sm-6 bodyRowHeight"><div class="loadingmessage">数据读取中…</div></div>
                                <div id="positionRankChart" class="col-sm-6 bodyRowHeight"><div class="loadingmessage">数据读取中…</div></div>
                            </div>
                        </div>
                        <div class="table-view positionOpt padding8 heightpc100">
                            <div class="chartloadingmsg hide"></div>
                            <div class="hide">
                                <div id="positionSequenceTable" class="ct-mCustomScrollBar overflowauto heightpc100">
                                    <table class="table table-condensed minwidthpc100">
                                        <thead></thead>
                                        <tbody></tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">下级组织人员分布</div>

                        <!--                         <div class="rightSetUpBtn"> -->
                        <!--                             <div class="rightSetUpBtnDiv rightSetUpLeft rightSetUpBtnSelect"> -->
                        <!--                                 <div class="rightSetUpBtnTop"></div> -->
                        <!--                                 <div class="rightSetUpLeftShowIcon"></div> -->
                        <!--                                 <div class="rightSetUpLeftHideIcon"></div> -->
                        <!--                             </div> -->
                        <!--                             <div class="rightSetUpBtnDiv rightSetUpRight"> -->
                        <!--                                 <div class="rightSetUpBtnTop"></div> -->
                        <!--                                 <div class="rightSetUpRightShowIcon"></div> -->
                        <!--                                 <div class="rightSetUpRightHideIcon"></div> -->
                        <!--                             </div> -->
                        <!--                         </div> -->
                    </div>
                    <div class="bottom-div height420">
                        <div id="organDistributionSelect"></div>
                        <span>&nbsp;</span>
                        <div class="chart-view whpc100 organDistributionOpt" style="text-align: center;">
                            <div class="chartloadingmsg hide"></div>
                            <div class="hide">
                                <div id="organDistributionChart" class="pieChartText co l-sm-12 overflowhidden bodyRowHeight"><div class="loadingmessage">数据读取中…</div></div>
                            </div>
                        </div>
                        <div class="table-view"></div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">工作地分布</div>

                        <!--                         <div class="rightSetUpBtn"> -->
                        <!--                             <div class="rightSetUpBtnDiv rightSetUpLeft rightSetUpBtnSelect"> -->
                        <!--                                 <div class="rightSetUpBtnTop"></div> -->
                        <!--                                 <div class="rightSetUpLeftShowIcon"></div> -->
                        <!--                                 <div class="rightSetUpLeftHideIcon"></div> -->
                        <!--                             </div> -->
                        <!--                             <div class="rightSetUpBtnDiv rightSetUpRight"> -->
                        <!--                                 <div class="rightSetUpBtnTop"></div> -->
                        <!--                                 <div class="rightSetUpRightShowIcon"></div> -->
                        <!--                                 <div class="rightSetUpRightHideIcon"></div> -->
                        <!--                             </div> -->
                        <!--                         </div> -->
                    </div>
                    <div class="bottom-div height420">
                        <div id="workLocationSelect"></div>
                        <span>&nbsp;</span>
                        <div class="chart-view whpc100 workLocationOpt" style="text-align: center;">
                            <div class="chartloadingmsg hide"></div>
                            <div class="hide">
                                <div id="workLocationChart" class="pieChartText col-sm-12 bodyRowHeight"><div class="loadingmessage">数据读取中…</div></div>
                            </div>
                        </div>
                        <div class="table-view"></div>
                    </div>
                </div>

            </div>

        </div>

        <div id="page-two" class="rightBodyPage">
            <div class="row ct-row">

                <%--<div class="col-sm-6 ct-line-col SetUpBody" view="chart">--%>
                <%--<div class="index-common-title bottom-title">--%>
                <%--<div class="index-common-title-left bottom-left"></div>--%>
                <%--<div class="index-common-title-text bottom-text">下级组织本月加班统计<span>(操作提示：点击柱状图显示加班明细)</span></div>--%>
                <%--</div>--%>

                <%--<div class="bottom-div">--%>
                <%--<div id="overtimeSelect"></div>--%>
                <%--<div class="chart-view text-center height100pc">--%>
                <%--<div id="overtime" class="chart height100pc" style="height:330px;margin-top:15px;"></div>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">男女比例</div>
                    </div>

                    <div class="bottom-div">
                        <div id="sexSelect"></div>
                        <div class="row whpc100 sexOpt">
                            <div class="chartloadingmsg hide"></div>
                            <div class="hide">
                                <div class="col-xs-12" id="sex_div">
                                    <div class="sex-pie" id="sex"></div>
                                    <img src="${ctx}/assets/img/manage/teamImg-sex.png" class="teamImg-sex hide" />
                                    <div class="lidyZone hide">
                                        <div>女</div>
                                        <div><span class="lidyVal"></span><span>人</span></div>
                                    </div>
                                    <div class="manZone hide">
                                        <div>男</div>
                                        <div><span class="manVal"></span><span>人</span></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">婚姻状况</div>
                    </div>

                    <div class="bottom-div">
                        <div id="marrySelect"></div>
                        <div class="row whpc100 marryOpt">
                            <div class="chartloadingmsg hide"></div>
                            <div class="whpc100 hide">
                                <div class="col-xs-12 whpc100" id="ms">
                                    <div class="marry_state">
                                        <table width="250" border="0">
                                            <tr height="200px;">
                                                <td align="center" valign="bottom">
                                                    <div class="leftBarPer"></div>
                                                    <div class="leftBar">
                                                        <span id="leftBarVal"></span>
                                                    </div>
                                                </td>
                                                <td></td>
                                                <td align="center" valign="bottom">
                                                    <div class="rightBarPer"></div>
                                                    <div class="rightBar">
                                                        <span id="righBarVal"></span>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center" id="highMsg" valign="bottom">未婚</td>
                                                <td>&nbsp;</td>
                                                <td align="center" id="lowMsg" valign="bottom">已婚</td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">血型</div>
                    </div>

                    <div class="bottom-div">
                        <div id="bloodSelect"></div>
                        <div class="chart-view bloodOpt text-center height100pc">
                            <div class="chartloadingmsg hide"></div>
                            <div class="whpc100 hide">
                                <div id="blood" class="chart height100pc"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">星座分布</div>
                    </div>
                    <div class="bottom-div">
                        <div id="constellatorySelect"></div>
                        <div class="chart-view constellatoryOpt text-center height100pc">
                            <div class="chartloadingmsg hide"></div>
                            <div class="whpc100 hide">
                                <div id="constellatory" class="chart height100pc"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">性格</div>
                    </div>

                    <div class="bottom-div">
                        <div id="personalitySelect"></div>
                        <div class="chart-view personalityOpt text-center height100pc">
                            <div class="chartloadingmsg hide"></div>
                            <div class="whpc100 hide">
                                <div id="personality" class="chart height100pc"></div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <!--人才结构指标设置 弹出框 begin-->
    <div class="ct-drag zd-window">
        <div class="drag-body">
            <div class="drag-content">
                <div class="drag-title">
                    <div class="drag-title-blue"></div>
                    <div class="drag-title-text">编制使用率预警设置</div>
                    <div class="cancelDragBtnEvent closeIcon"></div>
                </div>
                <div class="drag-middle ZBSetUp-drag-middle">
                    <div class="row ct-row">
                        <div class="col-sm-6 BSetUp-col">
                            <div class="BSetUp-line">
                                <div class="color-div green-color-div left"></div>
                                <span class="BSetUp-text-one left">正常值：</span>
                                <input id="zdWindowNormal" class="BSetUp-input left" type="text">
                                <span class="BSetUp-text-two left">%</span>
                            </div>
                        </div>
                        <div class="col-sm-6 BSetUp-col">
                            <div class="BSetUp-line">
                                <div class="color-div orange-color-div left"></div>
                                <span class="BSetUp-text-one left">风险值：</span>
                                <input id="zdWindowRisk" class="BSetUp-input left" type="text">
                                <span class="BSetUp-text-two left">%</span>
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
                    </div>
                </div>
                <div class="drag-bottom">
                    <div id="btnSaveRate" class="drag-btn initDragBtn">确定</div>
                    <div class="drag-btn cancelDragBtnEvent cancelDragBtn">取消</div>
                </div>
            </div>
        </div>
    </div>
    <!--人才结构指标设置 弹出框 end-->

    <!--详细名单列表 弹出框 begin-->
    <div class="modal fade" id="detailListDialog" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
                    </button>
                    <h4 class="modal-title">人员明细</h4>
                </div>
                <div class="modal-body page-content" id="detailListcontent" style="overflow-x: hidden; overflow-y: auto;">
                    <div class="search">
                        <label class="fl">组织架构：</label><span class="fl" id="dtOrg"></span>
                        <label class="fl">时间：</label><input class="fl" id="dtTime" value="" type="text" readonly="readonly"/>
                        <label class="fl">人群：</label><select class="fl" id="dtCrowd"></select>
                        <label class="fl" id="dtDimName"></label><select class="fl" id="dtDim"></select>
                        <button class="fl" type="button" id="dtSearch">搜索</button>
                    </div>
                    <div id="details">
                        <table id="detailList"></table>
                        <table id="detailPage"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--详细名单列表 弹出框 end-->

    <!--遮罩层 begin-->
    <div class="shade"></div>
    <!--遮罩层 end-->
</div>

<script type="text/javascript" src="${jsRoot}require.js"></script>
<script type="text/javascript" src="${jsRoot}lib/plugins/echarts/echarts.js"></script>
<script type="text/javascript" src="${jsRoot}biz/competency/talentStructure.js"></script>
</body>
</html>
