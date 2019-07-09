require(['jquery', 'echarts', 'echarts/chart/bar', 'echarts/chart/pie',
    'bootstrap', 'jgGrid', 'underscore', 'utils', 'unveil', 'ztree', 'riskTree','resize'], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        loaderUrl: webRoot + "/assets/img/base/loader.gif",
        directEmpDataUrl: webRoot + "/dismissRisk/getDirectEmpData.do", // 获取直接下属数据
        subOrganEmpUrl: webRoot + "/dismissRisk/querySubOrganEmpInfo.do",	//获取下级组织的员工信息
        dimissRissReviewUrl: webRoot + "/dismissRisk/queryDimissRissReviewInfo.do",	//获取离职风险预测回归的员工信息
        empBaseInfoUrl: webRoot + "/common/getEmpBaseInfo.do",	//获取员工基本信息
        empRiskDetailUrl: webRoot + "/dismissRisk/getEmpRiskDetail.do",	//获取员工离职风险信息
        riskWarningPeriodDescUrl: webRoot + '/dismissRisk/getRiskWarningPeriodDesc.do'
    };
    var ecConfig = require('echarts/config');

    $(win.document.getElementById('tree')).next().hide();
    var reqOrgId = $('#reqOrganId').val();
    var reqOrgText = $('#reqOrganName').val();

    var tabIds = {
        topContain: 'dimissTypeTabs',
        topRisk: 'dimissRiskTab', //人才流失风险
        topPreRisk: 'preDimissRiskTab', //风险流失回顾
        subContain: 'subDimissRiskTabs',
        directEmp: 'directEmpTab', //直接下属
        subEmp: 'subEmpTab' //下级组织员工
    };

    var barLegend = ['有风险', '无风险'];
    /**
     * 人才类型点击复选框
     */
    $('#talentTypeCheck input').click(function () {
        directEmpObj.generateDetail();
    });

    var riskTreeOption = {
        hasSelect: false,
        data: null,
        hasTopText: false
    };
    var riskFlagArr = ['gray', 'red', 'yellow', 'green'];
    /**
     * 直接下属
     */
    var directEmpObj = {
        chartId: 'directRiskBar',
        init: function () {
            this.barOption = null;
            this.barObj = echarts.init(document.getElementById(this.chartId));
            this.barObj.on(ecConfig.EVENT.CLICK, this.clickBar);
            this.initData();
        },
        initData: function () {
            var self = this;
            if (_.isEmpty(self.resultData)) {
                self.getRequestData();
                return;
            }
            self.generateDetail();
        },
        getRequestData: function () {
            var self = this;
            $.ajax({
                url: urls.directEmpDataUrl,
                success: function (data) {
                    self.resultData = data;
                    var curTabId = getActiveTabId('#dimissRiskTab');
                    if (curTabId == tabIds.directEmp) {
                        self.generateDetail();
                    }
                    currOrgPieArea.init(getDistinctEmp(data,false).arr);
                },
                error: function () {

                }
            });
        },
        generateDetail: function () {
            var self = this;
            if (_.isEmpty(self.resultData)) {
                showEmptyTip($('#directEmpTab .row').eq(1));
                return;
            }
            // 过滤人才类型后的数据
            var filterTypeData = getFilterTypeData(self.resultData);
            // 去重后的员工据,totalCount：总人数（包含理智风险和非离职风险的人数） arr：员工数据
            var distinctEmpObj = getDistinctEmp(filterTypeData, true);
            if (distinctEmpObj.arr.length == 0) {
                showEmptyTip($('#directEmpTab .row').eq(1));
                return;
            }
            removeEmptyTip($('#directEmpTab .row'), this.barObj);
            self.empArr = distinctEmpObj.arr;
            // 初始化离职风险的人员展示
            self.initEmpDetail(distinctEmpObj.arr);
            // 柱状图数据
            self.barData = self.sortData(getBarData(filterTypeData));
            self.noRiskData = self.getNoRiskData(distinctEmpObj.totalCount);
            initBar(self.barData, self.barOption, self.barObj, self.noRiskData,self.chartId,self.clickBar);
        },
        //按人数的多少排序 add by zgj 20151209
        sortData:function(data){
        	if(data!=null){
        		var len=data.length;
        		var valArr = new Array(len);
            	for(var i =0;i<len;i++){
            		valArr[i]=data[i].value;
            	}
            	valArr = valArr.sort();
            	var dataSort=new Array(len);
            	for(var a=0;a<len;a++){
                	for(var i =data.length-1;i>=0;i--){
                		if(data[i].value==valArr[a]){
                			dataSort[a]=data[i];
                			data.splice(i,1); 
                			break;
                		}
                	}
            	}
            	data=dataSort;
        	}
        	return data;
        },
        initEmpDetail: function (empArr) {
            var detailArr = [];
            $.each(empArr, function (i, item) {
                detailArr.push(getEmpDetailTpl(item));
            });
            $('#riskEmpDetail').empty().append(detailArr.join(''));
            $('#riskEmpDetail').find('img').unveil();
            $('#riskNum').text(empArr.length);
            this.bindPicClickEvent();
        },
        getNoRiskData: function (total) {
            var noRiskArr = [];
            $.each(this.barData, function (i, item) {
                noRiskArr.push((total - item.value) || '');
            });
            return noRiskArr;
        },
        clickBar: function (param) {		// 点击柱状图，进行数据钻取
            var barData = null, empArr = null;
            if (param.data.drillFlag) {
                //如果drillFlag为true,证明柱状图已经钻取过了，此时应返回上一层，即显示所有数据
                barData = directEmpObj.barData;
                empArr = directEmpObj.empArr;
                $('#riskTitle').text('离职风险人数');
            } else {
                param.data.drillFlag = 1;
                barData = [param.data];
                empArr = param.data.empArr;
                $('#riskTitle').text(param.name + '有风险');
            }
            initBar(barData, directEmpObj.barOption, directEmpObj.barObj, directEmpObj.noRiskData,directEmpObj.chartId,directEmpObj.clickBar);
            directEmpObj.initEmpDetail(empArr);
        },
        //点击图片
        bindPicClickEvent: function () {
            var self = this;
            $('#riskEmpDetail').find('.head-pic').unbind('click').click(function () {
                $('#riskDetailModal').modal('show');
                self.showDialogEmpBase(this);
                self.showDialoRiskTree(this);
            });
        },
        //弹出框
        showDialogEmpBase: function (clickedDom) {
            var empId = $(clickedDom).attr('data-id');
            var headPicUrl = $(clickedDom).attr('data-src');
            var talentType = $(clickedDom).attr('data-type');
            var baseInfoDom = $('#riskDetailModal .base-info');
            $('#riskDetailModal .base-info .head-pic').attr('src', headPicUrl);
            var detailText = $('#riskDetailModal .base-info .row').last().find('span');
            detailText.empty();
            $.ajax({
                url: urls.empBaseInfoUrl,
                data: {empId: empId},
                success: function (data) {
                    detailText.eq(0).text(data.userNameCh);
                    detailText.eq(2).text(talentType + '类人才 ' + data.sequenceName + ' ' + data.abilityName);
                }
            });
        },
        //渲染离职风险树
        showDialoRiskTree: function (clickedDom) {
            var empId = $(clickedDom).attr('data-id');
            $.ajax({
                url: urls.empRiskDetailUrl,
                data: {empId: empId},
                success: function (data) {
                    if (!_.isEmpty(data)) {
                        var topRiskInfo = data[0];
                        var flagClass = 'risk-flag img-circle ' + riskFlagArr[topRiskInfo.riskFlag];
                        $('#riskDetailModal .base-info .row').last().find('span').eq(1).removeClass().addClass(flagClass);
                        $('#riskDetailModal .suggest-info div').text(topRiskInfo.note || '');
                    }
                    riskTreeOption.data = data;
                    $('#riskDetailModal .risk-detail-info').children().riskTree(riskTreeOption);
                }
            });
        }

    };

    directEmpObj.init();

    /**
     * 获取复选框选中的值
     */
    function getCheckedArr() {
        var checkedDomArr = $('#talentTypeCheck input:checked');
        var checkedVal = _.map(checkedDomArr, function (item, i) {
            return $(item).val();
        });
        return checkedVal;
    }


    function initAccordArea(id) {
        //判断是人才流失风险 或是风险流失回顾
        var topTypeTabId = getActiveTabId('#dimissTypeTabs');
        if (topTypeTabId == tabIds.topPreRisk) {
            dimissRiskReviewObj.initData(id);
            return;
        }
        directEmpObj.initData(id);
        var curSubTabId = getActiveTabId('#subDimissRiskTabs');
        if (curSubTabId == 'subEmpTab') {
            //如果下级组织tab属于激活状态，则显示该区域数据
            subOrganEmpObj.initData(id);
        }
    }

    //点击直接下属、下级组织员工tab页
    $('#subDimissRiskTabs a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        var targetAreaId = $(e.target).attr('aria-controls');
        //切换标签页时，要调用echarts的resize()方法对图表进行重绘（当tab页的内容处于隐藏状态时，改变窗口大小后重新显示时，图表可能会显示不正确，故需重绘）
        var targetObj = (targetAreaId == tabIds.directEmp) ? directEmpObj : subOrganEmpObj;
        $('#chartArea').show();
        $('#rigthArea').hide();
        targetObj.barObj.resize();
        targetObj.initData(reqOrgId);
    });

    /**
     * 返回当前焦点tab页显示的区域id
     */
    function getActiveTabId(targetDom) {
        return $(targetDom).find('li.active').find('a').attr('aria-controls');
    }

    //饼图option(默认是当前组织离职风险)
    var pieOption = {
        title: {
            show: true,
            text: '',
            x: 'center',
            y: 'bottom',
            textStyle: {
                fontSize: 14
            }
        },
        color: ['#FFD966', '#BFBFBF'],
        calculable: false,
        series: [{
            type: 'pie',
            clickable: false,
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        textStyle: {
                            color: 'black'
                        }
                    },
                    labelLine: {
                        length: 1
                    }
                }
            },
            radius: '55%',
            data: []
        }]
    };

    /**
     * 当前组织离职风险分析
     */
    var currOrgPieArea = {
        option: {
            container: '.current-organ-boby .row',
            pieArr: []
        },
        init: function (data) {
            var self = this;
            if (_.isEmpty(data)) {
                showEmptyTip($(self.option.container));
                return;
            }
            removeEmptyTip($(self.option.container));
            $(self.option.container).empty();
            self.option.pieArr = [];
            var dataArr = self.packData(data);
            var domWidthClass = 'col-md-' + parseInt(12 / dataArr.length) + ' col-sm-6 col-xs-12';
            $.each(dataArr, function (i, item) {
                self.initPie(item, domWidthClass);
            });
        },
        //数据结构
        dataStructure: function (keyTalentTypeId, typeName) {
            return {
                keyTalentTypeId: keyTalentTypeId,
                title: typeName + '离职风险',
                barData: {riskSum: 0, noRiskSum: 0}
            };
        },
        packData: function (data) {
            var self = this;
            // 当前组织离职风险 饼图数据
            var totalPieData = this.dataStructure('', '当前组织');
            // 各个类别饼图数据集合
            var typeDataArr = [];
            $.each(data, function (i, item) {
                var existTypeItem = _.find(typeDataArr, function (obj) {
                    return item.keyTalentTypeId == obj.keyTalentTypeId;
                });
                if (!existTypeItem) {
                    existTypeItem = self.dataStructure(item.keyTalentTypeId, item.keyTalentTypeName + "类人群");
                    typeDataArr.push(existTypeItem);
                }
                var effectAttr = (item.separationRiskId) ? 'riskSum' : 'noRiskSum';
                existTypeItem.barData[effectAttr]++;
                totalPieData.barData[effectAttr]++;
            });
            typeDataArr.splice(0, 0, totalPieData);
            return typeDataArr;
        },
        initPie: function (data, widthClass) {
            var self = this;
            var chartDom = self.createPieDom(widthClass);
            var currOrganPie = echarts.init(chartDom);
            var newPieOption = self.getOption(data);
            currOrganPie.setOption(newPieOption, true);
            self.option.pieArr.push(currOrganPie);
        },
        createPieDom: function (widthClass) {
            var chartDom = document.createElement('div');
            $(this.option.container).append(chartDom);
            $(chartDom).addClass('curr-organ-pie ' + widthClass);
            return chartDom;
        },
        getOption: function (data) {
            var newPieOption = $.extend(true, {}, pieOption);
            var riskSum = data.barData.riskSum;
            var noRiskSum = data.barData.noRiskSum;
            var total = riskSum + noRiskSum;
            newPieOption.series[0].data = [formatData(barLegend[0], riskSum, total), formatData(barLegend[1], noRiskSum, total)];
            newPieOption.title.text = data.title;
            return newPieOption;
        }

    };


    // 横向柱状图的option
    var riskBarOption = {
        calculable: false,
        legend: {
            show: true,
            x: 'center',
            y: 'bottom',
            selectedMode: false,
            data: barLegend
        },
        //color: ['#FFD966', '#BFBFBF'],
        grid: {
            borderWidth: 0,
            x: 110,
            x2: 28,
            y: 10,
            y2: 35
        },
        xAxis: [{
            type: 'value',
            axisLabel: {
                show: false
            },
            splitLine: false,
            axisLine: {
                show: true,
                lineStyle: {
                    color: '#D9D9D9'
                }
            },
            axisTick: {
                show: false
            }
        }],
        yAxis: [{
            type: 'category',
            splitLine: false,
            axisLine: {
                show: true,
                lineStyle: {
                    color: '#D9D9D9'
                }
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                show: true,
                textStyle: {
                    color: '#333'
                }
            },
            data: []
        }],
        series: []
    };


    function showEmptyTip($targetDom) {
        //如果是显示状态
        if ($targetDom.find('.empty-tip').length != 0) {
            return;
        }
        var domHeight = $targetDom.height() || 100;
        $targetDom.children().hide();
        var emptyTipStyle = 'height:' + domHeight + 'px;line-height:' + domHeight + 'px;';
        $targetDom.append('<div class="empty-tip" style="' + emptyTipStyle + '">暂无数据</div>');
    }

    function removeEmptyTip($targetDom, chartObj) {
        $targetDom.find('.empty-tip').remove();
        $targetDom.children().show();
        if (chartObj) {
            chartObj.resize();
        }
    }

    /**
     * 获取过滤人才类型后的类型数据
     */
    function getFilterTypeData(riskData) {
        var filterTypeData = [];
        var checkedArr = getCheckedArr();
        $.each(riskData, function (i, item) {
            if (!_.isEmpty(checkedArr) && $.inArray(item.keyTalentTypeId, checkedArr) == -1) {
                //如果选中值不为空，并且当前数据不在选中值内，则不需加入该数据
                return true;
            }
            filterTypeData.push(item);
        });
        return filterTypeData;
    }

    function getBarData(filterTypeData) {
        var resulBarData = [];
        $.each(filterTypeData, function (i, item) {
            if (!item.separationRiskId) {
                return true;
            }
            var existRiskItem = _.find(resulBarData, function (obj) {
                return item.separationRiskId == obj.separationRiskId;
            });
            if (existRiskItem) {
                //如果结果集有存在该风险，则对该风险的值+1
                existRiskItem.value++;
                existRiskItem.empArr.push(item);
            } else {
                var riskItem = {separationRiskId: item.separationRiskId, riskName: item.separationRiskName, value: 1, empArr: [item]};
                resulBarData.push(riskItem);
            }
        });
        return resulBarData;
    }

    /**
     * 去除重复的员工数据,并给全局的选中类别的总人数 赋值
     * @param filterTypeData 过滤人才类别后的数据
     * @param isNoRiskDel 是否删除没有离职风险的人员; true:删除，false:不删除（直接下属页面需删除，下级组织员工不需删除）
     * @return totalCount：总人数（包含理智风险和非离职风险的人数）arr：符合条件的具体数据（由isNoRiskDel参数决定）
     */
    function getDistinctEmp(filterTypeData, isNoRiskDel) {
        var distinctEmpArr = [];
        var countTypeTotal = 0;
        $.each(filterTypeData, function (i, item) {
            if (isNoRiskDel && (!(item.separationRiskId) || item.riskFlag == 3) ) {
                countTypeTotal++;
                return true;
            }
            var existEmp = _.find(distinctEmpArr, function (obj) {
                return item.empId == obj.empId;
            });
            if (!existEmp) {
                countTypeTotal++;
                distinctEmpArr.push(item);
            }
        });
        return {totalCount: countTypeTotal, arr: distinctEmpArr};
    }

    var subEmpTreeObj;
    var subEmpTreeSetting = {
        view: {
            dblClickExpand: dblClickExpand,
            showIcon: false,
            showLine: true,
            selectedMulti: false,
            addDiyDom: addDiyDom
        },
        data: {
            key: {
                name: 'userNameCh'
            },
            simpleData: {
                enable: true,
                idKey: "empId",
                pIdKey: "parentEmpId",
                rootPId: ""
            }
        },
        callback: {
            onClick: zTreeOnClick
        }
    };

    function dblClickExpand(treeId, treeNode) {
        return treeNode.level > 0;
    }

    function addDiyDom(treeId, treeNode) {
        var aObj = $("#" + treeNode.tId + "_a");
        if ($("#diyBtn_" + treeNode.id).length > 0) return;
        var num = recursionChildrenNumber(treeNode);
        treeNode.childrenNumber = num;
        if (num == 0) {
            //treeNode.click = null;
            //console.log(treeNode);
        }
        var editStr = '（' + num + '）';
        aObj.append(editStr);
    }

    function zTreeOnClick(event, treeId, treeNode) {
        if (treeNode.childrenNumber <= 0) {	//子孙级没有离职风险不予点击
        	$('#rigthArea').show();
        	$('#chartArea').hide();
            return;
        }else{
        	$('#chartArea').show();
        	$('#rigthArea').hide();
        }
        var newNodes = recursionChildrenData(treeNode);
        var treeFilterData = [];
        var newNodeLists=[];//重排序
        $.each(subOrganEmpObj.resultData, function (i, item) {
            var existEmp = _.find(newNodes, function (obj) {
                return item.empId == obj.empId;
            });
            if (existEmp) {
                treeFilterData.push(item);
                if(!_.contains(newNodeLists,existEmp))
                    newNodeLists.push(existEmp);
            }
        });
        subOrganEmpObj.generateDetail(treeFilterData, newNodeLists, true);
    }

    /***递归获取子孙节点数据**/
    function recursionChildrenNumber(treeNode) {
        if (!treeNode.isParent) {
            return 0;
        }
        var num = 0;
        $.each(treeNode.children, function (i, obj) {
//            if (obj.parentEmpId != 888888) {
                num++;
//            }
            num += recursionChildrenNumber(obj);
        });
        return num;
    }

    /***递归获取子孙节点数据**/
    function recursionChildrenData(treeNode) {
        if (!treeNode.isParent) {
            return null;
        }
        var nodes = [];
        $.each(treeNode.children, function (i, obj) {
           // if (obj.parentEmpId != 888888) {
                nodes.push(obj);
           // }
            nodes = _.union(nodes, recursionChildrenData(obj));
        });
        return nodes;
    }

    //下级组织员工表格
    var subEmpRiskOption = {
        data: [],
        datatype: "local",
        altRows: true,	//设置表格行的不同底色
        autowidth: true,
        height: 268,
        colNames: ['姓名', '所属组织', '序列', '层级', '绩效','离职风险'],
        colModel: [
            {
                name: 'userNameCh',
                index: 'userNameCh',
                width: 100,
                sortable: false,
                align: 'left'
            },
            {
                name: 'organizationName',
                index: 'organizationName',
                width: 150,
                sortable: false,
                align: 'left'
            },
            {
                name: 'sequenceName',
                index: 'sequenceName',
                width: 90,
                sortable: false,
                align: 'center'
            },
            {
                name: 'abilityName',
                index: 'abilityName',
                width: 90,
                sortable: false,
                align: 'center'
            },
            {
                name: 'performanceName',
                index: 'performanceName',
                width: 80,
                sortable: false,
                align: 'center'
            },
            {
                name: 'separationRiskId',
                index: 'separationRiskId',
                width: 80,
                sortable: false,
                align: 'center',
                formatter:function(value){
                	return _.isEmpty(value)?"无":"有";
                }
            
            }
        ],
        rownumbers: true,
        rownumWidth: 40,
        scroll: true
    };

    //下级组织员工信息
    var subOrganEmpObj = {
        resultData: null,
        treeId: "#subEmpTree",
        empTotal: 0,
        organId: null,
        empRisks: null,
        directEmpData: null, //直接下属数据(做为移除数据用)
        chartId: 'subEmpRiskBar',
        gridId: '#subEmpRiskGrid',
        init: function () {
            var self = this;
            $(self.gridId).jqGrid(subEmpRiskOption);
            self.barOption = null;
            self.barObj = echarts.init(document.getElementById(self.chartId));
            self.barObj.on(ecConfig.EVENT.CLICK, self.clickBar);
        },
        initGrid: function () {
            var self = this;
            $(self.gridId).clearGridData().setGridParam({
                data: subEmpRiskOption.data
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        resizeGrid: function () {
            $(this.gridId).setGridWidth($('#subEmpRiskTable').width() * 0.98);
        },
        initData: function (organId) {
            var self = this;
            if (_.isNull(self.resultData) || self.organId != organId) {
                self.organId = organId;
                self.getRequestData(organId);
                return;
            }
            self.generateDetail();
        },
        getRequestData: function (organId) {
            var self = this;
            $.post(urls.subOrganEmpUrl, function (rs) {
                self.resultData = rs;
                self.initData(organId);
            });
        },
        generateDetail: function (treeFilterData, gridData, isTreeInit) {	//重新封装数据
            var self = this;
            if (_.isEmpty(self.resultData)) {
                showEmptyTip($('#subEmpTab'));
                return;
            }
            removeEmptyTip($('#subEmpTab'), subOrganEmpObj.barObj);
    //        removeEmptyTip($('#subEmpTab'));

            // 人员数据
            var filterTypeData = treeFilterData || self.resultData;
            if (_.isEmpty(filterTypeData)) {
                showEmptyTip($('#' + self.chartId).parent());
                self.gridData = [];
                self.initGrid();
                return;
            }
            removeEmptyTip($('#' + self.chartId).parent());
            // 去重后的员工数组
            self.distinctEmpObj = getDistinctEmp(filterTypeData, false);

            //tree 数据
            if (!isTreeInit) {
                var treeData = self.extendOrganTree(self.distinctEmpObj.arr);
                var treeListData = Tc.cloneObj(treeData);
                treeListData.sort(function(a,b){
                    return a.userNameCh.localeCompare(b.userNameCh);
                });
                subEmpTreeObj = $.fn.zTree.init($(self.treeId), subEmpTreeSetting, treeListData);
               // self.gridData = _.difference(treeData, self.directEmpData);
               // self.gridData=_.difference(treeData, {'empId': 888888, 'userNameCh': '所有', 'open': true});;
               // alert(treeData.length);
                treeData.pop();
               // alert(treeData.length);
                self.gridData = treeData;
            } else {
                self.gridData = gridData;
            }
            //表格数据
            subEmpRiskOption.data = self.gridData;
            self.initGrid();
            // 柱状图数据
//            self.barData = getBarData(self.removeDirectEmp(filterTypeData));
            // bug305: 柱状看的包括直接下属 by jxzhang
            self.barData = getBarData(filterTypeData);
            self.noRiskBarData = self.getNoRiskData(self.gridData);
            if (_.isEmpty(self.barData)) {
                showEmptyTip($('#' + self.chartId).parent());
                return;
            }
            initBar(self.barData, self.barOption, self.barObj, self.noRiskBarData,self.chartId,self.clickBar);
        },
        removeDirectEmp: function (sourceData) {		//去除直接下属数据
            var self = this;
            var directEmpData = self.directEmpData;
            var newData = [];
            $.each(sourceData, function (i, obj) {
                var bool = false;
                $.each(directEmpData, function (j, emp) {
                    if (obj.empId == emp.empId) {
                        bool = true;
                        return true;
                    }
                });
                if (!bool) newData.push(obj);
            });
            return newData;
        },
        extendOrganTree: function (distinctData) {
            var self = this;
            if (_.isEmpty(distinctData)) {
                return;
            }
            var i = 0;
            var extendsData = [];
            var directEmpData = [];
            $.each(distinctData, function (i, obj) {
                var newObj = _.clone(obj);
                var parentId = newObj.parentEmpId;
                var bool = false;
                $.each(distinctData, function (j, child) {
                    if (parentId == child.empId) {
                        bool = true;
                        return false;
                    }
                });
                if (!bool) {
                    newObj.parentEmpId = 888888;
                    directEmpData.push(newObj);
                }
                extendsData.push(newObj);
            });
            var rootData = {'empId': 888888, 'userNameCh': '所有', 'open': true};
            extendsData.push(rootData);
            directEmpData.push(rootData);

            self.directEmpData = directEmpData;
            return extendsData;
        },
        //获取没有风险的柱状图数据
        getNoRiskData: function (gridData) {
            var self = this;
            var noRiskBarData = [];
            //循环有离职风险的柱状图数据
            $.each(self.barData, function (i, item) {
                var noRiskItem = $.extend(true, {}, item);
                var riskArr = item.empArr;
                var noRiskEmpArr = [];
                $.each(gridData, function (j, gridItem) {
                    //判断当前值是否存在于有离职风险的集合中，
                    var existTypeItem = _.find(riskArr, function (obj) {
                        return gridItem.empId == obj.empId;
                    });
                    //如果没有，则加入无离职风险集合
                    if (!existTypeItem) {
                        noRiskEmpArr.push(gridItem);
                    }
                });
                noRiskItem.empArr = noRiskEmpArr;
                noRiskItem.value = noRiskEmpArr.length || '';
                noRiskItem.isNoRisk = true;
                noRiskBarData.push(noRiskItem);
            });
            return noRiskBarData;
        },
        clickBar: function (param) {
            var barData = null, empArr = null;
            if (param.data.drillFlag) {
                //如果drillFlag为true,证明柱状图已经钻取过了，此时应返回上一层，即显示所有数据
                barData = subOrganEmpObj.barData;
                empArr = subOrganEmpObj.gridData;
            } else {
                //如果param.data为数字，则表明点击的是无离职风险区域
                var drillFlag = (param.data.isNoRisk) ? 2 : 1;
                param.data.drillFlag = drillFlag;
                barData = [param.data];
                empArr = param.data.empArr;
            }
            initBar(barData, subOrganEmpObj.barOption, subOrganEmpObj.barObj, subOrganEmpObj.noRiskBarData,subOrganEmpObj.chartId,subOrganEmpObj.clickBar);
            subEmpRiskOption.data = empArr;
            subOrganEmpObj.initGrid();
        }
    };

    subOrganEmpObj.init();

    //初始化柱状图
    /**
     *
     */
    function initBar(data, option, barObj, noRiskData,chartId,clickBar) {
    	if (!option) {
            option = $.extend(true, {}, riskBarOption);
        }
        var category = [];
        $.each(data, function (i, item) {
            category.push(item.riskName);
        });
        option.color=['#FFD966', '#BFBFBF']
        option.yAxis[0].data = category;
        var legendName = null;
        if (data[0].drillFlag == 1) {
            //'有离职风险'
            option.series[0] = getSingleBarSeries(data, barLegend[0], true);
            //钻取操作只显示有离职风险数据
            legendName = [barLegend[0]];
        } else if (data[0].drillFlag == 2) {
            //'无离职风险'
            option.series[0] = getSingleBarSeries(data, barLegend[1], true);
            //钻取操作只显示有离职风险数据
            legendName = [barLegend[1]];
            option.color=['#BFBFBF'];
        } else {
            option.series[0] = getSingleBarSeries(data, barLegend[0], true);
            //如果是下级组织员工，则无风险人员取noRiskData，并且柱状图的无风险区域可以点击
            if(barObj == subOrganEmpObj.barObj){
                //'无离职风险';下级组织员工的无离职风险区域也能点击
                option.series[1] = getSingleBarSeries(noRiskData, barLegend[1], true);
                legendName = barLegend;
            }else{
                legendName = [barLegend[0]];
                option.color=['#FFD966'];
            }
        }
        option.legend.data = legendName;
        /**
		 * 加载之前先销毁  防止图表停留  
		 * 放此处 防止数据加载过慢  图表销毁后一定时间段内没有数据 并且消除图表闪一下的现象
		 */
        $("#"+chartId).html("");
        barObj = echarts.init(document.getElementById(chartId));
        if(chartId=="directRiskBar"){
        	directEmpObj.barObj= barObj;
        }else{
        	subOrganEmpObj.barObj= barObj;
        }
        
        barObj.on(ecConfig.EVENT.CLICK, clickBar);
        barObj.setOption(option, true);
        barObj.resize();
    }

    var dimissRiskReviewOption = {
        data: [],
        datatype: "local",
        altRows: true,//设置表格行的不同底色
//			altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: 268,//268
        colNames: ['姓名', '所属组织', '职级', '预警/评估次数', '最近评估', '最近预警', '离职日期'],
        colModel: [
            {
                name: 'userNameCh',
                index: 'userNameCh',
                width: 100,
                sortable: false,
                align: 'center'
            },
            {
                name: 'organizationName',
                index: 'organizationName',
                width: 100,
                sortable: false,
                align: 'center'
            },
            {
                name: 'rankName',
                index: 'rankName',
                width: 60,
                sortable: false,
                align: 'center'
            },
            {
                name: 'warnRiskCount',
                index: 'warnRiskCount',
                width: 80,
                sortable: false,
                align: 'center'
            },
            {
                name: 'riskDate',
                index: 'riskDate',
                width: 90,
                fixed: true,
                sortable: false,
                align: 'center'/*,
                formatter: function(cell, options, row) {
                	console.log(cell);
                	return moment(cell).format('YYYY-MM-DD');
                }*/
            },
            {
                name: 'warnDate',
                index: 'warnDate',
                width: 90,
                fixed: true,
                sortable: false,
                align: 'center'/*,
             formatter: function(cell, options, row) {
             console.log(cell);
             return moment(cell).format('YYYY-MM-DD');
             }*/
            },
            {
                name: 'runOffDate',
                index: 'runOffDate',
                width: 90,
                fixed: true,
                sortable: false,
                align: 'center'/*,
                formatter: function(cell, options, row) {
                	return moment(cell).format('YYYY-MM-DD');
                }*/
            },
        ],
        rownumbers: true,
        rownumWidth: 40,
        scroll: true
    };

    /**
     * 流失风险回顾
     */
    var dimissRiskReviewObj = {
        pieObj: null,
        resultData: null,
        organId: null,
        girdId: '#dimissRiskReviewGrid',
        chartId: 'dimissRiskReviewPie',
        pieLegendData: ['有预警提示','未预警提示'],
        pieColor: [ '#1AB394', '#79D2C0'],
        pieData: null,
        pieOption: null,
        init: function () {
            var self = this;
            $(self.girdId).jqGrid(dimissRiskReviewOption);
            self.pieObj = echarts.init(document.getElementById(self.chartId));
            self.pieObj.on(ecConfig.EVENT.PIE_SELECTED, self.selectedPie);
            self.requestRiskWarningDesc();
        },
        initData: function (organId) {
            var self = this;
            if (_.isNull(self.resultData) || self.organId != organId) {
                self.organId = organId;
                self.getRequestData(organId);
                return;
            }
            self.pieData = self.extendPieData();
            self.initDimissRissReviewPie();
            dimissRiskReviewOption.data = self.resultData;
            self.initGrid();
            self.pieObj.resize();
        },
        initDimissRissReviewPie: function () {	//初始化离职预测回顾
            var self = this;
            var pieData = self.pieData;
            var newPieOption = self.pieOption;
            if (_.isNull(newPieOption)) {
                newPieOption = $.extend(true, {}, pieOption);
                newPieOption.color = self.pieColor;
                newPieOption.series[0].selectedMode = 'single';
                newPieOption.series[0].clickable = true;
            }
            //未预警提示，有预警提示
            var noRiskSum = pieData[0].length;
            var riskSum = pieData[1].length;
            var total = riskSum + noRiskSum;
            var seriesData = [formatData(self.pieLegendData[0], riskSum, total), formatData(self.pieLegendData[1], noRiskSum, total)];
            newPieOption.series[0].data = seriesData;
            self.pieOption = newPieOption;
            self.pieObj.setOption(newPieOption, true);
        },
        initGrid: function () {
            var self = this;
            $(self.girdId).clearGridData().setGridParam({
                data: dimissRiskReviewOption.data
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        resizeGrid: function () {
            $(this.girdId).setGridWidth($('#dimissRiskReviewTable').width() * 0.95);
        },
        getRequestData: function (organId) {
            var self = this;
            $.post(urls.dimissRissReviewUrl, function (rs) {
                if (_.isEmpty(rs)) {
                    showEmptyTip($('#' + self.chartId).parent());
                    return;
                }
                removeEmptyTip($('#' + self.chartId).parent());
                self.resultData = rs;
                self.initData(organId);
            });
        },
        extendPieData: function () {
            var self = this;
            var sourceData = self.resultData;
            var pieData = [[], []];
            $.each(sourceData, function (i, obj) {
                pieData[obj.isWarn].push(obj);
            });
            return pieData;
        },
        selectedPie: function (param) {
            var self = dimissRiskReviewObj;
            var pieData = self.pieData;
            var selected = param.selected;
            var bool = false;
            var gridData = [];
            $.each(selected[0], function (i, serie) {
                if (serie) {
                    bool = true;
                    gridData = pieData[i];
                    return true;
                }
            });
            if (!bool) {
                gridData = self.resultData;
            }
            dimissRiskReviewOption.data = gridData;
            self.initGrid();
        },
        requestRiskWarningDesc: function(){
            var self = this;
            $.post(urls.riskWarningPeriodDescUrl,function(rs){
                if(rs.type){
                    $('#riskWarningDesc').text(rs.t);
                }
            });
        }
    };

    dimissRiskReviewObj.init();

    function getSingleBarSeries(data, name, clickable) {
        var obj = {
            type: 'bar',
            clickable: clickable,
            name: name,
            stack: 'stack',
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        position: 'inside',
                        textStyle: {
                            color: '#333'
                        }
                    }
                }
            },
            barGap: 0,	//柱之类的距离
            barCategoryGap: '35%',		//分类柱之间的距离
            barMaxWidth: 43,
            data: data
        };
        return obj;
    }


    function getEmpDetailTpl(data) {
        var bgColor = ' ';
        //if(data.riskFlag == 2){
        //    bgColor = ' style="background-color:#FFCC00"';
        //}
        //TODO 查找头像
        var src = webRoot + "/assets/photo.jpg";
        return '<div class="pull-left text-center">' +
            '<div class="position-relative">' +
            '<img class="head-pic img-circle" src="' + urls.loaderUrl + '" data-src="' + src + '" data-id="' + data.empId + '" data-type="' + data.keyTalentTypeName + '">' +
            '<span class="mark-talent-type img-circle" ' + bgColor + '>' + data.keyTalentTypeName + '</span>' +
            '</div>' +
            '<div class="emp-name">' + data.userNameCh + '</div>' +
            '</div>';
    }

    function formatData(name, value, total) {
        if (!value) {
            return {};
        }
        //页面展示为 ：name，(换行)value，percent%
        return {value: value, name: (name + '，\n' + Tc.formatNumber(value) + '人，' + ((value / total) * 100).toFixed(0) + '%')};
    }

    //格式化表格时间
    function formatterToDate(cellvalue, options, rowObject) {
        return moment(cellvalue).format('YYYY-MM-DD');
    }
    
    //点击人才离职风险、风险流失回顾tab页
    $('#dimissTypeTabs a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        initAccordArea(reqOrgId);
    });

    $(window).resize(function () {
        dimissRiskReviewObj.resizeGrid();
        subOrganEmpObj.resizeGrid();
        directEmpObj.barObj.resize();
        subOrganEmpObj.barObj.resize();
        $.each(currOrgPieArea.option.pieArr, function (i, item) {
            item.resize();
        });
    });
    $.zrw_resizeModal("riskDetailModal");
});