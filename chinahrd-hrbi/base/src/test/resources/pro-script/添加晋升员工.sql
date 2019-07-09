-- 添加晋升员工


-- 员工表
SET @eKey = 'glyang';
	SELECT rank_name FROM v_dim_emp WHERE emp_key = @eKey; -- 先查看当前职级
update soure_v_dim_emp t SET
-- 220e829770b111e58a1708606e0aa89a 1
-- 220f26e470b111e58a1708606e0aa89a 2
-- 220f826a70b111e58a1708606e0aa89a 3
-- 22100f3470b111e58a1708606e0aa89a 4
-- 2210998470b111e58a1708606e0aa89a 5
-- 2210e95870b111e58a1708606e0aa89a 6
-- 221137f870b111e58a1708606e0aa89a 7
-- 221196d270b111e58a1708606e0aa89a 8
	t.ability_lv_id = '221137f870b111e58a1708606e0aa89a', t.ability_lv_name='7级',
	t.rank_name = 'FR3.7'
where emp_key = @eKey and run_off_date is NULL;
SELECT @eKey;

-- SELECT * FROM soure_v_dim_emp WHERE emp_key = @eKey;
CALL pro_fetch_v_dim_emp('b5c9410dc7e4422c8e0189c7f8056b5f', '3cfd3db15ffc4c119e344e82eb8cbb19');
-- SELECT * FROM v_dim_emp WHERE emp_key = @eKey;


-- 工作异动
INSERT INTO soure_job_change (
	id, customer_id, emp_key, change_date, change_type, 
	organization_key, position_key, sequence_key, sequence_sub_key, ability_key, job_title_key, ability_lv_key,
	organization_name, position_name, sequence_name, sequence_sub_name, ability_name, job_title_name, ability_lv_name
)
SELECT 
	REPLACE (uuid(), '-', ''), t.customer_id, emp_key, '2015-7-12', 1,
	organization_key, position_key, sequence_key, sequence_sub_key, ability_key, job_title_key,
	6,
	t.organization_name, t.position_name, t.sequence_name, t.sequence_sub_name, t.ability_name, t.job_title_name,
	"6级"
FROM soure_v_dim_emp t
left JOIN dim_organization t1 on t.organization_id = t1.organization_id
left JOIN dim_position t2 on t.position_id = t2.position_id
left JOIN dim_sequence t3 on t.sequence_id = t3.sequence_id
left JOIN dim_sequence_sub t4 on t.sequence_sub_id = t4.sequence_sub_id
left JOIN dim_ability t5 on t.ability_id = t5.ability_id
left JOIN dim_job_title t6 on t.job_title_id = t6.job_title_id
WHERE t.emp_key = @eKey 
	and run_off_date is NULL;

-- SELECT * FROM soure_job_change WHERE emp_key = @eKey;
CALL pro_fetch_job_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');


-- 绩效
-- SET @rankName = (SELECT rank_name FROM v_dim_emp where emp_key = @eKey);
-- UPDATE soure_performance_change SET rank_name = @rankName WHERE emp_key = @eKey and `year_month` = (SELECT max(`year_month`) from soure_performance_change);
CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-06-10'); -- 绩效信息（周期事件）

SELECT * FROM soure_job_change where  emp_key = 'byao';
SELECT * FROM performance_change where `year_month` = 201506 and emp_key = 'byao'