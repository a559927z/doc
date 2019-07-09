<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<html>
<head>
<%@include file="/WEB-INF/views/include/top.jsp"%>
<title>添加员工</title>
	<link rel="stylesheet" href="${ctx}/assets/css/biz/employee/talentSearch.css"/>
<link href="${ctx}/assets/css/datetime/datetimepicker.css" rel="stylesheet" media="screen"/>
<link rel="stylesheet" href="${ctx}/assets/css/select2/select2.min.css"/>
<link rel="stylesheet" href="${ctx}/assets/css/biz/employee/addEmp.css"/>
</head>
<body>
<input type="text"id="sdfsdfsd" name="" hidden="" value="${emp.positionId}"/>

<input type="text"id="newEmpId" name="newEmpId" hidden="true" value="${emp.empId}"/>
<input type="text"id="myEmpId" name="myEmpId" hidden="true" value="${empId}" />
<input type="text"id="vDimEmpId" name="vDimEmpId" hidden="true" value="${emp.vDimEmpId}" />
<input type="text"id="customerId" name="customerId" hidden="true" value="${emp.customerId}" />
	<div class="page-content">
		<div class="row column panel-main-header">
			<div class="col-xs-10">
				<div class="panel panel-default">
					<div class="panel-heading" id="section-1-">
						<h3 class="panel-title">
							<a class="accordion-toggle" data-toggle="collapse"
								href="#collapseOne"> <i class="icon-angle-down bigger-110"
								data-icon-hide="icon-angle-down"
								data-icon-show="icon-angle-right"></i> &nbsp;基本信息
							</a>
						</h3>
					</div>
					<div class="panel-collapse collapse in" id="collapseOne">
						<div class="panel-body page-main-header">
							<div class="base-info">
							<div class="row">
								<div class="col-sm-4 col-xs-12 normal-row">
									<div class="col-xs-12">
										<label>工号：</label> 
										<input type="text" id="empKey" name="empKey" value="${emp.empKey}"/>
										<span id="empKeySpan" class="span-info-error hide">有重复</span>
									</div>
									<div class="col-xs-12">
										<label>用户名：</label> 
										<input type="text" id="userName" name="userName" value="${emp.userName}"/>
										<span id="userNameSpan" class="span-info-error hide">有重复</span>
									</div>
									<div class="col-xs-12">
										<label>姓名：</label> 
										<input type="text" id="userNameCh" maxlength="5" name="userNameCh" value="${emp.userNameCh}" />
									</div>
									<div class="col-xs-12">
										<label>性别：</label>
										<select id="sex" name="sex" value="${emp.sex}">
											<option value="m">男</option>
											<option value="w">女</option>
										</select>
									</div>
									<div class="col-xs-12">
										<label class="col-xs-12 text-nowrap">出生日期：</label>
												<div id="birthD" class="pull-right input-group date form_date" data-date=""  data-link-field="birthDate" >
													<input class="form-control" type="text" value="${emp.birthDate}" readonly>
													<span class="input-group-addon"><span class="glyphicon glyphicon-remove icon-remove"></span></span>
													<span class="input-group-addon"><span class="glyphicon glyphicon-calendar icon-calendar"></span></span>
												</div>
												<input type="hidden" name="birthDate" id="birthDate" value="${emp.birthDate}" /><br/>
									</div>
									<div class="col-xs-12">
										<label>学历：</label>
										<select	id="degree" name="degree" value="${emp.degreeId}">
										</select>
									</div>
									<div class="col-xs-12">
										<label>婚姻状况：</label>
										<select id="marryStatus" name="marryStatus" value="${emp.marryStatus}">
											<option value="1">已婚</option>
											<option selected="selected" value="0">未婚</option>
										</select>
									</div>
<!-- 									<div class="col-xs-12"> -->
<!-- 										<label>子女数量：</label> -->
<!-- 										<input type="text" id="childNum" name="childNum"/> -->
<!-- 									</div> -->
								</div>
								<div class="col-sm-4 col-xs-12 normal-row">
									<div class="col-xs-12">
										<label>籍贯：</label>
										<input type="text" id="nativePlace" name="nativePlace" value="${emp.nativePlace}"/>
										
									</div>
									<div class="col-xs-12">
										<label>民族：</label>
										<select	id="nation" id="nation" name="nation" value="${emp.nation}">
										</select>
									</div>
									<div class="col-xs-12">
										<label>政治面貌：</label>
										<select	id="pattyName" name="pattyName" value="${emp.pattyName}">
										</select>
									</div>
									<div class="col-xs-12">
										<label>证件类型：</label>
										<select	id="nationalType" name="nationalType" value="${emp.nationalType}">
										</select>
									</div>
									<div class="col-xs-12">
										<label>证件号码：</label>
										<input type="text" id="nationalId" name="nationalId" value="${emp.nationalId}"/>
									</div>
									<div class="col-xs-12">
										<label>户口所在地：</label>
										<input type="text" id="birthPlace" name="birthPlace" value="${emp.birthPlace}"/>
									</div>
								</div>
								<div class="col-sm-4 col-xs-12 normal-row">
									<div class="col-xs-12">
										<label>现住址：</label>
										<input type="text" id="address" name="address" value="${emp.address}"/>
									</div>
