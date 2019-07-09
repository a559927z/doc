#=======================================================pro_fetch_dim_ability=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19','sequence'
-- 保证 dim_emp 表 完成
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_ability`;
CREATE PROCEDURE pro_fetch_dim_ability(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32) )
BEGIN


	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【职位层级-维度表】：数据刷新完成');

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

		REPLACE INTO dim_ability (
			ability_id,
			customer_id,
			ability_key,
			ability_name,
			curt_name,
			type,
			show_index
		)
		SELECT 
			code_item_id,
			customerId,
			code_item_key,
			code_item_name,
			curt_name cName,
			type,
			show_index
		FROM soure_code_item t
		WHERE t.customer_id = customerId and t.code_group_id = p_key_work;

		
			IF p_error = 1 THEN  
					ROLLBACK;  
-- 					SET p_message = concat("【职位层级-维度表】：数据刷新失败。操作用户：", optUserId);
-- 					INSERT INTO db_log 
-- 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_ability', p_message, startTime, now(), 'error' );
			ELSE  
					COMMIT;  
-- 					INSERT INTO db_log 
-- 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_ability', p_message, startTime, now(), 'sucess' );
			END IF;

END;
-- DELIMITER ;

-- CALL pro_fetch_dim_ability('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19','ability');
