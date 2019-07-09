-- 打卡机
DROP PROCEDURE if exists `pro_fetch_emp_attendance`;
CREATE PROCEDURE `pro_fetch_emp_attendance`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_error INTEGER DEFAULT 0;
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-出勤记录】';
	DECLARE yesterday VARCHAR(10) DEFAULT fn_getYesterday_li();

	SET @正常上班 = (SELECT checkwork_type_id from dim_checkwork_type WHERE curt_name = 1);

		-- 先确定清一清昨天数据
		DELETE FROM emp_attendance where days = yesterday;
		INSERT INTO emp_attendance
		SELECT
			replace(UUID(), '-',''), customerId, t.emp_key, t.emp_id,
			empiname user_name_ch,
			CASE WHEN zd IS NULL AND rectime IS NOT NULL THEN concat(recdate, ' ', rectime) ELSE
				CASE WHEN zd IS NOT NULL AND rectime IS NOT NULL THEN concat(recdate, ' ', zd) ELSE NULL END
			END clock_in_am,
			null AS clock_out_am,
			null AS clock_in_pm,
			CASE WHEN zd IS NOT NULL AND rectime IS NOT NULL THEN  concat(recdate, ' ', rectime) ELSE
				CASE WHEN zd IS NULL AND rectime IS NOT NULL THEN NULL END
			END clock_out_pm,
			null AS opt_in,
			null AS opt_out,
			null AS opt_reason,
			null AS cal_hour,
			t.organization_id,
			@正常上班 as checkwork_type_id, -- 默认给出，正常上班
			null AS note,
			recdate days,
			date_format(recdate,'%Y%m'),
			null
		FROM
			(
					SELECT
						cardid, empiname, recdate, zd, rectime,
						IF (@d = cardid AND @e = recdate , @rn :=@rn + 1 ,@rn := 1) rn,		-- 5：初始和定义rn值
						@d := cardid, @e := recdate
					FROM
					(
						SELECT cardid, empiname, recdate, zd, rectime
						FROM
							(
								SELECT
									cardid, empiname, recdate, rectime,
									IF ( @a = cardid AND @b = recdate, @c, NULL ) zd,				-- 2: cardid 和 recdate为一组，取全局变量就是上一次值，
									@a := cardid ,@b := recdate ,@c := rectime							-- 3: 赋值，对第二次是再重新赋值
								FROM
									`mup-source`.source_emp_attendance a,
									(SELECT @a := NULL ,@b := NULL ,@c := NULL ,@d := NULL ,@e := NULL ,@rn := NULL) b  -- 1:初始化变量
								ORDER BY cardid, recdate, rectime
							) a																				-- 4：封装为a表
					) a
					ORDER BY cardid, recdate, rectime DESC
			) a
		INNER JOIN v_dim_emp t ON a.empiname = t.user_name_ch
		WHERE rn = 1 AND a.recdate = yesterday
		;
END;


-- 出勤记录
drop procedure if exists pro_fetch_emp_attendance_day;
CREATE  PROCEDURE `pro_fetch_emp_attendance_day`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-出勤记录】';
	DECLARE yesterday VARCHAR(10) DEFAULT fn_getYesterday_li();

	-- 正常上班
	SET @checkworkTypeId = (SELECT checkwork_type_id from dim_checkwork_type WHERE curt_name = 1);

	LB_INSERT:BEGIN
		INSERT INTO emp_attendance_day(
			emp_attendance_day_id, customer_id,
			emp_key, emp_id, user_name_ch, hour_count, theory_hour_count, organization_id, checkwork_type_id, days, year_months
		)
		SELECT
			emp_attendance_day_id, customerId,
			a.emp_key, a.emp_id, a.user_name_ch, a.hour_count, a.theory_hour_count, a.organization_id, a.checkwork_type_id, a.days, a.year_months
		FROM `mup-source`.source_emp_attendance_day a
		WHERE a.days = yesterday 
		;

		-- 补回在不考勤表里没有员工
		INSERT INTO emp_attendance_day (
			emp_attendance_day_id, customer_id, emp_key, emp_id, user_name_ch, hour_count, theory_hour_count, organization_id, checkwork_type_id, days, year_months )
		SELECT
			fn_getId(), vde.customer_id, vde.emp_key, vde.emp_id, vde.user_name_ch, 0, t1.hour_count, vde.organization_id, @checkworkTypeId, yesterday, DATE_FORMAT(yesterday, '%Y%m')
		FROM v_dim_emp vde
		INNER JOIN theory_attendance t1 ON t1.days = yesterday
		INNER JOIN days t2 ON t2.days = t1.days AND t2.is_work_flag = 1
		WHERE NOT EXISTS ( SELECT 1 FROM emp_attendance_day ead WHERE ead.days = yesterday AND ead.emp_id = vde.emp_id );
	END LB_INSERT;
