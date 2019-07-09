CREATE PROCEDURE `pro_lv3_all_YuanGongJiXiao`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
TOP:BEGIN 

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_message VARCHAR(10000) DEFAULT '【关系表-员工绩效】';
   
	-- 更新-执行中
	update sys_sync_status set status = 2 where code = 'pro_lv3_all_YuanGongJiXiao';
	
	-- 绩效信息
	CALL pro_fetch_performance_change(customerId, optUserId);
	
	-- 更新-完成
	update sys_sync_status set status = 3 where code = 'pro_lv3_all_YuanGongJiXiao';

--	INSERT INTO log VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_lv3_all_YuanGongJiXiao', "【员工绩效-子表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenLiChengBen , showIndex);

END;
