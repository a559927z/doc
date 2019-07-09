<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>添加员工</title>
    <style>
		.dsable {color: #c2c2c2}
    </style>
    
</head>
<body>
	<div>
		<form class="f1" action="${ctx }/user/addForm" method="post">
			<fieldset>
				<legend>添加员工</legend>
				<table width=100%>
					<tbody>
						<tr>
							<td class=“left” width=40% align="right"><label for="t1">员工编码：</label></td>
							<td class="right"><input type="text" id="t1" name="emp_key"></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label for="t2">英文名：</label></td>
							<td class="right"><input type="text" id="t2" name="user_name"></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label for="t3">姓名：</label></td>
							<td class="right"><input type="text" id="t3" name="user_name_ch"></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label for="t4">上级领导：</label></td>
							<td class="right"><input id="t4" type="password" name="emp_hf_key" /></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label for="t5">邮 箱：</label></td>
							<td class="right"><input type="text" id="t5" name="email"></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right">头像上 传：</td>
							<td><input type="file" id="t6" name="img_path" value="File1" /></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label for="1">正职还是兼职：</label></td>
							<td class="right">
								<input type="radio" id="t61" name="passtime_or_fulltime" value="f" />fulltime
								<input type="radio" id="t62" name="passtime_or_fulltime" value="p" />passtime
							</td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label for="t7">合同性质：</label></td>
							<td class="right"><input type="text" id="t7" name="contract"></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label for="t8">血型：</label></td>
							<td class="right"><input type="text" id="t8" name="blood"></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label class="dsable" for="t9">年龄：</label></td>
							<td class="right"><input type="text" id="t9" name="age"></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label class="dsable" for="t11">司龄：</label></td>
							<td class="right"><input type="text" id="t11" name="company_age"></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label class="dsable" for="t12">是否关键人才：</label></td>
							<td class="right"><input type="text" id="t12" name="is_key_talent"></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label for="t13">性别：</label></td>
							<td class="right">
								<input type="radio" id="t131" name="sex" value="m" />男
								<input type="radio" id="t132" name="sex" value="w" />女
							</td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label class="" for="t14">民族：</label></td>
							<td class="right"><input type="text" id="t14" name="nation"></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label for="t15">学历：</label></td>
							<td id="t15" class="right"></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label class="" for="t16">籍贯：</label></td>
							<td class="right"><input type="text" id="t16" name="native_place"></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label class="" for="t17">出生地：</label></td>
							<td class="right"><input type="text" id="t17" name="birth_place"></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label class="" for="t18">生日日期：</label></td>
							<td class="right"><input type="text" id="t18" name="birth_date"></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label class="" for="t19">证件号：</label></td>
							<td class="right"><input type="text" id="t19" name="national_id"></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right">证件类型：</td>
							<td>
								<select id="20" name="national_type">
									<option value="18位身份证号">18位身份证号</option>
									<option value="9位护照号">9位护照号</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label for="t20">是否已婚：</label></td>
							<td class="right">
								<input type="radio" id="t201" name="marry_status" value="0" />未婚
								<input type="radio" id="t202" name="marry_status" value="1" />已婚
							</td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label for="t21">政治面貌：</label></td>
							<td class="right">
								<input type="radio" id="t211" name="patty_name" value="群众" />群众
								<input type="radio" id="t212" name="patty_name" value="团员" />团员
								<input type="radio" id="t212" name="patty_name" value="党员" />党员
							</td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right">岗位：</td>
							<td><select id="t22" name="position_name"></select></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right">本机构：</td>
							<td><select id="t23" name="organization_name"></select></td>
						</tr>
<!-- 						<tr> -->
<!-- 							<td class=“left” width=40% align="right">父机构：</td> -->
<!-- 							<td><select id="t24" name="organization_parent_name"></select></td> -->
<!-- 						</tr> -->
						<tr>
							<td class=“left” width=40% align="right">主序列：</td>
							<td><select id="t25" name="sequence_name"></select></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right">子序列：</td>
							<td><select id="t26" name="sequence_sub_name"></select></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right">绩效：</td>
							<td id="t27"></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right">能力层级(大职级)：</td>
							<td><select id="t28" name="ability_name"></select></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right">职级(小职级)：</td>
							<td id="t29"></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right">职衔：</td>
							<td><select id="t30" name="job_title_name"></select></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right">离职日期：</td>
							<td><input type="text" id="t31" name="run_off_date" value="" /></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right">入职日期：</td>
							<td><input type="text" id="t32" name="entry_date" value="" /></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right">手机号码：</td>
							<td><input type="text" id="t33" name="tel_phone" value="" /></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right">QQ号码：</td>
							<td><input type="text" id="t34" name="qq" value="" /></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right">微信号码：</td>
							<td><input type="text" id="t35" name="wx_code" value="" /></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right">住址：</td>
							<td><input type="text" id="t36" name="address" value="" /></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right">合同单位：</td>
							<td><select id="t37" name="contract_unit"></select></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right">工作地点（市）：</td>
							<td><select id="t38" name="city_name"></select></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label for="t39">转正日期：</label></td>
							<td class="right"><input type="text" id="t39" name="regular_date" value="" /></td>
						</tr>
						<tr>
							<td class=“left” width=40% align="right"><label for="t40">是否主岗：</label></td>
							<td class="right">
								<input type="radio" id="t401" name="is_regular" value="1" />是
								<input type="radio" id="t402" name="is_regular" value="0" />否
							</td>
						</tr>
						
						
<!-- 						<tr> -->
<!-- 							<td class=“left” width=40% align="right"><label for="txtarea">简 介：</label></td> -->
<!-- 							<td><textarea id="txtarea"></textarea></td> -->
<!-- 						</tr> -->
<!-- 						<tr> -->
<!-- 							<td class=“left” width=40% align="right">兴 趣：</td> -->
<!-- 							<td><input type="checkbox" id="cbox1" name="dushu" -->
<!-- 								value="c1">读书 <input type="checkbox" id="cbox2" -->
<!-- 								name="yundong" value="c2">运动 <input type="checkbox" -->
<!-- 								id="cbox3" name="chihe" value="c3">吃喝</td> -->
<!-- 						</tr> -->

						<tr>
							<td class=“left” width=40% align="right" rowspan=2><input id="Submit1" type="submit" value="提 交" /></td>
							<td><input id="Reset1" type="reset" value="重 置" /></td>
						</tr>
					</tbody>
				</table>
			</fieldset>
		</form>
	</div>

	<script>
	    require(['jgGrid', 'underscore', 'utils'], function () {
	        var webRoot = G_WEB_ROOT;
	        var urls = {
	        	dimsValue: webRoot + '/user/getDimsValue.do',
	        	seqSubBySeqId: webRoot + '/user/getSeqSubBySeqId.do',
	        	abilityBySeqId: webRoot + '/user/getAbilityBySeqId.do'
	        }
	        $.post(urls.dimsValue, function (rs) {
	        	if(_.isEmpty(rs)){return;}
                var html="", degreeStr="degree", positionStr="position", organStr="organ",  
					sequenceStr="sequence", sequenceSubStr="sequenceSub", performanceStr="performance",
					abilityStr="ability", abilityLvStr="abilityLv";
                var degree=rs[degreeStr], position=rs[positionStr]; organ=rs[organStr]; 
					sequence=rs[sequenceStr]; sequenceSub=rs[sequenceSubStr]; performance=rs[performanceStr];
					ability=rs[abilityStr]; abilityLv=rs[abilityLvStr];
                $.each(rs["degree"], function(i, item){ html += "<input type='radio' id='t15"+i+"' name='"+degree+"' value='" + item.itemId + "' />"+item.itemName +"  "; });
                $("#t15").append(html); html = "";
                $.each(rs["position"], function(i, item){ html += "<option value="+item.k+">"+item.v+"</option><br/>"});
                $("#t22").append(html); html = "";
                $.each(organ, function(i, item){ html += "<option value="+item.k+">"+item.v+"</option><br/>"});
                $("#t23").append(html); html = "";
                $.each(rs["sequence"], function(i, item){ html += "<option value="+item.itemId+">"+item.itemName+"</option><br/>"});
                $("#t25").append(html); html = "";
				$.each(rs["sequenceSub"], function(i, item){ html += "<option value="+item.k+">"+item.v+"</option><br/>"});
                $("#t26").append(html); html = "";
				$.each(rs["performance"], function(i, item){ html += "<input type='radio' id='t27"+i+"' name='"+performance+"' value='" + item.k + "' />"+item.v +"  "; });
                $("#t27").append(html); html = "";
				$.each(ability, function(i, item){ html += "<option value="+item.k+">"+item.v+"</option><br/>"});
                $("#t28").append(html); html = "";
				$.each(abilityLv, function(i, item){ html += "<input type='radio' id='t27"+i+"' name='"+performance+"' value='" + item.k + "' />"+item.v +"  "; });
                $("#t29").append(html); html = "";
				$.each(rs["jobTitle"], function(i, item){ html += "<option value="+item.k+">"+item.v+"</option><br/>"});
                $("#t30").append(html); html = "";
                $.each(organ, function(i, item){ html += "<option value="+item.k+">"+item.v+"</option><br/>"});
                $("#t37").append(html); html = "";
                $.each(rs["city"], function(i, item){ html += "<option value="+item.k+">"+item.v+"</option><br/>"});
                $("#t38").append(html); html = "";
	        });
			function init(){ event(); }
			function event(){
				seqSubEvent();
			};
			function seqSubEvent(){
				$("#t25").change(function(){
					var seqId = $("#t25").val();
					$("#t26").empty();// 先清空第二个
					$.post(urls.seqSubBySeqId, {"seqId": seqId}, function (rs) {
						if(_.isEmpty(rs)){return;}
						var html = '';
						$.each(rs, function(i, item){ html += "<option value="+item.k+">"+item.v+"</option><br/>" });
						$("#t26").append(html); html ='';
					});
					$("#t28").empty();
					$.post(urls.abilityBySeqId, {"seqId": seqId}, function (rs) {
						if(_.isEmpty(rs)){return;}
						var html = '';
						$.each(rs, function(i, item){ html += "<option value="+item.k+">"+item.v+"</option><br/>" });
						$("#t28").append(html); html ='';
					});
				});
			}
			
			
			
			init();
	    });
	</script>
    
</body>
</html>