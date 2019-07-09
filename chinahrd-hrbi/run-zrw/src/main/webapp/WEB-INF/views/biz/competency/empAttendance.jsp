<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>

<html lang="en">
<head>
	<meta http-equiv="pragma" CONTENT="no-cache"> 
	<meta http-equiv="Cache-Control" CONTENT="no-cache"> 
	<meta http-equiv="expires" CONTENT="0">
	<%@include file="/WEB-INF/views/include/top.jsp"%>
	<title>才报平台员工主页</title>
	<link rel="stylesheet" href="${ctx}/assets/css/biz/widgets/clndr.css" />
	<link rel="stylesheet" href="${ctx}/assets/css/datetime/datetimepicker.css"/>
	<link rel="stylesheet" href="${ctx}/assets/css/biz/competency/empAttendance.css"/>
</head>
<body>
<%@include file="../../include/attendanceHeader.jsp" %>
<input type="hidden" id="sex" value="${empDto.sex }">
<input type="hidden" id="warnDay" value="${warnDay}">
<input type="hidden" id="beginYear" value="${beginYear}">
<input type="hidden" id="organId" value="${empDto.organId }">
<div class="main-container" id="main-container">
	<div class="main-contents">
		<div class="nav-content">
<%-- 			<span class="weather-icon" style="background-image:url(${ctx}/assets/img/icon/${weatherDto.condCode}.png);*background-image:none;*filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=${ctx}/assets/img/icon/${weatherDto.condCode}.png, enabled=true)"></span> --%>
			<span class="weather-icon"></span>
			<span class="" id="weatherInfo">
<%-- 					${weatherDto.date }，${weatherDto.weekDay }，${weatherDto.cond } --%>
			</span>
			<span class="remind-left"></span>
			<span class="margin-lr20">
				<span id="weatherDtoTmpMax"><%-- ${weatherDto.tmpMax } --%></span>℃&nbsp;~&nbsp;
				<span id="weatherDtoTmpMin"><%-- ${weatherDto.tmpMin } --%></span>℃&nbsp;&nbsp;&nbsp;&nbsp;
				<span id="weatherDtoSuggest"><%-- ${weatherDto.suggest } --%></span>
			</span>
			<span class="remind-right"></span>
		</div>
		<div style="clear: both;"></div>
		<div class="content-body">
			<div class="nav-attendance">
				<div class="nav-attendance-img-m" id="nav_attendance_img">
					<div class="img-left">
						<span class="text-year">${empDto.entryYear }</span><br>
						<span class="text-date">${empDto.entryMonth }月${empDto.entryDay }日，你来到中人网</span><br>
						<span class="text-cue">希望在接下来的日子里继续共同成长</span>
					</div>
					<div class="img-right">
						<span class="right-text-cue">至今你已陪伴中人网</span><br>
						<span class="right-text-time">${empDto.incumbencyTime }</span>
						<span class="right-text-cue">天</span>
					</div>
				</div>
			</div>
			<div class="attendance-info">
				<div class="attendance-info-left">
					<div class="user-info1">
						<div class="user-img"></div>
						<div class="text-bold18">${empDto.userName }</div>
						<div class="text-font12">
							<span>${empDto.organName }</span>/
							<span>${empDto.positionName }</span>
						</div>
						<div class="margin-top8">
							<span class="font-size12999">工作地点：</span>
							<span class="font-size12666">${empDto.workPlace }</span>
						</div>
					</div>
					<div class="height131">
						<div class="font-size14444">本年可调休
							<span class="font-size2409c" id="vacationTotal">0</span>天=内部调休
							<span class="font-size2409c" id="vacationCanLeave">0</span>+年假
							<span class="font-size2409c" id="vactionAnnual">0</span>
						</div>
<!-- 						<div class="margin-left20 font-size12999">提示：可调休天数包括年假！</div> -->
					</div>
				</div>
				<div class="attendance-info-right" id="attendanceInfoRight">
					<div class="index-common-title">
						<div class="index-common-title-left"></div>
						<div class="index-common-title-text"><span id="attendanceMonth"></span>月考勤信息</div>
					</div>
					<div class="attendance_bar">
						<div class="height260" id="attendanceBar"></div>
					</div>
					<div class="height30">
						<div class="text-btn" id="adjustment">考勤调整</div>
					</div>
				</div>
				<div class="remind-remainder" id="remainderBlock">
					<div class="remind-remainder-text left">离调整<span class="remainder-month"></span>月考勤剩</div>
					<div class="remind-remainder-img">
						<span class="remainder-num"></span>
						<span class="remainder-tian">天</span>
					</div>
				</div>
			</div>
			
			<div class="attendance-other-day"></div>
			
		</div>
	</div>
</div>

