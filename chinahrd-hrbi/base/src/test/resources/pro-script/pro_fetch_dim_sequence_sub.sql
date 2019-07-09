#=======================================================pro_fetch_dim_sequence_sub=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19','sequence'
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_sequence_sub`;
CREATE PROCEDURE pro_fetch_dim_sequence_sub(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32) )
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【子序列-维度表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

			REPLACE INTO dim_sequence_sub (
				sequence_sub_id,customer_id, sequence_id,
				sequence_sub_key,sequence_sub_name, curt_name, show_index
			)
			SELECT code_item_id, customerId, seq.sequence_id,
				code_item_key ,
				code_item_name ,
				t.curt_name,
				t.show_index
			FROM soure_code_item t
			INNER JOIN dim_sequence seq on seq.sequence_key = (select SUBSTRING_INDEX(t.code_item_key,"_",1) )
			WHERE t.customer_id = customerId and t.code_group_id = p_key_work;

	IF p_error = 1 THEN  
			ROLLBACK;  
-- 			SET p_message = concat("【子序列-维度表】：数据刷新失败。操作用户：", optUserId);
-- 			INSERT INTO db_log 
-- 			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_sequence_sub', p_message, startTime, now(), 'error' );
	ELSE  
			COMMIT;  
-- 			INSERT INTO db_log 
-- 			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_sequence_sub', p_message, startTime, now(), 'sucess' );
	END IF;


END;
-- DELIMITER ;
