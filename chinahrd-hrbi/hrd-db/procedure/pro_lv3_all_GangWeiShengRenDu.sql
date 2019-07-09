CREATE PROCEDURE `pro_lv3_all_GangWeiShengRenDu`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
top:begin 
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();
		
    update sys_sync_status set status = 2 where code = 'pro_lv3_all_GangWeiShengRenDu';
	-- 员工岗位素质得分
	CALL pro_fetch_emp_pq_relation(customerId, optUserId);
	
	-- 岗位能力素质
	call pro_fetch_position_quality(p_customer_id,p_user_id);
	-- 更新
	update sys_sync_status set status = 3 where code = 'pro_lv3_all_GangWeiShengRenDu' ;

--	INSERT INTO log 
--	VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_lv3_all_GangWeiShengRenDu', "【岗位胜任度-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenLiChengBen , showIndex);

end;

