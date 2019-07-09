require(['jquery', 'vue2x','compUtils', 'annularComp'], function ($, Vue, compUtils, annularComp) {
	var vm = new Vue({
		el: '.annularCompId',
		components: {
			'annular-component': annularComp
		},
		data: function () {
			return {
				loading: true,
				list: [],
			}
		},
		methods: {
			render: function () {
				var _this = this;
				// TODO ajax
				var rs = {
					"8": [{
						"name": "影响型",
						"value": "871"
					}, {
						"name": "稳健型",
						"value": "30"
					}, {
						"name": "支配型",
						"value": "14"
					}, {
						"name": "谨慎型",
						"value": "10"
					}, {
						"name": "其他",
						"value": "0"
					}]
				};
				setTimeout(function () {
					var _list = {};
					// 性格
					var personalityLegend = [];
					var personalityData = [];
					$.each(rs[8], function (a, b) {
						personalityData.push({ value: b.value, name: b.name, emps: b.empNames });
						personalityLegend.push(b.name);
					});
					_list.personality = compUtils.dataPacket({
						data: personalityData,
						legend: personalityLegend
					})
					_this.list = _list;
					_this.loading = false;
				}, 0);
			},
		},
		created: function () {
			this.render();
		},
	});
});