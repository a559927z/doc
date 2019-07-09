define(['jquery', 'vue2x', 'compUtils', 'underscore', 'echarts', 'echarts/chart/line', 'echarts/chart/bar'], 
		function($, Vue, compUtils, _, echarts){
	return Vue.extend({
		template: '<div></div>',
		props: ['list'],
		data: function(){
			return {
				init: false,
				chartObj: null,
				option: {
					grid: {
			            borderWidth: 0
			        },
			        legend: {
				    	y: 'bottom',
				        data:[]
				    },
			        tooltip: {
			        	trigger: 'axis',
			        	axisPointer : {
			        		type : 'line',
			        		lineStyle : {
			        			color : '#999999',
			        			type : 'dashed',
			        			width : 1
			        		}
			        	}
			        },
			        xAxis: [{
			            type: 'category',
			            splitLine: false,
			            axisLine: {
			                lineStyle: {
			                	width: 1,
			                    color: '#666666'
			                }
			            },
			            axisTick: {
			                show: true,
			                lineStyle: {
			                    color: '#666666'
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
			                    color: '#e4e4e4'
			                }
			            },
			            axisLine: {
			                lineStyle: {
			                	width: 1,
			                    color: '#666666'
			                }
			            }
			        }],
			        series: [
			            {
			                type: 'bar',
			                clickable: true,
			                data: [],
			                barMaxWidth: 30,
			                itemStyle: {
			                    normal: {
			                        color: compUtils.colorPie[0],
			                        label: {
			                        	show: true,
                                        textStyle: {
                                            color: '#444444'
                                        }
			                        }
			                    },
			                    emphasis: {
			                    	label: {
			                            show: true,
			                            textStyle: {
                                            color: '#444444'
                                        }
			                        }
			                    }
			                },
			            },
			            {
			            	type: 'bar',
			            	clickable: true,
			            	data: [],
			            	barMaxWidth: 30,
			            	itemStyle: {
			            		normal: {
			            			color: compUtils.colorPie[1],
			            			label: {
			            				show: true,
			            				textStyle: {
			            					color: '#444444'
			            				}
			            			}
			            		},
			            		emphasis: {
			            			label: {
			            				show: true,
			            				textStyle: {
			            					color: '#444444'
			            				}
			            			}
			            		}
			            	},
			            },
			            {
			                type: 'line',
			                smooth: true,
			                clickable: true,
			                symbol: 'circle',
			                symbolSize: 0,
			                markPoint: {
			                    data: [],
			                    symbolSize: 16,
			                    itemStyle: {
			                        normal: {
			                            color: compUtils.colorLine[0],
			                        }
			                    },
			                },
			                itemStyle: {
			                    normal: {
			                        color: compUtils.colorLine[0],
			                        label: {
			                            show: false,
			                            textStyle: {
                                            color: '#444444'
                                        }
			                        }
			                    }
			                },
			                data: []
	
			            },
			            {
			            	type: 'line',
			            	smooth: true,
			            	clickable: true,
			            	symbol: 'circle',
			            	symbolSize: 0,
			            	markPoint: {
			            		data: [],
			            		symbolSize: 16,
			            		itemStyle: {
			            			normal: {
			            				color: compUtils.colorLine[1],
			            			}
			            		},
			            	},
			            	itemStyle: {
			            		normal: {
			            			color: compUtils.colorLine[1],
			            			label: {
			            				show: false,
			            				textStyle: {
			            					color: '#444444'
			            				}
			            			}
			            		}
			            	},
			            	data: []
			            	
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
		mounted: function() {
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
				var unit = '', seriesName0 = '', seriesName1 = '', seriesName2 = '', seriesName3 = '';
				//定义legend
				if(this._isFined(data.legend)){
					this.option.legend = $.extend(true, {}, this.option.legend, data.legend);
					seriesName0 = this._isFined(data.legend.data) ? data.legend.data[0] : '';
					seriesName1 = this._isFined(data.legend.data) ? data.legend.data[1] : '';
					seriesName2 = this._isFined(data.legend.data) ? data.legend.data[2] : '';
					seriesName3 = this._isFined(data.legend.data) ? data.legend.data[3] : '';
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
					if(this._isFined(seriesName0)){
						this.option.series[0].name = seriesName0;
					}
					if(this._isFined(seriesName1)){
						this.option.series[1].name = seriesName1;
					}
					if(this._isFined(seriesName2)){
						this.option.series[2].name = seriesName2;
					}
					if(this._isFined(seriesName3)){
						this.option.series[3].name = seriesName3;
					}
					this.option.series[0] = $.extend(true, {}, this.option.series[0], data.series[0]);
					this.option.series[1] = $.extend(true, {}, this.option.series[1], data.series[1]);
					this.option.series[2] = $.extend(true, {}, this.option.series[2], data.series[2]);
					this.option.series[3] = $.extend(true, {}, this.option.series[3], data.series[3]);
				}
				//定义悬浮窗
				this.option.tooltip.formatter = function(param){
	        		var div;
	        		if(param[0].value == '-'){
	        			div = '<div>' + param[0].name + '</div>'
		            	 	+ '<div>' + param[2].seriesName + ' : ' + param[2].value + unit + '</div>'
		        			+ '<div>' + param[3].seriesName + ' : ' + param[3].value + unit + '</div>';
	        		} else {
	        			div = '<div>' + param[0].name + '</div>'
		        			+ '<div>' + param[0].seriesName + ' : ' + param[0].value + unit + '</div>'
		        			+ '<div>' + param[1].seriesName + ' : ' + param[1].value + unit + '</div>';
	        		}
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