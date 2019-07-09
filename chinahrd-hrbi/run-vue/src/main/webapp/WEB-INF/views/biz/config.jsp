<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>系统设置</title>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/config.css"/>
    <link rel="stylesheet" href="${ctx}/assets/js/lib/jquery-validation/1.11.1/validate.css"/>
</head>
<body>
<div class="page-content">
    <div class="main-container-inner">
        <div class="row column config-page accordion-style1 panel-group">
            <div class="page-explain">说明:以下设置的修改会影响到系统界面和运行</div>
            <form class="form-horizontal" autocomplete="off">
                <div class="panel config-body">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">
                                <a class="accordion-toggle" data-toggle="collapse"
                                   href="#sysConfig"> <i class="icon-angle-down bigger-110"
                                                         data-icon-hide="icon-angle-down"
                                                         data-icon-show="icon-angle-right"></i> &nbsp;系统基础配置
                                </a>
                            </h3>
                        </div>
                        <div class="panel-collapse collapse in" id="sysConfig">
                            <div class="panel-body page-main-header">
                                <div class="row">
                                    <div class="form-group">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="col-sm-7 col-xs-7">
                                                <label for="exampleInputName2">系统名称</label>
                                                <div class="explain">(系统名称会显示于系统左上角,字符范围：5-30)</div>
                                            </div>
                                            <div class="col-sm-3 col-xs-3">
                                                <input type="text" name="sysname" required minlength="5" maxlength="30"
                                                       class="form-control" id="XTSZ-sysName" placeholder="请输入系统名称"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="split"></div>
                                    <div class="form-group">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="col-sm-7 col-xs-7">
                                                <label for="exampleInputName2">系统服务</label>
                                                <div class="explain">(关闭服务后,除admin外所有账户均不能登入。默认值为:是)</div>
                                            </div>
                                            <div class="col-sm-3 col-xs-3">
                                                <select class="form-control" id="XTSZ-isOffService">
                                                    <option value="0">是</option>
                                                    <option value="1">否</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="split"></div>
                                    <div class="form-group">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="col-sm-7 col-xs-7">
                                                <label for="exampleInputName2">admin密码</label>
                                                <div class="explain">(admin是系统拥有的最高权限账户,请设置比较复杂的密码，以提高安全性。密码长度：10-20位)
                                                </div>
                                            </div>
                                            <div class="col-sm-3 col-xs-3">
                                                <input type="password" name="password" class="form-control"
                                                       id="XTSZ-adminPassword" placeholder="请输入admin密码"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="split"></div>
                                    <div class="form-group">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="col-sm-7 col-xs-7">
                                                <label for="exampleInputName2">确认密码</label>
                                                <div class="explain">(再一次输入admin密码)</div>
                                            </div>
                                            <div class="col-sm-3 col-xs-3">
                                                <input type="password" name="repassword" class="form-control"
                                                       id="again-XTSZ-adminPassword" placeholder="再一次输入admin密码"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">
                                <a class="accordion-toggle" data-toggle="collapse"
                                   href="#email_config"> <i class="icon-angle-down bigger-110"
                                                            data-icon-hide="icon-angle-down"
                                                            data-icon-show="icon-angle-right"></i> &nbsp;邮件服务
                                </a>
                            </h3>
                        </div>
                        <div class="panel-collapse collapse in" id="email_config">
                            <div class="panel-body page-main-header">
                                <div class="row">
                                    <div class="form-group">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="col-sm-7 col-xs-7">
                                                <label for="exampleInputName2">邮箱主机</label>
                                                <div class="explain">(发送邮件的STMP服务器地址)</div>
                                            </div>
                                            <div class="col-sm-3 col-xs-3">
                                                <input type="text" class="form-control" name="emailHost"
                                                       id="XTSZ-eMailHost" placeholder="请输入邮箱主机"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="split"></div>
                                    <div class="form-group">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="col-sm-7 col-xs-7">
                                                <label for="exampleInputName2">邮箱端口</label>
                                                <div class="explain">(发送邮件的STMP服务器端口)</div>
                                            </div>
                                            <div class="col-sm-3 col-xs-3">
                                                <input type="number" class="form-control" id="XTSZ-eMailPort"
                                                       placeholder="请输入邮箱端口"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="split"></div>
                                    <div class="form-group">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="col-sm-7 col-xs-7">
                                                <label for="exampleInputName2">邮箱账户</label>
                                                <div class="explain">(发送人的邮箱账户)</div>
                                            </div>
                                            <div class="col-sm-3 col-xs-3">
                                                <input type="email" class="form-control" id="XTSZ-eMailAccount"
                                                       placeholder="请输入邮箱账户"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="split"></div>
                                    <div class="form-group">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="col-sm-7 col-xs-7">
                                                <label for="exampleInputName2">邮箱密码</label>
                                                <div class="explain">(发送人的邮箱账户密码)</div>
                                            </div>
                                            <div class="col-sm-3 col-xs-3">
                                                <input type="text" class="form-control" id="XTSZ-eMailPassword"
                                                       placeholder="请输入邮箱密码"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="split"></div>
                                    <div class="form-group">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="col-sm-7 col-xs-7">
                                                <label for="exampleInputName2">admin邮箱</label>
                                                <div class="explain">(admin邮箱是当系统有异常时,发送报告到此邮箱)</div>
                                            </div>
                                            <div class="col-sm-3 col-xs-3">
                                                <input type="email" class="form-control" id="XTSZ-adminMail"
                                                       placeholder="请输入admin邮箱"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="split"></div>
                                    <div class="form-group">
                                        <div class="col-sm-12 col-xs-12">
                                            <div style="float: right;margin-right: 10px;">
                                                <span class="btn btn-primary" id="sendEmail">发送测试邮件</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">
                                <a class="accordion-toggle" data-toggle="collapse"
                                   href="#sms_config"> <i class="icon-angle-down bigger-110"
                                                          data-icon-hide="icon-angle-down"
                                                          data-icon-show="icon-angle-right"></i> &nbsp;短信服务
                                </a>
                            </h3>
                        </div>
                        <div class="panel-collapse collapse in" id="sms_config">
                            <div class="panel-body page-main-header">
                                <div class="row">
                                    <div class="form-group">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="col-sm-7 col-xs-7">
                                                <label for="exampleInputName2">短信主机</label>
                                                <div class="explain">(再一次输入admin密码)</div>
                                            </div>
                                            <div class="col-sm-3 col-xs-3">
                                                <input type="text" class="form-control" id="XTSZ-msgHost"
                                                       placeholder="再一次输入admin密码"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="split"></div>
                                    <div class="form-group">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="col-sm-7 col-xs-7">
                                                <label for="exampleInputName2">短信账户</label>
                                                <div class="explain">(再一次输入admin密码)</div>
                                            </div>
                                            <div class="col-sm-3 col-xs-3">
                                                <input type="text" class="form-control" id="XTSZ-msgAccount"
                                                       placeholder="请输入admin密码"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="split"></div>
                                    <div class="form-group">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="col-sm-7 col-xs-7">
                                                <label for="exampleInputName2">短信密码</label>
                                                <div class="explain">(再一次输入admin密码)</div>
                                            </div>
                                            <div class="col-sm-3 col-xs-3">
                                                <input type="text" class="form-control" id="XTSZ-msgPassword"
                                                       placeholder="请输入admin密码"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="split"></div>
                                    <div class="form-group">
                                        <div class="col-sm-12 col-xs-12">
                                            <div style="float: right;margin-right: 10px;">
                                                <span class="btn btn-primary">发送测试短信</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                <div id="footer" class="row" style="width:100%;background-color:white;">
                    <div class="col-sm-12 col-xs-12">
                        <div align="center">
                            <button class="btn btn-primary">保存</button>
                            <span class="btn">重置</span>
                        </div>
                    </div>
                </div>
            </form>

        </div>
    </div>
</div>


<script src="${ctx}/assets/js/biz/config.js"></script>
</body>
</html>
