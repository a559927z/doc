<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>功能管理界面</title>
	<link href="${ctx}/assets/css/ztree/metroStyle/metroStyle.css" rel="stylesheet" />
	<style>
		.radio-inline{
			padding-left: 0;
			padding-right: 10px;
		}
		.radio-inline label{
			cursor: pointer;
		}
	</style>
</head>
<body>

	<div class="rightBody">
		<div class="SetUpBody">
			<div class="index-common-title bottom-title">
				<div class="index-common-title-left bottom-left"></div>
				<div class="index-common-title-text bottom-text">功能界面</div>
			</div>
			<div class="bottom-div clearfix">
				<div class="col-sm-12 ct-col2">
					<button type="button" class="btn btn-info" id="addParentFunctionBtn">添加根节点</button>
					<ul id="ztree" class="ztree"></ul>
				</div>
			</div>
		</div>
	</div>


	<!-- 功能(Modal) -->
	<div class="modal fade" id="functionModal" tabindex="-1" role="dialog"
		 aria-labelledby="functionModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<form id="functionFrom" name="functionFrom" class="form-horizontal" action="${ctx}/function/editFunction" method="post">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
					<h3 class="modal-title" id="functionModalLabel">添加功能<input type="hidden" name="functionId"></h3>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label class="col-sm-3 control-label">功能编码</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="functionKey" placeholder="请输入功能编码">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">功能名称</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="functionName" placeholder="请输入功能名称">
						</div>
					</div>
					<div class="form-group" id="parentFunctionGroup">
						<label class="col-sm-3 control-label">上级功能</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" id="functionParentName" readonly>
							<input type="hidden" class="form-control"  id="functionParentKey" name="pKey">
							<input type="hidden" class="form-control" id="functionParentId" name="pid">
							<input type="hidden" class="form-control" id="functionParentTreeId">
						</div>
					</div>
					<div class="form-group" id="isMenuGroup">
						<label class="col-sm-3 control-label">是否菜单栏</label>
						<div class="col-sm-9">
							<div class="radio-inline">
								<label>
									<input name="isMenu" type="radio" value="1" class="ace" checked="checked">
									<span class="lbl">是</span>
								</label>
							</div>
							<div class="radio-inline">
								<label>
									<input name="isMenu" type="radio" value="0" class="ace">
									<span class="lbl">否</span>
								</label>
							</div>
						</div>
					</div>
					<div class="form-group hide" id="urlGroup">
						<label class="col-sm-3 control-label">功能链接</label>
						<div class="col-sm-9">
							<input type="text" class="form-control" name="url" placeholder="请输入功能链接">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">功能排序</label>
						<div class="col-sm-9">
							<input type="number" class="form-control" name="showIndex" placeholder="请输入功能排序">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">功能描述</label>
						<div class="col-sm-9">
							<textarea name="note" rows="3" class="form-control" placeholder="请输入功能描述"></textarea>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">提交更改</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div><!-- /.modal-content -->
			</form>
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

	<!-- 功能操作(Modal) -->
	<div class="modal fade" id="itemModal" tabindex="-1" role="dialog"
		 aria-labelledby="itemModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<input type="hidden" id="itemParentTreeId">
			<form id="itemFrom" name="itemFrom" class="form-horizontal" action="${ctx}/function/editFunctionItem" method="post">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">×</button>
						<h3 class="modal-title" id="itemModalLabel">添加功能操作<input type="hidden" name="functionItemId"></h3>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-sm-2 control-label">操作说明</label>
							<div class="col-sm-10">
								<input type="text" name="note" class="form-control" placeholder="请输入操作说明" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">操作项</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="itemCode" placeholder="请输入操作项">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">所属功能</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="functionName" readonly>
								<input type="hidden" id="functionId" name="functionId">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">提交更改</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div><!-- /.modal-content -->
			</form>
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

	<!-- 删除（Modal） -->
	<div class="modal fade" id="removeModal" tabindex="-1" role="dialog"
		 aria-labelledby="removeModalLabel" aria-hidden="true">
		<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3 class="modal-title" id="removeModalLabel">警告</h3>
					</div>
					<div class="modal-body">是否确定删除？</div>
					<div class="modal-footer">
						<button type="button" id="removeSubmitBtn" class="btn btn-primary">确定</button>
						<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
					</div>
				</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<script src="${ctx}/assets/js/biz/admin/functionList.js"></script>
</body>
</html>