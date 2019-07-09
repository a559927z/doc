<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>

<html lang="en">
<head>
  <link rel="stylesheet" href="${ctx}/assets/css/jqueryui/jquery-ui.min.css" />
  <link rel="stylesheet" href="${ctx}/assets/css/font-awesome/font-awesome.min.css"/>
  <!-- jsp文件头和头部 -->
  <%@include file="/WEB-INF/views/include/top.jsp" %>
  <title>招聘看板</title>

  <link rel="stylesheet" href="${ctx}/assets/css/zTreeStyle/zTreeStyle.css"/>
  <link rel="stylesheet" href="${ctx}/assets/css/datetime/datetimepicker.css"/>
  <link rel="stylesheet" href="${ctx}/assets/css/biz/productivity/recruitBoard.css"/>
</head>
<body>
<div class="recruit-board" id="recruitboard">
  <div class="leftBody">
    <div class="leftListBigDiv">招聘看板</div>
    <div page="page-one" class="leftListDiv selectList">招聘统计</div>
    <div page="page-two" class="leftListDiv">招聘画像</div>
  </div>

  <div class="rightBody">
    <div id="page-one" class="rightBodyPage">
      <div class="row ct-row">

        <div id="recruitingPositionNum" class="col-sm-3 ct-22 ct-line-col">
          <div class="top-div">
            <div class="index-common-title">
              <div class="index-common-title-left"></div>
              <div class="index-common-title-text"><span class="year"></span>待招聘岗位</div>
            </div>
            <div class="body-div">
                <div class="data loadingtext">数据加载中</div>
                <div class="data hide">
                  <div class="accord-yj-float">
                    <span class="accord-yj-float-value" >0</span>
                    <span class="accord-yj-float-people">个</span>
                  </div>
                  <div class="detail clearfix">
                    <div class="text pull-left"></div>
                    <div class="pull-right">></div>
                  </div>
                </div>
            </div>
          </div>
        </div>

        <div id="recruitingPeopleNum" class="col-sm-3 ct-22 ct-line-col">
          <div class="top-div">
            <div class="index-common-title">
              <div class="index-common-title-left"></div>
              <div class="index-common-title-text"><span class="year"></span>待招聘人数</div>
            </div>
            <div class="body-div">
              <div class="data loadingtext">数据加载中</div>
              <div class="data hide">
                <div class="accord-yj-float">
                  <span class="accord-yj-float-value" >0</span>
                  <span class="accord-yj-float-people">人</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div id="recruitingCost" class="col-sm-3 ct-22 ct-line-col">
          <div class="top-div">
            <div class="index-common-title">
              <div class="index-common-title-left"></div>
              <div class="index-common-title-text"><span class="year"></span>招聘费用</div>
            </div>
            <div class="body-div">
              <div class="data loadingtext">数据加载中</div>
              <div class="data hide">
                <div class="accord-yj-float">
                  <span class="accord-yj-float-value">0</span>
                  <span class="accord-yj-float-people">万元</span>
                </div>
                <div class="text-center" style="color:#999;">已花费今年招聘预算<span class="usedCost">0%</span></div>
              </div>
            </div>
          </div>
        </div>

        <div class="col-sm-3 ct-34 ct-line-col">
          <input id="quotaId" type="hidden" value="${quotaId}">
          <div class="top-div" id="timeLine"></div>
        </div>
      </div>

      <div class="row ct-row">
        <div class="col-sm-12 ct-line-col SetUpBody">
          <div class="index-common-title bottom-title">
            <div class="index-common-title-left bottom-left"></div>
            <div class="index-common-title-text bottom-text">岗位满足率统计<span><i id="positionSetting" title="岗位统计显示设置" class="icon-cog"></i>（操作提示：鼠标点击可查看招聘进度）</span></div>
          </div>
          <div class="bottom-div bottom-div-two">
            <div class="row" id="positionFillRate">
              <div class="data loadingtext position">数据加载中</div>
              <div class="data clearfix hide"></div>
            </div>
          </div>
        </div>
      </div>

      <div class="row ct-row">
        <div class="col-sm-12 ct-line-col SetUpBody">
          <div class="index-common-title bottom-title">
            <div class="index-common-title-left bottom-left"></div>
            <div class="index-common-title-text bottom-text">招聘渠道统计</div>
          </div>
          <div class="bottom-div bottom-div-two">
            <div class="row">
              <div class="padding8 height402">
                <div class="dis-search clearfix">
                  <input class="dis-search-input" id="txtTrenchStatistics" type="text" placeholder="请输入岗位名称">
                  <div class="add-on dis-search-input-btn" id="btnTrenchStatistics">搜索</div>
                </div>
                <div id="trenchStatistics">

                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row ct-row">
        <div class="col-sm-12 ct-line-col SetUpBody">
          <div class="index-common-title bottom-title">
            <div class="index-common-title-left bottom-left"></div>
            <div class="index-common-title-text bottom-text">人员异动提醒<span>（说明：以下人员异动可能需要招聘新的人员）</span></div>
          </div>
          <div class="bottom-div bottom-div-two">
            <div class="row transaction">

              <div class="col-md-3 col-sm-6 col-xs-12 item" id="promote">
                <div class="itembd" data-index="0">
                  <div class="itemtop">
                    <div class="text"><span>0</span><small>人</small></div>
                    <div>最近晋级</div>
                  </div>
                  <div class="itembottom"></div>
                </div>
              </div>
              <div class="col-md-3 col-sm-6 col-xs-12 item" id="move">
                <div class="itembd" data-index="1">
                  <div class="itemtop">
                    <div class="text"><span>0</span><small>人</small></div>
                    <div>最近调动</div>
                  </div>
                  <div class="itembottom"></div>
                </div>
              </div>
              <div class="col-md-3 col-sm-6 col-xs-12 item" id="dimission">
                <div class="itembd" data-index="2">
                  <div class="itemtop">
                    <div class="text"><span>0</span><small>人</small></div>
                    <div>最近离职</div>
                  </div>
                  <div class="itembottom"></div>
                </div>
              </div>
              <div class="col-md-3 col-sm-6 col-xs-12 item" id="retire">
                <div class="itembd" data-index="3">
                  <div class="itemtop">
                    <div class="text"><span>0</span><small>人</small></div>
                    <div>最近退休</div>
                  </div>
                  <div class="itembottom"></div>
                </div>
              </div>

            </div>
          </div>
        </div>
      </div>

    </div>

    <div id="page-two" class="rightBodyPage">

      <div class="row ct-row">
        <div class="col-sm-12 ct-line-col SetUpBody">
          <div class="index-common-title bottom-title">
            <div class="index-common-title-left bottom-left"></div>
            <div class="index-common-title-text bottom-text">高绩效员工画像</div>
          </div>
          <div class="bottom-div bottom-div-two">
            <div class="row">

              <div class="padding8 clearfix overflowauto">

                  <div class="col-sm-6 col-xs-12 padding0">
                    <div class="padding8">
                      <div class="dis-search marginbottom8 clearfix">
                        <input class="dis-search-input" id="txtPerformance" type="text" placeholder="请输入岗位名称">
                        <div class="add-on dis-search-input-btn" id="btnperformance">搜索</div>
                      </div>

                      <table id="performance">
                        <tr class="bd">
                          <td class="lr pfleft"></td>
                          <td class="cent">
                            <img src="${ctx}/assets/img/avatar.png" />
                          </td>
                          <td class="lr pfright"></td>
                        </tr>
                        <tr class="bd hide">
                          <td class="msg" colspan="3">数据加载中</td>
                        </tr>
                        <tr>
                          <td colspan="3" class="set">
                            <div>高绩效员工画像<i id="performanceSetting" class="icon-cog" title="高绩效员工画像设置"></i></div>
                          </td>
                        </tr>
                        <tr>
                          <td colspan="3" class="color999">统计说明：高绩效员工为近<span id="yeardefault">3</span>年有过<span id="frequencydefault">2</span>次<span id="stardefault">4</span>星以上人员</td>
                        </tr>
                      </table>
                    </div>
                  </div>
                  <div class="col-sm-6 col-xs-12 padding0">
                    <div class="padding8">
                      <div class="padding8 paddingleft0">
                        <span class="marginright10">人员推荐</span>
                        <label id="resetSearch" class="hide">您已修改以下列表的搜索条件。<a href="javascript:;">点击重置</a></label>
                      </div>
                      <div id="performanceList">
                        <table id="performanceGrid"></table>
                        <table id="performanceGridPage"></table>
                      </div>
                    </div>
                  </div>
              </div>

            </div>
          </div>
        </div>
      </div>

      <div class="row ct-row">
        <div class="col-sm-12 ct-line-col SetUpBody">
          <div class="index-common-title bottom-title">
            <div class="index-common-title-left bottom-left"></div>
            <div class="index-common-title-text bottom-text">招聘岗位薪酬参考</div>
          </div>
          <div class="bottom-div bottom-div-two">
            <div class="row">
              <div class="padding8">
                <div class="dis-search clearfix">
                  <input class="dis-search-input" id="txtRemuneration" type="text" placeholder="请输入岗位名称">
                  <div class="add-on dis-search-input-btn" id="btnRemuneration">搜索</div>
                </div>
                <div id="remuneration" class="paddingtop8">
                  <div>
                    <table id="remunerationGrid">
                      <thead>
                        <tr><th>招聘岗位</th><th>职级</th><th>薪资</th><th>薪资均值</th></tr>
                      </thead>
                      <tbody>

                      </tbody>
                    </table>
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





