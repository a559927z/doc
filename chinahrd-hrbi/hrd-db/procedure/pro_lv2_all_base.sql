drop procedure if exists pro_lv2_all_base;
CREATE PROCEDURE `pro_lv2_all_base`(
	in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), 
	in p_refresh TIMESTAMP(6),
	in p_getYM VARCHAR(6)
	)
BEGIN
	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	
	update sys_sync_status set status = 2 where code = 'pro_lv2_all_base';
	
	-- 员工表
	CALL pro_fetch_v_dim_emp(customerId, optUserId, refresh);
	-- 年龄，司龄，rank_name
	CALL pro_update_v_dim_emp(customerId, optUserId);
	-- 汇报链
	CALL pro_update_emp_report_relation(customerId, optUserId, refresh);
	-- 我的下属（子孙）
	CALL pro_fetch_underling(customerId, optUserId);
	
	
	-- 家庭关系
	CALL pro_fetch_emp_family(customerId, optUserId);
	-- 所获职称
	CALL pro_fetch_emp_prof_title(customerId, optUserId);
	-- 教育背景
	CALL pro_fetch_emp_edu(customerId, optUserId);
	-- 奖惩
	CALL pro_fetch_emp_bonus_penalty(customerId, optUserId);
	-- 联系人信息
	CALL pro_fetch_emp_contact_person(customerId, optUserId);
	-- 过往履历（不是简历）
	CALL pro_fetch_emp_past_resume(customerId, optUserId);
	-- 证书信息
	CALL pro_fetch_emp_certificate_info(customerId, optUserId);
	
	-- 人群-日
	CALL pro_fetch_population_relation(customerId, optUserId);
	-- 人群-月（本月具备昨天最新数据）
  	CALL pro_fetch_population_relation_month(p_customer_id,p_user_id);
  	
	-- 机构负责人
	CALL pro_fetch_organization_emp_relation(customerId, optUserId, refresh);
	-- 机构编制数-年
	CALL pro_fetch_budget_emp_number(customerId, optUserId);
	-- 岗位编制数
	CALL pro_fetch_budget_position_number(customerId, optUserId, refresh);
	
	-- 机构-日月切片（本月具备昨天最新数据）
	CALL pro_fetch_history_dim_organization_month(customerId,  optUserId, refresh);
	-- 员工月切片
	CALL pro_fetch_dim_emp_month(customerId,  optUserId, refresh);
	-- 日、月表总人数（本月具备昨天最新数据）
	CALL pro_fetch_history_emp_count(customerId, optUserId, refresh);
	
	-- 等效全职员工数
	CALL pro_fetch_fact_fte(customerId,  optUserId);
	-- 机构KPI
	CALL pro_fetch_dept_kpi(customerId, optUserId);
	-- 行业指标值
	CALL pro_fetch_profession_value(customerId, optUserId, refresh);

	update sys_sync_status set status = 3 where code = 'pro_lv2_all_base';
END;











