#=======================================================pro_fetch_recruit_result=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_recruit_result`;
CREATE PROCEDURE pro_fetch_recruit_result(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_y int(4))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN

						INSERT INTO recruit_result (
							recruit_result_id, customer_id, recruit_result_key, username, sex, age, degree_id, major, school,
							is_resume, is_interview, is_offer, is_entry, url, recruit_public_id, YEAR,  c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							customer_id,
							recruit_result_key,
							username,
							sex,
							age,
							degree_id,
							major,
							school,
							is_resume,
							is_interview,
							is_offer,
							is_entry,
							url,
							recruit_public_id,
							year,
							c_id
						FROM soure_recruit_result t 
						WHERE customer_id = customerId AND year=p_y
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								recruit_result_key = t.recruit_result_key,
								username = t.username,
								sex = t.sex,
								age = t.age,
								degree_id = t.degree_id,
								major = t.major,
								school = t.school,
								is_resume = t.is_resume,
								is_interview = t.is_interview,
								is_offer = t.is_offer,
								is_entry = t.is_entry,
								url = t.url,
								recruit_public_id = t.recruit_public_id,
								year = t.year
								
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			
	END IF;


END;
-- DELIMITER ;

CALL pro_fetch_recruit_result('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',2015);

