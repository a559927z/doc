<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>中人网才报平台-修改新密码</title>
    <link rel="stylesheet" href="${ctx}/assets/css/base/updatePasswd.css"/>
</head>
<body class="passwd-layout">
    <div class="main-container">
        <div class="passwd-border"></div>
        <div class="main-content">
            <div class="col-sm-12">
                <div class="passwd-header">
                    <span class="hrd-icon"></span>
                    <span class="passwd-header-title">修改新密码</span>
                </div>
                <div class="passwd-container">
                    <div class="passwd-box widget-box visible">
                        <div class="widget-body">
                            <div class="widget-header">
                                <h5 class="white smaller">
                                    <span class="hint-icon"></span><span class="widget-caption" id="passwdCaption">您当前的登陆密码过于简单，请输入您的新密码</span>
                                </h5>
                            </div>
                            <div class="widget-main">
                                <form name="updateForm" action="_this" method="post">
                                    <label class="block">
                                        <input type="password" id="pw" name="pw" class="form-control" placeholder="新的登陆密码"/>
                                    </label>

                                    <label class="block">
                                        <input type="password" id="rpw" name="rpw" class="form-control"
                                               placeholder="确认新的登陆密码"/>
                                    </label>

                                    <div class="widget-btn">
                                        <button type="button" id="submitBtn" class="btn btn-success" disabled>更改密码</button>
                                    </div>
                                </form>
                            </div>
                        </div><!-- /widget-body -->
                    </div><!-- /signup-box -->
                </div>
            </div><!-- /.col -->
        </div>
    </div><!-- /.main-container -->
    <script src="${ctx}/assets/js/biz/base/updatePasswd.js"></script>
</body>
</html>
