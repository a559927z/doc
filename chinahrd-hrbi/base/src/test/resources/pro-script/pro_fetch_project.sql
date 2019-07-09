#=======================================================pro_fetch_project=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_project`;
CREATE PROCEDURE pro_fetch_project(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN
					
						INSERT INTO project (
							project_id,
							customer_id,
							project_key,
							project_name,
							emp_id,
							organization_id,
							project_type_id,
							project_progress_id,
							project_parent_id,
							full_path,
							has_chilren,
							start_date,
							end_date
						)
						SELECT 
							id,
							customer_id,
							project_key,
							project_name,
							emp_id,
							organization_id,
							project_type_id,
							project_progress_id,
							project_parent_id,
							full_path,
							has_chilren,
							start_date,
							end_date
						FROM soure_project t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								project_key = t.project_key,
								project_name = t.project_name,
								emp_id = t.emp_id,
								organization_id = t.organization_id,
								project_type_id = t.project_type_id,
								project_progress_id = t.project_progress_id,
								project_parent_id = t.project_parent_id,
								full_path = t.full_path,
								has_chilren = t.has_chilren,
								start_date = t.start_date,
								end_date = t.end_date								
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			-- 去掉已删除数据
			DELETE FROM project WHERE project_id NOT IN 
			( SELECT t2.id FROM soure_project t2 WHERE t2.customer_id = customerId); 

			-- 主导机构下的参与机构关系
			CALL pro_fetch_project_partake_relation(customerId,p_user_id);
			
	END IF;

END;
-- DELIMITER ;

-- TRUNCATE project;
CALL pro_fetch_project('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');
