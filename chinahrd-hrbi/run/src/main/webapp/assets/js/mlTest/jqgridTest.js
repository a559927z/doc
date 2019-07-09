/*
 * echarts 测试 
 */
require(
		[ "jquery", "jgGrid" ],
		function($) {
			var webRoot = G_WEB_ROOT;
			var urls = {
				getUrl1 : webRoot + '/jqgrid/loadData1.do',
				getUrl2 : webRoot + '/jqgrid/loadData2.do'
			}
			$("#list2").jqGrid(
					{
						url : urls.getUrl2,
						datatype : "json",
						colNames : [ 'Inv No', 'Date', 'Client', 'Amount',
								'Tax', 'Total', 'Notes' ],
						colModel : [
								{
									name : 'id',
									index : 'id',
									width : 55
								},
								{
									name : 'invdate',
									index : 'invdate',
									width : 90
								},
								{
									name : 'name',
									index : 'name asc, invdate',
									width : 100,
									formatter : function(cellvalue, options,
											rowObject) {
										if (cellvalue == 'Client 1') {
											return '男';
										} else {
											return cellvalue;
										}
									}
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
									sortable : false
								} ],
						rowNum : 10,
						rowList : [ 10, 20, 30 ],
						pager : '#pager2',
						// sortname : 'name',
						mtype : "post",
						viewrecords : true,
						sortorder : "desc",
						repeatitems : false
					// caption : "JSON 实例"
					});
			// $("#list2").jqGrid('navGrid', '#pager2', {
			// edit : false,
			// add : false,
			// del : false
			// });
			$("#list2").setGridWidth($('#jqgrid-div').width() * 0.98);
			$("#list2").setGridHeight($('#jqgrid-div').height() * 0.98);

			var jqgridObj1 = {
				init : function() {
					var self = this;
					$("#list1").closest("div.ui-jqgrid-view").children(
							"div.ui-jqgrid-titlebar").children(
							"span.ui-jqgrid-title").css("background-color",
							"#0f0");
					self.initJqgridList();
					self.getUrl();
				},
				initJqgridList : function() {
					$("#list1")
							.jqGrid(
									{
										datatype : "local",
										height : 250,
										colNames : [ 'Inv No', 'Date',
												'Client', 'Amount', 'Tax',
												'Total', 'Notes' ],
										colModel : [
												{
													name : 'id',
													index : 'id',
													width : 60,
													sorttype : "int",
													sortable : false,
													formatter : function(
															cellvalue, options,
															rowObject) {
														return "<input type='radio' name='myId' value='"
																+ cellvalue
																+ "'  onclick=\"radioSelect('myId', 'listTable')\" />";
													}
												},
												{
													name : 'invdate',
													index : 'invdate',
													width : 90,
													sorttype : "date",
													sortable : false,
													formatter : function(value,
															grid, rows, state) {
														return "<a href=\"#\" style=\"color:#f60\" onclick=\"Modify("
																+ value
																+ ")\">Edit</a>"
													}
												},
												{
													name : 'name',
													index : 'name',
													width : 100,
													sortable : false
												},
												{
													name : 'amount',
													index : 'amount',
													width : 80,
													align : "right",
													sorttype : "float",
													sortable : false,
													cellattr : function() {
														return "style='border: 0; border-bottom: solid 1px #ccc;'";
													}
												},
												{
													name : 'tax',
													index : 'tax',
													width : 80,
													align : "right",
													sorttype : "float",
													sortable : false,
													cellattr : function() {
														return "style='border: 0; border-bottom: solid 1px #ccc;'";
													}
												},
												{
													name : 'total',
													index : 'total',
													width : 100,
													align : "right",
													sorttype : "float",
													fixed : true,
													sortable : false,
													cellattr : function(rowId,
															val, rawObject, cm,
															rdata) {
														return "style='color:red;font-weight:bold;text-decoration:underline'";
													}
												}, {
													name : 'note',
													index : 'note',
													width : 250,
													fixed : true,
													sortable : false
												} ],
										// multiselect : false,
										cellEdit : false,
										// scroll : true,
										hoverrows : false,
										// shrinkToFit : true,
										autowidth : true,
//										rowNum : 10,
//										rowList : [ 10, 20, 30 ],
//										pager : '#pager1',
									// caption : "Manipulating Array Data"
									});
				},
				getUrl : function() {
					$.ajax({
						url : urls.getUrl1,
						type : 'post',
						success : function(data) {
							$.each(data, function(ind, obj) {
								// for (var i = 0; i <= mydata.length; i++) {
								// $("#list1").jqGrid('addRowData', i + 1,
								// mydata[i]);
								// }
								$("#list1").jqGrid('addRowData', ind, obj);
								console.log(obj);
							});
						},
						error : function() {
						}
					});
				}
			}
			function format(cellvalue) { // cellvalue表示当前单元格的值
				var str = "";
				var hour = 0;
				var minute = 0;
				var second = 0;

				second = cellvalue / 1000;

				if (second > 60) {
					minute = second / 60;
					second = second % 60;
				}
				if (minute > 60) {
					hour = minute / 60;
					minute = minute % 60;
				}

				var strHour = parseInt(hour);
				var strMinute = parseInt(minute);
				var strSecond = parseInt(second);

				return (strHour + "小时" + strMinute + "分钟" + strSecond + "秒");
			}
			jqgridObj1.init();

			var jqgridObj3 = {
				init : function() {

				},
				setGrid : function() {
					$("#list3").jqGrid(
							{
//								url : urls.getUrl2,
								datatype : "json",
								colNames : [ 'Inv No', 'Date', 'Client',
										'Amount', 'Tax', 'Total', 'Notes' ],
								colModel : [
										{
											name : 'id',
											index : 'id',
											width : 55
										},
										{
											name : 'invdate',
											index : 'invdate',
											width : 90
										},
										{
											name : 'name',
											index : 'name asc, invdate',
											width : 100,
											formatter : function(cellvalue,
													options, rowObject) {
												if (cellvalue == 'Client 1') {
													return '男';
												} else {
													return cellvalue;
												}
											}
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
											sortable : false
										} ],
								rowNum : 10,
								rowList : [ 10, 20, 30 ],
								pager : '#pager3',
								// sortname : 'name',
								mtype : "post",
								viewrecords : true,
								sortorder : "desc",
								repeatitems : false
							// caption : "JSON 实例"
							});
				},
				getRequest : function() {
					$.ajax({
						url : urls.getUrl2,
						type : 'post',
						success : function(data) {
							$.each(data, function(ind, obj) {
								$("#list3").jqGrid('addRowData', ind, obj);
//								console.log(obj);
							});
						},
						error : function() {
						}
					});
				}
			}

			$(window).resize(function() {
				$("#list1").setGridWidth($(window).width() - 10);
			});
		});