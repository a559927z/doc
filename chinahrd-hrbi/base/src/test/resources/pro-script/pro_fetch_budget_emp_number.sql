-- =======================================pro_fetch_budget_emp_number============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：v_dim_emp
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_budget_emp_number`;
CREATE PROCEDURE pro_fetch_budget_emp_number(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_nowTime datetime)
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
-- 		DECLARE `y` INT(4) DEFAULT date_format(DATE_ADD(p_nowTime, Interval 0 minute), '%Y');

		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000) DEFAULT '【编制员工人数\机构负责人-业务表】：数据刷新完成';
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	
		START TRANSACTION;
				REPLACE INTO budget_emp_number(
						budget_emp_number_id,
						customer_id,
						organization_id,
						number,
						`year`
				)
				SELECT id,
						tt.customer_id,
						organization_id,
						number,
						`year`
				FROM soure_budget_emp_number tt
				INNER JOIN dim_organization t on t.organization_key = tt.organization_key AND t.customer_id = tt.customer_id
				WHERE t.customer_id = customerId;
				
-- 				-- 机构负责人
-- 				DELETE FROM organization_emp_relation WHERE customer_id = customerId;
-- 				CALL pro_fetch_organization_emp_relation(customerId, optUserId);
				
-- 				REPLACE INTO organization_emp_relation(
-- 					organization_emp_relation_id,
-- 					customer_id,
-- 					organization_id,
-- 					emp_id
-- 				)
-- 				SELECT replace(UUID(),'-',''),
-- 						tt.customer_id,
-- 						t1.organization_id,
-- 						emp_id
-- 				FROM soure_budget_emp_number tt
-- 				INNER JOIN dim_emp t on t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
-- 				INNER JOIN dim_organization t1 on t1.organization_key = tt.organization_key AND t1.customer_id = tt.customer_id
-- 				WHERE t.customer_id = customerId;


		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【编制员工人数\机构负责人-业务表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'budget_emp_number、organization_emp_relation', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'budget_emp_number、organization_emp_relation', p_message, startTime, now(), 'sucess' );
		END IF;



END;
-- DELIMITER ;

-- TRUNCATE budget_emp_number;
-- TRUNCATE organization_emp_relation;
-- CALL pro_fetch_budget_emp_number('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19',now());
