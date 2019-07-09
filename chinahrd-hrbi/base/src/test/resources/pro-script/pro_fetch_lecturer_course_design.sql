#=======================================================pro_fetch_lecturer_course_design=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_lecturer_course_design`;
CREATE PROCEDURE pro_fetch_lecturer_course_design(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;


			REPLACE INTO lecturer_course_design (
				lecturer_course_design_id,
				customer_id,
				lecturer_id,
				course_id
			)
			SELECT 
				id,
				customer_id,
				lecturer_id,
				course_id
			FROM soure_lecturer_course_design t
			WHERE t.customer_id = customerId 
			;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
			-- 去掉已删除数据
			DELETE FROM lecturer_course_design WHERE lecturer_course_design_id NOT IN 
			( SELECT t2.id FROM soure_lecturer_course_design t2 WHERE t2.customer_id = customerId);


	END IF;


END;
-- DELIMITER ;
	CALL pro_fetch_lecturer_course_design('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');	