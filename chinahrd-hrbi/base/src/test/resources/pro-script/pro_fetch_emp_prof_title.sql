-- =======================================pro_fetch_emp_prof_title============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：v_dim_emp
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_prof_title`;
CREATE PROCEDURE pro_fetch_emp_prof_title(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
-- 	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
-- 	DECLARE exist int;
-- 	DECLARE startTime TIMESTAMP;

		DELETE FROM emp_prof_title WHERE customer_id = customerId;

		INSERT INTO emp_prof_title(
				emp_bonus_penalty_id,
				customer_id,
				emp_id,
				bonus_penalty_name,
				type,
				type_name,
				grant_unit,
				witness_name
		)
		SELECT replace(UUID(),'-',''), customerId, emp_id,
				gain_date,
				emp_prof_title_name,
				prof_lv,
				award_unit,
				effect_date
		FROM soure_emp_prof_title tt
		INNER JOIN v_dim_emp t on t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
		WHERE t.customer_id = customerId;


END;
-- DELIMITER ;
