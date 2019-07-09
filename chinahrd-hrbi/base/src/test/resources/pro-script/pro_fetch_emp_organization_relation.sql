#=======================================================pro_fetch_emp_organization_relation=======================================================
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- 'demo','jxzhang'
--  保证：dim_organization表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_organization_relation`;
CREATE PROCEDURE pro_fetch_emp_organization_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【数据权限-关系表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

					REPLACE INTO emp_organization_relation (
						emp_organization_id,
						customer_id,
						emp_id,
						organization_id,
						half_check,
						create_user_id,
						create_time
					)
					SELECT 
						id,
						customerId,
						t2.emp_id,
						t1.organization_id,
						0,
						optUserId,
						CURDATE()
					FROM soure_emp_organization_relation t 
					INNER JOIN dim_organization t1 on t.organization_key = t1.organization_key and t1.customer_id = t.customer_id
					INNER JOIN v_dim_emp t2 on t.emp_key = t2.emp_key and t2.customer_id = t.customer_id
					WHERE t.customer_id = customerId;

	IF p_error = 1 THEN  
			ROLLBACK;  
			SET p_message = concat("【数据权限-关系表】：数据刷新失败。操作用户：", optUserId);
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_organization_relation', p_message, startTime, now(), 'error' );
	ELSE  
			COMMIT;  
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_organization_relation', p_message, startTime, now(), 'sucess' );
	END IF;


	
END;
-- DELIMITER ;

CALL pro_fetch_emp_organization_relation('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');
