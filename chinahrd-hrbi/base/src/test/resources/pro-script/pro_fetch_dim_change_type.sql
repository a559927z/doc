#=======================================================pro_fetch_dim_change_type=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_change_type`;
CREATE PROCEDURE pro_fetch_dim_change_type(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_key VARCHAR(10))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;		

				LB_INSERT:BEGIN
					
						INSERT INTO dim_change_type (
							change_type_id,
							customer_id,
							change_type_name,
							curt_name,
							show_index
						)
						SELECT 
							code_item_id,
							customer_id,
							code_item_name,
							curt_name,
							show_index
						FROM soure_code_item t where customer_id = customerId AND code_group_id = p_key
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								change_type_name = t.code_item_name,
								curt_name = t.curt_name,
								show_index = t.show_index,
								
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


END;
-- DELIMITER ;

CALL pro_fetch_dim_change_type('b5c9410dc7e4422c8e0189c7f8056b5f','DBA','changeType');

