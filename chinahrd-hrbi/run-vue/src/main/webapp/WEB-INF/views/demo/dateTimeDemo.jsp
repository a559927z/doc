<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>日期组件</title>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
    <link href="${ctx}/assets/css/datetime/datetimepicker.css" rel="stylesheet" media="screen"/>
    <style>
    	.container{
    		margin:50px auto;
    	}
    </style>
</head>
<body>
<div class="page-content">
	<div class="container">
		<form action="" class="form-horizontal"  role="form">
			<fieldset>
				<div class="form-group">
					<div class="input-group date form_date col-md-5" data-date=""  data-link-field="dtp_input2" >
						<input class="form-control" size="16" type="text" value="" readonly>
						<span class="input-group-addon"><span class="glyphicon glyphicon-remove icon-remove"></span></span>
						<span class="input-group-addon"><span class="glyphicon glyphicon-calendar icon-calendar"></span></span>
					</div>
					<input type="hidden" id="dtp_input2" value="" /><br/>
				</div>

			</fieldset>
		</form>
	</div>
</div>
<script>
require(['datetimepicker'],function(){
		
		 $(".form_date").datetimepicker({
		        format: "yyyy-mm-dd - hh:ii:ss",
		        autoclose: true,
		        todayBtn: true,
		        startDate: "2013-02-14 10:00",
		        minuteStep: 10,
		        pickerPosition: "bottom-left"
		    });
});
</script>
</body>
</html>