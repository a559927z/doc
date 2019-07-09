define(['jquery', 'vue2x','underscore','bootstrap'], function (jq, Vue,_) {
	var webRoot = G_WEB_ROOT;
		return Vue.extend({
			created : function() {
//				this.$options.template ='';
			},
			template:
			'<div >\
				<div class="index-common-title bottom-title">\
			    	<div v-if="lable.length > 0" class="index-common-title-left bottom-left"></div>\
			    	<div v-if="lable.length > 0"  class="index-common-title-text bottom-text">{{lable}}</div>\
			    	<div v-if="sublable.length > 0"  class="index-common-title-tooltip">{{sublable}}</div>\
			    	<div class="rightSetUpBtn">\
			        	<div @click="tab(true)" ref="left" class="rightSetUpBtnDiv rightSetUpLeft icon rightSetUpBtnSelect" >\
			            	<div class="rightSetUpBtnTop"></div>\
							<template v-if="tabtxt.length > 0">\
								<div class="text">{{_txt1}}</div>\
							</template>\
							<template v-else>\
								<div class="rightSetUpLeftShowIcon"></div>\
			        			<div class="rightSetUpLeftHideIcon"></div>\
							</template>\
			        	</div>\
			            <div @click="tab(false)" ref="right" class="rightSetUpBtnDiv rightSetUpRight icon ">\
			                <div class="rightSetUpBtnTop"></div>\
							<template v-if="tabtxt.length > 0">\
								<div class="text">{{_txt2}}</div>\
							</template>\
							<template v-else>\
								<div class="rightSetUpLeftShowIcon"></div>\
				    			<div class="rightSetUpLeftHideIcon"></div>\
							</template>\
			            </div>\
			    	</div>\
			    </div>\
				<div :class="_classHeight">\
				<template v-if="leftView">\
			        <div class="chart-view">\
			            <div class="col-sm-12 ">\
			 				<div class="loadingmessage" v-if="loading">数据读取中...</div>\
			 				<div class="loadingmessage" v-if="!loading && empty">暂无数据</div>\
							<slot name="left"></slot>\
						</div>\
			        </div>\
				</template>\
				<template v-else>\
					<div class="chart-view">\
						<div class="col-sm-12 ">\
			 				<div class="loadingmessage" v-if="loading">数据读取中...</div>\
			 				<div class="loadingmessage" v-if="!loading && empty">暂无数据</div>\
							<slot name="right"></slot>\
						</div>\
		        	</div>\
				</template>\
			    </div>\
			</div>'
			,
		    props : {
		    	'lable'			:{type:String, default:''},	// 标题
		    	'sublable'		:{type:String, default:''},	// 副标题
				'title'			:{type:String, default:''}, // 提示
				'height'		:{type:String, default:''},	// 块的内框高
				'tabtxt'		:{type:String, default:''}	// tab文字显示
		    },
			data : function(){
				return {
					loading:false,
					empty:false,
					leftView:true,
				}
			},
			computed : {
				_classHeight : function(){
					return "bottom-div bottom-div-two " + this.height;
				},
				_txt1 : function(){
					if(this.tabtxt.length > 0 && this.tabtxt.length < 4){ 
						return this.tabtxt.split(",")[0]
					}
				},
				_txt2 : function(){
					if(this.tabtxt.length > 0 && this.tabtxt.length < 4){ 
						return this.tabtxt.split(",")[1]
					}
				}
			},
			mounted : function(){
			},
			methods:{
				tab:function(params){
					if(params){
						var el = this.$refs.right;
						jq(el).removeClass("rightSetUpBtnSelect");
						jq(this.$refs.left).addClass("rightSetUpBtnSelect");
						this.leftView=true;
					}else{
						var el = this.$refs.left;
						jq(el).removeClass("rightSetUpBtnSelect");
						jq(this.$refs.right).addClass("rightSetUpBtnSelect");
						this.leftView=false;
					}
				}
			}
		});
	});