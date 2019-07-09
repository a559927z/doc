<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>提示控件</title>
<style>
</style>
</head>
<body>
	<div class="page-content">
		<a href="http://github.hubspot.com/messenger" target="_blank">messenger的文档地址</a>
		<div>
			<code>
				Messenger().post({
				message: '直接拷贝我就够用了',
				type: 'info'//success、error
				})
			</code>
		</div>
		<div class="normal">
			<button id="info">一般提示</button>
			<button id="success">成功提示</button>
			<button id="error">错误</button>
		</div>

		<div>
			<button id="btn">带有按钮</button>
		</div>
		<div>
			<button id="event">事件，估计一般都用不上了</button>
		</div>
	</div>

<script>
	require([ 'messenger'], function() {

		Messenger().post({
			  message: '直接拷贝我就够用了',
			  type: 'info'//success、error
		})


		/**以下是提示信息的其他用法，不常用
		--------------------------------------*/
		// Messenger().post("这是最简单写法，效果默认值为info");


		 $('.normal button').click(function(){
			 var type = $(this).attr('id');
			 Messenger().post({
				  id : type, //同一id消息框只会出现一个
				  message: type,
				  type: type
			})
		 })

		 $('#btn').click(function(){
			 var msg = Messenger().post({
				  message: "你确定要删除该信息吗",
				  actions: {
					add: {
					  label:"添加",
					  action: function(){
						  alert('添加')
						msg.hide();
					  }
					},
					del: {
					  label:"删除",
					  action: function(){
						  alert('删除')
						  //do sth
						msg.hide();
					  }
					},


					cancel: {
					  action: function(){
						msg.hide()
					  }
					}
				  }
				})
		 });
		 //消息有添加删除按钮

			$('#event').click(function(){
				Messenger().post({
					  message: "点击事件弹出框产生事件",
					  events: {
						"click": function(e){
						  alert("点击");
						}
					  }
					});
			});
			//
	});
</script>
</body>
</html>
