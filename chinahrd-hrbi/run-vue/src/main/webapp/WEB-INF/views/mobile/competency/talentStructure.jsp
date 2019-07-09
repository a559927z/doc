<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="../include/top.jsp" %>
    <title>人员结构</title>
        <link rel="stylesheet" href="${ctx}/assets/mobile/css/tab.css?v=${v.version}"/>
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/biz/competency/talentStructure.css?v=${v.version}"/>
</head>
<body>
<input type="hidden" id="reqOrganId" value="${reqOrganId}"/>
<input type="hidden" id="time" value="${time}"/>
<div class="page-content ovfHiden">
    <div class="main-container-inner">
    		<div class="page-top">
                <div class="content">
                	<div align="center" class="top-content-note" >
 						<div class="icon"></div>
 						<div>
 						编制使用统计<span class="time">(${time })</span>	
 						</div>
 						</div>
                	<div align="center" class="top-content-panel">
                		<div class="top-panel" >
	                 		 <div><label>可用</label></div>
	                 		 <span class="usableEmpCount value">0</span>
	                	</div>
	                	 <div class="top-operator">
	                 		<span>=</span>
	                	</div>
		                <div class="top-panel">
		                 	 <div><label>编制数</label></div>
		                 	 <span class="number">0</span>
		                </div>
		                <div class="top-operator">
		                	 <span>-</span>
		                </div>
		                <div class="top-panel ">
		                 	 <div><label>在岗人数</label></div>
		                 	  <span class="empCount">0</span>
		                </div>
                	</div>
                </div>
          </div>
          <div class="fixed">
	  		  <div class="fixed-tab">
	                <div class="col-xs-12 tabPanel" >
	               		<div class="col-xs-3 tab9-active active" aria-controls="glzblPanel" data-toggle="tab"><div class="icon"></div>管理者比例</div>
	               		<div class="col-xs-3 tab10" aria-controls="xltjPanel" data-toggle="tab"><div class="icon"></div>序列统计</div>
	               		<div class="col-xs-3 tab5" aria-controls="zzfbPanel" data-toggle="tab"><div class="icon"></div>组织分布</div>
	               		<div class="col-xs-3 tab11" aria-controls="tzfxPanel" data-toggle="tab"><div class="icon"></div>特征分析</div>
	                </div>
	          </div>
          </div>
		  <div class="tab-content ct-row">
                <div class="col-xs-12 tab-panel active" id="glzblPanel">
			        <div class="content" align="center">
			       		<div class="title"></div>
               		    <div class="managerAndEmployee">
               		    	<div class="backgroud">
               		    	<div class="manager-normal">
               		    	    <div class="managerBackgroud"></div>
               		    	   <label>管理者</label>
               		    	   <label><span>0</span>人</label>
               		    	</div>
               		    	 <div class="employee-normal">
               		    	    <div class="employeeBackgroud"></div>
               		    	    <label>员工 </label>
               		    	   <label><span>0</span>人</label>
               		    	</div>
               		    	
               		    	</div>
               		    	<div class="line"></div>
               		    	<div class="triangle"></div>
               		    </div>
			        </div>	
                </div>
                <div class="col-xs-12 tab-panel" id="xltjPanel">
			       <div class="panel">
						<div class="content">
		                 	<div class="tabPanel-interior">
			               		<div class="tab-interior active" aria-controls="xlfb" data-toggle="tab">序列分布</div>
			               		<div class="tab-interior" aria-controls="zjfb" data-toggle="tab">职级分布</div>
			               	</div>
		               		<div class="tab-interior-content ct-row">
	               		 		<div class="tab-interior-panel active" id="xlfb">
	               		 			<div class="panel">
		               		 			<div class="content">
					        				<div class="title"></div>
					        				<div class="chartPanel">
		               		   		 			<div class="chart" id="positionSequenceChart"></div>
	               		    				</div>
				        				</div>	
	               		 			</div>
	               		 		</div>
	               		 		<div class="tab-interior-panel" id="zjfb">
	               		 			<div class="panel">
				        				<div class="content">
				        					<div class="btn-group" id="allSeq">
					        	
				        					</div>
				        	 				<div class="chartPanel">
	               		   		 				<div class="chart" id="positionRankChart"></div>
	               		   					</div>
				        				</div>	
	               		 			</div>
	               		 		</div>
		               		</div>
	               		 </div>	
			        </div>
                </div>
                 <div class="col-xs-12 tab-panel" id="zzfbPanel">
                 	<div class="panel">
				        <div class="content">
		                 	<div class="tabPanel-interior">
			               		<div class="tab-interior active" aria-controls="jgfb" data-toggle="tab">架构分布</div>
			               		<div class="tab-interior" aria-controls="gzdfb" data-toggle="tab">工作地分布</div>
			               	</div>
		               		<div class="tab-interior-content ct-row">
	               		 		<div class="tab-interior-panel active" id="jgfb">
	               		 			<div class="panel">
		               		 			<div class="content">
					        				<div class="title"></div>
					        				<div class="chartPanel">
		               		   		 			<div class="chart" id="organDistributionChart"></div>
	               		    				</div>
				        				</div>	
	               		 			</div>
	               		 		</div>
	               		 		<div class="tab-interior-panel" id="gzdfb">
	               		 			<div class="panel">
				        				<div class="content">
				        					<div class="chartPanel">
	               		  					  <div class="chart" id="workLocationChart"></div>
	               		  				 	</div>
				        				</div>	
	               		 			</div>
	               		 		</div>
		               		</div>
	               		 </div>	
			        </div>
                </div>
                 <div class="col-xs-12 tab-panel" id="tzfxPanel">
			        <div class="panel">
				        <div class="content">
		                 	<div class="tabPanel-interior">
			               		<div class="tab-interior active" aria-controls="slfb" data-toggle="tab">司龄分布</div>
			               		<div class="tab-interior" aria-controls="xuelilfb" data-toggle="tab">学历分布</div>
			               	</div>
		               		<div class="tab-interior-content ct-row">
	               		 		<div class="tab-interior-panel active" id="slfb">
	               		 			<div class="panel">
		               		 			<div class="content">
					        				<div class="title"></div>
					        				<div class="chartPanel">
		               		   		 			<div class="chart" id="seniorityChart"></div>
	               		    				</div>
				        				</div>	
	               		 			</div>
	               		 		</div>
	               		 		<div class="tab-interior-panel" id="xuelilfb">
	               		 			<div class="panel">
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
                </div>
               
          </div>	
	</div>
</div>
<div class="shade"></div>
<script src="${ctx}/assets/js/require.js"></script>
<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/mobile/js/biz/competency/talentStructure.js?v=${v.version}"></script>
</body>
</html>
