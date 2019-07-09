DROP PROCEDURE `pro_lv3_all_ZhaoPinKanBan`;
CREATE PROCEDURE `pro_lv3_all_ZhaoPinKanBan`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
top:begin 

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();
 	update sys_sync_status set status = 2 where code = 'pro_lv3_all_ZhaoPinKanBan' ;

   -- 关系表-岗位能力素质
      call pro_fetch_position_quality(p_customer_id,p_user_id);

   -- 匹配表-分数映射
      call pro_fetch_matching_soure(p_customer_id,p_user_id);
 
   -- 业务表-招聘结果
      call pro_fetch_recruit_result(p_customer_id,p_user_id);

   -- 业务表-招聘发布
      call pro_fetch_recruit_public(p_customer_id,p_user_id);
   
   -- 业务表-招聘渠道
      call pro_fetch_recruit_channel(p_customer_id,p_user_id);
 
   -- 业务表-招聘年度费用（年）
      call pro_fetch_recruit_value(p_customer_id,p_user_id);
  
   -- 招聘岗位薪酬
      call pro_fetch_recruit_salary_statistics(p_customer_id,p_user_id);
   
   -- 关系表-外部人才库
      call pro_fetch_out_talent(p_customer_id,p_user_id);
      call pro_fetch_recruitment_process(p_customer_id,p_user_id);

	-- 目前岗位平均薪酬
		CALL pro_recruit_salary_statistics(customerId, optUserId, y);
	-- 更新
	update sys_sync_status set status = 3 where code = 'pro_lv3_all_ZhaoPinKanBan' ;

--	INSERT INTO log 
--	VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_lv3_all_ZhaoPinKanBan', "【招聘看板-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenLiChengBen , showIndex);

end;

