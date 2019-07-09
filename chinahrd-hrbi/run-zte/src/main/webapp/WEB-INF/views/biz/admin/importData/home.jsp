<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>数据导入管理</title>
	<link href="${ctx}/assets/css/biz/admin/organList.css" rel="stylesheet" />
</head>
<body>
	<div class="page-content">
		<div class="container">
			<div class="page-header">
				<h1>
					数据导入管理
					<%--<small>--%>
						<%--<i class="icon-double-angle-right"></i>--%>
						<%--Drag &amp; drop file upload with image preview--%>
					<%--</small>--%>
				</h1>
			</div><!-- /.page-header -->

			<div class="importData-warp">
				<div class="row">
					<div class="col-xs-12">
						<div class="alert alert-info">
							<i class="icon-hand-right"></i>
							在这里您可以选择需要导入的 <code>Excel数据模板</code>。这有助于我们更准确验证您的Excel结构是否合适。
							<%--<button class="close" data-dismiss="alert">--%>
								<%--<i class="icon-remove"></i>--%>
							<%--</button>--%>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12 col-xs-12">
						<div class="panel panel-default">
							<div class="panel-body">
								<form action="${ctx}/importData/doImportExcel" class="form-group" id="importForm" name="importForm"
									  role="form" enctype="multipart/form-data" method="post" >
									<div class="row">
										<div class="col-md-12 col-xs-12">
											<div class="control-group">
												<div class="checkbox-inline">
													<label>
														<input type="radio" name="type" class="form-field-checkbox" value="organ" aria-label="...">
														<span> 机构树</span>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input type="radio" name="type" class="form-field-checkbox" value="user" aria-label="...">
														<span> 用户</span>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input type="radio" name="type" class="form-field-checkbox" value="emp" aria-label="...">
														<span> 员工</span>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input type="radio" name="type" class="form-field-checkbox" value="role" aria-label="...">
														<span> 角色</span>
													</label>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6 col-xs-12">
											<input type="file" name="inputfile" id="inputfile" class="fallback">
										</div>
									</div>
									<div class="row">
										<div class="col-md-12 col-xs-12">
											<button type="submit" class="btn btn-success">提交</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" js-main="biz/admin/importData"
		src="${ctx}/assets/js/require.js"></script>
</body>
</html>