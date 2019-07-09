#=======================================================pro_fetch_train_satfac=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_train_satfac`;
CREATE PROCEDURE pro_fetch_train_satfac(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;
			-- 	删除五年前今天以往的数据
-- 			DELETE FROM soure_train_plan 
-- 			WHERE customer_id = customerId
-- 			AND	id in (
-- 					SELECT id FROM (
-- 						SELECT id FROM soure_train_plan t where `date` < DATE_SUB(now(), INTERVAL 5 YEAR)
-- 					) t
-- 			);

			REPLACE INTO train_satfac (
				train_satfac_id,
				customer_id,
				organization_id,
				soure,
				`year`
			)
			SELECT 
				id,
				customer_id,
				organization_id,
				soure,
				`year`
			FROM soure_train_satfac t
			WHERE t.customer_id = customerId 
			;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
			-- 去掉已删除数据
			DELETE FROM train_satfac WHERE train_satfac_id NOT IN 
			( SELECT t2.id FROM soure_train_satfac t2 WHERE t2.customer_id = customerId);

	END IF;
END;
-- DELIMITER ;
	CALL pro_fetch_train_satfac('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');	