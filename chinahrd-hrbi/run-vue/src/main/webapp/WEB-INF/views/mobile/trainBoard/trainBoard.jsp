<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
     <%@include file="../include/top.jsp" %>
    <title>培训看板</title>
     
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/tab.css?v=${v.version}"/>
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/biz/trainBoard/trainBoard.css?v=${v.version}"/>
</head>
<body>
     <input type="hidden" id="reqOrganId" value="${reqOrganId}">
<div class="page-content ovfHiden">
    <div class="main-container-inner">
            <div class="page-top">
                <div class="content">
                	<div class="trainCardPanel" >
                		<div class="trainCard" >
	                 		<label>培训费用执行率</label>
	                 		 <span>0%</span>
	                	</div>
						<div class="trainCardSplit" >
	                 		
	                	</div>
		                <div class="trainCard">
		                	<label>培训计划完成率</label>
		                 	 <span>0%</span>
		                </div>
		                
                	</div>
                </div>
          </div>
          
          <div class="fixed">
	  		  <div class="fixed-tab">
	                <div class="col-xs-12 tabPanel" >
	               		<div class="col-xs-3 tab1-active active" aria-controls="trainCostPanel" data-toggle="tab"><div class="icon"></div>培训费用</div>
	               		<div class="col-xs-3 tab2" aria-controls="trainImplementationPanel" data-toggle="tab"><div class="icon"></div>培训实施</div>
	               		<div class="col-xs-3 tab3" aria-controls="trainEffectPanel" data-toggle="tab"><div class="icon"></div>培训效果</div>
	               		<div class="col-xs-3 tab5" aria-controls="trainRecordPanel" data-toggle="tab"><div class="icon"></div>培训记录</div>
	                </div>
	          </div>
          </div>
		  <div class="tab-content ct-row">
		  		 <div class="col-xs-12 tab-panel active" id="trainCostPanel">
			       	<div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">培训费用统计</div>
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
				        </div>
				        <div class="content">
				        	<div class="chartPanel">
	               		   		 <div class="chart" id="trainCostChart"></div>
	               		    </div>
				        </div>	
			        </div>
			        <div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">人均培训费用统计</div>
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
				        </div>
				        <div class="content">
				        	<div class="chartPanel">
	               		   		 <div class="chart" id="avgpriceCostChart"></div>
	               		    </div>
				        </div>	
			        </div>
			        <div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">培训费用年度趋势</div>
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
				        </div>
				        <div class="content">
				        	<div class="chartPanel">
	               		   		 <div class="chart" id="yearTrendChart"></div>
	               		    </div>
				        </div>	
			        </div>
			        <div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">下级组织培训费用对比</div>
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
				        </div>
				        <div class="content">
				        	<div class="chartPanel">
	               		   		 <div class="chart" id="subOrganContrastChart"></div>
	               		     		<div class="note"></div>
	               		    </div>
				        </div>	
			        </div>
			        
			        
                </div>
                <div class="col-xs-12 tab-panel" id="trainImplementationPanel">
		        	<div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">培训计划完成率</div>
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
				        </div>
				        <div class="content">
				        	<div class="chartPanel">

	               		   		 <div class="chart" id="completionRateChart">
	               		   		 
	               		   		 </div>
	               		    </div>
				        </div>	
			        </div>
			        <div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">下级组织培训覆盖率对比</div>
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
				        </div>
				        <div class="content">
				        	<div class="chartPanel">
	               		   		 <div class="chart" id="coverageContrastChart"></div> 		
	               		   <div class="note"></div>
	               		    </div>
	               		    
				        </div>	
			        </div>
			        <div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">培训类型统计</div>
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
				        </div>
				        <div class="content">
				        	<div class="chartPanel">
	               		   		 <div class="chart" id="trainTypeChart"></div>
	               		    </div>
				        </div>	
			        </div>
			        <div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">培训讲师统计</div>
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
				        </div>
				        <div class="content">
				        	<div class="chartPanel">
	               		   		 <div class="chart" id="teacherChart"></div>
	               		    </div>
				        </div>	
			        </div>
			        
                </div>
                <div class="col-xs-12 tab-panel " id="trainEffectPanel">
                	<div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">下级组织人均学时分析</div>
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
				        </div>
				        <div class="content">
				        	<div class="chartPanel">
				        	 	<div class="chart" id="subOrganSchoolChart"></div>
	               		    </div>
				        </div>	
			        </div>
			        <div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">下级组织培训人次对比</div>
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
				        </div>
				        <div class="content">
				        	<div class="chartPanel">
				        	 	<div class="chart" id="peopleCompareChart"></div>
	               		    </div>
				        </div>	
			        </div>
			        <div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">培训满意度统计</div>
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
				        </div>
				        <div class="content">
				        	<div class="chartPanel">
				        	 	<div class="chart" id="satisfactionChart"></div>
	               		    </div>
				        </div>	
			        </div>
			     </div>	
                 <div class="col-xs-12 tab-panel" id="trainRecordPanel">
                 	<div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">培训记录查询</div>
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
				        </div>
				        <div class="content">
				        	<div class="tablePanel" id="trainDetailGridPanel">  
	               		      <table id="trainDetailGrid" class="trainTable" ></table>   
				        	</div>	
				        </div>	
			        </div>
                </div>
          </div>	
	</div>
</div>

<script src="${ctx}/assets/js/require.js"></script>
<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/mobile/js/biz/trainBoard/trainBoard.js?v=${v.version}"></script>
</body>
</html>