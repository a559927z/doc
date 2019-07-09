define(['jquery','vue2x', 'compUtils', 'underscore', 'echarts3'],
   function ($, Vue, utils, _, echarts) {
	var myComponent = Vue.extend({
		template:'<div class="chart"><slot></slot></div>',
        props:['list','listen'], 
		data: function(){
			return{
				init: null,
				chartObj: null,
				option:{
				    tooltip : {
				        formatter: "{b} : {c}%"//{a} <br/>{b} : {c}%
				    },
				    toolbox: false,					
	                series: [],
				},
				demoseries: {
                    name: '',//formatter {a}
                    type: 'gauge',
                    max: 25,
                    splitNumber: null,
                    axisLine: {            // 坐标轴线
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: utils.axisLine_lineStyle_color,
                            width: 6
                        }
                    },
                    axisTick: {            // 坐标轴小标记
                        splitNumber: 1,   // 每份split细分多少段
                        length: 6        // 属性length控制线长
                    },
                    axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
                        show: true,
                        formatter: function (v) {
                            if (v == 0) return '';
                            return Math.round(v);
                        },
                        textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                            color: utils.axisLabel_textStyle_color
                        }
                    },
                    pointer: {
                        length: '80%',
                        width: 4,
                        color: utils.pointer_color
                    },
                    detail: false,
                    data: [{value: 4.8}]//formatter {b}{c}****{value: 4.8, name: '完成率'}****
                },
                sloop: null,
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
			render: function(){
				var _this = this;
                if (this.$parent.loading ) {
                    if (this.chartObj) {
                        this.chartObj.clear();
                    }
                    return;
                }
                if (!this.init) {
                    this.chartObj = echarts.init(this.$el);
					if(!_.isEmpty(this.listen)){
				        var ecConfig = require('echarts/config');  
						this.chartObj.on(this.listen.type,this.listen.call);
					}
                    this.init = true;
                }

                if(_this.list == undefined || _this.list == null || _this.list.length == 0) return;
                //
                var tmpserie = [];
                for(i=0; i<_this.list.data.length; i++){

                	_this.sloop =$.extend(true, {}, _this.demoseries);
                    _this.sloop.name = _this.list.data[i].name;
                    _this.sloop.data.value = _this.list.data[i].value;
                    tmpserie.push(_this.sloop);
                }
                this.option.series = tmpserie;
                this.chartObj.clear();
                this.chartObj.setOption(this.option);

               /* this.chartObj.on('click', function (params) {
                	_this.$emit('childsay',"123");
                });*/
			}
			
		}
	});
	return myComponent;
});