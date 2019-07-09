require(["bootstrap", "ace", "bottomStock"], function () {
    var index = $("#sidebar").find('a[target="mainFrame"]').parent(".active").children();
    recordPage.push(index);

    var hmain = document.getElementById("mainFrame");
    var fScreenBool = false;

    function activeMenu(href) {
        hmain.src = href;
        $("#navList .active").removeClass("active").removeClass("open");
        $("#navList a").each(function () {
            if ($(this).attr("href") == href) {
                $(this).parent().addClass("active").parent().parent().addClass("active").addClass("open");
            }
        });
    }

    var homepageName = $("#breadcrumbLast").text();
    var homepageLink = $("#breadcrumbFirst a").data("href");
    $("#breadcrumbFirst a").click(function () {
        $("#breadcrumbLast").text(homepageName);
        activeMenu(homepageLink);
    });
    function cmainFrame() {
        if (fScreenBool)  $(hmain).height($(window).height());
        else  $(hmain).height($(window).height() - 45);
    }

    function redirectIframe() {
        $(".redirectIframe").unbind("click").on("click", function () {
            activeMenu($(this).data("href"));
        });
    }

    function setSidebar() {
        var _sidebar = document.getElementById('sidebar');
        if (_sidebar) {
            $(_sidebar).find('a[target="mainFrame"]').bind('click', function (e) {
                if ($(recordPage[recordPage.length - 1]).html() != $(this).html()) {
                    recordPage.push(this);
                }
                e.preventDefault();
                var _this = $(this);
                var _parent = _this.closest('.submenu');

                $('#navList li').removeClass('active').removeClass('open');
                $('#navList li .submenu').hide();
                _this.parent().addClass('active');
                _parent.show();
                var nav = _this.attr("href") != homepageLink ? "<a href=\"javascript:;\" class=\"redirectIframe\" data-href=\"" + _this.attr("href") + "\" target=\"mainFrame\">" + _this.text() + "</a>" : homepageName;
                if (_parent.length > 0) {
                    var _prev = _parent.prev().children('span');
                    _parent.parent().addClass('active').addClass('open');
                    $('#breadcrumbSecond').html(_prev.text()).removeClass('hide');
                    $('#breadcrumbLast').html(nav);
                } else {
                    $('#breadcrumbSecond').html('').addClass('hide');
                    $('#breadcrumbLast').html(nav);
                }
                redirectIframe();
                hmain.src = _this.attr('href');
                window.stackObject.bottomStock('hideFrame', 1);
            });
        }
    }

    setSidebar();
    cmainFrame();

    $(window).resize(function () {
        cmainFrame();
    });

    window.stackObject = $('#bottom_layout').bottomStock({hide: 1});

    window.setlocationUrl = function (url) {
        hmain.src = url;
    }

    window.setCurrNavStyle = function () {
        var navUrl = hmain.src;
        navUrl = navUrl.split('?')[0];
        var navUrls = navUrl.split('/');
        navUrl = navUrls[navUrls.length - 2] + '/' + navUrls[navUrls.length - 1];
        var _navList = $('#navList');
        if (!_navList) {
            return false;
        }
        var _currLinkObj = _navList.find('a[href="' + navUrl + '"]');

        $('#navList li').removeClass('active').removeClass('open');
        $('#navList li .submenu').hide();

        _currLinkObj.parent().addClass('active');
        var _parents = _currLinkObj.closest('ul');
        if (_parents.hasClass('submenu')) {
            _parents.show();
            _parents.parent().addClass('active open');
        }
    }

    window.doFullScreen = function (func) {
        $(document.body).removeClass('navbar-fixed').css('overflow', 'hidden');
        $('#sidebar,#navbar').hide();
        $('#main-content').css('marginLeft', 0);
        fScreenBool = true;
        cmainFrame();
        if ($.isFunction(func))   func();
    }


    window.doRestoreWindow = function (func) {
        $(document.body).addClass('navbar-fixed');
        $('#sidebar,#navbar').show();
        $('#main-content').removeAttr('style');
        fScreenBool = false;
        cmainFrame();
        if ($.isFunction(func))   func();
    }

    //TODO 开发使用
//    window.setlocationUrl('positionCompetency/toPositionCompetencyView');
//     window.setlocationUrl('monthReport/toMonthReportView');
});