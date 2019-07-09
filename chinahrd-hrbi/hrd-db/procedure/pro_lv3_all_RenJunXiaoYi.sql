drop PROCEDURE if EXISTS pro_lv3_all_RenJunXiaoYi;
CREATE PROCEDURE `pro_lv3_all_RenJunXiaoYi`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_refresh TIMESTAMP(6))
top:begin 

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();
		DECLARE refresh TIMESTAMP DEFAULT p_refresh;

	update sys_sync_status set status = 2 where code = 'pro_lv3_all_RenJunXiaoYi';
		
		-- 明年目标人均效益
		CALL pro_fetch_target_benefit_value(customerId, optUserId, refresh);
		-- 营业利润
		CALL pro_fetch_trade_profit( customerId, optUserId, refresh);
		-- 等效员工数-变化幅度
		CALL pro_update_ffRange(customerId, optUserId, refresh);
		
	-- 更新
	update sys_sync_status set status = 3 where code = 'pro_lv3_all_RenJunXiaoYi';
	
	-- INSERT INTO log VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_lv3_all_RenJunXiaoYi', "【人均效益-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenJunXiaoYi_error_mgs , showIndex);

end;
