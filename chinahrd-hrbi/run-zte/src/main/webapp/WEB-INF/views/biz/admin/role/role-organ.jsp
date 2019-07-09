<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>角色数据配置</title>
	<link href="${ctx}/assets/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" />
	<style>
		.panel-footer .btn:last-child{
			margin-left: 12px;
		}
	</style>
</head>
<body>
	<div class="page-content">
		<div class="container">
			<div class="page-header">
				<input type="hidden" id="roleId" value="${roleDto.roleId}">
				<h1>
					角色数据配置界面<small>(${roleDto.roleName})</small>
				</h1>
			</div>
			<div class="col-md-6 col-sm-8 col-xs-12">
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

	<script type="text/javascript" src="${ctx}/assets/js/require.js"></script>
	<script type="text/javascript">
		require([ 'messenger', 'ztree','utils' ], function() {
			var getTreeDataJsonUrl = G_WEB_ROOT + "/role/getTreeDataJson";
			var treeData = [];
			function getTreeDataJson(){
				console.log($("#roleId").val());
				$.ajax({
					url : getTreeDataJsonUrl,
					type : 'post',
					dataType : 'json',
					data:{roleId: $("#roleId").val()},
					async: false,	//同步
					success : function(data) {
						console.log(data);
						treeData = data;
					}
				});
			}
			getTreeDataJson();
			
			
			var url = G_WEB_ROOT + "/role/addRoleOrganiation";
			
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
					var selectData = [];
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
						roleId : $("#roleId").val(),
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
					window.location.href = G_WEB_ROOT + '/role/list';
				});
			});// end $(function(){})
		});
	</script>
</body>
</html>