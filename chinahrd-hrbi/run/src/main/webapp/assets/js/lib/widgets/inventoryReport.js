/**
 * 说明：
 * $('#id').inventoryRepost({
 * 		isClick : false,	//默认false, 表示默认点击事件， true 表示外部没有点击事件，需要调用该点击事件，防止冒泡
 * 		empId : null,	//传入所需参数, 参数中必须有empId
 * 		compareClickCallback : function(param){},	//点击“对比”
 * 		talentKeyClickCallback : function(param){}	//	点击“关键人才”
 * })
 * */
require([ 'jquery', 'echarts', 'echarts/chart/line', 'echarts/chart/scatter',
          'bootstrap', 'messenger', 'keyTalentsAdd' ], function($, echarts) {
	var webRoot = G_WEB_ROOT;
	var win = top != window ? top.window : window;
	//初始化'加入对比'
    var stackObject;
    function loadStack() {
        if (win.stackObject) {
            stackObject = win.stackObject;
        }
        //假如有对比人员则显示，没有则隐藏
        if (stackObject && stackObject.bottomStock('getPersonIds') != '') stackObject.bottomStock('hideFrame', 0);
        else stackObject.bottomStock('hideFrame', 1);
    }

    loadStack();
	$.fn.inventoryReport = function(options){
		var _defaults = $.fn.inventoryReport.defaults;
		var opt = $.extend({}, _defaults, options || {});
		var report = function(element, options){
			this.init(element, options);
		}
		var modalDiv = '<div class="modal fade topWindow popup-modal" id="inventoryReportModal"'
				+ 'tabindex="-1" role="dialog" aria-labelledby="inventoryReportModalLabel" aria-hidden="true">'
				+ '<div class="modal-dialog"><div class="modal-content"><div class="modal-header">'
				+ '<div class="modal-header-text" id="inventoryReportModalLabel">盘点报告</div>'
				+ '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button></div>'
				+ '<div class="modal-body"><div class="row ct-row top-div"><div class="col-xs-1">'
				+ '<div class="col-div" id="topDivImg"></div>'
				+ '<div class="col-div btn-div">'
				+ '<div class="right-btn-f" id="keyTalentId">＋关键人才</div>'
				+ '</div><div class="col-div btn-div">'
				+ '<div class="right-btn-s" id="compareId">＋对比</div></div></div>'
				+ '<div class="col-xs-3"><div class="border">'
				+ '<div class="top-title">基本信息</div>'
				+ '<div class="top-content"><div class="top-content-head">'
				+ '<span>姓名：</span><span class="top-content-th" id="baseName"></span>'
				+ '<img class="top-base-img" id="baseImgId" src="' + webRoot + '/assets/img/talent/talent_px.png"></div>'
				+ '<div class="top-content-head">'
				+ '<span>岗位：</span><span class="top-content-th" id="basePositionName"></span></div>'
				+ '<div class="top-content-head">'
				+ '<span>职级：</span><span class="top-content-th" id="baseRankName"></span></div></div></div></div>'
				+ '<div class="col-xs-3"><div class="border">'
				+ '<div class="top-title">离职风险</div>'
				+ '<div class="top-content"><div class="top-content-head">'
				+ '<span>离职风险：</span><span id="baseRiskFlag"><i class="circle-img {StaffRisk}"></i></span>'
				+ '<img class="top-risk-img" id="riskImgId" src="' + webRoot + '/assets/img/talent/pleft.png"></div>'
				+ '<div class="top-content-head">'
				+ '<span>BP备注：</span><span class="top-content-th" id="baseNote"></span></div></div></div></div>'
				+ '<div class="col-xs-5"><div class="border">'
				+ '<div class="top-title">标签信息</div>'
				+ '<div class="top-content"><div class="top-content-head tag-div-style">'
				+ '<span class="left">优势标签</span><div class="left flag-content" id="baseAdvantageTag"></div></div>'
				+ '<div class="top-content-head tag-div-style">'
				+ '<span class="left">劣势标签</span><div class="left flag-content" id="baseInferiorityTag"></div></div>'
				+ '<div class="top-content-head tag-div-style">'
				+ '<span class="left">激励要素</span><div class="left flag-content" id="baseEncourage"></div></div></div></div></div></div>'
				+ '<div class="row ct-row content-div">'
				+ '<div class="col-xs-6">'
				+ '<div class="index-common-title bottom-title">'
				+ '<div class="index-common-title-left bottom-left"></div>'
				+ '<div class="index-common-title-text bottom-text">人才地图轨迹</div></div>'
				+ '<div class="bottom-div-one">'
				+ '<div class="col-xs-12" id="talentMapTrajectoryChart"></div></div></div>'
				+ '<div class="col-xs-6">'
				+ '<div class="index-common-title bottom-title">'
				+ '<div class="index-common-title-left bottom-left"></div>'
				+ '<div class="index-common-title-text bottom-text">绩效轨迹</div></div>'
				+ '<div class="bottom-div-one">'
				+ '<div class="col-xs-12" id="performanceTrajectoryChart"></div></div></div></div>'
				+ '<div class="row ct-row content-div">'
				+ '<div class="col-xs-6">'
				+ '<div class="index-common-title bottom-title">'
				+ '<div class="index-common-title-left bottom-left"></div>'
				+ '<div class="index-common-title-text bottom-text">能力轨迹</div></div>'
				+ '<div class="bottom-div-one">'
				+ '<div class="col-xs-12" id="abilityTrajectoryChart"></div></div></div>'
				+ '<div class="col-xs-6">'
				+ '<div class="index-common-title bottom-title">'
				+ '<div class="index-common-title-left bottom-left"></div>'
				+ '<div class="index-common-title-text bottom-text">异动轨迹</div></div>'
				+ '<div class="bottom-div-one">'
				+ '<div class="col-xs-12" id="changeTrajectoryChart"></div>'
				+ '</div></div></div></div></div></div></div>';
		
		var option1 = {
			grid : {
				x : 60,
				x2 : 50,
				y : 35,
				borderWidth : 0
			},
			calculable : false,
			xAxis : [ {
				type : 'category',
				axisLine : {
					lineStyle : {
						type : 'solid',
						color : '#000',
						width : 2
					}
				},
				axisTick : {
					show : false
				},
				axisLabel : {
					interval : 0
				},
				splitLine : {
					show : false
				},
				data : []
			} ],
			yAxis : [ {
				type : 'value',
				min : 0,
				axisLine : {
					lineStyle : {
						type : 'solid',
						color : '#000',
						width : 2
					}
				},
				axisTick : {
					show : false
				},
				axisLabel : {
					interval : 0
				}
			} ],
			series : [ {
				type : 'line',
				data : [],
				clickable : false,
				symbol : 'circle',
				symbolSize : 6,
				itemStyle : {
					normal : {
						color : '#019AEF',
						label : {
							show : false
						},
						lineStyle : {
							type : 'dashed'
						}
					}
				}
			} ]
		}
		var option2 = {
			grid : {
				x : 60,
				x2 : 50,
				y : 35,
				borderWidth : 0
			},
			calculable : false,
			xAxis : [ {
				type : 'category',
				axisLine : {
					lineStyle : {
						type : 'solid',
						color : '#000',
						width : 2
					}
				},
				axisTick : {
					show : false
				},
				axisLabel : {
					interval : 0
				},
				splitLine : {
					show : false
				},
				data : []
			} ],
			yAxis : [ {
				type : 'value',
				min : 0,
				axisLine : {
					lineStyle : {
						type : 'solid',
						color : '#000',
						width : 2
					}
				},
				axisTick : {
					show : false
				},
				axisLabel : {
					interval : 0
				}
			} ],
			series : [ {
				type : 'line',
				data : [],
				clickable : false,
				symbol : 'circle',
				symbolSize : 6,
				itemStyle : {
					normal : {
						color : '#019AEF',
						label : {
							show : false
						},
						lineStyle : {
							type : 'solid'
						}
					}
				}
			} ]
				
		}
		var option3 = {
			grid : {
				x : 60,
				x2 : 50,
				y : 35,
				borderWidth : 0
			},
			calculable : false,
			xAxis : [ {
				type : 'category',
				axisLine : {
					lineStyle : {
						type : 'solid',
						color : '#000',
						width : 2
					}
				},
				axisTick : {
					show : false
				},
				axisLabel : {
					interval : 0
				},
				splitLine : {
					show : false
				},
				data : []
			} ],
			yAxis : [ {
				type : 'value',
				min : 0,
				axisLine : {
					lineStyle : {
						type : 'solid',
						color : '#000',
						width : 2
					}
				},
				axisTick : {
					show : false
				},
				axisLabel : {
					interval : 0
				}
			} ],
			series : [ {
				type : 'line',
				clickable : false,
				symbol : 'circle',
				symbolSize : 6,
				data : [],
				itemStyle : {
					normal : {
						color : '#019AEF',
						label : {
							show : true,
							formatter : function(params) {
								return params.data.name;
//								return params.data.name + '\n' + params.data.abilityName;
							},
							position : 'top'
						},
						lineStyle : {
							type : 'solid'
						}
					}
				}
			} ]
		}
		$(document.body).append(modalDiv);
		// 获取参数
		function getParam(){
			var empId = _defaults.empId;
			if(isFined(options.empId)){
				empId = options.empId;
			}
			var date = _defaults.date;
			var param = {
				empId : empId,
				date : date,
				rows : _defaults.rows
			}
			return param;
		}
		// 人才地图轨迹
		var talentMapTrajectory = {
			chartId : 'talentMapTrajectoryChart',
			chart: echarts.init(document.getElementById('talentMapTrajectoryChart')),
			option : option1,
			init : function(){
				var _this = this;
				clearChart(_this.chart);
				_this.getRequestData();
			},
			getRequestData : function(){
				var _this = this;
				loadingChart(_this.chartId);
				var url = _defaults.talentMapTrajectoryUrl;
				if(options.talentMapTrajectoryUrl){
					url = options.talentMapTrajectoryUrl;
				}
				$.ajax({
					url : url,
					data : getParam(),
					type : 'post',
					success : function(data){
						_this.getOption(data);
					},
					error : function(){}
				});
			},
			getOption : function(data){
				var _this = this;
				if(data && data.xData != undefined && data.xData.length > 0){
					hideChart(_this.chartId);
	    			_this.option.xAxis[0].name = '时间';
	    			_this.option.yAxis[0].name = '地图位置';
	    			_this.option.xAxis[0].data = data.xData;
	    			_this.option.yAxis[0].splitNumber = data.yVal.length;
	    			_this.option.yAxis[0].axisLabel.formatter = function(value) {
						for(var i=0;i<data.yVal.length;i++){
							if(value == data.yVal[i]){
								return data.yData[i];
							}
						}
					}
	    			_this.option.series[0].data = data.data;
	    			changeXAxisLabelRotate(_this.option, data.xData);
	    			_this.setOption();
	    		} else {
	    			hideChart(_this.chartId, true);
	    		}
			},
			setOption : function(){
				var _this = this;
				_this.chart.setOption(_this.option);
				resizeChart(_this.chart);
			}
		}
		//绩效轨迹
		var performanceTrajectory = {
			chartId : 'performanceTrajectoryChart',
			chart: echarts.init(document.getElementById('performanceTrajectoryChart')),
			option: option2,
			init : function(){
				var _this = this;
				clearChart(_this.chart);
				_this.getRequestData();
			},
			getRequestData : function(){
				var _this = this;
				loadingChart(_this.chartId);
				var url = _defaults.performanceTrajectoryUrl;
				if(options.performanceTrajectoryUrl){
					url = options.performanceTrajectoryUrl;
				}
				$.ajax({
					url : url,
					data : getParam(),
					type : 'post',
					success : function(data){
						_this.getOption(data);
					},
					error : function(){}
				});
			},
			getOption : function(data){
				var _this = this;
				if(data && data.xData != undefined && data.xData.length > 0){
					hideChart(_this.chartId);
					_this.option.xAxis[0].name = '时间';
	    			_this.option.yAxis[0].name = '绩效';
					_this.option.xAxis[0].data = data.xData;
					_this.option.yAxis[0].splitNumber = data.yVal.length;
	    			_this.option.yAxis[0].axisLabel.formatter = function(value) {
						for(var i=0;i<data.yVal.length;i++){
							if(value == data.yVal[i]){
								return data.yData[i];
							}
						}
					}
	    			_this.option.series[0].data = data.data;
	    			changeXAxisLabelRotate(_this.option, data.xData);
	    			_this.setOption();
	    		} else {
	    			hideChart(_this.chartId, true);
	    		}
			},
			setOption : function(){
				var _this = this;
				_this.chart.setOption(_this.option);
				resizeChart(_this.chart);
			}
		}
		//能力轨迹
		var abilityTrajectory = {
			chartId : 'abilityTrajectoryChart',
			chart: echarts.init(document.getElementById('abilityTrajectoryChart')),
			option: option2,
			init : function(){
				var _this = this;
				clearChart(_this.chart);
				_this.getRequestData();
			},
			getRequestData : function(){
				var _this = this;
				loadingChart(_this.chartId);
				var url = _defaults.abilityTrajectoryUrl;
				if(options.abilityTrajectoryUrl){
					url = options.abilityTrajectoryUrl;
				}
				$.ajax({
					url : url,
					data : getParam(),
					type : 'post',
					success : function(data){
						_this.getOption(data);
					},
					error : function(){}
				});
			},
			getOption : function(data){
				var _this = this;
				if(data && data.xData != undefined && data.xData.length > 0){
					hideChart(_this.chartId);
					_this.option.xAxis[0].name = '时间';
	    			_this.option.yAxis[0].name = '能力';
					_this.option.xAxis[0].data = data.xData;
					_this.option.yAxis[0].splitNumber = data.yVal.length;
	    			_this.option.yAxis[0].axisLabel.formatter = function(value) {
						for(var i=0;i<data.yVal.length;i++){
							if(value == data.yVal[i]){
								return data.yData[i];
							}
						}
					}
	    			_this.option.series[0].data = data.data;
	    			changeXAxisLabelRotate(_this.option, data.xData);
	    			_this.setOption();
	    		} else {
	    			hideChart(_this.chartId, true);
	    		}
			},
			setOption : function(){
				var _this = this;
				_this.chart.setOption(_this.option);
				resizeChart(_this.chart);
			}
		}
		//异动轨迹
		var changeTrajectory = {
			chartId : 'changeTrajectoryChart',
			chart: echarts.init(document.getElementById('changeTrajectoryChart')),
			option: option3,
			init : function(){
				var _this = this;
				clearChart(_this.chart);
				_this.getRequestData();
			},
			getRequestData : function(){
				var _this = this;
				loadingChart(_this.chartId);
				var url = _defaults.changeTrajectoryUrl;
				if(options.changeTrajectoryUrl){
					url = options.changeTrajectoryUrl;
				}
				$.ajax({
					url : url,
					data : getParam(),
					type : 'post',
					success : function(data){
						_this.getOption(data);
					},
					error : function(){}
				});
			},
			getOption : function(data){
				var _this = this;
				if(data && data.xData != undefined && data.xData.length > 0){
					hideChart(_this.chartId);
					_this.option.xAxis[0].name = '时间';
	    			_this.option.yAxis[0].name = '职级';
					_this.option.xAxis[0].data = data.xData;
					_this.option.yAxis[0].splitNumber = data.yVal.length;
	    			_this.option.yAxis[0].axisLabel.formatter = function(value) {
						for(var i=0;i<data.yVal.length;i++){
							if(value == data.yVal[i]){
								return data.yData[i];
							}
						}
					}
	    			_this.option.series[0].data = data.data;
	    			changeXAxisLabelRotate(_this.option, data.xData);
	    			_this.setOption();
	    		} else {
	    			hideChart(_this.chartId, true);
	    		}
			},
			setOption : function(){
				var _this = this;
				_this.chart.setOption(_this.option);
				resizeChart(_this.chart);
			}
		}
		var initChart = function(chartId){
			echarts.init(document.getElementById(chartId));
//			echarts.init(document.getElementById('talentMapTrajectoryChart'));
		}
		/**
		 * 清空面板
		 * */
		var clearChart =  function(chart){
			if(chart){
				chart.clear();
			}
		}
		/**
		 * 重置面板
		 * */
		var resizeChart = function(chart){
			chart.resize();
		}
	    /**
	     * 设置x轴标签显示方向
	     * @param option echart对象
	     * @param xAxisLabel x轴标签
	     */
	    function changeXAxisLabelRotate(option, xAxisLabel){
	    	if(xAxisLabel.length > 4){
				option.xAxis[0].axisLabel.rotate = 30;
			}else{
				option.xAxis[0].axisLabel.rotate = 0;
			}
	    }
	    
		report.prototype = {
			modalId : '#inventoryReportModal',
			imgDivId: '#topDivImg',
			baseImgId: '#baseImgId',
			baseNameId : '#baseName',
			basePositionNameId : '#basePositionName',
			baseRankNameId : '#baseRankName',
			baseRiskFlagId : '#baseRiskFlag',
			riskImgId: '#riskImgId',
			baseNoteId: '#baseNote',
			keyTalentId: '#keyTalentId',
			compareId: '#compareId',
			baseAdvantageTagId: '#baseAdvantageTag',
			baseInferiorityTagId: '#baseInferiorityTag',
			baseEncourageId: '#baseEncourage',
			staffRisk: 0, //1红 2黄 2绿 0灰
			addKeyTalent : '#keyTalentId',
			init : function(element, options){
				$.extend(this, options);
				this.el = element;
				this.options = options;
				this._defaults = _defaults;
				this._inClickFun();
			},
			_inClickFun: function(){
				var _this = this;
				var flag = false;
				var _isClick = _this.options.isClick;
				if(_isClick != undefined){
					flag = _isClick;
				}
				if(flag){
					$(_this.el).click(function(){
						_this._modalFun();
					});
				} else {
					_this._modalFun();
				}
			},
			_modalFun: function(){
				var _this = this;
				$(_this.modalId).modal('show');
				$(_this.modalId).on('shown.bs.modal', function() {
					_this._baseInfoRequestData();
					_this._getMinMaxDate();
				});
				$(_this.modalId).on('hidden.bs.modal', function() {
					$(_this.modalId).off();
				});
			},
			_getMinMaxDate: function(){
				var _this = this;
				var url = _this._defaults.getMinMaxDateUrl;
				$.ajax({
					url : url,
					type : 'post',
					dataType : 'json',
					success : function(data){
						_this._defaults.date = data.date;
						talentMapTrajectory.init();
						performanceTrajectory.init();
						abilityTrajectory.init();
						changeTrajectory.init();
					},
					error : function(){}
				});
			},
			_clearData: function(){
				var _this = this;
				$(_this.baseNameId).html('');
				$(_this.basePositionNameId).html('');
				$(_this.baseRankNameId).html('');
				$(_this.baseRiskFlagId).html('').html('<i class="circle-img {StaffRisk}"></i>');
				$(_this.baseNoteId).html('');
				$(_this.baseAdvantageTagId).empty();
				$(_this.baseInferiorityTagId).empty();
				$(_this.baseEncourageId).empty();
			},
			_baseInfoRequestData: function(){
				var _this = this;
				_this._clearData();
				var empId = _this._defaults.empId;
				if(isFined(_this.options.empId)){
					empId = _this.options.empId;
				}
				var param = {
					empId : empId
				}
				var url = _this._defaults.baseInfoUrl;
				if(isFined(_this.options.baseInfoUrl)){
					url = _this.options.baseInfoUrl;
				}
				$.ajax({
					url : url,
					data : param,
					type : 'post',
					success : function(data){
						_this._baseInfoLoadData(data);
					},
					error : function(){}
				});
			},
			_baseInfoLoadData: function(datas){
				var _this = this;
				var data;
				$.each(datas, function(ind, obj){
					data = obj;
				});
				if(data == null) return;
				var risk = "";
				_this.staffRisk = data.riskFlag;
				switch (_this.staffRisk) {
				case 1:
					risk = "backgroundred";
					break;
				case 2:
					risk = "backgroundyellow";
					break;
				case 3:
					risk = "backgroundgreen";
					break;
				case 0:
					risk = "backgroundgray";
					break;
				default:
					"";
				break;
				}
				_this.empName = data.userName;
				_this._isKeyTalentClick(data.isKeyTalent, data.runOffDate);
				$(_this.baseNameId).html(data.userName);
				$(_this.basePositionNameId).html(data.positionName);
				$(_this.baseRankNameId).html(data.rankName);
				var html = $(_this.baseRiskFlagId).html().replace('{StaffRisk}', risk);
				$(_this.baseRiskFlagId).html(html);
				$(_this.baseNoteId).html(data.note);
//				$(_this.baseAdvantageTagId).empty();
//				$(_this.baseInferiorityTagId).empty();
//				$(_this.baseEncourageId).empty();
				_this._isShowTag(data);
				_this._leftImgLoad(data);
				_this._keyTalentClick();
				_this._compareClick();
			},
			_isKeyTalentClick: function(isKeyTalent, runOffDate){
				var _this = this;
				if(isKeyTalent == 0 && (runOffDate == '' || runOffDate == null || runOffDate == 'null')){
					$(_this.addKeyTalent).removeClass('hide');
				} else {
					$(_this.addKeyTalent).addClass('hide');
				}
			},
			_isShowTag: function(data){
				var _this = this;
				if(data.isKeyTalent != 0 && data.runOffDate != '' && data.runOffDate != null && data.runOffDate != 'null'){
					if(isFined(data.advantageTag)){
						var advArr = data.advantageTag.split(',');
						var advDiv = "";
						$.each(advArr, function(ind, obj){
							advDiv += "<div class='left adv-style'>" + obj + "</div>";
						});
						$(_this.baseAdvantageTagId).append(advDiv);
					}
					if(isFined(data.inferiorityTag)){
						var infArr = data.inferiorityTag.split(',');
						var infDiv = "";
						$.each(infArr, function(ind, obj){
							infDiv += "<div class='left inf-style'>" + obj + "</div>";
						});
						$(_this.baseInferiorityTagId).append(infDiv);
					}
					if(isFined(data.encourage)){
						var encArr = data.encourage.split(',');
						var encDiv = "";
						$.each(encArr, function(ind, obj){
							encDiv += obj + "&nbsp;&nbsp;";
//						encDiv += "<div class='left enc-style'>" + obj + "</div>";
						});
						$(_this.baseEncourageId).append(encDiv);
					}
				}
			},
			_keyTalentClick: function(){
				var _this = this;
				$(_this.keyTalentId).unbind('click').bind('click', function(){
					var empId = _this._defaults.empId;
					if(isFined(_this.options.empId)){
						empId = _this.options.empId;
					}
					var param = {
						empId : empId,
						empName : _this.empName
					}
					var _callback = _this._defaults.talentKeyClickCallback;
					var _optCallback = _this.options.talentKeyClickCallback;
					if(isFined(_optCallback)){
						_callback = _optCallback;
					}
					if(_this._isFunction(_callback)){
						_callback(param);
					}
				});
			},
			_compareClick: function(){
				var _this = this;
				$(_this.compareId).unbind('click').bind('click', function(){
					var empId = _this._defaults.empId;
					if(isFined(_this.options.empId)){
						empId = _this.options.empId;
					}
					var param = {
						empId : empId
					}
					var _callback = _this._defaults.compareClickCallback;
					var _optCallback = _this.options.compareClickCallback;
					if(isFined(_optCallback)){
						_callback = _optCallback;
					}
					if(_this._isFunction(_callback)){
						_callback(param);
					}
				});
			},
			_leftImgLoad: function(data){
				var _this = this;
				$(_this.imgDivId).empty();
				var img = data.imgPath != '' && data.imgPath != null ? data.imgPath : webRoot + "/assets/photo.jpg";
		    	var html = '<img class="right-img" src="' + img + '" data-src="' + img + '" data-id="' + data.empId + '" >';
		    	$(_this.imgDivId).append(html);
		    	_this._leftImgClickFun();
		    	_this._baseImgLoad(data);
		    	_this._riskImgLoad(data);
			},
			_baseImgLoad: function(data){
				var _this = this;
				$(_this.baseImgId).unbind('click').bind('click', function(){
					_this._imgToRedirectTDV(data.empId);
				});
			},
			_riskImgLoad: function(data){
				var _this = this;
				$(_this.riskImgId).unbind('click').bind('click', function(){
					_this._imgToRedirectKTV(data.empId);
				});
			},
			_leftImgClickFun : function(){
				var _this = this;
				$(_this.imgDivId).find('.right-img').unbind('click').bind('click', function(){
					var id = $(this).attr('data-id');
					_this._imgToRedirectTDV(id);
				});
			},
			_imgToRedirectTDV: function(id){
				var _this = this;
				var _href = _this._defaults.baseImgHref;
				var optionHref = _this.options.baseImgHref;
				if(optionHref){
					_href = optionHref;
				}
				_href = _href + '?empId=' + id + '&rand=' + Math.random();
				window.open(_href);
			},
			_imgToRedirectKTV: function(id){
				var _this = this;
				var _href = _this._defaults.riskImgHref;
				var optionHref = _this.options.riskImgHref;
				if(optionHref){
					_href = optionHref;
				}
				_href = _href + '?empId=' + id + '&rand=' + Math.random();
				win.setlocationUrl(_href);
			},
			_isFunction : function(callback){
				return typeof callback == 'function';
			}
		}
		function isFined(param){
			if(param != undefined && param != null && param != ''){
				return true;
			} else {
				return false;
			}
		}
		return new report(this, opt);
	}
	$.fn.inventoryReport.defaults = {
		baseImgHref : webRoot + '/talentProfile/toTalentDetailView.do',	//跳转进入人才剖像
		riskImgHref : webRoot + '/keyTalents/toKeyTalentsView',	//跳转进入关键人才库
		getMinMaxDateUrl : webRoot + '/talentMaps/getMinMaxDate.do',	//人员信息
		baseInfoUrl : webRoot + '/talentMaps/queryInventoryReportPersonInfo.do',	//人员信息
		talentMapTrajectoryUrl : webRoot + '/talentMaps/queryTalentMapTrajectory.do',	//人才地图轨迹
		performanceTrajectoryUrl : webRoot + '/talentMaps/queryPerformanceTrajectory.do',	//绩效轨迹
		abilityTrajectoryUrl : webRoot + '/talentMaps/queryAbilityTrajectory.do',	//能力轨迹
		changeTrajectoryUrl : webRoot + '/talentMaps/queryChangeTrajectory.do',	//异动轨迹
		empId : null,
		date : null,
		rows : 3,
		compareClickCallback : function(param){
			if (stackObject) {
                stackObject.bottomStock('hideFrame', 0);
                stackObject.bottomStock('addPerson', param.empId);
            } else {
                showErrMsg("stackObject object is not exists!");
            }
		},
		talentKeyClickCallback : function(param){
			$(this).keyTalentsAdd({
				empId : param.empId,
				empName : param.empName,
				isKeyTalentCallback : function(param){
					if(param.isKeyTalent){
						$('#keyTalentId').addClass('hide');
					}
				}
			});
		}
	}
	//提示
	function showErrMsg(content) {
        $._messengerDefaults = {
            extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top messenger-on-right'
        };
        Messenger().post({
            message: content,
            type: 'error',
            hideAfter: 3
        });
    }
	//隐藏加载/暂无数据
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
	//数据加载
    function loadingChart(chartId) {
        var $chart = $("#" + chartId);
        $chart.children('div.loadingmessage').remove();
        $chart.children().hide();
        $chart.prepend("<div class='loadingmessage'>数据加载中</div>");
    }
})