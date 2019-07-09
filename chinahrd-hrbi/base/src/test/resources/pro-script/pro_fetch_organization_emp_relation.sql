-- =======================================pro_fetch_organization_emp_relation============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：dim_emp、dim_organization、dim_position、dim_ability、dim_ability_lv、dim_sequence表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_organization_emp_relation`;
CREATE PROCEDURE pro_fetch_organization_emp_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
 	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【机构负责人-中间表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

			LB_REPLACE:BEGIN
					REPLACE INTO organization_emp_relation(
						organization_emp_relation_id,
						customer_id,
						organization_id,
						emp_id,
						code_item_id,
						refresh_date)
					SELECT 
						tt.id, customerId,
						t.organization_id, t5.emp_id, t6.code_item_id, startTime
					FROM soure_organization_emp_relation tt
					LEFT JOIN dim_organization t on t.organization_key = tt.organization_key
							AND t.customer_id = tt.customer_id
					LEFT JOIN v_dim_emp t5 on t5.emp_key = tt.emp_key
							AND t5.customer_id = tt.customer_id AND t5.run_off_date IS NULL
					LEFT JOIN soure_code_item t6 on t6.code_item_key = tt.type 
							AND t6.code_group_id = 'personType' and t6.customer_id = t5.customer_id
				;
			END LB_REPLACE;



		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【机构负责人-中间表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'organization_emp_relation', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'organization_emp_relation', p_message, startTime, now(), 'sucess' );
		END IF;
 
END;
-- DELIMITER ;
-- DELETE FROM organization_emp_relation WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
-- CALL pro_fetch_organization_emp_relation('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');
