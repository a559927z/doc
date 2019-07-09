-- 开启时事
SET GLOBAL event_scheduler = 1;


-- =======================================每晚=======================================
-- =======================================每晚=======================================
-- =======================================每晚=======================================
-- =======================================每晚=======================================
-- =======================================每晚=======================================

		-- 基础表********************************************************************
					
					-- DELIMITER $$	
					DROP EVENT
						IF EXISTS evn_refresh_dim_user_lock;
						CREATE EVENT
						IF NOT EXISTS evn_refresh_dim_user_lock ON SCHEDULE EVERY 1 DAY STARTS CONCAT(CURDATE(), ' ', '01:00:00') ON COMPLETION PRESERVE DO
					--		BEGIN
								UPDATE dim_user 
								SET is_lock = 1,
										modify_user_id = 'DB-Event',
										modify_time = NOW()
								WHERE user_id IN (
									SELECT user_id FROM (
										SELECT user_id FROM dim_user WHERE DATE_SUB(CURDATE(), INTERVAL 7 DAY) > create_time
										AND is_lock = 0 AND `password` = '123456'
									) t 
								)
					--		END$$
					--DELIMITER ;


					-- 机构类别（每天查看有没有新数据）
					DROP EVENT
					IF EXISTS evn_refresh_dim_organization_type;
					CREATE EVENT
					IF NOT EXISTS evn_refresh_dim_organization_type ON SCHEDULE EVERY 1 DAY STARTS CONCAT(CURDATE(), ' ', "01:20:00") ON COMPLETION PRESERVE DO
						CALL pro_fetch_dim_organization_type ('b5c9410dc7e4422c8e0189c7f8056b5f', 'system-evn-execut');

					-- 机构（每天查看有没有新的机构添加）
					DROP EVENT
					IF EXISTS evn_init_organization;
					CREATE EVENT
					IF NOT EXISTS evn_init_organization ON SCHEDULE EVERY 1 DAY STARTS CONCAT(CURDATE(), ' ', "01:30:00") ON COMPLETION PRESERVE DO
						-- BEGIN
							CALL pro_init_organization ('demo');
						-- END;

					-- v_dim_emp（每天晚上获取员工最新数据）
					DROP EVENT
					IF EXISTS evn_refresh_v_dim_emp;
					CREATE EVENT
					IF NOT EXISTS evn_refresh_v_dim_emp ON SCHEDULE EVERY 1 DAY STARTS CONCAT(CURDATE(), ' ', "02:00:00") ON COMPLETION PRESERVE DO
						CALL pro_fetch_v_dim_emp ('b5c9410dc7e4422c8e0189c7f8056b5f', 'system-event-execut');
						

					-- 员工编制数、机构负责人（每天晚上获取最新数据）
					DROP EVENT
					IF EXISTS evn_refresh_budget_emp_number;
					CREATE EVENT
					IF NOT EXISTS evn_refresh_budget_emp_number ON SCHEDULE EVERY 1 DAY STARTS CONCAT(CURDATE(), ' ', "03:00:00") ON COMPLETION PRESERVE DO
						CALL pro_fetch_budget_emp_number ('b5c9410dc7e4422c8e0189c7f8056b5f', 'system-event-execut', now());
						
					-- 机构BP员工关系（每天晚上获取最新数据）
					DROP EVENT
					IF EXISTS evn_refresh_organization_bpemp_relation;
					CREATE EVENT
					IF NOT EXISTS evn_refresh_organization_bpemp_relation ON SCHEDULE EVERY 1 DAY STARTS CONCAT(CURDATE(), ' ', "03:05:00") ON COMPLETION PRESERVE DO
						CALL pro_fetch_organization_bpemp_relation ('b5c9410dc7e4422c8e0189c7f8056b5f', 'system-event-execut');

		-- 流失率********************************************************************
		
					-- 流失记录表，同时更新是否有预警过（每天晚上获取最新数据）
					DROP EVENT
					IF EXISTS evn_refresh_budget_emp_number;
					CREATE EVENT
					IF NOT EXISTS evn_refresh_budget_emp_number ON SCHEDULE EVERY 1 DAY STARTS CONCAT(CURDATE(), ' ', "03:05:00") ON COMPLETION PRESERVE DO
						CALL pro_fetch_run_off_record('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');
						
						
					-- 月度员工数:月末(每天晚上更新当前人数，每月的第一天才会插入新的月头数据)
					DROP EVENT
					IF EXISTS evn_update_monthly_emp_count;
					CREATE EVENT
					IF NOT EXISTS evn_update_monthly_emp_count ON SCHEDULE EVERY 1 DAY STARTS  CONCAT(CURDATE(), ' ', "03:10:00") ON COMPLETION PRESERVE DO
						CALL pro_fetch_monthly_emp_count ('b5c9410dc7e4422c8e0189c7f8056b5f', '3cfd3db15ffc4c119e344e82eb8cbb19', CURDATE(), 0);
						

		-- 人均效益********************************************************************

					-- 人均效益(事件调用)
					DROP EVENT
					IF EXISTS evn_add_factfte;
					CREATE EVENT
					IF NOT EXISTS evn_add_factfte ON SCHEDULE EVERY 1 DAY STARTS CONCAT(CURDATE(), ' ', "04:00:00") ON COMPLETION PRESERVE DO
						CALL pro_fetch_factfte (DATE_ADD(now(), INTERVAL - 1 MONTH), 'demo' );


		-- 人才剖像********************************************************************


					-- 工作异动(每天晚上获取最新数据)
					DROP EVENT
					IF EXISTS evn_refresh_job_change;
					CREATE EVENT
					IF NOT EXISTS evn_refresh_job_change ON SCHEDULE EVERY 1 DAY STARTS  CONCAT(CURDATE(), ' ', "03:15:00")  ON COMPLETION PRESERVE DO
						CALL pro_fetch_job_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');

					-- 360测评(每天晚上获取最新数据)
					DROP EVENT
					IF EXISTS evn_refresh_360;
					CREATE EVENT
					IF NOT EXISTS evn_refresh_360 ON SCHEDULE EVERY 1 DAY STARTS  CONCAT(CURDATE(), ' ', "03:20:00")  ON COMPLETION PRESERVE DO
						CALL pro_fetch_360('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');

						

		-- 管理者首页********************************************************************

					-- 员工出勤情况(每天23:30:00获取最新数据)
					DROP EVENT
					IF EXISTS evn_refresh_emp_status;
					CREATE EVENT
					IF NOT EXISTS evn_refresh_emp_status ON SCHEDULE EVERY 1 DAY STARTS CONCAT(CURDATE(), ' ', "23:30:00") ON COMPLETION PRESERVE 
					DO
					-- 	REPLACE INTO soure_emp_status(id, customer_id, emp_key, status_type, date)
					-- 	SELECT replace(uuid(),'-', ''), 'b5c9410dc7e4422c8e0189c7f8056b5f', emp_key, 1, CURDATE() FROM v_dim_emp WHERE run_off_date is null;
						CALL pro_fetch_emp_status('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');
						
						
					-- 员工加班情况(每天23:40:00获取最新数据，如果是月尾还要把本月所有加班情况统计到 emp_overtime表 )
					DROP EVENT
					IF EXISTS evn_refresh_emp_overtime_day;
					CREATE EVENT
					IF NOT EXISTS evn_refresh_emp_overtime_day ON SCHEDULE EVERY 1 DAY STARTS CONCAT(CURDATE(), ' ', "23:40:00") ON COMPLETION PRESERVE DO
						CALL pro_fetch_emp_overtime_day('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');




					-- 绩效目标-部门
					DROP EVENT
					IF EXISTS evn_refresh_dept_per_exam_relation;
					CREATE EVENT
					IF NOT EXISTS evn_refresh_dept_per_exam_relation ON SCHEDULE EVERY 1 DAY STARTS CONCAT(CURDATE(), ' ', "03:10:00") ON COMPLETION PRESERVE 
					DO
						CALL pro_fetch_dept_per_exam_relation('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');

					-- 绩效目标-个人
					DROP EVENT
					IF EXISTS evn_refresh_emp_per_exam_relation;
					CREATE EVENT
					IF NOT EXISTS evn_refresh_emp_per_exam_relation ON SCHEDULE EVERY 1 DAY STARTS CONCAT(CURDATE(), ' ', "03:20:00") ON COMPLETION PRESERVE 
					DO
						CALL pro_fetch_emp_per_exam_relation('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');

						
					-- 预警(每天晚上获取最新数据)
					DROP EVENT
					IF EXISTS evn_refresh_warn_mgr;
					CREATE EVENT
					IF NOT EXISTS evn_refresh_warn_mgr ON SCHEDULE EVERY 1 DAY STARTS CONCAT(CURDATE(), ' ', "03:25:00") ON COMPLETION PRESERVE 
					DO
						CALL pro_fetch_warn_mgr('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');


						
					-- 人才发展(每天晚上获取最新数据)
					DROP EVENT
					IF EXISTS evn_refresh_warn_mgr;
					CREATE EVENT
					IF NOT EXISTS evn_refresh_warn_mgr ON SCHEDULE EVERY 1 DAY STARTS CONCAT(CURDATE(), ' ', "03:35:00") ON COMPLETION PRESERVE 
					DO
						CALL pro_fetch_talent_development('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');

						
		-- 关键人才库********************************************************************
			
					-- 关键人才库-自动标签(每天晚上获取最新数据)
					DROP EVENT
					IF EXISTS evn_refresh_key_talent_tags_auto;
					CREATE EVENT
					IF NOT EXISTS evn_refresh_key_talent_tags_auto ON SCHEDULE EVERY 1 DAY STARTS CONCAT(CURDATE(), ' ', "03:40:00") ON COMPLETION PRESERVE 
					DO
						CALL pro_fetch_key_talent_tags_auto('b5c9410dc7e4422c8e0189c7f8056b5f', 'system-event');




