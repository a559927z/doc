<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>角色数据配置</title>
	<link href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css" rel="stylesheet" />
	<style>
	h4{
		font-size: 18px;
		font-weight: normal;
		font-family: "Open Sans","Helvetica Neue",Helvetica,Arial,sans-serif;
	}

	.container .body {
		border: 1px solid #A0A0A0;
		overflow: auto;
		overflow-x: hidden;
		height: 500px;
	}
	</style>
</head>
<body>
	<div class="page-content">
		<div class="container">
			<input type="hidden" id="roleId" value="${roleDto.roleId}">
			<input type="hidden" id="fullPathsStr" value="${fullPathsStr}">
			<h4>
				角色数据配置界面<small>(${roleDto.roleName})</small>
			</h4>
			<div class="col-xs-8 body">
				<ul id="orgTree" class="ztree"></ul>
			</div>

			<div class="col-xs-12">
				<button type="submit" class="btn btn-primary submitBtn">提交</button>
				<button type="button" class="btn btn-default" id="resetBtn">返回</button>
			</div>
		</div>
	</div>

	<script>
		require([ 'messenger', 'ztree','utils' ], function() {
			var fullPathsArr = [];
			function processFullPathStr(){
				var fullPathsStr = $("#fullPathsStr").val();
				fullPathsArr = fullPathsStr.split(",");
			}
			processFullPathStr();
			
			var url = G_WEB_ROOT + "/role/addRoleOrganiation";
			var selectData = [];
			var treeObj;
			var setting = {
				async : {
					enable : true,
					url : G_WEB_ROOT + "/role/querySubOrganOfConfig",
					autoParam : [ "id" ],
					otherParam : ["fullPaths",fullPathsArr ]
				},
				check : {
					enable : true,
// 					chkboxType : { "Y" : "s", "N" : "s" }
					chkboxType : { "Y" :"", "N" : "" }
				},
				callback : {
					beforeClick : function(treeId, treeNode) {
						if (treeNode.isParent) {
							treeObj.expandNode(treeNode);
							return false;
						} 
					},
					// 获取已选的节点（点击节点）
					onCheck : function(event, treeId, treeNode) {
						if (treeNode.checked) {
// 							selectData.push({
// 								organizationId : treeNode.id,
// 								fullPath : treeNode.fullPath
// 							});
						}
					}
				}
			};

			$(function() {
				var el = $("#orgTree");
				treeObj = $.fn.zTree.init(el, setting);

				$(".submitBtn").click(function() {
					// 获取已选的节点（已存在节点）
					var checkedNodes = treeObj.getCheckedNodes(true);
					$.each(checkedNodes, function(index, item){
// 						if(!(item.children)){
							selectData.push({
								organizationId : item.id,
								fullPath : item.fullPath
							});
// 						}
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
// 							treeObj.reAsyncChildNodes(true, "refresh",true);
// 							treeObj = $.fn.zTree.getZTreeObj(el);
// 						setTimeout(window.location.href = G_WEB_ROOT + "/role/roleOrgan?roleId="+$("#roleId").val(),8000);
							
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