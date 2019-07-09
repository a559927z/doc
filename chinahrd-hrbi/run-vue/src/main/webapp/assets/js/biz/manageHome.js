require(['jquery','moment', 'echarts', 'echarts/chart/funnel', 'echarts/chart/line', 'echarts/chart/bar', 'echarts/chart/pie',  'bootstrap', 'underscore',
         'organTreeSelector', 'utils', ,'unveil', 'messenger', 'jgGrid', 'ztree', 'riskTree','tooltipZrw','resize'
         ], function ($, moment, echarts ) {

    var riskFlagArr = ['gray', 'red', 'yellow', 'green'];
    var riskTreeOption = {
            hasSelect: false,
            data: null,
            hasTopText: false
        }

    var webRoot = G_WEB_ROOT;
    var urls = {
    	loaderUrl: webRoot + "/assets/img/base/loader.gif",
		getChildDataUrl: webRoot + '/manageHome/getChildOrgData.do',	//获取子节点数据
        getRemindEmpUrl: webRoot + '/manageHome/getRemindEmp.do',        //获取团队提醒生日榜信息
        getGainOfLossUrl: webRoot + '/manageHome/getGainOfLoss.do',        //获取当季人才损益信息
        getTeamImgAbUrl: webRoot + '/manageHome/getTeamImgAb.do',        //获取团队画像职位层级
        empBaseInfoUrl: webRoot + "/common/getEmpBaseInfo.do",	//获取员工基本信息
        empRiskDetailUrl: webRoot + "/dismissRisk/getEmpRiskDetail.do",	//获取员工离职风险信息
		getPerformance: webRoot + '/manageHome/getPerformance.do',          //绩效目标
		getWorkOvertimeUrl: webRoot + '/manageHome/getWorkOvertimeInfo.do' ,         //人员加班详细信息
		//getWarnInfoUrl: webRoot + '/manageHome/getWarnInfo.do' ,         //人员预警
		getRunOffWarnEmpInfoUrl: webRoot + '/manageHome/getRunOffWarnEmpInfo.do' ,         //离职风险
		getLowPerformanceUrl: webRoot + '/manageHome/getLowPerformanceInfo.do' ,         //低绩效预警
		getHighPerformanceUrl: webRoot + '/manageHome/getHighPerformanceInfo.do' ,         //高绩效预警
		getOvertimeEmpUrl: webRoot + '/manageHome/getOvertimeEmp.do' ,         //低绩效预警
		toEmpDetailUrl: webRoot + '/talentProfile/toTalentDetailView.do',    //跳转到员工详情
		getTalentDevelUrl: webRoot + '/manageHome/getTalentDevel.do',          //人才发展
		getTalentDevelExamItemUrl: webRoot + '/manageHome/getTalentDevelExamItem.do'          //人才发展-测评项目
    };

    var treeSelector = null;
    var reqOrgId = $('#reqOrganId').val();
    var reqOrgText = $('#reqOrganName').val();
   
	var ecConfig = require('echarts/config');
	var TextShape = require('zrender/shape/Text');

    $(window).resize(function(){
        teamImgAbObj.funnelObj.resize();
        talentDevelObj.talentDevelObj.resize();
    });

	$("#toTeamImgView").click(function(){
		window.open(webRoot + "/manageHome/toTeamImgView"+"?organId="+reqOrgId);
	});
	$("#toPerChangeView").click(function(){
		window.open(webRoot + "/perChange/toPerChangeView"+"?organId="+reqOrgId);
	});
	
 $("#gotoOrgView").click(function(){
	 $(this).attr("href",webRoot+"/orgChart/toOrgView"+"?organId="+reqOrgId)
	//	window.open(webRoot + "/manageHome/toTeamImgView"+"?organId="+reqOrgId);
	 return true;
 });
    function initOrganTree() {
        treeSelector = $("#tree").organTreeSelector({
            multiple: false,
            value: {
                id: reqOrgId,
                text: reqOrgText
            },
//			params : {
//				name : "isSingleOrgan",//只显示有独立核算的机构(name必须为isSingleOrgan)
//				value : true
//			},
            showSelectBtn: false,
            onSelect: function (ids, texts) {
            	 $("#gotoOrgView").unbind("click").click(function(){
            		 $(this).attr("href",webRoot+"/orgChart/toOrgView"+"?organId="+ids)
            		//	window.open(webRoot + "/manageHome/toTeamImgView"+"?organId="+reqOrgId);
            		 return true;
            	 });
            	reqOrgId=ids;
            	warnPanel.loadPanel(ids);
                remindObj.requestData(ids);
				teamImgAbObj.getRequestData(ids);
                performanceObj.Init(ids);
				talentDevelObj.getRequestData(ids);
				 //$(orgTreeGridObj.treeGridId).clearGridData();
				orgTreeGridObj.first=false;
				orgTreeGridObj.init(ids);
				gainOfLossObj.init(ids);
            }
        });
    }

    initOrganTree();

    var gridOption = {
        data: [],
        datatype: "local",
        altRows: true,  //设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: 268,//268
        colNames: [' ', '名称', '岗位', '职位主序列', '职位子序列', '职位层级', '职级', '所待时长（年）', '操作'],
        colModel: [
            {name: 'imgPath', width: 35, sortable: false, align: 'center', formatter: formatterToImg},
            {name: 'userNameCh', width: 60, sortable: false, align: 'center'},
            {name: 'positionName', width: 70, sortable: false, align: 'center'},
            {name: 'sequenceName', width: 70, fixed: true, sortable: false, align: 'center'},
            {name: 'sequenceSubName', width: 70, fixed: true, sortable: false, align: 'center'},
            {name: 'abilityName', width: 70, fixed: true, sortable: false, align: 'center'},
            {name: 'rankName', width: 60, fixed: true, sortable: false, align: 'center'},
            {name: 'entryDate', width: 60, fixed: true, sortable: false, align: 'center'},
            {name: 'mycz', width: 120, fixed: true, sortable: false, align: 'center'}
        ],
        scroll: true
    };

    /**
     * 团队提醒
     */
    var remindObj = {
        modelId: '#remindModal',
        isHide: true,
        gridId: '#promotionGrid',
        init: function (organId) {
            var self = this;
            self.initBirthday();
            $('#remindBody').click(function () {
                $(self.modelId).modal('show');
            });
            $(self.modelId).on('shown.bs.modal', function () {
                resizeGrid(self.gridId);
            });
            self.requestData(organId);
        },
        initBirthday: function () {        //初始化生日榜
            var self = this;
            var month = new Date().getMonth();
            var first = $('#dirthday_dis').find('table tr:first');
            var first_child = first.children();
            var data_child = first.next().children();

            $(first_child[month]).removeClass('bg_default').addClass('bg_highlight');
            $(data_child[month]).removeClass('bg_default_name').addClass('bg_highlight_name');

            self.birthDis = data_child;

            var entryAnnual = $('#entryAnnual').val();
            self.annualArr = entryAnnual.split(',');
        },
        requestData: function (organId) {        //请求获取数据
            var self = this;
            $.post(urls.getRemindEmpUrl, {'organId': organId}, function (rs) {
                if (_.isEmpty(rs)) {
                    return;
                }
                self.extendsBirthData(rs);
                self.extendsPromotionData(rs);
                if (gainOfLossObj) {
                    gainOfLossObj.extendsTeamEmpData(rs);
                }
            });
        },
        extendsPromotionData: function (source) {           //封装晋升晋级数据
            var self = this;
            var annualArr = self.annualArr;
            //TODO 可晋级员工（拟定）
            var gridData = _.filter(source, function (obj) {
                var year = moment(obj.entryDate, "YYYYMMDD").fromNow();
                year = year.substr(0, year.indexOf('year') - 1);
                year = year == 'a' ? 1 : parseInt(year);
                return _.contains(annualArr, year + '');
            });
            var len = gridData.length;
            if (len > 0) {
                $('#promotionNum').text(len);
                var promotionHtml = '';
                var gridId = self.gridId;
                $.each(gridData, function (i, obj) {
                    if (i < 3) {
                        promotionHtml += (promotionHtml == '' ? '' : '、') + obj.userNameCh;
                    }
                });
                promotionHtml += (len > 3 ? '等' : '') + '可能存在晋升晋级资格';
                $('#promotionTxt').removeClass('hide').html(promotionHtml);
                var option = _.clone(gridOption);
                //所待时长字段数据格式化
                option.colModel[7].formatter = function (cellvalue, options, rowObject) {
                    var year = moment(cellvalue, "YYYYMMDD").fromNow();
                    year = year.substr(0, year.indexOf('year') - 1);
                    year = year == 'a' ? 1 : parseInt(year);
                    return year;
                };
                //加载完成之后执行的方法
                option.loadComplete = function (xhr) {
                    extendsLoadComplete(gridId, xhr);
                };

                initGrid(gridId, option, gridData);
            } else {
                $('#promotionNum').text(0);
                $('#promotionTxt').addClass('hide');
            }
        },
        extendsBirthData: function (source) {        //封装生日榜数据（包括生日提醒、入司周年提醒）
		
            var self = this;
            self.birthDis.html('');     //清空之前的数据
            self.birth = [], self.surplusBirth = {};
            var hasCurrDay = false, currMonthStr = '', currDayStr='',currMonthNum = 0;
            var entryYearArr = [];
			
            $.each(source, function (i, obj) {
			
                var month = new Date(obj.birthDate).getMonth() + 1;
                var birthdate = obj.birthDate;
                var dir = self.birth[month];
                if (!dir) {
                    self.birth[month] = [];
                }
                var birthArr = self.birth[month];
                var userName = obj.userNameCh;
                //生日榜数据
                if (birthArr.length == 4) {
                    if (!self.surplusBirth[month])
                        self.surplusBirth[month] = [];
                    self.surplusBirth[month].push(userName + "," + birthdate);
                    if (month) self.isHide = false;
                } else {
                    //人名超长处理
                    var monthName = userName;
                    if (monthName.length > 6) {
                        monthName = monthName.substr(0, 6) + "...";
                    }
                    var innerHtml = '<span title="' + userName + " " + moment(birthdate).format('YYYY-MM-DD') + '">' + monthName + '</span>';
                    var html = birthArr.length == 0 ? innerHtml : '<br>' + innerHtml;
                    $(self.birthDis[month - 1]).append(html);
                    self.birth[month].push(userName);
                }

                //TODO 当天生日
                var currDay = moment().format('MMDD');
                var currMonth = moment().format('MM');
                var currYear = moment().format('YYYY');
                if (currDay == moment(birthdate).format('MMDD')) {
                    hasCurrDay = true;
                    currDayStr += (currDayStr == '' ? '' : '、') +
                    '<span title=" ' + moment(birthdate).format('MM月DD日') + ' ">' + userName + '</span>';
                    currMonthStr += (currMonthStr == '' ? '' : '、') +
                    '<span title=" ' + moment(birthdate).format('MM月DD日') + ' ">' + userName + '</span>';
                    currMonthNum++;
                } else if (currMonth == moment(birthdate).format('MM')) {
                    currMonthStr += (currMonthStr == '' ? '' : '、') +
                    '<span title=" ' + moment(birthdate).format('MM月DD日') + ' ">' + userName + '</span>';
                    currMonthNum++;
                }

                //入司周年
                var entryDate = obj.entryDate;
                if (currMonth == moment(entryDate).format('MM')) {
                    var year = currYear - moment(entryDate, "YYYYMMDD").format('YYYY');
                    entryYearArr.push({'year': year, 'entryDate': entryDate, 'userName': userName});
                }
            });

            if (!self.isHide) {
                $('#more_dis').removeClass('hide');
                $('#reduce_dis').addClass('hide');

                $('#smoreBtn').click(function () {
                    self.moreBtnEvent();
                });
            }else{
                $('#more_dis').addClass('hide');
                $('#reduce_dis').addClass('hide');
            }
            //没有当天生日并且该元素没有隐藏 则添加隐藏样式
            if (!hasCurrDay) {
                $('#currBirth').addClass('hide');
            }else{
            	var currDayHtml =  currDayStr+  '&nbsp;&nbsp;<span class="red">今天</span>';
            	$('#currBirth').removeClass('hide').html(currDayHtml);
            }

            //当月生日员工
            if (currMonthNum > 0) {//渲染生日提醒的数据
                var currMonthHtml = '当月生日：' + currMonthStr;
                $('#currMonthTxt').removeClass('hide').html(currMonthHtml);
                $('#currMonthNum').text(currMonthNum);
            } else {
            	$('#currMonthNum').text(currMonthNum);
                $('#currMonthTxt').addClass('hide');
            }

            //入司周年
            if (entryYearArr.length > 0) {
                var entryQualifiedHtml = '';
                var annualArr = self.annualArr;
                var countNum = 0;
                for (var i = annualArr.length - 1; i >= 0; i--) {
                    var annualNum = annualArr[i];
                    var flag=false;
                    var QualifiedHtml='';
                    var boolNum = 0;
                    $.each(entryYearArr, function (e, obj) {
                        if (annualNum == obj.year) {
                        	QualifiedHtml += (boolNum > 0 ? '、' : '') + '<span title=" ' + moment(obj.entryDate).format('MM月DD日') + ' ">'
                            + obj.userName + '</span>';
                            boolNum++;
                            countNum++;
                            flag=true;
                        }
                    });
                    if(flag){
                    	entryQualifiedHtml += '<div>满<span class="red">' + annualNum + '</span>周年：&nbsp;';
                    	entryQualifiedHtml += QualifiedHtml
                    }
                    entryQualifiedHtml += '</div>';
                }
                //入司周年提醒
                $('#entryYearNum').text(countNum);
                $('#entryYearTxt').removeClass('hide').html(entryQualifiedHtml);
            } else {
                $('#entryYearNum').text(0);
                $('#entryYearTxt').addClass('hide');
            }
        },
        moreBtnEvent: function () {
            var self = this;
            if (!self.isHide) {
                $.each(self.surplusBirth, function (i, obj) {
                    if (!obj) return;
                    $.each(obj, function (j, str) {
                        var strList = str.split(',');
                        var name = strList[0];
                        //人名字符超长截取
                        var monthName = name;
                        if (monthName.length > 6) {
                            monthName = monthName.substr(0, 6) + "...";
                        }
                        var birthdate = strList[1];
                        $(self.birthDis[i - 1]).append('<br>' + '<span title="' + name + " " + moment(birthdate).format('YYYY-MM-DD') + '" class="birth">' + monthName + '</span>');
                    });
                });
                self.isHide = true;
            } else {
                $('.birth').removeClass('hide');
                $('.birth').prev().removeClass('hide');
            }
            $('#more_dis').addClass('hide');

            $('#reduce_dis').removeClass('hide');

            $('#sreduceBtn').click(function () {
                self.reduceBtnEvent();
            });
        },
        reduceBtnEvent: function () {
            var self = this;
            $('.birth').addClass('hide');
            $('.birth').prev().addClass('hide');

            $('#reduce_dis').addClass('hide');
            $('#more_dis').removeClass('hide');

            $('#smoreBtn').click(function () {
                self.moreBtnEvent();
            });
        }
    };
    remindObj.init(reqOrgId);

    //当季人才损益
    var gainOfLossObj = {
        modelId: '#gainOfLossModal',
        teamEmpGridId: '#teamEmpGrid',
        entryGridId: '#entryEmpsGrid',
        callinGridId: '#callinEmpsGrid',
        dimissionGridId: '#dimissionEmpsGrid',
        calloutGridId: '#calloutEmpsGrid',
        init: function (organId) {
            var self = this;

            self.requestData(organId);

            $('#gainOfLossBody').click(function () {
                $(self.modelId).modal('show');
            });

            $(self.modelId).on('shown.bs.modal', function () {
                resizeGrid(self.entryGridId);
                resizeGrid(self.callinGridId);
                resizeGrid(self.dimissionGridId);
                resizeGrid(self.calloutGridId);
            });

            $('#gainOfLossTabs a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
                var targetAreaId = $(e.target).attr('aria-controls');
                //切换标签页时，要调用echarts的resize()方法对图表进行重绘（当tab页的内容处于隐藏状态时，改变窗口大小后重新显示时，图表可能会显示不正确，故需重绘）
                if (targetAreaId == 'changeEmpTab') {
                    resizeGrid(self.entryGridId);
                    resizeGrid(self.callinGridId);
                    resizeGrid(self.dimissionGridId);
                    resizeGrid(self.calloutGridId);
                } else {
                    resizeGrid(self.teamEmpGridId);
                }
            });
        },
        requestData: function (organId) {
            var self = this;
            $.post(urls.getGainOfLossUrl, {'organId': organId}, function (rs) {
                if (_.isNull(rs)) {
                    return;
                }
                self.extendsGainOfLossData(rs);
            });
        },
        extendsGainOfLossData: function (source) {        //编制信息和招聘信息
            var self = this;
            var compileNum = source.compileNum;
            $('#compileNum').text(_.isNull(compileNum) ? '--' : compileNum);
            $('#usableCompileNum').text(_.isNull(compileNum) && _.isNull(source.workingNum) ? '--' : compileNum - source.workingNum);
            $('#publiceJobNum').text(_.isNull(source.publiceJobNum) ? '--' : source.publiceJobNum);
            $('#resumeNum').text(_.isNull(source.resumeNum) ? '--' : source.resumeNum);
            $('#acceptNum').text(_.isNull(source.acceptNum) ? '--' : source.acceptNum);
            $('#offerNum').text(_.isNull(source.offerNum) ? '--' : source.offerNum);
            self.extendsLossesEmpsData(source.empDtos);
        },
        extendsLossesEmpsData: function (source) {        //人才损益信息
            var self = this;
            if (_.isEmpty(source)) {
				$('#entryEmpsNum').text('--');
				$('#callinEmpsNum').text('--');
				$('#dimissionEmpsNum').text('--');
				$('#calloutEmpsNum').text('--');
				$('#gainOfLossNum').text(0);
				
				$('#entryEmpsNum2').text(0);
				$('#callinEmpsNum2').text(0);
				$('#dimissionEmpsNum2').text(0);
				$('#calloutEmpsNum2').text(0);
				 
                return;
            }

            var entryData = [], callinData = [], dimissionData = [], calloutData = [];
            //TODO 根据枚举或数据库调整   排序是：入职、调入、离职、调出
            $.each(source, function (i, obj) {
                switch (obj.changeType) {
                    case 3:
                        entryData.push(obj);
                        break;
                    case 2:
                        callinData.push(obj);
                        break;
                    case 5:
                        dimissionData.push(obj);
                        break;
                    case 4:
                        calloutData.push(obj);
                        break;
                }
            });
            var entryLen = entryData.length, callinLen = callinData.length,
                dimissionLen = dimissionData.length, calloutLen = calloutData.length;

            var entryGridId = self.entryGridId, callinGridId = self.callinGridId,
                dimissionGridId = self.dimissionGridId, calloutGridId = self.calloutGridId;

            //入职
            var entryOption = _.clone(gridOption);
            entryOption.colNames = [' ', '名称', '岗位', '职位主序列', '职位子序列', '职位层级', '职衔', '职级', '操作'];
            entryOption.colModel = [
                {name: 'imgPath', width: 35, sortable: false, align: 'center', formatter: formatterToImg},
                {name: 'userNameCh', width: 100, sortable: false, align: 'center'},
                {name: 'positionName', width: 100, sortable: false, align: 'center'},
                {name: 'sequenceName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'sequenceSubName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'abilityName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'jobTitleName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'rankName', width: 60, fixed: true, sortable: false, align: 'center'},
                {name: 'mycz', width: 100, fixed: true, sortable: false, align: 'center'}
            ];
            //加载完成之后执行的方法
            entryOption.loadComplete = function (xhr) {
                extendsLoadComplete(entryGridId, xhr);
            };
            initGrid(entryGridId, entryOption, entryData);

            //调入
            var callinOption = _.clone(entryOption);
            //加载完成之后执行的方法
            callinOption.loadComplete = function (xhr) {
                extendsLoadComplete(callinGridId, xhr);
            };
            initGrid(callinGridId, callinOption, callinData);

            //离职
            var dimisstionOption = _.clone(gridOption);
            dimisstionOption.colNames = [' ', '名称', '岗位', '职位主序列', '职位子序列', '职位层级', '职级', '绩效', '离职日期', '操作'];
            dimisstionOption.colModel = [
                {name: 'imgPath', width: 35, sortable: false, align: 'center', formatter: formatterToImg},
                {name: 'userNameCh', width: 100, sortable: false, align: 'center'},
                {name: 'positionName', width: 100, sortable: false, align: 'center'},
                {name: 'sequenceName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'sequenceSubName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'abilityName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'rankName', width: 60, fixed: true, sortable: false, align: 'center'},
                {name: 'performanceName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'changeDate', width: 80, fixed: true, sortable: false, align: 'center', formatter: formatterToDate},
                {name: 'mycz', width: 100, fixed: true, sortable: false, align: 'center'}
            ];
            //加载完成之后执行的方法
            dimisstionOption.loadComplete = function (xhr) {
                extendsLoadComplete(dimissionGridId, xhr);
            };
            initGrid(dimissionGridId, dimisstionOption, dimissionData);

            //调出
            var calloutOption = _.clone(dimisstionOption);
            calloutOption.colNames = [' ', '名称', '岗位', '职位主序列', '职位子序列', '职位层级', '职级', '绩效', '调出日期', '操作'];
            //加载完成之后执行的方法
            calloutOption.loadComplete = function (xhr) {
                extendsLoadComplete(calloutGridId, xhr);
            };
            initGrid(calloutGridId, calloutOption, calloutData);


            $('#entryEmpsNum').text(entryLen);
            $('#callinEmpsNum').text(callinLen);
            $('#dimissionEmpsNum').text(dimissionLen);
            $('#calloutEmpsNum').text(calloutLen);
            $('#entryEmpsNum2').text(entryLen);
            $('#callinEmpsNum2').text(callinLen);
            $('#dimissionEmpsNum2').text(dimissionLen);
            $('#calloutEmpsNum2').text(calloutLen);
            var gainOfLossNum = entryLen + callinLen - dimissionLen - calloutLen;
            $('#gainOfLossNum').text(gainOfLossNum < 0 ? gainOfLossNum : ('+' + gainOfLossNum));
        },
        extendsTeamEmpData: function (source) {         //团队员工信息
            var self = this;
            var gridId = self.teamEmpGridId;
            var option = _.clone(gridOption);
            option.colNames = [' ', '名称', '岗位', '职位主序列', '职位子序列', '职位层级', '职衔', '职级', '操作'];
            option.colModel = [
                {name: 'imgPath', width: 35, sortable: false, align: 'center', formatter: formatterToImg},
                {name: 'userNameCh', width: 100, sortable: false, align: 'center'},
                {name: 'positionName', width: 100, sortable: false, align: 'center'},
                {name: 'sequenceName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'sequenceSubName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'abilityName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'jobTitleName', width: 80, fixed: true, sortable: false, align: 'center'},
                {name: 'rankName', width: 60, fixed: true, sortable: false, align: 'center'},
                {name: 'mycz', width: 100, fixed: true, sortable: false, align: 'center'}
            ];
            //加载完成之后执行的方法
            option.loadComplete = function (xhr) {
                extendsLoadComplete(gridId, xhr);
            };
            initGrid(gridId, option, source);
        }
    };
    gainOfLossObj.init(reqOrgId);  
    
    //TODO ZOUGUANJIAN 
    var orgTreeGridObj={
		modelId: '#orgTreeGridModal',
        isHide: true,
        treeGridId: '#treegrid',
        modalTreeGridId: '#modalTreeGrid',
        first:true,
        firstModal:true,
        options:{  
   	            treeGrid: true,  
   	            treeGridModel: 'adjacency', //treeGrid模式，跟json元數據有關 ,adjacency/nested  
   	            url: urls.getChildDataUrl,  
   	            datatype: 'json',  
   	            //mtype: "POST",  
   	            height: "260px",  
   	            autowidth:true,
   	            ExpandColClick: true, //当为true时，点击展开行的文本时，treeGrid就能展开或者收缩，不仅仅是点击图片
   	            ExpandColumn : 'name',//树状结构的字段
   	            colNames: ['organizationId','架构名称','编制数','可用编制数','在岗人数','负责人'],
   	            colModel: [
   	                {name: 'id',key:true,hidden:true},
   	                {name: 'name', width: 240, sortable: false, align: 'left'},
   	                {name: 'number', width: 120,hidden:true, sortable: false, align: 'center'},
   	                {name: 'usableEmpCount', width: 110, sortable: false, align: 'center'},
   	                {name: 'empCount',width: 120, sortable: false, align: 'center'},
   	                {name: 'userName', width: 180, fixed: true, sortable: false, align: 'center'}
   	            ],
   	            jsonReader: {    
   	                root: "rows",  
   	                total: "total",    
   	                repeatitems: true    
   	            },  
   	            beforeRequest : function() {
   	            	if(orgTreeGridObj.first){
   	            		var data= this.p.data;
   	   	        	  	var postData= this.p.postData;
   	   	        	  	//var nodeId=postData.nodeid;
   	   	        	  	if(postData.nodeid==null){
   	   	        	  		postData.nodeid=reqOrgId;
   	   	        	  	}
   	   	        	  	if(postData.n_level != null) {
   	   						level = parseInt(postData.n_level,10);
   	   						postData.n_level=level+1;
   	   					}
   	            	}
   	        	  	
   			  },
   			  treeIcons : {
   				  //JQUERY UI
//   					"plus": "ui-icon-circlesmall-plus",
//   					"minus": "ui-icon-circlesmall-minus",
//   					"leaf" : "ui-icon-document"
   				  // BOOTSTARP UI
				"plus": "glyphicon glyphicon-plus",
				"minus": "glyphicon glyphicon-minus"
				//,"leaf" : "glyphicon glyphicon-star"
   			   },
   	        pager: "false"   
   	        },
        init: function (organId) {
            var self = this;
            self.organId=organId;
            self.initTreeGrid(organId,self.treeGridId,self.options);
            $('#subOrgInfoTitle').unbind("click").click(function () {
            	self.first=false;
                $(self.modelId).modal('show');
                self.options.height='400px';
                self.options.colModel[2].hidden=false;
                self.initTreeGrid(organId,self.modalTreeGridId,self.options);
            });
        },initTreeGrid:function(organId,gridId,options){
	   		//
        	var self=this;
        	if(self.first){
        		jQuery(gridId).jqGrid(options); 
        	}else{
        		if(gridId==self.modalTreeGridId){
        		//	jQuery(gridId).jqGrid(options); 
        			if(self.firstModal){
        				self.firstModal=false;
        				options.postData = {nodeid:organId};
        				jQuery(gridId).jqGrid(options);	
        			}else{
        				jQuery(gridId).clearGridData().setGridParam({
                            postData: {nodeid:reqOrgId}
                        }).trigger("reloadGrid");
        			}
        		}else{
        			jQuery(gridId).clearGridData().setGridParam({
                        postData: {nodeid:organId}
                    }).trigger("reloadGrid");
        		}
        		
        	}
        	self.first=true;
	   	}
    };    
    orgTreeGridObj.init(reqOrgId);
       
    
	
	 /**
     * 团队画像 
     */
    // by jxzhang
	var teamImgAbOpt = {
		color : ['#019BD9','#59C6B3','#FEEC61','#F9921B','#F93F19'],
		series : [
			{
				name:'',
				type:'funnel',
				
				y : '10%',
				sort : 'ascending',
				itemStyle: {
					normal: {
						label: {
							position: 'right' //inside
						}
					}
				},
				data : []
			}
		]
	};
    var teamImgAbObj = {
		funnelObj : null,
		chartId : 'teamImg-funel',
		resultData : null,
    	init : function(organId){
    		var self = this;
			self.funnelObj = echarts.init(document.getElementById(this.chartId));
			self.initData(organId);
			self.style();
    	},
		initData : function(organId){
			
			var self = this;
			if (_.isNull(self.resultData)) {
			  self.getRequestData(organId);
			  return;
			}
			
			var manPerData = self.resultData[0];
			var yearsData = self.resultData[1];
			var marryData = self.resultData[2];
			var abilityData = self.resultData[3];
			
			teamImgAbOpt.series[0].data = [];
			if(!_.isEmpty(abilityData)){
				$.each(abilityData, function(a,b){
					teamImgAbOpt.series[0].data.push({value:b.k, name:b.v});
					
				});
			}
			self.funnelObj.setOption(teamImgAbOpt, true);
			//if(!_.isNull(self.resultData[4])){
				$("#totalPer").text(self.resultData[4]);
			//}else{
			//	$("#totalPer").text(0);
			//}
			
            $('#manPer').removeClass("minInfo").addClass("minInfo");
			$('#manPer').text(manPerData.k + '占' + manPerData.v + '%');
			
			
			if(yearsData.v == null){
				$('#yearsMaster').hide();
			}else{
				$('#yearsMaster').removeClass("minInfo").addClass("minInfo");
				$('#yearsMaster').show();
				$('#yearsMaster').text(yearsData.k + '为主');
			}
			$('#unIsMarry').removeClass("minInfo").addClass("minInfo");
			$('#unIsMarry').text(marryData.k + marryData.v + '%');
			
		},
		getRequestData : function(organId){
			
			var self = this;
            $.post(urls.getTeamImgAbUrl, {'organId': organId}, function (rs) {
				self.resultData = rs;
				
				self.initData(organId);
			
            });
			
		},
		style : function(){
			var headWidth = $(".teamImg-head").width();
			var btnWidth = (headWidth / 3) - 35;
			var _marginLeft = 20; 
			$(".minInfo").css({width : btnWidth+"px", marginLeft : _marginLeft + "px"});
			
			//var width = $("#teamImg-funel").width();
			//var _left = (width / 2) ;
			//$(".teamImg-title").css({position:"absolute", top:"25px", left:_left+"px"});
		}
    }
    teamImgAbObj.init(reqOrgId);
    

	/**
     * 人才发展 
     */
	// by jxzhang
	var promoteGridOption = {
        data: [],
        datatype: "local",
        altRows: true,  //设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: 268,//268
        colNames: [' ', '姓名', '岗位', '职位主序列', '职位子序列', '当前职级', '原职级', '原职级所待时长（月）', '操作'],
        colModel: [

            {name: 'imgPath', width: 35, sortable: false, align: 'center', formatter: formatterToImg},
            {name: 'userNameCh', width: 30, sortable: false, align: 'center'},
            {name: 'positionName', width: 80, sortable: false, align: 'center'},
            {name: 'sequenceName', width: 80, fixed: true, sortable: false, align: 'center'},
            {name: 'sequenceSubName', width: 80, fixed: true, sortable: false, align: 'center'},
            {name: 'rankName', width: 60, fixed: true, sortable: false, align: 'center'},
            {name: 'rankNameEd', width: 60, fixed: true, sortable: false, align: 'center'},
            {name: 'stayTimeEd', width: 140, fixed: true, sortable: false, align: 'center'},
            {name: 'empId', width: 120, fixed: true, sortable: false, align: 'center', formatter: formatterToOpt}

        ],
        scroll: true,
			jsonReader: {    
				root: "rows",  
				total: "total",    
				repeatitems: true    
			}
    };
	var trainGridOption = {
        data: [],
        datatype: "local",
        altRows: true,  //设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: 268,//268
        colNames: [' ', '姓名', '岗位', '职位主序列', '职位子序列','总培训次数','部培训学时', '操作'],
        colModel: [
            {name: 'imgPath', width: 35, sortable: false, align: 'center', formatter: formatterToImg},
            {name: 'userNameCh', width: 30, sortable: false, align: 'center'},
			{name: 'positionName', width: 80, sortable: false, align: 'center'},
            {name: 'sequenceName', width: 80, fixed: true, sortable: false, align: 'center'},
            {name: 'sequenceSubName', width: 80, fixed: true, sortable: false, align: 'center'},
            {name: 'trainNum', width: 120, fixed: true, sortable: false, align: 'center'},
            {name: 'trainTime', width: 120, fixed: true, sortable: false, align: 'center'},
            {name: 'empId', width: 120, fixed: true, sortable: false, align: 'center', formatter: formatterToOpt}
           
        ]
    };
	var examGridOption = {
        data: [],
        datatype: "local",
        altRows: true,  //设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: 268,//268
        colNames: [' ', '姓名', '岗位', '职位主序列', '职位子序列','能力匹配度','测评日期', '操作'],
        colModel: [
            {name: 'imgPath', width: 35, sortable: false, align: 'center', formatter: formatterToImg},
            {name: 'userNameCh', width: 30, sortable: false, align: 'center'},
			{name: 'positionName', width: 80, sortable: false, align: 'center'},
            {name: 'sequenceName', width: 80, fixed: true, sortable: false, align: 'center'},
            {name: 'sequenceSubName', width: 80, fixed: true, sortable: false, align: 'center'},
            {name: 'abilityMatch', width: 70, fixed: true, sortable: false, align: 'center', formatter : function(cellvalue){ return (cellvalue*100).toFixed(2) + '%'}},
            {name: 'examDate', width: 140, fixed: true, sortable: false, align: 'center', formatter : function(cellvalue){ return cellvalue.substr(0, 10 )}},
            {name: 'empId', width: 120, fixed: true, sortable: false, align: 'center', formatter: formatterToAbilityInfo}
        ]
    };
	var talentDevelPieOpt = {
		calculable : false,
		color: ['#5B9BD5', '#E6E6E6'],
		title : {
			text: '',
			x:'center',
			y:'bottom',
			textStyle : {
				fontSize: 12,
				fontWeight:'normal',
				// fontWeight: 'bolder',
				color: '#333'
			}  
		},
		series : [
		  {
			type:'pie',
			radius : ['50%', '70%'],
			center: ['50%', '50%'],
			itemStyle : {
				normal : {
					label : { show : false },
					labelLine : { show : false }
				}
			},
			data : []
		  }
		]
	};
	var talentDevelPieOptImg = {
		calculable : false,
		color: ['#5B9BD5'],
		title : {
			text: '',
			x:'center',
			y:'bottom',
			textStyle : {
				fontSize: 12,
				fontWeight:'normal',
				// fontWeight: 'bolder',
				color: '#333'
			} 
		},
		series : [
		  {
			  type:'pie',
			  radius : '70%',
			  // center: ['50%', '38%'],
			  itemStyle : {
				normal : {
					label : { show : false },
					labelLine : { show : false }
				}
			},
			  data: [{value:1}]
		  }
		]
	};
	var talentDevelBarOpt = {
		grid : {
			borderWidth:0,
			y2: 20
		} ,
		tooltip : {
			formatter : ''
		},
		xAxis : [
			{
				type : 'value',
				boundaryGap : [0,  5],
				splitLine : false,
				max:5,
				splitNumber : 5
			}
		],
		yAxis : [
			{
				type : 'category',
				data : [],
				splitLine : false
			}
		],
		series : [
			{
				type:'bar',
				itemStyle: {
					normal: {
						label : {
							show: true, position: 'insideRight'
						}
					}
				},
				data:[]
			}
		]
	};
	var abilityInfoBarOpt = {
		grid : {
			borderWidth:0,
			y:0
		} ,
		legend: {
			data:['能力实际值', '能力标准值'],
			y:'bottom'
		},
		xAxis : [
			{
				type : 'value',
				boundaryGap : [0,  5],
				splitLine : false,
				max:5,
				splitNumber : 5
			}
		],
		yAxis : [
			{
				type : 'category',
				data : [],
				splitLine : false
			}
		],
		series : [
			{
				name:'能力实际值',
				type:'bar',
				itemStyle: {
					normal: {
						color : '#c2c2c2',
						label : {
							show: true, position: 'insideRight'
						}
						
					}
				},
				data:[]
			},
			{
				name:'能力标准值',
				type:'bar',
				itemStyle: {
					normal: {
						color : '#ABCDEF',
						label : {
							show: true, position: 'insideRight'
						}
					}
				},
				data:[]
			}
		]
	};
    $.fn.extend({
        shownBsModal:function(tabid){
            $(this).unbind("shown.bs.modal").on('shown.bs.modal', function () {
                $("#"+tabid).parent().find("li").removeClass("active");
                $("#"+tabid).addClass("active");
                $("#talentDevelModal .tab-pane").removeClass("active");
                $("#"+tabid.replace("Name","")).addClass("active");
                var grid="#" + tabid.replace("TabName","")+"Grid";
                resizeGrid(grid);
                //切换架构后再打开，确保jqgrid滚动条位置在顶部
                $(grid).parent().parent().scrollTop(0)
            })
        }
    });
	var talentDevelObj = {
		rowIds:[],
		resultData : null,
		talentDevelObj : null,
		barId : 'talentDevel-bar',
		pieObj1 : null,
		pieId1 : 'talentDevel-pie1',
		pieObj2 : null,
		pieId2 : 'talentDevel-pie2',
		pieObj3 : null,
		pieId3 : 'talentDevel-pie3',
		pieObj4 : null,
		pieId4 : 'talentDevel-pie4',
		resultData : null,
		modelId : '#talentDevelModal',
		gridId : ['#promoteGrid','#trainGrid','#360ExamGrid'],
		abilityInfoObj : null,
		barId2 : 'abilityInfo',
    	init : function(organId){
    		var self = this;

            $('#talentDevel-bar').click(function () {
                $(self.modelId).modal('show').shownBsModal("360ExamTabName");
            });
            $('#talentDevel-pie1,#talentDevel-pie2').parent().click(function () {
                $(self.modelId).modal('show').shownBsModal("promoteTabName");
            });
            $('#talentDevel-pie3,#talentDevel-pie4').parent().click(function () {
                $(self.modelId).modal('show').shownBsModal("trainTabName");
            });
			
			$(self.modelId).on('shown.bs.modal', function () {
                resizeGrid(self.gridId[0]);
                resizeGrid(self.gridId[1]);
                resizeGrid(self.gridId[2]);
            });
					
			$('#talentDevelTabs a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
				resizeGrid(self.gridId[0]);
                resizeGrid(self.gridId[1]);
                resizeGrid(self.gridId[2]);
			});
			
			self.talentDevelObj = echarts.init(document.getElementById(this.barId));
			self.pieObj1 = echarts.init(document.getElementById(this.pieId1));
			self.pieObj2 = echarts.init(document.getElementById(this.pieId2));
			self.pieObj3 = echarts.init(document.getElementById(this.pieId3));
			self.pieObj4 = echarts.init(document.getElementById(this.pieId4));
			
			self.abilityInfoObj = echarts.init(document.getElementById(this.barId2));
			
			self.initData(organId);
    	},
		initData : function(organId){
			var self = this;
			if (_.isNull(self.resultData)) {
			  self.getRequestData(organId);
			  return;
			}

			var total = self.resultData[0];
			var talentDevelBarData = self.resultData[1];
			var promoNum = self.resultData[2];
			var promoMonths = self.resultData[3];
			var trainPerpol = self.resultData[4];
			var trainPerpolAvg = self.resultData[5];
			var promotionData = self.resultData[6];
			var trainData = self.resultData[7];
			var examData = self.resultData[8];
			var year = self.resultData[9];
			if(_.isNull(total || talentDevelBarData||promoNum||promoMonths)){return ;}
				
			// eCharts and zRender
			// bar1 data 
			self.packZreander(total);
			self.packBar1(talentDevelBarData);
		
			// pie data
			var soure = {
				'total' : total, 'promoNum' : promoNum, 'promoMonths' : promoMonths,
				'trainPerpol' : trainPerpol, 'trainPerpolAvg' : trainPerpolAvg
				}
			self.packPie1(soure);
			
			// jqGrid
			var jgSoure = {
				'promotionData' : promotionData, 'trainData' : trainData, 'examData' : examData,"year":year
				}
			self.gridData(jgSoure);
			
		
			self.abInfoEchart();
			self.style();
		},
		packZreander : function(soure){
			var self = this;
			var _ZR = self.talentDevelObj.getZrender();
			self.headRender(_ZR, soure);
		},
		packBar1 : function(soure){
			if(null == soure||_.isEmpty(soure)||$.trim(soure)==""){return;}
			var self = this;
			var _data= [];
			var _yData= [];
			var _soure = soure.reverse(); // 因为echart的y轴是从下往上。所以把数据反转。
			$.each(_soure, function(i,n){
				var _obj = {
					name:  n.abilityName,
					value: n.gainScoreAvg,
					itemStyle:{normal:{color: n.moduleType == 1? '#5B9BD5': n.moduleType == 2 ? '#A9D18E' : '#FFC000'}},
					abTotal : n.abTotal
				};
				_yData.push(n.abilityName);
				_data.push(_obj);
			});
			talentDevelBarOpt.yAxis[0].data = _yData;
			talentDevelBarOpt.series[0].data = _data;
			// talentDevelBarOpt.yAxis[0].data = ['人际理解','沟通能力','动手能力','创新突破','商业敏感','战略前瞻'];
			// talentDevelBarOpt.series[0].data = 
				// [
					// {name:'人际理解',value:'1',itemStyle:{normal:{color:'#FFC000'}}}, 
					// {name:'沟通能力',value:'2.3',itemStyle:{normal:{color:'#A9D18E'}}},
					// {name:'动手能力',value:'5',itemStyle:{normal:{color:'#A9D18E'}}},
					// {name:'创新突破',value:'3.7',itemStyle:{normal:{color:'#5B9BD5'}}},
					// {name:'商业敏感',value:'1',itemStyle:{normal:{color:'#5B9BD5'}}},
					// {name:'战略前瞻',value:'2',itemStyle:{normal:{color:'#5B9BD5'}}}
				// ]
			// ;
			talentDevelBarOpt.tooltip.formatter = 
				function(a, b, c){
					var _data = a.data;
					var html = '<div>360能力维度：'+_data.name+'<br>';
						html+='参加人数：'+_data.abTotal+'<br>'
						html+='平均得分：'+_data.value+'<br>'
						html += '</div>';
					return html;
				};
			self.talentDevelObj.setOption(talentDevelBarOpt, true);
		},
		packPie1 : function(soure){
			var self = this;
			$('.pie-v1').text(soure.promoNum);
			talentDevelPieOpt.series[0].data = [{value:soure.promoNum, name:'当季没晋升人数'}, {value:soure.total - soure.promoNum, name:'当季晋升人数'}];
			talentDevelPieOpt.title.text = '当季晋升人数';
			self.pieObj1.setOption(talentDevelPieOpt, true);
			
			talentDevelPieOptImg.title.text = '人均晋升周期';
			$('.pie-v2').text(soure.promoMonths);
			self.pieObj2.setOption(talentDevelPieOptImg, true);
			
			$('.pie-v3').text(soure.trainPerpol);
			talentDevelPieOpt.series[0].data = [{value:soure.trainPerpol, name:'当季培训人数'}, {value:soure.total - soure.trainPerpol, name:'当季培训人数'}];
			talentDevelPieOpt.title.text = '当季培训人数';
			self.pieObj3.setOption(talentDevelPieOpt, true);
			
			talentDevelPieOptImg.title.text = '当季人均培训次数';
			$('.pie-v4').text(soure.trainPerpolAvg);
			self.pieObj4.setOption(talentDevelPieOptImg, true);
		
		},
		abInfoEchart: function(){
			var self = this;
			 //bootstrap tip	
			// ("[data-toggle='popover']").popover({html:true});
				// $("#abilityInfo").on('show.bs.popover', function () {
				 // var abilityInfoObj = echarts.init(document.getElementById('abilityInfo1'));
					// abilityInfoObj.setOption(abilityInfoBarOpt, true);
			// });
			
			var yearInt = $('#examDate').text();
			
			// $("#360ExamGrid").on('mouseenter', 'a', function(){
			$("#360ExamGrid").on('click', 'a', function(){
				var empId = $(this).attr('abInfoId');

				var tds = $(this).parent().prevUntil();
				var _trainDate = $(tds[0]).attr('title');
				var _userName = $(tds[5]).attr('title');
				$('#abInfoModalLable').text(_userName+' ,'+_trainDate);


				// abilityInfoBarOpt.yAxis[0].data = ['人际理解','沟通能力','动手能力','创新突破','商业敏感','战略前瞻'];
				// abilityInfoBarOpt.series[0].data = [ 2, 3, 2.2, 4, 2.5, 3.1];
				// abilityInfoBarOpt.series[1].data = [ 5, 4.7, 3.8, 4.2, 2.9, 4];
				$.post(urls.getTalentDevelExamItemUrl, {'yearInt':$(this).data("year"), 'empId': empId}, function (rs) {
					if(_.isNull(rs)){
						return;
					}
					var list = rs.reverse();
                    var listSort= _.sortBy(list, function(item, i){
                        return item.gainScore;
                    });
					var yAxisData = [], standardScoreData = [], gainScoreData = [];
					$.each(listSort , function(i, n){
						yAxisData.push(n.abilityName);
						standardScoreData.push(n.standardScore);
						gainScoreData.push(n.gainScore);
					});
					abilityInfoBarOpt.yAxis[0].data = yAxisData;
					abilityInfoBarOpt.series[0].data = standardScoreData;
					abilityInfoBarOpt.series[1].data = gainScoreData;
					
					self.abilityInfoObj.setOption(abilityInfoBarOpt, true);
					
				});
				$("#abInfoModal").modal('show');

			});

		},
		getRequestData : function(organId){
			var self = this;
			$.post(urls.getTalentDevelUrl, {'organId': organId}, function (rs) {
				self.resultData = rs;
				self.initData(organId);
            });
		},
		gridData : function(jgSoure){
			var self = this;

            $('#examDate').text(jgSoure.year?jgSoure.year:"-");
			var gridData1 = jgSoure.promotionData;
			var gridData2 = jgSoure.trainData;
			var gridData3 = jgSoure.examData;
			
			initGrid(self.gridId[0], promoteGridOption, gridData1);
			initGrid(self.gridId[1], trainGridOption, gridData2);
			initGrid(self.gridId[2], examGridOption, gridData3);
		},
		style : function(){
			var x = 15;
			y = ($(".talentDevel-bottom").height() / 2) - 15;
			var pieW1 = $("#talentDevel-pie1").width();	
			$(".txtPos").css({width: pieW1,  textAlign:'center',  position:'absolute', left : x+'px', top: y+'px' , zIndex: 1000 });
		},
		headRender : function(_ZR, soure){
			_ZR.clear();
			var _y = 30;
			var _x = 20;
			var v1X = _x + 70;
			var v2X = v1X + 120;
			var v3X = v2X + 15;
			var v4X = v3X + 50;
			var v5X = v4X + 15;
			var v6X = v5X + 50;
			var v7X = v6X + 15;
			_ZR.addShape(new TextShape({
		        style : {
		            x : _x,
		            y : _y,
		            color: '#666',
		            text : '能力测评',
		            textAlign : 'left',
		            textFont:'bolder 14px 微软雅黑',
		        },
			    hoverable : false
		    }));
			
			var textShape = new TextShape({
		        style : {
		            x : v1X,
		            y : _y,
		            color: '#666',
		            text : '共  '+soure+'  人参加',
		            textAlign : 'left',
		            textFont:'14px 微软雅黑',
		        },
			    hoverable : false
		    });
			_ZR.addShape(textShape);
			
			// 想事
			_ZR.addShape(new TextShape({
		        style : {
		            x : v2X,
		            y : _y,
		            color: '#5B9BD5',
		            text : '■',
		            textAlign : 'left',
		            textFont:'bolder 20px 微软雅黑',
		        },
			    hoverable : false
		    }));
			_ZR.addShape(new TextShape({
		        style : {
		            x : v3X,
		            y : _y,
		            color: '#666',
		            text : '想事',
		            textAlign : 'left',
		            textFont:'normal 14px 微软雅黑'
		        },
		        hoverable : false
		    }));
			
			// 做事
			_ZR.addShape(new TextShape({
		        style : {
		            x : v4X,
		            y : _y,
		            color: '#A9D18E',
		            text : '■',
		            textAlign : 'left',
		            textFont:'bolder 20px 微软雅黑',
		        },
			    hoverable : false
		    }));
			_ZR.addShape(new TextShape({
		        style : {
		            x : v5X,
		            y : _y,
		            color: '#666',
		            text : '做事',
		            textAlign : 'left',
		            textFont:'normal 14px 微软雅黑'
		        },
		        hoverable : false
		    }));
			
			// 带队
			_ZR.addShape(new TextShape({
		        style : {
		            x : v6X,
		            y : _y,
		            color: '#FFC000',
		            text : '■',
		            textAlign : 'left',
		            textFont:'bolder 20px 微软雅黑',
		        },
			    hoverable : false
		    }));
			_ZR.addShape(new TextShape({
		        style : {
		            x : v7X,
		            y : _y,
		            color: '#666',
		            text : '带队',
		            textAlign : 'left',
		            textFont:'normal 14px 微软雅黑'
		        },
		        hoverable : false
		    }));
			
			_ZR.refresh();
			
		}
	};
	talentDevelObj.init(reqOrgId);	
    
	
	
    
    //重新封装加载完表格之后的方法
    function extendsLoadComplete(gridId, xhr) {
        var rows = xhr.rows;
        var ids = $(gridId).jqGrid('getDataIDs');
        for (var i = 0; i < ids.length; i++) {
            var col = ids[i];
            var html = '<a href="javascript:void(0)" data-index="' + i + '" class="grow_col" >成长轨迹</a>'
                + ' <a href="javascript:void(0)" data-index="' + i + '" class="profile_col" >人才剖像</a>';
            $(gridId).jqGrid('setRowData', col, {mycz: html});
        }
        $('.grow_col').unbind().bind('click', function (e) {
            var _this = $(this);
            var idx = _this.attr('data-index');
            var userObj = rows[idx];
        });
    }

    //格式化表格时间
    function formatterToDate(cellvalue, options, rowObject) {
        return moment(cellvalue).format('YYYY-MM-DD');
    }

    //格式化表格图片
    function formatterToImg(cellvalue, options, rowObject) {
        var tempHtml = '';
        if (_.isEmpty(cellvalue)) {
            tempHtml += '<img src="' + (webRoot + '/assets/img/base/u-default.png') + '" width="32" height="32" >';
        } else {
            tempHtml += '<img src="' + cellvalue + '" width="32" height="32" >';
        }
        return tempHtml;
    }
	
	function formatterToOpt(cellvalue, options, rowObject){
		var tempHtml = '';
		tempHtml += '<a href='+webRoot+'/talentProfile/toTalentDetailView.do?empId='+cellvalue+'&rand='+Math.random()+'&anchor=growUpDiv target="_blank" ">成长轨迹</a> &nbsp'
		tempHtml += '<a href='+webRoot+'/talentProfile/toTalentDetailView.do?empId='+cellvalue+'&rand='+Math.random()+' target="_blank" ">人才剖像</a>'
		return tempHtml;
	}
	function formatterToAbilityInfo(cellvalue, options, rowObject) {
		// bootstrap tip
		// var contentHtml = '<div id="abilityInfo"'+cellvalue+' class="abilityInfo"></div>';
		// var tempHtml = "<a data-container='body' data-toggle='popover' data-placement='bottom' data-content='"+contentHtml+"'> 查看能力状况 </a>";
		// talentDevelObj.rowIds.push(cellvalue);
		// return tempHtml;
		return '<a abInfoId = '+cellvalue+'  data-year='+rowObject.year+'>查看能力状况</a>';
		
	}

	
	
	
	
	
	
	
	
	
	
	

	
	
	
    //初始化表格
    function initGrid(gridId, gridOption, data) {
        $(gridId).jqGrid(gridOption);
        //$(gridId).setGridHeight((data.length + 1) * 43);
        $(gridId).clearGridData().setGridParam({
            data: data,
            rowNum: 9999999
        }).trigger("reloadGrid");
        resizeGrid(gridId);
    }

    //重新加载表格
    function resizeGrid(gridId) {
        var parentDom = $('#gbox_' + gridId.split('#')[1]).parent();
        $(gridId).setGridWidth(parentDom.width());
    }

    //绩效目标
    var performanceObj={
        Init:function(orgId){
            $.post(urls.getPerformance, {'organId': orgId}, function (rs) {
                //部门绩效目标
                var dep=rs[0];
                var depHrml="";
                if(!_.isEmpty(dep)){
                	$.each(dep,function(i){
                        depHrml+="<tr>";
                        depHrml+="<td>"+dep[i].content+"</td>";
                        depHrml+="<td class=\"center\">"+dep[i].weight*100+"%</td>";
                        depHrml+="</tr>";
                    });
                }
                
                $("#performance_dep tbody").html(depHrml);

                //下属绩效目标
                var emp=rs[1];
                var empHtml="";
                var empObj=[];
                if(!_.isEmpty(emp)){
	                $.each(emp, function(i){
	                    var b=false;
                        $.each(empObj, function(j){
                            if(empObj[j].empId==emp[i].empId){
                                b=true;
                            }
                        });
                        if(!b){
                            empObj.push(emp[i]);
                        }
	                });
                }
                $.each(empObj, function(i){
                    var s="";
                    var w=0;
                    $.each(emp,function(j){
                        if(emp[j].empId==empObj[i].empId){
                            w+=emp[j].weight*100;
                            s+="<tr class=\"detail hide\">";
                            s+="<td class=\"nobb\"></td>";
                            s+="<td>"+emp[j].assessName+"</td>";
                            s+="<td class=\"center\">"+emp[j].weight*100+"%</td>";
                            s+="<td>"+emp[j].idp+"</td>";
                            s+="</tr>";
                        }
                    });
                    empHtml+="<tbody class=\"bb\">";
                    empHtml+="<tr class=\"head\">";
                    empHtml+="<td class=\"nobb\">"+empObj[i].name+"</td>";
                    empHtml+="<td>"+empObj[i].assessParentName+"</td>";
                    empHtml+="<td class=\"center\">"+w+"%</td>";
                    empHtml+="<td><div style='padding: 5px 0px;'>"+empObj[i].idptotal+"</div></td>";
                    empHtml+="</tr>";
                    empHtml+=s;
                    empHtml+="</tbody>";
                });
                $("#performance_sub tbody").remove();
                $("#performance_sub").append(empHtml);

                //js渲染
                performanceObj.Performance();
            });
        },
        Performance : function(){
            $("#performance_"+$(".performance .tab .on").data("index")).show();
            $(".performance .tab .title").mouseover(function(){
                $("#performance_"+$(".performance .tab .on").data("index")).hide();
                $("#performance_"+$(this).data("index")).show();
                $(".performance .tab .on").removeClass("on");
                $(this).addClass("on");
            });

            $("#performance_sub .head").click(function(){
                $(this).parent().find(".detail").toggleClass("hide");
                var imgname=$(this).parent().find(".detail").hasClass("hide")?"right":"down";
                $(this).find("img").attr("src",webRoot+"/assets/img/manage/performance_"+imgname+".png");
            });

            $("#performance_sub .head .nobb").each(function(){
                $(this).append("<img src=\""+webRoot+"/assets/img/manage/performance_right.png\">");
            });
        }
    };
    performanceObj.Init(reqOrgId);
    
    function getEmpDetailTpl(data) {
        //TODO 查找头像
        var src = webRoot + "/assets/photo.jpg";
        return '<div class="pull-left text-center">' +
            '<div class="position-relative">' +
            '<img class="head-pic img-circle" src="' + src + '" data-src="' + src + '" data-id="' + data.empId + '" data-type="' + data.keyTalentTypeName + '">' +
            '<span class="mark-talent-type img-circle">' + data.keyTalentTypeName + '</span>' +
            '</div>' +
            '<div class="emp-name">' + data.userNameCh + '</div>' +
            '</div>';
    }
    function getEmpWorkOvertimeDetailTpl(data) {
        //TODO 查找头像
        var src = webRoot + "/assets/photo.jpg";
        return '<div class="pull-left  work-div">' +
            '<div class="position-relative">' +
            '<img class="head-pic img-circle" src="' + src + '" data-src="' + src + '" data-id="' + data.empId + '" data-type="' + data.keyTalentTypeName + '">' +
            '<span class="mark-talent-type-work img-circle">' + data.keyTalentTypeName + '</span>' +
            
            '<span class="emp-name-work">' + data.userNameCh + '</span>'+
            '<span class="workovertime_time">近'+data.week+'周平均加班' + data.avHour + 'h</span>' +

   
            '</div>' +
           // '<div class="emp-name">' + data.userNameCh + '</div>' +
            '</div>';
    }

    /**
     * 绩效信息表格模板
     */
    var performanceWarnOption = {
            data: [],
            datatype: "local",
            altRows: true,	//设置表格行的不同底色
            autowidth: true,
            height: 150,
            colNames: ['', '姓名', '岗位', '职位主序列', '职位子序列','职衔','职级','绩效','操作'],
            colModel: [
                {
                    name: 'imgPath',
                    index: 'imgPath',
                    width: 70,
                    sortable: false,
                    align: 'center',
                    formatter:function(value){
                    	if(_.isEmpty(value)){
                    		value = webRoot + "/assets/photo.jpg";
                    	}
                    	return "<img src='"+value+"' class='head-pic img-circle'>";
                    }
                },
                {
                    name: 'userNameCh',
                    index: 'userNameCh',
                    width: 100,
                    sortable: false,
                    align: 'center'
                },
                {
                    name: 'positionName',
                    index: 'positionName',
                    width: 100,
                    sortable: false,
                    align: 'center'
                },
                {
                    name: 'sequenceName',
                    index: 'sequenceName',
                    width: 90,
                    sortable: false,
                    align: 'center'
                },
                {
                    name: 'sequenceSubName',
                    index: 'sequenceSubName',
                    width: 90,
                    sortable: false,
                    align: 'center'
                },{
                    name: 'jobTitleName',
                    index: 'jobTitleName',
                    width: 130,
                    sortable: false,
                    align: 'center'
                },{
                    name: 'abilityName',
                    index: 'abilityName',
                    width: 90,
                    sortable: false,
                    align: 'center'
                },{
                    name: 'performanceName',
                    index: 'performanceName',
                    width: 70,
                    sortable: false,
                    align: 'center'
                },
                {
                    name: 'empId',
                    index: 'empId',
                    width: 250,
                    sortable: false,
                    align: 'center',
                    formatter:function(value){
   
                    	return "<a href='javascript:void(0)' data='"+value+"'  class='growUp_col' style='margin:0 5px;'>成长轨迹</a>" +
                    			"<a href='javascript:void(0)' data='"+value+"' class='talent_col' >人才剖象</a>";
                    }
                
                }
            ],
           // rownumbers: true,
            //rownumWidth: 40,
           // scroll: true,
            loadComplete : function(xhr) {
				$('.growUp_col').unbind().bind('click',function(){
					var _this = $(this);
					var empId = _this.attr('data');
					var herf=urls.toEmpDetailUrl + '?empId=' + empId+ '&rand=' + Math.random()+"&anchor=growUpDiv";
					window.open(herf); 
				});

				$('.talent_col').unbind().bind('click',function(){
					var _this = $(this);
					var empId = _this.attr('data');
					var herf=urls.toEmpDetailUrl + '?empId=' + empId+ '&rand=' + Math.random();
					window.open(herf); 
					//window.location.href = webRoot + '/role/roleFunction?roleId='+empId;
					//document.getElementById('showPerformanceWarningFrm').src = herf;
					//$("#showPerformanceWarningModal").modal("show");
				});
			}
        };
    // 加班信息
	var overtimeOption = {
		calculable : false,
		grid : {
			borderWidth : 1,
			x:40,
			y:5
		},
		color : [ '#23C6C8' ],
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
				rotate:45, //刻度旋转45度角
				itemStyle : {
					color : '#BEBEBE'
				}
			},
			data : []
		} ],
		yAxis : [ {
            type: "value",
            show: true,
            splitLine: false,
            min:0,           //最小
            splitNumber:2,
            axisTick: {
                show: false
            },axisLabel:{
            	show:true,
            	formatter: '{value} h'
            },
            axisLine: {
                lineStyle: {
                    color: '#BCBCBC'
                }
            }
        } ],
		series : [  {
			 type: "line",
             smooth: false,
			clickable : false,
			itemStyle : {
				normal : {
					label : {
						show : true,
						textStyle : {
							color : 'black'
						}
					}
				}
			},
			data : []
		} ]
	};

    var warnPanel={
    		performanceWarnObj : {
    	            resultData: null,
    	            HPId: "#highPerformanceGrid",
    	            LPId: "#lowPerformanceGrid",
    	            init: function (l_data) {
    	                $(this.HPId).jqGrid(performanceWarnOption);
    	                $(this.LPId).jqGrid(performanceWarnOption);
    	            },
    	            initHPGrid:function(h_data){
    	            	$("#highNum").text(h_data.length);
    	            	 $(this.HPId).clearGridData().setGridParam({
     	                    data: h_data
     	                }).trigger("reloadGrid");
    	            	 this.resizeGrid();
    	            },
    	            initLPGrid: function (l_data) {
    	            	$("#lowNum").text(l_data.length);
    	                $(this.LPId).clearGridData().setGridParam({
    	                    data: l_data
    	                }).trigger("reloadGrid");
    	                this.resizeGrid();
    	            },
    	            resizeGrid: function () {
    	            	//alert($(this.HPId).width());
    	            	$(this.HPId).setGridWidth(880);
    	            	$(this.LPId).setGridWidth(880);
    	            	$("#highPerformanceArea").find(".ui-jqgrid-htable").width($("#highPerformanceArea").children(":first").width()*0.9);
    	            	$("#lowPerformanceArea").find(".ui-jqgrid-htable").width($("#lowPerformanceArea").children(":first").width()*0.9);
    	            	$("#highPerformanceArea").find(".ui-jqgrid-hdiv").width($("#highPerformanceArea").children(":first").width()*0.9);
    	            	$("#lowPerformanceArea").find(".ui-jqgrid-hdiv").width($("#lowPerformanceArea").children(":first").width()*0.9);
    	            	$("#highPerformanceArea").find(".ui-jqgrid-hbox").width($("#highPerformanceArea").children(":first").width()*0.5);
    	            	$("#lowPerformanceArea").find(".ui-jqgrid-hbox").width($("#lowPerformanceArea").children(":first").width()*0.5);
    	            	
    	            	// $(this.HPId).setGridWidth(880);
    	               // $(this.LPId).setGridWidth(880);
    	            }
    	       
    	        },loadRunOffWarnEmp:function(result){
    	        	this.runOffPanel(result);
                	if(!_.isEmpty(result)){
                		$(".wran-ul").children("li[aria-controls='runOffWarnTab']").find("span").text(result.length);
                		
                	}else
                	$(".wran-ul").children("li[aria-controls='runOffWarnTab']").find("span").text(0);
    	        },loadLowPerformance:function(result){
    	        	warnPanel.performanceWarnObj.initLPGrid(result);
                	if(!_.isEmpty(result)){
                		$(".wran-ul").children("li[sign='performanceLowWarnTab']").find("span").text(result.length);
                		
                	}else
                		$(".wran-ul").children("li[sign='performanceLowWarnTab']").find("span").text(0);
    	        },loadHighPerformance:function(result){
    	        	warnPanel.performanceWarnObj.initHPGrid(result);
                	if(!_.isEmpty(result)){
                		$(".wran-ul").children("li[sign='performanceHighWarnTab']").find("span").text(result.length);
                		
                	}else
                		$(".wran-ul").children("li[sign='performanceHighWarnTab']").find("span").text(0);
    	        },loadOvertimeEmp:function(result){
    	        	this.overtimePanel(result);
                	if(!_.isEmpty(result)){
                		$(".wran-ul").children("li[aria-controls='workLifeWarnTab']").find("span").text(result.length);
                	}else
                		$(".wran-ul").children("li[aria-controls='workLifeWarnTab']").find("span").text(0);
    	        },
    		loadPanel:function(orgId){
    			var self=this;
    			warnPanel.performanceWarnObj.init();
    			var bool=false;
    			$.each(warnPageCache,function(i,v){
					if(v.orgId==orgId){
						self.loadRunOffWarnEmp(v.data.runOffWarnEmp);
						self.loadLowPerformance(v.data.lowPerformance);
						self.loadHighPerformance(v.data.highPerformance);
						self.loadOvertimeEmp(v.data.overtimeEmp);
						bool=true;
						return;
					}
				});
    			if(bool)
				return;
    			var data={};
        		$.ajax({
                    url: urls.getRunOffWarnEmpInfoUrl,
                    type:"post",
                    data: {organId: orgId},
                    success: function (result) {
                    	data.runOffWarnEmp=result;
                    	self.loadRunOffWarnEmp(result);
                    }
        		});
        		$.ajax({
                    url: urls.getLowPerformanceUrl,
                    type:"post",
                    data: {organId: orgId},
                    success: function (result) {
                    	data.lowPerformance=result;
                    	self.loadLowPerformance(result);
                    }
        		});
        		$.ajax({
                    url: urls.getHighPerformanceUrl,
                    type:"post",
                    data: {organId: orgId},
                    success: function (result) {
                    	data.highPerformance=result;
                    	self.loadHighPerformance(result);
                    }
        		});
        		
        		$.ajax({
                    url: urls.getOvertimeEmpUrl,
                    type:"post",
                    data: {organId: orgId},
                    success: function (result) {
                    	data.overtimeEmp=result;
                    	self.loadOvertimeEmp(result);
                    }
        		});
        		warnPageCache.push({orgId:orgId,data:data});

    		},runOffPanel:function(empArr){
    			var detailArr = [];
    	     	  $('#riskEmpDetail').empty();
    	         $.each(empArr, function (i, item) {
    	         	var divmodal=$(getEmpDetailTpl(item));
    	         	 $('#riskEmpDetail').append(divmodal);
    	         	$(divmodal).find("img").tooltipZrw({
    	              	modal:"runoffInfo",
    	              	data:item,
    	              	event:"click|mousemove",
    	              //	style:"top",
    	              	callback:function(obj,rsdata){
    	              		 var empId = rsdata.empId;
    	                      var talentType =rsdata.keyTalentTypeName;
    	                      var baseInfoDom = $(obj).find('.base-info');
    	                      var detailText = $(obj).find('.base-info .row').last().find('span');
    	                      detailText.empty();
    	                      $.ajax({
    	                          url: urls.empBaseInfoUrl,
    	                          data: {empId: empId},
    	                          success: function (data) {
    	                              detailText.eq(0).text(data.userNameCh);
    	                              detailText.eq(2).text(talentType + '类人才 ' + data.sequenceName + ' ' + data.abilityName);
    	                          }
    	                      });
    	     
    	                      $.ajax({
    	                          url: urls.empRiskDetailUrl,
    	                          data: {empId: empId},
    	                          success: function (data) {
    	                              if (!_.isEmpty(data)) {
    	                                  var topRiskInfo = data[0];
    	                                  var flagClass = 'risk-flag img-circle ' + riskFlagArr[topRiskInfo.riskFlag];
    	                                  $(obj).find('.base-info .row').last().find('span').eq(1).removeClass().addClass(flagClass);
    	                                  $(obj).find('.suggest-info div').text(topRiskInfo.note || '');
    	                              }
    	                              riskTreeOption.data = data;
    	                              $(obj).find('.risk-detail-info').children().riskTree(riskTreeOption);
    	                          }
    	                      });
    	                  }
    	              });
    	         });
    	         $('#riskNum').text(empArr.length);
    		},overtimePanel:function(empArr){
    			 var detailArr = [];
    	      	  $('#workLifeDetail').empty();
    	          $.each(empArr, function (i, item) {
    	          	var divmodal=$(getEmpWorkOvertimeDetailTpl(item));
    	          	 $('#workLifeDetail').append(divmodal);
    	          	$(divmodal).find("img").tooltipZrw({
    	               	modal:"workLifeInfo",
    	               	data:item,
    	            	event:"click|mousemove",
    	               	callback:function(obj,rsdata){
    	               		 var empId = rsdata.empId;
    	                        var talentType =rsdata.keyTalentTypeName;
    	                    	$(obj).find("#total").text(item.totalHour);
    	                       $.ajax({
    	                           url: urls.getWorkOvertimeUrl,
    	                           type:"post",
    	                           data: {empId: empId},
    	                           success: function (data) {
    	                        	  // $(obj).find("#workLifeChart").width(800);
    	                        	   //$(obj).find("#workLifeChart").height(300);
    	                        	   if(!_.isEmpty(data)){
	        	                           	var workLifeChart=echarts.init($(obj).find("#workLifeChart")[0]); 
	        	                            var xdata=[];
	        	                            var ydata=[];
	        	                            var ymax=0;
	        	                           	$.each(data, function (i, obj) {
	        	                           		xdata.push(obj.date);
	        	                           		ydata.push(obj.hourCount);
	        	                           		if(ymax<obj.hourCount){
	        	                           			ymax=obj.hourCount;
	        	                           		}
	        	                            });
	        	                           	overtimeOption.xAxis[0].data = xdata;
	        	                           	overtimeOption.series[0].data = ydata;
	        	                           	overtimeOption.yAxis[0].max=((ymax/2)+1)*2;
	        	                           	overtimeOption.yAxis[0].splitNumber=((ymax/2)+1);
	        	                        	workLifeChart.setOption(overtimeOption, true);
    	                        		}
    	                        	
    	                           }
    	                       });
    	                   }
    	               });
    	          });
    	          $('#riskNum').text(empArr.length);
    		}
    }
    //页面预警缓存
    var warnPageCache=[];
    warnPanel.loadPanel(reqOrgId);
    
    $(".wran-ul").click(function(e){
    	var aria_controls=$(e.target).attr("aria-controls");
    	if(!_.isEmpty(aria_controls)){
    		$("#showWarningModal a[aria-controls='"+aria_controls+"']").tab('show');
    	}else{
    		$("#showWarningModal a:first").tab('show');
    	}
    	$("#showWarningModal").modal("show");
    });
    
    $("#person").click(function(e){
    	$("#riskDetailModal").modal("show");
    });
    $.zrw_resizeFrameSize();
    $.zrw_resizeModal("talentDevelModal");
   // $.zrw_resizeModal("gainOfLossModal");
    $.zrw_resizeModal("remindModal");
    $.zrw_resizeModal("abInfoModal");
    $.zrw_resizeModal("orgTreeGridModal");
    
//    $.zrw_resizeModal("showWarningModal");
    
});