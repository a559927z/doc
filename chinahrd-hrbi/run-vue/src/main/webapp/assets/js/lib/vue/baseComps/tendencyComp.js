/**
 * 趋势状态图
 * 指向上 中 下
 * 
 * 默认
 * 
 * 上箭头绿色
 * 中箭头蓝色
 * 下箭头红色
 * 
 */
define(['jquery','vue2x', 'compUtils', 'underscore', 'echarts3'],
   function ($, Vue, utils, _, echarts) {
	var myComponent = Vue.extend({
		template:'<div class="chart"><slot></slot></div>',
        props:['list','listen'], 
		data: function(){
			return{
				init: null,
				tendencyObj: function (obj, option) {
	                this.obj = obj;
	                this.option = option;
	                this.middleY = this.option.width / 2;
	            },
				obj: null,
				middleY: null,
				tendencyOption : {
				        gap: 4.5, //间隔
				        jianjiao: 16.5, //顶部和底部尖角
				        width: 300,
				        height: 220,
				        x: 10.5,//x轴的起点
				        topColor: 'rgb(92,200,43)',
				        middleColor: 'rgb(41,140,208)',
				        bottomColor: 'rgb(210,76,0)',
				        leftFontStyle: {
				            fontSize: 22,
				            fontColor: '#f0f0f0',
				            textAlign: 'center',
				            textBaseline: 'middle'
				        },
				        rigthFontStyle: {
				            fontSize: 13,
				            fontColor: '#000000',
				            lineColor: '#333333',
				            textAlign: 'left',
				            textBaseline: 'middle'
				        },
				        splitLineW: 40
				    },
			}
			
		},
		watch: {
			'list' :function(val, oval){
				this.list=val;
				this.render();
			},
        },
		mounted: function(){
            this.render();
        },
		methods:{
			createCanvas: function(){
				//清空div
		        $(this.$el).html('');

		        var canvas = document.createElement('canvas');
		        $(this.$el).append(canvas);
		        this.tendencyObj.setCanvasPos(canvas);
		        return canvas.getContext('2d');
			},
			setCanvasPos: function(canvas){
		        canvas.setAttribute('width', this.tendencyObj.option.width);
		        canvas.setAttribute('height', this.tendencyObj.option.height);
		        // 减少reflow
//					var canvasStyle = 'width:' + this.tendencyObj.option.width + 'px;height:' + this.tendencyObj.option.height + 'px;position:absolute;left:0px;top:' + 10 px;
//					canvas.style.cssText += canvasStyle;
				
			},
			drawTopArea: function(ctx, x, y, w, h){
		        ctx.fillStyle = this.tendencyObj.option.topColor;
		        ctx.strokeStyle = this.tendencyObj.option.topColor;
		        ctx.beginPath();
		        ctx.moveTo(x, y);//找到起始点.
		        ctx.lineTo(x - 20, y);//画横线 往左
		        ctx.lineTo(x + w / 2, y - this.tendencyObj.option.jianjiao);//画左上斜线
		        ctx.lineTo(x + w + 20, y);//画右下斜线 往右
		        ctx.lineTo(x + w, y);//画横线 往左
		        ctx.lineTo(x + w, h + y);//画竖线  往下
		        ctx.lineTo(x, h + y);//画左横线
		        ctx.lineTo(x, y);//画向上的线
		        ctx.fill();
		        ctx.stroke();
		        ctx.closePath();
		    },
			drawMiddleArea: function(ctx, x, y, w, h){
		        //75 172 178
		        ctx.fillStyle = this.tendencyObj.option.middleColor;
		        ctx.strokeStyle = this.tendencyObj.option.middleColor;
		        ctx.beginPath();
		        ctx.rect(x, y, w, h);
		        ctx.fill();
		        ctx.stroke();
		        ctx.closePath();
		    },
			drawBottomArea: function(ctx, x, y, w, h){
		        ctx.fillStyle = this.tendencyObj.option.bottomColor;
		        ctx.strokeStyle = this.tendencyObj.option.bottomColor;
		        ctx.beginPath();
		        ctx.moveTo(x, y);//找到起始点.
		        ctx.lineTo(x + w, y);//画横线 往右
		        ctx.lineTo(x + w, h + y);//画竖线  往下
		        ctx.lineTo(x + w + 20, h + y);//画横线 往右
		        ctx.lineTo(x + w / 2, h + y + this.tendencyObj.option.jianjiao);//画左下斜线
		        ctx.lineTo(x - 20, h + y);//画左上斜线
		        ctx.lineTo(x, h + y);//画横线 往右
		        ctx.lineTo(x, y);//画向上的线
		        ctx.fill();
		        ctx.stroke();
		        ctx.closePath();
		    },
			drawFont: function(ctx, x, y, str, fontStyle){
		        ctx.beginPath();
		        if (fontStyle.fontColor != null) {
		            ctx.fillStyle = fontStyle.fontColor;
		        }
		        if (fontStyle.fontSize != null) {
		            ctx.font = fontStyle.fontSize + 'px 微软雅黑';
		        }
		        if (fontStyle.textBaseline != null) {
		            ctx.textBaseline = fontStyle.textBaseline;
		        }
		        if (fontStyle.textAlign != null) {
		            ctx.textAlign = fontStyle.textAlign;
		        }
		        ctx.fillText(str, x, y);
		    },
			drawRightLine: function(ctx, x, y, w){
		        ctx.beginPath();
		        ctx.strokeStyle = this.tendencyObj.option.rigthFontStyle.lineColor;
		        ctx.moveTo(x, y);
		        ctx.lineTo(x + w, y);//画线
		        ctx.stroke();
		    },
		    initial: function(){
		        //获取中心位置
		        var ctx = this.tendencyObj.createCanvas();
		        var w = this.tendencyObj.option.width / 3;
		        var x = this.tendencyObj.option.x;
		        var gap = this.tendencyObj.option.gap;
		        var obj = this.tendencyObj.obj;
		        if (obj.data == null) {
		            obj = new Object();
		            obj ={  
		            		"data":{
		                    	"rise" : 0,
		                    	"down" : 0,
		                    	"equal" : 0
		                    	  },
		  	                    "label":{
			                    	"rise" : "有所上涨",
			                    	"equal" : "维持现状",
			                    	"down" :"出现下滑"
			                    },
			                    "unit":"单位"
		            }
		        }
		        var canvasCenterY = this.tendencyObj.option.height / 2;
		        //中间部分
		        var mh = 50;//高
		        var my = canvasCenterY;//获取中间位置
		        my = my - mh / 2;//开始位置 左上角
		        this.tendencyObj.drawMiddleArea(ctx, x, my, w, mh);

		        //底部b
		        var bh = 40;//高
		        var by = my + mh + gap;//中间的开始位置+中间部分的高+间隔
		        this.tendencyObj.drawBottomArea(ctx, x, by, w, bh);

		        //顶部
		        var th = 40;//高
		        var ty = my - th - gap;//中间的开始位置+中间部分的高+间隔
		        this.tendencyObj.drawTopArea(ctx, x, ty, w, th);

		        //写文字  middlearea  top bootom
		        //Y的部分 还可以加上文字的
		        var allCenterX = x + w / 2;
		        var txtTopY = ty + th / 2;
		        var txtMidY = canvasCenterY;
		        var txtBotY = by + bh / 2;
		        //比例
		        var count = obj.data.rise + obj.data.down + obj.data.equal;
		        var pRise = obj.data.rise / count;
		        var pDown = obj.data.down / count;
		        var pNoChange = 1 - pRise - pDown;

		        pNoChange = isNaN(pNoChange) ? 0 : pNoChange;
		        pRise = isNaN(pRise) ? 0 : pRise;
		        pDown = isNaN(pDown) ? 0 : pDown;

		        pRise = Math.round(pRise * 100) + '%';
		        pDown = Math.round(pDown * 100) + '%';
		        pNoChange = Math.round(pNoChange * 100) + '%';

		        this.tendencyObj.drawFont(ctx, allCenterX, txtMidY, pNoChange, this.tendencyObj.option.leftFontStyle);
		        this.tendencyObj.drawFont(ctx, allCenterX, txtTopY, pRise, this.tendencyObj.option.leftFontStyle);
		        this.tendencyObj.drawFont(ctx, allCenterX, txtBotY, pDown, this.tendencyObj.option.leftFontStyle);

		        //写右边的文字
		        var allTextX = x + w + this.tendencyObj.option.splitLineW;
		        this.tendencyObj.drawFont(ctx, allTextX, txtTopY, obj.label.rise + '，' + obj.data.rise + '' + obj.unit, this.tendencyObj.option.rigthFontStyle);
		        this.tendencyObj.drawFont(ctx, allTextX, txtMidY, obj.label.equal + '，' + obj.data.equal + '' + obj.unit, this.tendencyObj.option.rigthFontStyle);
		        this.tendencyObj.drawFont(ctx, allTextX, txtBotY, obj.label.down + '，' + obj.data.down + '' + obj.unit, this.tendencyObj.option.rigthFontStyle);

		        //画右边的线
		        var allLineX = x + w;
		        this.tendencyObj.drawRightLine(ctx, allLineX, txtMidY, this.tendencyObj.option.splitLineW);
		        this.tendencyObj.drawRightLine(ctx, allLineX, txtTopY, this.tendencyObj.option.splitLineW);
		        this.tendencyObj.drawRightLine(ctx, allLineX, txtBotY, this.tendencyObj.option.splitLineW);
		    },
	        setTendencyData: function (obj) {
	            this.tendencyObj = new this.tendencyObj(obj, this.tendencyOption);
	            this.tendencyObj.initial();
	        },
	        makeacallback: function(cc){
	        	return cc;
	        },
			render: function(){
				var _this = this;
                if (this.$parent.loading ) {
                    return;
                }
                if (!this.init) {
                	//创建画布
                	this.tendencyObj.prototype.createCanvas = function(){return _this.createCanvas();}
                	//设置画布样式
                	this.tendencyObj.prototype.setCanvasPos = function(canvas){return _this.setCanvasPos(canvas);}
                	//画顶部的区域
                	this.tendencyObj.prototype.drawTopArea = function(ctx, x, y, w, h){return _this.drawTopArea(ctx, x, y, w, h);}
                	//画中间的区域
                	this.tendencyObj.prototype.drawMiddleArea = function(ctx, x, y, w, h){return _this.drawMiddleArea(ctx, x, y, w, h);}
                	//画底部的区域
                    this.tendencyObj.prototype.drawBottomArea = function(ctx, x, y, w, h){return _this.drawBottomArea(ctx, x, y, w, h);}
                    //画文字
                    this.tendencyObj.prototype.drawFont = function(ctx, x, y, str, fontStyle){return _this.drawFont(ctx, x, y, str, fontStyle);}
                    //画右边的线
                	this.tendencyObj.prototype.drawRightLine = function(ctx, x, y, w){return _this.drawRightLine(ctx, x, y, w);}
                	this.tendencyObj.prototype.initial = function(){return _this.initial();}
					if(!_.isEmpty(this.listen)){
						this.tendencyObj.on(this.listen.type,this.listen.call);
					}
                    this.init = true;
                	
                }
                _this.setTendencyData(this.list);
			},
			
		}
	});
	return myComponent;
});