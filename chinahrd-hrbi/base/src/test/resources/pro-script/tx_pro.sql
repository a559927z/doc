	SET userId = (SELECT du.user_id FROM dim_user du WHERE du.user_name = 'jxzhang' and du.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f');

	CALL pro_fetch_dim_ability('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19','ability');	-- 层级
	CALL pro_fetch_dim_ability_lv('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19','ability_lv');	-- 职级
	CALL pro_fetch_dim_key_talent_type( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', 'keyTalent');	-- 人员类别
	CALL pro_fetch_dim_performance('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', 'performance');	-- 绩效
	CALL pro_fetch_dim_grade('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19','grade');	-- 等级
	CALL pro_fetch_dim_emp('b5c9410dc7e4422c8e0189c7f8056b5f', '3cfd3db15ffc4c119e344e82eb8cbb19');
	CALL pro_fetch_dim_user('b5c9410dc7e4422c8e0189c7f8056b5f', '3cfd3db15ffc4c119e344e82eb8cbb19');

	
	CALL pro_fetch_dim_role( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');
	
  CALL pro_fetch_dim_organization_type( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');
  CALL pro_fetch_organization( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');
-- 	CALL pro_init_organization('demo');		-- 初始机构，全路径、是否有子节点

	CALL pro_fetch_dim_sequence( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', 'sequence');	-- 序列
	CALL pro_fetch_dim_sequence_sub( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', 'sequence_sub');	-- 子序列
	CALL pro_fetch_dim_position( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');
	CALL pro_fetch_emp_position_relation( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');

	
	CALL pro_fetch_job_relation( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'); -- 职位关系

	

	-- 关系表
	CALL pro_fetch_user_role_relation( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');
	CALL pro_fetch_role_organization_relation( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');

-- 业务表-生产力-------------------------------------------------------------------------- 
	CALL pro_fetch_emp_overtime( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');	-- 加班时间
	CALL pro_fetch_trade_profit( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');	-- 营业利润
	CALL pro_init_factfte(DATE_ADD(now(), Interval -1 month), 'b5c9410dc7e4422c8e0189c7f8056b5f',);	-- 人均效益(事件调用)
			CALL pro_init_factfte('2013-01-11','demo');
			CALL pro_init_factfte('2013-02-11','demo');
			CALL pro_init_factfte('2013-03-11','demo');
			CALL pro_init_factfte('2013-04-11','demo');
			CALL pro_init_factfte('2013-05-11','demo');
			CALL pro_init_factfte('2013-06-11','demo');
			CALL pro_init_factfte('2013-07-11','demo');
			CALL pro_init_factfte('2013-08-11','demo');
			CALL pro_init_factfte('2013-09-11','demo');
			CALL pro_init_factfte('2013-10-11','demo');
			CALL pro_init_factfte('2013-11-11','demo');
			CALL pro_init_factfte('2013-12-11','demo');
			CALL pro_init_factfte('2014-01-11','demo');
			CALL pro_init_factfte('2014-02-11','demo');
			CALL pro_init_factfte('2014-03-11','demo');
			CALL pro_init_factfte('2014-04-11','demo');
			CALL pro_init_factfte('2014-05-11','demo');
			CALL pro_init_factfte('2014-06-11','demo');
			CALL pro_init_factfte('2014-07-11','demo');
			CALL pro_init_factfte('2014-08-11','demo');
			CALL pro_init_factfte('2014-09-11','demo');
			CALL pro_init_factfte('2014-10-11','demo');
			CALL pro_init_factfte('2014-11-11','demo');
			CALL pro_init_factfte('2014-12-11','demo');
			CALL pro_init_factfte('2015-01-11','demo');
			CALL pro_init_factfte('2015-02-11','demo');
			CALL pro_init_factfte('2015-03-11','demo');
			CALL pro_init_factfte('2015-04-11','demo');
			CALL pro_init_factfte('2015-05-11','demo');
			CALL pro_init_factfte('2015-06-11','demo');
			CALL pro_init_factfte('2015-07-11','demo');
			CALL pro_init_factfte('2015-08-11','demo');
			CALL pro_init_factfte('2015-09-11','demo');

-- 业务表-驱动力--------------------------------------------------------------------------
	CALL pro_fetch_dim_separation_risk( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');	-- 绩效
	CALL pro_fetch_emp_relation(  'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');		-- 员工链
	CALL pro_fetch_dim_run_off( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');  		-- 流失
	CALL pro_fetch_run_off_record('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'); -- 流失记录

	CALL pro_fetch_emp_status('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');	-- 员工状态

		-- 2013
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-05-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-05-31', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-06-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-06-30', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-07-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-07-31', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-08-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-08-31', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-09-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-09-30', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-10-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-10-31', 0); 
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-11-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-11-30', 0); 
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-12-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-12-31', 0); 
		-- 2014
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-01-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-01-31', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-02-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-02-28', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-03-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-03-31', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-04-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-04-30', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-05-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-05-31', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-06-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-06-30', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-07-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-07-31', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-08-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-08-31', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-09-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-09-30', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-10-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-10-31', 0); 
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-11-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-11-30', 0); 
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-12-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-12-31', 0); 
		-- 2015
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-01-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-01-31', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-02-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-02-28', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-03-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-03-31', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-04-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-04-30', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-05-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-05-31', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-06-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-06-30', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-07-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-07-31', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-08-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-08-31', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-09-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-09-30', 0);
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-10-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-10-31', 0); 

-- 业务表-人才剖像--------------------------------------------------------------------------
		CALL pro_fetch_job_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'); -- 工作异动（每晚事件）

		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2010-06-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2010-12-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2011-06-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2011-12-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2012-06-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2012-12-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-06-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-12-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-06-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-12-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-06-10'); -- 绩效信息（周期事件）

-- 快照表-员工视图
		CALL pro_fetch_v_dim_emp('b5c9410dc7e4422c8e0189c7f8056b5f', '3cfd3db15ffc4c119e344e82eb8cbb19');


	CALL pro_fetch_evaluation_report('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'); -- 360测评
	CALL pro_fetch_emp_train_experience('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'); -- 培训经历
	CALL pro_fetch_emp_past_resume('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'); -- 过往履历
	CALL pro_fetch_emp_edu('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'); -- 教育背景
	CALL pro_fetch_emp_family('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'); -- 家庭关系
	CALL pro_fetch_emp_bonus_penalty('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'); -- 奖惩信息
	CALL pro_fetch_emp_prof_title('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'); -- 所获职称
	CALL pro_fetch_emp_contact_person('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'); -- 联系人

-- 业务表-组织机构（编制与空缺）organization_emp_relation--------------------------------------------------------------------------
	CALL pro_fetch_budget_emp_number('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2010-01-10'); -- 编制员工人数（每年事件）
	CALL pro_fetch_budget_emp_number('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2011-01-10'); -- 编制员工人数（每年事件）
	CALL pro_fetch_budget_emp_number('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2012-01-10'); -- 编制员工人数（每年事件）
	CALL pro_fetch_budget_emp_number('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-01-10'); -- 编制员工人数（每年事件）
	CALL pro_fetch_budget_emp_number('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-01-10'); -- 编制员工人数（每年事件）
	CALL pro_fetch_budget_emp_number('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-01-10'); -- 编制员工人数（每年事件）



-- 管理者首页--------------------------------------------------------------------------
-- 管理者首页-预警
	CALL pro_fetch_warn_mgr('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');













DELETE FROM dim_ability WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM dim_key_talent_type WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM dim_performance WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM dim_emp WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM dim_user WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM dim_role WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM dim_organization_type WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM dim_organization WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM dim_sequence WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM dim_sequence_sub WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';

DELETE FROM dim_position WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM emp_position_relation WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM user_role_relation WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM role_organization_relation WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM emp_overtime WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM trade_profit WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
-- DELETE FROM fact_fte WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM dim_separation_risk WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM emp_relation WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM dim_run_off WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM monthly_emp_count WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM job_change WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM performance_change WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM evaluation_report WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM emp_train_experience WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
DELETE FROM emp_past_resume WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';



