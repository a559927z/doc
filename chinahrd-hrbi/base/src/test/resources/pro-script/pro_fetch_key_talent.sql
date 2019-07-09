#=======================================================pro_fetch_key_talent=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19','sequence'
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_key_talent`;
CREATE PROCEDURE pro_fetch_key_talent(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【关键人才库-业务表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

			REPLACE INTO key_talent (
				key_talent_id,
				customer_id,
				emp_id,
				key_talent_type_id,
				is_sychron,
				is_delete,
				note,
				create_emp_id,
				refresh_tag1, refresh_tag2, refresh_log, refresh_encourage
			)
			SELECT 
				id,
				customerId,
				t1.emp_id,
				t2.key_talent_type_id,
				1,
				is_delete,
				NULL,
				NULL,
				NULL, NULL, NULL, NULL
			FROM soure_key_talent t
			INNER JOIN v_dim_emp t1 on t1.emp_key = t.emp_key AND t.customer_id = t1.customer_id
			LEFT JOIN dim_key_talent_type t2 on t2.key_talent_type_key = t.key_talent_type_key AND t.customer_id = t2.customer_id
			WHERE t.customer_id = customerId ;

	IF p_error = 1 THEN  
			ROLLBACK;  
			SET p_message = concat("【关键人才库-业务表】：数据刷新失败。操作用户：", optUserId);
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'key_talent', p_message, startTime, now(), 'error' );
	ELSE  
			COMMIT;  
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'key_talent', p_message, startTime, now(), 'sucess' );
	END IF;



END;
-- DELIMITER ;
	CALL pro_fetch_key_talent('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');