-- ----------------------------------------------------
-- 事假变更为预支年假，时间为11日，12日，13日，14日，22日，23日，24日，共7天
-- ----------------------------------------------------

-- 把事假，调整为正常出勤 emp_attendance
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
				t.emp_id = ( SELECT emp_id FROM dim_user t WHERE t.user_name_ch = '张孝成' )
			AND t.days BETWEEN '2018-02-01' AND '2018-02-31'
			AND t.checkwork_type_id LIKE concat('%',(SELECT checkwork_type_id FROM dim_checkwork_type t WHERE t.checkwork_type_name='事假'),'%')
		) tt
	)
;
-- emp_attendance_day
UPDATE emp_attendance_day SET
	checkwork_type_id = (SELECT checkwork_type_id FROM dim_checkwork_type t WHERE t.checkwork_type_name='年假')
		WHERE		emp_id = ( SELECT emp_id FROM dim_user t WHERE user_name_ch = '张孝成' )
		AND days in ('2018-02-11','2018-02-12','2018-02-13','2018-02-14','2018-02-22','2018-02-23','2018-02-24')
;
-- emp_attendance_month
CALL pro_fetch_emp_attendance_month ( "1", "SYSTEM", ( SELECT emp_id FROM dim_user t WHERE t.user_name_ch = '张孝成' ), 201802 );

-- emp_other_day
UPDATE emp_other_day SET
checkwork_type_id =
        (SELECT checkwork_type_id FROM dim_checkwork_type t WHERE t.checkwork_type_name='年假')
WHERE emp_other_day_id IN
	(
		SELECT emp_other_day_id FROM (
			SELECT emp_other_day_id FROM emp_other_day t
			WHERE
				t.emp_id = ( SELECT emp_id FROM dim_user t WHERE t.user_name_ch = '张孝成' )
        AND t.days in ('2018-02-11','2018-02-12','2018-02-13','2018-02-14','2018-02-22','2018-02-23','2018-02-24')
			AND t.checkwork_type_id  = (SELECT checkwork_type_id FROM dim_checkwork_type t WHERE t.checkwork_type_name='事假')
		) tt
	)
;

-- 添加年假
INSERT INTO emp_vacation (emp_id, customer_id, emp_key, user_name_ch, annual, refresh)VALUE
(
  (SELECT emp_id FROM dim_user t WHERE t.user_name_ch = '张孝成' ),
  "1",
  (SELECT t.user_key FROM dim_user t WHERE t.user_name_ch = '张孝成' ),
  "张孝成",
  "-7.0",
  CURDATE()
)
;