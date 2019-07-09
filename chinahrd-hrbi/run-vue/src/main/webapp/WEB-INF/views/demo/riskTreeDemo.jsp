<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>人才离职风险树</title>
<%@include file="/WEB-INF/views/include/top.jsp"%>
<style>

#riskTreeDom {
	width: 500px;
	height: 700px;
	margin-top:20px;
}

</style>
</head>
<body style="background:white !important">
	
	<div id="riskTreeDom"></div>
	<script>
		require(['riskTree' ], function() {
					var testData = [ {
						'id' : '2',
						'pid' : '1',
						'name' : '薪酬竞争力-1',
						'riskFlag' : '0'
					}, {
						'id' : '3',
						'pid' : '5',
						'name' : '薪酬竞争力-1-1-1',
						'riskFlag' : '0'
					}, {
						'id' : '1',
						'pid' : '',
						'name' : '薪酬竞争力',
						'riskFlag' : '1'
					}, {
						'id' : '4',
						'pid' : '1',
						'name' : '薪酬竞争力-2',
						'riskFlag' : '1'
					}
					, {
						'id' : '5',
						'pid' : '2',
						'name' : '薪酬竞争力-1-1',
						'riskFlag' : '2'
					}, {
						'id' : '6',
						'pid' : '7',
						'name' : '薪酬竞争力-1-1',
						'riskFlag' : '0'
					}, {
						'id' : '7',
						'pid' : '3',
						'name' : '薪酬竞争力-1-1',
						'riskFlag' : '2'
					},
	                {'id':'10','pid':'3','name':'薪酬竞争力-1-1-10','riskFlag':'0'},
					];

					var riskTreeOption = {
						hasSelect : true,
						data : testData
					}
					$('#riskTreeDom').riskTree(riskTreeOption);
				});
	</script>
</body>
</html>
