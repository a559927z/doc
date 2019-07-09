require(['jquery', 'echarts', 'echarts/chart/bar','echarts/chart/line', 'echarts/chart/pie','jgGrid',
    'underscore', 'utils',  "appBase","chartTooltip","datetime"], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var urls = {
    		queryChangeConfigUrl: webRoot + '/mobile/talentProfitLoss/queryChangeConfig.do',	//获取配置信息
            queryPopulationPieUrl: webRoot + '/mobile/talentProfitLoss/queryPopulationPie.do',	//人才损益-人员分
            queryTopDataUrl: webRoot + '/mobile/talentProfitLoss/getTopData.do',	//获取人才损益值
            queryPopulationPieUrl: webRoot + '/mobile/talentProfitLoss/queryPopulationPie.do',	//人才损益-人员分布
            queryTalentProfitLossSameDataUrl: webRoot + '/mobile/talentProfitLoss/queryTalentProfitLossSameData.do',           //同比 环比
    		queryInflowOutflowChangeTypeUrl: webRoot + '/mobile/talentProfitLoss/queryInflowOutflowChangeType.do',           //异动分析
    		 
    		queryChangeDetailUrl: webRoot + '/mobile/talentProfitLoss/queryChangeDetail.do',           //加班时长趋势-人均时长  总长
    };
    var ecConfig = require('echarts/config');
    var reqOrganId=$("#reqOrganId").val();
    var time=$("#time").val();
    var minTime=$("#minTime").val();
    var maxTime=$("#maxTime").val();
    var currTime=maxTime;
    var selectY=time.substring(0,4);
    var selectM=time.substring(4,6);
    formatDate(time);
    function loadPage(){
        height=getWindowHeight()-140;
        $("#outputDetailPanel").width($(window).width())
        $("#outputDetailPanel").height(height)
        $("#inputDetailPanel").width($(window).width())
        $("#inputDetailPanel").height(height)
    }
    loadPage();
    $(window).bind(resizeEvent,function(){
    	laborRatioObj.resize();
    	profiLossTrendObj.resize();
		changeAnalyzeObj.resize();
		tableGrid.resize();
    });
    
    var currTab="laborEfficiencyPanel";
    $('.tabPanel div[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    	  var targetAreaId = $(e.target).attr('aria-controls');
    	  var load = $(e.target).attr('load');
    	if(targetAreaId=="laborEfficiencyPanel"){
    		currTab="laborEfficiencyPanel";
    		populationObj.resize();
    	}else if(targetAreaId=="overtimePanel"){
    		currTab="overtimePanel";
    		if(load=="true"){
    			profiLossTrendObj.resize();
      		}else{
      			$(e.target).attr('load',"true");
      			profiLossTrendObj.init(reqOrganId,time);
      		}
    	}else if(targetAreaId=="attendanceTimePanel"){
    		currTab="attendanceTimePanel";
    		if(load=="true"){
    			changeAnalyzeObj.resize();
      		}else{
      			$(e.target).attr('load',"true");
      			changeAnalyzeObj.init(reqOrganId,time);
      		}
    		
    	}else if(targetAreaId=="attendanceDetailPanel"){
    		currTab="attendanceDetailPanel";
    		if(load=="true"){
    			inputTableGrid.resize();
    			outputTableGrid.resize();
      		}else{
      			$(e.target).attr('load',"true");
      			inputTableGrid.init("");
      			outputTableGrid.init("");
      		}
    	}
    });
    
    
    /*==============================================================*/
    /* TOP															*/
    /*==============================================================*/
    var topObj={
    		chartId:"top",
    	className:"top-content-panel",
    	init:function (organId,time) {
    		var self=this;
    		loading(self.chartId);
    		$.ajax({
    			url : urls.queryTopDataUrl,
    			data : {organId: organId,
    				time:time,
    				},
    			type : 'post',
    			success : function(data){
    				loaded(self.chartId);
    				self.loadData(data);
    			},
    			error : function(){}
    		});
    	},loadData:function(data){
    		var teamCards=$("."+this.className).children(".top-panel");
    		$.each(teamCards,function(i,item){
    			var span=$(item).children("span");
    			if(i==0){
    				$(span).text(data.rcsy)
    			}else if(i==1){
    				$(span).text(data.input)
    			}else if(i==2){
    				$(span).text(data.output)
    			}
    		});
    	}
    
    }
    
    
    /*==============================================================*/
    /* 人员分布														*/
    var populationObj = {
		chartId: 'populationChart',
        chartObj: null,
        option: getDefaultPieOption({name: "人员分布"}),
        init: function (organId, time) {
            var self = this;
            if(self.chartObj==null){
    			self.chartObj =  echarts.init(document.getElementById(self.chartId));
    		}
            self.initData(organId, time);
        },
        initData: function (organId, time) {
        	var self = this;
        	loading(self.chartId);
        	$.post(urls.queryPopulationPieUrl, {organId: organId,time: time}, function (data) {
        		loaded(self.chartId);
        		self.generateChart(data);
        	});
        },
        generateChart: function (data) {
            var self = this;
            var category = [];
            var barDataArr = [];
    		if (_.isEmpty(data)) {
    			 showEmptyTip($('#' + self.chartId));
            } else {
                self.option.series[0].data = data;
                self.chartObj.setOption(self.option, true);
                var legend=[],total=0;
        		$.each(data, function(i, item) {
        			legend.push('<div>'+
        					'<label style="background-color: '+ colorPie[i] + ';"></label>'+
        					'<span>'+ item.name + '</span>'+
        					'</div>');
    			});
    			appendLegend($("#" + self.chartId), legend);
                $('#' + self.chartId).chartTooltip({chart:self.chartObj,formatter:function(p){  
                	var cols = [  ];
    					$.each(data, function(i, item) {
    						cols.push({
    							name : item.name,
    							value : item.value+"人" ,
    							rate : "("+(item.rate).toFixed(2)+"%)"
    						});

    					});
    					return {
    						color:colorPie,
    						title : "",
    						note:"",
    						cols : cols
    					};            }});
            }
        },
        resize: function () {
            this.chartObj.resize();
        }
    };

    
    
    /**
     * 同比环比
     * */
    var profiLossTrendObj = {
    	chartId: "profiLossTrendChart",
    	btnId:"profiLossTrendBtn",
		chartObj: null,
		xAxisData:[],
		data:[],
		option: {
			animation:animation,
        	grid: defaultGrid,
	        xAxis: [{
	            type: 'category',
	            splitLine: false,
	            axisLine: false,
	            axisTick: false,
	            axisLabel: {
	                show: true,
	                itemStyle: {
	                    color: '#D7D7D7'
	                }
	            },
	            data: []
	        }],
	        yAxis: [{
	            type: 'value',
	            splitLine: splitLine,
	            axisLine: false,
	            axisLabel: {
	                formatter: '{value}'
	            }
	        }],
	        series: [{
	            type: 'bar',
	            barCategoryGap: '45%',
	            barWidth: barWidth,
	            itemStyle: {
	                normal: {
	                	color: barColor,
	                    label: {
	                        show: true,
	                        formatter: function(o){
	                        	return o.value;
	                        }
	                    }
	                }
	            },
	            data: []
	        },{
	            type: 'bar',
	            barCategoryGap: '45%',
	            barWidth: 8*barWidth/10,
	            itemStyle: {
	                normal: {
	                	color: colorPie[1],
	                    label: {
	                        show: true,
	                        formatter: function(o){
	                        	return o.value;
	                        }
	                    }
	                }
	            },
	            data: []
	        }],
		},
		init: function (organId, time) {
			var self = this;
			if(self.chartObj==null){
				self.chartObj =  echarts.init(document.getElementById(self.chartId));
			}
            self.getRequestData(organId, time);
		},
        getRequestData: function(organId, time) {
        	var self = this;
        	loading(self.chartId);
        	$.post(urls.queryTalentProfitLossSameDataUrl, 
        			{organId: organId, time: time},
        		function (data) {
        			loaded(self.chartId);
        			if (_.isEmpty(data)) {
                     	showEmptyTip($('#' + self.chartId));
                     }else{
                    	 self.packData(data); 
                     }
        			self.clickTab(0);
        			// 添加点击事件  
        			self.clickBtn();
        	});
        },packData:function(data){
        	 	var self = this;
        	 	self.packXAxisAndLenged(data.curDate);
        	 	self.data[0]=data.curConNum;
        	 	self.data[1]=data.oldConNum;
        },packXAxisAndLenged:function(data){
    	 	var self = this;
    	 	self.xAxisData[0]=data;
    	 	self.xAxisData[1]=[];
    	 	var minYear=0;
    	 	var maxYear=0;
    	 	$.each(data,function(i,o){
    	 		var year=o.substring(0,4);
    	 		if(i==0){
    	 			maxYear=year;
    	 			minYear=year;
    	 		}
    	 		if(maxYear<year){
    	 			maxYear=year;
    	 		}
    	 		if(minYear>year){
    	 			minYear=year;
    	 		}
    	 		self.xAxisData[1].push(o.substring(4,6));
    	 	});
    	 	var legendC=maxYear+"";
    	 	if(maxYear>minYear){
    	 		legendC=minYear+"-"+maxYear;
    	 	}
    	 	var legendP=(maxYear-1)+"";
    	 	if(maxYear>minYear){
    	 		legendP=(minYear-1)+"-"+(maxYear-1);
    	 	}
    	 	self.legend=[legendP,legendC];
       },clickBtn:function(){
        	var self=this;
            $("#"+self.btnId).children().unbind(clickEvent).on(clickEvent,function(){
       		 var _t=this;
       		 	 self.clickTab($(this).attr("data"));
            	 setBtnClass(_t);
             });

        },clickTab:function(type){
        	 var self = this;
        		  var tempOption = $.extend(true, {}, self.option);
        		  tempOption.xAxis[0].data = self.xAxisData[type];
        		  var _barWidth=barWidth;
        		 if(type==0){
        			 tempOption.series[0].data = self.data[0];
        			 tempOption.series.splice(1, 1);
        		 }else{
        			 tempOption.legend=getDefaultLegend(self.legend);
        			 
        			 tempOption.series[0].barWidth= tempOption.series[1].barWidth;
        			 tempOption.series[0].name=self.legend[0];
        			 tempOption.series[1].name=self.legend[1];
        			 tempOption.series[0].data = self.data[1];
        			 tempOption.series[1].data = self.data[0];
        			 _barWidth=2*8*barWidth/10+4;
        		 }
        		 if($(window).width()<360&&self.xAxisData[type].length>=6){
        			 tempOption.xAxis[0].axisLabel.rotate=30;
                  }
                 self.chartObj.setOption(tempOption, true);
                 $('#' + self.chartId).chartTooltip({chart:self.chartObj,
                	 barWidth:_barWidth,
                	 formatter:function(i,v,p){ 
                 	if(type==0){
                 		//环比
                 		var cols=[{name:v.name,value:v.data[0].value,unit:"人"}];
                 		 return {title:"趋势环比",cols:cols};
                 	}else{
                 		var cols=[{name:v.data[0].name+v.name,value:v.data[0].value,unit:"人"}];
                 		cols.push({name:v.data[1].name+v.name,value:v.data[1].value,unit:"人"});
                 		 return {title:"趋势同比",cols:cols};
                 	}
                	 
                 	
                    
                 }});
        },
        resize: function () {
        	this.chartObj.resize();
        }
    };
    
    
    
    /**
     * 异动分析
     * */
    var changeAnalyzeObj = {
		chartId : 'changeAnalyzeChart',
		chartObj : null,
    	option : getDefaultPieOption({name: "异动分析", toroidal: true}),
		init : function(organId,time){
    		var self = this;
    		if(self.chartObj==null){
    			self.chartObj =  echarts.init(document.getElementById(self.chartId));
    		}
    		self.getRequestDataFun(organId,time);
    	},
    	getRequestDataFun: function(organId,time){
    		var self = this;
    		var param = {
    			organId : organId,
    			time : time
    		};
    		loading(self.chartId);
    		$.ajax({
    			url : urls.queryInflowOutflowChangeTypeUrl,
    			data : param,
    			type : 'post',
    			success : function(data){
    				loaded(self.chartId);
    				self.getOption(data);
    			},
    			error : function(){}
    		});
    	},
    	getOption: function(data){
			var self = this;
			if(data.list && data.list.length > 0){
				self.option.series[0].data = data.list;
				self.chartObj.setOption(self.option);
				var legend=[];
				$.each(data.list, function(i, item) {
					legend.push('<div>'+
					'<label style="background-color: '+ colorPie[i] + ';"></label>'+
					'<span>'+ item.name + '</span>'+
					'</div>');
				});
				appendLegend($("#" + this.chartId), legend);
				$('#' + self.chartId).chartTooltip({chart:self.chartObj,formatter:function(p){  
	            	var cols = [  ];
						$.each(data.list, function(i, item) {
							cols.push({
								name : item.name,
								value : item.value+"人" ,
								rate : "("+item.rate.toFixed(2)+"%)"
							});

						});

						return {
							color:colorPie,
							title : "",
							note:"",
							cols : cols
						};            }});
			}else{
				showEmptyTip($('#' + self.chartId));
			}
		},
        resize: function () {
        	this.chartObj.resize();
        }
    }
    
    
    var tableOption= {
			url: urls.queryChangeDetailUrl,
            datatype: 'json',
            mtype: 'POST',
            autowidth: true,
            colNames: ['<span class="grid-th">姓名</span>', 
                       '<span class="grid-th">异动类型</span>',
                       '<span class="grid-th">异动日期</span>', 
                       ],
            colModel: [
                       {
                           name: 'userName',
                           index: 'userName',
                           sortable: false,
                           align: 'center',
                           formatter: function (value, options, row) {
                               if (_.isEmpty(value)) {
                                   return "";
                               }
                               return "<span class='grid-td-high'>"+value+"<span>";
                           }
                       },
                       {name: 'changeTypeName', index: 'changeTypeName', sortable: false, align: 'center',
                    	   formatter: function (value, options, row) {
                               if (_.isEmpty(value)) {
                                   return "";
                               }
                               return "<span class='grid-td'>"+value+"<span>";
                           }},
                       {name: 'changeDate', index: 'changeDate', sortable: false, align: 'center',
                    	   formatter: function (value, options, row) {
                    		   if (_.isEmpty(value)) {
                                   return "";
                               }
                           return "<span class='grid-td'>"+value+"<span>";
                       }}
                ],loadComplete:function(xhr){
                },
                scroll: true
        };

	var inputTableGrid={
			gridId:"#inputDetail",
			PanelId:"#inputDetailPanel",
			option:$.extend(true, {}, tableOption),
			scroll:true,
			load:false,
			init:function(type){
				var self=this;
				if(!this.load){
					this.load=true;
					self.option.postData={'parentType':'input',
		    				'organId': reqOrganId,
		    				'time':time,type:type};
					$(self.gridId).jqGrid(self.option);
				}else{
					$(self.gridId).clearGridData().setGridParam({
		    			postData: {'parentType':'input',
		    				'organId': reqOrganId,
		    				'time':time,type:type}
		    		}).trigger("reloadGrid");
				}
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
	var outputTableGrid={
			gridId:"#outputDetail",
			PanelId:"#outputDetailPanel",
			option:$.extend(true, {}, tableOption),
			scroll:true,
			load:false,
			init:function(type){
				var self=this;
				if(!this.load){
					this.load=true;
					self.option.postData={'parentType':'output',
		    				'organId': reqOrganId,
		    				'time':time,type:type};
					$(self.gridId).jqGrid(self.option);
				}else{
					$(self.gridId).clearGridData().setGridParam({
		    			postData: {'parentType':'output',
		    				'organId': reqOrganId,
		    				'time':time,type:type}
		    		}).trigger("reloadGrid");
				}
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
    /*==============================================================*/
    /* 页面初始化													*/
    /*==============================================================*/
	$.post(urls.queryChangeConfigUrl, 
	function (data) {
		 $.each(data.input,function(i,o){
    		 var clazz="button";
    		 var option='<span class="'+clazz+'" data="'+o.code+'">'+o.name+'</span>';
    		 $("#inputBtn").append(option);
    	 });
		  $("#inputBtn").children().on(clickEvent,function(){
	       		 var _t=this;
	       		inputTableGrid.init($(this).attr("data"));
	            	 setBtnClass(_t);
	             });
		 $.each(data.output,function(i,o){
    		 var clazz="button";
    		 var option='<span class="'+clazz+'" data="'+o.code+'">'+o.name+'</span>';
    		 $("#outputBtn").append(option);
    	 });
		  $("#outputBtn").children().on(clickEvent,function(){
	       		 var _t=this;
	       		outputTableGrid.init($(this).attr("data"));
	            	 setBtnClass(_t);
	             });
		  loadData(time);
	});
	$(".config_icon").on(clickEvent,function(){
		$(".datetime_shade").show();
		$(".datetime_panel").show(200);
		$("#datetime2").datetime({
	        type: 'ym',//date,time,diy
	        date:  new Date(currTime),
	        minDate : new Date('2013-05-03'),
	        maxDate: new Date(maxTime),
	        onChange: function (data) {
	        	selectY=data.y;
	        	selectM=data.m;
	        }
	    });
		
	});
	
	$(".ui_datetime_btn").on(clickEvent,function(){
		$(".datetime_shade").hide();
		$(".datetime_panel").hide();
		currTime=selectY+"-"+selectM;
    	var temp=selectY+""+selectM;
    	if(time!=temp){
    		formatDate(temp);
    		loadData(temp);
    		$.each($('.tabPanel div[data-toggle="tab"]'),function(){
    			  var targetAreaId = $(this).attr('aria-controls');
    	    	if(targetAreaId=="overtimePanel"){
    	    		if(currTab==targetAreaId){
    	    			profiLossTrendObj.init(reqOrganId,temp);
    	    		}else{
    	    			$(this).attr('load',false);
    	    		}
    	    		
    	    	}else if(targetAreaId=="attendanceTimePanel"){
    	    		if(currTab==targetAreaId){
    	    			changeAnalyzeObj.init(reqOrganId,temp);
    	    		}else{
    	    			$(this).attr('load',false);
    	    		}
    	    	}else if(targetAreaId=="attendanceDetailPanel"){
    	    		if(currTab==targetAreaId){
    	    			time=temp;
    	    			inputTableGrid.init();
    	    			outputTableGrid.init();
    	    		}else{
    	    			$(this).attr('load',false);
    	    		}
    	    	}
    		});
    	}
    	time=temp;
	});
	loadData(time);
	function loadData(_time){
		topObj.init(reqOrganId,_time);
		populationObj.init(reqOrganId,_time);
	}
	
	function formatDate(time){
		$("#time_val").text(time.substring(0,4)+"年"+time.substring(4,6)+"月");
	}
    //laborRatioObj.init(reqOrganId);
//    $.screenZoom([
//               	constellatoryObj,
//               	bloodObj,
//               	sexObj,
//               	marryObj,
//               	ageObj,
//               	personalityObj
//              ]);
	
});