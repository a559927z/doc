require(['jquery', 'vue2x', 'compUtils', 'blockComp','pieComp','barComp'], function ($, Vue, compUtils, blockComp, PieComponent, BarComponent) {
	new Vue({
		el: '.blockCompId',
		watch: {},
		components: {
			'block-component': blockComp,
			'pie-component': PieComponent,
			'bar-component': BarComponent
		},
		data: function () {
			return {
				loading: true,
				list: {},
			}
		},
		methods: {
			render: function () {
				var _this = this;
				// TODO ajax
				var rs = {
					"0": [{
						"k": "5级",
						"v": "160"
					}, {
						"k": "4级",
						"v": "155"
					}, {
						"k": "3级",
						"v": "260"
					}, {
						"k": "2级",
						"v": "311"
					}, {
						"k": "1级",
						"v": "38"
					}, {
						"k": "其他",
						"v": "1"
					}],
					"4": {
						"seriesData": [22, 880, 23, 0, 0],
						"xAxisData": ["90后", "80后", "70后", "60后", "其他"]
					},
				};
				setTimeout(function () {
					var _list = {};
					// 职级
					var abilityLegend = [];
					var abilityData = [];
					$.each(rs[0], function (a, b) {
						abilityData.push({ value: b.v, name: b.k });
						abilityLegend.push(b.k);
					});
					_list.ability = compUtils.dataPacket({
						data: abilityData,
						legend: abilityLegend
					});
					// 年龄
					_list.age = compUtils.dataPacket({
						xAxis: rs[4].xAxisData,
						data: rs[4].seriesData
					});
					_this.list = _list;
					_this.loading = false;
				}, 2000);
			},
		},
		created: function () {
			this.render();
		},
	});
});