/**
 * Created by dcli on 2016/1/18 014.
 */

require(['jquery', 'bootstrap','jquery-pin', 'ztree', 'select2', 'scrollTo', 'underscore', 'messenger', 'riskList'], function ($) {
    var vUrl = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        warn: vUrl + "/keyTalents/getRunoffRiskWarnEmp", //离职风险预警 查询
        focuse: vUrl + "/keyTalents/getFocuseEmp",//我关注的人员查询
        newUpdate: vUrl + "/keyTalents/getLastRefreshEmp",//最近更新人员 查询
        addFocuse: vUrl + "/keyTalents/addFocuseEmp",//添加关注
        removeFocuse: vUrl + "/keyTalents/removeFocuseEmp",//取消关注
        deleteKeyTalent: vUrl + "/keyTalents/deleteKeyTalent",//删除关键人才
        addKeyTalent: vUrl + "/keyTalents/addKeyTalent",//添加关键人才
        category: vUrl + "/keyTalents/getKeyTalentTypePanel",//分类
        categoryList: vUrl + "/keyTalents/getKeyTalentByType",//分类列表
        org: vUrl + "/keyTalents/getKeyTalentOrganPanel",//部门
        orgList: vUrl + "/keyTalents/getKeyTalentByOrgan",//部门列表
        cup: vUrl + "/keyTalents/getKeyTalentEncouragePanel",//激励要素
        cupList: vUrl + "/keyTalents/getKeyTalentByEncourage", //激励要素列表
        searchTalent: vUrl + "/keyTalents/getKeyTalentByName", //关键人才
        selectTalent: vUrl + "/keyTalents/getNotKeyTalentByName", //非关键人才
        keytalentEval: vUrl + "/keyTalents/toKeyTalentsEvaluateView"
    };

    $(win.document.getElementById('tree')).next().hide();
    var showErrMsg = function (content) {
        Messenger().post({
            message: content,
            type: 'error',
            hideAfter: 2
        });
    };

    //初始化'加入对比'
    var stackObject;

    function loadStack() {
        var win = window != top ? top.window : window;
        if (win.stackObject) {
            stackObject = win.stackObject;
        }
        if (stackObject && stackObject.bottomStock('getPersonIds') != '')
            stackObject.bottomStock('hideFrame', 0);
        else
            stackObject.bottomStock('hideFrame', 1);
    }

    loadStack();

    //格式化日期
    var dataObj={
        padZero:function(num, len) {
            len = len || 2;

            var ret = "" + num;
            while(ret.length < len) {
                ret = "0" + ret;
            }
            return ret;
        },
        formatDate:function(date, pattern) {
            return date.substring(0,10);
            //date格式不规范，火狐不支持new Date(date)，使用截断处理
            var jsDate=new Date(date);
            var self=this;
            if (!jsDate) {
                return "";
            }
            pattern = pattern || 'Y-m-d';
            var y = jsDate.getFullYear();
            var m = self.padZero(jsDate.getMonth() + 1);
            var d = self.padZero(jsDate.getDate());

            return pattern.replace('Y', y).replace('m', m).replace('d', d);
        }
    }

    //初始化页面
    var pageObj = {
        init: function () {
            var self = this;
            self.categoryInit();
            self.cupInit();
            self.searchInit();
        },
        //分类
        categoryInit: function () {
            var self = this;
            var selectedId = $("#talentCateDetail .man .active").data("id");
            $.post(urls.category, function (da) {
                var html = [];
                $.each(da, function (i, item) {
                    html.push('<div data-id="' + item.id + '" class="clearfix cate">');
                    html.push('<div class="name" title="">' + item.name + '</div>');
                    html.push('<div class="num" title="">' + item.count + '</div>');
                    html.push('</div>');
                });
                $("#talentCateDetail .man").html(html.join(''));
                //绑定事件
                cateObj.manCate();

                //同时始始化添加关键人才分类
                addTalentObj.categorySelectInit(da);

                //如果原来有选中状态，刷新后也选中
                if(selectedId){
                    $("#talentCateDetail .man").find("[data-id='"+selectedId+"']").click();
                }
            });
        },
        //激励要素
        cupInit: function () {
            var selectedId = $("#talentCateDetail .cup .active").data("id");
            $.post(urls.cup, function (da) {
                var html = [];
                $.each(da, function (i, item) {
                    html.push('<div data-id="' + item.id + '" class="clearfix cate">');
                    html.push('<div class="name" title="">' + item.name + '</div>');
                    html.push('<div class="num" title="">' + item.count + '</div>');
                    html.push('</div>');
                });
                $("#talentCateDetail .cup").html(html.join(''));
                //绑定事件
                cateObj.cupCate();

                //如果原来有选中状态，刷新后也选中
                if(selectedId){
                    $("#talentCateDetail .cup").find("[data-id='"+selectedId+"']").click();
                }
            });
        },
        //搜索
        searchInit: function () {
            var self = this;
            $("#txtSearchName").unbind("keyup").keyup(function (e) {
                if (e.keyCode == 13) {
                    var v = $(this).val();
                    self.searchClick(v);
                }
            });
        },
        searchClick: function (v) {
            if (v != "") {
                unSelect(false);
                showContentIndex = 4;
                var page = 1;
                //得到搜索结果后，为下次分页准备
                $.post(urls.searchTalent, {keyName: v, page: page, rows: 30}, function (da) {
//                    console.log(da)
                    viewContentObj.searchContent(da.rows, {page: page, total: da.records,pageSize:30, title: v});
                });
            } else {
                showErrMsg("请输入员工姓名/ID进行搜索");
            }
        }
    };
    pageObj.init();


    //取消选中
    var unSelect = function (isOrg) {
        $("#" + tabObj.id + " .tabli, #" + cateObj.idDetail + " .man .cate, #" + cateObj.idDetail + " .cup .cate").removeClass("active");
        if (!isOrg)
            orgObj.zTreeObj.cancelSelectedNode();
    };

    //0预警；1分类；2组织架构；3激励；4搜索
    var showContentIndex = 0;
    //显示内容
    var showContent = function (indexShow) {
        //showContentIndex=indexShow;
        var ids = ["#tTabContent", "#tCategoryContent", "#tOrgContent", "#tCupContent", "#tSearchContent"];
        var hide = [], show = [];
        $.each(ids, function (i, item) {
            if (i == indexShow) {
                show.push(item);
            } else {
                hide.push(item);
            }
        });
        $(show.join(",")).removeClass("hide");
        $(hide.join(",")).addClass("hide");

        //设置显示方式
        oprationObj.setShowType();
    };

    //Tab
    var tabObj = {
        id: "talentTab",
        init: function () {
            var self = this;
            $("#" + self.id).find(".tabli").on("click", function () {
                showContentIndex = 0;
                //取消其他选中
                if (!$("#" + self.id + " .tabli").hasClass("active")) {
                    unSelect(false);
                }

                //显示
                showContent(0);

                //重置标题位置
                oprationObj.resetHeight();
                //滚动
                var index = $(this).data("index");
                var top = 0;
                switch (index) {
                    case 1:
                    {
                        top = oprationObj.attentionHeight;
                        break;
                    }
                    case 2:
                    {
                        top = oprationObj.newHeight;
                        break;
                    }
                }
                var obj = $(this);

                //自动滚动到顶
//                console.log($(this).attr("scrollposition"));
                $(window).scrollTop($("#"+$(this).attr("scrollposition")).offset().top);
                setTimeout(function () {
                    $("#talentTab .tabli").removeClass("active");
                    obj.addClass("active");
                }, 200);

                //绑定事件
                oprationObj.showType();
            });
        }
    };
    tabObj.init();


    //分类
    var cateObj = {
        id: "talentCate",
        idDetail: "talentCateDetail",
        path: vUrl + "/assets/img/talent/",
        imgs: ["talent_man.png", "talent_man_selected.png", "talent_org.png", "talent_org_selected.png", "talent_cup.png", "talent_cup_selected.png"],
        init: function () {
            var self = this;
            $("#" + self.id + " .avatar").find("img").on("click", function () {
                //图标
                var n = [0, 2, 4];
                var index = $(this).data("index");
                n[index] = n[index] + 1;
                $("#" + self.id + " .avatar img").each(function (i) {
                    $(this).attr("src", self.path + self.imgs[n[i]]);
                });
                //线
                $("#" + self.id + " .direct div").removeClass("active0").removeClass("active1").removeClass("active2").addClass("active" + index);
                //类
                $("#" + self.idDetail + " .row").each(function (i) {
                    if (i == index) {
                        $(this).removeClass("hide");
                    } else {
                        if (!$(this).hasClass("hide"))
                            $(this).addClass("hide");
                    }
                });
            });
            self.manCate();
            self.cupCate();
        },
        initCate: function (obj, callback) {
            obj.on("click", function () {
                unSelect(false);
                $(this).parent().find(".active").removeClass("active");
                $(this).addClass("active");
                callback.call(new Object(), this);
            });
        },
        //按人才分类查看
        manCate: function () {
            var self = this;
            var obj = $("#" + this.idDetail + " .man .cate");
            this.initCate(obj, function (t) {
                showContentIndex = 1;
                viewContentObj.cateContent({
                    id: $(t).data("id"),
                    title: $(t).find(".name").text(),
                    total: $(t).find(".num").text(),
                    page: 1,
                    scrollLoad: false
                });
            });
        },
        //按激励要素查看
        cupCate: function () {
            var self = this;
            var obj = $("#" + this.idDetail + " .cup .cate");
            self.initCate(obj, function (t) {
                showContentIndex = 3;
                viewContentObj.cupContent({
                    id: $(t).data("id"),
                    title: $(t).find(".name").text(),
                    total: $(t).find(".num").text(),
                    page: 1,
                    scrollLoad: false
                });
            });
        }
    };
    cateObj.init();

    //组织架构树
    var orgObj = {
        zTreeObj: null,
        optionObj: {
            view: {
                showIcon: false,
                showLine: true,
                selectedMulti: false,
                addDiyDom: function (treeId, treeNode) {
                    var aObj = $("#" + treeNode.tId + "_a");
                    var editStr = '<span style="color: #aaa;">（' + treeNode.count + '）</span>';
                    aObj.append(editStr);
                }
            },
            callback: {
                onClick: function (e, treeId, nodeId) {
                    showContentIndex = 2;
                    unSelect(true);
                    viewContentObj.orgContent({
                        id: nodeId.id,
                        title: nodeId.name,
                        total: nodeId.count,
                        page: 1,
                        scrollLoad: false
                    });
                }
            },
            data: {
                key: {
                    name: 'name'
                },
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pid",
                    rootPId: -1
                }
            }

        },
        init: function () {
            var self = this;
            var nodes=orgObj.zTreeObj?orgObj.zTreeObj.getSelectedNodes():null;
            $.post(urls.org, function (da) {
                var zTreeNodes = da;
                //默认只展开1、2级
                var rootPid = self.optionObj.data.simpleData.rootPId;
                $.each(zTreeNodes, function (i) {
                    zTreeNodes[i].open = zTreeNodes[i].pid == rootPid;
                });
                self.zTreeObj = $.fn.zTree.init($("#tree"), self.optionObj, zTreeNodes);

                //如果有选中项，刷新后重新选中
                if(nodes&&nodes.length>0){
                    var zt=orgObj.zTreeObj;
                    zt.selectNode(nodes[0]);
                    zt.setting.callback.onClick(null, zt.treeId, nodes[0]);//调用事件
                }
            });

        }
    };
    orgObj.init();

    //输出html
    var htmlObj = {
        tTitleId: "tTitleTemplate",
        Row: {
            StaffKeyTalentId: 0,
            StaffEmpId: 0,
            StaffUrl: "",
            StaffName: "",
            StaffCategory: "",
            StaffDep: "",
            StaffIsAttention: false,
            StaffRisk: 0, //1红 2黄 2绿 0灰
            StaffGoodnessNum: 0,
            StaffGoodnessDate: "",
            StaffInferiorNum: 0,
            StaffInferiorDate: "",
            StaffEncourageTitle: "",
            StaffEncourageDate: "",
            StaffFollowNum: 0,
            StaffFollowDate: "",
            StaffSychron:0,
            logContent: "",
            advantageTagContent: "",
            inferiorityTagCount: ""
            
        },
        gridHtml: $("#tTemplate .tgrid").html(),
        listHtml: $("#tTemplate .tlist tbody").html(),
        listHtmlTable: $("#tTemplate .tlist").html(),
        titleHtml: $("#tTitleTemplate").html(),
        menuHrml: $("#tMenuTemlate").html(),
        createHtml: function (obj, html, controlId) {
            var self = this;
            html = html.replace("{ControlId}", controlId);
            html = html.replace("{StaffEmpId}", obj.StaffEmpId);
            html = html.replace("{StaffKeyTalentId}", obj.StaffKeyTalentId);
            html = html.replace("{StaffUrl}", obj.StaffUrl);
            html = html.replace("{StaffName}", obj.StaffName);
            html = html.replace("{StaffCategory}", obj.StaffCategory);
            html = html.replace("{StaffDep}", obj.StaffDep);
            html = html.replace("{StaffRiskFlag}", obj.StaffRisk);
            html = html.replace("{StaffSychron}", obj.StaffSychron);
            var risk = "";
            switch (obj.StaffRisk) {
                case 1:
                    risk = "backgroundred";
                    break;
                case 2:
                    risk = "backgroundyellow";
                    break;
                case 3:
                    risk = "backgroundgreen";
                    break;
                case 0:
                    risk = "backgroundgray";
                    break;
                default:
                    "";
                    break;
            }
            
            html = html.replace("{StaffRisk}", risk);
            html = html.replace("{StaffGoodnessNum}", "<a class='marginright5' id=" + obj.StaffKeyTalentId + " href='javascript:void(0)'>" + obj.StaffGoodnessNum + "</a>个");
            html = html.replace("{StaffGoodnessContent}", "<a class='marginright5  textoverflowellipsis content'  id='" + obj.StaffKeyTalentId + "' href='javascript:void(0)'>" + obj.advantageTagContent + "</a> &nbsp;<a class='marginright5'  id='" + obj.StaffKeyTalentId + "' href='javascript:void(0)'>" + obj.StaffGoodnessNum + "</a>个");
            html = html.replace("{StaffGoodnessDate}", obj.StaffGoodnessDate != "" ? "(" + obj.StaffGoodnessDate + ")" : "");
            html = html.replace("{StaffInferiorNum}", "<a class='marginright5' id=" + obj.StaffKeyTalentId + " href='javascript:void(0)'>" + obj.StaffInferiorNum + "</a>个");
            html = html.replace("{StaffInferiorContent}", "<a class='marginright5 textoverflowellipsis content' id=" + obj.StaffKeyTalentId + " href='javascript:void(0)'>" + obj.inferiorityTagContent + "</a> &nbsp;<a class='marginright5' id=" + obj.StaffKeyTalentId + " href='javascript:void(0)'>" + obj.StaffInferiorNum + "</a>个");
            html = html.replace("{StaffInferiorDate}", obj.StaffInferiorDate != "" ? "(" + obj.StaffInferiorDate + ")" : "");
            html = html.replace("{StaffEncourageTitle}", "<a class='marginright5 textoverflowellipsis content' id=" + obj.StaffKeyTalentId + " href='javascript:void(0)'>" + obj.StaffEncourageTitle + "</a>&nbsp;<a class='marginright5' id=" + obj.StaffKeyTalentId + " href='javascript:void(0)'>" + obj.StaffEncourageNum + "</a>个");
            html = html.replace("{StaffEncourageDate}", obj.StaffEncourageDate != "" ? "(" + obj.StaffEncourageDate + ")" : "");
            html = html.replace("{StaffFollowNum}", "<a class='marginright5' id=" + obj.StaffKeyTalentId + " href='javascript:void(0)'>" + obj.StaffFollowNum + "</a>个");
            html = html.replace("{StaffFollowContent}", "<a class='marginright5 textoverflowellipsis content' id='" + obj.StaffKeyTalentId + "' href='javascript:void(0)'>" + obj.logContent + "</a> &nbsp;<a class='marginright5' id='" + obj.StaffKeyTalentId + "' href='javascript:void(0)'>" + obj.StaffFollowNum + "</a>个");
            html = html.replace("{StaffFollowDate}", obj.StaffFollowDate != "" ? "(" + obj.StaffFollowDate + ")" : "");
            //选中关注
            if (obj.StaffIsAttention) {
                var div = $("<div></div>")
                div.html(html);
                div.find(".attentionSelected").removeClass("hide");
                div.find(".attentiontext").text("已关注").attr("title", "取消关注");
                html = div.html();
            }
            return html;
        },
        getGrid: function (obj, controlId) {
            var html = this.createHtml(obj, this.gridHtml, controlId);
            var div = $("<div></div>");
            html = setStyle.makecategCenter(obj.StaffCategory, html, "grid");
            div.append(html);
            return div.html();
        },
        getList: function (obj, controlId) {
            var html = this.createHtml(obj, this.listHtml, controlId);
            html = setStyle.makecategCenter(obj.StaffCategory, html, "list");
            return html;
        },
        getListHead: function (html) {
            var div = $("<div></div>");
            div.html(this.listHtmlTable);
            div.find("tbody").html(html);
            return div.html();
        },
        getTitle: function (idName, name, num, id) {
            if (!id) id = "0";
            var div = $("<div></div>");
            div.html(this.titleHtml);
            div.find(".name").text(name);
            div.find(".name").addClass("title");
            div.find(".num").text(num);
            div.find(".num").addClass("titleNum");
            return '<div id="' + idName + '" data-id="' + id + '" class="row no-margin paddingtop6 floatleft width50">' + div.html() + '</div>';
        },
        getMenu: function (idName, showOrderby, showAdd) {
            var div = $("<div></div>");
            div.html(this.menuHrml);
            if (showOrderby)
                div.find(".orderBy").removeClass("hide");
            if (showAdd)
                div.find(".addTalentBtn").removeClass("hide");
            return '<div id="' + idName + '" class="row no-margin floatright width50  paddingtop6">' + div.html() + '</div>';
        }
    };
    var setStyle = {
    		makecategCenter: function(cate,html,oper){
    			if(cate != 'A' && cate != 'B' && cate != 'C'){
    				if(oper == "grid"){
    					html = html.replace("{StaffCategoryStyle}", "");
//    					console.log(oper);
    				}
    				if(oper == "list"){
    					html = html.replace("{StaffCategoryStyle}", "");
    				}
    			}
    			else{
    				html = html.replace("{StaffCategoryStyle}","");
    			}
    			return html;
    		},
    		initLeftPanel: function(){
    			//必须等到右边生成完成才调用，刷新也需要调用
    			var rightHeight = $("#talentRight").height();
    			//若右边没有数据，只设定为屏幕高度
    				$("#talentLeft").height(rightHeight);
//    			console.log("设定左边面板高度与右边一致:" + $("#talentLeft").height());
    		},
    		//确定底层的span宽度，设定上级的宽度，以使margin： 0 auto生效
    		getTalentTypeWidth4Par: function(){
    			var contents = $("#tTabContent").find(".talenttypecontent");
    			for(var i = 0,size = contents.length;i<size;i++){
    				var talentw = $(contents[i]).width();
    				$(contents[i]).parent().css("width",talentw+"px");
    			}
    		}
    };

    //操作
    var oprationObj = {
        titleHeight: 0,
        attentionHeight: 0,//关注高度
        newHeight: 0,//最新高度
        isloading: false,//判断是否正在加载
        path: vUrl + "/assets/img/talent/",
        imgs: ["talent_grid.png", "talent_grid_selected.png", "talent_list.png", "talent_list_selected.png"],
        titleIds: ["tTitle0", "tTitleCate", "tTitleOrg", "tTitleCup", "tTitleSearch"],
        menuIds: ["tMenuTab", "tMenuCate", "tMenuOrg", "tMenuCup", "tMenuSearch"],
        contentIds: ["tTabContent", "tCategoryContent", "tOrgContent", "tCupContent", "tSearchContent"],
        gridList: ["#tWarning .grid,#tAttention .grid,#tNew .grid", "#tWarning .list,#tAttention .list,#tNew .list", "#tCategoryContent .grid", "#tCategoryContent .list", "#tOrgContent .grid", "#tOrgContent .list", "#tCupContent .grid", "#tCupContent .list", "#tSearchContent .grid", "#tSearchContent .list"],
        init: function () {
            var self = this;
            self.resetHeight();
            self.showType();
            self.sort();
            self.scroll();
            self.bindEven();
            self.addTalent();
        },
        //添加关键人才按钮
        addTalent: function () {
            var self = this;
            $("#" + self.menuIds[showContentIndex] + " .addTalentBtn").on("click", function () {
                $("#addDialog").modal("show").on('shown.bs.modal', function () {
                    addTalentObj.select();
                });
            });
        },
        //重置高度，以适应以下滚动事件
        resetHeight: function () {
            if (showContentIndex == 0) {
                var self = this;
                //console.log(self.attentionHeight+" "+self.newHeight);
                var scrollTop = $("#talentRight").scrollTop();
                self.attentionHeight = $("#tAttention").position().top + scrollTop;
                self.newHeight = $("#tNew").position().top + scrollTop;
            }
        },
        //滚动事件
        scroll: function () {
            var self = this;

            newHeight = $(window).height(); //获取网页高度
			$(window).scroll(function(){ 
				//页面滚动时判断页面是否在显示
				//避免页面显示为“关键人才跟踪与评价”时获取最近更新人员
				if(!$("#mainDiv").hasClass("hide")){
				var s = $(window).height()-($(window).scrollTop());
					if(s<1000 && !this.isloading){ 
						isloading = true;
						newHeight = $(window).height(); 
		                //预警滚动事件
		                if (showContentIndex == 0) {
		                    var scrollTop = $("#talentRight").scrollTop();
		                    var index = 0;
		                    if (scrollTop < self.attentionHeight) {
		                    	viewContentObj.newupdateContent({
	                                total: 0,
	                                page: 0,
	                                scrollLoad: true
	                            });
		                        index = 0;
		                    } else if (self.attentionHeight <= scrollTop && scrollTop < self.newHeight) {
		                    	index = 1;
		                    } else {
		                        index = 2;
		                    }
		                    if (!$("#talentTab .tabli").eq(index).hasClass("active")) {
		                        $("#talentTab .tabli").removeClass("active");
		                        $("#talentTab .tabli").eq(index).addClass("active");
		                    }
		                    
		                } else {
		                    if ($("#talentRight").height() + $("#talentRight").scrollTop() + 1 > $("#tCategoryContent").height()) {
		                        //分类滚动事件
		                        if (showContentIndex == 1) {
		                            viewContentObj.cateContent({
		                                id: $("#tTitleCate").data("id"),
		                                title: "",
		                                total: 0,
		                                page: 0,
		                                scrollLoad: true
		                            });
		                        }
		                        //组织架构滚动事件
		                        else if (showContentIndex == 2) {
		                            viewContentObj.orgContent({
		                                id: $("#tTitleOrg").data("id"),
		                                title: "",
		                                total: 0,
		                                page: 0,
		                                scrollLoad: true
		                            });
		                        }
		                        //激励滚动事件
		                        else if (showContentIndex == 3) {
		                            viewContentObj.cupContent({
		                                id: $("#tTitleCup").data("id"),
		                                title: "",
		                                total: 0,
		                                page: 0,
		                                scrollLoad: true
		                            });
		                        }
		                        //搜索
		                        else if (showContentIndex == 4) {
		                            var m = viewContentObj.searchData;
		                            if (m.total > m.page * m.pageSize) {
		                                m.page = m.page + 1;
		                                $.post(urls.searchTalent, {
		                                    keyName: m.title,
		                                    page: m.page,
		                                    rows: m.pageSize
		                                }, function (da) {
		                                    viewContentObj.searchContent(da.rows, {
		                                        page: m.page,
		                                        total: m.total,
		                                        title: m.title
		                                    });
		                                });
		                            } else {
		                                //showErrMsg("没有更多了!");
		                            }
		                        }
		                    }
		                }
		                setTimeout(function(){isloading = false;},1500);
			    	}
				}
            });

        },
        //绑定切换网格或列表事件
        showType: function () {
            var id = this.menuIds[showContentIndex];
            var self = this;
            $("#" + id + " .showtype").unbind("click").on("click", function () {
                viewContentObj.showtype = $(this).data("type");
                self.setShowType();
            });
        },
        //设置网格或列表
        setShowType: function () {
            var self = this;
            var type = viewContentObj.showtype;
            //按钮图片
            $(".showtypegrid").attr({src: self.path + self.imgs[0]});
            $(".showtypelist").attr({src: self.path + self.imgs[2]});
            //当前是grid置灰
            if(type == "grid"){
            	$(".switchBtnL").addClass("fofofo");
            	$(".switchBtnR").removeClass("fofofo");
            }else{
            	$(".switchBtnL").removeClass("fofofo");
            	$(".switchBtnR").addClass("fofofo");
            }
            //显示grid或list
            //console.log(showContentIndex);
            $(self.gridList[showContentIndex * 2 + (type == "grid" ? 1 : 0)]).addClass("hide");
            $(self.gridList[showContentIndex * 2 + (type == "list" ? 1 : 0)]).removeClass("hide");
            //重置滚动位置
            self.resetHeight();
        },
        //排序
        sort: function () {
            if (showContentIndex > 0 && showContentIndex < 5) {
                var self = this;
                $("#tMenuTab .orderBy select").attr("disabled", "false");
                $("#" + self.menuIds[showContentIndex] + " .orderBy select").change(function () {
//                	console.log(showContentIndex);
                    var sort = $(this).val();
                    var obj = {
                        id: $("#" + self.titleIds[showContentIndex]).data("id"),
                        title: $("#" + self.titleIds[showContentIndex] + " .name").text(),
                        total: $("#" + self.titleIds[showContentIndex] + " .num").text(),
                        keyName: $("#txtSearchName").val(),
                        page: 1,
                        sort: sort
                    };
                    switch (showContentIndex) {
                        case 1:
                        {
                            viewContentObj.cateContent(obj);
                            break;
                        }
                        case 2:
                        {
                            viewContentObj.orgContent(obj);
                            break;
                        }
                        case 3:
                        {
                            viewContentObj.cupContent(obj);
                            break;
                        }
                        case 4:
                        {
                        	viewContentObj.searContent(obj);
                        	break;
                        }
                    }
                });
            }
            else{
                $("#tMenuTab .orderBy select").attr("disabled", "true");
            }
        },
        //取消关注
        cancelAttention: function(empId){
            $("#tTabContent .grid [data-id='"+empId+"'] .attentionSelected").addClass("hide");
            $("#tTabContent .list [data-id='"+empId+"'] .attention").text("加入关注").attr("title","加入关注");
            $("#tAttention .grid [data-id='"+empId+"'],#tAttention .list [data-id='"+empId+"']").remove();
            var len=$("#tAttention .grid [data-id]").length;
            $("#talentAttention .num,#tAttention #tTitle0 .num").text(len);

        },
        //绑定操作事件
        bindEven: function () {
            var self = this;
            var gridObj = $(self.gridList[showContentIndex * 2]);
            var listObj = $(self.gridList[showContentIndex * 2 + 1]);
            var gridList = $(self.gridList[showContentIndex * 2] + "," + self.gridList[showContentIndex * 2 + 1]);
            $('[data-toggle="tooltip"]').tooltip();
//            $("[data-toggle='tooltip']").tooltip();
//            //鼠标经过事件
//            gridObj.find(".gridDiv").hover(function () {
//                if ($(this).find(".attentionSelected").hasClass("hide")) {
//                    $(this).find(".attentionUnSelected").removeClass("hide");
//                }
//                $(this).find(".person,.pk,.delete").removeClass("hide");
//            }, function () {
//                $(this).find(".attentionUnSelected,.person,.pk,.delete").addClass("hide");
//            });
          //绑定点击功能键
			var $shiftbtn = $(".shiftbtn");
			var $attentionSelected = $(".attentionSelected");
			$shiftbtn.unbind("click").bind("click", function(){
				$(this).parent().parent().removeClass("operat");
				$(this).parent().parent().addClass("operat2");
				var self  = this;
				//获取到收藏状态
				var favorit = !$(this).parent().parent().parent().find(".attentionSelected").hasClass("hide");
//				console.log("收藏状态"+favorit);
				//获取到功能键
				var $shiftSpan = $(this).parent().parent().find(".shift");
				//获取到收藏键
				var $addfavoritBtn = $shiftSpan.find(".addfavorit");
				var hasHide = $shiftSpan.hasClass("hide");
				if(hasHide){
					$shiftSpan.removeClass("hide");
					$shiftSpan.find("img").removeClass("hide");
					$(this).addClass("hide");
					//已收藏
					if(favorit){
						//已收藏隐藏收藏键
						$addfavoritBtn.addClass("hide");
						//重新设置功能键style
						$shiftSpan.attr("style","margin: 0px");
					}
				}
				if(!hasHide)
					$(this).addClass("hide");
			});
			
            //绑定点击离职风险
            gridList.find(".riskClick").unbind("click").on("click", function () {
                var empId = $(this).parents("[data-controlid]").data("id");
                var riskFlagId = $(this).data("riskflag");
                self.risk(empId, riskFlagId);
            });
            //绑定关注
//            gridObj.find(".attention").unbind("click").on("click", function () {
            gridObj.find(".addfavorit").unbind("click").on("click", function () {

                var t = this;
                var obj = $(t).parents("[data-controlid]");
                var empId = obj.data("id");
                var talentid = obj.data("talentid");
                var cid = obj.data("controlid");
                self.attention(talentid, true, function () {
                    //list
                    var obj = $(self.gridList[showContentIndex * 2 + 1]).find("[data-controlid='" + cid + "']").find(".addfavorit");
                    //grid
                    $(t).addClass("hide");
                    $(t).parents("[data-controlid]").find(".attentionSelected").removeClass("hide");
                    obj.text("已关注").attr("title", "取消关注");
                    //关注后attention自动刷新页面，所以updateData.page应该设置为第二页
                    viewContentObj.updateData.page=2;
                });
            });
            listObj.find(".attention").unbind("click").on("click", function () {
                var t = this;
                var obj = $(t).parents("[data-controlid]");
                var empId = obj.data("id");
                var talentid = obj.data("talentid");
                var cid = obj.data("controlid");
                var addForcus = $(t).text() == "加入关注";
                self.attention(talentid, addForcus, function () {
                    //grid
                    var obj = $(self.gridList[showContentIndex * 2]).find("[data-controlid='" + cid + "']").find(".attention");
                    //list
                    if (addForcus) {//加入关注
                        obj.parent().find(".attentionSelected").removeClass("hide");
                        obj.parent().find(".attentionUnSelected").addClass("hide");
                        $(t).text("已关注").attr("title", "取消关注");
                    } else {//取消关注
                        self.cancelAttention(empId);
                        //obj.parent().find(".attentionSelected,.attentionUnSelected").addClass("hide");
                        //$(t).text("加入关注").attr("title", "加入关注");
                    }
                });
            });
            //grid上取消关注
			$attentionSelected.unbind("click").bind("click", function(){
                var t = this;
                var obj = $(t).parents("[data-controlid]");
                var empId = obj.data("id");
                var talentid = obj.data("talentid");
                var cid = obj.data("controlid");
                self.attention(talentid, false, function () {
                    //list
                    var obj = $(self.gridList[showContentIndex * 2 + 1]).find("[data-controlid='" + cid + "']").find(".addfavorit");
                    //grid
                    $(t).addClass("hide");
                    $(t).parent().find(".addfavorit").removeClass("hide");
                    //attention自动刷新页面，所以updateData.page应该设置为第二页
                    viewContentObj.updateData.page=2;
                });
			});

            //绑定剖像
            gridList.find(".person").unbind("click").on("click", function () {
                var empid = $(this).parents("[data-controlid]").data("id");
                self.person(empid);
            });

            //绑定pk
            gridList.find(".pk").unbind("click").on("click", function () {
                var empid = $(this).parents("[data-controlid]").data("id");
                self.pk(empid);
            });

            //绑定删除
            gridList.find(".delete").unbind("click").on("click", function () {
                var sychron=$(this).data("sychron");
                if(parseInt(sychron)==1){
                    showErrMsg("自动同步人员不能删除！");
                }else {
                    var obj = $(this).parents("[data-controlid]");
                    var empid = obj.data("id");
                    var talentid = obj.data("talentid");
                    var name = obj.find(".talentname").text();
                    var cid = obj.data("controlid");
                    self.deleteKeyTalent(talentid, name, function () {
                        //$("#"+self.contentIds[showContentIndex]).find("[data-id='"+empid+"']").remove();
                        self.refreshData();
                    });
                }
            });
            
            //只要左边有点击，即刻刷新左边高度
            $("#talentLeft").bind('click', function (){
            	setTimeout(function () {
            		setStyle.initLeftPanel();
            		oprationObj.viewKeyTalentEvalView();
            	},2000);
            });
        },
        //关注事件
        attention: function (talentid, addForcus, callback) {
        	
            $.post(addForcus ? urls.addFocuse : urls.removeFocuse, {keyTalentId: talentid}, function (da) {
                if (da.type) {
                    callback.call(new Object());
                    //刷新数据
                    viewContentObj.tabContent();
                }
                else {
                    showErrMsg(da.msg);
                }
            });
        },
        //打开离职风险窗口
        risk: function (empId, riskFlag) {
            var self=this;
            //console.log(riskFlag);
            var riskListOption = {
                hasSelect: false, //是否显示select
                hasTopText: true,
                riskModal :false,
                callback:function(){//刷新页面
                    self.refreshData();
                }
            }
            if (riskFlag != 0) {
                $("#riskDetailModal").modal("show");
                $("#riskDetailModal").unbind("shown.bs.modal").on('shown.bs.modal', function () {
                    var obj = $("#riskInfo");
                    riskListOption.empId = empId;
                    $(obj).riskList(riskListOption);
                });
            } else {  //直接评估
                riskListOption.riskModal = true;
                riskListOption.level=1;
                riskListOption.hasSelect = true; // true  直接评估  false  显示明细
                riskListOption.empId = empId;
                $("body").riskList(riskListOption);
            }
        },
        //人才剖像
        person: function (empid) {
            window.open(vUrl + "/talentProfile/toTalentDetailView.do?empId=" + empid);
        },
        //pk
        pk: function (empid) {
            if (stackObject) {
                stackObject.bottomStock('hideFrame', 0);
                stackObject.bottomStock('addPerson', empid);
            } else {
                showErrMsg("stackObject object is not exists!");
            }
        },
        //删除
        deleteKeyTalent: function (talentid, name, callback) {
            $("#delDialog .modal-body span").text(name);
            $("#delDialog").modal('show');
            $("#btnOk").unbind("click").on("click", function () {
                $.post(urls.deleteKeyTalent, {keyTalentId: talentid}, function (da) {
                    if (da.type) {
                        $("#delDialog").modal('hide').unbind("hidden.bs.modal").on('hidden.bs.modal', function () {
                            callback.call(new Object());
                        });
                    } else {
                        $("#delDialog").modal('hide');
                        showErrMsg(da.msg);
                    }

                });
            })
        },
        //刷新数据
        refreshData:function(){
            viewContentObj.tabContent();
            pageObj.init();
            orgObj.init();

            if(showContentIndex==4){//如果搜索列表，需要刷新
                var v=$("#txtSearchName").val();
                pageObj.searchClick(v);
            }
        },
        //关键人员跟踪与评价
        viewKeyTalentEvalView: function(){
        	$(".marginright5").click(function(){
        		var $link = $(this);
        		var id = $link.attr("id");

                $.get(urls.keytalentEval, {keyTalentId: id}, function (data) {
                    var $keytalenteval = $("#keytalentEval");
                    var $maindiv = $("#mainDiv");
                    $keytalenteval.html("");
                    $keytalenteval.append(data);
                    $keytalenteval.removeClass("hide");
                    $maindiv.addClass("hide");
                    
                });
        	});
        }
    };

    //显示内容
    var viewContentObj = {
        controlId:0,
        idName: ["tTitle0", "tTitle1", "tTitle2"],
        titleIds: ["tTitle0", "tTitleCate", "tTitleOrg", "tTitleCup", "tTitleSearch"],
        menuIds: ["tMenuTab", "tMenuCate", "tMenuOrg", "tMenuCup", "tMenuSearch"],
        contentIds: ["tTabContent", "tCategoryContent", "tOrgContent", "tCupContent", "tSearchContent"],
        titleName: ["离职风险预警", "我关注的人员", "最近更新人员"],
        showtype: "grid",
        option: {
            id: "",
            title: "",
            total: 0,
            page: 1,
            pageSize: 30,
            isRequest: false,
            sort: "warn", //warn  refresh  ability  createTime
            scrollLoad: false
        },
        searchData: {},
        updateData: {},
        cateData: {},//{id:"", title:"", total:252, page:1, html:""}
        orgData: {},
        cupData: {},
        nodatagrid: function(html){
            return html==""?"<div class='nodatagrid'>暂无数据</div>":html;
        },
        nodatalist: function(html){
            return html==""?"<tr><td colspan='4' class='borderbottom borderright nodatalist'><div>暂无数据</div></td></tr>":html;
        },
        getControlId: function () {
            this.controlId++;
            return this.controlId;
        },
        tran: function (das) {
            var lis = [];
            $.each(das, function (i, da) {
                lis.push({
                    StaffKeyTalentId: da.keyTalentId,
                    StaffEmpId: da.empId,
                    StaffUrl: da.imgPath,
                    StaffName: da.userName,
                    StaffCategory: da.keyTalentTypeName,
                    StaffDep: da.positionName,
                    StaffIsAttention: da.focusesId != null,
                    StaffRisk: da.riskFlag,
                    StaffGoodnessNum: da.advantageTagCount,
                    StaffGoodnessDate: da.advantageTagLastDate&&da.advantageTagCount>0?dataObj.formatDate(da.advantageTagLastDate, "Y-m-d"):"",
                    StaffInferiorNum: da.inferiorityTagCount,
                    StaffInferiorDate: da.inferiorityTagLastDate&&da.inferiorityTagCount>0?dataObj.formatDate(da.inferiorityTagLastDate, "Y-m-d"):"",
                    StaffEncourageTitle: da.encourageCount>0?da.encourageContent + "&nbsp;" : "",
                    StaffEncourageNum :da.encourageCount,
                    encourageNum: da.encourageCount,
                    StaffEncourageDate: da.encourageLastDate&&da.encourageCount>0?dataObj.formatDate(da.encourageLastDate, "Y-m-d"):"",
                    StaffFollowNum: da.logCount,
                    StaffFollowDate: da.logLastDate&&da.logCount>0?dataObj.formatDate(da.logLastDate, "Y-m-d"):"",
                    StaffSychron:da.sychron,
                    advantageTagContent: da.advantageTagContent == null ? '':da.advantageTagContent.replace(',,',' ').replace(' ,',' ').replace('  ', ''),
                    inferiorityTagContent: da.inferiorityTagContent == null ? '':da.inferiorityTagContent.replace(',,',' ').replace(' ,',' ').replace('  ', ''),
                    logContent: da.logContent == null ? '':da.logContent.replace(',,',' ').replace(' ,',' ').replace('  ', '')
                })
            });
            return lis;
        },
        requestNum: 0,
        //预警关注最新
        tabContent: function () {
            var self = this;
            self.requestNum = 0;

            //离职风险预警
            $.ajax({
                type: "POST",
                url: urls.warn,
                success: function (das) {
                    var da = self.tran(das);

                    var htmlGrid = "", htmlList = "", ctrlId = 0;
                    $.each(da, function (i, item) {
                        ctrlId = self.getControlId();
                        htmlGrid += htmlObj.getGrid(item, ctrlId);
                        htmlList += htmlObj.getList(item, ctrlId);
                    });
                    htmlList = htmlObj.getListHead(self.nodatalist(htmlList));

                    $("#talentRisk .num span").text(da.length);
                    var htmlWarning = htmlObj.getTitle(self.idName[0], self.titleName[0], da.length);
                    htmlWarning += htmlObj.getMenu("tMenuTab", false, false);
                    htmlWarning += '<div class="grid row no-margin hide floatleft">' + self.nodatagrid(htmlGrid) + '</div>';
                    htmlWarning += '<div class="list row no-margin hide floatleft">' + htmlList + '</div>';
                    $("#tWarning").html(htmlWarning);
                },
                complete: function (XHR, TS) {
                    //console.log("加载完成:离职风险预警");
                    self.tabContentBind();
                    setStyle.getTalentTypeWidth4Par();
                }
            });

            //我关注的人员
            $.ajax({
                type: "POST",
                url: urls.focuse,
                success: function (das) {
                    var da = self.tran(das);

                    var htmlGrid = "", htmlList = "", ctrlId = 0;
                    $.each(da, function (i, item) {
                        ctrlId = self.getControlId();
                        htmlGrid += htmlObj.getGrid(item, ctrlId);
                        htmlList += htmlObj.getList(item, ctrlId);
                    });
                    htmlList = htmlObj.getListHead(self.nodatalist(htmlList));

                    $("#talentAttention .num span").text(da.length);
                    var htmlAttention = htmlObj.getTitle(self.idName[0], self.titleName[1], da.length);
                    htmlAttention += '<div class="grid row no-margin hide floatleft">' + self.nodatagrid(htmlGrid) + '</div>';
                    htmlAttention += '<div class="list row no-margin hide floatleft">' + self.nodatalist(htmlList) + '</div>';
                    $("#tAttention").html(htmlAttention);
                },
                complete: function (XHR, TS) {
                    //console.log("加载完成:我关注的人员");
                    self.tabContentBind();
                    setStyle.getTalentTypeWidth4Par();
                }
            });

            //最近更新人员
            $.ajax({
                type: "POST",
                url: urls.newUpdate,
                success: function (das) {
                    var da = self.tran(das.list);
                    var htmlGrid = "", htmlList = "", ctrlId = 0;
                    $.each(da, function (i, item) {
                        ctrlId = self.getControlId();
                        htmlGrid += htmlObj.getGrid(item, ctrlId);
                        htmlList += htmlObj.getList(item, ctrlId);
                    });
                    htmlList = htmlObj.getListHead(self.nodatalist(htmlList));

                    var htmlNew = htmlObj.getTitle(self.idName[0], self.titleName[2], da.count);
                    htmlNew += '<div class="grid row no-margin floatleft" id="talentNewdiv">' + self.nodatagrid(htmlGrid) + '</div>';
                    htmlNew += '<div class="list row no-margin hide" id="talentNewdivhide">' + self.nodatalist(htmlList) + '</div>';
                    $("#tNew").html(htmlNew);
                    $("#talentNew .num span").text(das.count);//赋予导航总数
                    $("#tNew .num").text(das.count);//赋予title总数
                },
                complete: function (XHR, TS) {
                	$.extend(viewContentObj.updateData,{
                		page: 1,
                		total: $("#talentNew .num span").text(),
                		pageSize: 30,
                		isRequest: true,
                		scrollLoad: true
                	});
                    //console.log("加载完成:最近更新人员");
                    self.tabContentBind();
                    setStyle.initLeftPanel();
            		oprationObj.viewKeyTalentEvalView();
                    setStyle.getTalentTypeWidth4Par();
                }
            });
        },
        //预警关注最新绑定
        tabContentBind: function () {
            var self = this;
            self.requestNum++;
            if (self.requestNum == 3) {
                oprationObj.init();

                //设置显示方式
                oprationObj.setShowType();
            }
        },
        //分页模型
        page: function (obj, m) {
            var self = this;
            if (obj.page == 1) {
                m = {};
                $.extend(m, self.option, {
                    id: obj.id,
                    keyName: obj.keyName,
                    title: obj.title,
                    total: obj.total,
                    page: 1,
                    isRequest: obj.total > 0
                });
                if (obj.sort) {
                    m.sort = obj.sort;
                }
            } else {
                var isRequest = m.page * m.pageSize < m.total;
                $.extend(m, {
                    isRequest: isRequest,
                    page: isRequest ? m.page + 1 : m.page
                });
            }
            m.scrollLoad = obj.scrollLoad;
            return m;
        },
        show: function (m, da, index) {
            var self = this;

            var htmlGrid = "", htmlList = "", ctrlId = 0;
            $.each(da, function (i, item) {
                ctrlId = self.getControlId();
                htmlGrid += htmlObj.getGrid(item, ctrlId);
                htmlList += htmlObj.getList(item, ctrlId);
            });

            var contentId = self.contentIds[index];
            if (m.page == 1) {
                var html = htmlObj.getTitle(self.titleIds[index], m.title, m.total, m.id);
                html += htmlObj.getMenu(self.menuIds[index], true, true);
                html += '<div class="grid row no-margin floatleft">' + self.nodatagrid(htmlGrid) + '</div>';
                html += '<div class="list row no-margin hide">' + htmlObj.getListHead(self.nodatalist(htmlList)) + '</div>';
                $("#" + contentId).html(html).find(".orderBy select").val(m.sort);
            } else if (m.page > 1) {
                $("#" + contentId + " .grid").append(htmlGrid);
                $("#" + contentId + " .list tbody").append(htmlList);
            }

            showContent(index);

            oprationObj.init();
            setStyle.getTalentTypeWidth4Par();

            
            self.tabContentBind();
            setStyle.initLeftPanel();
    		oprationObj.viewKeyTalentEvalView();

            if (m.page == 1) {
                //滚动到顶部
                $("#talentRight").scrollTo(0, 300);
            }
        },
        //搜索
        searchContent: function (das, m) {
            var self = this;
            var index = 4;
            self.searchData = m;

            var da = self.tran(das);
            var htmlGrid = "", htmlList = "", ctrlId = 0;
            $.each(da, function (i, item) {
                ctrlId = self.getControlId();
                htmlGrid += htmlObj.getGrid(item, ctrlId);
                htmlList += htmlObj.getList(item, ctrlId);
            });

            var contentId = self.contentIds[index];
            if (m.page == 1) {
                var html = htmlObj.getTitle(self.titleIds[index], m.title, m.total, m.title);
                html += htmlObj.getMenu(self.menuIds[index], false, false);
                html += '<div class="grid row no-margin floatleft">' + self.nodatagrid(htmlGrid) + '</div>';
                html += '<div class="list row no-margin hide">' + htmlObj.getListHead(self.nodatalist(htmlList)) + '</div>';
                $("#" + contentId).html(html);
            } else if (m.page > 1) {
                $("#" + contentId + " .grid").append(htmlGrid);
                $("#" + contentId + " .list tbody").append(htmlList);
            }

            showContent(index);

            oprationObj.init();
            
            setStyle.getTalentTypeWidth4Par();
            
            self.tabContentBind();
            setStyle.initLeftPanel();
    		oprationObj.viewKeyTalentEvalView();
            setStyle.getTalentTypeWidth4Par();

            if (m.page == 1) {
                //滚动到顶部
                $("#talentRight").scrollTo(0, 300);
            }
        },
        //最近更新人员
        newupdateContent: function (obj) {
        	var self = this;
        	var index = 1;
        	self.updateData = self.page(obj, self.updateData);
        	var m = self.updateData;
        		$.post(urls.newUpdate, {
        			order: m.sort,
        			page: m.page,
        			rows: m.pageSize
        		},function(das){
        			var da = self.tran(das.list);
        			var htmlGrid = "", htmlList = "", ctrlId = 0;
                    $.each(da, function (i, item) {
                        ctrlId = viewContentObj.getControlId();
                        htmlGrid += htmlObj.getGrid(item, ctrlId);
                        htmlList += htmlObj.getList(item, ctrlId);
                    });
                    htmlList = htmlObj.getListHead(viewContentObj.nodatalist(htmlList));

                    $("#talentNew .num").text(das.count);
                    //var htmlNew = htmlObj.getTitle(viewContentObj.idName[0], viewContentObj.titleName[2], da.count);
                    $("#tNew .num").text(das.count);
                    var htmlNewDiv = viewContentObj.nodatagrid(htmlGrid);
                    $("#talentNewdiv").append(htmlNewDiv);
                    var htmlNewDivHide = viewContentObj.nodatalist(htmlList);
                    $("#talentNewdivhide").append(htmlNewDivHide);
                    oprationObj.init();
                    //设置显示方式
                    oprationObj.setShowType();
                    
                    setStyle.getTalentTypeWidth4Par();
        		});
        },
        //分类
        cateContent: function (obj) {
            var self = this;
            var index = 1;
            self.cateData = self.page(obj, self.cateData);
            var m = self.cateData;
            if (m.isRequest) {
                $.post(urls.categoryList, {
                    keyTalentTypeId: m.id,
                    order: m.sort,
                    page: m.page,
                    rows: m.pageSize
                }, function (das) {
                    var da = self.tran(das.rows);
                    self.show(m, da, index);
                });
            } else {
                if (!m.scrollLoad) {//第一次加载执行
                    self.show(m, [], index);
                } else {
                    console.info("分类：没有更多了！");
                }
            }
        },
        //组织架构
        orgContent: function (obj) {
            var self = this;
            var index = 2;
            self.orgData = self.page(obj, self.orgData);
            var m = self.orgData;
            if (m.isRequest) {
                $.post(urls.orgList, {
                    organizationId: m.id,
                    order: m.sort,
                    page: m.page,
                    rows: m.pageSize
                }, function (das) {
                    m.total=das.records;
                    var da = self.tran(das.rows);
                    self.show(m, da, index);
                });
            } else {
                if (!m.scrollLoad) {//第一次加载执行
                    self.show(m, [], index);
                } else {
                    console.info("组织架构：没有更多了！");
                }
            }
        },
        //激励
        cupContent: function (obj) {
            var self = this;
            var index = 3;
            self.cupData = self.page(obj, self.cupData);
            var m = self.cupData;
            if (m.isRequest) {
                $.post(urls.cupList, {
                    encourageId: m.id,
                    order: m.sort,
                    page: m.page,
                    rows: m.pageSize
                }, function (das) {
                    var da = self.tran(das.rows);
                    self.show(m, da, index);
                });
            } else {
                if (!m.scrollLoad) {//第一次加载执行
                    self.show(m, [], index);
                } else {
                    console.info("激励：没有更多了！");
                }
            }
        },
        //搜索
        searContent: function(obj) {
            var self = this;
            var index = 4;
            self.cateData = self.page(obj, self.cateData);
            var m = self.cateData;
            if (m.isRequest) {
                $.post(urls.searchTalent, {
                    keyName: m.keyName,
                    order: m.sort,
                    page: m.page,
                    rows: m.pageSize
                }, function (das) {
                    var da = self.tran(das.rows);
                    self.show(m, da, index);
                });
            } else {
                if (!m.scrollLoad) {//第一次加载执行
                    self.show(m, [], index);
                } else {
                    console.info("分类：没有更多了！");
                }
            }
        }
    };
    viewContentObj.tabContent();

    //添加关键人才
    var addTalentObj = {
        id: "txtKey",
        init: function () {
            var self = this;
            self.addTalent();
        },
        select: function () {
            var self = this;
            //分类时选中值
            if(showContentIndex==1){
                $("#talentCategory").val($("#tTitleCate").data("id"));
            }else{
                $("#talentCategory").val($("#talentCategory option").eq(0).attr("value"));
            }

            $("#" + self.id).select2({
                language: {
                    errorLoading: function () {
                        return "无法载入结果。"
                    },
                    inputTooLong: function (e) {
                        var t = e.input.length - e.maximum, n = "请删除" + t + "个字符";
                        return n
                    },
                    inputTooShort: function (e) {
                        var t = e.minimum - e.input.length, n = "请再输入至少" + t + "个字符";
                        return n
                    },
                    loadingMore: function () {
                        return "载入更多结果…"
                    },
                    maximumSelected: function (e) {
                        var t = "最多只能选择" + e.maximum + "个项目";
                        return t
                    },
                    noResults: function () {
                        return "未找到结果"
                    },
                    searching: function () {
                        return "搜索中…"
                    }
                },
                width: '200px',
                allowClear: true,
                multiple: false,
                openOnEnter: true,
                placeholder: "输入员工姓名",
                ajax: {
                    url: urls.selectTalent,
                    dataType: 'json',
                    delay: 500,
                    type: "POST",
                    data: function (params, page) {
                        var ps = {
                            keyName: params && params.term ? params.term : "",
                            page: params.page == null ? 1 : params.page,
                            rows: 30
                        };
                        return ps;
                    },
                    processResults: function (data) {
                        var lists = [];
                        $.each(data.rows, function (i, item) {
                            lists.push({id: item.empId, text: item.userName});
                        });
                        return {
                            results: lists,
                            pagination: {
                                more: data.total > data.page
                            }
                        };
                    }
                }
            }).val(null);
        },
        //添加关键人才分类下拉
        categorySelectInit: function (list) {
            var html = "";
            $.each(list, function (i, item) {
                html += "<option value='" + item.id + "'>" + item.name + "</option>";
            });
            $("#talentCategory").html(html);
            $("#talentCategory").select2({
            	tags:true,
            	width: '200px'
            });
        },
        //添加关键人才
        addTalent: function () {
            $("#btnAddTalent").click(function () {
                var eId = $("#txtKey").val()
                var typeId = $("#talentCategory").val();
                if (eId == "" || eId == null) {
                    showErrMsg("请选择姓名！")
                } else {
                    $.post(urls.addKeyTalent, {empId: eId, keyTalentTypeId: typeId}, function (data) {
                        $("#addDialog").modal('hide');
                        if (!data.type) {
                            showErrMsg(data.msg);
                        }else{
                            //刷新数据
                            viewContentObj.tabContent();
                            pageObj.init();
                            orgObj.init();
                        }
                    });
                }
            });
        }
    };
    addTalentObj.init();

    //设置高度实现内部滚动
    var windowHeightObj = {
        setHeight: function () {
            var self = this;
            var hasBreadcrumbs = window.parent.$(".breadcrumbs-fixed").length > 0;
            var iframeHeight = window.parent.$("#mainFrame").data("height") - 50 - (hasBreadcrumbs ? 40 : 0);
            window.parent.$("#mainFrame").css({height: iframeHeight - 18 + "px"});
            var contentHeight=iframeHeight - 36;
            $("#talentRight").css({"height": contentHeight});
            $("#talentCateDetail .org #tree").css({"min-height": contentHeight - 243});
        },
        init: function () {
            var self = this;
            self.setHeight();
            $(window).resize(function () {
                self.setHeight();
                //重置滚动位置
                oprationObj.resetHeight();
            });
        }
    };
    windowHeightObj.init();
    //订住左边div
    var pinleft = {
    		init: function(){
    		    $("#talentLeft").pin({
    		    	position: "fixed",
    		    });
    		    setTimeout(function(){
	    					$(".pin-wrapper").css("position","fixed")
	    				},1000);
    		}
    		
    }
    pinleft.init();
