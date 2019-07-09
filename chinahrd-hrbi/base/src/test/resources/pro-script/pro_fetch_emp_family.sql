-- =======================================pro_fetch_emp_family============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：v_dim_emp
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_family`;
CREATE PROCEDURE pro_fetch_emp_family(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【家庭关系-业务表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

		-- DELETE FROM emp_edu WHERE customer_id = customerId;

		REPLACE INTO emp_family(
				emp_family_id,
				customer_id,
				emp_id,
				emp_family_name,
				`call`,
				work_unit,
				department_name,
				position_name,
				tel_phone,
				born_date,
				note
		)
		SELECT id, customerId, emp_id,
				emp_family_name,
				`call`,
				work_unit,
				department_name,
				tt.position_name,
				tt.tel_phone,
				born_date,
				note
		FROM soure_emp_family tt
		INNER JOIN v_dim_emp t on t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
		WHERE t.customer_id = customerId;


	IF p_error = 1 THEN  
			ROLLBACK;  
			SET p_message = concat("【家庭关系-业务表】：数据刷新失败。操作用户：", optUserId);
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_family', p_message, startTime, now(), 'error' );
	ELSE  
			COMMIT;  
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_family', p_message, startTime, now(), 'sucess' );
	END IF;


END;
-- DELIMITER ;

-- CALL pro_fetch_emp_family('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');
