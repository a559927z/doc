#=======================================================pro_fetch_dim_project_type=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_project_type`;
CREATE PROCEDURE pro_fetch_dim_project_type(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

-- ======================================replace模板======================================
				LB_INSERT:BEGIN
					
						INSERT INTO dim_project_type (
							project_type_id,
							customer_id,
							project_type_name,
							show_index
						)
						SELECT 
							id,
							customer_id,
							project_type_name,
							show_index
						FROM soure_dim_project_type t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								project_type_name = t.project_type_name,
								show_index = t.show_index
								
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			-- 去掉已删除数据
			DELETE FROM dim_project_type WHERE project_type_id NOT IN 
			( SELECT t2.id FROM soure_dim_project_type t2 WHERE t2.customer_id = customerId); 
	END IF;


END;
-- DELIMITER ;


CALL pro_fetch_dim_project_type('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');

