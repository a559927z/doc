CREATE PROCEDURE `pro_lv3_all_RenCaiSunYi`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
top:begin 

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();


	SET @RenCaiSunYi = 0;
	
	-- 历史表-机构异动表
	CALL pro_fetch_organization_change(p_customer_id,p_user_id);
	
	CALL pro_fetch_job_change(p_customer_id,p_user_id);

	-- 更新
	update sys_sync_status set status = 2 where code = 'all_RenCaiSunYi' and status = 1;

	INSERT INTO log 
	VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_lv3_all_RenCaiSunYi', "【人才损益-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenCaiSunYi , showIndex);

end;

