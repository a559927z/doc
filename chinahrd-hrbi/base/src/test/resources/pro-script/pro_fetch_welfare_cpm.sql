#=======================================================pro_fetch_welfare_cpm=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_welfare_cpm`;
CREATE PROCEDURE pro_fetch_welfare_cpm(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

	-- 删除二年前数据
			DELETE FROM soure_welfare_cpm 
			WHERE customer_id = customerId
			AND	id in (
					SELECT id FROM (
						SELECT id FROM soure_welfare_cpm t where `year_month` = DATE_FORMAT(DATE_SUB(now(), INTERVAL 2 YEAR), '%Y%m')
					) t
			);
		

-- ======================================replace模板======================================
				LB_INSERT:BEGIN
					
						INSERT INTO welfare_cpm (
							welfare_cpm_id,
							customer_id,
							emp_id,
							cpm_id,
							welfare_value,
							`date`,
							`year_month`
						)
						SELECT 
							id,
							customer_id,
							emp_id,
							cpm_id,
							welfare_value,
							`date`,
							`year_month`
						FROM soure_welfare_cpm t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								emp_id = t.emp_id,
								cpm_id = t.cpm_id,
								welfare_value = t.welfare_value,
								`date` = t.`date`,
								`year_month` = t.`year_month`
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			-- 去掉已删除数据
			DELETE FROM welfare_cpm WHERE welfare_cpm_id NOT IN 
			( SELECT t2.id FROM soure_welfare_cpm t2 WHERE t2.customer_id = customerId); 
	END IF;


END;
-- DELIMITER ;

-- TRUNCATE TABLE welfare_cpm;

CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');

