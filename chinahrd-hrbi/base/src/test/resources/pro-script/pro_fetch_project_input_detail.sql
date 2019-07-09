#=======================================================pro_fetch_project_input_detail=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_project_input_detail`;
CREATE PROCEDURE pro_fetch_project_input_detail(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN
					
						INSERT INTO project_input_detail (
							project_input_detail_id,
							customer_id,
							project_id,
							project_input_type_id,
							outlay,
							date, type
						)
						SELECT 
							id,
							customer_id,
							project_id,
							project_input_type_id,
							outlay,
							date, type
						FROM soure_project_input_detail t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								project_id = t.project_id,
								project_input_type_id = t.project_input_type_id,
								outlay = t.outlay,
								date = t.date,
								type = t.type
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			-- 去掉已删除数据
			DELETE FROM project_input_detail WHERE project_input_detail_id NOT IN 
			( SELECT t2.id FROM soure_project_input_detail t2 WHERE t2.customer_id = customerId); 
	END IF;

END;
-- DELIMITER ;
CALL pro_fetch_project_input_detail('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');