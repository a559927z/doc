#=======================================================pro_fetch_project_maxvalue=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_project_maxvalue`;
CREATE PROCEDURE pro_fetch_project_maxvalue(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

-- ======================================replace模板======================================
				LB_INSERT:BEGIN
					
						INSERT INTO project_maxvalue (
							project_maxvalue_id,
							customer_id,
							organization_id,
							maxval,
							
							c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							customer_id,
							organization_id,
							maxval,
						
							c_id
						FROM soure_project_maxvalue t 
						WHERE customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								organization_id = t.organization_id,
								maxval = t.maxval
								
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


END;
-- DELIMITER ;

CALL pro_fetch_project_maxvalue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');

