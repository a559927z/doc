-- ============================================pro_fetch_emp_overtime========================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：dim_emp表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_overtime`;
CREATE PROCEDURE pro_fetch_emp_overtime(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE eId VARCHAR(32);
	DECLARE eKey VARCHAR(20);
	DECLARE hCount DOUBLE(4,2);
	DECLARE ym INT;

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;

	DECLARE startTime TIMESTAMP;
	DECLARE exist int;
	DECLARE done INT DEFAULT 0;

	DECLARE s_cur CURSOR FOR
		SELECT
			t1.emp_id eId, t.emp_key eKey,
			t.hour_count hCount, t.`year_month` ym
		FROM soure_emp_overtime t 
		INNER JOIN dim_emp t1 on t.emp_key = t1.emp_key AND t1.customer_id = t.customer_id AND now()>=t1.effect_date AND t1.expiry_date is null AND t1.enabled = 1
		WHERE t1.customer_id = customerId;

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN
			ROLLBACK; SHOW ERRORS;
			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
			VALUES(replace(UUID(),'-',''), customerId, optUserId, CONCAT("【员工加班-业务表】：", eKey, "与", ym, "复制数据失败"), startTime, now(), "error");
		END;

		SET startTime = now();

		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO eId, eKey, hCount, ym;

				START TRANSACTION;
				SELECT count(1) AS exist INTO exist FROM emp_overtime
				WHERE emp_key = eKey AND `year_month` = ym AND customer_id = customerId;

				IF exist>0 THEN
					UPDATE emp_overtime
					SET hour_count = hCount
					WHERE emp_key = eKey AND `year_month` = ym AND customer_id = customerId;
				ELSE
					INSERT INTO emp_overtime(emp_overtime_id, customer_id, 
														 			emp_id, emp_key, hour_count, 
														 			`year_month`)
					VALUES(replace(UUID(),'-',''), customerId, eId, eKey, hCount, ym);
				END IF;
				
		END WHILE;
		CLOSE s_cur;

		
END;
-- DELIMITER ;
