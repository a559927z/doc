<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>职位主序列统计</title>
    <%@include file="/WEB-INF/views/include/top.jsp" %>


    <%-- <link rel="stylesheet" href="${webRoot}/static/css/widget.css"></link> --%>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>

    <link rel="stylesheet" href="${ctx}/assets/css/job_main_seq_count2.css"/>
</head>
<body>

<div class="grid " style="min-width:1138px;">
    <input type="hidden" id="initFormatDate" value="${date}"/>
    <input type="hidden" id="initDate" value="${date}"/>
    <input type="hidden" id="mainSeqId" value="${fn:escapeXml(mainSeqId)}"/>
    <!-- TODO 从Controller转入机构Id和名称 -->
    <%-- 					<input type="hidden" id="uniqueId" value="${fn:escapeXml(uniqueDeptId)}"/> --%>
    <%-- 					<input type="hidden" id="deptName" value="${deptName}"/> --%>

    <input type="hidden" id="uniqueId" value="PA001_S000000002"/>
    <input type="hidden" id="deptName" value="集团总部"/>

    <!-- bootstrap的24栅格系统 开始 -->
    <div class="row column">
        <div class=" col-sm-2 col-xs-4">
            <div class="span10 condition">
                <div class="row condition-item">
                    <ul id="tree"></ul>
                    <input type="hidden" value="${uniqueDeptId}" id="uniqueDeptId"/>
                </div>
            </div>
        </div>
    </div>
    <!-- bootstrap的24栅格系统 结束 -->


    <!-- bootstrap的24栅格系统 开始 -->
    <div class="row column">
        <!-- fluid的24栅格系统 开始 -->
        <div class=" col-sm-12 col-xs-24">

            <!-- left -->
            <div class="span10" id="seqchart">
                <div id="seq" style="width:480px; height:400px;margin-top:40px;"></div>
            </div>
            <div class="span12">
                <div id="seqtable"></div>
            </div>
            <!-- left -->

            <!-- right -->
            <div class="span17 detailseq_area" style="display:none;">
                <span style="float:right;margin-top:10px;margin-right:10px;"><a class="seqdetailclose" id="titleLvClose"
                                                                                href="javascript:void(0);"></a></span>
                <div class="seqdetail">

                    <div class="childseq">
                        <div class="childseq_con">
                            <div class="span12">
                                <div class="title">
                                    <span>序列分布</span>
                                </div>
                                <div id="chart1" style="width:350px;height:300px; background:purple;"></div>
                            </div>
                            <div class="span12">
                                <div class="title">
                                    <span>职级分布</span>
                                </div>
                                <div id="chart2" style="width:350px;height:300px;  background: gray;"></div>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix"></div>

                    <div class="childseq_con" id="subSeq">
                        <div class="span24">
                            <div class="title">
                                <span>入职名单</span>
                            </div>
                            <div id="chart3" style="width:100%;height:250px;  background: teal;"></div>
                        </div>
                    </div>


                </div>
                <div class="clearfix"></div>
            </div>
            <!-- right -->

        </div>
        <!-- fluid的24栅格系统 结束 -->
    </div>
    <!-- bootstrap的24栅格系统 结束 -->
</div>

<script src="${jsRoot}lib/echarts/echarts.js"></script>
<script src="${jsRoot}demo/job_main_seq_count2.js" charset="utf-8"></script>
</body>
</html>