require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie', 'echarts/chart/gauge', "echarts/chart/scatter",
    'bootstrap', 'jgGrid', 'underscore', 'utils', 'timeLine2', 'searchBox3', 'jquery-ui', 'message', 'form'], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
    	/*page-one 项目总览*/
		getProjectConAndAvgNumUrl: webRoot + '/humanInventory/getProjectConAndAvgNum.do',           //获取本年度项目总数和人均项目数
		getProjectInputOutputNumUrl: webRoot + '/humanInventory/getProjectInputOutputNum.do',           //获取本年度项目投入产出
		getProjectLoadNumUrl: webRoot + '/humanInventory/getProjectLoadNum.do',           //获取本年度项目负荷
		queryInputOutputUrl: webRoot + "/humanInventory/queryInputOutput.do",	//投入产出分析-金额分析/产出投入比
		queryInputOutputMapUrl: webRoot + "/humanInventory/queryInputOutputMap.do",	//投入产出地图
		queryProfitLossProjectUrl: webRoot + "/humanInventory/queryProfitLossProject.do",	//盈亏项目数分析/盈亏金额分析
		getProfitAndLossCountAmountUrl: webRoot + "/humanInventory/getProfitAndLossCountAmount.do",	//盈亏项目明细-盈亏总金额
		getProfitAndLossProjectDetailUrl: webRoot + "/humanInventory/getProfitAndLossProjectDetail.do",	//盈亏项目明细-盈亏项目明细
		queryProjectManpowerUrl: webRoot + "/humanInventory/queryProjectManpower.do",	//项目投入统计-人力统计
		queryProjectInputCostUrl: webRoot + "/humanInventory/queryProjectInputCost.do",	//项目投入统计-费用统计

		/*page-two 明细分析*/
        findLeadingProjectUrl: webRoot + '/humanInventory/findLeadingProject.do',           //主导项目
        findParticipateProjectUrl: webRoot + '/humanInventory/findParticipateProject.do',           //参与项目
        findEmployeeStatisticsUrl: webRoot + '/humanInventory/findEmployeeStatistics.do',           //人员统计表
        getCurrentEmployeeListUrl: webRoot + '/humanInventory/getCurrentEmployeeList.do',           //当前人力投入
        getParticipateProjectDetailUrl: webRoot + '/humanInventory/getParticipateProjectDetail.do',           //参与项目明细
        getManpowerInputByMonthUrl: webRoot + '/humanInventory/getManpowerInputByMonth.do',           //人力环比趋势
        getSubprojectByIdUrl: webRoot + '/humanInventory/getSubprojectById.do',           //子项目明细
        getInputOutputByMonthUrl: webRoot + '/humanInventory/getInputOutputByMonth.do',           //投入产出比
        getFeeDetailByIdUrl: webRoot + '/humanInventory/getFeeDetailById.do',           //费用明细
        getDepartmentInputUrl: webRoot + '/humanInventory/getDepartmentInput.do',           //各部门人力投入
        getJobSeqInputUrl: webRoot + '/humanInventory/getJobSeqInput.do',           //职位序列人力投入
        getRankInputUrl: webRoot + '/humanInventory/getRankInput.do',           //职级人力投入
        getWorkplaceInputUrl: webRoot + '/humanInventory/getWorkplaceInput.do',           //工作地人力投入
