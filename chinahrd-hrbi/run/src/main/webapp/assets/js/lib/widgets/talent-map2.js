define(['jquery', 'template', 'zrender', 'zrender/shape/Polyline', 'zrender/shape/Polygon', 'zrender/shape/Text',
    'zrender/tool/color', 'zrender/shape/util/smoothSpline'], function ($, tpl, zrender) {
    /*
     * -------------------------------  html5 -----------------------------------------------
     */
    var Polyline = require('zrender/shape/Polyline');
    var Polygon = require('zrender/shape/Polygon');
    var Text = require('zrender/shape/Text');
    var guid = require('zrender/tool/guid');
    var color = require('zrender/tool/color');
    var smoothSpline = require('zrender/shape/util/smoothSpline');

    var colorIdx = 0;

    var html5Defaults = {
        arrow: {
            width: 12, // 宽
            length: 12, // 高
            color: '#cccccc'
        }
    };

    function drawLeftArrow(zr, x, y, opt) {
        var h = opt.length, w = opt.width / 2;
        zr.addShape(new Polygon({
            style: {
                pointList: [[x, y - w], [x + h, y], [x, y + w]],
                color: '#000'
            },
            hoverable: false
        }));
    }

    function drawUpArrow(zr, x, y, opt) {
        var h = opt.length, w = opt.width / 2;
        zr.addShape(new Polygon({
            style: {
                pointList: [[x - w, y], [x, y - h], [x + w, y]],
                color: '#000'
            },
            hoverable: false
        }));
    }

    function drawLine(zr, startX, startY, endX, endY) {
        zr.addShape(new Polyline({
            style: {
                pointList: [[startX, startY], [endX, endY]],
                lineWidth: 2,
                color: '#000'
            },
            hoverable: false
        }));
    }

    var html5 = function (elem, opt) {
        var html5 = {
            init: function (elem, opt) {
                var h = this;
                h.options = $.extend({}, html5Defaults, opt);
                $(elem).width(h.options.width).height(h.options.height);
                h.zr = zrender.init(elem);
            },

            drawLine: function (points, lineId, animation) {
                var m = this, shapeId = lineId || guid(), zr = this.zr;
                var smoothPoints = smoothSpline(points);
                var line = new Polyline({
                    id: shapeId,
                    style: {
                        pointList: animation ? [] : smoothPoints,
                        lineWidth: 3,
                        strokeColor: color.getColor(colorIdx++)
                    }
                });
                m.zr.addShape(line);
                if (animation) {
                    if (m.animationId) {
                        window.clearInterval(m.animationId)
                    }

                    var i = 0, len = smoothPoints.length;
                    var tid = window.setInterval(function () {
                        if (i >= len - 1) {
                            window.clearInterval(tid);
                            return;
                        }
                        line.style.pointList.push(smoothPoints[i++]);
                        if ($.browser.msie) {
                            var p = smoothPoints[i++];
                            p && line.style.pointList.push(p);
                        }
                        zr.modShape(shapeId, line);
                        zr.refresh();
                    }, 10);

                    m.animationId = tid;
                }
                return shapeId;
            },

            delShape: function (s) {
                this.zr.delShape(s);
            },

            drawHLine: function (y, startX, endX) {
                var h = this, p = h.options;
                drawLine(h.zr, startX, y, endX, y);
                drawLeftArrow(h.zr, endX, y, p.arrow);
            },

            drawVLine: function (x, startY, endY) {
                var h = this, p = h.options;
                drawLine(h.zr, x, startY, x, endY);
                drawUpArrow(h.zr, x, endY, p.arrow);
            },

            render: function () {
                this.zr.render();
            },

            clear: function () {
                this.zr.clear();
                color.resetPalette();
                colorIdx = 0;
            },

            resize: function () {
                this.zr.resize();
            },

            drawText: function (text, x, y) {
                var id = guid();
                this.zr.addShape(new Text({
                    id: id,
                    style: {
                        x: x,
                        y: y,
                        text: text || '',
                        color: '#999',
                        textFont: 'normal 25px 微软雅黑 ',
                        textAlign: 'center',
                        textBaseline: 'middle'
                    }
                }));
                return id;
            },
            drawTitleText: function (text, color, x, y) {
                var id = guid();
                this.zr.addShape(new Text({
                    id: id,
                    style: {
                        x: x,
                        y: y,
                        text: text || '',
                        color: color,
                        textFont: 'normal 14px 微软雅黑',
                        textAlign: 'center',
                        textBaseline: 'middle'
                    }
                }));
                return id;
            }
        };
        html5.init(elem, opt);
        return html5;
    };

    /*
     * -----------------------------------  模板 -----------------------------------------------
     */
    var mapTemplete =
        "<table class=<%=style.styleClass%> >" +
        //		<!-- 第一行 -->
        "<tr>" +
        "<td colspan=<%= yAxis.showType ? '2' : '0'  %> class=<%=style.yaxisTitle%> ><%=yAxis.title%></td>" +
        "<% for(var i = 0; i < xAxis.data.length; i++) { %>" +
        "<td class='u-map-top-label'></td>" +
        "<% } %>" +
        "</tr>" +

        "<% for(var i=0, ydata=yAxis.data, len=ydata.length; i < len; i++) { %>" +
        "<tr>" +
        //			<!-- 左侧类别,处理跨行 --> +
        "<% if(yAxis.showType) { %>" +
        "<% if(i == 0) { %>" +
        "<td rowspan='2' class='u-map-left_type'>潜力</td>" +
        "<% } else if(i == 2) { %>" +
        "<td rowspan='3' class='u-map-left_type'>胜任</td>" +
        "<% } else if(i == 5){ %>" +
        "<td rowspan='2' class='u-map-left_type'>观察</td>" +
        "<% } %>" +
        "<% } %>" +
        //			<!-- 左侧label --> +
        "<% var lblClass = '\"u-map-left_label ' + style.leftLabelPrefix + (i + 1) + '\"' %>" +
        "<td class=<%:= lblClass %> > <%= ydata[len - 1 - i] %> </td>" +
        //			<!-- grid -->	 +
        "<% for(var j = 0, xdata=xAxis.data, xlen=xdata.length; j < xlen; j++) { %>" +
        //				<!-- 字符拼接时要转义 --> +
        "<% var cellClazz = '\"u-map-grid ' + style.cellPrefix + (j + 1) + '_' + (len - i) + '\"' %>" +
        "<td class=<%:= cellClazz %> > </td>" +
        "<% } %>" +
        //			<!-- 右侧人数 --> +
        "<% if(showNum) { %>" +
        "<% var numId = 'u-map-right-num_' + elementId + '_' + (len - i) %>" +
        "<td id=<%= numId %> class=<%=style.rightNum%> >0人</td>" +
        "<% } else { %>" +
        "<td></td>" +
        "<% } %>	" +
        "</tr>" +
        "<% } %>" +

        //		<!-- x轴label -->
        "<tr>" +
        "<td colspan=<%= yAxis.showType ? '2' : '0'  %> ></td>" +
        "<% for(var i = 0, xdata=xAxis.data, xlen=xdata.length; i < xlen; i++) { %>" +
        "<td class='u-map-table-label'><%= xdata[i] %></td>" +
        "<% } %>" +
        "<td class=<%= style.xaxisTitle %> rowspan=<%= showNum ? '2' : '0'  %> ><%= xAxis.title %></td>" +
        "</tr>" +

        //  <!-- x轴最下一行 -->、
        "<tr>" +
        "<td colspan=<%= yAxis.showType ? '2' : '0'  %> ></td>" +
        "<% for(var i = 0, xdata=xAxis.data, xlen=xdata.length; i < xlen; i++) { %>" +
        "<% if(showNum) { %>" +
        "<% var numId = 'u-map-bottom-num_' + elementId + '_' + (i + 1) %>" +
        "<td id=<%= numId %> class=<%= style.bottomNum %> >(0人)</td>" +
        "<% } else { %>" +
        "<td></td>" +
        "<% } %>" +
        "<% } %>" +
        "</tr>" +
        "</table>";
    /*
     * -----------------------------------  地图 -----------------------------------------------
     */
    var defaults = {
        emptyMessage: '暂无数据...',
        width: 800,
        style: {
            styleClass: 'u-map-table',
            xaxisTitle: 'u-map-xaxis-title',
            yaxisTitle: 'u-map-yaxis-title',
            bottomNum: 'u-map-table_num',
            rightNum: 'u-map-right-label',
            cellPrefix: 'u-map-cell_',
            pointClass: 'u-point',
            leftLabelPrefix: 'u-map-left_label_' // 左侧label样式前缀
        },
        isSimple: false,
        showNum: true,      //显示统计
        showTeam: false,    //团队区分
        showText: true,    //显示名称
        teamColors: ['#0777F1', '#00CC00', '#FFCC00'],
        xAxis: {
            title: "",
            data: [],
            widths: []
        },
        yAxis: {
            title: "",
            data: [],
            showType: false,
            type: [{text: '观察 ', span: 2}, {text: '胜任', span: 3}, {text: '潜力', span: 2}]
        },
        /** 第三区域定义 */
        zAxis: {
            show: true,
            data: [
                // {name: '重点培养', color: '#B3E6B3', values: [{x: 4, y: 7}, {x: 5, y: 7}]},
                // {name: '关注培养', color: '#E1F5E1', values: [{x: 4, y: 6}, {x: 5, y: 6}]},
                // {name: '超越胜任', color: '#FFFFDD', values: [{x: 3, y: 5}, {x: 4, y: 5}]},
                // {name: '完全胜任', color: '#FFFFB9', values: [{x: 2, y: 4}, {x: 3, y: 4}, {x: 4, y: 4}]},
                // {name: '基本胜任', color: '#FFFF99', values: [{x: 2, y: 3}, {x: 3, y: 3}, {x: 4, y: 3}]},
                // {name: '重点观察', color: '#FDDFDE', values: [{x: 1, y: 2}, {x: 2, y: 2}, {x: 3, y: 2}]},
                // {name: '准备退出', color: '#FECAC9', values: [{x: 1, y: 1}, {x: 2, y: 1}]}
            ],
        },
        /** 得到点的背景图片样式 */
        getPointClass: null, // function(point) {}
        /** 点相关配置 */
        point: {
            /** 点添加到地图上后触发 */
            afterCreate: null, // function(elem, data) {},
            /** 地图上点的数量发生变化时触发 */
            afterChange: null, //function(points) {},
            onClick: null, //function(elem, data) {},
            onHover: null, //function(elem, data) {},
            sort: function (p1, p2) { // 默认排序方法
                if (p1._x === p2._x) {
                    return p1._y - p2._y;
                }
                return p1._x - p2._x;
            }
        },
        /** 汇总点 */
        groupPoint: {
            afterCreate: null, // function(elem, points, groupInfo) {},
            onClick: null, //function(elem, points, groupInfo) {},
            onHover: null //function(elem, points, groupInfo) {},
        }
    };

    /* 地图全局计数器 */
    var g_talentMapCnt = 0;
    $.fn.talentMap = function (options) {
        var map = {

            _create: function (elem, options) {
                var m = this, e = m.element = $(elem);
                if (e.length === 0) {
                    console && console.error(elem);
                    throw "Html element doesn't exist.";
                }
                var p = m.options = $.extend(true, {elementId: e.attr('id')}, defaults, options);
                if (!p.showText) {
                    m.element.addClass('u-map-hideText');
                }
                m.points = [], m.lines = [];
                m.frontMap = e.children(p.styleClass);
                // 表格
                tpl.LEFT_DELIMITER = "<%";
                tpl.RIGHT_DELIMITER = "%>";
                var bottomTabHtml = tpl(mapTemplete, p);
                var bottomTab = m._table = $(bottomTabHtml).width(p.width);
                e.html(bottomTab);
                //z轴添加
                if (p.zAxis && p.zAxis.show) m._renderZColor(bottomTab, p.zAxis.data, p.style.cellPrefix);
                // loading
                m._loading = $("<div class='f-overlay' style='z-index:150;'><div class='f-overlay-txt'>请稍候...</div></div>");
                m._loading.appendTo(e);
                e.width(bottomTab.width());
                m._initColumnWidth();
                // 画布
                var html5Div = m._canvas = $("<div class='u-map-canvas'></div>");
                e.append(html5Div);
                m.html5 = new html5(html5Div.get()[0], {
                    width: bottomTab.width(),
                    height: bottomTab.height()
                });
                // add point
                if (p.points && p.points.length) {
                    m.points = p.points;
                    m.addPoints(p.points);
                    p.showNum && m.refreshPointCount();
                }
                m._drawAxes();
                m.html5.render();

                // 事件

                if (p.isSimple) {
                    p.point.onClick && m.element.unbind('click').on("click", ".u-point-wrapper,.u-remain-point-wrapper", function (event) {
                        var elem = $(this), data = m._findPointDataById(elem.attr("id"));
                        data && p.point.onClick.call(m, elem, data);
                    });
                } else {
                    p.point.onClick && m.element.unbind('click').on("click", ".u-point-wrapper", function (event) {
                        var elem = $(this), data = m._findPointDataById(elem.attr("id"));
                        data && p.point.onClick.call(m, elem, data);
                    });
                }
                p.point.onHover && m.element.unbind('mouseenter').on("mouseenter", ".u-point-wrapper", function (event) {
                    var elem = $(this), data = m._findPointDataById(elem.attr("id"));
                    data && p.point.onHover.call(m, elem, data);
                });
                p.groupPoint.onClick && m.element.on("click", ".u-point-group-wrapper", function (event) {
                    var elem = $(this), inf = elem.data('ginfo');
                    var points = m._grepGroupPoints(inf.x, inf.y);
                    p.groupPoint.onClick.call(m, elem, points, inf)
                });
                p.groupPoint.onHover && m.element.on("mouseenter", ".u-point-group-wrapper", function (event) {
                    var elem = $(this), inf = elem.data('ginfo');
                    var points = m._grepGroupPoints(inf.x, inf.y);
                    p.groupPoint.onHover.call(m, elem, points, inf);
                });

                //  point id生成器
                m._point_cnt = 0;
                m._cnt = g_talentMapCnt++;
                m._invalidPoints = []; // 无效点
                m._loading.hide();
                return m;
            },
            _renderZColor: function (e, d, p) {
                var m = this;
                if (!$.isArray(d)) return;
                $.each(d, function (i, o) {
                    var color = o.color;
                    if (!$.isArray(o.values)) return true;
                    $.each(o.values, function (j, coor) {
                        e.find('td.u-map-grid.' + p + coor.x + '_' + coor.y).css('backgroundColor', color);
                    });
                });
            },
            _grepGroupPoints: function (x, y) {
                var m = this, p = m.options;
                var points = $.grep(m.points, function (p) {
                    return p._x === x && p._y === y;
                });
                if (p.point && p.point.sort) {
                    return points.sort(p.point.sort);
                }
                return points;
            },

            getOption: function () {
                var m = this, p = m.options;
                p.points = m.getAllPoints();
                return p;
            },

            /***
             * 画线
             * @param points 点数组
             */
            drawLine: function (points) {
                if (!points || points.length < 2) {
                    return '';
                }
                var m = this;
                var lineId = this._drawLine(points);
                m.lines.push({
                    id: lineId,
                    points: points
                });

                m.html5.render();
                return lineId;
            },

            _drawLine: function (points, lineId, animation) {
                var m = this, xys = [];
                $.each(points, function (i, p) {
                    if (!p.__id) {
                        console && console.error('error:p.__id is null.');
                        return;
                    }
                    var xy = m._getXY("#" + p.__id + " .u-point");
                    xy && xys.push([xy.x + 8, xy.y + 8]);
                });
                return m.html5.drawLine(xys, lineId, animation);
            },

            deleteLine: function (lineId) {
                if (lineId) {
                    var m = this;
                    m._deleteLineObject(lineId);
                    m.html5.delShape(lineId);
                    m.html5.render();
                }
            },

            _deleteLineObject: function (lineId) {
                var m = this;
                var lines = $.grep(m.lines, function (line) {
                    return line.id !== lineId;
                });
                delete m.lines;
                m.lines = lines;
            },

            /***
             * 根据x, y属性取得相应格子中的点.
             * 如果不传prop就返回点对象{}， 否则返回指定属性数组
             */
            getPoints: function (x, y, prop) {
                var m = this, p = m.options;
                var points = $.grep(m.points, function (p) {
                    return p._x == x && p._y == y;
                });
                return !prop ? points : $.map(points, function (p) {
                    return p[prop];
                });
            },
            getAllPoints: function () {
                return this.points;
            },
            getAllPointsNumber: function () {
                var m = this, p = m.options, points = m.points;
                if (!p.isSimple) return points.length;
                var num = 0;
                for (var i = 0; i < points.length; i++) {
                    if (undefined === points[i].remainNum) num++;
                    else num += points[i].remainNum;
                }
                return num;
            },
            /***
             * 根据x, y属性和团队编号取得相应格子中的点.
             * 如果不传prop就返回点对象{}， 否则返回指定属性数组
             */
            getTeamPoints: function (t, x, y, prop) {
                var m = this, p = m.options;
                var points = $.grep(m.points, function (p) {
                    return p._x == x && p._y == y && p._t == t;
                });
                return !prop ? points : $.map(points, function (p) {
                    return p[prop];
                });
            },

            getZAxisPoints: function (prop) {
                var m = this, p = m.options;
                if (!p.zAxis || !p.zAxis.show) return null;
                var zData = p.zAxis.data;
                if (p.isSimple) {
                    for (var i = 0; i < zData.length; i++) {
                        var o = zData[i];
                        if (!$.isArray(o.values)) continue;
                        var pointNumber = 0;
                        for (var j = 0; j < o.values.length; j++) {
                            var coor = o.values[j], p = m.getPoints(coor.x, coor.y, prop);
                            for (var x = 0; x < p.length; x++) {
                                if (undefined === p[x].remainNum) pointNumber++;
                                else pointNumber += p[x].remainNum;
                            }
                        }
                        o.pointsTotal = pointNumber;
                    }
                    return zData;
                }
                for (var i = 0; i < zData.length; i++) {
                    var o = zData[i];
                    if (!$.isArray(o.values)) continue;
                    var points = [];
                    for (var j = 0; j < o.values.length; j++) {
                        var coor = o.values[j], p = m.getPoints(coor.x, coor.y, prop);
                        points = points.concat(p);
                    }
                    o.points = points;
                    o.pointsTotal = points.length;
                }
                return zData;
            },
            getZAxisTeamPoints: function (prop) {
                var m = this, p = m.options;
                if (!p.zAxis || !p.zAxis.show || !p.showTeam) return null;
                var zData = p.zAxis.data, colors = p.teamColors, teamArray = p.teamArray, tlen = teamArray.length;
                for (var i = 0; i < zData.length; i++) {    //获取每块区域的数据
                    var o = zData[i];
                    if (!$.isArray(o.values)) continue;
                    var points = [], total = 0;
                    for (var c = 0; c < tlen; c++) {    //获取不同团队的数据
                        if (teamArray[c] == 0) continue;
                        var team = {}, data = [];
                        team.color = colors[c];
                        for (var j = 0; j < o.values.length; j++) { //获取不同区域的点
                            var coor = o.values[j], p = m.getTeamPoints(c, coor.x, coor.y, prop);
                            total += p.length;
                            data = data.concat(p);
                        }
                        team.data = data;
                        points.push(team);
                    }
                    o.points = points;
                    o.pointsTotal = total;
                }
                return zData;
            },

            grepPoints: function (prop, value) {
                var m = this, p = m.options;
                var points = $.grep(m.points, function (p) {
                    return p[prop] === value;
                });
                if (p.point && p.point.sort) {
                    return points.sort(p.point.sort);
                }
                return points;
            },


            _addPointData: function (point) {
                var m = this, p = m.options;
                if (!m._exists(point)) {
                    point.__id = 'p_' + m._cnt + '_' + (m._point_cnt++);
                    m.points.push(point);
                }
            },

            _createPointHtml: function (point) {
                var m = this, p = m.options;
                var html = $('<span class="u-point-wrapper"></span>');
                // 点
                var pointClass = p.getPointClass ? p.getPointClass(point) : p.style.pointClass;
                var icon = $("<i class='icon-circle'></i>");
                icon.addClass(pointClass).css('color', p.teamColors[point._t]).appendTo(html);
                // text
                var text = point.text || '';
                html.append('<span class="u-point-label" title=' + text + '>' + text + '</span>');
                html.attr('id', point.__id);
                // 事件
                if (p.point && p.point.afterCreate) {
                    setTimeout(function () {
                        p.point.afterCreate(html, point);
                    }, 0);
                }
                return html;
            },
            _createRemainPointHtml: function (point) {
                var m = this, p = m.options;
                var html = $('<span class="u-remain-point-wrapper"></span>');
                var txt = $('<span class="u-point-label"></span>');
                txt.text(point.remainNum);
                txt.attr('number', point.remainNum);
                txt.appendTo(html);
                html.attr('id', point.__id);
                // 事件
                if (p.point && p.point.afterCreate) {
                    setTimeout(function () {
                        p.point.afterCreate(html, point);
                    }, 0);
                }
                return html;
            },

            _exists: function (point) {
                if (!point.__id) {
                    return false;
                }
                var exists = false;
                $.each(this.points, function (idx, p) {
                    if (p.__id === point.__id) {
                        exists = true;
                        return false;
                    }
                });
                return exists;
            },

            /**
             * 清除地图上所有的覆盖物，包括点、线、汇总点
             */
            clear: function () {
                var m = this, p = m.options;
                // clear points and lines
                m.points = [];
                m.lines = [];
                if (p.showTeam) p.teamArray = [];
                // clear points & group points
                m._table.find(".u-point-wrapper").remove();
                m._table.find(".u-remain-point-wrapper").remove();
                m._table.find(".u-point-group-wrapper").remove();
                // redraw
                p.showNum && m.refreshPointCount();
                m._redrawCanvas();
                // 还需要触发事件
                if (p.point.afterChange) {
                    p.point.afterChange([]);
                }
                m._invalidPoints = [];
                m._toggleEmptyMessage();
            },

            delPoint: function (point) {
                var m = this, p = m.options;
                // 删除元素
                var elem = $("#" + point.__id);
                elem.remove();
                m.points = $.grep(m.points, function (pp) {
                    return pp.__id !== point.__id;
                });
                // 如果这个点在线中，把相关的线全删除
                $.each(m.lines, function (idx, line) {
                    $.each(line.points, function (pidx, lp) {
                        if (lp.__id === point.__id) {
                            m._deleteLineObject(line.id);
                            return false;
                        }
                    });
                });
                p.showNum && m.refreshPointCount();
                // 会变化大小，要重绘
                m._redrawCanvas();
                // 汇总模式也要变化数字
                if (m._groupMode) {
                    m._refreshGroupMode();
                }
                // 删除标记属性
                delete point.__id;
                delete point._x;
                delete point._y;
                // 还需要触发事件
                if (p.point.afterChange) {
                    var ps = m.getAllPoints();
                    p.point.afterChange(ps);
                }
                m._toggleEmptyMessage();
            },
            //批量删除点
            delPoints: function (points) { //add by lkliao
                var m = this, p = m.options, pointIds = [];
                //删除元素
                $.each(points, function (i, point) {
                    pointIds.push(point.__id);
                    var elem = $("#" + point.__id);
                    elem.remove();
                });

                m.points = $.grep(m.points, function (pp) {
                    return $.inArray(pp.__id, pointIds) == -1;
                });

                if (p.showTeam) {
                    var teamIdxs = [], teamArr = [];
                    $.each(m.points, function (i, p) {
                        if ($.inArray(p._t, teamIdxs) == -1) teamIdxs.push(p._t);
                    });
                    $.each(teamIdxs, function (index, id) {
                        teamArr.push(p.teamArray[id]);
                    });
                    p.teamArray = teamArr;
                }

                // 如果这个点在线中，把相关的线全删除
                $.each(m.lines, function (idx, line) {
                    $.each(line.points, function (pidx, lp) {
                        if ($.inArray(lp.__id, pointIds) != -1) {
                            m._deleteLineObject(line.id);
                        }
                    });
                });
                p.showNum && m.refreshPointCount();
                // 会变化大小，要重绘
                m._redrawCanvas();
                // 汇总模式也要变化数字
                if (m._groupMode) {
                    m._refreshGroupMode();
                }
                // 删除标记属性
                $.each(points, function (idx, point) {
                    delete point.__id;
                    delete point._x;
                    delete point._y;
                });
                // 还需要触发事件
                if (p.point.afterChange) {
                    var ps = m.getAllPoints();
                    p.point.afterChange(ps);
                }
                m._toggleEmptyMessage();

            },
            /***
             * 添加或设置团队名称，团队模式启动
             */
            initTeamName: function (names) {
                var m = this, p = m.options;
                p.teamArray = names;
            },
            /**
             * 往图上添加点
             * {
			 * 	 direction : 'up' | 'down' | 'left', // default
			 *   x : 1,
			 *   y : 2,
			 *   xLabel : '后10%',
			 *   yLabel : '准备退出',
			 * 	 text : '张三',
			 * }
             */
            addPoint: function (point) {
                var m = this, p = m.options;
                if (!point) {
                    m._toggleEmptyMessage();
                    return;
                }
                // 找出是哪个单元格
                var x = point.x || $.inArray(point.xLabel, p.xAxis.data) + 1;
                var y = point.y || $.inArray(point.yLabel, p.yAxis.data) + 1;
                if (m._isValidXy(x, y) === false) {
                    m._invalidPoints.push(point);
                    console && console.info(point);
                    return;
                }
                var cell = m._findCell(x, y);
                // 记住点在哪个上面
                point._x = x;
                point._y = y;
                // 添加点的团队区分,默认第一团队
                var colorNum = 0;
                if (p.showTeam) {
                    var teamArray = undefined === p.teamArray ? [] : p.teamArray;
                    if ($.inArray(point.team, teamArray) == -1) {
                        var tIdx = $.inArray(0, teamArray);
                        if (tIdx == -1) {
                            teamArray.push(point.team);
                        } else {
                            teamArray[tIdx] = point.team;
                        }
                    }
                    p.teamArray = teamArray;
                    colorNum = $.inArray(point.team, teamArray);
                }
                point._t = colorNum;

                m._addPointData(point);
                // 加到格子中对应的区域
                if (!m._groupMode) {
                    var html = m._createPointHtml(point);
                    cell.append(html);
                } else {
                    m._refreshGroupMode();
                }
                // 如果显示人数统计，还需要刷新人数
                p.showNum && m.refreshPointCount();
                // 加点会变化大小，要重绘
                m._redrawCanvas();
                // 还需要触发事件
                if (p.point.afterChange) {
                    var ps = m.getAllPoints();
                    p.point.afterChange(ps);
                }

                m._toggleEmptyMessage();
                return point;
            },

            addPoints: function (points) {
                var m = this, p = m.options;
                if (!points || !points.length) {
                    m._toggleEmptyMessage();
                    return;
                }
                points.length > 30 && m.showLoading();
                // 先把所有的cell查出来
                var cellAreas = {}; // key x_y_selector
                function getArea(x, y) {
                    var key = x + '_' + y;
                    if (undefined == cellAreas[key]) {
                        var cell = m._findCell(x, y);
                        cellAreas[key] = cell;
                    }
                    return cellAreas[key];
                }

                $.each(points, function (idx, point) {
                    var x = point.x || $.inArray(point.xLabel, p.xAxis.data) + 1;
                    var y = point.y || $.inArray(point.yLabel, p.yAxis.data) + 1;
                    if (m._isValidXy(x, y) === false) {
                        m._invalidPoints.push(point);
                        return;
                    }
                    // 记住点在哪个上面
                    point._x = x;
                    point._y = y;
                    // 添加点的团队区分,默认第一团队
                    var colorNum = 0;
                    if (p.showTeam) {
                        var teamArray = undefined === p.teamArray ? [] : p.teamArray;
                        if ($.inArray(point.team, teamArray) == -1) {
                            var tIdx = $.inArray(0, teamArray);
                            if (tIdx == -1) {
                                teamArray.push(point.team);
                            } else {
                                teamArray[tIdx] = point.team;
                            }
                        }
                        p.teamArray = teamArray;
                        colorNum = $.inArray(point.team, teamArray);
                    }
                    point._t = colorNum;

                    // 加到格子中对应的区域
                    m._addPointData(point);
                    // 加到格子中对应的区域
                    var html = m._createPointHtml(point);
                    getArea(x, y).append(html);
                    if (m._groupMode) m._refreshGroupMode();
                });
                // 如果显示人数统计，还需要刷新人数
                p.showNum && m.refreshPointCount();
                // 加点会变化大小，要重绘
                m._redrawCanvas();
                // 还需要触发事件
                if (p.point.afterChange) {
                    var ps = m.getAllPoints();
                    p.point.afterChange(ps);
                }
                m.hideLoading();
                m._toggleEmptyMessage();

                return points;
            },
            addSimplePoints: function (datas) {
                var m = this, p = m.options;
                if (!datas || !datas.length) {
                    m._toggleEmptyMessage();
                    return;
                }
                datas.length > 30 && m.showLoading();
                // 先把所有的cell查出来
                var cellAreas = {}; // key x_y_selector
                function getArea(x, y) {
                    var key = x + '_' + y;
                    if (undefined == cellAreas[key]) {
                        var cell = m._findCell(x, y);
                        cellAreas[key] = cell;
                    }
                    return cellAreas[key];
                }

                $.each(datas, function (idx, obj) {
                    var x = obj.x || $.inArray(obj.xLabel, p.xAxis.data) + 1;
                    var y = obj.y || $.inArray(obj.yLabel, p.yAxis.data) + 1;
                    if (m._isValidXy(x, y) === false) {
                        m._invalidPoints.push(obj);
                        return;
                    }
                    var areaObj = getArea(x, y);
                    //获取每个单元格宽度以及放置小点个数（40为小点宽度）* 2排 - 最后一个
                    var width = areaObj.width() || 175, points = obj.data, len = points.length, pointNum = parseInt(width / 40) * 2;
                    var bool = len == pointNum ? true : false;
                    pointNum = pointNum - (bool ? 0 : 1);
                    for (var i = 0; i < len && i < pointNum; i++) {
                        var point = points[i];
                        point.text = point.v;
                        point.empId = point.k;
                        // 记住点在哪个上面
                        point._x = x;
                        point._y = y;
                        point.xLabelId = obj.xLabelId;
                        point.yLabelId = obj.yLabelId;
                        // TODO Simple暂不考虑团队区分
                        point._t = 0;
                        // 加到格子中对应的区域
                        m._addPointData(point);
                        var html = m._createPointHtml(point);
                        areaObj.append(html);
                    }
                    if (!bool && obj.countNum && obj.countNum - pointNum > 0) {
                        var remainPoint = $.extend(true, {}, obj);
                        remainPoint.remainNum = obj.countNum - pointNum;
                        remainPoint._x = x;
                        remainPoint._y = y;
                        // Simple暂不考虑团队区分
                        remainPoint._t = 0;
                        // 加到格子中对应的区域
                        m._addPointData(remainPoint);
                        var html = m._createRemainPointHtml(remainPoint);
                        areaObj.append(html);
                    }
                    if (m._groupMode) m._refreshGroupMode();
                });
                m.simplePoints = datas;
                // 如果显示人数统计，还需要刷新人数
                p.showNum && m.refreshPointCount();
                // 加点会变化大小，要重绘
                m._redrawCanvas();
                // 还需要触发事件
                if (p.point.afterChange) {
                    var ps = m.getAllPoints();
                    p.point.afterChange(ps);
                }
                m.hideLoading();
                m._toggleEmptyMessage();
            },

            /**
             * 是否有效的点
             */
            _isValidXy: function (x, y) {
                var m = this, p = m.options;
                var v = x > 0 && y > 0 && x <= p.xAxis.data.length && y <= p.yAxis.data.length;
                return v;
            },

            _initColumnWidth: function () {
                var m = this, p = m.options, e = m._table;
                var gridWidth = e.width() - e.find(".u-map-yaxis-title:first").width() - e.find(".u-map-xaxis-title:first").width();
                var widths = [];
                if (p.xAxis.widths && p.xAxis.widths.length) {
                    $.each(p.xAxis.widths, function (idx, w) {
                        widths.push(w * gridWidth);
                    });
                } else {
                    var len = p.xAxis.data.length, w = gridWidth / len;
                    for (var i = 0; i < len; i++) {
                        widths.push(w);
                    }
                }

                var trs = this.element.find("tr");
                $.each(trs, function (idx, tr) {
                    var tds = $(tr).children(".u-map-grid");
                    $.each(tds, function (i, td) {
                        $(td).width(widths[i]);
                    });
                });
            },

            _findPointDataById: function (id) {
                var ret = null;
                $.each(this.points, function (i, p) {
                    if (p.__id === id) {
                        ret = p;
                        return false;
                    }
                });
                return ret;
            },

            _getPointSelector: function (point) {
                if (point.direction === 'up') {
                    return 'u-point-u';
                }
                if (point.direction === 'down') {
                    return 'u-point-d';
                }
                // 不设置则强制为左
                point.direction = 'left';
                return 'u-point-l';
            },

            _findCell: function (x, y) {
                var m = this, p = this.options;
                return m._table.find("." + p.style.cellPrefix + x + '_' + y);
            },

            /**  画坐标轴 */
            _drawAxes: function () {
                var m = this, p = m.options, teamArr = p.teamArray;
                // 绩效排名
                var xTitleTd = m._table.find('.u-map-xaxis-title:first');
                var leftTopXy = m._getXY(xTitleTd);
                var endX = leftTopXy.x, startY = leftTopXy.y + 1;
                // 能力等级
                var yTitleTd = m._table.find('.u-map-yaxis-title:first');
                var rightBottomXy = m._getXY(yTitleTd);
                var endY = rightBottomXy.y + yTitleTd.height(), startX = rightBottomXy.x + yTitleTd.width();
                // x轴
                m.html5.drawHLine(startY, startX + 19, endX + 20);
                // y轴
                m.html5.drawVLine(startX + 20, startY, endY - 20);
                //团队相关
                if (!p.showTeam || undefined === teamArr || teamArr.length == 0) return;
                var colors = p.teamColors, tlen = teamArr.length;
                var l = 0;
                for (var i = 0; i < tlen; i++) {
                    if (teamArr[i] === 0) continue;
                    //计算坐标轴位置
                    var len = teamArr[i].length, w = len * 18 + 5, sizeL = len * 16 / 2;
                    l += w;
                    m.html5.drawTitleText('●', colors[i], parseInt(endX / 2.5 + l - sizeL), endY - 30);
                    m.html5.drawTitleText(teamArr[i], '#000', parseInt(endX / 2.5 + l), endY - 30);
                }
            },

            _getXY: function (elem) {
                var eo = this.element.offset();
                var po = $(elem).offset();
                return po ? {
                    x: po.left - eo.left,
                    y: po.top - eo.top
                } : null;
            },

            /**
             * 重绘画布
             */
            _redrawCanvas: function (animation) {
                var m = this;
                // 画布大小
                m._canvas.width(m._table.width());
                m._canvas.height(m._table.height());
                m.html5.clear();
                m.html5.resize();
                // 重画线
                $.each(m.lines, function (idx, line) {
                    m._drawLine(line.points, line.id, animation);
                });
                // 重线坐标轴
                m._drawAxes();
                m.html5.render();
            },

            /** 显示/隐藏点的label  */
            toggleLabel: function () {
                this.element.toggleClass('u-map-hideText');
                this._redrawCanvas();
            },
            /** 显示/隐藏网格 */
            toggleGrids: function () {
                this.element.toggleClass('u-map-hideGrids');
                this._redrawCanvas();
            },
            _refreshGroupMode: function () {
                var m = this, p = m.options;
                // 删除汇总
                m._table.find(".u-point-group-wrapper").remove();
                m.toggleGroupMode(true);
            },

            /** 切换汇总模式 */
            toggleGroupMode: function (groupMode) {
                var m = this, p = m.options;
                m.showLoading();
                m._groupMode = undefined === groupMode ? !m._groupMode : groupMode;
                //  汇总模式
                if (m._groupMode) {
                    // 把所有的点去掉
                    m._table.find(".u-point-wrapper").remove();
                    if (p.isSimple) {
                        m._table.find(".u-remain-point-wrapper").remove();
                    }
                    // 统计出各单元格人数
                    // 初始化人数map
                    var countMap = {};
                    // x_y_team
                    var xlen = p.xAxis.data.length, ylen = p.yAxis.data.length, tlen = p.teamColors.length;
                    for (var x = 1; x <= xlen; x++) {
                        for (var y = 1; y <= ylen; y++) {
                            if (!p.showTeam) {
                                countMap[x + '_' + y] = 0;
                                continue;
                            }
                            for (var t = 0; t < tlen; t++) {
                                countMap[x + '_' + y + '_' + t] = 0;
                            }
                        }
                    }
                    $.each(m.points, function (idx, point) {
                        if (!p.showTeam) {
                            countMap[point._x + '_' + point._y] += (undefined === point.remainNum ? 1 : point.remainNum);
                            return true;
                        }
                        countMap[point._x + '_' + point._y + '_' + point._t] += 1;
                    });
                    // 显示汇总人数
                    for (var x = 1; x <= xlen; x++) {
                        for (var y = 1; y <= ylen; y++) {
                            var cell = m._findCell(x, y);
                            if (!p.showTeam) {
                                m._addGroupPointHtml(cell, countMap[x + '_' + y], x, y, 0);
                                continue;
                            }
                            for (var t = 0; t < tlen; t++) {
                                m._addGroupPointHtml(cell, countMap[x + '_' + y + '_' + t], x, y, t);
                            }
                        }
                    }
                } else {
                    // 删除汇总
                    m._table.find(".u-point-group-wrapper").remove();
                    if (!p.isSimple) {
                        m.addPoints(m.points);
                        return;
                    }
                    m.points = [];
                    m.addSimplePoints(m.simplePoints);
                }
                // 也要重画
                this._redrawCanvas();
                m.hideLoading();
            },
            _addGroupPointHtml: function (cell, cnt, x, y, t) {
                // 等于0就不显示
                if (!cnt) return;

                var m = this, p = m.options, color = p.teamColors[t];
                var html = $("<div class='u-point-group-wrapper'><i class='icon-group'></i></div>");
                html.append("<span class='u-point-group' style='border:2px solid " + color + ";'></span>");
                var ginfo = {
                    x: x,
                    y: y,
                    t: t
                };
                html.find(".u-point-group").text(cnt);
                html.data('ginfo', ginfo);

                cell.append(html);
                //  触发创建后事件
                if (p.groupPoint && p.groupPoint.afterCreate) {
                    var points = m._grepGroupPoints(x, y);
                    p.groupPoint.afterCreate(html, points, ginfo);
                }
            },

            refreshPointCount: function () {
                var m = this, p = m.options, teamArr = p.teamArray;
                var xlen = p.xAxis.data.length, ylen = p.yAxis.data.length;
                // 初始化
                var xcounts = [], ycounts = [];

                if (!p.showTeam || undefined == teamArr || teamArr.length == 0) {
                    for (var i = 0; i < xlen; i++) {
                        xcounts.push(0);
                    }
                    for (var i = 0; i < ylen; i++) {
                        ycounts.push(0);
                    }
                    // 计算数量
                    $.each(m.points, function (idx, point) {
                        var num = 1;
                        if (p.isSimple && point.countNum) num = point.remainNum;
                        xcounts[point._x - 1] += num;
                        ycounts[point._y - 1] += num;
                    });
                    // 填充数量
                    for (var i = 0; i < ylen; i++) {
                        $('#u-map-right-num_' + p.elementId + '_' + (i + 1)).text(ycounts[i] + "人");
                    }
                    for (var i = 0; i < xlen; i++) {
                        $('#u-map-bottom-num_' + p.elementId + '_' + (i + 1)).text(xcounts[i] + "人");
                    }
                    return;
                }
                var tlen = teamArr.length;
                for (var i = 0; i < xlen; i++) {
                    var n = [];
                    for (var j = 0; j < tlen; j++) {
                        n.push(0);
                    }
                    xcounts.push(n);
                }
                for (var i = 0; i < ylen; i++) {
                    var n = [];
                    for (var j = 0; j < tlen; j++) {
                        n.push(0);
                    }
                    ycounts.push(n);
                }
                // 计算数量
                $.each(m.points, function (idx, point) {
                    xcounts[point._x - 1][point._t] += 1;
                    ycounts[point._y - 1][point._t] += 1;
                });
                // x轴填充数量
                for (var i = 0; i < xcounts.length; i++) {
                    var html = '';
                    for (var j = 0; j < tlen; j++) {
                        if (teamArr[j] == 0) continue;
                        html += '<span class="u-map-bottom-block">';
                        html += '<i class="icon-circle" style="color: ' + p.teamColors[j] + ';"></i>';
                        html += '<span>' + xcounts[i][j] + '人</span></span>';
                    }
                    $('#u-map-bottom-num_' + p.elementId + '_' + (i + 1)).html(html);
                }
                // y轴填充数量
                for (var i = 0; i < ycounts.length; i++) {
                    var html = '';
                    for (var j = 0; j < tlen; j++) {
                        if (teamArr[j] == 0) continue;
                        html += '<div class="u-map-right-block">';
                        html += '<i class="icon-circle" style="color: ' + p.teamColors[j] + ';"></i>';
                        html += '<span>' + ycounts[i][j] + '人</span></div>';
                    }
                    $('#u-map-right-num_' + p.elementId + '_' + (i + 1)).html(html);
                }
            },

            /** 重画线 */
            redrawLines: function () {
                this._redrawCanvas(true);
            },

            resize: function (w) {
                var m = this;
                $(m.element).width(w);
                m._table.width(w);
                m._redrawCanvas(true);
            },

            /**
             * 无效的点
             */
            getInvalidPoints: function () {
                return this._invalidPoints || [];
            },

            showLoading: function () {
            },
            hideLoading: function () {
            },

            _toggleEmptyMessage: function () {
                var m = this, p = m.options;
                m._clearEmptyMessage();
                if (!m.points || !m.points.length) {
                    m._showEmptyMessage();
                }
            },

            _showEmptyMessage: function () {
                var m = this, p = m.options;
                var xTitleTd = m._table.find('.u-map-xaxis-title:first');
                var x = (m._table.width()) / 2;
                var y = (m._table.height() - xTitleTd.height()) / 2;
                m._emptyMessageId = m.html5.drawText(p.emptyMessage, x, y);
                m.html5.render();
            },

            _clearEmptyMessage: function () {
                var m = this, textId = m._emptyMessageId;
                if (textId) {
                    m.html5.delShape(textId);
                    m.html5.render();
                }
            }
        };

        return map._create(this, options);
    };

});