END;

-- 出勤记录-zrw（个性化）
DROP PROCEDURE if exists `pro_fetch_emp_attendance_day_zrw`;
CREATE PROCEDURE `pro_fetch_emp_attendance_day_zrw`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_error INTEGER DEFAULT 0;
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-出勤记录】';
	DECLARE yesterday VARCHAR(10) DEFAULT date_sub(curdate(),interval 1 day);

-- 	DECLARE showIndex INTEGER DEFAULT (SELECT show_index from procedure_info where pro_name = 'pro_fetch_emp_normal_attendance_day');

  START TRANSACTION;

-- 			TRUNCATE TABLE emp_attendance;
			DELETE FROM emp_attendance where days = yesterday;

			INSERT INTO emp_attendance
			SELECT
				replace(UUID(), '-',''), customerId, t.emp_key, t.emp_id,
				empiname user_name_ch,
				CASE WHEN zd IS NULL AND rectime IS NOT NULL THEN concat(recdate, ' ', rectime) ELSE
					CASE WHEN zd IS NOT NULL AND rectime IS NOT NULL THEN concat(recdate, ' ', zd) ELSE NULL END
				END clock_in_am,
				null clock_out_am,
				null clock_in_pm,
				CASE WHEN zd IS NOT NULL AND rectime IS NOT NULL THEN  concat(recdate, ' ', rectime) ELSE
					CASE WHEN zd IS NULL AND rectime IS NOT NULL THEN NULL END
				END clock_out_pm,
				null opt_in,
				null opt_out,
				null opt_reason,
				null cal_hour,
				t.organization_id,
				'b90bb95e3c01413b80899b49ba13392e' as checkwork_type_id, -- 正常上班
				null note,
				recdate days,
				date_format(recdate,'%Y%m'),
				null
			FROM
				(
						SELECT
							cardid, empiname, recdate, zd, rectime,
							IF (@d = cardid AND @e = recdate , @rn :=@rn + 1 ,@rn := 1) rn,		-- 5：初始和定义rn值
							@d := cardid, @e := recdate
						FROM
						(
							SELECT cardid, empiname, recdate, zd, rectime
							FROM
								(
									SELECT
										cardid, empiname, recdate, rectime,
										IF ( @a = cardid AND @b = recdate, @c, NULL ) zd,				-- 2: cardid 和 recdate为一组，取全局变量就是上一次值，
										@a := cardid ,@b := recdate ,@c := rectime							-- 3: 赋值，对第二次是再重新赋值
									FROM
										`mup-source`.source_emp_attendance a,
										(SELECT @a := NULL ,@b := NULL ,@c := NULL ,@d := NULL ,@e := NULL ,@rn := NULL) b  -- 1:初始化变量
									ORDER BY cardid, recdate, rectime
								) a																				-- 4：封装为a表
						) a
						ORDER BY cardid, recdate, rectime DESC
				) a
			INNER JOIN v_dim_emp t ON a.empiname = t.user_name_ch
			WHERE rn = 1 AND a.recdate = yesterday
			;



		SET @checkworkTypeId = (SELECT checkwork_type_id from dim_checkwork_type WHERE curt_name = 1);

				LB_INSERT:BEGIN
	-- TODO 真实是删除当日，不是全表
