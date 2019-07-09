<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>人力结构</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/datetime/datetimepicker.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/competency/talentStructure.css"/>
</head>
<body>
<div class="talentStructure">
    <div class="rightBody">
        <div id="page-one" class="rightBodyPage">
            <div class="row ct-row" id="top">
                <top-block-component lable="可用编制分析" :class="['col-sm-4 ct-line-col']">
                	<gauge-component slot="body" :list="gaugeData" :class="'structureRateChart'"></gauge-component>
                	<div slot="body" id="structureRateText" :class="color">
                                <div class="rate"> {{rate}} </div>
                                <div class="text"> {{text}} </div>
                            </div>
                </top-block-component>
                
				<top-block-component lable="可用编制分析" :class="['col-sm-4 ct-line-col']">
						<table slot="body" id="talentStructureTable" class="center talentStructureTable">
                                <tr>
                                    <th>可用编制</th>
                                    <th>=</th>
                                    <th>编制数</th>
                                    <th>-</th>
                                    <th>在岗人数</th>
                                </tr>
                                <tr>
                                    <td class="usableEmpCount">-</td>
                                    <td>=</td>
                                    <td class="number">-</td>
                                    <td>-</td>
                                    <td class="empCount">-</td>
                                </tr>
                            </table>
	    		</top-block-component>
            

                <div class="col-sm-4 ct-line-col">
                    <input id="quotaId" type="hidden" value="${quotaId}">
                    <div class="top-div" id="timeLine"></div>
                </div>

            </div>

            <div id="app" class="row ct-row">
                <div class="col-sm-12 ct-line-col SetUpBody" view="chart">
                	  <block-layout lable="职位序列分布" clazz="height100pc" >
                			<bar-component  :list="positionSequence" :listen="listenSequence"/>
               		  </block-layout>
               		   <block-layout lable="职级分布" clazz="height100pc" >
                			<bar-component  :list="positionRank" />
               		  </block-layout>
                   
                </div>
            </div>

        </div>
    </div>

    <!--人才结构指标设置 弹出框 begin-->
    <div class="modal fade popup-modal" id="settingModal" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-header-text">编制使用率预警设置</div>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                </div>

                <div class="modal-body">
                    <div class="row ct-row">
                        <div class="col-sm-6 BSetUp-col">
                            <div class="BSetUp-line">
                                <div class="color-div green-color-div pull-left"></div>
                                <span class="BSetUp-text-one pull-left">正常值：</span>
                                <input id="zdWindowNormal" class="BSetUp-input pull-left" type="text">
                                <span class="BSetUp-text-two pull-left">%</span>
                            </div>
                        </div>
                        <div class="col-sm-6 BSetUp-col">
                            <div class="BSetUp-line">
                                <div class="color-div orange-color-div pull-left"></div>
                                <span class="BSetUp-text-one pull-left">风险值：</span>
                                <input id="zdWindowRisk" class="BSetUp-input pull-left" type="text">
                                <span class="BSetUp-text-two pull-left">%</span>
                            </div>
                        </div>
                        <div class="col-xs-12 BSetUp-col">
                            <div class="BSetUp-text-line">
                                绿色区域：等于或低于正常值
                            </div>
                        </div>
                        <div class="col-xs-12 BSetUp-col">
                            <div class="BSetUp-text-line">
                                黄色区域：高于正常值，等于或低于风险值
                            </div>
                        </div>
                        <div class="col-xs-12 BSetUp-col">
                            <div class="BSetUp-text-line BSetUp-text-last-line">
                                红色区域：高于风险值
                            </div>
                        </div>
                        <div class="col-xs-12 BSetUp-col">
                            <div class="BSetUp-text-line">&nbsp;
                            <span class="errmsg hide">正常值必须小于风险值！</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <div id="btnSaveRate" class="modal-btn success-btn">确定</div>
                    <div id="cancelSetting" class="modal-btn default-btn" data-dismiss="modal">取消</div>
                </div>
            </div>
        </div>
    </div>
    <!--人才结构指标设置 弹出框 end-->
</div>
<script src="${jsRoot}lib/echarts/echarts.js"></script>
<script src="${jsRoot}biz/competency/talentStructure.js"></script>
</body>
</html>
