#=======================================================pro_fetch_city=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_city`;
CREATE PROCEDURE pro_fetch_city(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

-- ======================================replace模板======================================
				LB_REPLACE:BEGIN
					
						REPLACE INTO dim_city (
							city_id, customer_id, city_key, city_name, show_index
						)
						SELECT code_item_id,customer_id, code_item_key, code_item_name,show_index  FROM soure_code_item 
						WHERE code_group_id = p_key_work;
								
				END LB_REPLACE;


	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


	
END;
-- DELIMITER ;

CALL pro_fetch_city('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'city');