-- 				TRUNCATE TABLE emp_attendance_day;
			DELETE FROM emp_attendance_day where days = yesterday;

					INSERT INTO emp_attendance_day(
						emp_attendance_day_id, customer_id,
						emp_key, emp_id, user_name_ch, hour_count, theory_hour_count, organization_id, checkwork_type_id, days, year_months
					)
					SELECT replace(uuid(),'-',''), customerId, a.emp_key, a.emp_id, a.user_name_ch,

							CASE
							WHEN a.clock_in_am <= concat(a.days, ' 10:00:00') AND a.clock_out_pm >= concat(a.days, ' 18:00:00') THEN 7.5
							ELSE
								CASE
								WHEN a.clock_in_am < concat(a.days, ' 10:00:00') AND a.clock_out_pm >= concat(a.days, ' 13:30:00')
								THEN TIMESTAMPDIFF(MINUTE, concat(a.days, " 09:00:00"), a.clock_out_pm) / 60 - 1.5
								WHEN a.clock_in_am < concat(a.days, ' 10:00:00') AND a.clock_out_pm >= concat(a.days, ' 12:00:00')
								THEN 3
								WHEN a.clock_in_am < concat(a.days, ' 10:00:00') AND a.clock_out_pm < concat(a.days, ' 12:00:00')
								THEN TIMESTAMPDIFF(MINUTE, concat(a.days, " 09:00:00"), a.clock_out_pm) / 60

								WHEN a.clock_in_am > concat(a.days, ' 13:30:00') AND a.clock_out_pm >= concat(a.days, ' 18:00:00')
								THEN TIMESTAMPDIFF(MINUTE, a.clock_in_am, a.clock_out_pm) / 60
								WHEN a.clock_in_am > concat(a.days, ' 12:00:00') AND a.clock_in_am <= concat(a.days, ' 13:30:00') AND a.clock_out_pm >= concat(a.days, ' 18:00:00')
								THEN 4.5
								WHEN a.clock_in_am > concat(a.days, ' 10:00:00') AND a.clock_in_am <= concat(a.days, ' 12:00:00') AND a.clock_out_pm >= concat(a.days, ' 18:00:00')
								THEN TIMESTAMPDIFF(MINUTE, a.clock_in_am, concat(a.days, ' 18:00:00')) / 60 - 1.5

								WHEN a.clock_in_am > concat(a.days, ' 13:30:00') AND a.clock_out_pm < concat(a.days, ' 18:00:00') AND a.clock_out_pm > concat(a.days, ' 13:30:00')
								THEN TIMESTAMPDIFF(MINUTE, a.clock_in_am, a.clock_out_pm) / 60
								WHEN a.clock_in_am > concat(a.days, ' 12:00:00') AND a.clock_out_pm < concat(a.days, ' 18:00:00') AND a.clock_out_pm > concat(a.days, ' 13:30:00')
								THEN TIMESTAMPDIFF(MINUTE, concat(a.days, ' 13:30:00'), a.clock_out_pm) / 60
								WHEN a.clock_in_am > concat(a.days, ' 10:00:00') AND a.clock_out_pm < concat(a.days, ' 18:00:00') AND a.clock_out_pm > concat(a.days, ' 13:30:00')
								THEN TIMESTAMPDIFF(MINUTE, a.clock_in_am, a.clock_out_pm) / 60 - 1.5
								ELSE 0
								END
							END as hour_count,

							t1.hour_count AS theory_hour_count, a.organization_id, @checkworkTypeId, a.days, a.`year_month`
					FROM emp_attendance a
					INNER JOIN theory_attendance t1 on a.days = t1.days
					INNER JOIN days t2 ON a.days = t2.days AND t2.is_work_flag = 1
-- 					ON DUPLICATE KEY UPDATE
-- 							customer_id = t.customer_id,
-- 							emp_key = t.emp_key,
-- 							emp_id = t.emp_id,
-- 							user_name_ch = t.user_name_ch,
-- 							hour_count = (TIMESTAMPDIFF(MINUTE ,clock_in, clock_out) / 60) -1,
-- 							theory_hour_count = t1.hour_count,
-- 							organization_id = t.organization_id,
-- 							checkwork_type_id = t.checkwork_type_id,
-- 							days = t.days
					WHERE a.days = yesterday AND a.clock_in_am IS NOT NULL AND a.clock_out_pm IS NOT null
					;
				-- 处理：不在打卡机里的员工，把他们的名单都加入 day表
				INSERT INTO emp_attendance_day (
					emp_attendance_day_id, customer_id, emp_key, emp_id, user_name_ch, hour_count, theory_hour_count, organization_id, checkwork_type_id, days, year_months )
				SELECT
					REPLACE (uuid(), '-', ''), vde.customer_id, vde.emp_key, vde.emp_id, vde.user_name_ch, 0, t1.hour_count, vde.organization_id, @checkworkTypeId, yesterday, DATE_FORMAT(yesterday, '%Y%m')
				FROM v_dim_emp vde
				INNER JOIN theory_attendance t1 ON t1.days = yesterday
				INNER JOIN days t2 ON t2.days = t1.days AND t2.is_work_flag = 1
				WHERE NOT EXISTS ( SELECT 1 FROM emp_attendance_day ead WHERE ead.days = yesterday AND ead.emp_id = vde.emp_id );

				END LB_INSERT;


		SELECT fn_insert_log(customerId, optUserId, 'pro_fetch_emp_attendance_day', p_message, startTime, p_error);

	  IF p_error = 1 THEN
			ROLLBACK;
		ELSE
			COMMIT;
		END IF;
-- 			truncate table `mup-source`.source_emp_attendance; -- 这可由etl下次执行时再清空
END;


-- 加班记录
drop procedure if exists pro_fetch_emp_overtime_day;
CREATE  PROCEDURE `pro_fetch_emp_overtime_day`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-加班记录】';

	DECLARE ym VARCHAR(10) DEFAULT fn_getYM();

    INSERT INTO emp_overtime_day(
		emp_overtime_day_id,customer_id,emp_key,emp_id,user_name_ch,hour_count,organization_id,checkwork_type_id,days,`year_month`
	)
	SELECT
		emp_overtime_day_id,customerId,emp_id,emp_key,user_name_ch,hour_count,organization_id,checkwork_type_id,days,`year_month`
	FROM `mup-source`.source_emp_overtime_day
    ON DUPLICATE KEY UPDATE
		hour_count=a.hour_count,
		organization_id=a.organization_id,
		days=a.days;
