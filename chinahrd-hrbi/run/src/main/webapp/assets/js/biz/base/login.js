require(['jquery', 'bootstrap', 'placeholder'], function ($) {
    var win = (window != top) ? top : window;

    $("#u,#p").placeholder().on('focus', function () {
        $(this).parent().parent().addClass("inputFocus");
    }).on('blur', function () {
        $(this).parent().parent().removeClass("inputFocus");
    });

    function validate() {
        var user = $("#u").val(), pwd = $("#p").val();
        if (!user) {
            showErrMsg("您还没有输入用户名！");
            return false;
        }
        if (user.length < 3) {
            showErrMsg("请输入正确的用户名！");
            return false;
        }
        if (!pwd) {
            showErrMsg("您还没有输入密码！");
            return false;
        }
        if (pwd.length < 5) {
            showErrMsg("请输入正确的密码！");
            return false;
        }
        return true;
    }

    function showErrMsg(msg) {
        var tips = $("#errTips");
        tips.css("visibility", "visible").text(msg);
        tips.delay(1500).fadeOut(function () {
            tips.show().css("visibility", "hidden");
        });
    }

    $("#loginBtn").click(function () {
        if (!validate()) {
            return;
        }
        var btn = $(this), location = document.location, url = location.href;
        if (url.indexOf('/login') === -1) {
            url = location.protocol + "//" + location.host + location.pathname + "login";
            console.log(url);
        }
        var params = {
            username: $("#u").val(),
            password: $("#p").val()
        }
        btn.attr('disabled', true).html('登录中...');
        $.post(url, params, function (data) {
            if (data.roleIsNull) {
            	 btn.removeAttr("disabled").html('登录');
                showErrMsg("您的用户名还有没角色！请联系管理员。");
                return;
            }
            if (data.toUpdatePasswd) {
                var url = data._redirectUrl;
                $('#updateModal').modal('show');
                var i = 4;  //定时跳转
                var timeInterval = setInterval(function () {
                    $('.time-remind').text(i);
                    if (i == 0) {
                        clearInterval(timeInterval);
                        win.location.href = url;
                    }
                    i--;
                }, 1000);
                $('.btn-link').attr('href', url);
                return;
            }
            if (data._redirectUrl) {
                win.location.href = data._redirectUrl;
                return;
            }
            showErrMsg("您输入的帐号或密码不正确！请重新输入。");
            btn.removeAttr("disabled").html('登录');
            $("#p").val("");
        }, 'json');
    });

    $(window).keydown(function (e) {
        if (e.keyCode == 13 && (document.activeElement.id == "u" || document.activeElement.id == "p")) {
            $("#loginBtn").click();
        }
    })

});