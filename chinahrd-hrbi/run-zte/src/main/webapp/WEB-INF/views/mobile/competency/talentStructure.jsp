<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="../include/top.jsp" %>
    <title>人力结构</title>
        <link rel="stylesheet" href="${ctx}/assets/mobile/css/tab.css?v=${v.version}"/>
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/biz/competency/talentStructure.css?v=${v.version}"/>
</head>
<body>
<input type="hidden" id="reqOrganId" value="${reqOrganId}"/>

<div class="page-content ovfHiden">
    <div class="main-container-inner">
            <div class="row ct-row structure">
                <div class="col-xs-12 ct-11 ct-line-col structure-top">
                	<div align="center">
                          <div id="LSLChart" class="structure-chart" ></div>
                          <div id="LSLNormal" class="structure-normal">
                          	<div class="structure-note col-xs-12 ct-12" >
                          	 	编制使用统计（${time }）
                          	</div>
                         
			                	<div class="structure-value col-xs-3 ct-3" >
			                 		<span class="usableEmpCount">0</span>
<!-- 			                 		 <div><label>可用编制数</label></div> -->
											<div><label>可用</label></div>
			                	</div>
			                	 <div class="structure-operator">
			                 		<span>=</span>
			                	</div>
				                <div class="structure-value  col-xs-3 ct-3">
				                 	<span class="number">0</span>
				                 	 <div><label>编制数</label></div>
				                </div>
				                <div class="structure-operator">
				                	 <span>-</span>
				                </div>
				                <div class="structure-value  col-xs-3 ct-3">
				                 	 <span class="empCount">0</span>
				                 	 <div><label>在岗人数</label></div>
				                </div>
                          </div>
                     </div>
                </div>
          </div>
          
          <div class="fixed">
	  		  <div class="row structure fixed-tab" id="tabPanel">
	                <div class="col-xs-12 tabPanel" >
	               		<div class="col-xs-3" align="center"><div class="tab1-active" aria-controls="glzblPanel" data-toggle="tab"><div class="icon"></div>管理者比例</div></div>
	               		<div class="col-xs-3" align="center"><div class="tab2" aria-controls="xltjPanel" data-toggle="tab"><div class="icon"></div>序列统计</div></div>
	               		<div class="col-xs-3" align="center"><div class="tab3" aria-controls="zzfbPanel" data-toggle="tab"><div class="icon"></div>组织分布</div></div>
	               		<div class="col-xs-3" align="center"><div class="tab5" aria-controls="tzfxPanel" data-toggle="tab"><div class="icon"></div>特征分析</div></div>
	                </div>
	          </div>
          </div>
		  <div class="row structure tab-content ct-row">
                <div class="col-xs-12 tab-panel active" id="glzblPanel">
			        <div class="content" align="center">
			       		<div class="title"></div>
               		    <div class="managerAndEmployee">
               		    	<div class="manager-normal">
               		    	   <div class="manager"></div>
               		    	   <label>管理者</label>
               		    	   <label><span>0</span>人</label>
               		    	</div>
               		    	 <div class="line"></div>
               		        <div class="arrow"></div>
               		        <div class="employee-normal">
               		    	   <div class="employee"></div>
               		    	    <label>员工 </label>
               		    	   <label><span>0</span>人</label>
               		    	</div>
               		       
               		    </div>
			        </div>	
                </div>
                <div class="col-xs-12 tab-panel" id="xltjPanel">
			       <div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">职位序列分布</div>
				        </div>
				        <div class="content">
					         <div class="chartPanel">
		               		    <div class="chart" id="positionSequenceChart"></div>
		               		 </div>
				        </div>	
			        </div>
			        <div class="panel">
			        	<div class="tab-panel-title">
			        		<div class="tab-panel-title-left"></div>
			        		<div class="tab-panel-title-text">职级分布</div>
				        </div>
				        <div class="content">
				        	<div class="btn-group" id="allSeq">
					        	
				        	</div>
				        	 <div class="chartPanel">
	               		   		 <div class="chart" id="positionRankChart"></div>
	               		    </div>
				        </div>	
			        </div>
                </div>
                 <div class="col-xs-12 tab-panel" id="zzfbPanel">
                 	<div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">架构分布</div>
				        </div>
				        <div class="content">
					         <div class="chartPanel">
		               		    <div class="chart" id="organDistributionChart"></div>
					       	 </div>
				        </div>	
			        </div>

			        <div class="panel">
			        	<div class="tab-panel-title">
				            <div class="tab-panel-title-left"></div>
				            <div class="tab-panel-title-text">工作地分布</div>
				        </div>
				        <div class="content">
				        	 <div class="chartPanel">
	               		  		  <div class="chart" id="workLocationChart"></div>
	               		  	 </div>
				        </div>	
			        </div>
                </div>
                 <div class="col-xs-12 tab-panel" id="tzfxPanel">
			        <div class="panel">
			        	<div class="tab-panel-title">
				            <div class="tab-panel-title-left"></div>
				            <div class="tab-panel-title-text">司龄分布</div>
				        </div>
				        <div class="content">
				        	 <div class="chartPanel">
	               		    	<div class="chart" id="seniorityChart"></div>
	               		     </div>
				        </div>	
			        </div>
			        
			        <div class="panel">
			        	<div class="tab-panel-title">
				            <div class="tab-panel-title-left"></div>
				            <div class="tab-panel-title-text">学历分布</div>
				        </div>
				        <div class="content">
				         	<div class="chartPanel">
	               		    	<div class="chart" id="degreeChart"></div>
	               		    </div>
				        </div>	
			        </div>
                </div>
               
          </div>	
	</div>
</div>
<div class="shade"></div>
<script type="text/javascript" src="${ctx}/assets/js/require.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/lib/plugins/echarts/echarts.js"></script>
<script type="text/javascript" src="${ctx}/assets/mobile/js/biz/competency/talentStructure.js?v=${v.version}"></script>
</body>
</html>
