-- =======================================pro_fetch_job_relation============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：v_dim_emp
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_job_relation`;
CREATE PROCEDURE pro_fetch_job_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
-- 	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
-- 	DECLARE exist int;
-- 	DECLARE startTime TIMESTAMP;

-- 		DELETE FROM job_relation WHERE customer_id = customerId;

		REPLACE INTO job_relation(
				job_relation_id, customer_id,
				sequence_id,
				sequence_sub_id,
				ability_id,
				ability_lv_id,
				job_title_id,
				`year`,
				type
		)
		SELECT id, customerId,
				t.sequence_id,
				t1.sequence_sub_id,
				t2.ability_id,
				t3.ability_lv_id,
				t4.job_title_id, -- 职衔
				tt.`year`,
				tt.type
		FROM soure_job_relation tt
		left JOIN dim_sequence t on t.sequence_key = tt.sequence_key AND t.customer_id = tt.customer_id
		left JOIN dim_sequence_sub t1 on t1.sequence_sub_key = tt.sequence_sub_key AND t1.customer_id = tt.customer_id
		left JOIN dim_ability t2 on t2.ability_key = tt.ability_key AND t2.customer_id = tt.customer_id 
						and t2.type = tt.type
		left JOIN dim_ability_lv t3 on t3.ability_lv_key = tt.ability_lv_key AND t3.customer_id = tt.customer_id
		left JOIN dim_job_title t4 on t4.job_title_key = tt.job_title_key AND t4.customer_id = tt.customer_id
		WHERE t.customer_id = customerId;


END;
-- DELIMITER ;

 CALL pro_fetch_job_relation( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'); -- 职位关系
