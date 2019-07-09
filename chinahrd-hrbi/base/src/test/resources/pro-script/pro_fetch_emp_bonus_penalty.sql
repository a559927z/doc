-- =======================================pro_fetch_emp_bonus_penalty============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：v_dim_emp
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_bonus_penalty`;
CREATE PROCEDURE pro_fetch_emp_bonus_penalty(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN


	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【奖惩-业务表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

			REPLACE INTO emp_bonus_penalty(
					emp_bonus_penalty_id,
					emp_id,
					customer_id,
					bonus_penalty_name,
					type,
					grant_unit,
					witness_name,
					bonus_penalty_date
			)
			SELECT replace(UUID(),'-',''), customerId, emp_id,
					bonus_penalty_name,
					type,
					grant_unit,
					witness_name,
					bonus_penalty_date
			FROM soure_emp_bonus_penalty tt
			INNER JOIN v_dim_emp t on t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
			WHERE t.customer_id = customerId;

	IF p_error = 1 THEN  
			ROLLBACK;  
			SET p_message = concat("【奖惩-业务表】：数据刷新失败。操作用户：", optUserId);
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_bonus_penalty', p_message, startTime, now(), 'error' );
	ELSE  
			COMMIT;  
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_bonus_penalty', p_message, startTime, now(), 'sucess' );
	END IF;

END;
-- DELIMITER ;
