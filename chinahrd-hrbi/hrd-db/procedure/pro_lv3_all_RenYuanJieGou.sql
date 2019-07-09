CREATE PROCEDURE `pro_lv3_all_RenYuanJieGou`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
top:begin 



		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();
    
    call pro_fetch_emp_personality(p_customer_id,p_user_id);


	-- 更新
	update sys_sync_status set status = 2 where code = 'all_RenYuanJieGou' and status = 1;

	INSERT INTO log 
	VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_lv3_all_RenYuanJieGou', "【人员结构-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenLiChengBen , showIndex);

end;

