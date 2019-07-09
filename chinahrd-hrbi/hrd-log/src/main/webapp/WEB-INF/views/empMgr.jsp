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
</style>
<body>
<div class="bs-docs-header" id="content" tabindex="-1">
    <div class="container">
        <h1>员工管理</h1>
        <p>1：删除离职员工；2：补发员工年假；</p>
    </div>
</div>
<div class="container bs-docs-container">
    <div class="block info-block" id="user-view">
        <div class="container">
            <div class="row ">
                <span class="hidden" id="userId-view"></span>
                <div class="col-md-4 top-row-col">
                    <h4 class="prop-name"><i class="fa fa-id-card-o"></i> 账号：
                        <small class="prop-value" id="userKey-view"></small>
                    </h4>
                </div>
                <div class="col-md-4 top-row-col">
                    <h4 class="prop-name"><i class="fa fa-male"></i> 姓名：
                        <small class="prop-value" id="userNameCh-view"></small>
                    </h4>
                </div>
                <div class="col-md-4 top-row-col">
                    <h4 class="prop-name"><i class="fa fa-group"></i> 角色：
                        <small class="prop-value" id="role-view"></small>
                    </h4>
                </div>
            </div>
            <div class="row ">
                <div class="col-md-4 top-row-col">
                    <h4 class="prop-name"><i class="fa fa-university"></i> 分公司：
                        <small class="prop-value" id="orgParName-view"></small>
                    </h4>

                </div>
                <div class="col-md-4 top-row-col">
                    <h4 class="prop-name"><i class="fa fa-sitemap"></i> 部门：
                        <small class="prop-value" id="orgName-view"></small>
                    </h4>
                </div>
                <h4 class="prop-name"><i class="fa fa-suitcase"></i> 职位：
                    <small class="prop-value" id="positionName-view"></small>
                </h4>

            </div>
            <div class="row ">
                <div class="col-md-4 top-row-col">
                    <h4 class="prop-name"><i class="fa fa-clock-o"></i> 入职时间：
                        <small class="prop-value" id="entry-date-view"></small>
                    </h4>
                </div>

                <div class="col-md-4 top-row-col">
                    <h4 class="prop-name"><i class="fa fa-phone"></i> 电话：
                        <small class="prop-value" id="telPhone-view"></small>
                    </h4>
                </div>

                <div class="col-md-4 top-row-col">
                    <h4 class="prop-name"><i class="fa fa-credit-card"></i> 身份证：
                        <small class="prop-value" id="card-view"></small>
                    </h4>
                </div>
            </div>
            <div class="row ">
                <div class="col-md-4 top-row-col">
                    <h4 class="prop-name"><i class="fa fa-smile-o"></i> 本年度年假：
                        <small class="prop-value" id="annualTotal-view"></small>
                        <i class="fa fa-cog fa-spin" id="showAnnualModalBtn"></i>
                    </h4>
                </div>
                <div class="col-md-4 top-row-col">
                    <h4 class="prop-name"><i class="fa fa-bicycle"></i> 剩余年假：
                        <small class="prop-value" id="annual-view"></small>
                    </h4>
                </div>
                <div class="col-md-4 top-row-col">
                    <h4 class="prop-name"><i class="fa fa-coffee"></i> 剩余内部调休假：
                        <small class="prop-value" id="canLeave-view"></small>
                    </h4>
                </div>
            </div>
            <div class="row ">
                <div class="col-md-6 top-row-col">
                    <h4 class="prop-name"><i class="fa fa-smile-o"></i> 可调休天数(剩余年假+剩余内部调休假)：
                        <small class="prop-value" id="total-view"></small>
                    </h4>
                </div>
                <div class="col-md-6 top-row-col">
                    <h4 class="prop-name"><i class="fa fa-home"></i> 住址：
                        <small class="prop-value" id="address-view"></small>
                    </h4>
                </div>

            </div>
        </div>
    </div>

    <%--<button id="batch-btn-del" type="button" class="btn btn-default btn-sm">批量删除   <span class="glyphicon glyphicon-remove glyphiconColor"></span></button>--%>
    <div style='margin: 0 auto;' id="div-table-container">
        <table class="table table-striped table-bordered table-hover table-condensed" id="table-user">
            <thead>
            <tr class="glyphiconColor">
                <th>
                    <input type="checkbox" name="cb-check-all">
                </th>
                <th><i class="fa fa-id-card-o"></i> 编号</th>
                <th><i class="fa fa-male"></i> 名称</th>
                <th><i class="fa fa-envelope-o"></i> 邮箱</th>
                <th><i class="fa fa-lock"></i> 账号状态</th>
                <th><i class="fa fa-cog fa-spin"></i> 操作</th>
            </tr>
            </thead>
            <tbody></tbody>
            <tfoot>
            <tr class="glyphiconColor">
                <th>
                    <input type="checkbox" name="cb-check-all">
                </th>
                <th><i class="fa fa-id-card-o"></i> 编号</th>
                <th><i class="fa fa-male"></i> 名称</th>
                <th><i class="fa fa-envelope-o"></i> 邮箱</th>
                <th><i class="fa fa-lock"></i> 账号状态</th>
                <th><i class="fa fa-cog fa-spin"></i> 操作</th>
            </tr>
            </tfoot>
        </table>
    </div>
</div


        <!-- 模态框（Modal） -->
<div class="modal fade" id="updateAnnualTotalModal" tabindex="-1" role="dialog"
     aria-labelledby="updateAnnualTotalModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="width: 300px;">
        <div class="modal-content">
            <div class="modal-body">
                设置年假：<input id="setAnnualVal" type="test"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    关闭
                </button>
                <button id="updateAnnualBtn" type="button" class="btn btn-primary">
                    保存
                </button>
            </div>
        </div>
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- SpinJS-->
<%--<script type="text/javascript" src="${ctx}/asset/spin-2.1.0/jquery.spin.merge.js"></script>--%>
<!-- DataTables JS-->
<script type="text/javascript" src="${ctx}/assets/js/lib/jquery/jquery-1.11.1.min.js"></script>
<%--<script type="text/javascript" src="${ctx}/assets/lhgdialog-4.2.0/lhgdialog.js?skin=bootstrap2"></script>--%>

<script type="text/javascript" src="${ctx}/assets/datatables/constant.js"></script>
<script type="text/javascript" src="${ctx}/assets/biz/js/empMgr.js"></script>
</body>
</html>