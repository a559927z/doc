require(['jquery', 'vue2x', 'compUtils', 'lineComp'], function($, Vue, compUtils, lineComp){
	new Vue({
		el: '.lineCompId',
		data: function(){
			return {
				organId: '',
				list: []
			}
		},
		components: {
			'lineComponent': lineComp
		},
		watch: {
			'organId': function(){
				this.render();
			}
		},
		methods: {
			render: function(){
				var _this = this;
				//rs为获取数据
				var rs = {
					legend: {
						data: ['名称']
					},
					xAxis: {
						data: ['周一','周二','周三','周四','周五','周六','周日']
					},
					yAxis: {
						name: '度',
						axisLabel: {
							formatter: '{value} °C'
						}
					},
					series: {
						name: '名称',
						data: [15,22,23,25,19,20,22]
					}
				}
				setTimeout(function(){
					_this.list = compUtils.dataPacket(rs);
				}, 1000);
			},
			clickmethod: function(params){
				console.log(params);
			}
		},
		created: function(){
			this.render();
		}
	});
});