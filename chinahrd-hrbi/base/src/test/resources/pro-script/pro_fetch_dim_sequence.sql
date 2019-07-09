#=======================================================pro_fetch_dim_sequence=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19','sequence'
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_sequence`;
CREATE PROCEDURE pro_fetch_dim_sequence(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【主序列-维度表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

			REPLACE INTO dim_sequence (
				sequence_id,
				customer_id,
				sequence_key,
				sequence_name,
				curt_name,
				show_index
			)
			SELECT 
				code_item_id,
				customerId,
				code_item_key,
				code_item_name,
				curt_name,
				show_index
			FROM soure_code_item t
			WHERE t.customer_id = customerId and t.code_group_id = p_key_work;

	IF p_error = 1 THEN  
			ROLLBACK;  
-- 			SET p_message = concat("【主序列-维度表】：数据刷新失败。操作用户：", optUserId);
-- 			INSERT INTO db_log 
-- 			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_sequence', p_message, startTime, now(), 'error' );
	ELSE  
			COMMIT;  
			
			-- 去掉已删除数据
			DELETE FROM dim_sequence where sequence_id not in ( SELECT t2.code_item_id from soure_code_item t2 where t2.code_group_id = 'sequence');

-- 			INSERT INTO db_log 
-- 			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_sequence', p_message, startTime, now(), 'sucess' );
	END IF;





END;
-- DELIMITER ;
	CALL pro_fetch_dim_sequence( 'b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'sequence');	-- 序列