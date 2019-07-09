#=======================================================pro_fetch_dim_quality=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_quality`;
CREATE PROCEDURE pro_fetch_dim_quality(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

-- ======================================replace模板======================================
				LB_INSERT:BEGIN
					
						INSERT INTO dim_quality (
							quality_id,
							customer_id,
							vocabulary,
							note,
							
							c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							customer_id,
							vocabulary,
							note,
						
							c_id
						FROM soure_dim_quality t 
						WHERE customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								vocabulary = t.vocabulary,
								note = t.note
								
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


END;
-- DELIMITER ;

CALL pro_fetch_dim_quality('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');