<!--遮罩层 begin-->
<div class="shade"></div>
<!--遮罩层 end-->

<!-- 待招聘岗位 -->
<div class="modal fade" id="recruitingPositionModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
        </button>
        <h4 class="modal-title">
          待招岗位<span>0</span>
        </h4>
      </div>

      <div class="modal-body page-content" id="rpm">
        <div class="content clearfix">

        </div>
      </div>
    </div>
  </div>
</div>

<!-- 岗位统计显示设置 -->
<div class="modal fade" id="positionSettingModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
        </button>
        <h4 class="modal-title">
          岗位统计显示设置
        </h4>
      </div>

      <div class="modal-body page-content" id="psmc">
        <div class="tip">
          <div>提示：左右拖拽可以调整顺序</div>
        </div>
        <div class="body">
          <div class="b bshow">
            <div class="btext">已显示</div>
            <div class="content clearfix">

            </div>
          </div>

          <div class="b bhide">
            <div class="btext">已隐藏</div>
            <div class="content clearfix">

            </div>
          </div>

        </div>
        <div class="bottomdiv">
          <button id="saveSetting" class="save">确定</button>
          <button id="cancelSetting" class="cancel">取消</button>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- 岗位满足率统计列表 -->
<div class="modal fade" id="positionFillRateModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
        </button>
        <h4 class="modal-title">
          【<span></span>】招聘情况明细
        </h4>
      </div>

      <div class="modal-body page-content" id="pfrm" style="overflow: auto;">
        <div style="padding:8px;">
          <div class="taball clearfix">
            <div class="tab unselectable" data-index="0">收到简历<span class="resumeNum"></span></div>
            <div class="tab unselectable" data-index="1">面试<span class="interviewNum"></span></div>
            <div class="tab unselectable" data-index="2">offer<span class="offerNum"></span></div>
            <div class="tab unselectable" data-index="3">入职<span class="jobNum"></span></div>
          </div>
          <div class="detail">
            <div class="tgrid resume hide">
              <table id="resumeGrid"></table>
              <table id="resumeGridPage"></table>
            </div>
            <div class="tgrid interview hide">
              <table id="interviewGrid"></table>
              <table id="interviewGridPage"></table>
            </div>
            <div class="tgrid offer hide">
              <table id="offerGrid"></table>
              <table id="offerGridPage"></table>
            </div>
            <div class="tgrid job hide">
              <table id="jobGrid"></table>
              <table id="jobGridPage"></table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- 招聘渠道统计 -->
