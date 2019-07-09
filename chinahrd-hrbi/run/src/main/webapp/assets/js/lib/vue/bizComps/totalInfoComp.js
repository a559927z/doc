define(['vue2x','underscore','utils'], function (Vue,_) {
	var webRoot = G_WEB_ROOT;
		return Vue.extend({
			created : function() {
				this.$options.template =
					'<div class="content">\
								<div class="data cursorpointer">\
							<div class="accord-yj-float">\
								<span class="accord-yj-float-value">{{data.nowVal}}\
									<span v-if="data.nowVal.length > 7" class="accord-yj-float-layer"></span>\
									<span class="accord-yj-float-people">万</span>\
								</span>\
							</div>\
							<div class="accord-bottom-float">\
								<div class="accord-bottom-float-text">{{data.lastText}}</div>\
								<template v-if="arrow" >\
									<div class="accord-bottom-float-arrow accord-bottom-float-value-rise"></div>\
									<div class="accord-bottom-float-value accord-bottom-float-value-rise">{{data.lastVal}}</div>\
								</template>\
								<template v-else>\
									<div class="accord-bottom-float-arrow accord-bottom-float-value-drop"></div>\
									<div class="accord-bottom-float-value accord-bottom-float-value-drop">{{data.lastVal}}</div>\
								</template>\
								<div class="accord-bottom-float-people">%</div>\
							</div>\
						</div>\
					</div>'
			},
			props:{
				// 数据源
				'data':{
					type:Object,
					default:{ nowVal:0, lastText:'', lastVal :0}
				},
				'arrow':[Boolean]
			},
			data:function(){
				return {
					loading:true,
					empty:false,
				}
			},
			mounted:function(){
				this.data.nowVal = Tc.formatNumber(Tc.formatFloat(this.data.nowVal));
			},
			computed:function(){
			}
		});
	});
