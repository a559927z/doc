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
            <div class="row ct-row dismiss">
                <div class="col-xs-12 ct-11 ct-line-col">
                	<div align="center">
                          <div id="LSLDismissChart" class="accord-dismiss-chart"></div>
                          <div id="LSLDismissNormal" class="accord-dismiss-normal">
                              <div><span class="accord-main-number">0</span>%&nbsp;<span class="accord-main-date"></span></div>
                              <div class="accord-main-content"></div>
                              <div class="accord-main-note">
                              	<div class="accord-main-icon-note">说明：</div>
	                            <div class="accord-main-icon-value">
	                            	<div class="accord-main-icon-item">
	              					  <span class="accord-main-icon-yellow"></span>
	                                  <span class="accord-main-icon-note-warn"></span>             	
	                            	</div>
	                            	<div class="accord-main-icon-item">
									  <span class="accord-main-icon-red"></span>
	                                  <span class="accord-main-icon-note-over"></span>
	                              </div>
								</div>
	                             
                              </div>
                          </div>
                     </div>
                </div>
          </div>
          
          <div class="fixed">
	  		  <div class="row dismiss fixed-tab" id="tabPanel">
	                <div class="col-xs-12 tabPanel" >
	               		<div class="col-xs-3" align="center"><div class="tab1-active" aria-controls="ydqsPanel" data-toggle="tab"><div class="icon"></div>月度趋势</div></div>
	               		<div class="col-xs-3" align="center"><div class="tab2" aria-controls="xjdbPanel" data-toggle="tab"><div class="icon"></div>下级对比</div></div>
	               		<div class="col-xs-3" align="center"><div class="tab3" aria-controls="wdfxPanel" data-toggle="tab"><div class="icon"></div>维度分析</div></div>
	               		<div class="col-xs-3" align="center"><div class="tab4" aria-controls="xxmdPanel" data-toggle="tab"><div class="icon"></div>详细名单</div></div>
	                </div>
	          </div>
          </div>
		  <div class="row dismiss tab-content ct-row">
                <div class="col-xs-12 tab-panel active" id="ydqsPanel">
			        <div class="content">
			       		<div class="title">统计周期：<span id="ydqsTjzq">${timeRange}</span></div>
               		    <div class="chartPanel">
               		    	<div class="chart" id="ydqsChart"></div>
               		    </div>
               		   
			        </div>	
                </div>
                <div class="col-xs-12 tab-panel" id="xjdbPanel">
			        <div class="content">
			        	<div class="title">统计周期：${timeRange}</div>
			        	<div class="chartPanel">
               		    	<div class="chart" id="inferiorChart"></div>
               		    </div>
			        </div>	
                </div>
                 <div class="col-xs-12 tab-panel" id="wdfxPanel">
                 	<div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">按职级统计</div>
				        </div>
				        <div class="content">
				        	<div class="title">统计周期：${timeRange}</div>
				        	<div class="title">(目前只统计非管理人员)</div>
				        	<div class="chartPanel">
	               		   		 <div class="chart" id="abilityChart"></div>
	               		    </div>
				        </div>	
			        </div>
			        <div class="panel">
			        	<div class="tab-panel-title">
			        		<div class="tab-panel-title-left"></div>
			        		<div class="tab-panel-title-text">按绩效统计</div>
				        </div>
				        <div class="content">
				        	<div class="title">统计周期：${timeRange}</div>
				        	<div class="chartPanel">
	               		    	<div class="chart" id="perfChart"></div>
	               		    </div>
				        </div>	
			        </div>
			        <div class="panel">
			        	<div class="tab-panel-title">
				            <div class="tab-panel-title-left"></div>
				            <div class="tab-panel-title-text">按司龄统计</div>
				        </div>
				        <div class="content">
				        	<div class="title">统计周期：${timeRange}</div>
				        	<div class="chartPanel">
	               		   		<div class="chart" id="ageChart"></div>
	               		    </div>
				        </div>	
			        </div>
                </div>
                 <div class="col-xs-12 tab-panel" id="xxmdPanel">
               		 <div class="content">
<!-- 				        	<div class="runOffNum"></div> -->
							<div class="title runOffNum">
<!-- 							<span id="rymdTitle" class="tjzq" style="float: left;text-align: left;"></span> -->
								<span id="runOffNum"></span>
							</div>
	               		    <div class="row col-xs-12 runOffDetail" id="runOffDetailPanel">  
	               		      <table id="runOffDetail"></table>   
				        	</div>	
                	</div>
                </div>
               
          </div>	
	</div>
</div>
<script type="text/javascript" src="${ctx}/assets/js/require.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/lib/plugins/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/assets/mobile/js/biz/drivingforce/accordDismiss.js?v=${v.version}"></script>
</body>
</html>