<div class="modal fade" id="trenchStatisticsModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
        </button>
        <h4 class="modal-title">
          招聘渠道统计
        </h4>
      </div>

      <div class="modal-body page-content" id="tsm">
        <div class="padding8" style="height:100%;">
          <div id="tsmChart" style="height:100%;"><div class="loadingmessage">数据加载中</div></div>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- 最近人员异动 -->
<div class="modal fade" id="transactionPromptModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
        </button>
        <h4 class="modal-title">
          最近人员异动
        </h4>
      </div>

      <div class="modal-body page-content" id="tpm" style="overflow: auto;">
        <div class="padding8">
          <div class="taball clearfix">
            <div class="tab unselectable" data-index="0">晋级<span class="promoteNum">0</span></div>
            <div class="tab unselectable" data-index="1">调动<span class="moveNum">0</span></div>
            <div class="tab unselectable" data-index="2">离职<span class="dimissionNum">0</span></div>
            <div class="tab unselectable" data-index="3">退休<span class="retireNum">0</span></div>
          </div>
          <div class="detail">
            <div class="tgrid promote hide">
              <table id="promoteGrid"></table>
              <table id="promoteGridPage"></table>
            </div>
            <div class="tgrid move hide">
              <table id="moveGrid"></table>
              <table id="moveGridPage"></table>
            </div>
            <div class="tgrid dimission hide">
              <table id="dimissionGrid"></table>
              <table id="dimissionGridPage"></table>
            </div>
            <div class="tgrid retire hide">
              <table id="retireGrid"></table>
              <table id="retireGridPage"></table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- 调整画像条件 -->
