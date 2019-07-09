<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>角色管理</title>
</head>
<body>
	<div class="page-content">
		<div class="main-container-inner">
			<div class="col-xs-12">&nbsp;</div>
			<div class="col-xs-12">
				<table id="grid-table"></table>
				<div id="grid-pager"></div>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="${ctx}/assets/js/require.js"></script>
	<script type="text/javascript">
	require(['jgGrid', 'messenger'],function(){
		var webRoot = G_WEB_ROOT;
		var urls = {
			findRoleAll : webRoot + '/role/findRoleAll.do',
			operateRole : webRoot + '/role/operateRole.do',
			delRole : webRoot + '/role/delRole.do'
		}

		var grid_selector = "#grid-table";
		var pager_selector = "#grid-pager";

		var template = "<form class='container' style='width : 300px'> " +
							"<div class='row form-group'> " +
								"<label class='col-xs-4 text-right'>角色编号：</label> " +
								"<span class='col-xs-8'>{roleKey}</span> " +
							"</div> " +
							"<div class='row form-group'> " +
								"<label class='col-xs-4 text-right'>角色名称：</label> " +
								"<span class='col-xs-8'>{roleName}</span> " +
							"</div> " +
							"<div class='row form-group'> " +
								"<label class='col-xs-4 text-right'>描述：</label> " +
								"<span class='col-xs-8'>{note}</span> " +
							"</div> " +
							"<div class='row form-group' align='center'> {sData} {cData}</div>"+
						"</form>";
		jQuery(grid_selector).jqGrid({
			url: urls.findRoleAll,
			datatype: 'json',
			mtype: 'POST',
			autowidth: true,
			height: 326,
			colNames:['id','角色编码','角色名称','角色描述','创建时间','操作'],
			colModel:[
				{name: 'id', index: 'id', hidedlg:true,hidden:true,width: 60},
				{name:'roleKey',index:'roleKey', width:60,editable: true, editoptions:{maxlength:"20"}, editrules : { required: true}},
				{name:'roleName',index:'roleName', width:100, editable: true, editoptions:{size:"20",maxlength:"20"},editrules : { required: true}},
				{name:'note',index:'note', width:150,editable: true, editoptions:{size:"20",maxlength:"32"},editrules : { required: true}},
				{name:'createTime',index:'createTime',width:100, editable:true,align:'center', sorttype:"date"},
				{name:'myac',index:'', width:200, fixed:true, sortable:false, resize:false
	//				formatter:'actions',
	//				formatoptions:{
	//					keys:true,
	//					delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback}
	//				}
				}
			],
			viewrecords : true,
			rowNum: 10,
			rowList:[10,20,30],
			pager : pager_selector,
			altRows: true,
			multiselect: true,
			multiboxonly: true,
			loadComplete : function(xhr) {

				var rows = xhr.rows;
				var ids = $(grid_selector).jqGrid('getDataIDs');
				for (var i = 0; i < ids.length; i++) {
					var col = ids[i];
					var	html = '<shiro:hasPermission name="XiTongGuanLi_JueSeGuanLi:configFunction"><a href="javascript:void(0)" data-index="' + i + '" class="role_col1" style="margin:0 10px;">配置功能</a></shiro:hasPermission>'
					+ '<shiro:hasPermission name="XiTongGuanLi_JueSeGuanLi:configData"><a href="javascript:void(0)" data-index="' + i + '" class="role_col2" >配置数据</a></shiro:hasPermission>';
					$(grid_selector).jqGrid('setRowData', col, {myac: html});
				}

				$('.role_col1').unbind().bind('click',function(){
					var _this = $(this);
					var idx = _this.attr('data-index');
					var roleObj = rows[idx];
					window.location.href = webRoot + '/role/roleFunction?roleId='+roleObj.roleId;
				});

				$('.role_col2').unbind().bind('click',function(){
					var _this = $(this);
					var idx = _this.attr('data-index');
					var roleObj = rows[idx];
					//异步
	// 				window.location.href = webRoot + '/role/roleOrganAsync?roleId='+roleObj.roleId;
					//同步
					window.location.href = webRoot + '/role/roleOrgan?roleId='+roleObj.roleId;
				});

				var table = this;
				setTimeout(function(){
					styleCheckbox(table);

					updateActionIcons(table);
					updatePagerIcons(table);
					enableTooltips(table);
				}, 0);
			},
			editurl: urls.operateRole,
			caption: '角色管理'
		});

		//navButtons
		jQuery(grid_selector).jqGrid('navGrid', pager_selector,
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
				delfunc:function(ids){
					$.ajax({
						   type : 'post',
						   url: urls.delRole,
						   dataType:'json',
						   traditional: true,
//						   data : {ids : JSON.stringify(ids)},
						   data : {ids : ids},
						   success: function(data){
							   var dataType = (data.type) ? true : false;
		                        notifyInfo(data.msg, dataType);
		                        if(dataType){
		                        	$(grid_selector).clearGridData().trigger("reloadGrid");
		                        }
						   }
						});
				}
			},
			{
				editCaption: "修改",
				width : 350, 
				left:450,
				top:20,
				template: template,
				errorTextFormat: function (data) {
					return 'Error: ' + data.responseText
				}
			},
			// options for the Add Dialog
			{
				editCaption: "新增",
				template: template,
				width : 350,
				left:450,
				top:20,
				errorTextFormat: function (data) {
					return 'Error: ' + data.responseText
				}
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
				multipleSearch: true
			},
			{
				recreateForm: true,
				beforeShowForm: function(e){
					var form = $(e[0]);

				}
			}
		)

		function beforeDeleteCallback(e) {
			var form = $(e[0]);
		}
		function beforeEditCallback(e) {
			var form = $(e[0]);
		}
		function aceSwitch( cellvalue, options, cell ) {
		}
		function styleCheckbox(table) {
		}
		function updateActionIcons(table) {
		}


		function updatePagerIcons(table) {
			var replacement =
			{
				'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
				'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
				'ui-icon-seek-next' : 'icon-angle-right bigger-140',
				'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
			};
			$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
				var icon = $(this);
				var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

				if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
			})
		}

		function enableTooltips(table) {
		}
		
        function notifyInfo(msg, type) {
            Messenger().post({
                type: type,
                message: msg
            });
        }
	});
	</script>
</body>
</html>