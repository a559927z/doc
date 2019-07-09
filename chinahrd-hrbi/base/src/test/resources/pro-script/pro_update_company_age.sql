DROP PROCEDURE IF EXISTS `pro_update_company_age`;
CREATE PROCEDURE pro_update_company_age(in p_customer_key VARCHAR(50))
BEGIN

	DECLARE eId VARCHAR(32);
	DECLARE eKey VARCHAR(20);
	DECLARE compAge INT(3);

	DECLARE customerId VARCHAR(32) DEFAULT (SELECT dc.customer_id FROM dim_customer dc WHERE dc.customer_key=p_customer_key);

	DECLARE startTime TIMESTAMP;
	DECLARE exist int;
	DECLARE done INT DEFAULT 0;
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【员工-业务表】：司龄刷新完成'; 

	DECLARE s_cur CURSOR FOR
		SELECT 
				t.emp_id eId, t.emp_key eKey, t.company_age compAge
		FROM v_dim_emp t 
		WHERE t.customer_id = customerId AND now()>= t.effect_date AND t.expiry_date IS NULL AND t.enabled = 1	
		;
	
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET p_error = 1;

		SET startTime = now();

		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO eId, eKey, compAge;
 				START TRANSACTION;

					UPDATE v_dim_emp
					SET company_age = compAge+1
					WHERE emp_id = eId AND emp_key = eKey AND customer_id = customerId;

		END WHILE;
		CLOSE s_cur;


		IF p_error = 1 THEN  
			ROLLBACK; SHOW ERRORS;
			SET p_message = concat("【员工-业务表】：司龄刷新失败。", "操作用户：", optUserId);
			INSERT INTO db_log
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'v_dim_emp.company_age', p_message, startTime, now(), 'error' );
	 
		ELSE  
				COMMIT;  
				INSERT INTO db_log (log_id, customer_id, user_id, module, text, start_time, end_time, type)
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'v_dim_emp.company_age', p_message, startTime, now(), 'sucess' );
		END IF;
END;