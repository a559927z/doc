-- =======================================pro_fetch_emp_train_experience============================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_train_experience`;
CREATE PROCEDURE pro_fetch_emp_train_experience(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【培训经历-业务表】：数据刷新完成');

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;

			-- 	删除六年前今天以往的数据
			DELETE FROM soure_emp_train_experience 
			WHERE customer_id = customerId
			AND	id in (
					SELECT id FROM (
						SELECT id FROM soure_emp_train_experience t where `finish_date` < DATE_SUB(now(), INTERVAL 6 YEAR)
					) t
			);

			REPLACE INTO emp_train_experience (
				train_experience_id,
				customer_id,
				emp_id,
				course_record_id,
				course_name,
				start_date,
				finish_date,
				train_time,
				`status`,
				result,
				train_unit,
				gain_certificate,
				lecturer_name,
				note,
				`year`
			)
			SELECT 
				tt.id,
				customerId,
				t.emp_id,
				tt.course_record_id,
				tt.course_name,
				tt.start_date,
				tt.finish_date,
				tt.train_time,
				tt.`status`,
				tt.result,
				tt.train_unit,
				tt.gain_certificate,
				tt.lecturer_name,
				tt.note,
				tt.`year`
			FROM soure_emp_train_experience tt
			INNER JOIN v_dim_emp t on t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
			WHERE t.customer_id = customerId;

		IF p_error = 1 THEN  
				ROLLBACK;  
		ELSE  
				COMMIT;  

			-- 去掉已删除数据
			DELETE FROM emp_train_experience WHERE train_experience_id NOT IN 
			( SELECT t2.id FROM soure_emp_train_experience t2 WHERE t2.customer_id = customerId);
		END IF;
 
END;
-- DELIMITER ;

CALL pro_fetch_emp_train_experience('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');
