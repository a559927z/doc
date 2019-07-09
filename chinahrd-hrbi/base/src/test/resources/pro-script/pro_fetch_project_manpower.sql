#=======================================================pro_fetch_project_manpower=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_project_manpower`;
CREATE PROCEDURE pro_fetch_project_manpower(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN
					
						INSERT INTO project_manpower (
							project_manpower_id,
							customer_id,
							emp_id,
							input,
							note,
							project_id,
							project_sub_id,
							date, type
						)
						SELECT 
							id,
							customer_id,
							emp_id,
							input,
							note,
							project_id,
							project_sub_id,
							date, type
						FROM soure_project_manpower t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								emp_id = t.emp_id,
								input = t.input,
								note = t.note,
								project_id = t.project_id,
								project_sub_id = t.project_sub_id,
								date = t.date,
								type = t.type
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			-- 去掉已删除数据
			DELETE FROM project_manpower WHERE project_manpower_id NOT IN 
			( SELECT t2.id FROM soure_project_manpower t2 WHERE t2.customer_id = customerId); 
	END IF;

END;
-- DELIMITER ;
TRUNCATE TABLE project_manpower;
CALL pro_fetch_project_manpower('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');