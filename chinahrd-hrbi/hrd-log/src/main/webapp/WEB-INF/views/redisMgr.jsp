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
    .glyphiconColor {
        color: #0099cc;
    }
</style>
<body>

<div class="container">
    <h1>Redis控制台</h1>
    <p>ip:40.125.213.43  port:7000  auth:zrw1409</p>
    <p>存：</p>
    <div class="form-inline" role="form">
        <div class="form-group">
            <label class="sr-only" for="key">请输入键</label>
            <input type="text" name="key" class="form-control" id="key" placeholder="请输入键">
        </div>
        <div class="form-group">
            <label class="sr-only" for="value">请输入值</label>
            <input type="text" name="value" class="form-control" id="value" placeholder="请输入值">
        </div>
        <button id="saveBtn" class="btn btn-default">保存</button>
        <div class="form-group">
            <div id="rsSaveZone"/>
        </div>
    </div>

    <br/>
    <br/>
    <p>查：</p>
    <div class="form-inline" role="form">
        <div class="form-group">
            <label class="sr-only" for="key">请输入键</label>
            <input type="text" class="form-control" id="queryKey" placeholder="请输入键">
        </div>
        <button id="getBtn" class="btn btn-default">查询</button>
        <div class="form-group">
            <div id="rsGetZone"/>
        </div>
    </div>

    <br/>
    <br/>
    <p>删：</p>
    <div class="form-inline" role="form">
        <div class="form-group">
            <label class="sr-only" for="key">请输入键</label>
            <input type="text" class="form-control" id="delKey" placeholder="请输入键">
        </div>
        <button id="delBtn" class="btn btn-default">删除</button>
        <div class="form-group">
            <div id="rsDelZone"/>
        </div>
    </div>

    <br/>
    <br/>
    <p>目前所有键：</p>
    <div class="form-inline" role="form">
        <button id="refBtn" class="btn btn-default">刷新</button>
        <div style='margin: 0 auto;' id="div-table-container">
            <table class="table table-striped table-bordered table-hover table-condensed" id="table-user">
                <thead>
                <tr class="glyphiconColor">
                    <th width="100"><i class="fa fa-key"></i> 键</th>
                    <th><i class="fa fa-database"></i> 值</th>
                </tr>
                </thead>
                <tbody></tbody>
                <tfoot>
                <tr class="glyphiconColor">
                    <th width="100"><i class="fa fa-key"></i> 键</th>
                    <th><i class="fa fa-database"></i> 值</th>
                </tr>
                </tfoot>
            </table>
        </div>
        <div id="rsAllKeysZone"></div>
    </div>

</div>


<script type="text/javascript" src="${ctx}/assets/biz/js/redisMgr.js"></script>
</body>
</html>