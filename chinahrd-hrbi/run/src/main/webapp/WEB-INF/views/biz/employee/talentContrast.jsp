<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<html>
<head>
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>人员对比</title>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/employee/talentContrast.css"/>
</head>
<body>
<div class="contrast" id="contrast">
    <div class="menu">
        <input type="hidden" id="empIds" value="${empIds}">
        <ul class="menu-list">
            <li position="float0" class="menu-line menu-line1 menu-line-select">
                <div class="menu-line-spor menu-line-spor1"></div>
                <span class="menu-line-text">回顶部</span></li>
            <li position="float1" class="menu-line menu-line2">
                <div class="menu-line-spor"></div>
                <span class="menu-line-text">个人信息</span></li>
            <li position="float2" class="menu-line menu-line2">
                <div class="menu-line-spor"></div>
                <span class="menu-line-text">职务信息</span>
            </li>
            <li position="float3" class="menu-line menu-line2">
                <div class="menu-line-spor"></div>
                <span class="menu-line-text">个人绩效</span>
            </li>
            <li position="float4" class="menu-line menu-line2">
                <div class="menu-line-spor"></div>
                <span class="menu-line-text">个人能力</span>
            </li>
            <li position="float5" class="menu-line menu-line2">
                <div class="menu-line-spor"></div>
                <span class="menu-line-text">工作经历</span>
            </li>
            <li position="float6" class="menu-line menu-line3">
                <div class="menu-line-spor menu-line-spor2"></div>
                <span class="menu-line-text">成长轨迹</span>
            </li>
        </ul>
    </div>
    <div class="contrast-right">
        <div class="row ct-row top-fa">
            <div class="col-xs-2 ct-col1 top-obj top-left">
                <div class="top-left-text1">对比人员</div>
                <div class="top-left-text2">最多可添加4名成员
                </div>
            </div>
            <div class="col-xs-10 ct-col1 top-right" id="contrastObj">
                <div class="col-xs-3 ct-col1 top-obj top-div"></div>
                <div class="col-xs-3 ct-col1 top-obj top-div"></div>
                <div class="col-xs-3 ct-col1 top-obj top-div"></div>
                <div class="col-xs-3 ct-col1 top-obj top-div"></div>
            </div>
        </div>

        <div class="row ct-row" id="accordion">
            <div class="col-xs-12 ct-col1 ct-col-contrast">
                <div class="contrast-table-top float1">
                    <div class="contrast-table-switch contrast-table-reduce"></div>
                    <div class="contrast-table-switch contrast-table-add"></div>
                    <span class="contrast-table-title">个人信息</span>
                </div>
                <table class="contrast-table">
                    <tr id="sexRow">
                        <td class="contrast-td-one">性别</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr id="ageRow">
                        <td class="contrast-td-one">年龄</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr id="marryStatusRow">
                        <td class="contrast-td-one">婚否</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr id="degreeRow">
                        <td class="contrast-td-one">学历</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>

                <div class="contrast-table-top contrast-table-top-too float2">
                    <div class="contrast-table-switch contrast-table-reduce"></div>
                    <div class="contrast-table-switch contrast-table-add"></div>
                    <span class="contrast-table-title">职务信息</span>
                </div>
                <table class="contrast-table">
                    <tr id="organParentRow">
                        <td class="contrast-td-one">(分)公司</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr id="organRow">
                        <td class="contrast-td-one">部门</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr id="entryDateRow">
                        <td class="contrast-td-one">入职时间</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr id="sequenceRow">
                        <td class="contrast-td-one">职位主序列</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr id="sequenceSubRow">
                        <td class="contrast-td-one">职位子序列</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr id="abilityRow">
                        <td class="contrast-td-one">职位层级</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr id="rankRow">
                        <td class="contrast-td-one">职级</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr id="postionAssumeDateRow">
                        <td class="contrast-td-one">现岗位任职时间</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr id="positionRow">
                        <td class="contrast-td-one">现岗位</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>

                <div class="contrast-table-top contrast-table-top-too float3">
                    <div class="contrast-table-switch contrast-table-reduce"></div>
                    <div class="contrast-table-switch contrast-table-add"></div>
                    <span class="contrast-table-title">个人绩效</span>
                </div>
                <table class="contrast-table" id="collapseThree">
                    <tr>
                        <td class="contrast-td-one">绩效轨迹<input type="hidden" value="${performanceStr}"
                                                               id="performanceStr"></td>
                        <td id="perfTrackChart1"></td>
                        <td id="perfTrackChart2"></td>
                        <td id="perfTrackChart3"></td>
                        <td id="perfTrackChart4"></td>
                    </tr>
                </table>

                <div class="contrast-table-top contrast-table-top-too float4">
                    <div class="contrast-table-switch contrast-table-reduce"></div>
                    <div class="contrast-table-switch contrast-table-add"></div>
                    <span class="contrast-table-title">个人能力</span>
                </div>
                <table class="contrast-table" id="dimensionId">
                    <tr id="evalYearId">
                        <td class="contrast-td-one">测评年度</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td colspan="5" class="contrast-td-five">能力维度</td>
                    </tr>
                </table>

                <div class="contrast-table-top contrast-table-top-too float5">
                    <div class="contrast-table-switch contrast-table-reduce"></div>
                    <div class="contrast-table-switch contrast-table-add"></div>
                    <span class="contrast-table-title">工作经历</span>
                </div>
                <table class="contrast-table">
                    <tr id="departChange">
                        <td class="contrast-td-one">本公司经历</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr id="pastResume">
                        <td class="contrast-td-one">过往履历</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>

                <div class="contrast-table-top contrast-table-top-too float6">
                    <div class="contrast-table-switch contrast-table-reduce"></div>
                    <div class="contrast-table-switch contrast-table-add"></div>
                    <span class="contrast-table-title">成长轨迹</span>
                </div>
                <table class="contrast-table">
                    <tr id="growthLinkArea">
                        <td class="contrast-td-one">查看成长轨迹</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>

    <div id="img-window" class="hide">
        <div class="top-ct-circle">
            <img class="img-circle img-rc-head" src="${ctx}/assets/img/index/head2.png">
        </div>
        <div class="top-div-float">
            <div class="top-div-btn">搜索添加
                <div class="top-div-btn-add"></div>
            </div>
        </div>
    </div>

    <!--添加员工 弹出框 begin-->
    <div id="search-modal" class="modal fade popup-modal" data-backdrop="static" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-header-text">添加人员<input type="hidden" id="search-index"></div>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                </div>
                <div class="modal-body" style="min-height: 540px;">
                    <div class="col-xs-12 ct-col1">
                        <div class="yj-search">
                            <input type="text" class="yj-search-input" id="search-txt" placeholder="请输入员工ID/姓名"/>
                            <div class="yj-search-input-btn" id="search-btn">人员搜索</div>
                        </div>
                        <span class="search-text">*&nbsp;&nbsp;姓名支持模糊查询</span>
                    </div>
                    <div class="col-xs-12 ct-col1 add-middle" id="searchEmpTable">
                        <table id="searchEmpGrid"></table>
                        <div id="gridPager"></div>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
    <!--添加员工 弹出框 end-->

    <!-- 成长轨迹弹出框 -->
    <div id="growModal" class="modal fade popup-modal" data-backdrop="static" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-header-text">个人成长轨迹 —<span></span></div>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                </div>
                <div class="modal-body">
                    <div class="col-xs-12 ct-col1">
                        <div id="jobChangeChart" class="col-xs-12 ct-col1"></div>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
    <!--成长轨迹 弹出框 end-->

    <!--遮罩层 begin-->
    <div class="shade"></div>
    <!--遮罩层 end-->
</div>
<script src="${jsRoot}lib/echarts/echarts.js"></script>
<script src="${jsRoot}biz/employee/talentContrast.js"></script>
</body>
</html>