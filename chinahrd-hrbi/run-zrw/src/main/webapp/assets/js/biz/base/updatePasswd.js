require(['bootstrap'], function () {
    var webRoot = G_WEB_ROOT;
    var urls = {
        toHomeUrl: webRoot + '/',
        doUpdateUrl: webRoot + '/user/updatePasswd.do'
    }

    function checkField(val) {      //检查格式
        var re = /[a-zA-Z]/;
        var len = re.test(val);
        if (!len) return false;
        re = /[0-9]/;
        len = re.test(val);
        if (!len) return false;
        return true;
    }

    function checkAll(val) {
        var arr = val.split('');
        var str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()_-+={}[]|;:,<>.?/";
        var bool = true;
        for (var i in arr) {
            if (str.indexOf(arr[i]) === -1) {
                bool = false;
                return false;
            }
        }
        return bool;
    }

    function showErrMsg(content) {
        var $passwdCaption = $('#passwdCaption');
        $passwdCaption.text(content);
    }

    var passwdObj = {
        pwId: '#pw',
        rpwId: '#rpw',
        submitId: '#submitBtn',
        init: function () {
            var self = this;
            $(self.pwId)[0].focus();        //获取焦点

            self.bindEvent();
        },
        bindEvent: function () {
            var self = this, $pw = $(self.pwId), $rpw = $(self.rpwId), $submit = $(self.submitId), isShow = false;
            $pw.tooltip({
                title: '<ul class="tooltip-ul"><li>6-20个字符</li><li>支持任意大小写字母、数字及标点符号（不包括空格）</li><li>必须包含字母和数字</li></ul>',
                html: true,
                placement: 'auto right',
                trigger: 'manual',
                container: 'body'
            });

            $pw.keyup(function () {
                if (!isShow) {
                    $pw.tooltip('show');
                    isShow = true;
                }
                var pwdV = $(this).val(), $tooltipSub = $('.tooltip .tooltip-ul li');
                var $sub0 = $($tooltipSub[0]), $sub1 = $($tooltipSub[1]), $sub2 = $($tooltipSub[2]);

                if (pwdV.length >= 6 && checkAll(pwdV) && checkField(pwdV) && isShow) {
                    $tooltipSub.removeClass('not-passed').addClass('passed');
                    $pw.tooltip('hide');
                    isShow = false;
                    return;
                }

                if (pwdV.length < 6) {      //检查密码长度
                    if ($sub0.hasClass('passed') || !$sub0.hasClass('not-passed')) {
                        $sub0.removeClass('passed').addClass('not-passed');
                    }
                } else {
                    if ($sub0.hasClass('not-passed') || !$sub0.hasClass('passed')) {
                        $sub0.removeClass('not-passed').addClass('passed');
                    }
                }
                if (!checkAll(pwdV)) {        //检查密码格式
                    if ($sub1.hasClass('passed') || !$sub1.hasClass('not-passed')) {
                        $sub1.removeClass('passed').addClass('not-passed');
                    }
                } else {
                    if ($sub1.hasClass('not-passed') || !$sub1.hasClass('passed')) {
                        $sub1.removeClass('not-passed').addClass('passed');
                    }
                }
                if (!checkField(pwdV)) {     //密码必须包含字母数字
                    if ($sub2.hasClass('passed') || !$sub2.hasClass('not-passed')) {
                        $sub2.removeClass('passed').addClass('not-passed');
                    }
                } else {
                    if ($sub2.hasClass('not-passed') || !$sub2.hasClass('passed')) {
                        $sub2.removeClass('not-passed').addClass('passed');
                    }
                }
            });
            $pw.focusout(function () {
                $pw.tooltip('hide');
                isShow = false;
                self.validate();
            });

            $rpw.keyup(function () {
                var repwdV = $(this).val(), pwdV = $pw.val();
                if (!repwdV) {
                    showErrMsg("您还没有输入确认密码");
                    return false;
                }
                if (repwdV != pwdV) {
                    showErrMsg("您输入的密码与确认密码不一致");
                    return false;
                }
                self.validate();
            });

            $submit.click(function () {
                if (!self.validate()) {
                    return false;
                }

                var _btn = $(this);
                _btn.prop('disabled', 'disabled');
                var $uk =  USER_KEY; //$("#userKey").val();
                
                console.log(urls.doUpdateUrl, $uk,$pw.val());
                $.post(urls.doUpdateUrl, {userKey:$uk, passwd: $pw.val()}, function (data) {
                	
                    if (data) {
                        window.location.href = urls.toHomeUrl;
                        return;
                    }
                    _btn.removeAttr("disabled");
                    $pw.val("");
                    $rpw.val("");
                });
            });
        },
        validate: function () {
            var self = this, pwdV = $(self.pwId).val(), repwdV = $(self.rpwId).val(), $submit = $(self.submitId);
            if (!pwdV) {
                showErrMsg("您还没有输入密码");
                return false;
            }
            if (pwdV.length < 5 || !checkField(pwdV) || !checkAll(pwdV)) {
                showErrMsg("您输入的密码格式不对");
                return false;
            }
            if (!repwdV && document.activeElement.id != (self.rpwId.split('#')[1])) {
                showErrMsg("您还没有输入确认密码");
                return false;
            }
            if (repwdV != pwdV) {
                showErrMsg("您输入的密码与确认密码不一致");
                return false;
            }
            $submit.removeAttr('disabled');
            // showErrMsg("您当前的登陆密码过于简单，请输入您的新密码");
            showErrMsg("密码符合规范，请按更改密码按钮提交更改");
            return true;
        }
    }
    passwdObj.init();
});