<!-- 									<div class="col-xs-12"> -->
<!-- 										<label>紧急联系人：</label> -->
<!-- 										<input type="text" id="contactPersonName" name="contactPersonName"/> -->
<!-- 									</div> -->
<!-- 									<div class="col-xs-12"> -->
<!-- 										<label>联系方式：</label> -->
<!-- 										<input type="text" id="contactTelPhone" name="contactTelPhone"/> -->
<!-- 									</div> -->
<!-- 									<div class="col-xs-12"> -->
<!-- 										<label>关系：</label> -->
<!-- 										<input type="text" id="contactCall" name="contactCall"/> -->
<!-- 									</div> -->
									<div class="col-xs-12">
										<label>手机：</label>
										<input type="text" id="telPhone" maxlength="11" name="telPhone" value="${emp.telPhone}"/>
									</div>
									<div class="col-xs-12">
										<label>QQ号：</label>
										<input type="text" id="qq" name="qq" value="${emp.qq}"/>
									</div>
									<div class="col-xs-12">
										<label>邮箱：</label>
										<input type="text" id="email" name="email" value="${emp.email}"/>
									</div>
									<div class="col-xs-12">
										<label>微信：</label>
										<input type="text" id="wxCode" name="wxCode" value="${emp.wxCode}"/>
									</div>
									<div class="col-xs-12">
										<label>国籍：</label>
										<input type="text" id="country" name="country" value="${emp.country}"/>
									</div>
								</div>
							</div>
							</div>
							<div class="work-info">
							<div class="row">
								<div class="col-sm-4 col-xs-12 normal-row">
									<div class="col-xs-12"><label>合同单位：</label>
										<input type="text" id="contractUnit" name="contractUnit" value="${emp.contractUnit}"/>
									</div>
									<div class="col-xs-12"><label>工作地点：</label>
										<!-- 对应emp_city -->
										<select id="workPlace" name="workPlace" value="${emp.workPlaceId}"></select>
									</div>
									<div class="col-xs-12"><label>工作单位：</label>
										<select id="organizationParentName" name="organizationParentName" value="${emp.organizationParentId}"></select>
									</div>
									<div class="col-xs-12"><label>工作部门：</label>
										<select id="organName" name="organName" value="${emp.organId}"></select>
									</div>
									<div class="col-xs-12"><label>任职岗位：</label>
										<select id="positionName" name="positionName" value="${emp.positionId}"></select>
									</div>
									<div class="col-xs-12"><label>合同性质：</label>
										<select id="contract" name="contract" value="${emp.contract}"></select>
									</div>
									<div class="col-xs-12"><label>员工状态：</label>
										<select id="passtimeOrFulltime" name="passtimeOrFulltime" value="${emp.passtimeOrFulltime}">
											<option value="f">全职</option>
											<option value="p">兼职</option>
										</select>
									</div>
									<div class="col-xs-12"><label>正职：</label>
										<select id="isRegular" name="isRegular" value="${emp.isRegular}">
											<option value="1">是</option>
											<option value="0">否</option>
										</select>
									</div>
								</div>
								<div class="col-sm-4 col-xs-12 normal-row">
									<div class="col-xs-12"><label>职位主序列：</label>
										<select id="sequenceName" name="sequenceName" value="${emp.mainseqId}"></select>
									</div>
									<div class="col-xs-12"><label>职位子序列：</label>
										<select id="sequenceSubName" name="sequenceSubName" value="${emp.subseqId}"></select>
									</div>
									<div class="col-xs-12"><label>职位层级：</label>
										<select id="abilityName" name="abilityName" value="${emp.abilityId}"></select>
									</div>
									
									<div class="col-xs-12"><label>职级：</label>
										<select id="abilityLv" name="abilityLv" value="${emp.abilitylvId}"></select>
									</div>
									<div class="col-xs-12"><label class="text-nowrap" title="主子序列层级职级">主子序列层级职级：</label>
										<input type="text" disabled="disabled" id='rankName' name="rankName" value="" value="1233"/>
									</div>
									 <div class="col-xs-12"><label>职衔：</label>
									 	<select id="jobTitleName" name="jobTitleName" value="${emp.jobTitleId}"></select>
									 </div>
								</div>
								<div class="col-sm-4 col-xs-12 normal-row">
									<div class="col-xs-12">
										<label calss="col-xs-12 text-nowrap">直接上司：</label>
										<div class="pull-right input-group" >
											<input type="text" id="empHfId" name="empHfId" value="" hidden="true" value="${emp.empHfId}"/>
											<input type="text" id="empHfKey" name="empHfKey" value="" hidden="true" value="${emp.empHfKey}"/>
											<input type="text" class="form-control" id="empHfName" name="empHfName" disabled="disabled" value="${emp.empHfName}"/>
											<span class="input-group-addon"><span id="searchEmp" class="glyphicon icon-search"></span></span>
										</div>
									</div>
									<div class="col-xs-12">
										<label class="col-xs-12 text-nowrap">入职日期：</label>
										<div id="indate" class="pull-right input-group date form_date" data-date="${emp.entryDate}"  data-link-field="entryDate" >
											<input class="form-control" type="text" value="${emp.entryDate}" readonly>
											<span class="input-group-addon"><span class="glyphicon glyphicon-remove icon-remove"></span></span>
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar icon-calendar"></span></span>
										</div>
										<input type="hidden" name="entryDate" id="entryDate" value="${emp.entryDate}" /><br/>
									</div>
									<div class="col-xs-12">
										<label class="col-xs-12 text-nowrap">转正日期：</label>
											<div id="turndate" class="pull-right input-group date form_date" data-date=""  data-link-field="regularDate" >
												<input class="form-control" type="text" value="${emp.regularDate}" readonly>
												<span class="input-group-addon"><span class="glyphicon glyphicon-remove icon-remove"></span></span>
												<span class="input-group-addon"><span class="glyphicon glyphicon-calendar icon-calendar"></span></span>
											</div>
											<input type="hidden" name="regularDate" id="regularDate" value="${emp.regularDate}" /><br/>
									</div>
									<div class="col-xs-12 offDate hide">
										<label class="col-xs-12 text-nowrap">离职日期</label>
											<div id="offDate" class="pull-right input-group date form_date" data-date=""  data-link-field="runOffDate" >
												<input class="form-control" type="text" value="${emp.runOffDate}" readonly>
												<span class="input-group-addon"><span class="glyphicon glyphicon-remove icon-remove"></span></span>
												<span class="input-group-addon"><span class="glyphicon glyphicon-calendar icon-calendar"></span></span>
											</div>
											<input type="hidden" name="runOffDate" id="runOffDate" value="${emp.runOffDate}" /><br/>
									</div>
									<div class="col-xs-12"><label>应聘渠道：</label>
										<select id="applyChannel" name="applyChannel" value="${emp.applyChannel}"></select>
									</div>
								</div>
							</div>
							</div>
						</div>
					</div>
					
					<div class="panel-footer hight-footer" id="section-1-">
                    	<button id="onEmpInfOkBtnId" class="okBtn btn-margin">提交</button>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading" id="section-2-">
						<h3 class="panel-title">
							<a class="accordion-toggle" data-toggle="collapse"
								href="#collapseTwo"> <i class="icon-angle-down bigger-110"
								data-icon-hide="icon-angle-down"
								data-icon-show="icon-angle-right"></i> &nbsp;账号信息
							</a>
						</h3>
					</div>
					<div class="panel-collapse collapse in" id="collapseTwo">
						<div class="panel-body page-main-header">
						<input id="userUserId" value="${emp.user.userId}" hidden="hidden">
						<input id="userCustomerId" value="${emp.user.customerId}" hidden="hidden">
						<input id="userEmpId" value="${emp.user.empId}" hidden="hidden">
							<div class="row">
								<div class="col-sm-4 col-xs-12 normal-row">
									<div class="col-xs-12">
										<label>账号：</label> 
										<input type="text" id="userUserName" value="${emp.user.userName}"/>
										<span id="userUserNameSpan" class="span-info-error hide">有重复</span>
									</div>
									<div class="col-xs-12">
										<label>姓名：</label> 
										<input type="text" id="userUserNameCh" maxlength="5" value="${emp.user.userNameCh}"/>
									</div>
									<div class="col-xs-12">
										<label>编码：</label> 
										<input type="text" id="userUserKey" value="${emp.user.userKey}"/>
									</div>
									<div class="col-xs-12">
										<label>密码：</label> 
										<c:if test="${emp != null and emp.user != null and emp.user.userId != null and emp.user.userId !='' and emp.user.userId != 'null'}">
											<input type="text" id="userPassword" disabled="disabled"  value="●●●●●●●●●●"/>
											<input type="checkbox"  id="changepwd" title="重置为：123456">重置密码</input>
										</c:if>
										<c:if test="${emp.user.userId == null or emp.user.userId == null or emp.user.userId == ''}">
											<input type="text" id="userPassword" disabled="disabled"  value="123456"/>
										</c:if>
									</div>
