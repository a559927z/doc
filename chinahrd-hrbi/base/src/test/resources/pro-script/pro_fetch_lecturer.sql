#=======================================================pro_fetch_lecturer=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_lecturer`;
CREATE PROCEDURE pro_fetch_lecturer(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

			REPLACE INTO lecturer (
				lecturer_id,
				customer_id,
				emp_id,
				lecturer_name,
				level_id,
				type
			)
			SELECT 
				id,
				customer_id,
				emp_id,
				lecturer_name,
				level_id,
				type
			FROM soure_lecturer t
			WHERE t.customer_id = customerId 
			;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
			-- 去掉已删除数据
			DELETE FROM lecturer where lecturer_id not in ( SELECT t2.id from soure_lecturer t2 where t2.customer_id = customerId);

	END IF;


END;
-- DELIMITER ;
	CALL pro_fetch_lecturer('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');	