<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
     <%@include file="../include/top.jsp" %>
    <title>团队画像</title>
     
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/tab.css?v=${v.version}"/>
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/biz/teamImg/teamImg.css?v=${v.version}"/>
</head>
<body>
     <input type="hidden" id="reqOrganId" value="${reqOrganId}">
<div class="page-content ovfHiden">
    <div class="main-container-inner">
            <div class="page-top">
                <div class="content">
                	<div class="teamTotal">团队总人数：<span>0</span>人</div>
                	<div class="teamCardPanel" >
                		<div class="teamCard" >
	                 		<div class="value">
		                		<span>0</span>
		                		<label>%</label>
		                	</div>
	                 		 <label  class="label"></label>
	                	</div>
						<div class="teamCardSplit" >
	                 		
	                	</div>
		                <div class="teamCard">
		                	<div class="value">
		                		<span>0</span>
		                		<label>%</label>
		                	</div>
		                 	 <label  class="label"></label>
		                </div>
		                <div class="teamCardSplit" >
	                 		
	                	</div>
	                	  <div class="teamCard">
		                 	 <div class="value">
		                		<span>0</span>
		                		<label>%</label>
		                	</div>
		                 	 <label  class="label"></label>
		                </div>
                	</div>
                </div>
          </div>
          
          <div class="fixed">
	  		  <div class="fixed-tab">
	                <div class="col-xs-12 tabPanel" >
	               		<div class="col-xs-3 tab1-active active" aria-controls="constellatoryAndBloodPanel" data-toggle="tab"><div class="icon"></div>星座与血型</div>
	               		<div class="col-xs-3 tab2" aria-controls="sexAndMarryPanel" data-toggle="tab"><div class="icon"></div>性别与婚姻</div>
	               		<div class="col-xs-3 tab3" aria-controls="agePanel" data-toggle="tab"><div class="icon"></div>年龄分布</div>
	               		<div class="col-xs-3 tab5" aria-controls="personalityPanel" data-toggle="tab"><div class="icon"></div>性格分析</div>
	                </div>
	          </div>
          </div>
		  <div class="tab-content ct-row">
		  		 <div class="col-xs-12 tab-panel active" id="constellatoryAndBloodPanel">
			       	<div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">星座分布</div>
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
				        </div>
				        <div class="content">
				        	<div class="chartPanel">
	               		   		 <div class="chart" id="constellatory"></div>
	               		    </div>
				        </div>	
			        </div>
			        <div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">血型分布</div>
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
				        </div>
				        <div class="content">
				        	<div class="chartPanel">
	               		   		 <div class="chart" id="blood"></div>
	               		    </div>
				        </div>	
			        </div>
                </div>
                <div class="col-xs-12 tab-panel" id="sexAndMarryPanel">
		        	<div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">性别比例</div>
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
				        </div>
				        <div class="content">
				        	<div class="chartPanel">
				        	 <img src="${ctx}/assets/img/manage/teamImg-sex.png" class="teamImg-sex" />
	               		   		 <div class="chart" id="sex">
	               		   		 
	               		   		 </div>
<!-- 	               		   		 <div class="lidyZone"> -->
<!--                                         <div>女</div> -->
<!--                                         <div><span class="lidyVal"></span><span>人</span></div> -->
<!--                                     </div> -->
<!--                                     <div class="manZone"> -->
<!--                                         <div>男</div> -->
<!--                                         <div><span class="manVal"></span><span>人</span></div> -->
<!--                                     </div> -->
	               		    </div>
				        </div>	
			        </div>
			        <div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">婚姻状况</div>
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
				        </div>
				        <div class="content">
				        	<div class="chartPanel">
	               		   		 <div class="chart" id="marry"></div>
	               		    </div>
				        </div>	
			        </div>
                </div>
                <div class="col-xs-12 tab-panel " id="agePanel">
                	<div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">年龄分布</div>
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
				        </div>
				        <div class="content">
				        	<div class="chartPanel">
				        	 	<div class="chart" id="age"></div>
	               		    </div>
				        </div>	
			        </div>
			     </div>	
                 <div class="col-xs-12 tab-panel" id="personalityPanel">
                 	<div class="panel">
			            <div class="tab-panel-title">
			            	<div class="tab-panel-title-left"></div>
			            	<div class="tab-panel-title-text">性格分布</div>
<!-- 			            	<div class="tab-panel-title-zoom"></div> -->
				        </div>
				        <div class="content">
				        	<div class="chartPanel">
				        	 	<div class="chart" id="personality"></div>
	               		    </div>
				        </div>	
			        </div>
                </div>
               
          </div>	
	</div>
</div>


<script src="${ctx}/assets/js/require.js"></script>
<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/mobile/js/biz/teamImg/teamImg.js?v=${v.version}"></script>
</body>
</html>