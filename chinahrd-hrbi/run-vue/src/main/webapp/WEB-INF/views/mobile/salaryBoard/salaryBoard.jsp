<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
     <%@include file="../include/top.jsp" %>
    <title>薪酬看板</title>
     
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/tab.css?v=${v.version}"/>
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/biz/salaryBoard/salaryBoard.css?v=${v.version}"/>
</head>
<body>
     <input type="hidden" id="reqOrganId" value="${reqOrganId }">
<div class="page-content ovfHiden">
    <div class="main-container-inner">
            <div class="page-top">
                <div class="content">
                	<div class="salaryCardPanel margin30" >
                		<div class="salaryCard" >
	                 		<label>薪酬占人力成本比</label>
	                 		 <span></span>
	                	</div>
						<div class="salaryCardSplit" >
	                 		
	                	</div>
		                <div class="salaryCard">
		                	<label>人力资本投资回报率</label>
		                 	 <span></span>
		                </div>
		                
                	</div>
                </div>
          </div>
           <div class="fixed">
	  		  <div class="fixed-tab">
	                <div class="col-xs-12 tabPanel" >
	               		<div class="col-xs-3 tab12-active active" aria-controls="xczePanel" data-toggle="tab"><div class="icon"></div>薪酬总额</div>
	               		<div class="col-xs-3 tab1" aria-controls="qcyxxPanel" data-toggle="tab"><div class="icon"></div>薪酬有效性</div>
	               		<div class="col-xs-3 tab2" aria-controls="qccyxPanel" data-toggle="tab"><div class="icon"></div>薪酬差异性</div>
	               		<div class="col-xs-3 tab13" aria-controls="nzjfxPanel" data-toggle="tab"><div class="icon"></div>年终奖分析</div>
	                </div>
	          </div>
          </div>
		  <div class="tab-content ct-row">
		  	   <div class="col-xs-12 tab-panel active" id="xczePanel">
			      <div class="panel">
                 		<div class="xyzt"></div>
                 		<div class="content">
		                 	<div class="tabPanel-interior">
			               		<div class="tab-interior active" aria-controls="rjxy" data-toggle="tab">薪酬执行</div>
			               		<div class="tab-interior" aria-controls="yylr" data-toggle="tab">薪酬对比</div>
			               	</div>
		               		<div class="tab-interior-content ct-row">
	               		 		<div class="tab-interior-panel active" id="rjxy">
	               		 			<div class="panel">
		               		 			<div class="content">
					        				<div class="chartPanel">
		               		   		 			<div class="chart" id="trainCostChart"></div>
	               		    				</div>
				        				</div>	
	               		 			</div>
	               		 		</div>
	               		 		<div class="tab-interior-panel" id="yylr">
	               		 			<div class="panel">
				        				<div class="content">
				        					<div class="btn-group" id="allSeq">
					        	          <span class="button condition" data="1">薪酬总额</span>
					        	          <span class="button" data="2">平均薪酬</span>
				        					</div>
				        	 				<div class="chartPanel">
	               		   		 				<div class="chart" id="avgpriceCostChart"></div>
	               		   					</div>
				        				</div>	
	               		 			</div>
	               		 		</div>
		               		</div>
	               		 </div>	
			        </div>
                </div>
                <div class="col-xs-12 tab-panel" id="qcyxxPanel">
			      <div class="panel">
                 		<div class="xyzt"></div>
                 		<div class="content">
		                 	<div class="tabPanel-interior">
			               		<div class="tab-interior active" aria-controls="hbl" data-toggle="tab">人力资本投资回报率</div>
			               		<div class="tab-interior" aria-controls="ndqs" data-toggle="tab">组织KPI达标率、人力成本、薪酬总额的年度趋势</div>
			               		<div class="tab-interior" aria-controls="ydqs" data-toggle="tab">营业额、利润、人力成本及薪酬总额的月度趋势</div>
			               	</div>
		               		<div class="tab-interior-content ct-row">
	               		 		<div class="tab-interior-panel active" id="hbl">
	               		 			<div class="panel">
		               		 			<div class="content">
					        				<div class="chartPanel">
		               		   		 			<div class="chart" id="huiBaoLvChart"></div>
	               		    				</div>
				        				</div>	
	               		 			</div>
	               		 		</div>
	               		 		<div class="tab-interior-panel" id="ndqs">
	               		 			<div class="panel">
				        				<div class="content">
					        				<div class="chartPanel">
		               		   		 			<div class="chart" id="nianDuQuShiChart"></div>
	               		    				</div>
				        				</div>	
	               		 			</div>
	               		 		</div>
	               		 		<div class="tab-interior-panel" id="ydqs">
	               		 			<div class="panel">
				        				<div class="content">
					        				<div class="chartPanel">
		               		   		 			<div class="chart" id="yueDuQuShiChart"></div>
	               		    				</div>
				        				</div>	
	               		 			</div>
	               		 		</div>
		               		</div>
	               		 </div>	
			        </div>
                </div>
                  <div class="col-xs-12 tab-panel" id="qccyxPanel">
			      <div class="panel">
                 		
                 		<div class="content">
		                 	<div class="tabPanel-interior">
			               		<div class="tab-interior active" aria-controls="hyndqs" data-toggle="tab">行业分位值年度趋势</div>
			               		<div class="tab-interior" aria-controls="ygcrz" data-toggle="tab">员工CR值</div>
			               		<div class="tab-interior" aria-controls="xccygwb" data-toggle="tab">薪酬差异度岗位表</div>
			               	</div>
		               		<div class="tab-interior-content ct-row">
	               		 		<div class="tab-interior-panel active" id="hyndqs">
	               		 			<div class="panel">
		               		 			<div class="content">
					        				<div class="chartPanel">
		               		   		 			<div class="chart" id="hangYeNianDuqsChart"></div>
	               		    				</div>
				        				</div>	
	               		 			</div>
	               		 		</div>
	               		 		<div class="tab-interior-panel" id="ygcrz">
	               		 			<div class="panel">
				        				<div class="content">
					        				<div class="chartPanel">
		               		   		 			<div class="chart" id="yuanGongCRChart"></div>
	               		    				</div>
				        				</div>	
							            	<div class="tab-panel-title-text">&nbsp;&nbsp;注：此图只显示最新一期绩效为五星的人员</div>
	               		 			</div>
	               		 		</div>
	               		 		
				              <div class="tab-interior-panel" id="xccygwb">
			                 	<div class="panel">
							        <div class="content">
							        	<div class="tablePanel" id="xinCChayGangwbGridPanel">  
				               		      <table id="xinCChayGangwbGrid" ></table>   
							        	</div>	
							        </div>	
						        </div>
			                </div>
		               		</div>
	               		 </div>	
			        </div>
                </div>
                 <div class="col-xs-12 tab-panel" id="nzjfxPanel">
                 	<div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">年终奖金总额与利润的年度趋势</div>
				        </div>
				            <div class="content">
				        	<div class="chartPanel">
	               		   		<div class="chart" id="manpowerCostTrendsChars"></div>
	               		    </div>
				        </div>	
			        </div>
                </div>
          </div>	
	</div>
</div>

<script src="${ctx}/assets/js/require.js"></script>
<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/mobile/js/biz/salaryBoard/salaryBoard.js?v=${v.version}"></script> 
</body>
</html>