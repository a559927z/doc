require([ 'jquery', 'vue2x', 'compUtils', 'topBlockComp', 'totalInfoComp', 'utils' ], function($, Vue, compUtils, topBlockComp, totalInfoComp) {
	
	new Vue({
		el : '#topBlockCompId1',
		components : {
			'top-block-component' : topBlockComp,
			'total-info-component' : totalInfoComp,
		},
	});
	
	new Vue({
		el : '#topBlockCompId2',
		components : {
			'top-block-component' : topBlockComp,
			'total-info-component' : totalInfoComp,
		},
		data : function() {
			return {
				loading : true,
				data : {},
			}
		},
		methods : {
			render : function() {
				// TODO ajax
				var rs = {nowVal: 16.25, lastText:'较上月', lastVal:329.89};
				this.data = rs;
			},
		},
		created : function() {
			 this.render();
		},
	});
	
	new Vue({
		el : '#topBlockCompId3',
		components : {
			'top-block-component' : topBlockComp,
			'total-info-component' : totalInfoComp,
		},
		data : function() {
			return {
				loading : true,
				data : {},
			}
		},
		watch : {},
		methods : {
			budgetEvent:function(params){
				this.getRequestData(params);
			},
			accumulativeEvent:function(params){
				this.getRequestData(params);
			},
			getRequestData:function(params){
				// TODO ajax
				var rs = {};
				if(params==1){
					rs = {
						nowVal:30865.62,
						lastText:'较上一年薪酬总额', lastVal:9.04
					};
				}else{
					rs = {
						nowVal:29127.67,
						lastText:'较上一年同期', lastVal:9.03
					};
				}
				this.data = rs;
				if(!( _.isEmpty(this.data))) {
					this.render();
				}
			},
			render : function() {
				// TODO...
			},
		},
		created : function() {
			 this.getRequestData(1);
		},
		mounted : function() {
			$("#salarytotalTab span").on("click", function() {
				$(this).parent().find("span").removeClass("select");
				$(this).addClass("select");
//					$("#salarytotalContent .content").addClass("hide");
//					$("#" + $(this).data("id")).removeClass("hide");
			});
		}
	});
			
});