-- =======================================pro_fetch_job_change============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：dim_emp、dim_organization、dim_position、dim_ability、dim_ability_lv、dim_sequence表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_job_change`;
CREATE PROCEDURE pro_fetch_job_change(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
 	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

			LB_REPLACE:BEGIN	
					REPLACE INTO job_change(emp_job_change_id, customer_id, emp_key,
						change_date, change_type_id,
						organization_name, position_name, ability_name, job_title_name, ability_lv_name, sequence_name, sequence_sub_name,
						organization_id, position_id, sequence_id, sequence_sub_id, ability_id, job_title_id, ability_lv_id, emp_id, rank_name, note)
					SELECT 
						tt.id,
						tt.customer_id,
						t5.emp_key,
						tt.change_date, tt.change_type_id,
						tt.organization_name, tt.position_name, tt.ability_name, tt.job_title_name, tt.ability_lv_name, tt.sequence_name, tt.sequence_sub_name,
						t.organization_id, t1.position_id, t2.sequence_id, t6.sequence_sub_id, t3.ability_id, t7.job_title_id, t4.ability_lv_id, t5.emp_id, 
						CONCAT(t2.curt_name, t6.curt_name, t3.curt_name, ".", t4.curt_name), tt.note
					FROM soure_job_change tt
					LEFT JOIN dim_organization t on t.organization_key = tt.organization_key
							AND t.customer_id = tt.customer_id
					LEFT JOIN dim_position t1 on t1.position_key = tt.position_key
							AND t1.customer_id = tt.customer_id
					LEFT JOIN dim_sequence t2 on t2.sequence_key = tt.sequence_key
							AND t2.customer_id = tt.customer_id
					LEFT JOIN dim_sequence_sub t6 on t6.sequence_sub_key = tt.sequence_sub_key
							AND t6.customer_id = tt.customer_id
					LEFT JOIN dim_ability t3 on t3.ability_key = tt.ability_key
							AND t3.customer_id = tt.customer_id
					LEFT JOIN dim_ability_lv t4 on t4.ability_lv_key = tt.ability_lv_key
							AND t4.customer_id = tt.customer_id
					LEFT JOIN v_dim_emp t5 on t5.emp_key = tt.emp_key
							AND t5.customer_id = tt.customer_id
					LEFT JOIN dim_job_title t7 on t7.job_title_key = tt.job_title_key
							AND t7.customer_id = tt.customer_id
					WHERE tt.customer_id = customerId
				;

			END LB_REPLACE;


		IF p_error = 1 THEN  
				ROLLBACK;  
		ELSE  
				COMMIT;
				
		END IF;
 
END;
-- DELIMITER ;

CALL pro_fetch_job_change('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');

