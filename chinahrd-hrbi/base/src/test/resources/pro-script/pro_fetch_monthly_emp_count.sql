#=======================================================pro_fetch_monthly_emp_count=======================================================
-- 'demo','jxzhang'
-- CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-08-01', 1)
-- CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-08-31', 0)
-- 必须完成 dim_emp、dim_organization
-- 事件执行时必须先月头再月末参数
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_monthly_emp_count`;
CREATE PROCEDURE pro_fetch_monthly_emp_count(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_nowTime datetime, in p_mBegin_or_mEnd INT(1))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE yearMonth int(6) DEFAULT date_format(DATE_ADD(p_nowTime, Interval 0 minute),'%Y%m');

		-- 这个是月未调用时才用到，作用：月头第一天p_nowTime= '2015-08-31'， firstDay = '2015-08-01'
		DECLARE firstDay DATETIME DEFAULT  DATE_ADD(DATE_ADD(p_nowTime, Interval -1 month), INTERVAL 1 DAY);

		DECLARE be INT(1) DEFAULT p_mBegin_or_mEnd;	
		DECLARE exist INT(1) DEFAULT (SELECT count(1) FROM monthly_emp_count where `YEAR_MONTH` = yearMonth);
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000);
		
		DECLARE beginSUM, endSUM, actCountSum, unActCountSum INT(6);
		DECLARE monthCount, monthCountSum DOUBLE(6, 2);

		
		DECLARE done INT DEFAULT 0;
		DECLARE fullPath VARCHAR(32);
		DECLARE s_cur CURSOR FOR 
				SELECT organization_full_path fullPath from monthly_emp_count 
				WHERE customer_id = customerId AND `YEAR_MONTH` = yearMonth
				ORDER BY `YEAR_MONTH` ASC , organization_full_path ASC
				;

		DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET p_error = 1;

		IF be THEN
			IF exist<=0 THEN
			START TRANSACTION;
-- 				-- 月头的，先操作删除当前月。（这里是为了方便添加假数据先删除再添加使用，生产环境这句是多余的。）
-- 				DELETE FROM monthly_emp_count WHERE `YEAR_MONTH` = yearMonth;

				-- 月初
				INSERT INTO monthly_emp_count(
						monthly_emp_id, customer_id, organization_id, organization_full_path, month_begin, month_p_begin, month_f_begin, `YEAR_MONTH`
				) 
				SELECT replace(UUID(),'-',''), customerId, t.organization_id, t.full_path organization_full_path,
								IFNULL(tt.month_begin,0) month_begin, 
								IFNULL(ttp.month_begin,0) mbp, 
								IFNULL(ttf.month_begin,0) mbf, 
								yearMonth
				FROM dim_organization t
				LEFT JOIN (
					SELECT DISTINCT
						de.organization_id, count(1) month_begin
					FROM v_dim_emp de
					WHERE
						de.customer_id = customerId
-- 2013.05.01 <= 2015-01-01 AND (2015-02-01 > 2015-01-01 or NULL)
-- 作用：找出当前指定时间的在职员工。（员工入职日期少于当前时间，并且员工的离职日期大于当前时间或员工没有离职）
					AND de.entry_date <= p_nowTime AND ( de.run_off_date > p_nowTime or de.run_off_date IS NULL) 

					GROUP BY de.organization_id
				) tt on tt.organization_id = t.organization_id

				LEFT JOIN (
					SELECT DISTINCT
						de.organization_id, count(1) month_begin
					FROM v_dim_emp de
					WHERE
						de.customer_id = customerId
					AND de.entry_date <= p_nowTime AND ( de.run_off_date > p_nowTime or de.run_off_date IS NULL) 
					AND de.passtime_or_fulltime = 'p'
					GROUP BY de.organization_id
				) ttp on tt.organization_id = t.organization_id

				LEFT JOIN (
					SELECT DISTINCT
						de.organization_id, count(1) month_begin
					FROM v_dim_emp de
					WHERE
						de.customer_id = customerId
					AND de.entry_date <= p_nowTime AND ( de.run_off_date > p_nowTime or de.run_off_date IS NULL) 
					AND de.passtime_or_fulltime = 'f'
					GROUP BY de.organization_id
				) ttf on tt.organization_id = t.organization_id

				ORDER BY t.full_path;

			
			OPEN s_cur;
				WHILE done != 1 DO
					FETCH s_cur INTO fullPath;
						-- 月初-子孙
						SELECT sum(month_begin) INTO beginSUM FROM monthly_emp_count t
						INNER JOIN (
							SELECT t1.organization_id FROM v_dim_organization t1 WHERE locate(fullPath, t1.full_path ) AND t1.customer_id = customerId
						) t2 on t.organization_id = t2.organization_id
						WHERE t.`YEAR_MONTH` = yearMonth AND customer_id = customerId
						GROUP BY t.`YEAR_MONTH`;



						UPDATE monthly_emp_count SET month_begin_sum = beginSUM
						WHERE organization_full_path = fullPath AND customer_id = customerId AND `YEAR_MONTH` = yearMonth;
				END WHILE;
			CLOSE s_cur;
		END IF;


		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【月度总人数-", yearMonth, "月初】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'monthly_emp_count.organization_id、month_begin、month_begin_sum、year_month', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				SET p_message =  concat("【月度总人数-", yearMonth, "月初】：数据刷新完成");
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'monthly_emp_count.organization_id、month_begin、month_begin_sum、year_month', p_message, startTime, now(), 'sucess' );
		END IF;

	ELSE-- -------------------------上为月头、下为月末

		START TRANSACTION;
			-- 月末
			REPLACE INTO monthly_emp_count(
					monthly_emp_id, customer_id, organization_id, organization_full_path, 
					month_begin, month_end, month_count, 
					month_begin_sum, month_end_sum, month_count_sum,
					act_count, un_act_count,
					`YEAR_MONTH`) 
			SELECT 
					mec.monthly_emp_id, customerId, tt1.organization_id, tt1.organization_full_path,
					mec.month_begin, tt1.month_end, 0, 
					mec.month_begin_sum,0,0,
					0,0,
					yearMonth
			FROM monthly_emp_count mec
			LEFT JOIN (
					SELECT t.organization_id, customerId, t.full_path organization_full_path,
									IFNULL(tt.month_end,0) month_end, yearMonth
					FROM dim_organization t
					LEFT JOIN (
						SELECT DISTINCT
							de.organization_id,
							count(1) month_end
						FROM
							v_dim_emp de
						WHERE
							de.customer_id = customerId
						AND de.entry_date <= p_nowTime  AND ( de.run_off_date > p_nowTime or de.run_off_date IS NULL) 
						GROUP BY de.organization_id
					) tt on tt.organization_id = t.organization_id
					ORDER BY t.full_path
			) tt1 on tt1.organization_id = mec.organization_id
			WHERE mec.`YEAR_MONTH` = yearMonth
			ORDER BY mec.organization_full_path
			;

			OPEN s_cur;
			WHILE done != 1 DO
				FETCH s_cur INTO fullPath;

				-- 月末-子孙
				SELECT sum(month_end) INTO endSUM FROM monthly_emp_count t
				INNER JOIN (
					SELECT t1.organization_id FROM v_dim_organization t1 WHERE locate(fullPath, t1.full_path ) AND t1.customer_id = customerId
				) t2 on t.organization_id = t2.organization_id
				WHERE customer_id = customerId AND `YEAR_MONTH` = yearMonth;

				UPDATE monthly_emp_count
				SET month_end_sum = endSUM
				WHERE organization_full_path = fullPath AND customer_id = customerId AND `YEAR_MONTH` = yearMonth;
	
				-- 月度总数(本部门，子孙)
				SELECT (month_begin + month_end) / 2 , (month_begin_sum + month_end_sum) / 2 INTO monthCount, monthCountSum 
				FROM monthly_emp_count t WHERE organization_full_path = fullPath AND customer_id = customerId AND `YEAR_MONTH` = yearMonth;
				UPDATE monthly_emp_count
				SET month_count = monthCount, month_count_sum = monthCountSum
				WHERE organization_full_path = fullPath AND customer_id = customerId AND `YEAR_MONTH` = yearMonth;

			END WHILE;
			CLOSE s_cur;

			-- 	流失总人数
			REPLACE INTO monthly_emp_count(
				monthly_emp_id, customer_id, organization_id, organization_full_path, 
				month_begin, month_end, month_count, month_begin_sum, month_end_sum, month_count_sum,
				act_count, un_act_count,
				`YEAR_MONTH`
			)
			SELECT 
					t4.monthly_emp_id, t4.customer_id, t4.organization_id, t4.organization_full_path, 
					t4.month_begin, t4.month_end, t4.month_count, t4.month_begin_sum, t4.month_end_sum, t4.month_count_sum,
					IFNULL(tt.actCount, 0), IFNULL(tt1.unActCount, 0),
					yearMonth
			FROM monthly_emp_count t4 
			LEFT JOIN (
					SELECT count(1) actCount, t2.organization_id FROM run_off_record t1 
					left JOIN v_dim_emp t2 on t2.emp_id = t1.emp_id AND t1.customer_id = t2.customer_id
					left JOIN dim_run_off t3 on t1.run_off_id = t3.run_off_id AND t3.customer_id = t1.customer_id
					where 
						t1.run_off_date BETWEEN firstDay AND p_nowTime 
					AND t3.type = 1
					GROUP BY t2.organization_id
				) tt on tt.organization_id = t4.organization_id
			LEFT JOIN (
					SELECT count(1) unActCount, t2.organization_id FROM run_off_record t1 
					left JOIN v_dim_emp t2 on t2.emp_id = t1.emp_id AND t1.customer_id = t2.customer_id
					left JOIN dim_run_off t3 on t1.run_off_id = t3.run_off_id AND t3.customer_id = t1.customer_id
					where 
						t1.run_off_date BETWEEN firstDay AND p_nowTime 
					AND t3.type = 2
					GROUP BY t2.organization_id
				) tt1 on tt1.organization_id = t4.organization_id
			WHERE t4.`year_month` = yearMonth;


			-- 子孙 流失总人数
			SET done = 0;
			OPEN s_cur;
				WHILE done != 1 DO
					FETCH s_cur INTO fullPath;

						SELECT sum(act_count), sum(un_act_count) INTO actCountSum, unActCountSum  FROM monthly_emp_count t
						INNER JOIN (
							SELECT t1.organization_id FROM v_dim_organization t1 WHERE locate(fullPath, t1.full_path ) AND t1.customer_id = customerId
						) t2 on t.organization_id = t2.organization_id
						WHERE t.`YEAR_MONTH` = yearMonth AND customer_id = customerId
						GROUP BY t.`YEAR_MONTH`
						ORDER BY `YEAR_MONTH` ASC , organization_full_path ASC
						;

						UPDATE monthly_emp_count SET act_count_sum = actCountSum, un_act_count_sum = unActCountSum
						WHERE organization_full_path = fullPath AND customer_id = customerId AND `YEAR_MONTH` = yearMonth
						ORDER BY `YEAR_MONTH` ASC , organization_full_path ASC
						;

				END WHILE;
			CLOSE s_cur;


			IF p_error = 1 THEN  
					ROLLBACK;  
					SET p_message = concat("【月度总人数-", yearMonth, "月末】：数据刷新失败。操作用户：", optUserId);
					INSERT INTO db_log 
					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'monthly_emp_count.month_end、month_end_sum、month_count、month_count_sum、act_count、un_act_count', p_message, startTime, now(), 'error' );
			ELSE  
					COMMIT;  
					SET p_message =  concat("【月度总人数-", yearMonth, "月末】：数据刷新完成");
					INSERT INTO db_log 
					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'monthly_emp_count.month_end、month_end_sum、month_count、month_count_sum、act_count、un_act_count', p_message, startTime, now(), 'sucess' );
			END IF;


		END IF;

END;
-- DELIMITER ;