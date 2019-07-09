CREATE PROCEDURE `pro_lv3_all_JinJiKanBan`(in customerId VARCHAR(32), in optUserId VARCHAR(32))
begin 

		DECLARE customerId VARCHAR(32) DEFAULT customerId;
		DECLARE optUserId VARCHAR(32) DEFAULT optUserId;
		DECLARE startTime TIMESTAMP DEFAULT now();

	update sys_sync_status set status = 2 where code = 'pro_lv3_all_JinJiKanBan' ;
	
		-- 晋级要素方案 
		CALL pro_fetch_promotion_element_scheme(customerId,optUserId);
  
		-- 职级晋升方案
		CALL pro_fetch_promotion_plan(customerId,optUserId); -- 条件符合占比
 
    	-- 员工岗位能力评价
		CALL pro_fetch_emp_pq_eval_relation(customerId,optUserId);

		-- 员工占比统计
		CALL pro_fetch_promotion_total(customerId,optUserId);
		-- 更新:员工占比统计占比-条件符合占比 (java定时器完成)
--		CALL pro_update_promotion_total(customerId,optUserId);
    
		
		
		-- 添加员工占比分析：司龄分析，能力评价，资格证书，资格证书类型，绩效
--		CALL pro_fetch_promotion_analysis(customerId,optUserId);
		
    	
    -- 员工晋级结果
		CALL pro_fetch_promotion_results(customerId,optUserId);
		

	
	update sys_sync_status set status = 3 where code = 'pro_lv3_all_JinJiKanBan' ;

	-- INSERT INTO log VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_lv3_all_JinJiKanBan', "【晋升指标-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenLiChengBen , showIndex);

end;

