/**
 * Created by wqcai on 15/5/14.
 */
define(['jquery', 'template', 'underscore'],function(jQuery,tpl){

    var colorArray = [
        ['#BBD954','#FFD966','#FD7262'],
        ['#FD7262','#FFD966','#BBD954']
    ];

    var defaults = {
        title: null,
        width: 265,
        split: 15,
        data: [0.4, 0.7, 1],
        colors: 0,
        onClick: null
    };

    var baseTpl = '<div class="u-vCursor" style="width:<%=width%>px;">'
                + '<div class="u-vCursor-title"><span><%=title%></span><span class="u-vCursor-deploy"></span>'
                + '<div id="vCursorPopup" class="u-vCursor-popup" style="left:14px; z-index: 999;">'
                + '<div class="u-vCursor-popup-title">基本配置</div>'
                + '<div class="u-vCursor-popup-main">'
                + '<div><label>预警设置：</label>&nbsp;&nbsp;&nbsp;0&nbsp; - （<input type="text" name="txt1" value="<%=data[0]%>" />）</div>'
                + '<div><label>&nbsp;</label><input type="text" name="txt2" readonly="readonly" value="<%=data[0]%>" /> - （<input type="text" name="txt3" value="<%=data[1]%>" />）</div>'
                + '<div><label>&nbsp;</label><input type="text" name="txt4" readonly="readonly" value="<%=data[1]%>" /> - （<input type="text" name="txt5" value="<%=data[2]%>" />）</div>'
                + '<div><label>预警标注：</label>'
                + '<div class="radio-inline"><label><input type="radio" name="radio" value="0" checked/>绿黄红</label></div>'
                + '<div class="radio-inline"><label><input type="radio" name="radio" value="1" />红黄绿</label></div></div>'
                + '<div><label>预警刻度：</label>（<input type="text" name="txt6" value="<%=split%>" />）</div>'
                + '</div>'
                + '<div class="u-vCursor-popup-bottom"><button class="btn btn-cancel">取消</button><button class="btn btn-info">确定</button></div>'
                + '</div>'
                + '</div>'
                + '<div class="u-vCursor-main">'
                + '<div class="u-vcursor-scale">'
                + '<% for(var i = 0;i <= parseInt(split); i++){ %>'
                + '<% if(i == 0 ) {%>'
                + '<span style="width: 2px; height: 12px;"></span>'
                + '<% }else {%>'
                + '<span style="width:<%=(width-40-2)/split%>px;height:<%=(i%5==0 || i==split) ? 10 : 6%>px;"></span>'
                + '<% } %>'
                + '<% } %>'
                + '</div>'
                + '<div class="u-vCursor-line" ><span></span><span></span><span></span></div>'
                + '<div class="u-vCursor-rulers" ><span class="u-vCursor-move"></span></div>'
                + '<span class="u-vCursor-vals" ><%=Math.round(value*10000)/100+"%"%></span>'
                + '</div>'
                + '</div>';

    $.fn.extend({
        vernierCursor: function(options){
            var opt = $.extend(defaults, options);
            _create(this,opt);
        }
    });

    function _create(obj, options){
        var _this = $(obj);
        var width = options.width;
        var rulersWidth = width - 40;
        var defaultWidth = _.last(options.data);
        var defaultValue = options.value;
        var defaultColor = _.first(options.colors);
        var left = Math.round((defaultValue/defaultWidth) * rulersWidth * 10)/10;

        tpl.LEFT_DELIMITER = '<%';
        tpl.RIGHT_DELIMITER = '%>';
        var html = tpl(baseTpl, options);

        _this.html(html);
        _this.find('.u-vCursor-popup-main').find(':radio[value=' + options.colors + ']').prop('checked',true);

        var _cursorLine = _this.find('.u-vCursor-line');

        //计算刻度条的各个阶段长度
        $.each(_cursorLine.children(),function(i,obj){
            var firstWidth = i == 0 ? 0 : options.data[i-1];
            var lastWidth = options.data[i];

            var lineWidth = ((lastWidth - firstWidth)/defaultWidth) * rulersWidth;
            lineWidth = lineWidth < rulersWidth ? lineWidth : rulersWidth;
            if(defaultValue > firstWidth && defaultValue <= lastWidth){
                defaultColor = colorArray[options.colors][i];
            }
            $(obj).css({
                backgroundColor : colorArray[options.colors][i],
                width: lineWidth
            });
        });
        //计算刻度的偏离值
        var _cursorVals = _this.find('.u-vCursor-vals');
        var valsHalf = _cursorVals.width()/2 + 30;
        var marginLeft = width - valsHalf < left ? width - valsHalf : left;

        marginLeft = marginLeft < 15 ? 15 : marginLeft;
        _cursorVals.css({
            color: defaultColor,
            marginLeft: marginLeft
        });
        //数字的偏离值
        var rulerLeft = left > (rulersWidth-6) ? (rulersWidth-6) : left;
        _this.find('.u-vCursor-rulers').css({
            left: rulerLeft
        });

        _this.find('.u-vCursor-deploy').click(function(){
            var _this = $(this);
            var _next = _this.next();
            _next.show();
            _next.find(':input[name="txt1"]').unbind('keyup').bind('keyup',function(){
                _next.find(':input[name="txt2"]').val($(this).val());
            });
            _next.find(':input[name="txt3"]').unbind('keyup').bind('keyup',function(){
                _next.find(':input[name="txt4"]').val($(this).val());
            });
            _popup(_next,options, obj);
        });
    }

    function _popup(key, options, obj){
        var _this = $(key);
        _this.find('button.btn-cancel').click(function(){
            _this.hide();
        });
        _this.find('button.btn-info').click(function(){
            var split = _this.find(':input[name="txt6"]').val();
            var data1 = _this.find(':input[name="txt1"]').val();
            var data2 =  _this.find(':input[name="txt3"]').val();
            var data3 = _this.find(':input[name="txt5"]').val();
            var color = _this.find(':radio[name="radio"]:checked').val();

            split = $.isNumeric(split) ? split : options.split;
            data1 = $.isNumeric(data1) ? data1 : options.data[0];
            data2 = $.isNumeric(data2) ? data2 : options.data[1];
            data3 = $.isNumeric(data3) ? data3 : options.data[2];
            color = $.isNumeric(color) ? color : options.colors;

            var config = {
                split: split,
                data: [data1, data2, data3],
                colors: color
            }

            if(_.isFunction(options.onClick)){
                options.onClick(config);
            }
            var opt = $.extend(options, config);
            _create(obj, opt);
        });
    }
});
