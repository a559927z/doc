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
        <ul>
            <li id="tab1"><a href="#" class="on" onclick="switchTab('tab1','con1');this.blur();return false;">
                1.1 编制及人员分布概览</a>
			</li>
            <li id="tab2"><a href="#" onclick="switchTab('tab2','con2');this.blur();return false;">
                1.2 流程中异动人员名单</a>
			</li>
            <li id="tab3"><a href="#" onclick="switchTab('tab3','con3');this.blur();return false;">
                1.3 在岗人员名单</a>
			</li>
            <li id="tab4"><a href="#" onclick="switchTab('tab4','con4');this.blur();return false;">
                2.1 人员流动情况概览</a>
			</li>
			<li id="tab5"><a href="#" onclick="switchTab('tab5','con5');this.blur();return false;">
                2.2 流出分析-趋势及原因</a>
			</li>
			<li id="tab6"><a href="#" onclick="switchTab('tab6','con6');this.blur();return false;">
                2.3 流出分析 - 谁走了</a>
			</li>
			<li id="tab7"><a href="#" onclick="switchTab('tab7','con7');this.blur();return false;">
                2.4 流出分析 - 去向</a>
			</li>
			<li id="tab8"><a href="#" onclick="switchTab('tab8','con8');this.blur();return false;">
                2.5 流动人员名单</a>
			</li>
        </ul>
        <div style="clear: both">
        </div>
        <div id="con1">
            <a href="#"><img src="${ctx}/assets/img/temp/1.png"/></a>
        </div>
        <div id="con2" style="display: none">
            <a href="#"><img src="${ctx}/assets/img/temp/2.png"/></a>
        </div>
        <div id="con3" style="display: none">
            <a href="#"><img src="${ctx}/assets/img/temp/3.png"/></a>
        </div>
        <div id="con4" style="display: none">
            <a href="#"><img src="${ctx}/assets/img/temp/4.png"/></a>
        </div>
		<div id="con5" style="display: none">
            <a href="#"><img src="${ctx}/assets/img/temp/5.png"/></a>
        </div>
		<div id="con6" style="display: none">
            <a href="#"><img src="${ctx}/assets/img/temp/6.png"/></a>
        </div>
		<div id="con7" style="display: none">
            <a href="#"><img src="${ctx}/assets/img/temp/7.png"/></a>
        </div>
		<div id="con8" style="display: none">
            <a href="#"><img src="${ctx}/assets/img/temp/8.png"/></a>
        </div>
    </div>
	<!--/.fluid-container#main-container-->
</body>
</html>
