drop procedure if exists pro_lv3_all_XiaoShouKanBan;
CREATE PROCEDURE `pro_lv3_all_XiaoShouKanBan`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
top:begin 

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();
 		update sys_sync_status set status = 2 where code = 'pro_lv3_all_XiaoShouKanBan' ;

		-- 销售人员信息
		CALL pro_fetch_sales_emp(p_customer_id,p_user_id);
		-- 团队考核
		CALL pro_fetch_sales_team_target(p_customer_id,p_user_id);
		-- 产品考核
	   	CALL pro_fetch_sales_pro_target(p_customer_id,p_user_id);
		-- 机构考核
		CALL pro_fetch_sales_org_target(p_customer_id,p_user_id);
		-- 员工考核
		CALL pro_fetch_sales_emp_target(p_customer_id,p_user_id);
		-- 员工销售排名
	   	CALL pro_fetch_sales_emp_rank(p_customer_id,p_user_id);
		-- 团队销售排名
	   	CALL pro_fetch_sales_team_rank(p_customer_id,p_user_id);
		-- 业务能力考核
	   	CALL pro_fetch_sales_ability(p_customer_id,p_user_id);
		-- 销售明细
	   	CALL pro_fetch_sales_detail(p_customer_id,p_user_id);
		-- 历史销售明细
	   	CALL pro_fetch_history_sales_detail(p_customer_id,p_user_id);
		-- 机构日统计
	   	CALL pro_fetch_sales_org_day(p_customer_id,p_user_id);
		-- 员工销售月统计，机构产品月销售统计，机构产品月销售统计
	   	CALL pro_fetch_sales_emp_org_pro_month(p_customer_id,p_user_id);
		-- 员工销售月统计
	   	CALL pro_fetch_sales_emp_month(p_customer_id,p_user_id);
	   	-- 机构销售月统计
	   	CALL pro_fetch_sales_org_month(p_customer_id,p_user_id);
	   	-- 机构产品销售月统计
	   	CALL pro_fetch_sales_org_prod_month(p_customer_id,p_user_id);


	-- 更新
	update sys_sync_status set status = 3 where code = 'pro_lv3_all_XiaoShouKanBan';
	
--	INSERT INTO log 
--	VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_lv3_all_XiaoShouKanBan', "【`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenLiChengBen , showIndex);

end;

