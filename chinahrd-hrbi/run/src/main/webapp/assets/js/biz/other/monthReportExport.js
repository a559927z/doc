require(['jquery', 'jquery-ui', 'echarts', 'echarts/chart/bar', 'echarts/chart/line', 'echarts/chart/pie', 'echarts/chart/gauge',
    'bootstrap', 'jgGrid', 'underscore', 'utils', 'messenger', 'jquery-mCustomScrollBar'], function ($, jqueryui, echarts) {
    var webRoot = G_WEB_ROOT, win = top != window ? top.window : window;

    $(win.document.getElementById('tree')).next().show();
    if (win.setCurrNavStyle) win.setCurrNavStyle();
    var TextShape = require('zrender/shape/Text');
    $(".ct-mCustomScrollBar").mCustomScrollbar({
        axis: "yx",
        scrollButtons: {enable: true},
        scrollbarPosition: "outside",
        theme: "minimal-dark"
    });

    /**无数据时显示 暂无数据*/
    var showNoDataCharts = function (eObj) {
        eObj.clear();
        var zr = eObj.getZrender();
        zr.addShape(new TextShape({
            style: {
                x: (eObj.dom.clientWidth - 56) / 2,
                y: (eObj.dom.clientHeight - 20) / 2,
                color: '#ccc',
                text: '暂无数据',
                textFont: '14px 宋体'
            }
        }));
        zr.render();
    };
    
    var noDataOption = {
        text: '暂无数据',
        effect: 'bar',
        effectOption: {
            backgroundColor: "#fff",
            progress: 0
        },
        textStyle: {
            fontSize: 20,
            color: '#666'
        }
    }

    /** 数据加载中 */
    var showLoadingCharts = function (eObj) {
        var zr = eObj.getZrender();
        zr.addShape(new TextShape({
            style: {
                x: (eObj.dom.clientWidth - 56) / 2,
                y: (eObj.dom.clientHeight - 20) / 2,
                color: '#ccc',
                text: '数据加载中...',
                textFont: '14px 宋体'
            }
        }));
        zr.render();
    };

    /***
     * 初始化echart
     * @param domId
     * @returns {*}
     */
    function initEChart(domId) {
        return echarts.init(document.getElementById(domId));
    }

    function formatPieData(key, name, value) {
        if (!value) {
            return {};
        }
        //页面展示为 ：name，value
        return {
            value: value,
            key: key,
            name: (name + '\n' + Tc.formatNumber(value))
        };
    }

    function ShareFormatter(cellvalue) {
        return Tc.formatFloat(cellvalue) + '%';
    }

    function moneyFormatter(cellvalue) {
        if (_.isNull(cellvalue)) return 0;
        return Tc.formatFloat(cellvalue);
    }

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
    
    var reqOrgId = $('#organId').val(), reqOrgName = $('#organName').val();

    /***
     * 页面初始化对象
     */
    var pageSettingObj = {
        quotaId: null,
        yearMonth: null,
        init: function () {
            var self = this;
            self.quotaId = $('#quotaId').val();
            self.yearMonth = $('#yearMonth').val();
            self.setPageTitle();
        },
        setPageTitle: function () {
            var self = this, yearMonth = self.yearMonth;
            if (_.isNull(yearMonth)) return;
            var year = yearMonth.substr(0, 4);
            var month = parseInt(yearMonth.substr(4, 2));
            $('#pageTitle').text(reqOrgName + year + '年' + month + '月');
        }
    }
    pageSettingObj.init();
    
    
    /***
     * 人员结构分析
     */
    var budgetOption = {
        toolbox: {
            show: false
        },
        calculable: false,
        noDataLoadingOption: noDataOption,
        series: [
            {
                name: "编制",
                type: "gauge",
                data: [],
                min: 0,
                max: 100,
                axisLine: {
                    lineStyle: {
                        color: [[0.95, "#00b165"], [1, "#f09200"], [1, "#e7191b"]],
                        width: 10
                    },
                    show: true
                },
                title: {
                    show: false
                },
                detail: {
                    formatter: '{value}%',
                    textStyle: {
                        fontSize: 20
                    }
                },
                splitNumber: 5,
                pointer: {
                    length: "75%",
                    width: 5
                },
                radius: "95%",
                center: ["50%", "50%"],
                axisTick: {
                    show: true,
                    splitNumber: 2,
                    length: 4
                },
                splitLine: {
                    lineStyle: {
                        width: 1
                    },
                    show: true,
                    length: 10
                }
            }
        ]
    }
    var talentStructureObj = {
        budgetChartId: 'structureRateChart',
        managerTableId: '#managerTable',
        positionSequenceTableId: '#positionSequenceTable',
        color: ["green", "yellow", "red"],
        chartObj: null,
        data: {
            value: 0,
            greenValue: 0.95,
            yellowVlue: 1
        },
        init: function () {
            var self = this;
            self.getBudgetData();
            self.getManagerTableData();
        },
        getBudgetData: function () {
            var self = this, $Budget = $('#BudgetBlock');
            if (_.isNull(self.chartObj)) self.chartObj = initEChart(self.budgetChartId);

            showLoadingCharts(self.chartObj);
            var data = talentStructureDto;
//            $.post(urls.getBudgetAnalyseUrl, {organId: reqOrgId}, function (data) {
                if (data.hasBudgetPer) {
                    self.data.value = Tc.formatFloat(data.budgetPer * 100);
                    self.data.greenValue = Tc.formatFloat(data.normal * 100);
                    self.data.yellowVlue = Tc.formatFloat(data.risk * 100);
                    self.renderChart();
                } else {
                    showNoDataCharts(self.chartObj);
                }
                $("#budgetYear .number").text(data.hasBudgetPer ? data.number : "-");
                $("#budgetYear .empCount").text(data.empCount);
                $("#budgetYear .usableEmpCount").text(data.hasBudgetPer ? data.usableEmpCount : "-");
//            });
        },
        getManagerTableData: function () {
            var self = this;
            $(self.managerTableId).find('.loadingmessage').removeClass('hide');
            $(self.positionSequenceTableId).find('.loadingmessage').removeClass('hide');
            $(self.managerTableId).find('.table').addClass('hide');
            $(self.positionSequenceTableId).find('.table').addClass('hide');

            var data = getTalentStuctureByMonth;
//            $.post(urls.getTalentStuctureUrl, {"organId": reqOrgId}, function (data) {
                $(self.managerTableId).find('.loadingmessage').addClass('hide');
                $(self.positionSequenceTableId).find('.loadingmessage').addClass('hide');
                $(self.managerTableId).find('.table').removeClass('hide');
                $(self.positionSequenceTableId).find('.table').removeClass('hide');
                self.renderTable(data);
                self.positionSequence(data);
//            });
        },
        renderChart: function () {
            var self = this, data = self.data;
            //图表

            var maxValue = data.value > 100 ? parseInt(data.value / 10) * 10 + 10 : 100;
            budgetOption.series[0].data = [{value: data.value, name: "使用率"}];
            budgetOption.series[0].max = maxValue;
            var g = data.greenValue / maxValue;
            var y = data.yellowVlue / maxValue;
            if (g > 1) {
                data.greenValue = data.yellowVlue = 1;
            } else {
                data.greenValue = g;
                data.yellowVlue = y > 1 ? 1 : y;
            }
            budgetOption.series[0].axisLine.lineStyle.color = [[data.greenValue, "#00b165"], [data.yellowVlue, "#f09200"], [1, "#e7191b"]];
            self.chartObj.hideLoading();
            self.chartObj.clear();
            self.chartObj.setOption(budgetOption);

            //文字说明
            var index = 2;
            if (data.value <= data.greenValue * 100) {
                index = 0;
            } else if (data.value <= data.yellowVlue * 100) {
                index = 1;
            }
            var textArea = $('#structureRateText');
            textArea.removeClass(self.color[0]).removeClass(self.color[1]).removeClass(self.color[2]).addClass(self.color[index]);
            textArea.find(".rate").text(data.value + "%");
            // textArea.find(".text").text(self.text[index]);
        },
        renderTable: function (data) {
            var self = this;
            //数据表
            var manageTable = data.abilityEmpCount ? data.abilityEmpCount : [];
            var abilityTable = data.abilityCurtEmpCount ? data.abilityCurtEmpCount : [];
            var names = [];
            $.each(manageTable, function (n, v) {
                $.each(v, function (x, y) {
                    if (y !== 0) names.push(x);
                });
            });
            names = _.uniq(names);
            var lists = [];
            var managerTotal = 0, employeeTotal = 0;
            var objTotal = {'下级组织': '合计'};
            $.each(manageTable, function (n, v) {
                var obj = [];
                var manager = 0, employee = 0;
                obj.push(n);
                $.each(names, function (x, y) {
                    var num = v[y] == undefined ? 0 : v[y];
                    if (y == '员工') {
                        employee = num;
                        employeeTotal += num;
                    } else {
                        manager += num;
                        managerTotal += num;
                        obj.push(num);
                        objTotal[y] = (objTotal[y] == undefined ? 0 : objTotal[y]) + num;
                    }
                });
                obj.push(manager);
                obj.push(employee);
                var rate = "";
                if (manager > 0 && employee > 0) {
                    rate = "1:" + (employee / manager).toFixed(1);
                } else if (manager == 0 || employee == 0) {
                    rate = manager + ":" + employee;
                }
                obj.push(rate);
                lists.push(obj);
            });
            objTotal['管理者合计'] = managerTotal;
            objTotal['员工'] = employeeTotal;
            var rate = "";
            if (managerTotal > 0 && employeeTotal > 0) {
                rate = "1:" + (employeeTotal / managerTotal).toFixed(1);
            } else if (managerTotal == 0 || employeeTotal == 0) {
                rate = managerTotal + ":" + employeeTotal;
            }
            objTotal['管理者员工比例'] = rate;

            $.each(abilityTable, function (n, v) {
                $.each(v, function (x, y) {
                    if (y != 0)
                        names.push(x);
                });
            });
            names = _.uniq(names);
            var totals = 0;
            $.each(abilityTable, function (n, v) {
                var bool = false;
                var obj = _.find(lists, function (arr) {
                    return arr[0] == n;
                });
                if (!obj) {
                    obj = [];
                    bool = true;
                    obj.push(n);
                }
                var total = 0;

                $.each(names, function (x, y) {
                    if (v[y] == undefined) return true;
                    var num = v[y];
                    total += num;
                    totals += num;
                    obj.push(num);
                    objTotal[y] = (objTotal[y] == undefined ? 0 : objTotal[y]) + num;
                });
                obj.push(total);
                if (bool) lists.push(obj);
            });
            objTotal['合计'] = totals;

            //header
            var rowheader = ['<tr>'];
            $.each(objTotal, function (n, v) {
                rowheader.push('<th>' + n + '</th>');
            });
            $("#managerTable thead").html(rowheader.join(''));

            //body
            var rowTotal = [];
            $.each(objTotal, function (n, v) {
                rowTotal.push(v);
            });
            var rows = [rowTotal];
            $.each(lists, function (i, item) {
                rows.push(item)
            });
            var rowbody = [];
            $.each(rows, function (i, item) {
                rowbody.push("<tr>");
                $.each(item, function (j, value) {
                    rowbody.push('<td>' + value + '</td>');
                });
                rowbody.push("</tr>");
            });
            $("#managerTable tbody").html(rowbody.join(''));

            //样式
            $("#managerTable tbody tr").first().addClass("total");
            $("#managerTable tr").each(function (i, item) {
                $(item).find("th").first().addClass("textalignleft");
                $(item).find("td").first().addClass("textalignleft");
            });
        },
        //职位序列分布 序列分布
        positionSequence: function (data) {
            var self = this;

            var dataTable = data.seqEmpCount ? data.seqEmpCount : [];
            var names = [];
            $.each(dataTable, function (n, v) {
                $.each(v, function (x, y) {
                    names.push(x);
                });
            });
            names = _.uniq(names);
            var lists = [];
            var totals = 0;
            var objTotal = {'职级': '合计'};
            $.each(dataTable, function (n, v) {
                var obj = [], total = 0;
                obj.push(n);
                $.each(names, function (x, y) {
                    var num = v[y] == undefined ? 0 : v[y];
                    total += num;
                    totals += num;
                    obj.push(num);
                    objTotal[y] = (objTotal[y] == undefined ? 0 : objTotal[y]) + num;
                });
                obj.push(total);
                lists.push(obj);
            });
            objTotal['合计'] = totals;

            //header
            var rowheader = ['<tr>']
            $.each(objTotal, function (n, v) {
                rowheader.push('<th>' + n + '</th>');
            });
            $("#positionSequenceTable thead").html(rowheader.join(''));

            //body
            var rowbody = [];
            $.each(lists, function (i, item) {
                rowbody.push("<tr>");
                $.each(item, function (j, value) {
                    rowbody.push('<td>' + value + '</td>');
                });
                rowbody.push("</tr>");
            });
            $("#positionSequenceTable tbody").html(rowbody.join(''));

            $("#positionSequenceTable tr").each(function (i, item) {
                $(item).find("th").first().addClass("textalignleft");
                $(item).find("td").first().addClass("textalignleft");
            });
        },
    }
    talentStructureObj.init();
    
    /***
     * 人员流失分析
     * @type {{init: accordDismissObj.init}}
     */
    var changesOption = {       //异动统计option
        datatype: 'local',
        altRows: false,  //设置表格行的不同底色
        autowidth: true,
        height: 'auto',
        colNames: null,
        colModel: [
            {name: 'itemName', width: 'auto', sortable: false, align: 'center'},
            {name: 'entry', width: 'auto', sortable: false, align: 'center', formatter: changesFormatter},
            {name: 'transferIn', width: 'auto', fixed: true, sortable: false, align: 'center', formatter: changesFormatter},
            {
                name: 'transferOut',
                width: 65,
                fixed: true,
                sortable: false,
                align: 'center',
                formatter: changesFormatter
            },
            {name: 'dimission', width: 'auto', fixed: true, sortable: false, align: 'center', formatter: changesFormatter},
            {name: 'pureFlow', width: 'auto', fixed: true, sortable: false, align: 'center'}
        ],
        scroll: false,
        rowHeight: 36,
        styleUI: 'Bootstrap'
    };
    var dimissionOption = {       //离职员工option
    	datatype: 'local',
        altRows: false,  //设置表格行的不同底色
        autowidth: true,
        height: 'auto',
        colNames: ['姓名', '部门', '岗位', '序列', '职级', '司龄', '离职原因', '是否预警'],
        colModel: [
            {name: 'userNameCh', width: 'auto', sortable: false, align: 'center'},
            {name: 'organizationName', width: 'auto', sortable: false, align: 'center'},
            {name: 'positionName', width: 'auto', fixed: true, sortable: false, align: 'center'},
            {name: 'sequenceName', width: 'auto', fixed: true, sortable: false, align: 'center'},
            {name: 'rankName', width: 'auto', fixed: true, sortable: false, align: 'center'},
            {
                name: 'companyAge', width: 'auto', fixed: true, sortable: false, align: 'center',
                formatter: function (cellvalue, options, rowObject) {
                    var html = '', year = parseInt(cellvalue / 12), month = parseInt(cellvalue % 12);
                    if (year > 0)html += year + '年';
                    if (month > 0) html += month + '个月';
                    return html;
                }
            },
            {name: 'separationRiskName', width: 'auto', fixed: true, sortable: false, align: 'center'},
            {
                name: 'isWarn', width: 70, fixed: true, sortable: false, align: 'center',
                formatter: function (cellvalue, options, rowObject) {
                    return cellvalue == 0 ? '否' : '是';
                }
            }
        ],
        rownumbers: true,
        rownumWidth: 40,
        /*rowNum: 10,
        rowList: [10, 20, 30],
        pager: "#dimissionPaper",*/
        rowHeight: 36,
        styleUI: 'Bootstrap'
    };
    var accordDismissByOrganGridOption = {
    	datatype: 'local',
    	altRows: false,  //设置表格行的不同底色
        autowidth: true,
        height: 'auto',
        colNames: ['组织架构', '本年累计流失率', '本月流失率', '同比', '环比'],
        colModel: [
            {name: 'organName', width: 'auto', sortable: false, align: 'center'},
            {name: 'yearRate', width: 'auto', sortable: false, align: 'center'},
            {name: 'monthRate', width: 'auto', fixed: true, sortable: false, align: 'center'},
            {name: 'sameRatio', width: 'auto', fixed: true, sortable: false, align: 'center'},
            {name: 'basicRatio', width: 'auto', fixed: true, sortable: false, align: 'center'}
        ],
        scroll: false,
        rowHeight: 36,
        styleUI: 'Bootstrap'
    };
    var accordDismissChartOprion = {
        legend: {
            data: [],
            y: 'bottom',
            selectedMode: false
        },
        calculable: false,
        grid: {
            borderWidth: 0,
            x: 85,
            y: 45,
            x2: 50,
            y2: 70
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {type: 'none'}
        },
        xAxis: [{
            type: 'category',
            splitLine: false,
            axisLine: {
                show: true,
                onZero: false,
                lineStyle: {
                    color: '#D9D9D9'
                }
            },
            axisTick: {
                show: true,
                lineStyle: {
                    color: '#D9D9D9'
                }
            },
            axisLabel: {
//                    rotate: 30,
                show: true,
                itemStyle: {
                    color: '#BEBEBE'
                }
            },
            data: []
        }],
        yAxis: [{
            type: 'value',
            splitLine: false,
            axisLine: {
                show: false
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                show: true,
                formatter: function(data) {
                	return (data * 100) + '%';
                }
            }
        }],
        series: [{
            name: '',
            type: 'bar',
            clickable: false,
            yAxisIndex: 0,
            barCategoryGap: '45%',
            barMaxWidth: 43,
            itemStyle: {
                normal: {
                	color: '#0b98e0',
                    label: {
                        show: true,
                        formatter: function(data) {
                        	return Tc.formatFloat(data.value * 100) + '%';
                        },
                        textStyle: {
                            color: 'black'
                        }
                    }
                }
            },
            data: []
        }, {
            name: '',
            type: 'bar',
            clickable: false,
            yAxisIndex: 0,
            barCategoryGap: '55%',
            barMaxWidth: 43,
            itemStyle: {
                normal: {
                	color: '#EA711E',
                    label: {
                        show: true,
                        formatter: function(data) {
                        	return Tc.formatFloat(data.value * 100) + '%';
                        },
                        textStyle: {
                            color: 'black'
                        }
                    }
                }
            },
            data: []
        }]
    }
    var abnormalBarOption = {   //流失原因/流失去向柱状图option
        calculable: false,
        grid: {
            borderWidth: 0,
            x: 180,
            y: 10,
            y2: 0
        },
        xAxis: [{
            type: 'value',
            axisLabel: {show: false},
            splitLine: false,
            axisLine: {show: false},
            axisTick: {show: false}
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
            axisTick: {show: false},
            axisLabel: {
                show: true,
                textStyle: {
                    color: '#666'
                }
            },
            data: []
        }],
        series: [{
            name: '',
            barMaxWidth: 40,
            type: 'bar',
            clickable: false,
            itemStyle: {
                normal: {
//                    color: barColor[0],
                    label: {
                        show: true,
                        textStyle: {
                            color: 'black'
                        }
                    }
                }
            },
            barGap: 0,	//柱之类的距离
            barCategoryGap: '45%',		//分类柱之间的距离
            data: []
        }]
    };
    var dismissTypeEnum = {
        1: {name: '主动流失', color: '#0b98e0'},
        2: {name: '被动流失', color: '#00bda9'},
        3: {name: '其他', color: '#4573a7'}
    };


    function changesFormatter(cellvalue, options, rowObject) {
        if (cellvalue == 0) return cellvalue;
        var type = options.gid == 'changesOrganGrid' ? 2 : 1;
        var html = '<a href="javascript:void(0)" class="row-link" data-item="' + rowObject.itemId
            + '" data-pos="' + options.pos + '" data-type="' + type + '">' + cellvalue + '</a>';
        return html;
    }

    var empFlowObj = {
        abilityGridId: '#changesAbilityGrid',   //异动情况（按职级）
        organGridId: '#changesOrganGrid',       //异动情况（按组织）
        changesDetailsId: '#changesDetailsGrid',    //异动详情
        dimissionId: '#dimissionGrid',              //关键人才离职
        dimissionCauseId: 'dimissionCauseBar',       //离职原因
        dimissionWhereaboutsId: 'dimissionWhereaboutsBar',    //离职去向
        dimissionObj: null,
        dimissionCauseObj: null,
        dimissionWhereaboutsObj: null,
        isChangesFirst: true,
        init: function () {
            var self = this;
            var organId = reqOrgId;
            self.requestChangesToOrgan(organId);
            self.requestChangesToAbility(organId);
            self.initDimissionGrid(organId);
            self.initDismissRecord(organId);
            self.requestAccordDismissData(organId);
            self.getAccordDismissByYearMonth(organId);
        },
        requestChangesToOrgan: function (organId) {
            var self = this;
            var option = $.extend(true, {}, changesOption);
            option.colNames = ['下级组织', '入职', '调入', '调出', '离职', '净流动'];
            option.data = changesByOrgan;
            var obj = $(self.organGridId).jqGrid(option);
        },
        requestChangesToAbility: function (organId) {
            var self = this;
            var option = $.extend(true, {}, changesOption);
            option.data = changesByAbility;
            option.colNames = ['职级', '入职', '调入', '调出', '离职', '净流动'];
            var obj = $(self.abilityGridId).jqGrid(option);
        },
        renderChangesModal: function (type, pos, itemId) {  //异动弹窗
            var self = this, $changesDetailModal = $('#changesDetailModal');
            var currIdx = pos - 1 < 0 ? 0 : pos - 1;
            var $curr = $($changesDetailModal.find('.index-jxmb-btn').get(currIdx));
            $curr.siblings().removeClass('index-jxmb-btn-select');
            $curr.addClass('index-jxmb-btn-select');

            var params = self.redefineParams(type, pos, itemId);
            if (self.isChangesFirst) {    //有初始化则不需要再初始化
                changesDetailOption.postData = params;
                $(self.changesDetailsId).jqGrid(changesDetailOption);
                self.isChangesFirst = false;
            } else {
                $(self.changesDetailsId).clearGridData().setGridParam({
                    postData: params
                }).trigger("reloadGrid");
            }

            $changesDetailModal.modal('show');

            $changesDetailModal.find('.index-jxmb-btn').unbind('click').click(function () {
                var $this = $(this);

                $this.siblings().removeClass('index-jxmb-btn-select');
                $this.addClass('index-jxmb-btn-select');

                var idx = $this.index();
                var params = self.redefineParams(type, idx + 1, itemId);
                $(self.changesDetailsId).clearGridData().setGridParam({
                    postData: params
                }).trigger("reloadGrid");
                self.resizeChangesGrid();
            });
        },
        requestAccordDismissData: function(organId) {
    		var data = [];
    		$.each(getAccordDismissAnalysis, function(i, obj) {
    			var o = {
    				organId: obj.organId,
    				organName: obj.organName,
    				yearRate:  Tc.formatFloat(obj.yearRate * 100) + '%',
    				monthRate: Tc.formatFloat(obj.monthRate * 100) + '%',
    				sameRatio: Tc.formatFloat((obj.monthRate - obj.sameRatio) * 100) + '%',
    				basicRatio: Tc.formatFloat((obj.monthRate - obj.basicRatio) * 100) + '%'
    			}
    			data.push(o);
    		});
    		accordDismissByOrganGridOption.data = data;
			$('#accordDismissByOrganGrid').jqGrid(accordDismissByOrganGridOption);
			$('#accordDismissInYear').html(Tc.formatFloat(accordDismissInYear * 100) + '%');
        },
        getAccordDismissByYearMonth: function(organId) {
        	var self = this;
    		if(_.isNull(accordDismissByYearMonth)) return;
    		var thisYear = accordDismissByYearMonth.thisYear;
    		var lastYear = accordDismissByYearMonth.lastYear;
    		var seriesData1 = [];
    		var seriesData2 = [];
    		var nameArr = [];
    		var legendName = [];
    		var legendName1 = thisYear[0].yearMonth.toString().substr(0, 4) + '年';
    		var legendName2 = lastYear[0].yearMonth.toString().substr(0, 4) + '年';
    		legendName.push(legendName1);
    		legendName.push(legendName2);
    		$.each(lastYear, function(i, obj) {
    			var name = obj.yearMonth.toString().substr(4, 2) + '月';
    			nameArr.push(name);
    			var monthRate2 = obj.monthRate;
    			seriesData2.push(monthRate2);
    			if(thisYear[i] != undefined) {
    				var monthRate1 = thisYear[i].monthRate;
    				seriesData1.push(monthRate1);
    			} else {
    				seriesData1.push('-');
    			}
    		});
    		self.accordDismissObj = initEChart('dimissionByYearMonthBar');
    		accordDismissChartOprion.legend.data = legendName;
    		accordDismissChartOprion.xAxis[0].data = nameArr;
    		accordDismissChartOprion.series[0].data = seriesData1;
    		accordDismissChartOprion.series[0].name = legendName1;
    		accordDismissChartOprion.series[1].data = seriesData2;
    		accordDismissChartOprion.series[1].name = legendName2;
    		self.accordDismissObj.setOption(accordDismissChartOprion, true);
        },
        redefineParams: function (type, pos, itemId) {
            var self = this, organId = reqOrgId, yearMonth = pageSettingObj.yearMonth;
            if (type == 1) {
                var params = {
                    organId: organId,
                    type: type,
                    pos: pos,
                    yearMonth: yearMonth
                };
                if (itemId != 'total') params.itemId = itemId;
                return params;
            }
            var params = {
                organId: itemId == 'total' ? organId : itemId,
                type: type,
                pos: pos,
                yearMonth: yearMonth
            };
            return params;
        },
        resizeChangesGrid: function () {
            var self = this;
            $(self.changesDetailsId).setGridWidth($('#changesDetailsTable').width() * 0.98);
        },
        initDimissionGrid: function (organId) {
            var self = this, yearMonth = pageSettingObj.yearMonth;
//            var param = {organId: organId, yearMonth: yearMonth};
            if (!self.dimissionObj) {
//                dimissionOption.postData = param;
            	dimissionOption.data = getKeyTalentdimissionEmps;
                self.dimissionObj = $(self.dimissionId).jqGrid(dimissionOption);
                return;
            }
            self.dimissionObj.clearGridData().setGridParam({
            	data: getKeyTalentdimissionEmps
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        initDismissRecord: function (organId) {
            var self = this;
            self.requestDismissRecordData(organId);
        },
        requestDismissRecordData: function (organId) {
            var self = this, yearMonth = pageSettingObj.yearMonth;
            /*$.get(urls.dismissRecordUrl, {organizationId: organId, yearMonth: yearMonth}, function (data) {
                self.renderDismissRecord(data);
            });*/
        	self.renderDismissRecord(getDismissRecord);
        },
        renderDismissRecord: function (data) {
            var self = this;
            var dimissionCauseOption = $.extend(true, {}, abnormalBarOption);
            var dimissionWhereaboutsOption = $.extend(true, {}, abnormalBarOption);
            dimissionWhereaboutsOption.color = [dismissTypeEnum[1].color];
            var typeNameData = {}, whereAboutsData = {};
            //
            for (i in data) {
            	var item = data[i];
            	if(undefined != item.type) {
	            	self.setDataByKey(typeNameData, item.runOffName || '其他');
	                self.setDataByKey(whereAboutsData, item.whereAbouts);
	                //用于生成柱状图时，标识颜色
	                typeNameData[item.runOffName || '其他'].type = item.type;
            	}
            }
            /*$.each(data, function (i, item) {
                self.setDataByKey(typeNameData, item.runOffName || '其他');
                self.setDataByKey(whereAboutsData, item.whereAbouts);
                //用于生成柱状图时，标识颜色
                typeNameData[item.runOffName || '其他'].type = item.type;
            });*/
            self.dimissionCauseObj = initEChart(self.dimissionCauseId);
            self.dimissionWhereaboutsObj = initEChart(self.dimissionWhereaboutsId);
            //流失原因
            self.initBar(typeNameData, dimissionCauseOption, self.dimissionCauseObj, true);
            //流失去向
            self.initBar(whereAboutsData, dimissionWhereaboutsOption, self.dimissionWhereaboutsObj, false);
        },
        setDataByKey: function (obj, key) {
            if (!obj[key]) {
                obj[key] = {};
                obj[key].value = 1;
            } else {
                obj[key].value++;
            }
        },
        getItemColorStyle: function (type) {
            return {
                normal: {color: dismissTypeEnum[type].color}
            };
        },
        /**
         * 生成柱状图
         * @param data 数据
         * @param chartOption 图表的option
         * @param chartObj 图表对象
         * @param changeColor 是否需要改变柱子的颜色
         */
        initBar: function (data, chartOption, chartObj, changeColor) {
            var self = this;
            var category = [], valArr = [], list1 = [], list2 = [], list = [];
            $.each(data, function (i, item) {
                if (item.type == 1)
                    list1.push({"name": i, "type": item.type, "value": item.value});
                else if (item.type == 2)
                    list2.push({"name": i, "type": item.type, "value": item.value});
                else
                    list.push({"name": i, "type": item.type, "value": item.value});
            });
            var listsSort1 = _.sortBy(list1, function (o) {
                return o.value;
            });
            var listsSort2 = _.sortBy(list2, function (o) {
                return o.value;
            });
            var listsSort = _.sortBy(list, function (o) {
                return o.value;
            });
            var listsSort = _.union(listsSort2, listsSort1, listsSort);
            $.each(listsSort, function (i, item) {
                category.push(item.name);
                var newItem = {value: item.value};
                if (changeColor) {
                    newItem.itemStyle = self.getItemColorStyle(item.type);
                }
                valArr.push(newItem)
            });
            chartOption.yAxis[0].data = category;
            chartOption.series[0].data = valArr;
            chartObj.setOption(chartOption, true);
        },
        resizeChart: function () {
            var self = this;
            if (self.dimissionCauseObj) self.dimissionCauseObj.resize();
            if (self.dimissionWhereaboutsObj) self.dimissionWhereaboutsObj.resize();
        },
        resizeGrid: function () {
            var self = this;
            if (!_.isNull(self.dimissionObj))  self.dimissionObj.setGridWidth($("#dimissionTable").width());
        }
    }
    empFlowObj.init();
    
    /***
     * 人力成本分析
     */
    var manpowerCostPerOption = {
        legend: {
            x: "center",
            data: [],
            y: "bottom",
            orient: "horizontal",
            selectedMode: false
        },
        toolbox: {
            show: false,
            feature: {
                mark: {
                    show: true
                },
                dataView: {
                    show: true,
                    readOnly: true
                },
                restore: {
                    show: true
                },
                saveAsImage: {
                    show: true
                }
            }
        },
        series: [
            {
                name: "人力成本结构",
                type: "pie",
                radius: ["50%", "70%"],
                clickable: false,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            formatter: "{b}, {c},\n{d}%"
                        },
                        labelLine: {
                            show: true
                        }
                    },
                    emphasis: {
                        label: {
                            show: true,
                            position: "center",
                            textStyle: {
                                fontSize: "30",
                                fontWeight: "bold"
                            }
                        }
                    }
                },
                data: []
            }
        ],
        color: Tc.defaultPieColor
    };
    var manpowerTrendOption = {     //人力成本趋势发展option
        grid: {
            x: 55,
            y: 25,
            x2: 15,
            borderWidth: 0
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            y: 'bottom',
            data: ['人力成本', '利润', '销售额', '人均成本']
        },
        xAxis: [
            {
                type: "category",
                splitLine: {show: false},
                axisTick: false,
                data: [],
                axisLine: {
                    lineStyle: {
                        color: '#cecece',
                        width: 1
                    }
                },
                axisLabel: {
                    itemStyle: {
                        color: '#000000'
                    }, textStyle: {
                        color: '#000000',
                        fontSize: 12,
                    }
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '',
                splitLine: {
                    show: false
                },
                axisLine: {
                    lineStyle: {
                        color: '#cecece',
                        width: 1
                    }
                },
                axisLabel: {
                    formatter: '{value}'
                }
            }
        ],
        series: [
            {
                name: '人力成本',
                type: 'line',
                data: [],
                clickable: false,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            }
                        }
                    }
                }
            },
            {
                name: '利润',
                type: 'line',
                data: [],
                clickable: false,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            }
                        }
                    }
                }
            },
            {
                name: '销售额',
                type: 'line',
                data: [],
                clickable: false,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            }
                        }
                    }
                }
            },
            {
                name: '人均成本',
                type: 'line',
                data: [],
                clickable: false,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            }
                        }
                    }
                }
            },
        ]
    }
	//工资结构
    var salaryStructureOption = {
        legend: {
            data: [],
            y: 'bottom',
            selectedMode: true,
            textStyle: {color: '#555', fontFamily: '微软雅黑 verdana tahoma', fontSize: 13},
            itemWidth: 12,
            itemHeight: 12,
            itemGap: 12,
            padding: 15
        },
        toolbox: {
            show: false
        },
        calculable: false,
        series: [
            {
                name: "工资结构",
                clickable: false,
                type: "pie",
                radius: "60%",
                center: ["50%", "50%"],
                data: [],
                itemStyle: {
                    normal: {
                    	label: {
                            show: true,
                            formatter: "{b}, {c},\n{d}%"
                        },
                    },
                    emphasis: {
                        color: 'rgba(0,0,0,0)'
                    }
                }
            }
        ],
        title: {
            text: "",
            x: "left",
            y: "bottom",
            textStyle: {
                color: '#999',
                fontFamily: '微软雅黑 verdana tahoma',
                fontSize: 12,
                fontWeight: "normal"
            },
            padding: [0, 0, 0, 20]
        },
    }
    var manpowerCostInfoGridOption = {
    	datatype: 'local',
    	altRows: false,  //设置表格行的不同底色
        autowidth: true,
        height: 'auto',
        colNames: ['组织架构', '本年预算（万元）', '本年累计费用（万元）', '人力成本执行率', '本月费用（万元）', '同比', '环比', '人均成本（万元）'],
        colModel: [
            {name: 'organName', width: 'auto', sortable: false, align: 'center'},
            {name: 'sumYearBudget', width: 'auto', sortable: false, align: 'center'},
            {name: 'sumYearCost', width: 'auto', sortable: false, align: 'center'},
            {name: 'yearRate', width: 'auto', sortable: false, align: 'center'},
            {name: 'monthCost', width: 'auto', sortable: false, align: 'center'},
            {name: 'sameRatio', width: 'auto', sortable: false, align: 'center'},
            {name: 'basicRatio', width: 'auto', sortable: false, align: 'center'},
            {name: 'monthAvg', width: 'auto', sortable: false, align: 'center'}
        ],
        scroll: false,
        rowHeight: 36,
        styleUI: 'Bootstrap'
    };
    var manpowerCostObj = {
        structureId: 'contrastDetailPie', //结构ID
        trendId: 'manpowerTrendChart',                    //趋势ID
        structureObj: null,                 //结构饼图
        trendObj: null,
        salaryStructureChart: null,
        salaryStructureId: "salaryStructureChart",
        init: function (organId) {
            var self = this;
            self.requestManpowerCostInfo(organId);
            self.requestStructureData(organId);
            self.requestTrendData(organId);
            self.renderSalaryStructure();
        },
        requestManpowerCostInfo: function(organId) {
    		var data = [];
    		$.each(manpowerCostInfo, function(i, obj) {
    			var o = {
    				organName: obj.organName,
    				sumYearBudget: Tc.formatFloat(obj.sumYearBudget),
    				sumYearCost: Tc.formatFloat(obj.sumYearCost),
    				yearRate: Tc.formatFloat(obj.yearRate) + '%',
    				monthCost: Tc.formatFloat(obj.monthCost),
    				sameRatio: Tc.formatFloat(obj.sameRatio) + '%',
    				basicRatio: Tc.formatFloat(obj.basicRatio) + '%',
    				monthAvg: Tc.formatFloat(obj.monthAvg)
    			}
    			data.push(o);
    		});
    		manpowerCostInfoGridOption.data = data;
			$('#manpowerCostGrid').jqGrid(manpowerCostInfoGridOption);
        },
        requestStructureData: function (organId) {  //结构
            var self = this;
            if (_.isNull(self.structureObj)) self.structureObj = initEChart(self.structureId);

            showLoadingCharts(self.structureObj);
            /*$.get(urls.getItemDetailUrl, {'organId': organId}, function (rs) {
                if (!rs) {
                    showNoDataCharts(self.structureObj);
                    return;
                }
                self.renderStructure(rs);
            });*/
            self.renderStructure(queryItemDetail);
        },
        renderStructure: function (data) {
            var self = this;
            var legendData = [], seriesData = [];
            for(i in data) {
            	var item = data[i];
            	legendData.push({name: item.itemName, icon: 'bar'});
                seriesData.push({"value": item.cost, "name": item.itemName});
            }
            /*$.each(data, function (i, item) {
                legendData.push({name: item.itemName, icon: 'bar'});
                seriesData.push({"value": item.cost, "name": item.itemName});
            });*/
            var option = $.extend(true, {}, manpowerCostPerOption);
            option.legend.data = legendData;
            option.series[0].data = seriesData;
            self.structureObj.clear();
            self.structureObj.setOption(option, true);
        },
        requestTrendData: function (organId) {
            var self = this, year = pageSettingObj.yearMonth.substr(0, 4);
            if (_.isNull(self.trendObj)) self.trendObj = initEChart(self.trendId);
            showLoadingCharts(self.trendObj);
            /*$.get(urls.getAllDetailDataUrl, {'organId': organId, 'time': year}, function (rs) {
                if (_.isNull(rs) || _.isEmpty(rs)) {
                    showNoDataCharts(self.trendObj);
                    return;
                }
                self.renderTrend(rs);
            });*/
            self.renderTrend(getAllDetailData);

//            $.get(urls.getProportionYearUrl, {organId: organId, 'time': year}, function (result) {
            	var result = getProportionYear;
            	if (!result || _.isEmpty(result)) {
                    $("#manpowerOccupyAll").addClass("hide");
                    return;
                }
                $("#manpowerOccupyAll").removeClass("hide");
                var currObj = _.find(result, function (obj) {
                    return obj.type == 1;
                });
                if (!currObj) return;

                var occupyAllVal = Tc.formatFloat(currObj.cost / currObj.total * 100) + "%";
                $('#manpowerOccupyAllVal').text(occupyAllVal);
//            });
        },
        renderSalaryStructure: function () {
            var self = this;
            if (!getSalaryWageStructure.list || getSalaryWageStructure.list.length < 0) {
                return;
            }
            if (self.salaryStructureChart) {
                self.salaryStructureChart.clear();
            }
            self.salaryStructureChart = initEChart(self.salaryStructureId);
            var seriesData = [], legendData = [];
            $.each(getSalaryWageStructure.list, function (i, item) {
                seriesData.push({value: Tc.formatFloat(item.salaryValue), name: item.structureName});
                legendData.push({name: item.structureName, icon: 'bar'});
            });
            //option
            salaryStructureOption.legend.data = legendData;
            salaryStructureOption.series[0].data = seriesData;
            self.salaryStructureChart.setOption(salaryStructureOption);
            self.salaryStructureChart.refresh();
            self.salaryStructureChart.resize();
        },
        renderTrend: function (data) {
            var self = this;
            var xAxisData = [], seriesData1 = [], seriesData2 = [], seriesData3 = [], seriesData4 = [], all = [], max = 0;
            for(i in data) {
            	var item = data[i];
            	var month = parseInt((item.yearMonth + "").substring(4, 6)) + "月";
                xAxisData.push(month);
                seriesData1.push(Tc.formatFloat(item.cost));
                seriesData2.push(Tc.formatFloat(item.gainAmount));
                seriesData3.push(Tc.formatFloat(item.salesAmount));
                seriesData4.push(Tc.formatFloat(item.costAvg));

                all.push(Tc.formatFloat(item.cost));
                all.push(Tc.formatFloat(item.gainAmount));
                all.push(Tc.formatFloat(item.salesAmount));
                all.push(Tc.formatFloat(item.costAvg));
            }
            /*$.each(data, function (i, item) {
                var month = parseInt((item.yearMonth + "").substring(4, 6)) + "月";
                xAxisData.push(month);
                seriesData1.push(Tc.formatFloat(item.cost));
                seriesData2.push(Tc.formatFloat(item.gainAmount));
                seriesData3.push(Tc.formatFloat(item.salesAmount));
                seriesData4.push(Tc.formatFloat(item.costAvg));

                all.push(Tc.formatFloat(item.cost));
                all.push(Tc.formatFloat(item.gainAmount));
                all.push(Tc.formatFloat(item.salesAmount));
                all.push(Tc.formatFloat(item.costAvg));
            });*/
            max = _.max(all);
            max = parseInt(max / 100) * 100 + 100;
            manpowerTrendOption.yAxis[0].max = max;

            manpowerTrendOption.xAxis[0].data = xAxisData;
            manpowerTrendOption.series[0].data = seriesData1;
            manpowerTrendOption.series[1].data = seriesData2;
            manpowerTrendOption.series[2].data = seriesData3;
            manpowerTrendOption.series[3].data = seriesData4;

            self.trendObj.clear();
            self.trendObj.setOption(manpowerTrendOption);
        },
        resizeChart: function () {
            var self = this;
            if (self.structureObj) self.structureObj.resize();
            if (self.trendObj) self.trendObj.resize();
            if (self.salaryStructureChart) self.salaryStructureChart.resize();
        }
    }
    manpowerCostObj.init(reqOrgId);
    
    /***
     * 投入产出分析
     * @type {{init: perBenefitsObj.init}}
     */
    var organBenefitsGridOption = {     //机构人均效益option
        data: [],
        datatype: "local",
        autowidth: true,
        height: 'auto',
        colNames: ['机构', '人均效益(万元)'],
        colModel: [
            {name: 'organizationName', width: 'auto', sortable: false, align: 'center'},
            {name: 'benefitValue', width: 'auto', sortable: false, align: 'center'}
        ],
        scroll: false,
        rowHeight: 36,
        styleUI: 'Bootstrap'
    }
    var benefitsTrendOption = {     //人均效益趋势option
        title: {
            text: '集团人均效益趋势',
            x: 'center',
            y: 15
        },
        legend: {
            data: ['人均效益', '环比变化'],
            y: 'bottom',
            selectedMode: false
        },
        calculable: false,
        grid: {
            borderWidth: 0,
            x: 35,
            y: 45,
            x2: 50,
            y2: 70
        },
        color: ['#0b98e0', '#EA711E'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {type: 'none'}
        },
        xAxis: [{
            type: 'category',
            splitLine: false,
            axisLine: {
                show: true,
                onZero: false,
                lineStyle: {
                    color: '#D9D9D9'
                }
            },
            axisTick: {
                show: true,
                lineStyle: {
                    color: '#D9D9D9'
                }
            },
            axisLabel: {
                rotate: 30,
                show: true,
                itemStyle: {
                    color: '#BEBEBE'
                }
            },
            data: []
        }],
        yAxis: [{
            type: 'value',
            splitLine: false,
            axisLine: {
                show: false
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                show: true
            }
        }, {
            type: 'value',
            scale: true,
            splitLine: false,
            splitNumber: 4,
            axisLine: {
                show: false
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                show: true,
                formatter: '{value}%'
            }
        }],
        series: [{
            name: '人均效益',
            type: 'bar',
            clickable: false,
            yAxisIndex: 0,
            barCategoryGap: '45%',
            barMaxWidth: 43,
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        textStyle: {
                            color: 'black'
                        },
                        position: 'insideBottom'
                    }
                },
                emphasis: {
                    color: 'rgba(0,0,0,0)'
                }
            },
            data: []
        }, {
            name: '环比变化',
            type: 'line',
            clickable: false,
            yAxisIndex: 1,
            symbolSize: 2,
            symbol: 'circle',
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        textStyle: {
                            color: 'black'
                        }
                    }
                }
            },
            data: []
        }]
    }
    var organBringBackGridOption = {
        data: [],
        datatype: "local",
        autowidth: true,
        height: 'auto',
        colNames: ['机构', '人力资本投资回报率'],
        colModel: [
            {name: 'organizationName', width: 'auto', sortable: false, align: 'center'},
            {name: 'rateReturn', width: 'auto', sortable: false, align: 'center'}
        ],
        scroll: false,
        rowHeight: 36,
        styleUI: 'Bootstrap'
    }
    var salaryBringBackOption = {       //投资回报率option
        tooltip: {
            show: false
        },
        legend: {
            data: ["人力资本投资回报率"],
            y: "bottom",
            selectedMode: false
        },
        toolbox: {
            show: false,
            feature: {
                dataView: {
                    readOnly: true
                },
                magicType: {
                    type: ["line", "bar"],
                    show: false
                }
            }
        },
        calculable: false,
        xAxis: [
            {
                type: "category",
                boundaryGap: true,
                data: [],
                splitLine: {
                    show: false
                },
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: "rgb(204, 204, 204)",
                        width: 1
                    }
                },
                axisTick: {
                    length: 3,
                    lineStyle: {
                        color: "rgb(204, 204, 204)"
                    }
                },
                axisLabel: {
                    interval: 0,
                    rotate: 30
                }
            }
        ],
        yAxis: [
            {
                type: "value",
                axisLine: {
                    show: false
                },
                splitLine: {
                    show: false
                }
            }
        ],
        series: [
            {
                name: "人力资本投资回报率",
                type: "line",
                data: [],
                clickable: false,
                itemStyle: {
                    normal: {
                        label: {
                            show: true
                        }
                    }
                }
            }
        ],
        grid: {
            borderWidth: 0,
            x: 55,
            x2: 15,
            y: 25
        }
    }
    var investProduceObj = {
        organBenefitsId: 'organBenefitGrid',
        benefitsTrendId: 'benefitsTrendChart',
        organBringBackId: 'salaryBringBackGrid',
        bringBackChartId: 'salaryBringBackChart',
        organBenefitsObj: null,
        benefitsTrendObj: null,
        organBringBackObj: null,
        bringBackChartObj: null,
        init: function (organId) {
            var self = this;
            self.reuqestOrganBenefitsData(organId);
            self.reuqestSalaryBringBackData(organId);
        },
        reuqestOrganBenefitsData: function (organId) {
            var self = this;
            if (_.isNull(self.organBenefitsObj)) {
                self.organBenefitsObj = $("#" + self.organBenefitsId).jqGrid(organBenefitsGridOption);
            }
//            $.get(urls.orgBenefitUrl, {organizationId: organId}, function (data) {
            var data = getOrgBenefitData;
                if (data && !_.isEmpty(data)) {
                    self.isRenderChart = 1;
                    for(i=0; i<data.length; i++) {
                    	var item = data[i];
                    	item.benefitValue = Tc.formatFloat(item.benefitValue);
                    }
                    /*$.each(data, function (i, item) {
                        item.benefitValue = Tc.formatFloat(item.benefitValue);
                    });*/
                    self.organBenefitsObj.clearGridData().setGridParam({
                        data: data
                    }).trigger("reloadGrid");
                    self.resizeGrid();
                }
//            });
            
            if (_.isNull(self.organBringBackObj)) {
                self.organBringBackObj = $("#" + self.organBringBackId).jqGrid(organBringBackGridOption);
            }
            var yearMonth = $('#yearMonth').val();
//            $.get(urls.getSalaryOrganRateOfReturnUrl, {organId: organId, yearMonth: yearMonth}, function (data) {
            var data = getSalaryOrganRateOfReturn;
                if (data && !_.isEmpty(data)) {
                	for(i=0; i<data.length; i++) {
                		var item = data[i];
                		item.rateReturn = Tc.formatFloat(item.rateReturn);
                	}
                    /*$.each(data, function (i, item) {
                        item.rateReturn = Tc.formatFloat(item.rateReturn);
                    });*/
                    self.organBringBackObj.clearGridData().setGridParam({
                        data: data
                    }).trigger("reloadGrid");
                    self.organBringBackObj.setGridWidth($("#salaryBringBackTable").width());
                }
//            });

            if (_.isNull(self.benefitsTrendObj)) self.benefitsTrendObj = initEChart(self.benefitsTrendId);
            showLoadingCharts(self.benefitsTrendObj);
//            $.get(urls.benefitsTrendUrl, {organizationId: organId, type: 0}, function (data) {
            	var data = getTrendData;
                if (!data || _.isEmpty(data)) {
                    showNoDataCharts(self.benefitsTrendObj);
                    return;
                }
//                data.reverse();
            	data = self.arrReverse(data);
                var trendData = self.calculate(data);

                var option = self.getChartOption(trendData);
                self.benefitsTrendObj.clear();
                self.benefitsTrendObj.setOption(option);
                self.benefitsTrendObj.resize();
//            });
        },
        arrReverse: function(arr) {
        	var newArr = [];
        	for(i=0; i<arr.length; i++) {
        		newArr[i] = arr[arr.length-i-1];
        	}
        	return newArr;
        },
        getChartOption: function (trendData) {
            var self = this;
            var xAxisData = [];
            var benefitData = [];
            var changeData = [];
            for(i=0; i<trendData.length; i++) {
            	var o = trendData[i];
        		var yearMonthStr = o.yearMonth.substr(2, 2) + '/' + o.yearMonth.substr(4, 2);
                xAxisData.push(yearMonthStr);
                if (o.benefitValue == null) {
                    o.benefitValue = 0;
                }
                benefitData.push(Tc.formatFloat(o.benefitValue));
                changeData.push(o.changeValue >= 0 ? o.changeValue : self.getNagativeItem(o.changeValue));
            }
            /*$.each(trendData, function (i, o) {
                var yearMonthStr = o.yearMonth.substr(2, 2) + '/' + o.yearMonth.substr(4, 2);
                xAxisData.push(yearMonthStr);
                if (o.benefitValue == null) {
                    o.benefitValue = 0;
                }
                benefitData.push(Tc.formatFloat(o.benefitValue));
                changeData.push(o.changeValue >= 0 ? o.changeValue : self.getNagativeItem(o.changeValue));
            });*/

            var option = $.extend(true, {}, benefitsTrendOption);
            option.tooltip.formatter = function (params) {
                var html = "<div>" + params[0].name + "<br>人均效益：" + params[0].value + "万元<br>环比变化：" + params[1].value + "%<div>";
                return html;
            };
            option.series[1].itemStyle.normal.label.formatter = function (i) {
                return i.value + '%';
            };

            option.xAxis[0].data = xAxisData;
            option.series[0].data = benefitData;
            option.series[1].data = changeData;

            return option;
        },
        getNagativeItem: function (val) {
            return {
                value: val,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'red'
                            }
                        }
                    }
                }
            }
        },
        calculate: function (data) {
            // 上一个月/上一年 的数据（计算同比/环比 时用到）
            var preObj = null;
            var trendData = [];
            for(i=0; i<data.length; i++) {
            	var item = data[i];
            	// 将年月转为字符串
                item.yearMonth += "";
                if (i == 0) {
                    preObj = item;
                    item.changeValue = '-';
                    // 页面上最多展示12条数据，如果结果集为13条时，说明第一条结果集是用于计算同比/环比的，不需在图表上显示
                    if (data.length < 13) {
                        if (item.targetValue == null || item.targetValue == "null") {
                            item.targetValue = "";
                        }
                        if (item.targetValue == null || item.targetValue == "null" || item.targetValue == 0) {
                            if (item.targetValue == null || item.targetValue == "null") {
                                item.targetValue = "";
                            }
                            item.complianceRate = "100%";
                        } else {
                            item.complianceRate = Tc.formatFloat(item.benefitValue * 100 / item.targetValue) + "%";
                        }
                        trendData.push(item);
                    }
                } else {
	                // 同比/环比的值
	                //	alert(item.benefitValue - preObj.benefitValue);
	                if (preObj.benefitValue == null) {
	                    item.changeValue = 0;
	                } else {
	                    item.changeValue = Tc.formatFloat(((item.benefitValue - preObj.benefitValue) / preObj.benefitValue) * 100);
	                }
	
	                if (item.targetValue == null || item.targetValue == "null" || item.targetValue == 0) {
	                    if (item.targetValue == null || item.targetValue == "null") {
	                        item.targetValue = "";
	                    }
	                    item.complianceRate = "100%";
	                } else {
	                    item.complianceRate = Tc.formatFloat(item.benefitValue * 100 / item.targetValue) + "%";
	                }
	
	                trendData.push(item);
	                preObj = item;
                }
            }
            /*$.each(data, function (i, item) {
                // 将年月转为字符串
                item.yearMonth += "";
                if (i == 0) {
                    preObj = item;
                    item.changeValue = '-';
                    // 页面上最多展示12条数据，如果结果集为13条时，说名第一条结果集是用于计算同比/环比的，不需在图表上显示
                    if (data.length < 13) {
                        if (item.targetValue == null || item.targetValue == "null") {
                            item.targetValue = "";
                        }
                        if (item.targetValue == null || item.targetValue == "null" || item.targetValue == 0) {
                            if (item.targetValue == null || item.targetValue == "null") {
                                item.targetValue = "";
                            }
                            item.complianceRate = "100%";
                        } else {
                            item.complianceRate = Tc.formatFloat(item.benefitValue * 100 / item.targetValue) + "%";
                        }
                        trendData.push(item);
                    }
                    return true;
                }*/
                // 同比/环比的值
                //	alert(item.benefitValue - preObj.benefitValue);
                if (preObj.benefitValue == null) {
                    item.changeValue = 0;
                } else {
                    item.changeValue = Tc.formatFloat(((item.benefitValue - preObj.benefitValue) / preObj.benefitValue) * 100);
                }

                if (item.targetValue == null || item.targetValue == "null" || item.targetValue == 0) {
                    if (item.targetValue == null || item.targetValue == "null") {
                        item.targetValue = "";
                    }
                    item.complianceRate = "100%";
                } else {
                    item.complianceRate = Tc.formatFloat(item.benefitValue * 100 / item.targetValue) + "%";
                }

                trendData.push(item);
                preObj = item;
            return trendData;
        },
        reuqestSalaryBringBackData: function (organId) {
            var self = this;

            if (_.isNull(self.bringBackChartObj)) self.bringBackChartObj = initEChart(self.bringBackChartId);
            showLoadingCharts(self.bringBackChartObj);
//            $.get(urls.getSalaryMonthRateOfReturnUrl, {organId: organId}, function (data) {
            var data = getSalaryMonthRateOfReturn;
                if (!data || _.isEmpty(data)) {
                    showNoDataCharts(self.bringBackChartObj);
                    return;
                }

                var years = [], seriesData = [];
                for(i in data) {
                	if(i < data.length) {
                		var item = data[i];
                    	years.push(item.yearMonth);
                        seriesData.push(Tc.formatFloat(item.rateReturn));
                	}
                }
                /*$.each(data, function (i, item) {
                    years.push(item.yearMonth);
                    seriesData.push(Tc.formatFloat(item.rateReturn));
                });*/
                var option = $.extend(true, {}, salaryBringBackOption);
                //option
                option.xAxis[0].data = years;
                option.series[0].data = seriesData;//投资回报率
                self.bringBackChartObj.clear();
                self.bringBackChartObj.setOption(option, true);
                self.bringBackChartObj.resize();
//            });
        },
        resizeGrid: function () {
            var self = this;
            if (!_.isNull(self.organBenefitsObj))  self.organBenefitsObj.setGridWidth($("#organBenefitTable").width());
        },
        resizeChart: function () {
            var self = this;
            if (!_.isNull(self.benefitsTrendObj)) self.benefitsTrendObj.resize();
            if (!_.isNull(self.bringBackChartObj)) self.bringBackChartObj.resize();
        }
    }
    investProduceObj.init(reqOrgId);

    /***
     * 劳动力效能分析
     * @type {{}}
     */
    var laborGridOption = {     //劳动力效能下级组织对比grid
        data: [],
        datatype: "local",
        altRows: true,//设置表格行的不同底色
        autowidth: true,
        height: 'auto',//268
        colNames: ['', '组织架构', '应出勤时间', '实际出勤时间（不含加班）', '劳动力效能', '加班时间', '人均加班时长'],
        colModel: [
            {name: 'organId', hidden: true, sortable: false},
            {name: 'organName', index: 'organName', width: 'auto', sortable: false, align: 'center'},
            {name: 'beInAttendance', index: 'beInAttendance', width: 'auto', sortable: false, align: 'center'},
            {name: 'actualAttendance', index: 'actualAttendance', width: 'auto', sortable: false, align: 'center'},
            {
                name: 'attendanceRate', index: 'attendanceRate', width: 'auto', sortable: false, align: 'center',
                formatter: function (value) {
                    if (value == "") return 0;
                    return Tc.formatFloat(value * 100) + "%";
                }
            },
            {
                name: 'overTime', index: 'overTime', width: 'auto', sortable: false, align: 'center',
                formatter: function (value) {
                    if (value == "") return 0;
                    return Tc.formatFloat(value);
                }
            },
            {name: 'count', index: 'count', width: 'auto', sortable: false, align: 'center'}
        ],
        scroll: false,
        rowHeight: 36,
        styleUI: 'Bootstrap'
    };

    var laborEfficiencyObj = {
        loborGridId: '#laborRatioGrid',
        loborGridObj: null,
        init: function (organId) {
            var self = this;
            self.loborGridObj = $(self.loborGridId).jqGrid(laborGridOption);
            self.requestData(organId);
        },
        requestData: function (organId) {
            var self = this, yearMonth = pageSettingObj.yearMonth, loborGridObj = self.loborGridObj;
            var param = {
                organId: organId,
                beginTime: yearMonth,
                endTime: yearMonth
            }
//            $.get(urls.getLaborEfficiencyRatioUrl, param, function (data) {
                var result = getLaborEfficiencyRatio.listGrid;
                loborGridObj.clearGridData().setGridParam({
                    data: result
                }).trigger("reloadGrid");
                self.resizeGrid();
//            });

//            $.post(urls.getOvertimeByOrganUrl, param, function (data) {
                if (!queryOvertimeByOrgan || _.isEmpty(queryOvertimeByOrgan)) return;
                self.renderAvgData(organId, queryOvertimeByOrgan, loborGridObj);
//            });
        },
        renderAvgData: function (currOrgan, data, gridObj) {   //确保grid已有数据
            var self = this;
            var newTime = setTimeout(function () {
                var rowData = gridObj.jqGrid('getRowData');
                if (_.isEmpty(rowData)) {
                    self.renderAvgData(currOrgan, data, gridObj);
                    return;
                }
                for(i in rowData) {
                	var obj = rowData[i];
                	obj.attendanceRate = parseInt(obj.attendanceRate) / 100;
                    if (obj.organId == currOrgan) {
                        obj.count = Tc.formatFloat(data.avgNum);
                        obj.overTime = data.conNum;
                        return true;
                    }
                    var item = _.find(data.avgList, function (item) {
                        return item.organId == obj.organId;
                    });
                    if (_.isEmpty(item)) return true;
                    obj.overTime = item.hourCount;
                    obj.count = Tc.formatFloat(item.avgNum);
                }
                /*$.each(rowData, function (i, obj) {
                    obj.attendanceRate = parseInt(obj.attendanceRate) / 100;
                    if (obj.organId == currOrgan) {
                        obj.count = Tc.formatFloat(data.avgNum);
                        obj.overTime = data.conNum;
                        return true;
                    }
                    var item = _.find(data.avgList, function (item) {
                        return item.organId == obj.organId;
                    });
                    if (_.isEmpty(item)) return true;
                    obj.overTime = item.hourCount;
                    obj.count = Tc.formatFloat(item.avgNum);
                });*/
                gridObj.clearGridData().setGridParam({
                    data: rowData
                }).trigger("reloadGrid");
                self.resizeGrid();
                clearTimeout(newTime);
            }, 100);
        },
        resizeGrid: function () {
            var self = this;
            if (self.loborGridObj) self.loborGridObj.setGridWidth($('#laborRatioTable').width());
        }
    }
    laborEfficiencyObj.init(reqOrgId);
    
    /**
     * 招聘分析
     * @type {{}}
     */
    var postMeetRateOption = {     //岗位需求分析option
        data: [],
        datatype: "local",
        altRows: true,//设置表格行的不同底色
        autowidth: true,
        height: 'auto',//268
        colNames: ['组织架构', '需求岗位', '需求人数', '简历数', '面试数', '录用数', '入职数', '招聘完成率'],
        colModel: [
            {name: 'organName', width: 'auto', sortable: false, align: 'center'},
            {name: 'positionName', width: 'auto', sortable: false, align: 'center'},
            {name: 'planNum', width: 'auto', sortable: false, align: 'center'},
            {name: 'resumeNum', width: 'auto', sortable: false, align: 'center'},
            {name: 'interviewNum', width: 'auto', sortable: false, align: 'center'},
            {name: 'offerNum', width: 'auto', sortable: false, align: 'center'},
            {name: 'entryNum', width: 'auto', sortable: false, align: 'center'},
            {
                name: 'meetRate', width: 'auto', sortable: false, align: 'center',
                formatter: function (value) {
                    if (value == "") return '0%';
                    return Tc.formatFloat(value * 100) + '%';
                }
            }
        ],
        scroll: false,
        rowHeight: 36,
        styleUI: 'Bootstrap'
    };
    var trenchStatisticsOption = {      //渠道分析option
        hoverrows: false,
        viewrecords: false,
        gridview: true,
        height: "auto",
        loadonce: true,
        rowNum: -1,
        pager: "false",
        scroll: false,
        scrollrows: true,
        treeGrid: true,
        ExpandColumn: "name",
        treedatatype: "json",
        treeGridModel: "adjacency",
        treeReader: {
            level_field: "level",
            parent_id_field: "parent",
            leaf_field: "isLeaf",
            expanded_field: "expanded"
        },
        datatype: "local",
        colModel: [
            {"name": "channelName", "sorttype": "string", "label": "招聘渠道", "align": "left", "sortable": false},
            {"name": "employNum", "sorttype": "numeric", "label": "已招人数", "align": "center", "sortable": false},
            {"name": "dimissionRate", "label": "试用期离职率", "align": "center", "sortable": false},
            {"name": "days", "label": "招聘周期（天）", "align": "center", "sortable": false},
            {
                "name": "outlay", "index": "outlay", "label": "人均招聘费用（元）", "align": "center", "sortable": false,
                "formatter": function (cellvalue) {
                    return Tc.formatFloat(cellvalue);
                }
            },
            {"name": "parent", "index": "parent", "hidden": true}
        ],
        rowHeight: 36,
        styleUI: 'Bootstrap'
    }
    var recruitObj = {
        channelGridId: 'channelGrid',
        demandGridId: 'demandGrid',
        channelGridObj: null,
        demandGridObj: null,
        init: function (organId) {
            var self = this;
            if (_.isNull(self.demandGridObj)) {
                self.demandGridObj = $("#" + self.demandGridId).jqGrid(postMeetRateOption);
            }
            self.requestDemandData(organId);
            self.renderChannelGrid(organId);
            self.renderEvent(organId);
        },
        requestDemandData: function (organId) {
            var self = this, yearMonth = pageSettingObj.yearMonth;
            var param = {
                organId: organId
            }
            if (_.isEmpty(getPositionMeetRate)) return;
            self.demandGridObj.clearGridData().setGridParam({
                data: getPositionMeetRate
            }).trigger("reloadGrid");
            self.resizeGrid();
        },
        renderChannelGrid: function (organId) {
            var self = this;
//            var keyName = $("#channelTxt").val();
            $("#channelTable").html('<table id="' + self.channelGridId + '"></table>');
            trenchStatisticsOption.height = 305;
            trenchStatisticsOption.data = getRecruitChannel;
            self.channelGridObj = $("#" + self.channelGridId).jqGrid(trenchStatisticsOption);
            self.organId = reqOrgId;
            self.resizeGrid();
        },
        renderEvent: function (organId) {
            var self = this;
            $("#channelBtn").unbind("click").click(function () {
                self.renderChannelGrid(organId);
            });
        },
        resizeGrid: function () {
            var self = this;
            if (self.channelGridObj && $("#channelTable:visible").length > 0) {
                self.channelGridObj.setGridWidth($("#channelTable").width());
            }
            if (self.demandGridObj && $("#demandTable:visible").length > 0) {
                self.demandGridObj.setGridWidth($("#demandTable").width());
            }
        },
    }
    recruitObj.init(reqOrgId);
    
    /**
     * 培训分析
     * @type {{data: Array, datatype: string, altRows: boolean, autowidth: boolean, height: number, colNames: string[], colModel: *[], scroll: boolean}}
     */
    var trainGeneralOption = {     //培训概况option
        data: [],
        datatype: "local",
        altRows: true,//设置表格行的不同底色
        autowidth: true,
        height: 'auto',//268
        colNames: ['', '组织架构', '年度培训预算（万元）', '培训费用（万元）', '培训覆盖率', '培训人次', '培训讲师数', '人均学时（小时）'],
        colModel: [
            {name: 'organId', hidden: true, sortable: false},
            {name: 'organName', width: 'auto', sortable: false, align: 'center'},
            {name: 'budgetValue', width: 'auto', sortable: false, align: 'center', formatter: moneyFormatter},
            {name: 'outlays', width: 'auto', sortable: false, align: 'center', formatter: moneyFormatter},
            {name: 'coverageRate', width: 'auto', sortable: false, align: 'center', formatter: ShareFormatter},
            {name: 'frequency', width: 'auto', sortable: false, align: 'center'},
            {name: 'lecturerNum', width: 'auto', sortable: false, align: 'center'},
            {name: 'hours', width: 'auto', sortable: false, align: 'center'}
        ],
        scroll: false,
        rowHeight: 36,
        styleUI: 'Bootstrap'
    };

    var trainTypeNumOption = {  //培训类型分析option
        color: Tc.defaultPieColor,
        calculable: false,
        series: [{
            type: 'pie',
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
            radius: '60%',
            data: []
        }]
    };
    var trainTypeGridOption = {     //培训类型表格展示option
    	datatype: "local",
        altRows: true,//设置表格行的不同底色
        //altclass:'ui-priority-secondary',//用来指定行显示的css，可以编辑自己的css文件，只有当altRows设为 ture时起作用
        autowidth: true,
        height: 'auto',
        colNames: ['培训课程', '培训时间', '状态', '参培人数', '覆盖率', '主办方'],
        colModel: [
            {name: 'courseName', width: 'auto', sortable: false, align: 'left'},
            {
                name: 'startDate', width: 'auto', sortable: false, align: 'left',
                formatter: function (value, options, row) {
                    if (!value) {
                        return "";
                    }
                    var newDate = new Date(), newEndDate = new Date();
                	newDate.setTime(value);
                	if(row.endDate != null) {
                		newEndDate.setTime(row.endDate);
                	}
                    return '开始:' + newDate.toLocaleDateString() + '&nbsp;&nbsp;<br>结束:' + (row.endDate == null ? '-' : newEndDate.toLocaleDateString());
                }
            },
            {
                name: 'status', width: 'auto', fixed: true, sortable: false, align: 'center',
                formatter: function (value, options, row) {
                    if (_.isEmpty(value)) {
                        return "";
                    }
                    return value == 1 ? '已完成' : '进行中';
                }
            },
            {name: 'trainNum', width: 'auto', fixed: true, sortable: false, align: 'center'},
            {name: 'coverageRate', width: 'auto', fixed: true, sortable: false, align: 'center', formatter: ShareFormatter},
            {name: 'trainUnit', width: 'auto', fixed: true, sortable: false, align: 'center'}
        ],
        rownumbers: true,
        rownumWidth: 38,
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: "#trainTypePager",
        rowHeight: 36,
        styleUI: 'Bootstrap'
    };
    var trainObj = {
        generalGridId: 'trainGeneralGrid',
        typeChartId: 'trainTypeNumChart',
        typeGridId: '#trainTypeGrid',

        generalGridObj: null,
        typeChartObj: null,
        typeGridObj: null,
        init: function (organId) {
            var self = this;
            self.generalGridObj = $('#' + self.generalGridId).jqGrid(trainGeneralOption);
            self.requestData(organId);
            if (self.organId != organId) {
                self.requestTypeData(organId);
            }
            self.resizeChart();
        },
        requestData: function (organId) {
            var self = this, yearMonth = pageSettingObj.yearMonth + '', generalGridObj = self.generalGridObj;
            var year = yearMonth.substr(0, 4);
            var param = {
                organId: organId,
                year: year
            }
//            $.post(urls.getTrainGeneralUrl, param, function (result) {
                generalGridObj.clearGridData().setGridParam({
                    data: getTrainGeneral
                }).trigger("reloadGrid");
                self.resizeGrid();
//            });

            /*$.get(urls.getSubOrganizationCoverUrl, {organId: organId}, function (data) {
                if (!data || _.isEmpty(data)) return;
                self.renderCoverageData(organId, data, generalGridObj);
            });*/
            self.renderCoverageData(organId, getSubOrganizationCover, generalGridObj);
        },
        renderCoverageData: function (currOrganId, data, gridObj) {   //确保grid已有数据
            var self = this;
            var coverageTime = setTimeout(function () {
                var rowData = gridObj.jqGrid('getRowData');
                if (_.isEmpty(rowData)) {
                    self.renderCoverageData(currOrganId, data, gridObj);
                    return;
                }
                for(i in rowData) {
                	var obj = rowData[i];
                	if (obj.organId == currOrganId) {
                        obj.coverageRate = data.companyCover * 100;
                        return true;
                    }
                    var item = _.find(data.list, function (item) {
                        return item.organizationId == obj.organId;
                    });
                    if (_.isEmpty(item)) return true;
                    obj.coverageRate = item.coverageRate * 100;
                }
                /*$.each(rowData, function (i, obj) {
                    if (obj.organId == currOrganId) {
                        obj.coverageRate = data.companyCover * 100;
                        return true;
                    }
                    var item = _.find(data.list, function (item) {
                        return item.organizationId == obj.organId;
                    });
                    if (_.isEmpty(item)) return true;
                    obj.coverageRate = item.coverageRate * 100;
                });*/
                self.gridData = rowData;
                gridObj.clearGridData().setGridParam({
                    data: rowData
                }).trigger("reloadGrid");
                self.resizeGrid();
                clearTimeout(coverageTime);
            }, 100);
        },
        requestTypeData: function (organId) {
            var self = this;
            if (_.isNull(self.typeChartObj)) self.typeChartObj = initEChart(self.typeChartId);
            showLoadingCharts(self.typeChartObj);
            /*$.get(urls.getTrainingTypeUrl, {organId: organId}, function (result) {
                self.organId = organId;
                if (_.isEmpty(result)) {
                    showNoDataCharts(self.typeChartObj);
                    return;
                }
                self.renderTypeChart(result.list);
            });*/
            self.renderTypeChart(getTrainingType.list);
        },
        renderTypeChart: function (data) {
            var self = this;
            var seriesData = [], source = [];
            _.each(data, function (obj, idx) {
                if (idx == 0)  self.fisrtType = obj.courseTypeKey;
                if (obj.frequency == '-' || obj.frequency <= 0)  return true;
                source.push(obj);
                seriesData.push(formatPieData(obj.courseTypeKey, obj.courseTypeName, obj.frequency));
            });
            if (seriesData.length == 0) {
                showNoDataCharts(self.typeChartObj);
                return;
            }
            self.typeChartObj.clear();
            trainTypeNumOption.series[0].data = seriesData;
            self.initLengend(self.typeChartObj, data, trainTypeNumOption.color);
            self.typeChartObj.setOption(trainTypeNumOption, true);
            self.typeChartObj.on('click', function (e) {
                trainObj.initGrid(self.organId, e.data.key);
            });
            self.initGrid(self.organId);
        },
        //因为图表中没有图例，故用zrender画一个
        initLengend: function (_chartObj, data, colors) {
            var self = this;
            var _ZR = _chartObj.getZrender();
            var len = data.length, ids = 0;
            _.each(data, function (obj, i) {
                if (obj.frequency == '-' || obj.frequency <= 0) {
                    return true;
                }
                _ZR.addShape(new TextShape({
                    style: {
                        x: 0,
                        y: 10 + ids * 19,
                        color: colors[ids],
                        text: '▅',
                        textAlign: 'left',
                        textFont: 'bolder 17px 微软雅黑'
                    },
                    hoverable: false
                }));
                _ZR.addShape(new TextShape({
                    style: {
                        x: 21,
                        y: 13 + (ids * 19),
                        color: '#000',
                        text: obj.courseTypeName,
                        textAlign: 'left',
                        textBaseline: 'middle',
                        textFont: 'border 15px 微软雅黑'
                    },
                    hoverable: false
                }));
                ids++;
            });
            _ZR.refresh();
        },
        initGrid: function (organId, type) {
            var self = this;
//            var selectType = type ? type : self.fisrtType;
//            if (self.gridOrganId == organId) {
//                self.resizeGrid(organId, selectType);
//                return true;
//            }
//            trainTypeGridOption.postData = {'type': selectType, 'organId': organId};
            trainTypeGridOption.data = findTrainingTypeRecord;
            self.typeGridObj = $(self.typeGridId).jqGrid(trainTypeGridOption);
//            self.gridOrganId = organId;
//            self.selectType = selectType;
        },
        resize: function () {
            var self = this;
            self.resizeChart();
            self.resizeGrid();
        },
        resizeGrid: function () {
            var self = this;
            if (self.generalGridObj) self.generalGridObj.setGridWidth($('#trainGeneralTable').width());
            if (self.typeGridObj) self.typeGridObj.setGridWidth($('#trainTypeTable').width());
        },
        resizeChart: function () {
            var self = this;
            if (self.typeChartObj && $('#' + self.typeChartId).find('div.loadingmessage').length == 0) {
                self.typeChartObj.resize();
            }
        }
    }
    trainObj.init(reqOrgId);
    
    /**
     * 绩效分析
     * @type {{}}
     */
    var perChangeGridOption = {     //绩效概况option
        data: [],
        datatype: "local",
        altRows: true,//设置表格行的不同底色
        autowidth: true,
        height: 'auto',//268
        colNames: ['', '组织架构', '总人数', '绩效参评人数', '绩效考核覆盖率'],
        colModel: [
            {name: 'organId', hidden: true, sortable: false},
            {name: 'organName', width: 'auto', sortable: false, align: 'center'},
            {name: 'count', width: 'auto', sortable: false, align: 'center'},
            {name: 'perCount', width: 'auto', sortable: false, align: 'center'},
            {name: 'rate', width: 'auto', sortable: false, align: 'center',
            	formatter: function (cellvalue, options, rowObject) {
                    return Tc.formatFloat(cellvalue*100) + '%';
                }
            },
        ],
        scroll: false,
        rowHeight: 36,
        styleUI: 'Bootstrap'
    };
    var preChangeOption = {
        gap: 4.5, //间隔
        jianjiao: 16.5, //顶部和底部尖角
        width: 300,
        height: 220,
        x: 10.5,//x轴的起点
        topColor: 'rgb(92,200,43)',
        middleColor: 'rgb(41,140,208)',
        bottomColor: 'rgb(210,76,0)',
        leftFontStyle: {
            fontSize: 22,
            fontColor: '#f0f0f0',
            textAlign: 'center',
            textBaseline: 'middle'
        },
        rigthFontStyle: {
            fontSize: 13,
            fontColor: '#000000',
            lineColor: '#333333',
            textAlign: 'left',
            textBaseline: 'middle'
        },
        splitLineW: 40
    };

    //绩效结果变化趋势HTML5绘制图形
    var PreChange = function (mainId, obj, option) {
        this.mainId = mainId;
        this.obj = obj;
        this.option = option;
        this.middleY = this.option.width / 2;
    };

    //创建画布
    PreChange.prototype.createCanvas = function () {
        //清空div
        $('#' + this.mainId).html('');

        var canvas = document.createElement('canvas');
        $('#' + this.mainId).append(canvas);
        this.setCanvasPos(canvas);
        return canvas.getContext('2d');
    };

    //设置画布样式
    PreChange.prototype.setCanvasPos = function (canvas) {
        canvas.setAttribute('width', this.option.width);
        canvas.setAttribute('height', this.option.height);
        // 减少reflow
//			var canvasStyle = 'width:' + this.option.width + 'px;height:' + this.option.height + 'px;position:absolute;left:0px;top:' + 10 px;
//			canvas.style.cssText += canvasStyle;
    };

    //画上面的区域
    PreChange.prototype.drawTopArea = function (ctx, x, y, w, h) {
        ctx.fillStyle = this.option.topColor;
        ctx.strokeStyle = this.option.topColor;
        ctx.beginPath();
        ctx.moveTo(x, y);//找到起始点.
        ctx.lineTo(x + w / 2, y - this.option.jianjiao);//画左上斜线
        ctx.lineTo(x + w, y);//画横线 往右下
        ctx.lineTo(x + w, h + y);//画竖线  往下
        ctx.lineTo(x, h + y);//画左横线
        ctx.lineTo(x, y);//画向上的线
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
    };

    //画中间的区域
    PreChange.prototype.drawMiddleArea = function (ctx, x, y, w, h) {
        //75 172 178
        ctx.fillStyle = this.option.middleColor;
        ctx.strokeStyle = this.option.middleColor;
        ctx.beginPath();
        ctx.rect(x, y, w, h);
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
    };

    //画底部的区域
    PreChange.prototype.drawBottomArea = function (ctx, x, y, w, h) {
        ctx.fillStyle = this.option.bottomColor;
        ctx.strokeStyle = this.option.bottomColor;
        ctx.beginPath();
        ctx.moveTo(x, y);//找到起始点.
        ctx.lineTo(x + w, y);//画横线 往右
        ctx.lineTo(x + w, h + y);//画竖线  往下
        ctx.lineTo(x + w / 2, h + y + this.option.jianjiao);//画左下斜线
        ctx.lineTo(x, h + y);//画左上斜线
        ctx.lineTo(x, y);//画向上的线
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
    };
    //画文字
    PreChange.prototype.drawFont = function (ctx, x, y, str, fontStyle) {
        ctx.beginPath();
        if (fontStyle.fontColor != null) {
            ctx.fillStyle = fontStyle.fontColor;
        }
        if (fontStyle.fontSize != null) {
            ctx.font = fontStyle.fontSize + 'px 微软雅黑';
        }
        if (fontStyle.textBaseline != null) {
            ctx.textBaseline = fontStyle.textBaseline;
        }
        if (fontStyle.textAlign != null) {
            ctx.textAlign = fontStyle.textAlign;
        }
        ctx.fillText(str, x, y);
    };

    //画右边的线
    PreChange.prototype.drawRightLine = function (ctx, x, y, w) {
        ctx.beginPath();
        ctx.strokeStyle = this.option.rigthFontStyle.lineColor;
        ctx.moveTo(x, y);
        ctx.lineTo(x + w, y);//画线
        ctx.stroke();
    };

    PreChange.prototype.init = function () {
        //获取中心位置
        var ctx = this.createCanvas();
        var w = this.option.width / 3;
        var x = this.option.x;
        var gap = this.option.gap;
        var obj = this.obj;
        if (obj == null) {
            obj = new Object();
            obj.rise = 0;
            obj.equal = 0;
            obj.down = 0;
        }
        var canvasCenterY = this.option.height / 2;
        //中间部分
        var mh = 50;//高
        var my = canvasCenterY;//获取中间位置
        my = my - mh / 2;//开始位置 左上角
        this.drawMiddleArea(ctx, x, my, w, mh);

        //底部b
        var bh = 40;//高
        var by = my + mh + gap;//中间的开始位置+中间部分的高+间隔
        this.drawBottomArea(ctx, x, by, w, bh);

        //顶部
        var th = 40;//高
        var ty = my - th - gap;//中间的开始位置+中间部分的高+间隔
        this.drawTopArea(ctx, x, ty, w, th);

        //写文字  middlearea  top bootom
        //Y的部分 还可以加上文字的
        var allCenterX = x + w / 2;
        var txtTopY = ty + th / 2;
        var txtMidY = canvasCenterY;
        var txtBotY = by + bh / 2;
        //比例
        var count = obj.rise + obj.down + obj.equal;
        var pRise = obj.rise / count;
        var pDown = obj.down / count;
        var pNoChange = 1 - pRise - pDown;

        pNoChange = isNaN(pNoChange) ? 0 : pNoChange;
        pRise = isNaN(pRise) ? 0 : pRise;
        pDown = isNaN(pDown) ? 0 : pDown;

        pRise = Math.round(pRise * 100) + '%';
        pDown = Math.round(pDown * 100) + '%';
        pNoChange = Math.round(pNoChange * 100) + '%';

        this.drawFont(ctx, allCenterX, txtMidY, pNoChange, this.option.leftFontStyle);
        this.drawFont(ctx, allCenterX, txtTopY, pRise, this.option.leftFontStyle);
        this.drawFont(ctx, allCenterX, txtBotY, pDown, this.option.leftFontStyle);

        //写右边的文字
        var allTextX = x + w + this.option.splitLineW;
        this.drawFont(ctx, allTextX, txtTopY, '有所进步，' + obj.rise + '人', this.option.rigthFontStyle);
        this.drawFont(ctx, allTextX, txtMidY, '维持现状，' + obj.equal + '人', this.option.rigthFontStyle);
        this.drawFont(ctx, allTextX, txtBotY, '出现下滑，' + obj.down + '人', this.option.rigthFontStyle);

        //画右边的线
        var allLineX = x + w;
        this.drawRightLine(ctx, allLineX, txtMidY, this.option.splitLineW);
        this.drawRightLine(ctx, allLineX, txtTopY, this.option.splitLineW);
        this.drawRightLine(ctx, allLineX, txtBotY, this.option.splitLineW);

    };
    //绩效异常 配置对象
    var preUnusualOption = {
        gap: 4.5, //间隔
        jianjiao: 30, //顶部和底部尖角
        width: 300,
        height: 220,
        x: 20.5,//x轴的起点
        topColor: 'rgb(92,200,43)',
        bottomColor: 'rgb(210,76,0)',
        leftFontStyle: {
            fontSize: 22,
            fontColor: '#f0f0f0',
            textAlign: 'center',
            textBaseline: 'middle'
        },
        rigthFontStyle: {
            fontSize: 13,
            fontColor: '#000000',
            lineColor: '#333333',
            textAlign: 'left',
            textBaseline: 'middle'
        },
        splitLineW: 40
    };

    //绩效异常HTML5绘制图形
    var PreUnusual = function (mainId, obj, option) {
        this.mainId = mainId;
        this.obj = obj;
        this.option = option;
        this.middleY = this.option.width / 2;
    };

    //创建画布
    PreUnusual.prototype.createCanvas = function () {
        //清空div
        $('#' + this.mainId).html('');

        var canvas = document.createElement('canvas');
        $('#' + this.mainId).append(canvas);
        this.setCanvasPos(canvas);
        return canvas.getContext('2d');
    };

    //设置画布样式
    PreUnusual.prototype.setCanvasPos = function (canvas) {
        canvas.setAttribute('width', this.option.width);
        canvas.setAttribute('height', this.option.height);
        // 减少reflow
//		var canvasStyle = 'width:' + this.option.width + 'px;height:' + this.option.height + 'px;position:absolute;left:0px;top:' + 10 px;
//		canvas.style.cssText += canvasStyle;
    };

    //画上面的区域
    PreUnusual.prototype.drawTopArea = function (ctx, x, y, w, h) {
        ctx.fillStyle = this.option.topColor;
        ctx.strokeStyle = this.option.topColor;
        ctx.beginPath();
        ctx.moveTo(x, y);//找到起始点.
        ctx.lineTo(x - 20, y);//画横线 往左
        ctx.lineTo(x + w / 2, y - this.option.jianjiao);//画左上斜线
        ctx.lineTo(x + w + 20, y);//画右下斜线 往右
        ctx.lineTo(x + w, y);//画横线 往左
        ctx.lineTo(x + w, h + y);//画竖线  往下
        ctx.lineTo(x, h + y);//画左横线
        ctx.lineTo(x, y);//画向上的线
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
    };

    //话底部的区域
    PreUnusual.prototype.drawBottomArea = function (ctx, x, y, w, h) {
        ctx.fillStyle = this.option.bottomColor;
        ctx.strokeStyle = this.option.bottomColor;
        ctx.beginPath();
        ctx.moveTo(x, y);//找到起始点.
        ctx.lineTo(x + w, y);//画横线 往右
        ctx.lineTo(x + w, h + y);//画竖线  往下
        ctx.lineTo(x + w + 20, h + y);//画横线 往右
        ctx.lineTo(x + w / 2, h + y + this.option.jianjiao);//画左下斜线
        ctx.lineTo(x - 20, h + y);//画左上斜线
        ctx.lineTo(x, h + y);//画横线 往右
        ctx.lineTo(x, y);//画向上的线
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
    };
    //画文字
    PreUnusual.prototype.drawFont = function (ctx, x, y, str, fontStyle) {
        ctx.beginPath();
        if (fontStyle.fontColor != null) {
            ctx.fillStyle = fontStyle.fontColor;
        }
        if (fontStyle.fontSize != null) {
            ctx.font = fontStyle.fontSize + 'px 微软雅黑';
        }
        if (fontStyle.textBaseline != null) {
            ctx.textBaseline = fontStyle.textBaseline;
        }
        if (fontStyle.textAlign != null) {
            ctx.textAlign = fontStyle.textAlign;
        }
        ctx.fillText(str, x, y);
    };

    //画右边的线
    PreUnusual.prototype.drawRightLine = function (ctx, x, y, w) {
        ctx.beginPath();
        ctx.strokeStyle = this.option.rigthFontStyle.lineColor;
        ctx.moveTo(x, y);
        ctx.lineTo(x + w, y);//画线
        ctx.stroke();
    };

    PreUnusual.prototype.init = function () {
        //获取中心位置
        var ctx = this.createCanvas();
        var w = this.option.width / 3;
        var x = this.option.x;
        var gap = this.option.gap;
        var obj = this.obj;
        if (obj == null) {
            obj = new Object();
            obj.rise = 0;
            obj.equal = 0;
            obj.down = 0;
        }
        var canvasCenterY = this.option.height / 2;

        //底部b
        var bh = 50;//高
        var by = canvasCenterY + gap;//中间的开始位置+间隔
        this.drawBottomArea(ctx, x, by, w, bh);

        //顶部
        var th = 50;//高
        var ty = canvasCenterY - bh;//中间的开始位置+底部的高度
        this.drawTopArea(ctx, x, ty, w, th);

        //写文字  op bootom
        //Y的部分 还可以加上文字的
        var allCenterX = x + w / 2;
        var txtTopY = ty + th / 2;
        var txtBotY = by + bh / 2;
        //比例
        var pRise = obj.rise / obj.equal;
        var pDown = obj.down / obj.equal;

        pRise = isNaN(pRise) ? 0 : pRise;
        pDown = isNaN(pDown) ? 0 : pDown;

        pRise = Math.round(pRise * 100) + '%';
        pDown = Math.round(pDown * 100) + '%';

        this.drawFont(ctx, allCenterX, txtTopY, pRise, this.option.leftFontStyle);
        this.drawFont(ctx, allCenterX, txtBotY, pDown, this.option.leftFontStyle);

        //写右边的文字
        var allTextX = x + w + this.option.splitLineW;
        this.drawFont(ctx, allTextX, txtTopY, '飞速提升，' + obj.rise + '人', this.option.rigthFontStyle);
        this.drawFont(ctx, allTextX, txtBotY, '加速跌落，' + obj.down + '人', this.option.rigthFontStyle);

        //画右边的线
        var allLineX = x + w;
        this.drawRightLine(ctx, allLineX, txtTopY, this.option.splitLineW);
        this.drawRightLine(ctx, allLineX, txtBotY, this.option.splitLineW);

    };

    var perfChangeObj = {
    	perChangeGridId: 'perchangeByOrganGrid',
    	perChangeGridObj: null,
        init: function (organId) {
            var self = this;
            self.perChangeGridObj = $('#' + self.perChangeGridId).jqGrid(perChangeGridOption);
            self.requestPerfDate(organId);
        },
        requestPerfDate: function (organId) {
            var self = this;
            var param = {
            	organId: organId,
            	yearMonth: pageSettingObj.yearMonth
            }
//            $.post(urls.getPerchangeByOrganUrl, param, function (result) {
            var result = queryPerchangeByOrgan;
            	if(_.isEmpty(result)) {
            		$('#perchangeByOrganTable').html('');
            	} else {
            		self.perChangeGridObj.clearGridData().setGridParam({
                        data: result
                    }).trigger("reloadGrid");
                    self.resizeGrid();
            	}
//            });
            /*$.get(urls.getPerfChangeDateUrl, function (rs) {
            	var rs = getPerfChangeDate;
                if (_.isEmpty(rs)) {
                    $.each($(".mainArea"), function (i) {
                        var chartId = $($(this).find('.chartArea').children()).attr('id');
                        // hideChart(chartId, true);
                    });
                    return;
                }
                var res = '';
                for(i in rs) {
                	if(i < rs.length) {
                		if(i == rs.length - 1) {
                    		res += rs[i];
                    	} else {
                    		res += rs[i]+',';
                    	}
                	}
                }
                self.requestChangeData(organId, resrs.join(','));
            });*/
            self.requestChangeData(organId);
        },
        requestChangeData: function (organId, rs) {
            var self = this;
//            var params = {organizationId: organId, yearMonths: rs};
//            $.get(urls.getPreChangeCountDataUrl, params, function (data) {
            var data = getPreChangeCountData;
                if (!_.isEmpty(data)) {
                    if (!_.isEmpty(data.change)) {
                        // hideChart('preChange', false);
                        self.setPreChangeData(data.change);
                    } else {
                        // hideChart('preChange', true);
                    }
                    if (!_.isEmpty(data.bigChange)) {
                        // hideChart('preUnusual', false);
                        self.setPreExceptionData(data.bigChange);
                    } else {
                        // hideChart('preUnusual', true);
                    }
                } else {
                	for(i in $(".mainArea")) {
                		var chartId = $($(this).find('.chartArea').children()).attr('id');
                    }
                    /*$.each($(".mainArea"), function (i) {
                        var chartId = $($(this).find('.chartArea').children()).attr('id');
                        // hideChart(chartId, true);
                    });*/
                }
//            });
        },
        setPreChangeData: function (obj) {
            var c = new PreChange('preChange', obj, preChangeOption);
            c.init();
        },
        setPreExceptionData: function (obj) {
            var u = new PreUnusual('preUnusual', obj, preUnusualOption);
            u.init();
            return false;
            var preRiseHeight = 24;
            var preDownHeight = 24;
            var preRiseNumText = 0;
            var preDownNumText = 0;
            //百分比
            var preRise = $('#preRise');
            var preDown = $('#preDown');
            //具体数字
            var preRiseNum = '#preRiseNum';
            var preDownNum = '#preDownNum';
            //比率
            var pRise = 0;
            var pDown = 0;
            if (obj != null && obj.equal != 0) {
                //具体的数值
                preRiseNumText = obj.rise;
                preDownNumText = obj.down;
                //比率
                pRise = (obj.rise / obj.equal);
                pDown = obj.down / obj.equal;

                pRise = isNaN(pRise) ? 0 : pRise;
                pDown = isNaN(pDown) ? 0 : pDown;

                //先获取父div的高度,也就是最大高度，在按照高度进行计算
                var h = $('.preException').css('height');
                h = h.substr(0, h.length - 2);
                preRiseHeight = h * pRise;
                preDownHeight = h * pDown;
                //防止比率太小，显示不出来文字
                if (preRiseHeight < 24) {
                    preRiseHeight = 24;
                }
                if (preDownHeight < 24) {
                    preDownHeight = 24;
                }
            }
            //设置DIV高度
            preRise.css({'height': preRiseHeight + 'px', 'line-height': preRiseHeight + 'px'});
            preDown.css({'height': preDownHeight + 'px', 'line-height': preDownHeight + 'px'});
            //设置百分比
            preRise.text(Math.round(pRise * 100) + '%');
            preDown.text(Math.round(pDown * 100) + '%');
            //设置具体的数值
            $(preRiseNum).text(preRiseNumText);
            $(preDownNum).text(preDownNumText);
        },
        resizeGrid: function() {
        	if (self.perChangeGridObj) self.perChangeGridObj.setGridWidth($('#perchangeByOrganTable').width());
        }
    }
    perfChangeObj.init(reqOrgId);
    
    /**
     * 晋升分析 配置对象
     * @type {{}}
     */
    var promotionGeneralizeGridOption = {     //绩效概况option
        data: [],
        datatype: "local",
        altRows: true,//设置表格行的不同底色
        autowidth: true,
        height: 'auto',//268
        colNames: ['', '组织架构', '总人数', '晋级人数', '晋级比例', '晋级人员关键人才比例', '晋级速度'],
        colModel: [
            {name: 'organId', hidden: true, sortable: false},
            {name: 'organName', width: 'auto', sortable: false, align: 'center'},
            {name: 'count', width: 'auto', sortable: false, align: 'center'},
            {name: 'proCount', width: 'auto', sortable: false, align: 'center'},
            {name: 'proRate', width: 'auto', sortable: false, align: 'center',
            	formatter: function (cellvalue, options, rowObject) {
                    return Tc.formatFloat(cellvalue*100) + '%';
                }
            },
            {name: 'keyProRate', width: 'auto', sortable: false, align: 'center',
            	formatter: function (cellvalue, options, rowObject) {
            		return Tc.formatFloat(cellvalue*100) + '%';
            	}
            },
            {name: 'proVelocity', width: 'auto', sortable: false, align: 'center'}
        ],
        scroll: false,
        rowHeight: 36,
        styleUI: 'Bootstrap'
    };
    var promotionInJobsOption = {
        data: [],
        datatype: "local",
        altRows: true,//设置表格行的不同底色
        autowidth: true,
        height: 'auto',//268
        colNames: ['职级'],
        colModel: [
            {name: 'abilityName', width: 'auto', sortable: false, align: 'center'}
        ],
        scroll: false,
        rowHeight: 36,
        styleUI: 'Bootstrap'
    };
    var promotionObj = {
        inJobsId: 'promotionInJobsGrid',
        inJobsObj: null,
        promotionGeneralizeId: 'promotionGeneralizeGrid',
        promotionGeneralizeObj: null,
        init: function (organId) {
            var self = this;
            promotionGeneralizeGridOption.data = queryPromotionGeneral;
            self.promotionGeneralizeObj = $('#' + self.promotionGeneralizeId).jqGrid(promotionGeneralizeGridOption);
            self.requestInJobsData(organId);
        },
        requestInJobsData: function (organId) {
            var self = this;
            /*var param = {
            	organId: organId,
            	yearMonth: pageSettingObj.yearMonth
            }*/
//            $.post(urls.getPromotionGeneralUrl, param, function (result) {
            var result = queryPromotionGeneral;
            	if(_.isEmpty(result)) {
            		$('#promotionGeneralizeTable').html('');
            	} else {
            		self.promotionGeneralizeObj.clearGridData().setGridParam({
                        data: result
                    }).trigger("reloadGrid");
                    self.resizeGrid();
            	}
//            });
            /*$.post(urls.getInJobEmpCountUrl, {organId: organId}, function (rs) {
                if (!_.isEmpty(rs)) {
                    self.renderInJobsData(rs);
                }
            });*/
                self.renderInJobsData(getInJobEmpCount);
        },
        renderInJobsData: function (data) {
            var self = this, colNames = [], colIds = [];
            for(var i = 0; i < data.length; i ++) {
            	var obj = data[i];
            	if (_.indexOf(colIds, obj.sequenceId) < 0) {
                    colNames.push(obj.sequenceName);
                    colIds.push(obj.sequenceId);
                }
            }
            /*$.each(data, function (idx, obj) {
                if (_.indexOf(colIds, obj.sequenceId) < 0) {
                    colNames.push(obj.sequenceName);
                    colIds.push(obj.sequenceId);
                }
            });*/
            var newData = [], colNumber = [];
            for(i in colIds) {
            	colNumber[i] = 0;
            }
            /*$.each(colIds, function (idx, id) {
                colNumber[idx] = 0;
            });*/
            for(var i = 0; i < data.length; i ++) {
            	var obj = data[i];
            	var item = _.find(newData, function (n) {
                    return n.abilityName == obj.abilityName;
                });
                if (_.isUndefined(item)) {
                    item = {abilityName: obj.abilityName};
                    newData.push(item);
                }
                if (_.isUndefined(item[obj.sequenceId])) item[obj.sequenceId] = obj.empNumber;

                colNumber[_.indexOf(colIds, obj.sequenceId)] += obj.empNumber;
            }
            /*$.each(data, function (idx, obj) {
                var item = _.find(newData, function (n) {
                    return n.abilityName == obj.abilityName;
                });
                if (_.isUndefined(item)) {
                    item = {abilityName: obj.abilityName};
                    newData.push(item);
                }
                if (_.isUndefined(item[obj.sequenceId])) item[obj.sequenceId] = obj.empNumber;

                colNumber[_.indexOf(colIds, obj.sequenceId)] += obj.empNumber;
            });*/
            var totalData = {abilityName: '合计'}, celModel = [];
            $.each(colIds, function (idx, id) {
                totalData[id] = colNumber[idx];
                celModel.push({name: id, width: 100, sortable: false, align: 'center'});
            });
            newData.push(totalData);

            var option = $.extend(true, {}, promotionInJobsOption);
            var celNames = option.colNames.concat(colNames);
            celNames.push('合计');
            option.colNames = celNames;

            celModel = option.colModel.concat(celModel);
            celModel.push({
                name: 'total', width: 100, sortable: false, align: 'center',
                formatter: function (cellvalue, options, rowObject) {
                    var total = 0;
                    $.each(rowObject, function (k, v) {
                        if (k == 'abilityName') return true;
                        total += v;
                    });
                    return total;
                }
            });
            option.colModel = celModel;
            option.data = newData;

            self.inJobsObj = $('#' + self.inJobsId).jqGrid(option);
        },
        resizeGrid: function () {
            var self = this;
            if (self.promotionGeneralizeObj) self.promotionGeneralizeObj.setGridWidth($('#promotionGeneralizeTable').width());
            if (self.inJobsObj) self.inJobsObj.setGridWidth($('#promotionInJobsTables').width());
        }
    }
    promotionObj.init(reqOrgId);
    
    /***
     * 销售分析
     * @type {{data: Array, datatype: string, altRows: boolean, autowidth: boolean, height: number, colNames: string[], colModel: *[], scroll: boolean}}
     */
    var salesCountOption = {     //销售情况统计option
        data: [],
        datatype: "local",
        altRows: true,//设置表格行的不同底色
        autowidth: true,
        height: 'auto',//268
        colNames: ['', '产品', '销量', '人均销量', '目标值（万元）', '销售额（万元）', '回款额（万元）', '达标率', '回款率'],
        colModel: [
            {name: 'itemId', hidden: true, sortable: false},
            {name: 'itemName', width: 'auto', sortable: false, align: 'center'},
            {name: 'salesNumber', width: 'auto', sortable: false, align: 'center'},
            {name: 'avgNumber', width: 'auto', sortable: false, align: 'center'},
            {name: 'salesTarget', width: 'auto', sortable: false, align: 'center', formatter: moneyFormatter},
            {name: 'salesMoney', width: 'auto', sortable: false, align: 'center', formatter: moneyFormatter},
            {name: 'returnAmount', width: 'auto', sortable: false, align: 'center', formatter: moneyFormatter},
            {name: 'standardsRate', width: 'auto', sortable: false, align: 'center', formatter: ShareFormatter},
            {name: 'returnAmountRate', width: 'auto', sortable: false, align: 'center', formatter: ShareFormatter}
        ],
        scroll: false,
        rowHeight: 36,
        styleUI: 'Bootstrap'
    };
    var salesCountChartOption = {       //销售情况月度分析option
        title: {
            subtext: '(万元)'
        },
        legend: {
            data: ['目标值', '销售值', '回款值'],
            y: 'bottom',
            selectedMode: false
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {type: 'none'}
        },
        calculable: true,
        grid: {
            borderWidth: 0,
            x2: 50,
            y2: 70
        },
        color: Tc.defaultBarColor,
        xAxis: [
            {
                type: 'category',
                splitLine: false,
                axisLine: {
                    show: true,
                    onZero: false,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisTick: {
                    show: true,
                    lineStyle: {
                        color: '#D9D9D9'
                    }
                },
                axisLabel: {
                    // rotate: 30,
                    show: true,
                    itemStyle: {
                        color: '#BEBEBE'
                    }
                },
                data: []
            }
        ],
        yAxis: [
            {
                type: 'value',
                splitLine: false,
                axisLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    show: true
                }
            }
        ],
        series: [
            {
                name: '目标值',
                type: 'bar',
                clickable: false,
                barCategoryGap: '30%',
                barMaxWidth: 43,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            },
                            position: 'top'
                        }
                    },
                    emphasis: {
                        color: 'rgba(0,0,0,0)'
                    }
                },
                data: [],
            },
            {
                name: '销售值',
                type: 'bar',
                clickable: false,
                barCategoryGap: '30%',
                barMaxWidth: 43,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            },
                            position: 'top'
                        }
                    },
                    emphasis: {
                        color: 'rgba(0,0,0,0)'
                    }
                },
                data: [],
            },
            {
                name: '回款值',
                type: 'bar',
                clickable: false,
                barCategoryGap: '30%',
                barMaxWidth: 43,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: 'black'
                            },
                            position: 'top'
                        }
                    },
                    emphasis: {
                        color: 'rgba(0,0,0,0)'
                    }
                },
                data: [],
            }
        ]
    }

    var salesCountObj = {
        salesByProductGridId: '#salesByProductGrid',
        salesByOrganGridId: '#salesByOrganGrid',
        salesByProductGridObj: null,
        salesByOrganGridObj: null,
        salesByProductChartId: 'salesByProductChart',
        salesByOrganChartId: 'salesByOrganChart',
        salesByProductChartObj: null,
        salesByOrganChartObj: null,
        init: function (organId) {
            var self = this;
            if (_.isNull(self.salesByProductGridObj)) {
                var productOption = $.extend(true, {}, salesCountOption);
                productOption.colNames = ['', '产品', '销量', '人均销量', '目标值（万元）', '销售额（万元）', '回款额（万元）', '达标率', '回款率'];
                self.salesByProductGridObj = $(self.salesByProductGridId).jqGrid(productOption);
                self.requestData(1, organId, '#salesByProductTable');
            }
            if (_.isNull(self.salesByOrganGridObj)) {
                var organOption = $.extend(true, {}, salesCountOption);
                organOption.colNames = ['', '组织机构', '销量', '人均销量', '目标值（万元）', '销售额（万元）', '回款额（万元）', '达标率', '回款率'];
                self.salesByOrganGridObj = $(self.salesByOrganGridId).jqGrid(organOption);
                self.requestData(0, organId, '#salesByOrganTable');
            }
        },
        requestData: function (type, organId, id) {
            var self = this, yearMonth = pageSettingObj.yearMonth, gridObj = type == 1 ? self.salesByProductGridObj : self.salesByOrganGridObj;
            /*var param = {
                organId: organId,
                yearMonth: yearMonth,
                type: type
            }*/
//            $.post(urls.getSalesCountUrl, param, function (data) {
            var data;
            if(type == 1) {
            	data = getSalesCount1;
            } else {
            	data = getSalesCount0;
            }
                if (_.isEmpty(data)) {
                	$(id).html('');
                	return;
                }
                self.organId = organId;
                gridObj.clearGridData().setGridParam({
                    data: data
                }).trigger("reloadGrid");
                self.resizeGrid();

                var newData = [];
                for(var i = 0; i < data.length; i ++) {
                	var obj = data[i];
                	newData.push({k: obj.itemId, v: obj.itemName});
                }
                /*$.each(data, function (i, obj) {
                    newData.push({k: obj.itemId, v: obj.itemName});
                });*/
                self.initItemSelector(type, newData);
//            });
        },
        initItemSelector: function (type, data) {
            var self = this, $targetObj = type == 1 ? $('#salesByProductSelector') : $('#salesByOrganSelector');
            if (_.isEmpty(data)) return;
            $targetObj.html('');
            for(idx in data) {
            	var obj = data[idx];
            	var html = '<li data-toggle="' + obj.k + '" data-name="' + obj.v + '"><a href="javascript:void(0);" >' + obj.v + '</a></li>';
                $targetObj.append(html);
            }
            /*$.each(data, function (idx, obj) {
                var html = '<li data-toggle="' + obj.k + '" data-name="' + obj.v + '"><a href="javascript:void(0);" >' + obj.v + '</a></li>';
                $targetObj.append(html);
            });*/
            $targetObj.children().unbind('click').click(function () {
                var $this = $(this);
                var itemId = $this.data('toggle');
                var itemName = $this.data('name');

                $targetObj.prev('.btn').text(itemName);
                self.requestItemData(type, itemId);
            });
            self.requestItemData(type, data[0].k);
            $targetObj.prev('.btn').text(data[0].v);
        },
        requestItemData: function (type, itemId) {
            var self = this, yearMonth = pageSettingObj.yearMonth;
            var chartId, chartObj, params = {};
            var rs;
            if (type == 1) {
                chartId = self.salesByProductChartId;
                if (_.isNull(self.salesByProductChartObj)) self.salesByProductChartObj = initEChart(chartId);
                chartObj = self.salesByProductChartObj;
//                params = {itemId: itemId, organId: self.organId};
                rs = getSalesCountByMonth1;
            } else {
                chartId = self.salesByOrganChartId;
                if (_.isNull(self.salesByOrganChartObj)) self.salesByOrganChartObj = initEChart(chartId);
                chartObj = self.salesByOrganChartObj;
//                params = {organId: itemId};
                rs = getSalesCountByMonth0;
            }
//            params.type = type;
//            params.yearMonth = yearMonth;
            showLoadingCharts(chartObj);
//            $.post(urls.getSalesCountByMonthUrl, params, function (rs) {
                if (_.isEmpty(rs)) {
                    showNoDataCharts(chartObj);
                    return;
                }
                self.renderItemChart(chartObj, rs);
                self.resizeChart();
//            });
        },
        renderItemChart: function (chartObj, data) {
            if (chartObj) chartObj.clear();
            var xAxisData = [], targetData = [], moneyData = [], returnAmountData = [];
            for(var i = 0; i < data.length; i++) {
            	var obj = data[i];
            	var yearMonth = obj.yearMonth + '';
                xAxisData.push(yearMonth.substr(0, 4) + '年' + yearMonth.substr(4, 2) + '月');
                targetData.push(Tc.formatFloat(obj.salesTarget));
                moneyData.push(Tc.formatFloat(obj.salesMoney));
                returnAmountData.push(Tc.formatFloat(obj.returnAmount));
            }
            /*$.each(data, function (idx, obj) {
                var yearMonth = obj.yearMonth + '';
                xAxisData.push(yearMonth.substr(0, 4) + '年' + yearMonth.substr(4, 2) + '月');
                targetData.push(Tc.formatFloat(obj.salesTarget));
                moneyData.push(Tc.formatFloat(obj.salesMoney));
                returnAmountData.push(Tc.formatFloat(obj.returnAmount));
            });*/
            var option = $.extend(true, {}, salesCountChartOption);
            option.xAxis[0].data = xAxisData;
            option.series[0].data = targetData;
            option.series[1].data = moneyData;
            option.series[2].data = returnAmountData;
            chartObj.clear();
            chartObj.hideLoading();
            chartObj.setOption(option);
            // chartObj.refresh();
        },
        resizeGrid: function () {
            var self = this;
            if (self.salesByProductGridObj) self.salesByProductGridObj.setGridWidth($('#salesByProductTable').width());
            if (self.salesByOrganGridObj) self.salesByOrganGridObj.setGridWidth($('#salesByOrganTable').width());
        },
        resizeChart: function () {
            var self = this;
            if (self.salesByOrganChartObj) self.salesByOrganChartObj.resize();
            if (self.salesByProductChartObj) self.salesByProductChartObj.resize();
        }
    }
    salesCountObj.init(reqOrgId);

});