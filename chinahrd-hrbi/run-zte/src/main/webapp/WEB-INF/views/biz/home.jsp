<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<script type="text/javascript">
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
    <title>中人网才报平台</title>
</head>
<body>
<!-- 页面顶部¨ -->
<%@ include file="../include/header.jsp" %>

<div class="main-container" id="main-container">
    <script type="text/javascript">
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
                <script type="text/javascript">
                    try {
                        ace.settings.check('breadcrumbs', 'fixed')
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

            <!-- 换肤 -->
            <%--<div id="ace-settings-container" class="ace-settings-container hide">--%>
            <%--<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">--%>
            <%--<i class="icon-cog bigger-150"></i>--%>
            <%--</div>--%>
            <%--<div id="ace-settings-box" class="ace-settings-box">--%>
            <%--<div>--%>
            <%--<div class="pull-left">--%>
            <%--<select id="skin-colorpicker" class="hide">--%>
            <%--<option data-skin="default" value="#438EB9">#438EB9</option>--%>
            <%--<option data-skin="skin-1" value="#222A2D">#222A2D</option>--%>
            <%--<option data-skin="skin-2" value="#C6487E">#C6487E</option>--%>
            <%--<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>--%>
            <%--</select>--%>
            <%--</div>--%>
            <%--<span>&nbsp; 选择皮肤</span>--%>
            <%--</div>--%>
            <%--<div>--%>
            <%--<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar" checked="checked"/>--%>
            <%--<label class="lbl" for="ace-settings-navbar"> 固定导航条</label>--%>
            <%--</div>--%>

            <%--<div>--%>
            <%--<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar" checked="checked"/>--%>
            <%--<label class="lbl" for="ace-settings-sidebar"> 固定滑动条</label>--%>
            <%--</div>--%>

            <%--<div>--%>
            <%--<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs"/>--%>
            <%--<label class="lbl" for="ace-settings-breadcrumbs">固定面包屑</label>--%>
            <%--</div>--%>

            <%--<div>--%>
            <%--<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl"/>--%>
            <%--<label class="lbl" for="ace-settings-rtl">切换到左边</label>--%>
            <%--</div>--%>

            <%--<div>--%>
            <%--<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container"/>--%>
            <%--<label class="lbl" for="ace-settings-add-container">--%>
            <%--切换窄屏--%>
            <%--<b></b>--%>
            <%--</label>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</div>--%>
            <script type="text/javascript">
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
    <!-- #main-content -->
    <%--<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">--%>
    <%--<i class="icon-double-angle-up icon-only bigger-110"></i>--%>
    <%--</a>--%>
</div>
</div>
<!--/.fluid-container#main-container-->
<script type="text/javascript" src="${ctx}/assets/js/require.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/biz/home.js"></script>
</body>
</html>
