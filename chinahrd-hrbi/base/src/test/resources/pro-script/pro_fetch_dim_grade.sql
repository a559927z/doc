#=======================================================pro_fetch_dim_grade=======================================================
-- 'demo','jxzhang'
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_grade`;
CREATE PROCEDURE pro_fetch_dim_grade(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32) )
BEGIN

 	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【能力等级-维度表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

		REPLACE INTO dim_grade (
			grade_id,customer_id,grade_key,grade_name,show_index
		)
		SELECT code_item_id, 	customerId,
			code_item_key ,
			code_item_name ,
			show_index
		FROM soure_code_item t
		WHERE t.customer_id = customerId and t.code_group_id = p_key_work;
	
		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【能力等级-维度表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_grade', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_grade', p_message, startTime, now(), 'sucess' );
		END IF;

END;
-- DELIMITER ;

-- CALL pro_fetch_dim_grade('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19','ability');