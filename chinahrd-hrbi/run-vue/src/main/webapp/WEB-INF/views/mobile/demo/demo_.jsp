<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
     <%@include file="../include/top.jsp" %>
    <title>人才损益</title>
     
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/datetime.css?v=${v.version}"/>

</head>
<body>

<div class="datetime_panel">
          	<div class="ui_datetime_btn">完成</div>
          	  <div class="ui-datetime" id="datetime2"></div>
          </div>
<div class="datetime_shade"></div>
<script type="text/javascript" src="${ctx}/assets/js/require.js"></script>
<script>
require(['jquery', 'datetime'], function ($) {
	$(".datetime_panel").show();
	$(".datetime_shade").show();
	$("#datetime2").datetime({
        type: 'date',//date,time,diy
        date:  new Date("2013-05"),
        minDate : new Date("2013-01"),
        maxDate: new Date("2015-01"),
        onChange: function (data) {
            console.log("call back", data);
        }
    });
	
});
</script>
</body>
</html>