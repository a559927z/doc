<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>表格组件</title>
	<link href="${ctx}/assets/css/global.css" rel="stylesheet" />

</head>
<body>
<div class="page-content">
	<table id="grid-table"></table>
	<div id="grid-pager"></div>
</div>
<script>
require(['jgGrid'],function(){
	var grid_data = 
		[ 
			{id:"1",name:"Desktop Computer",note:"note",stock:"Yes",ship:"FedEx", sdate:"2007-12-03"},
			{id:"2",name:"Laptop",note:"Long text ",stock:"Yes",ship:"InTime",sdate:"2007-12-03"},
			{id:"3",name:"LCD Monitor",note:"note3",stock:"Yes",ship:"TNT",sdate:"2007-12-03"},
			{id:"4",name:"Speakers",note:"note",stock:"No",ship:"ARAMEX",sdate:"2007-12-03"},
			{id:"5",name:"Laser Printer",note:"note2",stock:"Yes",ship:"FedEx",sdate:"2007-12-03"},
			{id:"6",name:"Play Station",note:"note3",stock:"No", ship:"FedEx",sdate:"2007-12-03"},
			{id:"7",name:"Mobile Telephone",note:"note",stock:"Yes",ship:"ARAMEX",sdate:"2007-12-03"},
			{id:"8",name:"Server",note:"note2",stock:"Yes",ship:"TNT",sdate:"2007-12-03"},
			{id:"9",name:"Matrix Printer",note:"note3",stock:"No", ship:"FedEx",sdate:"2007-12-03"},
			{id:"10",name:"Desktop Computer",note:"note",stock:"Yes",ship:"FedEx", sdate:"2007-12-03"},
			{id:"11",name:"Laptop",note:"Long text ",stock:"Yes",ship:"InTime",sdate:"2007-12-03"},
			{id:"12",name:"LCD Monitor",note:"note3",stock:"Yes",ship:"TNT",sdate:"2007-12-03"},
			{id:"13",name:"Speakers",note:"note",stock:"No",ship:"ARAMEX",sdate:"2007-12-03"},
			{id:"14",name:"Laser Printer",note:"note2",stock:"Yes",ship:"FedEx",sdate:"2007-12-03"},
			{id:"15",name:"Play Station",note:"note3",stock:"No", ship:"FedEx",sdate:"2007-12-03"},
			{id:"16",name:"Mobile Telephone",note:"note",stock:"Yes",ship:"ARAMEX",sdate:"2007-12-03"},
			{id:"17",name:"Server",note:"note2",stock:"Yes",ship:"TNT",sdate:"2007-12-03"},
			{id:"18",name:"Matrix Printer",note:"note3",stock:"No", ship:"FedEx",sdate:"2007-12-03"},
			{id:"19",name:"Matrix Printer",note:"note3",stock:"No", ship:"FedEx",sdate:"2007-12-03"},
			{id:"20",name:"Desktop Computer",note:"note",stock:"Yes",ship:"FedEx", sdate:"2007-12-03"},
			{id:"21",name:"Laptop",note:"Long text ",stock:"Yes",ship:"InTime",sdate:"2007-12-03"},
			{id:"22",name:"LCD Monitor",note:"note3",stock:"Yes",ship:"TNT",sdate:"2007-12-03"},
			{id:"23",name:"Speakers",note:"note",stock:"No",ship:"ARAMEX",sdate:"2007-12-03"}
	    ];
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	
	jQuery(grid_selector).jqGrid({
		data: grid_data,
		datatype: "local",
		height: 390,
		colNames:['ID','Last Sales','Name', 'Stock', 'Ship via','Notes','operator'],
		colModel:[
			{name:'id',index:'id', width:60, sorttype:"int", editable: true},
			{name:'sdate',index:'sdate',width:90, editable:true, sorttype:"date",unformat: pickDate},
			{name:'name',index:'name', width:150,editable: true,editoptions:{size:"20",maxlength:"30"}},
			{name:'stock',index:'stock', width:70, editable: true,edittype:"checkbox",editoptions: {value:"Yes:No"},unformat: aceSwitch},
			{name:'ship',index:'ship', width:90, editable: true,edittype:"select",editoptions:{value:"FE:FedEx;IN:InTime;TN:TNT;AR:ARAMEX"}},
			{name:'note',index:'note', width:100, sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"}},
			{name:'myac',index:'', width:130, fixed:true, sortable:false, resize:false,
				formatter:'actions', 
				formatoptions:{ 
					keys:true,
					delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback}
				}
			}
		], 

		viewrecords : true,
		rowNum:10,
		rowList:[10,20,30],
		pager : pager_selector,
		rowHeight: 36,
		styleUI: 'Bootstrap',
		altRows: true,
		//toppager: true,
		multiselect: true,
		//multikey: "ctrlKey",
        multiboxonly: true,
		editurl: "/dummy.html",//nothing is saved
		caption: "jqGrid with inline editing",
		autowidth: true

	});
	
	//navButtons
	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{
			recreateForm: true,
			beforeShowForm : function(e) {
				var form = $(e[0]);
				
			}
		},
		{
			closeAfterAdd: true,
			recreateForm: true,
			viewPagerButtons: false,
			beforeShowForm : function(e) {
				var form = $(e[0]);
				
			}
		},
		{
			recreateForm: true,
			beforeShowForm : function(e) {
				var form = $(e[0]);
				
			},
			onClick : function(e) {
				alert(1);
			}
		},
		{
			recreateForm: true,
			afterShowSearch: function(e){
				var form = $(e[0]);
				
			},
			afterRedraw: function(){
				
			}
			,
			multipleSearch: true,
		},
		{
			recreateForm: true,
			beforeShowForm: function(e){
				var form = $(e[0]);
				
			}
		}
	)
});
</script>
</body>
</html>