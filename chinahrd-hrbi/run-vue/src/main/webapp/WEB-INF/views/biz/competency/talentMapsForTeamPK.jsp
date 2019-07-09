<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>团队PK首页</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/competency/talentMapsForTeamPK.css"/>
</head>
<body>
<div class="talentmaps-teampk" id="talentMapsTeampk">
    <div class="rightBody">
        <div class="row ct-row">
            <div class="col-xs-12 ct-col1">
                <ul class="breadcrumb">
                    <li>
                        <i class="icon-home home-icon"></i>
                        <a href="javascript:void(0)" id="toHomeBtn">人才地图首页</a>
                    </li>
                    <li class="active">团队PK</li>
                </ul>
            </div>
        </div>
        <div class="SetUpBody">
            <input type="hidden" id="cycleDate" value="${cycleDate}">
            <div class="bottom-div" id="page-one">
                <div class="col-xs-12 ct-col2">
                    <span class="maps-pk-title">你可直接选择下级组织查看不同组织的人才地图分布</span>
                    <span class="maps-pk-remind">注:&nbsp;最多选择3个团队且总人数不能超过1000人</span>
                    <div class="maps-pk-main clearfix">
                        <c:forEach items="${organs}" var="dto">
                            <c:if test="${dto.empCount > 0}">
                                <div class="team-bottom-div pull-left" data-toggle="${dto.empCount}"
                                     data-organid="${dto.organId}" data-organname="${dto.organName}">
                                    <div class="circle pull-left">
                                        <span>${fn:substring(dto.organName, 0, 1)}</span>
                                    </div>
                                    <div class="bottom-text pull-left">
                                        <span>${dto.organName}</span><br>
                                        <span>${dto.empCount}人</span>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                    <div class="help-inline">
                        <label class="middle">
                            <input class="ace" type="checkbox" id="organTeamCheckBox"/>
                            <span class="lbl"> 保存选择，下次直接进入团队PK
							<img src="${ctx}/assets/img/base/tip.gif" data-toggle="tooltip" data-placement="bottom"
                                 data-title="勾选后下次将根据此次所选团队直接PK"/>
						</span>
                        </label>
                    </div>
                    <div class="maps-pk-bottom">
                        <button class="btn btn-info pull-left" id="organTeamBtn">
                            开始PK(<span id="organTeamNum">0</span>)
                        </button>
                        <a href="javascript:void(0)" id="customTeam" class="checkteam-link pull-left">我要自定义团队</a>
                    </div>
                </div>
            </div>
            <div class="bottom-div hide" id="page-two">
                <div class="col-xs-12 ct-col2">
                    <span class="maps-pk-title">你可点击设置条件将不同人群组合成多个团队查看人才地图分布</span>
                    <span class="maps-pk-remind">注:&nbsp;最多选择3个团队且总人数不能超过1000人</span>
                    <div class="maps-pk-main clearfix">
                        <div class="team-bottom-div pull-left">
                            <div class="circle pull-left">
                                <span>1</span>
                            </div>
                            <div class="bottom-text pull-left">
                                <span>团队1</span><br>
                                <a href="javascript:void(0)">设置条件</a>
                            </div>
                        </div>
                        <div class="team-bottom-div pull-left">
                            <div class="circle pull-left">
                                <span>2</span>
                            </div>
                            <div class="bottom-text pull-left">
                                <span>团队2</span><br>
                                <a href="javascript:void(0)">设置条件</a>
                            </div>
                        </div>
                        <div class="team-bottom-div pull-left">
                            <div class="circle pull-left">
                                <span>3</span>
                            </div>
                            <div class="bottom-text pull-left">
                                <span>团队3</span><br>
                                <a href="javascript:void(0)">设置条件</a>
                            </div>
                        </div>
                    </div>
                    <div class="help-inline">
                        <label class="middle">
                            <input class="ace" type="checkbox" id="customTeamCheckBox"/>
                            <span class="lbl"> 保存选择，下次直接进入团队PK
                                <img src="${ctx}/assets/img/base/tip.gif" data-toggle="tooltip" data-placement="bottom"
                                     data-title="勾选后下次将根据此次所选团队直接PK"/>
                            </span>
                        </label>
                    </div>
                    <div class="maps-pk-bottom">
                        <button class="btn btn-info left" id="customTeamBtn">开始PK</button>
                        <a href="javascript:void(0)" id="checkSuborgan" class="checkteam-link left">选择下级组织快速PK</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 搜索弹出框 start -->
<div class="modal fade popup-modal" id="customTeamModal" tabindex="-1" role="dialog"
     aria-labelledby="versionModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-text">团队设置：</div>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">团队命名：</label>
                        <div class="col-sm-10 ct-col1"><input id="teamName" class="input-large" type="text"
                                                              placeholder="请输入团队名称"/></div>
                    </div>
                    <div class="form-group">
                        <input type="hidden" id="topOrganId" value="${topOrgan.organizationId}">
                        <input type="hidden" id="topOrganName" value="${topOrgan.organizationName}">
                        <label class="col-sm-2 control-label">组织架构： </label>
                        <div class="col-sm-10 ct-col1">
                            <div class="col-sm-6 ct-col1">
                                <ul class="ztree" id="organZtree"></ul>
                                <i id="organRemovebtn" class="icon-remove hide"></i></div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">主序列： </label>
                        <div class="col-sm-10 ct-col1" id="sequenceId"></div>
                    </div>
                    <div class="form-group hide">
                        <label class="col-sm-2 control-label">子序列： </label>
                        <div class="col-sm-10 ct-col1" id="sequenceSubId"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">职位层级： </label>
                        <div class="col-sm-10 ct-col1" id="abilityId"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">绩效： </label>
                        <div class="col-sm-10 ct-col1" id="performanceKey"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">年龄： </label>
                        <div class="col-sm-10 ct-col1" id="ageId"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">性别： </label>
                        <div class="col-sm-10 ct-col1" id="sexId"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">学历： </label>
                        <div class="col-sm-10 ct-col1" id="eduId"></div>
                    </div>
                    <div class="form-group" id="continueAddBlock">
                        <label class="col-sm-2 control-label"></label>
                        <div class="col-sm-10">
                            <label class="middle" id="continueAddTeamBtn">
                                <input class="ace" type="checkbox" id="continueAddTeam"/>
                                <span class="lbl"> 继续添加团队</span>
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label"></label>
                        <div class="col-sm-10 ct-col1">
                            <button class="btn btn-info" id="customTeamSubmitBtn">确定</button>
                            <button class="btn btn-white" data-dismiss="modal" aria-hidden="true">取消</button>
                            <label class="form-teamNum">当前团队人数<span id="teamNum">...</span>人</label>
                        </div>
                    </div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 搜索弹出框 end -->

<script src="${ctx}/assets/js/biz/competency/talentMapsForTeamPK.js"></script>
</body>
</html>