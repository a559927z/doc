define(['vue2x','underscore','bootstrap'], function (Vue,_) {
	var webRoot = G_WEB_ROOT;
		return Vue.extend({
			created : function() {
					this.$options.template =
						'<div class="">\
							<div class="top-div">\
								<div class="index-common-title">\
									<div class="index-common-title-left"></div>\
									<div class="index-common-title-text">{{lable}}\
						 				<img v-if="tip.length > 0" src="'+webRoot+'/assets/img/base/tip.gif" data-toggle="tooltip" data-placement="bottom" data-original-title="'+this.tip+'">\
						 			</div>\
									<div class="index-common-title-right">\
						 				<slot name="titleRight"></slot>\
									</div>\
								</div>\
								<div class="body-div">\
						 				<div class="loadingmessage" v-if="loading">数据读取中...</div>\
						 				<div class="loadingmessage" v-if="!loading && empty">暂无数据</div>\
						 			<slot name="body"></slot>\
								</div>\
							</div>\
						</div>';
			},
			props:{
				'lable':[String],	// 标题
				'clazz':[String],	// 块组件的宽度样式
				'tip':{type:String, default:''} // 提示
			},
			data:function(){
				return {
					loading:false,
					empty:false
				}
			},
			mounted:function(){
				// 处理tip事件
				if(this.tip.length > 0){
					$("[data-toggle='tooltip']").tooltip();
				}
				
			}
		});
	});