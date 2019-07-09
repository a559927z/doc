<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/views/include/top-min.jsp" %>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="description" content="overview & stats"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>日志平台</title>
</head>
<style>
    .glyphiconColor {
        color: #0099cc;
    }
</style>
<body>
<div class="bs-docs-header" id="content" tabindex="-1">
    <div class="container">
        <h1>休假明细</h1>
        <p>1：本年度所有员工假期情况记录日志。</p>
    </div>
</div>


<div class="container bs-docs-container">

    <div style='margin: 0 auto;' id="div-table-container">
        <table class="table table-striped table-bordered table-hover table-condensed" id="table-user">
            <thead>
            <tr class="glyphiconColor">
                <th><i class="fa fa-id-card-o"></i> 编号</th>
                <th><i class="fa fa-male"></i> 姓名</th>
                <th>类型</th>
                <th>天数</th>
                <th>日期</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
</div>
</body>
<script type="text/javascript" src="${ctx}/assets/datatables/constant.js"></script>
<script type="text/javascript" src="${ctx}/assets/biz/js/empOtherDayLogMgr.js"></script>
</html>