define([ 'jquery', 'vue2x', 'compUtils', 'underscore', 'echarts',
		'echarts/chart/bar' ], function($, Vue, compUtils, _, echarts) {
	function getCategoryAxis(data){
		return {
			type : 'category',
			axisLabel : {
				textStyle : {
					color : '#000'
				}
			},
			splitLine : false,
			axisTick : false,
			axisLine : false,
			z : 10,
			data : data==undefined?[]:data
		} ;
	}
	function getValueAxis(){
		return {
			axisLine : false,
			splitLine : false,
			axisLabel : false,
			type : 'value'
		} ;
	}
	return Vue.extend({
		template : '<div class="chart" ></div>',
		props : [ 'label', 'list','listen'],
		data : function() {
			return {
				init : null,
				chartObj : null,
				option : {
					calculable : false,
					grid : {
						borderWidth : 0,
					},
					xAxis : [ getCategoryAxis() ],
					yAxis : [ getValueAxis],
					series : [ {
						type : 'bar',
						clickable : false,
						barWidth : 30,
						itemStyle : {
							normal : {
								label : {
									show : true,
									textStyle : {
										color : 'black'
									},
//									position : 'top',
									formatter : ''
								}
							}
						},
						data : []
					} ]
				}
			}
		},
		mounted : function() {
			this.render();
		},
		watch:{
			'list' :function(val, oval){
				this.list=val;
				this.render();
			}
		},
		methods : {
			render : function() {
				this.$parent.loading= compUtils.isLoading(this.list);
				if(this.$parent.loading){
					if (this.chartObj) {
						this.chartObj.clear();
					}
					return;
				}
				var data= compUtils.analysisData(this.list);
				this.$parent.empty= _.isEmpty(data);
				if (this.$parent.empty) {
					if (this.chartObj) {
						this.chartObj.clear();
					}
					return;
				}
				if (!this.init) {
					this.chartObj = echarts.init(this.$el);
					if(!_.isEmpty(this.listen)){
				        var ecConfig = require('echarts/config');  
						this.option.series[0].clickable=true;
						this.chartObj.on(this.listen.type,this.listen.call);
					}else{
						this.option.series[0].clickable=false;
					}
					
					this.init = true;
				}
				if(!_.isEmpty(data.xAxis)){
					this.option.xAxis[0]=getCategoryAxis(data.xAxis);
					this.option.yAxis[0]=getValueAxis();
				}else if(!_.isEmpty(data.yAxis)){
					this.option.yAxis[0]=getCategoryAxis(data.yAxis);
					this.option.xAxis[0]=getValueAxis();
				}
				this.option.series[0].data = data.data;
				this.chartObj.clear();
				this.chartObj.setOption(this.option);
			}
		}
	});
});