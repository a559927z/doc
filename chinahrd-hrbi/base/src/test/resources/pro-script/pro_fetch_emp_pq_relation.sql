#=======================================================pro_fetch_emp_pq_relation=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_pq_relation`;
CREATE PROCEDURE pro_fetch_emp_pq_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN
					
						INSERT INTO emp_pq_relation (
							emp_pq_relation_id,
							customer_id,
							emp_id,
							date,
							matching_soure_id,
							postion_quality_id,
							
							c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							customer_id,
							emp_id,
							date,
							matching_soure_id,
							postion_quality_id,
						
							c_id
						FROM soure_emp_pq_relation t 
						WHERE customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								date = t.date,
								matching_soure_id = t.matching_soure_id,
								postion_quality_id = t.postion_quality_id
								
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


END;
-- DELIMITER ;

CALL pro_fetch_emp_pq_relation('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');

