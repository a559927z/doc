#=======================================================pro_fetch_dim_ability_lv=======================================================
-- 'demo','jxzhang'
-- CALL pro_fetch_dim_ability_lv('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19','ability_lv');
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_ability_lv`;
CREATE PROCEDURE pro_fetch_dim_ability_lv(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32) )
BEGIN

	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【职级-维度表】：数据刷新完成');

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;


	REPLACE INTO dim_ability_lv (
		ability_lv_id,customer_id,ability_lv_key,ability_lv_name,curt_name,show_index
	)
	SELECT 
		code_item_id,
		customerId,
		code_item_key,
		code_item_name,
		curt_name cName,
		show_index
	FROM soure_code_item t
	WHERE t.customer_id = customerId and t.code_group_id = p_key_work;

			IF p_error = 1 THEN  
					ROLLBACK;  
-- 					SET p_message = concat("【职级-维度表】：数据刷新失败。操作用户：", optUserId);
-- 					INSERT INTO db_log 
-- 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_ability_lv', p_message, startTime, now(), 'error' );
			ELSE  
					COMMIT;  
-- 					INSERT INTO db_log 
-- 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_ability_lv', p_message, startTime, now(), 'sucess' );
			END IF;

END;
-- DELIMITER ;

-- CALL pro_fetch_dim_ability_lv('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19','ability_lv'); 