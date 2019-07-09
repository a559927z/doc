#=======================================================pro_fetch_matching_soure=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_matching_soure`;
CREATE PROCEDURE pro_fetch_matching_soure(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

-- ======================================replace模板======================================
				LB_INSERT:BEGIN
					
						INSERT INTO matching_soure (
							matching_soure_id,
							customer_id,
							v1,
							show_index,
							
							c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							customer_id,
							v1,
							show_index,
						
							c_id
						FROM soure_matching_soure t 
						WHERE customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								v1 = t.v1,
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

CALL pro_fetch_matching_soure('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');

