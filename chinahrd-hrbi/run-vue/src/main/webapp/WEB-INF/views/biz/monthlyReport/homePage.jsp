<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>月报</title>
	<script>
        function switchTab(ProTag, ProBox) {
            for (i = 1; i < 9; i++) {
                if ("tab" + i == ProTag) {
                    document.getElementById(ProTag).getElementsByTagName("a")[0].className = "on";
                } else {
                    document.getElementById("tab" + i).getElementsByTagName("a")[0].className = "";
                }
                if ("con" + i == ProBox) {
                    document.getElementById(ProBox).style.display = "";
                } else {
                    document.getElementById("con" + i).style.display = "none";
                }
            }
        }
    </script>
    <style>
        *{
            padding: 0;
            margin: 0;
            line-height: 25px;
            font-size: 14px;
            list-style:none
        }
        #tabContainer li
        {
            float: left;
            width: 170px;
            margin: 0 1px;
            background: #efefef;
            text-align: center;
        }
        #tabContainer a
        {
            display: block;
        }
        #tabContainer a.on
        {
            background: palegreen ;
        }
        ul, ol {
		    margin: 0 0 0 0;
		    padding: 0;
		}
    </style>
</head>
<body>

	<div id="tabContainer">
        <div style="clear: both">
        </div>
        <div id="con1">
            <a href="#"><img src="${ctx}/assets/img/temp/new/1.png"/></a>
        </div>
        <div id="con2">
            <a href="#"><img src="${ctx}/assets/img/temp/new/2.png"/></a>
        </div>
        <div id="con3">
            <a href="#"><img src="${ctx}/assets/img/temp/new/3.png"/></a>
        </div>
        <div id="con4">
            <a href="#"><img src="${ctx}/assets/img/temp/new/4.png"/></a>
        </div>
		<div id="con5">
            <a href="#"><img src="${ctx}/assets/img/temp/new/5.png"/></a>
        </div>
		<div id="con6">
            <a href="#"><img src="${ctx}/assets/img/temp/new/6.png"/></a>
        </div>

    </div>
    <div style="left:205px;top:310px;position: absolute;z-index: 1000;width: 20px;height: 20px;background-color: white;">
    
    </div>
	<!--/.fluid-container#main-container-->
</body>
</html>
