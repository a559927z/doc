-- ----------------------------------------------------
-- 2月24日的调休用的项目组的调休，变更为正常出勤
-- ----------------------------------------------------

-- 把调休，调整为正常出勤 emp_attendance
UPDATE emp_attendance SET
cal_hour = '7.5',
checkwork_type_id =
        (SELECT checkwork_type_id FROM dim_checkwork_type t WHERE t.checkwork_type_name='正常出勤')
WHERE emp_attendance_id IN 
	(
		SELECT * FROM (
			SELECT t.emp_attendance_id FROM emp_attendance t
			WHERE
				t.emp_id = ( SELECT emp_id FROM dim_user t WHERE t.user_name_ch = '谢树义' )
				AND t.days = '2018-02-24'
				AND t.checkwork_type_id LIKE concat('%',(SELECT checkwork_type_id FROM dim_checkwork_type t WHERE t.checkwork_type_name='调休'),'%')
		) tt
	) 
;
-- emp_attendance_day
UPDATE emp_attendance_day SET
  hour_count = 7.5,
	checkwork_type_id = (SELECT checkwork_type_id FROM dim_checkwork_type t WHERE t.checkwork_type_name='正常出勤')
		WHERE		emp_id = ( SELECT emp_id FROM dim_user t WHERE user_name_ch = '谢树义' )
		AND days in ('2018-02-24')
;

-- emp_other_day
DELETE FROM emp_other_day
WHERE emp_other_day_id IN
	(
		SELECT emp_other_day_id FROM (
										SELECT emp_other_day_id FROM emp_other_day t
										WHERE
											t.emp_id = ( SELECT emp_id FROM dim_user t WHERE t.user_name_ch = '谢树义' )
											AND t.days in ('2018-02-24')
											AND t.checkwork_type_id  = (SELECT checkwork_type_id FROM dim_checkwork_type t WHERE t.checkwork_type_name='调休')
									) tt
	)
;


-- emp_attendance_month
CALL pro_fetch_emp_attendance_month ( "1", "SYSTEM", ( SELECT emp_id FROM dim_user t WHERE t.user_name_ch = '谢树义' ), 201802 );