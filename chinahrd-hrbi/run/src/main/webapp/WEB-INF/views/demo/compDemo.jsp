<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/WEB-INF/views/include/top.jsp"%>
<style>
.height300pc {
	height: 300px;
}
.height378pc {
	height: 378px;
}
</style>
</head>
<body>
<div class="compDemoId">
	<div class="container">
		<div class="row">
			<div class="col-md-3 compBtn">
				<h4>所有组件</h4>
				<a class="list-group-item active">所有组件</a>
				<h4>基础组件</h4>
				<a class="list-group-item ">PieComp饼形组件</a>
				<a class="list-group-item ">AnnularComp环形组件</a>
				<a class="list-group-item ">BarComp柱状组件</a>
				<a class="list-group-item ">BlockComp块组件</a>
				<a class="list-group-item ">TopBlockComp块组件</a>
				<a class="list-group-item ">TabBlockComp块组件</a>
				<h4>业务组件</h4>
				<a class="list-group-item ">ConstellationComp星座组件</a>
			</div>
			<div  class="col-md-9" style="background-color:#fff">
				<div class="pieCompId compZone">
				<p class="text-info">参数：lable=标题、:class=图表高、:list=数据源</p>
					<pie-component lable="职级" :class="['height300pc']" :list="list.ability" ></pie-component>
				</div>
				<div class="annularCompId compZone">
				<p class="text-info">参数：lable=标题、:class=图表高、:list=数据源</p>
					<annular-component lable="性格" :class="['height300pc']" :list="list.personality" ></annular-component>
				</div>
				<div class="barCompId compZone">
				<p class="text-info">参数：lable=标题、:class=图表高、:list=数据源</p>
					<bar-component lable="年龄" :class="['height300pc']" :list="list.age" ></bar-component>
				</div>
				<div class="constellationCompId compZone">
				<p class="text-info">参数：lable=标题、:class=图表高、:list=数据源</p>
					<constellation-component lable="星座" :class="['height378pc']" :list="list.constellatory" ></constellation-component>
				</div>
				<div class="blockCompId compZone">
				<p class="text-info">参数：lable=标题</p>
<!-- 					<block-component></block-component> -->
					<block-component lable="职级" >
						<pie-component lable="职级" :class="['height300pc']" :list="list.ability" ></pie-component>
					</block-component>
    				<block-component lable="年龄" >
    					<bar-component lable="年龄" :class="['height300pc']" :list="list.age" ></bar-component>
    				</block-component>
				</div>
				<div class="topBlockCompId compZone">
				<p class="text-info">参数：lable=标题、:class=图表宽度、tip=提示信息</p>
				<p class="text-info">子组件参数：slot=titleRight、slot=body</p>
					<div id="topBlockCompId1">
						<top-block-component lable="我是个空Block" :class="['col-sm-2 ct-30 ct-line-col']"></top-block-component>
					</div>
					<div id="topBlockCompId2">
						<top-block-component lable="人均效益" tip="一种人力投入衡量方法，把全职员工加班时数及兼职员工工作时数折算为全职员工人数" :class="['col-sm-2 ct-30 ct-line-col']">
							<span slot="titleRight" class="select">万元</span>
							<total-info-component slot="body" :data="data" :arrow="true"></total-info-component>
	    				</top-block-component>
					</div>
					<div id="topBlockCompId3">
						<top-block-component lable="2015年薪酬总额" :class="['col-sm-3 ct-36 ct-line-col']">
							<div slot="titleRight" id="salarytotalTab" class="index-common-title-right">
	                            <span @click="budgetEvent(1)" data-id="budget" class="select">预算</span>
	                            <span @click="accumulativeEvent(2)" data-id="accumulative" class="">累计</span>
	                        </div>
							<total-info-component slot="body" :data="data" :arrow="false"></total-info-component>
	    				</top-block-component>
					</div>
				</div>
				<div class="tabBlockCompId compZone">
				<p class="text-info">参数：lable=标题、sublable="副标题" :class=图表宽度</p>
				<p class="text-info">子组件参数：slot=left、slot=right</p>
					<tab-block-component lable="职位序列分布" sublable="操作提示：点击职位序列可查看该序列下的职级分布，再次点击则可返回默认统计" :class="'col-sm-12 ct-line-col SetUpBody'">
						<div slot="left"> A tab content</div>
						<div slot="right"> B tab content</div>
					</tab-block-component>
				</div>
			</div>
		</div>
	</div>
</div>
	<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
	<script src="${ctx}/assets/js/demo/vue/pieCompDemo.js"></script>
	<script src="${ctx}/assets/js/demo/vue/annularCompDemo.js"></script>
	<script src="${ctx}/assets/js/demo/vue/barCompDemo.js"></script>
	<script src="${ctx}/assets/js/demo/vue/constellatoryCompDemo.js"></script>
	<script src="${ctx}/assets/js/demo/vue/blockCompDemo.js"></script>
	<script src="${ctx}/assets/js/demo/vue/topBlockCompDemo.js"></script>
	<script src="${ctx}/assets/js/demo/vue/tabBlockCompDemo.js"></script>

	<script type="text/javascript">
		require(['jquery'], function($) {
			$('.compBtn a').on('click', function(){
				$('.compBtn a').removeClass('active');
				$(this).addClass('active');
				$('.compZone').hide();
				if('所有组件' == $(this).text()){
					window.location.reload();
				}
				if('PieComp饼形组件' == $(this).text()){
					$('.pieCompId').show();
				}
				if('AnnularComp环形组件' == $(this).text()){
					$('.annularCompId').show();
				}
				if('BarComp柱状组件' == $(this).text()){
					$('.barCompId').show();
				}
				if('ConstellationComp星座组件' == $(this).text()){
					$('.constellationCompId').show();
				}
				if('BlockComp块组件' == $(this).text()){
					$('.blockCompId').show();
				}
				if('TopBlockComp块组件' == $(this).text()){
					$('.topBlockCompId').show();
				}
				if('TabBlockComp块组件' == $(this).text()){
					$('.tabBlockCompId').show();
				}
			});
		});
	</script>
</body>
</html>