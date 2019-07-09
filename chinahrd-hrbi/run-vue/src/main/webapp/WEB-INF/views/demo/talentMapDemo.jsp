<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<html>
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>人才地图Demo</title>
	<link href="${ctx}/assets/css/talent-map.css" rel="stylesheet" />
</head>
<body>
	<div class="page-content">
		<div class="main-container">
			<div class="row column">
				<div class="col-md-12 col-xs-12">
					<button class="u-btn" id="toggleText" >显示/隐藏姓名</button>
					<button class="u-btn" id="toggleGroupMode" >切换汇总模式</button>
					<!-- <button class="u-btn" id="export" >导出</button> -->
				</div>
			</div>
			<div class="row column">
				<div class="col-md-12 col-xs-12">
					<div id="map" class="u-map u-map-hideText" style="margin-left: 200px;"></div>
		
					<div id="map2" class="u-map" style="margin-left: 200px;"></div>
					
					<div id="map3" class="u-map" style="margin-left: 200px;"></div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</body>
<script>
	require(["utils","talent-map",'tooltip'], function() {
		var map = $("#map").talentMap({
			showNum : true,
			xAxis : {
				title : "绩效排名",
				data : ['后10%', '[ 70 - 90% )', '[ 70% - 40% )', '[40% - 20%)', '前20%'],
				widths : [0.1, 0.2, 0.3, 0.2, 0.2]
			},
			yAxis : {
				title : "能力等级",
				data : ['准备退出', '重点观察', '基本胜任', '完全胜任', '超越胜任', '关注培养', '重点培养']
			},
			point : {
				afterCreate : function(elem, point) {
					
				},
				afterChange : function(points) {
					/* console.log('afterChange');
					console.log(points); */
				},
				onHover : function(elem, point) {
					var obj = elem.children(':first');
					obj.attr({
						'data-toggle': 'tooltip',
						//'data-placement': 'top'	//默认top
					});
					obj.tooltip({
						title: point.text + '(' + point._x + ',' + point._y + ')'
					}).tooltip('show');
				}
			}
		});
		
		$("#toggleText").click(function() {map.toggleLabel();});
		$("#toggleGroupMode").click(function() {map.toggleGroupMode();});
		window.setTimeout(function() { // 模仿ajax的感觉
			var points = [];
			for (var i = 0; i < 8; i++) {
				points.push({text : '张三三',x : 3,y : 3,direction : 'up'});
			}
			map.addPoints(points); // ！！批量添加点一定要用这个，尤其不确定点有多少时
		}, 500);
		// 加点的方法
		// 加点时，指定单元格有两种指定方法，同时存在时，组件优先用x, y
		map.addPoint({text : '张周三',x : 1,y : 2, xxxx : 'xxxxxxxxxxxx'});
		map.addPoint({text : '张三想',x : 3,y : 4});
		map.addPoint({text : '张三想',x : 5,y : 5,direction : 'down'});
		map.addPoint({text : '张三想',xLabel : '后10%',yLabel : '关注培养',direction : 'up'});
		map.addPoint({text : '张三想',xLabel : '后10%',yLabel : '关注培养',direction : 'left'});
		map.addPoint({text : '李四',xLabel : '后10%',yLabel : '关注培养',direction : 'down'});
		map.addPoint({text : '李四',xLabel : '[ 70 - 90% )',yLabel : '超越胜任',direction : 'down'});
		map.addPoint({text : '李四',xLabel : '前20%',yLabel : '关注培养',direction : 'up'});
		map.addPoint({text : '李四',xLabel : '前20%',yLabel : '重点培养',direction : 'up'});
		// 坐标错误的点，根本不会被加到地图上
		map.addPoint({text : '我是错误数据',x : 7, y : 10});  
		
		// ---------------------------------------------------------------------------------
		var map2 = $("#map2").talentMap({
			showNum : false,
			width : 500,
			xAxis : {
				title : "能力层级",
				data : ['辅助工作者', '独立工作者', '业务骨干', '公司专家', '行业专家']
			},
			yAxis : {
				showType : false,
				title : "能力等级",
				data : ['准备退出', '重点观察', '基本胜任', '完全胜任', '超越胜任', '关注培养', '重点培养']
			},
			style : {
				cellPrefix : 'u-map-c_'
			}
		});
		map2.addPoint({text : '花生2011',x : 1,y : 2, xxxx : 'xxxxxxxxxxxx'});
		map2.addPoint({text : '花生2012',x : 2,y : 2, xxxx : 'xxxxxxxxxxxx'});
		map2.addPoint({text : '花生2013',x : 3,y : 3, xxxx : 'xxxxxxxxxxxx'});
		map2.addPoint({text : '苗木花',x : 3,y : 4});
		map2.addPoint({text : '苗木花',x : 5,y : 5,direction : 'down'});
		map2.addPoint({text : '苗木花',xLabel : '独立工作者',yLabel : '关注培养', direction : 'up'});
		
		map2.drawLine(map2.grepPoints('xxxx', 'xxxxxxxxxxxx'));
		map2.drawLine(map2.grepPoints('text', '苗木花'));
		window.map2 = map2;
		window.map = map;
		
		$("#export").click(function() {
		   	$.download(G_WEB_ROOT + '/export/talentMap.do', map.getOption(), '人才地图demo');
		});
		
		// 演示自定义 图标样式 ---------------------------------------------------------------------------------
		var map3 = $("#map3").talentMap({
			showNum : false,
			width : 500,
			xAxis : {
				title : "能力层级",
				data : ['辅助工作者', '独立工作者', '业务骨干', '公司专家', '行业专家']
			},
			yAxis : {
				showType : false,
				title : "能力等级",
				data : ['准备退出', '重点观察', '基本胜任', '完全胜任', '超越胜任', '关注培养', '重点培养']
			},
			getPointClass : function(p) {
				if(p.empId == 'xxx') {
					return 'u-point-gray'
				}
				if(p.isRing == false) {
					return 'u-point';
				}
				if(p.comment !== undefined ) {
					return p.comment ? 'u-point-comment-y' : 'u-point-comment-n';
				}
				return 'u-point-ring';
			}
		});
		map3.addPoint({text : '花生2011',x : 1,y : 2, direction : 'up', isRing : false});
		map3.addPoint({text : '花生2011',x : 1,y : 2, direction : 'up', isRing : true});
		map3.addPoint({text : '花生2011',x : 1,y : 2, direction : 'down'});
		map3.addPoint({text : '花生2012',x : 2,y : 2,	 direction : 'left', empId : 'xxx' });
		map3.addPoint({text : '花生2012',x : 2,y : 2,	 direction : 'left', comment : true});
		map3.addPoint({text : '花生2012',x : 2,y : 2,	 direction : 'left', comment : false});
	});
</script>
</html>