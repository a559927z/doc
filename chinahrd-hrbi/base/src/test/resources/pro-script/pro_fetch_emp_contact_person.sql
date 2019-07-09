-- =======================================pro_fetch_emp_contact_person============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：v_dim_emp
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_contact_person`;
CREATE PROCEDURE pro_fetch_emp_contact_person(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【联系人-业务表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

		REPLACE INTO emp_contact_person(
			emp_contact_person_id, customer_id, emp_id,
			emp_contact_person_name,
			tel_phone,
			`call`,
			is_urgent,
			create_time
		)
		SELECT 
			id, customerId, emp_id,
			emp_contact_person_name,
			tt.tel_phone,
			`call`,
			is_urgent,
			create_time
		FROM soure_emp_contact_person tt
		INNER JOIN v_dim_emp t on t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
		WHERE t.customer_id = customerId;

	IF p_error = 1 THEN  
			ROLLBACK;  
			SET p_message = concat("【联系人-业务表】：数据刷新失败。操作用户：", optUserId);
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_contact_person', p_message, startTime, now(), 'error' );
	ELSE  
			COMMIT;  
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_contact_person', p_message, startTime, now(), 'sucess' );
	END IF;


END;
-- DELIMITER ;
