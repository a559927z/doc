<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>首页</title>
	<style>
		.quota-container{
			position:absolute;
			width:250px;
			height:132px;
			border:1px solid white;
		}
		
	</style>
</head>
<body>
	<div style="padding-left : 20px;">
		<img alt="" src="${ctx}/assets/img/index-test-1.jpg">
		<div style="position:relative;">
			<img alt="" src="${ctx}/assets/img/index-test-2.jpg">
			
			<a class="quota-container" style="top:48px;left:320px" target="_blank" href="${ctx}/perBenefit/toPerBenefitView" title="人均效益"></a>
			<a class="quota-container" style="top:445px;left:4px" target="_blank" href="${ctx}/accordDismiss/toAccordDismissView" title="主动流失率"></a>
			<a class="quota-container" style="top:445px;left:320px" target="_blank" href="${ctx}/dismissRisk/toDismissRiskView" title="人才流失风险"></a>
		</div>
	</div>
</body>
</html>
