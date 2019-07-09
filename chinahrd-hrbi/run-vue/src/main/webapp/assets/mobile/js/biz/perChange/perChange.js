require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie', 'echarts/chart/gauge',
    'jgGrid', 'underscore', 'utils',  "appBase","chartTooltip"], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var urls = {
    		getPageTopData: webRoot + '/mobile/perChange/getPageTopData',//获取顶部两块区域的数据
            queryPreStarCountByMonth: webRoot + '/mobile/perChange/queryPreStarCountByMonth',//获取绩效变化
            getPreChangeCountData: webRoot + '/mobile/perChange/getPreChangeCountData',//获取绩效变化
            queryPerformanceDetailUrl: webRoot + '/mobile/perChange/queryPerformanceDetail.do',//查询绩效详情
    };
    var ecConfig = require('echarts/config');
    var reqOrganId=$("#reqOrganId").val();
    var yearMonth = $('#yearMonth').val();
    var lastYearMonth = $('#lastYearMonth').val();
    var beforeLastYearMonth = $('#beforeLastYearMonth').val();
    var height;
    function loadLSL(){
         height=getWindowHeight()-102;
         $("#performanceDetailPanel").width($(window).width());
         $("#performanceDetailPanel").height(height);
    }
    $(window).bind(resizeEvent,function(){
    	loadLSL();
    });
    loadLSL();
    
    $('.tabPanel div[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    	  var targetAreaId = $(e.target).attr('aria-controls');
    	if(targetAreaId=="jxfbPanel"){
    		preDistributionObj.resize();
    	}else if(targetAreaId=="jxbhPanel"){
//    		inferiorChart.resize();
    	}else if(targetAreaId=="jxycPanel"){
//    		accordDismissPieObj.resize();
    	}else if(targetAreaId=="jxmxPanel"){
    		tableGrid.resize();
    	}
    });

    function initTopBlock(organizationId, yearMonth) {
    	if(yearMonth==""){
    		$(".changeHight").text('无');
        	$(".changeLow").text('无');
        	return;
    	}
        $.get(urls.getPageTopData, {
            organizationId: organizationId, yearMonth: yearMonth
        }, function (data) {
            if (!$.isEmptyObject(data)) {
            	$(".changeHight").text(data.highCount);
            	$(".changeLow").text(data.lowCount);
            } else {
            	$(".changeHight").text('无');
            	$(".changeLow").text('无');
            } 
        });

    }
    $("#allEmp").children().on(clickEvent,function(){
		 var _t=this;
   	     preDistributionObj.clickTab($(this).attr("data"));
     	 setBtnClass(_t);
      });

    
    //绩效分布
    var preDistributionObj = {
        joinEmpCount: 0,
        chartId:"preDisChart",
        starChartOption: null,
        chartObj:null,
        recordTotal: {},//记录已查询的总人数
        init: function (organizationId, yearMonth, empType) {
        	if(yearMonth==""){
        		showEmptyTip($("#"+this.chartId));
            	return;
        	}
            var self = this;
            this.first = false;
            if (yearMonth != null && yearMonth != "") {
                self.yearMonth = yearMonth;
                self.organizationId = organizationId;
                self.initChart(organizationId, yearMonth, empType);
            } else {
                return;
            }
        },initChart:function(organizationId,yearMonth, empType) {
        	var self=this;
        	$.get(urls.queryPreStarCountByMonth, {
                organizationId: organizationId, yearMonth: yearMonth
            }, function (data) {
                if (!$.isEmptyObject(data)) {
                	self.count=data.count;
                    var option = self.starChart(data.detail, empType);
                    self.starChartSetOption(option);
                } else {
                    return;
                }
            });
        },
        starChart: function (data, empType) {
        	
            var self = this;
            var option = {
            	animation:animation,
                grid: {
                    x: 50,
                    x2: 60,
                    y: 30,
                    y2: 30,
                    borderWidth: 0
                },
                legend: {
                    data: ['管理者', '员工'],
                    selectedMode:false,
                    itemWidth:10,
                    itemHeight:11,
                    y: 'top',
                    x: 'right',
                    textStyle: {
                    	fontSize: 12,
                    	fontStyle: 'normal',
                    	fontWeight: 'normal',

                    	},
                },
                xAxis: [
                    {
                        type: 'value',
                        show: false,
                        splitLine: {show: false},
                        axisLine: {show: false}
                    }
                ],
                yAxis: [
                    {
                        axisLabel: {
                            textStyle: {color: '#666666', fontSize: 15},
                            formatter: function (value) {
                                value = '' + value;
                                var name = value.substr(value.indexOf('_') + 1, value.length);
                                return name;
                            }
                        },
                        type: 'category',
                        data: [],
                        splitLine: {show: false},
                        axisLine: {show: false},
                        axisTick: {show: false}
                    }
                ],
                series: [
                    {
                        name: '管理者',
                        type: 'bar',
                        stack: '总量',
                        itemStyle: {normal: {color: '#8DC152'}},
                        data: [],
                        barWidth: barWidth,
                        clickable:false
                    },
                    {
                        name: '员工',
                        type: 'bar',
                        stack: '总量',
                        clickable:false,
                        itemStyle: {
                            normal: {
                                color: barColor, label: {
                                    show: true,
                                    formatter: function (param) {
                                        var name = param.name;
                                        var index;  //y轴序列顺序
                                        for (var i = 0; i < option.yAxis[0].data.length; i++) {
                                            if (name == option.yAxis[0].data[i]) {
                                                index = i;
                                                break;
                                            }
                                        }
                                        var total = param.value += option.series[0].data[index];
                                        if (self.joinEmpCount != null && self.joinEmpCount != "") {
                                            return total + '人(' + Math.round(total / self.joinEmpCount * 100) + '%)';
                                        } else {
                                            return total + '人(' + 0 + '%)';
                                        }
                                    },
                                    textStyle: {color: '#1E1E1E'}
                                }
                            }
                        },
                        data: [],
                        barWidth: barWidth
                    }
                ]
            };
            var num = 0;
            var yDataArr = [];
            var seriesDataArr1 = [];
            var seriesDataArr2 = [];
            for (var i = 0; i < data.length; i++) {
            	   num += data[i].joinCount;
                yDataArr[i] = data[i].performanceName;
                seriesDataArr1[i] = data[i].mgrCount;
                seriesDataArr2[i] = data[i].notMgrCount;
            }
            self.joinEmpCount = num;
            $('#joinCount').text(self.joinEmpCount);
            var orgEmpCount = data.count;
            $('#notJoinCount').text(self.count.total - self.joinEmpCount);

            option.yAxis[0].data = yDataArr;
            option.series[0].data = seriesDataArr1;
            option.series[1].data = seriesDataArr2;
            self.starChartOption = option;
            return option;
        },
        starChartSetOption: function (option) {
            this.chartObj = echarts.init(document.getElementById(this.chartId));
            this.chartObj.setOption(option);
            $('#' + this.chartId).chartTooltip({
				chart : this.chartObj,
				formatter : function(i, v, p) {
					console.log(v)
					var cols = [  ];
					$.each(v.data,function(i,o){
						cols.push({
							name : o.name,
							value : (o.value) ,
							unit:"人"
						});
					});
					
					return {
						title : v.name,
						cols : cols
					};
				}
			});
        },
        //点击tab页签
        clickTab: function (type) {
            var self = this;
            var tempOption = $.extend(true, {}, self.starChartOption);
            if ($.isEmptyObject(tempOption)) {
                return;
            }
            
            var total=self.count.total;
            var joinCount = self.joinEmpCount;
            if (type == 1) {
            	joinCount = 0
            	total=self.count.managerCount;
                //删除员工的图例
                tempOption.legend.show = false;
                tempOption.series[0].itemStyle.normal.label = {
                    show: true,
                    formatter: function (i) {
                        joinCount += i.value;
                        return i.value + '人';
                    },
                    textStyle: {color: '#1E1E1E'}
                };
                tempOption.legend.data.splice(1, 1);
                tempOption.series.splice(1, 1);
            } else if (type == 2) {
            	joinCount = 0;
            	total=self.count.empCount;
                tempOption.legend.data[0] = '员工';
                tempOption.legend.show = false;
                tempOption.legend.data.splice(1, 1);
                tempOption.series[0] = tempOption.series[1];
                //不显示label
                tempOption.series[0].itemStyle.normal.color = 'rgb(40,137,204)';
                tempOption.series[0].itemStyle.normal.label = {
                    show: true,
                    formatter: function (i) {
                        joinCount += i.value;
                        return i.value + '人';
                    },
                    textStyle: {color: '#1E1E1E'}
                };
                tempOption.series.splice(1, 1);
            }

            self.starChartSetOption(tempOption);
            $('#joinCount').text(joinCount);
            $('#notJoinCount').text(total -joinCount);
        },
        resize: function () {
            var self = this;
            if (self.chartObj != null) {
                self.chartObj.resize();
            }
        }
    };

    var getPixelRatio = function(context) {
    	  var backingStore = context.backingStorePixelRatio ||
    	    context.webkitBackingStorePixelRatio ||
    	    context.mozBackingStorePixelRatio ||
    	    context.msBackingStorePixelRatio ||
    	    context.oBackingStorePixelRatio ||
    	    context.backingStorePixelRatio || 1;
    	   return (window.devicePixelRatio || 1) / backingStore;
    	};
    
    
    
    //绩效结果变化趋势 配置对象
    var preChangeOption = {
        gap: 20, //间隔
        jianjiao: 30, //顶部和底部尖角
        width: $(window).width(),
        height: 200,
        x: 120,//x轴的起点
        topColor: '#1089F4',
        middleColor: '#FEC047',
        bottomColor: '#FF5B5A',
        leftFontStyle: {
            fontSize: 30,
            fontColor: '#f0f0f0',
            textAlign: 'center',
            textBaseline: 'middle'
        },
        rigthFontStyle: {
            fontSize: 24,
            fontColor: '#666666',
            lineColor: '#333333',
            textAlign: 'left',
            textBaseline: 'middle'
        },
        splitLineW: 102
    };

    //绩效结果变化趋势HTML5绘制图形
    var PreChange = function (mainId, obj, option) {
        this.mainId = mainId;
        this.obj = obj;
        this.option = option;
        this.middleY = this.option.width / 2;
    };

    //创建画布
    PreChange.prototype.createCanvas = function () {
        //清空div
        $('#' + this.mainId).html('');

        var canvas = document.createElement('canvas');
        $('#' + this.mainId).append(canvas);
        this.setCanvasPos(canvas);
        return canvas.getContext('2d');
    };

    //设置画布样式
    PreChange.prototype.setCanvasPos = function (canvas) {
    	  canvas.setAttribute('width', this.option.width*2);
          canvas.setAttribute('height', this.option.height*2);
          $(canvas).css('width', this.option.width);
          $(canvas).css('height', this.option.height);
    };

    //画上面的区域
    PreChange.prototype.drawTopArea = function (ctx, x, y, w, h) {
        ctx.fillStyle = this.option.topColor;
        ctx.strokeStyle = this.option.topColor;
        ctx.beginPath();
        ctx.moveTo(x, y);//找到起始点.
        ctx.lineTo(x + w / 2, y - this.option.jianjiao);//画左上斜线
        ctx.lineTo(x + w, y);//画横线 往右下
        ctx.lineTo(x + w, h + y);//画竖线  往下
        ctx.lineTo(x, h + y);//画左横线
        ctx.lineTo(x, y);//画向上的线
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
    };

    //画中间的区域
    PreChange.prototype.drawMiddleArea = function (ctx, x, y, w, h) {
        //75 172 178
        ctx.fillStyle = this.option.middleColor;
        ctx.strokeStyle = this.option.middleColor;
        ctx.beginPath();
        ctx.rect(x, y, w, h);
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
    };

    //话底部的区域
    PreChange.prototype.drawBottomArea = function (ctx, x, y, w, h) {
        ctx.fillStyle = this.option.bottomColor;
        ctx.strokeStyle = this.option.bottomColor;
        ctx.beginPath();
        ctx.moveTo(x, y);//找到起始点.
        ctx.lineTo(x + w, y);//画横线 往右
        ctx.lineTo(x + w, h + y);//画竖线  往下
        ctx.lineTo(x + w / 2, h + y + this.option.jianjiao);//画左下斜线
        ctx.lineTo(x, h + y);//画左上斜线
        ctx.lineTo(x, y);//画向上的线
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
    };
    //画文字
    PreChange.prototype.drawFont = function (ctx, x, y, str, fontStyle) {
        ctx.beginPath();
        if (fontStyle.fontColor != null) {
            ctx.fillStyle = fontStyle.fontColor;
        }
        if (fontStyle.fontSize != null) {
            ctx.font = fontStyle.fontSize + 'px 黑体';
        }
        if (fontStyle.textBaseline != null) {
            ctx.textBaseline = fontStyle.textBaseline;
        }
        if (fontStyle.textAlign != null) {
            ctx.textAlign = fontStyle.textAlign;
        }
        ctx.fillText(str, x, y);
    };

    //画右边的线
    PreChange.prototype.drawRightLine = function (ctx, x, y, w) {
        ctx.beginPath();
        ctx.strokeStyle = this.option.rigthFontStyle.lineColor;
        ctx.moveTo(x+10, y);
        ctx.lineTo(x + w, y);//画线
        ctx.stroke();
    };
    //画右边的线
    PreChange.prototype.drawRightConnectLine = function (ctx, x, y, w) {
        ctx.beginPath();
        ctx.strokeStyle = this.option.rigthFontStyle.lineColor;
        ctx.moveTo(x, y);
        ctx.lineTo(x, w);//画线
        ctx.stroke();
    };
    PreChange.prototype.init = function () {
        //获取中心位置
        var ctx = this.createCanvas();
        this.ratio = getPixelRatio(ctx);
        var w = this.option.width / 4;
        var x = this.option.x;
        var gap = this.option.gap;
        var obj = this.obj;
        if (obj == null) {
            obj = new Object();
            obj.rise = 0;
            obj.equal = 0;
            obj.down = 0;
        }
        var canvasCenterY = this.option.height / 2*2;
        //中间部分
        var mh = 40*2;//高
        var my = canvasCenterY;//获取中间位置
        my = my - mh / 2;//开始位置 左上角
        this.drawMiddleArea(ctx, x, my, w*2, mh);

        //底部b
        var bh = 30*2;//高
        var by = my + mh + gap;//中间的开始位置+中间部分的高+间隔
        this.drawBottomArea(ctx, x, by, w*2, bh);

        //顶部
        var th = 30*2;//高
        var ty = my - th - gap;//中间的开始位置+中间部分的高+间隔
        this.drawTopArea(ctx, x, ty, w*2, th);

        //写文字  middlearea  top bootom
        //Y的部分 还可以加上文字的
        var allCenterX = x + w / 2*2;
        var txtTopY = ty + th / 2-5;
        var txtMidY = canvasCenterY;
        var txtBotY = by + bh / 2+5;
        //比例
        var count = obj.rise + obj.down + obj.equal;
        var pRise = obj.rise / count;
        var pDown = obj.down / count;
        var pNoChange = 1 - pRise - pDown;

        pNoChange = isNaN(pNoChange) ? 0 : pNoChange;
        pRise = isNaN(pRise) ? 0 : pRise;
        pDown = isNaN(pDown) ? 0 : pDown;

        pRise = Math.round(pRise * 100) + '%';
        pDown = Math.round(pDown * 100) + '%';
        pNoChange = Math.round(pNoChange * 100) + '%';

        this.drawFont(ctx, allCenterX, txtMidY, pNoChange, this.option.leftFontStyle);
        this.drawFont(ctx, allCenterX, txtTopY, pRise, this.option.leftFontStyle);
        this.drawFont(ctx, allCenterX, txtBotY, pDown, this.option.leftFontStyle);

        //写右边的文字
        var allTextX = x + w*2 + this.option.splitLineW+10;
        this.drawFont(ctx, allTextX, txtTopY, '有所进步，' + obj.rise + '人', this.option.rigthFontStyle);
        this.drawFont(ctx, allTextX, txtMidY, '维持现状，' + obj.equal + '人', this.option.rigthFontStyle);
        this.drawFont(ctx, allTextX, txtBotY, '出现下滑，' + obj.down + '人', this.option.rigthFontStyle);

        //画右边的线
        var allLineX = x + w*2;
        this.drawRightLine(ctx, allLineX, txtMidY, this.option.splitLineW);
        this.drawRightLine(ctx, allLineX, txtTopY, this.option.splitLineW);
        this.drawRightLine(ctx, allLineX, txtBotY, this.option.splitLineW);

        this.drawRightConnectLine(ctx, allLineX+this.option.splitLineW, txtTopY, txtBotY);
    };
    var arrYearMonths;

    function initArrYearMonths() {
        if (yearMonths != null && yearMonths != "") {
            arrYearMonths = yearMonths.split(',');
        }
    }

    //绩效异常 配置对象
    var preUnusualOption = {
        gap: 20, //间隔
        jianjiao: 30, //顶部和底部尖角
        width: $(window).width(),
        height: 200,
        x: 120,//x轴的起点
        h:110,
        topColor: '#1089F4',
        bottomColor: '#FF5B5A',
        leftFontStyle: {
            fontSize: 30,
            fontColor: '#ffffff',
            textAlign: 'center',
            textBaseline: 'middle'
        },
        rigthFontStyle: {
            fontSize: 24,
            fontColor: '#666666',
            lineColor: '#333333',
            textAlign: 'left',
            textBaseline: 'middle'
        },
        splitLineW: 102
    };

    //绩效异常HTML5绘制图形
    var PreUnusual = function (mainId, obj, option) {
        this.mainId = mainId;
        this.obj = obj;
        this.option = option;
        this.middleY = this.option.width / 2;
    };

    //创建画布
    PreUnusual.prototype.createCanvas = function () {
        //清空div
        $('#' + this.mainId).html('');

        var canvas = document.createElement('canvas');
        $('#' + this.mainId).append(canvas);
        this.setCanvasPos(canvas);
        return canvas.getContext('2d');
    };

    //设置画布样式
    PreUnusual.prototype.setCanvasPos = function (canvas) {
        canvas.setAttribute('width', this.option.width*2);
        canvas.setAttribute('height', this.option.height*2);
        $(canvas).css('width', this.option.width);
        $(canvas).css('height', this.option.height);
        // 减少reflow
//			var canvasStyle = 'width:' + this.option.width + 'px;height:' + this.option.height + 'px;position:absolute;left:0px;top:' + 10 px;
//			canvas.style.cssText += canvasStyle;
    };

    //画上面的区域
    PreUnusual.prototype.drawTopArea = function (ctx, x, y, w, h) {
        ctx.fillStyle = this.option.topColor;
        ctx.strokeStyle = this.option.topColor;
        ctx.beginPath();
        ctx.moveTo(x, y);//找到起始点.
//        ctx.lineTo(x - 20, y);//画横线 往左
        ctx.lineTo(x , y);//画横线 往左
        ctx.lineTo(x + w / 2, y - this.option.jianjiao);//画左上斜线
//        ctx.lineTo(x + w + 20, y);//画右下斜线 往右
        ctx.lineTo(x + w , y);//画右下斜线 往右
        ctx.lineTo(x + w, y);//画横线 往左
        ctx.lineTo(x + w, h + y);//画竖线  往下
        ctx.lineTo(x, h + y);//画左横线
        ctx.lineTo(x, y);//画向上的线
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
    };

    //话底部的区域
    PreUnusual.prototype.drawBottomArea = function (ctx, x, y, w, h) {
        ctx.fillStyle = this.option.bottomColor;
        ctx.strokeStyle = this.option.bottomColor;
        ctx.beginPath();
        ctx.moveTo(x, y);//找到起始点.
        ctx.lineTo(x + w, y);//画横线 往右
        ctx.lineTo(x + w, h + y);//画竖线  往下
//        ctx.lineTo(x + w + 20, h + y);//画横线 往右
        ctx.lineTo(x + w , h + y);//画横线 往右
        ctx.lineTo(x + w / 2, h + y + this.option.jianjiao);//画左下斜线
//        ctx.lineTo(x - 20, h + y);//画左上斜线
        ctx.lineTo(x, h + y);//画左上斜线
        ctx.lineTo(x, h + y);//画横线 往右
        ctx.lineTo(x, y);//画向上的线
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
    };
    //画文字
    PreUnusual.prototype.drawFont = function (ctx, x, y, str, fontStyle) {
        ctx.beginPath();
        if (fontStyle.fontColor != null) {
            ctx.fillStyle = fontStyle.fontColor;
        }
        if (fontStyle.fontSize != null) {
            ctx.font = fontStyle.fontSize + 'px 黑体';
        }
        if (fontStyle.textBaseline != null) {
            ctx.textBaseline = fontStyle.textBaseline;
        }
        if (fontStyle.textAlign != null) {
            ctx.textAlign = fontStyle.textAlign;
        }
        ctx.fillText(str, x, y);
    };

    //画右边的线
    PreUnusual.prototype.drawRightLine = function (ctx, x, y, w) {
        ctx.beginPath();
        ctx.strokeStyle = this.option.rigthFontStyle.lineColor;
        ctx.moveTo(x+10, y);
        ctx.lineTo(x + w, y);//画线
        ctx.stroke();
    };
    //画右边的线
    PreUnusual.prototype.drawRightConnectLine = function (ctx, x, y, w) {
        ctx.beginPath();
        ctx.strokeStyle = this.option.rigthFontStyle.lineColor;
        ctx.moveTo(x, y);
        ctx.lineTo(x, w);//画线
        ctx.stroke();
    };
    PreUnusual.prototype.init = function () {
        //获取中心位置
        var ctx = this.createCanvas();
        var w = this.option.width / 4;
        var x = this.option.x;
        var gap = this.option.gap;
        var obj = this.obj;
        if (obj == null) {
            obj = new Object();
            obj.rise = 0;
            obj.equal = 0;
            obj.down = 0;
        }
        var canvasCenterY = this.option.height / 2*2;

        //底部b
        var bh = this.option.h;//高
        var by = canvasCenterY + gap;//中间的开始位置+间隔
        this.drawBottomArea(ctx, x, by, w*2, bh);

        //顶部
        var th = this.option.h;//高
        var ty = canvasCenterY - bh;//中间的开始位置+底部的高度
        this.drawTopArea(ctx, x, ty, w*2, th);

        //写文字  op bootom
        //Y的部分 还可以加上文字的
        var allCenterX = x + w / 2*2;
        var txtTopY = ty + th / 2-10;
        var txtBotY = by + bh / 2+10;
        //比例
        var pRise = obj.rise / obj.equal;
        var pDown = obj.down / obj.equal;

        pRise = isNaN(pRise) ? 0 : pRise;
        pDown = isNaN(pDown) ? 0 : pDown;

        pRise = Math.round(pRise * 100) + '%';
        pDown = Math.round(pDown * 100) + '%';

        this.drawFont(ctx, allCenterX, txtTopY, pRise, this.option.leftFontStyle);
        this.drawFont(ctx, allCenterX, txtBotY, pDown, this.option.leftFontStyle);

        //写右边的文字
        var allTextX = x + w*2 + this.option.splitLineW+10;
        this.drawFont(ctx, allTextX, txtTopY, '飞速提升，' + obj.rise + '人', this.option.rigthFontStyle);
        this.drawFont(ctx, allTextX, txtBotY, '加速跌落，' + obj.down + '人', this.option.rigthFontStyle);

        //画右边的线
        var allLineX = x + w*2;
        this.drawRightLine(ctx, allLineX, txtTopY, this.option.splitLineW);
        this.drawRightLine(ctx, allLineX, txtBotY, this.option.splitLineW);

        //画右边的连线
        var allLineX = x + w*2;
        this.drawRightConnectLine(ctx, allLineX+this.option.splitLineW, txtTopY, txtBotY);
    };


    //绩效结果变化和绩效异常
    var preChangeObj = {
        organizationId: null,
        yearMonths: null,
        first: true,
        init: function (organizationId, yearMonth,lastYearMonth) {
        	if(lastYearMonth==""){
        		$.each($(".mainArea"), function () {
        			showEmptyTip($(this).children());
                });
        		return;
        	}
        	
            var self = this;
            self.organizationId = organizationId;
            self.yearMonth = yearMonth;
            self.lastYearMonth = lastYearMonth;
            
            self.getPreChangeData();
            this.first = false;
        },
        getPreChangeData: function () {
            var self = this;
            $.each($(".mainArea"), function () {
            	showEmptyTip($(this).children());
            });
            $.ajax({
                url: urls.getPreChangeCountData,
                data: {
                    organizationId: self.organizationId, yearMonth: self.yearMonth,lastYearMonth:self.lastYearMonth
                },
                dataType: 'json',
                success: function (data) {
                    $.each($(".mainArea"), function () {
                    	removeEmptyTip($(this).children());
                    });
                    if (!$.isEmptyObject(data)) {
                        if (!$.isEmptyObject(data.change)) {
                            self.setPreChangeData(data.change);
                        } else {
                            $.each($(".mainArea"), function (i) {
                                if (i == 0) {
                                	showEmptyTip($(this).children());
                                }
                            });
                        }
                        if (!$.isEmptyObject(data.bigChange)) {
                            self.setPreExceptionData(data.bigChange);
                        } else {
                            $.each($(".mainArea"), function (i) {
                                if (i == 1) {
                                	showEmptyTip($(this).children());
                                }
                            });
                        }

                    } else {
                        $.each($(".mainArea"), function () {
                        	showEmptyTip($(this).children());
                        });
                    }
                }
            });
        },
        
        setPreChangeData: function (obj) {
            var c = new PreChange('preChange', obj, preChangeOption);
            c.init();
        },
        setPreExceptionData: function (obj) {
            var u = new PreUnusual('preUnusual', obj, preUnusualOption);
            u.init();
            return false;
            var preRiseHeight = 24;
            var preDownHeight = 24;
            var preRiseNumText = 0;
            var preDownNumText = 0;
            //百分比
            var preRise = $('#preRise');
            var preDown = $('#preDown');
            //具体数字
            var preRiseNum = '#preRiseNum';
            var preDownNum = '#preDownNum';
            //比率
            var pRise = 0;
            var pDown = 0;
            if (obj != null && obj.equal != 0) {
                //具体的数值
                preRiseNumText = obj.rise;
                preDownNumText = obj.down;
                //比率
                pRise = (obj.rise / obj.equal);
                pDown = obj.down / obj.equal;

                pRise = isNaN(pRise) ? 0 : pRise;
                pDown = isNaN(pDown) ? 0 : pDown;

                //先获取父div的高度,也就是最大高度，在按照高度进行计算
                var h = $('.preException').css('height');
                h = h.substr(0, h.length - 2);
                preRiseHeight = h * pRise;
                preDownHeight = h * pDown;
                //防止比率太小，显示不出来文字
                if (preRiseHeight < 24) {
                    preRiseHeight = 24;
                }
                if (preDownHeight < 24) {
                    preDownHeight = 24;
                }
            }
            //设置DIV高度
            preRise.css({'height': preRiseHeight + 'px', 'line-height': preRiseHeight + 'px'});
            preDown.css({'height': preDownHeight + 'px', 'line-height': preDownHeight + 'px'});
            //设置百分比
            preRise.text(Math.round(pRise * 100) + '%');
            preDown.text(Math.round(pDown * 100) + '%');
            //设置具体的数值
            $(preRiseNum).text(preRiseNumText);
            $(preDownNum).text(preDownNumText);
        }
    };
    
    
	var tableOption= {
			url: urls.queryPerformanceDetailUrl,
            datatype: 'json',
            postData: {'organId': reqOrganId,'yearMonth':yearMonth},
            mtype: 'POST',
//            altRows: true,//设置表格行的不同底色
//			altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
            autowidth: true,
//            height:height,
            colNames: ['<span class="grid-th">姓名</span>', 
                       '<span class="grid-th">当期绩效</span>',
                       '<span class="grid-th">上次绩效</span>', 
                       '<span class="grid-th">上上次绩效</span>'],
            colModel: [
                       {
                           name: 'userNameCh',
                           index: 'userNameCh',
                           sortable: false,
                           align: 'center',
                           formatter: function (value, options, row) {
                               if (_.isEmpty(value)) {
                                   return "";
                               }
                               return "<span class='grid-td-high'>"+value+"<span>";
                           }
                       },
                       {name: 'performanceName1', index: 'performanceName1', sortable: false, align: 'center',
                    	   formatter: function (value, options, row) {
                               if (_.isEmpty(value)) {
                                   return "";
                               }
                               return "<span class='grid-td'>"+value+"<span>";
                           }},
                       {name: 'performanceName2', index: 'performanceName2', sortable: false, align: 'center',
                    	   formatter: function (value, options, row) {
                    		   if(lastYearMonth==""){
                    			   return "-";
                    		   }
                           return "<span class='grid-td'>"+value+"<span>";
                       }},
                       {
                           name: 'performanceName3',
                           index: 'performanceName3',
                           sortable: false,
                           align: 'center',
                    	   formatter: function (value, options, row) {
                    		   if(beforeLastYearMonth==""){
                    			   return "-";
                    		   }
                           return "<span class='grid-td'>"+value+"<span>";
                           }
                       }
                ],loadComplete:function(xhr){
                	tableGrid.scroll=xhr.total>1;
                },
                scroll: true
        };

	var tableGrid={
			gridId:"#performanceDetail",
			PanelId:"#performanceDetailPanel",
			tableOption:tableOption,
			scroll:true,
			init:function(){
				var self=this;
				$(self.gridId).jqGrid(self.tableOption);
				this.resize();
			},resize: function () {
	        	var self=this;
	        	if(window.screenFull){
	        		$(self.gridId).setGridWidth($(self.PanelId).width()+22);
	        	}else{
	        		if(this.scroll){
	        			$(self.gridId).setGridWidth($(self.PanelId).width()+22);
	        		}else{
	        			$(self.gridId).setGridWidth($(self.PanelId).width());
	        		}
	        		
	        	}
	            $(self.gridId).setGridHeight($(self.PanelId).height());
	        }
	}
	initTopBlock(reqOrganId,yearMonth)
	preDistributionObj.init(reqOrganId,yearMonth,0)
	 preChangeObj.init(reqOrganId, yearMonth,lastYearMonth);
    $.screenZoom([
       	preDistributionObj,
       	tableGrid
    ]);
	if(yearMonth==""){
		showEmptyTip($("#performanceDetail"));
	}else{
		tableGrid.init();
	}
	
});