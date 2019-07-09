-- =======================================pro_fetch_emp_per_exam_relation============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：dim_emp、dim_organization、
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_per_exam_relation`;
CREATE PROCEDURE pro_fetch_emp_per_exam_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
 	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【个人绩效目标-业务表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

					REPLACE INTO emp_per_exam_relation(
						emp_per_exam_relation_id,
						customer_id,
						emp_id,
						content,
						sub_content,
						weight,
						progress,
						emp_idp,
						idp
					)
					SELECT 
						id,
						customer_id,
						emp_id,
						content,
						sub_content,
						weight,
						progress,
						emp_idp,
						idp
					FROM soure_emp_per_exam_relation
					WHERE customer_id = customerId
				;

		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【个人绩效目标-业务表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_per_exam_relation', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_per_exam_relation', p_message, startTime, now(), 'sucess' );
		END IF;
 
END;
-- DELIMITER ;

 CALL pro_fetch_emp_per_exam_relation('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');

