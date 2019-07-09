require(['jgGrid'],function(){
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	
	function setup(){
		setupGrid();
	}
	
	function setupGrid(){
		initGrid();	
		navButtons();
	}
	
	function initGrid(){
		var grid_data = 
			[ 
				{"organization_type_id":"1", "organization_type_key":"company", "organization_type_level":"2", "organization_type_name":"公司test"},
				{"organization_type_id":"2", "organization_type_key":"department", "organization_type_level":"3", "organization_type_name":"部门test"},
				{"organization_type_id":"3", "organization_type_key":"team", "organization_type_level":"5", "organization_type_name":"组test"},
				{"organization_type_id":"4", "organization_type_key":"group", "organization_type_level":"1", "organization_type_name":"集团test"},
				{"organization_type_id":"5", "organization_type_key":"center", "organization_type_level":"4", "organization_type_name":"中心test"},
		    ];
		var excelDataJson = $("#ol-GridData").text();
		if(excelDataJson != ""){
			grid_data = JSON.parse(excelDataJson);
		}
		var lastsel;
		jQuery(grid_selector).jqGrid({
			data: grid_data,
			datatype: "local",
			height: 250,
			colNames:['organization_type_id','编码','层级','机构级别名称' ],
			colModel:[
				{name:'organization_type_id', index:'organization_type_id'},
				{name:'organization_type_key',index:'organization_type_key',width:90, editable:true},
				{name:'organization_type_level',index:'organization_type_level',sortable:true, width:150, editable: true,editoptions:{size:"20",maxlength:"30"}},
				{name:'organization_type_name',index:'organization_type_name', width:150, editable: true,editoptions:{size:"20",maxlength:"30"}}
			],
			viewrecords : true,
			rowNum:10,
			rowList:[10,20,30],
			pager : pager_selector,
			altRows: true,
			// toppager: true,
			multiselect: true,
			// multikey: "ctrlKey",
	        multiboxonly: true,
			loadComplete : function() {
				var table = this;
				setTimeout(function(){
					styleCheckbox(table);
					updateActionIcons(table);
					updatePagerIcons(table);
					enableTooltips(table);
				}, 0);
			},
	        onSelectRow : function(id) {
	        	console.log(id);
	            if (id && id !== lastsel) {
	              jQuery(grid_selector).jqGrid('restoreRow', lastsel);
	              jQuery(grid_selector).jqGrid('editRow', id, true);
	              lastsel = id;
	            }
	        },
//			editurl: "/dummy.html",// nothing is saved
			caption: "【机构级别】",
			autowidth: true
		});
	}
	
	
	function navButtons(){
		// navButtons
		jQuery(grid_selector).jqGrid('navGrid', '#grid-pager',
			{ 	
				edit: true,
				editicon : 'icon-pencil blue',
				add: true,
				addicon : 'icon-plus-sign purple',
				del: true,
				delicon : 'icon-trash red',
				search: true,
				searchicon : 'icon-search orange',
				refresh: true,
				refreshicon : 'icon-refresh green',
				view: true,
				viewicon : 'icon-zoom-in grey',
			},
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
		);
	}
	
	function beforeDeleteCallback(e) {
		var form = $(e[0]);
	}
	function beforeEditCallback(e) {
		var form = $(e[0]);
	}
	function aceSwitch( cellvalue, options, cell ) {
	}
	function pickDate( cellvalue, options, cell ) {
	}
	
	function styleCheckbox(table) {
	}
	function updateActionIcons(table) {
	}
	function updatePagerIcons(table) {
		var replacement =  {
			'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
			'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
			'ui-icon-seek-next' : 'icon-angle-right bigger-140',
			'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
		};
		$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
			var icon = $(this);
			var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
			
			if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
		});
	}
	function enableTooltips(table) {
	}

	
	setup();
});