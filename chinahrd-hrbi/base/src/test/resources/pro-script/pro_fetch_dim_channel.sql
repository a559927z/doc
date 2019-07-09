#=======================================================pro_fetch_dim_channel=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_channel`;
CREATE PROCEDURE pro_fetch_dim_channel(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_work_key VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

-- ======================================replace模板======================================
				LB_INSERT:BEGIN
					
						INSERT INTO dim_channel (
							channel_id,
							customer_id,
							channel_key,
							channel_name,
							show_index,
							
							c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							customer_id,
							code_item_key,
							code_item_name,
							show_index,
						
							code_item_id
						FROM soure_code_item t 
						WHERE t.code_group_id = p_work_key AND customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								channel_key = t.code_item_key,
								channel_name = t.code_item_name,
								show_index = t.show_index,
								c_id = code_item_id
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


END;
-- DELIMITER ;

CALL pro_fetch_dim_channel('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'channel');

