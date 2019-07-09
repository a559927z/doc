define(['jquery', 'vue2x','underscore','bootstrap'], function (jq, Vue,_) {
	var webRoot = G_WEB_ROOT;
		return Vue.extend({
			created : function() {
//				this.$options.template ='';
			},
			template:
			'<div class="" view="chart">\
				<div class="index-common-title bottom-title">\
			    	<div class="index-common-title-left bottom-left"></div>\
			    	<div class="index-common-title-text bottom-text">{{lable}}</div>\
			    	<div class="index-common-title-tooltip">{{sublable}}</div>\
			    	<div class="rightSetUpBtn">\
			        	<div @click="tab(true)" ref="left" class="rightSetUpBtnDiv rightSetUpLeft icon rightSetUpBtnSelect" >\
			            	<div class="rightSetUpBtnTop"></div>\
			            	<div class="rightSetUpLeftShowIcon"></div>\
			            	<div class="rightSetUpLeftHideIcon"></div>\
			        	</div>\
			            <div @click="tab(false)" ref="right" class="rightSetUpBtnDiv rightSetUpRight icon ">\
			                <div class="rightSetUpBtnTop"></div>\
			                <div class="rightSetUpRightShowIcon"></div>\
			                <div class="rightSetUpRightHideIcon"></div>\
			            </div>\
			    	</div>\
			    </div>\
				<div class="bottom-div bottom-div-two">\
				<template v-if="leftView">\
			        <div class="chart-view">\
			            <div class="height320 col-sm-12">\
			 				<div class="loadingmessage" v-if="loading">数据读取中...</div>\
			 				<div class="loadingmessage" v-if="!loading && empty">暂无数据</div>\
							<slot name="left"></slot>\
						</div>\
			        </div>\
				</template>\
				<template v-else>\
					<div class="chart-view">\
			            <div class="height320 col-sm-12">\
			 				<div class="loadingmessage" v-if="loading">数据读取中...</div>\
			 				<div class="loadingmessage" v-if="!loading && empty">暂无数据</div>\
							<slot name="right"></slot>\
						</div>\
		        	</div>\
				</template>\
			    </div>\
			</div>'
	    ,
	    props:{
	    	'lable':[String],	// 标题
	    	'sublable':[String],// 副标题
//			'clazz':[String],	// 块组件的宽度样式
			'title':{type:String, default:''} // 提示
	    },
			data:function(){
				return {
					loading:false,
					empty:false,
					leftView:true
				}
			},
			mounted:function(){
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