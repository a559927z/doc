 require(['jquery','echarts','echarts/chart/bar'], function($,echarts) {
	
	/************************图例1************************/
	var option = {
			title : {
		        text: '幅度统计'
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            splitLine : {show : false},
		            data : ['高管','中干','总监','组长'],
		            axisTick  : {show : false}
		        }
		    ],
		    grid : {borderWidth : 0},
		    yAxis : [{
		       splitLine : {show : false},
               axisLine : {show : false},
               axisLabel :{show :false}
		    }],
		    series : [
		        {
		        	barWidth:30,
		            name:'幅度统计',
		            type:'bar',
		            data:[27,18,12,8]
		        }
		    ]
	 };
	 var chart = echarts.init(document.getElementById("main"));
	 var color = require('zrender/tool/color'); //颜色
	 var BrokenLine = require('zrender/shape/BrokenLine');//直线
	 var RectangleShape = require('zrender/shape/Rectangle');//矩形
	 var PolygonShape = require('zrender/shape/Polygon');//多边型
	 
	 var chartbg = echarts.init(document.getElementById("mainbg"));//第一层 背景层
	 var zr  = chartbg.getZrender();
	 
	 var linebg = echarts.init(document.getElementById("linebg")); //第三层 直线层
	 var linezr = linebg.getZrender();
	 
	 //画背景与直线
	 function drawBackgroundAndLine(elem,bgzr,linezr,chart,value){
		 var width = $(elem).width();
		 var height = $(elem).height();
		 var grid = chart.getOption().grid;
		 var x = grid.x ,y=grid.y,x2=grid.x2,y2=grid.y2;
		 var canvas_y = height-y-y2;
		 var canvas_x = width-x-x2;
		 var maxVal = chart.component.yAxis._axisList[0]._max;
		 y = (1-value/maxVal)*canvas_y;
		 //画上部分区域
		 drawRectangleShape(bgzr,x,grid.y,canvas_x,canvas_y,color.getColor(2));
		 //画下部分
		 drawRectangleShape(bgzr,x,grid.y+y,canvas_x,canvas_y-y,color.getColor(3));
		 //画线
		 drawLine(linezr,x,grid.y+y,width-grid.x,grid.y+y,value,'red');
		 bgzr.render();
		 linezr.render();
	 }
	 /****画直线*****/
	 function drawLine(zr,x,y,x2,y2,value,color){
		 zr.addShape(new BrokenLine({
			    style : {
			        pointList : [[x,y],[x2,y2]],
			        lineWidth : 1,
			        zlevel: 2,
			        hoverable : false,
			        clickable : false,
			        color : color,
			        draggable : false,//是否可以拖
			        lineType : 'dashed',    // default solid
			        text : value
			    }
		 }));
		 
	 }
	 
	 /****画矩形******/
	 function drawRectangleShape(zr,x,y,w,h,color){
		 zr.addShape(new RectangleShape({
			 style :{
				 x : x,
				 y : y ,
				 width : w,
			     height: h,
			     brushType : 'both',
			     color : color,
			     strokeColor : color,
			     lineWidth : 0,
			     draggable : false,//是否可以拖
			     lineJoin : 'round',
			 }
		 }));
	 }
	 
	 chart.setOption(option);
	 drawBackgroundAndLine($("#main"),zr,linezr,chart,12);
	
	 
	 //TO DO 由于需求还看不懂,算法还没有写,不知道需传入多少参数
	 /*************************图例2*****************************/
	 var  option2 = {
				title : {
			        text: '两年对比情况'
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            splitLine : {show : false},
			            data : ['2013','2014'],
			            axisTick  : {show : false}
			        }
			    ],
			    grid : {borderWidth : 0},
			    yAxis : [{
			       splitLine : {show : false},
	               axisLine : {show : false},
	               axisLabel :{show :false}
			    }],
			    series : [
			        {
			        	barWidth:30,
			            name:'两年对比情况',
			            type:'bar',
			            data:[47.4,69.4]
			        }
			    ]
		 };
	 var chart2 = echarts.init(document.getElementById("main2"));
	 var chartbg2 = echarts.init(document.getElementById("mainbg2"));//第一层 背景层
	 var linebg2 = echarts.init(document.getElementById("linebg2"));//第三层 画线
	 var zr2 = chartbg2.getZrender();
	 var linezr2 = linebg2.getZrender();
	 zr2.addShape(new PolygonShape({
		    style : {
		        pointList : [[80,240],[80,220],[400, 50], [400,240]],
		        brushType : 'both',
		        color : color.getColor(3),
		        strokeColor : color.getColor(3),
		        lineWidth : 1,
		        textPosition : 'inside'     // default top
		    },
		    draggable:true
		}));
	 zr2.addShape(new PolygonShape({
		    style : {
		        pointList : [[80,220],[80,40],[400,40], [400,80]],
		        brushType : 'both',
		        color : color.getColor(4),
		        strokeColor : color.getColor(4),
		        lineWidth : 1,
		        textPosition : 'inside'     // default top
		    },
		    draggable:true
		}));
	 linezr2.addShape(new BrokenLine({
		    style : {
		        pointList : [[80,220],[400,80]],
		        lineWidth : 1,
		        zlevel: 2,
		        hoverable : false,
		        clickable : false,
		        color : 'red',
		        draggable : false
		    }
	 }));
	 chart2.setOption(option2);
	 zr2.render();
	 linezr2.render();
	 
	 
	 /*****************瀑布流显示方式*******************************************************************/
	var result =  [ {
		  "id" : "fcb4d31b3470460f93be81cf1dd64cf0",
		  "organizationName" : "利润合计",
		  "avgValue" : null,
		  "yearMonth" : null,
		  "profit" : 480.0,
		  "eqEmpNum" : 33.87,
		  "benefitValue" : 14.1718,
		  "changeValue" : null,
		  "salesAmount" : 530.0,
		  "isSelfNode" : true
		}, {
		  "id" : "4e301608d90d4327aa9962f0d18eef8d",
		  "organizationName" : "深圳项目管理中心",
		  "avgValue" : null,
		  "yearMonth" : null,
		  "profit" : 170.0,
		  "eqEmpNum" : 0.0,
		  "benefitValue" : null,
		  "changeValue" : null,
		  "salesAmount" : 176.0,
		  "isSelfNode" : false
		}, {
		  "id" : "4f4deacb06ae457dad7ab8db27dea35e",
		  "organizationName" : "上海项目管理中心",
		  "avgValue" : null,
		  "yearMonth" : null,
		  "profit" : 120.0,
		  "eqEmpNum" : 0.0,
		  "benefitValue" : null,
		  "changeValue" : null,
		  "salesAmount" : 121.5,
		  "isSelfNode" : false
		}, {
		  "id" : "9f570168958c4f23a7d2b86a52f8b79b",
		  "organizationName" : "广州项目管理中心",
		  "avgValue" : null,
		  "yearMonth" : null,
		  "profit" : 90.0,
		  "eqEmpNum" : 33.87,
		  "benefitValue" : 2.6572,
		  "changeValue" : null,
		  "salesAmount" : 97.5,
		  "isSelfNode" : false
		} ];
	benefitOption = {
			legend : {
				data : [ '营业利润'],
				y : 'bottom',
				selectedMode : false
			},
			color : [ '#23C6C8'],
			grid : {
				borderWidth : 0
			},
			xAxis : [ {
				type : 'category',
				splitLine : false,
				axisLine : {
					show : true,
					onZero : false,
					lineStyle : {
						color : '#D9D9D9'
					}
				},
				axisTick : {
					show : true,
					lineStyle : {
						color : '#D9D9D9'
					}
				},
				axisLabel : {
					show : true,
					itemStyle : {
						color : '#BEBEBE'
					}
				},
				data : []
			} ],
			yAxis : [ {
				type : 'value',
				splitLine : false,
				axisLine : {
					show : false
				},
				axisTick : {
					show : false
				},
				axisLabel : {
					show : true
				}
			}, {
				type : 'value',
				scale : true,
				splitLine : false,
				splitNumber : 4,
				axisLine : {
					show : false
				},
				axisTick : {
					show : false
				},
				axisLabel : {
					show : true
				}
			} ],
			series : [ {
				name : '辅助',
				type : 'bar',
				barCategoryGap : '45%',
				barMaxWidth : 60,
				stack : '营业利润',
				itemStyle : {
					normal : {
						barBorderColor : 'rgba(0,0,0,0)',
						color : 'rgba(0,0,0,0)'
					},
					emphasis : {
						barBorderColor : 'rgba(0,0,0,0)',
						color : 'rgba(0,0,0,0)'
					}
				},
				data : [  ]
			}, {
				name : '营业利润',
				type : 'bar',
				barCategoryGap : '45%',
				barMaxWidth : 60,
				stack : '营业利润',
				itemStyle : {
					normal : {
						label : {
							show : true,
							position : 'inside'
						}
					}
				},
				data : []
			}]
		};
	var orgBenefitChart = echarts.init(document.getElementById('orgBenefitChart'));
	/**
	 * 初始化 当前组织人均效益 柱状/折线混合图
	 */
	function initOrgBenefitChart(result) {
		var xAxisData = [];
		// 营业利润
		var profitData = [];
		// 营业利润差值（总利润-当前利润,用于瀑布流显示方式）
		var minusProfitData = []
		// 营业利润总额
		var minusProfit = 0;
		$.each(result, function(i, item) {
			if (i == 0) {
				minusProfit = item.profit;
				minusProfitData.push(0);
			} else {
				minusProfit -= item.profit;
				minusProfitData.push(minusProfit.toFixed(2));
			}
			xAxisData.push(item.organizationName);
			// 营业利润
			profitData.push({
				id : item.id,
				value : item.profit
			});
		});
		benefitOption.xAxis[0].data = xAxisData;
		benefitOption.series[0].data = minusProfitData;
		benefitOption.series[1].data = profitData;
		orgBenefitChart.setOption(benefitOption, true);
	}
	initOrgBenefitChart(result);
	 
	 
});