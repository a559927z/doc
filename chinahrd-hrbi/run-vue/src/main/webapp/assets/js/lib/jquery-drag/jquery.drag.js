/*
 * drag 0.3
 * Copyright (c) 2016 yuming
 * Date: 2016-01-13
 * 拖拽排序功能
 */
(function ($) {

    var defaults = {
        data: [],
        url: ""
    }

    var common = {
        sortDiv: function (data, arg) {//div排序
            $(data).each(function (i, e) {
                $("div[name='" + data[i].name + "']").attr("showIndex", data[i].showIndex);
            });
            if (defaults.url == "index") {
                var modelList = ["预警", "首页"];
            }
            $(modelList).each(function (i, e) {
                var sort = $("div[name='" + e + "']").find('.DRAG-DIV').toArray().sort(function (a, b) {
                    return parseInt($(a).attr("showIndex")) - parseInt($(b).attr("showIndex"));
                });
                if (arg) {
                    $(sort).appendTo($("div[name='"+e+"']")).hide().show(50);
                }else{
                    $(sort).appendTo($("div[name='"+e+"']")).show();
                }
            });
        },
        sortUl: function (data) {//li排序
            $(data).each(function (i, e) {
                $("li[name='" + data[i].name + "']").attr("showIndex", data[i].showIndex);
            });
            if (defaults.url == "index") {
                var modelList = ["预警指标顺序", "指标/功能顺序"];
            }
            $(modelList).each(function (i, e) {
                var sort = $("ul[name='" + e + "']").find('.DRAG-LI').toArray().sort(function (a, b) {
                    return parseInt($(a).attr("showIndex")) - parseInt($(b).attr("showIndex"));
                });
                $(sort).appendTo($("ul[name='" + e + "']"));
            });

        },
        initUl: function () {//实例化li 拖拽
            if (defaults.url == "index") {
                var htmlStr = '';
                htmlStr = '<div id="drag-body-fa" class="drag-content"><div id="drag-title" class="drag-title"><div class="drag-title-blue"></div><div class="drag-title-text">首页设置</div></div><div class="drag-middle"><div id="drag-tips" class="drag-tips"><div class="drag-tips-close-text">提示：左右拖拽可以排序</div></div><div id="ul-str" class="ul-str"><div class="lineDiv"><div class="dragUlTitle">预警指标顺序：</div><ul id="dragUl1" name="预警指标顺序" class="dragUl connectedSortable"><li name="离职风险" showIndex="1" class="DRAG-LI"><div class="dragLiImg lzfx-icon"></div><div class="dragLiBottom">离职风险</div></li><li name="高绩效未晋升" showIndex="2" class="DRAG-LI"><div class="dragLiImg gzxwjs-icon"></div><div class="dragLiBottom">高绩效未晋升</div></li><li name="低绩效未调整" showIndex="3" class="DRAG-LI"><div class="dragLiImg djxwtz-icon"></div><div class="dragLiBottom">低绩效未调整</div></li><li name="离职率" showIndex="4" class="DRAG-LI"><div class="dragLiImg gzshphqj-icon"></div><div class="dragLiBottom">离职率</div></li></ul><div class="dragUlTitle">指标/功能顺序：</div><ul id="dragUl2" name="指标/功能顺序" class="dragUl connectedSortable"><li name="当季人才损益" showIndex="1" class="DRAG-LI"><div class="dragLiImg djrcsy-icon"></div><div class="dragLiBottom">当季人才损益</div></li><li name="绩效目标" showIndex="2" class="DRAG-LI"><div class="dragLiImg jxmb-icon"></div><div class="dragLiBottom">绩效目标</div></li><li name="团队画像" showIndex="3" class="DRAG-LI"><div class="dragLiImg tdhx-icon"></div><div class="dragLiBottom">团队画像</div></li><li name="团队提醒" showIndex="4" class="DRAG-LI"><div class="dragLiImg tdtx-icon"></div><div class="dragLiBottom">团队提醒</div></li><li name="员工管理" showIndex="5" class="DRAG-LI"><div class="dragLiImg yggl-icon"></div><div class="dragLiBottom">员工管理</div></li><li name="下属组织信息" showIndex="6" class="DRAG-LI"><div class="dragLiImg xszzxx-icon"></div><div class="dragLiBottom">下属组织信息</div></li></ul></div></div></div><div id="drag-bottom" class="drag-bottom"></div></div>';
                $("#drag-body").append(htmlStr);
                $('#dragUl1').children(':not(:last)').append('<i class="icon-minus-sign"></i>');
                $('#dragUl1').children(':last').append('<i class="icon-plus-sign"></i>');
                $('#dragUl2').children(':not(:last)').append('<i class="icon-minus-sign"></i>');
                $('#dragUl2').children(':last').append('<i class="icon-plus-sign"></i>');
                $("#drag-bottom").append('<div id="initDragBtn" class="drag-btn initDragBtn">确定</div>').find('#initDragBtn').click(function () {
                    common.determine();
                });
                $("#drag-bottom").append('<div id="cancelDragBtn" class="drag-btn cancelDragBtnEvent cancelDragBtn">取消</div>').find('#cancelDragBtn').click(function () {
                    common.cancel();
                });
                $("#drag-title").append('<div id="cancelDragBtnEvent"  class="cancelDragBtnEvent closeIcon"></div>').find('#cancelDragBtnEvent').click(function () {
                    common.cancel();
                });
                $("#drag-tips").append('<div id="drag-tips-close" class="drag-tips-close"></div>').find('#drag-tips-close').click(function () {
                    common.closeTips();
                });
                var dragUlIdArray = [];
                $("#ul-str .dragUl").each(function (i, e) {
                    dragUlIdArray.push("#" + $(e).attr('id'));
                });
                $('#dragUl1 i,#dragUl2 i').unbind('click').bind('click',function(){
                    var _this = $(this);
                    if(_this.hasClass('icon-minus-sign')){
                        _this.removeClass('icon-minus-sign').addClass('icon-plus-sign');
                    }else{
                        _this.removeClass('icon-plus-sign').addClass('icon-minus-sign');
                    }
                });
                $(dragUlIdArray.join(",")).sortable({revert: true});
            }
        },
        getData: function () {//获取拖拽后的数据
            var data = [];
            $("#ul-str .dragUl").each(function (i, e) {
                $(e).find("li").each(function (index, el) {
                    data.push({"name": $(e).find("li").eq(index).attr("name"), "showIndex": (index + 1).toString()});
                });
            });
            return data;
        },
        determine: function () {//确定设置
            var data = common.getData();
            for (var i = 0; i < defaults.data.length; i++) {
                for (var index = 0; index < data.length; index++) {
                    if (defaults.data[i].name == data[index].name) {
                        defaults.data[i].showIndex = data[index].showIndex;
                    }
                }
            }
            common.sortDiv(data, true);
            $("#ct-drag").hide();
            $("#shade").hide();

            $.post(G_WEB_ROOT + '/manageHome/editHomeConfig.do', {'homeConfigObj': JSON.stringify(defaults.data)}, function (rs) {
                if (!rs) {
                    console.log('后台存储有问题,请联系管理员！');
                }
            });
        },
        cancel: function () {//取消设置
            $("#ct-drag").hide();
            $("#shade").hide();
        },
        closeTips: function () {//关闭提示
            $("#drag-tips").hide();
        }
    }

    /*
     显示Drag界面
     */
    $("#showDragBtn").click(function () {
        if ($("#drag-body-fa").length == 0) {
            common.initUl();
        }
        common.sortUl(defaults.data);
        $("#ct-drag").show;
        $("#ct-drag").css("display", "table");
        $("#shade").show();
        $("#drag-tips").show();
    });


    $.fn.drag = function (options) {
        var options = $.extend(defaults, options);
        this.each(function (i, e) {
            common.sortDiv(options.data, false);//init
        });
    };
})(jQuery);