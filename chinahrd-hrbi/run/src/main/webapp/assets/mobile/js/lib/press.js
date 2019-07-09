
var presInterval=null;
/**
 * 手机端长按事件响应组件
 * @param $
 */
!function($){

	  var Press = function (element, options,func) {
		  options.id=element;
		    this.init(options,func)
		  };
		  var _options={
				  time:300
		  }
		  Press.prototype = {
				  backcall:function(){
					  
				  },
    			init:function(options,func){
    				var self=this;
    				if(typeof options.id == 'object'){
			    		this.$element=options.id;
			    	}else if(typeof parm.id == 'string'){
			    		this.$element=$("#"+options.id);
			    	}
    				if(typeof options =="function"){
    					self.backcall=options;
    					self.options = $.extend(true, _options , options );
    				}else if(typeof options =="object"){
    					self.options = $.extend(true, _options , options );
    				}else{
    					self.options = $.extend(true, _options , {} );
    				}
    				
    				if(typeof func =="function"){
    					self.backcall=func;
    				}
    				
    				$(this.$element).unbind('touchstart').bind('touchstart',function(event){
	                	event.preventDefault();//阻止触摸时浏览器的缩放、滚动条滚动
	                	self.startTime= 0;
	                	self.startTimeObj=true;
	                	presInterval=setInterval(function(){
	                		self.startTime=self.startTime+30;
	                		if(self.startTime>=self.options.time){
	                			window.clearInterval(presInterval);
	                			self.backcall(self.$element,event);
	                		}
	                	},30);
	                });
    				$(this.$element).unbind('touchend').bind('touchend',function(e){
    					window.clearInterval(presInterval);
	                });
    			}

    	}
    	
//    	tooltipZrw.init(option);
//    	
//    }
$.fn.press = function ( option,fun ) {
			  new Press(this, option,fun);
  };
  
  /**
   * 提供第二种调用方式
   */
  $.press=function(option,fun){
	  press.init(option,fun);
  }
 // $.fn.tooltip.Constructor = TooltipZrw;
}(window.jQuery)
