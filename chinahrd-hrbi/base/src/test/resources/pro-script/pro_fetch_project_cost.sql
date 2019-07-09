#=======================================================pro_fetch_project_cost=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_project_cost`;
CREATE PROCEDURE pro_fetch_project_cost(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN
					
						INSERT INTO project_cost (
							project_cost_id,
							customer_id,
							input,
							output,
							gain,
							project_id,
							date,type
						)
						SELECT 
							id,
							customer_id,
							input,
							output,
							gain,
							project_id,
							date,type
						FROM soure_project_cost t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								input = t.input,
								output = t.output,
								gain = t.gain,
								project_id = t.project_id,
								date = t.date,
								type = t.type
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			-- 去掉已删除数据
			DELETE FROM project_cost WHERE project_cost_id NOT IN 
			( SELECT t2.id FROM soure_project_cost t2 WHERE t2.customer_id = customerId); 

			CALL pro_fetch_project_input_detail(customerId,p_user_id);
	END IF;

END;
-- DELIMITER ;
TRUNCATE TABLE project_cost;
CALL pro_fetch_project_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');