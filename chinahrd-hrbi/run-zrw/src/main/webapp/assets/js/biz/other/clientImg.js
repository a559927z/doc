/**
 * Created by wqcai on 16/12/19 019.
 */
require(['jquery', 'echarts', 'dataTool', 'bootstrap', 'underscore', 'utils', 'select2'], function ($, echarts, dataTool) {
    var webRoot = G_WEB_ROOT, win = top != window ? top.window : window, modeCategory = 0, settingModal = 0;
    var defaultColor = ['#7996d2', '#78e1ff', '#33a4fc', '#3367fc', '#8333fc', '#6fcdb2', '#90d4e7', '#8fe7bf', '#8eb3e8', '#6386ce'];
    var urls = {
        getManageListUrl: webRoot + '/clientImg/getManageList',             //管理者关联数据
        getSalesEmpImgUrl: webRoot + '/clientImg/getSalesEmpImg',           //销售人员关联数据
        getSalesClientImgUrl: webRoot + '/clientImg/getSalesClientImg',     //销售客户关联数据
        getManageTotalSalesInfoUrl: webRoot + '/clientImg/getManageTotalSalesInfo',		//管理者-销售总额/销售利润/回款总额
        getSaleTotalSalesInfoUrl: webRoot + '/clientImg/getSaleTotalSalesInfo',		//销售人员-销售总额/销售利润/回款总额
        getClientTotalSalesInfoUrl: webRoot + '/clientImg/getClientTotalSalesInfo',		//销售客户-销售总额/销售利润/回款总额
        getManageSalesMoneyAndRingUrl: webRoot + '/clientImg/getManageSalesMoneyAndRing',		//管理者-销售总额/环比变化
        getSaleSalesMoneyAndRingUrl: webRoot + '/clientImg/getSaleSalesMoneyAndRing',		//销售人员-销售总额/环比变化
        getClientSalesMoneyAndRingUrl: webRoot + '/clientImg/getClientSalesMoneyAndRing',		//销售客户-销售总额/环比变化
        getManageReturnAmountAndRingUrl: webRoot + '/clientImg/getManageReturnAmountAndRing',		//管理者-回款总额/环比变化
        getSaleReturnAmountAndRingUrl: webRoot + '/clientImg/getSaleReturnAmountAndRing',		//销售人员-回款总额/环比变化
        getClientReturnAmountAndRingUrl: webRoot + '/clientImg/getClientReturnAmountAndRing',		//销售客户-回款总额/环比变化
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

    $('#setting0Modal .btn-layout').click(function () {
        var $this = $(this);
        var num = $this.data('toggle');
        if (_.isNumber(num)) settingModal = num;

        graphObj.render();
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
            	$('#conditionBlock').addClass('hide');
                if (!self.hasClick) {
                    $('#settingMain').slideToggle('fast', function(){
                    	if($('#settingMain').css('display') == 'none'){
                    		$('#conditionBlock').removeClass('hide');
                    	}
                    });
                    self.hasClick = true;
                } else {
                    $('#settingMain').slideToggle('fast', function(){
                    	if($('#settingMain').css('display') == 'none'){
                    		$('#conditionBlock').removeClass('hide');
                    	}
                    });
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
                    '<div class="sales-main"><span class="sales-title">销售目标</span><span class="sales-num">'+ Tc.formatFloat(obj.salesTarget) +'</span></div>' +
                    '<div class="sales-main"><span class="sales-title">预测完成</span><span class="sales-num">'+ Tc.formatFloat(obj.perNum) +'%</span></div>';
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
    		var $money = $('#manageSalesMoney'), $profit = $('#manageSalesProfit'), $returnAmount = $('#manageReturnAmount');
    		self.noImg($money);
			self.noImg($profit);
			self.noImg($returnAmount);
    		$.post(urls.getManageTotalSalesInfoUrl, function(data){
    			self.loadData($money, $profit, $returnAmount, data);
    		});
    	},
    	saleRequest: function(empId){
    		var self = this;
    		var param = {empId : empId}
    		var $money = $('#saleSalesMoney'), $profit = $('#saleSalesProfit'), $returnAmount = $('#saleReturnAmount');
    		self.noImg($money);
			self.noImg($profit);
			self.noImg($returnAmount);
    		$.post(urls.getSaleTotalSalesInfoUrl, param, function(data){
    			self.loadData($money, $profit, $returnAmount, data);
    		});
    	},
    	clientRequest: function(empId, clientId){
    		var self = this;
    		var param = {empId : empId, clientId: clientId}
    		var $money = $('#clientSalesMoney'), $profit = $('#clientSalesProfit'), $returnAmount = $('#clientReturnAmount');
    		self.noImg($money);
			self.noImg($profit);
			self.noImg($returnAmount);
    		$.post(urls.getClientTotalSalesInfoUrl, param, function(data){
    			self.loadData($money, $profit, $returnAmount, data);
    		});
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
    		if(num){
    			if(num > 0){
    				$id.siblings('.accord-bottom-float-arrow').addClass('accord-bottom-float-value-rise');
    			} else if(num < 0){
    				$id.siblings('.accord-bottom-float-arrow').addClass('accord-bottom-float-value-drop');
    			}
    		}
    	},
    	noImg: function($id){
    		$id.siblings('.accord-bottom-float-arrow').removeClass('accord-bottom-float-value-rise').removeClass('accord-bottom-float-value-drop');
    	}
    }
    salesTotalObj.init();

    /**
     * 销售额/回款额等公共option
     * */
    var option = {
        grid: {
            top: '20%',
            right: '15%',
            bottom: '25%'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer : {
	            type : 'line',
	            lineStyle : {
	            	type: 'dashed',
	            	width: 1,
	            	color: 'rgba(255, 255, 255, 0.8)'
	            }
	        },
	        formatter : function(data){
	        	var name = data[0].name;
	        	var div = '<div>'+ name +'</div>'
	        			+ '<div>' + data[0].seriesName + ': '+ data[0].value +'万元</div>';
	        	var unit = '';
	        	if(data[1].value != '-'){
	        		unit = '%';
	        	}
	        	div += '<div>'+ data[1].seriesName +': '+ data[1].value + unit +'</div>'
	        	return div;
	        }
        },
        legend: {
        	top: 0,
            right: 0,
            itemWidth : 20,
            itemHeight : 10,
        	textStyle : {
        		fontSize : 10,
        		color: 'rgba(255, 255, 255, 0.8)'
        	},
            data: []
        },
        xAxis: [
            {
                type: 'category',
                axisLabel: {
                	interval : 0
                },
                axisLine: {
                	lineStyle: {
                		color: 'rgba(255, 255, 255, 0.8)'
                	}
                },
                data: []
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '',
                splitNumber: 5,
                axisTick: false,
                axisLabel: {
                    formatter: '{value}'
                },
                axisLine: {
                	lineStyle: {
                		color: 'rgba(255, 255, 255, 0.8)'
                	}
                },
            },
            {
                type: 'value',
                axisTick: false,
                splitLine: false,
                splitNumber: 5,
                axisLine: {
                    show: false
                },
                axisLabel: {
                	textStyle: {
                		color: 'rgba(255, 255, 255, 0.5)'
                	},
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
        manageChartId: 'manageSalesChart',
        saleChartId: 'saleSalesChart',
        clientChartId: 'clientSalesChart',
        chartObj: null,
        init: function () {
            var self = this;
            self.manageRequest();
        },
        manageRequest: function(){
        	var self = this;
    		self.chartObj = initEChart(self.manageChartId);
        	var param = {row : 6}
        	self.requestData(urls.getManageSalesMoneyAndRingUrl, param);
        },
        saleRequest: function(empId){
        	var self = this;
    		self.chartObj = initEChart(self.saleChartId);
        	var param = {empId: empId, row : 6}
        	self.requestData(urls.getSaleSalesMoneyAndRingUrl, param);
        },
        clientRequest: function(empId, clientId){
        	var self = this;
    		self.chartObj = initEChart(self.clientChartId);
        	var param = {empId: empId, clientId: clientId, row : 6}
        	self.requestData(urls.getClientSalesMoneyAndRingUrl, param);
        },
        requestData: function(url, param){
        	var self = this;
        	$.post(url, param, function(data){
        		self.loadData(data);
        	});
        },
        loadData: function(data){
        	var self = this;
        	var arr = ['销售额', '环比变化'];
        	if(data && data.xAxisData.length > 0){
        		option.legend.data = arr;
        		$.each(data.xAxisData, function(ind, obj){
        			if(obj.indexOf('/')){
        				data.xAxisData[ind] = Number(obj.split('/')[1]) + '月';
        			}
        		});
        		option.xAxis[0].data = data.xAxisData;
        		option.yAxis[0].name = arr[0];
        		option.series[0].name = arr[0];
        		option.series[0].data = formatArrToFloat(data.seriesData);
        		option.series[1].name = arr[1];
        		option.series[1].data = formatArrToFloat(data.ringsData);
        		option.series[0].itemStyle.normal.color = new echarts.graphic.LinearGradient(
                    0, 0, 0, 1,
                    [
                        {offset: 0, color: '#188df0'},
                        {offset: 0.7, color: '#83bff6'},
                        {offset: 1, color: '#ffffff'}
                    ]
                );
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
		manageChartId: 'manageReturnAmountChart',
		saleChartId: 'saleReturnAmountChart',
		clientChartId: 'clientReturnAmountChart',
        chartObj: null,
        init: function () {
            var self = this;
            self.manageRequest();
        },
        manageRequest: function(){
        	var self = this;
    		self.chartObj = initEChart(self.manageChartId);
        	var param = {row : 6}
        	self.requestData(urls.getManageReturnAmountAndRingUrl, param);
        },
        saleRequest: function(empId){
        	var self = this;
    		self.chartObj = initEChart(self.saleChartId);
        	var param = {empId: empId, row : 6}
        	self.requestData(urls.getSaleReturnAmountAndRingUrl, param);
        },
        clientRequest: function(empId, clientId){
        	var self = this;
    		self.chartObj = initEChart(self.clientChartId);
        	var param = {empId: empId, clientId: clientId, row : 6}
        	self.requestData(urls.getClientReturnAmountAndRingUrl, param);
        },
        requestData: function(url, param){
        	var self = this;
        	$.post(url, param, function(data){
        		self.loadData(data);
        	});
        },
        loadData: function(data){
        	var self = this;
        	var arr = ['回款额', '环比变化'];
        	if(data && data.xAxisData.length > 0){
        		option.legend.data = arr;
        		$.each(data.xAxisData, function(ind, obj){
        			if(obj.indexOf('/')){
        				data.xAxisData[ind] = Number(obj.split('/')[1]) + '月';
        			}
        		});
        		option.xAxis[0].data = data.xAxisData;
        		option.yAxis[0].name = arr[0];
        		option.series[0].name = arr[0];
        		option.series[0].data = formatArrToFloat(data.seriesData);
        		option.series[1].name = arr[1];
        		option.series[1].data = formatArrToFloat(data.ringsData);
        		option.series[0].itemStyle.normal.color = new echarts.graphic.LinearGradient(
                    0, 0, 0, 1,
                    [
                        {offset: 0, color: '#188df0'},
                        {offset: 0.7, color: '#83bff6'},
                        {offset: 1, color: '#ffffff'}
                    ]
                );
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
		manageChartId: 'manageAnalyzeChart',
		saleChartId: 'saleAnalyzeChart',
		clientChartId: 'clientAnalyzeChart',
        chartObj: null,
        init: function () {
            var self = this;
            if (_.isEmpty(self.chartObj)) self.chartObj = initEChart(self.manageChartId);

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
    		if(obj == "-"){
    			newArr[ind] = obj;
    		} else {
    			newArr[ind] = Tc.formatFloat(obj);
    		}
    	});
    	return newArr;
    }
    /**
     * 销售关系网图
     */
    var graphObj = {        //管理者维度
        init: function () {
            var self = this;

            chartObj.showLoading();
            $.get(urls.getManageListUrl, function (data) {
                chartObj.hideLoading();
                self.render(data);
            });

            chartObj.on('click', function (params) {
                var categoryNum = params.data.category;
                if (categoryNum == modeCategory || categoryNum > 2) return;
                modeCategory = categoryNum;
                switch (categoryNum) {
                    case 1:
                        self.requestSalesData(params.data.id);
                        salesTotalObj.saleRequest(params.data.id);
                        salesMoneyAndRingObj.saleRequest(params.data.id);
                        returnAmountAndRingObj.saleRequest(params.data.id);
                        break;
                    case 2:
                        self.requestClientsData(params.data.parent, params.data.id);
                        salesTotalObj.clientRequest(params.data.parent, params.data.id);
                        salesMoneyAndRingObj.clientRequest(params.data.parent, params.data.id);
                        returnAmountAndRingObj.clientRequest(params.data.parent, params.data.id);
                        break;
                    default:
                        self.requestManageData();
                        salesTotalObj.manageRequest();
                        salesMoneyAndRingObj.manageRequest();
                        returnAmountAndRingObj.manageRequest();
                }
                initLayoutObj.showMode();
            });
        },
        requestManageData: function () {
            var self = this;
            $.get(urls.getManageListUrl, function (data) {
                self.render(data);
            });
        },
        requestSalesData: function (empId) {
            var self = this;
            $.post(urls.getSalesEmpImgUrl, {empId: empId}, function (data) {
                self.render(data);
            });
        },
        requestClientsData: function (empId, clientId) {
            var self = this;
            $.post(urls.getSalesClientImgUrl, {empId: empId, clientId: clientId}, function (data) {
                self.render(data);
            });
        },
        render: function (data) {
            var self = this;
            data = data || self.data;
            //搜索相关
            searchObj.init(data.nodes);

            var categories = [{name: '管理者'}, {name: '销售顾问'}, {name: '客户'}, {name: '客户维度'}, {name: '客户信息'}];
            data.nodes.forEach(function (node) {
                node.value = node.symbolSize;
                node.label = {
                    normal: {
                        textStyle: {
                            color: '#fff',
                            fontSize: 10
                        },
                        // position: 'bottom',
                        show: (node.category != 0 && node.symbolSize > 30),
                    }
                };
                node.draggable = true;      //节点是否可拖拽
                if (node.category == 0) {
                    node.symbol = 'image://' + webRoot + '/assets/img/base/client-img-me.png';
                } else if (node.category < 2) {
                    node.symbol = 'svg://' + getUserIconStr(node.id, defaultColor[node.category]);
                    // node.itemStyle = {
                    //     normal: {
                    //         shadowColor: defaultColor[node.category],
                    //         shadowBlur: 30
                    //     }
                    // }
                } else if (node.category == 4){
                	node.symbol = 'svg://' + getUserInfoStr(node, defaultColor[node.category]);
                	node.symbolSize =  [200, 100];
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
                graphic: [{
                    type: 'image',
                    style: {
                        image: webRoot + '/assets/img/base/client-img-bg.png',
                    }
                }],
                backgroundColor: '#194971',
                series: [
                    {
                        type: 'graph',
                        // layout: 'force',
                        data: data.nodes,
                        links: data.links,
                        categories: categories,
                        hoverAnimation: true,
                        roam: true,
                        force: {
                            repulsion: 300
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
            switch (settingModal) {
                case 1:
                    avgHierarchyCoordinate(option);
                    break;
                case 2:
                    avgSortCoordinate(option);
                    break;
                default:
                    avgCircleCoordinate(option);
            }
            chartObj.setOption(option);
            self.data = data;
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
        var warnNum = Math.round(Math.random() * 20), svgTxt = document.getElementById('svgTxt');
        if (warnNum == 0) {
            document.getElementById('smallCircle').style.display = 'none';
            svgTxt.style.display = 'none';
        } else {
            var len = (warnNum + '').length;
            if (len > 1) {
                svgTxt.style.fontSize = '12pt';
                svgTxt.setAttributeNS(null, 'x', 84);
            } else {
                svgTxt.style.fontSize = '14pt';
                svgTxt.setAttributeNS(null, 'x', 88);
            }
            svgTxt.innerHTML = warnNum;
        }
        // var imgObj = document.getElementById('imgObj');
        // imgObj.setAttributeNS('http://www.w3.org/1999/xlink', 'href', webRoot + '/assets/img/demo/graph/' + nodeId + '.png');
        var svgXml = (new XMLSerializer()).serializeToString(document.getElementById("svgObj"));
        return window.btoa(svgXml);
    }
    
    /**
     * 获取公司信息
     * @param nodeId
     * @param color
     * @returns {string}
     */
    function getUserInfoStr(node, color) {
    	var baseRect = document.getElementById('baseRect');
    	baseRect.style.fill = color;
    	baseRect.style.stroke = color;
    	baseRect.width = 400;
    	var text = document.createElementNS('http://www.w3.org/2000/svg', 'text');
    	var inText = $(text).attr({
    			id: 'id' + Math.random(),
    			'class': 'text',
    	        x: 5,
    	        y: 20,
    	        fill: '#fff'
        });
    	getInfos(node, text);
    	$('#baseObj').append(inText);
    	
    	var baseXml = (new XMLSerializer()).serializeToString(document.getElementById("baseObj"));
//    	return window.btoa(baseXml);
    	//解决中文不显示问题
    	return window.btoa(unescape(encodeURIComponent(baseXml)));
    }
    
    /**
     * 获取基础信息/联系人信息等具体内容
     * @param node 节点
     * @param text 需要追加的text
     * */
    function getInfos(node, text){
    	$('.tspan').remove();
    	var nText = node.text, object = node.object;
    	if(nText.indexOf('基础信息') != -1){
    		var tspan = getTspan(1);
    		$(tspan).text("公司名称：" + isNull(object.clientName));
    		$(text).append(tspan);
    		var tspan = getTspan(2);
    		$(tspan).text("公司性质：" + isNull(object.nature));
    		$(text).append(tspan);
    		var tspan = getTspan(3);
    		$(tspan).text("所属行业：" + isNull(object.industry));
    		$(text).append(tspan);
    		var tspan = getTspan(4);
    		$(tspan).text("客户类型：" + isNull(object.clientType));
    		$(text).append(tspan);
    		var tspan = getTspan(5);
    		$(tspan).text("年均营业额：" + isNull(object.turnover));
    		$(text).append(tspan);
    		var tspan = getTspan(6);
    		$(tspan).text("员工规模：" + isNull(object.empNum));
    		$(text).append(tspan);
    		var tspan = getTspan(7);
    		$(tspan).text("管理干部数量：" + isNull(object.leaderNum));
    		$(text).append(tspan);
    		var tspan = getTspan(8);
    		$(tspan).text("公司地址：" + isNull(object.address));
    		$(text).append(tspan);
    		var tspan = getTspan(9);
    		$(tspan).text("公司电话：" + isNull(object.clientTel));
    		$(text).append(tspan);
    		var tspan = getTspan(9, 40);
    		$(tspan).text("公司email：" + isNull(object.clientEmail));
    		$(text).append(tspan);
    	} else if(nText.indexOf('联系人信息') != -1){
    		var tspan = getTspan(1);
    		$(tspan).text("姓名：" + object.contactsName);
    		$(text).append(tspan);
    		var tspan = getTspan(1, 30);
    		$(tspan).text("性别：" + (object.sex == 'm'? '男' : '女'));
    		$(text).append(tspan);
    		var tspan = getTspan(2);
    		$(tspan).text("直属上司：" + isNull(object.parentName));
    		$(text).append(tspan);
    		var tspan = getTspan(3);
    		$(tspan).text("部门：" + isNull(object.dept));
    		$(text).append(tspan);
    		var tspan = getTspan(4);
    		$(tspan).text("职务：" + isNull(object.post));
    		$(text).append(tspan);
    		var tspan = getTspan(5);
    		$(tspan).text("年龄：" + isNull(object.age));
    		$(text).append(tspan);
    		var tspan = getTspan(5, 30);
    		$(tspan).text("户籍：" + isNull(object.nativePlace));
    		$(text).append(tspan);
    		var tspan = getTspan(6);
    		$(tspan).text("婚姻：" + isNull(object.marriage));
    		$(text).append(tspan);
    		var tspan = getTspan(6, 30);
    		$(tspan).text("子女：" + isNull(object.children));
    		$(text).append(tspan);
    		var tspan = getTspan(7);
    		$(tspan).text("兴趣：" + isNull(object.interest));
    		$(text).append(tspan);
    		var tspan = getTspan(8);
    		$(tspan).text("性格：" + isNull(object.disposition));
    		$(text).append(tspan);
    		var tspan = getTspan(9);
    		$(tspan).text("手机：" + isNull(object.contactsTel));
    		$(text).append(tspan);
    		var tspan = getTspan(9, 30);
    		$(tspan).text("email：" + isNull(object.contactsEmail));
    		$(text).append(tspan);
    	} else if(nText.indexOf('纪要信息') != -1){
    		var tspan = getTspan(1);
    		$(tspan).text("纪要内容：" + isNull(object.note));
    		$(text).append(tspan);
    		var tspan = getTspan(2);
    		$(tspan).text("纪要要点：" + isNull(object.keyNote));
    		$(text).append(tspan);
    		var tspan = getTspan(3);
    		$(tspan).text("感兴趣产品：" + isNull(object.product));
    		$(text).append(tspan);
    	}
    }
    
    /**
     * 动态追加tspan
     * @param yNum y轴的倍数
     * @param xNum x轴的倍数
     * @returns tspan
     * */
    function getTspan(yNum, xNum){
    	var tspan = document.createElementNS('http://www.w3.org/2000/svg', 'tspan');
    	var inTspan = $(tspan).attr({
    	        id: 'id' + Math.random(),
    	        'class': 'tspan',
    	        x: 5 * xNum || 5,
    	        y: 20 * yNum || 20,
    	        fill: '#fff'
        });
    	return inTspan;
    }
    /**
     * 判断是否存在
     * */
    function isNull(text){
    	return (_.isNull(text) || text == '') ? '' : text;
    }

    /**
     * 平均圆坐标位置
     */
    function avgCircleCoordinate(option) {
        var series = option.series[0], data = series.data, links = series.links;

        var mainObj = _.find(data, function (obj) {
            return obj.value == 60;
        });
        if (!mainObj)    return;
        var x = mainObj.x = parseInt(chartObj.getWidth() / 2), y = mainObj.y = parseInt(chartObj.getHeight() / 2);
        var maxNum = 0, radius = series.force.repulsion;
        var obj2s = _.filter(data, function (obj2) {
            if (obj2.parent == mainObj.id) {
                var obj3s = _.filter(data, function (obj3) {
                    return obj3.parent == obj2.id;
                });
                if (obj3s.length > maxNum) maxNum = obj3s.length;
            }
            return obj2.parent == mainObj.id;
        });
        var obj2Len = obj2s.length;
        $.each(obj2s, function (i, obj2) {
            var obj2Angle = 360 / obj2Len * (i + 1);            //角度
            var obj2Radian = (2 * Math.PI / 360) * obj2Angle;   //弧度
            var obj2r = radius * (Math.round(Math.random()) == 1 ? 1 : 0.9);
            // var obj2r = radius;
            var obj2x = x + Math.sin(obj2Radian) * obj2r;
            var obj2y = y - Math.cos(obj2Radian) * obj2r;
            obj2.x = obj2x;
            obj2.y = obj2y;

            var obj3s = _.filter(data, function (obj3) {
                return obj3.parent == obj2.id;
            });

            var obj3Len = obj3s.length, obj3r = getNodeLinkLength(obj3Len);
            $.each(obj3s, function (j, obj3) {
                var angle = getNodeAngle(obj3Len), deviant = getNodeDeviant(obj3Len);
                /*obj3Len == 1时计算有误，故修改为以下方法*/
                //var obj3Angle = angle / (obj3Len - 1) * j + (obj2Angle - deviant);
                var obj3Angle;
                if(obj3Len > 1){
                	obj3Angle = angle / (obj3Len - 1) * j + (obj2Angle - deviant);
                } else {
                	obj3Angle = angle * j + (obj2Angle - deviant);
                }
                var obj3Radian = (2 * Math.PI / 360) * obj3Angle;
                var obj3x = obj2x + Math.sin(obj3Radian) * obj3r;
                var obj3y = obj2y - Math.cos(obj3Radian) * obj3r;
                obj3.x = obj3x;
                obj3.y = obj3y;
            });
        });
        if (!mainObj.parent) return;

        var pObj = _.find(data, function (obj) {
            return obj.id == mainObj.parent;
        });
        var pObjr = radius + radius / 3;
        var pObjRadian = (2 * Math.PI / 360) * 225;
        var pObjx = x + Math.sin(pObjRadian) * pObjr;
        var pObjy = y - Math.cos(pObjRadian) * pObjr;
        pObj.x = pObjx;
        pObj.y = pObjy;

        if (!pObj.parent) return;
        var p1Obj = _.find(data, function (obj) {
            return obj.id == pObj.parent;
        });
        p1Obj.x = pObjx - pObj.value * 2;
        p1Obj.y = pObjy;
    }

    /**
     * 平均圆坐标位置
     */
    function avgCircleCoordinate2(option) {
        var series = option.series[0], data = series.data, links = series.links;
        var mainObj = _.find(data, function (obj) {
            return obj.value == 60;
        });
        if (!mainObj)    return;
        var x = mainObj.x = parseInt(chartObj.getWidth() / 2), y = mainObj.y = parseInt(chartObj.getHeight() / 2);

        var obj2s = _.filter(data, function (obj2) {
            return obj2.parent == mainObj.id;
        });
        var obj2Len = obj2s.length, obj2r = 120;
        var obj3Len = 0, obj3r = obj2r + obj2r / 2, idx = 1;
        $.each(obj2s, function (obj2Idx, obj2) {
            var obj3s = _.filter(data, function (obj3) {
                return obj3.parent == obj2.id;
            });
            obj3Len += obj3s.length;
            if ((obj2Idx + 1) == obj2Len) {
                idx = obj3s.length / 2 == 0 ? obj3s.length / 2 : parseInt(obj3s.length / 2) + 1;
            }
        });
        $.each(obj2s, function (Obj2Idx, obj2) {
            var obj2Radian = (2 * Math.PI / 360) * ((360 / obj2Len) * (Obj2Idx + 1));
            var obj2x = obj2.x = x + Math.sin(obj2Radian) * obj2r;
            var obj2y = obj2.y = y - Math.cos(obj2Radian) * obj2r;

            var obj3s = _.filter(data, function (obj3) {
                return obj3.parent == obj2.id;
            });
            $.each(obj3s, function (i, obj3) {
                var obj3Idx = (idx >= obj3Len ? idx : (idx - obj3Len));
                var obj3Radian = (2 * Math.PI / 360) * ((360 / obj3Len) * obj3Idx);
                var obj3x = x + Math.sin(obj3Radian) * obj3r;
                var obj3y = y - Math.cos(obj3Radian) * obj3r;
                obj3.x = obj3x;
                obj3.y = obj3y;
                idx++;
            });
        });
        if (!mainObj.parent) return;

        var pObj = _.find(data, function (obj) {
            return obj.id == mainObj.parent;
        });
        var pObjr = obj2r + parseInt(obj2r / 1.2);
        var pObjRadian = (2 * Math.PI / 360) * 225;
        var pObjx = x + Math.sin(pObjRadian) * pObjr;
        var pObjy = y - Math.cos(pObjRadian) * pObjr;
        pObj.x = pObjx;
        pObj.y = pObjy;

        if (!pObj.parent) return;
        var p1Obj = _.find(data, function (obj) {
            return obj.id == pObj.parent;
        });
        p1Obj.x = pObjx - pObj.value;
        p1Obj.y = pObjy;
    }

    /**
     * 组织模式
     */
    function avgHierarchyCoordinate(option) {
        var series = option.series[0], data = series.data, links = series.links;

        var mainObj = _.find(data, function (obj) {
            return obj.value == 60;
        });
        if (!mainObj)    return;
        var x = mainObj.x = parseInt(chartObj.getWidth() / 2), y = mainObj.y = parseInt(chartObj.getHeight() / 3);
        var obj3Len = 0, radius = series.force.repulsion, obj3Idx = 0;
        var obj2s = _.filter(data, function (obj2) {
            if (obj2.parent == mainObj.id) {
                var obj3s = _.filter(data, function (obj3) {
                    return obj3.parent == obj2.id;
                });
                obj3Len += obj3s.length;
            }
            return obj2.parent == mainObj.id;
        });
        var obj3w = parseInt(chartObj.getWidth() / obj3Len);
        $.each(obj2s, function (i, obj2) {
            if (obj3Len == 0) {
                var obj2w = parseInt(chartObj.getWidth() / obj2s.length);
                var obj2x = i * obj2w;
                var obj2y = y * 2;
                obj2.x = obj2x;
                obj2.y = obj2y;
                return true;
            }
            var obj3s = _.filter(data, function (obj3) {
                return obj3.parent == obj2.id;
            });
            var len = obj3s.length, bool = len % 2 == 0, obj3Middle = len / 2, obj3Section = 0;
            $.each(obj3s, function (j, obj3) {
                var obj3x = obj3Idx * obj3w;
                var obj3y = y * 3;
                obj3.x = obj3x;
                obj3.y = obj3y;

                if (bool && (obj3Middle == j || (obj3Middle + 1) == j)) {  //假如子节点个数刚好够除，
                    obj3Section += obj3x;
                } else if (!bool && (obj3Middle - 0.5) == j) {
                    obj3Section = obj3x * 2;
                }

                obj3Idx++;
            });
            var obj2x = obj3Section / 2;
            var obj2y = y * 2;
            obj2.x = obj2x;
            obj2.y = obj2y;
            obj3Idx++;
        });
        if (!mainObj.parent) return;

        var pObj = _.find(data, function (obj) {
            return obj.id == mainObj.parent;
        });
        var pObjr = radius;
        var pObjRadian = (2 * Math.PI / 360) * 280;
        var pObjx = x + Math.sin(pObjRadian) * pObjr;
        var pObjy = y - Math.cos(pObjRadian) * pObjr;
        pObj.x = pObjx;
        pObj.y = pObjy;

        if (!pObj.parent) return;
        var p1Obj = _.find(data, function (obj) {
            return obj.id == pObj.parent;
        });
        p1Obj.x = pObjx - pObj.value * 2;
        p1Obj.y = pObjy;
    }

    /**
     * 排序模式
     */
    function avgSortCoordinate(option) {
        var series = option.series[0], data = series.data, links = series.links;

        var mainObj = _.find(data, function (obj) {
            return obj.value == 60;
        });
        if (!mainObj)    return;
        var x = mainObj.x = parseInt(chartObj.getWidth() / 4), y = mainObj.y = parseInt(chartObj.getHeight() / 2);
        var obj3Len = 0, radius = series.force.repulsion, obj3Idx = 0;
        var obj2s = _.filter(data, function (obj2) {
            if (obj2.parent == mainObj.id) {
                var obj3s = _.filter(data, function (obj3) {
                    return obj3.parent == obj2.id;
                });
                obj3Len += obj3s.length;
            }
            return obj2.parent == mainObj.id;
        });
        var obj3h = parseInt(chartObj.getHeight() / (obj3Len + obj2s.length));
        $.each(obj2s, function (i, obj2) {
            if (obj3Len == 0) {
                var obj2h = parseInt(chartObj.getHeight() / obj2s.length);
                var obj2x = x * 2;
                var obj2y = i * obj2h;
                obj2.x = obj2x;
                obj2.y = obj2y;
                return true;
            }
            var obj3s = _.filter(data, function (obj3) {
                return obj3.parent == obj2.id;
            });
            var len = obj3s.length, bool = len % 2 == 0, obj3Middle = len / 2, obj3Section = 0;
            $.each(obj3s, function (j, obj3) {
                var obj3x = x * 3;
                var obj3y = obj3Idx * obj3h;
                obj3.x = obj3x;
                obj3.y = obj3y;

                if (bool && (obj3Middle == j || (obj3Middle + 1) == j)) {  //假如子节点个数刚好够除，
                    obj3Section += obj3y;
                } else if (!bool && (obj3Middle - 0.5) == j) {
                    obj3Section = obj3y * 2;
                }

                obj3Idx++;
            });
            var obj2x = x * 2;
            var obj2y = obj3Section / 2;
            obj2.x = obj2x;
            obj2.y = obj2y;
            obj3Idx++;
        });
        if (!mainObj.parent) return;

        var pObj = _.find(data, function (obj) {
            return obj.id == mainObj.parent;
        });
        var pObjr = radius + radius / 3;
        var pObjRadian = (2 * Math.PI / 360) * 225;
        var pObjx = x + Math.sin(pObjRadian) * pObjr;
        var pObjy = y - Math.cos(pObjRadian) * pObjr;
        pObj.x = pObjx;
        pObj.y = pObjy;

        if (!pObj.parent) return;
        var p1Obj = _.find(data, function (obj) {
            return obj.id == pObj.parent;
        });
        p1Obj.x = pObjx - pObj.value * 2;
        p1Obj.y = pObjy;
    }

    function getNoneIdx(arr, len) {
        var idx = parseInt(Math.random() * len) + 1;
        if (_.indexOf(arr, idx) == -1) return idx;
        else return getNoneIdx(arr, len);
    }

    function getNodeAngle(len) {
        var angleArr = [90, 180, 225, 270, 315], numArr = [3, 6, 12, 18, 24];
        var rs = 0;
        _.each(numArr, function (num, i) {
            if (rs == 0 && num >= len) {
                rs = angleArr[i];
            }
        });
        return rs;
    }

    function getNodeDeviant(len) {
        var deviantArr = [45, 90, 112.5, 135, 157.5], numArr = [3, 6, 12, 18, 24];
        var rs = 0;
        _.each(numArr, function (num, i) {
            if (rs == 0 && num >= len) {
                rs = deviantArr[i];
            }
        });
        return rs;
    }

    function getNodeLinkLength(len) {
        var linkLengthArr = [45, 45, 80, 100, 150], numArr = [3, 6, 12, 18, 24];
        var rs = 0;
        _.each(numArr, function (num, i) {
            if (rs == 0 && num >= len) {
                rs = linkLengthArr[i];
            }
        });
        return rs;
    }
});