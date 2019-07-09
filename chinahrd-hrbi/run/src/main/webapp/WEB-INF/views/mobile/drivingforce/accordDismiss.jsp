<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="../include/top.jsp" %>
    <title>主动流失率</title>
        <link rel="stylesheet" href="${ctx}/assets/mobile/css/tab.css?v=${v.version}"/>
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/biz/drivingforce/accordDismiss.css?v=${v.version}"/>
</head>
<body>
<input type="hidden" id="quarterLastDay" value="${quarterLastDay}"/>
<input type="hidden" id="reqOrganId" value="${reqOrganId}"/>
<input type="hidden" id="parentOrganId" value="${parentOrganId}"/>
<input type="hidden" id="reqOrganName" value="${reqOrganName}"/>
<input type="hidden" id="parentOrganName" value="${parentOrganName}"/>
<input type="hidden" id="timeRange" value="${timeRange}"/>

<div class="page-content ovfHiden">
    <div class="main-container-inner">
            <div class="page-top">
                	<div align="center"  class="content">
                          <div id="LSLDismissChart" class="top-chart"></div>
                          <div class="top-chart-icon-value"> 
	                           	<div class="top-chart-icon-item">
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
	               		<div class="col-xs-3  tab1-active active" aria-controls="ydqsPanel" data-toggle="tab"><div class="icon"></div>月度趋势</div>
	               		<div class="col-xs-3  tab2" aria-controls="xjdbPanel" data-toggle="tab"><div class="icon"></div>下级对比</div>
	               		<div class="col-xs-3  tab3" aria-controls="wdfxPanel" data-toggle="tab"><div class="icon"></div>维度分析</div>
	               		<div class="col-xs-3  tab4" aria-controls="xxmdPanel" data-toggle="tab"><div class="icon"></div>详细名单</div>
	                </div>
	          </div>
          </div>
		  <div class="tab-content ct-row">
                <div class="col-xs-12 tab-panel active" id="ydqsPanel">
                	<div class="panel">
<!-- 			            <div class="tab-panel-title"> -->
<!-- 			            	<div class="tab-panel-title-left"></div> -->
<!-- 			            	<div class="tab-panel-title-text">月度趋势</div> -->
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
<!-- 				        </div> -->
				        <div class="content">
				        	<div class="title">统计周期：<span id="ydqsTjzq">${timeRange}</span></div>
				        	<div class="chartPanel">
	               		   		 <div class="chart" id="ydqsChart"></div>
	               		    </div>
				        </div>	
			        </div>
			       
                </div>
                <div class="col-xs-12 tab-panel" id="xjdbPanel">
                	<div class="panel">
<!-- 			            <div class="tab-panel-title"> -->
<!-- 			            	<div class="tab-panel-title-left"></div> -->
<!-- 			            	<div class="tab-panel-title-text">下级对比</div> -->
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
<!-- 				        </div> -->
				        <div class="content">
				        	<div class="title">统计周期：${timeRange}</div>
				        	<div class="chartPanel">
	               		   		 <div class="chart" id="inferiorChart"></div>
	               		    </div>
				        </div>	
			        </div>
                </div>
                 <div class="col-xs-12 tab-panel" id="wdfxPanel">
                 	<div class="panel">
<!-- 			            <div class="tab-panel-title"> -->
<!-- 			            	<div class="tab-panel-title-left"></div> -->
<!-- 			            	<div class="tab-panel-title-text">按职级统计</div> -->
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
<!-- 				        </div> -->
						<div class="tabPanel-interior" >
		               		<div class="tab-interior active" aria-controls="zjtj" data-toggle="tab">职级统计</div>
		               		<div class="tab-interior" aria-controls="jxtj" data-toggle="tab">绩效统计</div>
		               		<div class="tab-interior" aria-controls="sltj" data-toggle="tab">司龄统计</div>
	               		</div>
	               		<div class="tab-interior-content ct-row">
	               		 		<div class="tab-interior-panel active" id="zjtj">
	               		 			<div class="panel">
		               		 			<div class="content">
					        				<div class="title">统计周期：${timeRange}</div>
