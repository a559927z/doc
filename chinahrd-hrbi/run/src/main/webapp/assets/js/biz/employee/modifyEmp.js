/**
 * Create by zhiwei 20161206
 */
require(['jquery', 'bootstrap', 'select2', 'datetimepicker', 'jgGrid', 'utils'],function($) {
	var nation = ['汉','蒙古','回','藏','维吾尔','苗','彝','壮','布依','朝鲜','满','侗','瑶','白','土家','哈尼','哈萨克','傣','黎','僳僳','佤','畲','高山','拉祜','水','东乡','纳西','景颇','柯尔克孜','土','达斡尔','仫佬','羌','布朗','撒拉','毛南','仡佬','锡伯','阿昌','普米','塔吉克','怒','乌孜别克','俄罗斯','鄂温克','德昂','保安','裕固','京','塔塔尔','独龙','鄂伦春','赫哲','门巴','珞巴','基诺'];
	var pattyName = ['群众','中共党员','中共预备党员','共青团员','民革党员','民盟盟员','民建会员','民进会员','农工党党员','致公党党员','九三学社社员','台盟盟员','无党派人士'];
	var nationalType = ['18位身份证号','8位护照','9位护照','10位护照'];
	var contract = ['劳动合同（非全）','劳动合同（全）'];
    var webRoot = G_WEB_ROOT;
	var urls = {
			addEmpUrl: webRoot+'/emp/saveEmpInfo.do',//提交
			getDirsUrl: webRoot+'/emp/getDirs.do',//获取页面下拉框选项
			existEmp: webRoot+'/emp/existEmp.do',//查重
	        toTalentSearchView: webRoot + '/emp/toTalentSearchView.do',	//跳到员工搜索初始化页面
	        findEmpUrl: webRoot + '/emp/findEmpAll.do',   //查询所有的员工信息
	        modifyUserUrl: webRoot + '/user/operateUser.do',//修改用户信息
	        modifyPwd: webRoot + '/user/updateUserPWDbyUserName.do', //修改登录密码
	        existUser: webRoot + '/user/existUser.do',	//判断用户是否存在
	        requiredOrgUrl: webRoot + '/organ/findList.do'//获取org
	};

	/****弹出窗查找上级Start************/

	var searchGridObj = {
	        gridId: '#searchGridTable',
	        init: function (keyTxt) {
	            var self = this;
	            gridOption.postData = {'keyName': keyTxt, 'reqOrgId': reqOrgId};
	            $(self.gridId).jqGrid(gridOption);
	        },
	        resizeGridData: function (keyTxt) {
	            var self = this;
	            $(self.gridId).clearGridData().setGridParam({
	                postData: {'keyName': keyTxt, 'reqOrgId': reqOrgId}
	            }).trigger("reloadGrid");
	            self.resizeGrid();
	        },
	        resizeGrid: function () {
	            $(this.gridId).setGridWidth($('#searchGridPanel').width() * 0.98);
	        }
	  }

    var win = top != window ? top.window : window;
	$(win.document.getElementById('tree')).next().show();
    var reqOrgId = win.currOrganId;

    win.organizationObject = function (organId) {
        reqOrgId = organId;
    }
    
	var gridOption = {
	        url: urls.findEmpUrl,
	        datatype: 'json',
	        postData: {},
	        mtype: 'POST',
	        autowidth: true,
	        height: 355,
	        colNames: ['id', '名称', '所属机构', '岗位', '序列', '职位层级', '操作'],
	        colModel: [
	            {name: 'empId', index: 'empId', hidedlg: true, hidden: true, sortable: false, width: 40},
	            {
	                name: 'userNameCh',
	                index: 'userNameCh',
	                editable: true,
	                width: 80,
	                sortable: false,
	                editoptions: {size: "20", maxlength: "30"}
	            },
	            {
	                name: 'organizationName',
	                index: 'organizationName',
	                width: 90,
	                sortable: false,
	                editable: true,
	                editoptions: {size: "20", maxlength: "30"}
	            },
	            {
	                name: 'positionName',
	                index: 'positionName',
	                width: 90,
	                sortable: false,
	                editable: true,
	                align: 'center',
	                editoptions: {size: "20", maxlength: "5"}
	            },
	            {
	                name: 'sequenceName',
	                index: 'sequenceName',
	                width: 80,
	                sortable: false,
	                editable: true,
	                align: 'center',
	                editoptions: {size: "20", maxlength: "30"}
	            },
	            {
	                name: 'abilityName',
	                index: 'abilityName',
	                width: 80,
	                sortable: false,
	                editable: true,
	                align: 'center',
	                editoptions: {size: "20", maxlength: "30"}
	            },
	            {
	                name: 'myac',
	                index: '',
	                width: 220,
	                fixed: true,
	                align: 'center',
	                sortable: false,
	                resize: false,
	                search: false
	            }
	        ],
	        hideCol: ['userKey'],
	        rownumbers: true,
	        rownumWidth: 50,
	        viewrecords: true,
	        rowNum: 10,
	        rowList: [10, 20, 30],
	        pager: "#searchGridPager",
	        rowHeight: 36,
	        styleUI: 'Bootstrap',
	        loadComplete: function (xhr) {
	            var rows = xhr.rows;
	            var gridSelector = searchGridObj.gridId;
	            var ids = $(gridSelector).jqGrid('getDataIDs');
	            for (var i = 0; i < ids.length; i++) {
	                var col = ids[i];
	                var html = '<button id="emp_col" data-index="' + i + '" type="button" class="btn btn-primary btn-sm">选定</button>';
	                $(gridSelector).jqGrid('setRowData', col, {myac: html});
	            }
	            $('.btn-primary').unbind().bind('click', function (e) {
	                var _this = $(this);
		    		setTimeout(	function(){
    	                var idx = _this.attr('data-index');
    	                var userObj = rows[idx];
    	                $("#empHfName").val(userObj.userNameCh);
    	                $("#empHfKey").val(userObj.userNameCh.substring(0,userObj.userNameCh.indexOf('(')));
    	                $("#empHfId").val(userObj.empId);
    	                $("#searchModal").modal('hide');
    				},500);
//	                alert(userObj.userNameCh+":"+userObj.userNameCh.substring(0,userObj.userNameCh.indexOf('('))+":"+userObj.empId);
	            });

	            var tableHeight = 0, iFrameHeight = 0;
	            if ($("#searchGridTable").data("tableHeight") == undefined) {
	                tableHeight = $("#searchGridTable").height();
	                $("#searchGridTable").data("tableHeight", tableHeight);
	            } else {
	                tableHeight = $("#searchGridTable").data("tableHeight");
	            }
	            if ($("#searchGridTable").data("iFrameHeight") == undefined) {
	                iFrameHeight = $("#searchGridTable").parents("body").height();
	                $("#searchGridTable").data("iFrameHeight", iFrameHeight);
	            } else {
	                iFrameHeight = $("#searchGridTable").data("iFrameHeight")
	            }
	            //window.parent.$("iframe").css({"height":$("#searchGridTable").height()-tableHeight+iFrameHeight});
	            $(".ui-jqgrid-bdiv").css({"height": $("#searchGridTable").height() + 1 + "px"});

	            $(".ui-jqgrid-htable,#searchGridTable").css({"width": $("#searchGridPanel").width() + "px"});
	        },
	        gridComplete: function () {
	            window.scrollTo(0, $("#searchGridTable_rn").offset().top);
	        }
	    };
	/****弹出窗查找上级END************/
	/**员工基础信息**/
	var empBaseInfo = {
			degreeId: 'degree',
			nationId: 'nation',
			pattyNameId: 'pattyName',
			nationalTypeId: 'nationalType',
			workPlaceId: 'workPlace',//工作地点
			organNameId: 'organName',//部门
			positionNameId: 'positionName',//岗位
			contractId: 'contract',//合同性质
			sequenceNameId: 'sequenceName',//主序列
			sequenceSubNameId: 'sequenceSubName',//子序列
			abilityNameId: 'abilityName',//职位层级
			abilityLvId: 'abilityLv',//职级
			jobTitleNameId: 'jobTitleName',//职衔
			applyChannelId: 'applyChannel',//应聘渠道
			submitEmpInfoId: 'onEmpInfOkBtnId',
			init: function(){
				this.initSelectFun();
				this.initDatetime();
				this.changezj();
				this.modifyEmpfun();
				this.modifyUserFun();
				this.initSearchEmp();
				this.initValidate();
				this.makeRankName();
				this.initBtns();
			},
			initSelectFun: function () {
	            var self = this;
	            var eId = $("#myEmpId").val();
	            var parentOrgId = $('#organizationParentName').attr("value");
	            var param = {
	            		'empId': eId,
	            		'parentOrgId': parentOrgId
	            };
	            $.ajax({
	            	url: urls.getDirsUrl,
	            	data: param,
                    type: 'post',
                    success: function (data) {
                    	self.initSelector(data);
                    },
                    error: function (XmlHttpRequest, errorThrown) {
                    	
                    }
	            });
	            
	        },
	        //初始化日期选择selector
	        initDatetime: function(){
	        	$("#birthD").datetimepicker({
			        format: "yyyy-mm-dd",
			        autoclose: true,
			        minView:'month',
			        language:  'zh-CN',
			        todayBtn: true,
			        startDate: "1901-00-00",
			        pickerPosition: "bottom-left"
			    });

	        	$("#indate").datetimepicker({
			        format: "yyyy-mm-dd",
			        autoclose: true,
			        minView:'month',
			        language:  'zh-CN',
			        todayBtn: true,
			        startDate: "2000-00-00",
			        pickerPosition: "bottom-left"
			    });
	        	$("#turndate").datetimepicker({
			        format: "yyyy-mm-dd",
			        minView:'month',
			        language:  'zh-CN',
			        autoclose: true,
			        todayBtn: true,
			        startDate: "2000-00-00",
			        pickerPosition: "bottom-left"
			    });
        		var empId = $("#newEmpId").val();
        		if(empId == undefined || empId == '' || empId == 'null'){}
        		else{
        			$(".offDate").removeClass("hide");
		        	$("#offDate").datetimepicker({
				        format: "yyyy-mm-dd",
				        minView:'month',
				        language:  'zh-CN',
				        autoclose: true,
				        todayBtn: true,
				        startDate: "2000-00-00",
				        pickerPosition: "bottom-left"
				    });
        		}
	        	
	        },
	        //初始化各个selector
	        initSelector: function(data){
	        	var self = this;
	        	var de = [];
	            var na = [];
	            var pa = [];
	            var nat = [];
	            //学历
	            var degree = data.degree;
	            for(var i = 0,size = degree.length; i < size; i++){
	            	if(i == 0)
	            		de.push('<option selected="selected"  value="' + degree[i].itemId + '">' + degree[i].itemName + '</option>');
	            	else
	            		de.push('<option value="' + degree[i].itemId + '">' + degree[i].itemName + '</option>');
	            }
	            $("#" + self.degreeId).append(de.join(""));
	            $("#" + self.degreeId).select2({tags:true,width: '50%'});
	            $("#" + self.degreeId).val($("#" + self.degreeId).attr("value")).trigger('change');
	            //民族
	            for(var i = 0,size = nation.length; i < size; i++){
	            	if(i == 0)
	            		na.push('<option selected="selected" value="' + nation[i] + '">' + nation[i] + '</option>');
	            	else
	            		na.push('<option value = "' + nation[i] + '">' + nation[i] + '</option>');
	            }
	            $("#" + self.nationId).append(na.join(""));
	            $("#" + self.nationId).select2({tags:true,width: '50%'});
	            $("#" + self.nationId).val( $("#" + self.nationId).attr("value")).trigger('change');
	            //政治面貌
	            for(var i = 0,size = pattyName.length; i < size; i++){
	            	if(i == 0)
	            		pa.push('<option selected="selected" value="' + pattyName[i] + '">' + pattyName[i] + '</option>');
	            	else
	            		pa.push('<option value = "' + pattyName[i] + '">' + pattyName[i] + '</option>');
	            }
	            $("#" + self.pattyNameId).append(pa.join(""));
	            $("#" + self.pattyNameId).select2({tags:true,width: '50%'});
	            $("#" + self.pattyNameId).val($("#" + self.pattyNameId).attr("value")).trigger('change');
	            //身份证类型
	            for(var i = 0,size = nationalType.length; i < size; i++){
	            	if(i == 0)
	            		nat.push('<option selected="selected" value="' + nationalType[i] + '">' + nationalType[i] + '</option>');
	            	else
	            		nat.push('<option value = "' + nationalType[i] + '">' + nationalType[i] + '</option>');
	            }
	            $("#" + self.nationalTypeId).append(nat.join(""));
	            $("#" + self.nationalTypeId).select2({tags:true,width: '50%'});
	            $("#" + self.nationalTypeId).val($("#" + self.nationalTypeId).attr("value")).trigger('change');
	            
	            //工作地点
	            var wp = [];
	            var workPlace = data.workplace;
	            for(var i = 0,size = workPlace.length; i < size; i++){
	            	if(i == 0)
	            		wp.push('<option selected="selected"  value="' + workPlace[i].k + '">' + workPlace[i].v + '</option>');
	            	else
	            		wp.push('<option value = "' + workPlace[i].k + '">' + workPlace[i].v + '</option>');
	            }
	            $("#" + self.workPlaceId).append(wp.join(""));
	            $("#" + self.workPlaceId).select2({tags:true,width: '50%'});
	            $("#" + self.workPlaceId).val($("#" + self.workPlaceId).attr("value")).trigger('change');
	            //工作单位
	            var on = [];
	            var organName = data.parentorgrization;
	            for(var i = 0,size = organName.length; i < size; i++){
	            	if(i == 0)
	            		on.push('<option selected="selected"  value="' + organName[i].organizationId + '">' + organName[i].organizationName + '</option>');
	            	else
	            		on.push('<option value = "' + organName[i].organizationId + '">' + organName[i].organizationName + '</option>');
	            }
	            $("#organizationParentName").append(on.join(""));
	            $("#organizationParentName").select2({tags:true,width: '50%'});
	            $("#organizationParentName").val($("#organizationParentName").attr("value")).trigger('change');
	            //工作部门
	            var on1 = [];
	            var organName1 = data.orgrization;
	            if(organName1 != undefined){
		            for(var i = 0,size = organName1.length; i < size; i++){
		            	if(i == 0)
		            		on1.push('<option selected="selected"  value="' + organName1[i].organizationId + '">' + organName1[i].organizationName + '</option>');
		            	else
		            		on1.push('<option value = "' + organName1[i].organizationId + '">' + organName1[i].organizationName + '</option>');
		            }
		            $("#" + self.organNameId).append(on1.join(""));
		            $("#" + self.organNameId).select2({tags:true,width: '50%'});
		            $("#" + self.organNameId).val($("#" + self.organNameId).attr("value")).trigger('change');
	            }
	            //任职岗位
	            var pos = [];
	            var position = data.position;
	            for(var i = 0,size = position.length; i < size; i++){
	            	if(i == 0)
	            		pos.push('<option selected="selected"  value="' + position[i].k + '">' + position[i].v + '</option>');
	            	else
	            		pos.push('<option value = "' + position[i].k + '">' + position[i].v + '</option>');
	            }
	            $("#" + self.positionId).append(pos.join(""));
	            $("#" + self.positionId).select2({tags:true,width: '50%'});
	            $("#" + self.positionId).val($("#" + self.positionId).attr("value")).trigger('change');
	            //合同性质
	            var con = [];
	            for(var i = 0,size = contract.length; i < size; i++){
	            	if(i == 0)
	            		con.push('<option selected="selected"  value="' + contract[i] + '">' + contract[i] + '</option>');
	            	else
	            		con.push('<option value = "' + contract[i] + '">' + contract[i] + '</option>');
	            }
	            $("#" + self.contractId).append(con.join(""));
	            $("#" + self.contracteId).select2({tags:true,width: '50%'});
	            $("#" + self.contracteId).val($("#" + self.contracteId).attr("value")).trigger('change');
	            //主序列
	            var seq = [];
	            var sequnce = data.sequnce;
	            for(var i = 0,size = sequnce.length; i < size; i++){
	            	seq.push('<option id="' + sequnce[i].itemId + '" prefix="' + sequnce[i].prefix + '" value="' + sequnce[i].itemId + '">' + sequnce[i].itemName + '</option>');
	            }
	            $("#" + self.sequenceNameId).append(seq.join(""));
	            $("#" + self.sequenceNameId).select2({tags:true,width: '50%'});
	            $("#" + self.sequenceNameId).val($("#" + self.sequenceNameId).attr("value")).trigger('change');
	            //子序列
	            var subseq = [];
	            var subsequnce = data.subsequnce;
	            for(var i = 0,size = subsequnce.length; i < size; i++){
	            	subseq.push('<option id="' + subsequnce[i].subId + '" prefix="' + subsequnce[i].curtName + '" value="' + subsequnce[i].subId + '">' + subsequnce[i].itemName + '</option>');
	            }
	            $("#" + self.sequenceSubNameId).append(subseq.join(""));
	            $("#" + self.sequenceSubNameId).select2({tags:true,width: '50%'});
	            $("#" + self.sequenceSubNameId).val($("#" + self.sequenceSubNameId).attr("value")).trigger('change');
	            //职位层级
	            var abn = [];
	            var positionAbility = data.positionAbility;
	            for(var i = 0,size = positionAbility.length; i < size; i++){
	            	abn.push('<option id="' + positionAbility[i].abilityId + '" prefix="' + positionAbility[i].curtName + '" value="' + positionAbility[i].abilityId + '">' + positionAbility[i].abilityName + '</option>');
	            }
	            $("#" + self.abilityNameId).append(abn.join(""));
	            $("#" + self.abilityNameId).select2({tags:true,width: '50%'});
	            $("#" + self.abilityNameId).val($("#" + self.abilityNameId).attr("value")).trigger('change');
	            //职级
	            var ablv = [];
	            var abilityLv = data.abilityLv;
	            for(var i = 0,size = abilityLv.length; i < size; i++){
	            	ablv.push('<option id="' + abilityLv[i].abilityLvId + '" prefix="' + abilityLv[i].curtName + '" value="' + abilityLv[i].abilityLvId + '">' + abilityLv[i].abilityLvName + '</option>');
	            }
	            $("#" + self.abilityLvId).append(ablv.join(""));
	            $("#" + self.abilityLvId).select2({tags:true,width: '50%'});
	            $("#" + self.abilityLvId).val($("#" + self.abilityLvId).attr("value")).trigger('change');
	            //职衔
	            var jt = [];
	            var jobTitle = data.jobTitle;
	            for(var i = 0,size = jobTitle.length; i < size; i++){
	            	if(i == 0)
	            		jt.push('<option selected="selected"  value="' + jobTitle[i].k + '">' + jobTitle[i].v + '</option>');
	            	else
	            		jt.push('<option value = "' + jobTitle[i].k + '">' + jobTitle[i].v + '</option>');
	            }
	            $("#" + self.jobTitleNameId).append(jt.join(""));
	            $("#" + self.jobTitleNameId).select2({tags:true,width: '50%'});
	            $("#" + self.jobTitleNameId).val($("#" + self.jobTitleNameId).attr("value")).trigger('change');
	            //应聘渠道
	            var cl = [];
	            var channel = data.channel;
	            for(var i = 0,size = channel.length; i < size; i++){
	            	if(i == 0)
	            		cl.push('<option selected="selected"  value="' + channel[i].k + '">' + channel[i].v + '</option>');
	            	else
	            		cl.push('<option value = "' + channel[i].k + '">' + channel[i].v + '</option>');
	            }
	            $("#" + self.applyChannelId).append(cl.join(""));
	            $("#" + self.applyChannelId).select2({tags:true,width: '50%'});
	            $("#" + self.applyChannelId).val($("#" + self.applyChannelId).attr("value")).trigger('change');
	            //岗位
	            var pn = [];
	            var position = data.position;
	            for(var i = 0,size = position.length; i < size; i++){
	            	if(i == 0)
	            		pn.push('<option selected="selected"  value="' + position[i].k + '">' + position[i].v + '</option>');
	            	else
	            		pn.push('<option value = "' + position[i].k + '">' + position[i].v + '</option>');
	            }
	            $("#" + self.positionNameId).append(pn.join(""));
	            $("#" + self.positionNameId).select2({tags:true,width: '50%'});
	            $("#positionName").val($("#positionName").attr("value")).trigger('change');
	            //婚姻状态
	            $("#marryStatus").select2({tags:true,width: '50%'});
	            $("#marryStatus").val($("#marryStatus").attr("value")).trigger('change');
	            //合同性质
	            $("#contract").select2({language: 'zh-CN',tags:true,width: '50%'});
//	            $("#contract").select2('val', $("#contract").val());
	            $("#contract").val($("#contract").attr("value")).trigger('change');
	            //员工状态
	            $("#passtimeOrFulltime").select2({tags:true,width: '50%'});
	            $("#passtimeOrFulltime").val($("#passtimeOrFulltime").attr("value")).trigger('change');
	            //性别
	            $("#sex").select2({tags:true,width: '50%'});
	            $("#isRegular").select2({tags:true,width: '50%'});
	            $("#sex").val($("#sex").attr("value")).trigger('change');
//	            //锁定状态
//	            $("#isLock").select2({tags:true, width: '50%'});
//	            $("#isLock").val($("#isLock").attr("value")).trigger('change');
	            
	        },
	        updateSelectOrg: function(){
	        	var self = this;
	        	//工作部门
	            var on = [];
	            var orgPraId = $("#organizationParentName").select2("data")[0].id;
	            var param = {
	            		'organizationParentId': orgPraId
	            };
	            $.ajax({
	            	url: urls.requiredOrgUrl,
	            	data: param,
	            	type: 'POST',
	            	success: function (data){
	            		if(data != undefined){
	            			if(data.length == 0){
	                        	self.alertModelFun("请求工作部门-该单位无部门！");
		        	            $("#" + self.organNameId).html("");
		        	            $("#" + self.organNameId).select2({tags:true,width: '50%'});
	                        	return;
	            			}
	        	            $("#" + self.organNameId).html("");
	        	            for(var i = 0,size = data.length; i < size; i++){
	        	            	if(i == 0)
	        	            		on.push('<option selected="selected"  value="' + data[i].organizationId + '">' + data[i].organizationName + '</option>');
	        	            	else
	        	            		on.push('<option value = "' + data[i].organizationId + '">' + data[i].organizationName + '</option>');
	        	            }
	        	            $("#" + self.organNameId).append(on.join(""));
	        	            $("#" + self.organNameId).select2({tags:true,width: '50%'});
	        	            $("#" + self.organNameId).val($("#" + self.organNameId).attr("value")).trigger('change');
	            			
	            		}
	            		else{
                        	self.alertModelFun("请求工作部门数据-服务器异常");
	            		}
	            	},
                    error: function (XmlHttpRequest, errorThrown) {
                    	self.alertModelFun("请求工作部门数据超时.");
                    }
	            });
	        },
	        changezj: function(){
	        	var self = this;
	        	$("#" + self.sequenceNameId).on('select2:select', function (){
	        		self.makeRankName();
	        		});
	        	$("#" + self.sequenceSubNameId).on('select2:select', function (){
	        		self.makeRankName();
	        		});
	        	$("#" + self.abilityNameId).on('select2:select', function (){
	        		self.makeRankName();
	        		});
	        	$("#" + self.abilityLvId).on('select2:select', function (){
	        		self.makeRankName();
	        		});
	        	$("#organizationParentName").on('select2:select', function(){
	        		self.updateSelectOrg();
	        	});
	        },
	        initBtns: function(){

                $('#'+self.submitEmpInfoId).attr('disabled', false).html('提交');
                $('#onEmpUserOkBtnId').attr('disabled', false).html('提交');
	        },
	        //主子序列层级职级 计算逻辑
	        makeRankName: function(){
	        	var self = this;
	        	var mainseq = $("#" + self.sequenceNameId).select2("data")[0].id;
	        	var mainseqCode = $("#" + mainseq).attr("prefix");
	        	var subseq = $("#" + self.sequenceSubNameId).select2("data")[0].id;
	        	var subseqCode = $("#" + subseq).attr("prefix");
	        	var ability = $("#" + self.abilityNameId).select2("data")[0].id;
	        	var abilityCode = $("#" + ability).attr("prefix");
	        	var abilitylv = $("#" + self.abilityLvId).select2("data")[0].id;
	        	var abilitylvCode = $("#" + abilitylv).attr("prefix");
	        	$("#rankName").val( mainseqCode+""+subseqCode+""+abilityCode+"."+abilitylvCode);
	        },
	        //提交基础信息方法
	        modifyEmpfun: function(){
	        	var self = this;
	        	//绑定点击提交按钮（base）
	            $("#" + self.submitEmpInfoId).unbind('click').bind('click', function () {
	            	var empKey = $("#empKey").val();
	            	var userName = $('#userName').val();
	            	var userNameCh = $('#userNameCh').val();
	            	var empHfId = $("#empHfId").val();
	            	var empHfKey = $("#empHfKey").val();
	            	var email = $("#email").val();
	            	var passtimeOrFulltime = $("#passtimeOrFulltime").val();
	            	var sex = ""; 
	            	if($("#sex").select2("data").length>0)
	            		sex = $("#sex").select2("data")[0].id;
	            	var nation = ""; 
	            	if($("#nation").select2("data").length>0)
	            		nation = $("#nation").select2("data")[0].text;
	            	var degreeId = ""; 
	            	if($("#degree").select2("data").length>0)
	            		degreeId = $("#degree").select2("data")[0].id;
	            	var degree = ""; 
	            	if($("#degree").select2("data").length>0)
	            		degree = $("#degree").select2("data")[0].text;
	            	var nativePlace = $("#nativePlace").val();
	            	var country = $("#country").val();
	            	var birthPlace = $("#birthPlace").val();
	            	var birthDate = $("#birthDate").val().substring(0,10);
	            	var nationalId = $("#nationalId").val();
	            	var nationalType = ""; 
	            	if($("#nationalType").select2("data").length>0)
	            		nationalType = $("#nationalType").select2("data")[0].id;
	            	var marryStatus = ""; 
	            	if($("#marryStatus").select2("data").length>0)
	            		marryStatus = $("#marryStatus").select2("data")[0].id;
	            	var pattyName = ""; 
	            	if($("#pattyName").select2("data").length>0)
	            		pattyName = $("#pattyName").select2("data")[0].text;
	            	var organId = ""; 
	            	if($("#organizationParentName").select2("data").length>0)
	            	if($("#organName").select2("data").length>0)
	            		organId = $("#organName").select2("data")[0].id;
	            	var organName = ""; 
	            	if($("#organizationParentName").select2("data").length>0)
	            	if($("#organName").select2("data").length>0)
	            		organName = $("#organName").select2("data")[0].text;
	            	var positionId = ""; 
	            	if($("#positionName").select2("data").length>0)
	            		positionId = $("#positionName").select2("data")[0].id;
	            	var positionName = ""; 
	            	if($("#positionName").select2("data").length>0)
	            		positionName = $("#positionName").select2("data")[0].text;
	            	var mainseqId = ""; 
	            	if($("#" + self.sequenceNameId).select2("data").length>0)
	            		mainseqId = $("#" + self.sequenceNameId).select2("data")[0].id;
	            	var mainseqName = ""; 
	            	if($("#" + self.sequenceNameId).select2("data").length>0)
	            		mainseqName = $("#" + self.sequenceNameId).select2("data")[0].text;
		        	var subseqId = ""; 
	            	if($("#" + self.sequenceSubNameId).select2("data").length>0)
	            		subseqId = $("#" + self.sequenceSubNameId).select2("data")[0].id;
		        	var subseqName = ""; 
	            	if($("#" + self.sequenceSubNameId).select2("data").length>0)
	            		subseqName = $("#" + self.sequenceSubNameId).select2("data")[0].text;
		        	var abilityId = ""; 
	            	if($("#" + self.abilityNameId).select2("data").length>0)
	            		abilityId = $("#" + self.abilityNameId).select2("data")[0].id;
		        	var abilityName = ""; 
	            	if($("#" + self.abilityNameId).select2("data").length>0)
	            		abilityName = $("#" + self.abilityNameId).select2("data")[0].text;
		        	var jobTitleId = ""; 
	            	if($("#" + self.jobTitleNameId).select2("data").length>0)
	            		jobTitleId = $("#" + self.jobTitleNameId).select2("data")[0].id;
		        	var jobTitleName = ""; 
	            	if($("#" + self.jobTitleNameId).select2("data").length>0)
	            		jobTitleName = $("#" + self.jobTitleNameId).select2("data")[0].text;
		        	var abilitylvId = ""; 
	            	if($("#" + self.abilityLvId).select2("data").length>0)
	            		abilitylvId = $("#" + self.abilityLvId).select2("data")[0].id;
		        	var abilitylvName = ""; 
	            	if($("#" + self.abilityLvId).select2("data").length>0)
	            		abilitylvName = $("#" + self.abilityLvId).select2("data")[0].text;
	            	var rankName = $("#rankName").val();
	            	//绩效
	            	//绩效名
	            	var telPhone = $("#telPhone").val();
	            	var qq = $("#qq").val();
	            	var wxCode = $("#wxCode").val();
	            	var address = $("#address").val();
	            	var contractUnit = $("#contractUnit").val();
	            	var workPlaceId = ""; 
	            	if($("#workPlace").select2("data").length>0)
	            		workPlaceId = $("#workPlace").select2("data")[0].id;
	            	var workPlaceName = ""; 
	            	if($("#workPlace").select2("data").length>0)
	            		workPlaceName = $("#workPlace").select2("data")[0].text;
	            	var regularDate = $("#regularDate").val().substring(0,10);
	            	var runOffDate = $("#runOffDate").val().substring(0,10);
	            	var applyChannel = ""; 
	            	if($("#applyChannel").select2("data").length>0)
	            		applyChannel = $("#applyChannel").select2("data")[0].id;

//	            	var childNum = $("#childNum").val();
//	            	var contactPersonName = $("#contactPersonName").val();
//	            	var contactTelPhone = $("#contactTelPhone").val();
//	            	var contactCall = $("#contactCall").val();
	            	
	            	var entryDate = $("#entryDate").val().substring(0,10);
	            	var newEmpId = $("#newEmpId").val();
	            	var vDimEmpId = $("#vDimEmpId").val();
	            	var customerId = $("#customerId").val();
	            	var isRegular = $("#isRegular").val();
	            	if(regularDate == '' || entryDate == '' || birthDate ==''){
                    	self.alertModelFun("时间项不能留空！");
                    	return;
	            	}
	            	if(organName == undefined || organName == ''){
                    	self.alertModelFun("部门不能留空！");
                    	return;
	            	}
	            	self.validateUserName();         	//数据库唯一键，自动提示异常
	            	self.validateEmpKey();

	            	var param = {
	            			'vDimEmpId': vDimEmpId,
	            			'customerId':	customerId,
	            			'empId':newEmpId,
	            			'empKey':empKey,
	            			'userName'          :userName           ,
	            			'userNameCh'        :userNameCh         ,
	            			'empHfId'           :empHfId            ,
	            			'empHfKey'          :empHfKey           ,
	            			'email'             :email              ,
	            			'passtimeOrFulltime':passtimeOrFulltime ,
	            			'sex'               :sex                ,
	            			'nation'            :nation             ,
	            			'degreeId'          :degreeId           ,
	            			'degree'            :degree             ,
	            			'nativePlace'       :nativePlace        ,
	            			'country'           :country            ,
	            			'birthPlace'        :birthPlace         ,
	            			'birthDate'         :birthDate          ,
	            			'nationalId'        :nationalId         ,
	            			'nationalType'      :nationalType       ,
	            			'marryStatus'       :marryStatus        ,
	            			'pattyName'         :pattyName          ,
	            			'organId'           :organId            ,
	            			'organName'         :organName          ,
	            			'positionId'        :positionId         ,
	            			'positionName'      :positionName       ,
	            			'mainseqId'         :mainseqId          ,
	            			'mainseqName'       :mainseqName        ,
	            			'subseqId'          :subseqId           ,
	            			'subseqName'        :subseqName         ,
	            			'abilityId'         :abilityId          ,
	            			'abilityName'       :abilityName        ,
	            			'jobTitleId'        :jobTitleId         ,
	            			'jobTitleName'      :jobTitleName       ,
	            			'abilitylvId'       :abilitylvId        ,
	            			'abilitylvName'     :abilitylvName      ,
	            			'rankName'          :rankName           ,
	            			'telPhone'          :telPhone           ,
	            			'qq'                :qq                 ,
	            			'wxCode'            :wxCode             ,
	            			'address'           :address            ,
	            			'contractUnit'      :contractUnit       ,
	            			'workPlaceId'       :workPlaceId        ,
	            			'workPlaceName'     :workPlaceName      ,
	            			'regularDate'       :regularDate        ,
	            			'runOffDate'		:runOffDate			,
	            			'applyChannel'      :applyChannel       ,
	            			'entryDate':entryDate,
	            			'isRegular':isRegular
	            	};
	            	$('#newEmpId').val("");
	                $('#'+self.submitEmpInfoId).attr('disabled', true).html('正在提交...');
	                $.ajax({
	                    url: urls.addEmpUrl,
	                    type: 'post',
	                    data: param,
	                    success: function (data) {
	                    	//隐藏提交状态
	    	                $('#'+self.submitEmpInfoId).attr('disabled', true).html('已保存');
	                        if (data.vDimEmpId != undefined && data.empId.length > 0) {
	                        	self.alertModelFun("保存基础信息成功，可以继续添加账号信息！");
	                        	$("#newEmpId").val(data.empId);
	                            return true;
	                        } 
	                        else{
		    	                $('#'+self.submitEmpInfoId).attr('disabled', false).html('提交');
	                        	self.alertModelFun("服务器保存异常");
	                        }
	                    },
	                    error: function (XmlHttpRequest, errorThrown) {
	    	                $('#'+self.submitEmpInfoId).attr('disabled', false).html('提交');
	                        self.alertModelFun("请求后台超时.");
	                    }
	                });
	            });
	        },
	        
	        /**
	         * 修改用户信息
	         */
	        modifyUserFun: function(){
	        	var self = this;
	        	var edit = "edit";
	        	var add = "add";
	        	var $oeuoBtn = $('#onEmpUserOkBtnId');
	        	$oeuoBtn.unbind('click').bind('click', function(){
	        		var empId = $("#newEmpId").val();
	        		if(empId == undefined || empId == '' || empId == 'null'){
	        			self.alertModelFun("请先保存基础信息！");
	        			setTimeout(function(){
	        				$('#submitModal').modal('hide');
	        			},1500);
	        			return;
	        		}
	        		var userUserName = $("#userUserName").val();
	        		var userUserNameCh = $("#userUserNameCh").val();  
	        		var userUserKey = $("#userUserKey").val();
	        		var userNote = $("#userNote").val();
	        		var changepwd = $("#changepwd").is(':checked');
	        		var userId = $("#userUserId").val();
	        		var email= $("#userEmail").val();
	        		//验证用户名是否重复
	        		self.validateUserUserName();
	        		//决定这次操作对用户是什么操作类型
	        		var oper ='';
	        		if(userId != undefined && userId != '' && userId != 'null')
	        			oper = edit;
	        		else
	        			oper = add;
	        		var param = {
	        				'empId' : empId,
	        				'userName':userUserName,
	        				'userNameCh':userUserNameCh,
	        				'userKey': userUserKey,
	        				'note':userNote,
	        				'id':userId,
	        				'userId':userId,
	        				'oper':oper,
	        				'email':email
	        		};
	        		$oeuoBtn.attr('disabled', true).html('正在提交...');
		                $.ajax({
		                    url: urls.modifyUserUrl,
		                    type: 'post',
		                    data: param,
		                    success: function (data) {
		                    	//隐藏提交状态
		                    	$oeuoBtn.attr('disabled', true).html('已保存');
		                        if (data != undefined && data == true) {
		                        	$oeuoBtn.attr('disabled', false).html('已保存');
		                        	self.alertModelFun("保存账号成功！");
		                        	setTimeout(function(){
		                        		if(changepwd != undefined && changepwd == true){
		                        			self.modifyPWDFun();
		                        		}
		                        	},1000);
		                            return true;
		                        } 
		                        else{
			                    	$oeuoBtn.attr('disabled', false).html('提交');
		                        	self.alertModelFun("服务器保存异常");
		                        }
		                    },
		                    error: function (XmlHttpRequest, errorThrown) {
		                    	$oeuoBtn.attr('disabled', false).html('提交');
		                        self.alertModelFun("请求后台超时.");
		                    }
		                });
	        	});
	        },
	        //修改密码
	        modifyPWDFun: function(){
	        	var self = this;
	        	var userName = $("#userUserName").val();
	        	var $oeuoBtn = $('#onEmpUserOkBtnId');
	        	var param = {
	        			'passwd': '123456',
	        			'userName': userName
	        	};
	        	$oeuoBtn.attr('disabled', true).html('修改密码...');
	        	$.ajax({
	        		url: urls.modifyPwd,
	        		type: 'POST',
	        		data: param,
	        		success: function(data){
	        			if(data != undefined && data == 'true'){
	        	        	$oeuoBtn.attr('disabled', false).html('已保存');
	        				self.alertModelFun("修改密码成功！");
	        			}
	        			else
	        				self.alertModelFun("服务器保存异常,此时只有密码未修改");
	        		},
	        		error: function(XmlHttpRequest, errorThrown){
    	                $('#'+self.submitEmpInfoId).attr('disabled', false).html('提交');
                        self.alertModelFun("请求修改密码超时.");
	        		}
	        	});
	        },
	        alertModelFun: function (data) {
	            $('#submitModal').modal('show').css({
	            	'padding-left': '20%',
	            	'padding-right': '20%'
                });
	            $('#submitModal').on('shown.bs.modal', function () {
	                $('#submitMsg').html(data);
	            });
	        },
	        validateEmpKey: function(){
	        	var ek = $("#empKey").val();
	        	var newEmpId = $("#newEmpId").val();
        		var param = {
        			'empKey':ek,
        			empId: newEmpId
        		};
        		if(ek == ''){
            		$("#empKeySpan").removeClass("hide");
            		$("#empKeySpan").html("需要输入员工号");
        		}else{
		            $.ajax({
		            	url: urls.existEmp,
		            	data:param,
	                    type: 'post',
	                    success: function (data) {
	                    	if(data+"" == "true"){
	                    		$("#empKeySpan").removeClass("hide");
	                    		$("#empKeySpan").html("有重复");
	                    	}
	                    	else{
	                    		$("#empKeySpan").addClass("hide");
	                    	}
	                    },
	                    error: function (XmlHttpRequest, errorThrown) {
	                    	self.alertModelFun("请求后台超时.");
	                    }
		            });
        		}
	        },
	        validateUserName: function(){
	        	var un = $("#userName").val();
	        	var newEmpId = $("#newEmpId").val();
        		var param = {
        			'userName': un,
        			empId: newEmpId
        		};
        		if(un == ''){
            		$("#userNameSpan").removeClass("hide");
            		$("#userNameSpan").html("需要输入用户名");
        			
        		}
        		else{
		            $.ajax({
		            	url: urls.existEmp,
		            	data:param,
	                    type: 'post',
	                    success: function (data) {
	                    	if(data+"" == 'true'){
	                    		$("#userNameSpan").removeClass("hide");
	                    		$("#userNameSpan").html("有重复");
	                    	}
	                    	else{
	                    		$("#userNameSpan").addClass("hide");
	                    	}
	                    },
	                    error: function (XmlHttpRequest, errorThrown) {
	                    	self.alertModelFun("请求后台超时.");
	                    }
		            });
        		}
	        },

	        validateUserUserName: function(){
	        	var un = $("#userUserName").val();
	        	var userEmpId = $("#userEmpId").val();
        		var param = {
        			'userName': un,
        			empId: userEmpId
        		};
        		if(un == ''){
            		$("#userUserNameSpan").removeClass("hide");
            		$("#userUserNameSpan").html("需要输入用户名");
        			
        		}
        		else{
		            $.ajax({
		            	url: urls.existUser,
		            	data:param,
	                    type: 'post',
	                    success: function (data) {
	                    	if(data+"" == 'true'){
	                    		$("#userUserNameSpan").removeClass("hide");
	                    		$("#userUserNameSpan").html("有重复");
	                    	}
	                    	else{
	                    		$("#userUserNameSpan").addClass("hide");
	                    	}
	                    },
	                    error: function (XmlHttpRequest, errorThrown) {
	                    	self.alertModelFun("请求后台超时.");
	                    }
		            });
        		}
	        },
	        initValidate: function(){
	        	var self = this;
	        	var $ek = $("#empKey");
	        	var $una = $("#userName");
	        	var $unach = $("#userNameCh");
	        	var $uuna = $("#userUserName");
	        	$ek.unbind("blur").bind("blur",function(){
	        		//默认vdimemp表的empKey与dimuser表的一致
	        		$("#userUserKey").val($ek.val());
	        		self.validateEmpKey();
	        	});
	        	$una.unbind("blur").bind("blur",function(){
	        		$("#userUserName").val($una.val());
	        		self.validateUserName();
	        	});
	        	$uuna.unbind("blur").bind("blur",function(){
	        		self.validateUserUserName();
	        	});
	        	$unach.unbind('blur').bind('blur', function(){
	        		$("#userUserNameCh").val($unach.val());
	        	});
	        },
	        initSearchEmp: function(){
	        	searchEmpId: 'searchEmp',
	        	$("#searchEmp").unbind("click").bind("click",function(){
	        		//alert($("#empHfName").val()+$("#empHfKey").val()+$("#empHfId").val());
	        		$("#searchModal").modal('show');
	        		//重置搜索框
	        		$("#firstSearchTxt").val("");
	        		$("#lastSearchTxt").val("");
	        		//设定宽度
	        		var $dia = $(".modal-dialog");
	        		$dia.width("80%");
	        		$dia.height("800px");
	                $('#firstSearchBtn').click(function () {
	                    var searchTxt = $.trim($('#firstSearchTxt').val());
	                    if (!$.isEmptyObject(searchTxt)) {
	                        initPage(searchTxt);
	                    }
	                });

	                $('#lastSearchBtn').click(function () {
	                    var searchTxt = $.trim($('#lastSearchTxt').val());
	                    if (!$.isEmptyObject(searchTxt)) {
	                        searchGridObj.resizeGridData(searchTxt);
	                    }
	                });
	                $("#firstSearchTxt").keydown(function (e) {
	                    if (e.keyCode == 13) {
	                        var firstSearchTxt = $.trim($('#firstSearchTxt').val());
	                        var hasHide = $('#firstRows').hasClass('hide');
	                        if (!$.isEmptyObject(firstSearchTxt) && !hasHide) {
	                            initPage(firstSearchTxt);
	                            return;
	                        }
	                        var lastSearchTxt = $.trim($('#lastSearchTxt').val());
	                        if (!$.isEmptyObject(lastSearchTxt)) {
	                            searchGridObj.resizeGridData(lastSearchTxt);
	                        }
	                    }
	                })
	                $("#lastSearchTxt").keydown(function (e) {
	                    if (e.keyCode == 13) {
	                        var firstSearchTxt = $.trim($('#firstSearchTxt').val());
	                        var hasHide = $('#firstRows').hasClass('hide');
	                        if (!$.isEmptyObject(firstSearchTxt) && !hasHide) {
	                            initPage(firstSearchTxt);
	                            return;
	                        }
	                        var lastSearchTxt = $.trim($('#lastSearchTxt').val());
	                        if (!$.isEmptyObject(lastSearchTxt)) {
	                            searchGridObj.resizeGridData(lastSearchTxt);
	                        }
	                    }
	                })

	                function initPage(searchTxt) {
	                    $('#lastRows').removeClass('hide');
	                    $('#firstRows').addClass('hide');
	                    $('#titleText').html(searchTxt);
	                    searchGridObj.init(searchTxt);
	                    $('#lastSearchTxt').val(searchTxt);
	                }	                
	                function backPage() {
	                    $('#lastRows').addClass('hide');
	                    $('#firstRows').removeClass('hide');
	                }

	                function initKeyNamePage() {
	                    var keyName = $.trim($('#keyName').val());
	                    if (keyName != '') {
	                        initPage(keyName);
	                    }
	                }

	                initKeyNamePage();
	                $("#gotoAdvancedView").click(function () {
	                    $(this).attr("href", webRoot + "/talentSearch/toTalentSearchAdvancedView");
	                });

	                $(window).resize(function () {
	                    searchGridObj.resizeGrid();
	                });

	                $('#searchReturn').click(function () {
	                	backPage();
	                });
	        	});
	        }
	};
	empBaseInfo.init();

});