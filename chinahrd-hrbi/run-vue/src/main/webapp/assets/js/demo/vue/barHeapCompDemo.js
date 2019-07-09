require([ 'jquery', 'vue2x','compUtils', 'barHeapComp' ], 
		function($, Vue, compUtils, barHeapComp) {
	var vm2 = new Vue({
		el:'.barheapCompId',
		
		components:{
			'barheap-component': barHeapComp
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

				var list = {
	                    "title":"",
	                    "yAxis":['周一','周二','周三','周四','周五','周六','周日'],
	                    "data":[
	                        {
		                        "name": "A",
		                        "labelposition":"insideLeft",
		                        "data": [320, 302, 301, 334, 390, 330, 320]
	                        },
	                        {
		                        "name": "B",
		                        "labelposition":"insideLeft",
		                        "data": [320, 302, 301, 334, 390, 330, 320]
	                        },
	                        {
		                        "name": "C",
		                        "labelposition":"insideLeft",
		                        "data": [320, 302, 301, 334, 390, 330, 320]
	                        },
	                        {
		                        "name": "D",
		                        "labelposition":"insideLeft",
		                        "data": [320, 302, 301, 334, 390, 330, 320]
	                        },
	                        {
		                        "name": "E",
		                        "labelposition":"insideLeft",
		                        "data": [320, 302, 301, 334, 390, 330, 320]
	                        },],
	                };
				var _this = this;
				setTimeout(function(){
					_this.list = list;
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