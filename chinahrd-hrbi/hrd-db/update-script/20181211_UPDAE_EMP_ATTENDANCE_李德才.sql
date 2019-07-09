drop procedure if exists pro_update_emp_attendance;
CREATE PROCEDURE pro_update_emp_attendance(
	in p_customer_id VARCHAR(32), in p_user_name_ch VARCHAR(32),
	in p_source VARCHAR(32), in p_target VARCHAR(32),
	in p_begin_day VARCHAR(32), in p_end_day VARCHAR(32),
	in p_ym INTEGER(6),
	in p_opt_day FLOAT(4,2) 
)
BEGIN

	set @session.empId = (SELECT emp_id FROM dim_user t WHERE t.user_name_ch = p_user_name_ch);
	set @session.source = (SELECT checkwork_type_id FROM dim_checkwork_type t WHERE t.checkwork_type_name=p_source);
	set @session.target = (SELECT checkwork_type_id FROM dim_checkwork_type t WHERE t.checkwork_type_name=p_target);

	-- emp_attendance
	UPDATE emp_attendance 
	SET cal_hour = '0,7.5',	checkwork_type_id = concat((SELECT @session.source), ',', (SELECT @session.target))
	WHERE emp_attendance_id IN
		(
			SELECT * FROM (
				SELECT t.emp_attendance_id FROM emp_attendance t
				WHERE t.emp_id = ( SELECT @session.empId )
				AND t.days BETWEEN p_begin_day AND p_end_day
				AND t.checkwork_type_id LIKE concat('%',(SELECT @session.source),'%')
			) tt
		)
	;

	-- emp_attendance_day
	UPDATE emp_attendance_day 
	SET hour_count = 0.0,	checkwork_type_id = (SELECT @session.target)
	WHERE	emp_id = (SELECT @session.empId) AND days BETWEEN p_begin_day AND p_end_day
	;

	-- emp_other_day
	UPDATE emp_other_day 
	SET checkwork_type_id = (SELECT @session.target)
	WHERE emp_other_day_id IN
		(
			SELECT emp_other_day_id FROM (
				SELECT emp_other_day_id FROM emp_other_day t
				WHERE t.emp_id = (SELECT @session.empId)
				AND t.days BETWEEN p_begin_day AND p_end_day
				AND t.checkwork_type_id  = (SELECT @session.target)
			) tt
		)
	;

	-- emp_vacation
	SET @session.annual = (SELECT annual FROM emp_vacation WHERE emp_id = (SELECT @session.empId)) + p_opt_day;
	UPDATE emp_vacation SET annual = (SELECT @session.annual) WHERE emp_id = (SELECT @session.empId);

	-- emp_attendance_month
	CALL pro_fetch_emp_attendance_month ( "1", "SYSTEM", (SELECT @session.empId), p_ym);


	COMMIT;
END;


-- ----------------------------------------------------
-- 11月份考勤中对应的2天，29日 30日 (正常出勤 --> 年假)
-- ----------------------------------------------------
/**
  * 入参说明
    ‘1’             固定  1代表中人网
    ‘李德才’         主角
    ‘正常出勤’       现在情况
    ‘年假’           将要调整后的情况
    ‘2018-11-29’    开始时间
    ‘2018-11-30’    结束时间
    201811          开始时间和结束时间的年月份
    -2.0            扣掉年假天数
 */
call pro_update_emp_attendance('1','李德才','正常出勤','年假','2018-11-29','2018-11-30', 201811, -2.0);

SELECT * from emp_vacation where emp_id = @session.empId;
