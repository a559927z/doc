<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<html>
<head>
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>人员对比</title>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/employee/talentContrast.css"/>
</head>
<body>
<div class="page-content">
    <div class="main-container-inner">
        <input type="hidden" id="empIds" value="${empIds}">
        <div class="row column" id="myScrollspy">
            <div class="col-xs-2" id="myNav_div">
                <ul class="nav u-affix-nav" id="myNav">
                    <li class="active"><a href="#section-1">个人信息</a></li>
                    <li><a href="#section-2">职务信息</a></li>
                    <li><a href="#section-3">个人绩效</a></li>
                    <li><a href="#section-4">个人能力</a></li>
                    <%--<li><a href="#section-5">培训经历</a></li>--%>
                    <li><a href="#section-6">工作经历</a></li>
                    <li><a href="#section-7">成长轨迹</a></li>
                </ul>
            </div>
            <div class="col-xs-10">
                <div class="panel panel-default page-contrast-main">
                    <div class="panel-heading">
                        <h3 class="panel-title">详细对比</h3>
                    </div>
                    <div class="panel-body">
                        <div class="panel panel-first">
                            <div class="panel-body">
                                <div class="row" id="contrastObj">
                                    <div class="col-xs-2"><h2>对比人员</h2></div>
                                    <div class="col-xs-10">
                                        <div class="row">
                                            <div class="col-xs-3"></div>
                                            <div class="col-xs-3"></div>
                                            <div class="col-xs-3"></div>
                                            <div class="col-xs-3"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="accordion" class="accordion-style1 panel-group">
                            <div class="panel panel-default">
                                <div class="panel-heading" id="section-1">
                                    <h3 class="panel-title">
                                        <a class="accordion-toggle" data-toggle="collapse"
                                           href="#collapseOne"> <i class="icon-angle-down bigger-110"
                                                                   data-icon-hide="icon-angle-down"
                                                                   data-icon-show="icon-angle-right"></i> &nbsp;个人信息
                                        </a>
                                    </h3>
                                </div>
                                <div class="panel-collapse collapse in" id="collapseOne">
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-xs-2"><span>性别</span></div>
                                            <div class="col-xs-10">
                                                <div class="row" id="sexRow">
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-2"><span>年龄</span></div>
                                            <div class="col-xs-10">
                                                <div class="row" id="ageRow">
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-2"><span>婚否</span></div>
                                            <div class="col-xs-10">
                                                <div class="row" id="marryStatusRow">
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-2"><span>学历</span></div>
                                            <div class="col-xs-10">
                                                <div class="row" id="degreeRow">
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="panel panel-default">
                                <div class="panel-heading" id="section-2">
                                    <h3 class="panel-title">
                                        <a class="accordion-toggle" data-toggle="collapse"
                                           href="#collapseTwo"> <i class="icon-angle-down bigger-110"
                                                                   data-icon-hide="icon-angle-down"
                                                                   data-icon-show="icon-angle-right"></i> &nbsp;职务信息
                                        </a>
                                    </h3>
                                </div>
                                <div class="panel-collapse collapse in" id="collapseTwo">
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-xs-2"><span>(分)公司</span></div>
                                            <div class="col-xs-10">
                                                <div class="row" id="organParentRow">
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-2"><span>部门</span></div>
                                            <div class="col-xs-10">
                                                <div class="row" id="organRow">
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-2"><span>入职时间</span></div>
                                            <div class="col-xs-10">
                                                <div class="row" id="entryDateRow">
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-2"><span>职位主序列</span></div>
                                            <div class="col-xs-10">
                                                <div class="row" id="sequenceRow">
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-2"><span>职位子序列</span></div>
                                            <div class="col-xs-10">
                                                <div class="row" id="sequenceSubRow">
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-2"><span>职位层级</span></div>
                                            <div class="col-xs-10">
                                                <div class="row" id="abilityRow">
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-2"><span>职级</span></div>
                                            <div class="col-xs-10">
                                                <div class="row" id="rankRow">
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-2"><span>现岗位任职时间</span></div>
                                            <div class="col-xs-10">
                                                <div class="row" id="postionAssumeDateRow">
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-2"><span>现岗位</span></div>
                                            <div class="col-xs-10">
                                                <div class="row" id="positionRow">
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="panel panel-default">
                                <div class="panel-heading" id="section-3">
                                    <h3 class="panel-title">
                                        <a class="accordion-toggle" data-toggle="collapse"
                                           href="#collapseThree"> <i
                                                class="icon-angle-down bigger-110"
                                                data-icon-hide="icon-angle-down"
                                                data-icon-show="icon-angle-right"></i> &nbsp;个人绩效
                                        </a>
                                    </h3>
                                </div>
                                <div class="panel-collapse collapse in" id="collapseThree">
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-xs-2">绩效轨迹</div>
                                            <input type="hidden" value="${performanceStr}" id="performanceStr">
                                            <div class="col-xs-10">
                                                <div class="row">
                                                    <div class="col-xs-3" id="perfTrackChart0"></div>
                                                    <div class="col-xs-3" id="perfTrackChart1"></div>
                                                    <div class="col-xs-3" id="perfTrackChart2"></div>
                                                    <div class="col-xs-3" id="perfTrackChart3"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="panel panel-default">
                                <div class="panel-heading" id="section-4">
                                    <h3 class="panel-title">
                                        <a class="accordion-toggle" data-toggle="collapse"
                                           href="#collapseFour"> <i
                                                class="icon-angle-down bigger-110"
                                                data-icon-hide="icon-angle-down"
                                                data-icon-show="icon-angle-right"></i> &nbsp;个人能力
                                        </a>
                                    </h3>
                                </div>
                                <div class="panel-collapse collapse in" id="collapseFour">
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-xs-2"><span>测评年度</span></div>
                                            <div class="col-xs-10">
                                                <div class="row" id="evalYearId">
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-3"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" >
                                            <div class="col-xs-12">能力维度</div>
                                        </div>
                                        <div class="" id="dimensionId"></div>
                                    </div>
                                </div>
                            </div>

                            <%--<div class="panel panel-default">--%>
                                <%--<div class="panel-heading" id="section-5">--%>
                                    <%--<h3 class="panel-title">--%>
                                        <%--<a class="accordion-toggle" data-toggle="collapse"--%>
                                           <%--href="#collapseFive"> <i--%>
                                                <%--class="icon-angle-down bigger-110"--%>
                                                <%--data-icon-hide="icon-angle-down"--%>
                                                <%--data-icon-show="icon-angle-right"></i> &nbsp;培训经历--%>
                                        <%--</a>--%>
                                    <%--</h3>--%>
                                <%--</div>--%>
                                <%--<div class="panel-collapse collapse in" id="collapseFive">--%>
                                    <%--<div class="panel-body">--%>
                                        <%--<div class="row">--%>
                                            <%--<div class="col-xs-2" style="line-height: 144px;">培训经历</div>--%>
                                            <%--<div class="col-xs-10">--%>
                                                <%--<div class="col-xs-3">--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-5">时间</div>--%>
                                                        <%--<div class="col-xs-7">课程/项目名称</div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-5">2014-03-20</div>--%>
                                                        <%--<div class="col-xs-7">高效能人士的7个好...</div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-5">2013-07-02</div>--%>
                                                        <%--<div class="col-xs-7">ioc aop 框架</div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-5"></div>--%>
                                                        <%--<div class="col-xs-7"></div>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-xs-3">--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-5">时间</div>--%>
                                                        <%--<div class="col-xs-7">课程/项目名称</div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-5">2014-09-05</div>--%>
                                                        <%--<div class="col-xs-7">高效能人士的7个好...</div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-5">2013-08-02</div>--%>
                                                        <%--<div class="col-xs-7">时间管理</div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-5">2012-07-10</div>--%>
                                                        <%--<div class="col-xs-7">IOS开发</div>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-xs-3"></div>--%>
                                                <%--<div class="col-xs-3"></div>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>

                            <div class="panel panel-default">
                                <div class="panel-heading" id="section-6">
                                    <h3 class="panel-title">
                                        <a class="accordion-toggle" data-toggle="collapse"
                                           href="#collapseSix"> <i
                                                class="icon-angle-down bigger-110"
                                                data-icon-hide="icon-angle-down"
                                                data-icon-show="icon-angle-right"></i> &nbsp;工作经历
                                        </a>
                                    </h3>
                                </div>
                                <div class="panel-collapse collapse in" id="collapseSix">
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-xs-2" id="departChange">本公司经历</div>
                                            <div class="col-xs-10">
                                                <div class="col-xs-3">
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-4">时间</div>--%>
                                                        <%--<div class="col-xs-4">公司</div>--%>
                                                        <%--<div class="col-xs-4">岗位</div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-4">2010/03/20-2011/08/11</div>--%>
                                                        <%--<div class="col-xs-4">百度</div>--%>
                                                        <%--<div class="col-xs-4">软件工程师</div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-4">2008/03/10-2010/03/21</div>--%>
                                                        <%--<div class="col-xs-4">联鑫</div>--%>
                                                        <%--<div class="col-xs-4">软件工程师</div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-4">2007/06/18-2008/03/10</div>--%>
                                                        <%--<div class="col-xs-4">创思</div>--%>
                                                        <%--<div class="col-xs-4">软件工程师</div>--%>
                                                    <%--</div>--%>
                                                </div>
                                                <div class="col-xs-3">
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-4">时间</div>--%>
                                                        <%--<div class="col-xs-4">公司</div>--%>
                                                        <%--<div class="col-xs-4">岗位</div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-4">2004/03/20-2011/08/11</div>--%>
                                                        <%--<div class="col-xs-4">力得</div>--%>
                                                        <%--<div class="col-xs-4">软件架构师</div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-4"></div>--%>
                                                        <%--<div class="col-xs-4"></div>--%>
                                                        <%--<div class="col-xs-4"></div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-4"></div>--%>
                                                        <%--<div class="col-xs-4"></div>--%>
                                                        <%--<div class="col-xs-4"></div>--%>
                                                    <%--</div>--%>
                                                </div>
                                                <div class="col-xs-3"></div>
                                                <div class="col-xs-3"></div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-2" id="pastResume">过往履历</div>
                                            <div class="col-xs-10">
                                                <div class="col-xs-3">
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-4">时间</div>--%>
                                                        <%--<div class="col-xs-4">公司</div>--%>
                                                        <%--<div class="col-xs-4">岗位</div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-4">2010/03/20-2011/08/11</div>--%>
                                                        <%--<div class="col-xs-4">百度</div>--%>
                                                        <%--<div class="col-xs-4">软件工程师</div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-4">2008/03/10-2010/03/21</div>--%>
                                                        <%--<div class="col-xs-4">联鑫</div>--%>
                                                        <%--<div class="col-xs-4">软件工程师</div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-4">2007/06/18-2008/03/10</div>--%>
                                                        <%--<div class="col-xs-4">创思</div>--%>
                                                        <%--<div class="col-xs-4">软件工程师</div>--%>
                                                    <%--</div>--%>
                                                </div>
                                                <div class="col-xs-3">
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-4">时间</div>--%>
                                                        <%--<div class="col-xs-4">公司</div>--%>
                                                        <%--<div class="col-xs-4">岗位</div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-4">2004/03/20-2011/08/11</div>--%>
                                                        <%--<div class="col-xs-4">力得</div>--%>
                                                        <%--<div class="col-xs-4">软件架构师</div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-4"></div>--%>
                                                        <%--<div class="col-xs-4"></div>--%>
                                                        <%--<div class="col-xs-4"></div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-xs-4"></div>--%>
                                                        <%--<div class="col-xs-4"></div>--%>
                                                        <%--<div class="col-xs-4"></div>--%>
                                                    <%--</div>--%>
                                                </div>
                                                <div class="col-xs-3"></div>
                                                <div class="col-xs-3"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="panel panel-default">
                                    <div class="panel-heading" id="section-7">
                                        <h3 class="panel-title">
                                            <a class="accordion-toggle" data-toggle="collapse"
                                               href="#collapseSeven"> <i
                                                    class="icon-angle-down bigger-110"
                                                    data-icon-hide="icon-angle-down"
                                                    data-icon-show="icon-angle-right"></i> &nbsp;成长轨迹
                                            </a>
                                        </h3>
                                    </div>
                                    <div class="panel-collapse collapse in" id="collapseSeven">
                                        <div class="panel-body">
                                            <div class="row">
                                                <div class="col-xs-2"><span>查看成长轨迹</span></div>
                                                <div class="col-xs-10" >
                                                    <div class="row" id="growthLinkArea">
                                                        <div class="col-xs-3"></div>
                                                        <div class="col-xs-3"></div>
                                                        <div class="col-xs-3"></div>
                                                        <div class="col-xs-3"></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="hiddrenObj" class="hide">
        <img src="${ctx}/assets/img/base/u-default.png"/>
        <button class="btn btn-light btn-header-search">搜索添加&nbsp;<i class="icon-plus"></i></button>
    </div>
    <!-- 成长轨迹弹出框 -->
    <div id="growModal" class="modal fade" data-backdrop="static" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title">个人成长轨迹 —<span></span></h3>
                </div>
                <div class="modal-body">
                    <div class="row">
                    	<div id="jobChangeChart" class="col-xs-12"></div>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
    
    <!-- 搜索弹出框 -->
    <div id="searchModal" class="modal fade" data-backdrop="static" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <input type="hidden" id="searchIndex" >
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title">添加人员</h3>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-7">
                            <div class="input-group">
                                <input type="text" class="form-control search-query" id="searchTxt" placeholder="请输入员工ID/姓名" />
                                <span class="input-group-btn">
                                    <button type="button" class="btn btn-white" id="searchBtn">
                                        	查询
                                        <i class="icon-search icon-on-right bigger-110"></i>
                                    </button>
                                </span>
                            </div>
                        </div>
                        <div class="col-xs-5"><label>* 姓名支持模糊查询</label></div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12 column" id="searchEmpTable">
                            <table class="borderless" id="searchEmpGrid"></table>
                        </div>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
</div>
<script type="text/javascript" src="${jsRoot}require.js"></script>
<script type="text/javascript" src="${jsRoot}lib/plugins/echarts/echarts.js"></script>
<script type="text/javascript" src="${jsRoot}biz/employee/talentContrast.js"></script>
</body>
</html>