-- =======================================每月=======================================
-- =======================================每月=======================================
-- =======================================每月=======================================
-- =======================================每月=======================================
-- =======================================每月=======================================
-- =======================================每月=======================================

			-- 营业利润(每月统计上个月的：比如今天是2015-11-01，这里抽数据为 2015-10月份)
			DROP EVENT
			IF EXISTS evn_refresh_trade_profit;
			CREATE EVENT
			IF NOT EXISTS evn_refresh_trade_profit ON SCHEDULE EVERY 1 MONTH STARTS CONCAT(CURDATE(), ' ', "03:35:00") ON COMPLETION PRESERVE 
			DO
				CALL pro_fetch_trade_profit('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19',  ( SELECT DATE_SUB(CURDATE(),INTERVAL  1 MONTH)) );

				
				
						
			-- 司龄（每月库员工的司龄加1）
			DROP EVENT
			IF EXISTS evn_update_company_age;
			CREATE EVENT
			IF NOT EXISTS evn_update_company_age ON SCHEDULE EVERY 1 MONTH STARTS 
			DATE_ADD(
				DATE_ADD(
					DATE_SUB(
						CURDATE(),
						INTERVAL DAY (CURDATE()) - 1 DAY
					),
					INTERVAL 1 MONTH
				),
				INTERVAL "3 15" HOUR_MINUTE
			) ON COMPLETION PRESERVE DO
				CALL pro_update_company_age ('demo');
			
			
			-- 月度员工数:月头
			DROP EVENT
			IF EXISTS evn_add_monthly_emp_count;
			CREATE EVENT
			IF NOT EXISTS evn_add_monthly_emp_count ON SCHEDULE EVERY 1 MONTH STARTS
			 DATE_ADD(
				DATE_ADD(
					DATE_SUB(
						CURDATE(),
						INTERVAL DAY (CURDATE()) - 1 DAY
					),
					INTERVAL 1 MONTH
				),
				INTERVAL "3 45" HOUR_MINUTE
			) ON COMPLETION PRESERVE DO
				CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', CURDATE(), 1); 
			
			
			
				
				
				
				
				
				
				
-- =======================================每周期=======================================
-- =======================================每周期=======================================
-- =======================================每周期=======================================
-- =======================================每周期=======================================
-- =======================================每周期=======================================
-- 绩效(每周期获取最新数据)
DROP EVENT
IF EXISTS evn_refresh_performace_change;
CREATE EVENT
IF NOT EXISTS evn_refresh_performace_change ON SCHEDULE EVERY 1 DAY STARTS TIMESTAMP '2015-11-12 03:15:00' ON COMPLETION PRESERVE DO
	-- CALL pro_fetch_performace_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', 201506);
	SELECT 'excute :  CALL pro_fetch_performace_change()'





