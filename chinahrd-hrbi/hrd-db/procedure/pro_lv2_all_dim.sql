drop procedure if exists pro_lv2_all_dim;
CREATE PROCEDURE `pro_lv2_all_dim`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_refresh TIMESTAMP(6))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;

	update sys_sync_status set status = 2 where code = 'pro_lv2_all_dim';
	
	-- 字典表
	CALL pro_fetch_sys_code_item(p_customer_id, p_user_id);	

	-- 机构类型
	CALL pro_fetch_dim_organization_type(customerId,  optUserId, refresh);
	-- 机构
	CALL pro_fetch_dim_organization(customerId, optUserId, refresh);
	-- 机构维-全路径，深度，是否有子节点
	CALL pro_update_dim_organization(customerId, optUserId, refresh);
	-- 主、子序列；职衔；能力层职； 子职；人群范围
	CALL pro_fetch_dim_sequence( customerId, optUserId, refresh);
	CALL pro_fetch_dim_sequence_sub( customerId, optUserId, refresh);
	CALL pro_fetch_dim_job_title(customerId, optUserId, refresh);
	CALL pro_fetch_dim_ability(customerId, optUserId, refresh);
	CALL pro_fetch_dim_ability_lv(customerId, optUserId, refresh);
	CALL pro_fetch_dim_population(customerId, optUserId, refresh);

	-- 岗位 
	CALL pro_fetch_dim_position(customerId, optUserId, refresh);	
	-- 关键人才库
	CALL pro_fetch_dim_key_talent_type(customerId, optUserId, refresh);
	-- 课程
	CALL pro_fetch_dim_course( customerId, optUserId, refresh);	
	-- 课程类型
	CALL pro_fetch_dim_course_type( customerId, optUserId, refresh);	
	-- 工资结构
	CALL pro_fetch_dim_structure(customerId, optUserId, refresh);	
	-- 项目类型
	CALL pro_fetch_dim_project_type(customerId, optUserId, refresh);
	-- 项目投入费用类型  
	CALL pro_fetch_dim_project_input_type(customerId, optUserId, refresh);
	-- 异动类型
	CALL pro_fetch_dim_change_type(customerId, optUserId, refresh);
	-- 招聘渠道
	CALL pro_fetch_dim_channel(customerId, optUserId, refresh);
	-- 离职周期
	CALL pro_fetch_dim_dismission_week(customerId, optUserId, refresh);
	-- 激励要素
	CALL pro_fetch_dim_encourages(customerId, optUserId, refresh);	
	-- 绩效
	CALL pro_fetch_dim_performance(customerId, optUserId, refresh);
	-- 人群
	CALL pro_fetch_dim_population(customerId, optUserId, refresh);
	-- 岗位能力素质维
	CALL pro_fetch_dim_quality(customerId, optUserId, refresh);
	-- 异动类型维
	CALL pro_fetch_dim_checkwork_type(customerId,  optUserId, refresh);
	-- 	证书信息
	CALL pro_fetch_dim_certificate_info(customerId,  optUserId, refresh);
	-- 团队信息
	CALL pro_fetch_dim_sales_team(customerId,  optUserId,  refresh);
	-- 团产品信息
	CALL pro_fetch_dim_sales_product(customerId,  optUserId, refresh);
	-- 离职风险
	CALL pro_fetch_dim_separation_risk(customerId,  optUserId,  refresh);
	-- 流失
	CALL pro_fetch_dim_run_off(customerId, optUserId, refresh);
	-- 满意度分类
	CALL pro_fetch_dim_satfac_genre(customerId,  optUserId, refresh);
	-- 敬业度分类
	CALL pro_fetch_dim_dedicat_genre(customerId,  optUserId, refresh);
	-- 行业维
	CALL pro_fetch_dim_profession(customerId,  optUserId, refresh);
	-- 市
	CALL pro_fetch_dim_city(customerId,  optUserId, refresh);
	-- 省
	CALL pro_fetch_dim_province(customerId,  optUserId, refresh);
	-- 学校
	CALL pro_fetch_matching_school(p_customer_id,p_user_id, refresh);
	-- 分数映射
	CALL pro_fetch_matching_score(p_customer_id,p_user_id, refresh);
	-- 员工能力记录
  	CALL pro_fetch_dim_ability_number(p_customer_id,p_user_id,  refresh);

 	-- 更新
	update sys_sync_status set status = 3 where code = 'pro_lv2_all_dim';
	
END;
