<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>员工数据配置</title>
	<link href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css" rel="stylesheet" />
	<style>
		.panel-footer .btn:last-child{
			margin-left: 12px;
		}
		#orgTree{
/* 		    width:300px; */
			min-height: 400px;max-height: 400px;height: 400px;
			overflow: auto;
		}	
	</style>
</head>
<body>
	<div class="page-content">
		<div class="container">
			<div class="page-header">
				<input type="hidden" id="empId" value="${empDto.empId}">
				<h1>
					员工数据配置界面<small>(${empDto.empName})</small>
				</h1>
			</div>
			<div class="col-md-4 col-sm-5 col-xs-6">
				<div class="panel panel-default">
					<div class="panel-body">
						<ul id="orgTree" class="ztree"></ul>
					</div>
					<div class="panel-footer">
						<button type="submit" class="btn btn-primary submitBtn">提交</button>
						<button type="button" class="btn btn-default" id="resetBtn">返回</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		require([ 'messenger', 'ztree','utils' ], function() {
			var getTreeDataJsonUrl = G_WEB_ROOT + "/emp/getTreeDataJson";
			var treeData = [];
			function getTreeDataJson(){
				console.log($("#empId").val());
				$.ajax({
					url : getTreeDataJsonUrl,
					type : 'post',
					dataType : 'json',
					data:{empId: $("#empId").val()},
					async: false,	//同步
					success : function(data) {
						console.log(data);
						treeData = data;
					}
				});
			}
			getTreeDataJson();
			
			
			var url = G_WEB_ROOT + "/emp/addEmpOrganiation";
			var selectData = [];
			var treeObj;
			var setting = {
				check : {
					enable : true,
					chkboxType : { "Y" : "ps", "N" : "ps" }
				},
				callback : {
					beforeClick : function(treeId, treeNode) {
						if (treeNode.isParent) {
							treeObj.expandNode(treeNode);
							return false;
						} 
					}
				}
			};

			$(function() {
				var el = $("#orgTree");
				treeObj = $.fn.zTree.init(el, setting, treeData);

				$(".submitBtn").click(function() {
					// 获取已选的节点（已存在节点）
					var checkedNodes = treeObj.getCheckedNodes(true);
					$.each(checkedNodes, function(index, item){
						selectData.push({
							organizationId : item.id,
							fullPath : item.fullPath,
							halfCheck : item.getCheckStatus().half==true?1:0
						});
					});
					var pojoDto = {
						empId : $("#empId").val(),
						organDto : selectData
					}
					$.ajax({
						url : url,
						type : 'post',
						data : JSON.stringify(pojoDto),
						dataType : 'json',
						contentType : 'application/json;charset=utf-8',
						success : function(data) {
							var dataType = (data.type) ? 'success' : 'error';
							notifyInfo(data.msg,dataType);
						}
					});
				});
				
				function notifyInfo(msg,type){
					Messenger().post({
						type : type,
						message : msg
					});
				}
				
				$('#resetBtn').click(function(){
					window.location.href = G_WEB_ROOT + '/emp/list';
				});
			});// end $(function(){})
		});
	</script>
</body>
</html>