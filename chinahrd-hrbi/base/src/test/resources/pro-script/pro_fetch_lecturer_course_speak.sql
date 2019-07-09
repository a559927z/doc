#=======================================================pro_fetch_lecturer_course_speak=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_lecturer_course_speak`;
CREATE PROCEDURE pro_fetch_lecturer_course_speak(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;

			-- 	删除五年前今天以往的数据
			DELETE FROM soure_lecturer_course_speak 
			WHERE customer_id = customerId
			AND	id in (
					SELECT id FROM (
						SELECT id FROM soure_lecturer_course_speak t where `end_date` < DATE_SUB(now(), INTERVAL 5 YEAR)
					) t
			);

			REPLACE INTO lecturer_course_speak (
				lecturer_course_speak_id,
				customer_id,
				lecturer_id,
				course_id,
				start_date,
				end_date,
				`year`
			)
			SELECT 
				id,
				customer_id,
				lecturer_id,
				course_id,
				's',
				end_date,
				`year`
			FROM soure_lecturer_course_speak t
			WHERE t.customer_id = customerId 
			;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
			-- 去掉已删除数据
			DELETE FROM lecturer_course_speak WHERE lecturer_course_speak_id NOT IN 
			( SELECT t2.id FROM soure_lecturer_course_speak t2 WHERE t2.customer_id = customerId);


-- 		删除五年前今天以往的数据
-- 		DELETE FROM lecturer_course_speak 
-- 		WHERE customer_id = customerId
-- 		AND	lecturer_course_speak_id in (
-- 				SELECT lecturer_course_speak_id FROM (
-- 					SELECT lecturer_course_speak_id FROM lecturer_course_speak t where `end_date` < DATE_SUB(now(), INTERVAL 5 YEAR)
-- 				) t;
-- 

	END IF;


END;
-- DELIMITER ;
	CALL pro_fetch_lecturer_course_speak('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');	