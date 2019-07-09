require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie', 'echarts/chart/gauge', "echarts/chart/scatter",
    'bootstrap', 'jgGrid', 'underscore', 'utils', 'timeLine2','searchBox3','jquery-ui', 'message', 'form'], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
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

        getProjectConAndAvgNumUrl: webRoot + '/humanInventory/getProjectConAndAvgNum.do',           //获取本年度项目总数和人均项目数
        getProjectInputOutputNumUrl: webRoot + '/humanInventory/getProjectInputOutputNum.do',           //获取本年度项目投入产出
        getProjectLoadNumUrl: webRoot + '/humanInventory/getProjectLoadNum.do',           //获取本年度项目负荷
        getProjectNumByYearUrl: webRoot + '/humanInventory/getProjectNumByYear.do',           //获取本年度主导项目总数
        getProjectInputNumUrl: webRoot + '/humanInventory/getProjectInputNum.do',           //获取本年度及本月项目投入金额
        getProjectOutputNumUrl: webRoot + '/humanInventory/getProjectOutputNum.do',           //获取本年度及本月项目产出金额
        queryInputOutputUrl: webRoot + "/humanInventory/queryInputOutput.do",	//投入产出分析-金额分析/产出投入比
        queryInputOutputMapUrl: webRoot + "/humanInventory/queryInputOutputMap.do",	//投入产出地图
        queryProfitLossProjectUrl: webRoot + "/humanInventory/queryProfitLossProject.do",	//盈亏项目数分析/盈亏金额分析
        getProfitAndLossCountAmountUrl: webRoot + "/humanInventory/getProfitAndLossCountAmount.do",	//盈亏项目明细-盈亏总金额
        getProfitAndLossProjectDetailUrl: webRoot + "/humanInventory/getProfitAndLossProjectDetail.do",	//盈亏项目明细-盈亏项目明细
        queryProjectManpowerUrl: webRoot + "/humanInventory/queryProjectManpower.do",	//项目投入统计-人力统计
        queryProjectInputCostUrl: webRoot + "/humanInventory/queryProjectInputCost.do",	//项目投入统计-费用统计
        queryProjectTypeUrl: webRoot + "/humanInventory/queryProjectType.do",	//项目类型分析
        queryProjectFeeTypeUrl: webRoot + "/humanInventory/queryProjectFeeType.do",	//项目费用类型
        downLoadProjectInfoAndCostUrl: webRoot + "/humanInventory/downLoadProjectInfoAndCost.do",	//下载《项目信息及费用数据》模板
        downLoadProjectPersonExcelUrl: webRoot + "/humanInventory/downLoadProjectPersonExcel.do",	//下载《项目人员数据》模板
        importProjectExcelDatasUrl: webRoot + "/humanInventory/importProjectExcelDatas.do",	//数据导入
        getDbDateUrl: webRoot + "/humanInventory/getDbDate.do"	// 获取数据库时间
        
    };
    
    $(win.document.getElementById('tree')).next().show();
