#=======================================================pro_fetch_dim_job_title=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_job_title`;
CREATE PROCEDURE pro_fetch_dim_job_title(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32) )
BEGIN


	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 


	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

		REPLACE INTO dim_job_title (
			job_title_id,
			customer_id,
			job_title_key,
			job_title_name,
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
			ELSE  
					COMMIT;  
			END IF;

END;
-- DELIMITER ;

-- CALL pro_fetch_dim_job_title('b5c9410dc7e4422c8e0189c7f8056b5f','DBA','title');
