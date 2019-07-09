require([ 'jquery', 'vue2x', 'compUtils', 'pieComp' ], function($, Vue, compUtils, pieComp) {
	var vm = new Vue({
		el : '.pieCompId',
		data : {
			organId : '',
		},
		watch : {
			'organId' : function() {
				this.render()
			}
		},
		components : {
			'pie-component' : pieComp
		},
		data : function() {
			return {
				list : [],
			}
		},
		methods : {
			render : function() {
				var _this = this;
				// TODO ajax
				var rs = {
					"0" : [ {
						"k" : "5级",
						"v" : "160"
					}, {
						"k" : "4级",
						"v" : "155"
					}, {
						"k" : "3级",
						"v" : "260"
					}, {
						"k" : "2级",
						"v" : "311"
					}, {
						"k" : "1级",
						"v" : "38"
					}, {
						"k" : "其他",
						"v" : "1"
					} ],
				};
				setTimeout(function() {
					var _list = {};
					// 职级
					var abilityLegend = [];
					var abilityData = [];
					$.each(rs[0], function(a, b) {
						abilityData.push({
							value : b.v,
							name : b.k
						});
						abilityLegend.push(b.k);
					});
					_list.ability = compUtils.dataPacket({
						data : abilityData,
						legend : abilityLegend
					})
					_this.list = _list;
				}, 1000);
			},
		},
		created : function() {
			this.render();
		},
	});
});