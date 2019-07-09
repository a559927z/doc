/**
 * Created by wqcai on 15/5/12.
 */
define(['template', 'moment'], function (tpl, moment) {
    if (!("zrw" in window)) {
        window.zrw = {}
    }

    var webRoot = G_WEB_ROOT;
    var Urls = {
        findPackItemUrl: webRoot + '/memo/findPackItem',            //查询锦囊
        deleteMemoUrl: webRoot + '/memo/deleteMemo',                //删除备忘录
        updateMemoStatusUrl: webRoot + '/memo/updateMemoStatus'     //修改已读状态
    }
    var defaults = {
        title: null,
        titleSuffix: '条',
        data: null,
        onSubmit: null
    };

    moment.locale('chn', {
        relativeTime: {
            future: "%s内",
            past: "%s前",
            s: "1秒",
            ss: "%d秒",
            m: "1分钟",
            mm: "%d分钟",
            h: "1小时",
            hh: "%d小时",
            d: "1天",
            dd: "%d天",
            M: "1个月",
            MM: "%d个月",
            y: "1年",
            yy: "%d年"
        }
    });

    window.zrw.checkContentLong = function(txt){
        var tLength = txt.length;
        var num = 0;
        var first = '',second = '',third='';
        for(var i = 0; i < tLength; i++){
            if(txt.charCodeAt(i) == 10){
                if(num == 0){ first = txt.substring(0,i); }
                if(num == 1){
                    second = txt.substring(first.length,i);
                    var thirdLength = first.length+second.length+1;
                    if(thirdLength < tLength){
                        third = txt.substring(thirdLength, tLength);
                    }
                }
                num++;
            }
        }
        return (num > 2) ||(third.length > 0) ||(num >= 0 && tLength > 42) || (num == 1 && first.length > 21 && second.length > 0) || (num == 1 && first.length > 0 && second.length > 21);
    }

    var template = '<div class="u-timeline">'
        + '    <% var dataLength = data.length; %>'
        + '    <div class="u-timeline-title"><label><%=title%></label><span class="u-timeline-title-small">（<span class="u-timeline-title-small2"><%=unreadNum%></span><%=titleSuffix%>）</span><button type="button" id="addMemoButton" data-toggle="modal"  class="btn btn-success">添加备忘</button></div>'
        + '    <div class="u-timeline-main" id="timeLineMain">'
        + '        <% for(var i = 0; i < dataLength && i < 5;i++){ %>'
        + '            <% var readStype = data[i].isRead ? "u-timeline-read" : "u-timeline-unread"; %>'
        + '            <div class="<%=readStype%>" data-id="<%=data[i].id %>" >'
        + '                <div class="u-timeline-ml"><%=data[i].fromNowTime%></div>'
        + '                <div class="u-timeline-mr">'
        + '                    <span><%=data[i].userName%> ( <%=data[i].userNameCh%> )</span>'
        + '                    <% if(data[i].hasDelete){ %>'
        + '                    <a href="javascript:void(0)"  class="u-timeline-delete-link">删除</a>'
        + '                    <% } %>'
        + '                    <% if(zrw.checkContentLong(data[i].content)){ %>'
        + '                    <a href="javascript:void(0)" data-status="0" class="u-timeline-open-link">展开</a>'
        + '                    <% } %>'
        + '                    <textarea readonly><%=data[i].content%></textarea>'
        + '                </div>'
        + '            </div>'
        + '        <% } %>'
        + '    </div>'
        + '    <% var hideStyle = dataLength > 0 ? "" : "hide"; %>'
        + '    <div class="u-timeline-bottom <%=hideStyle%>">'
        + '        <% if(dataLength > 5) {%><a href="javascript:void(0)" id="timeLineMore" class="timeLineMore">更多&nbsp;&nbsp;↓</a><% } %>'
        + '    </div>'
        + '</div>'
        + '<div id="memoModal" class="modal fade">'
        + '    <div class="modal-dialog">'
        + '        <div class="modal-content">'
        + '            <div class="modal-header">'
        + '                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
        + '                <h4 class="modal-title">添加备忘录</h4>'
        + '            </div>'
        + '            <div class="modal-body">'
        + '                <div class="form-group"><textarea name="memoText" class="form-control" rows="7"></textarea></div>'
        + '                <button type="button" id="addPackButton" class="btn btn-default">插入锦囊</button>'
        + '            </div>'
        + '            <div class="modal-footer">'
        + '                <button type="button" name="memoSubmitButton" class="btn btn-success">确定</button>'
        + '                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
        + '            </div>'
        + '        </div><!-- /.modal-content -->'
        + '    </div><!-- /.modal-dialog -->'
        + '</div>'
        + '<div id="addPackModal" class="modal fade">'
        + '    <div class="modal-dialog">'
        + '        <div class="modal-content">'
        + '            <div class="modal-header">'
        + '                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
        + '                <h4 class="modal-title">管理锦囊</h4>'
        + '            </div>'
        + '            <div class="modal-body">'
        + '                <ul class="nav nav-tabs nav-justified">'
        + '                    <% for(var i = 0; i < items.length; i++){ if(i == 0){ %> '
        + '                    <li class="active" role="presentation"><a href="#tab_<%=items[i].id %>"><%=items[i].name %></a></li>'
        + '                     <% }else{ %>'
        + '                    <li role="presentation"><a href="#tab_<%=items[i].id %>"><%=items[i].name %></a></li>'
        + '                    <% } } %>'
        + '                </ul>'
        + '                <div class="tab-content">'
        + '                    <% for(var i = 0; i < items.length; i++){ %> '
        + '                     <% if(i == 0) { %>'
        + '                    <div class="tab-pane active" id="tab_<%=items[i].id %>">'
        + '                     <% }else{ %>'
        + '                    <div class="tab-pane" id="tab_<%=items[i].id %>">'
        + '                     <% } %>'
        + '                        <% for(var j = 0; j < items[i].childs.length; j++){ %>'
        + '                          <% var child = items[i].childs[j]; %> '
        + '                          <div class="checkbox"><label><input type="checkbox" value="<%=child.name %>"><%=j+1 %>、<%=child.name %></label></div>'
        + '                        <% } %>'
        + '                    </div>'
        + '                    <% } %>'
        + '                </div>'
        + '            </div>'
        + '            <div class="modal-footer">'
        + '                <button type="button" id="addPackSubmitButton" class="btn btn-success">确定</button>'
        + '                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
        + '            </div>'
        + '        </div><!-- /.modal-content -->'
        + '    </div><!-- /.modal-dialog -->'
        + '</div>';

    $.fn.extend({
        timeLine: function (options) {
            var defaultHeight = 135;
            var _this = $(this);
            var opt = $.extend(defaults, options);
            var datas = opt.data;
            var unreadNum = 0;
            var nowDate= new Date(); 
            var threeM=1000*60*3;
            for (var i = 0; i < datas.length; i++) {
                if(!datas[i].isRead){
                    unreadNum++;
                }
                var createTime= new Date(datas[i].createTime); 
                if((nowDate.getTime()-createTime.getTime())<threeM){
                	opt.data[i].fromNowTime ="刚刚";
                }else
                opt.data[i].fromNowTime = moment(datas[i].createTime).fromNow();
            }
            opt.unreadNum = unreadNum;
            tpl.LEFT_DELIMITER = '<%';
            tpl.RIGHT_DELIMITER = '%>';

            $.post(Urls.findPackItemUrl, function (rs) {
                if (rs != null && !$.isEmptyObject(rs)) {
                    opt.items = rs;
                }
                var html = tpl(template, opt);
                _this.html(html);

                var _child = _this.children('.u-timeline');

                _child.bind('mouseenter', function () {
                    var _timeline = $(this);
                    var height = 20;
                    $.each(_timeline.children(), function (i, obj) {
                        height += $(obj).height();
                    });
                    if (height > defaultHeight) {
                        _timeline.stop().animate({
                            height: height + 'px',
                            overflow: null
                        });
                    }
                    //中间部分滚动事件
                    if(height >= 550){
                        _timeline.find('.u-timeline-main').css('overflow-y','auto');
                    }
                });
                _child.bind('mouseleave', function () {
                    var _timeline = $(this);
                    _timeline.stop().animate({
                        height: defaultHeight + 'px',
                        overflow: 'hidden'
                    });
                    //中间部分滚动事件
                    var _main = _timeline.find('.u-timeline-main');
                    if(_main.scrollTop() > 5){
                        _main.animate({scrollTop:0},100);
                    }
                    _main.css('overflow-y','hidden');
                });

                $('#timeLineMain .u-timeline-unread').bind('mouseenter', function () {
                    unreadMouseEnterEvent(this);
                });

                $('#timeLineMain .u-timeline-open-link').bind('click', function () {
                    contractionLink(this);
                });

                $('#timeLineMain .u-timeline-delete-link').bind('click', function () {
                    clickDeleteEvent(this);
                });

                $('#timeLineMore').click(function () {
                    clickTimeLineMoreEvent(opt.data);
                });

                $('#addMemoButton').click(function () {
                    $('#memoModal').modal('show');
                });

                $('#memoModal button[name="memoSubmitButton"]').click(function () {
                    var text = $('#memoModal textarea[name="memoText"]').val();

                    if (!$.isEmptyObject(text) && $.isFunction(opt.onSubmit)) {
                        opt.onSubmit(text);
                    }
                    $('#memoModal').modal('hide');
                });

                $('#addPackButton').click(function () {
                    $('.nav-tabs a:first').tab('show');
                    $('#addPackModal input[type="checkbox"]').prop('checked', false);
                    $('#addPackModal').modal('show');
                });

                $('.nav-tabs a').click(function (e) {
                    e.preventDefault();     //阻止a链接的跳转行为
                    $(this).tab('show');
                });

                $('#addPackSubmitButton').click(function () {
                    var _checkbox = $('.tab-pane :checkbox:checked');

                    var _textarea = $('#memoModal textarea[name="memoText"]');
                    $.each(_checkbox, function (i, obj) {
                        var _obj = $(obj);
                        var checkedTxt = (i + 1) + '、' + _obj.val();
                        if (i != 0 || !$.isEmptyObject(_textarea.val())) {    //插入之前先换行
                            _textarea.val(_textarea.val() + ' \n ');
                        }
                        _textarea.val(_textarea.val() + checkedTxt);
                    });
                    $('#addPackModal').modal('hide');
                });

            });
        }
    });


    function clickTimeLineMoreEvent(data) {
        var len = data.length;
        var _main = $('#timeLineMain');
        var _childLen = _main.children().length;
        var _child = $('.u-timeline');
        var html = '';
        var idx = _childLen;
        for (var i = _childLen; i < len && i < (_childLen + 5); i++) {
            var readStype = data[i].isRead ? 'u-timeline-read' : 'u-timeline-unread';
            html += '<div class="' + readStype + '" data-id="' + data[i].id + '">';
            html += '<div class="u-timeline-ml">' + data[i].fromNowTime + '</div>';
            html += '<div class="u-timeline-mr"><span>' + data[i].userName + ' ( ' + data[i].userNameCh + ' )</span>'
            if (data[i].hasDelete) {
                html += '<a href="javascript:void(0)" class="u-timeline-delete-link">删除</a>';
            }
            if (zrw.checkContentLong(data[i].content)) {
                html += '<a href="javascript:void(0)" data-status="0" class="u-timeline-open-link">展开</a>'
            }
            html += '<textarea readonly>' + data[i].content + '</textarea></div>';
            html += '</div>';

            idx = i;
        }
        _main.append(html);
        if(len > 7){  //超长显示滚动条
            _main.css('overflow-y','scroll');
        }

        _main.find('.u-timeline-unread').unbind('mouseenter').bind('mouseenter', function () {
            unreadMouseEnterEvent(this);
        });
        _main.find('.u-timeline-open-link').unbind('click').bind('click', function () {
            contractionLink(this);
        });
        _main.find('.u-timeline-delete-link').unbind('click').bind('click', function () {
            clickDeleteEvent(this);
        });
        if (idx == len - 1) {
            _child.find('.u-timeline-bottom a').remove();
        }
        var height = 22;
        $.each(_child.children(), function (i, obj) {
            height += $(obj).height();
        });
        if (height > 135) {
            _child.css({
                height: height + 'px',
                overflow: null
            });
        }
    }
    //未读信息鼠标触发功能
    function unreadMouseEnterEvent(obj){
        var _unread = $(obj);
        var memoId = _unread.attr('data-id');
        var _class=_unread.attr('class');
        if(memoId != ''&&_class=="u-timeline-unread"){
            $.post(Urls.updateMemoStatusUrl, {'memoId': memoId}, function(rs){
                if(rs.type){
                    _unread.removeClass('u-timeline-unread').addClass('u-timeline-read');
                    var _small = $('.u-timeline-title-small2');
                    var num = _small.text() > 0 ? _small.text() - 1 : 0;
                    _small.text(num);
                }
            });
        }
    }

    //删除功能
    function clickDeleteEvent(obj){
        var _deleteLink = $(obj);
        var _parents = _deleteLink.parent().parent();
        var memoId = _parents.attr('data-id');
        if(memoId != ''){
            $.post(Urls.deleteMemoUrl, {'memoId': memoId}, function(rs){
                if(rs.type){
                    if(_parents.siblings().length == 0){
                        $('.u-timeline-bottom').addClass('hide');
                    }
                    _parents.remove();
                }
            });
        }
    }

    //收缩操作
    function contractionLink(obj) {
        var _linkObj = $(obj);
        var _next = _linkObj.next();
        var _parents = _linkObj.parent().parent();
        var status = _linkObj.attr('data-status');
        var _child = $('.u-timeline');
        var suffix = 'px';
        if (status == 0) {
            var dheight = _next[0].scrollHeight + 8;
            _next.css('height', dheight + suffix);
            var diff = dheight - 44;
            _parents.css('height', (diff + 83) + suffix);

            _child.css('height',(_child.height() + diff) + suffix);
            _linkObj.text('收起');
            _linkObj.attr('data-status', 1);
        } else {
            var diff = _next.height() - 44;
            _next.css('height', '44px');
            _parents.css('height', '83px');
            _child.css('height',(_child.height() - diff) + suffix);
            _linkObj.text('展开');
            _linkObj.attr('data-status', 0);
        }
    }
});
