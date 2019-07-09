/*
 * Copyright (c) 2016 htpeng
 * Date: 2016-06-13
 *  手机端Gauge 辅助绘制 组件
 */
!function($){
	  var GaugeAuxiliary = function (element, options) {
		    options.id=element;
		    var obj={
	    			load:false,
	    	                		width:$(window).width(),
	    			textColor:"#000000",
	    			text:"",
	    			shadeColor:'rgba(243,245,247,0.4)', //弹出遮罩颜色
	    			blankColor:'rgba(255,255,255,1)', //弹出遮罩颜色
	    			borderColor:"rgba(227,230,235,0.3)", //弹出遮罩颜色
	    			textReactColor:'rgba(243,245,247,1)', //弹出遮罩颜色
	    			scal:false, //是否完成缩放
	    			init:function(options){
	    				if(typeof options.id == 'object'){
				    		this.$element=options.id;
				    	}else if(typeof parm.id == 'string'){
				    		this.$element=$("#"+options.id);
				    	}
	    				jQuery.extend(this,options);        
	       				this.validArc();
	    			},validArc:function(){
	    				this.getGraphics();
	    				
	    				var radius=this.option.series[0].radius;
	    				var center=this.option.series[0].center;
	    				var left=0.5;
	    				var top=0.5;
	    				if(center!=undefined){
	    					if(center[0]!=undefined){
		    					left=parseInt(center[0])/100;
		    				}
		    				if(center[1]!=undefined){
		    					top=parseInt(center[1])/100;
		    				}
	    				}
	    				this.ArcLeft=left;
	    				this.radius=radius;
//	    				radius=this.chartWidth>this.chartHeight?this.chartHeight*radius:this.chartWidth*radius;
//	    				this.radius=radius/this.scaleX;
//	    				alert(this.radius)
	    
	    				this.ArcPoint={x:this.chartWidth*left,y:this.chartHeight*top}
	    				this.fillArc(this.ArcPoint,this.radius);
	    			},appendShade:function(width,height){
	    		   		this.$canvas=$("<canvas width='"+width+"' height='"+height+"'></canvas>")
			    		this.$canvas.addClass("chart-gauge-canvas");
			    		this.$element.children(":first").append(this.$canvas);
			    		this.$canvas.width(this.chartWidth);
			    		this.$canvas.height(this.chartHeight);
	    				this.graphics=this.$canvas[0].getContext("2d");
	    				this.graphics.scale(width/this.$element.width(),height/this.$element.height());
	    				this.scaleX=width/this.$element.width();
	    				//this.scaleX=2  //检测到Android手机 使用计算的缩放不正确  强制定为2
	    			},removeShade:function(){
	    				this.$element.children(":first").children(".chart-tooltip-canvas").remove();
	    			},getGraphics:function(y){
	    				if(y==undefined)y=0;
						this.chartWidth=this.$element.width();
						this.chartHeight=this.$element.height();
        				this.chartLeft=(this.$element.offset().left);
	    				this.chartTop=(this.$element.offset().top);
	    				this.offsetX=-this.chartLeft+3;
	    				this.offsetY=y-this.height+20;
		    				this.scal=true;
							var canvass=this.$element.find("canvas");
				    		var width=this.chartWidth;
				    		var height=this.chartHeight;
				    		if(canvass.length>0){
				    			width=$(canvass[0]).attr("width");
				    			height=$(canvass[0]).attr("height");
				    		}
		    				if(!this.scal){
		    					this.scal=true;
		    					this.shadeWidth=width;
		    					this.shadeHeight=height;
								this.appendShade(width,height);
							}else{
								if(this.shadeHeight!=height){
									this.removeShade();
									this.shadeWidth=width;
			    					this.shadeHeight=height;
									this.appendShade(width,height);
								}
							}
	    			},fillArc:function(point,radius){

	    				this.graphics.fillStyle = this.shadeColor;
	    				this.graphics.shadowColor=this.borderColor;
	    				this.graphics.shadowOffsetX=3; 
	    				this.graphics.shadowOffsetY=-3;
    				    this.graphics.arc(point.x, point.y+4, radius+10, 0, Math.PI, true);    
    				    this.graphics.fill();
    				   
    				    this.graphics.beginPath()
    				    
    				    this.graphics.globalCompositeOperation="destination-out";
	    				this.graphics.fillStyle = this.blankColor;
   				        this.graphics.arc(point.x, point.y+4, radius-10, 0, Math.PI, true);    
   				        this.graphics.fill();
   				        
   				        
    					this.graphics.beginPath();
    					this.graphics.globalCompositeOperation="source-over"
    					this.graphics.shadowColor="#DFE0E2";
    				    this.graphics.fillStyle = this.textReactColor;
    				    this.graphics.shadowOffsetX=0; 
    				    this.graphics.shadowOffsetY=4; 
    				    this.graphics.arc(point.x, point.y-10, 25, 0, 2*Math.PI, false);    
    				    this.graphics.fill();
    				    this.graphics.save(); 
    				    
    				    this.graphics.fillStyle=this.textColor; 
    				    this.graphics.globalAlpha="1"; 
    				    this.graphics.textAlign="center"; 
    				    this.graphics.font="16px 黑体"; 
    				    this.graphics.shadowOffsetX=0; 
    				    this.graphics.shadowOffsetY=0;
    				    this.graphics.fillText(this.text,point.x, point.y-3);//IE不支持 
    	
    				    this.graphics.closePath();
	    			}

	    	}
		    obj.init(options)
		  };

		  
$.fn.gaugeAuxiliary = function ( option ) {
			  new GaugeAuxiliary(this, option);
  };
  
  /**
   * 提供第二种调用方式
   */
  $.gaugeAuxiliary=function(option){
	  gaugeAuxiliary.init(option);
  }

}(window.jQuery)
