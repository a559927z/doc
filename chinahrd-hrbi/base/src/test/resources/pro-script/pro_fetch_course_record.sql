#=======================================================pro_fetch_course_record=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_course_record`;
CREATE PROCEDURE pro_fetch_course_record(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;
			-- 	删除六年前今天以往的数据
			DELETE FROM soure_course_record
			WHERE customer_id = customerId
			AND	id in (
					SELECT id FROM (
						SELECT id FROM soure_course_record t where `end_date` < DATE_SUB(now(), INTERVAL 6 YEAR)
					) t
			);

			REPLACE INTO course_record (
				course_record_id,
				customer_id,
				course_id,
				start_date,
				end_date,
				`status`,
				train_unit,
				train_plan_id,
				`year`
			)
			SELECT 
				id,
				customer_id,
				course_id,
				start_date,
				end_date,
				`status`,
				train_unit,
				train_plan_id,
				`year`
			FROM soure_course_record t
			WHERE t.customer_id = customerId 
			;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
			-- 去掉已删除数据
			DELETE FROM course_record WHERE course_record_id NOT IN 
			( SELECT t2.id FROM soure_course_record t2 WHERE t2.customer_id = customerId);

	END IF;
END;
-- DELIMITER ;
	CALL pro_fetch_course_record('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');	