<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<html>
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>index</title>
</head>
<body>
<div class="page-content">
	<div class="column" style="width: 600px; background: white; margin: 50px">
		<!-- tab页的标题 -->
		<h4 class="tabs-title">当前组织人均效益</h4>
		<!-- Nav tabs -->
		<!-- nav-tabs-right 将tab标签控制在右边（没有该属性，tab页在左边） -->
		<ul class="nav nav-tabs nav-tabs-right" role="tablist">
			<li role="presentation" class="active">
				<!-- dataSelected用于标识该tab页是否已初始化 -->
				<a href="#orgBenefitChart" aria-controls="orgBenefitChart" role="tab"
				data-toggle="tab" data-selected="1">
					<!-- 图片 --> 
					<span class="tab-area tab-icon-chart">&nbsp;</span>
				</a>
			</li>
			<li role="presentation">
				<a href="#orgBenefitGrid" aria-controls="orgBenefitGrid" role="tab" data-toggle="tab"> 
					<!-- 文字 -->
					文字
				</a>
			</li>
		</ul>
		<!-- 标签页在右边时，该元素必须加 -->
		<div class="clearfix"></div>
		<!-- Tab panes -->
		<div class="tab-content">
			<div role="tabpanel" class="tab-pane active" id="orgBenefitChart">1</div>
			<div role="tabpanel" class="tab-pane" id="orgBenefitGrid">...2</div>
		</div>
	</div>
</div>

	<script>
		require([ 'bootstrap' ], function() {
			$('#myTabs a').click(function (e) {
				  e.preventDefault()
				  $(this).tab('show')
			})
			
			//点击tab页
			$('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
				var dataSelected = $(e.target).attr('data-selected');
				alert((dataSelected) ? '已加载过':'未加载')
				//切换标签页，如果 data-selected属性为1，则表明数据已经加载过，不需再重新请求
				if(dataSelected){
					return;
				}
				$(e.target).attr('data-selected',1);
				//e.target当前标签页；relatedTarget上一个激活的标签页 
			})
		});
	</script>
</body>
</html>