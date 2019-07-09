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
            <div class="page-top">
                <div class="content">
                	<div align="center" class="top-content-note" >
 						<div class="icon"></div>
 						<div><label class="trade-warn"></label> 
<!--  						<span class="value"></span> -->
 						<span class="time"></span></div>
 					</div>
                	<div align="center" class="top-content-panel">
                		<div class="top-panel" >
	                 		 <div><label>人均效益(万元)</label></div>
	                 		 <span class="benefit-value value">0</span>
	                	</div>
	                	 <div class="top-operator">
	                 		<span>=</span>
	                	</div>
		                <div class="top-panel">
		                 	 <div><label>营业利润(万元)</label></div>
		                 	 <span class="benefit-profit">0</span>
		                </div>
		                <div class="top-operator">
		                	 <span>/</span>
		                </div>
		                <div class="top-panel ">
		                 	 <div><label>员工数</label></div>
		                 	  <span class="eqEmpNum">0</span>
		                </div>
                	</div>
                </div>
          </div>
          
          <div class="fixed">
	  		  <div class="fixed-tab">
	                <div class="col-xs-12 tabPanel" >
	               		<div class="col-xs-3 tab12-active active" aria-controls="ztqsPanel" data-toggle="tab"><div class="icon"></div>整体趋势</div>
	               		<div class="col-xs-3 tab1" aria-controls="ydqsPanel" data-toggle="tab"><div class="icon"></div>月度趋势</div>
	               		<div class="col-xs-3 tab2" aria-controls="xjdbPanel" data-toggle="tab"><div class="icon"></div>下级对比</div>
	               		<div class="col-xs-3 tab13" aria-controls="ygsgcPanel" data-toggle="tab"><div class="icon"></div>员工数构成</div>
	                </div>
	          </div>
          </div>
		  <div class="tab-content ct-row">
		  		<div class="col-xs-12 tab-panel active" id="ztqsPanel">
                 	<div class="panel">
                 		<div class="xyzt"></div>
                 		<div class="content">
		                 	<div class="tabPanel-interior">
			               		<div class="tab-interior active" aria-controls="rjxy" data-toggle="tab">人均效益</div>
			               		<div class="tab-interior" aria-controls="yylr" data-toggle="tab">营业利润</div>
			               		<div class="tab-interior" aria-controls="ygs" data-toggle="tab">员工数</div>
			               	</div>
		               		<div class="tab-interior-content ct-row">
	               		 		<div class="tab-interior-panel active" id="rjxy">
	               		 			<div class="panel">
		               		 			<div class="content">
					        				<div class="chartPanel">
		               		   		 			<div class="chart" id="benefitChart"></div>
	               		    				</div>
				        				</div>	
	               		 			</div>
	               		 		</div>
	               		 		<div class="tab-interior-panel" id="yylr">
	               		 			<div class="panel">
				        				<div class="content">
								        	<div class="chartPanel">
					               		    	<div class="chart" id="profitChart"></div>
					               		    </div>
								        </div>	
	               		 			</div>
	               		 		</div>
	               		 		<div class="tab-interior-panel" id="ygs">
	               		 			<div class="panel">
	               		 				 <div class="content">
								        	<div class="chartPanel">
					               		   		<div class="chart" id="empNumChart"></div>
					               		    </div>
								        </div>	
	               		 			</div>
	               		 		</div>
		               		</div>
	               		 </div>	
			        </div>
                </div>
                <div class="col-xs-12 tab-panel" id="ydqsPanel">
                	 <div class="panel">
<!-- 			        	<div class="tab-panel-title"> -->
<!-- 				            <div class="tab-panel-title-left"></div> -->
<!-- 				            <div class="tab-panel-title-text">月度趋势</div> -->
<!-- 				            <div class="tab-panel-title-zoom"></div> --> 
<!-- 				        </div> -->
				        <div class="content">
							<div class="title">统计周期：${startYearMonth} - ${endYearMonth}</div>
							<div class="chartPanel">
	               		  	  <div class="chart" id="ydqsChart"></div>
	               		  	</div>
				        </div>	
			        </div>
                </div>
                <div class="col-xs-12 tab-panel " id="xjdbPanel">
                	<div class="panel">
<!-- 			        	<div class="tab-panel-title"> -->
<!-- 				            <div class="tab-panel-title-left"></div> -->
<!-- 				            <div class="tab-panel-title-text">下级对比</div> -->
<!-- 				            <div class="tab-panel-title-zoom"></div> -->
<!-- 				        </div> -->
				        <div class="content">
							<div class="title">统计周期：${startYearMonth} - ${endYearMonth}</div>
							<div class="chartPanel">
	               		  	  <div class="chart" id="inferiorChart"></div>
	               		  	</div>
				        </div>	
			        </div>
                </div>
                 <div class="col-xs-12 tab-panel" id="ygsgcPanel">
                 	<div class="panel">
<!-- 			        	<div class="tab-panel-title"> -->
<!-- 				            <div class="tab-panel-title-left"></div> -->
<!-- 				            <div class="tab-panel-title-text">员工数构成</div> -->
<!-- 				            <div class="tab-panel-title-zoom"></div> -->
<!-- 				        </div> -->
				        <div class="content">
							<div class="title">统计周期：${startYearMonth} - ${endYearMonth}</div>
							<div class="chartPanel">
	               		  	  <div class="chart" id="empComposingChart"></div>
	               		  	</div>
				        </div>	
			        </div>
                </div>
               
          </div>	
	</div>
</div>
<div class="shade"></div>
<script src="${ctx}/assets/js/require.js"></script>
<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/mobile/js/biz/perBenefit/perBenefit.js?v=${v.version}"></script>
</body>
</html>
