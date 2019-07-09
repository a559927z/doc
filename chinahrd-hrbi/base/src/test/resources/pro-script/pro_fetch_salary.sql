#=======================================================pro_fetch_salary=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_salary`;
CREATE PROCEDURE pro_fetch_salary(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

	-- 删除二年前数据
			DELETE FROM soure_salary 
			WHERE customer_id = customerId
			AND	id in (
					SELECT id FROM (
						SELECT id FROM soure_salary t where `year_month` = DATE_FORMAT(DATE_SUB(now(), INTERVAL 2 YEAR), '%Y%m')
					) t
			);
		

-- ======================================replace模板======================================
				LB_INSERT:BEGIN
					
						INSERT INTO salary (
							salary_id,
							customer_id,
							emp_id,
							structure_id,
							salary_value,
							`year_month`
						)
						SELECT 
							id,
							customer_id,
							emp_id,
							structure_id,
							salary_value,
							`year_month`
						FROM soure_salary t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								emp_id = t.emp_id,
								structure_id=t.structure_id,
								salary_value=t.salary_value,
								`year_month`=t.`year_month`
						;
				END LB_INSERT;



	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			-- 去掉已删除数据
			DELETE FROM salary WHERE salary_id NOT IN 
			( SELECT t2.id FROM soure_salary t2 WHERE t2.customer_id = customerId);  
	END IF;


	
END;
-- DELIMITER ;

-- TRUNCATE TABLE salary;

-- CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');

