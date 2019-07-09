-- =======================================pro_fetch_talent_development_train============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证表存在：
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_talent_development_train`;
CREATE PROCEDURE pro_fetch_talent_development_train(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE yQ VARCHAR(6) DEFAULT CONCAT(YEAR(NOW()), 'Q', QUARTER(NOW()));

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【管理者首页-人才发展(培训)-业务表】：数据刷新完成');


	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;

	-- 删除所有，重新写入。
			DELETE FROM talent_development_train WHERE customer_id = customerId AND year_quarter = yQ;

			INSERT INTO talent_development_train(
				talent_development_train_id,
				customer_id,
				emp_id,
				emp_key,
				organization_id, position_name, sequence_name, sequence_sub_name,
				train_time,
				train_num,
				year_quarter
			)
			SELECT 
				REPLACE(uuid(),'-',''), customerId,
				t.emp_id eId, t1.emp_key eKey ,
				t1.organization_id, t1.position_name, t1.sequence_name, t1.sequence_sub_name,
				sum(t.train_time) tTime, count(t.emp_id) tNum,
				yQ
			FROM emp_train_experience t 
			INNER JOIN v_dim_emp t1 on t1.emp_id = t.emp_id and t.customer_id = t1.customer_id
							-- 不包括离职员工
							-- AND t1.run_off_date is null
			WHERE t.customer_id = customerId AND ( YEAR(NOW()) = YEAR(t.start_date) AND QUARTER(NOW()) = QUARTER(t.start_date)) 
			GROUP BY t.emp_id ORDER BY t.train_time;


		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【管理者首页-人才发展(培训)-业务表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'talent_development_train', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'talent_development_train', p_message, startTime, now(), 'sucess' );
		END IF;
 

END;
-- DELIMITER ;

		CALL pro_fetch_talent_development_train('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');
