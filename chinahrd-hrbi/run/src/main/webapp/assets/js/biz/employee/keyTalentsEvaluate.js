
require(['jquery','bootstrap', 'underscore', 'messenger', 'utils','resize'], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var urls = {
        loaderUrl: webRoot + "/assets/img/base/loader.gif",
        toEmpDetailUrl: webRoot + '/talentProfile/toTalentDetailView.do',//跳转到员工详情
        getTag: webRoot + "/keyTalents/queryTag", //查询标签
        historyTag: webRoot + "/keyTalents/getHistoryTag", //查询历史标签
        keyTalent: webRoot + "/keyTalents/getKeyTalentById", //查询关键人才姓名
        addTag: webRoot + "/keyTalents/addKeyTalentTag",//查询关键人才
        getEncourage: webRoot + "/keyTalents/getEncourage",//查询核心激励要素
        getKeyEncourage: webRoot + "/keyTalents/getKeyTalentEncourage",//查询核心激励要素详细信息
        updateEncourage: webRoot + "/keyTalents/updateKeyTalentEncourage",//修改核心激励要素
        deleteTag: webRoot + "/keyTalents/deleteKeyTalentTag",//删除标签
        addLog: webRoot + "/keyTalents/addKeyTalentLog",//增加跟踪日志
        getLog: webRoot + "/keyTalents/getKeyTalentLog",//查询跟踪日志
        updateLog: webRoot + "/keyTalents/updateKeyTalentLog",//修改跟踪日志
        deleteLog: webRoot + "/keyTalents/deleteKeyTalentLog",//删除跟踪日志
        keytalentEval: webRoot + "/keyTalents/toKeyTalentsEvaluateView"
    }
    var talentId = $('#keyTalentId').val();
    var customerId = $('#customerId').val();
    var currentEmpId;
    
    var showErrMsg = function (content) {
        Messenger().post({
            message: content,
            type: 'error',
            hideAfter: 2
        });
    };
    /*
     * 自动标签
     */
    var automaticLabel = {
        initData: function () {
            var self = this;
            var src = webRoot + "/assets/photo.jpg";
            var contentLabel = [];

            $.post(urls.keyTalent, {keyTalentId: talentId}, function (result) {
                $("#empHeadPic").attr("src", src);
                $("#empName").text(result.empName);
                currentEmpId = result.empId;

                $.post(urls.getTag, {keyTalentId: talentId}, function (result) {
                    $.each(result, function (i, item) {
                        if (item.type == 0) {
                            contentLabel.push(item);
                        }
                    });
                    var flag = false;
                    if (contentLabel.length > 3) {
                        flag = true;
                    }

                    $.each(contentLabel, function (i, item) {
                        if (i < 3) {
                            $('#contentLabel').append('<span><a href="' + urls.toEmpDetailUrl + '?empId=' + currentEmpId + '" target="_Blank">' + item.content + '</a></span>');
                        } else {
                            $('#contentLabel').append('<span class="hide addMore"><a href="' + urls.toEmpDetailUrl + '?empId=' + currentEmpId + '" target="_Blank">' + item.content + '</a></span>');
                        }
                        if (((i + 1) % 3) == 0) {
                            $('#contentLabel').append('<br>');
                        }
                    });
                    if (flag) {
                        $('#more_dis').removeClass('hide');
                        $('#reduce_dis').addClass('hide');

                        $('#smoreBtn').unbind("click").click(function () {
                            self.moreBtnEvent();
                        });
                    }
                });
            });
        },
        moreBtnEvent: function () {
            var self = this;
            $('#contentLabel .addMore').removeClass('hide');
            $('#more_dis').addClass('hide');
            $('#reduce_dis').removeClass('hide');
            $('#sreduceBtn').unbind("click").click(function () {
                self.reduceBtnEvent();
            });
        },
        reduceBtnEvent: function () {
            var self = this;
            $('#contentLabel .addMore').addClass('hide');
            $('#reduce_dis').addClass('hide');
            $('#more_dis').removeClass('hide');
            $('#smoreBtn').unbind("click").click(function () {
                self.moreBtnEvent();
            });
        }
    }
    automaticLabel.initData();

    /*
     * 手工标签
     */
    var manualLabel = {
        initData: function () {
            var self = this;
            //优秀
            $.post(urls.getTag, {keyTalentId: talentId}, function (result) {
                //优势定义
                var excellentLabelNum = 0;
                var myExcellentLabelContent = [];
                var othersExcellentLabelContent = [];
                //短板定义
                var inferiorityLabelNum = 0;
                var myInferiorityLabelContent = [];
                var othersInferiorityLabelContent = [];

                $.each(result, function (i, item) {
                    if (item.type == 1) {
                        if (customerId == item.createEmpId) {
                            myExcellentLabelContent.push(item);
                        } else {
                            othersExcellentLabelContent.push(item);
                        }
                    } else if (item.type == 2) {
                        if (customerId == item.createEmpId) {
                            myInferiorityLabelContent.push(item);
                        } else {
                            othersInferiorityLabelContent.push(item);
                        }
                    }
                });
                $("#excellentLabelNum").text(myExcellentLabelContent.length + othersExcellentLabelContent.length);
                var excellentFlag = false;
                if (myExcellentLabelContent.length > 9 || othersExcellentLabelContent.length > 9
                    || (myExcellentLabelContent.length > 3 && othersExcellentLabelContent.length > 3) || (myExcellentLabelContent.length <= 3 && othersExcellentLabelContent.length > 6)) {
                    excellentFlag = true;
                }
                var wrapNum = 0;
                if (myExcellentLabelContent.length > 0) {
                    wrapNum = 1;
                }
                $.each(myExcellentLabelContent, function (i, item) {
                    if (i > 8) {
                        $('#myExcellentLabelContent').append('<span class="hide excellentMore" id=' + item.tagId + '>' + item.content + '&nbsp;&nbsp;&times;</span>');
                    } else {
                        $('#myExcellentLabelContent').append('<span id=' + item.tagId + '>' + item.content + '&nbsp;&nbsp;&times;</span>');
                    }
                    if (((i + 1) % 3) == 0) {
                        $('#myExcellentLabelContent').append('<br>');
                        wrapNum++;
                    }
                    $("#" + item.tagId).unbind("click").click(function () {
                        $("#delDialog .modal-body span").text(item.content);
                        $("#delDialog").modal('show');
                        $("#btnOk").unbind("click").on("click", function () {
                            $.ajax({
                                type: "POST",
                                url: urls.deleteTag,
                                data: {tagId: item.tagId, type: 1},
                                success: function (result) {
                                    if (result.type) {
                                        var contentIdStr = "myExcellentLabelContent";
                                        var totalNumIdStr = "excellentLabelNum";
                                        var moreStr = "excellentMore";
                                        var moreBtnStr = "myReduce_excellent";
                                        self.delReload(item.tagId, contentIdStr, totalNumIdStr, moreStr, moreBtnStr);
                                    } else {
                                        showErrMsg(result.msg);
                                    }
                                    //alert(result.msg);
                                    //window.location.reload();
                                },
                                complete: function () {
                                    $("#delDialog").modal('hide');
                                }
                            });
                        });
                    })
                });

                $.each(othersExcellentLabelContent, function (i, item) {
                    if (wrapNum == 3 || (wrapNum == 0 && i > 8 ) || (wrapNum == 1 && i > 5) || (wrapNum == 2 && i > 2)) {
                        $('#othersExcellentLabelContent').append('<span class="hide excellentMore" id=' + item.tagId + '>' + item.content + '&nbsp;&nbsp;&times;</span>');
                    } else {
                        $('#othersExcellentLabelContent').append('<span  id=' + item.tagId + '>' + item.content + '&nbsp;&nbsp;&times;</span>');
                    }
                    if (((i + 1) % 3) == 0) {
                        $('#othersExcellentLabelContent').append('<br>');
                    }
                    $("#" + item.tagId).unbind("click").click(function () {
                        $("#delDialog .modal-body span").text(item.content);
                        $("#delDialog").modal('show');
                        $("#btnOk").unbind("click").on("click", function () {
                            $.ajax({
                                type: "POST",
                                url: urls.deleteTag,
                                data: {tagId: item.tagId, type: 1},
                                success: function (result) {
                                    if (result.type) {
                                        var contentIdStr = "othersExcellentLabelContent";
                                        var totalNumIdStr = "excellentLabelNum";
                                        var moreStr = "excellentMore";
                                        var moreBtnStr = "othersReduce_excellent";
                                        self.delReload(item.tagId, contentIdStr, totalNumIdStr, moreStr, moreBtnStr);
                                    } else {
                                        showErrMsg(result.msg);
                                    }
                                },
                                complete: function () {
                                    $("#delDialog").modal('hide');
                                }
                            });
                        });
                    })
                });

                if (excellentFlag) {
                    $('#myMore_excellent').removeClass('hide');
                    $('#myReduce_excellent').addClass('hide');

                    $('#smoreBtnMy').unbind("click").click(function () {
                        self.moreBtnEvent();
                    });
                }
                //短板

                $("#inferiorityLableNum").text(myInferiorityLabelContent.length + othersInferiorityLabelContent.length);
                var inferiorityFlag = false;
                if (myInferiorityLabelContent.length > 9 || othersInferiorityLabelContent.length > 9
                    || (myInferiorityLabelContent.length > 3 && othersInferiorityLabelContent.length > 3) || (myInferiorityLabelContent.length <= 3 && othersInferiorityLabelContent.length > 6)) {
                    inferiorityFlag = true;
                }
                var inferiorityNum = 0;
                if (myInferiorityLabelContent.length > 0) {
                    inferiorityNum = 1;
                }
                $.each(myInferiorityLabelContent, function (i, item) {
                    if (i > 8) {
                        $('#myInferiorityLabelContent').append('<span class="hide inferiorityMore" id=' + item.tagId + '>' + item.content + '&nbsp;&nbsp;&times;</span>');
                    } else {
                        $('#myInferiorityLabelContent').append('<span id=' + item.tagId + '>' + item.content + '&nbsp;&nbsp;&times;</span>');
                    }
                    if (((i + 1) % 3) == 0) {
                        $('#myInferiorityLabelContent').append('<br>');
                        inferiorityNum++;
                    }
                    $("#" + item.tagId).unbind('click').click(function () {
                        $("#delDialog .modal-body span").text(item.content);
                        $("#delDialog").modal('show');
                        $("#btnOk").unbind("click").on("click", function () {
                            $.ajax({
                                type: "POST",
                                url: urls.deleteTag,
                                data: {tagId: item.tagId, type: 2},
                                success: function (result) {
                                    if (result.type) {
                                        $("#" + item.tagId).fadeOut(1000, function () {
                                            var contentIdStr = "myInferiorityLabelContent";
                                            var totalNumIdStr = "inferiorityLableNum";
                                            var moreStr = "inferiorityMore";
                                            var moreBtnStr = "myReduce_inferiority";
                                            self.delReload(item.tagId, contentIdStr, totalNumIdStr, moreStr, moreBtnStr);
                                        });
                                    } else {
                                        showErrMsg(result.msg);
                                    }
                                    //alert(result.msg);
                                    //window.location.reload();
                                },
                                complete: function () {
                                    $("#delDialog").modal('hide');
                                }
                            });
                        });
                    })
                });

                $.each(othersInferiorityLabelContent, function (i, item) {
                    if (inferiorityNum == 3 || (inferiorityNum == 0 && i > 8 ) || (inferiorityNum == 1 && i > 5) || (inferiorityNum == 2 && i > 2)) {
                        $('#othersInferiorityLableContent').append('<span class="hide inferiorityMore" id=' + item.tagId + '>' + item.content + '&nbsp;&nbsp;&times;</span>');
                    } else {
                        $('#othersInferiorityLableContent').append('<span id=' + item.tagId + '>' + item.content + '&nbsp;&nbsp;&times;</span>');
                    }
                    if (((i + 1) % 3) == 0) {
                        $('#othersInferiorityLableContent').append('<br>');
                    }
                    $("#" + item.tagId).unbind('click').click(function () {
                        $("#delDialog .modal-body span").text(item.content);
                        $("#delDialog").modal('show');
                        $("#btnOk").unbind("click").on("click", function () {
                            $.ajax({
                                type: "POST",
                                url: urls.deleteTag,
                                data: {tagId: item.tagId, type: 2},
                                success: function (result) {
                                    if (result.type) {
                                        $("#" + item.tagId).fadeOut(1000, function () {
                                            var contentIdStr = "othersInferiorityLabelContent";
                                            var totalNumIdStr = "inferiorityLableNum";
                                            var moreStr = "inferiorityMore";
                                            var moreBtnStr = "othersReduce_inferiority";
                                            self.delReload(item.tagId, contentIdStr, totalNumIdStr, moreStr, moreBtnStr);
                                        });
                                    } else {
                                        showErrMsg(result.msg);
                                    }
                                    //alert(result.msg);
                                    //window.location.reload();
                                },
                                complete: function () {
                                    $("#delDialog").modal('hide');
                                }
                            });
                        });
                    })
                });

                if (inferiorityFlag) {
                    $('#myMore_inferiority').removeClass('hide');
                    $('#myReduce_inferiority').addClass('hide');
                    $('#imoreBtnMy').unbind("click").click(function () {
                        self.moreBtn();
                    });
                }
            });
            this.bindRecordClickEvent();
            this.bindAddLableClickEvent();
        },
        //删除后重置
        delReload: function (tagIdStr, contentIdStr, totalNumIdStr, moreStr, moreBtnStr) {
            $("#" + tagIdStr).fadeOut(1000, function () {
                $("#" + tagIdStr).remove();
                $("#" + contentIdStr + " br").remove();
                $.each($("#" + contentIdStr + " span"), function (i, item) {
                    $(item).removeClass(moreStr).removeClass("hide");
                    if (i >= 9) {
                        $(item).addClass(moreStr);
                        if ($("#" + moreBtnStr).hasClass("hide")) {
                            $(item).addClass("hide");
                        }
                    }
                    if (i > 0 && i % 3 == 0) {
                        $(item).before("<br>");
                    }
                });
                var len = $("#" + contentIdStr + " span").length;
                $("#" + totalNumIdStr).text(len);
                if (len <= 9) {
                    $("#" + contentIdStr + " div").addClass("hide");
                }
            });
        },
        moreBtnEvent: function () {
            var self = this;
            $('#myExcellentLabelContent .excellentMore').removeClass('hide');
            $('#othersExcellentLabelContent .excellentMore').removeClass('hide');
            $('#myMore_excellent').addClass('hide');
            $('#myReduce_excellent').removeClass('hide');
            $('#sreduceBtnMy').unbind("click").click(function () {
                self.reduceBtnEvent();
            });
        },
        reduceBtnEvent: function () {
            var self = this;
            $('#myExcellentLabelContent .excellentMore').addClass('hide');
            $('#othersExcellentLabelContent .excellentMore').addClass('hide');
            $('#myReduce_excellent').addClass('hide');
            $('#myMore_excellent').removeClass('hide');
            $('#smoreBtnMy').click(function () {
                self.moreBtnEvent();
            });
        },
        moreBtn: function () {
            var self = this;
            $('#myInferiorityLabelContent .inferiorityMore').removeClass('hide');
            $('#othersInferiorityLableContent .inferiorityMore').removeClass('hide');
            $('#myMore_inferiority').addClass('hide');
            $('#myReduce_inferiority').removeClass('hide');
            $('#ireduceBtnMy').unbind("click").click(function () {
                self.reduceBtn();
            });
        },
        reduceBtn: function () {
            var self = this;
            $('#myInferiorityLabelContent .inferiorityMore').addClass('hide');
            $('#othersInferiorityLableContent .inferiorityMore').addClass('hide');
            $('#myReduce_inferiority').addClass('hide');
            $('#myMore_inferiority').removeClass('hide');
            $('#imoreBtnMy').unbind("click").click(function () {
                self.moreBtn();
            });
        },

        bindRecordClickEvent: function () {
            var self = this;
            $('.manualLabel').find('.labelRight').unbind("click").click(function () {
                $('#manualLabelDetailModal').modal('show');
                self.showDialogManualLabelBase();
            });
        },
        //手工标签记录
        showDialogManualLabelBase: function () {
            var excellentTable = $('.risk-detail-info').find('table');
            var einferiorityTable = $('.suggest-info').find('table');
            excellentTable.html("");
            einferiorityTable.html("");
            $.post(urls.keyTalent, {keyTalentId: talentId}, function (result) {
                $(".modal-title").find('span').text(result.empName);
            });
            var excellentLabelContent = [];
            var inferiorityLabelContent = [];
            $.post(urls.historyTag, {keyTalentId: talentId}, function (result) {
                $.each(result, function (i, item) {
                    if (item.type == 1) {
                        excellentLabelContent.push(item);
                    } else if (item.type == 2) {
                        inferiorityLabelContent.push(item);
                    }
                });
                $.each(excellentLabelContent, function (i, obj) {
                    var typeName = "";
                    if (obj.actionType == 1) {
                        typeName = "新增标签";
                    } else {
                        typeName = "删除标签";
                    }
                    var createTime = obj.createTime;
                    if (createTime.length >= 10) {
                        createTime = createTime.substring(0, 10);
                    }
                    var tr = '<tr><td>' + obj.createEmpName + '</td><td>' + createTime + '</td><td>' + typeName + '：</td><td>' + obj.content + '</td></tr>';
                    excellentTable.append(tr);
                });
                $.each(inferiorityLabelContent, function (i, obj) {
                    var typeName = "";
                    if (obj.actionType == 1) {
                        typeName = "新增标签";
                    } else {
                        typeName = "删除标签";
                    }
                    var createTime = obj.createTime;
                    if (createTime.length >= 10) {
                        createTime = createTime.substring(0, 10);
                    }
                    var tr = '<tr><td>' + obj.createEmpName + '</td><td>' + createTime + '</td><td>' + typeName + '：</td><td>' + obj.content + '</td></tr>';
                    einferiorityTable.append(tr);
                });
                $(document).ready(function () {
                    $(".risk-detail-info table tr:even").css("background-color", "#e7eaec");
                    $(".suggest-info table tr:even").css("background-color", "#e7eaec");
                });
            });

        },
        //增加优势标签
        bindAddLableClickEvent: function () {
            var self = this;
            var flag = false;
            $('#addExcellentLable').unbind('click').click(function () {
                flag = true;
                $("#lableName").val("");
                $('#addLabelDetailModal').modal('show');
                self.showDialogAddLabelBase(flag);
            });
            $('#addInferiorityLable').unbind('click').click(function () {
                flag = false;
                $("#lableName").val("");
                $('#addLabelDetailModal').modal('show');
                self.showDialogAddLabelBase(flag);
            });
        },
        //增加优势、短板标签
        showDialogAddLabelBase: function (flag) {
            if (flag) {
                $('#addLabelDetailModal').find('h5').text("添加优势标签");
                $('#description').text("请添加“" + name + "”优势标签：");
                $.post(urls.keyTalent, {keyTalentId: talentId}, function (result) {
                    $('#description').text("请添加“" + result.empName + "”优势标签：");
                });
                $('#addDetermine').unbind("click").click(function () {
                    var lableName = $.trim($('#lableName').val());
					if(lableName==""){
                        showErrMsg("请输入至少一个优势标签");
                        $('#lableName').focus();
						return;
					}
                    if (!checkTag(lableName)) {

                        showErrMsg("单个标签长度不能超过10个字符");
                        return;
                    }
                    $.ajax({
                        type: "POST",
                        url: urls.addTag,
                        data: {tags: lableName, type: 1, keyTalentId: talentId},
                        success: function (result) {
                            $('#lableName').val('');
                            $("#addLabelDetailModal").modal('hide');
                            showErrMsg(result.msg);
                            setTimeout(function(){
                                
                                reloadfun.reloadInmodify();
    	    				},1000);
                        }
                    });
                });
            } else {
                $('#addLabelDetailModal').find('h5').text("添加短板标签");
                $.post(urls.keyTalent, {keyTalentId: talentId}, function (result) {
                    $('#description').text("请添加“" + result.empName + "”短板标签：");
                });
                $('#addDetermine').unbind("click").click(function () {
                    var lableName = $.trim($('#lableName').val());
                    if(lableName==""){
                        showErrMsg("请输入至少一个短板标签");
                        $('#lableName').focus();
                        return;
                    }
                    if (!checkTag(lableName)) {
                        showErrMsg("单个标签长度不能超过10个字符");
                        return;
                    }
                    $.ajax({
                        type: "POST",
                        url: urls.addTag,
                        data: {tags: lableName, type: 2, keyTalentId: talentId},
                        success: function (result) {
                            $('#lableName').val('');
                            $("#addLabelDetailModal").modal('hide');
                            showErrMsg(result.msg);
                            setTimeout(function(){
                                
                                reloadfun.reloadInmodify();
    	    				},1000);
                        }
                    });
                });
            }
        },
    }
    manualLabel.initData();

    /*
     * 核心激励要素
     */
    var incentiveFactorsLabel = {
        initData: function () {
            var self = this;
            $.post(urls.getKeyEncourage, {keyTalentId: talentId}, function (result) {
                var time = result.time;
                if (time.length >= 16) {
                    time = time.substring(0, 16);
                }
                $.each(result.content, function (i, item) {
                    $('#incentiveFactors').append('<span>' + item + '</span>&nbsp;&nbsp;');
                });
                var note = result.note;
                if(note!=null && note!=""){
                	$('#remarks').find('span').text("备注：" + note);
                }
                var optUser = result.optUser;
                if(optUser!=null  && optUser!=""){
                	$('#operationInformation').find('span').text("最近更新人：" + result.optUser + "，更新时间：" + time);
                }
            });
            this.bindUpdateLableClickEvent();
        },
        bindUpdateLableClickEvent: function () {
            var self = this;
            $('.incentiveFactors').find('.operat-follow-btn').unbind("click").click(function () {
                $('#coreLabelDetailModal').modal('show');
                self.showDialogUpdateLableBase();
            });
        },
        //修改核心激励要素
        showDialogUpdateLableBase: function (flag) {
            $('#coreLabelDetailModal').find('h5').text("修改核心激励要素");
            $.post(urls.getEncourage, {keyTalentId: talentId}, function (result) {
                var checkboxCore = $('#checkboxCore');
                checkboxCore.html("");
                $.each(result, function (i, item) {
                    var checkbox = '<br><input type="checkbox"'+(item.select>0?" checked=\"checked\"":"")+' name="encourage" value=' + item.encourageId + '>' + item.content;
                    checkboxCore.append(checkbox);
                });
                checkboxCore.append('<br>说明：<br><textarea name="explain" id="explain" rows="3" cols="47" class="text"></textarea>');
				$.post(urls.keyTalent, {keyTalentId: talentId}, function (result) {
					$('#descriptionCore').text("请添加“" + result.empName + "”的核心激励要素（建议不超过3个）：");
					$("#explain").val(result.note);
				});
            });
            $('#updateDetermine').unbind("click").click(function () {
                var explain = $('#explain').val();
                var obj = document.getElementsByName('encourage');
                var encourages = '';
                for (var i = 0; i < obj.length; i++) {
                    if (obj[i].checked) {
                        encourages += obj[i].value + ';';
                    }
                }
                $.ajax({
                    type: "POST",
                    url: urls.updateEncourage,
                    data: {encourages: encourages, note: explain, keyTalentId: talentId},
                    success: function (result) {
                        $('#explain').val('');
                        $("#coreLabelDetailModal").modal('hide');
                        showErrMsg(result.msg);
                        setTimeout(function(){
                            
                            reloadfun.reloadInmodify();
	    				},1000);
                    }
                });
            });
        }
    }
    incentiveFactorsLabel.initData();

    /*
     * 跟踪日志
     */
    var trackingLogLabel = {
        initData: function () {
            var self = this;
            var trackingLogplace = $('#trackingLog');
            var src = webRoot + "/assets/photo.jpg";
            var logContent = [];
            var flag = false;
            $.post(urls.getLog, {keyTalentId: talentId}, function (result) {

                $.each(result, function (i, item) {
                    var html = $("#trackingLog").find("div:nth-child(4)").html();
                    var createTime = item.createTime;
                    if (createTime.length >= 16) {
                        createTime = createTime.substring(0, 16)
                    }
                    if (i <= 2) {
                        html = html.replace("{logname}", item.createEmpName);
                        html = html.replace("{logdate}", createTime);
                        html = html.replace("{logcontent}", item.content);
                        html = html.replace("{workbreak}", ' style="word-wrap:break-word;word-break:break-all;"')
//                        var tr1 = '<tr><td rowspan="3"><img src=' + src + '></td><td class="operationLog">' + item.createEmpName + '&nbsp;&nbsp;&nbsp;&nbsp;' + createTime + '</td><br>';
//                        var tr2 = '<tr><td style="word-wrap:break-word;word-break:break-all;">' + item.content + '</td><br>';
//                        var tr3 = '';
                        if (customerId == item.createEmpId) {
//                            tr3 = '<tr><td align="right">最近更新时间：' + createTime + '&nbsp;&nbsp;|&nbsp;&nbsp;<a id=u' + item.keyTalentLogId + '>修改</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a id=d' + item.keyTalentLogId + '>删除</a></td><br></tr>';
                        	html=html.replace("{updatedate}","<span class=''>最近更新时间："+createTime+"  &nbsp;|&nbsp;<a id=u"+item.keyTalentLogId+">修改</a>&nbsp;|&nbsp;<a id=d"+item.keyTalentLogId+">删除</a></span>");
                        } else {
                        	html=html.replace("{updatedate}","<span>最近更新时间："+createTime+"</span>");
                        }
                        trackingLogplace.append("<div class='logconter row'>"+html+"<div class='logconter hide row'>");
                    } else {

//                        var tr1 = '<tr class="logMore hide"><td rowspan="3"><img src=' + src + '></td><td class="operationLog">' + item.createEmpName + '&nbsp;&nbsp;&nbsp;&nbsp;' + createTime + '</td><br>';
//                        var tr2 = '<tr class="logMore hide"><td>' + item.content + '</td><br>';
//                        var tr3 = '';

                        html = html.replace("{logname}", item.createEmpName);
                        html = html.replace("{logdate}", createTime);
                        html = html.replace("{logcontent}", item.content);
                        if (customerId == item.createEmpId) {
//                            tr3 = '<tr class="logMore hide"><td align="right">最近更新时间：' + createTime + '&nbsp;&nbsp;|&nbsp;&nbsp;<a id=u' + item.keyTalentLogId + '>修改</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a id=d' + item.keyTalentLogId + '>删除</a></td><br></tr>';
                        	html=html.replace("{updatedate}","<span class='logMore hide ' style=''>最近更新时间："+createTime+"&nbsp;&nbsp;|&nbsp;&nbsp;<a id=u"+item.keyTalentLogId+">修改</a>&nbsp;&nbsp;|&nbsp;&nbsp;	<a id=d"+item.keyTalentLogId+">删除</a></span>");
                        } else {
//                            tr3 = '<tr class="logMore hide"><td align="right">最近更新时间：' + createTime + '</td><br></tr>';
                        	html=html.replace("{updatedate}","<span class='logMore hide'>最近更新时间："+createTime+"</span>");
                        }
                        console.log("customerId"+customerId);
                        console.log("item.createEmpId"+item.createEmpId);
                        console.log("html"+html);
                        trackingLogplace.append("<div class='logconter row'>"+html+"</div>");
                        flag = true;
                    }
                    if (flag) {

                        $('#logMore').removeClass('hide');
                        $('#logReduce').addClass('hide');

                        $('#logMoreBtn').unbind("click").click(function () {
                            self.moreBtnLog();
                        });
                    }
                    $("#u" + item.keyTalentLogId).unbind("click").click(function () {
                        $('#logLabelDetailModal').modal('show');
                        self.showDialogtrackingLogBase(false, item.keyTalentLogId,item.content);
                    });
                    $("#d" + item.keyTalentLogId).unbind("click").click(function () {
                        $("#delDialog .modal-body span").text("跟踪记录");
                        $("#delDialog").modal('show');
                        $("#btnOk").unbind("click").on("click", function () {
                            $("#delDialog").modal('hide');
                            $.ajax({
                                type: "POST",
                                url: urls.deleteLog,
                                data: {keyTalentId: talentId, keyTalentLogId: item.keyTalentLogId},
                                success: function (result) {
                                    showErrMsg(result.msg);
                                    setTimeout(function(){
                                        
                                        reloadfun.reloadInmodify();
            	    				},1000);
                                }
                            });
                        });
                    });
                });
            });
            this.trackingLogLabelClickEvent();
        },
        trackingLogLabelClickEvent: function () {
            var self = this;
            $('.log-follow').find('.operat-follow-btn').unbind('click').click(function () {
                $('#logLabelDetailModal').modal('show');
                self.showDialogtrackingLogBase(true, 1,null);
            });
        },
        //跟踪日志
        showDialogtrackingLogBase: function (flag, keyTalentLogId,content) {
            if (flag) {
                $('#logLabelDetailModal').find('h5').text("添加跟踪记录");
                $.post(urls.keyTalent, {keyTalentId: talentId}, function (result) {
                    $('#descriptionLog').text("请添加你对“" + result.empName + "”的跟踪记录：");
                });
                $('#increaseDetermine').unbind("click").click(function () {
                    var increase = $.trim($('#increase').val());
                    if(increase==""){
                        showErrMsg("请输入跟踪记录!");
                        $('#increase').focus();
                        return;
                    }
                    $.ajax({
                        type: "POST",
                        url: urls.addLog,
                        data: {content: increase, keyTalentId: talentId},
                        success: function (result) {
                            $('#increase').val('');
                            $("#logLabelDetailModal").modal('hide');
                            showErrMsg(result.msg);
                            setTimeout(function(){
                                
                                reloadfun.reloadInmodify();
    	    				},1000);
                        }
                    });
                });
            } else {
                $('#logLabelDetailModal').find('h5').text("修改跟踪记录");
                $.post(urls.keyTalent, {keyTalentId: talentId}, function (result) {
                    $('#descriptionLog').text("请修改你对“" + result.empName + "”的跟踪记录：");
                });
                $('#increase').text(content);
                $('#increaseDetermine').unbind("click").click(function () {
                    var increase = $.trim($('#increase').val());
                    if(increase==""){
                        showErrMsg("请输入跟踪记录!");
                        $('#increase').focus();
                        return;
                    }
                    $.ajax({
                        type: "POST",
                        url: urls.updateLog,
                        data: {content: increase, keyTalentId: talentId, keyTalentLogId: keyTalentLogId},
                        success: function (result) {
                            $('#increase').val('');
                            $("#logLabelDetailModal").modal('hide');
                            showErrMsg(result.msg);
                            setTimeout(function(){
                                
                                reloadfun.reloadInmodify();
    	    				},1000);
                        }
                    });
                });
            }

        },
        moreBtnLog: function () {
            var self = this;
            $('.trackingLog .logMore').removeClass('hide');
            $('#logMore').addClass('hide');
            $('#logReduce').removeClass('hide');
            $('#logReduceBtn').unbind("click").click(function () {
                self.reduceBtnLog();
            });
        },
        reduceBtnLog: function () {
            var self = this;
            $('.trackingLog .logMore').addClass('hide');
            $('#logReduce').addClass('hide');
            $('#logMore').removeClass('hide');
            $('#logMoreBtn').unbind("click").click(function () {
                self.moreBtnLog();
            });
        }
    }
    var backbtn = {
    		bindbackBtn: function(){
    			$(".backbtn").unbind('click').click(function(){
    				$("#keytalentEval").addClass("hide");
    				$("#keytalentEval").html('');
    				$("#mainDiv").removeClass("hide");
    			});
    		}
    }
    backbtn.bindbackBtn();

    var reloadfun = {
    		reloadInmodify:function(){
    			var id = $("#keyTalentId").val();
    			$.get(urls.keytalentEval, {keyTalentId: id}, function (data) {
                    var $keytalenteval = $("#keytalentEval");
                    $keytalenteval.html("");
                    $keytalenteval.append(data);

    	        	$(this).parents(".modal.fade.in").model("hide");
                    
                });
    		}
    }

    $(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });

    function checkTag(tags) {
        if (tags.indexOf(",") > 0) {
            tags = tags.replace(/,/g, ";");
        }
        if (tags.indexOf("，") > 0) {
            tags = tags.replace(/，/g, ";");
        }
        if (tags.indexOf("\n") > -1) {
            tags = tags.replace(/\n/g, ";");
        }
        if (!(tags.indexOf(",") > 0 || tags.indexOf("，") > 0 || tags.indexOf("\n") > 0)) {
            tags = tags + ";";
        }
        var arr = tags.split(";");
        var bool = true;
        $.each(arr, function (i, o) {
            if (o.length > 10) {
                bool = false;
            }
        });
        return bool;
    }

    trackingLogLabel.initData();
//    $.zrw_resizeFrameSize();
});