<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <!-- jsp文件头和头部 -->
  <%@include file="/WEB-INF/views/include/top.jsp"%>
  <title>组织架构(编制和空缺)</title>
  <link rel="stylesheet"
        href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css" />
  <link rel="stylesheet"
        href="${ctx}/assets/css/biz/other/orgChart2.css" />
</head>
<body>

<div class="orgchart-board" id="orgchartboard">
  <div class="rightBody">
    <div id="page-one" class="rightBodyPage">

      <div class="row ct-row">
        <div class="col-md-12 ct-line-col SetUpBody" view="chart">
          <div class="index-common-title bottom-title">
            <div class="index-common-title-left bottom-left"></div>
            <div class="index-common-title-text bottom-text">组织架构(编制和空缺)</div>
          </div>

          <div class="bottom-div clearfix">
            <div class="chart-view">
              <div class="col-xs-12 padding0 paddingtop10" id="benefitTrendMonth">

                <div id="organArea" class="organArea">
                  <!-- 空白区域，作用是占一块区域，让拓扑图收缩时没有闪动的效果 -->
                  <div id="placeHolder"></div>
                  <!-- 组织机构职数拓扑图 -->
                  <div class="clearfix" id="organDiv"></div>
                </div>
                <div id="showUnResultMsg" class="hidden span24">很抱歉，没有找到相关的记录</div>

                <!-- 拓扑图弹出框 -->
                <div id="showDetail" class="" style="display:none;"></div>

              </div>
            </div>
          </div>
        </div>
      </div>




    </div>
  </div>
</div>


<script src="${ctx}/assets/js/biz/other/orgChart2.js"></script>

</body>
</html>
