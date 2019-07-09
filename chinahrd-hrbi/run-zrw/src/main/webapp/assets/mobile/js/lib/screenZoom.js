/**
 * 面板全屏
 * @param $
 */
!function($){
	jQuery.extend({
		screenZoom:function (options) {
			 var obj={
			    objArr:[],
			    customName:"zoom-index",
				init:function(options){
					this.analyze(options);
					this.scal();
				},scal:function(){
					var self=this;
					$.each($(".tab-panel").find(".panel"),function(){
						 var panel=this;
						 var index=$(this).attr(self.customName);
						 if(index==undefined)return;
						 var content=$(panel).find(".content");
						 var contentPanel=$(panel).find(".chartPanel");
						 var chart=$(contentPanel).find(".chart");
						 var height=$(contentPanel).height();
						 var width=$(contentPanel).width();
						 var paddindHeight=80;
						 var first=true;
//						 $(this).find(".tab-panel-title").press({time:50},function(){
						 $(this).find(".tab-panel-title").find(".tab-panel-title-zoom").on("click", function(e) {
							 if(first){
								 first=false;
								 if(contentPanel.length==0){
									 contentPanel=$(panel).find(".tablePanel");
									 chart=$(contentPanel).find("table");
									 height=$(contentPanel).height();
									 width=$(contentPanel).width();
								 }
								 $.each($(panel).find(".title"),function(){
									 paddindHeight+=$(this).height();
								 });
								
							 }
							 if($(panel).hasClass("fixed-panel")){
								 $(panel).removeClass("fixed-panel");
								 $(content).removeClass("content-zoom");
								 $(this).removeClass("tab-panel-title-zoom-close")
								 $(contentPanel).height(height);
								 $(contentPanel).width(width);
								 $(chart).height(height);
								 $(chart).width(width);
								 window.screenScroll=true;
								 window.screenFull=false;
							 }else{
								 $(panel).addClass("fixed-panel");
								 $(content).addClass("content-zoom");
								 $(this).addClass("tab-panel-title-zoom-close")
								 $(contentPanel).height(getWindowHeight()-paddindHeight);
								 $(contentPanel).width($(window).width());
								 $(chart).width($(window).width());
								 $(chart).height(getWindowHeight()-paddindHeight);
								 window.screenFull=true;
								 window.screenScroll=false;
							 } 
							 if(index!=undefined&&index!==""){
								 var p= self.objArr[index];
								 if(p!=undefined)
									 p.resize();
							 }
		                });
					 })
				},analyze:function(options){
					var self=this;
					if(typeof options =="object"){
						if(Object.prototype.toString.call(options) === '[object Array]'){
							 this.objArr=options;
							$.each(options,function(i,o){
								var id=o.chartId;
								if(id==undefined){
									id=o.gridId;
								}
								if(id==undefined){
									return;
								}else{
									if(id.indexOf("#")!=0){
										id="#"+id;
									}
									var panel =$(id).parents(".panel");
									 panel.attr(self.customName,i);
								}
							});
						}else{
							var index=0;
							for(var key in options){
								 var panel =$("#"+key).parents(".panel");
								 panel.attr(self.customName,index++);
								 this.objArr.push(options[key]);
							}	
						}
						
					}
				} 
			};
			 obj.init(options)
      }
	 });
}(window.jQuery)
