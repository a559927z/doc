require(['jquery', 'jgGrid', 'validate', 'form', 'bootstrap', 'ace-elements', 'messenger'], function ($) {
    var webRoot = G_WEB_ROOT;
    var urls = {
        importExcelUrl: webRoot + "/importData/doImportExcel.do" // 导入excel
    };

    var grid_selector = "#grid-table";
    var pager_selector = "#grid-pager";

    var organTree = [""];

    function setup() {
//		setupGrid();
    }

    function setupGrid() {
        initGrid();
        navButtons();
    }

    /*
     function checkForm() {
     var raVal = $("input[type='radio']:checked").val();

     //				$("#importDataType").val();
     //				if (document.importForm.name.value == "admin") {
     //					alert("姓名不正确");
     //					return false;
     //				} else {
     //					return true;
     //				}
     alert('test : ' + raVal);
     return false;
     }

     */

    function initGrid() {
        var grid_data =
            [
                {"organization_type_id": "1", "organization_type_key": "company", "organization_type_level": "2", "organization_type_name": "公司test"},
                {
                    "organization_type_id": "2",
                    "organization_type_key": "department",
                    "organization_type_level": "3",
                    "organization_type_name": "部门test"
                },
                {"organization_type_id": "3", "organization_type_key": "team", "organization_type_level": "5", "organization_type_name": "组test"},
                {"organization_type_id": "4", "organization_type_key": "group", "organization_type_level": "1", "organization_type_name": "集团test"},
                {"organization_type_id": "5", "organization_type_key": "center", "organization_type_level": "4", "organization_type_name": "中心test"},
            ];
        var excelDataJson = $("#ol-GridData").text();
        if (excelDataJson != "") {
            grid_data = JSON.parse(excelDataJson);
        }
        var lastsel;
        jQuery(grid_selector).jqGrid({
            data: grid_data,
            datatype: "local",
            height: 250,
            colNames: ['organization_type_id', '编码', '层级', '机构级别名称'],
            colModel: [
                {name: 'organization_type_id', index: 'organization_type_id'},
                {name: 'organization_type_key', index: 'organization_type_key', width: 90, editable: true},
                {
                    name: 'organization_type_level',
                    index: 'organization_type_level',
                    sortable: true,
                    width: 150,
                    editable: true,
                    editoptions: {size: "20", maxlength: "30"}
                },
                {
                    name: 'organization_type_name',
                    index: 'organization_type_name',
                    width: 150,
                    editable: true,
                    editoptions: {size: "20", maxlength: "30"}
                }
            ],
            viewrecords: true,
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: pager_selector,
            altRows: true,
            // toppager: true,
            multiselect: true,
            // multikey: "ctrlKey",
            multiboxonly: true,
            loadComplete: function () {
                var table = this;
                setTimeout(function () {
                    styleCheckbox(table);
                    updateActionIcons(table);
                    updatePagerIcons(table);
                    enableTooltips(table);
                }, 0);
            },
            onSelectRow: function (id) {
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


    function navButtons() {
        // navButtons
        jQuery(grid_selector).jqGrid('navGrid', '#grid-pager',
            {
                edit: true,
                editicon: 'icon-pencil blue',
                add: true,
                addicon: 'icon-plus-sign purple',
                del: true,
                delicon: 'icon-trash red',
                search: true,
                searchicon: 'icon-search orange',
                refresh: true,
                refreshicon: 'icon-refresh green',
                view: true,
                viewicon: 'icon-zoom-in grey'
            },
            {
                recreateForm: true,
                beforeShowForm: function (e) {
                    var form = $(e[0]);

                }
            },
            {
                closeAfterAdd: true,
                recreateForm: true,
                viewPagerButtons: false,
                beforeShowForm: function (e) {
                    var form = $(e[0]);

                }
            },
            {
                recreateForm: true,
                beforeShowForm: function (e) {
                    var form = $(e[0]);

                },
                onClick: function (e) {
                    alert(1);
                }
            },
            {
                recreateForm: true,
                afterShowSearch: function (e) {
                    var form = $(e[0]);

                },
                afterRedraw: function () {

                }
                ,
                multipleSearch: true
            },
            {
                recreateForm: true,
                beforeShowForm: function (e) {
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

    function aceSwitch(cellvalue, options, cell) {
    }

    function pickDate(cellvalue, options, cell) {
    }

    function styleCheckbox(table) {
    }

    function updateActionIcons(table) {
    }

    function updatePagerIcons(table) {
        var replacement = {
            'ui-icon-seek-first': 'icon-double-angle-left bigger-140',
            'ui-icon-seek-prev': 'icon-angle-left bigger-140',
            'ui-icon-seek-next': 'icon-angle-right bigger-140',
            'ui-icon-seek-end': 'icon-double-angle-right bigger-140'
        };
        $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function () {
            var icon = $(this);
            var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

            if ($class in replacement) icon.attr('class', 'ui-icon ' + replacement[$class]);
        });
    }

    function enableTooltips(table) {
    }

    setup();
    $('#importForm').validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: false,
        rules: {
            'type': {
                required: true
            },
            'inputfile': {
                required: true
            }
        },
        messages: {
            'type': {
                required: "请选择模板类型"
            },
            'inputfile': {
                required: "请选择文件"
            }
        },
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        success: function (label) {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement: function (error, element) {
            element.parent('div').append(error);
        },
        submitHandler: function (form) {
            ajaxFunctionForm(form);
        }
    });


    function ajaxFunctionForm() {
        var _from = $('#importForm');
        _from.ajaxSubmit({
            success: function (data) {
                var type = data.type ? 'success' : 'error';
                Messenger().post({
                    message: data.msg,
                    type: type
                })
            }
        });
    }

    /********** 上传文件
     * -------------------------------------------------------------
     */
    $(function () {
        $('#inputfile').ace_file_input({
            style: 'well',
            btn_choose: '点击选择文件...',
            btn_change: null,
            no_icon: 'icon-cloud-upload',
            droppable: true,
            thumbnail: 'small'//large | fit
            //,icon_remove:null//set null, to hide remove/reset button
            /**,before_change:function(files, dropped) {
						//Check an example below
						//or examples/file-upload.html
						return true;
					}*/
            /**,before_remove : function() {
						return true;
					}*/
            ,
            preview_error: function (filename, error_code) {
                //name of the file that failed
                //error_code values
                //1 = 'FILE_LOAD_FAILED',
                //2 = 'IMAGE_LOAD_FAILED',
                //3 = 'THUMBNAIL_FAILED'
                //alert(error_code);
            }

        }).on('change', function () {
            //console.log($(this).data('ace_input_files'));
            //console.log($(this).data('ace_input_method'));
        });
    });


});