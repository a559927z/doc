define([ 'jquery', 'vue2x', 'compUtils', 'underscore', 'echarts',
         'echarts/chart/gauge' ], function($, Vue, compUtils, _, echarts) {

	return Vue.extend({
		template : '<div class="chart" ></div>',
		props : [ 'label', 'list','listen'],
		data : function() {
			return {
				init : null,
				chartObj : null,
				option : {
		            toolbox: {
		                show: false
		            },
		            series: [
		                {
		                    name: "编制",
		                    type: "gauge",
		                    data: [
		                        {
		                            value: 0,
		                            name: "使用率"
		                        }
		                    ],
		                    min: 0,
		                    max: 100,
		                    axisLine: {
		                        lineStyle: {
		                            color: [[0.95, "rgb(106, 175, 43)"], [1, "rgb(240, 166, 4)"], [1, "rgb(211, 82, 26)"]],
		                            width: 10
		                        },
		                        show: true
		                    },
		                    title: {
		                        show: false
		                    },
		                    detail: {
		                        show: false
		                    },
		                    splitNumber: 5,
		                    pointer: {
		                        length: "80%",
		                        width: 5
		                    },
		                    radius: "90%",
		                    center: ["50%", "55%"],
		                    axisTick: {
		                        show: true,
		                        splitNumber: 2,
		                        length: 4
		                    },
		                    splitLine: {
		                        lineStyle: {
		                            width: 1
		                        },
		                        show: true,
		                        length: 10
		                    }
		                }
		            ],
		            tooltip: {
		                show: false
		            }
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
				 this.option.series[0].data[0].value = data.value;
				 this.option.series[0].max = data.max;
		           
		            this.option.series[0].axisLine.lineStyle.color = data.color;
		            this.chartObj.clear();
				this.chartObj.setOption(this.option);
			}
		}
	});
});