END;

-- 其他考勤记录
drop procedure if exists pro_fetch_emp_other_day;
CREATE  PROCEDURE `pro_fetch_emp_other_day`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-其他考勤记录】';

      INSERT INTO emp_other_day (
			emp_other_day_id, customer_id,emp_key,emp_id,user_name_ch,hour_count,organization_id,checkwork_type_id,days,`year_months`
	  )
	  SELECT
	  		emp_other_day_id, customer_id,emp_key,emp_id,user_name_ch,hour_count,organization_id,checkwork_type_id,days,`year_months`
	  FROM `mup-source`.source_emp_other_day a
      ON DUPLICATE KEY UPDATE
		user_name_ch=a.user_name_ch,
		hour_count=a.hour_count,
		organization_id=a.organization_id,
		checkwork_type_id=a.checkwork_type_id;
END;


-- 出勤记录-月
drop procedure if exists pro_fetch_emp_attendance_month;
CREATE PROCEDURE `pro_fetch_emp_attendance_month`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_emp_id VARCHAR(32))
BEGIN
	DECLARE nowYm INTEGER(6) DEFAULT fn_getYM();
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-考勤记录】';

	-- 每晚更新月度表
	IF p_emp_id IS NOT NULL THEN
		-- 指定员工
			DELETE FROM emp_attendance_month where `YEAR_MONTH` =nowYm AND emp_id=p_emp_id;
			SET @atId = (SELECT checkwork_type_id FROM dim_checkwork_type WHERE curt_name = 1);
			SET @otId = (SELECT checkwork_type_id FROM dim_checkwork_type WHERE curt_name = 5);
			INSERT INTO emp_attendance_month (
				emp_attendance_month_id, customer_id,
				organization_id, emp_id, user_name_ch,
				attendance_type_id,
				at_hour,
				overtime_type_id,
				ot_hour,
				th_hour,
				`year_month`
			)
			SELECT
				fn_getId(), customerId,
				a.organization_id, a.emp_id, a.user_name_ch,
				@atId,
				sum(a.hour_count) AS at_hour,
				@otId,
				(SELECT sum(b.hour_count) FROM emp_overtime_day b where a.emp_id = b.emp_id and a.year_months = b.year_months) AS ot_hour,
				(SELECT sum(hour_count) FROM theory_attendance WHERE YEAR_MONTHs = nowYm) th_hour,
				nowYm AS ym
			FROM emp_attendance_day a
			WHERE a.year_months = nowYm and a.emp_id = p_emp_id
			GROUP BY date_format(a.days, '%Y%m'), a.organization_id, a.emp_id, a.user_name_ch
			;
	ELSE
		-- 不指定员工
			DELETE from emp_attendance_month where `YEAR_MONTH` =nowYm;
			SET @atId = (SELECT checkwork_type_id from dim_checkwork_type WHERE curt_name = 1);
			SET @otId = (SELECT checkwork_type_id from dim_checkwork_type WHERE curt_name = 5);
			INSERT INTO emp_attendance_month (
				emp_attendance_month_id, customer_id,
				organization_id, emp_id, user_name_ch,
				attendance_type_id,
				at_hour,
				overtime_type_id,
				ot_hour,
				th_hour,
				`year_month`
			)
			SELECT
				fn_getId(), customerId,
				a.organization_id, a.emp_id, a.user_name_ch,
				@atId,
				sum(a.hour_count) AS at_hour,
				@otId,
				(SELECT sum(b.hour_count) FROM emp_overtime_day b where a.emp_id = b.emp_id and a.year_months = b.year_months) AS ot_hour,
				(SELECT sum(hour_count) FROM theory_attendance WHERE YEAR_MONTHs = nowYm) th_hour,
				nowYm AS ym
			FROM emp_attendance_day a
			WHERE a.year_months = nowYm
			GROUP BY date_format(a.days, '%Y%m'), a.organization_id, a.emp_id, a.user_name_ch
			;
	END IF;

END;
-- 调用例子
-- call pro_fetch_emp_attendance_month(1, 'jxzhang',null,201805);

-- 应出勤记录
-- CREATE PROCEDURE `pro_fetch_theory_attendance`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
-- BEGIN
-- 	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
-- 	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
-- 	DECLARE p_message VARCHAR(10000) DEFAULT '【应出勤记录】';

-- 	insert into theory_attendance
-- 	select theory_attendance_id,hour_count,days,`year`
-- 	from soure_theory_attendance a on duplicate key
-- 	update
-- 	hour_count=a.hour_count;

-- END;