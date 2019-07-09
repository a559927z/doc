define([ 'jquery', 'vue2x', 'compUtils', 'underscore', 'echarts',
		'echarts/chart/line' ], function($, Vue, compUtils, _, echarts) {
	return Vue.extend({
		template: '<div></div>',
		props: ['list'],
		data: function(){
			return {
				init: false,
				chartObj: null,
				option: {
					tooltip : {
				        trigger: 'axis',
				        axisPointer : {
				            type : 'line',
				            lineStyle : {
				            	type: 'dashed',
				            	width: 1,
				            	color: '#999999'
				            }
				        }
				    },
				    legend: {
				    	y: 'bottom',
				        data:[]
				    },
				    grid : {
						borderWidth : 0
					},
				    calculable : false,
				    xAxis : [
				        {
				            type : 'category',
				            boundaryGap : false,
				            splitLine : false,
							axisLine : {
								show : true,
								onZero : false,
								lineStyle : {
									color : '#666666',
									width : 1
								}
							},
							axisTick : {
								show : false,
							},
							axisLabel : {
								show : true,
								itemStyle : {
									color : '#666666'
								}
							},
				            data : []
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            nameTextStyle: {
				            	fontFamily: 'Microsoft YaHei',
				                color: '#444444'
				            },
							splitNumber: 5,
							axisLine : {
								show : true,
								lineStyle : {
									color : '#666666',
									width : 1
								}
							},
							axisLabel: {
				                formatter: '{value}'
				            }
				        }
				    ],
				    series : [
				        {
				            type:'line',
				            clickable: true,
				            itemStyle: {
			                    normal: {
			                        color: compUtils.colorLine[0],
			                        label: {
			                            show: true
			                        }
			                    }
			                },
				            data:[]
				        }
				    ]
				}
			}
		},
		watch: {
			'list': function(val, oldVal){
				this.list = val;
				this.render();
			}
		},
		mounted: function(){
			this.render();
		},
		methods: {
			render: function(){
				var _this = this;
				this.$parent.loading = compUtils.isLoading(this.list);
				if(this.$parent.loading){
					if(this.chartObj){
						this.chartObj.clear();
					}
					return;
				}
				var data = compUtils.analysisData(this.list);
				this.$parent.empty = _.isEmpty(data);
				if(this.$parent.empty){
					if(this.chartObj){
						this.chartObj.clear();
					}
					return;
				}
				if(!this.init){
					this.chartObj = echarts.init(this.$el);
					this.init = true;
				}
				var unit = '', yName = '';
				//定义legend
				if(this._isFined(data.legend)){
					this.option.legend = $.extend(true, {}, this.option.legend, data.legend);
					yName = this._isFined(data.legend.data) ? data.legend.data[0] : '';
				}
				//定义grid
				if(this._isFined(data.grid)){
					this.option.grid = $.extend(true, {}, this.option.grid, data.grid);
				}
				//定义xAxis
				if(this._isFined(data.xAxis)){
					this.option.xAxis[0] = $.extend(true, {}, this.option.xAxis[0], data.xAxis);
				}
				//定义yAxis
				if(this._isFined(data.yAxis)){
					this.option.yAxis[0] = $.extend(true, {}, this.option.yAxis[0], data.yAxis);
					unit = this._isFined(data.yAxis.name) ? data.yAxis.name : '';
				}
				//定义series
				if(this._isFined(data.series)){
					if(this._isFined(yName)){
						this.option.series[0].name = yName;
					}
					this.option.series[0] = $.extend(true, {}, this.option.series[0], data.series);
				}
				//定义悬浮窗
				this.option.tooltip.formatter = function(param){
					var div = '<div>' + param[0].name + '</div>'
					+ '<div>' + param[0].seriesName + ': ' + param[0].value + unit + '</div>';
					return div;
				}
				if(this._isFined(data.tooltip)){
					this.option.tooltip = $.extend(true, {}, this.option.tooltip, data.tooltip);
				}
				this.chartObj.clear();
				this.chartObj.setOption(this.option);
				if(this.$parent.clickmethod){
					this.chartObj.on('click', function(params){
						_this.$emit('clickfun', params);
					});
				}
			},
			_isFined: function(obj){
				return obj != null && obj != '' && obj != undefined;
			}
		}
	});
});