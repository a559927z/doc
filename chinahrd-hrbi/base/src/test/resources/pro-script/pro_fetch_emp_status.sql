-- ============================================pro_fetch_emp_status========================================
DROP PROCEDURE IF EXISTS `pro_fetch_emp_status`;
CREATE PROCEDURE pro_fetch_emp_status(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN


	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【员工出勤情况-业务表】：数据刷新完成');



	-- 本月第一天
	DECLARE firstCurDate DATE DEFAULT (SELECT DATE_SUB(CURDATE(), INTERVAL extract( DAY FROM now())-1 DAY));
	-- 本月最后一天
	DECLARE lastCurDate DATE DEFAULT (SELECT DATE_SUB( DATE_ADD(CURDATE(), INTERVAL 1 MONTH), INTERVAL DAY (CURDATE()) DAY ));

	DECLARE lastYmOneInt int(6) DEFAULT date_format(DATE_ADD( DATE_ADD(firstCurDate,INTERVAL -1 DAY), Interval 0 minute),'%Y%m');


	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;
		-- 月头第一天，清掉过上个月所有数据
		IF(CURDATE() = firstCurDate) THEN
			DELETE FROM emp_status WHERE customer_id = customerId;
		END IF;

		REPLACE INTO emp_status (
			emp_status_id,
			customer_id,
			organization_id,
			emp_id,
			status_type,
			date
		)
		SELECT 
			t.id,
			customerId,
			t.organization_id,
			t1.emp_id,
			t.status_type,
			t.date
		FROM soure_emp_status t 
		INNER JOIN v_dim_emp t1 on t.customer_id = t1.customer_id AND t.emp_key = t1.emp_key
					AND t1.run_off_date IS NULL
		WHERE t.date between firstCurDate AND lastCurDate
		;


		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【员工出勤情况-业务表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_status', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_status', p_message, startTime, now(), 'sucess' );
		END IF;

END;
-- DELIMITER ;

-- 	CALL pro_fetch_emp_status('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');
