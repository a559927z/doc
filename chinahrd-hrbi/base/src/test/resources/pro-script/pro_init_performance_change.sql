-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：dim_organization、dim_emp、dim_performance、dim_grade表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_performance_change`;
CREATE PROCEDURE pro_fetch_performance_change(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_nowTime datetime)
BEGIN

	 	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE yearMonth int(6) DEFAULT date_format(DATE_ADD(p_nowTime, Interval 0 minute),'%Y%m');

	DECLARE exist int;
	SELECT count(1) AS exist INTO exist 
	FROM performance_change per WHERE per.`year_month` = yearMonth;
	
	IF exist = 0 THEN
			INSERT INTO performance_change(performance_change_id, customer_id, emp_key, emp_id,
						organization_id, organization_name, performance_id, performance_name, grade_id, grade_name,
						organization_parent_id, organization_parent_name,
						score, `year_month`
						)
			SELECT 
				replace(UUID(),'-',''), tt.customer_id,
				t2.emp_key, t2.emp_id,
				t.organization_name, t3.performance_name, t4.grade_name, 
				t.organization_id, t3.performance_id, t4.grade_id,
				t5.organization_id organization_parent_id, t5.organization_name organization_parent_name,
				tt.score, tt.`year_month`
			FROM soure_performance_change tt
			INNER JOIN dim_organization t on t.organization_key = tt.organization_key
					AND t.customer_id = tt.customer_id
					AND now() >= t.effect_date
					AND t.expiry_date IS NULL
					AND t.enabled = 1
			INNER JOIN dim_emp t2 on t2.emp_key = tt.emp_key
					AND t2.customer_id = tt.customer_id
					AND now() >= t2.effect_date
					AND t2.expiry_date IS NULL
					AND t2.enabled = 1
			INNER JOIN dim_performance t3 on t3.performance_key = tt.performance_key
					AND t3.customer_id = tt.customer_id
			INNER JOIN dim_grade t4 on t4.grade_key = tt.grade_key
					AND t4.customer_id = tt.customer_id
			INNER join dim_organization t5 ON t5.full_path = SUBSTRING_INDEX(t.full_path, "_", 2) 
			WHERE tt.customer_id = customerId
				AND	tt.`year_month` = yearMonth
			;
	END IF;


END;
-- DELIMITER ;