//	    //监视浏览器滚动条状态
//	var windowScroller = {
//		    isloading : false,
//			listen: function(){
//			    var wh = $(window).height(); 
//			    $(window).scroll(function(){ 
//			    	var s = wh-$(window).scrollTop();
//			    	if(s<300 && !this.isloading){ 
//			    		wh = $(window).height(); 
//			    		isloading = true;
//			    		//加载下一页
//			    		$.ajax({
//			                type: "POST",
//			                url: urls.newUpdate,
//			                success: function (das) {
//			                    var da = viewContentObj.tran(das);
//
//			                    var htmlGrid = "", htmlList = "", ctrlId = 0;
//			                    $.each(da, function (i, item) {
//			                        ctrlId = viewContentObj.getControlId();
//			                        htmlGrid += htmlObj.getGrid(item, ctrlId);
//			                        htmlList += htmlObj.getList(item, ctrlId);
//			                    });
//			                    htmlList = htmlObj.getListHead(viewContentObj.nodatalist(htmlList));
//
//			                    $("#talentNew .num").text(da.length);
//			                    var htmlNew = htmlObj.getTitle(viewContentObj.idName[0], viewContentObj.titleName[2], da.length);
//			                    var htmlNewDiv = viewContentObj.nodatagrid(htmlGrid);
//			                    $("#talentNewdiv").append(htmlNewDiv);
//			                    var htmlNewDivHide = viewContentObj.nodatalist(htmlList);
//			                    $("#talentNewdivhide").append(htmlNewDivHide);
//			                },
//			                complete: function (XHR, TS) {
//			                    //console.log("加载完成:最近更新人员");
//			                    oprationObj.init();
//			                    //设置显示方式
//			                    oprationObj.setShowType();
//			                }
//			            }); 
//			    		//加载完成
//			    		setTimeout(
//			    				function(){
//			    					this.isloading = false;
//			    				},1000);
//			    	}
//			    }
//			    );
//			}
//	
//    };
//	windowScroller.listen();


});