<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
     <%@include file="../include/top.jsp" %>
    <title>人均效益</title>
     
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/tab.css?v=${v.version}"/>
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/biz/perBenefit/perBenefit.css?v=${v.version}"/>
</head>
<body>
<input type="hidden" id="endYearMonth" value="${endYearMonth}"/>
<input type="hidden" id="startYearMonth" value="${startYearMonth}"/>
<input type="hidden" id="startMonth" value="${startMonth}"/>
<input type="hidden" id="year" value="${year}"/>
<input type="hidden" id="lastMonth" value="${lastMonth}"/>

<input type="hidden" id="reqOrganId" value="${reqOrganId}"/>
<div class="page-content ovfHiden">
    <div class="main-container-inner">
            <div class="row ct-row perBenefit ">
                <div class="col-xs-12 ct-11 ct-line-col">
                	<div>
                		<div class="benefit-normal col-xs-4 ct-4" >
	                 		<span class="benefit-value">0</span>
	                 		 <div><label>人均效益(万元)</label></div>
	                	</div>
	                	 <div class="benefit-operator">
	                 		<span>=</span>
	                	</div>
		                <div class="benefit-normal  col-xs-3 ct-3">
		                 	<span class="benefit-profit">0</span>
		                 	 <div><label>营业利润(万元)</label></div>
		                </div>
		                <div class="benefit-operator">
		                	 <span>/</span>
		                </div>
		                <div class="benefit-normal  col-xs-3 ct-3">
		                 	 <span class="eqEmpNum">0</span>
		                 	 <div><label>员工数</label></div>
		                </div>
                	</div>
	               
 					<div class="benefit-note" >
 						<div class="icon"></div>
 						<label class="trade-warn"></label> <span class="value"></span><span class="time">(统计时间：${lastMonth})</span>	
 					</div>
                </div>
          </div>
          
          <div class="fixed">
	  		  <div class="row perBenefit fixed-tab" id="tabPanel">
	                <div class="col-xs-12 tabPanel" >
	               		<div class="col-xs-3" align="center"><div class="tab1-active" aria-controls="ztqsPanel" data-toggle="tab"><div class="icon"></div>整体趋势</div></div>
	               		<div class="col-xs-3" align="center"><div class="tab2" aria-controls="ydqsPanel" data-toggle="tab"><div class="icon"></div>月度趋势</div></div>
	               		<div class="col-xs-3" align="center"><div class="tab3" aria-controls="xjdbPanel" data-toggle="tab"><div class="icon"></div>下级对比</div></div>
	               		<div class="col-xs-3" align="center"><div class="tab5" aria-controls="ygsgcPanel" data-toggle="tab"><div class="icon"></div>员工数构成</div></div>
	                </div>
	          </div>
          </div>
		  <div class="row perBenefit tab-content ct-row">
		  		<div class="col-xs-12 tab-panel active" id="ztqsPanel">
		  			<div class="content">
			        	<div class="xyzt"></div>
			        </div>	
                 	<div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">人均效益环比</div>
				        </div>
				        <div class="content">
<%-- 				        	<div class="tjzq">统计周期：${timeRange}</div> --%>
							<div class="chartPanel">
	               		  	  <div class="chart" id="benefitChart"></div>
	               		    </div>
				        </div>	
			        </div>
			        <div class="panel">
			        	<div class="tab-panel-title">
			        		<div class="tab-panel-title-left"></div>
			        		<div class="tab-panel-title-text">营业利润环比</div>
				        </div>
				        <div class="content">
<%-- 				        	<div class="tjzq">统计周期：${timeRange}</div> --%>
							<div class="chartPanel">
	               		    	<div class="chart" id="profitChart"></div>
	               		    </div>
				        </div>	
			        </div>
			        <div class="panel">
			        	<div class="tab-panel-title">
				            <div class="tab-panel-title-left"></div>
				            <div class="tab-panel-title-text">员工数环比</div>
				        </div>
				        <div class="content">
<%-- 				        	<div class="tjzq">统计周期：${timeRange}</div> --%>
							<div class="chartPanel">
	               		  	  <div class="chart" id="empNumChart"></div>
	               		  	</div>
				        </div>	
			        </div>
                </div>
		  
		  
               
                <div class="col-xs-12 tab-panel" id="ydqsPanel">
			        <div class="content">
			        	<div class="title">统计周期：${startYearMonth} - ${endYearMonth}</div>
			        	 <div class="chartPanel">  
			        	    <div class="chart" id="ydqsChart"></div>
			        	  </div>
			        </div>	
                </div>
                <div class="col-xs-12 tab-panel " id="xjdbPanel">
			        <div class="content">
			       		<div class="title">统计周期：${startMonth} - ${endYearMonth}</div>
	               		<div class="chartPanel">
	               			<div class="chart" id="inferiorChart"></div>
	               		</div>
			        </div>	
                </div>
                 <div class="col-xs-12 tab-panel" id="ygsgcPanel">
               		<div class="content">
			       		<div class="title">统计周期：${startMonth} - ${endYearMonth}</div>
	               		<div class="chartPanel">
	               			<div class="chart" id="empComposingChart"></div>
	               		</div>
			        </div>	
                </div>
               
          </div>	
	</div>
</div>
<div class="shade"></div>
<script type="text/javascript" src="${ctx}/assets/js/require.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/lib/plugins/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/assets/mobile/js/biz/perBenefit/perBenefit.js?v=${v.version}"></script>
</body>
</html>