<!-- 					        				<div class="title">(目前只统计非管理人员)</div> -->
					        				<div class="chartPanel">
		               		   		 			<div class="chart" id="abilityChart"></div>
	               		    				</div>
				        				</div>	
	               		 			</div>
	               		 		</div>
	               		 		<div class="tab-interior-panel" id="jxtj">
	               		 			<div class="panel">
				        				<div class="content">
								        	<div class="title">统计周期：${timeRange}</div>
								        	<div class="chartPanel">
					               		    	<div class="chart" id="perfChart"></div>
					               		    </div>
								        </div>	
	               		 			</div>
	               		 		</div>
	               		 		<div class="tab-interior-panel" id="sltj">
	               		 			<div class="panel">
	               		 				 <div class="content">
								        	<div class="title">统计周期：${timeRange}</div>
								        	<div class="chartPanel">
					               		   		<div class="chart" id="ageChart"></div>
					               		    </div>
								        </div>	
	               		 			</div>
	               		 		</div>
	               		</div>
<!-- 				        <div class="content"> -->
<%-- 				        	<div class="title">统计周期：${timeRange}</div> --%>
<!-- 				        	<div class="title">(目前只统计非管理人员)</div> -->
<!-- 				        	<div class="chartPanel"> -->
<!-- 	               		   		 <div class="chart" id="abilityChart"></div> -->
<!-- 	               		    </div> -->
<!-- 				        </div>	 -->
			        </div>
<!-- 			        <div class="panel"> -->
<!-- 			        	<div class="tab-panel-title"> -->
<!-- 			        		<div class="tab-panel-title-left"></div> -->
<!-- 			        		<div class="tab-panel-title-text">按绩效统计</div> -->
<!-- 			        		<div class="tab-panel-title-zoom"></div> -->
<!-- 				        </div> -->
<!-- 				        <div class="content"> -->
<%-- 				        	<div class="title">统计周期：${timeRange}</div> --%>
<!-- 				        	<div class="chartPanel"> -->
<!-- 	               		    	<div class="chart" id="perfChart"></div> -->
<!-- 	               		    </div> -->
<!-- 				        </div>	 -->
<!-- 			        </div> -->
<!-- 			        <div class="panel"> -->
<!-- 			        	<div class="tab-panel-title"> -->
<!-- 				            <div class="tab-panel-title-left"></div> -->
<!-- 				            <div class="tab-panel-title-text">按司龄统计</div> -->
<!-- 				            <div class="tab-panel-title-zoom"></div> -->
<!-- 				        </div> -->
<!-- 				        <div class="content"> -->
<%-- 				        	<div class="title">统计周期：${timeRange}</div> --%>
<!-- 				        	<div class="chartPanel"> -->
<!-- 	               		   		<div class="chart" id="ageChart"></div> -->
<!-- 	               		    </div> -->
<!-- 				        </div>	 -->
<!-- 			        </div> -->
                </div>
                 <div class="col-xs-12 tab-panel" id="xxmdPanel">
                 	 <div class="panel table-rectify">
<!-- 			        	<div class="tab-panel-title"> -->
<!-- 				            <div class="tab-panel-title-left"></div> -->
<!-- 				            <div class="tab-panel-title-text">详细名单</div> -->
<!-- 				            <div class="tab-panel-title-zoom"></div> -->
<!-- 				        </div> -->
				        <div class="content">
				        	<div id="runOffNumDiv">
								<span id="runOffNum"></span>
							</div>
	               		    <div class="tablePanel" id="runOffDetailPanel">  
	               		      <table id="runOffDetail"></table>   
				        	</div>	
				        </div>	
			        </div>
               		 
                </div>
               
          </div>	
	</div>
</div>
<script src="${ctx}/assets/js/require.js"></script>
<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/mobile/js/biz/drivingforce/accordDismiss.js?v=${v.version}"></script>
</body>
</html>