<!-- 									<div class="col-xs-12"> -->
<!-- 										<label>锁定:</label> -->
<%-- 										<select id="isLock" value="${emp.user.isLock}"> --%>
<!-- 											<option value="0">未锁定</option> -->
<!-- 											<option value="1">锁定</option> -->
<!-- 										</select> -->
<!-- 									</div> -->
								</div>
								
								<div class="col-sm-4 col-xs-12 normal-row">
									<div class="col-xs-12">
										<label>电子邮件：</label> 
										<input type="text" id="userEmail" value="${emp.user.email}" />
									</div>
									<div class="col-xs-12">
										<label>描述：</label>
										<textarea id="userNote" rows="2" cols="20" maxlength="200">${emp.user.note}</textarea>
									</div>
								</div>
								
							</div>
						</div>
					</div>
					<div class="panel-footer hight-footer" id="section-1-">
                    	<button id="onEmpUserOkBtnId" class="okBtn btn-margin">提交</button>
					</div>
					</div>
				</div>
			</div>

		</div>


	<div class="modal fade popup-modal" id="submitModal" tabindex="-1" role="dialog" aria-labelledby="submitLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<div class="modal-title" id="submitLabel">提示</div>
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				</div>
	            <div class="modal-body">
	                <p id="submitMsg" class="modal-body-p"></p>
	            </div>
			</div>
		</div>
	</div>
	<div class="modal fade popup-modal" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="searchLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<div class="modal-title" id="searchLabel">选择上级</div>
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				</div>
	            <div class="modal-body">
			                
					<div class="row column normal-row" id="firstRows">
						<div class="search-header normal-row">
							<div class="col-md-3 col-sm-2 col-xs-0"></div>
							<div class="col-md-6 col-sm-8 col-xs-12">
								<div class="row column normal-row">
									<div class="col-sm-12 col-xs-12 img-row">
										<span class="search-title">人才搜索</span>
									</div>
								</div>
								<div class="row column normal-row">
									<div class="col-sm-12 col-xs-12">
										<div class="input-group">
											<input type="text" class="form-control" id="firstSearchTxt" placeholder="请输入员工ID/姓名">
											<span class="input-group-btn">
												<button class="btn btn-search" id="firstSearchBtn" type="button">
													<%-- <i class="icon-search"></i> --%>
													搜索
												</button>
											</span>
										</div><!-- /input-group -->
									</div>
								</div>
								<div class="row column normal-row">
									<div class="col-sm-12 col-xs-12">
										<span class="hint">
											* 姓名支持模糊查询
										</span>
									</div>
								</div>
							</div>
							<div class="col-md-3 col-sm-2 col-xs-0"></div>
						</div>
					</div>
					<div class="row column hide normal-row" id="lastRows">
						<div class="col-xs-12 normal-row">
							<div class="panel panel-default">
								<div class="index-common-title bottom-title">
									<div class="index-common-title-left bottom-left"></div>
									<div class="index-common-title-text bottom-text">
										<span class="title-text" id="titleText"></span>的搜索结果
									</div>
								</div>
								<%-- <div class="panel-heading normal-row">
									<h3 class="panel-title">
										搜索结果列表
									</h3>
								</div> --%>
								<div class="bottom-div bottom-row">
									<div class="content-row">
										<div class="row head-row">
											<div class="col-md-4 col-sm-8 col-xs-12 search-row">
												<div class="input-group">
													<input type="text" class="form-control" id="lastSearchTxt" placeholder="请输入员工ID/姓名">
													<span class="input-group-btn">
														<button class="btn btn-light btn-search" id="lastSearchBtn" type="button">
															<%-- <i class="icon-search"></i> --%>
															搜索
														</button>
													</span>
												</div>
											</div>
											<div class="col-md-4 col-sm-4 col-xs-12 info-row">
												<span class="hint">
													* 姓名支持模糊查询
												</span>
											</div>
										</div>
										<div><span class="search-title-left-return"><a id="searchReturn">返回</a></span></div>
										<div class="row normal-row">
											<div class="col-xs-12 grid-row" id="searchGridPanel">
												<table id="searchGridTable" class="borderless"></table>
												<div id="searchGridPager"></div>
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

	</div>
	<script type="text/javascript" src="${jsRoot}biz/employee/modifyEmp.js"></script>

</body>
</html>