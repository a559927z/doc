<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/WEB-INF/views/include/top.jsp"%>
<style>
</style>
</head>
<body>
	<style>
.line {
	height: 1px; width: 100%; background: #444; overflow: hidden;
}

.comparedPreviousYear .w800 {
	width: 800px;
}

.comparedPreviousYear .chart {
	width: 600px; height: 400px;
}

.comparedPreviousYear .list-inline {
	text-align: right;
}
</style>
	<div class="comparedPreviousYear">
		<div class="w800">
			<div class="row">
				<div class="col-md-8">
					<ul class="list-inline">
						<li>赞成百分比</li>
						<li>中立百分比</li>
						<li>不赞成百分比</li>
					</ul>
				</div>
				<div class="col-md-4">
					<ul class="list-inline">
						<li>与xxBG差值</li>
						<li>与2016年差值</li>
					</ul>
				</div>
			</div>
			<!--</div>-->

			<div class="line"></div>
			<div>
				<div style="float: left" id="comparedPreviousYearId" class="chart"></div>
				<div style="float: left">123</div>
			</div>
		</div>
	</div>
	<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
	<script type="text/javascript">
		require([ 'jquery','echarts3' ], function($, echarts) {
			var comparedPreviousYearChart = echarts.init(document
					.getElementById('comparedPreviousYearId'));

			var comparedPreviousYear = {
				tooltip : {
					trigger : 'axis',
					axisPointer : {
						type : 'shadow'
					}
				},
				grid : {
					left : '3%',
					right : '4%',
					bottom : '3%',
					top : '0',
					containLabel : true
				},
				xAxis : {
					type : 'value',
					min : 0,
					max : 500,
					splitLine : false,
					axisLine : false
				},
				yAxis : {
					type : 'category',
					axisTick : false,
					axisLine : {
						lineStyle : {
							width : 0
						}
					},
					data : [ '我愿意向公司以外的人员宣传在这里工作的好处', '我愿意推荐朋友加入这家公司', 'Say' ]
				},
				series : [ {
					name : '直接访问',
					type : 'bar',
					stack : '1',
					label : {
						normal : {
							show : true,
							position : 'insideRight'
						}
					},
					data : [ 320, 202, 301 ]
				}, {
					name : '邮件营销',
					type : 'bar',
					stack : '1',
					label : {
						normal : {
							show : true,
							position : 'insideRight'
						}
					},
					data : [ 180, 298, 199 ]
				} ]
			};
			comparedPreviousYearChart.setOption(comparedPreviousYear);
		});
	</script>
</body>
</html>