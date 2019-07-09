#=======================================================pro_fetch_dim_course=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_course`;
CREATE PROCEDURE pro_fetch_dim_course(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

			REPLACE INTO dim_course (
				course_id,
				customer_id,
				course_key,
				course_name,
				course_type_id
			)
			SELECT 
				id,
				customer_id,
				course_key,
				course_name,
				course_type_id
			FROM soure_dim_course t
			WHERE t.customer_id = customerId 
			;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
			-- 去掉已删除数据
			DELETE FROM dim_course where course_id not in ( SELECT t2.id from soure_dim_course t2 where t2.customer_id = customerId);

	END IF;



END;
-- DELIMITER ;
	CALL pro_fetch_dim_course( 'b5c9410dc7e4422c8e0189c7f8056b5f','DBA');	