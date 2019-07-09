-- =======================================pro_fetch_emp_edu============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：v_dim_emp
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_edu`;
CREATE PROCEDURE pro_fetch_emp_edu(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【教育背景-业务表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

				LB_INSERT:BEGIN
					
						INSERT INTO emp_edu (
							emp_edu_id, customer_id, emp_id, school_name, degree, degree_id, major, 
							start_date, end_date, is_last_major, academic_degree, develop_mode, bonus, note, c_id
						)
						SELECT 
							replace(UUID(),'-',''),customer_id, 
							emp_id, school_name, degree, degree_id, major, 
							start_date, end_date, is_last_major, academic_degree, develop_mode, bonus, note, c_id
						FROM soure_emp_edu t 
						where customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								emp_id = t.emp_id,
								school_name = t.school_name,
								degree = t.degree,
								degree_id = t.degree_id,
								major = t.major,
								start_date = t.start_date,
								end_date = t.end_date,
								is_last_major = t.is_last_major,
								academic_degree = t.academic_degree,
								develop_mode = t.develop_mode,
								bonus = t.bonus,
								note = t.note,
								c_id = t.c_id
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


END;
-- DELIMITER ;

-- CALL pro_fetch_emp_edu('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');
