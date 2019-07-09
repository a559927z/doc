/**
 * Created by wqcai on 16/12/19 019.
 */
require(['jquery', 'echarts', 'dataTool', 'bootstrap', 'underscore', 'utils', 'select2'], function ($, echarts, dataTool) {
    var webRoot = G_WEB_ROOT, win = top != window ? top.window : window, modeCategory = 0;
    var defaultColor = ['#7996d2', '#78e1ff', '#33a4fc', '#3367fc', '#8333fc', '#6fcdb2', '#90d4e7', '#8fe7bf', '#8eb3e8', '#6386ce'];
    var urls = {
        getManageListUrl: webRoot + '/clientImg/getManageList',             //管理者关联数据
        getSalesEmpImgUrl: webRoot + '/clientImg/getSalesEmpImg',           //销售人员关联数据
        getSalesClientImgUrl: webRoot + '/clientImg/getSalesClientImg',     //销售客户关联数据
        getManageTotalSalesInfoUrl: webRoot + '/clientImg/getManageTotalSalesInfo',		//管理者-销售总额/销售利润/回款总额
        getSaleTotalSalesInfoUrl: webRoot + '/clientImg/getSaleTotalSalesInfo',		//销售人员-销售总额/销售利润/回款总额
        getManageSalesMoneyAndRingUrl: webRoot + '/clientImg/getManageSalesMoneyAndRing',		//管理者-销售总额/环比变化
        getSaleSalesMoneyAndRingUrl: webRoot + '/clientImg/getSaleSalesMoneyAndRing',		//销售人员-销售总额/环比变化
        getManageReturnAmountAndRingUrl: webRoot + '/clientImg/getManageReturnAmountAndRing',		//管理者-回款总额/环比变化
        getSaleReturnAmountAndRingUrl: webRoot + '/clientImg/getSaleReturnAmountAndRing',		//销售人员-回款总额/环比变化
    }

    $(win.document.getElementById('tree')).next().hide();
    if (win.setCurrNavStyle) win.setCurrNavStyle();

    var initLayoutObj = {
        chartId: '#chart',
        settingId: '#settingBlock',
        detailId: '#detailBlock',
        init: function () {
            var self = this, wHeight = document.body.offsetHeight < 200 ? document.body.clientHeight : document.body.offsetHeight;
            var $chartObj = $(self.chartId), $settingObj = $(self.settingId), $detailObj = $(self.detailId);
            var h = wHeight / 24, chartsH = Math.round(h * 18), blockH = Math.round(h * 6);
            if (wHeight > 768) {
                chartsH = Math.round(h * 19), blockH = Math.round(h * 5);
            }
            $chartObj.height(chartsH);
            $settingObj.height(chartsH);
            $detailObj.height(blockH);
            $detailObj.find('.col-xs-8').children().height(blockH);
        },
        showMode: function () {
            var self = this, $settingObj = $(self.settingId), $detailObj = $(self.detailId);
            $settingObj.children('.setting-main').children().addClass('hide');
            $('#setting' + modeCategory).removeClass('hide');

            $detailObj.children().addClass('hide');
            $('#detail' + modeCategory).removeClass('hide');
        }
    }
    initLayoutObj.init();

    $('.btn-layout').click(function () {
        $('.btn-layout').removeClass('active');
        $(this).addClass('active');
    });

    $('.dropdown-menu li').click(function () {
        var txt = $(this).children().text();
        $(this).parent().prev('button.btn').html(txt + '&nbsp;<span class="icon-caret-down icon-on-right"></span>');
    });

    var chartObj = initEChart('chart');

    /**
     * 搜索
     */
    var searchObj = {
        selecrId: '#selectEmp',
        conditionId: '#conditionMain',
        hasClick: false,
        init: function (data) {
            var self = this;
            self.renderSearch(data);
            self.renderCondition(data);

            $('.img-find').unbind('click').click(function () {
                if (!self.hasClick) {
                    $('#settingMain').slideToggle('fast');
                    self.hasClick = true;
                } else {
                    $('#settingMain').slideToggle('fast');
                    self.hasClick = false;
                }
            });
        },
        renderSearch: function (data) {
            var self = this, $selectObj = $(self.selecrId);
            // 下拉菜单,先初始化
            $selectObj.html('<option value="-1"></option>');
            var selectObj = $selectObj.select2({
                language: 'zh-CN',
                data: data,
                placeholder: {
                    id: '-1',
                    text: '请输入名称'
                },
                allowClear: true
            });
            selectObj.unbind('select2:select').on('select2:select', function (evt) {
                console.log(evt);
            });
            selectObj.unbind('select2:unselect').on('select2:unselect', function (evt) {
                console.log(evt);
            });
        },
        renderCondition: function (data) {
            var self = this, $conditionObj = $(self.conditionId);
            $conditionObj.empty();
            $.each(data, function (idx, obj) {
                if (obj.category != 1) return true;
                var html = '<div class="sales-layout">' +
                    '<img src="' + webRoot + '/assets/img/demo/graph/' + parseInt(Math.random() * 9) + '.png">' +
                    '<div class="sales-main"><span class="sales-title">销售目标</span><span class="sales-num">241</span></div>' +
                    '<div class="sales-main"><span class="sales-title">预测完成</span><span class="sales-num">98%</span></div>';
                html += '</div>';
                $conditionObj.append(html);
            })
        }
    }
    
    /**
     * 销售总额/销售利润/回款总额
     * */
    var salesTotalObj = {
    	init: function(){
    		var self = this;
    		self.manageRequest();
    	},
    	manageRequest: function(){
    		var self = this;
    		$.post(urls.getManageTotalSalesInfoUrl, function(data){
    			self.manageLoadData(data);
    		});
    	},
    	manageLoadData: function(data){
    		var self = this;
    		var $money = $('#manageSalesMoney'), $profit = $('#manageSalesProfit'), $returnAmount = $('#manageReturnAmount');
    		self.loadData($money, $profit, $returnAmount, data);
    	},
    	saleRequest: function(empId){
    		var self = this;
    		var param = {
    			empId : empId
    		}
    		$.post(urls.getSaleTotalSalesInfoUrl, param, function(data){
    			self.salesLoadData(data);
    		});
    	},
    	salesLoadData: function(data){
    		var self = this;
    		var $money = $('#saleSalesMoney'), $profit = $('#saleSalesProfit'), $returnAmount = $('#saleReturnAmount');
    		self.loadData($money, $profit, $returnAmount, data);
    	},
    	loadData: function($money, $profit, $returnAmount, data){
    		var self = this;
    		if(data){
    			var moneyNum = data.moneyNum == null ? 0 : Tc.formatFloat(data.moneyNum);
    			var profitNum = data.profitNum == null ? 0 : Tc.formatFloat(data.profitNum);
    			var returnAmount = data.returnAmount == null ? 0 : Tc.formatFloat(data.returnAmount);
    			var moneyPer = data.moneyPer == null ? 0 : data.moneyPer;
    			var profitPer = data.profitPer == null ? 0 : data.profitPer;
    			$money.html(moneyNum);
    			$profit.html(profitNum);
    			$returnAmount.html(returnAmount);
    			self.setImg($money, moneyPer);
    			self.setImg($profit, profitPer);
    			self.setImg($returnAmount);
    		}
    	},
    	setImg: function($id, num){
    		$id.siblings('.accord-bottom-float-arrow').removeClass('accord-bottom-float-value-rise').removeClass('accord-bottom-float-value-drop');
    		if(num){
    			if(num > 0){
    				$id.siblings('.accord-bottom-float-arrow').addClass('accord-bottom-float-value-rise');
    			} else if(num < 0){
    				$id.siblings('.accord-bottom-float-arrow').addClass('accord-bottom-float-value-drop');
    			}
    		}
    	}
    }
    salesTotalObj.init();

    /**
     * 销售额/回款额等公共option
     * */
    var  option = {
		grid: {
            top: '15%',
            right: '15%',
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            bottom: 12,
            data: []
        },
        xAxis: [
            {
                type: 'category',
                axisTick: {
                	interval: 0
                },
                axisLabel: {
                	show: true
                },
                data: []
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '',
                axisLabel: {
                    formatter: '{value}'
                }
            },
            {
                type: 'value',
                axisTick: false,
                splitLine: false,
                axisLine: {
                    show: false
                },
                axisLabel: {
                    formatter: '{value} %'
                },
            }
        ],
        series: [
            {
                name: '',
                type: 'bar',
                barMaxWidth: 20,
                itemStyle: {
                    normal: {
                        color: Tc.defaultBarColor[0]
                    }
                },
                data: []
            },
            {
                name: '',
                type: 'line',
                yAxisIndex: 1,
                itemStyle: {
                    normal: {
                        color: Tc.defaultLineColor[0]
                    }
                },
                data: []
            }
        ]
    }
    /**
     * 销售额/环比变化
     * */
    var salesMoneyAndRingObj = {
        chartId: 'trendChart',
        chartObj: null,
        init: function () {
            var self = this;
            if (_.isNull(self.chartObj)) self.chartObj = initEChart(self.chartId);
            self.manageRequest();
        },
        manageRequest: function(){
        	var self = this;
        	var param = {row : 6}
        	$.post(urls.getManageSalesMoneyAndRingUrl, param, function(data){
        		self.loadData(data);
        	});
        },
        saleRequest: function(empId){
        	var self = this;
        	var param = {empId: empId, row : 6}
        	$.post(urls.getSaleSalesMoneyAndRingUrl, param, function(data){
        		self.loadData(data);
        	});
        },
        loadData: function(data){
        	var self = this;
        	var arr = ['销售额', '环比变化'];
        	if(data && data.xAxisData.length > 0){
        		option.legend.data = arr;
        		option.xAxis[0].data = data.xAxisData;
        		option.yAxis[0].name = arr[0];
        		option.series[0].name = arr[0];
        		option.series[0].data = formatArrToFloat(data.seriesData);
        		option.series[1].name = arr[1];
        		option.series[1].data = formatArrToFloat(data.ringsData);
        		self.render();
        	}
        },
        render: function () {
            var self = this;
            self.chartObj.setOption(option);
            self.chartObj.resize();
        }
    }
    salesMoneyAndRingObj.init();
    /**
     * 回款额/环比变化
     * */
    var returnAmountAndRingObj = {
        chartId: 'totalChart',
        chartObj: null,
        init: function () {
            var self = this;
            if (_.isNull(self.chartObj)) self.chartObj = initEChart(self.chartId);
            self.manageRequest();
        },
        manageRequest: function(){
        	var self = this;
        	var param = {row : 6}
        	$.post(urls.getManageReturnAmountAndRingUrl, param, function(data){
        		self.loadData(data);
        	});
        },
        saleRequest: function(empId){
        	var self = this;
        	var param = {empId: empId, row : 6}
        	$.post(urls.getSaleReturnAmountAndRingUrl, param, function(data){
        		self.loadData(data);
        	});
        },
        loadData: function(data){
        	var self = this;
        	var arr = ['回款额', '环比变化'];
        	if(data && data.xAxisData.length > 0){
        		option.legend.data = arr;
        		option.xAxis[0].data = data.xAxisData;
        		option.yAxis[0].name = arr[0];
        		option.series[0].name = arr[0];
        		option.series[0].data = formatArrToFloat(data.seriesData);
        		option.series[1].name = arr[1];
        		option.series[1].data = formatArrToFloat(data.ringsData);
        		self.render();
        	}
        },
        render: function () {
            var self = this;
            self.chartObj.setOption(option);
            self.chartObj.resize();
        }
    }
    returnAmountAndRingObj.init();

    var analyzeObj = {
        chartId: 'analyzeChart',
        chartObj: null,
        init: function () {
            var self = this;
            if (_.isEmpty(self.chartObj)) self.chartObj = initEChart(self.chartId);

            self.render();
        },
        requestData: function () {

        },
        render: function () {
            var self = this;
            var option = {
                grid: {
                    top: '5%'
                },
                xAxis: [
                    {
                        type: 'category',
                        data: ['人数', '工资', '奖金', '销售总额']
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                color: Tc.defaultBarColor,
                series: [
                    {
                        name: '人数',
                        type: 'bar',
                        stack: '2',
                        data: [35, 28, 20, 25],
                        barMaxWidth: 30
                    },
                    {
                        name: '工资',
                        type: 'bar',
                        stack: '2',
                        data: [50, 40, 55, 30]
                    },
                    {
                        name: '奖金',
                        type: 'bar',
                        stack: '2',
                        data: [15, 32, 25, 45]
                    },
                    {
                        name: '人数',
                        type: 'line',
                        stack: '1',
                        data: [35, 28, 20, 25],
                        barMaxWidth: 30
                    },
                    {
                        name: '工资',
                        type: 'line',
                        stack: '1',
                        data: [50, 40, 55, 30]
                    },
                ]
            };
            self.chartObj.setOption(option);
        }
    }
    analyzeObj.init();
    /**
     * 格式化数组，保留两位小数
     * */
    function formatArrToFloat(arr){
    	var newArr = new Array();
    	$.each(arr, function(ind, obj){
    		newArr[ind] = Tc.formatFloat(obj);
    	});
    	return newArr;
    }
    /**
     * 销售关系网图
     */
    var graphObj = {        //管理者维度
        init: function () {
            var self = this;

            chartObj.on('dblclick', function (params) {
                var categoryNum = params.data.category;
                if (categoryNum == modeCategory || categoryNum > 2) return;
                modeCategory = categoryNum;
                switch (categoryNum) {
                    case 0:
                        self.requestManageData();
                        salesTotalObj.manageRequest();
                        salesMoneyAndRingObj.manageRequest();
                        returnAmountAndRingObj.manageRequest();
                        break;
                    case 1:
                        self.requestSalesData(params.data.id);
                        salesTotalObj.saleRequest(params.data.id);
                        salesMoneyAndRingObj.saleRequest(params.data.id);
                        returnAmountAndRingObj.saleRequest(params.data.id);
                        break;
                    case 2:
                        self.requestClientsData(params.data.parent,params.data.id);
                        break;
                    default:

                }
                initLayoutObj.showMode();
            });

            self.requestManageData();
        },
        requestManageData: function () {
            var self = this;
            chartObj.showLoading();
            $.get(urls.getManageListUrl, function (data) {
                chartObj.hideLoading();
                self.render(data);
            });
        },
        requestSalesData: function (empId) {
            var self = this;
            chartObj.showLoading();
            $.post(urls.getSalesEmpImgUrl, {empId: empId}, function (data) {
                chartObj.hideLoading();
                self.render(data);
            });
        },
        requestClientsData: function (empId, clientId) {
            var self = this;
            chartObj.showLoading();
            $.post(urls.getSalesClientImgUrl, {empId: empId, clientId: clientId}, function (data) {
                chartObj.hideLoading();
                self.render(data);
            });
        },
        render: function (data) {
            //搜索相关
            searchObj.init(data.nodes);

            var categories = [{name: '管理者'}, {name: '销售顾问'}, {name: '客户'}, {name: '客户维度'}];

            data.nodes.forEach(function (node) {
                node.value = node.symbolSize;
                node.label = {
                    normal: {
                        textStyle: {
                            color: '#fff',
                            fontSize: 10
                        },
                        position: 'bottom',
                        show: node.symbolSize > 30
                    }
                };
                node.draggable = true;      //节点是否可拖拽
                if (node.category == 0) {
                    node.symbol = 'image://' + webRoot + '/assets/img/base/client-img-me.png';
                } else if (node.category < 2) {
                    node.symbol = 'svg://' + getUserIconStr(node.id, defaultColor[node.category]);
                }
            });
            var option = {
                tooltip: false,
//                legend: [{
//                    // selectedMode: 'single',
//                    data: categories.map(function (a) {
//                        return a.name;
//                    })
//                }],
                color: defaultColor,
                animationDuration: 1500,
                animationEasingUpdate: 'quinticInOut',
                graphic: [{
                    type: 'image',
                    id: 'logo',
                    // bounding: 'raw',
                    // origin: [125, 125],
                    style: {
                        image: webRoot + '/assets/img/base/client-img-bg.png',
                    }
                }],
                backgroundColor: '#194971',
                series: [
                    {
                        type: 'graph',
                        layout: 'force',
                        data: data.nodes,
                        links: data.links,
                        categories: categories,
                        hoverAnimation: true,
                        roam: true,
                        force: {
                            gravity: 1,
                            // edgeLength: [50, 100],
                            repulsion: 1000
                        },
                        lineStyle: {
                            normal: {
                                color: '#7ddee7',
                                opacity: 0.6
                            }
                        }
//                        focusNodeAdjacency: true,     //上下级节点高亮
//                        lineStyle: {
//                            normal: {
//                                color: 'source',
//                                curveness: 0.3    //添加弧度
//                            }
//                        }
                    }
                ]
            };
            chartObj.clear();
            chartObj.setOption(option);
        }
    };
    graphObj.init();

    $(window).resize(function () {
        initLayoutObj.init();
        chartObj.resize();
        if (modeCategory == 0) {
            returnAmountAndRingObj.chartObj.resize();
            salesMoneyAndRingObj.chartObj.resize();
            analyzeObj.chartObj.resize();
        }
    });

    /***
     * 初始化echart
     * @param domId
     * @returns {*}
     */
    function initEChart(domId) {
        return echarts.init(document.getElementById(domId));
    }

    /**
     * 获取chart点图像信息
     * @param nodeId
     * @param color
     * @returns {string}
     */
    function getUserIconStr(nodeId, color) {
        var bigCircle = document.getElementById('bigCircle'), bigCircleFilter = document.getElementById('bigCircleFilter');
        bigCircle.style.fill = color;
        bigCircle.style.stroke = color;
        bigCircleFilter.style.fill = color;
        bigCircleFilter.style.stroke = color;
        var warnNum = Math.round(Math.random() * 20), svgTxt = document.getElementById('svgTxt');
        if (warnNum == 0) {
            document.getElementById('smallCircle').style.display = 'none';
            svgTxt.style.display = 'none';
        } else {
            var len = (warnNum + '').length;
            if (len > 1) {
                svgTxt.style.fontSize = '12pt';
                svgTxt.setAttributeNS(null, 'x', 82);
            } else {
                svgTxt.style.fontSize = '14pt';
                svgTxt.setAttributeNS(null, 'x', 85);
            }
            svgTxt.innerHTML = warnNum;
        }
        // var imgObj = document.getElementById('imgObj');
        // imgObj.setAttributeNS('http://www.w3.org/1999/xlink', 'href', webRoot + '/assets/img/demo/graph/' + nodeId + '.png');
        var svgXml = (new XMLSerializer()).serializeToString(document.getElementById("svgObj"));
        return window.btoa(svgXml);
    }
});