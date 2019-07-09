require(['jquery', 'vue2x','compUtils', 'constellationComp'], function ($, Vue, compUtils, constellationComp) {
	new Vue({
		el: '.constellationCompId',
		data: {
		},
		watch: {
		},
		components: {
			'constellation-component': constellationComp
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
					"7": {
						"seriesData": [13, 23, 8, 19, 37, 8, 10, 2, 2, 1, 0, 802],
						"xAxisData": ["白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座"]
					},
				};
				setTimeout(function () {
					var _list = {};
					//星座
					_list.constellatory = compUtils.dataPacket(
						{
							xAxis: rs[7].xAxisData,
							data: rs[7].seriesData
						});
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