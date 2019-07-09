require(['jquery', 'echarts3', 'echarts/chart/line', 'echarts/chart/scatter', 'bootstrap', 'searchBox3',
    'selection', 'mapGrid', 'inventoryReport', 'organTreeSelector', 'jBootstrapPage', 'select2',
    'jgGrid', 'underscore', 'messenger', 'talent-map2', 'cardTabel', 'keyTalentsAdd'], function ($, echarts) {
    var webRoot = G_WEB_ROOT, win = top != window ? top.window : window;
    var urls = {
        toTalentMapsViewUrl: webRoot + '/talentMaps/toTalentMapsView.do',	//人才地图首页
        toTalentMapsTeamCPViewUrl: webRoot + '/talentMaps/toTalentMapsTeamCPView.do',	//团队能力和绩效趋势图
        queryTeamCPMapUrl: webRoot + '/talentMaps/queryTeamCPMap.do',	// 团队能力和绩效趋势图-地图显示
        queryTeamCPMapPersonDetailUrl: webRoot + '/talentMaps/queryTeamCPMapPersonDetail.do',	// 团队能力和绩效趋势图-地图显示-人员明细
        queryAbilityForListUrl: webRoot + '/talentMaps/queryAbilityForList.do',	// 团队能力和绩效趋势图-列表显示-标题列表
        queryTeamCPGridUrl: webRoot + '/talentMaps/queryTeamCPGrid.do',	// 团队能力和绩效趋势图-列表显示
        getMapsBaseInfoForTeamCPUrl: webRoot + '/talentMaps/getMapsBaseInfoForTeamCP.do',             //获取地图轴线数据-能力/绩效/时间
        queryTeamCPDetailUrl: webRoot + '/talentMaps/queryTeamCPDetail.do',	// 团队能力和绩效趋势图-明细显示
        queryTeamCPFullDetailUrl: webRoot + '/talentMaps/queryTeamCPFullDetail.do',	// 团队能力和绩效趋势图-全屏明细显示
        searchBoxUrl: webRoot + '/common/getTalentMapsSearchBox.do',	//筛选条件信息
        toTalentDetailUrl: webRoot + '/talentProfile/toTalentDetailView.do',	//跳转进入人才剖像
        getTalentMapsEmpPointByStrUrl: webRoot + '/talentMaps/getTalentMapsEmpPointByStr.do',  //获取地图员工点信息
    }
    var ecConfig = require('echarts/config');
    var TextShape = require('zrender/shape/Text');
    $(win.document.getElementById('tree')).next().hide();

    //初始化'加入对比'
    var stackObject;

    function loadStack() {
        if (win.stackObject) {
            stackObject = win.stackObject;
        }
        //假如有对比人员则显示，没有则隐藏
        if (stackObject && stackObject.bottomStock('getPersonIds') != '') stackObject.bottomStock('hideFrame', 0);
        else stackObject.bottomStock('hideFrame', 1);
    }

    loadStack();

    var reqOrgId = win.currOrganId;
    var reqOrgTxt = win.currOrganTxt;

    $("[data-toggle='tooltip']").tooltip();
    // 搜索组件默认值
//    var defaultBoxData = {};
    var defaultBoxData = {
        sequenceStr: null,
        sequenceSubStr: null,
        abilityStr: null,
        performanceStr: null,
        ageStr: null,
        sexStr: null,
        eduStr: null
    }
    var defaultData = {
        organId: null,
        date: null,
        viewType: 0,	//查看能力，默认0，1表示查看绩效
        xData: [],
        yData: [],
        titleId: '',	//列表id
        count: 0,	//总记录数
        pageNum: 1,	//默认第一页
        pageSize: 12,	//默认一页10条
        detailYTitle: '能力'
    }
    var minTime = $('#minTime').val();
    var maxTime = $('#maxTime').val();
    var times = $('#times').val();

    var btnsObj = {
        topViewId: '#topView',
        thisViewId: '#thisView',
        selectTimeId: '#selectTimeId',
        selectChartId: '#selectChartId',
        changeChartId: '#changeChartId',
        treeId: '#organTree',
        changeOrganId: '#changeOrganId',
        changeViewTypeId: '#changeViewTypeId',
        showDetailSpan: '#showDetailSpan',
        hiddenGridSpan: '#hiddenGridSpan',
        showFullViewId: '#showFullViewId',
        fullCloseId: '#fullCloseBtn',
        fullDetailCloseId: '#fullDetailCloseBtn',
        fullMapId: '#fullMap',
        fullGridId: '#fullGrid',
        fullDetailId: '#fullDetail',
        fullOrganText: '#fullOrganText',
        fullTeamText: '#fullTeamText',
        fullDetailOrganText: '#fullDetailOrganText',
        fullDetailTeamText: '#fullDetailTeamText',
        organId: null,
        organText: null,
        pointsData: [],
        navSelectObj: null,
        init: function (organId, flag) {
            var self = this;
            if (!flag) {
                defaultData.date = times;
            }
            self.organId = organId;
            self.initLoadFun();
            self.changeChartViewFun();
            self.changeChartFun();
            self.changeTimeFun();
            self.changeViewTypeFun();
            self.changeViewDetailFun();
            self.changeFullViewFun();
            self.userNameSearchFun();
        },
        initLoadFun: function () {
            var self = this;
            var text = $(self.changeViewTypeId).find('option:selected').text();
            if ($(self.showDetailSpan + ' input').is(':checked')) {
                $('.detail-view').removeClass('closed').siblings('.content-div').addClass('closed');
                self.showDetailBtnFun();
                self.showFullBtnFun();
                detailChartViewObj.init(self.organId);
            } else if (text == '地图显示') {
                $('.map-view').removeClass('closed').siblings('.content-div').addClass('closed');
                self.showDetailBtnFun();
                self.showFullBtnFun();
                mapChartViewObj.init(self.organId);
            } else {
                $('.grid-view').removeClass('closed').siblings('.content-div').addClass('closed');
                self.closeDetailBtnFun();
                self.closeFullBtnFun();
                gridViewObj.init(self.organId);
            }
        },
        changeChartViewFun: function () {
            var self = this;
            $(self.topViewId).unbind('click').bind('click', function () {
                win.setlocationUrl(urls.toTalentMapsViewUrl);
            });
            $(self.thisViewId).unbind('click').bind('click', function () {
                win.setlocationUrl(urls.toTalentMapsTeamCPViewUrl);
            });
            btnCursorStyleFun(self.topViewId);
            btnCursorStyleFun(self.thisViewId);
        },
        changeTimeFun: function () {
            var self = this;
            $(self.selectTimeId).selection({
                dateType: 6,
                dateRange: {
                    min: minTime,
                    max: maxTime
                },
                dateSelected: defaultData.date.split(','),
                dateSelectedLength:5,
                // crowdSelected:['0'],
                ok: function (event, data) {
                    defaultData.date = data.date.join(',');
                    self.initLoadFun();
                }
            });
            $(self.selectTimeId).unbind('click').bind('click', function () {
                if ($(this).hasClass('closeed')) {
                    $(this).addClass('bord-style');
                } else {
                    $(this).removeClass('bord-style');
                }
            });
        },
        changeChartFun: function () {
            var self = this;
            $(self.selectChartId).change(function () {
                var text = $(this).find('option:selected').text();
                $(self.changeChartId).html(text);
                if (text == '团队能力趋势图') {
                    defaultData.viewType = 0;
                    defaultData.detailYTitle = '能力';
                } else {
                    defaultData.viewType = 1;
                    defaultData.detailYTitle = '绩效';
                }
                self.initLoadFun();
            });
            self.organTreeFun();
        },
        organTreeFun: function () {
            var self = this;
            var deptInfo = {
                id: reqOrgId,
                text: reqOrgTxt
            }
            $(self.treeId).parent().find('.dropDownValue').text(reqOrgTxt);
            $(self.treeId).organTreeSelector({
                multiple: false,
                showSelectBtn: false,
                value: deptInfo,
                onSelect: function (ids, texts) {
                    defaultData.organId = ids;
                    defaultData.organText = texts;
                    self.organId = ids;
                    $(self.changeOrganId).html(texts);
                    self.organText = texts;
                    self.initLoadFun();
                }
            });
        },
        changeViewTypeFun: function () {
            var self = this;
            $(self.changeViewTypeId).change(function () {
                self.removeDetailAndGridCheckedFun();
                var text = $(this).find('option:selected').text();
                if (text == '地图显示') {
                    $('.map-view').removeClass('closed').siblings('.content-div').addClass('closed');
                    self.showDetailBtnFun();
                    self.showFullBtnFun();
                    mapChartViewObj.init(self.organId);
                } else {
                    self.closeDetailBtnFun();
                    self.closeFullBtnFun();
                    $('.grid-view').removeClass('closed').siblings('.content-div').addClass('closed');
                    gridViewObj.init(self.organId);
                }
            });
        },
        changeViewDetailFun: function () {
            var self = this;
            $(self.showDetailSpan).unbind('click').bind('click', function () {
                if ($(self.showDetailSpan + ' input').is(':checked')) {
                    $(self.showDetailSpan + ' input').prop('checked', false);
                    self.changeViewDetailCheckedTrueFun();
                } else {
                    $(self.showDetailSpan + ' input').prop('checked', true);
                    self.changeViewDetailCheckedflaseFun();
                }
            });
            $(self.showDetailSpan + ' input').unbind('click').bind('click', function () {
                if ($(this).is(':checked')) {
                    $(self.showDetailSpan + ' input').prop('checked', false);
                    self.changeViewDetailCheckedTrueFun();
                } else {
                    $(self.showDetailSpan + ' input').prop('checked', true);
                    self.changeViewDetailCheckedflaseFun();
                }
            });
            self.changeViewGridFun();
        },
        changeViewGridFun: function () {
            var self = this;
            $(self.hiddenGridSpan).unbind('click').bind('click', function () {
            	var option = mapChartOption;
                if ($(self.hiddenGridSpan + ' input').is(':checked')) {
                    $(self.hiddenGridSpan + ' input').prop('checked', false);
                    option.grid.show = true;
                    option.xAxis.splitLine.show = true;
                    option.xAxis.splitLine.lineStyle.color = '#999999';
                    option.yAxis.splitLine.show = true;
                    option.yAxis.splitLine.lineStyle.color = '#999999';
                } else {
                    $(self.hiddenGridSpan + ' input').prop('checked', true);
                    option.grid.show = false;
                    option.xAxis.splitLine.show = false;
                    option.xAxis.splitLine.lineStyle.color = '#ffffff';
                    option.yAxis.splitLine.show = false;
                    option.yAxis.splitLine.lineStyle.color = '#ffffff';
                }
                if($(self.showDetailSpan + ' input').is(':checked')){
                	detailChartViewObj.mapObj.toggleGrids();
                } else {
                	mapChartViewObj.chart.setOption(option, true);
                }
            });
            $(self.hiddenGridSpan + ' input').unbind('click').bind('click', function () {
                if ($(this).is(':checked')) {
                    $(self.hiddenGridSpan + ' input').prop('checked', false);
                } else {
                    $(self.hiddenGridSpan + ' input').prop('checked', true);
                }
            });
        },
        changeViewDetailCheckedTrueFun: function () {
            var self = this;
            var text = $(self.changeViewTypeId).find('option:selected').text();
            if (text == '地图显示') {
                $('.map-view').removeClass('closed').siblings('.content-div').addClass('closed');
                self.showDetailBtnFun();
                self.showFullBtnFun();
                mapChartViewObj.init(self.organId);
            } else {
                $('.grid-view').removeClass('closed').siblings('.content-div').addClass('closed');
                self.closeDetailBtnFun();
                self.closeFullBtnFun();
                gridViewObj.init(self.organId);
            }
        },
        changeViewDetailCheckedflaseFun: function () {
            var self = this;
            $('.detial-view').removeClass('closed').siblings('.content-div').addClass('closed');
            self.showFullBtnFun();
            detailChartViewObj.init(self.organId);
        },
        removeDetailAndGridCheckedFun: function () {
            var self = this;
            $(self.showDetailSpan + ' input').prop('checked', false);
            $(self.hiddenGridSpan + ' input').prop('checked', false);
            $(self.hiddenGridSpan).addClass('closed');
        },
        fullViewTitleFun: function (flag) {
            var self = this;
            var text = $(self.selectChartId).find('option:selected').text();
            if (flag) {
                $(self.fullDetailOrganText).html(self.organText);
                $(self.fullDetailTeamText).html(text);
            } else {
                $(self.fullOrganText).html(self.organText);
                $(self.fullTeamText).html(text);
            }
        },
        changeFullViewFun: function () {
            var self = this;
            $(self.showFullViewId).unbind('click').bind('click', function () {
                if ($(self.showDetailSpan + ' input').is(':checked')) {
                    var mapObj = detailChartViewObj.mapObj, number = mapObj.getAllPointsNumber();
                    if (number <= 1000 && number > 0) {
                        win.doFullScreen(function (e) {
                            $('#fullMapDetailBody').removeClass('hide');
                            $('#teamCPChartId').addClass('hide');
                            self.fullViewTitleFun(true);
                            var wid = $(self.fullMapId).parent().width() - 30;
                            var hei = $(window).height() - 50;
//			            	$(self.fullDetailId).width(wid).height(hei*0.9);
                            detailChartViewObj.fullInit(self.organId);
                            searchObj.isFirst = true;
                            searchObj.init();
                        });
                        return;
                    } else {
                        showErrMsg("当前人数影响全屏显示，请通过筛选条件过滤多余人员，最多支持1000人全屏!");
                    }
                } else {
                    win.doFullScreen(function (e) {
                        $('#fullMapBody').removeClass('hide');
                        $('#teamCPChartId').addClass('hide');
                        self.fullViewTitleFun();
                        var wid = $(self.fullMapId).parent().width() - 30;
                        var hei = $(window).height() - 50;
                        var text = $(self.changeViewTypeId).find('option:selected').text();
                        if (text == '地图显示') {
                            $(self.fullMapId).removeClass('hide').siblings().addClass('hide');
                            $(self.fullMapId).width(wid).height(hei * 0.9);
                            mapChartViewObj.fullInit(self.organId);
                        } else {
                            $(self.fullGridId).removeClass('hide').siblings().addClass('hide');
                            $(self.fullGridId).width(wid).height(hei * 0.9);
                            gridViewObj.fullInit(self.organId);
                        }

                    });
                }
            });
            self.closeFullViewFun();
        },
        closeFullViewFun: function () {
            var self = this;
            $(self.fullCloseId).unbind('click').bind('click', function () {
                win.doRestoreWindow(function (e) {
                    $('#fullMapBody').addClass('hide');
                    $('#teamCPChartId').removeClass('hide');
                });
            });
            $(self.fullDetailCloseId).unbind('click').bind('click', function () {
                win.doRestoreWindow(function (e) {
                    detailChartViewObj.resizeMap();
                    searchObj.exit();
                    $('#fullMapDetailBody').addClass('hide');
                    $('#teamCPChartId').removeClass('hide');
                });
            });
        },
        userNameSearchFun: function () {
            var self = this, $navSearchEmp = $('#navSearchEmp');

//            var pointsData = {};
            self.pointsData = [];
            // 下拉菜单
            self.navSelectObj = $navSearchEmp.select2({
                placeholder: "请输入名称",
                language: 'zh-CN',
                allowClear: true,
                minimumInputLength: 1,
                ajax: {
                    url: urls.getTalentMapsEmpPointByStrUrl,
                    dataType: "json",
                    delay: 250,
                    type: 'post',
                    data: function (params) {
                        var queryDto = defaultBoxData;
                        queryDto.organId = btnsObj.organId;
                        queryDto.date = defaultData.date;
                        queryDto.yearMonths = null;
                        queryDto.name = params.term;
                        return {
                            queryDtoStr: JSON.stringify(queryDto),
                            page: params.page || 1,
                            rows: 30
                        };
                    },
                    cache: true,
                    processResults: function (data, page) {
                        btnsObj.pointsData = [];
                        if (data.rows != null && data.rows.length > 0) {
                            $.each(data.rows, function (index, object) {
                            	$.each(object.children, function(ind, obj){
                            		obj.id = object.text + '_' + obj.id;
                            	});
                                btnsObj.pointsData.push({text: object.text, children: object.children});
                            });
                        }
                        return {
                            results: btnsObj.pointsData,
                            pagination: {
                                more: (data.page * 30) < data.records
                            }
                        }
                    },
                },
                escapeMarkup: function (markup) {
                    return markup;
                }
            });
            self.navSelectObj.on('select2:select', function (evt) {
                var obj = evt.params.data;
                var yearMonthPoints = _.find(btnsObj.pointsData, function (o) {
                    return o.text == obj.yearMonthText;
                });
                var point = _.find(yearMonthPoints.children, function (o) {
                    return o.empId == obj.id.split('_')[1];
                });
                btnsObj.drawDislog(point);
            });
            self.navSelectObj.on('select2:unselect', function (evt) {
                $('#navSearchBlock').addClass('hide');
            });
        },
        drawDislog: function (point) {
            var self = this;
            var $navSearchBlock = $('#navSearchBlock');
            $navSearchBlock.removeClass('hide');
            $navSearchBlock.html(searchObj.drawEmpDislog(point));
            $navSearchBlock.find('button.btn').click(function () {
                var $this = $(this), pointId = $this.data('toggle');
                $navSearchBlock.addClass('hide');
                $this.inventoryReport({
                    empId: pointId
                });
            });
        },
        closeDetailBtnFun: function () {
            var self = this;
            $(self.showDetailSpan).addClass('closed');
        },
        showDetailBtnFun: function () {
            var self = this;
            $(self.showDetailSpan).removeClass('closed');
        },
        closeFullBtnFun: function () {
            var self = this;
            $(self.showFullViewId).addClass('closed');
        },
        showFullBtnFun: function () {
            var self = this;
            $(self.showFullViewId).removeClass('closed');
        }
    }

    /**
     * 地图显示
     * */
    var mapChartOption = {
        animation: false,
        grid: {
            show: true,
            borderColor: '#999999'
//			height : '70%',
//			y : '10%'
        },
        xAxis: {
            name: '时间',
            type: 'category',
            data: [],
            splitArea: {
                show: true
            },
            axisLine: {
                lineStyle: {
                    color: '#000',
                    width: 2
                }
            },
            axisLabel: {
                interval: 0
            },
            axisTick: false,
            splitLine: {
                show: true,
                lineStyle: {
                    color: '#999999',
                    width: 1
                }
            }
        },
        yAxis: {
            name: defaultData.detailYTitle,
            type: 'category',
            data: [],
            splitArea: {
                show: true
            },
            axisLine: {
                lineStyle: {
                    color: '#000',
                    width: 2
                }
            },
            axisTick: false,
            splitLine: {
                show: true,
                lineStyle: {
                    color: '#999999',
                    width: 1
                }
            }
        },
        visualMap: {
            min: 0,
            max: 30,
            calculable: true,
            orient: 'vertical',
            right: '2%',
            top: '15%',
            color: ['#019AEF', '#ffffff']
        },
        series: [{
            type: 'heatmap',
            data: [],
            label: {
                normal: {
                    show: true,
                    textStyle: {
                        color: '#000'
                    }
                }
            },
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }]
    };
    /**
     * 地图显示-人员明细
     * */
    var mapPersonOption = {
        url: urls.queryTeamCPMapPersonDetailUrl,
        postData: {},
        splitNum: 5,
        type: "post",
        splitHeight: 10,
        modelHeight: 150,
        height: 150,
        rows: 20,
        model: $("#maps-person-table").html(),
        formatModel: function (el, o) {
            var img = o.imgPath != '' && o.imgPath != null ? o.imgPath : webRoot + "/assets/photo.jpg";
            var result = '<div class="div-block">'
                + '<img class="head-pic img-circle span-img" src="' + img + '" data-src="' + img + '" data-id="' + o.empId + '" >'
                + '<div>' + o.userName + '</div>'
                + '</div>';
            return result;
        },
        loadRowComple: function (el, o, index) {
            mapChartViewObj.detailPersonClickFun();
        }
    }
    /**
     * 地图显示
     * */
    var mapChartViewObj = {
        chartId: 'mapChartId',
        chart: initChart('mapChartId'),
        fullChart: initChart('fullMap'),
        option: mapChartOption,
        modalId: '#personDetailModal',
        tabId: '#person_tabel',
        init: function (organId) {
            var self = this;
            this.organId = organId;
            loadingChart(self.chartId);
            clearEChart(self.chart);
            self.getRequestDatasFun();
            resizeEChart(self.chart);
        },
        fullInit: function (organId) {
            var self = this;
            this.organId = organId;
            clearEChart(self.fullChart);
            self.setFullOptionFun();
            resizeEChart(self.fullChart);
//    		self.getRequestDatasFun();
        },
        getRequestDatasFun: function () {
            var self = this;
            var param = defaultBoxData;
            param.organId = self.organId;
            param.viewType = defaultData.viewType;
            param.date = defaultData.date;
            $.post(urls.queryTeamCPMapUrl, param, function (data) {
                self.getOptionFun(data);
            });
        },
        getOptionFun: function (data) {
            var self = this;
            if (data && data.xData != undefined && data.xData.length > 0) {
                hideChart(self.chartId);
                var mapData = data.fData;
                mapData = mapData.map(function (item) {
                    return [item[1], item[0], item[2] || '0'];
                });
                defaultData.xData = data.xIdData;
                defaultData.yData = data.yIdData;
                self.option.xAxis.data = data.xData;
                self.option.yAxis.name = defaultData.detailYTitle;
                self.option.yAxis.data = data.yData;
                self.option.visualMap.min = data.min - 1;
                self.option.visualMap.max = data.max + 1;
                self.option.series[0].data = mapData;
                self.setOptionFun();
            } else {
                hideChart(self.chartId, true);
            }
        },
        setOptionFun: function () {
            var self = this;
            self.chart.setOption(self.option);
            self.optionClickFun();
        },
        optionClickFun: function () {
            var self = this;
            self.chart.on(ecConfig.EVENT.CLICK, function (param) {
                var f = true;
                document.onclick = function (event) {
                    if (f) {
                        var e = event;
                        var xy;
                        xy = e.clientX + ',' + e.clientY;
                        self.showPersonDetailFun(param.value, xy);
                        f = false;
                    }
                }
            });
        },
        setFullOptionFun: function () {
            var self = this;
            self.fullChart.setOption(self.option);
            self.fullOptionClickFun();
        },
        fullOptionClickFun: function () {
            var self = this;
            self.fullChart.on(ecConfig.EVENT.CLICK, function (param) {
                var f = true;
                document.onclick = function (event) {
                    if (f) {
                        var e = event;
                        var xy;
                        xy = e.clientX + ',' + e.clientY;
                        self.showPersonDetailFun(param.value, xy);
                        f = false;
                    }
                }
            });
        },
        showPersonDetailFun: function (value, xy) {
            var self = this;
            self.showPersonDetailDialogStyleFun(xy);
//    		$(self.modalId + ' .modal-body').empty();
            $(self.modalId).modal('show');
            $(self.modalId).on('shown.bs.modal', function () {
                self.detailRequestDataFun(value);
            });
            $(self.modalId).on('hidden.bs.modal', function () {
                $(self.modalId).off();
            });
        },
        detailRequestDataFun: function (value) {
            var self = this;
            var param = defaultBoxData;
            param.organId = self.organId;
            param.yearMonths = defaultData.xData[value[0]];
            param.yVal = defaultData.yData[value[1]];
            param.viewType = defaultData.viewType;
            if ($(self.tabId).html() == '') {
                mapPersonOption.postData = param;
                $(self.tabId).cardTabel(mapPersonOption);
            } else {
                $(self.tabId).clearCardTableData().setCardTableParam({
                    postData: param
                }).reloadCardTable();
            }
        },
        detailPersonClickFun: function () {
            var self = this;
            $(self.modalId).find('.span-img').unbind('click').bind('click', function () {
                var _empId = $(this).attr('data-id');
                var url = urls.toTalentDetailUrl + '?empId=' + _empId + '&rand=' + Math.random();
                window.open(url);
            });
        },
        showPersonDetailDialogStyleFun: function (xy) {
            var self = this;
            xy = xy.split(',');
            var bodyWidth = document.body.clientWidth;
            var bodyHeight = document.body.clientHeight;
            var modalWidth = $(self.modalId + ' .modal-dialog').width();
            var modalHeight = $(self.modalId + ' .modal-dialog').height();
            var acWidth = Number(xy[0]) + Number(modalWidth);
            var newLeft = Number(xy[0]) - Number(modalWidth);
            var acHeight = Number(xy[1]) + Number(modalHeight);
            var newHeight = Number(xy[1]) - Number(modalHeight);
            var style;
            if (acWidth > bodyWidth && acHeight > bodyHeight) {
                style = 'margin-left: ' + newLeft + '; margin-top: ' + newHeight + ';';
            } else if (acWidth > bodyWidth && acHeight <= bodyHeight) {
                style = 'margin-left: ' + newLeft + '; margin-top: ' + xy[1] + ';';
            } else if (acWidth <= bodyWidth && acHeight > bodyHeight) {
                style = 'margin-left: ' + xy[0] + '; margin-top: ' + newHeight + ';';
            } else if (acWidth <= bodyWidth && acHeight <= bodyHeight) {
                style = 'margin-left: ' + xy[0] + '; margin-top: ' + xy[1] + ';';
            }
            $(self.modalId).attr('style', style);
        }
    }
    /**
     * 列表显示
     * */
    var gridViewObj = {
        gridViewId: '#gridViewId',
        fullGridViewId: '#fullGrid',
        init: function (organId) {
            var self = this;
            $(self.gridViewId).empty();
            this.organId = organId;
            self.appendData();
        },
        fullInit: function (organId) {
            var self = this;
            this.organId = organId;
            $(self.fullGridViewId).empty();
            self.requestData(self.fullGridViewId, true);
        },
        getYearMonths: function () {
            var self = this;
            var arr = new Array();
            if (defaultData.date != null && defaultData.date != undefined) {
                var dates = defaultData.date.split(',');
                for (var i = dates[0]; i <= dates[2]; i++) {
                    arr.push(i + '06');
                    arr.push(i + '12');
                }
                if (dates[1] == 2) {
                    arr.shift(arr[0]);
                }
                if (dates[3] == 1) {
                    arr.pop(arr[arr.length - 1]);
                }
            }
            return arr;
        },
        appendData: function () {
            var self = this;
            $('.grid-list').remove();
            var yearMonths = self.getYearMonths();
            if (yearMonths != null && yearMonths.length > 0) {
                for (var i = yearMonths.length - 1; i >= 0; i--) {
                    var div = '<div class="grid-list"><h5>' + self.getStringByNum(yearMonths[i]) + '</h5></div>';
                    div += '<div class="grid-list grid-view" id="grid_' + i + '"></div>';
                    $(self.gridViewId).append(div);
                    var divId = '#grid_' + i;
//                    self.requestData(divId);
                    var param = self.getParam();
                    param.yearMonths = yearMonths[i];
                    param.arr = yearMonths;
                    $(divId).mapGrid({param : param});
                }
            }
        },
        getStringByNum: function (num) {
            var self = this;
            if (num != null) {
                var year = num.substr(0, 4);
                var month = num.substr(4);
                if (month == '06') {
                    return year + '年上半年';
                } else {
                    return year + '年下半年';
                }
            }
        },
        getParam: function () {
            var self = this;
            var param = defaultBoxData;
            param.organId = self.organId;
            param.viewType = defaultData.viewType;
//            param.yearMonths = self.yearMonth[i];
            return param;
        },
        getOption: function (flag) {
            var self = this;
            var option = {
                param: self.getParam()
            };
            if (flag) {
                option.isFull = true;
                option.pageSize = 20;
            } else {
                option.isFull = false;
            }
            return option;
        },
        requestData: function (id, flag) {
            var self = this;
            $(id).mapGrid(self.getOption(flag));
        }
    }
    /**人才地图小弹窗option*/
    var mapsEmpsTablesOption = {
        url: urls.queryTeamCPFullDetailUrl,
        postData: {},
        splitNum: 12,
        type: "post",
        splitHeight: 10,
        modelHeight: 400,
        height: 400,
        rows: 144,
        model: $("#maps-emp-table").html(),
        formatModel: function (el, o) {
            var result = '<span class="u-point-wrapper" data-row="' + o.empId + '">' +
                '<i class="icon-circle u-point"></i>' +
                '<span class="u-point-label" title="' + o.text + '">' + o.text + '</span></span>';
            return result;
        },
        loadRowComple: function (el, o, index) {
            $('#emp_tabel').find('.u-point-wrapper').unbind('click').click(function () {
                var $this = $(this), empId = $this.data('row');
                $this.inventoryReport({
                    empId: empId
                });
            });
        }
    }
    /**
     * 明细显示
     * */
    var detailChartViewObj = {
        mapId: '#detailChartId',
        fullMapId: '#fullDetail',
        mapObj: null,
        fullMapObj: null,
        organId: null,
        xData: [],
        yData: [],
        list: null,
        empTabel: '#emp_tabel',
        init: function (organId) {
            var self = this;
            self.organId = organId;
            self.requestMapsAxis();
        },
        requestMapsAxis: function (flag) {
            var self = this;
            var param = {
                date: defaultData.date,
                viewType: defaultData.viewType,
            };
            $.post(urls.getMapsBaseInfoForTeamCPUrl, param, function (data) {
                self.xData = data.xAxisData;
                self.yData = data.yAxisData;
                if (flag) {
                    self.fullInitChart();
                } else {
                    self.initChart();
                }
            });
        },
        initChart: function () {
            var self = this;
            var w;
            if (self.xData.length == 1) {
                w = 600;
            } else {
                w = $(self.mapId).parent().width() - 30;
            }
            self.mapObj = $(self.mapId).talentMap({
                width: w,
                showNum: true,
                isSimple: true,
                xAxis: {
                    title: "时间",
                    data: self.xData
                },
                yAxis: {
                    title: defaultData.detailYTitle,
                    data: self.yData
                },
                //关闭背景颜色
                zAxis: false,
                point: {
                    /** 点添加到地图上后触发 */
                    afterCreate: function (elem, point) {
                    },
                    /** 地图上点的数量发生变化时触发 */
                    afterChange: function (points) {
//                        var mapObj = detailChartViewObj.mapObj;
//                        var teamPoints = mapObj.getZAxisPoints();
//                        var len = mapObj.getAllPointsNumber();
//                        self.renderAxis(teamPoints, len);
                    },
                    onClick: function (elem, point) {
                        if (undefined === point.remainNum) {
                            $(this).inventoryReport({
                                empId: point.empId
                            });
                            return;
                        }
                        var param = defaultBoxData;
                        param.organId = self.organId;
                        param.viewType = defaultData.viewType;
                        param.yLaberId = point.yLabelId;
                        param.yearMonths = point.xLabelId;
                        self.talentMapsModal(param, point.yLabel, point.xLabel);
                    }
                }
            });
            self.attachEvent(self.mapObj);
            self.requestData(self.mapObj);
        },
        talentMapsModal: function (param, yLabel, xLabel) {
            var self = this, $talentMapsModal = $('#talentMapsModal');
            $talentMapsModal.find('.modal-header-text').text(yLabel + '  ' + xLabel);
            $talentMapsModal.modal('show');
            if ($(self.empTabel).html() == '') {
                mapsEmpsTablesOption.postData = param;
                $(self.empTabel).cardTabel(mapsEmpsTablesOption);
            } else {
                $(self.empTabel).clearCardTableData().setCardTableParam({
                    postData: param
                }).reloadCardTable();
            }

        },
        attachEvent: function (mapObj) {
            var self = this;
            //显示/隐藏表格
            if ($(btnsObj.hiddenGridSpan + ' input').is(':checked')) {
            	$(self.mapId).addClass('u-map-hideGrids');
            } else {
            	$(self.mapId).removeClass('u-map-hideGrids');
            }
        },
        requestData: function (mapObj) {
            var self = this;
            var param = defaultBoxData;
            param.organId = self.organId;
            param.date = defaultData.date;
            param.viewType = defaultData.viewType;
            param.isFull = 0;
            $.post(urls.queryTeamCPDetailUrl, param, function (data) {
                if (mapObj) {
                    mapObj.clear();
                    if (!_.isEmpty(data.list)) mapObj.addSimplePoints(data.list);
                    self.resizeMap();
                }
            });
        },
        fullInit: function (organId) {
            var self = this;
            self.organId = organId;
            self.requestMapsAxis(true);
        },
        fullInitChart: function () {
            var self = this;
            var w;
            w = $(self.fullMapId).parent().width() - 30;
            self.fullMapObj = $(self.fullMapId).talentMap({
                width: w,
                showNum: true,
                xAxis: {
                    title: "时间",
                    data: self.xData
                },
                yAxis: {
                    title: defaultData.detailYTitle,
                    data: self.yData
                },
                //关闭背景颜色
                zAxis: false,
                point: {
                    /** 点添加到地图上后触发 */
                    afterCreate: function (elem, point) {
                    },
                    /** 地图上点的数量发生变化时触发 */
                    afterChange: function (points) {
                        var test = detailChartViewObj.fullMapObj.getZAxisPoints();
                    },
                    onClick: function (elem, point) {
                        $(this).inventoryReport({
                            empId: point.empId
                        });
                    }
                }
            });
            self.fullRequestData(self.fullMapObj);
        },
        fullRequestData: function (mapObj) {
            var self = this;
            var param = defaultBoxData;
            param.organId = self.organId;
            param.date = defaultData.date;
            param.viewType = defaultData.viewType;
            param.isFull = 1;
            $.post(urls.queryTeamCPDetailUrl, param, function (rs) {
                if (mapObj) {
                    mapObj.clear();

                    if (!_.isEmpty(rs.list)) mapObj.addPoints(rs.list);
                }
            });
        },
        resizeMap: function () {
            var self = this;
            var w = $(self.mapId).parent().width() - 30;
            if (w > 0 && !_.isNull(self.mapObj)) self.mapObj.resize(w);
            var fw = $(self.fullMapId).parent().width() - 30;
            if (fw > 0 && !_.isNull(self.fullMapObj)) self.fullMapObj.resize(fw);
        }
    }

    /***
     * 全屏下的搜索功能
     * @type {{show: boolean, isFirst: boolean, searchBtn: string, searchBlock: string, init: searchObj.init, requestData: searchObj.requestData, drawDislog: searchObj.drawDislog, drawEmpDislog: searchObj.drawEmpDislog, exit: searchObj.exit, resize: searchObj.resize}}
     */
    var searchObj = {
        show: false,
        isFirst: true,
        searchBtn: '#searchEmpBtn',
        searchBlock: '#searchEmpBlock',
        init: function () {
            var self = this;
            //解除绑定是为了防止取消全屏之后重复绑定事件
            $(self.searchBtn).unbind('click').click(function () {
                var $searchBlock = $(self.searchBlock);
                $searchBlock.height(0);
                var h = $searchBlock.parents('.row').height();
                var winh = document.body.clientHeight - 41;
                $searchBlock.height(h > winh ? h : winh);
                $searchBlock.fadeToggle('fast', 'linear');
                self.show = self.show ? false : true;

                if (!self.show) {
                    if (self.point) self.drawDislog(self.point, false);
                    return;
                }

                //第一次获取地图人员数据并渲染select2的值
                if (self.isFirst) self.requestData();

                var select2Obj = self.selectObj;
                if (undefined === select2Obj) return;
                select2Obj.unbind('select2:select').on('select2:select', function (evt) {
                    self.drawDislog(evt.params.data, true);
                });
                select2Obj.unbind('select2:unselect').on('select2:unselect', function (evt) {
                    $('#searchContent').empty();
                    $('#shade').hide();
                });
            });
            //隐藏搜索框操作
            $('#searchCloseBtn').unbind('click').click(function () {
                $(self.searchBtn).click();
            });
        },
        requestData: function () {
            var self = this, $selectEmp = $('#selectEmp'), mapsObj = detailChartViewObj.fullMapObj;
            if (!mapsObj) return;
            var allPoints = mapsObj.getAllPoints();
            if ($.isEmptyObject(allPoints)) return;
            
            var pointsData = [];
            $.each(allPoints, function (idx, item) {
                item.id = item.__id;
                var yearMonth = item.xLabel;
                var points = _.find(pointsData, function (obj) {
                    return obj.text == yearMonth;
                });
                if (_.isUndefined(points)) pointsData.push({text: yearMonth, children: [item]});
                else points.children.push(item);
            });
            pointsData = _.sortBy(pointsData, 'text').reverse();

            // 下拉菜单,先初始化
            $selectEmp.html('<option value="-1"></option>');
            self.selectObj = $selectEmp.select2({
                language: 'zh-CN',
                data: pointsData,
                placeholder: {
                    id: '-1',
                    text: '请输入名称'
                },
                allowClear: true
            });
            self.isFirst = false;
        },
        drawDislog: function (point, bool) {
            var self = this;
            var pointId = point.id;
            if (!bool) {
                $('#shade').hide();
                $('#' + pointId).removeAttr('style');
                return;
            }
            $('#shade').show();
            //假如之前有搜索过,隐藏之前的
            if (self.point) $('#' + self.point.id).removeAttr('style');

            $('#' + pointId).css({
                'zIndex': 200,
                'backgroundColor': '#fff'
            });
            var $searchContent = $('#searchContent');
            $searchContent.html(self.drawEmpDislog(point));
            $searchContent.find('button.btn').click(function () {
                $(this).inventoryReport({
                    empId: point.empId
                });
            });
            self.point = point;
        },
        drawEmpDislog: function (point) {
            var html = $('<div class="search-emp-main"></div>');

            html.append('<span class="col-xs-6 ct-container-fluid left">' + point.text + '</span>');
            html.append('<span class="col-xs-6 ct-col1 right"><button class="btn btn-primary" data-toggle="' + point.empId + '">盘点报告</button></span>');
            html.append('<span class="col-xs-6 ct-container-fluid left">' + point.xLabel + '</span>');
            html.append('<span class="col-xs-6 ct-container-fluid left">' + point.yLabel + '</span>');

            return html;
        },
        exit: function () {
        	var self = this, $searchContent = $('#searchContent');
            if (self.show) $(self.searchBtn).click();
            $searchContent.empty();
            self.isFirst = true;
        },
        resize: function () {
            var self = this;
            var $searchBlock = $(self.searchBlock);
            $searchBlock.height(0);
            var h = $searchBlock.parents('.row').height();
            var winh = document.body.clientHeight - 41;
            $searchBlock.height(h > winh ? h : winh);
        }
    }

    /**
     * 追加图片
     * */
    function getEmpDetailTpl(data) {
        var img = data.imgPath != '' ? data.imgPath : webRoot + "/assets/photo.jpg";
        var html = '<div class="pull-left text-center img-div-width"><div class="position-relative img-div-float">';
        html += '<img class="head-pic img-circle" src="' + img + '" data-src="' + img + '" data-id="' + data.empId + '" >';
        html += '</div><div class="emp-name"><span>' + data.userName + '</span><br><span>近两周加班</span><span class="emp-hours">' + data.conNum + '</span><span>h</span></div></div>';
        return html;
    }

    /**
     * 鼠标移动字体样式
     * */
    function btnCursorStyleFun(id) {
        $(id).hover(function () {
            $(this).addClass("font-color");
        }, function () {
            $(this).removeClass("font-color");
        });
    }

    /**
     * 搜索组件
     * */
    var searchBoxConfig = {
        url: urls.searchBoxUrl,
        lazy: true,
        attach: [],
        // 重写组件里的onClick事件
        onClick: function (obj) {
            var selectData = this.getSelectData();
            defaultBoxData.sequenceStr = selectData.sequenceId;
            defaultBoxData.sequenceSubStr = selectData.sequenceSubId;
            defaultBoxData.performanceStr = selectData.performanceKey;
            defaultBoxData.abilityStr = selectData.abilityId;
            defaultBoxData.ageStr = selectData.ageId;
            defaultBoxData.sexStr = selectData.sexId;
            defaultBoxData.eduStr = selectData.eduId;
            reqOrgId = defaultData.organId;
            reqOrgTxt = defaultData.organText;
            btnsObj.init(reqOrgId, true);
        },
        close: function (w, h) {
        },
        expand: function (w, h) {
        },
        loadComple: function (o) {
            var $component = $(o.$component), $sequenSubGroup = $component.find('div[name="sequenceSubId"]').parents('.form-group');
            $sequenSubGroup.addClass('hide');
            var $sequenGroup = $component.find('div[name="sequenceId"]').parents('.form-group');
            //主序列选项触发事件
            $sequenGroup.find('.childrenBtn').children().click(function () {
                var $this = $(this), bool = $this.hasClass('condition-click'), $condition = $this.siblings('.condition-click'), $sequenSub = $sequenSubGroup.find('.childrenBtn').children();
                //假如当前为选中,同级也有选中；或者当前为未选中，同级不止一个为选中 则隐藏子序列
                if ((bool && $condition.length != 0)
                    || (!bool && $condition.length != 1)) {

                    $sequenSubGroup.addClass('hide');
                    $sequenSub.removeClass('condition-click');
                    return;
                }
                //当前选择则取当前序列的ID，否则取同级选中的ID 渲染子序列
                var sequenceId = bool ? $this.attr('value') : $($condition[0]).attr('value');
                $sequenSub.addClass('hide');
                $sequenSubGroup.removeClass('hide');
                $.each($sequenSub, function (idx, item) {
                    if (sequenceId == $(item).data('bindObj')) $(item).removeClass('hide');
                });
            });
        }
    };
    var searchBoxObj = {
        searchBoxId: '#searchBox',
        init: function () {
            var self = this;
            $(self.searchBoxId).searchBox3(searchBoxConfig);
        }
    }

    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        reqOrgId = organId;
        reqOrgTxt = organTxt;
        btnsObj.init(reqOrgId);
//        timeLineObj.init(reqOrgId);
//        perBenefitOjb.init();
//        profitObj.init();
//        orgBenefitObj.init();
    };

    /**
     * 初始化echart对象
     */
    function initChart(targetId) {
        return echarts.init(document.getElementById(targetId));
    }

    /**
     * 清除echart面板
     * @param eChartObj
     */
    function clearEChart(eChartObj) {
        if (eChartObj) {
            eChartObj.clear();
        }
    }

    /**
     * 重置echart面板
     * @param eChartObj
     */
    function resizeEChart(eChartObj) {
        if (eChartObj) {
            eChartObj.resize();
        }
    }

    function hideChart(chartId, hide) {
        var $chart = $("#" + chartId);
        if (hide) {
            $chart.children('div.loadingmessage').remove();
            $chart.children().hide();
            $chart.append("<div class='loadingmessage'>暂无数据</div>");
        } else {
            $chart.children('div.loadingmessage').remove();
            $chart.children().show();
        }
    }

    function loadingChart(chartId) {
        var $chart = $("#" + chartId);
        $chart.children('div.loadingmessage').remove();
        $chart.children().hide();
        $chart.prepend("<div class='loadingmessage'>数据加载中</div>");
    }

    function changeData() {
        btnsObj.init(reqOrgId);
        searchBoxObj.init();
    }

    changeData();
    $(window).resize(function () {
        mapChartViewObj.chart.resize();
        detailChartViewObj.resizeMap();
        searchObj.resize();
    });
    function showErrMsg(content) {
        $._messengerDefaults = {
            extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top messenger-on-right'
        };
        Messenger().post({
            message: content,
            type: 'error',
            hideAfter: 3
        });
    }

});