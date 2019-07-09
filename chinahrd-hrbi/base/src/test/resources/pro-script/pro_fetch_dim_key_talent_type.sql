#=======================================================pro_fetch_dim_key_talent_type=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19','keyTalent'
-- 保证 dim_emp 表 完成
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_key_talent_type`;
CREATE PROCEDURE pro_fetch_dim_key_talent_type(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32) )
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
-- 	DECLARE p_message VARCHAR(10000) DEFAULT '【人员类别-维度表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

		REPLACE into dim_key_talent_type (key_talent_type_id,customer_id,key_talent_type_key,key_talent_type_name, show_index)
		SELECT code_item_id, customerId,
			code_item_key ,
			code_item_name ,
			show_index
		FROM soure_code_item t
		WHERE t.customer_id = customerId and t.code_group_id = p_key_work;

		IF p_error = 1 THEN  
				ROLLBACK;  
			
		ELSE  
				COMMIT;  
			
		END IF;
END;
-- DELIMITER ;
-- CALL pro_fetch_dim_key_talent_type('b5c9410dc7e4422c8e0189c7f8056b5f','DBA','keyTalent');