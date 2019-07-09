-- 绩效信息
DROP PROCEDURE IF EXISTS pro_fetch_performance_change;
CREATE PROCEDURE `pro_fetch_performance_change`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【历史表-绩效信息】';

    INSERT INTO performance_change(
    	performance_change_id, customer_id,emp_id,emp_key,organization_parent_id,organization_parent_name,organization_id,organization_name,position_id,
    	position_name,performance_id,performance_name,
		rank_name,rank_name_past,
		type,`year_month`)
    SELECT
    	performance_change_id, 1,emp_id,emp_key,organization_parent_id,organzation_parent_name,organization_id,organization_name,position_id,
    	position_name,performance_id,performance_name,
		rank_name,-- fn_getRank(emp_id),	-- 现在
		rank_name_past,-- rank_name_past,	-- 上一次
		type,`year_month`
    FROM `mup-source`.source_performance_change t
	ON DUPLICATE KEY UPDATE
		emp_id = t.emp_id,
		emp_key = t.emp_key,
		organization_parent_id = t.organization_parent_id,
		organization_parent_name = t.organzation_parent_name,
		organization_id = t.organization_id,
		organization_name = t.organization_name,
		position_id = t.position_id,
		position_name = t.position_name,
		performance_id = t.performance_id,
		performance_name = t.performance_name,
		rank_name = t.rank_name,
		rank_name_past = t.rank_name_past,
		type = t.type,
		`year_month` = t.`year_month`
	;

	-- truncate table `mup-source`.source_performance_change;
END;