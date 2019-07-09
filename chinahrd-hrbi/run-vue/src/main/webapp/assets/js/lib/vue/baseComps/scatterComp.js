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
					backgroundColor: new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [{
	                    offset: 0,
	                    color: '#f7f8fa'
	                }, {
	                    offset: 1,
	                    color: '#cdd0d5'
	                }]),
	                title: {
	                    text: 'title'
	                },
	                legend: {},
	                xAxis: {
	                    splitLine: {
	                        lineStyle: {
	                            type: 'dashed'
	                        }
	                    }
	                },
	                yAxis: {
	                    splitLine: {
	                        lineStyle: {
	                            type: 'dashed'
	                        }
	                    },
	                    scale: true
	                },
	                series: [],
				},
				demoseries: {
                    name: '1900',
                    data: [],
                    type: 'scatter',
                    symbolSize: function (data) {
                        return Math.sqrt(data[2]) / 5e2;
                    },
                    label: {
                        emphasis: {
                            show: true,
                            formatter: function (param) {
                                return param.data[3];
                            },
                            position: 'top'
                        }
                    },
                    itemStyle: {
                        normal: {
                            shadowBlur: 10,
                            shadowColor: '',
                            shadowOffsetY: 5,
                            color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
                                offset: 0,
                                color: 'rgb(' + parseInt(Math.random()*256) + ', ' + parseInt(Math.random()*256) + ', ' + parseInt(Math.random()*256) + ')'//默认的颜色
                            }])
                        }
                    },
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
		computed:{
			sloop :function(){
				this.setcolor();
			}
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
                this.option.title.text = _this.list.title;
                this.option.legend = $.extend(true,{}, utils.legend);
                this.option.legend.backgroundColor = null;
                this.option.legend.selectMode = true;
                var tmpserie = [];
                for(i=0; i<_this.list.data.length; i++){

                	_this.sloop =$.extend(true, {}, _this.demoseries);
                    _this.sloop.name = _this.list.data[i].year;
                    _this.sloop.data = _this.list.data[i].data;
                    if(!_this.list.data[i].shadowColor)
                    	_this.sloop.itemStyle.normal.shadowColor = _this.list.data[i].shadowColor;
                    else
                    	_this.sloop.itemStyle.normal.shadowColor = 'rgb(' + parseInt(Math.random()*256) + ', ' + parseInt(Math.random()*256) + ', ' + parseInt(Math.random()*256) + ')';
                    this.option.legend.data.push(_this.list.data[i].year);
                    tmpserie.push(_this.sloop);
                }
                this.option.series = tmpserie;
                this.chartObj.clear();
                this.chartObj.setOption(this.option);

               /* this.chartObj.on('click', function (params) {
                	_this.$emit('childsay',"123");
                });*/
			},
			setcolor: function(){
				var _this = this;
				_this.demoseries.itemStyle.normal.color = new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
                    offset: 0,
                    color: 'rgb(' + parseInt(Math.random()*256) + ', ' + parseInt(Math.random()*256) + ', ' + parseInt(Math.random()*256) + ')'//默认的颜色
                }]);
			}
			
		}
	});
	return myComponent;
});