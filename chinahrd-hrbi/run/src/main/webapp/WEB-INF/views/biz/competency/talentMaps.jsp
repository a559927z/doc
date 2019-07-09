<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>人才地图</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/competency/talentMaps.css"/>
</head>
<body>

<div class="talent-maps" id="talentMaps">
    <div class="rightBody">
        <div id="page-one" class="rightBodyPage">
        	<c:choose>
        		<c:when test="${isAuditor}">
					<div class="row ct-row rightBackground">
						<div class="col-md-12 ct-line-col SetUpBody">
							<div class="index-common-title bottom-title">
								<div class="index-common-title-left bottom-left"></div>
								<div class="index-common-title-text bottom-text">待发布地图</div>
								<div class="right-center clearfix">
								    <a href="javascript:void(0)" class="index-jxmb-href">全部历史审核&gt;&gt;</a>
								</div>
							</div>
		
							<div class="bottom-div-two"></div>
		               </div>
					</div>
				</c:when>
				<c:otherwise>
					<shiro:hasPermission name="ShengRenLi_RenCaiDiTu:ShenHeQuanXuan">
						<div class="row ct-row rightBackground">
							<div class="col-md-12 ct-line-col SetUpBody">
								<div class="index-common-title bottom-title">
									<div class="index-common-title-left bottom-left"></div>
									<div class="index-common-title-text bottom-text">待发布地图</div>
									<div class="right-center clearfix">
									    <a href="javascript:void(0)" class="index-jxmb-href">全部历史审核&gt;&gt;</a>
									</div>
								</div>
			
								<div class="bottom-div-two"></div>
			               </div>
						</div>
					</shiro:hasPermission>
				</c:otherwise>
			</c:choose>
			<div style="height: 20px;"></div>
            
            <shiro:hasPermission name="ShengRenLi_RenCaiDiTu:OnlyView">
            <div class="row ct-row rightBackground-bottom">
                <div class="col-sm-12 ct-line-col SetUpBody">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">查看地图</div>
                    </div>
                    <div class="bottom-div-maps">
                        <div class="bottom-div2 left">
                            <div class="img-div">
                                <div class="talentmap-img"></div>
                            </div>
                            <div class="text-div">人才地图</div>
                            <div class="text-span clearfix">
                                <span class="span3">以绩效和能力维度分析团队的人才分布情况</span>
                                <span class="span3 newMapsDate">最新地图：<span class="maps-date">无</span></span>
                                <span class="span3 noMapsDate hide">暂无地图信息</span>
                            </div>
                            <div class="button-block">
                                <button class="button-style" id="map1">查看地图</button>
                            </div>
                        </div>
                        <div class="bottom-div2 left">
                            <div class="img-div">
                                <div class="teamability-img"></div>
                            </div>
                            <div class="text-div">团队能力/绩效趋势图</div>
                            <div class="text-span clearfix">
                                <span class="span3">以时间维度盘点团队的绩效/能力区间分布情况</span>
                                <span class="span3 noMapsDate hide">暂无地图信息</span>
                            </div>
                            <div class="button-block">
                                <button class="button-style" id="map2">查看地图</button>
                            </div>
                        </div>
                        <div class="bottom-div2 left">
                            <div class="img-div">
                                <div class="teampk-img"></div>
                            </div>
                            <div class="text-div">团队PK</div>
                            <div class="text-span clearfix">
                                <span class="span3">提供自定义条件将不同人群组合成多个团队进行比较分析</span>
                                <span class="span3 newMapsDate">最新地图：<span class="maps-date">无</span></span>
                                <span class="span3 noMapsDate hide">暂无地图信息</span>
                            </div>
                            <div class="button-block">
                                <button class="button-style" id="teampk">查看地图</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 
            <div style="height: 20px;"></div>
            
            <div class="row ct-row rightBackground">
                <div class="col-sm-12 ct-line-col SetUpBody">
                    <div class="index-common-title bottom-title">
                        <div class="index-common-title-left bottom-left"></div>
                        <div class="index-common-title-text bottom-text">查看地图</div>
                    </div>
                    <div class="bottom-div-maps">
                        <div class="bottom-div2 left">
                            <div class="img-div">
                                <div class="img1"></div>
                            </div>
                            <div class="text-div">人才地图</div>
                            <div class="clearfix">
                                <span class="span3">以绩效和能力维度分析团队的人才分布情况</span>
                                <span class="span3 newMapsDate">最新地图：<span class="maps-date">无</span></span>
                                <span class="span3 noMapsDate hide">暂无地图信息</span>
                            </div>
                            <div class="button-block">
                                <button class="btn btn-info btn-minier" id="map1">查看地图</button>
                            </div>
                        </div>
                        <div class="bottom-div2 left">
                            <div class="img-div">
                                <div class="img1"></div>
                            </div>
                            <div class="text-div">团队能力/绩效趋势图</div>
                            <div class="clearfix">
                                <span class="span3">以时间维度盘点团队的绩效/能力区间分布情况</span>
                                <span class="span3 noMapsDate hide">暂无地图信息</span>
                            </div>
                            <div class="button-block">
                                <button class="btn btn-info btn-minier" id="map2">查看地图</button>
                            </div>
                        </div>
                        <div class="bottom-div2 left">
                            <div class="img-div">
                                <div class="img1"></div>
                            </div>
                            <div class="text-div">团队PK</div>
                            <div class="clearfix">
                                <span class="span3">提供自定义条件将不同人群组合成多个团队进行比较分析</span>
                                <span class="span3 newMapsDate">最新地图：<span class="maps-date">无</span></span>
                                <span class="span3 noMapsDate hide">暂无地图信息</span>
                            </div>
                            <div class="button-block">
                                <button class="btn btn-info btn-minier" id="teampk">查看地图</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div> -->
            </shiro:hasPermission>
        </div>
    </div>
</div>

<script src="${ctx}/assets/js/biz/competency/talentMaps.js"></script>
</body>
</html>
