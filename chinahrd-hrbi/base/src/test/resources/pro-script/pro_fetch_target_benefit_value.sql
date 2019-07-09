#=======================================================pro_fetch_target_benefit_value=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- 保证 dim_emp 表 完成
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_target_benefit_value`;
CREATE PROCEDURE pro_fetch_target_benefit_value(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_nextYear datetime )
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE y int(4) DEFAULT date_format(DATE_ADD(p_nextYear, Interval 0 minute),'%Y');
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【明年目标人均效益-业务表】：数据刷新完成');


	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

		REPLACE INTO target_benefit_value (
			target_benefit_value_id,
			customer_id,
			organization_id,
			target_value,
			`year`
		)
		SELECT 
			t.id,
			customerId,
			t1.organization_id,
			target_value,
			`year`
		FROM soure_target_benefit_value t
		INNER JOIN dim_organization t1 on t.organization_key = t1.organization_key
			AND t.customer_id = t1.customer_id
		WHERE t.customer_id = customerId and `year` = y;

		
			IF p_error = 1 THEN  
					ROLLBACK;  
-- 					SET p_message = concat("【每年目标人均效益-业务表】：数据刷新失败。操作用户：", optUserId);
-- 					INSERT INTO db_log 
-- 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'target_benefit_value', p_message, startTime, now(), 'error' );
			ELSE  
					COMMIT;  
-- 					INSERT INTO db_log 
-- 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'target_benefit_value', p_message, startTime, now(), 'sucess' );
			END IF;
	 


END;
-- DELIMITER ;

-- CALL pro_fetch_target_benefit_value('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');

