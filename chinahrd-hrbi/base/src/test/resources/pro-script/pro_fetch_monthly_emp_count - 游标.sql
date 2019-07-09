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
		DECLARE beginSUM, endSUM INT(6);
		DECLARE monthCount, monthCountSum DOUBLE(6, 2);
-- 		DECLARE act, unAct INT(1);
		DECLARE actNum, unActNum INT(4);
		
		DECLARE done INT DEFAULT 0;
		DECLARE fullPath VARCHAR(32);
		DECLARE s_cur CURSOR FOR 
				SELECT organization_full_path fullPath from monthly_emp_count WHERE customer_id = customerId AND `YEAR_MONTH` = yearMonth;
		DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

		IF be THEN
			IF exist<=0 THEN
				-- 月初
				INSERT INTO monthly_emp_count(monthly_emp_id, customer_id, organization_id, organization_full_path, month_begin, `YEAR_MONTH`) 
				SELECT replace(UUID(),'-',''), customerId, t.organization_id, t.full_path organization_full_path,
								IFNULL(tt.month_begin,0) month_begin, 201305
				FROM dim_organization t
				LEFT JOIN (
					SELECT DISTINCT
						de.organization_id,
						count(1) month_begin
					FROM
						v_dim_emp de
					WHERE
						de.customer_id = customerId
-- 2013.05.01 <= 2015-01-01 AND (2015-02-01 > 2015-01-01 or NULL)
-- 作用：找出当前指定时间的在职员工。（员工入职日期少于当前时间，并且员工的离职日期大于当前时间或员工没有离职）
					AND de.entry_date <= '2013-05-01' AND ( de.run_off_date > '2013-05-01' or de.run_off_date IS NULL) 
					GROUP BY de.organization_id
				) tt on tt.organization_id = t.organization_id
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

	ELSE-- -------------------------上为月头、下为月末

			OPEN s_cur;
			WHILE done != 1 DO
				FETCH s_cur INTO fullPath;
				-- 月末
					UPDATE monthly_emp_count 
					SET month_end = (
								SELECT DISTINCT count(1) month_end FROM dim_emp de
								INNER JOIN emp_position_relation t1 ON t1.emp_id = de.emp_id
										AND t1.customer_id = de.customer_id
								INNER JOIN dim_position t2 ON t1.position_id = t2.position_id
										AND t2.customer_id = de.customer_id
										AND now() >= de.effect_date
										AND de.expiry_date IS NULL
										AND de.enabled = 1
								INNER JOIN dim_organization t3 ON t3.organization_id = t2.organization_id
										AND t3.customer_id = de.customer_id
										AND now() >= de.effect_date
										AND de.expiry_date IS NULL
										AND de.enabled = 1
								WHERE
									de.customer_id = customerId
								AND de.entry_date <= p_nowTime AND ( de.run_off_date > p_nowTime or de.run_off_date IS NULL) 
								AND t3.full_path = fullPath
								AND de.expiry_date IS NULL
								AND de.enabled = 1
						)
					WHERE organization_full_path = fullPath AND customer_id = customerId AND `YEAR_MONTH` = yearMonth;

				-- 月末-子孙
				SELECT sum(month_end) INTO endSUM FROM monthly_emp_count t
				INNER JOIN (
					SELECT t1.organization_id FROM v_dim_organization t1 WHERE locate(fullPath, t1.full_path ) AND t1.customer_id = customerId
				) t2 on t.organization_id = t2.organization_id
				WHERE customer_id = customerId AND `YEAR_MONTH` = yearMonth;

				UPDATE monthly_emp_count
				SET month_end_sum = endSUM
				WHERE organization_full_path = fullPath AND customer_id = customerId AND `YEAR_MONTH` = yearMonth;
	
				-- 月度总数
				SELECT (month_begin + month_end) / 2 , (month_begin_sum + month_end_sum) / 2 INTO monthCount, monthCountSum 
				FROM monthly_emp_count t WHERE organization_full_path = fullPath AND customer_id = customerId AND `YEAR_MONTH` = yearMonth;
				UPDATE monthly_emp_count
				SET month_count = monthCount, month_count_sum = monthCountSum
				WHERE organization_full_path = fullPath AND customer_id = customerId AND `YEAR_MONTH` = yearMonth;

				-- 	流失总人数
				SET actNum = (
						SELECT count(1) FROM run_off_record t
						INNER JOIN  dim_emp de
						INNER JOIN emp_position_relation t1 ON t1.emp_id = de.emp_id
								AND t1.customer_id = de.customer_id
						INNER JOIN dim_position t2 ON t1.position_id = t2.position_id
								AND t2.customer_id = de.customer_id
								AND now() >= de.effect_date
								AND de.expiry_date IS NULL
								AND de.enabled = 1
-- 						INNER JOIN dim_organization t3 ON t3.organization_id = t2.organization_id
-- 								AND t3.customer_id = de.customer_id
-- 								AND now() >= de.effect_date
-- 								AND de.expiry_date IS NULL
-- 								AND de.enabled = 1
-- 								AND t3.full_path = 'ZRW_SZ'
						INNER JOIN (
							SELECT t1.organization_id FROM v_dim_organization t1 WHERE locate(fullPath , t1.full_path ) AND t1.customer_id = customerId
						) t3 ON t3.organization_id = t2.organization_id
						INNER JOIN dim_run_off t4 ON t.run_off_id = t4.run_off_id
								AND t4.type = 1
						WHERE
								de.customer_id = customerId
								AND de.run_off_date = t.run_off_date 
								AND	t.run_off_date BETWEEN firstDay AND p_nowTime 
-- 								AND t.run_off_date <= p_nowTime
				);
				SET unActNum = (
						SELECT count(1) FROM run_off_record t
						INNER JOIN  dim_emp de
						INNER JOIN emp_position_relation t1 ON t1.emp_id = de.emp_id
								AND t1.customer_id = de.customer_id
						INNER JOIN dim_position t2 ON t1.position_id = t2.position_id
								AND t2.customer_id = de.customer_id
								AND now() >= de.effect_date
								AND de.expiry_date IS NULL
								AND de.enabled = 1
						INNER JOIN (
							SELECT t1.organization_id FROM v_dim_organization t1 WHERE locate('ZRW', t1.full_path ) AND t1.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
						) t3 ON t3.organization_id = t2.organization_id
						INNER JOIN dim_run_off t4 ON t.run_off_id = t4.run_off_id
								AND t4.type = 2
						WHERE
								de.customer_id = customerId
								AND de.run_off_date = t.run_off_date 
								AND	t.run_off_date BETWEEN firstDay AND p_nowTime 
-- 								AND t.run_off_date <= p_nowTime
				);

				UPDATE monthly_emp_count SET act_count = actNum, un_act_count = unActNum
				WHERE organization_full_path = fullPath AND customer_id = customerId AND `YEAR_MONTH` = yearMonth;

