
require(['jquery', 'moment','swal', 'echarts', 'echarts/chart/line','slide','bootstrap','jgGrid', 'underscore','appBase','press'], function ($, moment,swal, echarts) {
	var empIds = "";	
	var isMobile = ('ontouchstart' in document.documentElement);
	var clickEvent = isMobile ? 'touchstart' : 'click';
	var loadEnd=[[false,false,false,false,false],
				 [false,false,false,false,false],
				 [false,false,false,false,false],
				 [false,false,false,false,false],
				];
	function resetLoadEnd(index){
		loadEnd[index]=[false,false,false,false,false];
		
	}
	function checkLoadEnd(index,loadindIndex){
		var arr=loadEnd[index];
		arr[loadindIndex]=true;
		var over=true;	
		$.each(arr,function(){
			if(this==false){
				over=false;
			}
		});
		if(over){
			//加载结束
			empAssignInfoObj.bindCloseEvent(index);
		}
	}
	
	$(window).on("touchstart touchmove touchend scroll",function(){
		var scrollTop = $(document).scrollTop();
		var scrollHeard=$(".scroll-heard");
		var compareEmp=$(".compareEmp");
		if (Math.abs(scrollTop) >= 60) {
			compareEmp.hide();
			compareEmp.find("table").removeClass("activeTop")
			$("#accordion").css("margin-top","65px");
			scrollHeard.show();
			//scrollHeard.css("opacity",1);
			scrollHeard.find("table").removeClass("activeTop").addClass("activeTop")
			
		} else {
			//var opacity=(60-Math.abs(scrollTop))/60
			scrollHeard.hide();
			$("#accordion").css("margin-top","0");
			//scrollHeard.css("opacity",1-opacity);
			scrollHeard.find("table").removeClass("activeTop")
			compareEmp.show();
			
			//compareEmp.css("opacity",opacity);
			compareEmp.find("table").removeClass("activeTop").addClass("activeTop")
		}
	});
	$(window).scroll();
	
	
	var webRoot = G_WEB_ROOT;
		    var urls = {
		        getEmpInfoUrl: webRoot + '/mobile/talentContrast/getEmpInfo.do',               //获取员工信息
		        getPerfChangeUrl: webRoot + '/mobile/talentContrast/getPerfChange.do',          //获取员工绩效信息
		        getJobChangeUrl: webRoot + '/mobile/talentContrast/getJobChange.do',             //获取工作异动相关信息
		        getSearchEmpUrl: webRoot + '/mobile/talentContrast/getSearchEmpList.do',        //获取搜索人员信息
		        getEvalReportUrl: webRoot + '/mobile/talentContrast/getEvalReport.do',          //获取测试信息
		        getDepartChangeUrl: webRoot + '/mobile/talentContrast/getDepartChange.do',      //获取本公司经历信息
		        getPastResumeUrl: webRoot + '/mobile/talentContrast/getPastResume.do'              //获取过往履历信息
		    };
		    
		    //赋值员工信息
		    var empAssignInfoObj = {
		        init: function (empObj, index) {
		            var self = this;
		            if (_.isEmpty(empObj)) {
		                winContrastObj.initHeaderHTML(index);
		                return;
		            }
		            var empId = empObj.empId;
		            self.assignBase(empObj, index);
		            //生成底部的查看链接
		            jobChangeObj.init(empObj.empId, index);
                    perfObj.init(empId, index);
                    evalObj.init(empId, index);
                    workChangeObj.init(empId, index);
		        },
		        bindCloseEvent:function(index){
		        	 var _imgParent = $($('#contrastObj .top-div')[index]);
		        	var imgHtml = '<i class="icon-cross" type="close" >×</i>';
			            _imgParent.append(imgHtml);
			            _imgParent.find('.icon-cross[type="close"]').bind(clickEvent, removeEmpBtnEvent);
			            var _imgParentScroll = $($('#scrollObj .top-div')[index]);
			            _imgParentScroll.append(imgHtml);
			            _imgParentScroll.find('.icon-cross[type="close"]').bind(clickEvent, removeEmpBtnEvent);
		        },
		        assignBase: function (empObj, index) {
		            var self = this;
		            var imgPath = _.isEmpty(empObj.imgPath) ? webRoot + '/assets/img/base/u-default.png' : empObj.imgPath;
		            var imgHtml = '<div class="top-ct-circle">';
		            imgHtml += '<input type="hidden" id="empId' + index + '" value="' + empObj.empId + '">';
		            imgHtml += '<div class="img-rc-head-del btn-header-remove"></div>';
		            imgHtml += '<img class="img-circle img-rc-head" src="' + imgPath + '"></div>';
		            imgHtml += '<div class="top-div-float"><div class="top-div-name">' + empObj.userName + '</div></div>';
		            var _imgParent = $($('#contrastObj .top-div')[index]);
		            _imgParent.html(imgHtml);
		          //  _imgParent.find('.icon-cross[type="close"]').bind('click', removeEmpBtnEvent);
		            var _imgParentScroll = $($('#scrollObj .top-div')[index]);
		            _imgParentScroll.html(empObj.userName);
		            
		            self.compareDiff($('#sexRow').children()[index], empObj.sex == 'm' ? '男' : '女');
		            self.compareDiff($('#ageRow').children()[index], empObj.age);
		            self.compareDiff($('#marryStatusRow').children()[index], empObj.marryStatus == 0 ? '未婚' : '已婚');
		            self.compareDiff($('#degreeRow').children()[index], empObj.degree);
		            self.compareDiff($('#organParentRow').children()[index], empObj.organParentName);
		            self.compareDiff($('#organRow').children()[index], empObj.organName);
		            self.compareDiff($('#entryDateRow').children()[index], empObj.entryDate);
		            self.compareDiff($('#sequenceRow').children()[index], empObj.sequenceName);
		            self.compareDiff($('#sequenceSubRow').children()[index], empObj.sequenceSubName);
		            self.compareDiff($('#abilityRow').children()[index], empObj.abilityName);
		            self.compareDiff($('#rankRow').children()[index], empObj.rankName);
		            self.compareDiff($('#positionRow').children()[index], empObj.positionName);
		        },
		        compareDiff: function (obj, value) {
		            var _obj = $(obj);
		            var _txt = value == null ? '-' : value;
		            _obj.text(_txt);
		            var _arounds = _obj.siblings();
		            var hasDiff = false;
		            $.each(_arounds, function (i, temp) {
		                var _tempVal = $.trim($(temp).text());
		                if (!_.isEmpty(_tempVal) && _tempVal != value) {
		                    hasDiff = true;
		                    return true;
		                }
		            });
		            if (!hasDiff) return;
		            $.each(_arounds, function (i, temp) {
		                var _temp = $(temp);
		                var _tempVal = $.trim(_temp.text());
		                if (!_.isEmpty(_tempVal)) {
		                    _temp.addClass('diff');
		                }
		            });
		            _obj.addClass('diff');
		        }
		    };
		    
		    
		    var jobChangeChartOption = {
		    		animation:animation,
		    	       grid: {
			                borderWidth: 0,
			                x: 20,
			                y: 5,
			                x2: 10,
			                y2: 40
			            },
		            calculable: false,
		            xAxis: [
		                {
		                    boundaryGap: true,
		                    type: "category",
//		                    name: "工作异动",
		                    nameTextStyle: {
		                        color: '#000000'
		                    },axisLabel:{
		                    	textStyle:{}
		                    },
		                    splitLine: {
		                        color: ['#ccc'],
		                        width: 1,
		                        type: 'dashed'
		                    },
		                    axisLine: {
		                        lineStyle: {
		                            color: '#BCBCBC'
		                        }
		                    }, axisTick: {
		                    	show: false
		                    },
		                    data: []
		                }
		            ],
		            yAxis: [
		                {
		                    boundaryGap: false,
		                    type: "category",
//		                    name: "职级",
		                    nameTextStyle: {
		                        color: '#000000'
		                    },
		                    splitLine: {
		                        color: ['#ccc'],
		                        width: 1,
		                        type: 'dashed'
		                    },axisLabel:{
		                    	textStyle:{}
		                    },
		                    axisTick: {
		                        show: false
		                    },
		                    axisLine: {
//		                            onZero: false,
		                        lineStyle: {
		                            color: '#BCBCBC'
		                        }
		                    },
		                    data: []
		                },
		                {
		                    type: "value",
		                    show: false,
		                    splitLine: false,
		                    min: 1,
		                    max: 5,         //TODO 此处根据不同公司的职级划分来定义
		                    splitNumber: 5,
		                    axisLine: {
		                        lineStyle: {
		                            color: '#BCBCBC'
		                        }
		                    }
		                }
		            ],
		            series: [
		                {
		                    type: "line",
		                    smooth: false,
		                    yAxisIndex: 1,
		                    itemStyle: {
		                        normal: {
		                            label: {
		                                show: true,
		                                textStyle: {
		                                    color: '#000000'
		                                },
		                                formatter: function (a) {
		                                    var data = a.data.data;
		                                    return data.rankName + '\n' + data.positionName;
		                                }
		                            },
		                            lineStyle: {
		                                color: '#86D7C6'
		                                // type: 'dotted'
		                            },
		                            color: '#86D7C6'
		                        }
		                    },
		                    data: []
		                }
		            ]
		        };
		    //成长轨迹
		    var jobChangeObj = {
		    	chartId: 'growthChart',
			    modelChartId: 'jobChangeChart',
		        requestSource: null,
		        init: function (empId,index) {
		            var self = this;
		            $.post(urls.getJobChangeUrl, {'empId': empId}, function (rs) {
		                if (_.isEmpty(rs)) {
		                	 checkLoadEnd(index,0);
		                    return;
		                }
		                self.initChart(rs,index);
		                checkLoadEnd(index,0);
		            });
		        },
		        initChart: function (source,index) {
		            var self = this;
		            var filterData = {};
		            for (var i = source.length - 1; i >= 0; i--) {
		                //10/22，跟xn,sy确定以季度为单位，一个季度有多条数据，取最后一条
		                var obj = source[i];
		                var timeKey = self.getYearQuarter(obj.changeDate);
		                filterData[timeKey] = obj;
		            }
		            self.filterData = filterData;
		            var timeArr = self.packTimeArr(filterData);
		            var seriesData = self.getSeriesData(filterData, timeArr);
		            
		            var chartObj = echarts.init(document.getElementById(self.chartId + index));
	                var xAxisDataNew = [];
	                self.option=$.extend(true,{}, jobChangeChartOption);
//	                self.option.yAxis[0].axisLabel.textStyle={fontSize:'1'};
//	                self.option.xAxis[0].axisLabel.textStyle={fontSize:'1'};
	                self.option.yAxis[0].data=['1', '2', '3', '4', '5'];
	                self.option.series[0].itemStyle.normal.label.formatter=function (a, b, c) {

                    };
                    self.option.yAxis[0].axisLabel={show:false};
	                self.option.grid={x:10,y:10,x2:10,y2:10};
	                self.option.xAxis[0].axisLabel = {show:false}
//                    self.option.grid= {
//		                borderWidth: 0,
//		                x: 20,
//		                y: 5,
//		                x2: 10,
//		                y2: 10
//		            },
	                self.option.xAxis[0].data = timeArr;
	                self.option.series[0].data = seriesData;
	                chartObj.setOption(self.option, true);
	                if(null==self.modelChart){
	                	self.modelChart=echarts.init(document.getElementById(self.modelChartId));
	                	self.modelOption = $.extend(true,{}, jobChangeChartOption);
	                	self.modelOption.yAxis[0].data=['1级', '2级', '3级', '4级', '5级'];
	                	self.modelOption.grid.x=35;
	                }
//	                self.chartObj.resize();
	                
	                $("#"+self.chartId + index).press(function(){
        				$("#growModal").modal('show');
	                	self.modelOption.xAxis[0].data = timeArr;
	                	self.modelOption.series[0].data = seriesData;
	                	self.modelChart.setOption(self.modelOption, true);
	                });
		        },
		        getSeriesData: function (filterData, timeArr) {
		            var seriesData = [];
		            $.each(timeArr, function (i, time) {
		                var obj = filterData[time];
		                var rsData;
		                if (!obj) {
		                    var preData = _.clone(seriesData[i - 1]);
		                    preData.symbolSize = 0;
		                    rsData = preData;
		                } else {
		                    //-1是为了在坐标上正确显示，如A1.2,应该是显示在坐标轴[0,1]区间
		                    var value = parseFloat(obj.rankName.substr(-3, 3)) ;
		                    rsData = {value: value, data: obj};
		                }
		                seriesData.push(rsData);
		            });
		            return seriesData;
		        },
		        //遍历时间集合，生成每一个区间。如：开始时间 = 2014Q4 ,结束时间 = 2015Q2,则生成[2014Q4,2015Q1,2015Q2]
		        packTimeArr: function (filterData) {
		            var filterTime = _.keys(filterData);
		            return filterTime;
		        },
		        //转换成年度季度，如2015Q1
		        getYearQuarter: function (date) {
		            var ymd = date.split('-');
		            var month = parseInt(ymd[1]);
		            var quarter;
		            if (month <= 3) {
		                quarter = 1;
		            } else if (month <= 6) {
		                quarter = 2;
		            } else if (month <= 9) {
		                quarter = 3;
		            } else {
		                quarter = 4;
		            }
		            return ymd[0] + 'Q' + quarter;
		        },
		        //生成查看链接
		        generateGrowLink: function (empObj, index) {
		            var growLink = $('<a  data-id="' + empObj.empId + '" data-name="' + empObj.userName + '">查看</a>');
		            $('#growthLinkArea div').eq(index).empty().append(growLink);
		            growLink.click(function () {
		                var frames = window.parent.document.getElementById("mainFrame");

		                $('#growModal').modal('show');
		                $('#growModal .modal-title ').find('span').text($(this).attr('data-name'));
		                jobChangeObj.init($(this).attr('data-id'));
		            });
		        }
		    };
		    var perfEnumStr = $('#performanceStr').val().split(',');
		    var perfEnum =$('#performanceValue').val().split(',');
		    var perfChartOption = {
		    		animation:animation,
		            grid: {
		                borderWidth: 0,
		                x: 20,
		                y: 5,
		                x2: 10,
		                y2: 40
		            },
		            calculable: false,
		            xAxis: [
		                {
		                    boundaryGap: false,
		                    type: "category",
		                    splitLine: {
		                        color: ['#ccc'],
		                        width: 0,
		                        type: 'dashed'
		                    },
		                    axisLine: {
		                        lineStyle: {
		                            width: 0,
		                            color: '#BCBCBC'
		                        }
		                    },
		                    axisLabel: {
		                        rotate: 30,
		                    },
		                    axisTick: false,
		                    data: []
		                }
		            ],
		            yAxis: [
		                {
		                    axisTick: false,
		                    boundaryGap: false,
		                    type: "category",
		                    splitLine: {
		                        color: ['#ccc'],
		                        width: 0,
		                        type: 'dashed'
		                    },

		                    axisLine: {
		                        onZero: false,
		                        lineStyle: {
		                            width: 0,
		                            color: '#BCBCBC'
		                        }
		                    },axisLabel:{
		                    	textStyle:{}
		                    },
		                    data: []
		                },
		                {
		                    boundaryGap: false,
		                    type: "value",
		                    show: false,
		                    splitLine: false,
		                    splitNumber: 1,
		                    min: 1, //最小1星
		                    max: perfEnum.length,         //TODO 此处根据不同公司的职级划分来定义
		                    splitNumber: perfEnum.length,
		                    axisLine: {
		                        lineStyle: {
		                            color: '#BCBCBC'
		                        }
		                    }
		                }
		            ],
		            series: [
		                {
		                    type: "line",
		                    smooth: false, //不用平滑模式
		                    yAxisIndex: 1,
		                    showAllSymbol: true,
		                    itemStyle: {
		                        normal: {
		                            label: {
		                                show: false
		                            },
		                            lineStyle: {
		                                color: '#86D7C6'
		                                // ,type: 'dotted'
		                            },
		                            color: '#86D7C6'
		                        }
		                    },
		                    data: []
		                }
		            ]
		        };
		    /**
		     * 个人绩效
		     */
		    var perfObj = {
		            chartId: 'perfTrackChart',
		            modelChartId: 'perChart',
		            isQuarter: false,
		            modelChart: null,
		            isFirst: true,
		            init: function (empId, index) {
		                var self = this;
		                $.post(urls.getPerfChangeUrl, {'empId': empId}, function (rs) {
		                    if (_.isNull(rs)) {
		                    	 checkLoadEnd(index,1);
		                        return;
		                    }
		                    self.extendData(rs, index, empId);
		                    checkLoadEnd(index,1);
		                });
		            },
		            initChart: function (xAxisData, seriesData, index) {
		            	   var self = this;
		                var chartObj = echarts.init(document.getElementById(self.chartId + index));
		                var xAxisDataNew = [];
		                $.each(xAxisData, function (i, obj) {
		                    var str = obj.substring(0, 4);
		                    str += obj.substring(5, 6);
		                    xAxisDataNew.push(str);
		                })
		                self.option=$.extend(true,{}, perfChartOption);
//		                self.option.yAxis[0].axisLabel.textStyle={fontSize:'1'};
//		                self.option.xAxis[0].axisLabel.textStyle={fontSize:'1'};
		                self.option.yAxis[0].data=perfEnum;
		                self.option.xAxis[0].data=perfEnum;
		                self.option.yAxis[0].axisLabel={show:false};
		                self.option.grid={x:10,y:10,x2:10,y2:10};
		                self.option.xAxis[0].axisLabel = {show:false};
		                self.option.series[0].data = seriesData;
		                chartObj.setOption(self.option, true);
		                if(null==self.modelChart){
		                	self.modelChart=echarts.init(document.getElementById(self.modelChartId));
		                	self.modelOption = $.extend(true,{}, perfChartOption);
		                	self.modelOption.yAxis[0].data=perfEnumStr;
		                	self.modelOption.grid.x=35;
		                }
		                $("#"+self.chartId + index).press(function(){
		                	$("#perModal").modal('show');
		                	self.modelOption.xAxis[0].data = xAxisDataNew;
		                	self.modelOption.series[0].data = seriesData;
		                	self.modelChart.setOption(self.modelOption, true);
		                });
	
		            },
		            extendData: function (source, index, empId) {
		                var self = this;
		                var bool = false;
		                if(_.isEmpty(source)){
		                	return;
		                }
		                var newSource = [];

		                for (var i = 0; i < source.length; i++) {
		                    newSource[i] = source[source.length - 1 - i];
		                }

		                var chartData={xAxisYearData:[],xAxisQuarterData:[],seriesData:[]};
		              //  var chartDataMap = {};
		                $.each(newSource, function (i, obj) {
		                 //   var chartData = chartDataMap[obj.empId] || {};
		                    var yearMonth = obj.yearMonth.toString();
		                    var month = parseInt(yearMonth.substring(4, yearMonth.length));
		                    if (month > 1 && month <= 3 || month > 7 && month <= 9) {
		                        bool = true;
		                    }
//		                    if (!chartData.xAxisYearData) {
//		                        chartData.xAxisYearData = [];
//		                    }
//		                    if (!chartData.xAxisQuarterData) {
//		                        chartData.xAxisQuarterData = [];
//		                    }
//		                    if (!chartData.seriesData) {
//		                        chartData.seriesData = [];
//		                    }
		                    chartData.xAxisYearData.push(obj.rankingYear);
		                    chartData.xAxisQuarterData.push(obj.rankingQuarter);
		                    chartData.seriesData.push(obj.perfKey);
		                   // chartDataMap[obj.empId] = chartData;
		                });
		                self.isQuarter = bool;
		                self.initChart(bool ? chartData.xAxisQuarterData : chartData.xAxisYearData, chartData.seriesData, index);
//		                var empArr = empId.split(',');
//		                $.each(empArr, function (i, item) {
//		                    var chartData = chartDataMap[item];
//		                    self.initChart(bool ? chartData.xAxisQuarterData : chartData.xAxisYearData, chartData.seriesData, index || i);
//		                });

		            }
		        };
		    
		    //测评信息
		    var evalObj = {
		        evalYear: '#evalYearId',
		        dimension: '#dimensionId',
		        dimensionPanel: '#dimensionDivId',
		        init: function (empId, index) {
		            var self = this;
		            $.post(urls.getEvalReportUrl, {'empId': empId}, function (rs) {
		                if (_.isEmpty(rs)) {
		                	 checkLoadEnd(index,2);
		                    var empArr = empId.split(',');
		                    self.compareDiff(index, empArr.length > 1 ? empArr : $('#contrastObj .col-xs-10 .col-xs-3'));
		                    return;
		                }
		                self.extendData(rs, index, empId);
		                checkLoadEnd(index,2);
		            });
		        },
		        extendData: function (source, index, empId) {
		            var self = this;
		            var empArr = empId.split(',');
		            $.each(source, function (i, obj) {

		                if (_.isEmpty(obj.abilityName)) {
		                    return true;
		                }
		                var idx = index || _.indexOf(empArr, obj.empId);
		                $($(self.evalYear).children()[idx]).text(obj.reportYear + '年度');

		                var score = (Math.round(obj.score * 10000) / 100) + '%';
		                var _childs = $(self.dimension).children();
		                var _childsPanel = $(self.dimensionPanel).children();
		                if (_childsPanel.length == 0) {
		                    self.createNewRows(obj, idx, score);
		                    return true;
		                }
		                var bool = false;
		                $.each(_childsPanel, function (c, child) {
		                    var _child = $(child);
		                    if (_child.attr('dimId') == obj.abilityId) {
		                        bool = true;
		                        $($(_childs[c]).children()[idx]).html(score);
		                        return true;
		                    }
		                });
		                if (!bool) self.createNewRows(obj, idx, score);
		            });
		            self.compareDiff(index, empArr.length > 1 ? empArr : $('#contrastObj .added div'));
		        },
		        createNewRows: function (obj, index, score) {
		            var self = this;
		            var panel='<dd dimId="' + obj.abilityId + '"><span>' + obj.abilityName + '</span></dd>';
		            var html = '<tr>';
		            	   for (var i = 0; i < 4; i++) {
				                html += '<td>' + (i == index ? score + ' ' : ' ') + '</td>';
				            }
		            html += '</tr>';
//		            var html = '<div class="row" dimId="' + obj.abilityId + '">'
//		                + '<div class="col-xs-2"><span>' + obj.abilityName + '</span></div>'
//		                + '<div class="col-xs-10">'
//		                + '<div class="row">';
//		            for (var i = 0; i < 4; i++) {
//		                html += '<div class="col-xs-3">' + (i == index ? score + ' ' : ' ') + '</div>';
//		            }
//		            html += '</div></div></div>';
		            $(self.dimension).append(html);
		            $(self.dimensionPanel).append(panel);
		        }, remove: function () {
		            var self = this;
		            var _childsPanel = $(self.dimensionPanel).children();
		            $.each($(self.dimension).children(), function (i, child) {
		                var num = 0;
		                var recordMax = 0;
		                var maxDivObj = null;
		                $.each($(child).children(), function (j, div) {
		                    var text = $(div).html();
		                    $(this).removeClass('high');
		                    if (text == "- " || text == "-" || text == "" || text == " ") {
		                        num++;
		                    } else {
		                        var val = parseFloat(text);
		                        if (val > recordMax) {
		                            recordMax = val;
		                            maxDivObj = this;
		                        }
		                    }
		                });
		                if (num >= 4) {
		                	$(child).remove();
		                	$(_childsPanel[i]).remove();
		                } else if (maxDivObj != null) {
		                    $(maxDivObj).addClass('high');
		                }
		            });
		        },
		        compareDiff: function (index, empArr) {
		            var self = this;
		            $.each($(self.dimension).children(), function (i, child) {
		                var _child = $(child).children();
		                $.each(empArr, function (e, emp) {
		                    if (!_.isObject(emp)) {
		                        var _one = $(_child[e]);
		                        if (_.isEmpty($.trim(_one.text()))) {
		                            _one.text('- ');
		                        }
		                        return true;
		                    }
		                    var _empChild = $(emp).children('.icon-cross[type="close"]');
		                    if (_empChild.length > 0) {
		                        var _one = $(_child[e]);
		                        if (_.isEmpty($.trim(_one.text()))) {
		                            _one.text( );
		                        }
		                    }
		                });
		                var recordMax = 0;
		                var maxDivObj = null;
		                $.each($(child).children(), function (j, div) {
		                    var text = $(div).html();
		                    $(this).removeClass('high');
		                    if (text == "- " || text == "-" || text == "" || text == " ") {

		                    } else {
		                        var val = parseFloat(text);
		                        if (val > recordMax) {
		                            recordMax = val;
		                            maxDivObj = this;
		                        }
		                    }
		                });
		                if (maxDivObj != null) {
		                    $(maxDivObj).addClass('high');
		                }
		            });
		            $.each($(self.evalYear).children(), function (i, obj) {
		                var _child = $(obj);
		                $.each(empArr, function (e, emp) {
		                    if (!_.isObject(emp)) {
		                        var _one = $(_child[i]);
		                        if (_.isEmpty($.trim(_one.text()))) {
		                            _one.text('- ');
		                        }
		                        return true;
		                    }
		                    var _empChild = $(emp).children('.icon-cross[type="close"]');
		                    if (_empChild.length > 0) {
		                        var _one = $(_child[i]);
		                        if (_.isEmpty($.trim(_one.text()))) {
		                            _one.text('- ');
		                        }
		                    }
		                });
		            });
		        }
		    };

		    //工作经历
		    var workChangeObj = {
		        departChange: '#departChange',
		        pastResume: '#pastResume',
		        init: function (empId, index) {
		            var self = this;
		            if(!index&&index!=0){
		            	$.each(empId.split(","),function(i,o){
		            		self.requestData(urls.getDepartChangeUrl, self.departChange, i, o);
				            self.requestData(urls.getPastResumeUrl, self.pastResume, i, o);
		            	});
		            }else{
		            	 self.queryDepartChangeData( self.departChange, index, empId);
				            self.queryPastResumeData( self.pastResume, index, empId);
		            }
		           
		        },
		       queryDepartChangeData: function ( obj, index, empId) {
		            var self = this;
		            $.post(urls.getDepartChangeUrl, {'empId': empId}, function (rs) {
		                if (_.isEmpty(rs)) {
		                	 checkLoadEnd(index,3);
		                    return;
		                }
		                self.extendData(rs, obj, index, empId);
		                checkLoadEnd(index,3);
		            });
		        },
		        queryPastResumeData: function (obj, index, empId) {
		            var self = this;
		            $.post(urls.getPastResumeUrl, {'empId': empId}, function (rs) {
		                if (_.isEmpty(rs)) {
		                	 checkLoadEnd(index,4);
		                    return;
		                }
		                self.extendData(rs, obj, index, empId);
		                checkLoadEnd(index,4);
		            });
		        },

		        requestData: function (url, obj, index, empId) {
		            var self = this;
		            $.post(url, {'empId': empId}, function (rs) {
		                if (_.isEmpty(rs)) {
		                	 checkLoadEnd(index,3);
		                    return;
		                }
		                self.extendData(rs, obj, index, empId);
		                checkLoadEnd(index,3);
		            });
		        },
		        extendData: function (source, objId, index, empId) {
		            var self = this;
		            var empArr = empId.split(',');
		            var currObj=null;
		            $.each(source, function (i, obj) {
		            	var pobj=null;
		            	if(i>0){
		            		pobj=source[i-1];
		            	}
		                var idx = index || _.indexOf(empArr, obj.empId);
		                var bool=false;
		                $.each($(objId).children("tr"),function(j,tr){
		                	var td=$(tr).children()[idx];
		                	if(!bool&&$(td).html().length==0){
		                		bool=true;
		                		self.renderData($(td),obj,objId,pobj);
		                		return;
		                	}
		                });
		                if(!bool){
		                	//创建一行
		                	self.renderData(self.createNewRow(objId).children()[idx],obj,objId,pobj);
		                }
		                
		                //为（现岗位任职时间）赋值,并对比
		                if (objId == self.departChange && i==0) {
		                    self.compareAssumeDate(obj.changeDate, idx);
		                }
		            });
		            self.addDiffHTML(objId);
		        },
		        createNewRow: function (objId) {
		            var row = $('<tr>'
		                + '<td></td>'
		                + '<td></td>'
		                + '<td></td>'
		                + '<td></td>'
		                + '</tr>');
		            $(objId).append(row);
		            return row;
		        },
		        renderData: function (td, obj,objId,pobj) {
		            var self = this;
		            var type = objId == self.departChange;
		            var timeInterval = type ? moment(obj.changeDate).format('YYYY.MM')
		                : (moment(obj.entryDate).format('YYYY.MM') + '-' + moment(obj.runOffDate).format('YYYY.MM'));
		            if(pobj!=null){
		            	timeInterval+=type ?"-"+moment(pobj.changeDate).format('YYYY.MM'):""
		            }else{
		            	timeInterval+=type ?"-至今":"";
		            }
		            var timeStyle = type ? 'style="line-height:36px;" ' : '';
		            var workUnit = type ? obj.organName : obj.workUnit;
		            var unitStyle = workUnit.length < 6 ? 'style="line-height:36px;" ' : '';
		            var unitTitle = workUnit.length > 12 ? ('title="' + workUnit + '" ') : '';
		            var unitShow = workUnit.length > 12 ? workUnit.substring(0, 11) + '...' : workUnit;
		            var position = obj.positionName;
		            var positionStyle = position.length < 7 ? 'style="line-height:36px;" ' : '';
		            var positionTitle = position.length > 12 ? ('title="' + position + '" ') : '';
		            var positionShow = position.length > 12 ? position.substring(0, 11) + '...' : position;
		            var rowHtml = timeInterval+"<br/>"+unitShow+"，"+positionShow;
		               
		            $(td).append(rowHtml);
		        },
		        addDiffHTML: function (objId) {
		            var self = this;
		            var _obj = $(objId);
		            var _childs = _obj.children();
		            var height=0;
		            $.each(_obj.children(),function(i,tr){
		            	height+=$(tr).height();
		            });
		            var height=height+ 'px';
		            $(objId+"Panel").css('lineHeight', height);
		        },
		        compareAssumeDate: function (dateValue, index) {
		            var _rowChild = $('#postionAssumeDateRow').children();
		            var _rowOneChild = $(_rowChild[index]);
		            _rowOneChild.text(dateValue);
		            var _diffChild = _.find(_rowOneChild.siblings(), function (sib) {
		                var sibTxt = $.trim($(sib).text());
		                return (!_.isEmpty(sibTxt) && sibTxt != dateValue);
		            });
		            if (_diffChild) {
		                $.each(_rowChild, function (j, newObj) {
		                    var _newObj = $(newObj);
		                    var newObjTxt = $.trim(_newObj.text());
		                    if (!_.isEmpty(newObjTxt)) {
		                        _newObj.addClass('diff');
		                    }
		                });
		            }
		        }
		    };
		    

		    //移除时对比（工作经历）的不同来修改布局
		    function removeOfCompareDiff(objId, index) {
		    	var obj=$(objId);
		    	var removeNum=0;
		    	var removeTrObj=[];
		       $.each(obj.children(),function(i,tr){
		    	   var bool=true;
		    	   $.each($(tr).children(),function(m,td){
		    		   if($(td).html().length>0){
		    			   bool=false;
		    			   return;
		    		   }
		    	   });
		    	   if(bool){
		    		   removeNum++;
		    		   removeTrObj.push($(tr));
		    	   }
		       });
		       var saveFirst=removeNum==obj.children().length;
	    	   $.each(removeTrObj,function(i,tr){
	    		   if(!saveFirst||(saveFirst&&i>0)){
	    			   $(tr).remove();
	    		   }
	    		   
	    	   });
		    	   var height=0;
		       $.each(obj.children(),function(i,tr){
	            	height+=$(tr).height();
	            });
	            var height=height+ 'px';
	            $(objId+"Panel").css('lineHeight', height);
		    };
		    
		    /**
		     * 搜索表格
		     */
		    var searchEmpObj = {
		            gridId: '#searchEmpGrid',
		            searchTxt: null,
		            gridOption: {
		                url: urls.getSearchEmpUrl,
		                datatype: 'json',
		                postData: {},
		                mtype: 'POST',
		                autowidth: true,
		                height: 250,//250
//		                rowHeight:83,
		                colNames: ['', ''],
		                colModel: [
		                    {name: 'imgPath', width: 80, sortable: false, align: 'center',
		                        formatter: function (a,b,obj) {
		                        	var result='<div class="memo-list">'+
		                        	'<div class="memo-col-img">'+
		                        	'<img class="ct-circle-header" src="'+webRoot+'/assets/img/index/head4.jpg">'+
		                        	'</div>'+
		                        	'<div class="memo-col-content">'+

		                                   '<div class="memo-list-name-value">'+obj.userName+' </div>'+
		                                   '<div class="memo-list-name-value">'+obj.organName+'</div>'+
//		                                    '<div class="memo-list-name-value">'+obj.organParentName+'-'+obj.positionName+' </div>'+
//		                                     '<div class="memo-list-name-value">'+obj.sequenceName+'-'+obj.sequenceSubName+'</div>'+
		                            '</div>'+

//		                            	'</div>'+
		                        	'</div>'+
		                        '</div>';
		                        	return result;
		                        }},
		                    {
		                        name: 'myac',
		                        width: 100,
		                        fixed: true,
		                        sortable: false,
		                        align: 'center',
		                        formatter: function (value, options, row) {
		                            return '<div data-index="' + row.empId + '" class="add_col" >加入对比</div>';
		                        }
		                    }
		                ],
		                loadComplete: function (xhr) {
		                	if(xhr.page==1){
		                		var h=1;
		                		var total=xhr.records;
		                		if(total>3){
			                		total=3;
			                	}
			                	h=total*83+1;
			                	$("#searchEmpTable").height(h);
			                	$("#searchEmpTable").find(".ui-jqgrid-bdiv").height(h);
		                	}
		                    $('.add_col').unbind(clickEvent).bind(clickEvent, function (e) {
		                        var _this = $(this);
		                        var empId = _this.attr('data-index');
		                        //TODO 添加进对比的清单
		                        if (empIds.indexOf(empId, 0) != -1) {
		                        	swal("","该员工已存在对比列表！");
		                            return;
		                        } else {
		                            empIds += ' ' + empId;
		                        }
		                        $.post(urls.getEmpInfoUrl, {'empId': empId}, function (empObj) {
		                            if (_.isEmpty(empObj)) {
		                                return;
		                            }
		                            var idx = $('#search-index').val();
		                            empAssignInfoObj.init(empObj, idx);
		                            $('#search-modal').modal('hide');
		                        });
		                    });
		                },gridComplete: function () {

		                    $(this).closest('.ui-jqgrid-view').find('div.ui-jqgrid-hdiv').hide();

		                },
		                scroll: true
		            },
		            init: function (searchTxt) {
		                var self = this;
		                if (!self.initTable) {
		                    self.gridOption.postData = {'keyName': searchTxt};
		                    $(self.gridId).jqGrid(self.gridOption);
		                    self.initTable=true;
		                }else{
			                self.initGrid(searchTxt);
		                }
		               
		            },reset:function(){
		            	var self=this;
		            	if(self.initTable){
		            		$(self.gridId).clearGridData();
		            	}
		            	lastEmpId="";
		            	$("#searchEmpTable").height(1);
		            	$("#searchEmpTable").find(".ui-jqgrid-bdiv").height(1);
		            },
		            initGrid: function (keyTxt) {
		                var self = this;
		                $(self.gridId).clearGridData().setGridParam({
		                    postData: {'keyName': keyTxt}
		                }).trigger("reloadGrid");
		                self.resizeGrid();
		            },
		            resizeGrid: function (width) {
		                var self = this;
//		                var parentDom = $(self.gridId).parent();
		                $(self.gridId).setGridWidth(width);
		            }
		        };
		    
	    //初始化页面数据对象
	    var winContrastObj = {
	        empId: '#empIds',
	        init: function () {
	            var self = this;
	            self.initHeaderHTML();
//	             empIds = $(self.empId).val();
//	            if (!_.isEmpty(empIds)) {
//	                self.requestData(empIds);
//	                perfObj.init(empIds);
//	                evalObj.init(empIds);
//	                workChangeObj.init(empIds);
//	            } else {
//	                self.initHeaderHTML();
//	            }
	        },
	        requestData: function (empIds) {
//	            var self = this;
//	            $.post(urls.getEmpInfoUrl, {'empIds': empIds}, function (rs) {
//	                if (_.isEmpty(rs)) {
//	                    self.initHeaderHTML();
//	                    return;
//	                }
//	                for (var i = 0; i < 4; i++) {
//	                    empAssignInfoObj.init(rs[i], i);
//	                }
//	            });
	        },
	        initHeaderHTML: function (index) {
	        	initHeaderHTML(index);
//	            var _headerObj = $('#contrastObj .top-div');
//	            if (!_.isUndefined(index)) {
//	                _headerObj = $(_headerObj[index]);
//	            }
//	            _headerObj.html($('#img-window').html());
//	            _headerObj.find('.top-div-btn-add').bind('click', searchBtnEvent);
//	            
//	            var _headerObj = $('.scroll-heard .top-div');
//	            if (!_.isUndefined(index)) {
//	                _headerObj = $(_headerObj[index]);
//	            }
//	            _headerObj.html($('#img-window-scroll').html());
//	            _headerObj.find('.top-div-btn-add').bind('click', searchBtnEvent);
	        }
	    };
	    winContrastObj.init();
	    var searchTisk=null;
	    var lastTime=Date.now();
	    var lastEmpId="";
	    /**
	     * 搜索按钮点击事件
	     */
	    function searchBtnEvent() {
	        var _this = $(this);
	        var _parent = _this.parents('.top-div');
	        var idx = _this.parents('.added').index();
	        $('#search-index').val(idx);

	        var _modal = $('#search-modal');
	     //   _modal.find('.modal-content').css('margin-top', winObj.pageYOffset + 80);
	        searchEmpObj.reset();
	        $('#search-txt').val("");
	        _modal.modal('show');
	       
	    }
	    $('#search-txt').bind('input propertychange',function () {
	    	lastTime=Date.now();
	    	//console.log("change:"+lastTime);
	    	if(searchTisk!=null){
	    		clearTimeout(searchTisk);
	    		searchTisk=null
	    	}
	    	searchTisk=setTimeout(function(){
    			var nowTime=Date.now();
    			//console.log("nowTime:"+nowTime+"   change:"+lastTime+"  jiange:"+(nowTime-lastTime));
    			if(nowTime-lastTime>490){
    				var searchTxt = $.trim($('#search-txt').val());
    				var equals=lastEmpId!=searchTxt;
    				lastEmpId=searchTxt;
    	            if (!_.isEmpty(searchTxt)) {
    	            	if(equals){
    	            		searchEmpObj.init(searchTxt);
    	            	}
    	            }else{
    	            
    	            	 searchEmpObj.reset();
    	            }
    			}
    			searchTisk=null;  
    		}, 500);
        });
	    
	   function initHeaderHTML(index) {
            var _headerObj = $('#contrastObj .top-div');
            if (!_.isUndefined(index)) {
                _headerObj = $(_headerObj[index]);
            }
            _headerObj.html($('#img-window').html());
            _headerObj.find('.top-div-btn-add').bind(clickEvent, searchBtnEvent);
            
            var _headerObj = $('.scroll-heard .top-div');
            if (!_.isUndefined(index)) {
                _headerObj = $(_headerObj[index]);
            }
            _headerObj.html($('#img-window-scroll').html());
            _headerObj.find('.top-div-btn-add').bind(clickEvent, searchBtnEvent);
        }
	    /**
	     * 移除人员按钮事件
	     */
	    function removeEmpBtnEvent() {
	        var _this = $(this);
	        var _parent = _this.parents('.top-div');
	        var idx = _this.parents('.added').index();
	        //删除已存在的empid
	        var empid = $('#empId' + idx).val();
	        if (empIds.indexOf(empid, 0) != -1) {
	            empIds = empIds.replace(empid, '');
	        }
	        
	        initHeaderHTML(idx);
	        
//	        _parent.html($('#img-window').html());
//
//	        $(_parent.find('.top-div-btn-add')).bind('click', searchBtnEvent);

	        
	        var _rowObjs = $('#accordion .cont .slide table tr');
	        $.each(_rowObjs, function (i, obj) {
	            var tds = $(obj).children();
	            var td = $(tds[idx]);
	            td.html('');
	            td.removeClass('diff').removeClass('high');
	            td.unbind("touchstart").unbind("touchend");
	            var _sibling = tds.siblings();
	            var eq = true;
	            var record = "";
	            $.each(_sibling, function (i, td) {
	                if (eq) {
	                	var tdHtml=$.trim($(td).html());
	                	if(tdHtml!=""){
	                		if (record!=""&&record !=tdHtml ) {
		                		 eq = false;
			                     return;
		                    } 
	                		record = $.trim($(td).html());
	                	}
	                }
	            });
	            if (eq) {     //取消同级对比
	                _sibling.removeClass('diff').removeClass('high');
	            }
	        });
	        evalObj.remove();
	        removeOfCompareDiff('#departChange', idx);
	        removeOfCompareDiff('#pastResume', idx);
	        resetLoadEnd(idx);
	    }
	    
	    function windowResize(){
	    	var windownWidth=$(window).width();
	    	var windownHeight=$(window).height();
	    	 $("#perChart").css("width",windownWidth-30);
	    	 $("#perChart").css("height",windownHeight/3*2);
	    	 $("#jobChangeChart").css("width",windownWidth-30);
	    	 $("#jobChangeChart").css("height",windownHeight/3*2);
	    	 if(null!=jobChangeObj.modelChart){
	    		 jobChangeObj.modelChart.resize();
	    	 }
	    	 if(null!=perfObj.modelChart){
	    		 perfObj.modelChart.resize();
	    	 }
	    	 var gridWidth=windownWidth-20-10;

	    	 $("#searchEmpTable").width(gridWidth);
	    	 searchEmpObj.resizeGrid(gridWidth);

	    }
	    windowResize();
	    var scrollEvent = isMobile ? 'touchstart touchmove touchend scroll' : 'scroll';
	    var resizeEvent=isMobile?'orientationchange':'resize';
	    $(window).bind(resizeEvent,function(){
	    	windowResize();
	    });
	    
	});