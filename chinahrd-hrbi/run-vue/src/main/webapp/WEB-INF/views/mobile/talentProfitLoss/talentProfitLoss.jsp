<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
     <%@include file="../include/top.jsp" %>
    <title>人才损益</title>
         <link rel="stylesheet" href="${ctx}/assets/mobile/css/datetime.css?v=${v.version}"/>
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/tab.css?v=${v.version}"/>
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/biz/talentProfitLoss/talentProfitLoss.css?v=${v.version}"/>
</head>
<body>
     <input type="hidden" id="reqOrganId" value="${reqOrganId}">
	<input type="hidden" id="minTime" value="${minTime }">
	<input type="hidden" id="maxTime" value="${maxTime }">
	<input type="hidden" id="timeVal" value="${timeVal }">
	<input type="hidden" id="time" value="${time }">
<div class="page-content ovfHiden">
    <div class="main-container-inner">
            <div class="page-top">
                <div class="content ">
                	<div align="center" class="top-content-note" >
 						<span class="time">统计时间：<span id="time_val">${time }</span>
 						<div class="config_icon"></div>
 					</div>
                	<div align="center" class="top-content-panel">
                		<div class="top-panel" >
	                 		 <div><label>人才损益值</label></div>
	                 		 <span class="value">0</span>
	                	</div>
	                	 <div class="top-operator">
	                 		<span>=</span>
	                	</div>
		                <div class="top-panel">
		                 	 <div><label>流入人数（人）</label></div>
		                 	 <span>0</span>
		                </div>
		                <div class="top-operator">
		                	 <span>-</span>
		                </div>
		                <div class="top-panel ">
		                 	 <div><label>流出人数（人）</label></div>
		                 	  <span class="eqEmpNum">0</span>
		                </div>
                	</div>
                </div>
          </div>
          
          <div class="fixed">
	  		  <div class="fixed-tab">
	                <div class="col-xs-12 tabPanel" >
	               		<div class="col-xs-3 tab1-active active" aria-controls="laborEfficiencyPanel" data-toggle="tab"><div class="icon"></div>人员分布</div>
	               		<div class="col-xs-3 tab2" aria-controls="overtimePanel" data-toggle="tab"><div class="icon"></div>损益趋势</div>
	               		<div class="col-xs-3 tab3" aria-controls="attendanceTimePanel" data-toggle="tab"><div class="icon"></div>异动分析</div>
	               		<div class="col-xs-3 tab5" aria-controls="attendanceDetailPanel" data-toggle="tab"><div class="icon"></div>异动明细</div>
	                </div>
	          </div>
          </div>
		  <div class="tab-content ct-row">
		  		 <div class="col-xs-12 tab-panel active" id="laborEfficiencyPanel">
			       	<div class="panel">
			       		<div class="content">
	        				<div class="chartPanel">
    		   		 			 <div class="chart" id="populationChart"></div>
   		    					 <div class="note"></div>
   		    				</div>
        				</div>	
			        </div>
                </div>
                <div class="col-xs-12 tab-panel" id="overtimePanel">
	                <div class="panel">
	                	<div class="content">
				        	<div class="btn-group" id="profiLossTrendBtn" onselectstart="return false">	
					        	<span class="button condition" data="0">&nbsp;&nbsp;&nbsp;环比&nbsp;&nbsp;&nbsp;</span>
					        	<span class="button" data="1">&nbsp;&nbsp;&nbsp;&nbsp;同比&nbsp;&nbsp;&nbsp;&nbsp;</span>
							</div>
				        	<div class="chartPanel">
	              		   		 <div class="chart" id="profiLossTrendChart"></div>
	              		         <div class="note"></div>
		              		</div>
				        </div>
				    </div>
                </div>
                <div class="col-xs-12 tab-panel " id="attendanceTimePanel">
                	<div class="panel">
				        <div class="content">
				        	<div class="chartPanel">
				        	 	<div class="chart" id="changeAnalyzeChart"></div>
	               		    </div>
				        </div>	
			        </div>
			     </div>	
                 <div class="col-xs-12 tab-panel" id="attendanceDetailPanel">
                 	<div class="panel">
				       <div class="content">
				       		<div class="tabPanel-interior" >
			               		<div class="tab-interior active" aria-controls="inputPanel" data-toggle="tab">流入明细</div>
			               		<div class="tab-interior" aria-controls="outputPanel" data-toggle="tab">流出明细</div>
		               		</div>
		               		<div class="tab-interior-content ct-row">
	               		 		<div class="tab-interior-panel active" id="inputPanel">
	               		 			<div class="panel">
	               		 				 <div class="content">
								        	 <div class="btn-group" id="inputBtn" onselectstart="return false">	
											        	<span class="button condition" data="">&nbsp;全部&nbsp;</span>
											</div>
								        	  <div class="tablePanel" id="inputDetailPanel">  
	               		      					<table id="inputDetail"></table>   
				        					</div>	
								        </div>	
	               		 			</div>
	               		 		</div>
	               		 		<div class="tab-interior-panel" id="outputPanel">
	               		 			<div class="panel">
				        				<div class="content">
								        	<div class="btn-group" id="outputBtn" onselectstart="return false">	
							        			<span class="button condition" data="">&nbsp;全部&nbsp;</span>
											</div>
								        	<div class="tablePanel" id="outputDetailPanel">
					               		   		 <table id="outputDetail"></table>
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

  <div class="datetime_panel ">
       	<div class="ui_datetime_btn">完成</div>
       	<div class="ui-datetime" id="datetime2"></div>
  </div>

<div class="datetime_shade "></div>
<script src="${ctx}/assets/js/require.js"></script>
<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/mobile/js/biz/talentProfitLoss/talentProfitLoss.js?v=${v.version}"></script>
</body>
</html>