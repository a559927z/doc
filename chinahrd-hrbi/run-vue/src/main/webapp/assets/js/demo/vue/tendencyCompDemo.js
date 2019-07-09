require([ 'jquery', 'vue2x','compUtils', 'tendencyComp'], function($, Vue, compUtils, tendencyComp) {
	var vm2 = new Vue({
		el:'.tendencyCompId',
		
		components:{
			'tendency-component': tendencyComp
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
	                    "data":{
	                    	"rise" : 2182,
	                    	"down" : 1876,
	                    	"equal" : 50223
	                    	  },
	                    "label":{
	                    	"rise" : "有所上涨",
	                    	"down" : "出现下滑",
	                    	"equal" :"维持现状"
	                    },
	                    "unit":"单位"
	                };
				var _this = this;
				setTimeout(function(){
					_this.list = tmplist.data;
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