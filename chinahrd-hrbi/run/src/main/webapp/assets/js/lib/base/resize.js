/**
 * htpeng
 * @param 用于动态调整mainFrame页面高度 使内容全部显示出来
 */
!function ($) {

    /**
     * 重置frame高度
     */
//	$('#main-container a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
//		window.ischang=false;
//	    });
    var autoHeight = 0;
    $.zrw_resizeFrameSize = function (id, height) {
//		var windowHeight=$(window).height();
//		alert(windowHeight);
        var win = typeof(window.parent) == null ? window : window.parent;
        if (!height) {
            height = 20;
        }
        if (!id) {
            id = "main-container";
        }
        var h = $("#" + id).height() + height;

        if (h < 768) {
            h = 768;
        }
        if($(win.document.getElementById("mainFrame")).height() == h){
            autoHeight += 1;
        }else{
            autoHeight = 0;
        }
        //$(window.parent.document.getElementById("mainFrame")).height($(document).height());
        $(win.document.getElementById("mainFrame")).height(h);
        var timer = setTimeout(function () {
            $.zrw_resizeFrameSize(id, height);
        }, 150);
        if(autoHeight > 10){
            clearTimeout(timer);
        }

//		$('#main-container a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
//			  // $(window.parent.document.getElementById("mainFrame")).height(768);
//			//$(window).resize();
//			$(window.parent.document.getElementById("mainFrame")).height(h);
//			alert($(window).height());
//			setTimeout(function(){
//				alert($(window).height());
//				
//				$(window.parent.document.getElementById("mainFrame")).height($(window).height());
//			},3000);
//			//   
//		    });
    }

    /**
     * 固定模态窗口
     */
    $.zrw_resizeModal = function (id) {
        var frames = window.parent.document.getElementById("mainFrame");
        if (!frames) {
            return;
        }
        var w = window;
        var winObj = window.parent;
        var breadcrumbs = winObj.document.getElementById("breadcrumbs");
        var navbar = winObj.document.getElementById("navbar");

        $(winObj).scroll(function () {
            var offetTop = 183;
            var _this = $(this);
            var top = _this.scrollTop();
            
//            if (!$(breadcrumbs).hasClass("breadcrumbs-fixed")) {
//                if (top > 50) {
//                    top -= 50;
//                } else {
//                    top = 0;
//                }
//
//            }
            if (!$(navbar).hasClass("navbar-fixed-top")) {
                if (top > 45) {
                    top -= 45;
                } else {
                    top = 0;
                }
            }
            $('#' + id).css({
                top: top
            });
            $('#' + id).css('overflow-y', "hidden");
            $(w).scrollTop(0);
        });
    }

}(window.jQuery)