<!-- 考勤调整 弹出框 begin -->
<div class="modal fade popup-modal" id="attendanceAdjustmentModal"
	tabindex="-1" role="dialog" aria-labelledby="modalLabel"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<div class="modal-header-text">考勤调整</div>
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<div class="modal-title title-span" style="display: inline-block;">
                 	<div class="modal-style-margin">
						<select class="select-first selection" id="selectYear">
						</select>
						<select class="select-second selection" id="selectMonth">
						</select>
					</div>
				</div>
				<div class="grid">
					<div class="span13">
						<div>
						<div id="multiday-mixed-performance" class="cal1"></div>
						</div>
						<div class="padding20">
							<span class="exception-div left"></span>
							<span class="font-size12666">异常出勤</span>
							<input type="hidden" id="isFisrt" value="${isFisrt}">
							<input type="hidden" id="isSZ" value="${isSZ}">
						</div>
					</div>
					<div class="span11">
						<div class="modal-content-right">
							<div class="content-header" id="contentTitle">调整说明</div>
							<div class="instruction" id="instruction">
								<ul class="adjstment-table">
									<li>点击日历上的时间可查看/调整当天考勤信息；</li>
									<li>加班暂不支持自动统计，请点击日期申报；</li>
									<li>如果你的考勤没有同步数据，可尝试<a href="#" id="batchLink">批量调整</a>。</li>
								</ul>
							</div>
							<div class="adjustment-div margin-top20" id="batchAdjustment" style="display: none;">
								<div style="display: none;" id="yearMonth"></div>
								<div class="adjustment-body">
									<table class="adjstment-table" cellspacing="20" id="batchAdjustTable">
										<tr>
											<td>调整日期：</td><td class="td_content"><select id="monthStart"></select>&nbsp;-&nbsp;<select id="monthEnd"></select></td>
										</tr>
										<tr>
											<td>调整时间：</td>
											<td>
												<div class="bootstrap-timepicker">
									                <input class="input-time" id="inTime" type="text" readonly="readonly">&nbsp;-
									                <input class="input-time" id="outTime" type="text" readonly="readonly">
									                <a href="#" id="batchLeave">全天请假</a>	
										        </div>
											</td>
										</tr>
										<tr id="batchResultTr">
											<td>考勤结果：</td>
											<td class="td_content">
												<div class="left">
													<select id="batchResult"></select>
													<span id="batchResultLine">-</span>
													<span id="normalResult"></span>
													<span id="batchLeaveTime"></span>
													<span id="batchOverTime"></span>
												</div>
												<div class="card-info-img" id="cardInfoB" data-toggle="tooltip" data-placement="bottom" data-original-title="" ></div>
											</td>
										</tr>
										<tr id="tr_batchResultWarn">
											<td width="76px;"></td>
											<td class="td_content">
												<div id="batchResultWarn" class="icon-warning-sign warning">
													<span id="batchLeaveInLieu">你当前可调休<span id="batchResultday">10</span>天！</span>
													<span id="batchAnnualLeave">你当前可用年假<span id="batchAnnualday">10</span>天！</span>
													<span id="batchOvertime_remind">请确认加班申请已跟公司申请！</span>
												</div>
											</td>
										</tr>
										<tr id="batchReasonTr">
											<td>异常原因：</td>
											<td class="td_content">
												<select id="batchReason"></select>
												<span id="batchReasonLine">-</span>
											</td>
										</tr>
										<tr id="tr_batchExceptionWarn">
											<td width="76px;"></td>
											<td class="td_content">
												<div id="batchExceptionWarn" class="icon-warning-sign warning">
													<span class="warning">请选择异常原因！</span>
												</div>
											</td>
										</tr>
										<tr>
											<td>备&nbsp;&nbsp;&nbsp;&nbsp;注：</td><td class="td_content"><textarea id="note" rows="3" cols="35"></textarea></td>
										</tr>
									</table>
								</div>
								<div class="btn-style">
									<div class="submit-btn left" id="batchSubmit">提交</div>
									<div class="cancel-btn left" id="cancelBtn">取消</div>
								</div>
							</div>
							<div class="batch-submit-remind adjustment-div margin-top20" style="display: none;">
								<div class="adjustment-body">
									<div class="batch-submit-remind-img"></div>
									<div class="batch-submit-remind-text">确定要把<span id="days"></span>日调整为<span id="attendancetype">正常出勤</span>吗？</div>
								</div>
								<div class="btn-style">
									<div class="submit-btn left" id="batchRemindSubmit">确定</div>
									<div class="submit-now-btn left" id="batchNowSubmit">正在提交...</div>
									<div class="cancel-btn left" id="cancelRemindBtn">取消</div>
								</div>
							</div>
							<div id="preDayAdjustment" class="adjustment-div margin-top20" style="display: none;">
								<div class="adjustment-body">
									<table class="adjstment-table" cellspacing="20">
										<tr class="tr-height">
											<td>打卡时间：</td>
											<td class="td_content">
												<span id="dayBeginTime"></span>&nbsp;-&nbsp;
												<span id="dayEndTime"></span>&nbsp;&nbsp;
											</td>
										</tr>
										<tr class="tr-height">
											<td>调整时间：</td><td class="td_content"><span id="adjustBegin"></span>&nbsp;-&nbsp;<span id="adjustEnd"></td>
										</tr>
										<tr class="tr-height">
											<td>考勤结果：</td><td class="td_content"><span id="attendanceResult">正常出勤</span></td>
										</tr>
										<tr id="tr_dayReason" class="tr-height">
											<td>异常原因：</td><td id="excTd" class="td_content"></td>
										</tr>
										<tr id="tr_dayNote">
											<td>备&nbsp;&nbsp;&nbsp;&nbsp;注：</td><td id="preNote" class="td_content"></td>
										</tr>
									</table>
								</div>
								<div class="btn-style">
									<div class="submit-btn left" id="preDayAdjustmentBtn">调整</div>
									<div class="cancel-btn left" id="comeBack">返回说明</div>
								</div>
							</div>
							<div id="dayAdjustment" class="adjustment-div margin-top20" style="display: none;">
								<div class="adjustment-body">
									<table class="adjstment-table" cellspacing="20">
										<tr class="tr-height">
											<td width="76px;">打卡时间：</td>
											<td class="td_content">
												<div class="left">
													<span id="dayBeginTimeAfter"></span>&nbsp;-&nbsp;
													<span id="dayEndTimeAfter"></span>
												</div>
												<div class="card-info-img" data-toggle="tooltip" data-placement="bottom" data-original-title="正常时间：10:00 - 18:00" ></div>
											</td>
										</tr>
										<tr id="adjustTime" class="tr-height">
											<td width="76px;">调整时间：</td>
											<td>
												<div class="bootstrap-timepicker">
									                <input class="input-time" id="indayTime" type="text" readonly="readonly">&nbsp;-
									                <input class="input-time" id="outdayTime" type="text" readonly="readonly">
									                <a href="#" id="linkLeave">全天请假</a>	
										        </div>
											</td>
										</tr>
										<tr class="tr-height">
											<td width="76px;">考勤结果：</td>
											<td id="dayResultTd" class="td_content">
												<div class="left">
													<select id="dayResult"></select>
													<span id="dayResultLine">-</span>
													<span id="normalDayResult"></span>
													<span id="dayLeaveTime"></span>
													<span id="dayOverTime"></span>
												</div>
												<div class="card-info-img" id="cardInfo" data-toggle="tooltip" data-placement="bottom" data-original-title="" ></div>
											</td>
										</tr>
										<tr id="tr_resultWarn">
											<td width="76px;"></td>
											<td class="td_content">
												<div id="resultWarn" class="icon-warning-sign warning">
													<span id="leaveInLieu">你当前可调休<span id="resultday">0</span>天！</span>
													<span id="annualLeave">你当前可用年假<span id="annualday">0</span>天！</span>
													<span id="overtime_remind">请确认加班申请已跟公司申请！</span>
												</div>
											</td>
										</tr>
										<tr class="tr-height">
											<td width="76px;">异常原因：</td>
											<td id="td_dayReason" class="td_content">
												<select id="dayReason"></select>
												<span id="dayReasonLine">-</span>
												<span id="dayReasonText"></span>
												
											</td>
										</tr>
										<tr id="tr_exceptionWarn">
											<td width="76px;"></td>
											<td class="td_content">
												<div id="exceptionWarn" class="icon-warning-sign warning">
													<span class="warning">请选择异常原因！</span>
												</div>
											</td>
										</tr>
										<tr>
											<td width="76px;">备&nbsp;&nbsp;&nbsp;&nbsp;注：</td><td class="td_content"><textarea id="dayNote" rows="3" cols="35"></textarea></td>
										</tr>
									</table>
								</div>
								<div class="btn-style">
									<div class="submit-btn left" id="daySubmit">提交</div>
									<div class="submit-now-btn left" id="dayNowSubmit">正在提交...</div>
									<div class="cancel-btn left" id="dayCancel">取消</div>
								</div>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
</div>
<!-- 考勤调整 弹出框 end -->
<div id="tooltipOne" class="tooltip-one"><span class="tooltipOneBtn"></span></div>
<div id="tooltipTwo" class="tooltip-block"><div class="tooltip-two-bg"></div></div>
<div id="tooltipThree" class="tooltip-block"><div class="tooltip-three-bg"><span class="tooltipThreeBtn"></span></div></div>
<div id="tooltipFour" class="tooltip-block"><div class="tooltip-four-bg"><span class="tooltipFourBtn"></span></div></div>
<div id="tooltipFive" class="tooltip-block"><div class="tooltip-five-bg"><span class="tooltipFiveBtn"></span></div></div>
<!--遮罩层-->
<%--<div id="shade" class="shade"><div class="tooltip-two"></div><div class="test1"></div></div>--%>
<!--遮罩层-->
<script src="${jsRoot}require.js"></script>
<script src="${jsRoot}lib/echarts/echarts.js"></script>
<script src="${jsRoot}biz/competency/empAttendance.js?reflesh=${reflesh}"></script>
</body>
</html>