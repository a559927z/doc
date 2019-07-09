#=======================================================pro_fetch_position_quality=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_position_quality`;
CREATE PROCEDURE pro_fetch_position_quality(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

				LB_INSERT:BEGIN
					
						INSERT INTO position_quality (
							position_quality_id,
							customer_id,
							position_id,
							quality_id,
							matching_soure_id,
							show_index,
							
							c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							customer_id,
							position_id,
							quality_id,
							matching_soure_id,
							show_index,
						
							c_id
						FROM soure_position_quality t 
						WHERE customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								position_id = t.position_id,
								quality_id = t.quality_id,
								matching_soure_id = t.matching_soure_id,
								show_index = t.show_index
								
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


END;
-- DELIMITER ;

CALL pro_fetch_position_quality('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');

