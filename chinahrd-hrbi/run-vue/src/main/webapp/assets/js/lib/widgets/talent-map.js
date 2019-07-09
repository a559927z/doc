define(['jquery', 'template', 'zrender', 'zrender/shape/Polyline', 'zrender/shape/Polygon', 'zrender/shape/Text', 'zrender/tool/color', 'zrender/shape/util/smoothSpline'], function($, tpl, zrender) {
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
		arrow : {
			width : 12, // 宽
			length : 15, // 高
			color : '#cccccc'
		}
	};

	function drawLeftArrow(zr, x, y, opt) {
		var h = opt.length, w = opt.width / 2;
		zr.addShape(new Polygon({
			style : {
				pointList : [[x, y - w], [x + h, y], [x, y + w]],
				color : '#cccccc'
			},
			hoverable : false
		}));
	}

	function drawUpArrow(zr, x, y, opt) {
		var h = opt.length, w = opt.width / 2;
		zr.addShape(new Polygon({
			style : {
				pointList : [[x - w, y], [x, y - h], [x + w, y]],
				color : '#cccccc'
			},
			hoverable : false
		}));
	}

	function drawLine(zr, startX, startY, endX, endY) {
		zr.addShape(new Polyline({
			style : {
				pointList : [[startX, startY], [endX, endY]],
				lineWidth : 4,
				color : '#cccccc'
			},
			hoverable : false
		}));
	}

	var html5 = function(elem, opt) {
		var html5 = {
			init : function(elem, opt) {
				this.options = $.extend({}, html5Defaults, opt);
				$(elem).width(this.options.width).height(this.options.height);
				this.zr = zrender.init(elem);
			},
	
			drawLine : function(points, lineId, animation) {
				var shapeId = lineId || guid(), zr = this.zr;
				var smoothPoints = smoothSpline(points);
				var line = new Polyline({
					id : shapeId,
					style : {
						pointList : animation ? [] : smoothPoints,
						lineWidth : 3,
						strokeColor : color.getColor(colorIdx++)
					}
				});
				this.zr.addShape(line);
				if(animation) {
					if(this.animationId) {window.clearInterval(this.animationId)}
					
					var i = 0, len = smoothPoints.length;
					var tid = window.setInterval(function() {
						if(i >= len - 1) {window.clearInterval(tid); return;}
						line.style.pointList.push(smoothPoints[i++]);
						if($.browser.msie) {
							var p = smoothPoints[i++];
							p && line.style.pointList.push(p);
						}
						zr.modShape(shapeId, line);
						zr.refresh();
					}, 10);
					
					this.animationId = tid;
				}
				return shapeId;
			},
	
			delShape : function(s) {
				this.zr.delShape(s);
			},
	
			drawHLine : function(y, startX, endX) {
				var h = this, p = h.options;
				drawLine(this.zr, startX, y, endX, y);
				drawLeftArrow(this.zr, endX, y, p.arrow);
			},
	
			drawVLine : function(x, startY, endY) {
				var h = this, p = h.options;
				drawLine(this.zr, x, startY, x, endY);
				drawUpArrow(this.zr, x, endY, p.arrow);
			},
	
			render : function() {
				this.zr.render();
			},
	
			clear : function() {
				this.zr.clear();
				color.resetPalette();
				colorIdx = 0;
			},
	
			resize : function() {
				this.zr.resize();
			},
			
			drawText : function(text, x, y) {
				var id = guid();
				this.zr.addShape(new Text({
					id : id,
					style : {
						x : x,
						y : y,
						text : text || '',
						color : '#999999',
						textFont : 'normal 25px 微软雅黑 ',
						textAlign: 'center',
						textBaseline : 'middle'
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
				"<tr>"+
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
						"<td class=<%:= cellClazz %> > " + 
							"<div class='u-point-u'></div><div class='u-point-l'></div><div class='u-point-d'></div>" + 
						"</td>" + 
	           		"<% } %>" + 
		//			<!-- 右侧人数 --> + 
					"<% if(showNum) { %>" + 
						"<% var numId = 'u-map-right-num_' + (len - i) %>" + 
						"<td id=<%= numId %> class=<%=style.rightNum%> >0人</td>" + 
					"<% } else { %>" + 
						"<td></td>" + 
					"<% } %>	" + 					
				"</tr>" + 
				"<% } %>" + 
				
		//		<!-- x轴label -->
				"<tr>" + 
					"<td class='u-map-table-label' colspan=<%= yAxis.showType ? '2' : '0'  %> ></td>" + 
					"<% for(var i = 0, xdata=xAxis.data, xlen=xdata.length; i < xlen; i++) { %>" + 
						"<td class='u-map-table-label'><%= xdata[i] %></td>" + 
	           		"<% } %>" + 
					"<td class=<%= style.xaxisTitle %> rowspan=<%= showNum ? '2' : '0'  %> ><%= xAxis.title %></td>" + 
				"</tr>" + 
				
		//		<!-- x轴最下一行 -->
				"<tr>" + 
					"<td colspan=<%= yAxis.showType ? '2' : '0'  %> class='u-map-table_num'></td>" + 
					"<% for(var i = 0, xdata=xAxis.data, xlen=xdata.length; i < xlen; i++) { %>" + 
						"<% if(showNum) { %>" + 
							"<% var numId = 'u-map-bottom-num_' + (i + 1) %>" + 
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
		emptyMessage : '暂无数据。',
		//height : 300,
		width : 800,
		style : {
			styleClass : 'u-map-table',
			xaxisTitle : 'u-map-xaxis-title',
			yaxisTitle : 'u-map-yaxis-title',
			bottomNum : 'u-map-table_num',
			rightNum : 'u-map-right-label',
			cellPrefix : 'u-map-cell_',
			pointClass : 'u-point',
			leftLabelPrefix : 'u-map-left_label_' // 左侧label样式前缀
		},
		showNum : true,
		xAxis : {
			title : "",
			data : [],
			widths : []
		},
		yAxis : {
			title : "",
			data : [],
			showType : true,
			type : [{text : '观察 ',span : 2}, {text : '胜任',span : 3}, {text : '潜力',span : 2}]
		},
		/** 得到点的背景图片样式 */
		getPointClass : null, // function(point) {}
		/** 点相关配置 */
		point : {
			/** 点添加到地图上后触发 */
			afterCreate : null, // function(elem, data) {},
			/** 地图上点的数量发生变化时触发 */
			afterChange : null, //function(points) {},
			onClick : null, //function(elem, data) {},
			onHover : null, //function(elem, data) {},
			sort : function(p1, p2) { // 默认排序方法
				if(p1._x === p2._x) {
					return p1._y - p2._y;
				}
				return p1._x - p2._x;
			} 
		},
		/** 汇总点 */
		groupPoint : {
			afterCreate : null, // function(elem, points, groupInfo) {},
			onClick : null, //function(elem, points, groupInfo) {},
			onHover : null //function(elem, points, groupInfo) {},
		}
	};

	/* 地图全局计数器 */
	var g_talentMapCnt = 0;
	$.fn.talentMap = function(options) {
		var map = {
	
			_create : function(elem, options) {
				var m = this, e = m.element = $(elem);
				if(e.length === 0) {
					console && console.error(elem);
					throw "Html element doesn't exist.";
				}
				
				var p = m.options = $.extend(true, {}, defaults, options);
				m.points = [];
				m.lines = [];
				m.frontMap = e.children(p.styleClass);
				// 表格
				tpl.LEFT_DELIMITER = "<%";
				tpl.RIGHT_DELIMITER = "%>";
				var bottomTabHtml = tpl(mapTemplete, p);
				var bottomTab = m._table = $(bottomTabHtml).width(p.width).height(p.height);
				bottomTab.appendTo(e);				
				// loading
				m._loading = $("<div class='f-overlay' style='z-index:150;'><div class='f-overlay-txt'>请稍候...</div></div>");
				m._loading.appendTo(e);				
				e.width(bottomTab.width());
				m._initColumnWidth();				
				// 画布
				var html5Div = m._canvas = $("<div class='u-map-canvas'></div>");
				e.append(html5Div);
				m.html5 = new html5(html5Div.get()[0], {
					width : bottomTab.width(),
					height : bottomTab.height()
				});
				m._drawAxes();
				m.html5.render();
				// add point
				if(p.points && p.points.length) {
					m.points = p.points;
					m.addPoints(p.points);
					p.showNum && m.refreshPointCount();
				}
				// 事件
				p.point.onClick && m.element.unbind('click').on("click", ".u-point-wrapper", function(event) {
					var elem = $(this), data = m._findPointDataById(elem.attr("id"));
					data && p.point.onClick.call(m, elem, data);
				});
				p.point.onHover && m.element.unbind('mouseenter').on("mouseenter", ".u-point-wrapper", function(event) {
					var elem = $(this), data = m._findPointDataById(elem.attr("id"));
					data && p.point.onHover.call(m, elem, data);
				});
				p.groupPoint.onClick && m.element.on("click", ".u-point-group-wrapper", function(event) {
					var elem = $(this), inf = elem.data('ginfo');
					var points = m._grepGroupPoints(inf.x, inf.y, inf.direction);
					p.groupPoint.onClick.call(m, elem, points, inf)
				});
				p.groupPoint.onHover && m.element.on("mouseenter", ".u-point-group-wrapper", function(event) {
					var elem = $(this), inf = elem.data('ginfo');
					var points = m._grepGroupPoints(inf.x, inf.y, inf.direction);
					p.groupPoint.onHover.call(m, elem, points, inf);
				});
	
				//  point id生成器
				m._point_cnt = 0;
				m._cnt = g_talentMapCnt++;
				m._invalidPoints = []; // 无效点
				m._loading.hide();
				return m;
			},
			
			_grepGroupPoints : function(x, y, direction) {
				var m = this, p = m.options;
				var points = $.grep(m.points, function(p) {
					return p._x === x 
						&& p._y === y 
						&& p.direction === direction;
				});
				if (p.point && p.point.sort) {
					return points.sort(p.point.sort);
				}
				return points;
			},
			
			getOption : function() {
				var m = this, p = m.options;
				p.points = m.getAllPoints();
				return p;
			},
	
			/***
			 * 画线
			 * @param points 点数组
			 */
			drawLine : function(points) {
				if (!points || points.length < 2) {
					return '';
				}				
				var m = this;
				var lineId = this._drawLine(points);
				m.lines.push({
					id : lineId,
					points : points
				});
	
				m.html5.render();
				return lineId;
			},
	
			_drawLine : function(points, lineId, animation) {
				var m = this, xys = [];
				$.each(points, function(i, p) {
					if (!p.__id) {
						console && console.error('error:p.__id is null.');
						return;
					}
					var xy = m._getXY("#" + p.__id + " .u-point");
					xy && xys.push([xy.x + 8, xy.y + 8]);
				});
				return m.html5.drawLine(xys, lineId, animation);
			},
	
			deleteLine : function(lineId) {
				if (lineId) {
					this._deleteLineObject(lineId);
					this.html5.delShape(lineId);
					this.html5.render();
				}
			},
	
			_deleteLineObject : function(lineId) {
				var m = this;
				var lines = $.grep(m.lines, function(line) {
					return line.id !== lineId;
				});
				delete m.lines;
				m.lines = lines;
			},
			
			/***
			 * 根据x, y属性取得相应格子中的点.
			 * 如果不传prop就返回点对象{}， 否则返回指定属性数组
			 */
			getPoints : function(x, y, prop) {
				var m = this, p = m.options;
				var points = $.grep(m.points, function(p) {
					return p._x == x && p._y == y;
				});
				return !prop ? points : $.map(points, function(p) {
					return p[prop];
				});
			},
	
			grepPoints : function(prop, value) {
				var m = this, p = m.options;
				var points = $.grep(m.points, function(p) {
					return p[prop] === value;
				});
				if (p.point && p.point.sort) {
					return points.sort(p.point.sort);
				}
				return points;
			},
	
			getAllPoints : function() {
				return this.points;
			},
			
			_addPointData : function(point) {
				var m = this, p = m.options;
				if (!m._exists(point)) {
					point.__id = 'p_' + m._cnt + '_' + (m._point_cnt++);
					m.points.push(point);
				}
			},
	
			_createPointHtml : function(point) {
				var m = this, p = m.options;
				var html = $('<span class="u-point-wrapper"></span>');
				// 点
				var pointClass = p.getPointClass ? p.getPointClass(point) : p.style.pointClass;
				$("<span></span>").addClass(pointClass).appendTo(html);
				// text
				var text = point.text || '';
				html.append('<span class="u-point-label" title=' + text + '>' + text + '</span>');
				html.attr('id', point.__id);
				// 事件
				if(p.point && p.point.afterCreate) {
					setTimeout(function() {
						p.point.afterCreate(html, point);
					}, 0);
				}
				return html;
			},
			
			_exists : function(point) {
				if(!point.__id) {
					return false;
				}
				var exists = false;
				$.each(this.points, function(idx, p) {
					if(p.__id === point.__id) {
						exists = true;
						return false;
					}
				});
				return exists;
			},
			
			/**
			 * 清除地图上所有的覆盖物，包括点、线、汇总点
			 */
			clear : function() {
				var m = this, p = m.options;
				// clear points and lines
				m.points = [];
				m.lines = [];
				// clear points & group points
				m._table.find(".u-point-wrapper").remove();
				m._table.find(".u-point-group-wrapper").remove();
				// redraw
				p.showNum && m.refreshPointCount();
				m._redrawCanvas();
				// 还需要触发事件
				if(p.point.afterChange) {
					p.point.afterChange([]);
				}
				m._invalidPoints = [];
			},
	
			delPoint : function(point) {
				var m = this, p = m.options;
				// 删除元素
				var elem = $("#" + point.__id);
				elem.remove();
				m.points = $.grep(m.points, function(pp) {
					return pp.__id !== point.__id;
				});
				// 如果这个点在线中，把相关的线全删除
				$.each(m.lines, function(idx, line) {
					$.each(line.points, function(pidx, lp) {
						if (lp.__id === point.__id) {
							m._deleteLineObject(line.id);
							return false;
						}
					});
				});
				// 会变化大小，要重绘
				m._redrawCanvas();
				p.showNum && m.refreshPointCount();
				// 汇总模式也要变化数字
				if (m._groupMode) {
					m._refreshGroupMode();
				}
				// 删除标记属性
				delete point.__id;
				delete point._x;
				delete point._y;
				// 还需要触发事件
				if(p.point.afterChange) {
					var ps = m.getAllPoints();
					p.point.afterChange(ps);
				}
				m._toggleEmptyMessage();
			},
			//批量删除点
			delPoints : function(points){ //add by lkliao
				var m = this, p = m.options;
				//删除元素
				$.each(points,function(idx,point){
					 var elem = $("#" + point.__id);
					 elem.remove();
				});
				var pointIds = $.map(points,function(p){
					return p.__id;
				});
				m.points = $.grep(m.points, function(pp) {
					return $.inArray(pp.__id,pointIds) == -1;
				});
				
				// 如果这个点在线中，把相关的线全删除
				$.each(m.lines, function(idx, line) {
					$.each(line.points, function(pidx, lp) {
						if($.inArray(lp.__id,pointIds)!=-1){
							m._deleteLineObject(line.id);
						} 
					});
				});
				
				// 会变化大小，要重绘
				m._redrawCanvas();
				p.showNum && m.refreshPointCount();
				// 汇总模式也要变化数字
				if (m._groupMode) {
					m._refreshGroupMode();
				}
				// 删除标记属性
				$.each(points,function(idx,point){
					delete point.__id;
					delete point._x;
					delete point._y;
				});
				// 还需要触发事件
				if(p.point.afterChange) {
					var ps = m.getAllPoints();
					p.point.afterChange(ps);
				}
				m._toggleEmptyMessage();
				
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
			addPoint : function(point) {
				var m = this, p = m.options;
				if (!point) {
					m._toggleEmptyMessage();
					return;
				}
				// 找出是哪个单元格
				var x = point.x || $.inArray(point.xLabel, p.xAxis.data) + 1;
				var y = point.y || $.inArray(point.yLabel, p.yAxis.data) + 1;
				if(m._isValidXy(x, y) === false) {
					m._invalidPoints.push(point);
					console && console.info(point);
					return;
				}			
				var cell = m._findCell(x, y);
				// 记住点在哪个上面
				point._x = x;
				point._y = y;
				m._addPointData(point);
				// 加到格子中对应的区域
				if(!m._groupMode) {
					var html = m._createPointHtml(point);
					var selector = m._getPointSelector(point);
					cell.children("." + selector).append(html);
				} else {
					m._refreshGroupMode();
				}
				// 加点会变化大小，要重绘
				m._redrawCanvas();
				// 如果显示人数统计，还需要刷新人数
				p.showNum && m.refreshPointCount();
				// 还需要触发事件
				if(p.point.afterChange) {
					var ps = m.getAllPoints();
					p.point.afterChange(ps);
				}
				
				m._toggleEmptyMessage();
			},
	
			addPoints : function(points) {
				var m = this, p = m.options;
				if(!points || !points.length) {
					m._toggleEmptyMessage();
					return;
				}
				points.length > 30 && m.showLoading();
				// 先把所有的cell查出来
				var cellAreas = {}; // key x_y_selector
				function getArea(x, y, selector) {
					var key = x  + '_' + y + selector;
					if(undefined == cellAreas[key]) {
						var cell = m._findCell(x, y);
						cellAreas[key] = cell.children("." + selector);
					}
					return cellAreas[key];
				}
				
				var getPointClass = m._getPointSelector;
				$.each(points, function(idx, point) {
					var x = point.x || $.inArray(point.xLabel, p.xAxis.data) + 1;
					var y = point.y || $.inArray(point.yLabel, p.yAxis.data) + 1;
					if(m._isValidXy(x, y) === false) {
						m._invalidPoints.push(point);
						return;
					}
					// 记住点在哪个上面
					point._x = x;
					point._y = y;
					// 加到格子中对应的区域
					m._addPointData(point);
					// 加到格子中对应的区域
					//jxzhang
					var html = m._createPointHtml(point);
					var selector = getPointClass(point);
					getArea(x, y, selector).append(html);
					if(m._groupMode) m._refreshGroupMode();
				});
				// 加点会变化大小，要重绘
				m._redrawCanvas();
				// 如果显示人数统计，还需要刷新人数
				p.showNum && m.refreshPointCount();
				// 还需要触发事件
				if(p.point.afterChange) {
					var ps = m.getAllPoints();
					p.point.afterChange(ps);
				}
				m.hideLoading();
				m._toggleEmptyMessage();
			},
			
			/**
			 * 是否有效的点 
			 */
			_isValidXy : function(x, y) {
				var m = this, p = m.options;
				var v = x > 0 && y > 0 && x <= p.xAxis.data.length && y <= p.yAxis.data.length;
				return v;
			},
	
			_initColumnWidth : function() {
				var m = this, p = m.options, e = m._table;
				var gridWidth = e.width() - e.find(".u-map-yaxis-title:first").width() - e.find(".u-map-xaxis-title:first").width();
	
				var widths = [];
				if (p.xAxis.widths && p.xAxis.widths.length) {
					$.each(p.xAxis.widths, function(idx, w) {
						widths.push(w * gridWidth);
					});
				} else {
					var len = p.xAxis.data.length, w = gridWidth / len;
					for (var i = 0; i < len; i++) {
						widths.push(w);
					}
				}
	
				var trs = this.element.find("tr");
				$.each(trs, function(idx, tr) {
					var tds = $(tr).children(".u-map-grid");
					$.each(tds, function(i, td) {
						$(td).width(widths[i]);
					});
				});
			},
	
			_findPointDataById : function(id) {
				var ret = null;
				$.each(this.points, function(i, p) {
					if (p.__id === id) {
						ret = p;
						return false;
					}
				});
				return ret;
			},
			
			_getPointSelector : function(point) {
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
	
			_findCell : function(x, y) {
				var m = this, p = this.options;
				return m._table.find("." + p.style.cellPrefix + x + '_' + y);
			},
	
			/**  画坐标轴 */
			_drawAxes : function() {
				var m = this;
				// 绩效排名
				var xTitleTd = m._table.find('.u-map-xaxis-title:first');
				var leftTopXy = m._getXY(xTitleTd);
				var endX = leftTopXy.x, startY = leftTopXy.y + 1;
				// 能力等级
				var yTitleTd = m._table.find('.u-map-yaxis-title:first');
				var rightBottomXy = m._getXY(yTitleTd);
				var endY = rightBottomXy.y + yTitleTd.height(), startX = rightBottomXy.x + yTitleTd.width();
				// x轴
				m.html5.drawHLine(startY, 0, endX + 12);
				// y轴
				m.html5.drawVLine(startX, startY, endY - 12);
			},
	
			_getXY : function(elem) {
				var eo = this.element.offset();
				var po = $(elem).offset();
				return po ? {
					x : po.left - eo.left,
					y : po.top - eo.top
				} : null;
			},
	
			/**
			 * 重绘画布
			 */
			_redrawCanvas : function(animation) {
				var m = this;
				// 画布大小
				m._canvas.height(m._table.height());
				m.html5.clear();
				m.html5.resize();
				// 重画线
				$.each(m.lines, function(idx, line) {
					m._drawLine(line.points, line.id, animation);
				});
				// 重线坐标轴
				m._drawAxes();
				m.html5.render();
			},
	
			/** 显示/隐藏点的label  */
			toggleLabel : function() {
				this.element.toggleClass('u-map-hideText');
				this._redrawCanvas();
			},
			
			_refreshGroupMode : function() {
				var m = this, p = m.options;
				// 删除汇总
				m._table.find(".u-point-group-wrapper").remove();
				m.toggleGroupMode(true);
			},
	
			/** 切换汇总模式 */
			toggleGroupMode : function(groupMode) {
				var m = this, p = m.options;
				m.showLoading();
				if (undefined === groupMode) {
					m._groupMode = !m._groupMode;
				} else {
					m._groupMode = groupMode;
				}
				//  汇总模式
				if (m._groupMode) {
					// 把所有的点去掉
					m._table.find(".u-point-wrapper").remove();
					// 统计出各单元格人数
					// 初始化人数map
					var countMap = {};
					// x_y_direction
					var xlen = p.xAxis.data.length, ylen = p.yAxis.data.length;
					for (var x = 1; x <= xlen; x++) {
						for (var y = 1; y <= ylen; y++) {
							countMap[x + '_' + y + 'up'] = 0;
							countMap[x + '_' + y + 'down'] = 0;
							countMap[x + '_' + y + 'left'] = 0;
						};
					};
					$.each(m.points, function(idx, point) {
						countMap[point._x + '_' + point._y + point.direction] += 1;
					});
					// 显示汇总人数
					for (var x = 1; x <= xlen; x++) {
						for (var y = 1; y <= ylen; y++) {
							var cell = m._findCell(x, y);
							m._addGroupPointHtml(cell, countMap[x + '_' + y + 'up'], x, y, 'up');
							m._addGroupPointHtml(cell, countMap[x + '_' + y + 'left'], x, y, "left");
							m._addGroupPointHtml(cell, countMap[x + '_' + y + 'down'], x, y, "down");
						};
					};
				} else {
					// 删除汇总
					m._table.find(".u-point-group-wrapper").remove();
					m.addPoints(m.points);
				}
				// 也要重画
				this._redrawCanvas();
				m.hideLoading();
			},
	
			_addGroupPointHtml : function(cell, cnt, x, y, direction) {
				// 等于0就不显示
				if (cnt) {
					var m = this, p = m.options;
					var html = $("<div class='u-point-group-wrapper'><span class='u-point-group'></span></div>");
					var cntHtml = $("<span class='u-point-group-arrow'></span>");
					var ginfo = {
						x : x,
						y : y,
						direction : direction
					};
					html.find(".u-point-group").text(cnt);
					html.data('ginfo', ginfo);
					html.append(cntHtml);
					
					var classSellector = '.u-point-l:first';
					var title = '';
					if(direction === 'up') {
						classSellector = ".u-point-u:first";
						title = '发展趋势：箭头向上';
					} else if(direction === 'down') {
						classSellector = ".u-point-d:first";
						title = '发展趋势：箭头向下';
					}  else {
						title = '发展趋势：箭头持平';
					}
					html.attr('title', title);
					cell.children(classSellector).append(html);
					//  触发创建后事件
					if(p.groupPoint && p.groupPoint.afterCreate) {
						var points = m._grepGroupPoints(x, y, direction);
						p.groupPoint.afterCreate(html, points, ginfo);
					}
				}
			},
	
			refreshPointCount : function() {
				var m = this, p = m.options;
				var xlen = p.xAxis.data.length, ylen = p.yAxis.data.length;
				// 初始化
				var xcounts = [], ycounts = [];
				for (var i = 0; i < xlen; i++) {
					xcounts.push(0);
				};
				for (var i = 0; i < ylen; i++) {
					ycounts.push(0);
				};
				// 计算数量
				$.each(m.points, function(idx, point) {
					xcounts[point._x - 1] += 1;
					ycounts[point._y - 1] += 1;
				});
				// 填充数量
				for (var i = 0; i < ylen; i++) {
					$('#u-map-right-num_' + (i + 1)).text(ycounts[i] + "人");
				};
				for (var i = 0; i < xlen; i++) {
					$('#u-map-bottom-num_' + (i + 1)).text(xcounts[i] + "人");
				};
			},
			
			/** 重画线 */
			redrawLines : function() {
				this._redrawCanvas(true);
			},
			
			/**
			 * 无效的点
			 */
			getInvalidPoints : function() {
				return this._invalidPoints || [];
			},

			showLoading : function() {
			},
			hideLoading : function() {
			},

			_toggleEmptyMessage : function() {
				var m = this, p = m.options;
				m._clearEmptyMessage();
				if (!m.points || !m.points.length) {
					m._showEmptyMessage();
				}
			},
			
			_showEmptyMessage : function() {
				var m = this, p = m.options;
				var xTitleTd = m._table.find('.u-map-xaxis-title:first');
				var x = (m._table.width()) / 2 ;
				var y = (m._table.height() - xTitleTd.height()) / 2;
				m._emptyMessageId = m.html5.drawText(p.emptyMessage, x, y);
				m.html5.render();
			},
			
			_clearEmptyMessage : function() {
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
