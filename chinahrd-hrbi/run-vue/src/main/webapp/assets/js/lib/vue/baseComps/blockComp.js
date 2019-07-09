define(['vue2x','underscore'], function (Vue,_) {
	var webRoot = G_WEB_ROOT;
		return Vue.extend({
			template:
				'<div class="col-sm-6 ct-line-col SetUpBody">\
					<div class="index-common-title bottom-title">\
						<div class="index-common-title-left bottom-left"></div>\
						<div class="index-common-title-text bottom-text">{{lable}}</div>\
					</div>\
					<div class="bottom-div">\
						<div class="loadingmessage" v-if="loading">数据读取中...</div>\
						<div class="loadingmessage" v-if="!loading && empty">暂无数据</div>\
						<slot></slot>\
					</div>\
				 </div>',
			props:{
				'lable':[String],	// 标题
				'clazz':[String],	// 块组件的宽度样式
			},
			data:function(){
				return {
					loading:false,
					empty:false
				}
			},
			mounted:function(){
			}
		});
	});