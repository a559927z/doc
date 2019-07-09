<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>NavMenuDemo</title>
	<style>
	textarea {
		width: 600px;
		height: 16px;
	}
	</style>
</head>
<body>
<div class="page-content">
	<!-- 异航菜单start -->
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Brand</a>
			</div>
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li>
					<li><a id="tabBtn" class="u-nMenu-a-btn" href="#">navMenuDemo<span
							class="caret"></span></a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">BootstrapDemo
							<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
							<li class="divider"></li>
							<li><a href="#">One more separated link</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- 异航菜单end -->
	<div class="container">
		<!-- 组件demo start -->
		<div class="row">
			<h2>navMenu组件，让用户供json数据</h2>
			<a id="tabBtn2" class="btn btn-primary" href="javascript:;">
				navMenuDemo-设置data<span class="caret"></span>
			</a>
			<p>文档流...文档流...文档流...文档流...文档流...文档流...文档流...F12看选取结果</p>

			<div style="height: 30px;"></div>

			<h2>navMenu组件，让用户供url</h2>
			<a id="tabBtn3" class="btn btn-primary" href="javascript:;">
				navMenuDemo-设置url <span class="caret"></span>
			</a>
			<p>文档流...文档流...文档流...文档流...文档流...文档流...文档流...F12看选取结果</p>

			<div style="height: 30px;"></div>
			<h2>bootstarp没有js，纯css3控制</h2>
			<div class="dropdown">
				<a id="dLabel" role="button" data-toggle="dropdown"
					class="btn btn-primary" data-target="#" href="javascript:;">
					bootstarpDemo <span class="caret"></span>
				</a>
				<ul class="dropdown-menu multi-level" role="menu"
					aria-labelledby="dropdownMenu">
					<li><a href="javascript:;">一级菜单</a></li>
					<li><a href="javascript:;">一级菜单</a></li>
					<li class="divider"></li>
					<li class="u-nMenu-dropdown-submenu"><a href="javascript:;">一级菜单A</a>
						<ul class="dropdown-menu">
							<li><a href="javascript:;">二级菜单</a></li>
							<li><a href="javascript:;">二级菜单AA</a></li>
							<li class="divider"></li>
							<li class="u-nMenu-dropdown-submenu"><a href="javascript:;">二级菜单AA</a>
								<ul class="dropdown-menu">
									<li><a href="javascript:;">三级菜单AAA</a></li>
								</ul></li>
						</ul></li>
					<li class="divider"></li>
					<li class="u-nMenu-dropdown-submenu"><a href="javascript:;">一级菜单B</a>
						<ul class="dropdown-menu">
							<li class="u-nMenu-dropdown-submenu"><a href="javascript:;">二级菜单BB</a>
								<ul class="dropdown-menu">
									<li class="u-nMenu-dropdown-submenu"><a
										href="javascript:;">三级菜单BBB</a>
										<ul class="dropdown-menu">
											<li><a href="javascript:;">四级菜单BBBB</a></li>
										</ul></li>
								</ul></li>
						</ul></li>
				</ul>
			</div>
		</div>
		<!-- 组件demo end -->
		<!-- 使用说明start -->
		<div style="height: 30px;"></div>
		<div class="container">
			<div class="row">
				<h2>使用说明:使用navMenu三步搞掂</h2>
			</div>
			<div class="page-header">
				<span class="label label-default">import</span> <small> <textarea
						style="height: 68px;">
				注意css引入的顺序
				<link href="bootstrap.min.css" rel="stylesheet" />
				<link href="global.css" rel="stylesheet" />
				<script src="require.js"></script></textarea>
				</small> <br /> <br /> <span class="label label-default">Html</span> <small>
					<textarea> <a id="btnId" href="javascript:;">btn<span
							class="caret"></span></a></textarea>
				</small> <br /> <br /> <span class="label label-default">js</span> <small>
					<textarea>  $('#btnId').navMenu({url : url}).show();</textarea>
				</small>
			</div>
			<!-- 使用说明end -->
		</div>
	</div>
</div>
<script src="${ctx}/assets/js/demo/navMenuDemo.js"></script>
</body>
</html>