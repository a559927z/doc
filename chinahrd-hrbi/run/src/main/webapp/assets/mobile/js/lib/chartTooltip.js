/*
 * Copyright (c) 2016 htpeng
 * Date: 2016-06-13
 *  手机端echart tooltip 组件
 */
!function($){
	  var ChartTooltip = function (element, options) {
		    options.id=element;
		    var obj={
	    			load:false,
	    			style:"top",
	    			lock:false,
	    			render:function(parm){
	    				var valueSize=parm.valueSize;
	    				if(valueSize==undefined){
	    					valueSize="";
	    				}
	    				var valueClass="chart-tooltip-value"+valueSize;
	    		    	var result='<div class="chart-tooltip" >';
	    		    	if(parm.title!=undefined&&parm.title!="undefined"&&parm.title!="")
	        				result+='<div class="chart-tooltip-title">'+parm.title+'</div>';
	        			if(!_.isEmpty(parm.cols)){
	        				var len=parm.cols.length;
	        				len=len>0?len:1;
	        				var width=100/len;
	        				$.each(parm.cols,function(i,o){
	        					var unit=o.unit==undefined?"":o.unit;
	        		    		result+='<div class="chart-tooltip-col" style="width:'+width+'%">';
	        		    			result+='<div class="chart-tooltip-name"><label>'+o.name+'</label></div>';
	        		    			result+='<div class="chart-tooltip-value-col">';
	        		    				result+='<label class="'+valueClass+'">'+o.value+'</label>';
	        		    				result+='<label class="chart-tooltip-unit">'+unit+'</label>';
	        		    			result+="</div>";
	        		    		result+='</div>';
	        		    	});
	        			}
	    	    	
	        			result+='</div>';
			        	return result;
			        },renderPie:function(parm){
			        	var self=this;
			        	var color=parm.color;
			        	var result = '<div class="chart-tooltip" >';
			        	if(parm.title!=undefined&&parm.title!="undefined"&&parm.title!="")
						result += '<div class="chart-tooltip-title">'+ parm.title + '</div>';
						result += '<div>';
						if (!_.isEmpty(parm.cols)) {
							$.each(parm.cols,function(i, o) {
								result += '<div class="pie-tooltip" style="width:'+self.pieSplitWidth+';">';
								result += '<label style="background-color: '+ color[i] + ';"></label>';
								result += '<span>'+ o.name + '</span>';
								result += '<span>'+ o.value + '</span>';
								result += '<span>'+ o.rate + '</span>';
								result += '</div>';
							});
						}
						result += '</div>';
						if(parm.note!=undefined&&$.trim(parm.note)!=""){
							result += '<span class="pie-contrastDetail">'+parm.note+'</span>';
						}
						
					result += '<div>';
						result += '</div>';
						return result;
			        },
	    			width:$(window).width(),
	    			height:118,
	    			tooltipOffsetX:3,
	    			barWidth:15,      //默认柱状图和折线图宽度
	    			pieSplitNumber:1,//pie图弹出详情每行显示数目
	    			horizontal:true, //图片是否水平展示
	    			regularX:"",     //指定是否水平显示
	    			touchShade:null,  //弹出遮罩
	    			shadeColor:'rgba(4,4,4,0.2)', //弹出遮罩颜色
	    			shadeReactColor:'rgba(4,4,4,1)', //弹出遮罩颜色
	    			scal:false, //是否完成缩放
	    			showEvent:'touchstart',
	    			closeEvent:'touchend',
	    			closeAnimate:null,
	    			pie:false,       //是否为饼图
	    			showAnimate:null,
	    			toolTipShow:false,
	    			toolTipTop:null,
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
	       				if(this.pie){
       						this.listenerPie();
       						return;
	       				}
	       				//console.log(this.chart)
	       				var option=this.chart._optionRestore;
	       				if(option==undefined){
	       					option=this.chart._model.option;
	       				}
	       				this.option=option;
	       				if(option==undefined){
	       					if(this.pie){
	       						this.listenerPie();
	       					}else{this.listener();}
	       					
	       				}else{
	       					this.series=option.series;
	       					if(option.series[0].type=="pie"){
		       					this.pie=true;
		       					if(this.pieSplitNumber>1){
		       						this.pieSplitWidth=(100/this.pieSplitNumber)+"%";
		       					}else{
		       						this.pieSplitWidth="99%";
		       					}
		       					this.listenerPie();
		       				}else{
		       					this.listener();
		       				}
	       				}
	       				
	       				
	    			},validRange:function(){  
	    				var defalutGrid=this.option.grid;
	    				if(echartsVersion>3){
	    					defalutGrid=this.option.grid[0];
	    				}
	    				var grid=$.extend({x:80,y:80,x2:80,y2:80},defalutGrid);
	    				this.series=this.option.series;
	    				var xAxis=this.option.xAxis;
	    				var yAxis=this.option.yAxis;
	    				var self=this;
	    				this.vaild=[];
	    				this.shadeValid=[];
	    				if(this.regularX==""){
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
	    				}else if(!this.regularX){
	    					this.nAxis=yAxis[0];
	    					this.horizontal=false;
	    				}else {
	    					this.nAxis=xAxis[0];
	    					this.horizontal=true;
	    					
	    				}
        				this.getGraphics(grid.y);
	
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
	    				
	    			},validArcRange:function(){
	    				var radius=this.series[0].radius;
	    				this.getGraphics();
	    				if(typeof radius ==="object"){
	    					radius=parseInt(radius[1])/100 ;
	    				}else{
	    					radius=parseInt(radius)/100 ;
	    				}
	    				var center=this.series[0].center;

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
	    				radius=this.chartWidth>this.chartHeight?this.chartHeight*radius:this.chartWidth*radius;
	    				this.radius=radius/this.scaleX
	    				this.ArcPoint={x:this.chartWidth*left,y:this.chartHeight*top}
	    			

	    			},appendShade:function(width,height){
	    		   		this.$canvas=$("<canvas width='"+width+"' height='"+height+"'></canvas>")
			    		this.$canvas.addClass("chart-tooltip-canvas");
			    		this.$element.children(":first").append(this.$canvas);
			    		this.$canvas.width(this.chartWidth);
			    		this.$canvas.height(this.chartHeight);
	    				this.graphics=this.$canvas[0].getContext("2d");
	    				this.graphics.scale(width/this.$element.width(),height/this.$element.height());
	    				this.$canvasRect=$("<canvas width='"+width+"' height='"+height+"'></canvas>")
			    		this.$canvasRect.addClass("chart-tooltip-canvas");
			    		this.$element.children(":first").append(this.$canvasRect);
			    		this.$canvasRect.width(this.chartWidth);
			    		this.$canvasRect.height(this.chartHeight);
	    				this.graphicsRect=this.$canvasRect[0].getContext("2d");
	    				this.graphicsRect.scale(width/this.$element.width(),height/this.$element.height());
	    				this.scaleX=width/this.$element.width();
	    				this.scaleX=2  //检测到Android手机 使用计算的缩放不正确  强制定为2
	    			},removeShade:function(){
	    				this.$element.children(":first").children(".chart-tooltip-canvas").remove();
	    				this.$element.children(":first").children(".chart-tooltip-canvas-rect").remove();
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
			    		this.$result.css("min-height",this.height);
			    		//this.$result.height(this.height);
			    		this.$resultWidth=$(window).width()-2*this.tooltipOffsetX;
			    		this.$result.width(this.$resultWidth);
			    		
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
    					$.each(this.vaild,function(i,obj){
    						if(!exist&&validRange>obj.validMin&&validRange<obj.validMax&&obj.min-5<=val&&obj.max+5>=val){
    							exist=true;
    							index=i;
    						}
    					});
    					if(exist){
    						var mid=this.vaild[index].mid;
    						if(this.horizontal){
    							this.fillRect((this.shadeValid[index]-1),this.rect.y-2,this.rect.width+2,this.rect.height,(this.shadeValid[index]-2),this.rect.y-2,this.rect.width+4,this.rect.height);
    						}else{
    							this.fillRect(this.rect.x,(this.shadeValid[index]-top-1),this.rect.width,this.rect.height+2,this.rect.x,(this.shadeValid[index]-top-2),this.rect.width,this.rect.height+4);
    						}
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
    						self.show();
    						self.lastX=x;
    						self.lastY=y;
    					}
	    			},touchChartPie:function(e){
	    				var self=this;
	    				var top=(this.$element.offset().top);
    					var _touch = e.originalEvent.targetTouches[0]; 
    					var y= _touch.pageY-top;
    					var x=_touch.pageX+this.offsetX;
    					var range=Math.sqrt((x-this.ArcPoint.x)*(x-this.ArcPoint.x)+(y-this.ArcPoint.y)*(y-this.ArcPoint.y))
    					if(range<this.radius){
    						this.fillArc(this.ArcPoint,this.radius);
    						var mid=$(window).width()*this.ArcLeft-3;
    						this.renderData(mid,0,self.series);
    						self.show();
    					}
	    			},touchChartAll:function(){
						this.getGraphics();
	
						var mid=$(window).width()/2;
						this.fillRect(0,0,this.chartWidth,this.chartHeight,0,0,this.chartWidth,this.chartHeight);
						this.renderData(mid,0,this.series);
						this.show();
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
//	        				self.lockScreen(false)
//	        				window.screenScroll=true;
	    				});
	    			},listenerPie:function(){
	    				var self=this;
	    				this.$element.unbind("touchstart").on("touchstart", function(e) {
	    					self.validArcRange();
	    					self.touchChartPie(e);
	    				})

	    				this.$element.unbind("touchend").on("touchend", function(e) {
	        				self.closeMove();
//	        				self.lockScreen(false)
//	        				window.screenScroll=true;
	    				});
	    			},listenerAll:function(){
	    				var self=this;
	    				this.$element.unbind("touchstart").on("touchstart", function(e) {

	    					self.touchChartAll();
	    				})

	    				this.$element.unbind("touchend").on("touchend", function(e) {
	        				self.closeMove();
//	        				self.lockScreen(false)
//	        				window.screenScroll=true;
	    				});
	    				
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
	    			},fillRect:function(x,y,w,h,x1,y1,w1,h1){
						this.clearRect();
						this.clearRect();
						this.graphics.beginPath(); 
						this.graphics.fillStyle = this.shadeColor;
						this.graphics.fillRect(x,y,w,h);
						this.graphics.closePath();
						
						this.graphicsRect.beginPath();
						this.graphicsRect.fillStyle = this.shadeReactColor;
						this.graphicsRect.strokeRect(x1,y1,w1,h1);
						this.graphicsRect.closePath();
	    			},fillArc:function(point,radius){
	    				this.clearRect();
	    				this.clearRect();
	    				this.graphics.beginPath();
	    				 this.graphics.fillStyle = this.shadeColor;
    				    this.graphics.arc(point.x, point.y, radius+2, 0, 2*Math.PI, false);    
    				    this.graphics.fill();
    				    this.graphics.closePath();
    				    
    				    this.graphicsRect.fillStyle = this.shadeReactColor;
    				    this.graphicsRect.arc(point.x, point.y, radius+2, 0, 2*Math.PI, false);    
    				    this.graphicsRect.stroke();
    				    this.graphicsRect.closePath();
	    			},
	    			renderData:function(mid,index,parm){
	    				if(mid+10>this.$resultWidth){
	    					mid=this.$resultWidth-10;
	    				}
	    				$(this.$result).find(".arrow").css("left",(mid)+"px");
			    		$(this.$result).addClass(this.style);
			    		this.$result.css("left",this.tooltipOffsetX);
			    		if(this.pie){
			    			this.$content.html(this.renderPie(this.formatter(this.series)));
			    		}else{
			    			this.$content.html(this.render(this.formatter(index,parm,this.series)));
			    		}
//			    		console.log($(this.$element).offset().top)
//			    		if(this.toolTipTop==null){
			    			this.toolTipTop=$(this.$element).offset().top-this.$result.height();
			    		if(this.toolTipTop<30)this.toolTipTop=30;
//			    			if(this.pie){
//			    				this.toolTipTop=this.offsetY-3;
//				    			 $.each($(this.$element).parents(".panel").find(".title"),function(){
//				    				 this.toolTipTop+=$(this).outerHeight();
//								 });
//				    		}else{
//				    			var paddingHeight=0;
//				    			$.each($(this.$element).parents(".panel").find(".title"),function(){
//				    				paddingHeight+=$(this).outerHeight();
//								 });
//				    			if(paddingHeight==0){
//				    				paddingHeight=76;
//				    			}
//				    			this.toolTipTop-=paddingHeight;
//				    		}
//			    			if(!window.screenFull){
//			    				this.toolTipTop=this.toolTipTop+118-this.$result.height();
//			    			}
//			    		}
			    		this.$result.css("top",this.toolTipTop);
	    			},clearRect:function(){
	    				this.graphics.closePath(); 
	    				this.graphics.beginPath(); 
	    				this.graphics.clearRect(0,0,this.chartWidth,this.chartHeight);
	    				this.graphics.closePath(); 
	    				this.graphicsRect.closePath(); 
	    				this.graphicsRect.beginPath();
	    				this.graphicsRect.clearRect(0,0,this.chartWidth,this.chartHeight);
	    				this.graphicsRect.closePath(); 
	    				
	    			},show:function(){
	    				var self=this;
	    				if(!self.toolTipShow){
							if(self.touchShade==null){
								self.lockScreen(true)
								self.touchShade=setTimeout(function(){
									self.toolTipShow=true;
			    			    	$(self.$result).stop(true,true).show(200);
    								self.touchShade=null;
        						},200)
							}
						}
	    			},
	    			closeMove:function(){
	    				var self=this;
	    				this.lockScreen(false)
	    				clearTimeout(this.touchShade);
	    				this.touchShade=null;
	    				this.clearRect();
	    				this.clearRect();
						this.toolTipShow=false;
	    				this.$result.stop(true,true).hide(200);
	    			},formatter:function(params){
	    				
	    			},lockScreen:function(bool){
	    				if(!window.screenFull){
	    					window.screenScroll=!bool
    					}
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