<div class="modal fade" id="portrayalModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
        </button>
        <h4 class="modal-title">
          调整画像条件
        </h4>
      </div>

      <div class="modal-body page-content" id="ptm">
        <div class="padding8">
          <div class="pbody">
            <div class="pcontent bborder clearfix">
              <div class="title">高绩效人群定义</div>
              <div class="content clearfix">
                <span>近</span>
                <select id="year">
                  <option value="1">1</option>
                  <option value="2">2</option>
                  <option value="3">3</option>
                  <option value="4">4</option>
                  <option value="5">5</option>
                </select>
                <span>年有过</span>
                <select id="frequency">
                  <option value="1">1</option>
                  <option value="2">2</option>
                  <option value="3">3</option>
                  <option value="4">4</option>
                  <option value="5">5</option>
                </select>
                <span>次</span>
                <select id="star">
                  <option value="0">数据加载中</option>
                </select>
                <span>以上人员</span>
              </div>
            </div>
            <div class="pcontent clearfix">
              <div class="title">基本信息</div>
              <div class="content clearfix">
                <select id="sex">
                  <option value="0">全部性别</option>
                  <option value="m">男生</option>
                  <option value="w">女生</option>
                </select>
                <select id="school">
                  <option value="0">数据加载中</option>
                </select>
                <select id="degree">
                  <option value="0">数据加载中</option>
                </select>
              </div>
            </div>
            <div class="pcontent clearfix paddingbottom7">
              <div class="title">能力素质</div>
              <div id="ability" class="content clearfix">
                <div class="msg">数据加载中</div>
                <%--<div class="ability clearfix">--%>
                  <%--<div class="aby">--%>
                    <%--<i class="add fa fa-plus-circle"></i>--%>
                    <%--<i class="remove fa fa-minus-circle"></i>--%>
                    <%--<i class="eyeshow eye fa fa-eye"></i>--%>
                    <%--<i class="eyehide eye fa fa-eye-slash color999 hide"></i>--%>
                    <%--<select>--%>
                    <%--</select>--%>
                  <%--</div>--%>
                  <%--<div class="abyc clearfix">--%>
                    <%--<div class="all">不限</div>--%>
                    <%--<div class="clearfix">--%>
                      <%--<div class="aly">5分</div>--%>
                      <%--<div class="aly">4分</div>--%>
                      <%--<div class="aly">3分</div>--%>
                      <%--<div class="aly">2分</div>--%>
                      <%--<div class="aly">1分</div>--%>
                    <%--</div>--%>
                  <%--</div>--%>
                <%--</div>--%>

              </div>
            </div>
            <div class="pcontent clearfix">
              <div class="title">人员标签</div>
              <div id="tag" class="content clearfix">
                  <div class="tag" data-id="1">勤奋</div>
                  <div class="tag" data-id="2">勤奋</div>
                  <div class="tag" data-id="3">勤奋</div>
              </div>
            </div>
            <div class="pcontent clearfix">
              <div class="title">&nbsp;</div>
              <div class="content clearfix">
                <div class="botton"><button id="save" type="button">调整画像</button></div>
                <div class="bottondiv">符合搜索条件共<span>0</span>人</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript" src="${jsRoot}require.js"></script>
<script type="text/javascript" src="${jsRoot}lib/plugins/echarts/echarts.js"></script>
<script type="text/javascript" src="${jsRoot}biz/productivity/recruitBoard.js"></script>
</body>
</html>
