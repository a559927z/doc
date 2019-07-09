#=======================================================pro_fetch_dim_separation_risk=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_separation_risk`;
CREATE PROCEDURE pro_fetch_dim_separation_risk(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN
					
						INSERT INTO dim_separation_risk (
							separation_risk_id, customer_id, separation_risk_key, separation_risk_parent_id, separation_risk_parent_key, separation_risk_name, refer, show_index
						)
						SELECT 
							id, customer_id, separation_risk_key, parent_id, parent_key, separation_risk_name, refer, show_index
						FROM soure_dim_separation_risk t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								separation_risk_key = t.separation_risk_key,
								separation_risk_parent_id = t.parent_id,
								separation_risk_parent_key = t.parent_key,
								separation_risk_name = t.separation_risk_name,
								refer = t.refer,
								show_index = t.show_index
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			-- 去掉已删除数据
			DELETE FROM dim_separation_risk WHERE separation_risk_id NOT IN 
			( SELECT t2.id FROM soure_dim_separation_risk t2 WHERE t2.customer_id = customerId); 
	END IF;

END;
-- DELIMITER ;
CALL pro_fetch_dim_separation_risk('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');

