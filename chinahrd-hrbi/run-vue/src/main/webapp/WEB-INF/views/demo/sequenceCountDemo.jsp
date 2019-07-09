<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<html>
<head>
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>职位序列Demo</title>
    <style>
    .count{min-width:960px;margin:10px auto; position:relative;}
    .count p,.count ul, .count li{padding:0;margin:0; list-style:none; }
    .count h3{font-size:15px;font-weight:bold;padding:5px 0;margin: 0; text-align:left;}
    .count .arrow_btn{width:5px;height:3px; position:absolute;top:26px;left:68px; background:url(${ctx}/assets/img/other/ico_01.jpg) no-repeat 0 0; display:inline-block;}
    .count .left{width:76px;float:left;border-right:5px solid #b0b0b0;height:368px;}
    .count .left li{display:table;width:100%; height:74px; background:url(${ctx}/assets/img/other/ico_01.jpg) no-repeat bottom right;}
    .count .left li p{width:27px;height:54px; text-align:center; color:#323232; display:table-cell; background: url(${ctx}/assets/img/other/titlebg.png) no-repeat center center; vertical-align:middle;font-size:13px;font-weight:bold;padding:0 15px 0 10px;}
    .count .right{width:540px;float:left; }

    .count .right .title{width:530px;height:25px; line-height:25px;font-size:12px;text-align:center;margin-top:5px; position:relative; cursor:pointer}
    .count .right .title .pre{width:16px;height:38px; display:inline-block; cursor:pointer; background:url(${ctx}/assets/img/other/pre_ico.jpg) no-repeat 0 0; position:absolute; bottom:29px;left:-8px;}
    .count .right .title .next{width:23px;height:38px;display:inline-block; cursor:pointer; background:url(${ctx}/assets/img/other/next_ico.jpg) no-repeat 0 0; position:absolute;bottom:29px ;right:2px;}
    .count .right .title ul{ position:absolute;top:0;left:-10px;}
    .count .right .title li{float:left;}
    .count .right .title .fl,.count .right .title .fr{width:100px; background-color:#3687bf;text-indent:-9999px;}
    .count .right .title .fm{width:330px;color:#fff;font-weight:bold; background-color:#3687bf;}

    .count .right .main{width:525px;height:auto; position:relative; margin-left:1px;overflow:hidden;}
    .count .right .main .list{width:100px;float:left;}
    .count .right .main .list .cate{width: 94%;font-size: 12px;margin-left:8%;height:38px;border:1px solid #adc2c3;font-size:12px; background-color:#e9f0f6; text-align:center; line-height:38px;margin-top:5px;}
    .count .right .main .list ul{padding-top:1px;padding-bottom:1px; position:relative; }
    .count .right .main .list li{height:73px;background-color:#A5CAE3;border-bottom:1px solid #fff;width:90%;font-size:13px;margin-left:10%; padding-top:15px; text-align:center}
    .count .right .main .list .two{background-color:#8EBCDD!important;}
    .count .right .main .list .three{background-color:#78AFD6!important;color:#fff;}
    .count .right .main .list .four{background-color:#62A2D0!important;color:#fff;}
    .count .right .main .list .five{background-color:#3886BA!important;color:#fff;}
    </style>
</head>
<body>
<div class="page-content">
    <div class="container">
        <div class="page-header">
            <h1>职位序列demo</h1>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <div class="count">
                    <h3>能力层级</h3>
                    <span class="arrow_btn"></span>

                    <div class="left">
                        <ul>
                            <li><p>行业专家</p></li>
                            <li><p>公司专家</p></li>
                            <li><p>业务骨干</p></li>
                            <li><p>独立工作者</p></li>
                            <li><p>辅助工作者</p></li>
                        </ul>
                    </div>
                    <div class="right">
                        <div class="main clearfix">
                            <div style="width:3135px;" id="boxes">
                                <div class="list">
                                    <ul>
                                        <li class="five">A4.1~A4.3<br/>职衔：2</li>
                                        <li class="four">A4.1~A4.3<br/>职衔：2</li>
                                        <li class="three">A4.1~A4.3<br/>职衔：2</li>
                                        <li class="two">A4.1~A4.3<br/>职衔：2</li>
                                        <li>A4.1~A4.3<br/>职衔：2</li>
                                    </ul>
                                    <p class="cate">人力资源</p>
                                </div>
                                <!--list-->
                                <div class="list">
                                    <ul>
                                        <li class="five">A4.1~A4.3<br/>职衔：2</li>
                                        <li class="four">A4.1~A4.3<br/>职衔：2</li>
                                        <li class="three">A4.1~A4.3<br/>职衔：2</li>
                                        <li class="two">A4.1~A4.3<br/>职衔：2</li>
                                        <li>A4.1~A4.3<br/>职衔：2</li>
                                    </ul>
                                    <p class="cate">企划</p>
                                </div>
                                <!--list-->
                                <div class="list">
                                    <ul>
                                        <li class="five">A4.1~A4.3<br/>职衔：2</li>
                                        <li class="four">A4.1~A4.3<br/>职衔：2</li>
                                        <li class="three">A4.1~A4.3<br/>职衔：2</li>
                                        <li class="two">A4.1~A4.3<br/>职衔：2</li>
                                        <li>A4.1~A4.3<br/>职衔：2</li>
                                    </ul>
                                    <p class="cate">财务</p>
                                </div>
                                <!--list-->
                                <div class="list">
                                    <ul>
                                        <li class="five">A4.1~A4.3<br/>职衔：2</li>
                                        <li class="four">A4.1~A4.3<br/>职衔：2</li>
                                        <li class="three">A4.1~A4.3<br/>职衔：2</li>
                                        <li class="two">A4.1~A4.3<br/>职衔：2</li>
                                        <li>A4.1~A4.3<br/>职衔：2</li>
                                    </ul>
                                    <p class="cate">稽核</p>
                                </div>
                                <!--list-->
                                <div class="list">
                                    <ul>
                                        <li class="five">A4.1~A4.3<br/>职衔：2</li>
                                        <li class="four">A4.1~A4.3<br/>职衔：2</li>
                                        <li class="three">A4.1~A4.3<br/>职衔：2</li>
                                        <li class="two">A4.1~A4.3<br/>职衔：2</li>
                                        <li>A4.1~A4.3<br/>职衔：2</li>
                                    </ul>
                                    <p class="cate">合规</p>
                                </div>
                                <!--list-->
                            </div>
                        </div>
                        <!--main-->
                        <div class="title">
                            <div>
                                <a href="#" class="pre"></a>
                                <a href="#" class="next"></a>
                            </div>
                            <ul class="clearfix">
                                <li class="fl">管理序列</li>
                                <li class="fm">职能序列</li>
                                <li class="fr">业务序列</li>
                            </ul>
                        </div>
                        <!--title-->
                    </div>
                    <!--right-->
                </div>
                <!--p_wrap-->
            </div>
        </div>
    </div>
</div>
<script>
    require(['jquery'], function ($) {

        $(".title ul").hover(function () {
            $(this).find("li").not(".fm").css({"background-color": "#cbcdfd", "text-indent": "0px"})
        }, function () {
            $(this).find("li").not(".fm").css({"background-color": "#3687bf", "text-indent": "-9999px"})
        });

        var trid = {
            init: function () {
                this.li = $(".list");
                this.licur = $(".list");
                this.ul = $("#boxes");
                this.len = $(".list").length;

                this.gor = $('.next');
                this.gol = $('.pre');
                this.format();
                this.bindact();
                this.door = true;
                this.curidx = 1;
            },
            format: function () {
                var w = (this.len) * 94 + 94 + "px";
                this.ul.css({
                    "width": w
                });
            },
            step: function (i) {
                var self = this;
                if (!self.door)
                    return false;

                self.door = false;
                self.curidx += i;
                var m = 94 * i;

                if (i < 0) {
                    if (self.curidx <= -1) {
                        self.curidx += self.li.length;
                    }
                    i = -i;
                    for (var k = 0; k < i; k++) {
                        self.ul.find("div:last").prependTo(self.ul);
                    }
                    self.ul.css({
                        "margin-left": m + "px"
                    });
                    self.ul.animate({
                        "margin-left": "0"
                    }, {
                        duration: 500,
                        complete: function () {
                            self.door = true;
                        }
                    });
                }
                else if (i > 0) {
                    if (self.curidx >= self.li.length) {
                        self.curidx -= self.li.length;
                    }
                    self.ul.animate({
                        "margin-left": -m + "px"
                    }, {
                        duration: 500,
                        complete: function () {
                            for (var k = 0; k < i; k++) {
                                self.ul.find("div:first").appendTo(self.ul);
                            }
                            self.ul.css({
                                "margin-left": 0
                            });
                            self.door = true;
                        }
                    });
                }
            },
            bindact: function () {
                var self = this;
                this.gor.click(function () {
                    self.step(-1);
                    $(this).blur();

                });
                this.gol.click(function () {
                    self.step(1);
                    $(this).blur();
                });

            }
        };
        trid.init();
    });
</script>
</body>
</html>