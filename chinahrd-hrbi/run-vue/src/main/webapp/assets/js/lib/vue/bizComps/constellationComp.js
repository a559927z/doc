define(['jquery', 'vue2x','compUtils','underscore', 'echarts', 'echarts/chart/bar'],
function ($, Vue,compUtils, _, echarts) {

		var webRoot = G_WEB_ROOT;
		return Vue.extend({
			template: '<div class="chart"></div>',
			props: ['label', 'list'],
			data: function () {
				return {
					init: null,
					chartObj: null,
					option: {
						calculable: false,
						grid: {
							borderWidth: 0,
							x: 20,
							x2: 20,
							y: 80,
							y2: 60
						},
						xAxis: [
							{
								type: 'category',
								show: false,
								data: []
							}
						],
						yAxis: [
							{
								type: 'value',
								show: false
							}
						],
						series: [
							{
								name: '',
								type: 'bar',
								clickable: false,
								itemStyle: {
									normal: {
										color: function (params) {
											var colorList = [
												'#848ADC', '#A07E67', '#F92D3B', '#C7B043', '#8CBE45',
												'#797A7D', '#E98AC9', '#893DB2', '#6EAB90', '#5FD384',
												'#49ACEC', '#E2A43E'
											];
											return colorList[params.dataIndex]
										},
										label: {
											show: true,
											position: 'top',
											formatter: function (i) {
												return i.name + "\n" + i.value;
											}
										}
									}
								},
								data: [],
								markPoint: {
									tooltip: {
										trigger: 'item',
										backgroundColor: 'rgba(0,0,0,0)',
										formatter: function (params) {
											return '<img src="'
												+ params.data.symbol.replace('image://', '')
												+ '">';
										}
									},
									clickable: false,
									data: [
										{
											xAxis: 0,
											y: 345,
											name: '白羊座',
											symbolSize: 20,
											symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-1.png'
										},
										{
											xAxis: 1,
											y: 345,
											name: '金牛座',
											symbolSize: 20,
											symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-2.png'
										},
										{
											xAxis: 2,
											y: 345,
											name: '双子座',
											symbolSize: 20,
											symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-3.png'
										},
										{
											xAxis: 3,
											y: 345,
											name: '巨蟹座',
											symbolSize: 20,
											symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-4.png'
										},
										{
											xAxis: 4,
											y: 345,
											name: '狮子座',
											symbolSize: 20,
											symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-5.png'
										},
										{
											xAxis: 5,
											y: 345,
											name: '处女座',
											symbolSize: 20,
											symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-6.png'
										},
										{
											xAxis: 6,
											y: 345,
											name: '天秤座',
											symbolSize: 20,
											symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-7.png'
										},
										{
											xAxis: 7,
											y: 345,
											name: '天蝎座',
											symbolSize: 20,
											symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-8.png'
										},
										{
											xAxis: 8,
											y: 345,
											name: '射手座',
											symbolSize: 20,
											symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-11.png'
										},
										{
											xAxis: 9,
											y: 345,
											name: '摩羯座',
											symbolSize: 20,
											symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-10.png'
										},
										{
											xAxis: 10,
											y: 345,
											name: '水瓶座',
											symbolSize: 20,
											symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-9.png'
										},
										{
											xAxis: 11,
											y: 345,
											name: '双鱼座',
											symbolSize: 20,
											symbol: 'image://' + webRoot + '/assets/img/manage/teamImg-star-12.png'
										}
									]
								}
							}
						]
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
					this.option.xAxis[0].data = data.xAxis;
					this.option.series[0].data = data.data;
					this.chartObj.clear();
					this.chartObj.setOption(this.option);
				}
			}
		});
	});