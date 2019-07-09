<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="description" content="overview & stats"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>才报平台</title>
    <link rel="icon" href="${ctx}/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon"/>
    <link href="${ctx}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/assets/css/zrw.min.css" rel="stylesheet">
    <link href="${ctx}/assets/css/base/login.css" rel="stylesheet">
    <script>
        if (window != top) {
            top.location.href = location.href;
        }
    </script>
</head>
<body>
<style>
	.updatePwd {
		position: absolute;
	    top: 0;
	    right: 0;
	}
</style>
<div class="header">
    <div class="wrapper">
        <div class="topBar">
            <a href="#" class="logo">才报平台</a>
        </div>
    </div>
</div>
<div class="banner">
    <div class="wrapper">
        <div class="slide"></div>
        <div class="loginPanel">
            <div class="loginTitle" id="loginTitle">
          		 帐号登录
             		<a href="${ctx}/forgetPassword/toView">
          		 <span class="glyphicon glyphicon-new-window updatePwd">
             		
          		 </span>
             		</a>
            </div>
            <div class="loginTips" id="errTips" style="visibility: hidden;"></div>
            <div class="loginForm">
                <div class="inputOuter">
                    <div class="inputInner">
                        <input type="text" id="u" name="u" value="${username}"
                               placeholder="请输入用户名" tabindex="1">
                    </div>
                </div>
                <div class="inputOuter">
                    <div class="inputInner">
                        <input type="password" id="p" name="p" value=""
                               placeholder="请输入密码" tabindex="2">
                    </div>
                </div>
                <button type="submit" id="loginBtn" class="btn">登录</button>
            </div>
        </div>
    </div>
   
</div>
<div class="foot">
    <h6>Copyright ©2001-<script>document.write((new Date()).getFullYear());</script>
        ChinaHRD.Net <span>All Rights Reserved.</span></h6>
    <h6>中人网 版权所有</h6>
</div>

<!--弹出框 begin -->
<div class="modal fade popup-modal" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-text" id="modalLabel">修改密码</div>
                <%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>--%>
            </div>
            <div class="modal-body">
                <div class="col-xs-12">
                    <p>您当前的密码过于简单，系统将于<span class="time-remind">5</span>秒后自动跳转到修改密码页面！</p>
                    <p>如若超时未跳转，请<a href="#" class="btn-link">点击这里.</a></p>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!--弹出框 end -->

</body>
<script src="${ctx}/assets/js/require.js" js-main="biz/base/login" charset="utf-8"></script>
</html>