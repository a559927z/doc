define(['vue2x', 'utils', 'underscore', 'echarts3'],
   function (Vue, utils, _, echarts) {
	var myComponent = Vue.extend({
		template:'<div class="chart"><slot></slot></div>',
        props:['list','listen'], 
		data: function(){
			return{
				init: null,
				chartObj: null,
				option:{
					tooltip:{
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
					},
	                title: {
	                    text: ''
	                },
	                legend: {
	                    right: 10,
	                    data: []//可多个
	                },
	                xAxis: {
	                    type: 'value'
	                },
	                yAxis: {
	                    type: 'category',
	                    data: []
	                },
	                series: [],
				},
				demoseries: {
	                name: '',
	                type: 'bar',
	                stack: '总量',
	                label: {
	                    normal: {
	                        show: true,
	                        position: ''
	                    }
	                },
	                data: []
	            },
			}
			
		},
		watch: {
			'list' :function(val, oval){
				this.list=val;
				this.render();
			}
        },
		mounted: function(){
            this.render();
        },
		methods:{
			render: function(){
				var _this = this;
                
                if (this.loading ) {
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
                this.option.yAxis.data = _this.list.yAxis;
                var tempserie = [];
                for(i=0; i<_this.list.data.length; i++){

                	var s = $.extend(true, {}, _this.demoseries);
                    s.name = _this.list.data[i].name;
                    s.label.normal.position = _this.list.data[i].labelposition;
                    s.data = _this.list.data[i].data;
                    tempserie.push(s);
                    //放入length
                    this.option.legend.data.push(_this.list.data[i].name);
                }
                this.option.series = tempserie;
                this.chartObj.clear();
                this.chartObj.setOption(_this.option);

               /* this.chartObj.on('click', function (params) {
                	_this.$emit('childsay',"123");
                });*/
			},
			
		}
	});
	return myComponent;
});