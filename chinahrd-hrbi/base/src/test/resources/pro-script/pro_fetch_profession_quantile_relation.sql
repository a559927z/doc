#=======================================================pro_fetch_profession_quantile_relation=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_profession_quantile_relation`;
CREATE PROCEDURE pro_fetch_profession_quantile_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;


-- ======================================replace模板======================================
				LB_INSERT:BEGIN
					
						INSERT INTO profession_quantile_relation (
							profession_quantile_id,
							customer_id,
							profession_id,
							quantile_id,
							quantile_value,
							type,
							`year`
						)
						SELECT 
							id,
							customer_id,
							profession_id,
							quantile_id,
							quantile_value,
							type,
							`year`
						FROM soure_profession_quantile_relation t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								profession_id = t.profession_id,
								quantile_id = t.quantile_id,
								quantile_value = t.quantile_value,
								type = t.type,
								`year` = t.`year`
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			-- 去掉已删除数据
			DELETE FROM profession_quantile_relation WHERE profession_quantile_id NOT IN 
			( SELECT t2.id FROM soure_profession_quantile_relation t2 WHERE t2.customer_id = customerId); 
	END IF;


END;
-- DELIMITER ;

-- TRUNCATE TABLE profession_quantile_relation;

CALL pro_fetch_profession_quantile_relation('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');