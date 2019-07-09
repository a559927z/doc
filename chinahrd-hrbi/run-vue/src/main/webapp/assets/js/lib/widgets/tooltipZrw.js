/**
 * 
 * @param $
 */
!function($){
/**
 * option 参数  
 *        id 需要添加事件的对象id  或者 对象本身
 *        modal 弹出层用的div id
 *        style  弹出层 方向   默认 bottom
 *        event  触发事件  默认 click
 *        data   回调时传入的数据
 *        callback  function弹出层加载完成时的回调方法
 */
	  var TooltipZrw = function (element, options) {
		  options.id=element;
		    this.init(options)
		  };

		  TooltipZrw.prototype = {
//$.tooltipZrw=function(option){
//    	var tooltipZrw={
    			load:false,
    			style:"bottom",
    			styleCode:3,
    			event:"click",
    			showEvent:"click",
    			closeEvent:"click",
    			width:800,
    			height:400,
    			isClose:false,
    			isInContext:false,
    			init:function(options){
    				if(typeof options.id == 'object'){
			    		this.$element=options.id;
			    	}else if(typeof parm.id == 'string'){
			    		this.$element=$("#"+options.id);
			    	}
    				this.modal=options.modal;
    				this.data=options.data;
    				if(!_.isEmpty(options.style)){
    					switch(options.style){
    						case "top":
    							this.style=options.style;
    							this.styleCode=1;
    							break;
    						case "right":
    							this.style=options.style;
    							this.styleCode=2;
    							break;
    						case "left":
    							this.style=options.style;
    							this.styleCode=4;
    							break;
    					}
    				}
    				this.validateEvent(options);
    			
    				if(options.width&&parseInt(options.width)>0){
    					this.width=parseInt(options.width);
    				}
    				if(options.height&&parseInt(options.height)>0){
    					this.height=parseInt(options.height);
    				}
   
       				if(typeof options.callback == 'function'){
       					this._callback=options.callback;
       				}else{
       					this._callback=function(_,__){
       					}
       				}
       				this.listenerShow();
    			},initModal:function(){
    				this.$result=$('<div></div>');
		    		$(this.$result).attr("role","tooltip");
		    		$(this.$result).addClass("tooltipZrw fade in");
		        	this.$arrow=$('<div></div>').addClass("arrow");
		    		this.$result.append(this.$arrow);
		    		this.$title=$('<h3></h3>').addClass("tooltipZrw-title");
		    		this.$title.hide();
		    		this.$result.append(this.$title);
		    		this.$content=$('<div></div>').addClass("tooltipZrw-content");
		    		this.$result.append(this.$content);
		    		$(this.$content).width(this.width);
		    		$(this.$content).height(this.height);
    			},
    			listenerShow:function(){
    				var self=this;
    				$(this.$element).on(this.showEvent,function(){
    					self.isClose=false;
    					self.show();
    		    	});
    			},listenerClose:function(){
    				if(this.load){
    					return;
    				}
       				if(this.closeEvent=="mousemove"){
       					this.closeMove();
       					this.closeClick();
       				}else
       				
       				this.closeClick();
    			},
    			show:function(){
    				var self=this;
		    		var popoverId=$(this.$element).attr("popover-id");
		    		
		    		if(this.load){
		    			$(self.$result).css("opacity",1);
		    			this.resize(true);
		    			//$("#"+popoverId).show();
		    			return;
		    		}
	 				this.initModal();
		    		popoverId="popover"+Math.floor(Math.random()*999999+1);
		    		$(this.$element).attr("popover-id",popoverId);
		    		$(this.$result).attr("id",popoverId);
		    		$(this.$result).addClass(this.style);
		    		this.$content.append($("#"+this.modal).html());
		        	this.callback(this.$content,this.data);
		        	this.listenerClose();
		        	this.load=true;
    			},getPosition:function(){
    				var tp;
    				switch(this.styleCode){
					case 1:
						var top=$(this.$element).offset().top+$(this.$element).height();
			    		var left=$(this.$element).offset().left>5?$(this.$element).offset().left:5;
			    		left+=$(this.$element).width()/2-5;
			    		tp={top:top,left:left};
						break;
					case 2:
						var top=$(this.$element).offset().top+$(this.$element).height()/2;
			    		var left=$(this.$element).offset().left>5?$(this.$element).offset().left:5;
			    		left+=$(this.$element).width()-5;
			    		tp={top:top,left:left};
						break;
					case 3:
						var top=$(this.$element).offset().top+$(this.$element).height();
			    		var left=$(this.$element).offset().left>5?$(this.$element).offset().left:5;
			    		left+=$(this.$element).width()/2-5;
			    		tp={top:top,left:left};
						break;
					case 4:
						var top=$(this.$element).offset().top+$(this.$element).height()/2;
			    		var left=$(this.$element).offset().left>5?$(this.$element).offset().left:5;
			    		tp={top:top,left:left};
						break;
				}
    				return tp;
    			},
    			closeClick:function(){
    				$(window).click(function(e){
    		    		 $.each($(".tooltipZrw"), function (i, obj) {
    		                 if($(e.target).attr("popover-id")!=obj.id&&e.target.id!=obj.id){
    		                	 var div=$(e.target).parents(".tooltipZrw");
    		             		if(div.length==0){
    		             			self.isClose=true;
    		             			$(this).hide();
    		             		}
    		                	 
    		                 }
    		             });
    		    	});
    			},
    			closeMove:function(){
    				var self=this;
    				$(this.$result).mouseout(function(e){
    					//self.isClose=true;
    					self.isInContext=false;
    					//r();
    		    	});
    				$(this.$result).mouseover(function(e){
    					//self.isClose=false;
    					self.isInContext=true;
    		    		//hs($(self.$result).css("opacity"));
    		    	});
//					var hs=function(o){
//    					setTimeout(function(){
//    						console.log(o);
//    						if(o>=1.0){
//    							$(self.$result).css("opacity",1);
//    						}else{
//    							$(self.$result).css("opacity",o);
//    							hs(parseFloat(o)+0.3);
//    						}
//    					},100);
//    				}
    				var h=function(o){
    					setTimeout(function(){
    						if(!self.isClose){
    							return;
    						}
    						if(o<=0){
    							$(self.$result).hide();
    						}else{
    							$(self.$result).css("opacity",o);
        						h(o-0.1);
    						}
    					},200);
    					
    				}
    				var r=function(){
    					setTimeout(function(){
    						if(self.isClose){
    							if(!self.isInContext){
    								$(self.$result).hide();
    								//h($(self.$result).css("opacity"));
    							}else{
    								r();
    							}
    						}
    					},100);
    				}
//    				$(this.$element).mouseover(function(e){
//    					self.isClose=false;
//    					self.isInContext=true;
//    		    		hs($(self.$result).css("opacity"));
//    		    	});
    				$(this.$element).mouseout(function(e){
    					self.isClose=true;
    				//	self.isInContext=false;
    					r();
    		    	});
    			},resize:function(bool){
    				if(bool||!$(this.$result).is(':hidden')){
						$(this.$result).show();
					}
    				var tp=this.getPosition();
    				 $(this.$result).css("top",tp.top);
    				 $(this.$result).css("left",tp.left);
 		        	 this.check();
    				 //$(this.$result).show();
						
    			},
    			callback:function(obj,data){
    				var self=this;
    				switch(this.styleCode){
					case 1:
	    				 setTimeout(function() {
	    					 var top=$(self.$result).offset().top-$(self.$result).height()-5;
	    					 $(self.$result).css("top",top);
	    				      },1000)
						break;

					case 4:
						setTimeout(function() {
	    					 var left=$(self.$result).offset().left-$(self.$result).width()+5;
	    				
	    					 $(self.$result).css("left",left);
	    				
	    				      },1000)
						break;
    				}
    				this._callback(obj,data);
		        	$('body').append(this.$result);
		        	this.resize(true);
    				var self=this;
					$(window).resize(function(e){
						self.resize();
					});
    			},check:function(){
    				$(this.$arrow).css("left",10);
    				var offsetX=$(this.$result).offset().left+$(this.$result).width()-$(window).width();
    				//alert(offsetX+"   "+$(window).width()+" "+$(this.$result).width()+" "+$(this.$result).offset().left);
    				if(offsetX>0){
    					$(this.$result).css("left",$(this.$result).offset().left-offsetX);
    					//alert($(this.$arrow).css("left")+"  "+$(this.$result).offset().left);
    					$(this.$arrow).css("left",(10+offsetX));
    					
    				}
    			},validateEvent:function(options){
    				if(!_.isEmpty(options.event)){
    					var arr =(options.event).split("|");
    					var s=this.showEvent;
    					var c=this.closeEvent;
    					if(arr.length==1){
    						s=options.event;
    						c=options.event;
    					}else{
    						s=arr[0];
    						c=arr[1];
    					}
    					switch(s){
							case "click":
							case "dblclick":
							case "mousedown":
							case "mouseup":
							case "mousemove":
							case "mouseover":
							case "mouseout":
							case "change":
							case "select":
								this.showEvent=s;
								break;
    					}
    					switch(c){
						case "click":
						case "dblclick":
						case "mousedown":
						case "mouseup":
						case "mousemove":
						case "mouseover":
						case "mouseout":
						case "change":
						case "select":
							this.closeEvent=c;
							break;
					}
    				}
	    				
    			}

    	}
    	
//    	tooltipZrw.init(option);
//    	
//    }
$.fn.tooltipZrw = function ( option ) {
			  new TooltipZrw(this, option);
  };
  
  /**
   * 提供第二种调用方式
   */
  $.tooltipZrw=function(option){
	  tooltipZrw.init(option);
  }
 // $.fn.tooltip.Constructor = TooltipZrw;
}(window.jQuery)
