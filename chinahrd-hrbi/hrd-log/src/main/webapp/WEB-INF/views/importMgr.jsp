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
    <title>导出管理</title>

    <link rel="stylesheet" href="${ctx}/assets/datatables/user-manage.css">
</head>
<style>
    .btn-zone {
        cursor: pointer;
        border: 1px solid transparent;
        border-radius: 4px;
    }

    .btn-zone:hover {
        background-color: #c2c2c2;
    }

    .style-danger {
        background-color: #ebccd1;
    }

    .style-success {
        background-color: #d6e9c6;
    }

    .style-warning {
        background-color: #faebcc;
    }


</style>
<body>
<div class="bs-docs-header" id="content" tabindex="-1">
    <div class="container">
        <h1>导出管理</h1>
        <p>1：广深员工考勤导出(2016年~当前年)；2：调整带薪假期范围（2016年~明年）。</p>
    </div>
</div>
<div class="container bs-docs-container">
    <div class="container">
        <p>
            <span style="width:20px; height:20px; color:#ebccd1"><i class='fa fa-square fa-1x'></i></span>
            没有数据
            <span style="width:20px; height:20px; color:#d6e9c6"><i class='fa fa-square fa-1x'></i></span>
            已有数据
        </p>
        <p>*操作角色：超级管理员、某分公司管理员、员工</p>
        <p>*操作：点击月份下载</p>
        <div class="panel panel-info">
            <div class="panel-heading">
                <div class="pull-right">
                    <h3 class="panel-title">导出excel考勤</h3>
                </div>
                <div class="pull-left importExcelDropClass1 ">
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="panel-body">
                <div id="monthRowZone1Id" class="row">
                </div>
                <div id="monthRowZone2Id" class="row">
                </div>
                <div id="monthRowZone3Id" class="row ">
                </div>
            </div>
        </div>

        <p>
            <span style="width:20px; height:20px; color:#d6e9c6"><i class='fa fa-square fa-1x'></i></span>
            工作天
            <span style="width:20px; height:20px; color:#ebccd1"><i class='fa fa-square fa-1x'></i></span>
            周末
            <span style="width:20px; height:20px; color:#faebcc"><i class='fa fa-square fa-1x'></i></span>
            带薪假
        </p>
        <p>*操作角色：超级管理员、某分公司管理员</p>
        <p>*操作：点击天号设换带薪假期 (开发中)</p>
        <div class="panel panel-warning">
            <div class="panel-heading">
                <div class="pull-right ">
                    <h3 class="panel-title">变更带薪假期</h3>
                </div>
                <div class="pull-left importExcelDropClass2">
                </div>
                <div class="pull-left">
                    <div class="dropdown">
                        <button type="button" class="btn dropdown-toggle" id="dropMonthId" data-toggle="dropdown">
                        </button>
                        <ul class="dropdown-menu" role="menu" aria-labelledby="dropMonthId">
                            <li role="presentation">
                                <a class="monthDropClass" role="menuitem" tabindex="-1" href="#">01月</a>
                            </li>
                            <li role="presentation">
                                <a class="monthDropClass" role="menuitem" tabindex="-1" href="#">02月</a>
                            </li>
                            <li role="presentation">
                                <a class="monthDropClass" role="menuitem" tabindex="-1" href="#">03月</a>
                            </li>
                            <li role="presentation">
                                <a class="monthDropClass" role="menuitem" tabindex="-1" href="#">04月</a>
                            </li>
                            <li role="presentation">
                                <a class="monthDropClass" role="menuitem" tabindex="-1" href="#">05月</a>
                            </li>
                            <li role="presentation">
                                <a class="monthDropClass" role="menuitem" tabindex="-1" href="#">06月</a>
                            </li>
                            <li role="presentation">
                                <a class="monthDropClass" role="menuitem" tabindex="-1" href="#">07月</a>
                            </li>
                            <li role="presentation">
                                <a class="monthDropClass" role="menuitem" tabindex="-1" href="#">08月</a>
                            </li>
                            <li role="presentation">
                                <a class="monthDropClass" role="menuitem" tabindex="-1" href="#">09月</a>
                            </li>
                            <li role="presentation">
                                <a class="monthDropClass" role="menuitem" tabindex="-1" href="#">10月</a>
                            </li>
                            <li role="presentation">
                                <a class="monthDropClass" role="menuitem" tabindex="-1" href="#">11月</a>
                            </li>
                            <li role="presentation">
                                <a class="monthDropClass" role="menuitem" tabindex="-1" href="#">12月</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="panel-body">
                <div id="dayRowZone1Id" class="row"></div>
                <div id="dayRowZone2Id" class="row"></div>
                <div id="dayRowZone3Id" class="row"></div>
                <div id="dayRowZone4Id" class="row"></div>
                <div id="dayRowZone5Id" class="row"></div>
            </div>
        </div>
    </div>


    <!-- DataTables JS-->
    <script type="text/javascript" src="${ctx}/assets/js/lib/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/assets/biz/js/importMgr.js"></script>
</body>
</html>