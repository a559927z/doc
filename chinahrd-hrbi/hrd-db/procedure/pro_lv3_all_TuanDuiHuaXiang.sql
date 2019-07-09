CREATE PROCEDURE `pro_lv3_all_TuanDuiHuaXiang`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_refresh TIMESTAMP(6))
top:begin 

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【关系表-职位序列关系】';
	
   	update sys_sync_status set status = 2 where code = 'pro_lv3_all_TuanDuiHuaXiang' ;

	CALL pro_fetch_emp_personality(customerId, optUserId, p_refresh);
	
	-- 更新
   	update sys_sync_status set status = 3 where code = 'pro_lv3_all_TuanDuiHuaXiang' ;

--	INSERT INTO log 
--	VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_lv3_all_TuanDuiHuaXiang', "【职能序列-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenLiChengBen , showIndex);

end;
