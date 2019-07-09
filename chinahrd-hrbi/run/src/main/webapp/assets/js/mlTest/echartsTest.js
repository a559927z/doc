/*
 * echarts 测试 
 */
require(
		[ "jquery", "echarts", "echarts/chart/pie", "echarts/chart/line",
				"echarts/chart/bar", "echarts/chart/scatter",
				'echarts/chart/map', "zrender", "bootstrap", "jgGrid",
				"underscore", "datetimepicker" ],
		function($, echart) {

			var webRoot = G_WEB_ROOT;
			/*
			 * 饼图设置及测试用例
			 */
			var colorPie = [ "#0b98e0", "#00bda9", "#4573a7", "#92c3d4",
					"#de6e1b", "#ff0084", "#af00e1", "#8d55f6", "#6a5888",
					"#2340f3" ];
			// 测试1 demo
			var option = {
				title : {
					// text : '某站点用户访问来源',
					// subtext : '纯属虚构',
					// x : 'center'
					show : false
				},
				// 提示框
				tooltip : {
					// trigger : 'item',
					// formatter : "{a} <br/>{b} : {c} ({d}%)"
					show : false
				},
				calculable : false,
				// 提示图例
				legend : {
					orient : 'horizontal',
					// 居于上下左右分别用x,y标识，可用20，20%，或left，right，bottom，top，center等并表示位置
					x : 'center',
					y : 'bottom',
					// 动态显示饼图
					// selectedMode : false,
					data : [ {
						name : '直接访问',
						icon : 'bar',
						textStyle : {
							fontWeight : 'bold',
							color : 'green'
						}
					}, '邮件营销', '联盟广告', '视频广告', '搜索引擎' ]
				},
				series : [ {
					name : '访问来源',
					type : 'pie',
					radius : '70%',
					center : [ '50%', '50%' ],
					data : [ {
						value : 335,
						name : '直接访问'
					}, {
						value : 310,
						name : '邮件营销'
					}, {
						value : 234,
						name : '联盟广告'
					}, {
						value : 135,
						name : '视频广告'
					}, {
						value : 1548,
						name : '搜索引擎'
					} ],

					// 文本标签和文本标识线条
					itemStyle : {
						normal : {
							label : {
								show : false
							},
							labelLine : {
								show : false
							}
						}
					}

				/*
				 * , itemStyle : { emphasis : { shadowBlur : 10, shadowOffsetX :
				 * 0, shadowColor : 'rgba(0, 0, 0, 0.5)' } }
				 */
				} ],
				color : colorPie
			};
			var echartsPieObj = {
				pieChart1 : echart.init(document.getElementById("pie-1")),
				pieOption1 : option,
				init : function() {
					var self = this;
					self.setOptionFun();
				},
				setOptionFun : function() {
					var self = this;
					self.pieChart1.setOption(self.pieOption1);
				}
			}
			echartsPieObj.init();

			// 测试1 demo
			var pieOption2 = {
				legend : {
					orient : 'horizontal',
					x : 'center',
					y : 'bottom',
					data : [ '优化管理', '技术升级', '外部咨询', '其他' ]
				},
				series : [ {
					name : '访问来源',
					type : 'pie',
					radius : '70%',
					center : [ '50%', '50%' ],
					data : [ {
						value : 335,
						name : '优化管理, ' + 10
					}, {
						value : 310,
						name : '技术升级'
					}, {
						value : 234,
						name : '外部咨询'
					}, {
						value : 135,
						name : '其他'
					} ],
				// itemStyle : {
				// normal : {
				// labelLine : {
				// show : false
				// }
				// }
				// }
				} ],
				color : colorPie
			};
			var echartsPie2Obj = {
				pieChart2 : echart.init(document.getElementById("pie-2")),
				pieOption2 : pieOption2,
				init : function() {
					var self = this;
					self.setOptionFun();
				},
				setOptionFun : function() {
					var self = this;
					self.pieChart2.setOption(self.pieOption2);
				}
			}
			echartsPie2Obj.init();

			// 测试2 demo
			var echartsLineObj = {
				lineChart1 : echart.init(document.getElementById("line-1")),
				lineOption1 : {
					title : {
						text : '未来一周气温变化',
						subtext : '纯属虚构',
						x : 'center'
					},
					tooltip : {
						trigger : 'axis'
					},
					legend : {
						x : 'right',
						data : [ '最高气温', '最低气温' ]
					},
					toolbox : {
						show : true,
						feature : {
							dataZoom : {},
							dataView : {
								readOnly : false
							},
							magicType : {
								type : [ 'line', 'bar' ]
							},
							restore : {},
							saveAsImage : {}
						}
					},
					xAxis : {
						type : 'category',
						boundaryGap : false,
						data : [ '周一', '周二', '周三', '周四', '周五', '周六', '周日' ]
					},
					yAxis : {
						type : 'value',
						axisLabel : {
							formatter : '{value} °C'
						}
					},
					series : [ {
						name : '最高气温',
						type : 'line',
						smooth : true,
						data : [ 11, 11, 15, 13, 12, 13, 10 ],
						markPoint : {
							data : [ {
								type : 'max',
								name : '最大值'
							}, {
								type : 'min',
								name : '最小值'
							} ]
						},
						markLine : {
							data : [ {
								type : 'average',
								name : '平均值'
							} ]
						}
					}, {
						name : '最低气温',
						type : 'line',
						// smooth: true,
						data : [ 1, -2, -2, -8, -3, 2, 0 ],
						markPoint : {
							data : [ {
								name : '周最低',
								value : -2,
								xAxis : 1,
								yAxis : -1.5
							} ]
						},
						markLine : {
							data : [ {
								type : 'average',
								name : '平均值'
							} ]
						}
					} ]
				},
				init : function() {
					var self = this;
					self.setOptionFun();
				},
				setOptionFun : function() {
					var self = this;
					self.lineChart1.setOption(self.lineOption1);
				}
			}
			echartsLineObj.init();

			// 测试3 demo
			var echartsLine2Obj = {
				lineChart2 : echart.init(document.getElementById("line-2")),
				lineOption2 : {
					/*
					 * title : { text : '未来一周气温变化', subtext : '纯属虚构', x :
					 * 'center' },
					 */
					// 提示框组件
					/*
					 * tooltip : { show:false, trigger : 'item' },
					 */
					// 图例组件
					legend : {
						x : 'right',
						data : [ '投入产出比' ]
					},

					// 外边框设置0
					grid : {
						borderWidth : 0,
						x : 50,
						y : 50
					},
					xAxis : {
						type : 'category',
						// x轴名称是否留白
						boundaryGap : true,
						// 是否显示坐标轴线及设置
						// axisLine:false,
						axisLine : {
							// onZero:false,
							lineStyle : {
								color : '#ccc',
							// type:['solid','arrow']
							}
						},
						// 刻度显示
						splitLine : {
							show : false
						},
						// 坐标轴刻度显示
						axisTick : false,
						data : [ '周一', '周二', '周三', '周四', '周五', '周六', '周日' ]
					},
					yAxis : {
						type : 'value',
						// 坐标轴线
						axisLine : false,
						// 刻度显示
						splitLine : false,
						axisLabel : {
							formatter : '{value} %'
						}
					},
					series : [ {
						name : '投入产出比',
						type : 'line',
						smooth : true,
						// 标记的图形 none不显示
						symbol : 'circle',
						data : [ 2, 5, 8, 13, 12, 13, 10 ],
						itemStyle : {
							normal : {
								label : {
									show : true,
									position : 'top',
									formatter : function(params, ticket,
											callback) {
										return params.value + ' %'
									}
								}
							}
						}
					// 平均线
					/*
					 * markLine : { data : [ { type : 'average', name : '平均值' } ] }
					 */
					} ]
				},
				init : function() {
					var self = this;
					self.setOptionFun();
				},
				setOptionFun : function() {
					var self = this;
					self.lineChart2.setOption(self.lineOption2);
				}
			}
			echartsLine2Obj.init();

			// 测试4 demo
			var echartsLine3Obj = {
				lineChart3 : echart.init(document.getElementById("line-3")),
				lineOption3 : {
					// 提示框组件
					/*
					 * tooltip : { trigger : 'axis' },
					 */
					// 工具标识
					/*
					 * toolbox : { show : true, feature : { mark : { show : true },
					 * dataView : { show : true, readOnly : false }, magicType : {
					 * show : true, type : [ 'line', 'bar' ] }, restore : { show :
					 * true }, saveAsImage : { show : true } } },
					 */
					// 是否启用拖拽重计算特性,折线点为空时不显示该点
					// calculable : true,
					// 外边框设置0
					// 图例
					/*
					 * legend : { data : [ '蒸发量', '降水量', '平均温度' ] },
					 */
					grid : {
						borderWidth : 0
					},
					xAxis : [ {
						type : 'category',
						// 坐标轴刻度显示
						axisTick : false,
						// 坐标轴在 grid 区域中的分隔线
						splitLine : false,
						data : [ '当年累计-b', '1月', '2月', '3月', '4月', '5月', '6月',
								'7月', '8月', '9月', '10月', '11月', '12月' ],
						axisLine : {
							lineStyle : {
								color : '#ccc'
							}
						},
						axisLabel : {
							// x轴标签是否全部显示，默认自动隐藏，0为全部显示
							interval : 0
						}
					} ],
					yAxis : [ {
						type : 'value',
						name : '万元',
						max : 1,
						axisLabel : {
							formatter : '{value}'
						},
						// 坐标轴在 grid 区域中的分隔线
						splitLine : false,
						axisLine : {
							show : true,
							lineStyle : {
								color : '#D7D7D7'
							}
						},
						axisTick : {
							show : true,
							lineStyle : {
								color : '#D9D9D9'
							}
						}
					} ],
					series : [

					{
						name : '蒸发量',
						type : 'bar',
						data : [ 0 ],
						itemStyle : {
							normal : {
								color : '#0099cb',
								type : 'dotted'
							}
						},
					},
					/*
					 * { name : '降水量', type : 'bar', data : [ 12 ], itemStyle : {
					 * normal : { color : '#FF5F50', type : 'dotted' } }, },
					 */
					{
						name : '平均温度',
						type : 'line',
						// y轴有多个时指定对应哪个轴数据，下标从0开始
						// yAxisIndex : 1,
						// 折线图平滑显示
						smooth : true,
						// 标记的图形 none不显示
						symbol : 'circle',
						symbolSize : 0,
						markPoint : {
							data : [ /*
										 * { // name : '周最低', value : 6.2, xAxis :
										 * 12, yAxis : 6.2 }
										 */],
							itemStyle : {
								normal : {
									color : '#de6e1b'
								}
							},
						},
						itemStyle : {
							normal : {
								color : '#de6e1b',
								label : {
									show : true
								}
							}
						},
						data : []

					} ]
				},
				init : function() {
					var self = this;
					self.getOption();
					self.setOptionFun();
				},
				seriesData : [ '-', 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1,
						0.1, 0.1, 0.1 ],
				getOption : function() {
					var self = this;
					var len = self.seriesData.length;
					var arr = {
						value : self.seriesData[len - 1],
						xAxis : len - 1,
						yAxis : self.seriesData[len - 1]
					}
					// self.lineOption3.yAxis[0].max = 1;
					self.lineOption3.series[1].data = self.seriesData;
					self.lineOption3.series[1].markPoint.data[0] = arr;
					return self.lineOption3;
				},
				setOptionFun : function() {
					var self = this;
					self.lineChart3.setOption(self.lineOption3);
				}
			}
			echartsLine3Obj.init();

			// 测试5 demo
			var echartsLine4Obj = {
				lineChart4 : echart.init(document.getElementById("line-4")),
				lineOption4 : {
					// 提示框组件
					/*
					 * tooltip : { trigger : 'axis' },
					 */
					// 工具标识
					/*
					 * toolbox : { show : true, feature : { mark : { show : true },
					 * dataView : { show : true, readOnly : false }, magicType : {
					 * show : true, type : [ 'line', 'bar' ] }, restore : { show :
					 * true }, saveAsImage : { show : true } } },
					 */
					// 是否启用拖拽重计算特性,折线点为空时不显示该点
					// calculable : true,
					// 外边框设置0
					// 图例
					legend : {
						y : 'bottom',
						data : [ '项目投入', '项目产出' ]
					},

					grid : {
						borderWidth : 0
					},
					xAxis : [ {
						type : 'category',
						// 坐标轴刻度显示
						axisTick : false,
						// 坐标轴在 grid 区域中的分隔线
						splitLine : false,
						data : [ '当年累计', '1月', '2月', '3月', '4月', '5月', '6月',
								'7月', '8月', '9月', '10月', '11月', '12月' ],
						axisLine : {
							lineStyle : {
								color : '#ccc'
							}
						},
						axisLabel : {
							rotate : 45
						}
					} ],
					yAxis : [ {
						type : 'value',
						name : '万元',
						axisLabel : {
							formatter : '{value}'
						},
						// 坐标轴在 grid 区域中的分隔线
						splitLine : false,
						// 坐标轴线
						axisLine : false
					} ],
					series : [

							{
								name : '项目投入',
								type : 'bar',
								data : [ 20 ],
								itemStyle : {
									normal : {
										color : '#0099cb',

										label : {
											show : true
										}
									}
								},
							},
							{
								name : '项目产出',
								type : 'bar',
								data : [ 12 ],
								itemStyle : {
									normal : {
										color : '#de6e1b',

										label : {
											show : true
										}
									}
								},
							},
							{
								name : '项目投入',
								type : 'line',
								// y轴有多个时指定对应哪个轴数据，下标从0开始
								// yAxisIndex : 1,
								// 折线图平滑显示
								smooth : true,
								// 标记的图形 none不显示
								symbol : 'circle',
								itemStyle : {
									normal : {
										color : '#0099cb',

										label : {
											show : true
										}
									}
								},
								data : [ '-', 1.2, 2.2, 3.3, 4.5, 5.3, 2.2,
										4.3, 6.4, 2.0, 3.5, 5.0, 6.2 ]

							},
							{
								name : '项目产出',
								type : 'line',
								// y轴有多个时指定对应哪个轴数据，下标从0开始
								// yAxisIndex : 1,
								// 折线图平滑显示
								smooth : true,
								// 标记的图形 none不显示
								symbol : 'circle',
								itemStyle : {
									normal : {
										color : '#de6e1b',

										label : {
											show : true
										}
									}
								},
								clickable : true,
								data : [ '-', 3.2, 2.2, 4.3, 5.5, 7.3, 11.2,
										21.3, 22.4, 23.0, 17.5, 13.0, 5.2 ]

							} ]
				},
				init : function() {
					var self = this;
					self.setOptionFun();
				},
				setOptionFun : function() {
					var self = this;
					self.lineChart4.setOption(self.lineOption4);
					self.lineChart4
							.on(self.ecConfig.EVENT.CLICK, self.eConsole);
				},
				ecConfig : require('echarts/config'),
				eConsole : function(param) {
					var mes;

					if (_.isUndefined(param.seriesIndex)) {
						mes += ' value-x:' + param.data[0];
						mes += ' value-y:' + param.data[1];
					}
					console.log(param);
					$('#profitLossProjectDetailModal').modal('show');
					$('#profitLossProjectDetailModal').on('shown.bs.modal',
							function() {
								// 执行一些动作...
								loadJqgrid();
							});
				}

			}
			echartsLine4Obj.init();

			function loadJqgrid() {
				jQuery("#profitProjectGrid").jqGrid(
						{
							url : webRoot + '/echarts/loadData.do',
							datatype : "json",
							colNames : [ 'Inv No', 'Date', 'Client', 'Amount',
									'Tax', 'Total', 'Notes' ],
							colModel : [ {
								name : 'id',
								index : 'id',
								width : 55
							}, {
								name : 'invdate',
								index : 'invdate',
								width : 90
							}, {
								name : 'name',
								index : 'name asc, invdate',
								width : 100
							}, {
								name : 'amount',
								index : 'amount',
								width : 80,
								align : "right"
							}, {
								name : 'tax',
								index : 'tax',
								width : 80,
								align : "right"
							}, {
								name : 'total',
								index : 'total',
								width : 80,
								align : "right"
							}, {
								name : 'note',
								index : 'note',
								width : 150,
								sortable : false
							} ],
							rowNum : 10,
							rowList : [ 10, 20, 30 ],
							// pager : '#pager2',
							sortname : 'id',
							mtype : "post",
							viewrecords : true,
							sortorder : "desc",
							// caption : "JSON 实例"
							autowidth : true,
							scroll : true
						});
			}

			// 测试6 demo
			var echartsLine5Obj = {
				lineChart5 : echart.init(document.getElementById("line-5")),
				lineOption5 : {
					tooltip : {
						trigger : 'axis'
					},
					legend : {
						data : [ '最高', '最低' ]
					},
					toolbox : {
						show : true,
						feature : {
							mark : {
								show : true
							},
							dataView : {
								readOnly : false
							},
							magicType : {
								show : true,
								type : [ 'line', 'bar', 'stack', 'tiled' ]
							},
							restore : {
								show : true
							},
							saveAsImage : {
								show : true
							}
						}
					},
					calculable : true,
					dataZoom : {
						show : true,
						realtime : true,
						start : 40,
						end : 60
					},
					xAxis : [ {
						type : 'category',
						boundaryGap : true,
						data : function() {
							var list = [];
							for (var i = 1; i <= 30; i++) {
								list.push('2013-03-' + i);
							}
							return list;
						}()
					} ],
					yAxis : [ {
						type : 'value'
					} ],
					series : [ {
						name : '最高',
						type : 'line',
						data : function() {
							var list = [];
							for (var i = 1; i <= 30; i++) {
								list.push(Math.round(Math.random() * 30) + 30);
							}
							return list;
						}()
					}, {
						name : '最低',
						type : 'bar',
						data : function() {
							var list = [];
							for (var i = 1; i <= 30; i++) {
								list.push(Math.round(Math.random() * 10));
							}
							return list;
						}()
					} ]
				},
				init : function() {
					var self = this;
					self.setOptionFun();
				},
				setOptionFun : function() {
					var self = this;
					self.lineChart5.setOption(self.lineOption5);
					self.lineChart5
							.on(self.ecConfig.EVENT.CLICK, self.eConsole);
				},
				ecConfig : require('echarts/config'),
				eConsole : function(param) {
					var mes = '【' + param.type + '】';
					if (typeof param.seriesIndex != 'undefined') {
						mes += '  seriesIndex : ' + param.seriesIndex;
						mes += '  dataIndex : ' + param.dataIndex;
						mes += ' data:' + param.data;
						mes += ' name:' + param.data;
						mes += ' value:' + param.data;
					}
					console.log(param);
				}
			}
			echartsLine5Obj.init();

			var ecConfig = require('echarts/config');
			function eConsole(param) {
				var mes = '【' + param.type + '】';
				if (typeof param.seriesIndex != 'undefined') {
					mes += '  seriesIndex : ' + param.seriesIndex;
					mes += '  dataIndex : ' + param.dataIndex;
				}
				if (param.type == 'hover') {
					document.getElementById('hover-console').innerHTML = 'Event Console : '
							+ mes;
				} else {
					document.getElementById('console').innerHTML = mes;
				}
				console.log(param);
			}
			/*
			 * // -------全局通用 REFRESH: 'refresh', RESTORE: 'restore', RESIZE:
			 * 'resize', CLICK: 'click', DBLCLICK: 'dblclick', HOVER: 'hover',
			 * MOUSEOUT: 'mouseout', // -------业务交互逻辑 DATA_CHANGED:
			 * 'dataChanged', DATA_ZOOM: 'dataZoom', DATA_RANGE: 'dataRange',
			 * DATA_RANGE_HOVERLINK: 'dataRangeHoverLink', LEGEND_SELECTED:
			 * 'legendSelected', LEGEND_HOVERLINK: 'legendHoverLink',
			 * MAP_SELECTED: 'mapSelected', PIE_SELECTED: 'pieSelected',
			 * MAGIC_TYPE_CHANGED: 'magicTypeChanged', DATA_VIEW_CHANGED:
			 * 'dataViewChanged', TIMELINE_CHANGED: 'timelineChanged', MAP_ROAM:
			 * 'mapRoam',
			 */
			// myChart.on(ecConfig.EVENT.CLICK, eConsole);
			// myChart.on(ecConfig.EVENT.DBLCLICK, eConsole);
			// //myChart.on(ecConfig.EVENT.HOVER, eConsole);
			// myChart.on(ecConfig.EVENT.DATA_ZOOM, eConsole);
			// myChart.on(ecConfig.EVENT.LEGEND_SELECTED, eConsole);
			// myChart.on(ecConfig.EVENT.MAGIC_TYPE_CHANGED, eConsole);
			// myChart.on(ecConfig.EVENT.DATA_VIEW_CHANGED, eConsole);
			// 测试7 demo
			// tan 70 = 2.747
			// tan = 0.466
			var echartsLine6Obj = {
				lineChart6 : echart.init(document.getElementById("line-6")),
				lineOption6 : {
					grid : {
						x : 15,
						y : 15,
						x2 : 100,
						borderWidth : 0
					},
					tooltip : {
						trigger : 'item',
						showDelay : 0,
						formatter : function(params) {
							if (params.value.length > 1) {
								return '累计投入: ' + params.value[0] + '万元<br/> '
										+ '累计产出: ' + params.value[1] + '万元 ';
							}
						}
					},
					legend : {
						x : 'center',
						y : 'bottom',
						data : [ '盈利', '有亏损', '亏损严重' ]
					},
					xAxis : [ {
						name : '投入 (万元)',
						min : 0,
						max : 300,
						nameTextStyle : {
							color : '#ccc'
						},
						type : 'value',
						scale : true,
						splitLine : false,
						axisLabel : {
							show : false,
							interval : 0,
							formatter : '{value} 万元'
						}
					} ],
					yAxis : [ {
						name : '产出（万元）',
						min : 0,
						max : 300,
						splitNumber : 10,
						type : 'value',
						scale : true,
						splitLine : false,
						axisLabel : {
							show : false,
							interval : 0,
							formatter : '{value} 万元'
						}
					} ],
					series : [
							{
								name : '盈利',
								type : 'scatter',
								symbol : 'circle',
								symbolSize : 8,
								data : [ {
									name : 'A',
									value : [ 100, 251 ]
								}, {
									name : 'B',
									value : [ 50, 120 ]
								} ],
								itemStyle : {
									normal : {
										color : '#59B700',
										label : {
											show : true,
											formatter : function(params) {
												return params.name;
											},
											position : 'bottom'
										}
									}
								},
							},
							{
								name : '有亏损',
								type : 'scatter',
								itemStyle : {
									normal : {
										color : '#EFA100'
									}
								},
								symbol : 'circle',
								symbolSize : 8,
								data : [ [ 180.0, 98.6 ], [ 100.3, 59.8 ],
										[ 160.3, 73.2 ] ]
							},
							{
								name : '亏损严重',
								type : 'scatter',
								itemStyle : {
									normal : {
										color : '#E83D2B'
									}
								},
								symbol : 'circle',
								symbolSize : 8,
								data : [ [ 400, 10 ], [ 186, 12 ], [ 170, 25 ] ]
							} ]
				},
				init : function() {
					var self = this;
					self.setOptionFun();
				},
				setOptionFun : function() {
					var self = this;
					self.lineOption6.xAxis[0].min = 0;
					self.lineOption6.xAxis[0].max = 400;
					self.lineOption6.xAxis[0].splitNumber = 10;
					self.lineOption6.yAxis[0].min = 0;
					self.lineOption6.yAxis[0].max = 400;
					self.lineOption6.yAxis[0].splitNumber = 10;
					self.lineChart6.setOption(self.lineOption6);
				}

			}
			echartsLine6Obj.init();

			var barOption1 = {
				// calculable : true,
				grid : {
					x : 35,
					y : 25,
					x2 : 15,
					borderWidth : 0
				},
				xAxis : [ {
					type : 'category',
					axisTick : false,
					splitLine : false,
					axisLabel : {
						interval : 0
					},
					axisLine : {
						lineStyle : {
							color : '#D7D7D7'
						}
					},
					axisLabel : {
						margin : -90
					},
					data : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月',
							'9月', '10月', '11月', '12月' ]
				} ],
				yAxis : [ {
					type : 'value',
					axisTick : false,
					splitLine : false,
					axisLabel : {
						interval : 0
					},
					axisLine : {
						lineStyle : {
							color : '#D7D7D7'
						}
					}
				} ],
				series : [ {
					name : '蒸发量',
					type : 'bar',
					data : [ 2.0, 4.9, 7.0, 23.2, -25.6, 76.7, -135.6, 162.2,
							32.6, 20.0, 6.4, 3.3 ],
					itemStyle : {
						normal : {
							color : '#00bda9'
						}
					}
				} ]
			};

			var barObj1 = {
				option : barOption1,
				barChart : echart.init(document.getElementById("bar-1")),
				init : function() {
					var self = this;
					self.setOption();
				},
				setOption : function() {
					var self = this;
					self.barChart.setOption(self.option);
				}
			}
			barObj1.init();

			var barOption2 = {
				grid : {
					x : 35,
					y : 25,
					x2 : 15,
					borderWidth : 0
				},
				// calculable : true,
				xAxis : [ {
					type : 'category',
					axisTick : false,
					splitLine : false,
					axisLabel : {
						interval : 0
					},
					axisLine : {
						lineStyle : {
							color : '#D7D7D7'
						}
					},
					// axisLabel : {
					// margin : -90
					// },
					data : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月',
							'9月', '10月', '11月', '12月' ]
				} ],
				yAxis : [ {
					type : 'value',
					// name : '损益值',
					// nameTextStyle : {
					// fontWeight : 'bold',
					// color : '#ccc'
					// },
					axisTick : false,
					splitLine : false,
					axisLabel : {
						interval : 0
					},
					axisLine : {
						lineStyle : {
							color : '#D7D7D7'
						}
					}
				} ],
				series : [
						{
							name : '蒸发量',
							type : 'bar',
							data : [ 2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6,
									162.2, 32.6, 20.0, 6.4, 3.3 ]
						},
						{
							name : '降水量',
							type : 'bar',
							data : [ 2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6,
									182.2, 48.7, 18.8, 6.0, 2.3 ]
						} ]
			};

			var barObj2 = {
				option : barOption2,
				barChart : echart.init(document.getElementById("bar-2")),
				init : function() {
					var self = this;
					self.setOption();
				},
				setOption : function() {
					var self = this;
					self.barChart.setOption(self.option);
				}
			}
			barObj2.init();

			var barOption3 = {
				// calculable : true,
				grid : {
					x : 35,
					y : 25,
					x2 : 15,
					borderWidth : 0
				},
				xAxis : [ {
					type : 'category',
					axisTick : false,
					splitLine : false,
					axisLabel : {
						interval : 0
					},
					axisLine : {
						lineStyle : {
							color : '#D7D7D7'
						}
					},
					// axisLabel : {
					// margin : -90
					// },
					data : [ '1月', '2月', '3月', '4月' ]
				} ],
				yAxis : [ {
					type : 'value',
					axisTick : false,
					axisLine : {
						show : false
					},
					axisLabel : {
						formatter : '{value} %'
					}
				} ],
				series : [ {
					// name : '蒸发量',
					type : 'bar',
					data : [ 2.0, 4.9, 7.0, 23.2 ],
					itemStyle : {
						normal : {
							color : '#0099cb',
							label : {
								show : true,
								formatter : function(params, ticket, callback) {
									return params.value + ' %'
								}
							}
						}
					}
				} ]
			};

			var barObj3 = {
				option : barOption3,
				barChart : echart.init(document.getElementById("bar-3")),
				init : function() {
					var self = this;
					self.setOption();
				},
				setOption : function() {
					var self = this;
					self.barChart.setOption(self.option);
				}
			}
			barObj3.init();

			var timeObj = {
				init : function() {
					var self = this;
					// $('.form_date_11').datetimepicker({
					// language : 'zh-CN',
					// weekStart : 1,
					// todayBtn : 1,
					// autoclose : 1,
					// todayHighlight : 1,
					// startView : 2,
					// minView : 2,
					// forceParse : 0
					// });
					self.setDateFun();
				},
				setDateFun : function() {
					$(".form_date_1").datetimepicker({
						language : 'cn',
						format : "yyyy-mm-dd",
						autoclose : true,
						minView : "month",
						maxView : "decade",
						todayBtn : true,
						pickerPosition : "bottom-left"
					}).on(
							"changeDate",
							function(ev) {
								$(".form_date_1").datetimepicker("setEndDate",
										$(".form_date_2 input").val());
							});
					$(".form_date_2").datetimepicker({
						language : 'cn',
						format : "yyyy-mm-dd",
						autoclose : true,
						minView : "month",
						maxView : "decade",
						todayBtn : true,
						pickerPosition : "bottom-left"
					}).on(
							"changeDate",
							function(ev) {
								$(".form_date_2").datetimepicker(
										"setStartDate",
										$(".form_date_1 input").val());
							});
				}
			}
			timeObj.init();

			var mapOption = {
				dataRange : {
					min : 0,
					max : 600,
					x : 'center',
					y : 'top',
					text : [ '高', '低' ], // 文本，默认为数值文本
					orient : 'horizontal'
				},
				series : [ {
					type : 'map',
					mapType : 'china',
					roam : false,
					data : [ {
						name : '北京',
						value : 100
					}, {
						name : '天津',
						value : 500
					}, {
						name : '上海',
						value : 400
					}, {
						name : '重庆',
						value : 300
					} ]
				} ]
			};

			var mapObj = {
				option : mapOption,
				chart : echart.init(document.getElementById("map-1")),
				init : function() {
					var self = this;
					self.setOption();
				},
				setOption : function() {
					var self = this;
					self.chart.setOption(self.option);
				}
			}
			mapObj.init();

			var mapPieOption = {
				legend : {
					orient : 'horizontal',
					x : 'center',
					y : 'bottom',
					data : [ '优化管理', '技术升级', '外部咨询', '其他' ]
				},
				series : [ {
					name : '访问来源',
					type : 'pie',
					radius : '70%',
					center : [ '50%', '50%' ],
					// selectedOffset : 10,
					selectedMode : 'single',
					data : [ {
						value : 335,
						name : '优化管理'
					}, {
						value : 310,
						name : '技术升级'
					}, {
						value : 234,
						name : '外部咨询'
					}, {
						value : 135,
						name : '其他'
					} ],
				} ],
				color : colorPie
			};
			var mapPieObj = {
				chart : echart.init(document.getElementById("map-pie")),
				option : mapPieOption,
				init : function() {
					var self = this;
					self.setOptionFun();
				},
				setOptionFun : function() {
					var self = this;
					self.chart.setOption(self.option);
				}
			}
			mapPieObj.init();

			var mapOption2 = {
				tooltip : {
					trigger : 'item',
					formatter : '{b}'
				},
				series : [ {
					type : 'map',
					mapType : 'china',
					selectedMode : 'single',
//					itemStyle : {
//						normal : {
//							label : {
//								show : true
//							}
//						},
//						emphasis : {
//							label : {
//								show : true
//							}
//						}
//					},
					data : [ {
						name : '广东',
						selected : true
					} ],
					animation: false
				} ]
			};
			
			var mapObj2 = {
				option : mapOption2,
				chart : echart.init(document.getElementById("map-2")),
				init : function() {
					var self = this;
					self.setOption();
				},
				setOption : function() {
					var self = this;
					self.chart.setOption(self.option);
					self.clickFun();
				},
				ecConfig : require('echarts/config'),
				clickFun: function(){
					var self = this;
					self.chart.on(self.ecConfig.EVENT.MAP_SELECTED, function (param){
					    var selected = param.selected;
					    var str;
					    for (var p in selected) {
					        if (selected[p]) {
					            str = p;
					        }
					    }
						
					    console.log(str);
					})
				}
			}
			mapObj2.init();
		});