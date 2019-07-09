#=======================================================pro_fetch_nfb_proportion=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_nfb_proportion`;
CREATE PROCEDURE pro_fetch_nfb_proportion(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;


-- ======================================replace模板======================================
				LB_REPLACE:BEGIN
					
						INSERT INTO nfb_proportion (
							nfb_proportion_id,
							customer_id,
							city_id,
							nfb_id,
							proportion_value,
							`year`
						)
						SELECT id,
							customer_id,
							city_id,
							nfb_id,
							proportion_value,
							`year`
						FROM soure_nfb_proportion t
						ON DUPLICATE KEY UPDATE 
							nfb_proportion.city_id = t.city_id,
							nfb_proportion.nfb_id = t.nfb_id,
							nfb_proportion.proportion_value = t.proportion_value,
							nfb_proportion.`year` = t.`year`
-- 							city_id = VALUES(city_id),
-- 							nfb_id = VALUES(nfb_id),
-- 							proportion_value = VALUES(proportion_value),
-- 							`year` = VALUES(`year`),
							;


				END LB_REPLACE;




	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
	END IF;


	
END;
-- DELIMITER ;

CALL pro_fetch_nfb_proportion('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');

