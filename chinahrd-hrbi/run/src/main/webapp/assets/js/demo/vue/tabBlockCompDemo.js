require([ 'jquery', 'vue2x', 'compUtils', 'tabBlockComp', 'utils' ], function($, Vue, compUtils, tabBlockComp) {
	
	
	new Vue({
		el : '.tabBlockCompId',
		components : {
			'tab-block-component' : tabBlockComp,
		},
		data : function() {
			return {
				loading : true,
				data : {},
			}
		},
		watch : {},
		methods : {
			getRequestData:function(params){
			},
			render : function() {
				// TODO...
			},
		},
		created : function() {
//			 this.getRequestData(1);
		},
		mounted : function() {
		}
	});
			
});