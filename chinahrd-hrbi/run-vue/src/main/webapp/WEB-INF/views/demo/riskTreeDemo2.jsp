<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>测试</title>
	<link rel="stylesheet" href="${ctx}/assets/css/toastr.min.css" />
</head>
<body style="height: 900px;">
	<div class="page-content">
		<div class="modal fade" id="testModal" tabindex="-1" role="dialog"
     				aria-labelledby="abInfoModalLable" aria-hidden="true">
		    <div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                    &times;
		                </button>
						<h4 class="modal-title bolder" id="abInfoModalLable">离职风险评估</h4>
		            </div>
		            <div class="modal-body">
						<div class="row" id="runoffInfo">
			
						</div>
		            </div>
		             <div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		            </div>
		        </div><!-- /.modal-content -->
		    </div><!-- /.modal -->
		</div>
		
	</div>
	
	<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
	<script>
	require(['jquery','riskTree2'],function(){
		var webRoot = G_WEB_ROOT;
		var urls = {
	        	
	            empRiskDetailUrl: webRoot + "/dismissRisk/getEmpRiskDetail.do",	//获取员工离职风险信息
	        };
		 var riskFlagArr = ['gray', 'red', 'yellow', 'green'];
		    var riskTreeOption = {
		            hasSelect: false, //是否显示select
		            hasTopText: false,
// 		            hasSelect:false,
// 		            riskModal:false // true  直接评估  false  显示明细
		        }
		 var riskModal=true;   
		     if(!riskModal){
		    	 $("#testModal").modal("show");
				    $("#testModal").on('shown.bs.modal', function () {
				    	 var obj=$("#runoffInfo");
						    riskTreeOption.empId = '1b921f663cea11e59c5508606e0aa89a';
				            $(obj).riskTree2(riskTreeOption);
			         }); 
		     }else{  //直接评估
		    	 riskTreeOption.riskModal=true;
		    	 riskTreeOption.hasSelect=true;
		    	 riskTreeOption.empId = '1b921f663cea11e59c5508606e0aa89a';
		            $("body").riskTree2(riskTreeOption); 
		     }
		    
		   
	})
		
	
	</script>
</body>
</html>