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
</head>
<style>
    .top-row-col {
        padding: 50px 60px;
        background-color: #0099cc;
        box-shadow: inset 1px -1px 1px #444, inset -1px 1px 1px #444;
    }

    .btn-zone {
        /* 	width: 200px; */
        height: 100px;
        /*line-height: 200px;*/
        /*text-align: center;*/
        font:font;
        /*font-size: 16px;*/
        /*color: #fff;*/
    }

    .btn-zone a {
        font: bold;
        /*font-size: 16px;*/
        color: #fff;
    }
</style>
<body>

<div class="container">
    <h1>日志系统</h1>
    <div class="row ">
        <div class="col-md-4 top-row-col">
            <div class="btn-zone">
                <h2><a href="${ctx}/log/operLogMgr/index.do" target="_blank">操作日志</a></h2>
                <p class="text-left">操作角色：员工</p>
            </div>
        </div>
        <div class="col-md-4 top-row-col">
            <div class="btn-zone">
                <h2><a href="${ctx}/log/empVactionLogMgr/index.do" target="_blank">剩余假期汇总</a></h2>
                <p class="text-left">操作角色：员工</p>
            </div>
        </div>
        <div class="col-md-4 top-row-col">
            <div class="btn-zone">
                <h2><a href="${ctx}/log/empOtherDayLogMgr/index.do" target="_blank">休假明细</a></h2>
                <p class="text-left">操作角色：员工</p>
            </div>
        </div>
    </div>
    <div class="row ">
        <div class="col-md-4 top-row-col">
            <div class="btn-zone">
                <h2><a href="${ctx}/log/toTomcatLog" target="_blank">Tomcat日志</a></h2>
                <p class="text-left">操作角色：超级管理员、DBA</p>
            </div>
        </div>
        <div class="col-md-4 top-row-col">
            <div class="btn-zone">
                <%--<i class="fa fa-address-book-o"></i>--%>
                <h2><a href="${ctx}/emp/index.do" target="_blank">员工管理</a></h2>
                <p>操作角色：超级管理员、某分公司管理员</p>
            </div>
        </div>
        <div class="col-md-4 top-row-col">
            <div class="btn-zone">
                <%--<i class="fa fa-address-book-o"></i>--%>
                <h2><a href="${ctx}/import/index.do" target="_blank">考勤导出</a></h2>
                <p>操作角色：超级管理员、某分公司管理员、员工</p>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>