-- 				UPDATE monthly_emp_count
-- 				SET run_off_count = (
-- 						SELECT count(1) FROM run_off_record t
-- 						INNER JOIN  dim_emp de
-- 						INNER JOIN emp_position_relation t1 ON t1.emp_id = de.emp_id
-- 								AND t1.customer_id = de.customer_id
-- 						INNER JOIN dim_position t2 ON t1.position_id = t2.position_id
-- 								AND t2.customer_id = de.customer_id
-- 								AND now() >= de.effect_date
-- 								AND de.expiry_date IS NULL
-- 								AND de.enabled = 1
-- 						INNER JOIN dim_organization t3 ON t3.organization_id = t2.organization_id
-- 								AND t3.customer_id = de.customer_id
-- 								AND now() >= de.effect_date
-- 								AND de.expiry_date IS NULL
-- 								AND de.enabled = 1
-- 						WHERE
-- 							de.run_off_date = t.run_off_date AND de.customer_id = customerId
-- 						AND	t.run_off_date BETWEEN firstDay AND p_nowTime 
-- 				)
-- 				WHERE organization_full_path = fullPath AND customer_id = customerId AND `YEAR_MONTH` = yearMonth;


			END WHILE;
			CLOSE s_cur;
 		END IF;

END;
-- DELIMITER ;