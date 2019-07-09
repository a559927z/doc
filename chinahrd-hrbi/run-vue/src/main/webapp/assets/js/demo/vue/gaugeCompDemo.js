require([ 'jquery', 'vue2x','compUtils', 'gaugeComp'], function($, Vue, compUtils, gaugeComp) {
	var vm2 = new Vue({
		el:'.gaugeCompId',
		
		components:{
			'gauge-component': gaugeComp
		},
		data: function(){
			return{
				list:[],
				loading: true,
			}
		},
		computed:{
			listentomychild: function(){
				return {
					type: "click",
					call: this.listen,
				}
			}
		},
		methods:{
			render: function(){

				var tmplist = {
						'data':[{
							'name':'主动流失率',
							'value':'4.8',
						}]
	                };
				var _this = this;
				setTimeout(function(){
					_this.list = tmplist;
					_this.loading = false;
				},1000);
			},
			//子组件调用父级的call
			listen: function(msg){
				console.log(msg);
			}
		},
		created: function(){
			this.render();
		}
	});
});