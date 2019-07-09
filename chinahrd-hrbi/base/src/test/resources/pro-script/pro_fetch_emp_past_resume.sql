-- =======================================pro_fetch_emp_past_resume============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：v_dim_emp
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_past_resume`;
CREATE PROCEDURE pro_fetch_emp_past_resume(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
-- 	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
-- 	DECLARE exist int;
-- 	DECLARE startTime TIMESTAMP;

		DELETE FROM emp_past_resume WHERE customer_id = customerId;

		INSERT INTO emp_past_resume(emp_past_resume_id,customer_id, emp_id,
						work_unit,
						department_name,
						position_name,
						bonus_penalty_name,
						witness_name,
						witness_contact_info,
						change_reason,
						entry_date,
						run_off_date)
		SELECT replace(UUID(),'-',''), customerId, emp_id, 
						work_unit,
						department_name,
						tt.position_name,
						bonus_penalty_name,
						witness_name,
						witness_contact_info,
						change_reason,
						tt.entry_date,
						tt.run_off_date
		FROM soure_emp_past_resume tt
		INNER JOIN v_dim_emp t on t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
		WHERE t.customer_id = customerId;

END;
-- DELIMITER ;
