#=======================================================pro_fetch_out_talent=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_out_talent`;
CREATE PROCEDURE pro_fetch_out_talent(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

-- ======================================replace模板======================================
				LB_INSERT:BEGIN
					
						INSERT INTO out_talent (
							out_talent_id,
							customer_id,
							user_name_ch,
							user_name,
							email,
							age,
							sex,
							degree_id,
							url,
							school,
							tag,
							
							c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							customer_id,
							user_name_ch,
							user_name,
							email,
							age,
							sex,
							degree_id,
							url,
							school,
							tag,
						
							c_id
						FROM soure_out_talent t 
						WHERE customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								user_name_ch = t.user_name_ch,
								user_name = t.user_name,
								email = t.email,
								age = t.age,
								sex = t.sex,
								degree_id = t.degree_id,
								url = t.url,
								school = t.school,
								tag = t.tag
								
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


END;
-- DELIMITER ;

CALL pro_fetch_out_talent('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');

