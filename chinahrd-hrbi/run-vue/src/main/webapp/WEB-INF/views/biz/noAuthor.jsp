<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <!-- jsp文件头和头部 -->
  <%@include file="/WEB-INF/views/include/top.jsp"%>
  <title>未授权页面</title>
  <style>
    .author{width: 100%;margin:200px 300px; }
  </style>
</head>
<body>
  <div class="author">
    提示：您还未获得该指标授权，如有疑问请联系管理员。
  </div>
</body>
</html>
