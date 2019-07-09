require([ 'jquery', 'vue2x','compUtils', 'barComp' ], function($, Vue, compUtils, barComp) {
	var vm2 = new Vue({
		el : '.barCompId',
		components : {
			'bar-component' : barComp
		},
		data : function() {
			return {
				loading : true,
				list : [],
			}
		},
		methods : {
			render : function() {
				var _this = this;
				// TODO ajax
				var rs = {
					"4" : {
						"seriesData" : [ 22, 880, 23, 0, 0 ],
						"xAxisData" : [ "90后", "80后", "70后", "60后", "其他" ]
					},
				};
				setTimeout(function() {
					var _list = {};
					// 年龄
					_list.age = compUtils.dataPacket({
						xAxis : rs[4].xAxisData,
						data : rs[4].seriesData
					});
					_this.list = _list;
					_this.loading = false;
				}, 0);
			},
		},
		created : function() {
			this.render();
		},
	});
});