//        getChildDataUrl: webRoot + '/manageHome/getChildOrgData.do' 	    //获取子节点数据

        /*page-three 数据导入*/
        queryProjectTypeUrl: webRoot + "/humanInventory/queryProjectType.do",	//项目类型分析
        queryProjectFeeTypeUrl: webRoot + "/humanInventory/queryProjectFeeType.do",	//项目费用类型
        downLoadProjectInfoAndCostUrl: webRoot + "/humanInventory/downLoadProjectInfoAndCost.do",	//下载《项目信息及费用数据》模板
        downLoadProjectPersonExcelUrl: webRoot + "/humanInventory/downLoadProjectPersonExcel.do",	//下载《项目人员数据》模板
        importProjectExcelDatasUrl: webRoot + "/humanInventory/importProjectExcelDatas.do",	//数据导入
        getDbDateUrl: webRoot + "/humanInventory/getDbDate.do"	// 获取数据库时间

    };

    $(win.document.getElementById('tree')).next().show();
    win.setCurrNavStyle();
    var reqOrgId = win.currOrganId;

    var ecConfig = require('echarts/config');
    var pieDefaultColor = ['#5cb7f1', '#01d286', '#e5689b', '#b285c3', '#4682bc', '#8f684b', '#f28e7f', '#fbc370'];
    var fontDefaultColor = ['#333333', '#444444', '#666666', '#999999'];
    var barDefaultColor = ['#5cb7f1', '#6cdcb4'];
    var lineDefaultColor = ['#e4e4e4', '#f5b147'];
    var defaultDatas = {
        projectConNum: 0,
        projectAvgNum: 0,
        projectInputNum: 0,
        projectOutputNum: 0,
        projectNumConText: "年主导项目",
        projectNumAvgText: "年人均项目",
        projectInputText: "年项目投入",
        projectOutputText: "年项目产出",
        curMonth: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
        curQuarter: ['Q1', 'Q2', 'Q3', 'Q4'],
        curHalfYear: ['06', '12'],
        curYear: ['12'],
        date: null
    }
    var inventoryDateType = $('#inventoryDateType').val();

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        var curTopTabId = getActiveTabId(".leftListDiv");
        changeData(curTopTabId);
    };
    //echarts滚动条
    var dataZoom = {
        show: true,
        realtime: true,
        height: 20,
        start: 100,
        end: 60,
        showDetail: false,
        y: 310
    };
    //echarts滚动条显示条件
    var showDataZoomNumber = 4;
    
    //echarts 正常显示
    var normalLabelDefaultColor = {
        show: true,
        textStyle: {
        	color: fontDefaultColor[2]
        }
    }
    //echarts 高亮显示
    var emphasisDefaultColor = {
		label: {
    		show: true,
    		textStyle: {
    			color: fontDefaultColor[2]
    		}
    	}
    }
    
    //提示窗
    $("[data-toggle='tooltip']").tooltip();

    //获取本年度主导项目数量
    var projectConAndAvgNumObj = {
        init: function (organId) {
            var self = this;
            self.getRequestDatas(organId);
        },
        getRequestDatas: function (organId) {
            var self = this;
            var param = {organId: organId};
            $.post(urls.getProjectConAndAvgNumUrl, param, function(data){
            	defaultDatas.projectConNum = data.conNum;
            	defaultDatas.projectAvgNum = Tc.formatFloat(data.avgNum);
            	self.setJspDatas(data);
            	getProjectNumSelect();
            });
        },
        setJspDatas: function (data) {
            $("#projectNumCurrentYear").html(data.year);
            $("#inputOutputCurrentYear").html(data.year);
        }
    }

    //获取本年度项目投入产出
    var projectInputOutputNumObj = {
        init: function (organId) {
            var self = this;
            self.getRequestDatas(organId);
        },
        getRequestDatas: function (organId) {
            var self = this;
            var param = {organId: organId};
            $.post(urls.getProjectInputOutputNumUrl, param, function(data){
            	defaultDatas.projectInputNum = Tc.formatFloat(data.sumInputNum);
            	defaultDatas.projectOutputNum = Tc.formatFloat(data.sumOutputNum);
            	getInputOutputSelect();
            });
        }
    }

    //获取本年度项目负荷
    var projectLoadNumObj = {
        init: function (organId) {
            var self = this;
            self.getRequestDatas(organId);
        },
        getRequestDatas: function (organId) {
            var self = this;
            var param = {organId: organId};
            $.post(urls.getProjectLoadNumUrl, param, function(data){
            	self.setJspData(data);
            });
        },
        setJspData: function (data) {
            $("#projectLoadNum").html(Tc.formatFloat(data.loadNum));
        }
    }

    //设置投入项目、项目产出按钮切换
    var toolbarStyleObj = {
        produceId: "projectNum_toolbar",
        investmentId: "investment_toolbar",
        init: function () {
            var self = this;
            self.setInvestmentStyle(self.investmentId);
            self.setProduceStyle(self.produceId);
        },
        setInvestmentStyle: function (id) {
            checkToolbarStyle(id);
        },
        setProduceStyle: function (id) {
            checkToolbarStyle(id);
        }
    }

    //设置选中状态下本年及本月的数据赋值
    function getProjectNumSelect() {
        var _name = $("#projectNum_toolbar").find(".select").text();
        if (_name == '总数') {
            $("#projectNumText").html(defaultDatas.projectNumConText);
            $("#projectNum").html(defaultDatas.projectConNum);
        } else {
            $("#projectNumText").html(defaultDatas.projectNumAvgText);
            $("#projectNum").html(defaultDatas.projectAvgNum);
        }
    }

    //设置选中状态下本年及本月的数据赋值
    function getInputOutputSelect() {
        var _name = $("#investment_toolbar").find(".select").text();
        if (_name == '投入') {
            $("#inputOutputText").html(defaultDatas.projectInputText);
            $("#projectInputOutputNum").html(Tc.formatDigital(defaultDatas.projectInputNum));
        } else {
            $("#inputOutputText").html(defaultDatas.projectOutputText);
            $("#projectInputOutputNum").html(Tc.formatDigital(defaultDatas.projectOutputNum));
        }
    }

    function checkToolbarStyle(id) {
        $("#" + id + " span").unbind('click').bind('click', function () {
            var par = $(this).parent();
            var _t = this;
            $.each(par.children(), function (i, o) {
                if (this == _t) {
                    $(this).addClass("select");
                    if ($(this).parent().prev().html() == '项目数') {
                        if ($(this).text() == '总数') {
                            $("#projectNumText").html(defaultDatas.projectNumConText);
                            $("#projectNum").html(defaultDatas.projectConNum);
                        } else {
                            $("#projectNumText").html(defaultDatas.projectNumAvgText);
                            $("#projectNum").html(defaultDatas.projectAvgNum);
                        }
                    } else {
                        if ($(this).text() == '投入') {
                            $("#inputOutputText").html(defaultDatas.projectInputText);
                            $("#projectInputOutputNum").html(Tc.formatDigital(defaultDatas.projectInputNum));
                        } else {
                            $("#inputOutputText").html(defaultDatas.projectOutputText);
                            $("#projectInputOutputNum").html(Tc.formatDigital(defaultDatas.projectOutputNum));
                        }
                    }
                } else {
                    $(this).removeClass("select");
                }
            });
        });
    }

    /**
     * 金额分析
     */
    var amountBarOption = {
        legend: {
            y: 'bottom',
            data: ['项目投入', '项目产出']
        },
        grid: {
            x: 55,
            y: 25,
            x2: 15,
            borderWidth: 0
        },
        tooltip: {
        	trigger: 'axis',
        	axisPointer : {
        		type : 'line',
        		lineStyle : {
        			color : fontDefaultColor[3],
        			type : 'dashed',
        			width : 1
        		}
        	},
        	formatter : function(param){
        		var div;
        		if(param[0].value == '-'){
        			div = '<div>' + param[0].name + '</div>'
	            	 	+ '<div>' + param[2].seriesName + ' : ' + param[2].value + '万元</div>'
	            		+ '<div>' + param[3].seriesName + ' : ' + param[3].value + '万元</div>';
        		} else {
        			div = '<div>' + param[0].name + '</div>'
	        			+ '<div>' + param[0].seriesName + ' : ' + param[0].value + '万元</div>'
	        			+ '<div>' + param[1].seriesName + ' : ' + param[1].value + '万元</div>';
        		}
            	return div;
            }
        },
        xAxis: [{
            type: 'category',
            splitLine: false,
            axisLine: {
                lineStyle: {
                	width: 1,
                    color: fontDefaultColor[2]
                }
            },
            axisTick: {
                show: true,
                lineStyle: {
                    color: fontDefaultColor[2]
                }
            },
            axisLabel: {
                interval: 0
            },
            data: []
        }],
        yAxis: [{
            type: 'value',
            splitLine: {
            	show : true,
            	lineStyle : {
            		color : lineDefaultColor[0]
            	}
            },
            axisLine: {
                lineStyle: {
                	width: 1,
                    color: fontDefaultColor[2]
                }
            },
        }],
        calculable: false,
        series: [

            {
                name: '项目投入',
                type: 'bar',
                clickable: false,
                barMaxWidth: 30,
                itemStyle: {
                    normal: {
                        color: barDefaultColor[0],
                        label: normalLabelDefaultColor
                    },
		            emphasis: emphasisDefaultColor
                },
                data: []
            },
            {
                name: '项目产出',
                type: 'bar',
                clickable: false,
                barMaxWidth: 30,
                itemStyle: {
                    normal: {
                        color: barDefaultColor[1],
                        label: normalLabelDefaultColor
                    },
                    emphasis: emphasisDefaultColor
                },
                data: []
            },
            {
                name: '项目投入',
                type: 'line',
                smooth: true,
                symbol: 'circle',
                clickable: false,
                itemStyle: {
                    normal: {
                        color: barDefaultColor[0],
                        label: {
                            show: false
                        }
                    }
                },
                data: []

            },
            {
                name: '项目产出',
                type: 'line',
                smooth: true,
                symbol: 'circle',
                clickable: false,
                itemStyle: {
                    normal: {
                        color: barDefaultColor[1],
                        label: {
                            show: false
                        }
                    }
                },
                data: []

            }]
    };

    var amountObj = {
    	chartId: 'amountChart',
        chart: initEChart("amountChart"),
        option: amountBarOption,
        init: function (organId) {
            var self = this;
            self.getRequestDatas(organId);
        },
        getRequestDatas: function (organId) {
            var self = this;
            clearEChart(self.chart);
            loadingChart(self.chartId);
            var param = {organId: organId};
            $.post(urls.queryInputOutputUrl, param, function(data){
            	self.getOption(data);
            	outputInputPercentObj.init(data);
            });
        },
        getOption: function (data) {
            var self = this;
            if (data.sumDate != undefined && data.sumDate.length > 1) {
            	hideChart(self.chartId);
            	setFormatFloat(data.sumInputCon);
            	setFormatFloat(data.sumOutputCon);
            	setFormatFloat(data.sumInput);
            	setFormatFloat(data.sumOutput);
                self.option.xAxis[0].data = data.sumDate;
                self.option.series[0].data = data.sumInputCon;
                self.option.series[1].data = data.sumOutputCon;
                self.option.series[2].data = data.sumInput;
                self.option.series[3].data = data.sumOutput;
                var val = [];
                val[0] = data.sumInputCon[0];
                val[1] = data.sumOutputCon[0];
                setGridX(val, self.option.grid);
                if(data.sumDate.length > showDataZoomNumber){
                	dataZoom.end = 40;
                	dataZoom.y = 310;
                	self.option.dataZoom = dataZoom;
                	self.option.legend.y = 280;
                	self.option.grid.y2 = 100;
                } else {
                	self.option.dataZoom = null;
                	self.option.legend.y = 320;
                	self.option.grid.y2 = 60;
                }
                self.setOption();
            } else {
                hideChart(self.chartId, true);
            }
        },
        setOption: function () {
            var self = this;
            self.chart.setOption(self.option);
            self.chart.resize();
        }
    }

    /**
     * 投入产出分析-产出投入比
     */
    var outputInputPercentOption = {
        legend: {
            x: 'center',
            y: 'bottom',
            data: ['产出投入比']
        },
        grid: {
            x: 55,
            y: 25,
            x2: 15,
            borderWidth: 0
        },
        tooltip: {
        	trigger: 'axis',
        	axisPointer : {
        		type : 'line',
        		lineStyle : {
        			color : fontDefaultColor[3],
        			type : 'dashed',
        			width : 1
        		}
        	},
        	formatter : function(param){
        		return '<div>' + param[0].name + '</div>'
        				+ '<div>' + param[0].seriesName + ' : ' + param[0].value + '%</div>';
            }
        },
        xAxis: [{
            type: 'category',
            boundaryGap: true,
            axisLine: {
                lineStyle: {
                	width: 1,
                    color: fontDefaultColor[2]
                }
            },
            axisLabel: {
                interval: 0
            },
            splitLine: {
                show: false
            },
            axisTick: {
                show: true,
                lineStyle: {
                    color: fontDefaultColor[2]
                }
            },
            data: []
        }],
        yAxis: [{
            type: 'value',
            splitLine: {
            	show : true,
            	lineStyle : {
            		color : lineDefaultColor[0]
            	}
            },
            axisLine: {
                lineStyle: {
                	width: 1,
                    color: fontDefaultColor[2]
                }
            },
            axisLabel: {
                formatter: '{value} %'
            }
        }],
        series: [{
            name: '产出投入比',
            type: 'line',
            smooth: true,
            symbol: 'circle',
            clickable: false,
            data: [],
            itemStyle: {
                normal: {
                	color: lineDefaultColor[1],
                    label: {
                        show: true,
                        textStyle: {
                        	color: fontDefaultColor[2]
                        },
                        formatter: function (params, ticket, callback) {
                            return params.value + ' %'
                        }
                    }
                }
            }
        }]
    };
    var outputInputPercentObj = {
    	chartId: 'outputInputPercentChart',
        chart: initEChart("outputInputPercentChart"),
        option: outputInputPercentOption,
        init: function (data) {
            var self = this;
            clearEChart(self.chart);
            loadingChart(self.chartId);
            self.getOption(data);
        },
        getOption: function (data) {
            var self = this;
            if (data.sumDate != undefined && data.sumDate.length > 1) {
            	hideChart(self.chartId);
            	setFormatFloat(data.percent);
                self.option.xAxis[0].data = data.sumDate;
                self.option.series[0].data = data.percent;
                setGridX(data.percent, self.option.grid);
                if(data.sumDate.length > showDataZoomNumber){
                	dataZoom.end = 40;
                	dataZoom.y = 310;
                	self.option.dataZoom = dataZoom;
                	self.option.legend.y = 280;
                	self.option.grid.y2 = 100;
                } else {
                	self.option.dataZoom = null;
                	self.option.legend.y = 320;
                	self.option.grid.y2 = 60;
                }
                self.setOption();
            } else {
                hideChart(self.chartId, true);
            }
        },
        setOption: function () {
            var self = this;
            self.chart.setOption(self.option);
            self.chart.resize();
        }
    }

    /**
     * 投入产出地图
     */
    var inputOutputMapOption = {
        grid: {
            x: 15,
            y: 5,
            borderWidth: 0
        },
        tooltip: {
            trigger: 'item',
            showDelay: 0,
            formatter: function (params) {
            	if (params.value.length > 1) {
                	var div = '<div>'+ params.name +'</div>';
                	div += '<div>累计投入: ' + Tc.formatFloat(params.value[0]) + '万元</div>';
                	div += '<div>累计产出: ' + Tc.formatFloat(params.value[1]) + '万元</div>';
                    return div;
                }
            }
        },
        legend: {
            x: 'center',
            y: 315,
            data: ['盈利', '有亏损', '亏损严重']
        },
        xAxis: [{
            name: '投入 (万元 )',
            min: 0,
            nameTextStyle: {
            	fontFamily: 'Microsoft YaHei',
                color: fontDefaultColor[1]
            },
            type: 'value',
            scale: true,
            splitLine: false,
            axisLine: {
                lineStyle: {
                	width: 1,
                    color: fontDefaultColor[2]
                }
            },
            axisLabel: {
                show: false,
                interval: 0,
                formatter: '{value} 万元'
            }
        }],
        yAxis: [{
            min: 0,
            type: 'value',
            scale: true,
            splitLine: false,
            axisLine: {
                lineStyle: {
                	width: 1,
                    color: fontDefaultColor[2]
                }
            },
            axisLabel: {
                show: false,
                interval: 0,
                formatter: '{value} 万元'
            }
        }],
        series: [
            {
                name: '盈利',
                type: 'scatter',
                symbol: 'circle',
                symbolSize: 8,
                data: [],
                itemStyle: {
                    normal: {
                        color: '#00b165',
                        label: {
                            show: true,
                            formatter: function (params) {
                                return params.name;
                            },
                            position: 'bottom'
                        }
                    }
                }
            },
            {
                name: '有亏损',
                type: 'scatter',
                symbol: 'circle',
                symbolSize: 8,
                data: [],
                itemStyle: {
                    normal: {
                        color: '#f09200',
                        label: {
                            show: true,
                            formatter: function (params) {
                                return params.name;
                            },
                            position: 'bottom'
                        }
                    }
                }
            },
            {
                name: '亏损严重',
                type: 'scatter',
                symbol: 'circle',
                symbolSize: 8,
                data: [],
                itemStyle: {
                    normal: {
                        color: '#e7191b',
                        label: {
                            show: true,
                            formatter: function (params) {
                                return params.name;
                            },
                            position: 'bottom'
                        }
                    }
                }
            }]
    };
    var inputOutputMapObj = {
    	chartId: 'inputOutputMapChart',
        chart: initEChart("inputOutputMapChart"),
        option: inputOutputMapOption,
        init: function (organId) {
            var self = this;
            self.getRequestDatas(organId);
        },
        getRequestDatas: function (organId) {
            var self = this;
            clearEChart(self.chart);
            loadingChart(self.chartId);
            var param = {organId: organId};
            $.post(urls.queryInputOutputMapUrl, param, function(data){
            	self.getOption(data);
            });
        },
        getOption: function (data) {
            var self = this;
            if (data.greenDatas != undefined && data.greenDatas.length > 0) {
            	hideChart(self.chartId);
                self.option.xAxis[0].max = data.maxNum + 20;
                self.option.xAxis[0].splitNumber = 10;
                self.option.yAxis[0].max = data.maxNum + 20;
                self.option.yAxis[0].splitNumber = 10;
                self.option.series[0].data = data.greenDatas;
                self.option.series[1].data = data.yellowDatas;
                self.option.series[2].data = data.redDatas;
                self.setOption();
            } else {
                hideChart(self.chartId, true);
            }
        },
        setOption: function () {
            var self = this;
            self.chart.setOption(self.option);
            self.chart.resize();
        }
    }

    /**
     * 盈亏项目数分析
     */
    var profitLossProjectOption = {
        legend: {
            y: 'bottom',
            data: ['盈利项目', '亏损项目']
        },
        grid: {
            x: 25,
            y: 25,
            x2: 15,
            borderWidth: 0
        },
        tooltip: {
        	trigger: 'axis',
        	axisPointer : {
        		type : 'line',
        		lineStyle : {
        			color : fontDefaultColor[3],
        			type : 'dashed',
        			width : 1
        		}
        	},
        	formatter : function(param){
        		var div;
        		if(param[0].value == '-'){
        			div = '<div>' + param[0].name + '</div>'
	            	 	+ '<div>' + param[2].seriesName + ' : ' + param[2].value + '个</div>'
	            		+ '<div>' + param[3].seriesName + ' : ' + param[3].value + '个</div>';
        		} else {
        			div = '<div>' + param[0].name + '</div>'
	        			+ '<div>' + param[0].seriesName + ' : ' + param[0].value + '个</div>'
	        			+ '<div>' + param[1].seriesName + ' : ' + param[1].value + '个</div>';
        		}
            	return div;
            }
        },
        xAxis: [{
            type: 'category',
            splitLine: false,
            axisLabel: {
                interval: 0
            },
            axisTick: {
                show: true,
                lineStyle: {
                    color: fontDefaultColor[2]
                }
            },
            axisLine: {
                lineStyle: {
                	width: 1,
                    color: fontDefaultColor[2]
                }
            },
            data: []
        }],
        yAxis: [{
            type: 'value',
            splitLine: {
            	lineStyle: {
                    color: lineDefaultColor[0]
                }
            },
            axisLine: {
                lineStyle: {
                	width: 1,
                    color: fontDefaultColor[2]
                }
            }
        }],
        series: [

            {
                name: '盈利项目',
                type: 'bar',
                data: [],
                barMaxWidth: 30,
                clickable: false,
                itemStyle: {
                    normal: {
                        color: barDefaultColor[0],
                        label: normalLabelDefaultColor
                    },
                    emphasis: emphasisDefaultColor
                }
            },
            {
                name: '亏损项目',
                type: 'bar',
                data: [],
                barMaxWidth: 30,
                clickable: false,
                itemStyle: {
                    normal: {
                        color: barDefaultColor[1],
                        label: normalLabelDefaultColor
                    },
                    emphasis: emphasisDefaultColor
                }
            },
            {
                name: '盈利项目',
                type: 'line',
                smooth: true,
                symbol: 'circle',
                itemStyle: {
                    normal: {
                        color: barDefaultColor[0],
                        label: {
                            show: false
                        }
                    }
                },
                data: []

            },
            {
                name: '亏损项目',
                type: 'line',
                smooth: true,
                symbol: 'circle',
                itemStyle: {
                    normal: {
                        color: barDefaultColor[1],
                        label: {
                            show: false
                        }
                    }
                },
                clickable: true,
                data: []

            }]
    };
    var profitLossProjectObj = {
    	chartId: 'profitLossProjectChart',
        chart: initEChart("profitLossProjectChart"),
        option: profitLossProjectOption,
        init: function (organId) {
            var self = this;
            self.getRequestDatas(organId);
        },
        getRequestDatas: function (organId) {
            var self = this;
            clearEChart(self.chart);
            loadingChart(self.chartId);
            var param = {organId: organId};
            $.post(urls.queryProfitLossProjectUrl, param, function(data){
            	self.getOption(data);
            	profitLossAmountObj.init(data);
            });
        },
        getOption: function (data) {
            var self = this;
            if (data.sumDate != undefined && data.sumDate.length > 1) {
            	hideChart(self.chartId);
                self.option.xAxis[0].data = data.sumDate;
                self.option.series[0].data = data.totalMoreCon;
                self.option.series[1].data = data.totalLessCon;
                self.option.series[2].data = data.sumMoreCon;
                self.option.series[3].data = data.sumLessCon;
                var val = [];
                val[0] = data.totalMoreCon[0];
                val[1] = data.totalLessCon[0];
                setGridX(val, self.option.grid);
                if(data.sumDate.length > showDataZoomNumber){
                	dataZoom.end = 40;
                	dataZoom.y = 280;
                	self.option.dataZoom = dataZoom;
                	self.option.legend.y = 250;
                	self.option.grid.y2 = 100;
                } else {
                	self.option.dataZoom = null;
                	self.option.legend.y = 290;
                	self.option.grid.y2 = 60;
                }
                self.setOption();
            } else {
                hideChart(self.chartId, true);
            }
        },
        setOption: function () {
            var self = this;
            self.chart.setOption(self.option);
            self.chart.resize();
            self.chart.on(ecConfig.EVENT.CLICK, self.eConsole);
        },
        eConsole: function (param) {
            if (typeof param.seriesIndex != 'undefined') {
                if (param.value != 0) {
                    $('#profitLossProjectDetailModal').modal('show');
                    $('#profitLossProjectDetailModal').on('shown.bs.modal', function () {
                        // 执行一些动作...
                        projectNumDetailObj.setTabsSelectFun(param.seriesIndex);
                        projectNumDetailObj.getProfitAndLossCountAmount(reqOrgId, param.name);
                        projectNumDetailObj.getProfitAndLossProjectDetail(reqOrgId, param.name);
                    });
                    $('#profitLossProjectDetailModal').on('hidden.bs.modal', function () {
                        $("#profitProjectGrid tbody").empty();
                        $("#lossProjectGrid tbody").empty();
                        $("#profitLossProjectDetailYear").html(" ");
                        $("#profitLossProjectDetailMonth").html(" ");
                        $("#profitCountAmount").html("0");
                        $("#lossCountAmount").html("0");
                        $("#profitProjectNum").html("0");
                        $("#lossProjectNum").html("0");
                        $('#profitLossProjectDetailModal').off();
                    });
                }
            }
        }
    }

    /**
     * 盈亏项目分析 - 盈利总金额，亏损总金额
     */
    var projectNumDetailObj = {
        setTabsSelectFun: function (index) {
            if (index == 2) {
                $("#profitLossProjectDetailModal ul li:eq(0)").addClass('active').siblings().removeClass('active');
                $('#profitProjectTab').addClass('active');
                $('#lossProjectTab').removeClass('active');
            } else if (index == 3) {
                $("#profitLossProjectDetailModal ul li:eq(1)").addClass('active').siblings().removeClass('active');
                $('#profitProjectTab').removeClass('active');
                $('#lossProjectTab').addClass('active');
            }
        },
        getProfitAndLossCountAmount: function (organId, data) {
            var self = this;
            var param = {organId: organId, month: data};
            $.post(urls.getProfitAndLossCountAmountUrl, param, function(data){
            	$("#profitLossProjectDetailYear").html(data.year);
            	$("#profitLossProjectDetailMonth").html(data.month);
            	$("#profitCountAmount").html(Tc.formatFloat(self.changeNullToZero(data.map.pNum)));
            	$("#lossCountAmount").html(Tc.formatFloat(self.changeNullToZero(data.map.lNum)));
            });
        },
        getProfitAndLossProjectDetail: function (organId, data) {
            var self = this;
            var param = {organId: organId, month: data};
            $.post(urls.getProfitAndLossProjectDetailUrl, param, function(data){
            	$("#profitProjectNum").html(data.pNum);
            	$("#lossProjectNum").html(data.lNum);
            	self.loadGrid(data.pDetail, "profitProjectGrid");
            	self.loadGrid(data.lDetail, "lossProjectGrid");
            });
        },
        loadGrid: function (data, id) {
            $("#" + id + " tbody").empty();
            var tr = "";
            $.each(data, function (index, object) {
                tr += "<tr><td class='tbody-tr-td-first'>" + object.projectName + "</td><td class='tbody-tr-td'>" 
                	+ Tc.formatFloat(object.input) + "</td><td class='tbody-tr-td'>"
                    + Tc.formatFloat(object.output) + "</td><td class='tbody-tr-td'>" 
                    + Tc.formatFloat(object.gain) + "</td><td class='tbody-tr-td'>" 
                    + addPlusOrMinusSign(Tc.formatFloat(object.sumGain)) + "</td></tr>";
            });
            $("#" + id + " tbody").append(tr);
        },
        changeNullToZero: function (data) {
            if (data != null && data != '' && data != 'null') {
                return data;
            } else {
                return 0;
            }
        }
    }

    /**
     * 判断数值，前面补充'+'或'-'
     */
    function addPlusOrMinusSign(data) {
        var mes;
        if (data > 0) {
            mes = "+" + data;
        } else {
            mes = data;
        }
        return mes;
    }

    /**
     * 盈亏金额分析
     */
    var profitLossAmountOption = {
        legend: {
            y: 'bottom',
            data: ['盈利金额', '亏损金额']
        },
        grid: {
            x: 55,
            y: 25,
            x2: 15,
            borderWidth: 0
        },
        tooltip: {
        	trigger: 'axis',
        	axisPointer : {
        		type : 'line',
        		lineStyle : {
        			color : fontDefaultColor[3],
        			type : 'dashed',
        			width : 1
        		}
        	},
        	formatter : function(param){
        		var div;
        		if(param[0].value == '-'){
        			div = '<div>' + param[0].name + '</div>'
	            	 	+ '<div>' + param[2].seriesName + ' : ' + param[2].value + '万元</div>'
	            		+ '<div>' + param[3].seriesName + ' : ' + param[3].value + '万元</div>';
        		} else {
        			div = '<div>' + param[0].name + '</div>'
	        			+ '<div>' + param[0].seriesName + ' : ' + param[0].value + '万元</div>'
	        			+ '<div>' + param[1].seriesName + ' : ' + param[1].value + '万元</div>';
        		}
            	return div;
            }
        },
        xAxis: [{
            type: 'category',
            splitLine: false,
            axisLabel: {
                interval: 0
            },
            axisTick: {
                show: true,
                lineStyle: {
                    color: fontDefaultColor[2]
                }
            },
            axisLine: {
                lineStyle: {
                	width: 1,
                    color: fontDefaultColor[2]
                }
            },
            data: []
        }],
        yAxis: [{
            type: 'value',
            splitLine: {
            	lineStyle: {
                    color: lineDefaultColor[0]
                }
            },
            axisLine: {
                lineStyle: {
                	width: 1,
                    color: fontDefaultColor[2]
                }
            }
        }],
        series: [

            {
                name: '盈利金额',
                type: 'bar',
                clickable: false,
                barMaxWidth: 30,
                data: [],
                itemStyle: {
                    normal: {
                        color: barDefaultColor[0],
                        label: normalLabelDefaultColor
                    },
                    emphasis: emphasisDefaultColor
                }
            },
            {
                name: '亏损金额',
                type: 'bar',
                clickable: false,
                barMaxWidth: 30,
                data: [],
                itemStyle: {
                    normal: {
                        color: barDefaultColor[1],
                        label: normalLabelDefaultColor
                    },
                    emphasis: emphasisDefaultColor
                }
            },
            {
                name: '盈利金额',
                type: 'line',
                smooth: true,
                symbol: 'circle',
                itemStyle: {
                    normal: {
                        color: barDefaultColor[0],
                        label: {
                            show: false
                        }
                    }
                },
                data: []

            },
            {
                name: '亏损金额',
                type: 'line',
                smooth: true,
                symbol: 'circle',
                itemStyle: {
                    normal: {
                        color: barDefaultColor[1],
                        label: {
                            show: false
                        }
                    }
                },
                clickable: true,
                data: []

            }]
    };
    var profitLossAmountObj = {
    	chartId: 'profitLossAmountChart',
        chart: initEChart("profitLossAmountChart"),
        option: profitLossAmountOption,
        init: function (data) {
            var self = this;
            clearEChart(self.chart);
            loadingChart(self.chartId);
            self.getOption(data);
        },
        getOption: function (data) {
            var self = this;
            if (data.sumDate.length > 1) {
            	hideChart(self.chartId);
            	setFormatFloat(data.totalMoreGain);
            	setFormatFloat(data.totalLessGain);
            	setFormatFloat(data.sumMoreGain);
            	setFormatFloat(data.sumLessGain);
                self.option.xAxis[0].data = data.sumDate;
                self.option.series[0].data = data.totalMoreGain;
                self.option.series[1].data = data.totalLessGain;
                self.option.series[2].data = data.sumMoreGain;
                self.option.series[3].data = data.sumLessGain;
                var val = [];
                val[0] = data.totalMoreGain[0];
                val[1] = data.totalLessGain[0];
                setGridX(val, self.option.grid);
                if(data.sumDate.length > showDataZoomNumber){
                	dataZoom.end = 40;
                	dataZoom.y = 280;
                	self.option.dataZoom = dataZoom;
                	self.option.legend.y = 250;
                	self.option.grid.y2 = 100;
                } else {
                	self.option.dataZoom = null;
                	self.option.legend.y = 290;
                	self.option.grid.y2 = 60;
                }
                self.setOption();
            } else {
                hideChart(self.chartId, true);
            }
        },
        setOption: function () {
            var self = this;
            self.chart.setOption(self.option);
            self.chart.resize();
            self.chart.on(ecConfig.EVENT.CLICK, self.eConsole);
        },
        eConsole: function (param) {
            if (typeof param.seriesIndex != 'undefined') {
                if (param.value != 0) {
                    $('#profitLossProjectDetailModal').modal('show');
                    $('#profitLossProjectDetailModal').on('shown.bs.modal', function () {
                        // 执行一些动作...
                        projectNumDetailObj.setTabsSelectFun(param.seriesIndex);
                        projectNumDetailObj.getProfitAndLossCountAmount(reqOrgId, param.name);
                        projectNumDetailObj.getProfitAndLossProjectDetail(reqOrgId, param.name);
                    });
                    $('#profitLossProjectDetailModal').on('hidden.bs.modal', function () {
                        $("#profitProjectGrid tbody").empty();
                        $("#lossProjectGrid tbody").empty();
                        $("#profitLossProjectDetailYear").html(" ");
                        $("#profitLossProjectDetailMonth").html(" ");
                        $("#profitCountAmount").html("0");
                        $("#lossCountAmount").html("0");
                        $("#profitProjectNum").html("0");
                        $("#lossProjectNum").html("0");
                        $('#profitLossProjectDetailModal').off();
                    });
                }
            }
        }
    }

    /**
     * 项目投入统计-费用统计
     */
    var trainCostOption = {
        grid: {
            x: 55,
            y: 25,
            x2: 15,
            borderWidth: 0
        },
        tooltip: {
        	trigger: 'axis',
        	axisPointer : {
        		type : 'line',
        		lineStyle : {
        			color : fontDefaultColor[3],
        			type : 'dashed',
        			width : 1
        		}
        	},
        	formatter : function(param){
        		var div;
        		if(param[0].value == '-'){
        			div = '<div>' + param[0].name + '</div>'
	            	 	+ '<div>' + param[1].seriesName + ' : ' + param[1].value + '万元</div>';
        		} else {
        			div = '<div>' + param[0].name + '</div>'
	        			+ '<div>' + param[0].seriesName + ' : ' + param[0].value + '万元</div>';
        		}
            	return div;
            }
        },
        xAxis: [{
            type: 'category',
            splitLine: false,
            axisTick: {
                show: true,
                lineStyle: {
                    color: fontDefaultColor[2]
                }
            },
            axisLine: {
                lineStyle: {
                	width: 1,
                    color: fontDefaultColor[2]
                }
            },
            axisLabel: {
                interval: 0
            },
            data: []
        }],
        yAxis: [{
            type: 'value',
            splitLine: {
            	lineStyle: {
                    color: lineDefaultColor[0]
                }
            },
            axisLine: {
                lineStyle: {
                	width: 1,
                    color: fontDefaultColor[2]
                }
            }
        }],
        series: [

            {
            	name: '项目投入',
                type: 'bar',
                clickable: false,
                data: [],
                barMaxWidth: 30,
                itemStyle: {
                    normal: {
                        color: barDefaultColor[0],
                        label: normalLabelDefaultColor
                    },
                    emphasis: emphasisDefaultColor
                },
            },
            {
            	name: '项目投入',
                type: 'line',
                smooth: true,
                symbol: 'circle',
                symbolSize: 0,
                markPoint: {
                    clickable: false,
                    symbolSize: 16,
                    data: [],
                    itemStyle: {
                        normal: {
                            color: lineDefaultColor[1],
                        }
                    },
                },
                itemStyle: {
                    normal: {
                        color: lineDefaultColor[1],
                        label: normalLabelDefaultColor
                    }
                },
                data: []

            }]
    }
    var trainCostObj = {
    	chartId: 'trainCostChart',
        chart: initEChart("trainCostChart"),
        option: trainCostOption,
        init: function (organId) {
            var self = this;
            self.getRequestDatas(organId);
        },
        getRequestDatas: function (organId) {
            var self = this;
            clearEChart(self.chart);
            loadingChart(self.chartId);
            var param = {organId: organId};
            $.post(urls.queryProjectInputCostUrl, param, function(data){
            	self.getOption(data);
            });
        },
        getOption: function (data) {
            var self = this;
            if (data.sumDate.length > 1) {
            	hideChart(self.chartId);
                var len = data.sumInput.length;
                var markPointData = {
                    value: data.sumInput[len - 1],
                    xAxis: len - 1,
                    yAxis: data.sumInput[len - 1]
                };
                var arrPoint = new Array();
                arrPoint.push(markPointData);
                setFormatFloat(data.totalInput);
                setFormatFloat(data.sumInput);
                self.option.xAxis[0].data = data.sumDate;
                self.option.series[0].data = data.totalInput;
                self.option.series[1].data = data.sumInput;
//                self.option.series[1].markPoint.data = arrPoint;
                var val = [];
                val[0] = data.totalInput[0];
                setGridX(val, self.option.grid);
                if(data.sumDate.length > showDataZoomNumber){
                	dataZoom.end = 40;
                	dataZoom.y = 310;
                	self.option.dataZoom = dataZoom;
                	self.option.grid.y2 = 80;
                } else {
                	self.option.dataZoom = null;
                	self.option.grid.y2 = 40;
                }
                self.setOption();
            } else {
                hideChart(self.chartId, true);
            }
        },
        setOption: function () {
            var self = this;
            self.chart.setOption(self.option);
            self.chart.resize();
        }
    }

    /**
     * 项目投入统计-人力统计
     */
    var projectManpowerOption = {
        grid: {
            x: 55,
            y: 25,
            x2: 15,
            borderWidth: 0
        },
        tooltip: {
        	trigger: 'axis',
        	axisPointer : {
        		type : 'line',
        		lineStyle : {
        			color : fontDefaultColor[3],
        			type : 'dashed',
        			width : 1
        		}
        	},
        	formatter : function(param){
        		var div;
        		if(param[0].value == '-'){
        			div = '<div>' + param[0].name + '</div>'
	            	 	+ '<div>' + param[1].seriesName + ' : ' + param[1].value + '人天</div>';
        		} else {
        			div = '<div>' + param[0].name + '</div>'
	        			+ '<div>' + param[0].seriesName + ' : ' + param[0].value + '人天</div>';
        		}
            	return div;
            }
        },
        xAxis: [{
            type: 'category',
            splitLine: false,
            axisLine: {
                lineStyle: {
                	width: 1,
                    color: fontDefaultColor[2]
                }
            },
            axisTick: {
                show: true,
                lineStyle: {
                    color: fontDefaultColor[2]
                }
            },
            axisLabel: {
                interval: 0
            },
            data: [],
        }],
        yAxis: [{
            type: 'value',
            splitLine: {
            	lineStyle: {
                    color: lineDefaultColor[0]
                }
            },
            axisLine: {
                lineStyle: {
                	width: 1,
                    color: fontDefaultColor[2]
                }
            }
        }],
        series: [
            {
            	name: '项目投入',
                type: 'bar',
                clickable: false,
                data: [],
                barMaxWidth: 30,
                itemStyle: {
                    normal: {
                        color: barDefaultColor[0],
                        label: normalLabelDefaultColor
                    },
                    emphasis: emphasisDefaultColor
                },
            },
            {
            	name: '项目投入',
                type: 'line',
                smooth: true,
                clickable: false,
                symbol: 'circle',
                symbolSize: 0,
                markPoint: {
                    data: [],
                    symbolSize: 16,
                    itemStyle: {
                        normal: {
                            color: lineDefaultColor[1]
                        }
                    },
                },
                itemStyle: {
                    normal: {
                        color: lineDefaultColor[1],
                        label: normalLabelDefaultColor
                    }
                },
                data: []

            }]
    }
    var projectManpowerObj = {
    	chartId: 'projectManpowerChart',
        chart: initEChart("projectManpowerChart"),
        option: projectManpowerOption,
        init: function (organId) {
            var self = this;
            self.getRequestDatas(organId);
        },
        getRequestDatas: function (organId) {
            var self = this;
            clearEChart(self.chart);
            loadingChart(self.chartId);
            var param = {organId: organId};
            $.post(urls.queryProjectManpowerUrl, param, function(data){
            	self.getOption(data);
            });
        },
        getOption: function (data) {
            var self = this;
            if (data.sumDate.length > 1) {
            	hideChart(self.chartId);
                var len = data.sumInput.length;
                var markPointData = {
                    value: data.sumInput[len - 1],
                    xAxis: len - 1,
                    yAxis: data.sumInput[len - 1]
                };
                var arrPoint = new Array();
                arrPoint.push(markPointData);
                setFormatFloat(data.totalInput);
                setFormatFloat(data.sumInput);
                self.option.xAxis[0].data = data.sumDate;
                self.option.series[0].data = data.totalInput;
                self.option.series[1].data = data.sumInput;
//                self.option.series[1].markPoint.data = arrPoint;
                var val = [];
                val[0] = data.totalInput[0];
                setGridX(val, self.option.grid);
                if(data.sumDate.length > showDataZoomNumber){
//                	dataZoom.end = 70;
                	dataZoom.y = 310;
                	self.option.dataZoom = dataZoom;
                	self.option.grid.y2 = 80;
                } else {
                	self.option.dataZoom = null;
                	self.option.grid.y2 = 40;
                }
                self.setOptionFun();
            } else {
                hideChart(self.chartId, true);
            }
        },
        setOptionFun: function () {
            var self = this;
            self.chart.setOption(self.option);
            self.chart.resize();
        }
    }

    /**
     * 项目类型分析
     */
    var projectTypeOption = {
		tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} 个"
	    },
        series: [
            {
                name: '项目类型',
                type: 'pie',
                radius: '70%',
                clickable: false,
                itemStyle: {
                    normal: {
                    	label: normalLabelDefaultColor,
                    	labelLine: {
                            lineStyle: {
                                length: 1
                            }
                        }
                    }
                },
                data: []
            }
        ],
        color: pieDefaultColor
    }
    var projectTypeObj = {
    	chartId: 'projectTypeChart',
        newLegendId: "#projectTypeLegend",
        newLegendId2: "#projectTypeLegend2",
        chart: initEChart("projectTypeChart"),
        option: projectTypeOption,
        init: function (organId) {
            var self = this;
            self.getRequestDatas(organId);
        },
        getRequestDatas: function (organId) {
            var self = this;
            clearEChart(self.chart);
            loadingChart(self.chartId);
            $(self.newLegendId2 + " label").remove();
            var param = {organId: organId};
            $.post(urls.queryProjectTypeUrl, param, function(data){
            	self.getOption(data);
            });
        },
        getOption: function (data) {
            var self = this;
            if (data.typeNames.length > 0) {
            	hideChart(self.chartId);
            	$.each(data.typeDatas, function(ind, obj){
            		obj.name = obj.name.split(',')[0];
            	});
                self.option.series[0].data = data.typeDatas;
//                self.setNewLegend(self.newLegendId);
                self.setNewLegend2(self.newLegendId2, data.typeNames);
                self.setOption();
            } else {
                $(".typePieLegend").remove();
                hideChart(self.chartId, true);
            }
        },
        setOption: function () {
            var self = this;
            self.chart.setOption(self.option);
            self.chart.resize();
        },
        setNewLegend: function (id) {
            $(".typePieLegend").remove();
            var _div = "<span class='.typePieLegend'>（单位：个）</span>";
            $(id).append(_div);
        },
        setNewLegend2: function (id, data) {
        	var _div = "";
        	$.each(data, function (ind, obj) {
        		_div += "<label class='margin0 marginRight7 imgLabel' style='background: " + pieDefaultColor[ind] 
        			+ ";'> </label>&nbsp;<label class='margin0 marginRight20'>" + data[ind] + "</label>";
        	});
        	$(id).append(_div);
        }
    }

    /**
     * 设置x轴标签显示方向
     * @param option echart对象
     * @param xAxisLabel x轴标签
     */
    function changeXAxisLabelRotate(option, xAxisLabel) {
        if (xAxisLabel.length > 6) {
            option.xAxis[0].axisLabel.rotate = 45;
        } else {
            option.xAxis[0].axisLabel.rotate = 0;
        }
    }

    /**
     * 管理建议与备忘
     * @type {{init: timeLineObj.init, getOption: timeLineObj.getOption}}
     */
    var timeLineObj = {
        init: function (organId) {
            var self = this;
            self.organizationId = organId;
            $('#timeLine').timeLine(self.getOption());	//初始化
        },
        getOption: function () {
            var organizationId = this.organizationId;
            var quotaId = $('#quotaId').val();
            //参数配置
            var options = {
                quotaId: quotaId,
                organId: organizationId
            }
            return options;
        }
    }

    /***
     * 主导项目
     */
    var tableHeight = 310;
    var leadingProjectOption = {
        url: urls.findLeadingProjectUrl,
        datatype: "json",
        postData: {},
        mtype: 'POST',
        altRows: true,//设置表格行的不同底色
        autowidth: true,
        height: tableHeight,
        colNames: ['项目名称', '负责人', '本年人力', '本月人力', '上月人力', '人力变化率', '本年投入费用', '本月投入费用', '上月投入费用', '费用变化率', '累计利润', '本月利润', '上月利润', '利润变化率', '项目开始时间', '项目结束时间', '项目进度'],
        colModel: [
            {name: 'projectName', sortable: false, fixed: true, width: 100, align: 'center', editable: false},
            {name: 'empName', sortable: false, fixed: true, width: 70, align: 'center', editable: false},
            {
                name: 'manpowerInYear', sortable: false, fixed: true, width: 60, align: 'center', editable: false,
                formatter: function (value, options, row) {
                    if (value == 0) {
                        return "0";
                    }
                    return "<a href='javascript:void(0)' data-key='" + row.projectId + "' class='manpowerInYear_col' value='" +
                        value + "' key-projectName='" + row.projectName + "' key-startDate='" + row.startDate + "' key-endDate='" + row.endDate + "'>" + Tc.formatFloat(value) + "</a>";
                }
            },
            {name: 'manpowerInMonth', sortable: false, fixed: true, width: 60, align: 'center', editable: false,
            	formatter: function (value, options, row) {
            		return Tc.formatFloat(value);
            	}
            },
            {name: 'manpowerLastMonth', sortable: false, fixed: true, width: 60, align: 'center', editable: false,
            	formatter: function (value, options, row) {
            		return Tc.formatFloat(value);
            	}
            },
            {
                name: 'manpowerChangeRate', sortable: false, fixed: true, width: 60, align: 'center', editable: false,
                formatter: function (value, options, row) {
                    if (row.manpowerLastMonth == 0 || row.manpowerLastMonth == null) {
                        return '-';
                    }
                    if (row.manpowerInMonth == 0 || row.manpowerInMonth == null) {
                        return '-100%';
                    }
                    return Tc.formatFloat(value * 100) + '%';
                }
            },
            {
                name: 'feeYearInput', sortable: false, fixed: true, width: 60, align: 'center', editable: false,
                formatter: function (value, options, row) {
                    if (value == 0) {
                        return "0";
                    }
                    return "<a href='javascript:void(0)' data-key='" + row.projectId + "' class='feeYearInput_col' value='" + value +
                        "' key-projectName='" + row.projectName + "' key-startDate='" + row.startDate + "' key-endDate='" + row.endDate + "'>" + Tc.formatFloat(value) + "</a>";
                }
            },
            {
                name: 'feeMonthInput', sortable: false, fixed: true, width: 60, align: 'center', editable: false,
                formatter: function (value) {
                    if (value != null) {
                        return Tc.formatFloat(value);
                    }
                }
            },
            {
                name: 'feeLastMonthInput', sortable: false, fixed: true, width: 60, align: 'center', editable: false,
                formatter: function (value) {
                    if (value != null) {
                        return Tc.formatFloat(value);
                    }
                }
            },
            {
                name: 'feeChangeRate', sortable: false, fixed: true, width: 60, align: 'center', editable: false,
                formatter: function (value, options, row) {
                    if (row.feeLastMonthInput == 0 || row.feeLastMonthInput == null) {
                        return '-';
                    }
                    if (row.feeMonthInput == 0 || row.feeMonthInput == null) {
                        return '-100%';
                    }
                    return Tc.formatFloat(value * 100) + '%';
                }
            },
            {
                name: 'gainInYear', sortable: false, fixed: true, width: 60, align: 'center', editable: false,
                formatter: function (value) {
                    if (value != null) {
                        return Tc.formatFloat(value);
                    }
                }
            },
            {
                name: 'gainInMonth', sortable: false, fixed: true, width: 60, align: 'center', editable: false,
                formatter: function (value) {
                    if (value != null) {
                        return Tc.formatFloat(value);
                    }
                }
            },
            {
                name: 'gainLastMonth', sortable: false, fixed: true, width: 60, align: 'center', editable: false,
                formatter: function (value) {
                    if (value != null) {
                        return Tc.formatFloat(value);
                    }
                }
            },
            {
                name: 'gainChangeRate', sortable: false, fixed: true, width: 60, align: 'center', editable: false,
                formatter: function (value, options, row) {
                    if (row.gainLastMonth == 0 || row.gainLastMonth == null) {
                        return '-';
                    }
                    if (row.gainInMonth == 0 || row.gainInMonth == null) {
                        return '-100%';
                    }
                    return Tc.formatFloat(value * 100) + '%';
                }
            },
            {
                name: 'startDate', sortable: false, fixed: true, width: 80, align: 'center', editable: false,
                formatter: function (value) {
                    if (value == null || value == '') {
                        return '-';
                    }
                    return value;
                }
            },
            {
                name: 'endDate', sortable: false, fixed: true, width: 80, align: 'center', editable: false,
                formatter: function (value) {
                    if (value == null || value == '') {
                        return '-';
                    }
                    return value;
                }
            },
            {name: 'projectProgress', sortable: false, fixed: true, width: 80, align: 'center', editable: false}
        ],
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: "#leadingProjectSel",
        rowHeight: 36,
        styleUI: 'Bootstrap',
        loadComplete: function (xhr) {
            var $tabs = $('#manpowerTabs');
            var projectId = "";
            $tabs.find('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
                var $this = $(e.target);
                var key = $this.attr('aria-controls');
                var thisTab = $('#' + key);
                if (thisTab.hasClass("in active"))return;
                $this.parents(".modal-body").find(".tab-pane").removeClass("in active");
                thisTab.addClass("in active");
                if (key == 'tabpanel2') {
                    manpowerInputObj.resizeChart();
                } else if (key == 'tabpanel3') {
                    departmentInputPieObj.resizeChart();
                    jobSeqInputObj.resizeChart();
                    rankInputObj.resizeChart();
                    workplaceInputObj.resizeChart();
                } else if (key == 'tabpanel4') {
                    subprojectTreeGridObj.resizeGrid();
                } else {
                    currentEmployeeDetailObj.resizeGrid();
                }
            });

            var $tabs = $('#feeInputTabs');
            $tabs.find('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
                var $this = $(e.target);
                var key = $this.attr('aria-controls');
                var thisTab = $('#' + key);
                if (thisTab.hasClass("in active"))return;
                $this.parents(".modal-body").find(".tab-pane").removeClass("in active");
                thisTab.addClass("in active");

                if (key == 'feeInput2') {
                    inputOutputRatioObj.resizeChart();
                } else if (key == 'feeInput3') {
                    $("#feeDetailTreegrid").setGridWidth($("#index-son-feedetailgrid-body").width());
                } else {
                    inputOutputChartObj.resizeChart();
                }
            });
            $('.manpowerInYear_col').unbind().bind('click', function () {
                var $this = $(this);
                projectId = $this.data('key');
                var beginTime = $this.attr('key-startDate');
                	beginTime = beginTime.substring(0, 4) + '.' + beginTime.substring(5, 7);
                var endTime = $this.attr('key-endDate');
                var projectName = $this.attr('key-projectName');

                $('#manpowerInYearModal').modal('show');
                $('#manpowerInYearModal').on('shown.bs.modal', function(){
                	
                	$('#manpowerBeginDate').html(beginTime);
                	if (endTime == "null" || endTime == "") {
                		$('#manpowerEndDate').html('');
                		$('#manpowerLine').hide();
                	} else {
                		$('#manpowerLine').show();
                		$('#manpowerEndDate').html(endTime.substring(0, 4) + '.' + endTime.substring(5, 7));
                	}
                	$('#manpowerProjectName').html(projectName);
                	$('#manpowerInputNum').html($this.attr('value'));
                	currentEmployeeDetailObj.init(reqOrgId, projectId);
                	manpowerInputObj.init(reqOrgId, projectId);
                	departmentInputPieObj.init(reqOrgId, projectId);
                	jobSeqInputObj.init(reqOrgId, projectId);
                	rankInputObj.init(reqOrgId, projectId);
                	workplaceInputObj.init(reqOrgId, projectId);
                	subprojectTreeGridObj.init(reqOrgId, projectId);
                });
                $('#manpowerInYearModal').on('hidden.bs.modal', function(){
                	$('#manpowerInYearModal').off();
                });
            });
            $('.feeYearInput_col').unbind().bind('click', function () {
                var $this = $(this);
                projectId = $this.data('key');
                var date = new Date();
                var beginTime = date.getFullYear() + '.' + (date.getMonth() + 1);
                var endTime = $this.attr('key-endDate');
                var projectName = $this.attr('key-projectName');

                $('#feeInputInYearModal').modal('show');
                $('#feeInputInYearModal').on('shown.bs.modal', function(){
                	$('#feeBeginDate').html(beginTime);
                	if (endTime == "null" || endTime == "") {
                		$('#feeEndDate').html('');
                		$('#feeLine').hide();
                	} else {
                		$('#feeLine').show();
                		$('#feeEndDate').html(endTime.substring(0, 4) + '.' + endTime.substring(5, 7));
                	}
                	$('#feeProjectName').html(projectName);
                	$('#feeInputNum').html($this.attr('value'));
                	inputOutputChartObj.init(reqOrgId, projectId);
                	inputOutputRatioObj.init(reqOrgId, projectId);
                	feeTypeObj.init(reqOrgId, projectId);
                });
                $('#feeInputInYearModal').on('hidden.bs.modal', function(){
                	$('#feeInputInYearModal').off();
                });
            });
            $("#leadingProjectTable").find(".ui-jqgrid-bdiv").height(tableHeight + 50);
            $("#leadingProjectGrid").closest(".ui-jqgrid-bdiv").css({'overflow-x': 'scroll'});
        }
    };
    var leadingProjectObj = {
        gridId: '#leadingProjectGrid',
        resultData: null,
        isClicked: false,
        organId: null,
        keyName: null,
        manpowerInputSelect: null,
        startManpowerInput: null,
        endManpowerInput: null,
        feeInputSelect: null,
        startFeeInput: null,
        endFeeInput: null,
        projectGainSelect: null,
        startProjectGain: null,
        endProjectGain: null,
        startDate: null,
        endDate: null,
        projectProgress: null,
        principal: null,
        init: function (organId) {
            var self = this;
            self.organId = organId;
            if (self.hasInit) {
            	self.keyName = null;
            	self.manpowerInputSelect = null;
            	self.startManpowerInput = null;
            	self.endManpowerInput = null;
            	self.feeInputSelect = null;
            	self.startFeeInput = null;
            	self.endFeeInput = null;
            	self.projectGainSelect = null;
            	self.startProjectGain = null;
            	self.endProjectGain = null;
            	self.startDate = null;
            	self.endDate = null;
            	self.projectProgress = null;
            	self.principal = null;
                self.reloadData();
                return;
            }
            self.loadComple();
            leadingProjectOption.postData = {organId: organId};
            $(self.gridId).jqGrid(leadingProjectOption);
            self.hasInit = true;
        },
        loadComple: function () {
            var self = this;
            $("#projectSearchBtn").click(function () {
                var searchTxt = $.trim($("#projectSearchTxt").val());
                self.isClicked = true;
                self.keyName = searchTxt;
                self.manpowerInputSelect = null;
            	self.startManpowerInput = null;
            	self.endManpowerInput = null;
            	self.feeInputSelect = null;
            	self.startFeeInput = null;
            	self.endFeeInput = null;
            	self.projectGainSelect = null;
            	self.startProjectGain = null;
            	self.endProjectGain = null;
            	self.startDate = null;
            	self.endDate = null;
            	self.projectProgress = null;
            	self.principal = null;
                leadingProjectObj.reloadData();
            });
            $("#projectSearchTxt").keydown(function (e) {
                if (e.keyCode == 13) {
                    $("#projectSearchBtn").click();
                }
            });
            $("#dominantConditionSearch").click(function () {
            	var $principalId = $("#principal"), $smInputId = $("#startManpowerInput"), $emInputId = $("#endManpowerInput"),
            		$sfInputId = $("#startFeeInput"), $efInputId = $("#endFeeInput"), $spGainId = $("#startProjectGain"),
            		$epGainId = $("#endProjectGain"), $spTimeId = $("#startProjectTime"), $epTimeId = $("#endProjectTime");
            	self.principal = $.trim($principalId.val());
                self.manpowerInputSelect = $('#manpowerInputSelect option:selected').attr('value');
                self.startManpowerInput = $.trim($smInputId.val());
                self.endManpowerInput = $.trim($emInputId.val());
                self.feeInputSelect = $('#feeInputSelect option:selected').attr('value');
                self.startFeeInput = $.trim($sfInputId.val());
                self.endFeeInput = $.trim($efInputId.val());
                self.projectGainSelect = $('#projectGainSelect option:selected').attr('value');
                self.startProjectGain = $.trim($spGainId.val());
                self.endProjectGain = $.trim($epGainId.val());
                self.startDate = $.trim($spTimeId.val());
                self.endDate = $.trim($epTimeId.val());
                self.projectProgress = $('#projectProgressSelect option:selected').attr('value');
                var flag = dateCompare(self.startDate, self.endDate);
                
                removeRedAndTooltip($principalId);
                removeRedAndTooltip($smInputId);
                removeRedAndTooltip($emInputId);
                removeRedAndTooltip($sfInputId);
                removeRedAndTooltip($efInputId);
                removeRedAndTooltip($spGainId);
                removeRedAndTooltip($epGainId);
                removeRedAndTooltip($spTimeId);
                removeRedAndTooltip($epTimeId);
                var isTrue = true;
                if (flag == -1 || flag == 0) {
                	addRedAndTooltip($epTimeId, '开始时间必须小于结束时间!');
                	isTrue = false;
                }
                if (isNaN(self.startManpowerInput)) {
                    addRedAndTooltip($smInputId, '人力投入请填写数字!');
                    isTrue = false;
                }
                if (isNaN(self.endManpowerInput)) {
                	addRedAndTooltip($emInputId, '人力投入请填写数字!');
                	isTrue = false;
                }
                if (parseFloat(self.startManpowerInput) > parseFloat(self.endManpowerInput)) {
                    addRedAndTooltip($emInputId, '人力投入开始值不能大于结束值!');
                    isTrue = false;
                }
                if (isNaN(self.startFeeInput)) {
                    addRedAndTooltip($sfInputId, '费用投入请填写数字!');
                    isTrue = false;
                }
                if (isNaN(self.endFeeInput)) {
                	addRedAndTooltip($efInputId, '费用投入请填写数字!');
                	isTrue = false;
                }
                if (parseFloat(self.startFeeInput) > parseFloat(self.endFeeInput)) {
                    addRedAndTooltip($efInputId, '费用投入开始值不能大于结束值!');
                    isTrue = false;
                }
                if (isNaN(self.startProjectGain)) {
                    addRedAndTooltip($spGainId, '项目利润请填写数字!');
                    isTrue = false;
                }
                if (isNaN(self.endProjectGain)) {
                	addRedAndTooltip($epGainId, '项目利润请填写数字!');
                	isTrue = false;
                }
                if (parseFloat(self.startProjectGain) > parseFloat(self.endProjectGain)) {
                    addRedAndTooltip($epGainId, '项目利润开始值不能大于结束值!');
                    isTrue = false;
                }
                if(!isTrue) return;
                self.keyName = null;
                leadingProjectObj.reloadData();
                $("#moreLabel").removeClass("icon-panel-down icon-panel-up");
                $("#moreLabel").text('更多筛选条件');
                $("#moreLabel").addClass("icon-panel-down");
                $("#dominantCondition").addClass('hide');
            });
            $("#dominantConditionReset").click(function () {
                $("#dominantConditionSearchForm").resetForm();
            });
        },
        reloadData: function () {
            var self = this;
            var params = {
                organId: self.organId,
                keyName: self.keyName,
                manpowerInputSelect: self.manpowerInputSelect,
                startManpowerInput: self.startManpowerInput,
                endManpowerInput: self.endManpowerInput,
                feeInputSelect: self.feeInputSelect,
                startFeeInput: self.startFeeInput,
                endFeeInput: self.endFeeInput,
                projectGainSelect: self.projectGainSelect,
                startProjectGain: self.startProjectGain,
                endProjectGain: self.endProjectGain,
                startDate: self.startDate,
                endDate: self.endDate,
                projectProgress: self.projectProgress,
                principal: self.principal
            };
            $(self.gridId).clearGridData().setGridParam({
                postData: params
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#leadingProjectTable').width());
        }
    };

    /***
     * 参与项目
     */
    var participateGridOption = {
        url: urls.findParticipateProjectUrl,
        datatype: "json",
        postData: {},
        mtype: 'POST',
        altRows: true,//设置表格行的不同底色
        autowidth: true,
        height: tableHeight,
        colNames: ['项目名称', '人力总投入', '本月人力', '上月人力', '人力变化率', '管理序列', '业务序列', '专业序列', '职能序列', '项目开始时间', '项目结束时间', '项目进度'],
        colModel: [
            {name: 'projectName', index: 'projectName', sortable: false, fixed: true, width: 150, align: 'center', editable: false},
            {name: 'manpowerInYear', index: 'manpowerInYear', sortable: false, fixed: true, width: 100, align: 'center', editable: false,
                formatter: function (value, options, row) {
                    if (value == 0) {
                        return '0';
                    }
                    return "<a href='javascript:void(0)' data-key='" + row.projectId + "' class='manpower1_col' value='" + value +
                        "' key-projectName='" + row.projectName + "' key-startDate='" + row.startDate + "' key-endDate='" + row.endDate + "'>" + Tc.formatFloat(value) + "</a>";
                }
            },
            {name: 'manpowerInMonth', index: 'manpowerInMonth', sortable: false, fixed: true, width: 80, align: 'center', editable: false,
            	formatter: function (value, options, row) {
            		return Tc.formatFloat(value);
            	}
            },
            {name: 'manpowerLastMonth', index: 'manpowerLastMonth', sortable: false, fixed: true, width: 100, align: 'center', editable: false,
            	formatter: function (value, options, row) {
            		return Tc.formatFloat(value);
            	}
            },
            {name: 'manpowerChangeRate', index: 'manpowerChangeRate', sortable: false, fixed: true, width: 80, align: 'center', editable: false,
                formatter: function (value, options, row) {
                    if (row.manpowerLastMonth == 0 || row.manpowerLastMonth == null) {
                        return '-';
                    }
                    if (row.manpowerInMonth == 0 || row.manpowerInMonth == null) {
                        return '-100%';
                    }
                    return Tc.formatFloat(value * 100) + '%';
                }
            },
            {name: 'manageSeries', index: 'manageSeries', sortable: false, fixed: true, width: 80, align: 'center', editable: false},
            {name: 'businessSeries', index: 'businessSeries', sortable: false, fixed: true, width: 80, align: 'center', editable: false },
            {name: 'professionalSeries', index: 'professionalSeries', sortable: false, fixed: true, width: 80, align: 'center', editable: false },
            {name: 'functionalSeries', index: 'functionalSeries', sortable: false, fixed: true, width: 80, align: 'center', editable: false },
            {name: 'startDate', index: 'satartDate', sortable: false, fixed: true, width: 108, align: 'center', editable: false,
                formatter: function (value) {
                    if (value == null || value == '') {
                        return '-';
                    }
                    return value;
                }
            },
            {name: 'endDate', index: 'endDate', sortable: false, fixed: true, width: 108, align: 'center', editable: false,
                formatter: function (value) {
                    if (value == null || value == '') {
                        return '-';
                    }
                    return value;
                }
            },
            { name: 'projectProgress', index: 'projectProgress', sortable: false, fixed: true, width: 80, align: 'center', editable: false}
        ],
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: "#participateDetailSel",
        rowHeight: 36,
        styleUI: 'Bootstrap',
        loadComplete: function (xhr) {
            var $tabs = $('#partManpoerInputTabs');
            var projectId = "";
            $tabs.find('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
                var $this = $(e.target);
                var key = $this.attr('aria-controls');
                var thisTab = $('#' + key);
                if (thisTab.hasClass("in active"))return;
                $this.parents(".modal-body").find(".tab-pane").removeClass("in active");
                thisTab.addClass("in active");
                if (key == 'part2') {
                    partManpowerInputObj.resizeChart();
                } else if (key == 'part3') {
                    partDeprtmentInputPieObj.resizeChart();
                    partJobSeqInputObj.resizeChart();
                    partRankInputObj.resizeChart();
                    partWorkplaceInputObj.resizeChart();
                } else {
                    currentEmployeeObj.resizeGrid();
                }
            });
            $('.manpower1_col').unbind().bind('click', function () {
                var $this = $(this);
                projectId = $this.data('key');
                var date = new Date();
                var beginTime = date.getFullYear() + '.' + (date.getMonth() + 1);
                var endTime = $this.attr('key-endDate');
                var projectName = $this.attr('key-projectName');
                $('#manpowerPartInYearModal').modal('show');
                $('#manpowerPartInYearModal').on('shown.bs.modal', function(){
                	currentEmployeeObj.init(reqOrgId, projectId);
                	partManpowerInputObj.init(reqOrgId, projectId);
                	partDeprtmentInputPieObj.init(reqOrgId, projectId);
                	partJobSeqInputObj.init(reqOrgId, projectId);
                	partRankInputObj.init(reqOrgId, projectId);
                	partWorkplaceInputObj.init(reqOrgId, projectId);
                	currentEmployeeObj.init(reqOrgId, projectId);
                	$('#partManpowerBeginDate').html(beginTime);
                	if (endTime == "null" || endTime == "") {
                		$('#partManpowerEndDate').html('');
                		$('#partManpowerLine').hide();
                	} else {
                		$('#partManpowerLine').show();
                		$('#partManpowerEndDate').html(endTime.substring(0, 4) + '.' + endTime.substring(5, 7));
                	}
                	$('#partManpowerProjectName').html(projectName);
                	$('#partManpowerInputNum').html($this.attr('value'));
                });
                $('#manpowerPartInYearModal').on('hidden.bs.modal', function(){
                	$('#manpowerPartInYearModal').off();
                });
            });
            $("#participateDeailTable").find(".ui-jqgrid-bdiv").height(tableHeight + 70);
            $("#participateDetailGrid").closest(".ui-jqgrid-bdiv").css({'overflow-x': 'scroll'});
        }
    };
    var participateDetailObj = {
        gridId: '#participateDetailGrid',
        resultData: null,
        isClicked: false,
        organId: null,
        keyName: null,
        participateManpowerInputSelect: null,
        participateStartInput: null,
        participateEndInput: null,
        startDate: null,
        endDate: null,
        projectProgress: null,
        init: function (organId) {
            var self = this;
            self.organId = organId;
            if (self.hasInit) {
            	self.keyName = null;
            	self.participateManpowerInputSelect = null;
            	self.participateStartInput = null;
            	self.participateEndInput = null;
            	self.startDate = null;
            	self.endDate = null;
            	self.projectProgress = null;
                self.reloadData();
                return;
            }
            self.loadComple();
            participateGridOption.postData = {organId: organId};
            $(self.gridId).jqGrid(participateGridOption);
            self.hasInit = true;
        },
        loadComple: function () {
            var self = this;
            $("#participateSearchBtn").click(function () {
                var searchTxt = $.trim($("#participateSearchTxt").val());
                self.isClicked = true;
                self.keyName = searchTxt;
                self.participateManpowerInputSelect = null;
            	self.participateStartInput = null;
            	self.participateEndInput = null;
            	self.startDate = null;
            	self.endDate = null;
            	self.projectProgress = null;
                participateDetailObj.reloadData();
            });
            $("#participateSearchTxt").keydown(function (e) {
                if (e.keyCode == 13) {
                    $("#participateSearchBtn").click();
                }
            });
            $("#participateConditionSearch").click(function () {
            	var $psInputId = $("#participateStartInput"), $peInputId = $("#participateEndInput"),
            		$psTimeId = $("#participateStartTime"), $peTimeId = $("#participateEndTime");
            	self.participateManpowerInputSelect = $('#participateManpowerInputSelect option:selected').attr('value');
                self.participateStartInput = $.trim($psInputId.val());
                self.participateEndInput = $.trim($peInputId.val());
                self.projectProgress = $('#participateProjectProgressSelect option:selected').attr('value');
                self.startDate = $.trim($psTimeId.val());
                self.endDate = $.trim($peTimeId.val());
                var flag = dateCompare(self.startDate, self.endDate);
                removeRedAndTooltip($psInputId);
                removeRedAndTooltip($peInputId);
                removeRedAndTooltip($psTimeId);
                removeRedAndTooltip($peTimeId);
                var isTrue = true;
                if (flag == -1 || flag == 0) {
                    addRedAndTooltip($peTimeId, '开始时间必须小于结束时间!');
                    isTrue = false;
                }
                if (isNaN(self.participateStartInput)) {
                    addRedAndTooltip($psInputId, '人力投入请填写数字!');
                    isTrue = false;
                }
                if (isNaN(self.participateEndInput)) {
                	addRedAndTooltip($peInputId, '人力投入请填写数字!');
                	isTrue = false;
                }
                if (parseFloat(self.participateStartInput) > parseFloat(self.participateEndInput)) {
                    addRedAndTooltip($peInputId, '人力投入开始值不能大于结束值!');
                    isTrue = false;
                }
                if(!isTrue) return;
                self.keyName = null;
                participateDetailObj.reloadData();
                $("#participateMoreLabel").removeClass("icon-panel-down icon-panel-up");
                $("#participateMoreLabel").text('更多筛选条件');
                $("#participateMoreLabel").addClass("icon-panel-down");
                $("#participateCondition").addClass('hide');
            });
            $("#participateConditionReset").click(function () {
                $("#participateConditionSearchForm").resetForm();
            });
        },
        reloadData: function () {
            var self = this;
            var params = {
                organId: self.organId,
                keyName: self.keyName,
                participateManpowerInputSelect: self.participateManpowerInputSelect,
                participateStartInput: self.participateStartInput,
                participateEndInput: self.participateEndInput,
                startDate: self.startDate,
                endDate: self.endDate,
                projectProgress: self.projectProgress
            };
            $(self.gridId).clearGridData().setGridParam({
                postData: params
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#participateDeailTable').width());
        }
    };

    /***
     * 人员统计表
     */
    var employeeGridOption = {
        url: urls.findEmployeeStatisticsUrl,
        datatype: "json",
        postData: {},
        mtype: 'POST',
        altRows: true,//设置表格行的不同底色
        autowidth: true,
//        height: tableHeight,
        colNames: ['姓名', '所属组织', '本年参与项目数量', '当前参与项目数量', '是否担任过项目负责人'],
        colModel: [
            {
                name: 'empName', index: 'empName', sortable: false, width: 180, align: 'center', editable: false,
                formatter: function (value, options, row) {
                    return "<a href='javascript:void(0)' data-key='" + row.empId + "' class='manpower_col' >" + value + "</a>";
                }
            },
            {name: 'organName', index: 'organName', sortable: false, width: 220, align: 'center', editable: false},
            {name: 'projectNumInYear', index: 'projectNumInYear', sortable: false, width: 240, align: 'center', editable: false,
            	formatter: function (value, options, row) {
            		return Tc.formatFloat(value);
            	}
            },
            {name: 'projectNum', index: 'projectNum', sortable: false, width: 240, align: 'center', editable: false,
            	formatter: function (value, options, row) {
            		return Tc.formatFloat(value);
            	}
            },
            {name: 'isPrincipal', index: 'isPrincipal', sortable: false, width: 240, align: 'center', editable: false,
                formatter: function (value, options, row) {
                    if (row.isPrincipal > 0) {
                        return "是";
                    }
                    return "否";
                }
            }
        ],
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: "#employeeDetailSel",
        rowHeight: 36,
        styleUI: 'Bootstrap',
        loadComplete: function (xhr) {
            var empId = "";
            $('.manpower_col').unbind().bind('click', function () {
                var $this = $(this);
                empId = $this.data('key');
                $('#participateProjectModal').modal('show');
                $('#participateProjectModal').on('shown.bs.modal', function(){
                	participatejProjectDetailObj.init(reqOrgId, empId);
                });
                $('#participateProjectModal').on('hidden.bs.modal', function(){
                	$('#participateProjectModal').off();
                });
            });
            $("#employeeDetailTable").find(".ui-jqgrid-bdiv").height(tableHeight + 50);
        }
    };
    var employeeDetailObj = {
        gridId: '#employeeDetailGrid',
        resultData: null,
        isClicked: false,
        keyName: null,
        organId: null,
        startProjectNumInYear: null,
        endProjectNumInYear: null,
        startProjectNum: null,
        endProjectNum: null,
        principal: null,
        init: function (organId) {
            var self = this;
            self.organId = organId;
            if (self.hasInit) {
            	self.keyName = null;
            	self.startProjectNumInYear = null;
            	self.endProjectNumInYear = null;
            	self.startProjectNum = null;
            	self.endProjectNum = null;
            	self.principal = null;
                self.reloadData();
                return;
            }
            self.loadComple();
            employeeGridOption.postData = {organId: self.organId};
            $(self.gridId).jqGrid(employeeGridOption);
            self.hasInit = true;
        },
        loadComple: function () {
            var self = this;
            $("#employeeSearchBtn").click(function () {
                var searchTxt = $.trim($("#employeeSearchTxt").val());
                self.isClicked = true;
                self.keyName = searchTxt;
                self.startProjectNumInYear = null;
            	self.endProjectNumInYear = null;
            	self.startProjectNum = null;
            	self.endProjectNum = null;
            	self.principal = null;
                employeeDetailObj.reloadData();
            });
            $("#employeeSearchTxt").keydown(function (e) {
                if (e.keyCode == 13) {
                    $("#employeeSearchBtn").click();
                }
            });
            $("#employeeConditionSearch").click(function () {
            	var $spYearId = $("#startProjectNumInYear"), $epYearId = $("#endProjectNumInYear"),
            		$spNumId = $("#startProjectNum"), $epNumId = $("#endProjectNum");
                self.startProjectNumInYear = $.trim($spYearId.val());
                self.endProjectNumInYear = $.trim($epYearId.val());
                self.startProjectNum = $.trim($spNumId.val());
                self.endProjectNum = $.trim($epNumId.val());
                removeRedAndTooltip($spYearId);
                removeRedAndTooltip($epYearId);
                removeRedAndTooltip($spNumId);
                removeRedAndTooltip($epNumId);
                var isTrue = true;
                if (isNaN(self.startProjectNumInYear)) {
                    addRedAndTooltip($spYearId, '累计参与项目数请填写数字!');
                    isTrue = false;
                }
                if (isNaN(self.endProjectNumInYear)) {
                	addRedAndTooltip($epYearId, '累计参与项目数请填写数字!');
                	isTrue = false;
                }
                if (parseFloat(self.startProjectNumInYear) > parseFloat(self.endProjectNumInYear)) {
                    addRedAndTooltip($epYearId, '累计参与项目数开始值不能大于结束值!');
                    isTrue = false;
                }
                if (isNaN(self.startProjectNum)) {
                    addRedAndTooltip($spNumId, '当前参与项目数请填写数字!');
                    isTrue = false;
                }
                if (isNaN(self.endProjectNum)) {
                	addRedAndTooltip($epNumId, '当前参与项目数请填写数字!');
                	isTrue = false;
                }
                if (parseFloat(self.startProjectNum) > parseFloat(self.endProjectNum)) {
                    addRedAndTooltip($epNumId, '当前参与项目数开始值不能大于结束值!');
                    isTrue = false;
                }
                if(!isTrue) return;
                self.principal = $("#isPrincipal1").parent().find(".condition-btn-selected").attr("value");
                self.keyName = null;
                employeeDetailObj.reloadData();
                $('#employeeMoreLabel').removeClass("icon-panel-down icon-panel-up");
                $('#employeeMoreLabel').text('更多筛选条件');
                $('#employeeMoreLabel').addClass("icon-panel-down");
                $("#employeeCondition").addClass('hide');
            });
            $("#employeeConditionReset").click(function () {
                $("#employeeConditionSearchForm").resetForm();
                $("#isPrincipal1").parent().find(".condition-btn").removeClass("condition-btn-selected");
                $("#allChecked").addClass("condition-btn-selected");
            });
            $(".condition-btn").click(function () {
                var $this = $(this);
                if ($this.hasClass("condition-btn-selected"))return;
                $this.parent().find(".condition-btn").removeClass("condition-btn-selected");
                $this.addClass("condition-btn-selected");
            });
        },
        reloadData: function () {
            var self = this;
            var params = {
                organId: self.organId,
                keyName: self.keyName,
                startProjectNumInYear: self.startProjectNumInYear,
                endProjectNumInYear: self.endProjectNumInYear,
                startProjectNum: self.startProjectNum,
                endProjectNum: self.endProjectNum,
                principal: self.principal
            };
            $(self.gridId).clearGridData().setGridParam({
                postData: params
            }).trigger("reloadGrid");
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#employeeDetailTable').width());
        },
    };

    /***
     * 当前人力投入
     */
    var currentEmployeeDetailGridOption = {
        url: urls.getCurrentEmployeeListUrl,
        datatype: "json",
        postData: {},
        mtype: 'POST',
        altRows: true,//设置表格行的不同底色
        autowidth: true,
        height: 300,
        colNames: ['姓名', '所属组织', '子项目', '人力投入', '工作内容'],
        colModel: [
            {name: 'empName', sortable: false, width: 135, align: 'center', editable: false},
            {name: 'projectName', sortable: false, width: 200, align: 'center', editable: false},
            {name: 'subProjectName', sortable: false, width: 200, align: 'center', editable: false},
            {name: 'manpowerInput', sortable: false, width: 100, align: 'center', editable: false,
            	formatter: function (value, options, row) {
            		return Tc.formatFloat(value);
            	}
            },
            {name: 'workContent', sortable: false, width: 200, align: 'center', editable: false},
        ],
        viewrecords: true,
        scroll: true,
        rowNum: -1,
        rowHeight: 36,
        styleUI: 'Bootstrap',
        loadComplete: function (xhr) {
            $("#currentEmployeeDeailTable").find(".ui-jqgrid-bdiv").height(300 + 2);
            currentEmployeeDetailObj.resizeGrid();
        }
    };
    var currentEmployeeDetailObj = {
        gridId: '#currentEmployeeDetailGrid',
        resultData: null,
        init: function (organId, projectId) {
            var self = this;
            if (self.hasInit) {
                self.reloadData(organId, projectId);
                return;
            }
            currentEmployeeDetailGridOption.postData = {organId: organId, projectId: projectId};
            $(self.gridId).jqGrid(currentEmployeeDetailGridOption);
            self.hasInit = true;
        },
        reloadData: function (organId, projectId) {
            var self = this;
            var params = {organId: organId, projectId: projectId};
            $(self.gridId).clearGridData().setGridParam({
                postData: params
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#currentEmployeeDeailTable').width());
        }
    };
    var currentEmployeeGridOption = {
        url: urls.getCurrentEmployeeListUrl,
        datatype: "json",
        postData: {},
        mtype: 'POST',
        altRows: true,//设置表格行的不同底色
        autowidth: true,
        height: 300,
        colNames: ['姓名', '所属组织', '子项目', '人力投入', '工作内容'],
        colModel: [
            {name: 'empName', sortable: false, fixed: false, width: 135, align: 'center', editable: false},
            {name: 'projectName', sortable: false, fixed: false, width: 200, align: 'center', editable: false},
            {name: 'subProjectName', sortable: false, fixed: false, width: 200, align: 'center', editable: false},
            {name: 'manpowerInput', sortable: false, fixed: false, width: 100, align: 'center', editable: false,
            	formatter: function (value, options, row) {
            		return Tc.formatFloat(value);
            	}
            },
            {name: 'workContent', sortable: false, fixed: false, width: 200, align: 'center', editable: false},
        ],
        viewrecords: true,
        scroll: true,
        rowNum: -1,
        rowHeight: 36,
        styleUI: 'Bootstrap',
        loadComplete: function (xhr) {
            $("#currentEmployeeTable").find(".ui-jqgrid-bdiv").height(300 + 2);
            currentEmployeeObj.resizeGrid();
        }
    };
    var currentEmployeeObj = {
        gridId: '#currentEmployeeGrid',
        resultData: null,
        init: function (organId, projectId) {
            var self = this;
            if (self.hasInit) {
                self.reloadData(organId, projectId);
                return;
            }
            currentEmployeeGridOption.postData = {organId: organId, projectId: projectId};
            $(self.gridId).jqGrid(currentEmployeeGridOption);
            self.hasInit = true;
        },
        reloadData: function (organId, projectId) {
            var self = this;
            var params = {organId: organId, projectId: projectId};
            $(self.gridId).clearGridData().setGridParam({
                postData: params
            }).trigger("reloadGrid");
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#currentEmployeeTable').width());
        }
    };

    //人力投入环比趋势
    var manpowerInputOption = {
        grid: {
        	x : 35,
			x2 : 25,
			y : 25,
			y2 : 20,
            borderWidth: 0
        },
        tooltip: {
        	trigger: 'axis',
        	axisPointer : {
        		type : 'line',
        		lineStyle : {
        			color : fontDefaultColor[3],
        			type : 'dashed',
        			width : 1
        		}
        	}
        },
        xAxis: [
            {
                type: "category",
                boundaryGap: false,
                splitLine: {show: false},
                axisTick: {
                    lineStyle: {
                        color: fontDefaultColor[2]
                    }
                },
                data: [],
                axisLine: {
                    lineStyle: {
                        color: fontDefaultColor[2],
                        width: 1
                    }
                },
                axisLabel: {
                    itemStyle: {
                        color: fontDefaultColor[2]
                    }, textStyle: {
                        color: fontDefaultColor[2],
                        fontSize: 12
                    }
                }
            }
        ],
        yAxis: [
            {
                type: "value",
                nameTextStyle: {
                    color: fontDefaultColor[1],
                    margin: '100px'
                },
                axisLine: {
                    lineStyle: {
                        color: fontDefaultColor[2],
                        width: 1
                    }
                },
                splitLine: {
                	lineStyle: {
                        color: lineDefaultColor[0]
                    }
                }
            }
        ],
        calculable: false,
        series: [
            {
            	name: '人力投入',
                type: "line",
                smooth: false,  //曲线true 折线false
                itemStyle: {
                    normal: {
                    	color: lineDefaultColor[1],
                    	label: normalLabelDefaultColor
                    },
                    emphasis: {
                        label: false
                    }
                },
                clickable: false,
                data: []
            }
        ]
    };
    var manpowerInputObj = {
        id: "manpowerInput",
        chart: null,
        init: function (organId, projectId) {
            var self = this;
            if (this.chart == null) {
                this.chart = echarts.init(document.getElementById(this.id));
            }
            this.getData(organId, projectId);
        },
        getData: function (organId, projectId) {
            var self = this;
            $.ajax({
                url: urls.getManpowerInputByMonthUrl,
                type: "get",
                data: {"organId": organId, "projectId": projectId},
                dataType: "json",
                success: function (result) {
                    if (!_.isEmpty(result)) {
                        self.render(result);
                    }
                }
            });
        },
        render: function (result) {
            var self = this;
            var dataValue = [];
            var xAxisData = [];
            $.each(result, function (i, o) {
                dataValue.push(o.manpowerInput);
                var month = (o.yearMonth + "").substring(0, 4) + "/" + (o.yearMonth + "").substring(4, 6);
                xAxisData.push(month);
            });
            xAxisData.reverse();
            dataValue.reverse();
            self.chart.clear();
            manpowerInputOption.xAxis[0].data = xAxisData;
            manpowerInputOption.series[0].data = dataValue;
            self.chart.setOption(manpowerInputOption);
            self.resizeChart();
        },
        resizeChart: function () {
            var self = this;
            if (self.chart != null) {
                self.chart.resize();
            }
        }
    };

    var partManpowerInputObj = {
        id: "partManpowerInput",
        chart: null,
        init: function (organId, projectId) {
            var self = this;
            if (this.chart == null) {
                this.chart = echarts.init(document.getElementById(this.id));
            }
            this.getData(organId, projectId);
        },
        getData: function (organId, projectId) {
            var self = this;
            $.ajax({
                url: urls.getManpowerInputByMonthUrl,
                type: "get",
                data: {"organId": organId, "projectId": projectId},
                dataType: "json",
                success: function (result) {
                    if (!_.isEmpty(result)) {
                        self.render(result);
                    }
                }
            });
        },
        render: function (result) {
            var self = this;
            var dataValue = [];
            var xAxisData = [];
            $.each(result, function (i, o) {
                dataValue.push(o.manpowerInput);
                var month = (o.yearMonth + "").substring(0, 4) + "/" + (o.yearMonth + "").substring(4, 6);
                xAxisData.push(month);
            });
            xAxisData.reverse();
            dataValue.reverse();
            self.chart.clear();
            manpowerInputOption.xAxis[0].data = xAxisData;
            manpowerInputOption.series[0].data = dataValue;
            self.chart.setOption(manpowerInputOption);
            self.resizeChart();
        },
        resizeChart: function () {
            var self = this;
            if (self.chart != null) {
                self.chart.resize();
            }
        }
    };

    //饼图颜色
    var optionPie = {
        tooltip: {
            show: false
        },
        toolbox: {
            show: false
        },
        calculable: false,
        series: [
            {
                clickable: false,
                type: "pie",
                radius: "90%",
                center: ["50%", "50%"],
                data: [],
                tooltip: {
                    show: false
                },
                itemStyle: {
                    normal: {
                        label: {
                            show: false
                        },
                        labelLine: {
                            show: false
                        }
                    }
                }
            }
        ],
        color: pieDefaultColor
    };

    /*
     * 各部门人力投入
     * */
    var departmentInputPieObj = {
        chart: echarts.init(document.getElementById('departmentInputChart')),
        option: optionPie,
        init: function (organId, projectId) {
            var self = this;
            if (self.organId == organId && self.projectId == projectId) {
                return;
            }
            self.getRequestDatas(organId, projectId);
            self.organId = organId;
            self.projectId = projectId;
        },
        getRequestDatas: function (organId, projectId) {
            var self = this;
            var param = {organId: organId, projectId: projectId};
            $.ajax({
                url: urls.getDepartmentInputUrl,
                data: param,
                type: 'post',
                success: function (data) {
                    self.getOption(data);
                },
                error: function () {
                }
            });
        },
        getOption: function (data) {
            if (data == undefined) {
                console.info("各部门人力投入出错了！");
            } else {
                var self = this;
                var lis = [], legend = [];
                var i = 0;
                $.each(data, function () {
                    $.each(this, function (n, v) {
                        if (v != 0) {
                            lis.push({"name": n, "value": v});
                            legend.push('<div><label style="background-color: ' + pieDefaultColor[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
                            i++;
                        }
                    });
                });
                self.option.series[0].data = lis;
                self.chart.clear();
                self.chart.setOption(self.option);
                self.chart.refresh();
                $("#departmentInputText").removeClass("hide").find(".legend").html(legend.join(""));
            }
        },
        resizeChart: function () {
            var self = this;
            if (self.chart != null) {
                self.chart.resize();
            }
        }
    };

    /*
     * 参与项目各部门人力投入
     * */
    var partDeprtmentInputPieObj = {
        chart: echarts.init(document.getElementById('partDeprtmentInputChart')),
        option: optionPie,
        init: function (organId, projectId) {
            var self = this;
            if (self.organId == organId && self.projectId == projectId) {
                return;
            }
            self.getRequestDatas(organId, projectId);
            self.organId = organId;
            self.projectId = projectId;
        },
        getRequestDatas: function (organId, projectId) {
            var self = this;
            var param = {organId: organId, projectId: projectId};
            $.ajax({
                url: urls.getDepartmentInputUrl,
                data: param,
                type: 'post',
                success: function (data) {
                    self.getOption(data);
                },
                error: function () {
                }
            });
        },
        getOption: function (data) {
            if (data == undefined) {
                console.info("各部门人力投入出错了！");
            } else {
                var self = this;
                var lis = [], legend = [];
                var i = 0;
                $.each(data, function () {
                    $.each(this, function (n, v) {
                        if (v != 0) {
                            lis.push({"name": n, "value": v});
                            legend.push('<div><label style="background-color: ' + pieDefaultColor[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
                            i++;
                        }
                    });
                });
                self.option.series[0].data = lis;
                self.chart.clear();
                self.chart.setOption(self.option);
                self.chart.refresh();
//	            self.resizeChart();
                $("#partDeprtmentInputText").removeClass("hide").find(".legend").html(legend.join(""));
            }
        },
        resizeChart: function () {
            var self = this;
            if (self.chart != null) {
                self.chart.resize();
            }
        }
    };

    /*
     * 职位序列人力投入
     * */
    var jobSeqInputObj = {
        chart: echarts.init(document.getElementById('jobSeqInputChart')),
        option: optionPie,
        init: function (organId, projectId) {
            var self = this;
            if (self.organId == organId && self.projectId == projectId) {
                return;
            }
            self.getRequestDatas(organId, projectId);
            self.organId = organId;
            self.projectId = projectId;
        },
        getRequestDatas: function (organId, projectId) {
            var self = this;
            var param = {organId: organId, projectId: projectId};
            $.ajax({
                url: urls.getJobSeqInputUrl,
                data: param,
                type: 'post',
                success: function (data) {
                    self.getOption(data);
                },
                error: function () {
                }
            });
        },
        getOption: function (data) {
            if (data == undefined) {
                console.info("职位序列人力投入出错了！");
            } else {
                var self = this;
                var lis = [], legend = [];
                var i = 0;
                $.each(data, function () {
                    $.each(this, function (n, v) {
                        if (v != 0) {
                            lis.push({"name": n, "value": v});
                            legend.push('<div><label style="background-color: ' + pieDefaultColor[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
                            i++;
                        }
                    });
                });
                self.option.series[0].data = lis;
                self.chart.clear();
                self.chart.setOption(self.option);
                self.chart.refresh();
//	            self.resizeChart();
                $("#jobSeqInputText").removeClass("hide").find(".legend").html(legend.join(""));
            }
        },
        resizeChart: function () {
            var self = this;
            if (self.chart != null) {
                self.chart.resize();
            }
        }
    };

    /*
     * 参与项目职位序列人力投入
     * */
    var partJobSeqInputObj = {
        chart: echarts.init(document.getElementById('partJobSeqInputChart')),
        option: optionPie,
        init: function (organId, projectId) {
            var self = this;
            if (self.organId == organId && self.projectId == projectId) {
                return;
            }
            self.getRequestDatas(organId, projectId);
            self.organId = organId;
            self.projectId = projectId;
        },
        getRequestDatas: function (organId, projectId) {
            var self = this;
            var param = {organId: organId, projectId: projectId};
            $.ajax({
                url: urls.getJobSeqInputUrl,
                data: param,
                type: 'post',
                success: function (data) {
                    self.getOption(data);
                },
                error: function () {
                }
            });
        },
        getOption: function (data) {
            if (data == undefined) {
                console.info("职位序列人力投入出错了！");
            } else {
                var self = this;
                var lis = [], legend = [];
                var i = 0;
                $.each(data, function () {
                    $.each(this, function (n, v) {
                        if (v != 0) {
                            lis.push({"name": n, "value": v});
                            legend.push('<div><label style="background-color: ' + pieDefaultColor[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
                            i++;
                        }
                    });
                });
                self.option.series[0].data = lis;
                self.chart.clear();
                self.chart.setOption(self.option);
                self.chart.refresh();
//	            self.resizeChart();
                $("#partJobSeqInputText").removeClass("hide").find(".legend").html(legend.join(""));
            }
        },
        resizeChart: function () {
            var self = this;
            if (self.chart != null) {
                self.chart.resize();
            }
        }
    };

    /*
     * 职级人力投入
     */
    var rankInputObj = {
        chart: echarts.init(document.getElementById('rankInputChart')),
        option: optionPie,
        init: function (organId, projectId) {
            var self = this;
            if (self.organId == organId && self.projectId == projectId) {
                return;
            }
            self.getRequestDatas(organId, projectId);
            self.organId = organId;
            self.projectId = projectId;
        },
        getRequestDatas: function (organId, projectId) {
            var self = this;
            var param = {organId: organId, projectId: projectId};
            $.ajax({
                url: urls.getRankInputUrl,
                data: param,
                type: 'post',
                success: function (data) {
                    self.getOption(data);
                },
                error: function () {
                }
            });
        },
        getOption: function (data) {
            if (data == undefined) {
                console.info("职位序列人力投入出错了！");
            } else {
                var self = this;
                var lis = [], legend = [];
                var i = 0;
                $.each(data, function () {
                    $.each(this, function (n, v) {
                        if (v != 0) {
                            lis.push({"name": n, "value": v});
                            legend.push('<div><label style="background-color: ' + pieDefaultColor[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
                            i++;
                        }
                    });
                });
                self.option.series[0].data = lis;
                self.chart.clear();
                self.chart.setOption(self.option);
                self.chart.refresh();
//	            self.resizeChart();
                $("#rankInputText").removeClass("hide").find(".legend").html(legend.join(""));
            }
        },
        resizeChart: function () {
            var self = this;
            if (self.chart != null) {
                self.chart.resize();
            }
        }
    };

    /*
     * 参与项目职级人力投入
     */
    var partRankInputObj = {
        chart: echarts.init(document.getElementById('partRankInputChart')),
        option: optionPie,
        init: function (organId, projectId) {
            var self = this;
            if (self.organId == organId && self.projectId == projectId) {
                return;
            }
            self.getRequestDatas(organId, projectId);
            self.organId = organId;
            self.projectId = projectId;
        },
        getRequestDatas: function (organId, projectId) {
            var self = this;
            var param = {organId: organId, projectId: projectId};
            $.ajax({
                url: urls.getRankInputUrl,
                data: param,
                type: 'post',
                success: function (data) {
                    self.getOption(data);
                },
                error: function () {
                }
            });
        },
        getOption: function (data) {
            if (data == undefined) {
                console.info("职位序列人力投入出错了！");
            } else {
                var self = this;
                var lis = [], legend = [];
                var i = 0;
                $.each(data, function () {
                    $.each(this, function (n, v) {
                        if (v != 0) {
                            lis.push({"name": n, "value": v});
                            legend.push('<div><label style="background-color: ' + pieDefaultColor[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
                            i++;
                        }
                    });
                });
                self.option.series[0].data = lis;
                self.chart.clear();
                self.chart.setOption(self.option);
                self.chart.refresh();
//	            self.resizeChart();
                $("#partRankInputText").removeClass("hide").find(".legend").html(legend.join(""));
            }
        },
        resizeChart: function () {
            var self = this;
            if (self.chart != null) {
                self.chart.resize();
            }
        }
    };

    /*
     * 工作地人力投入
     */
    var workplaceInputObj = {
        chart: echarts.init(document.getElementById('workplaceInputChart')),
        option: optionPie,
        init: function (organId, projectId) {
            var self = this;
            if (self.organId == organId && self.projectId == projectId) {
                return;
            }
            self.getRequestDatas(organId, projectId);
            self.organId = organId;
            self.projectId = projectId;
        },
        getRequestDatas: function (organId, projectId) {
            var self = this;
            var param = {organId: organId, projectId: projectId};
            $.ajax({
                url: urls.getWorkplaceInputUrl,
                data: param,
                type: 'post',
                success: function (data) {
                    self.getOption(data);
                },
                error: function () {
                }
            });
        },
        getOption: function (data) {
            if (data == undefined) {
                console.info("职位序列人力投入出错了！");
            } else {
                var self = this;
                var lis = [], legend = [];
                var i = 0;
                $.each(data, function () {
                    $.each(this, function (n, v) {
                        if (v != 0) {
                            lis.push({"name": n, "value": v});
                            legend.push('<div><label style="background-color: ' + pieDefaultColor[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
                            i++;
                        }
                    });
                });
                self.option.series[0].data = lis;
                self.chart.clear();
                self.chart.setOption(self.option);
                self.chart.refresh();
//	            self.resizeChart();
                $("#workplaceInputText").removeClass("hide").find(".legend").html(legend.join(""));
            }
        },
        resizeChart: function () {
            var self = this;
            if (self.chart != null) {
                self.chart.resize();
            }
        }
    };

    /*
     * 参与项目工作地人力投入
     */
    var partWorkplaceInputObj = {
        chart: echarts.init(document.getElementById('partWorkspaceInputChart')),
        option: optionPie,
        init: function (organId, projectId) {
            var self = this;
            if (self.organId == organId && self.projectId == projectId) {
                return;
            }
            self.getRequestDatas(organId, projectId);
            self.organId = organId;
            self.projectId = projectId;
        },
        getRequestDatas: function (organId, projectId) {
            var self = this;
            var param = {organId: organId, projectId: projectId};
            $.ajax({
                url: urls.getWorkplaceInputUrl,
                data: param,
                type: 'post',
                success: function (data) {
                    self.getOption(data);
                },
                error: function () {
                }
            });
        },
        getOption: function (data) {
            if (data == undefined) {
                console.info("职位序列人力投入出错了！");
            } else {
                var self = this;
                var lis = [], legend = [];
                var i = 0;
                $.each(data, function () {
                    $.each(this, function (n, v) {
                        if (v != 0) {
                            lis.push({"name": n, "value": v});
                            legend.push('<div><label style="background-color: ' + pieDefaultColor[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
                            i++;
                        }
                    });
                });
                self.option.series[0].data = lis;
                self.chart.clear();
                self.chart.setOption(self.option);
                self.chart.refresh();
//	            self.resizeChart();
                $("#partWorkspaceInputText").removeClass("hide").find(".legend").html(legend.join(""));
            }
        },
        resizeChart: function () {
            var self = this;
            if (self.chart != null) {
                self.chart.resize();
            }
        }
    };

    /**
     * 明细分析-主导项目-人力结构分析-子项目明细
     * */
    var subprojectTreeGridObj = {
        shrinkToFit: true,
        isHide: true,
        treeGridId: '#subprojectTreegrid',
        first: true,
        organId: null,
        projectId: null,
        options: {
            treeGrid: true,
            treeGridModel: 'adjacency', //treeGrid模式，跟json元数据有关 ,adjacency/nested
            url: urls.getSubprojectByIdUrl,
            datatype: 'json',
            mtype: "get",
            height: "280px",
            autoWidth: true,
            ExpandColClick: true, //当为true时，点击展开行的文本时，treeGrid就能展开或者收缩，不仅仅是点击图片
            ExpandColumn: 'projectName',//树状结构的字段
            rowHeight: 36,
            styleUI: 'Bootstrap',
            colNames: ['projectId', '项目名称', '负责人', '本期投入', '上期投入', '管理序列', '业务序列', '专业序列', '职能序列'],
            colModel: [
                {name: 'id', key: true, hidden: true},
                {name: 'projectName', width: 200, sortable: false, align: 'left'},
                {name: 'empName', width: 100, sortable: false, align: 'center'},
                {name: 'manpowerInMonth', width: 80, sortable: false, align: 'center',
                	formatter: function (value, options, row) {
                		return Tc.formatFloat(value);
                	}
                },
                {name: 'manpowerLastMonth', width: 80, sortable: false, align: 'center',
                	formatter: function (value, options, row) {
                		return Tc.formatFloat(value);
                	}
                },
                {name: 'manageSeries', width: 80, sortable: false, align: 'center'},
                {name: 'businessSeries', width: 80, sortable: false, align: 'center'},
                {name: 'professionalSeries', width: 80, sortable: false, align: 'center'},
                {name: 'functionSeries', width: 80, sortable: false, align: 'center'}
            ],
            jsonReader: {
                root: "rows",
                total: "total",
                repeatitems: true
            },
            beforeRequest: function () {
                var self = subprojectTreeGridObj;
                var data = this.p.data;
                var postData = this.p.postData;
                if (postData.organId == null) {
                    postData.organId = reqOrgId;
                }
                postData.projectId = self.projectId;
                postData.type = '1';
                if (postData.n_level != null && postData.n_level.length != 0) {
                    level = parseInt(postData.n_level, 10);
                    postData.n_level = level + 1;
                    postData.type = '2';
                }
            },
            treeIcons: {
                "plus": "ct-glyphicon-plus",
                "minus": "ct-glyphicon-minus"
            },
            pager: "false"
        },
        init: function (organId, projectId) {
            var self = this;
            if (self.organId == organId && self.projectId == projectId) {
                return;
            }
            self.organId = organId;
            self.projectId = projectId;
            self.initTreeGrid(organId, projectId, self.treeGridId, self.options);
        },
        initTreeGrid: function (organId, projectId, gridId, options) {
            var self = this;
            if (self.first) {
                options.postData = {organId: organId, projectId: projectId};
                jQuery(gridId).jqGrid(options);
                self.resizeGrid();
                self.first = false;
            } else {
                jQuery(gridId).clearGridData().setGridParam({
                    postData: {organId: organId, projectId: projectId}
                }).trigger("reloadGrid");
            }
        },
        resizeGrid: function () {
            var self = this;
            $(self.treeGridId).setGridWidth($('#subprojectTreegridbody').width());
        }
    };

    //投入产出费用
    var inputOutputOption = {
        legend: {
            data: ['投入费用', '产出费用'],
            y: 'bottom'
        },
        calculable: false,
        tooltip: {
        	trigger: 'axis',
        	axisPointer : {
        		type : 'line',
        		lineStyle : {
        			color : fontDefaultColor[3],
        			type : 'dashed',
        			width : 1
        		}
        	},
        	formatter : function(param){
        		var div;
        		if(param[0].value == '-'){
        			div = '<div>' + param[0].name + '</div>'
	            	 	+ '<div>' + param[2].seriesName + ' : ' + param[2].value + '万元</div>'
	            		+ '<div>' + param[3].seriesName + ' : ' + param[3].value + '万元</div>';
        		} else {
        			div = '<div>' + param[0].name + '</div>'
	        			+ '<div>' + param[0].seriesName + ' : ' + param[0].value + '万元</div>'
	        			+ '<div>' + param[1].seriesName + ' : ' + param[1].value + '万元</div>';
        		}
            	return div;
            }
        },
        grid: {
        	x : 45,
			x2 : 25,
			y : 25,
			y2 : 55,
            borderWidth: 0
        },
        color: ['#23C6C8', '#EA711E'],
        xAxis: [{
            type: 'category',
            splitLine: false,
            axisLine: {
                show: true,
                lineStyle: {
                	width: 1,
                    color: fontDefaultColor[2]
                }
            },
            axisTick: {
                show: true,
                lineStyle: {
                    color: fontDefaultColor[2]
                }
            },
            axisLabel: {
                show: true,
                itemStyle: {
                    color: fontDefaultColor[2]
                }
            },
            data: []
        }],
        yAxis: [{
            type: 'value',
            splitLine: {
            	show : true,
            	lineStyle : {
            		color : lineDefaultColor[0]
            	}
            },
            axisLine: {
                lineStyle: {
                	width: 1,
                    color: fontDefaultColor[2]
                }
            },
            axisLabel: {
                show: true,
                itemStyle: {
                    color: fontDefaultColor[2]
                }
            }
        }],
        series: [{
            name: '投入年累计',
            type: 'bar',
            clickable: false,
            barMaxWidth: 30,
            itemStyle: {
                normal: {
                    color: barDefaultColor[0],
                    type: 'dotted',
                    label: normalLabelDefaultColor
                },
                emphasis: emphasisDefaultColor
            },
            data: []
        }, {
            name: '产出年累计',
            type: 'bar',
            clickable: false,
            barMaxWidth: 30,
            itemStyle: {
                normal: {
                    color: barDefaultColor[1],
                    type: 'dotted',
                    label: normalLabelDefaultColor
                },
                emphasis: emphasisDefaultColor
            },
            data: []
        }, {
            name: '投入费用',
            type: 'line',
            clickable: false,
            symbol: 'circle',
            itemStyle: {
                normal: {
                    color: barDefaultColor[0],
                    type: 'dotted',
                    label: {
                        show: false
                    }
                }
            },
            data: []
        }, {
            name: '产出费用',
            type: 'line',
            clickable: false,
            symbol: 'circle',
            itemStyle: {
                normal: {
                    color: barDefaultColor[1],
                    type: 'dotted',
                    label: {
                        show: false
                    }
                }
            },
            data: []
        }]
    };

    /**
     * 初始化 投入产出费用 柱状/折线混合图
     */
    var inputOutputChartObj = {
        id: "inputOutputFeeChart",
        chart: null,
        init: function (organId, projectId) {
            var self = this;
            if (this.chart == null) {
                this.chart = echarts.init(document.getElementById(this.id));
            }
            this.getData(organId, projectId);
        },
        getData: function (organId, projectId) {
            var self = this;
            $.ajax({
                url: urls.getInputOutputByMonthUrl,
                //  type:"post",
                data: {organId: organId, projectId: projectId},
                success: function (result) {
                    if (!_.isEmpty(result)) {
                        self.render(result);
                    }
                }
            });
        },
        render: function (result) {
            var self = this;
            var dataValue = [];
            var xAxisData = [];
            var yearInputData = [];
            var yearOutputData = [];
            if (undefined != result.dto) {
                yearInputData.push(Tc.formatFloat(result.dto.inputFeeInYear));
                yearOutputData.push(Tc.formatFloat(result.dto.outputFeeInYear));
            }
            var monthInputData = [];
            var monthOutputData = [];
            $.each(result.list, function (i, o) {
                monthInputData.push(Tc.formatFloat(o.inputFee));
                monthOutputData.push(Tc.formatFloat(o.outputFee));
                var month = (o.yearMonth + "").substring(2, 4) + "/" + (o.yearMonth + "").substring(4, 6);
                xAxisData.push(month);
            });
            xAxisData.push('当年累计');
            monthInputData.push('-');
            monthOutputData.push('-');
            xAxisData.reverse();
            monthOutputData.reverse();
            monthInputData.reverse();
            inputOutputOption.xAxis[0].data = xAxisData;
            inputOutputOption.series[0].data = yearInputData;
            inputOutputOption.series[1].data = yearOutputData;
            inputOutputOption.series[2].data = monthInputData;
            inputOutputOption.series[3].data = monthOutputData;
            self.chart.setOption(inputOutputOption, true);
            self.resizeChart();
        },
        resizeChart: function () {
            var self = this;
            if (self.chart != null) {
                self.chart.resize();
            }
        }
    };

    //投入产出比
    var inputOutputRatioOption = {
        grid: {
        	x : 45,
			x2 : 25,
			y : 25,
			y2 : 20,
            borderWidth: 0
        },
        tooltip: {
        	trigger: 'axis',
        	axisPointer : {
        		type : 'line',
        		lineStyle : {
        			color : fontDefaultColor[3],
        			type : 'dashed',
        			width : 1
        		}
        	},
        	formatter : function(param){
        		return '<div>' + param[0].name + '</div>'
    				+ '<div>' + param[0].seriesName + ' : ' + param[0].value + '%</div>';
            }
        },
        xAxis: [
            {
                type: "category",
                splitLine: {show: false},
                axisTick: {
                    lineStyle: {
                        color: fontDefaultColor[2]
                    }
                },
                data: [],
                axisLine: {
                    lineStyle: {
                        color: fontDefaultColor[2],
                        width: 1
                    }
                },
                axisLabel: {
                    itemStyle: {
                        color: fontDefaultColor[2]
                    }, textStyle: {
                        color: fontDefaultColor[2],
                        fontSize: 12
                    }
                }
            }
        ],
        yAxis: [
            {
                type: "value",
                nameTextStyle: {
                    color: fontDefaultColor[1],
                    margin: '100px'
                },
                axisLine: {
                    lineStyle: {
                        color: fontDefaultColor[2],
                        width: 1
                    }
                },
                axisLabel: {
                    formatter: '{value} %'
                },
                splitLine: {
                	show : true,
                	lineStyle : {
                		color : lineDefaultColor[0]
                	}
                }
            }
        ],
        calculable: false,
        series: [
            {
                type: "line",
                name: "产出投入比",
                smooth: true,
                itemStyle: {
                    normal: {
                    	color: lineDefaultColor[1],
                        label: {
                            show: true,
                            textStyle: {
                            	color: fontDefaultColor[2]
                            },
                            formatter: function (params) {
                                return Tc.formatFloat(params.value) + '%';
                            }
                        }
                    },
                    emphasis: {
                        label: false
                    }
                },
                clickable: false,
                data: []
            }
        ]
    };
    var inputOutputRatioObj = {
        id: "inputOutputRatioChart",
        chart: null,
        init: function (organId, projectId) {
            var self = this;
            if (this.chart == null) {
                this.chart = echarts.init(document.getElementById(this.id));
            }
            var _projectId = projectId;
            if (projectId == null || projectId == "") {
                _projectId = self.projectId;
            }
            this.getData(organId, _projectId);
            self.organId = organId;
            self.projectId = projectId;
        },
        getData: function (organId, projectId) {
            var self = this;
            $.ajax({
                url: urls.getInputOutputByMonthUrl,
                //  type:"post",
                data: {organId: organId, projectId: projectId},
                success: function (result) {
                    if (!_.isEmpty(result)) {
                        self.render(result);
                    }
                }
            });
        },
        render: function (result) {
            var self = this;
            var dataValue = [];
            var xAxisData = [];
            $.each(result.list, function (i, o) {
                dataValue.push(Tc.formatFloat(o.inputOutputRatio * 100));
                var month = (o.yearMonth + "").substring(2, 4) + "/" + (o.yearMonth + "").substring(4, 6);
                xAxisData.push(month);
            });
            xAxisData.push('当年累计');
            if (undefined != result.dto)
                dataValue.push(Tc.formatFloat((result.dto.outputFeeInYear / result.dto.inputFeeInYear) * 100));
            else
                dataValue.push(0);
            xAxisData.reverse();
            dataValue.reverse();
            self.chart.clear();
            inputOutputRatioOption.xAxis[0].data = xAxisData;
            inputOutputRatioOption.series[0].data = dataValue;
            self.chart.setOption(inputOutputRatioOption);
            self.resizeChart();
        },
        resizeChart: function () {
            var self = this;
            if (self.chart != null) {
                self.chart.resize();
            }
        }
    };
	//明细分析-本年投入费用-费用明细
    var feeTypeObj = {
        organId: null,
        projectId: null,
        init: function (organId, projectId) {
            var self = this;
            if (self.organId == organId && self.projectId == projectId) {
                return;
            }
            self.organId = organId;
            self.projectId = projectId;
            self.getRequestData();
        },
        getRequestData: function () {
            var self = this;
            $.ajax({
                url: urls.queryProjectFeeTypeUrl,
                type: 'get',
                success: function (data) {
                    self.loadGridFun(data);
                },
                error: function () {
                }
            });
        },
        loadGridFun: function (data) {
            var self = this;
            var modelArray = data.listModel;
            for (var i = 2; i < modelArray.length; i++) {
                modelArray[i].formatter = toFormatter;
            }
            projectFeeDetailGridObj.colModel = modelArray;
            projectFeeDetailGridObj.colName = data.listName;
            projectFeeDetailGridObj.init(self.organId, self.projectId);
        }
    };
	//明细分析-本年投入费用-费用明细
    var projectFeeDetailGridObj = {
        treeGridId: '#feeDetailTreegrid',
        first: true,
        colName: null,
        colModel: null,
        organId: null,
        projectId: null,
        init: function (organId, projectId) {
            var self = this;
            if (self.organId == organId && self.projectId == projectId) {
                return;
            }
            self.organId = organId;
            self.projectId = projectId;
            self.initTreeGridFun(organId, projectId);
        },
        initTreeGridFun: function (organId, projectId) {
            var self = this;
            var param = {
                organId: organId,
                projectId: projectId
            };
            if (!self.first) {
                self.reloadGridFun(param);
            }
            ;
            $(self.treeGridId).jqGrid({
                treeGrid: true,
                treeGridModel: 'adjacency', //treeGrid模式，跟json元数据有关 ,adjacency/nested
                url: urls.getFeeDetailByIdUrl,
                datatype: 'json',
                //mtype: "POST",
                height: "280px",
                autoWidth: true,
                ExpandColClick: true, //当为true时，点击展开行的文本时，treeGrid就能展开或者收缩，不仅仅是点击图片
                ExpandColumn: 'projectName',//树状结构的字段
                rowHeight: 36,
                styleUI: 'Bootstrap',
                colNames: self.colName,
                colModel: self.colModel,
                jsonReader: {
                    root: "rows",
                    total: "total",
                    repeatitems: true
                },
                beforeRequest: function () {
                    var data = this.p.data;
                    var postData = this.p.postData;
                    if (postData.organId == null) {
                        postData.organId = reqOrgId;
                    }
                    postData.projectId = self.projectId;
                    postData.type = '1';
                    if (postData.n_level != null && postData.n_level.length != 0) {
                        level = parseInt(postData.n_level, 10);
                        postData.n_level = level + 1;
                        postData.type = '2';
                    }
                },
                treeIcons: {
                    "plus": "ct-glyphicon-plus",
                    "minus": "ct-glyphicon-minus"
                },
                pager: "false"
            });
            self.resizeScroll();
            self.first = false;
        },
        reloadGridFun: function (param) {
            var self = this;
            $(self.treeGridId).clearGridData().setGridParam({
                postData: param
            }).trigger("reloadGrid");
        },
        resizeScroll: function () {
            var self = this;
            $(self.treeGridId).closest(".ui-jqgrid-bdiv").css({'overflow-x': 'auto'});
        }
    };

    //列格式
    var toFormatter = function (value) {
        return Tc.formatFloat(value);
    };

    /***
     * 参与项目明细
     */
    var participatejProjectGridOption = {
        url: urls.getParticipateProjectDetailUrl,
        datatype: "json",
        postData: {},
        mtype: 'POST',
        altRows: true,//设置表格行的不同底色
        autowidth: true,
        height: 500,
        colNames: ['项目名称', '角色', '投入人/天', '开始时间', '结束时间', '状态'],
        colModel: [
            {name: 'projectName', sortable: false, fixed: false, width: 220, align: 'center',editable: false},
            {name: 'empName', sortable: false, fixed: false, width: 135, align: 'center', editable: false },
            {name: 'manpowerInput', sortable: false, fixed: false,  width: 100, align: 'center', editable: false,
            	formatter: function (value, options, row) {
            		return Tc.formatFloat(value);
            	}
            },
            {name: 'startDate', sortable: false, fixed: false, width: 120, align: 'center', editable: false,
                formatter: function (value) {
                    if (value == null || value == '') {
                        return '-';
                    }
                    return value;
                }
            },
            {name: 'endDate', sortable: false, fixed: false, width: 120, align: 'center', editable: false,
                formatter: function (value) {
                    if (value == null || value == '') {
                        return '-';
                    }
                    return value;
                }
            },
            {name: 'projectProgress', sortable: false, fixed: false, width: 120, align: 'center', editable: false}
        ],
        viewrecords: true,
        rowHeight: 36,
        styleUI: 'Bootstrap',
        loadComplete: function (xhr) {
            $("#participatejProjectTable").find(".ui-jqgrid-bdiv").height(tableHeight + 50);
            participatejProjectDetailObj.resizeGrid();
        }
    };
    var participatejProjectDetailObj = {
        gridId: '#participatejProjectGrid',
        resultData: null,
        init: function (organId, empId) {
            var self = this;
            if (self.hasInit) {
                self.reloadData(organId, empId);
                return;
            }
            participatejProjectGridOption.postData = {organId: organId, empId: empId};
            $(self.gridId).jqGrid(participatejProjectGridOption);
            self.hasInit = true;
        },
        reloadData: function (organId, empId) {
            var self = this;
            var params = {organId: organId, empId: empId};
            $(self.gridId).clearGridData().setGridParam({
                postData: params
            }).trigger("reloadGrid");
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#participatejProjectTable').width());
        }
    };

    /***
     * 初始化echart
     * @param domId
     * @returns {*}
     */
    function initEChart(domId) {
        return echarts.init(document.getElementById(domId));
    }

    /**
     * 清除echart面板
     * @param eChartObj
     */
    function clearEChart(eChartObj) {
        if (eChartObj) {
            eChartObj.clear();
        }
    }

    /**
     * 重新加载表格
     * @param gridId
     */
    function resizeGrid(gridId) {
        var parentDom = $('#gbox_' + gridId.split('#')[1]).parent();
        $(gridId).setGridWidth(parentDom.width());
    }

    /**
     * 返回当前焦点tab页显示的区域id
     */
    function getActiveTabId(targetDom) {
        var _currObj = _.find($(targetDom), function (obj) {
            return $(obj).hasClass('selectList');
        });
        return _currObj != null ? $(_currObj).attr('page') : 'page-one';
    }

    function formatPieData(key, name, value) {
        if (!value) {
            return {};
        }
        //页面展示为 ：name，value
        return {
            value: value,
            key: key,
            name: (name + '，' + Tc.formatNumber(value))
        };
    }

    function hideChart(chartId, hide) {
        var $chart = $("#" + chartId);
        if (hide) {
            $chart.children('div.loadingmessage').remove();
            $chart.children().hide();
            $chart.append("<div class='loadingmessage'>暂无数据</div>");
        } else {
            $chart.children('div.loadingmessage').remove();
            $chart.children().show();
        }
    }

    var requestErrorData = [];
    var requestInfoData = [];
    /**
     * 项目人力盘点数据导入
     * 模板/按钮等切换事件
     */
    projectMessageUploadObj = {
        date: null,
        organId: null,
        init: function (organId) {
            var self = this;
            var date = new Date();
            self.date = date.getFullYear();
            self.organId = organId;
            self.getPostDateFun(organId);
        },
        getPostDateFun: function (organId) {
            var self = this;
            $.ajax({
                url: urls.getDbDateUrl,
                type: 'post',
                success: function (data) {
                    self.date = data.year;
                    self.radioChangeModelFun();
                    self.downLoadCostFun();
                    self.downLoadPersonFun();
                    self.selectChangeFun(data);
                    self.btnChickStyleFun();
                    self.projectModelFileSelectedFun();
                    self.continueImportBtnFun();
                    self.cancelImportBtnFun();
                },
                error: function () {
                }
            });
        },
        radioChangeModelFun: function () {
            $('#optionsRadiosCost').unbind('click').bind('click', function () {
                $('#downLoadProjectCostModel').show();
                $('#downLoadProjectPersonModel').hide();
            });
            $('#optionsRadiosPerson').unbind('click').bind('click', function () {
                $('#downLoadProjectPersonModel').show();
                $('#downLoadProjectCostModel').hide();
            });
        },
        downLoadCostFun: function () {
            var self = this;
            self.setCostHrefFun('downLoadProjectCostModel');
        },
        setCostHrefFun: function (id) {
            $('#' + id).unbind('click').bind('click', function () {
                window.location.href = urls.downLoadProjectInfoAndCostUrl;
            });
        },
        downLoadPersonFun: function () {
            var self = this;
            self.setPersonHrefFun('downLoadProjectPersonModel');
        },
        setPersonHrefFun: function (id) {
            $('#' + id).unbind('click').bind('click', function () {
                window.location.href = urls.downLoadProjectPersonExcelUrl;
            });
        },
        selectChangeFun: function (data) {
            var self = this;

            self.appendSelectOptionFun(data.dateList);

            $('#selectTotalId').change(function () {
                var option = $('#selectTotalId option:selected').attr('value');
                if (option == 'month') {
                    self.appendSelectOptionFun(defaultDatas.curMonth);
                } else if (option == 'quarter') {
                    self.appendSelectOptionFun(defaultDatas.curQuarter);
                } else if (option == 'halfYear') {
                    self.appendSelectOptionFun(defaultDatas.curHalfYear);
                } else if (option == 'year') {
                    self.appendSelectOptionFun(defaultDatas.curYear);
                }
            });
        },
        appendSelectOptionFun: function (data) {
            var self = this;
            $('#selectDetailId').empty();
            var option = "";
            $.each(data, function (ind, obj) {
                option += "<option>" + obj + "</option>";
            });
            $('#selectDetailId').append(option);
        },
        projectModelFileSelectedFun: function () {
            var self = this;
            self.addBtnDisabledStyleFun();
            $('#projectModelFile').change(function () {
                $('#importState').hide();
                self.changeImportBtnShowFun();
                self.setBtnDisabledByFileFun();
            });
        },
        btnChickStyleFun: function () {
            var self = this;
            $('#btn-form').unbind('click').bind('click', function () {
                self.changeNowImportBtnShowFun();
                $('#organIdHidden').val(self.organId);
                self.importFormSubmitFun("1");
            });
        },
        importFormSubmitFun: function (type) {
            var self = this;
            $('#importProjectModelForm').ajaxSubmit({
                url: urls.importProjectExcelDatasUrl,
                type: 'post',
                data: {type: type},
                dataType: 'json',
                success: function (data) {
                    self.setImportStateAllHideenFun();
                    self.setFileErrorsFun(data);
                },
                error: function (XmlHttpRequest, errorThrown) {
                    $('#importProjectModelForm').resetForm();
                    self.loadErrorsFun("请求后台超时.");
                }
            });
        },
        continueImportBtnFun: function () {
            var self = this;
            $('#continueImport').unbind('click').bind('click', function () {
                $('#organIdHidden').val(self.organId);
                self.importFormSubmitFun("2");
            });
        },
        cancelImportBtnFun: function () {
            var self = this;
            $('#cancelImport').unbind('click').bind('click', function () {
                $('#importProjectModelForm').resetForm();
                self.hideImpStateModelFun();
                self.setImportStateAllHideenFun();
                self.changeImportBtnShowFun();
                self.setBtnDisabledByFileFun();
            });
        },
        setBtnDisabledByFileFun: function () {
            var self = this;
            var fileVal = $('#projectModelFile').val();
            if (fileVal != null && fileVal != '') {
                self.removeBtnDisabledStyleFun();
            } else {
                self.addBtnDisabledStyleFun();
            }
        },
        setFileErrorsFun: function (data) {
            var self = this;
            if (data.fileError != undefined) {
                self.loadErrorsFun(data.fileError);
                self.changeImportBtnShowFun();
            } else if (data.fileIsRepeat != undefined) {
                self.loadErrorsFun(data.fileIsRepeat);
            } else {
                self.loadErrorsOrOperateFun(data);
            }
        },
        loadErrorsOrOperateFun: function (data) {
            var self = this;
            self.setImportStateAllHideenFun();
            self.showImpStateModelFun();
            self.loadTemplateErrorsFun(data);
            self.loadFileNullErrorsFun(data);
            self.loadImportErrorsFun(data);
            self.loadOperateErrorsFun(data);
            self.loadImportSuccessFun(data);
        },
        loadTemplateErrorsFun: function (data) {
            var self = this;
            if (data.templateError != undefined) {
                $('#templateInfo').show();
                if (data.templateError == 'personTypeError') {
                    $('#personType').show();
                    $('#costType').hide();
                    self.setPersonHrefFun('personType');
                } else {
                    $('#costType').show();
                    $('#personType').hide();
                    self.setCostHrefFun('costType');
                }
                self.changeImportBtnShowFun();
            }
        },
        loadFileNullErrorsFun: function (data) {
            var self = this;
            if (data.contentIsNull != undefined) {
                self.loadErrorsFun(data.contentIsNull);
            }
        },
        loadErrorsFun: function (data) {
            var self = this;
            self.setImportStateAllHideenFun();
            $('#fileErrorsId').html(data);
            $('#fileInfo').show();
            self.changeImportBtnShowFun();
        },
        loadImportErrorsFun: function (data) {
            var self = this;
            if (data.listErrors != undefined) {
                requestErrorData = data.listErrors;
                formalErrorGridObj.init();
                $('#importErrorNum').html(data.listErrors.length);
                $('#importError').show();
//				$('#continueImportBtn').hide();
                self.changeImportBtnShowFun();
            }
        },
        loadOperateErrorsFun: function (data) {
            if (data.listInfo != undefined) {
                requestInfoData = data.listInfo;
                updateAndDeleteTipsGridObj.init();
                $('#import-info-year').html(data.infoDate.substr(0, 4));
                $('#import-info-month').html(data.infoDate.substr(4, 5));
                $('#importInfo').show();
            }
        },
        loadImportSuccessFun: function (data) {
            var self = this;
            if (data.success != undefined) {
                $('#totalNum').html(data.totalNum);
                $('#importSuccess').show();
                $('#importProjectModelForm').resetForm();
                var style = 'display : none;';
                $('#downLoadProjectCostModel').removeAttr('style');
                $('#downLoadProjectPersonModel').removeAttr('style').attr(style);
                self.changeImportBtnShowFun();
                self.showSuccessDatasFun();
                self.setBtnDisabledByFileFun();
            }
        },
        setImportStateAllHideenFun: function () {
            $('#templateInfo').hide();
            $('#fileInfo').hide();
            $('#importError').hide();
            $('#importInfo').hide();
            $('#importSuccess').hide();
        },
        changeImportBtnShowFun: function () {
            var self = this;
            $('#btnImportText').show();
            $('#btnNowImportText').hide();
            self.removeBtnDisabledStyleFun();
        },
        changeNowImportBtnShowFun: function () {
            var self = this;
            $('#btnNowImportText').show();
            $('#btnImportText').hide();
            self.addBtnDisabledStyleFun();
        },
        addBtnDisabledStyleFun: function () {
            $('#btn-form').attr('disabled', 'disabled');
        },
        removeBtnDisabledStyleFun: function () {
            $('#btn-form').removeAttr('disabled');
        },
        showImpStateModelFun: function () {
            $('#importState').show();
        },
        hideImpStateModelFun: function () {
            $('#importState').hide();
        },
        showSuccessDatasFun: function () {
            $('#showDatas').unbind('click').bind('click', function () {
                $(".rightBodyPage").hide();
                $(".leftListDiv").removeClass("selectList");
                $("#page-two").show();
                $(".leftListDiv[page=page-two]").addClass("selectList");
                changeData('page-two');
            });
        }
    }

    /***
     * 格式错误信息
     */
    var formalErrorGridOption = {
        datatype: "local",
        autowidth: true,
        height: 200,
        colNames: ['错误位置', '错误信息'],
        colModel: [
            {name: 'locationError', sortable: false, fixed: true, width: 200, align: 'center', editable: false},
            {name: 'errorMsg', sortable: false, fixed: true, width: 850, align: 'left', editable: false}
        ],
        scroll: true,
        rownumbers: true,
        hoverrows: false,
        editable: false,
        rowHeight: 36,
        styleUI: 'Bootstrap',
        loadComplete: function (xhr) {
            $("#formalErrorTable").find(".ui-jqgrid-bdiv").height(200 + 2);
        }
    };
    var formalErrorGridObj = {
        gridId: '#formalErrorGrid',
        resultData: null,
        init: function () {
            var self = this;
            self.appendGrid();
        },
        reloadData: function () {
            var self = this;
            for (var i = 0; i <= requestErrorData.length; i++) {
                $(self.gridId).jqGrid('addRowData', i + 1, requestErrorData[i]);
            }
            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#importError').width() * 0.98);
        },
        clearGrid: function () {
            var self = this;
            $(self.gridId).clearGridData();
        },
        appendGrid: function () {
            var self = this;
            $('.importErrorTr').remove();
            var tr = "";
            var num = 1;
            $.each(requestErrorData, function (index, object) {
                tr += "<tr class='importErrorTr'><td>" + num + "</td><td>"
                    + object.locationError + "</td><td class='importErrorTd'>"
                    + object.errorMsg + "</td></tr>";
                num++;
            });
            $(self.gridId).append(tr);
        }
    };

    /***
     * 删除更新提示
     */
    var updateAndDeleteTipsGridOption = {
        datatype: "local",
        autowidth: true,
        height: 200,
        colNames: ['项目名称', '执行动作'],
        colModel: [
            {name: 'projectName', sortable: false, fixed: true, width: 200, align: 'center', editable: false},
            {name: 'action', sortable: false, fixed: true, width: 850, align: 'left', editable: false}
        ],
        viewrecords: true,
        scroll: true,
        rownumbers: true,
        rownumWidth: 100,
        rowHeight: 36,
        styleUI: 'Bootstrap',
        loadComplete: function (xhr) {
            $("#updateAndDeleteTipsTable").find(".ui-jqgrid-bdiv").height(200 + 2);
        }
    };
    var updateAndDeleteTipsGridObj = {
        gridId: '#updateAndDeleteTipsGrid',
        resultData: null,
        init: function () {
            var self = this;
            self.appendGrid();
        },
        reloadData: function () {
            var self = this;
            for (var i = 0; i <= requestInfoData.length; i++) {
                $(self.gridId).jqGrid('addRowData', i + 1, requestInfoData[i]);
            }
            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#importInfo').width() * 0.98);
        },
        clearGrid: function () {
            var self = this;
            $(self.gridId).clearGridData();
        },
        appendGrid: function () {
            var self = this;
            $('.importInfoTr').remove();
            var tr = "";
            var num = 1;
            $.each(requestInfoData, function (index, object) {
                if (object.action.indexOf('删除') >= 0) {
                    tr += "<tr class='importInfoTr'><td>" + num + "</td><td>"
                        + object.projectName + "</td><td class='importInfoTd'>"
                        + object.action + "</td></tr>";
                } else {
                    tr += "<tr class='importInfoTr'><td>" + num + "</td><td>"
                        + object.projectName + "</td><td>"
                        + object.action + "</td></tr>";
                }
                num++;
            });
            $(self.gridId).append(tr);
        }
    };

    var pageOneObj = {
        trainCover: 0.0,
        init: function (organId) {
            var self = this;
            self.organId = organId;
            self.requestTrainCostYear();
            self.requestTrainPlan();
            self.requestTrainCover();
            toolbarStyleObj.init();
            projectConAndAvgNumObj.init(self.organId);
            projectInputOutputNumObj.init(self.organId);
            projectLoadNumObj.init(self.organId);
            amountObj.init(self.organId);
            inputOutputMapObj.init(self.organId);
            profitLossProjectObj.init(self.organId);
            trainCostObj.init(self.organId);
            projectManpowerObj.init(self.organId);
            projectTypeObj.init(self.organId);

        },
        requestTrainCostYear: function () {
            var self = this;
            $.get(urls.getTrainCostYearUrl, {organId: self.organId}, function (rs) {
                if (!_.isEmpty(rs)) {
                    var $parent = $('#costNumTxt').parents('.body-div');
                    $parent.children('div.noData').remove();
                    $parent.children().show();
                    self.costYear = rs.year;
                    $('#costYearTxt').text(rs.year);
                    $('#costNumTxt').text(rs.cost);
                    $('#budgetRateTxt').text(Tc.formatFloat(rs.budgetRate * 100));
                } else {
                    $('#costYearTxt').text(new Date().getYear());
                    var $parent = $('#costNumTxt').parents('.body-div');
                    $parent.children('div.noData').remove();
                    $parent.children().hide();
                    $parent.append('<div class="noData">暂无数据</div>');
                }
            });
        },
        requestTrainPlan: function () {
            var self = this;
            $.get(urls.getTrainPlanUrl, {organId: self.organId}, function (rs) {
                if (!_.isEmpty(rs)) {
                    var $parent = $('#completeRateTxt').parents('.body-div');
                    $parent.children('div.noData').remove();
                    $parent.children().show();
                    self.completeYear = rs.year;
                    $('#completeYearTxt').text(rs.year);
                    $('#completeRateTxt').text(Tc.formatFloat(rs.completeRate * 100));
                } else {
                    $('#completeYearTxt').text(new Date().getFullYear());
                    var $parent = $('#completeRateTxt').parents('.body-div');
                    $parent.children('div.noData').remove();
                    $parent.children().hide();
                    $parent.append('<div class="noData">暂无数据</div>');
                }
            });
        },
        requestTrainCover: function () {
            var self = this;
            $.get(urls.getTrainCoverUrl, {organId: self.organId}, function (rs) {
                if (!_.isEmpty(rs)) {
                    var $parent = $('#coverageRateTxt').parents('.body-div');
                    $parent.children('div.noData').remove();
                    $parent.children().show();
                    $('#coverageYearTxt').text(self.costYear);
                    self.trainCover = rs.result;
                    $('#coverageRateTxt').text(Tc.formatFloat(rs.result * 100));
                } else {
                    $('#coverageYearTxt').text(new Date().getFullYear());
                    var $parent = $('#coverageRateTxt').parents('.body-div');
                    $parent.children('div.noData').remove();
                    $parent.children().hide();
                    $parent.append('<div class="noData">暂无数据</div>');
                }
            });
        }
    }

    var pageTwoObj = {
        init: function (organId) {
            var self = this;
            self.organId = organId;
        }
    }


    /**
     * 切换机构或点击tab页时,更新页面数据
     */
    function changeData(targetAreaId) {
        var selOrganId = reqOrgId;
        if (targetAreaId == 'page-three') {
            projectMessageUploadObj.init(selOrganId);
        } else if (targetAreaId == 'page-two') {
//            pageTwoObj.init(selOrganId);
            leadingProjectObj.init(selOrganId);
            participateDetailObj.init(selOrganId);
            employeeDetailObj.init(selOrganId);

        } else {
            timeLineObj.init(selOrganId);
            pageOneObj.init(selOrganId);
        }
    }

    changeData('page-one');

    /*切换左边导航*/
    $(".leftListDiv").click(function (e) {
        e.stopPropagation();
        if ($(this).hasClass("selectList")) {
            return;
        } else {
            var _page = $(this).attr("page");
            $(".rightBodyPage").hide();
            $("#" + _page).show();
            $(".leftListDiv").removeClass("selectList");
            $(this).addClass("selectList");
            changeData(_page);
            $('#projectSearchTxt').val("");
            $('#participateSearchTxt').val("");
            $('#employeeSearchTxt').val("");
            $('#employeeConditionSearchForm').resetForm();
            $('#dominantConditionSearchForm').resetForm();
            $('#participateConditionSearchForm').resetForm();
        }
    });

    /*
     显示 tableView chart View
     */
    $(".rightSetUpBtnDiv").click(function () {
        if ($(this).hasClass("rightSetUpBtnSelect"))return;
        $(this).parents(".rightSetUpBtn").find(".rightSetUpBtnDiv").removeClass("rightSetUpBtnSelect");
        $(this).addClass("rightSetUpBtnSelect");
        if ($(this).parents(".SetUpBody").attr("view") == "chart") {
            $(this).parents(".SetUpBody").find(".table-view").show();
            $(this).parents(".SetUpBody").find(".chart-view").hide();
            $(this).parents(".SetUpBody").attr("view", "table");

            outputInputPercentObj.chart.resize();
            projectManpowerObj.chart.resize();
        } else {
            $(this).parents(".SetUpBody").find(".chart-view").show();
            $(this).parents(".SetUpBody").find(".table-view").hide();
            $(this).parents(".SetUpBody").attr("view", "chart");
            amountObj.chart.resize();
            trainCostObj.chart.resize();
        }
    });
    /**
     * 项目列表/人员统计表标签切换
     * */
    $(".index-jxmb-btn").click(function () {
        if ($(this).hasClass("index-jxmb-btn-select"))return;
        $(this).parents(".index-jxmb-tab").find(".index-jxmb-btn").removeClass("index-jxmb-btn-select");
        $(this).addClass("index-jxmb-btn-select");
        if ($(this).parents(".SetUpBody").attr("view") == "project") {
            $(this).parents(".SetUpBody").find(".project-view").removeClass('hide');
            $(this).parents(".SetUpBody").find(".employee-view").addClass('hide');
            $(this).parents(".SetUpBody").attr("view", "employee");
            leadingProjectObj.resizeGrid();
            participateDetailObj.resizeGrid();
        } else {
            $(this).parents(".SetUpBody").find(".employee-view").removeClass('hide');
            $(this).parents(".SetUpBody").find(".project-view").addClass('hide');
            $(this).parents(".SetUpBody").attr("view", "project");
            employeeDetailObj.resizeGrid();
        }
    });
    /**
     * 主导项目/参与项目标签切换
     * */
    $(".index-proj-btn").click(function () {
        if ($(this).hasClass("index-proj-btn-select"))return;
        $(this).parents(".index-proj-tab").find(".index-proj-btn").removeClass("index-proj-btn-select");
        $(this).addClass("index-proj-btn-select");
        if ($(this).parents(".project-view").attr("view") == "dominant") {
            $(this).parents(".project-view").find(".dominant-view").removeClass('hide');
            $(this).parents(".project-view").find(".participate-view").addClass('hide');
            $(this).parents(".project-view").attr("view", "participate");
            leadingProjectObj.resizeGrid();
        } else {
            $(this).parents(".project-view").find(".participate-view").removeClass('hide');
            $(this).parents(".project-view").find(".dominant-view").addClass('hide');
            $(this).parents(".project-view").attr("view", "dominant");
            participateDetailObj.resizeGrid();
        }
    });

    $(window).resize(function () {
        var _page = $('.leftListDiv.selectList').attr('page');
        if (_page == 'page-one') {
            changeChartsResize();
        }
        if (_page == 'page-two') {
            changeChartsTwoResize();
        }
    });
    /**项目总览页面重置echart对象*/
    function changeChartsResize() {
        amountObj.chart.resize();
        outputInputPercentObj.chart.resize();
        inputOutputMapObj.chart.resize();
        profitLossProjectObj.chart.resize();
        profitLossAmountObj.chart.resize();
        trainCostObj.chart.resize();
        projectManpowerObj.chart.resize();
        projectTypeObj.chart.resize();
    }

    function changeChartsTwoResize() {
        leadingProjectObj.resizeGrid();
        participateDetailObj.resizeGrid();
        employeeDetailObj.resizeGrid();
    }

    function dateCompare(date1, date2) {
        date1 = date1.replace(/\-/gi, "/");
        date2 = date2.replace(/\-/gi, "/");
        var time1 = new Date(date1).getTime();
        var time2 = new Date(date2).getTime();
        if (time1 > time2) {
            return -1;
        } else if (time1 == time2) {
            return 0;
        } else {
            return 1;
        }
    }

    $("#moreLabel").click(function () {
        var _this = $(this);
        if (_this.attr("class") == "more-search-label icon-panel-down") {
            _this.removeClass("icon-panel-down icon-panel-up");
            _this.text('精简筛选条件');
            _this.addClass("icon-panel-up");
            $("#dominantCondition").removeClass('hide');
        } else {
            _this.removeClass("icon-panel-down icon-panel-up");
            _this.text('更多筛选条件');
            _this.addClass("icon-panel-down");
            $("#dominantCondition").addClass('hide');
        }
    });
    $("#participateMoreLabel").click(function () {
        var _this = $(this);
        if (_this.attr("class") == "more-search-label icon-panel-down") {
            _this.removeClass("icon-panel-down icon-panel-up");
            _this.text('精简筛选条件');
            _this.addClass("icon-panel-up");
            $("#participateCondition").removeClass('hide');
        } else {
            _this.removeClass("icon-panel-down icon-panel-up");
            _this.text('更多筛选条件');
            _this.addClass("icon-panel-down");
            $("#participateCondition").addClass('hide');
        }
    });
    $("#employeeMoreLabel").click(function () {
        var _this = $(this);
        if (_this.attr("class") == "more-search-label icon-panel-down") {
            _this.removeClass("icon-panel-down icon-panel-up");
            _this.text('精简筛选条件');
            _this.addClass("icon-panel-up");
            $("#employeeCondition").removeClass('hide');
        } else {
            _this.removeClass("icon-panel-down icon-panel-up");
            _this.text('更多筛选条件');
            _this.addClass("icon-panel-down");
            $("#employeeCondition").addClass('hide');
        }
    });
    $('.form_date').datetimepicker({
        language: 'cn',
//        todayBtn:  1,
        autoclose: true,
//		todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        format: 'yyyy-mm-dd',
        pickerPosition: 'bottom-left'
    });
    $('.clearText').click(function () {
        $(this).parents('.input-group').find('.form-control').val('');
    });
    /**动态设置echarts x轴距离*/
    function setGridX(arrData, optionGrid){
    	var maxVal = Math.max.apply(this, arrData);
    	if(maxVal > 1000000){
    		optionGrid.x = 70;
    	} else if(maxVal > 100000){
    		optionGrid.x = 60;
    	}else if(maxVal > 10000){
			optionGrid.x = 50;
		} else if(maxVal > 1000){
			optionGrid.x = 40;
    	} else {
    		optionGrid.x = 30;
    	}
    }
    /**保留两位小数
     * @param data 数组
     * */
    function setFormatFloat(data){
    	$.each(data, function(ind, obj){
    		if(obj != '-'){
    			data[ind] = Tc.formatFloat(obj);
    		}
    	});
    	return data;
    }

    /**无数据/隐藏数据加载*/
    function hideChart(chartId, hide) {
        var $chart = $("#" + chartId);
        if (hide) {
            $chart.children('div.loadingmessage').remove();
            $chart.children().hide();
            $chart.append("<div class='loadingmessage'>暂无数据</div>");
        } else {
            $chart.children('div.loadingmessage').remove();
        }
    }
    /**数据加载*/
    function loadingChart(chartId) {
        var $chart = $("#" + chartId);
        $chart.children('div.loadingmessage').remove();
        $chart.children().hide();
        $chart.prepend("<div class='loadingmessage'>数据加载中</div>");
    }
    /** 去除字体红色及提示框 */
    function removeRedAndTooltip($id){
    	$id.removeClass('fontRed').removeAttr('data-original-title');
    }
    /** 增加字体红色及提示框 */
    function addRedAndTooltip($id, title){
		$id.addClass('fontRed');
		$id.attr('data-toggle', 'tooltip').attr('data-placement', 'right')
			.attr('data-original-title', title);
		$("[data-toggle='tooltip']").tooltip();
    }
    
})
;