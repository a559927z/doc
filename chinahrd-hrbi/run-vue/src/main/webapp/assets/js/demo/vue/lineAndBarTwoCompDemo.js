require(['jquery', 'vue2x', 'compUtils', 'lineAndBarTwoComp'], 
		function($, Vue, compUtils, lineAndBarTwoComp){
	new Vue({
		el: '.lineAndBarTwoCompId',
		data: function(){
			return {
				organId: '',
				list: []
			}
		},
		components: {
			'lineBarTwoComponent': lineAndBarTwoComp
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
						data: ['累计投入', '累计产出', '项目投入', '项目产出']
					},
					xAxis: {
						data: ['累计', '周一','周二','周三','周四','周五','周六','周日']
					},
					yAxis: {
						name: '万元'
					},
					series: [
						{	
							clickable: false,
							data: [100,'-','-','-','-','-','-','-']
						},         
						{	
							clickable: false,
							data: [120,'-','-','-','-','-','-','-']
						},         
						{
							clickable: false,
							data: ['-', 15,22,23,25,19,20,22]
						},         
						{
							clickable: false,
							data: ['-', 22,26,28,35,24,40,32]
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