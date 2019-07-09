#=======================================================pro_update_history_emp_count_month=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_update_history_emp_count_month`;
CREATE PROCEDURE pro_update_history_emp_count_month(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_ym INTEGER(6))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	
	DECLARE p_error INTEGER DEFAULT 0; 

	START TRANSACTION;
			
	-- 薪酬汇总表
		LB_CURSOR_SUM:BEGIN

			INSERT INTO history_emp_count_month (
				history_emp_count_month_id,
				customer_id,
				organization_id,
				organization_full_path,
				month_begin,
				month_begin_sum,
				month_end,
				month_end_sum,
				month_count,
				month_count_sum,
				month_avg,
				month_avg_sum,
				`year_month`
			)
			SELECT 
			history_emp_count_month_id,
				customer_id,
				organization_id,
				organization_full_path,
				month_begin,
				month_begin_sum,
				month_end,
				month_end_sum,
				month_count,
				month_count_sum,
				month_avg,
				month_avg_sum,
				`year_month`
			FROM (
				SELECT 
					history_emp_count_month_id,
					customer_id,
					organization_id,
					organization_full_path,
					month_begin,
					month_begin_sum,
					month_end,
					month_end_sum,
					month_count,
					month_count_sum,
					(month_begin + month_end)/2 AS month_avg,
					(month_begin_sum + month_end_sum)/2 AS month_avg_sum,
					`year_month`
				 FROM history_emp_count_month
				where `year_month` = p_ym
				GROUP BY organization_id
			) t
			where `year_month` = p_ym
			ON DUPLICATE KEY UPDATE
				month_avg = t.month_avg, 
				month_avg_sum = t.month_avg_sum
			;


	END LB_CURSOR_SUM;


	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;

END;
-- DELIMITER ;
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201401);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201402);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201403);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201404);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201405);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201406);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201407);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201408);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201409);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201410);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201411);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201412);	

	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201501);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201502);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201503);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201504);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201505);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201506);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201507);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201508);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201509);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201510);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201511);	
	CALL pro_update_history_emp_count_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201512);	

