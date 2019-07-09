<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE HTML> 
<html> 
<head>
	<title>提示组件</title>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
    <link href="${ctx}/assets/css/datetime/datetimepicker.css" rel="stylesheet" media="screen"/>
    <style>
    	.container{
    		margin:0px auto;
    		padding:150px;
    	}
    	.container .row{
    		display:inline-block;
    		margin-top:30px;
    	}
    </style>
</head>
<body>
<div class="page-content">
	<div class="container">
		<div class="row">
			<h1>confirm</h1>
			<a class="btn" data-toggle="confirmation" data-placement="bottom" data-original-title="" title="">确认</a>
	   </div>
		<div class="row">
			   <h1>Tooltip</h1>
			   <p class="well tooltip-demo">Tight pants next level keffiyeh
					<a title="Default tooltip" href="#" rel="tooltip">you probably</a> apparel
					<a href="#" rel="tooltip" data-original-title="Another tooltip">have a</a> A really ironic artisan
					<a title="Another one here too" href="#" rel="tooltip">whatever keytar</a>, scenester farm-to-table banksy Austin
					<a href="#" rel="tooltip" data-original-title="The last tip!">twitter handle</a> freegan cred raw .
			   </p>
		</div>
		<div class="row">
			 <h1>提示、警告样式</h1>
			 <div class="alert alert-warning alert-dismissible" role="alert">
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				  <strong>Warning!</strong> Better check yourself, you're not looking too good.
			 </div>

		</div>
	</div>
</div>
<script>
require(['confirmation'],function(){
	  $('[data-toggle="confirmation"]').confirmation({
		  trigger:'click', //'事件类型'
		  onConfirm:function(e){
			  console.info(e);
		  },
		  onCancel:function(e){
			  console.info(e);
		  }
	  });
	  $('.tooltip-demo').tooltip({
          selector: "a[rel=tooltip]"
      });

});
</script>
</body>
</html>