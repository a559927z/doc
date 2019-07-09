require(['jquery', 'bootstrap', 'underscore', 'utils', 'layer'], function ($) {
    var webRoot = G_WEB_ROOT;
    var urls = {
        getMinMaxTime: webRoot + '/import/getMinMaxTime.do',
        queryDayList: webRoot + '/import/queryDayList.do',
        exportAttendanceMonthly: webRoot + "/import/exportAttendanceMonthly.do",//导出月度考勤
        getMaxDay: webRoot + "/import/getMaxDay.do",//最大日期
        updateVacation: webRoot + "/import/updateVacation.do",//更新days表带薪假
        getDaysDto: webRoot + "/import/getDaysDto.do",// easybots网获到DaysDto，同步ajax
    }
    layer.config({
        path: webRoot + '/assets/layer-v3.0.3/layer/'
    });
    // 因为公司数据从这时开始入库
    var minTime = 201605;
    // 考勤最大时间
    var maxTime;
    var minYear;
    var maxYear;
    // 最大到最小年份
    // var yearList = [];
    // 考勤最大时间的月份
    var maxMonth;
    // 选择的月份下所有日期
    var days = [];
    // day最小日期
    var minDay;
    // day最大日期
    var maxDay;
    // 当前时间
    var nowData = new Date();
    // 当前月
    var nowYear = nowData.getFullYear();
    // 当前月
    var nowMonth = nowData.getMonth() + 1 < 10 ? "0" + (nowData.getMonth() + 1) : nowData.getMonth() + 1;
    // 当前日
    var nowDay = nowData.getDate() < 10 ? "0" + nowData.getDate() : nowData.getDate();

    // 红，绿， 黄
    var styleColor = ["#ebccd1", "#d6e9c6", "#faebcc"];
    var style = ["style-danger", "style-success", "style-warning"];

    function getData(y) {
        /**
         * importExcelData
         */
        Tc.ajax({
            url: urls.getMinMaxTime,
            success: function (rs) {
                minTime = rs.minTime;
                maxTime = rs.maxTime;
                maxTime = Tc.null2Zeno(maxTime);

                if (0 == maxTime) {
                    layer.msg("d");
                    return;
                }

                minYear = parseInt(minTime.toString().substring(0, 4));
                maxYear = parseInt(maxTime.toString().substring(0, 4));
                maxMonth = parseInt(maxTime.toString().substring(4, 6));

                var year = maxYear;
                // 最大到最小年份
                var yearList = [];
                while (minYear <= year) {
                    yearList.push(year);
                    year = year - 1;
                }

                $(".importExcelDropClass1").append(reanderDropYear(1, yearList));
                reanderMonthZone();
                eventDropYear1(yearList);
            }
        });

        /**
         * daysData
         */
        Tc.ajax({
            url: urls.getMaxDay,
            success: function (rs) {
                console.log(rs.minDay)
                minDay = rs.minDay;
                maxDay = rs.maxDay;
                var maxYear = maxDay.split("-")[0];
                var minYear = minDay.split("-")[0];

                var daysYearList = [];
                while (minYear <= maxYear) {
                    daysYearList.push(maxYear);
                    maxYear = maxYear - 1;
                }

                $(".importExcelDropClass2").append(reanderDropYear(2, daysYearList));
                eventDropYear2(daysYearList);
                eventDropMonth();
            }
        })
        $("#dropMonthId").text(nowMonth + "月").append('<span class="caret"></span>');
        getDateVacation();
    }

    function eventMonth() {
        $(".dayBtnClass").on("click", function () {
            var selectDay = $(this).text().trim().replace("日", "");
            var styleVal = $(this).attr("data");
            var msg = " 设置为带薪假期";

            var y = $(".yearDropClass2").text().trim().replace("年", "");
            var m = $("#dropMonthId").text().trim().replace("月", "");
            var ymd = y + '-' + m + '-' + (selectDay = selectDay < 10 ? "0" + selectDay : selectDay);
            var param = {
                "days": ymd,
                "isWorkFlag": 0,
                "isHoliday": 0,
                "isVacation": 1
            }
            // 恢复是先去查easybots返回来来再去ajax upload
            if (styleVal == styleColor[2]) {
                msg = " 恢复国务院的公报";
                var param = {
                    "days": ymd,
                    "isWorkFlag": 9999,
                    "isHoliday": 9999,
                    "isVacation": 9999
                }
            }
            layer.confirm(ymd + ' 这天' + msg, {
                btn: ['确认', '取消'] //按钮
            }, function () {
                var setting = {
                    url: urls.updateVacation,
                    data: param,
                    success: function (rs, status) {
                        console.log(rs);
                        if (rs.k) {
                            layer.msg("设置成功", {icon: 6});
                            getDateVacation(ymd);
                        } else {
                            layer.msg(rs.v, {icon: 5});
                        }
                    }
                };
                Tc.ajax(setting);
            }, function () {
                layer.msg('已取消删除操作', {
                    time: 20000 //20s后自动关闭
                });
            });

        });
    }

    /**
     * 日期数据
     */
    function getDateVacation(ymd) {

        if (ymd == null) {
            ymd = nowYear + '-' + nowMonth + '-' + nowDay;
        }
        Tc.ajax({
            url: urls.queryDayList,
            data: {"yearMonthDay": ymd},
            success: function (rs) {
                if (rs == null) {
                    layer.msg("暂无数据");
                }
                days = rs;
                reanderDayZone();
                eventMonth();
            }
        });
    }


    /**
     * 下拉月份事件
     */
    function eventDropMonth() {
        var y = $(".yearDropClass2").text().trim().replace("年", ""), m;
        $(".monthDropClass").on("click", function () {
            m = $(this).text().trim().replace("月", "");
            $("#dropMonthId").text(m + "月").append('<span class="caret"></span>');

            $("#dayRowZone1Id").empty();
            $("#dayRowZone2Id").empty();
            $("#dayRowZone3Id").empty();
            $("#dayRowZone4Id").empty();
            $("#dayRowZone5Id").empty();

            // 当事件触发，重获数据getDateVacation()，并重烩
            var ymd = y + '-' + m + '-' + nowDay;
            getDateVacation(ymd);

        });
    }


    /**
     * 下拉年份事件
     */
    function eventDropYear2(yearList) {
        var y = yearList[0];
        $(".yearClass2").on("click", function () {
            y = $(this).attr("data");
            $(".yearDropClass2").text(y + '年\n').append('<span class="caret"></span>');
            // 1 过往年份
            $("#dayRowZone1Id").empty();
            $("#dayRowZone2Id").empty();
            $("#dayRowZone3Id").empty();
            $("#dayRowZone4Id").empty();
            $("#dayRowZone5Id").empty();

            // 当事件触发，重获数据getDateVacation()，并重烩
            var ymd = y + '-' + nowMonth + '-' + nowDay;
            getDateVacation(ymd);

        });
    }

    /**
     * 下拉年份事件
     */
    function eventDropYear1(yearList) {
        var y = yearList[0], m;
        $(".yearClass1").on("click", function () {
            y = $(this).attr("data");
            $(".yearDropClass1").text(y + '年\n').append('<span class="caret"></span>');
            // 1 过往年份
            $("#monthRowZone1Id").empty();
            $("#monthRowZone2Id").empty();
            $("#monthRowZone3Id").empty();
            var flag = 1;
            if (y == yearList[0]) {
                flag = 0;
            }
            reanderMonthZone(flag);
        });
        $(".monthBtnClass").on("click", function () {
            m = $(this).attr("data");
            m = parseInt(m) <= 9 ? "0" + m : m;
            getRequestExcel(y + "-" + m);
        });
    }

    /**
     * excel导出
     * @param organId
     * @param yearmonth
     * @param stl
     */
    function getRequestExcel(yearmonth, stl) {
        var form = $("<form>");//定义一个form表单
        form.attr("style", "display:none");
        form.attr("target", "");
        form.attr("method", "post");
        form.attr("action", urls.exportAttendanceMonthly);
        var input1 = $("<input>");
        input1.attr("type", "hidden");
        input1.attr("name", "yearmonth");
        input1.attr("value", yearmonth);
        $("body").append(form);//将表单放置在web中
        form.append(input1);
        form.submit();//表单提交
    }

    /**
     * 下拉框年份
     * @returns {string}
     */
    function reanderDropYear(num, yearList) {
        var _yearList = [];
        // 如果是1就是用考勤数据最大年份
        // 如果是2就可以支持设置明年的数据
        // if (num == 1) {
        //     _yearList = yearList;
        // }
        // if (num == 2) {
        //     _yearList.push(yearList[0] + 1);
        //     $.each(yearList, function (index, item) {
        //         _yearList.push(yearList[index]);
        //     });
        // }
        _yearList = yearList;
        var liHtml = '';
        $.each(_yearList, function (index, item) {
            liHtml +=
                '                            <li role="presentation">\n' +
                '                                <a class="yearClass' + num + '" data="' + item + '" role="menuitem" tabindex="-1" href="#">' + item + '年</a>\n' +
                '                            </li>\n';
        });
        var html = '';
        html +=
            '                   <div class="dropdown">\n' +
            '                        <button type="button" class="btn dropdown-toggle yearDropClass' + num + '" id="yearDropId" data-toggle="dropdown">\n' +
            +_yearList[0] + '年\n' +
            '                            <span class="caret"></span>\n' +
            '                        </button>\n' +
            '                        <ul class="dropdown-menu" role="menu" aria-labelledby="yearDropId">\n' +
            liHtml +
            '                        </ul>\n' +
            '                    </div>';
        return html;
    }


    /**
     * 月份区域
     * @param flag 1下拉年分， 0默认当时年份
     */
    function reanderMonthZone(flag) {
        for (var i = 1; i <= 12; i++) {
            var styleBg = style[1];
            var stylePanel = "panel-success";
            if (flag == 1) {
                styleBg = style[1];
            } else if (i <= maxMonth) {
                styleBg = style[1];
            } else {
                styleBg = style[0];
                stylePanel = "panel-warning";
            }
            var colHtml =
                '                   <div class="col-md-3 top-row-col">\n' +
                '                        <div class="panel ' + stylePanel + '">\n' +
                '                            <div data="' + i + '" class="panel-body text-center monthBtnClass  ' + styleBg + ' btn-zone">\n' +
                i + '月\n' +
                '                            </div>\n' +
                '                        </div>\n' +
                '                    </div>';

            if (i <= 4) {
                $("#monthRowZone1Id").append(colHtml);
            }
            if (i <= 8 & i >= 5) {
                $("#monthRowZone2Id").append(colHtml);
            }
            if (i <= 12 & i >= 9) {
                $("#monthRowZone3Id").append(colHtml);
            }
        }
    }

    /**
     * 日区域
     */
    function reanderDayZone() {
        $("#dayRowZone1Id").empty();
        $("#dayRowZone2Id").empty();
        $("#dayRowZone3Id").empty();
        $("#dayRowZone4Id").empty();
        $("#dayRowZone5Id").empty();

        var styleData = styleColor[1];
        var styleBg = style[1];
        var len = days.length;
        var stylePanel = "panel-success";

        for (var i = 1; i <= len; i++) {
            var day = days[i - 1];
            // 休息日（6，7）
            if (day.isHoliday == 1) {
                styleBg = style[0];
                styleData = styleColor[0];
                stylePanel = "panel-danger";
                console.log(day);
            }
            // 节假日
            else if (day.isVacation == 1) {
                styleBg = style[2];
                styleData = styleColor[2];
                stylePanel = "panel-warning";
            }
            else if (day.isWorkFlag == 1) {
                styleBg = style[1];
                styleData = styleColor[1];
                stylePanel = "panel-success";
            }
            var colHtml =
                '                   <div class="col-md-2 top-row-col">\n' +
                '                        <div class="panel ' + stylePanel + '">\n' +
                '                            <div data = ' + styleData + '  class="panel-body text-center dayBtnClass btn-zone ' + styleBg + '">\n' +
                i + '日\n' +
                '                            </div>\n' +
                '                        </div>\n' +
                '                    </div>'
            ;

            if (i <= 6) {
                $("#dayRowZone1Id").append(colHtml);
            }
            if (i <= 12 & i >= 7) {
                $("#dayRowZone2Id").append(colHtml);
            }
            if (i <= 18 & i >= 13) {
                $("#dayRowZone3Id").append(colHtml);
            }
            if (i <= 24 & i >= 19) {
                $("#dayRowZone4Id").append(colHtml);
            }
            if (i <= 31 & i >= 25) {
                $("#dayRowZone5Id").append(colHtml);
            }
        }
    }


    function main() {
        getData();
    }

    main();
});

