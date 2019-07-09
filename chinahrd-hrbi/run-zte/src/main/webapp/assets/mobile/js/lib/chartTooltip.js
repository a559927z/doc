/**
 * 手机端echart tooltip 组件
 * @param $
 */
!function($){

	  var ChartTooltip = function (element, options) {
		    options.id=element;
		    var obj={
	    			load:false,
	    			style:"top",
	    			render:function(parm){
	    		    	var result='<div class="chart-tooltip" >';
	        				result+='<div class="chart-tooltip-title">';
	        				if(parm.title!=undefined&&parm.title!="undefined"&&parm.title!=""){
	        					result+=parm.title;
	        				}		
	        				result+='</div>';
	        			if(!_.isEmpty(parm.cols)){
	        				$.each(parm.cols,function(i,o){
	        		    		result+='<div class="chart-tooltip-col">';
	        		    			result+='<div class="chart-tooltip-name"><label>'+o.name+'</label></div>';
	        		    			result+='<div class="chart-tooltip-value"><label>'+o.value+'</label></div>';
	        		    		result+='</div>';
	        		    	});
	        			}
	    	    	
	        			result+='</div>';
			        	return result;
			        },
	    			width:$(window).width(),
	    			height:76,
	    			barWidth:10,
	    			isInContext:false,
	    			horizontal:true,
	    			touchShade:null,
	    			shadeColor:'#040404',
	    			scal:false,
	    			showEvent:'touchstart',
	    			closeEvent:'touchend',
	    			closeAnimate:null,
	    			showAnimate:null,
	    			toolTipShow:false,
	    			vaild:[],
	    			init:function(options){
	    				if(typeof options.id == 'object'){
				    		this.$element=options.id;
				    	}else if(typeof parm.id == 'string'){
				    		this.$element=$("#"+options.id);
				    	}
	    				jQuery.extend(this,options);        
	       				if(typeof options.callback == 'function'){
	       					this._callback=options.callback;
	       				}else{
	       					this._callback=function(_,__){
	       					}
	       				}
	       				this.initModal();
	       				this.listener();
	    			},validRange:function(){  
	    				var option=this.chart._optionRestore;
	    				var grid=$.extend({x:80,y:80,x2:80,y2:80},option.grid);
	    				this.series=option.series;
	    				var xAxis=option.xAxis;
	    				var yAxis=option.yAxis;
	    				var self=this;
	    				this.vaild=[];
	    				this.shadeValid=[];
	    				this.chartLeft=(this.$element.offset().left);
	    				this.chartTop=(this.$element.offset().top);
	    				this.offsetX=-this.chartLeft+3;
	    				this.offsetY=grid.y-this.height+20;
	    				
	    				if(xAxis.length>0&&xAxis[0].type=="category"){
	    					//x
	    					this.nAxis=xAxis[0];

	    					this.horizontal=true;
	    				}
	    				if(yAxis.length>0&&yAxis[0].type=="category"){
	    					//y
	    					this.nAxis=yAxis[0];

	    					this.horizontal=false;
	    				}
	    				this.chartWidth=this.$element.width();
	    				this.chartHeight=this.$element.height();
        				
						if(!this.scal){
							this.scal=true;
							var canvass=this.$element.find("canvas");
				    		var width=this.chartWidth;
				    		var height=this.chartHeight;
				    		if(canvass.length>0){
				    			width=$(canvass[0]).attr("width");
				    			height=$(canvass[0]).attr("height");
				    		}
							this.appendShade(width,height);
						}
						var rectHeight=this.chartHeight-grid.y-grid.y2;
						var rectWidth=this.chartWidth-grid.x-grid.x2;
	    				
	    				if(this.horizontal){
	        				var vaildXmin=this.chartLeft+grid.x;
	        				var vaildXmax=this.chartLeft+this.chartWidth-grid.x2;
	        				var vaildX=vaildXmax-vaildXmin;
	        				var validMin=grid.y;
	        				var validMax=this.chartHeight-grid.y2;
	        				this.dataCount=xAxis[0].data.length;
	        				var barLen=this.dataCount*this.barWidth;
	        				var spaceWidth=(vaildX-this.dataCount*this.barWidth)/(this.dataCount*2);
	        				this.spaceWidth=spaceWidth;
	        				for( var i=0;i<this.dataCount;i++){
	        					var Xmin=vaildXmin+(2*i+1)*spaceWidth+this.barWidth*i;
	        					var Xmax=Xmin+this.barWidth;
	        					var Xmid=Xmin+this.barWidth/2-this.chartLeft-this.offsetX;
	        					this.shadeValid.push(Xmin+this.offsetX-3);
	        					this.vaild.push({min:Xmin,max:Xmax,mid:Xmid,validMin:validMin,validMax:validMax});
	        				}
	        				this.rect={x:1,y:grid.y,width:this.barWidth,height:rectHeight+3};
	    				}else{
	        				var vaildYmin=this.chartTop+grid.y;
	        				var vaildYmax=this.chartTop+this.chartHeight-grid.y2;
	        				var vaildY=vaildYmax-vaildYmin;
	        				var validMin=grid.x-this.offsetX;
	        				var validMax=this.chartWidth-grid.x2-this.offsetX;
	        				this.dataCount=yAxis[0].data.length;
	        				var barLen=this.dataCount*this.barWidth;
	        				var spaceWidth=(vaildY-this.dataCount*this.barWidth)/(this.dataCount*2);
	        				this.spaceWidth=spaceWidth;
	        				for( var i=0;i<this.dataCount;i++){
	        					var Ymin=vaildYmin+(2*i+1)*spaceWidth+this.barWidth*i;
	        					var Ymax=Ymin+this.barWidth;
	        					var Ymid=(this.width+this.offsetX)/2;
	        					this.shadeValid.push(Ymin);
	        					this.vaild.push({min:Ymin,max:Ymax,mid:Ymid,validMin:validMin,validMax:validMax});
	        				}
	        				this.rect={x:grid.x,y:1,width:rectWidth,height:this.barWidth};
	    				}
	    				
	    			},appendShade:function(width,height){
	    		   		this.$canvas=$("<canvas width='"+width+"' height='"+height+"'></canvas>")
			    		this.$canvas.addClass("chart-tooltip-canvas");
			    		this.$element.children(":first").append(this.$canvas);
			    		this.$canvas.width(this.chartWidth);
			    		this.$canvas.height(this.chartHeight);
	    				this.graphics=this.$canvas[0].getContext("2d");
	    				this.graphics.scale(width/this.$element.width(),height/this.$element.height());
	    				this.$canvasRect=$("<canvas width='"+width+"' height='"+height+"'></canvas>")
			    		this.$canvasRect.addClass("chart-tooltip-canvas-rect");
			    		this.$element.children(":first").append(this.$canvasRect);
			    		this.$canvasRect.width(this.chartWidth);
			    		this.$canvasRect.height(this.chartHeight);
	    				this.graphicsRect=this.$canvasRect[0].getContext("2d");
	    				this.graphicsRect.scale(width/this.$element.width(),height/this.$element.height());
	    				this.scaleX=width/this.$element.width();
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
			    		this.$result.height(this.height);
			    		this.$result.width($(window).width()-9);
			    		this.$element.append(this.$result);
	    			},touchChart:function(e,start){
	    				var self=this;
	    				var top=(this.$element.offset().top);
    					var _touch = e.originalEvent.targetTouches[0]; 
    					var y= _touch.pageY;
    					var x=_touch.pageX;
    					var exist=false;
    					
    					var index=-1;
    					var val=this.horizontal?x:y;
    					var validRange=this.horizontal?y-top:x;
    				//	console.log(validRange+"  "+this.vaild[0].validMin+"  "+this.vaild[0].validMax);
    					$.each(this.vaild,function(i,obj){
    						if(!exist&&validRange>obj.validMin&&validRange<obj.validMax&&obj.min-5<=val&&obj.max+5>=val){
    							exist=true;
    							index=i;
    						}
    					});
    					if(exist){
    						window.screenScroll=false;
    						var mid=this.vaild[index].mid;
    						this.clearRect();
    						this.graphics.beginPath(); 
    						this.graphicsRect.beginPath();
    						this.graphics.fillStyle = this.shadeColor;
    						this.graphicsRect.fillStyle = "#000000";
    						if(this.horizontal){
    							this.graphics.fillRect((this.shadeValid[index]-1),this.rect.y-2,this.rect.width+2,this.rect.height);
    							this.graphicsRect.strokeRect((this.shadeValid[index]-2),this.rect.y-2,this.rect.width+4,this.rect.height);
    						}else{
    							this.graphics.fillRect(this.rect.x,(this.shadeValid[index]-top-1),this.rect.width,this.rect.height+2);
    							this.graphicsRect.strokeRect(this.rect.x,(this.shadeValid[index]-top-2),this.rect.width,this.rect.height+4);
    						}
    						this.graphics.closePath();
    						this.graphicsRect.closePath();
    						if(!this.horizontal){
    							index=this.dataCount-1-index;
    						}
    						
    						var parm={name:"",data:[]};
    						$.each(this.series,function(i,s){
    							if(s.name instanceof Array){
    								parm.data.push({name:s.name[index],value:s.data[index]});	
    							}else{
    								parm.data.push({name:s.name,value:s.data[index]});	
    							}
    						});
    						
    						parm.name=self.nAxis.data[index];
    						this.renderData(mid,index,parm);
    						if(!self.toolTipShow){
    							if(self.touchShade==null){
    								self.touchShade=setTimeout(function(){
//        								if(start){
//        									if(self.horizontal&&Math.abs(self.lastX-x)<5){
//        										self.show();
//        										
//        									}else if(!self.horizontal&&Math.abs(self.lastY-y)<5){
//                    								self.show();
//                    						}
////        									if(Math.abs(self.lastX-x)<5&&Math.abs(self.lastY-y)<5){
////                								self.show();
////                							}
//        								}else{
//        									self.show();
//        								}
        								self.show();
        								self.touchShade=null;
            						},200)
    							}
    							
    						}
    						
    						self.lastX=x;
    						self.lastY=y;
    					}
	    			},
	    			listener:function(){
	    				var self=this;
	    				this.$element.unbind("touchstart").on("touchstart", function(e) {
	    					self.validRange();
	    					self.touchChart(e,true);
	    				})
	    				this.$element.unbind("touchmove").on("touchmove", function(e) {
	    					self.touchChart(e,false);
	    				});
	    				this.$element.unbind("touchend").on("touchend", function(e) {
	        				self.closeMove();
	        				window.screenScroll=true;
	    				});
	    			},
	    			renderData:function(mid,index,parm){

	    				if(this.horizontal){
	    					 $(this.$result).find(".arrow").css("left",mid+"px");
	    				}else{
	    					$(this.$result).find(".arrow").css("left",mid+"px");
	    				}
	    	        
			    		$(this.$result).addClass(this.style);
			    		$(this.$content).width(this.width+this.offsetX);
			    		this.$result.css("left",this.offsetX);
			    		//var scrollTop=window.screenTop||$(window).scrollTop();
			    		var top=this.$element.offset().top;
			    		var offsetY=this.offsetY-10;
			    		if(top>65){
			    			if(top<top-this.offsetY-10){
			    				offsetY=this.offsetY-10;
			    			}else{
			    				offsetY=30-top;
			    			}
			    		}else{
			    			offsetY=30-top;	
			    		}
			    		this.tooltipOffsetY=offsetY;
			    		this.$result.css("top",offsetY);
			    		this.$content.html(this.render(this.formatter(index,parm,this.series)));
    					
	    			},clearRect:function(){
	    				this.graphics.beginPath(); 
	    				this.graphics.clearRect(0,0,this.chartWidth,this.chartHeight);
	    				this.graphics.closePath(); 
	    				this.graphicsRect.beginPath();
	    				this.graphicsRect.clearRect(0,0,this.chartWidth,this.chartHeight);
	    				this.graphicsRect.closePath(); 
	    			},show:function(){
	    				var self=this;	
	    				this.toolTipShow=true;
    			    	$(this.$result).stop(true,true).show(200);
    			    	//$(this.$result).animate({top:this.tooltipOffsetY},200);
    			    
	    			},
	    			closeMove:function(){
	    				var self=this;
	    				clearTimeout(this.touchShade);
	    				this.touchShade=null;
	    				this.clearRect();
						this.toolTipShow=false;
	    				this.$result.stop(true,true).hide(200);
	    			},formatter:function(params){
	    				
	    			}

	    	}
		    obj.init(options)
		  };

		  
$.fn.chartTooltip = function ( option ) {
			  new ChartTooltip(this, option);
  };
  
  /**
   * 提供第二种调用方式
   */
  $.chartTooltip=function(option){
	  chartTooltip.init(option);
  }

}(window.jQuery)
