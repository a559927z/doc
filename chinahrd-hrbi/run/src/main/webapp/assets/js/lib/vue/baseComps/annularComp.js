define(['jquery', 'vue2x', 'compUtils', 'underscore', 'echarts',
	'echarts/chart/pie'], function ($, Vue, compUtils, _, echarts) {

		return Vue.extend({
			template: '<div class="chart"></div>',
			props: ['label', 'list'],
			data: function () {
				return {
					init: null,
					chartObj: null,
					option: {
						calculable: false,
						legend: compUtils.legend,
						color: compUtils.colorPie,
						series: [{
							// name: 'xxx',
							clickable: false,
							type: 'pie',
							radius: ['40%', '60%'],
							center: ['45%', '50%'],
							data: []
						}]
					}
				}
			},
			watch:{
				'list' :function(val, oval){
					this.list=val;
					this.render();
				}
			},
			mounted: function () {
				this.render();
			},
			methods: {
				render: function () {
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
						this.init = true;
					}
					this.option.series[0].data = data.data;
					this.option.legend.data = this._legend(data.legend);
					this.chartObj.clear();
					this.chartObj.setOption(this.option);
				},
				_legend: function (data) {
					var array = [];
					_.each(data, function (item, index) {
						array.push({ name: item, icon: 'bar', width: '5px' })
					});
					return array;
				}
			}
		});
	});