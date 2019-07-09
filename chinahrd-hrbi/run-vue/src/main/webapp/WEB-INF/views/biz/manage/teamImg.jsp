<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>团队画像</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/teamImg.css"/>
</head>
<body>
    <div class="page-content" id="main-container">
        <div class="main-container-inner">
            <div class="row column">
                <div class="col-xs-3">
                    <ul class="ztree" id="tree"></ul>
                </div>
                <div class="col-offset-xs-8">
                    <div class="update-time">更新时间：&nbsp;<span id="updateTime">
                        <fmt:formatDate value="${updateDate}" pattern="yyyy/MM/dd HH:mm"></fmt:formatDate></span>&nbsp;&nbsp;
                    </div>
                </div>
                <input type="hidden" id="reqOrganId" value="${reqOrganId}">
                <input type="hidden" id="reqOrganName" value="${reqOrganName}">
                <input type="hidden" id="entryAnnual" value="${entryAnnual}">
            </div>

            <div class="row column">
                <div class="tab-pane col-sm-6 col-xs-12">
                    <div class="widget-box transparent f-white-bg">
                        <div class="widget-header widget-header-flat">
                            <h4 class="bolder"><i class="static_icon"></i>星座分布</h4>
                        </div>
                        <div class="widget-body">
                            <div class="row">
                                <div class="col-xs-12">
                                    <div class="constellatory-bar" id="constellatory"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="tab-pane col-sm-6 col-xs-12">
                    <div class="widget-box transparent f-white-bg">
                        <div class="widget-header widget-header-flat">
                            <h4 class="bolder"><i class="static_icon"></i>性格</h4>
                        </div>
                        <div class="widget-body">
                            <div class="row">
                                <div class="col-xs-12">
                                    <div class="personality-pie" id="personality"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="tab-pane col-sm-6 col-xs-12">
                    <div class="widget-box transparent f-white-bg">
                        <div class="widget-header widget-header-flat">
                            <h4 class="bolder"><i class="static_icon"></i>职级</h4>
                        </div>
                        <div class="widget-body">
                            <div class="row">
                                <div class="col-xs-12">
                                    <!-- <div class="" style="width:500px; height:280px;  background-color:red;"> -->
                                    <div class="abilityLv-pie" id="abilityLv">

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="tab-pane col-sm-6 col-xs-12">
                    <div class="widget-box transparent f-white-bg">
                        <div class="widget-header widget-header-flat">
                            <h4 class="bolder"><i class="static_icon"></i>工作地点</h4>
                        </div>
                        <div class="widget-body">
                            <div class="row">
                                <div class="col-xs-12">
                                    <div class="workLocation-pie" id="workLocation"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="tab-pane col-sm-6 col-xs-12">
                    <div class="widget-box transparent f-white-bg">
                        <div class="widget-header widget-header-flat">
                            <h4 class="bolder"><i class="static_icon"></i>男女比例</h4>
                        </div>
                        <div class="widget-body">
                            <div class="row">
                                <div class="col-xs-12" id="sex_div">
                                    <div class="sex-pie" id="sex"></div>
                                    <img src="${ctx}/assets/img/manage/teamImg-sex.png" class="teamImg-sex"></img>
                                    <div class="lidyZone">
                                        <div>女</div>
                                        <div><span class="lidyVal"></span><span>人</span></div>
                                    </div>
                                    <div class="manZone">
                                        <div>男</div>
                                        <div><span class="manVal"></span><span>人</span></div>
                                    </div>
                                </div>


                            </div>
                        </div>
                    </div>
                </div>

                <div class="tab-pane col-sm-6 col-xs-12">
                    <div class="widget-box transparent f-white-bg">
                        <div class="widget-header widget-header-flat">
                            <h4 class="bolder"><i class="static_icon"></i>婚姻状况</h4>
                        </div>
                        <div class="widget-body" id="ms_div">
                            <div class="row">
                                <div class="col-xs-12" id="ms">
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

                <div class="tab-pane col-sm-6 col-xs-12">
                    <div class="widget-box transparent f-white-bg">
                        <div class="widget-header widget-header-flat">
                            <h4 class="bolder"><i class="static_icon"></i>年龄</h4>
                        </div>
                        <div class="widget-body">
                            <div class="row">
                                <div class="col-xs-12">
                                    <div class="age-bar" id="age"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="tab-pane col-sm-6 col-xs-12">
                    <div class="widget-box transparent f-white-bg">
                        <div class="widget-header widget-header-flat">
                            <h4 class="bolder"><i class="static_icon"></i>司龄</h4>
                        </div>
                        <div class="widget-body">
                            <div class="row">
                                <div class="col-xs-12">
                                    <div class="companyAge-bar" id="companyAge"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="tab-pane col-sm-6 col-xs-12">
                    <div class="widget-box transparent f-white-bg">
                        <div class="widget-header widget-header-flat">
                            <h4 class="bolder"><i class="static_icon"></i>血型</h4>
                        </div>
                        <div class="widget-body">
                            <div class="row">
                                <div class="col-xs-12">
                                    <div class="blood-pie" id="blood"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/js/biz/teamImg.js"></script>
</body>
</html>
