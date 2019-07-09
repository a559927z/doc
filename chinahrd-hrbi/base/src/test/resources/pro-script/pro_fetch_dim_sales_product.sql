-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_sales_product`;
CREATE PROCEDURE pro_fetch_dim_sales_product(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32) )
BEGIN


	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '����Ʒ��Ϣ��';
	DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_procedure_info where pro_name = 'pro_fetch_dim_sales_product');

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

		

		INSERT INTO db_log 
		VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_dim_sales_product', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		set @error_message = p_error;  


END;
-- DELIMITER ;



