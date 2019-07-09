require(['jquery', 'vue2x', 'compUtils', 'lineAndBarOneComp'], 
		function($, Vue, compUtils, lineAndBarOneComp){
	new Vue({
		el: '.lineAndBarOneCompId',
		data: function(){
			return {
				organId: '',
				list: []
			}
		},
		components: {
			'lineBarOneComponent': lineAndBarOneComp
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
						data: ['累计投入', '项目投入']
					},
					xAxis: {
						data: ['累计投入', '周一','周二','周三','周四','周五','周六','周日']
					},
					yAxis: {
						name: '万元',
//						axisLabel: {
//							formatter: '{value} W'
//						}
					},
					series: [
						{	
							clickable: false,
							data: [100,'-','-','-','-','-','-','-']
						},         
						{
							data: ['-', 15,22,23,25,19,20,22]
						}         
					]
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