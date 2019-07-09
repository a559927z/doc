<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<style>
		.example_chart_position{
			width:500px;
			height:300px;
			position:absolute;
			left:0;
			top:0;
		}
	</style>
</head>
<body>
	<div class="page-content">
		<div class="container">
			<div  style="position:relative;width:100%;height:300px;">
				<div class="example_chart_position" id="mainbg"  style="z-index:1;">
				</div>
				<div class="example_chart_position" id="main" style="z-index:2;">
				</div>
				<div class="example_chart_position" id="linebg" style="z-index:3;">
				</div>
			</div>
			<div  style="position:relative;width:100%;height:300px;">
				<div class="example_chart_position" id="mainbg2" style="z-index:1;">
				</div>
				<div class="example_chart_position" id="main2" style="z-index:100;">
				</div>
				<div class="example_chart_position" id="linebg2" style="z-index:1000000;">
				</div>
			</div>
			<div  style="position:relative;width:100%;height:300px;">
				<div class="example_chart_position" id="orgBenefitChart">
			</div>
		</div>
		</div>
	</div>

	<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
</body>
</html>