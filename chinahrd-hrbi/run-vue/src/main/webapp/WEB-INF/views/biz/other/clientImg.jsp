<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>客户画像</title>
    <link rel="stylesheet" href="${ctx}/assets/css/select2/select2.min.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/other/clientImg.css">
</head>
<body class="overflow-hidden">
<div class="graph-layout">
    <div class="col-xs-12">
        <div class="graph-header">

            <div class="btn-group">
                <button data-toggle="dropdown" class="btn dropdown-toggle">
                    员工姓名
                    <span class="icon-caret-down icon-on-right"></span>
                </button>
                <ul class="dropdown-menu dropdown-default">
                    <li><a href="javascript:void(0);">员工姓名</a></li>
                    <li><a href="javascript:void(0);">客户名称</a></li>
                </ul>
            </div>
            <label for="selectEmp">
                <select id="selectEmp" class="width150"></select>
            </label>
        </div>
    </div>
    <div id="chart" class="col-xs-10 ct-col1"></div>
    <div class="col-xs-2 ct-col1 pull-right" id="settingBlock">
        <div class="panel panel-default setting-main" id="settingMain">
            <div class="panel-body ct-col3" id="setting0">
                <div class="setting-header">
                    <i class="icon-cog"></i>
                    设置
                </div>
                <div class="setting-btns">
                    <span class="btn-layout">关系模式</span>
                    <span class="btn-layout">组织模式</span>
                    <span class="btn-layout">排序模式</span>
                </div>
                <div class="setting-demos">

                </div>
                <div class="setting-header">
                    <i class="icon-cog"></i>
                    凸显
                </div>
                <div class="setting-btns">
                    <span class="btn-layout">新增客户</span>
                    <span class="btn-layout">核心客户</span>
                    <span class="btn-layout">最近更新</span>
                    <span class="btn-layout">待激活</span>
                    <span class="btn-layout">更多选项</span>
                </div>
                <div class="setting-header">
                    <i class="icon-cog"></i>
                    筛选
                </div>
                <div class="setting-btns">
                    <span class="btn btn-xs">...</span>
                </div>
            </div>

            <div class="panel-body ct-col3 hide" id="setting1">
                <div class="setting-header">
                    <i class="icon-cog"></i>
                    排序
                </div>
                <div class="setting-btns">
                    <span class="btn-layout">按类别</span>
                    <span class="btn-layout">按产品</span>
                    <span class="btn-layout">按月份</span>
                </div>
                <div class="setting-demos"></div>
                <div class="setting-header">
                    <i class="icon-cog"></i>
                    客户排序
                </div>
                <div class="setting-btns">
                    <span class="btn-layout">新增客户</span>
                    <span class="btn-layout">核心客户</span>
                    <span class="btn-layout">最近更新</span>
                    <span class="btn-layout">待激活</span>
                    <span class="btn-layout">更多选项</span>
                </div>
            </div>
        </div>

        <div class="panel panel-default col-xs-12  ct-col3" id="conditionBlock">
            <div class="panel-body ct-col3">
                <div class="setting-header">
                    <i class="icon-comments-alt"></i>
                    销售预测
                </div>
                <div id="conditionMain"></div>
            </div>
        </div>
    </div>
    <div class="col-xs-12 ct-col1" id="detailBlock">
        <div class="panel panel-default" id="detail0">
            <div class="panel-body ct-col">
                <div class="col-xs-2 ct-container-fluid">
                    <div class="money-layout">
                        <span class="money-title">销售总额</span>
                        <span class="money-num" id="manageSalesMoney">0</span>
                        <div class="accord-bottom-float-arrow accord-bottom-float-value-drop"></div>
                        <%--<span class="money-unit">万</span>--%>
                    </div>
                    <div class="money-layout">
                        <span class="money-title">销售利润</span>
                        <span class="money-num" id="manageSalesProfit">0</span>
                        <div class="accord-bottom-float-arrow accord-bottom-float-value-rise"></div>
                        <%--<span class="money-unit">万</span>--%>
                    </div>
                    <div class="money-layout">
                        <span class="money-title">回款总额</span>
                        <span class="money-num" id="manageReturnAmount">0</span>
                        <%--<span class="money-unit">万</span>--%>
                    </div>
                </div>
                <div class="col-xs-8 ct-col1">
                    <div class="col-xs-4 ct-col1" id="trendChart"></div>
                    <div class="col-xs-4 ct-col1" id="totalChart"></div>
                    <div class="col-xs-4 ct-col1" id="analyzeChart"></div>
                </div>
                <div class="col-xs-2 ct-col1">
                    <div class="icon-layout">
                        <span class="img-find" id="findImg"></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel panel-default hide" id="detail1">
            <div class="panel-body ct-col">
                <div class="col-xs-2 ct-container-fluid">
                    <div class="money-layout">
                        <span class="money-title">销售总额</span>
                        <span class="money-num" id="saleSalesMoney">0</span>
                        <div class="accord-bottom-float-arrow accord-bottom-float-value-rise"></div>
                        <%--<span class="money-unit">万</span>--%>
                    </div>
                    <div class="money-layout">
                        <span class="money-title">销售利润</span>
                        <span class="money-num" id="saleSalesProfit">0</span>
                        <div class="accord-bottom-float-arrow accord-bottom-float-value-drop"></div>
                        <%--<span class="money-unit">万</span>--%>
                    </div>
                    <div class="money-layout">
                        <span class="money-title">回款总额</span>
                        <span class="money-num" id="saleReturnAmount">0</span>
                        <%--<span class="money-unit">万</span>--%>
                    </div>
                </div>
                <div class="col-xs-8 ct-container-fluid"></div>
                <div class="col-xs-2 ct-col1">
                    <div class="icon-layout">
                        <span class="img-find"></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="hide">
    <svg id="svgObj" width="140" height="140" xmlns:xlink="http://www.w3.org/1999/xlink"
         xmlns="http://www.w3.org/2000/svg">
        <defs>
            <filter id="f1">
                <feGaussianBlur in="SourceGraphic" stdDeviation="5" />
            </filter>
        </defs>
        <g>
            <circle id="bigCircle" cx="70" cy="70" r="50"  class="big-circle"  />
            <circle id="bigCircleFilter" cx="70" cy="70" r="60" filter="url(#f1)" fill-opacity="0.3"  />
            <circle id="smallCircle" cx="90" cy="25" r="12" style="stroke: #d73f38; fill: #d73f38;"/>
            <text id="svgTxt" style="font-size:14pt;fill:#fff;" x="85" y="30">2</text>
        </g>
    </svg>
    <%--<svg id="svgObj" width="120" height="120" xmlns:xlink="http://www.w3.org/1999/xlink"--%>
         <%--xmlns="http://www.w3.org/2000/svg">--%>
        <%--<defs>--%>
            <%--<pattern id="avatar" width="100%" height="100%" patternContentUnits="userSpaceOnUse">--%>
                <%--<image width="100" height="100" xlink:href="${ctx}/assets/img/demo/graph/0.png"/>--%>
            <%--</pattern>--%>
        <%--</defs>--%>
        <%--<g>--%>
            <%--<circle id="bigCircle" cx="52" cy="52" r="51" fill="url(#avatar)" class="big-circle"  />--%>
            <%--&lt;%&ndash;<rect x="2" y="2" width="100" height="100" fill="url(#avatar)" />&ndash;%&gt;--%>
            <%--<circle id="smallCircle" cx="80" cy="15" r="12" style="stroke: #d73f38; fill: #d73f38;"/>--%>
            <%--<text id="svgTxt" style="font-size:14pt;fill:#fff;" x="76" y="22">2</text>--%>
        <%--</g>--%>
    <%--</svg>--%>
</div>
<script src="${jsRoot}require2.js"></script>
<script src="${jsRoot}biz/other/clientImg.js"></script>
</body>
</html>