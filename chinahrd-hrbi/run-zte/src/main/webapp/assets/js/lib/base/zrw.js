jQuery(function () {

    handle_side_menu();

    //菜单缩放
    $("#sidebar-collapse").on("click", function () {
        $("#sidebar").toggleClass("menu-min");
        $(this.firstChild).toggleClass("icon-double-angle-right");
        var a = $("#sidebar").hasClass("menu-min");
        if (a) {
            $(".open > .submenu").removeClass("open");
        }
    });
    /**
     * 菜单点击
     */
    function handle_side_menu() {
        $("#menu-toggler").on("click", function () {

            $("#sidebar").toggleClass("display");
            $(this).toggleClass("display");
            return false;
        });
        var a = false;
        $(".nav-list .dropdown-toggle").each(function () {
                var b = $(this).next().get(0);
                $(this).on("click", function (e) {
                    if (a) {
                        return false;
                    }
                    $(".open > .submenu").each(function () {
                        if (this != b && !$(this.parentNode).hasClass("active")) {
                            $(this).slideUp(200).parent().removeClass("open")
                        }
                    });
                    $(b).slideToggle(200).parent().toggleClass("open");
                    return false;
                })
            }
        )
    }

});