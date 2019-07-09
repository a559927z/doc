require(['jquery', 'utils','underscore'], function ($) {

    //机构图一个框的数据
    function OrgData(data) {
        this.id = data.organizationId;
        this.parentDeptId = data.organizationParentId;
        this.hasChildren = data.hasChildren;
        this.name = data.organizationName;
        this.userName = data.userName;
        this.number = data.number;
        this.empCount = data.empCount;
        this.usableEmpCount = data.usableEmpCount;
        this.warnType = data.warnType;
        /*if(this.usableEmpCount>0&&this.usableEmpCount<=10){
         this.warnType=1;//预警
         }else if(this.usableEmpCount<0){
         this.warnType=2;//超编
         }*/
    }

    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        rootDataUrl: webRoot + "/orgChart/getRootOrgData.do", // 获取根节点数据
        childDataUrl: webRoot + '/orgChart/getChildOrgData.do'	//获取子节点数据
    };
    var treeSelector = null;

    $(win.document.getElementById('tree')).next().show();
    if (win.setCurrNavStyle) win.setCurrNavStyle();

    var organizationId = win.currOrganId;
    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
        initAll(organId);
    };
    var rootData2;
    var childData = [];

    initOrganTopoData();//获取机构拓扑图数据
    function initAll(id) {
        organizationId = id;
        rootData2 = null;
        childData = [];
        initOrganTopoData();//获取机构拓扑图数据
    }

    function initOrganTopoData() {
        //根节点数据
        $.ajax({
            url: urls.rootDataUrl,
            data: {
                organizationId: organizationId
            },
            dataType: 'json',
            success: function (data) {
                if (!$.isEmptyObject(data)) {
                    rootData2 = new OrgData(data);
                    if (data.hasChildren) {
                        getRootChildData();
                    } else {
                        var storage = new Storage('organDiv', 'showDetail');
                        var root = {
                            root: rootData2,
                            children: null
                        };
                        storage.init(root);
                    }
                    return;
                }
            },
            error: function (e) {
                $('#showUnResultMsg').show();
                return;
            }
        });
    }

    function getRootChildData() {
        //根节点的子节点数据
        $.ajax({
            url: urls.childDataUrl,
            data: {
                organizationId: organizationId
            },
            dataType: 'json',
            success: function (data) {
                if (!$.isEmptyObject(data)) {
                    for (var i = 0; i < data.length; i++) {
                        childData.push(new OrgData(data[i]));
                    }
                    var storage = new Storage('organDiv', 'showDetail');
                    var root = {
                        root: rootData2,
                        children: childData
                    };
                    storage.init(root);
                }
            },
            error: function (e) {
                $('#showUnResultMsg').show();
                return;
            }
        });
    }


    // 隐藏拓扑图弹出框
    $('#showDetail').hover(function (e) {

    }, function (e) {
        $(this).hide();
    });
    // 超编、预警
    var warnTypeEmune = ['2', '1'];
    // 节点圆角半径
    var r = 5;

    var topoOpt = {
        maxGap: 150,// 垂直方向元素之间的距离
        vertiGap: 60,// 垂直方向元素之间的距离
        blankWidth: 30,// 画布左右留白
        lineColor: '#83B4FE',
        hoverNodeColor: '#1E7ABA',
        boldLineWidth: 4,
        // 节点的字体大小和颜色(不包括根节点)
        normFontStyle: {fontSize: 13, color: 'black'},
        // 鼠标选中时 节点的字体大小和颜色(不包括根节点)
        hoverFontStyle: {fontSize: 13, color: 'white'},
        warnIconHeight: 8,
        fixedGap: 20,// 非平均分配时节点之间的间距
        drawTimeout: null,
        //节点的宽度
        normNodeWidth: 30,
        // 画布的宽度
        normCanvasWidth: 1100,
        maxCanvasWidth: 0,
        // 根节点字体大小
        rootFontSize: 18,
        rootNodeHeight: 40,
        rootHoverFont: {fontSize: 18, color: 'white'}
    };

    /*
     * 整体画图 organContainer : 包含图表的区域 dialogId ：鼠标悬停时显示的弹出框id
     */
    function Storage(organContainer, dialogId) {
        this.organContainer = organContainer;
        this.$dialog = $('#' + dialogId);
        // 根节点画笔
        this.rootCtx = '';
    }

    Storage.prototype = {
        init: function (data) {
            $('#' + this.organContainer).empty();
            if (!data.root) {
                $('#showUnResultMsg').show();
                return;
            }
            topoOpt.normCanvasWidth = 1100;
            $('#showUnResultMsg').hide();
            // 根结点数据
            var dataRoot = data.root;
            var orgName = dataRoot.name;
            var hasRootChild = !$.isEmptyObject(data.children);

            var orgId = dataRoot.id;
            var rootParentId = dataRoot.parentDeptId == null ? '' : dataRoot.parentDeptId;
            // 根节点
            var rootNodeWidth = orgName.length * (topoOpt.rootFontSize) + 80;
            var rootCtxArr = this.createLevel(topoOpt.normCanvasWidth, topoOpt.vertiGap + topoOpt.rootNodeHeight, '0px');
            this.rootCtx = rootCtxArr[0];
            var rootOrg = new Organ(rootCtxArr[0].canvas.width / 2, topoOpt.vertiGap / 2, rootNodeWidth,
                topoOpt.rootNodeHeight, orgId, rootParentId, orgName, true, '', '', true, dataRoot.warnType, hasRootChild,
                dataRoot.userName, dataRoot.number, dataRoot.empCount, dataRoot.usableEmpCount
            );
            rootOrg.draw(rootCtxArr[0], rootNodeWidth, topoOpt.rootNodeHeight, {
                fontSize: topoOpt.rootFontSize,
                color: 'black'
            });

            var rootCanvasOrg = new CanvasOrg(rootCtxArr[1], ['click', 'mousemove'], {
                color: topoOpt.lineColor,
                width: 1
            }, topoOpt.rootHoverFont);
            if (!hasRootChild) {
                rootCanvasOrg = new CanvasOrg(rootCtxArr[1], ['mousemove'], {
                    color: topoOpt.lineColor,
                    width: 1
                }, topoOpt.rootHoverFont);
            }

            this.buildEvent(rootCanvasOrg, [rootOrg]);
            this.rootCtx.font = topoOpt.normFontStyle.fontSize + 'px 微软雅黑';
            this.drawRightIcon();
            //如果根节点存在下级节点
            if (hasRootChild) {
                var ctxArr = this.createLevel('0', '0', '0px');
                var childrenOrg = this.expandNextLv(rootCtxArr[0].canvas, rootOrg, data.children, true, ctxArr);
                //加亮 根节点
                this.changeNodeState(rootCtxArr[1], rootOrg, topoOpt.rootHoverFont);
                rootCanvasOrg.organ = rootOrg;
                rootOrg.children = childrenOrg;
            }
        },

        drawRightIcon: function () {
            var canvas = this.rootCtx.canvas;
            var iconWidth = 30;
            var fontSize = topoOpt.normFontStyle.fontSize;
            var beginX = canvas.width - topoOpt.blankWidth - iconWidth * 2 - fontSize * 8;
            var y = 20;
            // ie下为定位为left ?
            this.rootCtx.textAlign = 'left';
            this.rootCtx.textBaseline = 'middle';
            drawWarnIcon(this.rootCtx, beginX, y, iconWidth, 'red');
            drawWarnIcon(this.rootCtx, beginX + iconWidth + fontSize * 2 + 24, y, iconWidth, '#BF9000');
            this.rootCtx.beginPath();
            this.rootCtx.fillStyle = '#666666';
            this.rootCtx.fillText('超编', beginX + 34, y + 6);
            this.rootCtx.fillText('预警', beginX + 60 + fontSize * 2 + 28, y + 6);
        },
        // 初始化页面时绘图和更新绘图
        initDraw: function (ctx, parentOrgan, organs, nodeHeight, average, fontStyle, lineStyle) {
            if (!organs) {
                return;
            }
            var len = organs.length;
            // 去掉两边空白后的画布大小
            var canvasWidth = ctx.canvas.width - topoOpt.blankWidth * 2;
            var parentX = parentOrgan.x;
            // 节点的宽度
            var nodeWidth = topoOpt.normNodeWidth;
            // 节点之间的间距
            var gap;
            // 节点和间距的总和(不包括左右留白)
            var totalWidth;
            // 层级之间的连线起始点（上部分）
            var lineBegin;
            var lineSpot2, lineSpot3;
            // 同级元素起始点横坐标
            var nodeX;

            if (average) {
                // 平均分配,给定最大宽度
                totalWidth = (topoOpt.maxGap + nodeWidth) * len - topoOpt.maxGap;
                if (totalWidth < canvasWidth) {
                    gap = topoOpt.maxGap;
                } else {
                    gap = (canvasWidth - nodeWidth * len) / (len - 1);
                    totalWidth = (gap + nodeWidth) * len - gap;
                }
                lineBegin = {x: parentX + parentOrgan.width / 2, y: 0};
                lineSpot2 = lineBegin.x - (totalWidth - nodeWidth) / 2 > 0 ? lineBegin.x - (totalWidth - nodeWidth) / 2 : (canvasWidth - totalWidth) / 2 + topoOpt.blankWidth + nodeWidth / 2;
                lineSpot3 = lineSpot2 + totalWidth - nodeWidth;
                nodeX = (canvasWidth - totalWidth) / 2 + topoOpt.blankWidth;
            } else {
                gap = topoOpt.fixedGap;
                lineBegin = {x: parentX + parentOrgan.width / 2, y: 0};
                totalWidth = (topoOpt.fixedGap + nodeWidth) * len - gap;
                if (totalWidth / 2 < parentX && totalWidth / 2 < canvasWidth - parentX) {
                    // 平均分布
                    lineSpot2 = lineBegin.x - (totalWidth - nodeWidth) / 2;
                    lineSpot3 = lineBegin.x + (totalWidth - nodeWidth) / 2;
                    nodeX = lineSpot2 - nodeWidth / 2;
                } else if (totalWidth / 2 > parentX && (nodeWidth * 3.5 + topoOpt.blankWidth) < parentX && canvasWidth - nodeWidth * 3.5 + topoOpt.blankWidth > totalWidth) {
                    // 靠左，留三个节点宽度
                    lineSpot2 = lineBegin.x - nodeWidth * 3;
                    lineSpot3 = lineBegin.x + totalWidth - nodeWidth * 4;
                    nodeX = lineSpot2 - nodeWidth / 2;
                } else if (totalWidth / 2 > parentX && canvasWidth - parentX > totalWidth / 2) {
                    // 靠左，最左边
                    nodeX = topoOpt.blankWidth;
                    lineSpot2 = nodeX + nodeWidth / 2;
                    lineSpot3 = lineSpot2 + totalWidth - topoOpt.normNodeWidth;
                } else if (parentX + totalWidth - topoOpt.blankWidth < canvasWidth && nodeWidth * 2 < canvasWidth - parentX) {
                    // 靠右，留两个节点宽度
                    lineSpot2 = lineBegin.x - totalWidth + nodeWidth * 2.5;
                    lineSpot3 = lineBegin.x + nodeWidth * 1.5;
                    nodeX = lineSpot2 - nodeWidth / 2;
                } else {
                    lineSpot3 = parentX + parentOrgan.width / 2;
                    lineSpot2 = lineSpot3 - totalWidth + nodeWidth / 2;
                    nodeX = lineSpot3 - totalWidth;
                }
                ;
            }
            // 修正因线宽造成的有缝连接
            lineSpot2 = lineSpot2 - lineStyle.width / 2;
            lineSpot3 = lineSpot3 + lineStyle.width / 2;
            var lineSpotY = lineBegin.y + topoOpt.vertiGap / 2;
            var nodeY = lineBegin.y + topoOpt.vertiGap;

            // 层级之间的连线(上半部分)
            ctx.save();
            ctx.beginPath();
            ctx.lineWidth = lineStyle.width;
            ctx.strokeStyle = lineStyle.color;
            ctx.moveTo(lineBegin.x, lineBegin.y);
            ctx.lineTo(lineBegin.x, lineSpotY);
            ctx.moveTo(lineSpot2, lineSpotY);
            ctx.lineTo(lineSpot3, lineSpotY);
            ctx.stroke();
            ctx.restore();
            return this.drawNode(ctx, organs, nodeHeight, parentOrgan, nodeX, nodeY, gap, nodeWidth, fontStyle, lineStyle);
        },
        drawNode: function (ctx, organs, nodeHeight, parentOrgan, x, y, gap, nodeWidth, fontStyle, lineStyle) {
            var newOrgans = new Array();
            for (var i = 0, len = organs.length; i < len; i++) {
                var newOrg = organs[i];
                // 如果没有设置过元素宽度，则表示该节点没有加载过，需初始化
                if (!newOrg.width) {
                    var tmpOrg = new Organ(x + nodeWidth / 2, y, nodeWidth, nodeHeight, newOrg.id,
                        newOrg.parentDeptId, newOrg.name, '', '', parentOrgan.x + parentOrgan.width / 2, false, newOrg.warnType,
                        newOrg.hasChildren, newOrg.userName, newOrg.number, newOrg.empCount, newOrg.usableEmpCount
                    );
                    newOrg = tmpOrg;
                }
                newOrg.draw(ctx, nodeWidth, nodeHeight, fontStyle, lineStyle);
                x += gap + nodeWidth;
                newOrgans.push(newOrg);
            }
            return newOrgans;
        },

        // 选中节点时，加粗层级的连线以及填充节点颜色
        clickNode: function (ctx, organ, hoverFontStyle) {
            this.changeNodeState(ctx, organ, hoverFontStyle);
            if (organ.isRoot) {
                // 如果是顶级节点，则节点头部没有层级连线
                return;
            }
            // 节点头部的层级连线
            ctx.save();
            var x = organ.x;
            var y = organ.y;
            ctx.beginPath();
            ctx.strokeStyle = topoOpt.lineColor;
            ctx.lineWidth = topoOpt.boldLineWidth;
            ctx.moveTo(x + organ.width / 2, y);
            ctx.lineTo(x + organ.width / 2, y - topoOpt.vertiGap / 2);
            ctx.lineTo(organ.parentCenter, y - topoOpt.vertiGap / 2);
            ctx.lineTo(organ.parentCenter, y - topoOpt.vertiGap);
            ctx.stroke();
            ctx.restore();
        },

        buildEvent: function (canvasOrgan, organs) {
            var storage = this;
            var hoverCtx = canvasOrgan.hoverCtx;
            var hoverCanvas = hoverCtx.canvas;
            // 画布在页面的坐标
            var canvasPos = $(hoverCanvas).offset();
            var cursorTimeout = canvasOrgan.cursorTimeout;
            if ($.inArray('click', canvasOrgan.events) != -1) {
                $(hoverCanvas).bind('click', function (e) {
                    if (!organs) {
                        return;
                    }
                    // 鼠标在画布上的坐标
                    var x = e.pageX - canvasPos.left;
                    var y = e.pageY - canvasPos.top;
                    for (var i = 0, len = organs.length; i < len; i++) {
                        var organ = organs[i];
                        // 如果为选中节点
                        if (organ.isScope(x, y)) {
                            if (!organ.hasChildren) {
                                return;
                            }
                            storage.clearCanvas(hoverCtx);
                            var curmaxCanvasWidth = 0;
                            $.each($(hoverCanvas).prevAll(), function (i, n) {
                                curmaxCanvasWidth = curmaxCanvasWidth > n.width ? curmaxCanvasWidth : n.width;
                            });
                            topoOpt.normCanvasWidth = curmaxCanvasWidth;
                            $(hoverCanvas).nextAll().remove();
                            if (canvasOrgan.organ && organ.id === canvasOrgan.organ.id) {
                                // 如果该节点已经展开，则清空选中节点
                                ////调整画布div区域大小
                                var cs = $('canvas');
                                var h2 = 0;
                                for (var a = 0; a < cs.length;) {
                                    h2 += cs[a].height;
                                    a = a + 2;
                                }
                                $('#organArea').height(h2);
                                //console.log('设置div大小为：'+h2);
                                canvasOrgan.organ = null;
                                break;
                            }

                            // 将当前选中的节点保存起来
                            canvasOrgan.organ = organ;
                            // 填充选中节点的颜色
                            storage.clickNode(hoverCtx, organ, canvasOrgan.hoverFontStyle);
                            // organ.drawFont(hoverCtx,topoOpt.hoverFontStyle);
                            // 判断是否添加过子节点,如果已经添加过，则不再查数据库
                            var ctxArr = storage.createLevel('0', '0', '0px');
                            if (organ.isChildAdded) {
                                var children = organ.children;
                                storage.renderNext(hoverCanvas, organ, children, ctxArr);
                            } else {
                                $.ajax({
                                    url: urls.childDataUrl,
                                    data: {
                                        organizationId: organ.id
                                    },
                                    dataType: 'json',
                                    success: function (data) {
                                        var childs = [];
                                        if (!$.isEmptyObject(data)) {
                                            for (var i = 0; i < data.length; i++) {
                                                childs.push(new OrgData(data[i]));
                                            }
                                        }
                                        storage.renderNext(hoverCanvas, organ, childs, ctxArr);
                                    },
                                    error: function () {
                                        $('#showUnResultMsg').show();
                                        return;
                                    }

                                });
                            }
                            break;
                        }
                        ;
                    }
                    ;
                });// end click
            }
            if ($.inArray('mousemove', canvasOrgan.events) != -1) {
                $(hoverCtx.canvas).bind('mousemove', function (e) {
                    clearTimeout(cursorTimeout);
                    cursorTimeout = setTimeout(function () {
                        $(hoverCtx.canvas).css('cursor', '');
                    }, 10);
                    if (!organs) {
                        return;
                    }
                    // 鼠标在画布上的坐标
                    var x = e.pageX - canvasPos.left;
                    var y = e.pageY - canvasPos.top;
                    storage.clearCanvas(hoverCtx);
                    clearTimeout(topoOpt.drawTimeout);
                    // 是否显示弹出框
                    var isDetailShow = false;
                    for (var i = 0, len = organs.length; i < len; i++) {
                        var organ = organs[i];
                        if (organ.isScope(x, y)) {
                            storage.$dialog.empty();
                            storage.showDialog(organ, x, y, hoverCanvas, canvasPos);
                            isDetailShow = true;
                            if (!organ.hasChildren) {
                                break;
                            }
                            hoverCtx.fillStyle = topoOpt.lineColor;
                            storage.changeNodeState(hoverCtx, organ, canvasOrgan.hoverFontStyle);
                            clearTimeout(cursorTimeout);
                            $(hoverCtx.canvas).css('cursor', 'pointer');
                            break;
                        }
                    }
                    if (!isDetailShow) {
                        storage.$dialog.stop().hide();
                    } else {
                        topoOpt.drawTimeout = setTimeout(function () {
                            storage.$dialog.stop().show();
                        }, 200);

                    }

                    if (!$.isEmptyObject(canvasOrgan.organ) && canvasOrgan.organ != '') {
                        storage.clickNode(hoverCtx, canvasOrgan.organ, canvasOrgan.hoverFontStyle);
                    }

                });
            }// end mousemove
            $(hoverCanvas).bind('mouseout', function (e) {
                storage.clearCanvas(hoverCtx);
                if (!$.isEmptyObject(canvasOrgan.organ)) {
                    storage.clickNode(hoverCtx, canvasOrgan.organ, canvasOrgan.hoverFontStyle);
                }
            });
        },
        // 显示 提示加载数据的弹出框
        showLoadingTip: function (canvasPos, organ, y) {
            this.$dialog.empty().css({
                width: '120px',
                top: canvasPos.top + y - 10,
                left: organ.x + organ.width + canvasPos.left
            });
            this.$dialog.append('<label class="center-text">数据加载中，请稍候...</label>');
        },
        // 显示弹出框
        showDialog: function (organ, x, y, hoverCanvas, canvasPos) {
            this.$dialog.empty();
            this.$dialog.css('width', '260px');
            this.$dialog.css('min-height', '90px');
            var style = 'normal';
//			if(organ.usableEmpCount<=0){
//				style= 'warn';
//			}else{
//				style= 'normal';
//			}
            if (organ.warnType == warnTypeEmune[0]) {
                style = 'overstaff';
            } else if (organ.warnType == warnTypeEmune[1]) {
                style = 'warning';
            }
            var userName;
            var tmp;
            if (organ.userName && !$.isEmptyObject(organ.userName)) {
                tmp = organ.userName.split(',');
                if (tmp.length > 2) {
                    userName = tmp[0] + ',' + tmp[1] + '...';
                } else {
                    userName = organ.userName;
                }
            }

//			if(this.warnType == warnTypeEmune[0]){
//				drawWarnIcon(ctx,this.x,this.y,this.width,'red');
//			}else if(this.warnType == warnTypeEmune[1]){
//				drawWarnIcon(ctx,this.x,this.y,this.width,'#BF9000');
//			}
            var html = "<div><span>编制数：</span><span class='normal'>" + formatToZero(organ.number) + "</span><span>&nbsp&nbsp&nbsp&nbsp可用编制数：</span><span  class='";
            html += style + "'>" + formatToZero(organ.usableEmpCount) + "</span></div>";
            html += "<div><span>在岗人数：</span><span class='normal'>" + formatToZero(organ.empCount) + "</span></div>";
            html += "<div><span>负责人：</span><span class='normal'>" + formatToDash(userName) + "</span></div>";
            // console.log(html);
            var content = $(html);

            // 弹出框底部
            this.$dialog.append(content);
            // 如果节点位置超过画布的2/3，则弹出框放在左边
            var dialogLeft = organ.x < hoverCanvas.width / 3 * 2 ? organ.x + organ.width + canvasPos.left : organ.x - this.$dialog.width() + canvasPos.left;
            var dialogTop = canvasPos.top + $(hoverCanvas).height() - topoOpt.vertiGap / 2 - this.$dialog.height();
            this.$dialog.css({top: dialogTop, left: dialogLeft});
        },

        renderNext: function (hoverCanvas, organ, children, ctxArr) {
            // 如果没有子节点，直接返回
            if (!children || $.isEmptyObject(children)) {
                return;
            }
            organ.children = this.expandNextLv(hoverCanvas, organ, children, organ.isRoot, ctxArr);
        },
        // 鼠标悬停或点击时，改变节点字体颜色，鼠标样式
        changeNodeState: function (hoverCtx, organ, fontStyle) {
            if (!organ || organ == '') {
                return;
            }
            // 给节点填充颜色
            hoverCtx.fillStyle = topoOpt.hoverNodeColor;
            hoverCtx.beginPath();
            drawRect(organ.x, organ.y, organ.width, organ.height, hoverCtx);
            hoverCtx.closePath();
            hoverCtx.fill();
            organ.drawFont(hoverCtx, fontStyle);
            organ.checkWarn(hoverCtx);
        },

        // 清空画布
        clearCanvas: function (ctx) {
            ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
        },
        /*
         * 展开下一级 parentCanvas 上一级的画布 parentOrgan 上一级机构 data 机构数据，数组结构 average
         * 是否平均分配（只有第二级节点为平均分配画布空间）
         */
        expandNextLv: function (parentCanvas, parentOrgan, data, average, ctxArr) {
            if (data.length * (topoOpt.normNodeWidth + topoOpt.fixedGap) + topoOpt.blankWidth * 2 > topoOpt.normCanvasWidth) {
                topoOpt.maxCanvasWidth = data.length * (topoOpt.normNodeWidth + topoOpt.fixedGap) + parentOrgan.x + topoOpt.blankWidth * 2;
                topoOpt.normCanvasWidth = topoOpt.normCanvasWidth > topoOpt.maxCanvasWidth ? topoOpt.normCanvasWidth : topoOpt.maxCanvasWidth;
            }
            this.rootCtx.font = topoOpt.normFontStyle.fontSize + 'px 微软雅黑';
            var nodeHeight = this.getMaxFontLen(data, this.rootCtx) * 15 + 20;
            var canvasHeight = nodeHeight + topoOpt.vertiGap * 1.5;
            var canvasTop = $(parentCanvas).position().top + parentCanvas.height - topoOpt.vertiGap / 2;
            for (var i = 0; i < ctxArr.length; i++) {
                this.setCanvasPos(ctxArr[i].canvas, topoOpt.normCanvasWidth, canvasHeight, canvasTop + 'px');
            }
            var canvasOrg = new CanvasOrg(ctxArr[1], ['click', 'mousemove'], {
                color: topoOpt.lineColor,
                width: 1
            }, topoOpt.hoverFontStyle);
            var orgs = this.initDraw(ctxArr[0], parentOrgan, data, nodeHeight, average, topoOpt.normFontStyle, canvasOrg.lineStyle);
            this.buildEvent(canvasOrg, orgs);
            var placeHolderTop = $(ctxArr[1].canvas).offset().top - $('#organArea').offset().top;
            var placeHolderHeight = $(ctxArr[1].canvas).height() - topoOpt.vertiGap / 2;
            $('#placeHolder').css({top: placeHolderTop, height: placeHolderHeight});
            return orgs;
        },

        // 获取同级中最长的机构名字
        getMaxFontLen: function (data, ctx) {
            var maxFontLen = 0;
            var maxFontWidth = topoOpt.normNodeWidth - 12;
            for (var i = 0, dataLen = data.length; i < dataLen; i++) {
                var name = data[i].name;
                var strs = '';
                var curStrsWidth = 0;
                var strWidth = 0;
                var regrex = /\s/;
                // 名字所占的行数
                var nameLen = 0;
                for (var j = 0, len = name.length; j < len; j++) {
                    var newChar = name.substr(j, 1);
                    // 按照当前字体对给定的文字进行测量 metrics = context.measureText(text)
                    // 调用width属性
                    strWidth = ctx.measureText($.trim(newChar)).width;
                    curStrsWidth += strWidth;
                    if (curStrsWidth > maxFontWidth || regrex.test(newChar)) {
                        strs = newChar;
                        curStrsWidth = strWidth;
                        nameLen++;
                    } else {
                        strs += newChar;
                    }

                }
                maxFontLen = nameLen > maxFontLen ? nameLen : maxFontLen;
            }
            return ++maxFontLen;
        },
        // 创建组织机构级别，返回一个数组，共两个ctx，0是机构显示层，1是鼠标悬浮层
        createLevel: function (width, height, top) {
            var ctxArr = new Array();
            for (var i = 0; i < 2; i++) {
                ctxArr.push(this.createCanvas(width, height, top));
            }
            return ctxArr;
        },
        // 创建画布
        createCanvas: function (width, height, top) {
            var canvas = document.createElement('canvas');
            $('#' + this.organContainer).append(canvas);
            this.setCanvasPos(canvas, width, height, top);
            this.supportIE(canvas);
            return canvas.getContext('2d');
        },
        setCanvasPos: function (canvas, width, height, top) {
            canvas.setAttribute('width', width);
            canvas.setAttribute('height', height);
            // 减少reflow
            var canvasStyle = 'width:' + width + 'px;height:' + height + 'px;position:absolute;left:0px;top:' + top;
            canvas.style.cssText += canvasStyle;
            //调整画布div区域大小
            var h = $('#organArea').height();
            $('#organArea').height(height / 2 + h);
            //console.log('原大小:'+h+'\t增加：'+(height/2));
        },
        // 支持动态创建canvas
        supportIE: function (canvas) {
            if (typeof FlashCanvas != "undefined") {
                FlashCanvas.initElement(canvas);
            }
        }

    };
    // 用一个对象保存悬浮的画布、画布的事件、连线粗细、点击选中的节点
    function CanvasOrg(hoverCtx, events, lineStyle, hoverFontStyle, organ) {
        this.hoverCtx = hoverCtx;
        this.events = events;
        this.lineStyle = lineStyle;
        this.hoverFontStyle = hoverFontStyle;
        // 点击选中的节点
        this.organ = organ;
        // this.fontStyle = fontStyle;
        // 子画布
        this.cursorTimeout = '';

    }


    /**
     * x,y 元素在画布的横纵坐标
     * width,height 元素的宽高
     * id 机构的id
     * name 机构的名字
     * isRoot 是否根节点
     * children 子节点
     * parentCenter 父级节点的中间坐标，层级间连线的标记
     * isChildAdded 是否添加过子节点
     * warnType 提醒类型（超编，预警）
     * addStatus 查弹出框内容的状态（0：未查 1：已查 ，-1:查询中）
     * detail 弹出框的内容
     */

    function Organ(x, y, width, height, id, parentId, name, isRoot, children, parentCenter,
                   isChildAdded, warnType, hasChildren, userName, number, empCount, usableEmpCount, addStatus) {
        this.x = x - width / 2;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.isRoot = isRoot || false;
        this.children = children || [];
        this.parentCenter = parentCenter;
        this.isChildAdded = isChildAdded;
        this.warnType = warnType;
        this.hasChildren = hasChildren;
        this.userName = userName;
        this.number = number;
        this.empCount = empCount;
        this.usableEmpCount = usableEmpCount;
        this.addStatus = addStatus;
    }

    Organ.prototype = {
        // fontStyle 应该传两个属性{fontSize：'',fontColor:''}
        draw: function (ctx, nodeWidth, nodeHeight, fontStyle, lineStyle) {
            this.height = nodeHeight;
            this.width = nodeWidth;
            ctx.save();
            ctx.beginPath();
            drawRect(this.x, this.y, this.width, this.height, ctx);
            ctx.strokeStyle = topoOpt.lineColor;
            ctx.fillStyle = '#E8F1F8';
            ctx.fill();
            ctx.stroke();
            // ctx.lineWidth = 1;
            // 填充文字
            this.drawFont(ctx, fontStyle);
            this.checkWarn(ctx);
            if (this.isRoot) {
                return;
            }
            ctx.save();
            ctx.lineWidth = lineStyle.width;
            ctx.strokeStyle = lineStyle.color;
            // 层级之间的连线（下部分）
            ctx.moveTo(this.x + this.width / 2, this.y);
            ctx.lineTo(this.x + this.width / 2, this.y - topoOpt.vertiGap / 2);
            ctx.closePath();
            ctx.stroke();
            ctx.restore();
        },
        // 检查是否超编或预警
        checkWarn: function (ctx) {
            if (this.warnType == warnTypeEmune[0]) {
                drawWarnIcon(ctx, this.x, this.y, this.width, 'red');
            } else if (this.warnType == warnTypeEmune[1]) {
                drawWarnIcon(ctx, this.x, this.y, this.width, '#BF9000');
            }
        },

        // 给节点添加文字
        drawFont: function (ctx, fontStyle) {
            ctx.save();
            ctx.beginPath();
            // ctx.font='15px sans-serif';
            var fontSize = fontStyle.fontSize;
            ctx.font = fontSize + 'px 微软雅黑';
            ctx.fillStyle = fontStyle.color;
            // ie8文字默认排列方式就是 right ，middle，且textAlign、textBaseline属性设置无效
            // 文字对齐方式（左右） start; end；left；right; center (start,end
            // 与canvas风格的direction定义相关；)
            ctx.textAlign = 'right';
            // 对齐基线（上下） alphabetic ；top; bottom; middle; hanging; ideographic
            // （基线）
            ctx.textBaseline = 'middle';
            // 文字占的最大宽度
            var maxFontWidth = this.width - 12;

            // 文字起始坐标 (顶级节点上下居中对齐)
            var fontBeginY = this.isRoot == true ? this.height / 2 + this.y : this.y + 20;
            var name = this.name;
            var strs = '';
            var curStrsWidth = 0;
            var strWidth = 0;
            var newChar;
            var regrex = /\s/;

            for (var i = 0, len = name.length; i < len; i++) {
                newChar = name.substr(i, 1);
                // 按照当前字体对给定的文字进行测量 metrics = context.measureText(text)
                // 调用width属性
                strWidth = ctx.measureText($.trim(newChar)).width;
                curStrsWidth += strWidth;
                if (curStrsWidth > maxFontWidth || regrex.test(newChar)) {
                    // fillText 实心 strokeText 空心
                    ctx.fillText(strs, this.x + (this.width + curStrsWidth - strWidth) / 2, fontBeginY);
                    strs = newChar;
                    curStrsWidth = strWidth;
                    fontBeginY += fontSize + 2;
                } else {
                    strs += newChar;
                }
                if (i === name.length - 1) {
                    ctx.fillText(strs, this.x + (this.width + curStrsWidth) / 2, fontBeginY);
                }
            }
            ctx.restore();
        },

        // 判断鼠标是否在元素上
        isScope: function (x, y) {
            var nodeWidth = this.x + this.width;
            var nodeHeight = this.y + this.height;
            return x >= this.x && x <= nodeWidth && y >= this.y && y <= nodeHeight;
        }
    };

    // 画“超编、预警”的提示图标
    var drawWarnIcon = function (ctx, x, y, width, iconColor) {
        ctx.fillStyle = iconColor;
        ctx.strokeStyle = iconColor;
        ctx.beginPath();
        //if($.browser.msie&&($.browser.version == "8.0")){
        //	ctx.rect(x,y,width,topoOpt.warnIconHeight);
        //}else{
        ctx.moveTo(x + r, y);
        ctx.arcTo(x + width, y, x + width, y + topoOpt.warnIconHeight, r);
        ctx.lineTo(x + width, y + topoOpt.warnIconHeight);
        ctx.lineTo(x, y + topoOpt.warnIconHeight);
        ctx.arcTo(x, y, x + width, y, r);
        //}
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
        return ctx;
    };

    // 画矩形
    function drawRect(x, y, w, h, ctx) {
        // 如果是ie8，则不使用圆角
        //if($.browser.msie&&($.browser.version == "8.0")){
//			ctx.rect(x,y,w,h);
//		}else{
        roundRect(x, y, w, h, r, ctx);
//		}
    }

    // 圆角矩形
    function roundRect(x, y, w, h, r, ctx) {
        if (w < 2 * r) r = w / 2;
        if (h < 2 * r) r = h / 2;
        ctx.beginPath();
        ctx.moveTo(x + r, y);
        ctx.arcTo(x + w, y, x + w, y + h, r);
        ctx.arcTo(x + w, y + h, x, y + h, r);
        ctx.arcTo(x, y + h, x, y, r);
        ctx.arcTo(x, y, x + w, y, r);
        // this.arcTo(x+r, y);
        ctx.closePath();
        return ctx;
    }


    // 格式化数字 ,如果为空，则返回0
    function formatToZero(num) {
        return num == null || num == undefined ? 0 : Tc.formatNumber(num);
    }

    // 格式化数字 ,如果为空，则返回‘-’
    function formatToDash(num) {
        return num == null || num == undefined ? '-' : Tc.formatNumber(num);
    }
});