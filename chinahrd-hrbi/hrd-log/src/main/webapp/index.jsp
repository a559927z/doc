<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@include file="/WEB-INF/views/include/checkVersion.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>门户入口</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="icon" href="${ctx}/favicon.ico" type="image/x-icon">
    <link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${ctx}/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <script src="${ctx}/assets/js/lib/jquery/jquery-1.11.1.min.js"></script>
    <script src="${ctx}/assets/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <style>
        .panel-primary .list-group .list-group-item {
            cursor: pointer;
        }

        .panel-link {
            position: absolute;
            right: 25px;
            top: 10px;
        }

        .panel-link a {
            color: #e0e0e0;
        }

        .sm {
            font-size: 12px;
            color: #999999;
        }

        .versionNumber {
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row">&nbsp;</div>
    <div class="row">
        <div class="col-xs-4">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">生产环境</h3>
                </div>
                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item"><a href=${zrwURL} target="_blank">中人网官网</a>
                            <br/>
                            <span class="sm">使命：启迪智慧，传承仁爱</span>
                            <%--<br/>--%>
                            <%--<span class="sm">愿景：</span>--%>
                            <%--<br/>--%>
                            <%--<span class="sm">助每个人成为更幸福的自己，助每家组织成为更美好的所在</span>--%>
                            <%--<br/>--%>
                            <%--<span class="sm">核心价值观：</span>--%>
                            <%--<br/>--%>
                            <%--<span class="sm">诚信（诚：有话直说，信：有诺必践）</span>--%>
                        </li>
                    </ul>
                    <ul class="list-group">
                        <li class="list-group-item"><a href=${outTr} target="_blank">our.tm</a>
                            <br/>
                            <span class="sm">我的项目</span>
                        </li>
                    </ul>
                    <ul class="list-group">
                        <li class="list-group-item"><a href=${caibaoURL} target="_blank">对标平台</a>
                            <br/>
                            <span class="sm">企业HR数据对标平台（调研）</span>
                        </li>
                    </ul>
                    <ul class="list-group">
                        <li class="list-group-item"><a href=${hrbiURL} target="_blank">才报平台(演示版系统)</a>
                            <br/>
                            <span class="sm">才报平台对外发布版，最新版本为V1.8 </span>
                            <br/>
                            <span class="sm">账号密码：gzt/zrw1409</span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-xs-4">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">面向内部员工</h3>
                </div>
                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item"><a href=${eadURL}  target="_blank">考勤系统</a>
                            <br/>
                            <span class="sm">中人网内部才报系统</span>
                            <br/>
                            <span class="sm">账号密码：邮箱@前面一段/123456</span>
                        </li>
                    </ul>
                    <ul class="list-group">
                        <li class="list-group-item">
                            <a href="${svnURL} target=" _blank">SVN</a>
                            <br/>
                            <span class="sm">广深分公司代码/文档本版控制</span>
                            <br/>
                            <span class="sm">**:仓库目录</span>
                            <br/>
                            <span class="sm">http://svn.gs.chinahrd.net/svn/**</span>
                        </li>
                    </ul>
                    <ul class="list-group">
                        <li class="list-group-item"><a href="${gitlabURL}" target="_blank">GitLab版本管理</a>
                            <br/><span class="sm">GitLab是利用 Ruby on Rails 一个开源的版本管理系统，实现一个自托管的Git项目仓库</span>
                        </li>
                    </ul>
                    <ul class="list-group">
                        <li class="list-group-item">
                            <a href="" data-toggle="modal" data-target="#authUserModal">日志系统</a>
                            <br/>
                            <span class="sm">各平台操作日志</span>
                            <br/>
                            <span class="sm">账号密码：考勤系统一样</span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-xs-4">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">开发/测试工具</h3>
                    <div class="panel-link"><a href="assets/开启hrbi权限.bat">下载权限脚本（仅限广州本地）</a></div>
                </div>
                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item"><a href="${nexusURL}" target="_blank">Maven私服</a>
                            <br/><span class="sm">Nexus简化了自己内部仓库的维护和外部仓库的访问</span>
                            <br/><span class="sm">账号密码：admin/admin123</span>
                        </li>
                    </ul>
                    <ul class="list-group">
                        <li class="list-group-item"><a href="${solrURL}" target="_blank">Solr全文检索</a>
                            <br/><span class="sm">集成v_dim_emp数据索引</span>
                        </li>
                    </ul>
                    <ul class="list-group">
                        <li class="list-group-item"><a href="${redisURL}" target="_blank">Redis缓存数据</a>
                            <br/><span class="sm">分配物理内存三分一</span>
                            <br/><span class="sm">auth：zrw1409</span>
                        </li>
                    </ul>
                    <ul class="list-group">
                        <li class="list-group-item"><a href="http://172.16.9.80/zentao/" target="_blank">禅道</a>
                            <br/><span class="sm">才报BUG管理工具（部署在广州）</span>
                        </li>
                    </ul>
                    <ul class="list-group">
                        <li class="list-group-item"><a href="http://172.16.9.80:9090/" target="_blank">才报原型</a>
                            <br/><span class="sm">才报原型（部署在广州）</span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-xs-12">
            <div class="panel panel-success">
                <div class="panel-heading">
                    <h3 class="panel-title">服务器介绍</h3>
                </div>
                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item"><b>北京服务器：</b><span class="sm">代码管理svn、才报平台数据库、才爆平台数据库、手环数据库、应用服务器、nginx代理服务器、jenkin代码自动构建持续集成服务、项目管理系统等多个系统、全文检索索引elasticseach等服务。</span>
                        </li>
                        <li class="list-group-item"><b>广州服务器：</b><span class="sm">才报平台数据库（开发库）、应用服务器、全文检索索引elasticseach、axure原型服务、禅道服务。</span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>


    <div class="col-sm-12 ct-col versionNumber">
        <span>${sysVersion}</span>
    </div>
</div>


<!-- 模态框（Modal） -->
<div class="modal fade" id="authUserModal" tabindex="-1" role="dialog" aria-labelledby="authUserModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="width: 300px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="authUserModalLabel">
                    认证用户权限
                </h4>
            </div>
            <div class="modal-body">
                账号：<input id="userNameInput" type="test"/>
                <br>
                密码：<input id="passwordInput" type="password"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    关闭
                </button>
                <button id="authUserBtn" type="button" class="btn btn-primary">
                    认证
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- 模态框（Modal） -->
<div class="modal fade" id="versionModal" tabindex="-1" role="dialog" aria-labelledby="versionModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="width: 600px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="versionModalLabel">
                    v2.6.0版本更新内容（更新时间：2019-02-15）
                </h4>
                <p>
                    [*] 日志系统->权限细分
                </p>
            </div>
            <div class="modal-body">
                <h4 class="modal-title" id="versionModalLabel">
                    v2.5.5版本更新内容（更新时间：2019-01-24）
                </h4>
                <p>
                    [+] 日志系统->员工管理->剩余假期汇总：带出上一年延伸假期
                </p>
                <p>
                    [+] emp_vacation_history 表，记录每年延伸假期和本年发放假期
                </p>
                <h4>
                    v2.5.4版本更新内容（更新时间：2018-11-21）
                </h4>
                <p>
                    [+] 加入私有gitlab服务
                </p>
                <h4>
                    v2.5.3版本更新内容（更新时间：2018-10-09）
                </h4>
                <p>
                    [+] redis服务端口开放，可以通过RedisDesktopManager直连
                    <br/>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ip:40.125.213.43
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;port:7000
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;auth:zrw1409
                </p>
                <p>
                    [*] 针对腾讯企业邮箱发送邮件代码优化。提升获取忘记密码，验证码功能
                </p>
                <p>
                    [+] 日志系统->员工管理：加入本年总共还有可调休天数 显示。
                </p>
                <h4>
                    v2.5.2版本更新内容（更新时间：2018-5-15）
                </h4>
                <p>
                    [+] redis服务
                </p>
                <h4>
                    v2.5.1版本更新内容（更新时间：2018-5-3）
                </h4>
                <p>
                    [+] 日志系统->员工管理->受权，权限控制到页面元素
                </p>
                <p>
                    [+] 加入版本后提示信息
                </p>
                <h4>
                    v2.5.0版本更新内容（更新时间：2018-5-1）
                </h4>
                <p>
                    [+] 日志系统->员工管理
                </p>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script type="text/javascript">
    $(function () {
        if (isPopOut) {
            $("#userNameInput").val('');
            $("#passwordInput").val('');
            $('#versionModal').modal('show');
        } else {
            $('#versionModal').modal('hide');
        }


        var webRoot = G_WEB_ROOT;
        $('.list-group .list-group-item').click(function () {
            var $this = $(this);
            var link = $this.data('link');
            if (undefined === link) return;
            window.open(link);
        });

        $('#authUserBtn').on('click', function () {
            var userName = $("#userNameInput").val();
            var password = $("#passwordInput").val();
            var param = {
                "userNameCh": userName,
                "empKey": password
            };
            $.ajax({
                url: webRoot + "/login/in.do",
                type: 'POST',
                data: JSON.stringify(param),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                success: function (res) {
                    if (res.code === 1) {
                        var logPage = webRoot + "/log/logMgr.do";
                        window.location.href = logPage;
                    } else {
                        alert(res.msg);
                    }
                    $('#authUserModal').modal('hide');
                }
            });
        });
    });
</script>


</body>
</html>

