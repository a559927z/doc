#=======================================================pro_fetch_dim_structure=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_structure`;
CREATE PROCEDURE pro_fetch_dim_structure(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

				LB_INSERT:BEGIN
						INSERT INTO dim_structure (
							structure_id, customer_id, structure_name, is_fixed
						)
						SELECT 
							id, customer_id, structure_name, is_fixed
						FROM soure_dim_structure t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								structure_name = t.structure_name,
								is_fixed = t.is_fixed
								
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			-- 去掉已删除数据
			DELETE FROM dim_structure WHERE structure_id NOT IN 
			( SELECT t2.id FROM soure_dim_structure t2 WHERE t2.customer_id = customerId); 
	END IF;


END;
-- DELIMITER ;

CALL pro_fetch_dim_structure('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');