//    win.setCurrNavStyle();
    var reqOrgId = win.currOrganId;

    var ecConfig = require('echarts/config');
    var TextShape = require('zrender/shape/Text');
    var pieDefaultColor = ['#0b98e0', '#00bda9', '#4573a7', '#92c3d4', '#de6e1b', '#ff0084', '#af00e1', '#8d55f6', '#6a5888', '#2340f3'];
    var defaultDatas = {
		projectConNum : 0,
		projectAvgNum : 0,
		projectInputNum : 0,
		projectOutputNum : 0,
    	projectNumConText : "年主导项目",
    	projectNumAvgText : "年人均项目",
    	projectInputText : "年项目投入",
    	projectOutputText : "年项目产出",
    	curMonth: ['01','02','03','04','05','06','07','08','09','10','11','12'],
    	curQuarter: ['Q1','Q2','Q3','Q4'],
    	curHalfYear: ['06','12'],
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
    
    //获取本年度主导项目数量
    var projectConAndAvgNumObj = {
    	init : function(organId){
    		var self = this;
    		self.getRequestDatas(organId);
    	},
    	getRequestDatas: function(organId){
    		var self = this;
    		var param = {organId : organId};
    		$.ajax({
    			url: urls.getProjectConAndAvgNumUrl,
    			data: param,
    			type: 'post',
    			success: function(data){
    				defaultDatas.projectConNum = data.conNum;
    				defaultDatas.projectAvgNum = Tc.formatFloat(data.avgNum);
    				self.setJspDatas(data);
    				getProjectNumSelect();
    			},
    			error: function(){}
    		});
    	},
    	setJspDatas: function(data){
			$("#projectNumCurrentYear").html(data.year);
			$("#inputOutputCurrentYear").html(data.year);
    	}
    }
    
    //获取本年度项目投入产出
    var projectInputOutputNumObj = {
    		init : function(organId){
    			var self = this;
    			self.getRequestDatas(organId);
    		},
    		getRequestDatas: function(organId){
    			var self = this;
    			var param = {organId : organId};
    			$.ajax({
    				url: urls.getProjectInputOutputNumUrl,
    				data: param,
    				type: 'post',
    				success: function(data){
    					defaultDatas.projectInputNum = data.sumInputNum;
    					defaultDatas.projectOutputNum = data.sumOutputNum;
    					getInputOutputSelect();
    				},
    				error: function(){}
    			});
    		}
    }
    
    //获取本年度项目负荷
    var projectLoadNumObj = {
    		init : function(organId){
    			var self = this;
    			self.getRequestDatas(organId);
    		},
    		getRequestDatas: function(organId){
    			var self = this;
    			var param = {organId : organId};
    			$.ajax({
    				url: urls.getProjectLoadNumUrl,
    				data: param,
    				type: 'post',
    				success: function(data){
    					self.setJspData(data);
    				},
    				error: function(){}
    			});
    		},
    		setJspData : function(data){
    			$("#projectLoadNum").html(Tc.formatFloat(data.loadNum));
    		}
    }
    
    //无数据据时显示 暂无数据
    var showNoDataEcharts = function (echartObj) {
        var zr = echartObj.getZrender();
        zr.addShape(new TextShape({
            style: {
                x: (echartObj.dom.clientWidth - 56) / 2,
                y: (echartObj.dom.clientHeight - 20) / 2,
                color: '#ccc',
                text: '暂无数据',
                textFont: '14px 宋体'
            }
        }));
        zr.render();
    };
    
    //设置投入项目、项目产出按钮切换
    var toolbarStyleObj = {
		produceId: "projectNum_toolbar",
		investmentId: "investment_toolbar",
    	init : function(){
    		var self = this;
    		self.setInvestmentStyle(self.investmentId);
    		self.setProduceStyle(self.produceId);
    	},
    	setInvestmentStyle: function(id){
    		checkToolbarStyle(id);
    	},
    	setProduceStyle: function(id){
    		checkToolbarStyle(id);
    	}
    }
    
    //设置选中状态下本年及本月的数据赋值
    function getProjectNumSelect(){
    	var _name = $("#projectNum_toolbar").find(".select").text();
    	if(_name == '总数'){
    		$("#projectNumText").html(defaultDatas.projectNumConText);
    		$("#projectNum").html(defaultDatas.projectConNum);
		}else{
			$("#projectNumText").html(defaultDatas.projectNumAvgText);
			$("#projectNum").html(defaultDatas.projectAvgNum);
		}
    }
    
    //设置选中状态下本年及本月的数据赋值
    function getInputOutputSelect(){
    	var _name = $("#investment_toolbar").find(".select").text();
    	if(_name == '投入'){
    		$("#inputOutputText").html(defaultDatas.projectInputText);
    		$("#projectInputOutputNum").html(defaultDatas.projectInputNum);
    	}else{
    		$("#inputOutputText").html(defaultDatas.projectOutputText);
    		$("#projectInputOutputNum").html(defaultDatas.projectOutputNum);
    	}
    }
    
    function checkToolbarStyle(id){
    	$("#"+id+" span").unbind('click').bind('click',function(){
    		var par = $(this).parent();
    		var _t = this;
    		$.each(par.children(), function (i, o) {
    			if (this == _t) {
    				$(this).addClass("select");
    				if($(this).parent().prev().html() == '项目数'){
    					if($(this).text() == '总数'){
    						$("#projectNumText").html(defaultDatas.projectNumConText);
    						$("#projectNum").html(defaultDatas.projectConNum);
    					}else{
    						$("#projectNumText").html(defaultDatas.projectNumAvgText);
    						$("#projectNum").html(defaultDatas.projectAvgNum);
    					}
    				}else{
    					if($(this).text() == '投入'){
    						$("#inputOutputText").html(defaultDatas.projectInputText);
    						$("#projectInputOutputNum").html(defaultDatas.projectInputNum);
    					}else{
    						$("#inputOutputText").html(defaultDatas.projectOutputText);
    						$("#projectInputOutputNum").html(defaultDatas.projectOutputNum);
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
		legend : {
			y : 'bottom',
			data : [ '项目投入', '项目产出' ]
		},
		grid : {
			x: 55,
            y: 25,
            x2: 15,
			borderWidth : 0
		},
		xAxis : [ {
			type : 'category',
			axisTick : false,
			splitLine : false,
			axisLine : {
				lineStyle : {
					color : '#D7D7D7'
				}
			},
			axisLabel : {  
			    interval : 0   
			},
			data : []
		} ],
		yAxis : [ {
			type : 'value',
			splitLine : false,
			axisLine : false
		} ],
		calculable: false,
		series : [

				{
					name : '项目投入',
					type : 'bar',
					clickable: false,
					itemStyle : {
						normal : {
							color : '#0099cb',
							label : {
								show : true
							}
						}
					},
					data : []
				},
				{
					name : '项目产出',
					type : 'bar',
					clickable: false,
					itemStyle : {
						normal : {
							color : '#de6e1b',
							label : {
								show : true
							}
						}
					},
					data : []
				},
				{
					name : '项目投入',
					type : 'line',
					smooth : true,
					symbol : 'circle',
					clickable: false,
					itemStyle : {
						normal : {
							color : '#0099cb',
							label : {
								show : true
							}
						}
					},
					data : []

				},
				{
					name : '项目产出',
					type : 'line',
					smooth : true,
					symbol : 'circle',
					clickable: false,
					itemStyle : {
						normal : {
							color : '#de6e1b',
							label : {
								show : true
							}
						}
					},
					data : []

				} ]	
    };
    
    var amountObj = {
    	amountChart: initEChart("amountChart"),
    	option: amountBarOption,
		init: function(organId){
			var self = this;
			clearEChart(self.amountChart);
			self.getRequestDatas(organId);
		},
		getRequestDatas: function(organId){
			var self = this;
			var param = {organId : organId};
			$.ajax({
				url: urls.queryInputOutputUrl,
				data: param,
				type: 'post',
				success: function(data){
					self.getOption(data);
					outputInputPercentObj.init(data);
				},
				error: function(){}
			});
		},
		getOption: function(data){
			var self = this;
			if(data.sumDate != undefined && data.sumDate.length > 1){
				self.option.xAxis[0].data = data.sumDate;
				self.option.series[0].data = data.sumInputCon;
				self.option.series[1].data = data.sumOutputCon;
				self.option.series[2].data = data.sumInput;
				self.option.series[3].data = data.sumOutput;
				changeXAxisLabelRotate(self.option, data.sumDate);
				self.setOption();
			}else{
				showNoDataEcharts(self.amountChart);
			}
		},
		setOption: function(){
			var self = this;
			self.amountChart.setOption(self.option);
		}
    }
    
    /**
     * 投入产出分析-产出投入比
     */
    var outputInputPercentOption = {
		legend : {
			x : 'center',
			y : 'bottom',
			data : [ '产出投入比' ]
		},
		grid : {
			x: 55,
            y: 25,
            x2: 15,
			borderWidth : 0
		},
		xAxis : [{
			type : 'category',
			boundaryGap : true,
			axisLine : {
				lineStyle : {
					color : '#D7D7D7'
				}
			},
			axisLabel : {  
			    interval : 0   
			},
			splitLine : {
				show : false
			},
			axisTick : false,
			data : []
		}],
		yAxis : [{
			type : 'value',
			axisLine : false,
			splitLine : false,
			axisLabel : {
				formatter : '{value} %'
			}
		}],
		series : [ {
			name : '产出投入比',
			type : 'line',
			smooth : true,
			symbol : 'circle',
			clickable: false,
			data : [],
			itemStyle : {
				normal : {
					label : {
						show : true,
						formatter: function (params,ticket,callback) {
							return params.value + ' %'
						}
					}
				}
			}
		} ]
    };
    var outputInputPercentObj = {
		outputInputPercentChart: initEChart("outputInputPercentChart"),
    	option: outputInputPercentOption,
		init: function(data){
			var self = this;
			clearEChart(self.outputInputPercentChart);
			self.getOption(data);
		},
		getOption: function(data){
			var self = this;
			if(data.sumDate != undefined && data.sumDate.length > 1){
				self.option.xAxis[0].data = data.sumDate;
				self.option.series[0].data = data.percent;
				changeXAxisLabelRotate(self.option, data.sumDate);
				self.setOption();
			}else{
				showNoDataEcharts(self.outputInputPercentChart);
			}
		},
		setOption: function(){
			var self = this;
			self.outputInputPercentChart.setOption(self.option);
		}
    }
    
    /**
     * 投入产出地图
     */
    var inputOutputMapOption = {
			grid : {
				x: 15,
	            y: 5,
				borderWidth : 0
			},
			tooltip : {
				trigger : 'item',
				showDelay : 0,
				formatter : function(params) {
					if (params.value.length > 1) {
						return '累计投入: ' + params.value[0] + '万元<br/> '
								+ '累计产出: ' + params.value[1] + '万元 ';
					}
				}
			},
			legend : {
				x : 'center',
				y : 'bottom',
				data : [ '盈利', '有亏损', '亏损严重' ]
			},
			xAxis : [ {
				name : '投入 （ 万元 ）',
				min:0,
				nameTextStyle : {
					fontWeight : 'bold',
					color : '#ccc'
				},
				type : 'value',
				scale : true,
				splitLine : false,
				axisLabel : {
					show:false,
					interval : 0,
					formatter : '{value} 万元'
				}
			} ],
			yAxis : [ {
				min:0,
				type : 'value',
				scale : true,
				splitLine : false,
				axisLabel : {
					show:false,
					interval : 0,
					formatter : '{value} 万元'
				}
			} ],
			series : [
					{
						name : '盈利',
						type : 'scatter',
						symbol:'circle',
						symbolSize:8,
						data: [],
						itemStyle : {
							normal : {
								color : '#59B700',
								label : {
									show : true,
									formatter : function(params) {
										return params.name;
									},
									position : 'bottom'
								}
							}
						}
					},
					{
						name : '有亏损',
						type : 'scatter',
						symbol:'circle',
						symbolSize:8,
						data: [],
						itemStyle : {
							normal : {
								color : '#EFA100',
								label : {
									show : true,
									formatter : function(params) {
										return params.name;
									},
									position : 'bottom'
								}
							}
						}
					},
					{
						name : '亏损严重',
						type : 'scatter',
						symbol:'circle',
						symbolSize:8,
						data: [],
						itemStyle : {
							normal : {
								color : '#E83D2B',
								label : {
									show : true,
									formatter : function(params) {
										return params.name;
									},
									position : 'bottom'
								}
							}
						}
					} ]
    };
    var inputOutputMapObj = {
    		inputOutputMapChart: initEChart("inputOutputMapChart"),
    		option: inputOutputMapOption,
    		init: function(organId){
    			var self = this;
    			clearEChart(self.inputOutputMapChart);
    			self.getRequestDatas(organId);
    		},
    		getRequestDatas: function(organId){
    			var self = this;
    			var param = {organId : organId};
    			$.ajax({
    				url: urls.queryInputOutputMapUrl,
    				data: param,
    				type: 'post',
    				success: function(data){
    					self.getOption(data);
    				},
    				error: function(){}
    			});
    		},
    		getOption: function(data){
    			var self = this;
    			if(data.greenDatas != undefined && data.greenDatas.length > 0){
    				self.option.xAxis[0].max = data.maxNum + 20;
					self.option.xAxis[0].splitNumber = 10;
					self.option.yAxis[0].max = data.maxNum + 20;
					self.option.yAxis[0].splitNumber = 10;
    				self.option.series[0].data = data.greenDatas;
    				self.option.series[1].data = data.yellowDatas;
    				self.option.series[2].data = data.redDatas;
    				self.setOption();
    			}else{
    				showNoDataEcharts(self.inputOutputMapChart);
    			}
    		},
    		setOption: function(){
    			var self = this;
    			self.inputOutputMapChart.setOption(self.option);
    		}
    }
    
    /**
     * 盈亏项目数分析
     */
    var profitLossProjectOption = {
		legend : {
			y : 'bottom',
			data : [ '盈利项目', '亏损项目' ]
		},
		grid : {
			x: 25,
            y: 25,
            x2: 15,
			borderWidth : 0
		},
		xAxis : [ {
			type : 'category',
			axisTick : false,
			splitLine : false,
			axisLabel : {
				interval : 0
			},
			axisLine : {
				lineStyle : {
					color : '#D7D7D7'
				}
			},
			data : []
		} ],
		yAxis : [ {
			type : 'value',
			splitLine : false,
			axisLine : false
		} ],
		series : [

				{
					name : '盈利项目',
					type : 'bar',
					data : [],
					clickable : false,
					itemStyle : {
						normal : {
							color : '#0099cb',
							label : {
								show : true
							}
						}
					},
				},
				{
					name : '亏损项目',
					type : 'bar',
					data : [],
					clickable : false,
					itemStyle : {
						normal : {
							color : '#de6e1b',
							label : {
								show : true
							}
						}
					},
				},
				{
					name : '盈利项目',
					type : 'line',
					smooth : true,
					symbol : 'circle',
					itemStyle : {
						normal : {
							color : '#0099cb',
							label : {
								show : true
							}
						}
					},
					data : []

				},
				{
					name : '亏损项目',
					type : 'line',
					smooth : true,
					symbol : 'circle',
					itemStyle : {
						normal : {
							color : '#de6e1b',
							label : {
								show : true
							}
						}
					},
					clickable : true,
					data : []

				} ]	
    };
    var profitLossProjectObj = {
    	plProjectChart: initEChart("profitLossProjectChart"),
    	option: profitLossProjectOption,
		init: function(organId){
			var self = this;
			clearEChart(self.plProjectChart);
			self.getRequestDatas(organId);
		},
		getRequestDatas: function(organId){
			var self = this;
			var param = {organId : organId};
			$.ajax({
				url: urls.queryProfitLossProjectUrl,
				data: param,
				type: 'post',
				success: function(data){
					self.getOption(data);
					profitLossAmountObj.init(data);
				},
				error: function(){}
			});
		},
		getOption: function(data){
			var self = this;
			if(data.sumDate != undefined && data.sumDate.length > 1){
				self.option.xAxis[0].data = data.sumDate;
				self.option.series[0].data = data.totalMoreCon;
				self.option.series[1].data = data.totalLessCon;
				self.option.series[2].data = data.sumMoreCon;
				self.option.series[3].data = data.sumLessCon;
				changeXAxisLabelRotate(self.option, data.sumDate);
				self.setOption();
			}else{
				showNoDataEcharts(self.plProjectChart);
			}
		},
		setOption: function(){
			var self = this;
			self.plProjectChart.setOption(self.option);
			self.plProjectChart.on(ecConfig.EVENT.CLICK, self.eConsole);
		},
		eConsole : function(param) {
			if (typeof param.seriesIndex != 'undefined') {
				if(param.value != 0){
					$('#profitLossProjectDetailModal').modal('show');
					$('#profitLossProjectDetailModal').on('shown.bs.modal', function() {
						// 执行一些动作...
						projectNumDetailObj.setTabsSelectFun(param.seriesIndex);
						projectNumDetailObj.getProfitAndLossCountAmount(reqOrgId, param.name);
						projectNumDetailObj.getProfitAndLossProjectDetail(reqOrgId, param.name);
					});
					$('#profitLossProjectDetailModal').on('hidden.bs.modal', function() {
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
    		setTabsSelectFun: function(index){
    			if(index == 2){
    				$("#profitLossProjectDetailModal ul li:eq(0)").addClass('active').siblings().removeClass('active');
    				$('#profitProjectTab').addClass('active');
    				$('#lossProjectTab').removeClass('active');
    			} else if(index == 3){
    				$("#profitLossProjectDetailModal ul li:eq(1)").addClass('active').siblings().removeClass('active');
    				$('#profitProjectTab').removeClass('active');
    				$('#lossProjectTab').addClass('active');
    			}
    		},
    		getProfitAndLossCountAmount: function(organId, data){
    			var self = this;
    			var param = {organId: organId, month: data};
    			$.ajax({
    				url: urls.getProfitAndLossCountAmountUrl,
    				data: param,
    				type: 'post',
    				success: function(data){
    					$("#profitLossProjectDetailYear").html(data.year);
    					$("#profitLossProjectDetailMonth").html(data.month);
    					$("#profitCountAmount").html(self.changeNullToZero(data.map.pNum));
    					$("#lossCountAmount").html(self.changeNullToZero(data.map.lNum));
    				},
    				error: function(){}
    			});
    		},
    		getProfitAndLossProjectDetail: function(organId, data){
    			var self = this;
    			var param = {organId: organId, month: data};
    			$.ajax({
    				url: urls.getProfitAndLossProjectDetailUrl,
    				data: param,
    				type: 'post',
    				success: function(data){
    					$("#profitProjectNum").html(data.pNum);
    					$("#lossProjectNum").html(data.lNum);
    					self.loadGrid(data.pDetail, "profitProjectGrid");
    					self.loadGrid(data.lDetail, "lossProjectGrid");
    				},
    				error: function(){}
    			});
    		},
    		loadGrid: function(data, id){
    			$("#" + id + " tbody").empty();
    			var tr = "";
    			$.each(data,function(index,object){
    				tr += "<tr><td class='tbody-tr-td-first'>"+object.projectName+"</td><td class='tbody-tr-td'>"+object.input+"</td><td class='tbody-tr-td'>"
    					+object.output+"</td><td class='tbody-tr-td'>"+object.gain+"</td><td class='tbody-tr-td'>"+addPlusOrMinusSign(object.sumGain)+"</td></tr>";
    			});
    			$("#" + id + " tbody").append(tr);
    		},
    		changeNullToZero: function(data){
    			if(data != null && data != '' && data != 'null'){
    				return data;
    			}else{
    				return 0;
    			}
    		}
    }
    
    /**
     * 判断数值，前面补充'+'或'-'
     */
    function addPlusOrMinusSign(data){
    	var mes;
    	if(data > 0){
    		mes = "+"+data;
    	} else {
    		mes = data;
    	}
    	return mes;
    }
    
    /**
     * 盈亏金额分析
     */
    var profitLossAmountOption = {
    		legend : {
    			y : 'bottom',
    			data : [ '盈利金额', '亏损金额' ]
    		},
    		grid : {
    			x: 55,
                y: 25,
                x2: 15,
    			borderWidth : 0
    		},
    		xAxis : [ {
    			type : 'category',
    			axisTick : false,
    			splitLine : false,
    			axisLabel : {
    				interval : 0
    			},
    			axisLine : {
    				lineStyle : {
    					color : '#D7D7D7'
    				}
    			},
    			data : []
    		} ],
    		yAxis : [ {
    			type : 'value',
    			splitLine : false,
    			axisLine : false
    		} ],
    		series : [
    		          
    		          {
    		        	  name : '盈利金额',
    		        	  type : 'bar',
    		        	  clickable: false,
    		        	  data : [],
    		        	  itemStyle : {
    		        		  normal : {
    		        			  color : '#0099cb',
    		        			  label : {
    		        				  show : true
    		        			  }
    		        		  }
    		        	  },
    		          },
    		          {
    		        	  name : '亏损金额',
    		        	  type : 'bar',
    		        	  clickable: false,
    		        	  data : [],
    		        	  itemStyle : {
    		        		  normal : {
    		        			  color : '#de6e1b',
    		        			  label : {
    		        				  show : true
    		        			  }
    		        		  }
    		        	  },
    		          },
    		          {
    		        	  name : '盈利金额',
    		        	  type : 'line',
    		        	  smooth : true,
    		        	  symbol : 'circle',
    		        	  itemStyle : {
    		        		  normal : {
    		        			  color : '#0099cb',
    		        			  label : {
    		        				  show : true
    		        			  }
    		        		  }
    		        	  },
    		        	  data : []
    		        	  
    		          },
    		          {
    		        	  name : '亏损金额',
    		        	  type : 'line',
    		        	  smooth : true,
    		        	  symbol : 'circle',
    		        	  itemStyle : {
    		        		  normal : {
    		        			  color : '#de6e1b',
    		        			  label : {
    		        				  show : true
    		        			  }
    		        		  }
    		        	  },
    		        	  clickable : true,
    		        	  data : []
    		        	  
    		          } ]	
    };
    var profitLossAmountObj = {
    		plAmountChart: initEChart("profitLossAmountChart"),
    		option: profitLossAmountOption,
    		init: function(data){
    			var self = this;
    			clearEChart(self.plAmountChart);
    			self.getOption(data);
    		},
    		getOption: function(data){
    			var self = this;
    			if(data.sumDate.length > 1){
    				self.option.xAxis[0].data = data.sumDate;
    				self.option.series[0].data = data.totalMoreGain;
    				self.option.series[1].data = data.totalLessGain;
    				self.option.series[2].data = data.sumMoreGain;
    				self.option.series[3].data = data.sumLessGain;
    				changeXAxisLabelRotate(self.option, data.sumDate);
    				self.setOption();
    			}else{
    				showNoDataEcharts(self.plAmountChart);
    			}
    		},
    		setOption: function(){
    			var self = this;
    			self.plAmountChart.setOption(self.option);
    			self.plAmountChart.on(ecConfig.EVENT.CLICK, self.eConsole);
    		},
    		eConsole : function(param) {
    			if (typeof param.seriesIndex != 'undefined') {
    				if(param.value != 0){
//    					$('#profitLossAmountDetailModal').modal('show');
//    					$('#profitLossAmountDetailModal').on('shown.bs.modal', function() {
    					$('#profitLossProjectDetailModal').modal('show');
    					$('#profitLossProjectDetailModal').on('shown.bs.modal', function() {
    						// 执行一些动作...
    						projectNumDetailObj.setTabsSelectFun(param.seriesIndex);
    						projectNumDetailObj.getProfitAndLossCountAmount(reqOrgId, param.name);
    						projectNumDetailObj.getProfitAndLossProjectDetail(reqOrgId, param.name);
    					});
    					$('#profitLossProjectDetailModal').on('hidden.bs.modal', function() {
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
		grid : {
			x: 55,
            y: 25,
            x2: 15,
			borderWidth : 0
		},
		xAxis : [ {
			type : 'category',
			axisTick : false,
			splitLine : false,
			axisLine : {
				lineStyle : {
					color : '#D7D7D7'
				}
			},
			axisLabel : {
				interval : 0
			},
			data : []
		} ],
		yAxis : [ {
			type : 'value',
			splitLine : false,
			axisLine : {
				lineStyle : {
					color : '#D7D7D7'
				}
			},
			axisTick: {
                show: true,
                lineStyle: {
                    color: '#D7D7D7'
                }
            }
		} ],
		series : [

				{
					type : 'bar',
					clickable: false,
					data : [],
					itemStyle : {
						normal : {
							color : '#0099cb',
							label:{
								show:true
							}
						}
					},
				},
				{
					type : 'line',
					smooth : true,
					symbol : 'circle',
					symbolSize : 0,
					markPoint : {
						clickable: false,
						symbolSize : 16,
						data : [],
						itemStyle : {
							normal : {
								color : '#de6e1b',
							}
						},
					},
					itemStyle : {
						normal : {
							color : '#de6e1b',
							label : {
		        				  show : true
		        			  }
						}
					},
					data : []

				} ]
    }
    var trainCostObj = {
    		trainCostChart: initEChart("trainCostChart"),
    		option: trainCostOption,
    		init: function(organId){
    			var self = this;
    			clearEChart(self.trainCostChart);
    			self.getRequestDatas(organId);
    		},
    		getRequestDatas: function(organId){
    			var self = this;
    			var param = {organId : organId};
    			$.ajax({
    				url: urls.queryProjectInputCostUrl,
    				data: param,
    				type: 'post',
    				success: function(data){
    					self.getOption(data);
    				},
    				error: function(){}
    			});
    		},
    		getOption: function(data){
    			var self = this;
    			if(data.sumDate.length > 1){
    				var len = data.sumInput.length;
    				var markPointData = {
    					value : data.sumInput[len-1],
    					xAxis : len-1,
    					yAxis : data.sumInput[len-1]
    				};
    				var arrPoint = new Array();
    				arrPoint.push(markPointData);
    				self.option.xAxis[0].data = data.sumDate;
    				self.option.series[0].data = data.totalInput;
    				self.option.series[1].data = data.sumInput;
    				self.option.series[1].markPoint.data = arrPoint;
    				changeXAxisLabelRotate(self.option, data.sumDate);
    				self.setOption();
    			}else{
    				showNoDataEcharts(self.trainCostChart);
    			}
    		},
    		setOption: function(){
    			var self = this;
    			self.trainCostChart.setOption(self.option);
    		}
    }
    
    /**
     * 项目投入统计-人力统计
     */
    var projectManpowerOption = {
    		grid : {
    			x: 55,
                y: 25,
                x2: 15,
    			borderWidth : 0
    		},
    		xAxis : [ {
    			type : 'category',
    			axisTick : false,
    			splitLine : false,
    			axisLine : {
    				lineStyle : {
    					color : '#D7D7D7'
    				}
    			},
    			axisLabel : {
    				interval : 0
    			},
    			data : [],
    		} ],
    		yAxis : [ {
    			type : 'value',
    			splitLine : false,
    			axisLine : {
    				lineStyle : {
    					color : '#D7D7D7'
    				}
    			},
    			axisTick: {
                    show: true,
                    lineStyle: {
                        color: '#D7D7D7'
                    }
                }
    		} ],
    		series : [
    		          {
    		        	  type : 'bar',
    		        	  clickable: false,
    		        	  data : [],
    		        	  itemStyle : {
    		        		  normal : {
    		        			  color : '#0099cb',
    		        			  label:{
    		        				  show:true
    		        			  }
    		        		  }
    		        	  },
    		          },
    		          {
    		        	  type : 'line',
    		        	  smooth : true,
		        		  clickable: false,
		        		  symbol : 'circle',
		        		  symbolSize : 0,
    		        	  markPoint : {
    		        		  data : [],
    		        		  symbolSize : 16,
    		        		  itemStyle : {
    		        			  normal : {
    		        				  color : '#de6e1b'
    		        			  }
    		        		  },
    		        	  },
    		        	  itemStyle : {
    		        		  normal : {
    		        			  color : '#de6e1b',
    		        			  label : {
    		        				  show : true
    		        			  }
    		        		  }
    		        	  },
    		        	  data : []
    		        	  
    		          } ]
    }
    var projectManpowerObj = {
    		projectManpowerChart: initEChart("projectManpowerChart"),
    		option: projectManpowerOption,
    		init: function(organId){
    			var self = this;
    			clearEChart(self.projectManpowerChart);
    			self.getRequestDatas(organId);
    		},
    		getRequestDatas: function(organId){
    			var self = this;
    			var param = {organId : organId};
    			$.ajax({
    				url: urls.queryProjectManpowerUrl,
    				data: param,
    				type: 'post',
    				success: function(data){
    					self.getOption(data);
    				},
    				error: function(){}
    			});
    		},
    		getOption: function(data){
    			var self = this;
    			if(data.sumDate.length > 1){
    				var len = data.sumInput.length;
    				var markPointData = {
    					value : data.sumInput[len-1],
    					xAxis : len-1,
    					yAxis : data.sumInput[len-1]
    				};
    				var arrPoint = new Array();
    				arrPoint.push(markPointData);
    				self.option.xAxis[0].data = data.sumDate;
    				self.option.series[0].data = data.totalInput;
    				self.option.series[1].data = data.sumInput;
    				self.option.series[1].markPoint.data = arrPoint;
    				changeXAxisLabelRotate(self.option, data.sumDate);
    				self.setOptionFun();
    			}else{
    				showNoDataEcharts(self.projectManpowerChart);
    			}
    		},
    		setOptionFun: function(){
    			var self = this;
    			self.projectManpowerChart.setOption(self.option);
    		}
    }
    
    /**
     * 项目类型分析
     */
    var projectTypeOption = {
	    series : [
	        {
	            name:'访问来源',
	            type:'pie',
	            radius : '50%',
	            clickable: false,
	            itemStyle : {
					normal : {
						labelLine : {
							lineStyle: {
								length:1,
								color:'#fff'
							}
						}
					}
				},
	            data:[]
	        }
	    ],
		color : pieDefaultColor
    }
    var projectTypeObj = {
    		newLegendId: "projectTypeLegend",
    		projectTypeChart: initEChart("projectTypeChart"),
    		option: projectTypeOption,
    		init: function(organId){
    			var self = this;
    			clearEChart(self.projectTypeChart);
    			self.getRequestDatas(organId);
    		},
    		getRequestDatas: function(organId){
    			var self = this;
    			var param = {organId : organId};
    			$.ajax({
    				url: urls.queryProjectTypeUrl,
    				data: param,
    				type: 'post',
    				success: function(data){
    					self.getOption(data);
    				},
    				error: function(){}
    			});
    		},
    		getOption: function(data){
    			var self = this;
    			if(data.typeNames.length > 0){
    				self.option.series[0].data = data.typeDatas;
    				self.setNewLegend(self.newLegendId,data.typeNames);
    				self.setOption();
    			}else{
    				$(".typePieLegend").remove();
    				showNoDataEcharts(self.projectTypeChart);
    			}
    		},
    		setOption: function(){
    			var self = this;
    			self.projectTypeChart.setOption(self.option);
    		},
    		setNewLegend: function(id, data){
    			$(".typePieLegend").remove();
    			var _div = "";
    			_div += "<div class='typePieLegend'><span>（单位：个）</span></div>";
    			$.each(data,function(ind,obj){
    				_div += "<div class='typePieLegend'><label style='background: "+pieDefaultColor[ind]+"; width: 10px; height: 10px;'> </label>&nbsp;<span>"+data[ind]+"</span></div>";
    			});
    			$("#"+id).append(_div);
    		}
    }
    
    /**
     * 设置x轴标签显示方向
     * @param option echart对象
     * @param xAxisLabel x轴标签
     */
    function changeXAxisLabelRotate(option, xAxisLabel){
    	if(xAxisLabel.length > 6){
			option.xAxis[0].axisLabel.rotate = 45;
		}else{
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
//		data: requestData,
//        datatype: "local",
        altRows: true,//设置表格行的不同底色
        autowidth: true,
        height: tableHeight,
        colNames:['项目名称','负责人','本年人力','本月人力','上月人力','人力变化率','本年投入费用','本月投入费用','上月投入费用','费用变化率','累计利润','本月利润','上月利润','利润变化率','项目开始时间','项目结束时间','项目进度'],
        colModel:[
//            {name:'projectId', index: 'projectId', key: true, hidden: true},
			{name:'projectName', sortable:false,fixed:true, width: 100,align: 'center',editable: false},
			{name:'empName', sortable:false,fixed:true, width: 70,align: 'center',editable: false},
			{name:'manpowerInYear', sortable:false,fixed:true, width: 60,align: 'center',editable: false,
				formatter: function (value, options, row) {
                    if (value == 0) {
                        return "0";
                    }
                    return "<a href='javascript:void(0)' data-key='" + row.projectId + "' class='manpowerInYear_col' value='" + 
                    value + "' key-projectName='" + row.projectName + "' key-startDate='" + row.startDate + "' key-endDate='" + row.endDate + "'>" + value + "</a>";
                }
			},
			{name:'manpowerInMonth', sortable:false,fixed:true, width: 60,align: 'center',editable: false},
			{name:'manpowerLastMonth', sortable:false,fixed:true, width: 60,align: 'center',editable: false},
			{name:'manpowerChangeRate', sortable:false,fixed:true, width: 60,align: 'center',editable: false,
				formatter: function (value, options, row) {
					if(row.manpowerLastMonth==0 || row.manpowerLastMonth==null){
						return '-';
					}
					if(row.manpowerInMonth==0 || row.manpowerInMonth==null){
						return '-100%';
					}
					return (value*100).toFixed(1) + '%';
                }
			},
			{name:'feeYearInput', sortable:false,fixed:true, width: 60,align: 'center',editable: false,
				formatter: function (value, options, row) {
					if (value == 0) {
                        return "0";
                    }
                    return "<a href='javascript:void(0)' data-key='" + row.projectId + "' class='feeYearInput_col' value='" + value + 
                    "' key-projectName='" + row.projectName + "' key-startDate='" + row.startDate + "' key-endDate='" + row.endDate + "'>" + value + "</a>";
                }
			},
			{name:'feeMonthInput', sortable:false,fixed:true, width: 60,align: 'center',editable: false},
			{name:'feeLastMonthInput', sortable:false,fixed:true, width: 60,align: 'center',editable: false},
			{name:'feeChangeRate', sortable:false,fixed:true, width: 60,align: 'center',editable: false,
				formatter: function (value, options, row) {
					if(row.feeLastMonthInput==0 || row.feeLastMonthInput==null){
						return '-';
					}
					if(row.feeMonthInput==0 || row.feeMonthInput==null){
						return '-100%';
					}
					return (value*100).toFixed(1) + '%';
                }
			},
			{name:'gainInYear', sortable:false,fixed:true, width: 60,align: 'center', editable: false},
			{name:'gainInMonth', sortable:false,fixed:true, width: 60,align: 'center', editable: false},
			{name:'gainLastMonth', sortable:false,fixed:true, width: 60,align: 'center', editable: false},
			{name:'gainChangeRate', sortable:false,fixed:true, width: 60,align: 'center', editable: false,
				formatter: function (value, options, row) {
					if(row.gainLastMonth==0 || row.gainLastMonth==null){
						return '-';
					}
					if(row.gainInMonth==0 || row.gainInMonth==null){
						return '-100%';
					}
					return (value*100).toFixed(1) + '%';
                }
			},
			{name:'startDate', sortable:false,fixed:true, width: 80,align: 'center', editable: false,
				formatter: function (value) {
					if(value==null || value==''){
						return '-';
					}
					return value;
                }
			},
			{name:'endDate', sortable:false,fixed:true, width: 80,align: 'center', editable: false,
				formatter: function (value) {
					if(value==null || value==''){
						return '-';
					}
					return value;
                }
			},
			{name:'projectProgress', sortable:false,fixed:true, width: 80,align: 'center', editable: false}
//			{name:'myac',index:'', width:100, fixed:true, sortable:false, resize:false}
		],
//        rownumbers: true,
//        rownumWidth: 40,
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: "#leadingProjectSel",
        loadComplete: function (xhr) {
            setTimeout(function () {
                updatePagerIcons();
            }, 0);
            var $tabs = $('#manpowerTabs');
            var projectId = "";
			$tabs.find('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
				var $this = $(e.target);
				var key = $this.attr('aria-controls');
				var thisTab = $('#'+key);
//				$('#manpowerTabs a[aria-controls="'+key+'"]').tab('show');
				if(thisTab.hasClass("in active"))return;
				$this.parents(".modal-body").find(".tab-pane").removeClass("in active");
				thisTab.addClass("in active");
				if(key == 'tabpanel2'){
					manpowerInputObj.resizeChart();
				}else if(key == 'tabpanel3'){
					departmentInputPieObj.resizeChart();
					jobSeqInputObj.resizeChart();
					rankInputObj.resizeChart();
					workplaceInputObj.resizeChart();
				}else if(key == 'tabpanel4'){
//					$("#subprojectTreegrid").setGridWidth($("#index-son-subprojectgrid-body").width());
					subprojectTreeGridObj.resizeGrid();
				}else{
					currentEmployeeDetailObj.resizeGrid();
				}
			});
			
			var $tabs = $('#feeInputTabs');
			$tabs.find('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
				var $this = $(e.target);
				var key = $this.attr('aria-controls');
				var thisTab = $('#'+key);
				if(thisTab.hasClass("in active"))return;
				$this.parents(".modal-body").find(".tab-pane").removeClass("in active");
				thisTab.addClass("in active");
				
				if(key == 'feeInput2'){
					inputOutputRatioObj.resizeChart();
				}else if(key == 'feeInput3'){
					$("#feeDetailTreegrid").setGridWidth($("#index-son-feedetailgrid-body").width());
				}else{
					inputOutputChartObj.resizeChart();
				}
			});
            $('.manpowerInYear_col').unbind().bind('click',function(){
//            	$('#leadingProjectTabs a[aria-controls="1"]').tab('show');
            	var $this = $(this);
            	projectId = $this.data('key');
            	currentEmployeeDetailObj.init(reqOrgId, projectId);
            	manpowerInputObj.init(reqOrgId, projectId);
            	departmentInputPieObj.init(reqOrgId, projectId);
				jobSeqInputObj.init(reqOrgId, projectId);
				rankInputObj.init(reqOrgId, projectId);
				workplaceInputObj.init(reqOrgId, projectId);
				subprojectTreeGridObj.init(reqOrgId, projectId);
				
				$('#manpowerInYearModal').modal('show');
				
				var beginTime = $this.attr('key-startDate');
				var endTime = $this.attr('key-endDate');
				var projectName = $this.attr('key-projectName');
	    		$('#manpowerBeginDate').html(beginTime.substring(0,4)+'.'+beginTime.substring(5,7));
				if (endTime == "null" || endTime == "") { 
					$('#manpowerEndDate').html(''); 
					$('#manpowerLine').hide(); 
				} else { 
					$('#manpowerLine').show();
					$('#manpowerEndDate').html(endTime.substring(0,4)+'.'+endTime.substring(5,7)); 
				}
				$('#manpowerProjectName').html(projectName);
				$('#manpowerInputNum').html($this.attr('value'));
            });
            $('.feeYearInput_col').unbind().bind('click',function(){
            	var $this = $(this);
            	projectId = $this.data('key');
            	inputOutputChartObj.init(reqOrgId, projectId);
            	inputOutputRatioObj.init(reqOrgId, projectId);
				feeTypeObj.init(reqOrgId, projectId);
            	
            	$('#feeInputInYearModal').modal('show');
            	
            	var beginTime = $this.attr('key-startDate');
				var endTime = $this.attr('key-endDate');
				var projectName = $this.attr('key-projectName');
            	$('#feeBeginDate').html(beginTime.substring(0,4)+'.'+beginTime.substring(5,7));
            	if (endTime == "null" || endTime == "") { 
            		$('#feeEndDate').html('');
            		$('#feeLine').hide();
				} else { 
					$('#feeLine').show();
					$('#feeEndDate').html(endTime.substring(0,4)+'.'+endTime.substring(5,7));
				}
				$('#feeProjectName').html(projectName);
				$('#feeInputNum').html($this.attr('value'));
            });
            $("#leadingProjectTable").find(".ui-jqgrid-bdiv").height(tableHeight + 2);
            $("#leadingProjectGrid").closest(".ui-jqgrid-bdiv").css({ 'overflow-x' : 'scroll' });
        }
    };
    var leadingProjectObj = {
        gridId: '#leadingProjectGrid',
        resultData: null,
        isClicked: false,
        init: function (organId) {
            var self = this;
            if (self.hasInit) {
                self.reloadData(organId, null);
                return;
            }
            self.loadComple();
            leadingProjectOption.postData = {'organId': organId, type: '1'};
            $(self.gridId).jqGrid(leadingProjectOption);
            self.hasInit = true;
        },
        loadComple: function () {
        	var self = this;
            $("#projectSearchBtn").click(function () {
                var searchTxt = $.trim($("#projectSearchTxt").val());
                if (searchTxt != "") {
                	self.isClicked = true;
                	leadingProjectObj.reloadData(reqOrgId, searchTxt);
                }
            });
            $("#projectSearchTxt").keydown(function (e) {
                if (e.keyCode == 13) {
                    $("#projectSearchBtn").click();
                }
            });
            $("#dominantConditionSearch").click(function(){
            	var principal = $.trim($("#principal").val());
            	var manpowerInputSelect = $('#manpowerInputSelect option:selected').attr('value');
            	var startManpowerInput = $.trim($("#startManpowerInput").val());
            	var endManpowerInput = $.trim($("#endManpowerInput").val());
            	var feeInputSelect = $('#feeInputSelect option:selected').attr('value');
            	var startFeeInput = $.trim($("#startFeeInput").val());
            	var endFeeInput = $.trim($("#endFeeInput").val());
            	var projectGainSelect = $('#projectGainSelect option:selected').attr('value');
            	var startProjectGain = $.trim($("#startProjectGain").val());
            	var endProjectGain = $.trim($("#endProjectGain").val());
            	var startProjectTime = $.trim($("#startProjectTime").val());
            	var endProjectTime = $.trim($("#endProjectTime").val());
            	var projectProgressSelect = $('#projectProgressSelect option:selected').attr('value');
            	var flag = dateCompare(startProjectTime, endProjectTime);
            	if(flag == -1 || flag == 0) {
            		alert("开始时间必须小于结束时间！！！");
            		return;
            	}
            	if(isNaN(startManpowerInput) || isNaN(endManpowerInput)){
         		   alert("人力投入请填写数字！！！");
         		   return;
         		}
            	if(parseFloat(startManpowerInput) > parseFloat(endManpowerInput)) {
            		alert("人力投入开始值不能大于结束值！！！");
          		   return;
            	}
             	if(isNaN(startFeeInput) || isNaN(endFeeInput)){
             		alert("费用投入请填写数字！！！");
             		return;
             	}
             	if(parseFloat(startFeeInput) > parseFloat(endFeeInput)) {
            		alert("费用投入开始值不能大于结束值！！！");
          		   return;
            	}
             	if(isNaN(startProjectGain) || isNaN(endProjectGain)){
             		alert("项目利润请填写数字！！！");
             		return;
             	}
             	if(parseFloat(startProjectGain) > parseFloat(endProjectGain)) {
            		alert("项目利润开始值不能大于结束值！！！");
          		   return;
            	}
            	leadingProjectObj.reloadData2(reqOrgId, principal, manpowerInputSelect, startManpowerInput, endManpowerInput, feeInputSelect,
            			startFeeInput, endFeeInput, projectGainSelect, startProjectGain, endProjectGain, startProjectTime, 
            			endProjectTime, projectProgressSelect);
            	$("#moreLabel").removeClass("icon-panel-down icon-panel-up");
            	$("#moreLabel").text('更多筛选条件');
            	$("#moreLabel").addClass("icon-panel-down");
                $("#dominantCondition").hide();
            });
            $("#dominantConditionReset").click(function(){
            	$("#dominantConditionSearchForm").resetForm();
            });
        },
        reloadData: function (organId, keyName) {
            var self = this;
            var _keyName = keyName;// || self.keyName;
            var params = {organId: organId, keyName: _keyName, type: '1'};
            $(self.gridId).clearGridData().setGridParam({
                postData: params
            }).trigger("reloadGrid");
//            $(self.gridId).trigger("reloadGrid");
            self.keyName = _keyName;
            self.resizeGrid();
            self.organId = organId;
            if(!self.isClicked){
            	$('#projectSearchTxt').val("");
                $('#dominantConditionSearchForm').resetForm();
            }
            self.isClicked = false;
        },
        reloadData2: function (organId, principal, manpowerInputSelect, startManpowerInput, endManpowerInput, feeInputSelect,
    			startFeeInput, endFeeInput, projectGainSelect, startProjectGain, endProjectGain, startProjectTime, 
    			endProjectTime, projectProgressSelect) {
            var self = this;

            var params = {organId: organId, principal: principal, manpowerInputSelect: manpowerInputSelect, startManpowerInput: startManpowerInput,
            		endManpowerInput: endManpowerInput, feeInputSelect: feeInputSelect, startFeeInput: startFeeInput, endFeeInput: endFeeInput, 
            		projectGainSelect: projectGainSelect, startProjectGain: startProjectGain, endProjectGain: endProjectGain, 
            		startDate: startProjectTime, endDate: endProjectTime, projectProgress: projectProgressSelect, type: '2'};
            $(self.gridId).clearGridData().setGridParam({
                postData: params
            }).trigger("reloadGrid");
            self.resizeGrid();
            self.organId = organId;
            
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#leadingProjectTable').width() * 0.98);
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
        colNames:['项目名称','人力总投入','本月人力','上月人力','人力变化率','管理序列','职能序列','专业序列','业务序列','项目开始时间','项目结束时间','项目进度'],
        colModel:[
			{name:'projectName', index: 'projectName', sortable:false,fixed:true, width: 150,align: 'center',editable: false},
			{name:'manpowerInYear',index:'manpowerInYear', sortable:false,fixed:true, width: 100,align: 'center',editable: false,
				formatter: function (value, options, row) {
					if(value==0){
						return '0';
					}
                    return "<a href='javascript:void(0)' data-key='" + row.projectId + "' class='manpower1_col' value='" + value + 
                    "' key-projectName='" + row.projectName + "' key-startDate='" + row.startDate + "' key-endDate='" + row.endDate + "'>" + value + "</a>";
                }
			},
			{name:'manpowerInMonth',index:'manpowerInMonth', sortable:false,fixed:true, width: 80,align: 'center',editable: false},
			{name:'manpowerLastMonth',index:'manpowerLastMonth', sortable:false,fixed:true, width: 100,align: 'center',editable: false},
			{name:'manpowerChangeRate',index:'manpowerChangeRate', sortable:false,fixed:true, width: 80,align: 'center',editable: false,
				formatter: function (value, options, row) {
					if(row.manpowerLastMonth==0 || row.manpowerLastMonth==null){
						return '-';
					}
					if(row.manpowerInMonth==0 || row.manpowerInMonth==null){
						return '-100%';
					}
					return (value*100).toFixed(1) + '%';
                }
			},
			{name:'manageSeries',index:'manageSeries', sortable:false,fixed:true, width: 80,align: 'center',editable: false},
			{name:'functionalSeries',index:'functionalSeries', sortable:false,fixed:true, width: 80,align: 'center',editable: false},
			{name:'professionalSeries',index:'professionalSeries', sortable:false,fixed:true, width: 80,align: 'center',editable: false},
			{name:'businessSeries',index:'businessSeries', sortable:false,fixed:true, width: 80,align: 'center',editable: false},
			{name:'startDate',index:'satartDate', sortable:false,fixed:true, width: 108,align: 'center', editable: false,
				formatter: function (value) {
					if(value==null || value==''){
						return '-';
					}
					return value;
                }
			},
			{name:'endDate',index:'endDate', sortable:false,fixed:true, width: 108,align: 'center', editable: false,
				formatter: function (value) {
					if(value==null || value==''){
						return '-';
					}
					return value;
                }
			},
			{name:'projectProgress',index:'projectProgress', sortable:false,fixed:true, width: 80,align: 'center', editable: false}
		],
//        rownumbers: true,
//        rownumWidth: 40,
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: "#participateDetailSel",
        loadComplete: function (xhr) {
            setTimeout(function () {
                updatePagerIcons();
            }, 0);
            var $tabs = $('#partManpoerInputTabs');
            var projectId = "";
			$tabs.find('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
				var $this = $(e.target);
				var key = $this.attr('aria-controls');
				var thisTab = $('#'+key);
				if(thisTab.hasClass("in active"))return;
				$this.parents(".modal-body").find(".tab-pane").removeClass("in active");
				thisTab.addClass("in active");
				if(key == 'part2'){
					partManpowerInputObj.resizeChart();
				}else if(key == 'part3'){
					partDeprtmentInputPieObj.resizeChart();
					partJobSeqInputObj.resizeChart();
					partRankInputObj.resizeChart();
					partWorkplaceInputObj.resizeChart();
				}else{
					currentEmployeeObj.resizeGrid();
				}
			});
            $('.manpower1_col').unbind().bind('click',function(){
            	var $this = $(this);
            	projectId = $this.data('key');
            	currentEmployeeObj.init(reqOrgId, projectId);
            	partManpowerInputObj.init(reqOrgId, projectId);
            	partDeprtmentInputPieObj.init(reqOrgId, projectId);
				partJobSeqInputObj.init(reqOrgId, projectId);
				partRankInputObj.init(reqOrgId, projectId);
				partWorkplaceInputObj.init(reqOrgId, projectId);
				currentEmployeeObj.init(reqOrgId, projectId);
            	
            	$('#manpowerPartInYearModal').modal('show');
            	
            	var beginTime = $this.attr('key-startDate');
            	var endTime = $this.attr('key-endDate');
            	var projectName = $this.attr('key-projectName');
	            $('#partManpowerBeginDate').html(beginTime.substring(0,4)+'.'+beginTime.substring(5,7));
	            if (endTime == "null" || endTime == "") { 
	            	$('#partManpowerEndDate').html('');
	            	$('#partManpowerLine').hide();
				} else { 
					$('#partManpowerLine').show();
					$('#partManpowerEndDate').html(endTime.substring(0,4)+'.'+endTime.substring(5,7));
				}
				$('#partManpowerProjectName').html(projectName);
				$('#partManpowerInputNum').html($this.attr('value'));
            });
            $("#participateDeailTable").find(".ui-jqgrid-bdiv").height(tableHeight + 2);
            $("#participateDetailGrid").closest(".ui-jqgrid-bdiv").css({ 'overflow-x' : 'scroll' });
        }
    };
    var participateDetailObj = {
        gridId: '#participateDetailGrid',
        resultData: null,
        isClicked: false,
        init: function (organId) {
            var self = this;
            if (self.hasInit) {
                self.reloadData(organId, null);
                return;
            }
            self.loadComple();
            participateGridOption.postData = {organId: organId, type: 1};
            $(self.gridId).jqGrid(participateGridOption);
            self.hasInit = true;
        },
        loadComple: function () {
        	var self = this;
            $("#participateSearchBtn").click(function () {
                var searchTxt = $.trim($("#participateSearchTxt").val());
                if (searchTxt != "") {
                	self.isClicked = true;
                	participateDetailObj.reloadData(reqOrgId, searchTxt);
                }
            });
            $("#participateSearchTxt").keydown(function (e) {
                if (e.keyCode == 13) {
                    $("#participateSearchBtn").click();
                }
            });
            $("#participateConditionSearch").click(function () {
            	var participateManpowerInputSelect = $('#participateManpowerInputSelect option:selected').attr('value');
            	var participateStartInput = $.trim($("#participateStartInput").val());
            	var participateEndInput = $.trim($("#participateEndInput").val());
            	var participateProjectProgressSelect = $('#participateProjectProgressSelect option:selected').attr('value');
            	var participateStartTime = $.trim($("#participateStartTime").val());
            	var participateEndTime = $.trim($("#participateEndTime").val());
            	var flag = dateCompare(participateStartTime, participateEndTime);
            	if(flag == -1 || flag == 0) {
            		alert("开始时间必须小于结束时间！！！");
            		return;
            	}
            	if(isNaN(participateStartInput) || isNaN(participateEndInput)){
         		   alert("人力投入请填写数字！！！");
         		   return;
         		}
            	if(parseFloat(participateStartInput) > parseFloat(participateEndInput)) {
            		alert("人力投入开始值不能大于结束值！！！");
          		   return;
            	}
            	participateDetailObj.reloadData2(reqOrgId, participateManpowerInputSelect, participateStartInput, participateEndInput, 
            			participateProjectProgressSelect, participateStartTime, participateEndTime);
            	$("#participateMoreLabel").removeClass("icon-panel-down icon-panel-up");
            	$("#participateMoreLabel").text('更多筛选条件');
            	$("#participateMoreLabel").addClass("icon-panel-down");
                $("#participateCondition").hide();
            });
            $("#participateConditionReset").click(function () {
            	$("#participateConditionSearchForm").resetForm();
            });
        },
        reloadData: function (organId, keyName) {
            var self = this;
            var _keyName = keyName;// || self.keyName;
            var params = {organId: organId, keyName: _keyName, type: 1};
            $(self.gridId).clearGridData().setGridParam({
                postData: params
            }).trigger("reloadGrid");
//            $(self.gridId).trigger("reloadGrid");
            self.keyName = _keyName;
            self.resizeGrid();
            self.organId = organId;
            if(!self.isClicked) {
            	$('#participateSearchTxt').val("");
                $('#participateConditionSearchForm').resetForm();
            }
            self.isClicked = false;
        },
        reloadData2: function (organId, participateManpowerInputSelect, participateStartInput, participateEndInput, 
    			participateProjectProgressSelect, participateStartTime, participateEndTime) {
            var self = this;
            var startManpowerInYear, endManpowerInYear, startManpowerInMonth, endtManpowerInMonth, startManpowerLastMonth, endManpowerLastMonth;
            var params = {organId: organId, participateManpowerInputSelect: participateManpowerInputSelect, 
            		participateStartInput: participateStartInput, participateEndInput: participateEndInput, startDate: participateStartTime, 
            		endDate: participateEndTime, projectProgress: participateProjectProgressSelect, type: 2};
            $(self.gridId).clearGridData().setGridParam({
                postData: params
            }).trigger("reloadGrid");
            self.resizeGrid();
            self.organId = organId;
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#participateDeailTable').width() * 0.98);
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
        colNames:['姓名','所属组织','本年参与项目数量','当前参与项目数量','是否担任过项目负责人'],
        colModel:[
			{name:'empName',index:'empName', sortable:false, width: 180,align: 'center',editable: false,
				formatter: function (value, options, row) {
                    return "<a href='javascript:void(0)' data-key='" + row.empId + "' class='manpower_col' >" + value + "</a>";
                }
			},
			{name:'organName',index:'organName', sortable:false, width: 220,align: 'center',editable: false},
			{name:'projectNumInYear',index:'projectNumInYear', sortable:false, width: 240,align: 'center',editable: false},
			{name:'projectNum',index:'projectNum', sortable:false, width: 240,align: 'center',editable: false},
			{name:'isPrincipal',index:'isPrincipal', sortable:false, width: 240,align: 'center',editable: false,
				formatter: function (value, options, row) {
                    if (row.isPrincipal>0) {
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
        loadComplete: function (xhr) {
            setTimeout(function () {
                updatePagerIcons();
            }, 0);
            var empId = "";
            $('.manpower_col').unbind().bind('click',function(){
            	var $this = $(this);
            	empId = $this.data('key');
            	participatejProjectDetailObj.init(reqOrgId, empId);
            	participatejProjectDetailObj.resizeGrid();
            	$('#participateProjectModal').modal('show');
            });
            $("#employeeDetailTable").find(".ui-jqgrid-bdiv").height(tableHeight + 2);
        }
    };
    var employeeDetailObj = {
        gridId: '#employeeDetailGrid',
        resultData: null,
        isClicked: false,
        init: function (organId) {
            var self = this;
            if (self.hasInit) {
                self.reloadData(organId, null);
                return;
            }
            self.loadComple();
            employeeGridOption.postData={organId: organId, type: 1};
            $(self.gridId).jqGrid(employeeGridOption);
            self.hasInit = true;
        },
        loadComple: function () {
        	var self = this;
            $("#employeeSearchBtn").click(function () {
                var searchTxt = $.trim($("#employeeSearchTxt").val());
                if (searchTxt != "") {
                	self.isClicked = true;
                	employeeDetailObj.reloadData(reqOrgId, searchTxt);
                }
            });
            $("#employeeSearchTxt").keydown(function (e) {
                if (e.keyCode == 13) {
                    $("#employeeSearchBtn").click();
                }
            });
            $("#employeeConditionSearch").click(function () {
            	var startProjectNumInYear = $.trim($("#startProjectNumInYear").val());
            	var endProjectNumInYear = $.trim($("#endProjectNumInYear").val());
            	var startProjectNum = $.trim($("#startProjectNum").val());
            	var endProjectNum = $.trim($("#endProjectNum").val());
            	if(isNaN(startProjectNumInYear) || isNaN(endProjectNumInYear)){
        		   alert("累计参与项目数请填写数字！！！");
        		   return;
        		}
            	if(parseFloat(startProjectNumInYear) > parseFloat(endProjectNumInYear)) {
            		alert("累计参与项目数开始值不能大于结束值！！！");
         		   return;
            	}
            	if(isNaN(startProjectNum) || isNaN(endProjectNum)){
            		alert("当前参与项目数请填写数字！！！");
            		return;
            	}
            	if(parseFloat(startProjectNum) > parseFloat(endProjectNum)) {
            		alert("当前参与项目数开始值不能大于结束值！！！");
         		   return;
            	}
            	var principal = $("#isPrincipal1").parent().find(".condition-btn-selected").attr("value");
            	employeeDetailObj.reloadData2(reqOrgId, startProjectNumInYear, endProjectNumInYear, startProjectNum, 
            			endProjectNum, principal);
            	$('#employeeMoreLabel').removeClass("icon-panel-down icon-panel-up");
            	$('#employeeMoreLabel').text('更多筛选条件');
            	$('#employeeMoreLabel').addClass("icon-panel-down");
        		$("#employeeCondition").hide();
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
        reloadData: function (organId, keyName) {
            var self = this;
            var _keyName = keyName;// || self.keyName;
            var params = {organId: organId, keyName: _keyName, type: 1};
            $(self.gridId).clearGridData().setGridParam({
                postData: params
            }).trigger("reloadGrid");
//            $(self.gridId).trigger("reloadGrid");
            self.keyName = _keyName;
            self.organId = organId;
            self.resizeGrid();
            if(!self.isClicked){
            	$('#employeeSearchTxt').val("");
                $('#employeeConditionSearchForm').resetForm();
            }
            self.isClicked = false;
        },
        reloadData2: function (organId, startProjectNumInYear, endProjectNumInYear, startProjectNum, 
    			endProjectNum, principal) {
            var self = this;
            var params = {organId: organId, startProjectNumInYear: startProjectNumInYear, endProjectNumInYear: endProjectNumInYear, 
            		startProjectNum: startProjectNum, endProjectNum: endProjectNum, principal: principal, type: 2};
            $(self.gridId).clearGridData().setGridParam({
                postData: params
            }).trigger("reloadGrid");
            self.organId = organId;
            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#employeeDetailTable').width() * 0.98);
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
        autowidth: false,
        height: 300,
        colNames:['姓名','所属组织','子项目','人力投入','工作内容'],
        colModel:[
			{name:'empName',sortable:false, width: 135,align: 'center',editable: false},
			{name:'projectName',sortable:false, width: 200,align: 'center',editable: false},
			{name:'subProjectName',sortable:false, width: 200,align: 'center',editable: false},
			{name:'manpowerInput',sortable:false, width: 100,align: 'center',editable: false},
			{name:'workContent',sortable:false, width: 200,align: 'center',editable: false},
		],
        viewrecords: true,
        scroll: true,
        loadComplete: function (xhr) {
            setTimeout(function () {
                updatePagerIcons();
            }, 0);
            $("#currentEmployeeDeailTable").find(".ui-jqgrid-bdiv").height(300 + 2);
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
//            $(self.gridId).trigger("reloadGrid");
            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#currentEmployeeDeailTable').width() * 0.98);
        }
    };
    var currentEmployeeGridOption = {
            url: urls.getCurrentEmployeeListUrl,
            datatype: "json",
            postData: {},
            mtype: 'POST',
            altRows: true,//设置表格行的不同底色
            autowidth: false,
            height: 300,
            colNames:['姓名','所属组织','子项目','人力投入','工作内容'],
            colModel:[
    			{name:'empName',sortable:false,fixed:true, width: 135,align: 'center',editable: false},
    			{name:'projectName',sortable:false,fixed:true, width: 200,align: 'center',editable: false},
    			{name:'subProjectName',sortable:false,fixed:true, width: 200,align: 'center',editable: false},
    			{name:'manpowerInput',sortable:false,fixed:true, width: 100,align: 'center',editable: false},
    			{name:'workContent',sortable:false,fixed:true, width: 200,align: 'center',editable: false},
    		],
            viewrecords: true,
            scroll: true,
            loadComplete: function (xhr) {
                setTimeout(function () {
                    updatePagerIcons();
                }, 0);
                $("#currentEmployeeTable").find(".ui-jqgrid-bdiv").height(300 + 2);
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
            $(self.gridId).jqGrid(currentEmployeeGridOption);
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
            $(self.gridId).setGridWidth($('#currentEmployeeTable').width() * 0.98);
        }
    };
    
	//人力投入环比趋势
    var manpowerInputOption = {
        grid: {
            x: 55,
            x2: 15,
            borderWidth: 0
        },
        xAxis: [
            {
                type: "category",
                splitLine: {show: false},
                axisTick: {
                    lineStyle: {
                        color: '#cecece'
                    }
                },
                data: [],
                axisLine: {
                    lineStyle: {
                        color: '#cecece',
                        width: 1
                    }
                },
                axisLabel: {
                    itemStyle: {
                        color: '#000000'
                    }, textStyle: {
                        color: '#000000',
                        fontSize: 12
                    }
                }
            }
        ],
        yAxis: [
            {
                type: "value",
                nameTextStyle: {
					color: '#000000',
					margin: '100px'
				},
				axisLine: {
					lineStyle: {
						color: '#cecece',
						width: 1
					}
				},
                splitLine: false
            }
        ],
        calculable: false,
        series: [
            {
                type: "line",
                smooth:false,  //曲线true 折线false
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            }
                        }
                    },
                    emphasis: {
                        label: false
                    }
                },
                clickable: false,
//                symbolSize: 0,
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
                type:"get",
                data: {"organId": organId, "projectId": projectId},
                dataType:"json",
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
                var month =  (o.yearMonth + "").substring(0,4) + "/" + (o.yearMonth + "").substring(4,6);
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
                type:"get",
                data: {"organId": organId, "projectId": projectId},
                dataType:"json",
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
                var month =  (o.yearMonth + "").substring(0,4) + "/" + (o.yearMonth + "").substring(4,6);
                xAxisData.push(month);
            });
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
    var colorPie=["#0b98e0","#00bda9","#4573a7","#92c3d4","#de6e1b","#ff0084","#af00e1","#8d55f6","#6a5888","#2340f3"];
    var optionPie={
        tooltip: {
            show: false
        },
        toolbox: {
            show: false
        },
        calculable: false,
        series: [
            {
                clickable:false,
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
        color: colorPie
    };
    
    /*
     * 各部门人力投入
     * */
    var departmentInputPieObj={
        chart: echarts.init(document.getElementById('departmentInputChart')),
        option: optionPie,
        init: function(organId, projectId){
        	var self = this;
        	if(self.organId == organId && self.projectId == projectId) {
        		return;
        	}
        	self.getRequestDatas(organId, projectId);
        	self.organId = organId;
        	self.projectId = projectId;
        },
        getRequestDatas: function(organId, projectId){
			var self = this;
			var param = {organId: organId, projectId: projectId};
			$.ajax({
				url: urls.getDepartmentInputUrl,
				data: param,
				type: 'post',
				success: function(data){
					self.getOption(data);
				},
				error: function(){}
			});
		},
		getOption: function(data){
			if(data==undefined){
	            console.info("各部门人力投入出错了！");
	        }else {
	            var self = this;
	            var lis = [], legend = [];
	            var i = 0;
	            $.each(data, function(){
	                $.each(this, function (n, v) {
	                    if(v!=0) {
	                        lis.push({"name": n, "value": v});
	                        legend.push('<div><label style="background-color: ' + colorPie[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
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
    var partDeprtmentInputPieObj={
        chart: echarts.init(document.getElementById('partDeprtmentInputChart')),
        option: optionPie,
        init: function(organId, projectId){
        	var self = this;
        	if(self.organId == organId && self.projectId == projectId) {
        		return;
        	}
        	self.getRequestDatas(organId, projectId);
        	self.organId = organId;
        	self.projectId = projectId;
        },
        getRequestDatas: function(organId, projectId){
			var self = this;
			var param = {organId: organId, projectId: projectId};
			$.ajax({
				url: urls.getDepartmentInputUrl,
				data: param,
				type: 'post',
				success: function(data){
					self.getOption(data);
				},
				error: function(){}
			});
		},
		getOption: function(data){
			if(data==undefined){
	            console.info("各部门人力投入出错了！");
	        }else {
	            var self = this;
	            var lis = [], legend = [];
	            var i = 0;
	            $.each(data, function(){
	                $.each(this, function (n, v) {
	                    if(v!=0) {
	                        lis.push({"name": n, "value": v});
	                        legend.push('<div><label style="background-color: ' + colorPie[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
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
    var jobSeqInputObj={
        chart: echarts.init(document.getElementById('jobSeqInputChart')),
        option: optionPie,
        init: function(organId, projectId){
        	var self = this;
        	if(self.organId == organId && self.projectId == projectId) {
        		return;
        	}
        	self.getRequestDatas(organId, projectId);
        	self.organId = organId;
        	self.projectId = projectId;
        },
        getRequestDatas: function(organId, projectId){
			var self = this;
			var param = {organId: organId, projectId: projectId};
			$.ajax({
				url: urls.getJobSeqInputUrl,
				data: param,
				type: 'post',
				success: function(data){
					self.getOption(data);
				},
				error: function(){}
			});
		},
		getOption: function(data){
			if(data==undefined){
	            console.info("职位序列人力投入出错了！");
	        }else {
	            var self = this;
	            var lis = [], legend = [];
	            var i = 0;
	            $.each(data, function(){
	                $.each(this, function (n, v) {
	                    if(v!=0) {
	                        lis.push({"name": n, "value": v});
	                        legend.push('<div><label style="background-color: ' + colorPie[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
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
    var partJobSeqInputObj={
        chart: echarts.init(document.getElementById('partJobSeqInputChart')),
        option: optionPie,
        init: function(organId, projectId){
        	var self = this;
        	if(self.organId == organId && self.projectId == projectId) {
        		return;
        	}
        	self.getRequestDatas(organId, projectId);
        	self.organId = organId;
        	self.projectId = projectId;
        },
        getRequestDatas: function(organId, projectId){
			var self = this;
			var param = {organId: organId, projectId: projectId};
			$.ajax({
				url: urls.getJobSeqInputUrl,
				data: param,
				type: 'post',
				success: function(data){
					self.getOption(data);
				},
				error: function(){}
			});
		},
		getOption: function(data){
			if(data==undefined){
	            console.info("职位序列人力投入出错了！");
	        }else {
	            var self = this;
	            var lis = [], legend = [];
	            var i = 0;
	            $.each(data, function(){
	                $.each(this, function (n, v) {
	                    if(v!=0) {
	                        lis.push({"name": n, "value": v});
	                        legend.push('<div><label style="background-color: ' + colorPie[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
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
	var rankInputObj={
        chart: echarts.init(document.getElementById('rankInputChart')),
        option: optionPie,
        init: function(organId, projectId){
        	var self = this;
        	if(self.organId == organId && self.projectId == projectId) {
        		return;
        	}
        	self.getRequestDatas(organId, projectId);
        	self.organId = organId;
        	self.projectId = projectId;
        },
        getRequestDatas: function(organId, projectId){
			var self = this;
			var param = {organId: organId, projectId: projectId};
			$.ajax({
				url: urls.getRankInputUrl,
				data: param,
				type: 'post',
				success: function(data){
					self.getOption(data);
				},
				error: function(){}
			});
		},
		getOption: function(data){
			if(data==undefined){
	            console.info("职位序列人力投入出错了！");
	        }else {
	            var self = this;
	            var lis = [], legend = [];
	            var i = 0;
	            $.each(data, function(){
	                $.each(this, function (n, v) {
	                    if(v!=0) {
	                        lis.push({"name": n, "value": v});
	                        legend.push('<div><label style="background-color: ' + colorPie[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
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
	var partRankInputObj={
        chart: echarts.init(document.getElementById('partRankInputChart')),
        option: optionPie,
        init: function(organId, projectId){
        	var self = this;
        	if(self.organId == organId && self.projectId == projectId) {
        		return;
        	}
        	self.getRequestDatas(organId, projectId);
        	self.organId = organId;
        	self.projectId = projectId;
        },
        getRequestDatas: function(organId, projectId){
			var self = this;
			var param = {organId: organId, projectId: projectId};
			$.ajax({
				url: urls.getRankInputUrl,
				data: param,
				type: 'post',
				success: function(data){
					self.getOption(data);
				},
				error: function(){}
			});
		},
		getOption: function(data){
			if(data==undefined){
	            console.info("职位序列人力投入出错了！");
	        }else {
	            var self = this;
	            var lis = [], legend = [];
	            var i = 0;
	            $.each(data, function(){
	                $.each(this, function (n, v) {
	                    if(v!=0) {
	                        lis.push({"name": n, "value": v});
	                        legend.push('<div><label style="background-color: ' + colorPie[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
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
    var workplaceInputObj={
        chart: echarts.init(document.getElementById('workplaceInputChart')),
        option: optionPie,
        init: function(organId, projectId){
        	var self = this;
        	if(self.organId == organId && self.projectId == projectId) {
        		return;
        	}
        	self.getRequestDatas(organId, projectId);
        	self.organId = organId;
        	self.projectId = projectId;
        },
        getRequestDatas: function(organId, projectId){
			var self = this;
			var param = {organId: organId, projectId: projectId};
			$.ajax({
				url: urls.getWorkplaceInputUrl,
				data: param,
				type: 'post',
				success: function(data){
					self.getOption(data);
				},
				error: function(){}
			});
		},
		getOption: function(data){
			if(data==undefined){
	            console.info("职位序列人力投入出错了！");
	        }else {
	            var self = this;
	            var lis = [], legend = [];
	            var i = 0;
	            $.each(data, function(){
	                $.each(this, function (n, v) {
	                    if(v!=0) {
	                        lis.push({"name": n, "value": v});
	                        legend.push('<div><label style="background-color: ' + colorPie[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
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
    var partWorkplaceInputObj={
        chart: echarts.init(document.getElementById('partWorkspaceInputChart')),
        option: optionPie,
        init: function(organId, projectId){
        	var self = this;
        	if(self.organId == organId && self.projectId == projectId) {
        		return;
        	}
        	self.getRequestDatas(organId, projectId);
        	self.organId = organId;
        	self.projectId = projectId;
        },
        getRequestDatas: function(organId, projectId){
			var self = this;
			var param = {organId: organId, projectId: projectId};
			$.ajax({
				url: urls.getWorkplaceInputUrl,
				data: param,
				type: 'post',
				success: function(data){
					self.getOption(data);
				},
				error: function(){}
			});
		},
		getOption: function(data){
			if(data==undefined){
	            console.info("职位序列人力投入出错了！");
	        }else {
	            var self = this;
	            var lis = [], legend = [];
	            var i = 0;
	            $.each(data, function(){
	                $.each(this, function (n, v) {
	                    if(v!=0) {
	                        lis.push({"name": n, "value": v});
	                        legend.push('<div><label style="background-color: ' + colorPie[i] + ';"></label><span>' + n + ' ' + v + '人</span></div>');
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
    
    /*
	子项目明细
    */
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
           autoWidth:true,
           ExpandColClick: true, //当为true时，点击展开行的文本时，treeGrid就能展开或者收缩，不仅仅是点击图片
           ExpandColumn: 'projectName',//树状结构的字段
           colNames: ['projectId', '项目名称', '负责人', '本期投入', '上期投入', '管理序列', '职能序列', '专业序列', '业务序列'],
           colModel: [
               {name: 'id', key: true, hidden: true},
               {name: 'projectName', width: 200, sortable: false, align: 'left'},
               {name: 'empName', width: 100, sortable: false, align: 'center'},
               {name: 'manpowerInMonth', width: 80, sortable: false, align: 'center'},
               {name: 'manpowerLastMonth', width: 80, sortable: false, align: 'center'},
               {name: 'manageSeries', width: 80, sortable: false, align: 'center'},
               {name: 'functionSeries', width: 80, sortable: false, align: 'center'},
               {name: 'professionalSeries', width: 80, sortable: false, align: 'center'},
               {name: 'businessSeries', width: 80, sortable: false, align: 'center'}
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
           if(self.organId == organId && self.projectId == projectId) {
        	   return;
           }
           self.organId = organId;
           self.projectId = projectId;
           self.initTreeGrid(organId, projectId, self.treeGridId, self.options);
       }, 
       initTreeGrid: function (organId, projectId, gridId, options) {
    	   var self = this;
           if (self.first) {
        	   options.postData={organId: organId, projectId: projectId};
               jQuery(gridId).jqGrid(options);
               self.resizeGrid();
               self.first = false;
           }else {
               jQuery(gridId).clearGridData().setGridParam({
                   postData: {organId: organId, projectId: projectId}
               }).trigger("reloadGrid");
           }
//           self.resizeGrid();
       },
       resizeGrid: function () {
           var self = this;
           $(self.treeGridId).setGridWidth($('#subprojectTreegridbody').width());
       }
   };
    
	//投入产出费用
	var inputOutputOption = {	
		legend : {
			data : [ '投入费用', '产出' ],
			y : 'bottom',
			selectedMode : false
		},
		calculable : false,
		grid : {
			borderWidth : 0
		},
		color : [ '#23C6C8', '#EA711E' ],
		xAxis : [ {
			type : 'category',
			splitLine : false,
			axisLine : {
				show : true,
				onZero : false,
				lineStyle : {
					color : '#D9D9D9'
				}
			},
			axisTick : {
				show : true,
				lineStyle : {
					color : '#D9D9D9'
				}
			},
			axisLabel : {
				show : true,
				itemStyle : {
					color : '#BEBEBE'
				}
			},
			data : []
		} ],
		yAxis : [ {
			type : 'value',
			splitLine : false,
			axisLine : false,
			axisTick : false,
			axisLabel : {
				show : true
			}
		}, {
			type : 'value',
			splitLine : false,
			axisLine : false,
			axisLabel : false
		} ],
		series : [ {
			name : '投入年累计',
			type : 'bar',
			clickable : false,
			barMaxWidth: 30,
			itemStyle : {
				normal : {
					color : '#0099cb',
					type : 'dotted',
					label : {
						show : true
					}
				}
			},
			data : []
		},{
			name : '产出年累计',
			type : 'bar',
			clickable : false,
			barMaxWidth: 30,
			itemStyle : {
				normal : {
					color : '#de6e1b',
					type : 'dotted',
					label : {
						show : true
					}
				}
			},
			data : []
		}, {
			name : '投入费用',
			type : 'line',
			clickable : false,
			symbol : 'circle',
			itemStyle : {
				normal : {
					color : '#0099cb',
					type : 'dotted',
					label : {
						show : true
					}
				}
			},
			data : []
		},{
			name : '产出',
			type : 'line',
			clickable : false,
			symbol : 'circle',
			itemStyle : {
				normal : {
					color : '#de6e1b',
					type : 'dotted',
					label : {
						show : true
					}
				}
			},
			data : []
		} ]
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
            yearInputData.push(result.dto.inputFeeInYear);
    		var yearOutputData = [];
    		yearOutputData.push(result.dto.outputFeeInYear);
    		var monthInputData = [];
    		var monthOutputData = [];
    		$.each(result.list, function (i, o) {
//    			if(i > 0){
    				monthInputData.push(o.inputFee);
        			monthOutputData.push(o.outputFee);
                    var month = (o.yearMonth + "").substring(2, 4)+ "/" +(o.yearMonth + "").substring(4, 6);
                    xAxisData.push(month);
//    			}
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
            x: 55,
            x2: 15,
            borderWidth: 0
        },
        xAxis: [
            {
                type: "category",
                splitLine: {show: false},
                axisTick: {
                    lineStyle: {
                        color: '#cecece'
                    }
                },
                data: [],
                axisLine: {
                    lineStyle: {
                        color: '#cecece',
                        width: 1
                    }
                },
                axisLabel: {
                    itemStyle: {
                        color: '#000000'
                    }, textStyle: {
                        color: '#000000',
                        fontSize: 12
                    }
                }
            }
        ],
        yAxis: [
            {
                type: "value",
                nameTextStyle: {
					color: '#000000',
					margin: '100px'
				},
				axisLine: {
					lineStyle: {
						color: '#cecece',
						width: 1
					}
				},
				axisLabel : {
					formatter: '{value} %'
				},
                splitLine: false
            }
        ],
        calculable: false,
        series: [
            {
                type: "line",
                smooth:true,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            },
			            	formatter : function(params) {
								return params.value.toFixed(1) + '%';
							}
                        }
                    },
                    emphasis: {
                        label: false
                    }
                },
                clickable: false,
//                symbolSize: 0,
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
            if(projectId == null || projectId == "") {
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
//            	if(i > 0){
            		dataValue.push(o.inputOutputRatio*100);
                    var month = (o.yearMonth + "").substring(2, 4)+ "/" +(o.yearMonth + "").substring(4, 6);
                    xAxisData.push(month);
//            	}
            });
            xAxisData.push('当年累计');
    		dataValue.push((result.dto.outputFeeInYear/result.dto.inputFeeInYear)*100);
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
    
    var feeTypeObj = {
    	organId: null,
    	projectId: null,
    	init: function(organId, projectId) {
    		var self = this;
    		if(self.organId == organId && self.projectId == projectId) {
    			return;
    		}
    		self.organId = organId;
    		self.projectId = projectId;
    		self.getRequestData();
    	},
    	getRequestData: function() {
    		var self = this;
			$.ajax({
				url : urls.queryProjectFeeTypeUrl,
    			type : 'get',
    			success : function(data){
    				self.loadGridFun(data);
    			},
    			error : function(){}
			});
		},
		loadGridFun: function(data) {
			var self = this;
    		var modelArray = data.listModel;
    		for(var i = 2;i < modelArray.length; i++){
    			modelArray[i].formatter = toFormatter;
    		}
    		projectFeeDetailGridObj.colModel = modelArray;
    		projectFeeDetailGridObj.colName = data.listName;
    		projectFeeDetailGridObj.init(self.organId, self.projectId);
		}
    };
    
    var projectFeeDetailGridObj = {
    	treeGridId: '#feeDetailTreegrid',
    	first: true,
    	colName: null,
    	colModel: null,
    	organId: null,
    	projectId: null,
    	init: function (organId, projectId) {
            var self = this;
            if(self.organId == organId && self.projectId == projectId) {
         	   return;
            }
            self.organId = organId;
            self.projectId = projectId;
            self.initTreeGridFun(organId, projectId);
        },
    	initTreeGridFun: function(organId, projectId){
    		var self = this;
    		var param = {
    			organId : organId,
    			projectId : projectId
    		};
    		if(!self.first){
    			self.reloadGridFun(param);
    		};
    		$(self.treeGridId).jqGrid({
    			treeGrid: true,
				treeGridModel: 'adjacency', //treeGrid模式，跟json元数据有关 ,adjacency/nested
				url: urls.getFeeDetailByIdUrl,
				datatype: 'json',
				//mtype: "POST",
				height: "280px",
				autoWidth:true,
				ExpandColClick: true, //当为true时，点击展开行的文本时，treeGrid就能展开或者收缩，不仅仅是点击图片
				ExpandColumn: 'projectName',//树状结构的字段
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
            self.resizeScroll();
        },
        resizeScroll: function () {
            var self = this;
            $(self.treeGridId).closest(".ui-jqgrid-bdiv").css({ 'overflow-x' : 'scroll' });
        }
    };
    
    //列格式
    var toFormatter = function (value) {
    	return value.toFixed(2);
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
        colNames:['项目名称','角色','投入人/天','开始时间','结束时间','状态'],
        colModel:[
			{name:'projectName',index:'projectName', sortable:false,fixed:true, width: 180,align: 'center',editable: false},
			{name:'empName',index:'empName', sortable:false,fixed:true, width: 135,align: 'center',editable: false},
			{name:'manpowerInput',index:'manpowerInput', sortable:false,fixed:true, width: 100,align: 'center',editable: false},
			{name:'startDate',index:'startDate', sortable:false,fixed:true, width: 150,align: 'center',editable: false,
				formatter: function (value) {
					if(value==null || value==''){
						return '-';
					}
					return value;
                }
			},
			{name:'endDate',index:'endDate', sortable:false,fixed:true, width: 150,align: 'center',editable: false,
				formatter: function (value) {
					if(value==null || value==''){
						return '-';
					}
					return value;
                }	
			},
			{name:'projectProgress',index:'projectProgress', sortable:false,fixed:true, width: 120,align: 'center',editable: false}
		],
        viewrecords: true,
        loadComplete: function (xhr) {
            setTimeout(function () {
                updatePagerIcons();
            }, 0);
            $("#participatejProjectTable").find(".ui-jqgrid-bdiv").height(tableHeight + 2);
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
            participatejProjectGridOption.postData = {organId:organId, empId: empId};
            $(self.gridId).jqGrid(participatejProjectGridOption);
            self.hasInit = true;
        },
        reloadData: function (organId, empId) {
            var self = this;
            var params = {organId: organId, empId: empId};
            $(self.gridId).clearGridData().setGridParam({
                postData: params
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#participatejProjectTable').width() * 0.98);
        }
    };
    

    /***
     * 修改grid分页图标
     */
    function updatePagerIcons() {
        var replacement =
        {
            'ui-icon-seek-first': 'icon-double-angle-left bigger-140',
            'ui-icon-seek-prev': 'icon-angle-left bigger-140',
            'ui-icon-seek-next': 'icon-angle-right bigger-140',
            'ui-icon-seek-end': 'icon-double-angle-right bigger-140'
        };
        $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function () {
            var icon = $(this);
            var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

            if ($class in replacement) icon.attr('class', 'ui-icon ' + replacement[$class]);
        })
    }

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
    function clearEChart(eChartObj){
    	if(eChartObj){
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
    	init: function(organId){
    		var self = this;
    		var date = new Date();
    		self.date = date.getFullYear();
    		self.organId = organId;
    		self.getPostDateFun(organId);
    	},
    	getPostDateFun : function(organId){
    		var self = this;
			$.ajax({
				url : urls.getDbDateUrl,
				type: 'post',
				success: function(data){
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
				error: function(){}
			});
		},
    	radioChangeModelFun: function(){
    		$('#optionsRadiosCost').unbind('click').bind('click', function(){
    			$('#downLoadProjectCostModel').show();
    			$('#downLoadProjectPersonModel').hide();
    		});
    		$('#optionsRadiosPerson').unbind('click').bind('click', function(){
    			$('#downLoadProjectPersonModel').show();
    			$('#downLoadProjectCostModel').hide();
    		});
    	},
    	downLoadCostFun: function(){
    		var self = this;
    		self.setCostHrefFun('downLoadProjectCostModel');
    	},
    	setCostHrefFun: function(id){
    		$('#' + id).unbind('click').bind('click',function(){
    			window.location.href = urls.downLoadProjectInfoAndCostUrl;
    		});
    	},
    	downLoadPersonFun: function(){
    		var self = this;
    		self.setPersonHrefFun('downLoadProjectPersonModel');
    	},
    	setPersonHrefFun: function(id){
    		$('#' + id).unbind('click').bind('click',function(){
    			window.location.href = urls.downLoadProjectPersonExcelUrl;
    		});
    	},
    	selectChangeFun: function(data){
    		var self = this;
    		
    		self.appendSelectOptionFun(data.dateList);
    		
    		$('#selectTotalId').change(function(){
    			var option = $('#selectTotalId option:selected').attr('value');
    			if(option == 'month'){
    				self.appendSelectOptionFun(defaultDatas.curMonth);
    			}else if(option == 'quarter'){
    				self.appendSelectOptionFun(defaultDatas.curQuarter);
	    		}else if(option == 'halfYear'){
	    			self.appendSelectOptionFun(defaultDatas.curHalfYear);
	    		}else if(option == 'year'){
	    			self.appendSelectOptionFun(defaultDatas.curYear);
	    		}
    		});
    	},
    	appendSelectOptionFun: function(data){
    		var self = this;
    		$('#selectDetailId').empty();
    		var option = "";
			$.each(data,function(ind, obj){
				option += "<option>"+ obj +"</option>";
			});
			$('#selectDetailId').append(option);
    	},
    	projectModelFileSelectedFun: function() {
    		var self = this;
    		self.addBtnDisabledStyleFun();
    		$('#projectModelFile').change(function(){  
    			$('#importState').hide();
    			self.changeImportBtnShowFun();
    			var fileVal = $('#projectModelFile').val();
    			if(fileVal != null && fileVal != ''){
    				self.removeBtnDisabledStyleFun();
    			} else {
    				self.addBtnDisabledStyleFun();
    			}
            });
    	},
    	btnChickStyleFun: function(){
    		var self = this;
    		$('#btn-form').unbind('click').bind('click', function(){
    			self.changeNowImportBtnShowFun();
    			$('#organIdHidden').val(self.organId);
    			self.importFormSubmitFun("1");
    		});
    	},
    	importFormSubmitFun: function(type){
    		var self = this;
    		$('#importProjectModelForm').ajaxSubmit({
    			url: urls.importProjectExcelDatasUrl,
    			type: 'post',
    			data: {type: type},
    			dataType: 'json',
    			success: function(data){
    				self.setImportStateAllHideenFun();
    				self.setFileErrorsFun(data);
    			},
    			error: function(XmlHttpRequest, errorThrown){
    				$('#importProjectModelForm').resetForm();
//    				self.alertModelFun("请求后台超时.");
    				self.loadErrorsFun("请求后台超时.");
    			}
    		});
    	},
    	continueImportBtnFun: function() {
    		var self = this;
    		$('#continueImport').unbind('click').bind('click', function(){
    			$('#organIdHidden').val(self.organId);
    			self.importFormSubmitFun("2");
    		});
    	},
    	cancelImportBtnFun: function() {
    		var self = this;
    		$('#cancelImport').unbind('click').bind('click', function(){
    			$('#importProjectModelForm').resetForm();
    			self.hideImpStateModelFun();
    			self.setImportStateAllHideenFun();
    			self.changeImportBtnShowFun();
    			var fileVal = $('#projectModelFile').val();
    			if(fileVal != null && fileVal != ''){
    				self.removeBtnDisabledStyleFun();
    			} else {
    				self.addBtnDisabledStyleFun();
    			}
    		});
    	},
    	setFileErrorsFun: function(data){
    		var self = this;
    		if(data.fileError != undefined){
//    			self.alertModelFun(data.fileError);
    			self.loadErrorsFun(data.fileError);
    			self.changeImportBtnShowFun();
    		}else if(data.fileIsRepeat != undefined){
//    			self.alertModelFun(data.fileIsRepeat);
    			self.loadErrorsFun(data.fileIsRepeat);
    			self.changeImportBtnShowFun();
    			self.hideImpStateModelFun();
    		}else{
    			self.loadErrorsOrOperateFun(data);
    		}
    	},
    	loadErrorsOrOperateFun: function(data){
    		var self = this;
    		self.setImportStateAllHideenFun();
    		self.showImpStateModelFun();
    		self.loadTemplateErrorsFun(data);
    		self.loadFileNullErrorsFun(data);
    		self.loadImportErrorsFun(data);
    		self.loadOperateErrorsFun(data);
    		self.loadImportSuccessFun(data);
    	},
    	alertModelFun: function(data){
    		$('#importDataTemplate').modal('show');
			$('#importDataTemplate').on('shown.bs.modal', function() {
				$('#importFileSizeError').html(data);
			});
    	},
    	loadTemplateErrorsFun: function(data){
    		var self = this;
    		if(data.templateError != undefined){
    			$('#templateInfo').show();
    			if(data.templateError == 'personTypeError'){
    				$('#personType').show();
    				$('#costType').hide();
    				self.setPersonHrefFun('personType');
    			}else{
    				$('#costType').show();
    				$('#personType').hide();
    				self.setCostHrefFun('costType');
    			}
    			self.changeImportBtnShowFun();
    		}
    	},
    	loadFileNullErrorsFun: function(data){
    		var self = this;
    		if(data.contentIsNull != undefined){
    			self.loadErrorsFun(data.contentIsNull);
    		}
    	},
    	loadErrorsFun: function(data){
    		var self = this;
    		self.setImportStateAllHideenFun();
    		$('#fileErrorsId').html(data);
    		$('#fileInfo').show();
    		self.changeImportBtnShowFun();
    	},
    	loadImportErrorsFun: function(data){
    		var self = this;
    		if(data.listErrors != undefined){
    			requestErrorData = data.listErrors;
				formalErrorGridObj.init();
				$('#importErrorNum').html(data.listErrors.length);
				$('#importError').show();
//				$('#continueImportBtn').hide();
				self.changeImportBtnShowFun();
			}
    	},
    	loadOperateErrorsFun: function(data){
    		if(data.listInfo != undefined){
    			requestInfoData = data.listInfo;
				updateAndDeleteTipsGridObj.init();
				$('#import-info-year').html(data.infoDate.substr(0, 4));
				$('#import-info-month').html(data.infoDate.substr(4, 5));
				$('#importInfo').show();
			}
    	},
    	loadImportSuccessFun: function(data){
    		var self = this;
    		if(data.success != undefined) {
				$('#totalNum').html(data.totalNum);
				$('#importSuccess').show();
				$('#importProjectModelForm').resetForm();
				var style = 'display : none;';
				$('#downLoadProjectCostModel').removeAttr('style');
				$('#downLoadProjectPersonModel').removeAttr('style').attr(style);
				self.changeImportBtnShowFun();
				self.showSuccessDatasFun();
			}
    	},
    	setImportStateAllHideenFun: function(){
    		$('#templateInfo').hide();
    		$('#fileInfo').hide();
    		$('#importError').hide();
    		$('#importInfo').hide();
    		$('#importSuccess').hide();
    	},
    	changeImportBtnShowFun: function(){
    		var self = this;
			$('#btnImportText').show();
			$('#btnNowImportText').hide();
			self.removeBtnDisabledStyleFun();
    	},
    	changeNowImportBtnShowFun: function(){
    		var self = this;
    		$('#btnNowImportText').show();
    		$('#btnImportText').hide();
    		self.addBtnDisabledStyleFun();
    	},
    	addBtnDisabledStyleFun: function(){
    		$('#btn-form').attr('disabled','disabled');
    	},
    	removeBtnDisabledStyleFun: function(){
    		$('#btn-form').removeAttr('disabled');
    	},
    	showImpStateModelFun: function(){
    		$('#importState').show();
    	},
    	hideImpStateModelFun: function(){
    		$('#importState').hide();
    	},
    	showSuccessDatasFun: function(){
    		$('#showDatas').unbind('click').bind('click',function(){
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
        colNames:['错误位置','错误信息'],
        colModel:[
			{name:'locationError',sortable:false,fixed:true, width: 200,align: 'center',editable: false},
			{name:'errorMsg',sortable:false,fixed:true, width: 850,align: 'left',editable: false}
		],
        scroll: true,
        rownumbers: true,
        hoverrows : false,
        editable : false,
        loadComplete: function (xhr) {
            setTimeout(function () {
                updatePagerIcons();
            }, 0);
            $("#formalErrorTable").find(".ui-jqgrid-bdiv").height(200 + 2);
        }
    };
    var formalErrorGridObj = {
        gridId: '#formalErrorGrid',
        resultData: null,
        init: function () {
            var self = this;
//            self.clearGrid();
//            $(self.gridId).jqGrid(formalErrorGridOption);
//            self.reloadData();
            self.appendGrid();
        },
        reloadData: function () {
            var self = this;
            for ( var i = 0; i <= requestErrorData.length; i++){ 
            	$(self.gridId).jqGrid('addRowData', i + 1, requestErrorData[i]); 
            }
            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#importError').width() * 0.98);
//            $(self.gridId).setGridWidth($('#formalErrorTable').width() * 0.90);
        },
        clearGrid: function(){
        	var self = this;
        	$(self.gridId).clearGridData();
        },
        appendGrid: function(){
        	var self = this;
			$('.importErrorTr').remove();
			var tr="";
			var num = 1;
			$.each(requestErrorData,function(index,object){
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
        colNames:['项目名称','执行动作'],
        colModel:[
			{name:'projectName',sortable:false,fixed:true, width: 200,align: 'center',editable: false},
			{name:'action',sortable:false,fixed:true, width: 850,align: 'left',editable: false}
		],
        viewrecords: true,
        scroll: true,
        rownumbers: true,
        rownumWidth: 100,
        loadComplete: function (xhr) {
            setTimeout(function () {
                updatePagerIcons();
            }, 0);
            $("#updateAndDeleteTipsTable").find(".ui-jqgrid-bdiv").height(200 + 2);
        }
    };
    var updateAndDeleteTipsGridObj = {
        gridId: '#updateAndDeleteTipsGrid',
        resultData: null,
        init: function () {
            var self = this;
//            self.clearGrid();
//            $(self.gridId).jqGrid(updateAndDeleteTipsGridOption);
//            self.reloadData();
            self.appendGrid();
        },
        reloadData: function () {
            var self = this;
            for ( var i = 0; i <= requestInfoData.length; i++){ 
            	$(self.gridId).jqGrid('addRowData', i + 1, requestInfoData[i]); 
            }
            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            $(self.gridId).setGridWidth($('#importInfo').width() * 0.98);
//            $(self.gridId).setGridWidth($('#updateAndDeleteTipsTable').width() * 0.90);
        },
        clearGrid: function(){
        	var self = this;
        	$(self.gridId).clearGridData();
        },
        appendGrid: function(){
        	var self = this;
			$('.importInfoTr').remove();
			var tr="";
			var num = 1;
			$.each(requestInfoData,function(index,object){
				if(object.action.indexOf('删除') >= 0){
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
            
            outputInputPercentObj.outputInputPercentChart.resize();
            projectManpowerObj.projectManpowerChart.resize();
        } else {
            $(this).parents(".SetUpBody").find(".chart-view").show();
            $(this).parents(".SetUpBody").find(".table-view").hide();
            $(this).parents(".SetUpBody").attr("view", "chart");
            amountObj.amountChart.resize();
            trainCostObj.trainCostChart.resize();
        }
    });

    $(".index-jxmb-btn").click(function () {
        if ($(this).hasClass("index-jxmb-btn-select"))return;
        $(this).parents(".index-jxmb-tab").find(".index-jxmb-btn").removeClass("index-jxmb-btn-select");
        $(this).addClass("index-jxmb-btn-select");
        if ($(this).parents(".SetUpBody").attr("view") == "project") {
            $(this).parents(".SetUpBody").find(".project-view").show();
            $(this).parents(".SetUpBody").find(".employee-view").hide();
            $(this).parents(".SetUpBody").attr("view", "employee");
            $('#projectSearchTxt').val("");
            $('#participateSearchTxt').val("");
            $('#dominantConditionSearchForm').resetForm();
            $('#participateConditionSearchForm').resetForm();
            leadingProjectObj.resizeGrid();
            participateDetailObj.resizeGrid();
        } else {
            $(this).parents(".SetUpBody").find(".employee-view").show();
            $(this).parents(".SetUpBody").find(".project-view").hide();
            $(this).parents(".SetUpBody").attr("view", "project");
            $('#employeeSearchTxt').val("");
            $('#employeeConditionSearchForm').resetForm();
            employeeDetailObj.resizeGrid();
        }
    });
    
    $(".index-proj-btn").click(function () {
        if ($(this).hasClass("index-proj-btn-select"))return;
        $(this).parents(".index-proj-tab").find(".index-proj-btn").removeClass("index-proj-btn-select");
        $(this).addClass("index-proj-btn-select");
        if ($(this).parents(".project-view").attr("view") == "dominant") {
            $(this).parents(".project-view").find(".dominant-view").show();
            $(this).parents(".project-view").find(".participate-view").hide();
            $(this).parents(".project-view").attr("view", "participate");
            $('#projectSearchTxt').val("");
            $('#dominantConditionSearchForm').resetForm();
            leadingProjectObj.resizeGrid();
        } else {
            $(this).parents(".project-view").find(".participate-view").show();
            $(this).parents(".project-view").find(".dominant-view").hide();
            $(this).parents(".project-view").attr("view", "dominant");
            $('#participateSearchTxt').val("");
            $('#participateConditionSearchForm').resetForm();
            participateDetailObj.resizeGrid();
        }
    });

    $(window).resize(function () {
        var _page = $('.leftListDiv.selectList').attr('page');
        if(_page == 'page-one'){
        	changeChartsResize();
        }
        if(_page == 'page-two'){
        	changeChartsTwoResize();
        }
    });
    /**项目总览页面重置echart对象*/
    function changeChartsResize(){
        amountObj.amountChart.resize();
        outputInputPercentObj.outputInputPercentChart.resize();
        inputOutputMapObj.inputOutputMapChart.resize();
        profitLossProjectObj.plProjectChart.resize();
        profitLossAmountObj.plAmountChart.resize();
        trainCostObj.trainCostChart.resize();
        projectManpowerObj.projectManpowerChart.resize();
        projectTypeObj.projectTypeChart.resize();
    }
    
    function changeChartsTwoResize() {
    	leadingProjectObj.resizeGrid();
    	participateDetailObj.resizeGrid();
    	employeeDetailObj.resizeGrid();
    }
    
    function dateCompare(date1,date2){
    	date1 = date1.replace(/\-/gi,"/");
    	date2 = date2.replace(/\-/gi,"/");
    	var time1 = new Date(date1).getTime();
    	var time2 = new Date(date2).getTime();
    	if(time1 > time2){
    		return -1;
    	}else if(time1 == time2){
    		return 0;
    	}else{
    		return 1;
    	}
    }
    
    $("#moreLabel").click(function(){
    	var _this = $(this);
        if (_this.attr("class")=="more-search-label icon-panel-down") {
        	_this.removeClass("icon-panel-down icon-panel-up");
            _this.text('精简筛选条件');
            _this.addClass("icon-panel-up");
            $("#dominantCondition").show();
        } else {
        	_this.removeClass("icon-panel-down icon-panel-up");
            _this.text('更多筛选条件');
            _this.addClass("icon-panel-down");
            $("#dominantCondition").hide();
        }
    });
    $("#participateMoreLabel").click(function(){
    	var _this = $(this);
        if (_this.attr("class")=="more-search-label icon-panel-down") {
        	_this.removeClass("icon-panel-down icon-panel-up");
            _this.text('精简筛选条件');
            _this.addClass("icon-panel-up");
            $("#participateCondition").show();
        } else {
        	_this.removeClass("icon-panel-down icon-panel-up");
            _this.text('更多筛选条件');
            _this.addClass("icon-panel-down");
            $("#participateCondition").hide();
        }
    });
    $("#employeeMoreLabel").click(function(){
    	var _this = $(this);
    	if (_this.attr("class")=="more-search-label icon-panel-down") {
    		_this.removeClass("icon-panel-down icon-panel-up");
    		_this.text('精简筛选条件');
    		_this.addClass("icon-panel-up");
    		$("#employeeCondition").show();
    	} else {
    		_this.removeClass("icon-panel-down icon-panel-up");
    		_this.text('更多筛选条件');
    		_this.addClass("icon-panel-down");
    		$("#employeeCondition").hide();
    	}
    });
    $('.form_date').datetimepicker({
        language:  'cn',
//        todayBtn:  1,
        autoclose: true,
//		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0,
		format: 'yyyy-mm-dd',
	    pickerPosition: 'bottom-left'
    });
    $('.clearText').click(function() {
    	$(this).parents('.input-group').find('.form-control').val('');
    });
})
;