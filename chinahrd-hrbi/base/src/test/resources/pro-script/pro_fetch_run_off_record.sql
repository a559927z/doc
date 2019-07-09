#=======================================================pro_fetch_run_off_record=======================================================
-- 'demo','jxzhang'
-- 不保存历史
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_run_off_record`;
CREATE PROCEDURE pro_fetch_run_off_record(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN


	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE s_cur CURSOR FOR
					SELECT 
						t2.run_off_id roId,
						t1.emp_id eId,
						t.emp_key eKey,
						t.run_off_key roKey,
						t.where_abouts wAbouts,
						t.run_off_date roDate,
						t.re_hire reHire
					FROM soure_run_off_record t
					INNER JOIN v_dim_emp t1 on t.emp_key = t1.emp_key 
								AND t1.customer_id = t.customer_id 
-- 								AND t1.enabled = 1 AND t1.effect_date <=NOW() AND (t1.expiry_Date IS NULL OR t1.expiry_Date> NOW())
								AND t1.run_off_date IS NULL
					INNER JOIN dim_run_off t2 on t2.run_off_key = t.run_off_key AND t2.customer_id = t.customer_id
					WHERE t.customer_id = customerId;
	

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

		REPLACE INTO run_off_record(
			run_off_record_id,
			customer_id,
			emp_id,
			run_off_id,
			where_abouts,
			run_off_date,
			is_warn,
			re_hire
		)
		SELECT
				id,
				t2.customer_id,
				t1.emp_id,
				t3.run_off_id,
				where_abouts,
				t2.run_off_date,
				0,
				re_hire
		FROM soure_run_off_record t2
		INNER JOIN v_dim_emp t1 on t2.emp_key = t1.emp_key AND t2.customer_id = t1.customer_id
		INNER JOIN dim_run_off t3 on t3.run_off_key = t2.run_off_key AND t3.customer_id = t2.customer_id
		WHERE	 t2.customer_id = customerId;

		-- 同时更新员工的离职时间
		UPDATE soure_v_dim_emp t SET t.run_off_date = (SELECT t2.run_off_date FROM soure_run_off_record t2 WHERE t2.emp_key = t.emp_key);
		UPDATE v_dim_emp t SET t.run_off_date = (SELECT t2.run_off_date FROM soure_run_off_record t2 WHERE t2.emp_key = t.emp_key);

		-- 更新是否有预警
		UPDATE run_off_record t2 , (
				SELECT 
					t.emp_id
				FROM
					dimission_risk t
				INNER JOIN v_dim_emp t1 ON t.emp_id = t1.emp_id
				AND t1.customer_id = t.customer_id
				AND t1.run_off_date >= t.risk_date	-- 流失日期 => 评估时间
-- 				AND t1.enabled = 1 AND t1.effect_date <=NOW() AND (t1.expiry_Date IS NULL OR t1.expiry_Date> NOW())
			) t 
		SET t2.is_warn = 1
		where t2.emp_id = t.emp_id;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  	
	END IF;


END;
-- DELIMITER ;

CALL pro_fetch_run_off_record('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');