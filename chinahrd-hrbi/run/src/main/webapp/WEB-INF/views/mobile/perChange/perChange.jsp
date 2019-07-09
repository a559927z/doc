<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
     <%@include file="../include/top.jsp" %>
    <title>员工绩效</title>
     
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/tab.css?v=${v.version}"/>
    <link rel="stylesheet" href="${ctx}/assets/mobile/css/biz/perChange/perChange.css?v=${v.version}"/>
</head>
<body>
    <input type="hidden" id="yearMonth" value="${yearMonth}">
     <input type="hidden" id="lastYearMonth" value="${lastYearMonth}">
      <input type="hidden" id="beforeLastYearMonth" value="${beforeLastYearMonth}">

     <input type="hidden" id="reqOrganId" value="${reqOrganId}">
<div class="page-content ovfHiden">
    <div class="main-container-inner">
            <div class="page-top">
                <div class="content">
                	<div class="changeTime">${currTime}</div>
                	<div class="changeCardPanel" >
                		<div class="changeCard" >
	                 		<div  class="title"><span>当期高绩效</span><img src="${ctx }/assets/mobile/img/highChange.png"/></div>
	                 		 <div><label  class="changeHight">0</label>
	                 		 <label class="text-high text">人</label></div>
	                	</div>
						<div class="changeCardSplit" >
	                 		
	                	</div>
		                <div class="changeCard">
		                 	<span class="title">当期低绩效<img src="${ctx }/assets/mobile/img/lowChange.png"/></span>
		                 	 <div><label  class="changeLow">0</label>
								<label class="text-low text">人</label></div>
		                </div>
                	</div>
                </div>
          </div>
          
          <div class="fixed">
	  		  <div class="fixed-tab">
	                <div class="col-xs-12 tabPanel" >
	               		<div class="col-xs-3 tab5-active active" aria-controls="jxfbPanel" data-toggle="tab"><div class="icon"></div>绩效分布</div>
	               		<div class="col-xs-3 tab6" aria-controls="jxbhPanel" data-toggle="tab"><div class="icon"></div>绩效变化</div>
	               		<div class="col-xs-3 tab7" aria-controls="jxycPanel" data-toggle="tab"><div class="icon"></div>绩效异常</div>
	               		<div class="col-xs-3 tab8" aria-controls="jxmxPanel" data-toggle="tab"><div class="icon"></div>绩效明细</div>
	                </div>
	          </div>
          </div>
		  <div class="perBenefit tab-content ct-row">
		  		 <div class="col-xs-12 tab-panel active" id="jxfbPanel">
			        <div class="content">
			        	<div class="panel">
<!-- 				        	<div class="tab-panel-title"> -->
<!-- 					            <div class="tab-panel-title-left"></div> -->
<!-- 					            <div class="tab-panel-title-text">绩效分布</div> -->
<!-- 					            <div class="tab-panel-title-zoom"></div> -->
<!-- 					        </div> -->
					        <div class="content">
								<div align="center" class="change-btn-group">	
						        	 <div class="btn-group" id="allEmp" onselectstart="return false">	
							        	<span class="button condition" data="0">&nbsp;&nbsp;&nbsp;&nbsp;全部&nbsp;&nbsp;&nbsp;&nbsp;</span>
							        	<span class="button" data="1">&nbsp;&nbsp;管理者&nbsp;&nbsp;</span>
							        	<span class="button" data="2">&nbsp;&nbsp;&nbsp;&nbsp;员工&nbsp;&nbsp;&nbsp;&nbsp;</span>
							        </div>
						        </div>
						        <div class="title">共<span  id="joinCount">0</span>人参与了本期绩效评估，未参与
		                    		<span  id="notJoinCount">0</span>人</div>
								<div class="chartPanel">
		               		  	  <div class="chart" id="preDisChart"></div>
		               		  	</div>
						     </div>	
				        </div>
			        </div>	
                </div>
                <div class="col-xs-12 tab-panel" id="jxbhPanel">
			        <div class="content">
			        	<div class="title"><span class="blue">有所进步</span>-与上期绩效相比，提升一级或以上</div>	
			        	<div class="title"><span class="red">出现下滑</span>-与上期绩效相比，下降一级或以上</div>	
			        	 <div class="customChartPanel mainArea" align="center">  
			        	    <div class="customChart" id="preChange"></div>
			        	  </div>
			        </div>	
                </div>
                <div class="col-xs-12 tab-panel " id="jxycPanel">
			        <div class="content">
			            <div class="title"><span class="blue">飞速提升</span>-与上期绩效相比，提升幅度至少2个星级</div>	
			        	<div class="title"><span class="red">加速跌落</span>-与上期绩效相比，下滑幅度至少2个星级</div>	
	               		<div class="customChartPanel mainArea" align="center">
	               			<div class="customChart" id="preUnusual"></div>
	               		</div>
           		 </div>
			     </div>	
                 <div class="col-xs-12 tab-panel" id="jxmxPanel">
                 	 <div class="panel">
<!-- 			        	<div class="tab-panel-title"> -->
<!-- 				            <div class="tab-panel-title-left"></div> -->
<!-- 				            <div class="tab-panel-title-text">绩效明细</div> -->
<!-- 				            <div class="tab-panel-title-zoom"></div> -->
<!-- 				        </div> -->
				        <div class="content">
	               		    <div class="tablePanel" id="performanceDetailPanel">  
	               		      <table id="performanceDetail"></table>   
				        	</div>	
				        </div>	
			        </div>
                </div>
               
          </div>	
	</div>
</div>
<!--遮罩层 begin-->
<div class="shade"></div>
<!--遮罩层 end-->

</div>

<script src="${ctx}/assets/js/require.js"></script>
<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/mobile/js/biz/perChange/perChange.js?v=${v.version}"></script>
</body>
</html>