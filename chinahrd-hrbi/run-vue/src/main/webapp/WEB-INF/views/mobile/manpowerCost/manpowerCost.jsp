<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="../include/top.jsp" %>
    <title>人力成本</title>
        <link rel="stylesheet" href="${ctx}/assets/mobile/css/tab.css?v=${v.version}"/>
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/biz/manpowerCost/manpowerCost.css?v=${v.version}"/>
</head>
<body>
    <input type="hidden" id="curdate" value="${curdate}">
<input type="hidden" id="reqOrganId" value="${reqOrganId}"/>
<input type="hidden" id="reqOrganName" value="${reqOrganName}"/>
<div class="page-content ovfHiden">
    <div class="main-container-inner">
            <div class="page-top">
            	<div align="center"  class="content">
                    <div id="LSLChart" class="top-chart"></div>
                    <div class="top-chart-icon-value"> 
                      	<div class="accord-main-icon-item">
        					  <span class="top-chart-icon-yellow"></span>
                             <span class="top-chart-icon-note-warn"></span>             	
                      	</div>
                      	<div class="top-chart-icon-item">
		  					<span class="top-chart-icon-red"></span>
                            <span class="top-chart-icon-note-over"></span>
                        </div>
  					</div>
				  <div class="top-chart-content-div">
				  	<span  class="top-chart-content"></span>
				  	<span class="top-chart-date"></span>
				  </div>
               </div>
          </div>
          
          <div class="fixed">
	  		  <div class="fixed-tab">
	                <div class="col-xs-12 tabPanel" >
	               		<div class="col-xs-3 tab14-active active" aria-controls="zxqkPanel" data-toggle="tab"><div class="icon"></div>执行情况</div>
	               		<div class="col-xs-3 tab6" aria-controls="qsbhPanel" data-toggle="tab"><div class="icon"></div>趋势变化</div>
	               		<div class="col-xs-3 tab15" aria-controls="jgdbPanel" data-toggle="tab"><div class="icon"></div>架构对比</div>
	               		<div class="col-xs-3 tab16" aria-controls="cbjgPanel" data-toggle="tab"><div class="icon"></div>成本结构</div>
	                </div>
	          </div>
          </div>
		  <div class="tab-content ct-row">
                <div class="col-xs-12 tab-panel active" id="zxqkPanel">
                	<div class="panel">
<!-- 			        	<div class="tab-panel-title"> -->
<!-- 				            <div class="tab-panel-title-left"></div> -->
<!-- 				            <div class="tab-panel-title-text">执行情况</div> -->
<!-- 				            <div class="tab-panel-title-zoom"></div> -->
<!-- 				        </div> -->
				        <div class="content">
				        	<div class="chartPanel">
	               		   		<div class="chart cost-chart" id="trendByMonth"></div>
	               		    </div>
				        </div>	
			        </div>
<!-- 			        <div class="content"> -->
<!--                		    <div class="chartPanel"> -->
<!--                		    	<div class="chart cost-chart" id="trendByMonth"></div> -->
<!--                		    </div> -->
               		   
<!-- 			        </div>	 -->
                </div>
                <div class="col-xs-12 tab-panel" id="qsbhPanel">
                	<div class="panel">
<!-- 			        	<div class="tab-panel-title"> -->
<!-- 				            <div class="tab-panel-title-left"></div> -->
<!-- 				            <div class="tab-panel-title-text">趋势变化</div> -->
<!-- 				            <div class="tab-panel-title-zoom"></div> -->
<!-- 				        </div> -->
				        <div class="content">
				        	<div class="title" id="manpowerCostTrends">人力成本占总本比例：<span class="currentMonth"></span></div>
				        	<div class="chartPanel">
	               		   		<div class="chart cost-chart" id="manpowerCostTrendsChars"></div>
	               		    </div>
				        </div>	
			        </div>
<!-- 			        <div class="content"> -->
<!-- 			        	<div class="title" id="manpowerCostTrends">人力成本占总本比例：<span class="currentMonth"></span></div> -->
<!-- 			        	<div class="chartPanel"> -->
<!--                		    	<div class="chart cost-chart" id="manpowerCostTrendsChars"></div> -->
<!--                		    </div> -->
<!-- 			        </div>	 -->
                </div>
                 <div class="col-xs-12 tab-panel" id="jgdbPanel">
                 	<div class="panel">
<!-- 			        	<div class="tab-panel-title"> -->
<!-- 				            <div class="tab-panel-title-left"></div> -->
<!-- 				            <div class="tab-panel-title-text">架构对比</div> -->
<!-- 				            <div class="tab-panel-title-zoom"></div> -->
<!-- 				        </div> -->
				        <div class="content">
				        	<div class="chartPanel">
	               		   		<div class="chart cost-chart" id="contrastOrgChart"></div>
	               		    </div>
				        </div>	
			        </div>
<!-- 			        <div class="content"> -->
<!-- 			        	<div class="chartPanel"> -->
<!--                		    	<div class="chart cost-chart" id="contrastOrgChart"></div> -->
<!--                		    </div> -->
<!-- 			        </div>	 -->
                </div>
                 <div class="col-xs-12 tab-panel" id="cbjgPanel">
                 	<div class="panel">
<!-- 			        	<div class="tab-panel-title"> -->
<!-- 				            <div class="tab-panel-title-left"></div> -->
<!-- 				            <div class="tab-panel-title-text">成本结构</div> -->
<!-- 				            <div class="tab-panel-title-zoom"></div> -->
<!-- 				        </div> -->
				        <div class="content">
				        	<div class="chartPanel">
	               		   		<div class="chart cost-chart" id="contrastDetailChart"></div>
	               		    </div>
				        </div>	
			        </div>
<!--                		 <div class="content"> -->
<!-- 			        	<div class="chartPanel"> -->
<!--                		    	<div class="chart cost-chart" id="contrastDetailChart"></div> -->
<!--                		    </div> -->
<!-- 			        </div>	 -->
                </div>
               
          </div>	
	</div>
</div>
<script src="${ctx}/assets/js/require.js"></script>
<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/mobile/js/biz/manpowerCost/manpowerCost.js?v=${v.version}"></script>
</body>
</html>
