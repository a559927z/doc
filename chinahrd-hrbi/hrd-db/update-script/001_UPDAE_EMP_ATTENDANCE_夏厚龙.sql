-- ----------------------------------------------------
-- 2月份考勤中对应的调休5天，9日 11日 12日 13日 14日改为预支年假
-- ----------------------------------------------------

-- 把调休，调整为预支年假 emp_attendance
UPDATE emp_attendance SET
cal_hour = '0,7.5',
checkwork_type_id = concat(
        (SELECT checkwork_type_id FROM dim_checkwork_type t WHERE t.checkwork_type_name='正常出勤'),
        ',',
        (SELECT checkwork_type_id FROM dim_checkwork_type t WHERE t.checkwork_type_name='年假'))
WHERE emp_attendance_id IN 
	(
		SELECT * FROM (
			SELECT t.emp_attendance_id FROM emp_attendance t
			WHERE
				t.emp_id = ( SELECT emp_id FROM dim_user t WHERE t.user_name_ch = '夏厚龙' )
			AND t.days BETWEEN '2018-02-01' AND '2018-02-31'
			AND t.checkwork_type_id LIKE concat('%',(SELECT checkwork_type_id FROM dim_checkwork_type t WHERE t.checkwork_type_name='调休'),'%')
		) tt
	)
;
-- emp_attendance_day
UPDATE emp_attendance_day SET
  hour_count = 0.0,
	checkwork_type_id = (SELECT checkwork_type_id FROM dim_checkwork_type t WHERE t.checkwork_type_name='年假')
		WHERE		emp_id = ( SELECT emp_id FROM dim_user t WHERE user_name_ch = '夏厚龙' )
		AND days in ('2018-02-09','2018-02-11','2018-02-12','2018-02-13','2018-02-14')
;

-- emp_other_day
UPDATE emp_other_day SET
checkwork_type_id =
        (SELECT checkwork_type_id FROM dim_checkwork_type t WHERE t.checkwork_type_name='年假')
WHERE emp_other_day_id IN
	(
		SELECT emp_other_day_id FROM (
			SELECT emp_other_day_id FROM emp_other_day t
			WHERE
				t.emp_id = ( SELECT emp_id FROM dim_user t WHERE t.user_name_ch = '夏厚龙' )
        AND t.days in ('2018-02-09','2018-02-11','2018-02-12','2018-02-13','2018-02-14')
			AND t.checkwork_type_id  = (SELECT checkwork_type_id FROM dim_checkwork_type t WHERE t.checkwork_type_name='调休')
		) tt
	)
;

-- 添加年假
INSERT INTO emp_vacation (emp_id, customer_id, emp_key, user_name_ch, annual, refresh)VALUE
	(
		(SELECT emp_id FROM dim_user t WHERE t.user_name_ch = '夏厚龙' ),
		"1",
		(SELECT t.user_key FROM dim_user t WHERE t.user_name_ch = '夏厚龙' ),
		"夏厚龙",
		"-5.0",
		CURDATE()
	)
;

-- emp_attendance_month
CALL pro_fetch_emp_attendance_month ( "1", "SYSTEM", ( SELECT emp_id FROM dim_user t WHERE t.user_name_ch = '夏厚龙' ), 201802 );
