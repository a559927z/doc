<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
     <%@include file="../include/top.jsp" %>
    <title>劳动力效能</title>
     
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/tab.css?v=${v.version}"/>
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/biz/laborEfficiency/laborEfficiency.css?v=${v.version}"/>
</head>
<body>
     <input type="hidden" id="reqOrganId" value="${reqOrganId}">
	<input type="hidden" id="timeRange" value="${timeRange }">
	<input type="hidden" id="timeVal" value="${timeVal }">
	<input type="hidden" id="time" value="${time }">
<div class="page-content ovfHiden">
    <div class="main-container-inner">
            <div class="page-top">
                <div class="content">
                	<div class="teamTotal ">统计时间：<span>${timeVal }</span></div>
                	<div class="teamCardPanel margin30" >
                		<div class="teamCard" >
                		    <label  class="label">劳动力效能</label>
	                 		<div class="value">
		                		<span>0</span>
		                		<label>%</label>
		                	</div>
	                 		
	                	</div>
						<div class="teamCardSplit" >
	                 		
	                	</div>
		                <div class="teamCard">
		                	<label  class="label">总加班时长</label>
		                	<div class="value">
		                		<span>0</span>
		                		<label>小时</label>
		                	</div>
		                 	 
		                </div>
		                <div class="teamCardSplit" >
	                 		
	                	</div>
	                	  <div class="teamCard">
	                	  <label  class="label">加班预警</label>
		                 	 <div class="value">
		                		<span>0</span>
		                		<label>人</label>
		                	</div>
		                 	 
		                </div>
                	</div>
                </div>
          </div>
          
          <div class="fixed">
	  		  <div class="fixed-tab">
	                <div class="col-xs-12 tabPanel" >
	               		<div class="col-xs-3 tab1-active active" aria-controls="laborEfficiencyPanel" data-toggle="tab"><div class="icon"></div>劳动力效能</div>
	               		<div class="col-xs-3 tab2" aria-controls="overtimePanel" data-toggle="tab"><div class="icon"></div>加班时长</div>
	               		<div class="col-xs-3 tab3" aria-controls="attendanceTimePanel" data-toggle="tab"><div class="icon"></div>考勤类型</div>
	               		<div class="col-xs-3 tab5" aria-controls="attendanceDetailPanel" data-toggle="tab"><div class="icon"></div>出勤明细</div>
	                </div>
	          </div>
          </div>
		  <div class="tab-content ct-row">
		  		 <div class="col-xs-12 tab-panel active" id="laborEfficiencyPanel">
			       	<div class="panel">
			       		<div class="tabPanel-interior" >
		               		<div class="tab-interior active" aria-controls="zjtj" data-toggle="tab">下级组织对比</div>
		               		<div class="tab-interior" aria-controls="jxtj" data-toggle="tab">劳动力效能趋势</div>
	               		</div>
	               		<div class="tab-interior-content ct-row">
	               		 		<div class="tab-interior-panel active" id="zjtj">
	               		 			<div class="panel">
		               		 			<div class="content">
					        				<div class="title">统计周期：${timeRange}</div>
					        				<div class="chartPanel">
		               		   		 			 <div class="chart" id="laborRatioChart"></div>
	               		    					 <div class="note"></div>
	               		    				</div>
				        				</div>	
	               		 			</div>
	               		 		</div>
	               		 		<div class="tab-interior-panel" id="jxtj">
	               		 			<div class="panel">
				        				<div class="content">
								        	<div class="chartPanel">
					               		    	<div class="chart" id="laborEfficiencyRatio"></div>
					               		    </div>
								        </div>	
	               		 			</div>
	               		 		</div>
	               		</div>
			        </div>
                </div>
                <div class="col-xs-12 tab-panel" id="overtimePanel">
                	<div class="tabPanel-interior" >
		               		<div class="tab-interior active" aria-controls="overtimeChartPanel" data-toggle="tab">下级组织加班时长对比</div>
		               		<div class="tab-interior" aria-controls="overtimeTrendChartPanel" data-toggle="tab">加班时长趋势</div>
	               		</div>
	               		<div class="tab-interior-content ct-row">
               		 		<div class="tab-interior-panel active" id="overtimeChartPanel">
               		 			<div class="panel">
               		 				 <div class="content">
							        	 <div class="btn-group" id="overtimeBtn" onselectstart="return false">	
										        	<span class="button condition" data="0">&nbsp;&nbsp;&nbsp;人均时长&nbsp;&nbsp;&nbsp;</span>
										        	<span class="button" data="1">&nbsp;&nbsp;&nbsp;&nbsp;总时长&nbsp;&nbsp;&nbsp;&nbsp;</span>
										</div>
											<div class="title">统计周期：${timeRange}</div>
							        	<div class="chartPanel">
				               		   		 <div class="chart" id="overtimeChart"></div>
				               		         <div class="note"></div>
				               		    </div>
							        </div>	
               		 			</div>
               		 		</div>
               		 		<div class="tab-interior-panel" id="overtimeTrendChartPanel">
               		 			<div class="panel">
			        				<div class="content">
							        	<div class="btn-group" id="overtimeTrendBtn" onselectstart="return false">	
						        			<span class="button condition" data="0">&nbsp;&nbsp;&nbsp;人均时长&nbsp;&nbsp;&nbsp;</span>
						        			<span class="button" data="1">&nbsp;&nbsp;&nbsp;&nbsp;总时长&nbsp;&nbsp;&nbsp;&nbsp;</span>
										</div>
							        	<div class="chartPanel">
				               		   		 <div class="chart" id="overtimeTrendChart"></div>
				               		    </div>
							        </div>	
               		 			</div>
               		 		</div>
			        </div>
                </div>
                <div class="col-xs-12 tab-panel " id="attendanceTimePanel">
                	<div class="panel">
				        <div class="content">
				        	<div class="chartPanel">
				        	 	<div class="chart" id="checkWorkTypeChart"></div>
	               		    </div>
				        </div>	
			        </div>
			     </div>	
                 <div class="col-xs-12 tab-panel" id="attendanceDetailPanel">
                 	<div class="panel">
<!-- 			            <div class="tab-panel-title"> -->
<!-- 			            	<div class="tab-panel-title-left"></div> -->
<!-- 			            	<div class="tab-panel-title-text">出勤明细</div> -->
<!-- 		            	<div class="tab-panel-title-zoom"></div> -->
<!-- 				        </div> -->
				       <div class="content">
				       		<div id="gridNode">
								<span id="text">说明：统计时间为${timeVal }，统计单位均为小时</span>
							</div>
	               		    <div class="tablePanel" id="workDetailPanel">  
	               		      <table id="workDetail"></table>   
				        	</div>	
				        </div>	
			        </div>
                </div>
               
          </div>	
	</div>
</div>


<script src="${ctx}/assets/js/require.js"></script>
<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/mobile/js/biz/laborEfficiency/laborEfficiency.js?v=${v.version}"></script>
</body>
</html>