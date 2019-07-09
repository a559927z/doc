<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<html>
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>数据添加</title>
	<link rel="stylesheet" href="${ctx}/assets/css/biz/employee/talentDetail.css"/>
</head>
<body>
	<div class="page-content" >
		<div class="main-container-inner" id="myScrollspy">
			<div class="row talent-detail-header">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="row">
							Base：所有维度表
							<button id = "callDimTables">callDimTables</button> 
							机构表
							<button id = "repleacDimOrganization">dim_organization</button> 
						</div>
						
						<div class="row">
							员工：源-员工表
							<button id = "repleacSoureVDimEmp">soure_v_dim_emp</button> 
							员工表
							<button id = "replaceVDimEmp">v_dim_emp</button> 
							我的下属
							<button id = "callUnderling">underling</button> 
							员工加班记录 -天/月
							<button id = "callEmpOvertimeDay">emp_overtime_day</button> 
							总人数
							<button id = "callHistoryEmpCount">history_emp_count</button> 
							月度人数
							<button id = "historyEmpCountMonth">historyEmpCountMonth</button> 
							工作异动
							<button id = "jobChange">jobChange</button> 
						</div>
						
						<div class="row">
							流失率：月度流失情况
							<button id = "replaceMonthlyEmpCount">monthly_emp_count</button> 
						</div>
						
						<div class="row">
							人均效益：明年目标人均效益
							<button id = "callTargetBenefitValue">target_benefit_value</button> 
							营业利润
							<button id = "callTradeProfit">trade_profit</button> 
							等效员工
							<button id = "callFactfte">fact_fte</button> 
						</div>
						
						
						<div class="row">
							加数据：addDimEmpDays
							<button id = "addDimEmpDays">addDimEmpDays</button> 
						</div>
						
					</div>
				</div>
			</div>
			
		</div>
	</div>
<script>
require([ 'jquery', 'bootstrap', 'jgGrid', 'utils' ], function($) {
	var webRoot = G_WEB_ROOT;
	console.log(webRoot);

	var urls = {
		//Base
		callDimTables : webRoot + '/dbImportJSP/callDimTables.do',
		repleacDimOrganization : webRoot + '/dbImportJSP/repleacDimOrganization.do',

		// 员工
		repleacSoureVDimEmp : webRoot + '/dbImportJSP/repleacSoureVDimEmp.do',
		callUnderling : webRoot + '/dbImportJSP/callUnderling.do',
		callHistoryEmpCount : webRoot + '/dbImportJSP/callHistoryEmpCount.do',
		historyEmpCountMonth : webRoot + '/dbImportJSP/historyEmpCountMonth.do',
		callEmpOvertimeDay : webRoot + '/dbImportJSP/callEmpOvertimeDay.do',
		callEmpAll : webRoot + '/dbImportJSP/callEmpAll.do',

		// 业务：
		// 人均效益
		callTradeProfit : webRoot + '/dbImportJSP/callTradeProfit.do',
		callFactfte : webRoot + '/dbImportJSP/callFactfte.do',

		// 流失率-月度总人数
		replaceMonthlyEmpCount : webRoot + '/dbImportJSP/replaceMonthlyEmpCount.do',

		makeData : webRoot + '/dbImportJSP/makeData.do',
	};
	$("#addDimEmpDays").bind('click', function() {
		$.ajax({
			url : urls.makeData,
			data: {'key': 'addDimEmpDays'},
			type : "post",
			success : function(result) {
				alert(result.type);
			}
		});
	});
	$("#jobChange").bind('click', function() {
		$.ajax({
			url : urls.callEmpAll,
			data: {'buiss': 'jobChange'},
			type : "post",
			success : function(result) {
				alert(result.type);
			}
		});
	});
	$("#repleacSoureVDimEmp").bind('click', function() {
		$.ajax({
			url : urls.callEmpAll,
			data: {'buiss': 'repleacSoureVDimEmp'},
			type : "post",
			success : function(result) {
				alert(result.type);
			}
		});
	});
	$("#replaceVDimEmp").bind('click', function() {
		$.ajax({
			url : urls.callEmpAll,
			data: {'buiss': 'replaceVDimEmp'},
			type : "post",
			success : function(result) {
				alert(result.type);
			}
		});
	});













	$("#historyEmpCountMonth").bind('click', function() {
		$.ajax({
			url : urls.historyEmpCountMonth,
			type : "post",
			success : function(result) {
				alert(result.type);
			}
		});
	});
	$("#callDimTables").bind('click', function() {
		$.ajax({
			url : urls.callDimTables,
			type : "post",
			success : function(result) {
				alert(result.type);
			}
		});
	});
	$("#callFactfte").bind('click', function() {
		$.ajax({
			url : urls.callFactfte,
			type : "post",
			success : function(result) {
				alert(result.type);
			}
		});
	});
	$("#callTradeProfit").bind('click', function() {
		$.ajax({
			url : urls.callTradeProfit,
			type : "post",
			success : function(result) {
				alert(result.type);
			}
		});
	});
	$("#callEmpOvertimeDay").bind('click', function() {
		$.ajax({
			url : urls.callEmpOvertimeDay,
			type : "post",
			success : function(result) {
				alert(result.type);
			}
		});
	});
	$("#replaceMonthlyEmpCount").bind('click', function() {
		$.ajax({
			url : urls.replaceMonthlyEmpCount,
			type : "post",
			success : function(result) {
				alert(result.type);
			}
		});
	});


	$("#repleacDimOrganization").bind('click', function() {
		$.ajax({
			url : urls.repleacDimOrganization,
			type : "post",
			success : function(result) {
				alert(result.type);
			}
		});
	});






	$("#callUnderling").bind('click', function() {
		$.ajax({
			url : urls.callUnderling,
			type : "post",
			success : function(result) {
				alert(result.type);
			}
		});
	});

	$("#callHistoryEmpCount").bind('click', function() {
		$.ajax({
			url : urls.callHistoryEmpCount,
			type : "post",
			success : function(result) {
				alert(result.type);
			}
		});
	});

});
</script>
	
	
</body>
</html>