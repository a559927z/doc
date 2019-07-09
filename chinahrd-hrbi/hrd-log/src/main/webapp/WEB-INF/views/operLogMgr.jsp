<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/views/include/top-min.jsp" %>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="description" content="overview & stats"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>日志平台</title>

    <link rel="stylesheet" href="${ctx}/assets/datatables/user-manage.css">
</head>
<style>
    .glyphiconColor {
        color: #0099cc;
    }

    .navbar.navbar-default {
        margin-bottom: 0px;
    }

    .text-none {
        color: #74f747;
    }

    .text-warn {
        color: #a94440;
    }

    .container {
        width: 1550px;
    }
</style>
<body>
<div class="bs-docs-header" id="content" tabindex="-1">
    <div class="container">
        <h1>操作日志</h1>
        <p>1：对考勤系统点击；2：一个请求完成时间记录日志。</p>
        <p>
            <i class="fa fa-spinner fa-pulse"></i> 用时/毫秒
            <span style="width:20px; height:20px; color:#a94440"><i class='fa fa-exclamation-triangle fa-1x'></i></span>
            > 500ms
            <span style="width:20px; height:20px; color:#74f747"><i class='fa fa-check fa-1x'></i></span>
            <= 500ms
        </p>
    </div>
</div>

<div class="container bs-docs-container">

    <div style='margin: 0 auto;' id="div-table-container">
        <table class="table table-striped table-bordered table-hover table-condensed" id="table-user">
            <thead>
            <tr class="glyphiconColor">
                <th><i class="fa fa-id-card-o"></i> 编号</th>
                <th><i class="fa fa-male"></i> 姓名</th>
                <th><i class="fa fa-university"></i> IP地址</th>
                <th><i class="fa fa-television"></i> URI</th>
                <th><i class="fa fa-tag"></i> method</th>
                <th><i class="fa fa-spinner fa-pulse"></i> 用时/毫秒</th>
                <th><i class="fa fa-clock-o"></i> 操作时间</th>
            </tr>
            </thead>
            <tbody></tbody>
            <tfoot>
            <tr class="glyphiconColor">
                <th><i class="fa fa-id-card-o"></i> 编号</th>
                <th><i class="fa fa-male"></i> 姓名</th>
                <th><i class="fa fa-university"></i> IP地址</th>
                <th><i class="fa fa-television"></i> URI</th>
                <th><i class="fa fa-tag"></i> method</th>
                <th><i class="fa fa-spinner fa-pulse"></i> 用时/毫秒</th>
                <th><i class="fa fa-clock-o"></i> 操作时间</th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>


<script type="text/javascript" src="${ctx}/assets/js/lib/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${ctx}/assets/lhgdialog-4.2.0/lhgdialog.js?skin=bootstrap2"></script>
<script type="text/javascript" src="${ctx}/assets/datatables/constant.js"></script>
<script type="text/javascript" src="${ctx}/assets/biz/js/operLogMgr.js"></script>
</html>