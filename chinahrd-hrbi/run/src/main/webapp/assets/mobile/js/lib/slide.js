
/*
*@param author htpeng
*/

(function ($) {
    'use strict'

    !function () {
        var $parentFixed = $('.vs-cartype');
        if ($parentFixed.length > 0) {
            var parTop = $parentFixed.offset().top;
            var oBox = $parentFixed.find('.box');
//            $(window).on('scroll', function () {
//                if (parTop <= $('body').scrollTop()) {
//                    // oBox.addClass('fixed');
//                    oBox.css({
//                        'position': 'fixed',
//                        'top': '0px',
//                        'left': '0px',
//                        'right': '0px',
//                        'zIndex': 99
//                    });
//                } else {
//                    // oBox.removeClass('fixed');
//                    oBox.attr("style","");
//                }
//            });
        }
    }();
    /*
    *@function swipleftRight 屏幕左右滑动
    */
    var swipleftRight = function (config) {
        this.config = $.extend({}, this.defaultOption, config);
        this.init();
    };
    swipleftRight.prototype = {
        defaultOption: {
            touchEle: $(".slide"),
            startX: 0,
            startY: 0,
            endX: 0,
            endY: 0,
            distanceX: 0,
            distanceY: 0,
            lenArr: [],
            isMoveLen: 0,
            isMoved: false
        },
        init: function () {
            this.onEvent();
        },
        onEvent: function () {
            var _this = this;
            var $touch = _this.config.touchEle;
            $touch.on("touchstart", function (e) {
                _this.tStart(e);
            });
            $touch.on("touchmove", function (e) {
                _this.tMove(e);
            });
            $touch.on("touchend", function (e) {
                _this.tEnd(e);
            });
        },
        tStart: function (e) {
            var _this = this;
            var param = _this.config;
            param.startY = _this.getPos(e).posY;
            param.startX = _this.getPos(e).posX;
            param.isMoved = false;
        },
        tMove: function (e) {
            var _this = this;
            var param = _this.config;
            var $table = param.touchEle.find(".activeTop");
            if ($table.width() < 241) {
                return false;
            }
            param.endX = _this.getPos(e).posX;
            param.endY = _this.getPos(e).posY;

            param.distanceY = param.endY - param.startY;
            param.distanceX = param.endX - param.startX;

            if ((Math.abs(param.distanceY) - Math.abs(param.distanceX)) < 5) {
                param.isMoved = true;
                _this.moveTo(param.distanceX);
                e.preventDefault();
            }
        },
        tEnd: function (e) {
            var _this = this;
            var param = _this.config;
            if (param.isMoved) {
                param.isMoveLen = param.lenArr[param.lenArr.length - 1];
                param.lenArr.length = 0;
            }
        },
        moveTo: function (dis) {
            var _this = this;
            var param = _this.config;
            if (param.isMoveLen != 0) {
                dis = dis + param.isMoveLen;
            }
            var $table = param.touchEle.find(".activeTop");
            if ($table.width() < 241) {
                return false;
            }
            if (dis < 0 && dis <= -($table.width() - 241)) {
                dis = -($table.width() - 241);
            } else if (dis >= 0) {
                dis = 0;
            }
            if($(window).width()<560){ //240+320
            	var min=$(window).width()-560;
                if(dis<min-5){
                	dis=min-5;
                }
            }else{
            	  dis = 0;
            }
            
            param.lenArr.push(dis);
            param.touchEle.css('-webkit-transform', 'translate3d(' + dis + 'px,0,0)');
        },
        getPos: function (e) {
            return {
                posX: e.changedTouches[0].clientX,
                posY: e.changedTouches[0].clientY
            };
        }
    };
    
    $(function () {
        //屏幕左右滑动
        new swipleftRight();
        $('#loadData').on('click', '.w-nav-mini-btn', function () {
            $(this).siblings('.w-nav-mini-pop').toggleClass('w-fn-hide');
            return false;
        })
            .on('click', '.first-w-nav-back', function () {
                $('#loadSpec').hide();
                $('.wrapper').show();
                return false;
            })
            .on('click', '.second-w-nav-back', function () {
                $('#loadSeries').hide();
                $('#loadSpec').show();
                return false;
            });

    });
    //
})(Zepto)
