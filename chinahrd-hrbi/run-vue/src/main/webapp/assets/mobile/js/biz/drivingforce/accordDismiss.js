require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie', 'echarts/chart/gauge',
    'jgGrid', 'underscore', 'utils', "appBase","chartTooltip","gaugeAuxiliary"], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var urls = {
        queryAccordDismissUrl: webRoot + '/mobile/accordDismiss/queryDisminss4Quarter.do', //查询主动流失率信息
        subDismissDataUrl: webRoot + '/mobile/accordDismiss/getSubDismissData.do',	//本部与下属部门流失率对比数据
        getDismissTrendUrl: webRoot + '/mobile/accordDismiss/queryDismissTrend.do',	//主动流失率趋势
        getDismissInfoUrl: webRoot + '/mobile/accordDismiss/queryDismissInfo.do',
        queryRunOffDetailUrl: webRoot + '/mobile/accordDismiss/queryRunOffDetail.do',        //流失人员明细
    };
    var ecConfig = require('echarts/config');
    var reqOrganId=$("#reqOrganId").val();
    var reqOrganName=$("#reqOrganName").val();
    var parentOrganId=$("#parentOrganId").val(); //上级机构ID
    var parentOrganName=$("#parentOrganName").val();
    var timeRange=$("#timeRange").val();
    var LSLDismissNormalMinWidth=parseInt($("#LSLDismissNormal").css("min-width"));
    var height;
    var maxWidth=$(window).width()-72;
    var splitNum=$(window).width()<360?3:7;
    
    function loadLSL(){
    	var windownWidth=$(window).width();
//         height=$(window).height()-158;
    	  height=getWindowHeight()-140;
         $("#runOffDetailPanel").height(height);
         var windownWidth=$(window).width();
    	 $("#runOffDetailPanel").css("width",windownWidth);
    }

    $(window).bind(resizeEvent,function(){
    	loadLSL();
    	dismissObj.resize();
    });
    loadLSL();
    var quarterLastDay = $('#quarterLastDay').val();

    $('.tabPanel div[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    	  var targetAreaId = $(e.target).attr('aria-controls');
    	if(targetAreaId=="ydqsPanel"){
    		trendChartObj.resize();
    	}else if(targetAreaId=="xjdbPanel"){
    		inferiorChart.resize();
    	}else if(targetAreaId=="wdfxPanel"){
    		accordDismissPieObj.resize();
    	}else if(targetAreaId=="xxmdPanel"){
    		tableGrid.resize();
    	}
    });

    /**
     * 主动流失率
     * @type 
     */
    var dismissObj = {
        chartId: 'LSLDismissChart',
        chartObj: null,
        quarterCount: 0,
        chartOption: getDefaultGaugeOption("主动流失率"),
        init: function (organId) {
            var self = this;
            self.chartObj = initEChart(self.chartId);
            self.initData(organId);
        },
        initData: function (organId) {
            var self = this;
            self.organId = organId;
            $.ajax({
                url: urls.queryAccordDismissUrl,
                type: "post",
                async: false,
                data: {'organId': organId, 'prevQuarter': quarterLastDay},
                success: function (rs) {
                    var dismissTrend = rs.dismissTrend;
                	var date=rs.date;
              	  $('.page-top .top-chart-date').text(date);
              	
                    if (!$.isEmptyObject(dismissTrend)) {
                        self.dismissTrend = dismissTrend;
                        self.quarterCount = dismissTrend.count;

                        self.normal = rs.normal;
                        self.risk = rs.risk;
                        self.initChart(rs.normal, rs.risk, rs.max);
                    }else{
                    	  self.initChart(rs.normal, rs.risk, rs.max);
                    }
                }
            });
        },
        initChart: function (normal, risk, max) {
        	  var self = this;
              var normalPer = Tc.formatFloat(normal * 100), riskPer = Tc.formatFloat(risk * 100);
              self.chartOption.series[0].max = parseFloat((max * 100).toFixed(2));
              self.chartOption.series[0].axisLine.lineStyle.color[0] = [Tc.formatFloat(normal / max), '#61bb33'];
              self.chartOption.series[0].axisLine.lineStyle.color[1] = [Tc.formatFloat(risk / max), '#F1A502'];

              var dismissTrend = self.dismissTrend;

              var trendRate = dismissTrend.rate;
              var rate = Tc.formatFloat(trendRate * 100);
              self.chartOption.series[0].data[0].value = rate;
              
              
        	//定义文本信息
//            $('.page-top .top-chart-number').text(rate);
            $('.page-top .top-chart-icon-note-warn').text(normalPer + '% - ' + riskPer + '%');
            $('.page-top .top-chart-icon-note-over').text(riskPer + '% 以上');
            
            if (trendRate <= normal) {
            	self.chartOption.series[0].pointer.color='#61bb33';
                $('.page-top .top-chart-content').html('流失率正常,请继续保持!');
            } else if (normal < trendRate || trendRate <= risk) {
            	self.chartOption.series[0].pointer.color='#ffc000';
                $('.page-top .top-chart-content').html('流失率偏高,请持续关注!');
            } else {
            	self.chartOption.series[0].pointer.color='#ff3232';
                $('.page-top .top-chart-content').html('流失率超高,队伍状态异常!');
            }
          
            $('#' + self.chartId).gaugeAuxiliary({option:self.chartOption,
            	textColor:self.chartOption.series[0].pointer.color,
            	text:rate+"%"});
            self.chartObj.setOption(self.chartOption, true);
        },
        resize: function(){
            this.chartObj.resize();
        }
    };


    /**]
     * 月度趋势
     */
    /***
     * 主动流失率趋势
     * @type 
     */
    var trendChartObj = {
        chartId: 'ydqsChart',
        maxNum: 4,
        hasParent:(parentOrganId!=""),
        chartObj: null,
        chartOption: {
        	  calculable: false,
        	  animation:animation,
            legend: getDefaultLegend(reqOrganName),
            grid: defaultGrid,
            xAxis: [{
                type: 'category',
                splitLine: false,
                axisLine: false,
                axisTick: {
                    show: false
                },
                axisLabel: {
                    show: true,
                    itemStyle: {
                        color: '#BEBEBE'
                    },
                    formatter: function (value) {
                    	var v=value.substring(4,6);
                    	if(v.indexOf("0")==0){
                    		v=v.substring(1,2);
                    	}
                    	v+="月";
                        return v;
                    }
                },
                data: []
            }],
            yAxis: [{
                type: 'value',
                splitLine: splitLine,
                axisLine: {
                	show:false
                },axisLabel: {
                    show: true,
                    formatter: function (value) {
                        return value*100+"%";
                    }
                },
                splitNumber:4
            }],

            series: [{
                name: reqOrganName,
                type: 'bar',
                barWidth :barWidth,
                barCategoryGap: '45%',
                itemStyle: {
                    normal: {
                    	color: barColor,
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            },
                            formatter: function (i) {
                                return formatPercentage(i.value);
                            }
                        }
                    }
                },
                data: []
            }]
        },
        init: function (organId) {
            var self = this;
            self.chartObj = initEChart(self.chartId);
            self.initData(organId);
           
        },
        initData: function (organId) {
            var self = this;
            if (self.organId == organId) {
                return;
            }
            self.organId = organId;
            self.requestData(organId);
        },
        initChartData: function (data) {
            var self = this;
            var xAxisData = [], chainData = [], dismissRateData = [], parentRateData = [];

            var hasParent = self.hasParent;
            var timeArr=timeRange.split("-");
            var minDate="";
            var maxDate="";
            if(timeArr.length>0){
            	  minDate=timeArr[0];
                  maxDate=timeArr[0];
            }
            if(timeArr.length>1){
               maxDate=timeArr[1];
            }
            for(var i=data.length-self.maxNum;i<data.length;i++){
            	var obj=data[i];
            	xAxisData.push(obj.month);
                dismissRateData.push({value:obj.rate,num:obj.accordCount});
                if (hasParent) {
                    parentRateData.push(obj.parentRate);
                }
                if(i==data.length-self.maxNum){
                	minDate=obj.month;
                	minDate=minDate.substring(0,4)+"."+minDate.substring(4,6)+".01";
                }
            }
           $("#ydqsTjzq").text(minDate+"-"+maxDate);
           self.chartOption.xAxis[0].data = xAxisData;
           self.chartOption.series[0].data = dismissRateData;
            if (hasParent) {
            	self.chartOption.series.push({
            		itemStyle: {
                        normal: {
                        	color: lineColor,
                        }
                    },
                    name: parentOrganName,
                    type: 'line',
                    yAxisIndex: 0,
                    symbol: 'circle',
                    data: parentRateData
                }) ;
            	self.chartOption.legend.data.push(parentOrganName);
            } 
//            self.extendsOption = chartOption;
        },
        resize: function(){
            this.chartObj.resize();
        },
        requestData: function (organId) {
            var self = this;
            $.post(urls.getDismissTrendUrl, {'organId': organId, 'prevQuarter': quarterLastDay}, function (rs) {
                if (_.isEmpty(rs)) {
                    showEmptyTip($('#' + self.chartId));
                    return;
                }
                removeEmptyTip($('#' + self.chartId).parent());
                self.extendData(rs);
                self.chartObj.setOption(self.chartOption, true);
                $('#' + self.chartId).chartTooltip({chart:self.chartObj,formatter:function(i,v,p){ 
                	var cols=[{name:"流失人数",value:(v.data[0].value.num),unit:"人"}];
                	cols.push({name:"流失率",value:(v.data[0].value.value*100).toFixed(2),unit:"%"});
                	if(trendChartObj.hasParent){
                		cols.push({name:"上级组织流失率",value:(v.data[1].value*100).toFixed(2),unit:"%"});
                	}
                    return {title:v.name,cols:cols};
                }});
                
            
            });
        },
        extendData: function (source) {
            var self = this;
            var packData = source.current, parentData = source.parent;		//封装的数据
            if (!$.isEmptyObject(parentData)) {
                self.calculateRate(parentData);
            }
            //计算流失率
            self.calculateRate(packData);
            self.extendParentRate(packData, parentData);
            self.initChartData(packData);
       
        },calculateRate: function (data) {
            $.each(data, function (i, obj) {
                //计算当前季度流失率
            	//alert(obj.accordCount)
            	if(!_.isNull(obj.accordCount)&&!_.isNull(obj.monthCount)&&obj.monthCount!=0){
            		 obj.rate = Math.round(obj.accordCount / obj.monthCount * 100) / 100;
            	}else{
            		 obj.rate=0;
            	}
                obj.month=obj.yearMonth;
            });
        },
        extendParentRate: function (packData, parentData) {
        	var self=this;
            var hasParent = self.hasParent;
            $.each(packData, function (i, obj) {
                if (!hasParent) {
                    obj.parentRate = 0;
                    return true;
                }
                var bool = false;
                $.each(parentData, function (j, pObj) {
                    if (obj.month == pObj.month) {
                    	if(!_.isNull(pObj.rate)||pObj.rate!="null"){
                    		 obj.parentRate = pObj.rate;
                             bool = true;
                    	}
                       
                    }
                });
                if (!bool) obj.parentRate = 0;
            });
        }
    };
    
    
    /**
     * 下级对比
     */
    var inferiorChart = {
    	chartObj:null,
        chartId: 'inferiorChart',
        option:{
                calculable: false,
                animation:animation,
                grid: organGrid,
                xAxis: [{
                    type: 'value',
                    axisLabel: {
                        formatter: function (value) {
                            return value + '%';
                        }
                    },
                    axisLine: false,
                    splitLine: splitLine,
                   
                    axisTick: {
                        show: false
                    }
                }],
                yAxis: [{
                    type: 'category',
                    splitLine: false,
                    axisLine: {
                    	show:false,
                        lineStyle: {
                            color: '#D9D9D9'
                        }
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: '#333'
                        }
                    },
                    data: []
                }],
                series: [{
                    type: "bar",
                    barWidth :barWidth,
                    data: [],
                    itemStyle: {
                        normal: {
                            color: barColor,
                            label: {
                                show: true,
                                textStyle: {
                                    color: "rgb(34, 34, 34)"
                                },
                                formatter: function (a) {
                                    return a.value+"%";
                                }
                            }
                        }
                    }
                }]
            },
        init: function (organId) {
            this.chartObj = echarts.init(document.getElementById(this.chartId));
            this.initData(organId);
        },
        initData: function (organId) {
            var self = this;
            if (_.isEmpty(self.resultData)) {
                self.getRequestData(organId);
                return;
            }
            self.generateDetail();
        },resize:function(){
        	 this.chartObj.resize();
        },
        getRequestData: function (organId) {
            var self = this;
            $.ajax({
                url: urls.subDismissDataUrl,
                data:{organizationId: organId, date: quarterLastDay},
                success: function (data) {
                    self.resultData = data;
                    scalChartHeight(self,data.length);
                    
                    removeEmptyTip($('#' + self.chartId));
                    if (_.isEmpty(data) || data.length < 1) {
                        showEmptyTip($('#' + self.chartId));
                        return;
                    }
                    var yaxisData=[], seriesData=[];
                    $.each(data, function(n, v){
                        yaxisData.push(formatAxis(v.organName));
                        seriesData.push(v.rate);
                    });
                    self.option.yAxis[0].data=yaxisData;
                    self.option.series[0].data=seriesData;

                    self.chartObj.setOption(self.option);
                    $('#' + self.chartId).chartTooltip({chart:self.chartObj,formatter:function(index,v,p){
                    	var cols=[{name:v.name,value:(v.data[0].value).toFixed(2),unit:"%"}];

                        return {title:timeRange,cols:cols};
                    }});
                },
                error: function () {

                }
            });
        }
    };
    
    
    
    function getName(value){
    	switch(value){
		case "1":
		case "2":
		case "3":
		case "4":
		case "5":
		case "6":
		case "7":
		case "8":
			return value + '级';
		default:
			return value;
	}
    }
    /**
     * 
     */
    var accordDismissPieObj = {
        perfChartId: 'perfChart',
        abilityChartId: 'abilityChart',
        ageChartId: 'ageChart',
        option:{
            grid: defaultGrid,            
            xAxis: [{
                type: 'category',
                splitLine: false,
                axisLine: false,
                axisTick: {
                    show: false
                },
                axisLabel: {
                    show: true,
                    itemStyle: {
                        color: '#BEBEBE'
                    },formatter: function (value) {
                    	return getName(value);
                    }
                },
                data: []
            }],
            yAxis: [{
                type: 'value',
                splitLine: splitLine,
                axisLine: false,
                axisLabel: {
                    formatter: '{value}%'
                }
            }],
            series: [{
                type: 'bar',
                clickable: false,
                barCategoryGap: '45%',
                barWidth :barWidth,
                itemStyle: {
                    normal: {
                    	 color: barColor,
                        label: {
                            show: true,
                            textStyle: {
                                color: '#333'
                            },
                            formatter: function (a) {
                                return  a.value + '%';
                            }
                        }
                    }
                },
                data: [],
                markLine: {
                    clickable: false,
                    symbolSize: [0, 0],
                    itemStyle: {
                        normal: {
                            color: '#1AB394',
                            lineStyle: {
                                type: 'solid'
                            }
                        }
                    },
                    data: []
                }
            }]
        },
        init: function (organId) {
            var self = this;
//            var height = 320;
//            $('#' + self.perfChartId).height(height);
//            $('#' + self.abilityChartId).height(height);
//            $('#' + self.ageChartId).height(height);

            self.perfPieObj = echarts.init(document.getElementById(self.perfChartId));
            self.abilityPieObj = echarts.init(document.getElementById(self.abilityChartId));
            self.agePieObj = echarts.init(document.getElementById(self.ageChartId));
            self.perfOption = $.extend(true,{}, self.option);
            self.abilityOption = $.extend(true,{}, self.option);
            self.abilityOption.title={
            		show: true,
            		zlevel: 0,
            		z: 6,
            		text: '(目前只统计非管理人员)',
            		link: '',
            		target: null,
            		subtext: '',
            		sublink: '',
            		subtarget: null,
            		x: 'right',
            		y: 'top',
            		textStyle:{
            			fontFamily:fontFamily,
            			fontSize: 11,
            			fontWeight: 'normal',
            		}
            };
            
            self.ageOption =$.extend(true,{}, self.option);
            self.initData(organId);
        },
        initData: function (organId) {
            var self = this;
            if (self.organId != organId) {
                self.organId = organId;
                self.extendsData(organId);
                return;
            }
            var perfData = self.perfData;
            if (!$.isEmptyObject(perfData)) {
                
                self.initChart(self.perfPieObj,self.perfOption,perfData,self.perfChartId);
            }
            var abilityData = self.abilityData;
            if (!$.isEmptyObject(abilityData)) {
                self.initChart(self.abilityPieObj,self.abilityOption,abilityData,self.abilityChartId);

            }
            var companyAgeData = self.companyAgeData;
            if (!$.isEmptyObject(companyAgeData)) {
                self.initChart(self.agePieObj,self.ageOption, companyAgeData,self.ageChartId);
            }
        },
        extendsData: function (organId) {
            var self = this;
            $.post(urls.getDismissInfoUrl, {'organId': organId, 'prevQuarter': quarterLastDay}, function (rs) {
                if (_.isEmpty(rs.ability)) {
                    showEmptyTip($('#' + self.abilityChartId));
                } else {
                    removeEmptyTip($('#' + self.abilityChartId));
                }
                self.abilityData = rs.ability;
                if (_.isEmpty(rs.companyAge)) {
                    showEmptyTip($('#' + self.ageChartId));
                } else {
                    removeEmptyTip($('#' + self.ageChartId));
                }
                self.companyAgeData = rs.companyAge;
                if (_.isEmpty(rs.pref)) {
                    showEmptyTip($('#' + self.perfChartId));
                } else {
                    removeEmptyTip($('#' + self.perfChartId));
                }
                self.perfData = rs.pref;
                self.initData(organId);
            });
        },
        initChart: function (chartObj,option, resultData, chartId) {
        	chartObj.clear();
            var self = this;
            var xaxisData=[], seriesData=[];var runOffCountData=[];
            $.each(resultData, function(n, v){
                xaxisData.push(v.typeName);
                if(v.rate!=null){
                	seriesData.push((v.rate).toFixed(2));
                }else{
                	seriesData.push(0);
                }
                if(v.runOffCount==null){
                	v.runOffCount=0;
                }
                runOffCountData.push(v.runOffCount);
            });
            option.xAxis[0].data=xaxisData;
            option.series[0].name=runOffCountData;
            option.series[0].data=seriesData;
            chartObj.setOption(option, true);
            $('#' + chartId).chartTooltip({chart:chartObj,formatter:function(i,v,p){  
            	//console.log(v);
            	var cols=[{name:getName(v.name)+"流失人数",value:v.data[0].name,unit:"人"}];
            	cols.push({name:"流失占比",value:(v.data[0].value*1.0).toFixed(2),unit:"%"});
                return {title:timeRange,cols:cols};
            }});
            
        },resize:function(){
        	  var self=this;
        	  self.perfPieObj.resize();
              self.abilityPieObj.resize();
              self.agePieObj.resize();
        }
    };
    
    var fomartDate=function(date){
    	var arr=date.split(" ");
    	if(arr.length=2){
    		return arr[0];
    	}else{
    		return date;
    	}
    }
	var tableOption= {
			url: urls.queryRunOffDetailUrl,
            datatype: 'json',
//            postData: {keyName:'梁'},
            postData: {'organId': reqOrganId, 'prevQuarter': quarterLastDay},
            mtype: 'POST',
           // altRows: true,//设置表格行的不同底色
//			altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
            autowidth: true,
            height: 247,//268
//            height:height,
            colNames: [''],
            colModel: [
                {align: 'left',sortable: false, formatter: function(a,b,obj){
                	var positionName=obj.positionName;
                	if(positionName.length>splitNum){
                		positionName=positionName.substring(0,splitNum)+"...";
                	}
                	var result='<div class="memo-list">'+
//                	'<div class="memo-col-img">'+
//                	 	'<img class="ct-circle-header" src="'+obj.imgPath+'">'+
                	'<img class="memo-col-img" src="'+webRoot+'/assets/img/index/head4.jpg">'+
//                	'</div>'+
                	'<div class="memo-col-content" style="max-width:'+maxWidth+'px;">'+
                			'<div class="memo-list-detail">'+
                				'<span class="memo-list-name">'+
                				obj.userNameCh+
                				'</span>'+
                				'<span  class="memo-list-value">'+
                				positionName+
                				'</span>'+
                				'<span  class="memo-list-time">'+
                				"离职时间："+fomartDate(obj.roDate)+
                				'</span>'+
                			 '</div>'+
                             '<div class="memo-list-detail">'+
                              '离职原因:'+obj.whereAbouts+
                            '</div>'+
                	'</div>'+
                	'</div>'+
                '</div>';
                	return result;
                }}
            ],loadComplete:function(xhr){
            	var records=xhr.records;
            	if(records>0){
            		$("#runOffNum").text("离职"+records+"人（"+timeRange+"）");
            	}else{
            		$("#runOffNum").text("没有人离职（"+timeRange+"）");
            	}
            	tableGrid.scroll=xhr.total>1;
            },gridComplete:function(){
            	$(this).closest('.ui-jqgrid-view').find('div.ui-jqgrid-hdiv').hide();
            	
            	

            },
//            rownumWidth: 40,
//            rownumbers: true,
            rowHeight: 69,
            scroll: true
        };

	var tableGrid={
			gridId:"#runOffDetail",
			PanelId:"#runOffDetailPanel",
			tableOption:tableOption,
			scroll:true  ,
			init:function(){
				var self=this;
				$(self.gridId).jqGrid(self.tableOption);
				self.resize();
			},resize: function (data) {
	        	var self=this;
	        	if(this.scroll){
        			$(self.gridId).setGridWidth($(self.PanelId).width()+20);
        		}else{
        			$(self.gridId).setGridWidth($(self.PanelId).width());
        		}
	            $(self.gridId).setGridHeight($(self.PanelId).height());
	        }
	}
	tableGrid.init();
    dismissObj.init(reqOrganId);
    trendChartObj.init(reqOrganId);
    inferiorChart.init(reqOrganId);
    accordDismissPieObj.init(reqOrganId);
    $.screenZoom([
                 	dismissObj,
                 	trendChartObj,
                 	inferiorChart,
                 	accordDismissPieObj,
                 	tableGrid
                 ]);
    /***
     * 初始化echart
     * @param domId
     * @returns {*}
     */
    function initEChart(domId) {
        return echarts.init(document.getElementById(domId));
    }

   
    function formatPercentage(value) {
        if (!value) {
            return '-';
        }
        return value == 0 ? '-' : Math.round(value * 10000) / 100 + '%';
    }
});