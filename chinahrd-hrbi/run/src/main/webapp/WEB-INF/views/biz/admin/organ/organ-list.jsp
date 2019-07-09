<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>组织机构管理</title>
	<link href="${ctx}/assets/css/biz/admin/organList.css" rel="stylesheet" />
</head>
<body>
	<div id="ol-GridData" class="hidden">${excelDataJson}</div>

	<div class="page-content">
		<div class="main-container-inner">
			<div class="organList-warp">
					<div class="col-xs-12">
						<h4 class="page-header">
							机构级别维 <small>在添加机构数据前，我们必须完成前下以的步骤。<code>机构级别维度</code> 和<code>机构行业维度</code>
								的数据导入，这有助于我们更准确验证机构数据。
							</small> <small>【机构级别维度】Excel导入必须具备：<code>organization_type_id</code>
								、<code>organization_type_key</code> 、<code>organization_type_level</code>、<code>organization_type_name</code>
								的字段。
							</small>
						</h4>
					</div>
					<div class="col-xs-8">
						<form action="${ctx}/organ/doImportExcel" role="form"
							enctype="multipart/form-data" method="post">
							<div class="form-group">
								<input type="file" name="inputfile" value="123">
							</div>
							<button type="submit" class="btn btn-success">提交</button>
						</form>
					</div>
					<div class="col-xs-4"></div>
					<div class="col-xs-12">
						<table id="grid-table"></table>
						<div id="grid-pager"></div>
					</div>
				<p class="text-right">
					<button class="btn btn-primary">下一步</button>
				</p>

			</div>
		</div>
	</div>
	</div>
	</div>
	<script src="${ctx}/assets/js/biz/admin/organList.js"></script>
</body>
</html>