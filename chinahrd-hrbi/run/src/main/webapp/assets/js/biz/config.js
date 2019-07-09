require(['jquery','bootstrap','validate','validateCH','messenger', 'underscore', 'utils'], function ($) {
    var webRoot = G_WEB_ROOT;
    var hostValid = /^([a-zA-Z0-9])+\.(([a-zA-Z0-9])+\.)?([a-zA-Z0-9])+[a-zA-Z0-9]$/;
    var emailValid= /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
    var emailMapper=[{id:"XTSZ-eMailHost",name:"邮件主机",valid:{rule:hostValid,message:"格式不正确"}},
                {id:"XTSZ-eMailPort",name:"邮箱端口"},
                {id:"XTSZ-eMailAccount",name:"发送人的邮箱账户",valid:{rule:emailValid,message:"格式不正确"}},
                {id:"XTSZ-eMailPassword",name:"发送人的邮箱账户密码"},
                {id:"XTSZ-adminMail",name:"admin邮箱",valid:{rule:emailValid,message:"格式不正确"}}
                ];
    var urls = {
		getSysConfigUrl: webRoot + '/config/getSysConfig.do',	//获取所有系统配置
		upadteSysConfigUrl: webRoot + '/config/upadteSysConfig.do',	//修改所有系统配置
		sendEmailUrl: webRoot + '/config/sendEmail.do',	//发送测试邮件
		sendMessageUrl: webRoot + '/config/sendMessage.do',	//发送测试短信
    };

    $("#sendEmail").click(function(){
    	var map={};
    	var bool=true;
    	var message="";
    	$.each(emailMapper,function(index,o){
    		if(bool){
    			var val=$("#"+o.id).val();
        		if($.trim(val)==""){
        			message=o.name+"不能为空";
        			bool=false;
        		}else{
        			if(o.valid){
        				bool=o.valid.rule.test(val);
        				if(!bool){
        					message=o.name+o.valid.message;
        				}
        			}
        			map[o.id]=val;
        		}
        		
    		}
    	});
    	if(bool){
    		 $.ajax({
       	         url: urls.sendEmailUrl,
       	         type: 'post',
       	         data: JSON.stringify(map),
       	         dataType: 'json',
       	         contentType: 'application/json;charset=utf-8',
       	         success: function (data) {
       	             var dataType = (data.type) ? 'success' : 'error';
       	             notifyInfo(data.msg, dataType);
       	             if(data.type){
       	            	recordSysConfig=map;
       	             	//$('#resetBtn').click();
       	             }
       	         }
       	     });
    	}else{
    		alert(message);
    	}
    });
    $("#sendMessage").click(function(){
   	 $.ajax({
  	         url: urls.sendMessageUrl,
  	         type: 'post',
  	         data: JSON.stringify(list),
  	         dataType: 'json',
  	         contentType: 'application/json;charset=utf-8',
  	         success: function (data) {
  	             var dataType = (data.type) ? 'success' : 'error';
  	             notifyInfo(data.msg, dataType);
  	             if(data.type){
  	            	recordSysConfig=map;
  	             	//$('#resetBtn').click();
  	             }
  	         }
  	     });
   });
function notifyInfo(msg, type) {
    Messenger().post({
        type: type,
        message: msg
    });
}
$("#footer").find("span").click(function(){
	initPage();
});  
var recordSysConfig;
function initPage(){
	$.each(recordSysConfig,function(i,o){
		$("#"+i).val(o);
	});
}
    $.ajax({
        url: urls.getSysConfigUrl,
        success: function (result) {
        	if(!_.isEmpty(result)){
        		recordSysConfig=result;
        		initPage();
        	}
        }
	});
    jQuery.validator.addMethod("host", function(value, element, param) {
    	  return this.optional(element) || (hostValid.test(value));
    	}, $.validator.format("请输入正确的邮箱服务器主机地址"));
    $('.form-horizontal').validate({  
        errorElement : 'span',  
        errorClass : 'help-block',  
        focusInvalid : true,  
        rules : {  
        	sysname : {  
                required : true  
            },  
            password : {  
            	required: true,
                minlength: 10,
                maxlength: 20
            },
            repassword:{
            	required: true,
            	equalTo:"#XTSZ-adminPassword"
            },emailHost:{
            	host : [10,20],  
            }
        },  
        messages : {  
        	sysname : {  
                required : "系统名称不能为空."  
            },  
            password : {  
                required : "密码不能为空.", 
                minlength: "密码长度为10-20个字符",
                minlength: "密码长度为10-20个字符",
            },repassword:{
            	required: "请输入确认密码",
                equalTo: "两次输入密码不一致不一致"
            }
        },  
        highlight : function(element) {  
            $(element).closest('.form-group').addClass('has-error');  
        },  
        success : function(label) {  
            label.closest('.form-group').removeClass('has-error');  
            label.remove();  
        },  
        errorPlacement : function(error, element) {  
            element.parent('div').append(error);  
        },submitHandler:function(form){
        	var list=[];
        	var map={};
	   		 $.each($('.form-horizontal input'), function (index, el) {
	   			 list.push({
	   				 configKey: $(el).attr("id"),
	   				 configVal: $(el).val()
	   	         });
	   			map[$(el).attr("id")]=$(el).val();
	   	     });
	   		 $.each($('.form-horizontal select'), function (index, el) {
	   			 list.push({
	   				 configKey: $(el).attr("id"),
	   				 configVal: $(el).val()
	   	         });
	   			map[$(el).attr("id")]=$(el).val();
	   	     });
	   	     $.ajax({
	   	         url: urls.upadteSysConfigUrl,
	   	         type: 'post',
	   	         data: JSON.stringify(list),
	   	         dataType: 'json',
	   	         contentType: 'application/json;charset=utf-8',
	   	         success: function (data) {
	   	             var dataType = (data.type) ? 'success' : 'error';
	   	             notifyInfo(data.msg, dataType);
	   	             if(data.type){
	   	            	recordSysConfig=map;
	   	             	//$('#resetBtn').click();
	   	             }
	   	         }
	   	     });
        }    
    });  
    
});