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
	<div class="">
		<div class="container">
			<div class="row">
				<div class="col-md-3">
					<div class="btn" id="exportBtn">btn</div>
					123
				</div>
			</div>
		</div>
	</div>
	<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>

	<script type="text/javascript">
		require([ 'jquery' ],
				function($) {
					var webRoot = G_WEB_ROOT, win = top != window ? top.window
							: window;
					var urls = {
						doExportUrl : webRoot + '/demo/export'
					}
					$('#exportBtn').unbind('click').click(function() {
						window.location.href = urls.doExportUrl;
					});
				});
	</script>
</body>
</html>