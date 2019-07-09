require(['bootstrap', 'messenger'], function() {
	var url = G_WEB_ROOT + '/user/updatePasswd.do';

    $('#pw')[0].focus();        //获取焦点

    function validate() {
    	var _pwd = $("#pw"),_repwd = $('#rpw');
		var pwdV = _pwd.val(),repwdV = _repwd.val();
    	if(!pwdV) {showErrMsg("您还没有输入密码！");_pwd[0].focus();return false;}
    	if(pwdV.length < 5) {showErrMsg("密码长度至少要6-20个字符！");_pwd[0].focus();return false;}
		if(!checkField(pwdV)) {showErrMsg("密码格式不对！");_pwd[0].focus();return false;}
    	if(!repwdV) {showErrMsg("您还没有输入确认密码！");return false;}
		if(repwdV != pwdV) {showErrMsg("您输入的密码与确认密码不一致！");return false;}
    	return true;
    }

	function checkField(val){
		var re = /[a-zA-Z]/;
		var len = re.test(val);
		if(!len) return false;
		re = /[0-9]/;
		len = re.test(val);
		if(!len) return false;
		len = checkAll(val);
		if(!len) return false;
		return true;
	}

	function checkAll(val){
		var arr = val.split('');
		var str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()_-+={}[]|;:,<>.?/";
		var bool = true;
		for(var i in arr){
			if(str.indexOf(arr[i]) === -1){
				bool = false;
				return false;
			}
		}
		return bool;
	}

	function showErrMsg(content){
		Messenger().post({
			message: content,
			type: 'error',
            hideAfter: 3
		});
	}
	$('#pw').focusin(function(){
		$(this).tooltip({
			title:'<ul class="tooltip-ul"><li>6-20个字符</li><li>支持任意大小写字母、数字及标点符号（不包括空格）</li><li>必须包含字母和数字</li></ul>',
			html: true,
			placement: 'auto right',
			trigger: 'click',
			container: 'body'
		}, 'show');
	});
	$('#pw').focusout(function(){
		var _pwd = $(this);
		var pwdV = _pwd.val();
		_pwd.tooltip('hide').tooltip('destroy');

		if(!pwdV) {showErrMsg("您还没有输入密码！");_pwd[0].focus();return false;}
		if(pwdV.length < 5) {showErrMsg("密码长度至少要6-20个字符！");_pwd[0].focus();return false;}
		if(!checkField(pwdV)) {showErrMsg("密码格式不对！");_pwd[0].focus();return false;}
	});

	$('#rpw').focusout(function(){
		var _repwd = $(this);
		var repwdV = _repwd.val(),pwdV = $("#pw").val();
		if(!repwdV) {showErrMsg("您还没有输入确认密码！");return false;}
		if(repwdV != pwdV) {showErrMsg("您输入的密码与确认密码不一致！");return false;}
	});

    $("#submitBtn").click(function() {
    	if(!validate()) {return false;}

    	var _btn = $(this);
		_btn.prop('disabled', 'disabled');
		$.post(url, {
			passwd : $("#pw").val()
		}, function(data) {
			if(data) {
				window.location.href = G_WEB_ROOT+"/";
				return;
			}
			_btn.removeAttr("disabled");
    		$("#pw").val("");
			$("#rpw").val("");
		});
    });
    
});