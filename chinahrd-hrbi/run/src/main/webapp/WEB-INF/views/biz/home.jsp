<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<script>
    var recordPage = [];
    var gotoRecordPage = function () {
//         for (var i = 0; i < recordPage.length; i++) {
//             console.log(recordPage[i]);
//         }
        if (recordPage.length >= 2) {
            recordPage.pop();
            recordPage[recordPage.length - 1].click();
        }
    }
</script>
<html lang="en">

<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <!-- ace settings handler -->
    <script src="${ctx}/assets/js/lib/base/ace-extra.min.js"></script>
    <title>中人网才报平台</title>
    <style>
        body {
            overflow-y: hidden;
        }
    </style>
</head>
<body>
<!-- 页面顶部¨ -->
<%@ include file="../include/header.jsp" %>
<div class="main-container" id="main-container">
    <script>
        try {
            ace.settings.check('main-container', 'fixed');
        } catch (e) {
        }
    </script>

    <div class="skin-1 main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>
        <!-- 左侧菜单 -->
        <%@ include file="../include/left.jsp" %>

        <div id="main-content" class="clearfix main-content">
            <div class="breadcrumbs" id="breadcrumbs">
                <script>
                    try {
                        ace.settings.check('breadcrumbs', 'fixed');
                    } catch (e) {
                    }
                </script>
                <ul class="breadcrumb">
                    <li id="breadcrumbFirst">
                        <i class="icon-home home-icon"></i><a href="javascript:;" data-href="manageHome/index">首页</a>
                    </li>

                    <li class="active" id="breadcrumbLast">首页</li>
                </ul><!-- .breadcrumb -->

            </div>

            <div class="page-content">
                <iframe name="mainFrame" id="mainFrame" style="width:100%;height:100%;" scrolling="auto" frameborder="0"
                        src="manageHome/index"></iframe>
                <div class="col-sm-12" id="bottom_layout"></div>
            </div>
            <script>
                //固定导航栏和标签栏
                try {
                    ace.settings.navbar_fixed(true);
                    ace.settings.sidebar_fixed(true);
                } catch (e) {
                }
            </script>
            <!--/#ace-settings-container-->
        </div>
    </div>
</div>
</div>
<!--/.fluid-container#main-container-->
<script src="${ctx}/assets/js/biz/home.js"></script>
</body>
</html>
