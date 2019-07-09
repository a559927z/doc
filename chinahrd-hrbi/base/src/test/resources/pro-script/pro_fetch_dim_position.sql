#=======================================================pro_fetch_dim_position=======================================================
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- 'demo','jxzhang'
--  保证：dim_organization表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_position`;
CREATE PROCEDURE pro_fetch_dim_position(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【岗位-维度表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

					REPLACE INTO dim_position (
						position_id,
						customer_id,
						position_key,
 						position_name,
						organization_id,
						sequence_id
					)
					SELECT 
						id,
						customerId,
						t.position_key,
 						t.position_name,
						t1.organization_id,
						t2.sequence_id
					FROM soure_dim_position t 
					INNER JOIN dim_organization t1 on  t.organization_key = t1.organization_key and t1.customer_id = t.customer_id
					INNER JOIN dim_sequence t2 on t.sequence_key = t2.sequence_key and t2.customer_id = t.customer_id
					WHERE t.customer_id = customerId;

	IF p_error = 1 THEN  
			ROLLBACK;  
			SET p_message = concat("【岗位-维度表】：数据刷新失败。操作用户：", optUserId);
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_position', p_message, startTime, now(), 'error' );
	ELSE  
			COMMIT;  
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_position', p_message, startTime, now(), 'sucess' );
	END IF;


	
END;
-- DELIMITER ;

-- CALL pro_fetch_dim_position('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');
