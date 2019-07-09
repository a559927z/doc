<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>团队画像</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/teamImg2.css"/>
</head>
<body>

<div class="teamimg">
    <div class="rightBody">
        <div class="rightbodypage">

            <div class="row ct-row">

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">星座分布</div>
                    </div>
                    <div class="bottom-div">
                        <div class="chart-view text-center height100pc">
                            <div id="constellatory" class="chart height100pc"></div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">性格</div>
                    </div>

                    <div class="bottom-div">
                        <div class="chart-view text-center height100pc">
                            <div id="personality" class="chart height100pc"></div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">职级</div>
                    </div>

                    <div class="bottom-div">
                        <div class="chart-view text-center height100pc">
                            <div id="abilityLv" class="chart height100pc"></div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">工作地点</div>
                    </div>

                    <div class="bottom-div">
                        <div class="chart-view text-center height100pc">
                            <div id="workLocation" class="chart height100pc"></div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">男女比例</div>
                    </div>

                    <div class="bottom-div">
                        <div class="row">
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

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">婚姻状况</div>
                    </div>

                    <div class="bottom-div">
                        <div class="row">
                            <div class="col-xs-12" id="ms">
                                <div class="marry_state hide">
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

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">年龄</div>
                    </div>

                    <div class="bottom-div">
                        <div class="chart-view text-center height100pc">
                            <div id="age" class="chart height100pc"></div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">司龄</div>
                    </div>

                    <div class="bottom-div">
                        <div class="chart-view text-center height100pc">
                            <div id="companyAge" class="chart height100pc"></div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 ct-line-col SetUpBody" view="chart">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">血型</div>
                    </div>

                    <div class="bottom-div">
                        <div class="chart-view text-center height100pc">
                            <div id="blood" class="chart height100pc"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/js/biz/teamImg2.js"></script>
</body>
</html>
