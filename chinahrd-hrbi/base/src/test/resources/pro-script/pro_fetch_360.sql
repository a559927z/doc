-- =======================================pro_fetch_360============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_360`;
CREATE PROCEDURE pro_fetch_360(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
 	

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【360测评模块-业务表】：数据刷新完成');

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

		REPLACE INTO 360_time(
			360_time_id,
			customer_id,
			emp_id,
			report_date,
			report_name,
			path,
			type,
			`YEAR`
		)
		SELECT
			id,
			customerId,
			t.emp_id,
			report_date,
			report_name,
			path,
			type,
			`YEAR`
		FROM soure_360_time tt
		INNER JOIN v_dim_emp t ON t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
		WHERE t.customer_id = customerId ;


		REPLACE INTO 360_report(
			360_report_id,
			customer_id,
			emp_id,
			360_ability_id,
			360_ability_name,
			360_ability_lv_id,
			360_ability_lv_name,
			standard_score,
			gain_score,
			score,
			module_type,
			360_time_id
		)
		SELECT
			id,
			customerId,
			t.emp_id,
			t2.code_item_id 360_ability_id,
			tt.360_ability_name,
			t3.code_item_id 360_ability_lv_id,
			tt.360_ability_lv_name, 
			standard_score,
			gain_score,
			score,
			module_type,
			tt.360_time_id
		FROM soure_360_report tt
		INNER JOIN v_dim_emp t ON t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
		INNER JOIN soure_code_item t2 ON tt.360_ability_key = t2.code_item_key AND tt.customer_id = t2.customer_id
		INNER JOIN soure_code_item t3 ON tt.360_ability_key = t3.code_item_key AND tt.customer_id = t3.customer_id
		WHERE t.customer_id = customerId ;


			IF p_error = 1 THEN  
					ROLLBACK;  
					SET p_message = concat("【360测评模块-业务表】：数据刷新失败。操作用户：", optUserId);
					INSERT INTO db_log 
					VALUES(replace(uuid(), '-',''), customerId, optUserId, '360_time、360_report', p_message, startTime, now(), 'error' );
			ELSE  
					COMMIT;  
					INSERT INTO db_log 
					VALUES(replace(uuid(), '-',''), customerId, optUserId, '360_time、360_report', p_message, startTime, now(), 'sucess' );
			END IF;


 
END;
-- DELIMITER ;

-- CALL pro_fetch_360('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');