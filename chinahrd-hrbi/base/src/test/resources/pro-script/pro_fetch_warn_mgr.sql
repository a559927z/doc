-- =======================================pro_fetch_warn_mgr============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证表存在：
-- 	organization_emp_relation
-- 	tmp_stewards_under
-- 	warn_rang
-- 	performance_change
-- 	dim_performance
-- 	emp_status
-- 	emp_overtime_day

-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_warn_mgr`;
CREATE PROCEDURE pro_fetch_warn_mgr(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE yearMonth int(6) DEFAULT date_format(DATE_ADD(now(), Interval 0 minute),'%Y%m');
	DECLARE orgId VARCHAR(32);

	DECLARE stewardsStr, underStr VARCHAR(5000);


	DECLARE roNum INT(4);
	DECLARE hPerNum INT(4);
	DECLARE lPerNum INT(4);
	DECLARE lNGNum INT(4);


-- 	DECLARE hPerLowId, hPerMaxId VARCHAR(32);


	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【管理者首页-预警-业务表】：数据刷新完成');

	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
			SELECT DISTINCT t.organization_id orgId FROM organization_emp_relation t WHERE t.customer_id = customerId;

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;
	-- 删除所有，重新写入。
	TRUNCATE tmp_stewards_under;
	DELETE FROM warn_mgr WHERE customer_id = customerId;
	
		-- 考察绩效周期
		SET @perCyc = (SELECT max_per FROM warn_rang WHERE type = 3);
		SET @perCycBegin = CONCAT(YEAR(DATE_ADD(CURDATE(),INTERVAL -@perCyc YEAR)),"12"); -- 201312
 		SET @perCycEnd = CONCAT(YEAR(DATE_ADD(CURDATE(),INTERVAL -1 YEAR)),"12");			-- 201412
-- 		SET @perCycEnd = (SELECT MAX(`YEAR_MONTH`) FROM performance_change WHERE customer_id = customerId);			-- 201412
		



		-- 考察加班周期
		SET @otCyc = (SELECT max_per FROM warn_rang WHERE type = 6);
		-- 考察周期里的假期数
		SET @holiDayNum = (SELECT count(1) FROM holiday 
										WHERE (type=1 or type=2 or type = 3)
											AND customer_id = customerId
											AND date BETWEEN DATE_ADD(CURDATE(),INTERVAL -@otCyc WEEK)
																	 AND DATE_ADD(CURDATE(),INTERVAL -1 DAY));
		-- 考察周期里的工作天数
		SET @workDayNum = (SELECT count(1) FROM holiday WHERE type=4  AND date BETWEEN DATE_ADD(CURDATE(),INTERVAL -@otCyc WEEK)
																	AND DATE_ADD(CURDATE(),INTERVAL -1 DAY));

		-- 平均加班时长
		SET @avgOtMax = (SELECT max_per FROM warn_rang WHERE type = 7);



	OPEN s_cur;
	WHILE done != 1 DO
		FETCH s_cur INTO orgId;
		IF NOT done THEN

			INSERT INTO tmp_stewards_under(
					organization_id, steward_emp_id, steward_emp_key, under_emp_id, under_emp_key)
			SELECT 
				DISTINCT t.organization_id, 
				t2.emp_id,  t2.emp_key, 
				t1.emp_id,  t1.emp_key
			FROM organization_emp_relation t 
			LEFT JOIN v_dim_emp t2 on t2.emp_id = t.emp_id AND t.customer_id = t2.customer_id AND t2.run_off_date IS NULL		-- 负责人
			LEFT JOIN v_dim_emp t1 on t1.emp_hf_id = t.emp_id AND t.customer_id = t1.customer_id AND t1.run_off_date IS NULL -- 直接下属
			WHERE t.organization_id = orgId;
				


			/*==============================================================*/
			/* 负责人Str、直接下属Str、离职风险Num	                        */
			/*==============================================================*/
					SELECT
								count(ro.emp_id) AS roNum,
								IFNULL(GROUP_CONCAT(DISTINCT t.steward_emp_key), NULL) AS stewardsStr,
								IFNULL(GROUP_CONCAT(DISTINCT t.under_emp_key) , NULL) AS underStr
					INTO roNum, stewardsStr, underStr
					FROM tmp_stewards_under t	
					-- 离职风险
					LEFT JOIN(
						SELECT t1.emp_id FROM dimission_risk t1 WHERE t1.customer_id = customerId
					) ro on ro.emp_id = t.under_emp_id
					WHERE t.organization_id = orgId
					;



			/*==============================================================*/
			/* 高绩没普升Num						                                    */
			/*==============================================================*/
					SELECT count(1) AS hPerNum
					INTO hPerNum
					FROM (
											SELECT t.performance_id, tmp.under_emp_id, t.rank_name
											FROM tmp_stewards_under tmp 			-- 直接下属
											INNER JOIN performance_change t on tmp.under_emp_id = t.emp_id AND  t.customer_id = customerId
													-- 高绩效范围
													AND t.performance_id IN
																				(
																					SELECT per.performance_id FROM dim_performance per
																					INNER JOIN warn_rang t1 on (per.performance_key = t1.min_per OR per.performance_key = t1.max_per) AND t1.type = 4
																					WHERE per.customer_id = customerId
																				)
													AND	t.`year_month` = @perCycBegin
											WHERE tmp.organization_id = orgId
					) tt
					INNER JOIN (
											SELECT t.performance_id, tmp.under_emp_id, t.rank_name
											FROM tmp_stewards_under tmp  			-- 直接下属
											INNER JOIN performance_change t on tmp.under_emp_id = t.emp_id AND  t.customer_id = customerId
													-- 高绩效范围
													AND t.performance_id IN
																				(
																					SELECT per.performance_id FROM dim_performance per
																					INNER JOIN warn_rang t1 on (per.performance_key = t1.min_per OR per.performance_key = t1.max_per) AND t1.type = 4
																					WHERE per.customer_id = customerId
																				)
													AND	t.`year_month` = @perCycEnd
											WHERE tmp.organization_id = orgId
					) ttt
					-- 周期里 高绩没变 职级没变
							on tt.under_emp_id = ttt.under_emp_id and tt.rank_name = ttt.rank_name
					INNER JOIN v_dim_emp t3 
					-- 周期里 职级没变
					on t3.rank_name = tt.rank_name AND t3.emp_id = tt.under_emp_id AND t3.customer_id = customerId
				;


			/*==============================================================*/
			/* 低绩岗位没变Num					                                    */
			/*==============================================================*/
					SELECT count(tt.performance_id) AS lPerNum
					INTO lPerNum
					FROM (
					
						SELECT t.performance_id, tmp.under_emp_id FROM
						tmp_stewards_under tmp					-- 直接下属
						INNER JOIN performance_change t on tmp.under_emp_id = t.emp_id AND  t.customer_id = customerId
								-- 低绩效范围
								AND t.performance_id IN
															(
																SELECT per.performance_id FROM dim_performance per
																INNER JOIN warn_rang t1 on (per.performance_key = t1.min_per OR per.performance_key = t1.max_per) AND t1.type = 5
																WHERE per.customer_id = customerId
															)
								AND	t.`year_month` = @perCycBegin
						WHERE tmp.organization_id = orgId
					) tt
					INNER JOIN 
						(
							SELECT t.performance_id, tmp.under_emp_id  FROM
							tmp_stewards_under tmp 					-- 直接下属
							INNER JOIN performance_change t on tmp.under_emp_id = t.emp_id AND  t.customer_id = customerId
									AND t.performance_id IN
																(
																	SELECT per.performance_id FROM dim_performance per
																	INNER JOIN warn_rang t1 on (per.performance_key = t1.min_per OR per.performance_key = t1.max_per) AND t1.type = 5
																	WHERE per.customer_id = customerId
																)
									AND	t.`year_month` = @perCycEnd
								WHERE tmp.organization_id = orgId 
							) ttt
						-- 周期里 低绩没变
					on tt.under_emp_id = ttt.under_emp_id
						-- 周期里 岗位没变
					WHERE tt.under_emp_id NOT IN (
								SELECT t2.emp_id FROM job_change t2 WHERE  t2.customer_id = customerId and t2.change_type != 3
							)
					;

			/*==============================================================*/
			/* 工作欠皆Num                                                  */
			/*==============================================================*/
					SELECT count(tt.emp_id) AS lNGNum
					INTO lNGNum
					FROM (
								SELECT 
										t.emp_id,
										t.organization_id,
										sum(t.hour_count) AS otHour, -- 加班周期里的加班时长
										@workDayNum AS workDayNum, 						-- 周期里的工作天数
										(IFNULL(sum(hour_count),0) / @workDayNum) AS avgOt	-- （近两周平均加班=近两周加班时数/近两周工作日天数）
								FROM emp_overtime_day t
								INNER JOIN tmp_stewards_under tmp 
												-- 直接下属
												on t.emp_id = tmp.under_emp_id
												and tmp.organization_id = orgId
								WHERE t.customer_id = customerId
									AND t.date BETWEEN DATE_ADD(CURDATE(),INTERVAL -@otCyc WEEK) AND CURDATE()
					 GROUP BY t.emp_id
					) tt
					WHERE tt.avgOt > 4
					;


		
			INSERT INTO warn_mgr (
				warn_mgr_id, customer_id, organization_id,
				steward_emp_key_str, under_emp_key_str, run_off_num,
				high_performance_num, low_performance_num, life_not_good_num
			) VALUES(
				replace(uuid(),'-',''), customerId, orgId, 
				stewardsStr, underStr, roNum,
				hPerNum, lPerNum, lNGNum
			);



		END IF;
	END WHILE;
	CLOSE s_cur;

	
		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【管理者首页-预警-业务表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'warn_mgr', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'warn_mgr', p_message, startTime, now(), 'sucess' );
		END IF;
 


END;
-- DELIMITER ;
-- 		TRUNCATE tmp_stewards_under;
-- 		TRUNCATE warn_mgr;
		CALL pro_fetch_warn_mgr('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');
