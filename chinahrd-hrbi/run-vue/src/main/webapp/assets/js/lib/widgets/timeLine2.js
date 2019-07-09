/**
 * Created by wqcai on 16/2/23.
 */
define(['template', 'moment', 'underscore', 'jquery-mCustomScrollBar'], function (tpl, moment) {
    var webRoot = G_WEB_ROOT;
    var Urls = {
        findPackItemUrl: webRoot + '/memo/findPackItem',            //查询锦囊
        findMemoUrl: webRoot + '/memo/findMemo.do',				    //查看备忘录信息
        addMemoUrl: webRoot + '/memo/addMemo.do',			        //添加备忘录信息
        deleteMemoUrl: webRoot + '/memo/deleteMemo',                //删除备忘录
        updateMemoStatusUrl: webRoot + '/memo/updateMemoStatus',     //修改已读状态
        defaultImage: webRoot + '/assets/img/base/u-default.png'    //员工默认头像
    };
    var defaults = {
        title: '管理建议与备忘',
        quotaId: null,
        organId: null,
        onSubmit: null
    };
    moment.locale('zh-cn');

    // moment.locale('chn', {
    //     relativeTime: {
    //         future: "%s内",
    //         past: "%s前",
    //         s: "1秒",
    //         ss: "%d秒",
    //         m: "1分钟",
    //         mm: "%d分钟",
    //         h: "1小时",
    //         hh: "%d小时",
    //         d: "1天",
    //         dd: "%d天",
    //         M: "1个月",
    //         MM: "%d个月",
    //         y: "1年",
    //         yy: "%d年"
    //     }
    // });

    var template = '<div class="index-common-title">'
        + ' <div class="memoBtn">添加备忘</div>'
        + ' <div class="index-common-title-left"></div>'
        + ' <div class="index-common-title-text"><%=title%>'
        + ' <% if(unreadNum != 0){ %>'
        + ' <span class="memo">(<span class="memoValue"><%=unreadNum%></span>)</span>'
        + ' <% } %>'
        + ' </div>'
        + '</div>'
        + '<div class="body-div memo-body-div" id="memo-body-div">'
        + ' <div class="memo-big-list">'
        + '     <% if(!_.isEmpty(data)){%>'
        + '     <div class="memo-list">'
        + '         <img class="ct-circle-header" src="<%=data[0].imgPath%>" onerror="this.src=\'' + Urls.defaultImage + '\'">'
        + '         <div class="memo-list-content">'
        + '             <div class="memo-list-name-line">'
        + '                 <div class="memo-list-name-value"><%=data[0].userNameCh%></div>'
        + '                 <div class="memo-list-name-time"><%=data[0].createTime%></div>'
        + '             </div>'
        + '             <% var readStype = data[0].isRead ? "memo-list-value" : "memo-list-value-unread"; %>'
        + '             <div class="<%=readStype%>" data-id="<%=data[0].id %>">'
        + '                 <div class="memo-list-value-bubble"></div>'
        + '                 <div class="memo-list-value-content"><%=data[0].content%></div>'
        + '                 <% if(data[0].hasDelete){ %>'
        + '                     <div class="memo-list-value-del"></div>'
        + '                 <% } %>'
        + '             </div>'
        + '         </div>'
        + '         <% } %>'
        + '     </div>'
        + '     <div id="memo-big-list-more" class="memo-big-list-more">'
        + '         <% for(var i = 1; i < dataLength && i < 5;i++){ %>'
        + '         <div class="memo-list">'
        + '             <img class="ct-circle-header" src="<%=data[i].imgPath%>" onerror="this.src=\'' + Urls.defaultImage + '\'">'
        + '             <div class="memo-list-content">'
        + '                 <div class="memo-list-name-line">'
        + '                     <div class="memo-list-name-value"><%=data[i].userNameCh%></div>'
        + '                     <div class="memo-list-name-time"><%=data[i].createTime%></div>'
        + '                 </div>'
        + '                 <% var readStype = data[i].isRead ? "memo-list-value" : "memo-list-value-unread"; %>'
        + '                 <div class="<%=readStype%>" data-id="<%=data[i].id %>">'
        + '                     <div class="memo-list-value-bubble"></div>'
        + '                     <div class="memo-list-value-content"><%=data[i].content%></div>'
        + '                     <% if(data[i].hasDelete){ %>'
        + '                         <div class="memo-list-value-del"></div>'
        + '                     <% } %>'
        + '                 </div>'
        + '             </div>'
        + '         </div>'
        + '         <% } %>'
        + '         <% if(dataLength > 5){ %>'
        + '             <div class="memo-list-query">'
        + '                 <div class="memo-list-query-div">'
        + '                     <span class="memo-list-query-div-text">查看更多>></span>'
        + '                 </div>'
        + '             </div>'
        + '         <% } %>'
        + '     </div>'
        + ' </div>'
        + '</div>'
        + '<!--备忘录 弹出框 begin-->'
        + '<div class="modal fade popup-modal memo-window" aria-hidden="true">'
        + '<div class="modal-dialog">'
        + '<div class="modal-content">'
        + ' <div class="modal-header">'
        + '     <div class="modal-header-text" id="personDetailModalLabel">备忘录</div>'
        + '     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'
        + ' </div>'
        + ' <div class="modal-body">'
        + '     <div class="memo-list-window ct-mCustomScrollBar mCustomScrollbar" data-mcs-theme="minimal-dark">'
        + '     <% for(var i = 0; i < dataLength;i++){ %>'
        + '     <div class="memo-list">'
        + '         <img class="ct-circle-header" src="<%=data[i].imgPath%>" onerror="this.src=\'' + Urls.defaultImage + '\'">'
        + '         <div class="memo-list-content">'
        + '             <div class="memo-list-name-line">'
        + '                 <div class="memo-list-name-value"><%=data[i].userNameCh%></div>'
        + '                 <div class="memo-list-name-time"><%=data[i].createTime%></div>'
        + '             </div>'
        + '             <% var readStype = data[i].isRead ? "memo-list-value" : "memo-list-value-unread"; %>'
        + '             <div class="<%=readStype%>" data-id="<%=data[i].id %>">'
        + '                 <div class="memo-list-value-bubble"></div>'
        + '                 <div class="memo-list-value-content"><%=data[i].content%></div>'
        + '                 <% if(data[i].hasDelete){ %>'
        + '                 <div class="memo-list-value-del"></div>'
        + '                 <% } %>'
        + '             </div>'
        + '         </div>'
        + '     </div>'
        + '     <% } %>'
        + '     </div>'
        + ' </div>'
        + ' <div class="modal-footer">'
        + '     <input class="memoInput" name="memoInput" type="text" maxlength="2000"  placeholder="请输入备忘内容">'
        + '     <div class="drag-btn initDragBtn memoBtn memoBtnTwo">添加备忘</div>'
        + ' </div>'
        + '</div>'
        + '</div>'
        + '</div>'
        + '<!--备忘录 弹出框 end-->';


    $.fn.extend({
        timeLine: function (options) {
            var _this = $(this);
            var opt = $.extend(defaults, options);
            //查询备忘信息
            $.post(Urls.findMemoUrl, {quotaId: opt.quotaId, organizationId: opt.organId}, function (rs) {
                extendDateToHtml(_this, rs, opt);
            });
        }
    });

    function extendDateToHtml(_e, datas, options) {
        var unreadNum = 0;
        var len = datas.length;
        for (var i = 0; i < len; i++) {
            if (!datas[i].isRead) {
                unreadNum++;
            }
        }
        options.unreadNum = unreadNum;
        options.data = datas;
        options.dataLength = len;
        tpl.LEFT_DELIMITER = '<%';
        tpl.RIGHT_DELIMITER = '%>';

        var html = tpl(template, options);
        _e.addClass('u-timeline-memo').html(html);

        $(".ct-mCustomScrollBar").mCustomScrollbar({});     //mCustomScrollbar

        //鼠标移动事件-显示更多备忘
        $("#memo-body-div").mouseenter(function () {
            $("#memo-big-list-more").show();
        });
        $("#memo-body-div").mouseleave(function () {
            $("#memo-big-list-more").hide();
        });

        //未读状态事件-修改为已读
        _e.find('.memo-list .memo-list-value-unread').bind('mouseenter', function () {
            unreadMouseEnterEvent(this);
        });

        //显示更多窗口
        _e.find('.memoBtn,.memo-list-query-div-text').click(function () {
            if ($(this).hasClass('memoBtn')) {
                _e.find('.memo-window input[name="memoInput"]')[0].focus();
            }
            $(".memo-window").modal('show');
        });
        //隐藏窗口
        _e.find('.memo-window .closeIcon').click(function () {
            $(".memo-window").modal('hide');
        });

        //添加备忘事件
        _e.find('.memo-window .memoBtnTwo').click(function () {
            var text = _e.find('.memo-window input[name="memoInput"]').val();
            if (!_.isEmpty(text)) {
                var param = {
                    'quotaId': options.quotaId,
                    'organizationId': options.organId,
                    'content': text
                }
                $.post(Urls.addMemoUrl, param, function (result) {
                    if (result.type) {
                        extendDateToHtml(_e, result.t, options);
                        // $(".memo-window").modal('show');
                    }
                });
                //调用外部定义方法
                if ($.isFunction(options.onSubmit)) {
                    options.onSubmit(text);
                }
            }
        });
        //移除事件-移除自己添加的备忘
        _e.find('.memo-list .memo-list-value-del').bind('click', function () {
            var _d = $(this);
            var _p = _d.parent();
            var memoId = _p.attr('data-id');
            if (memoId != '') {
                $.post(Urls.deleteMemoUrl, {
                    'quotaId': options.quotaId,
                    'organizationId': options.organId,
                    'memoId': memoId
                }, function (rs) {
                    if (rs.type) {
                        _d.parents('.memo-list').remove();
                    }
                });
            }
        });
    }

    //未读信息鼠标触发功能
    function unreadMouseEnterEvent(obj) {
        var _unread = $(obj);
        var memoId = _unread.attr('data-id');
        var _class = _unread.attr('class');
        if (memoId != '' && _class == "memo-list-value-unread") {
            $.post(Urls.updateMemoStatusUrl, {'memoId': memoId}, function (rs) {
                if (rs.type) {
                    _unread.removeClass('memo-list-value-unread').addClass('memo-list-value');
                    var _small = $('.memoValue');
                    var num = _small.text() > 0 ? _small.text() - 1 : 0;
                    _small.text(num);
                }
            });
        }
    }
});
