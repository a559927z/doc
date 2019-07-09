DROP PROCEDURE IF EXISTS pro_lv3_all_RenCaiPouXiang;
CREATE PROCEDURE `pro_lv3_all_RenCaiPouXiang`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
top:begin 

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
   
		-- 360测评报告
		CALL pro_fetch_360_report(p_customer_id,p_user_id);
		-- 360测评时段
		CALL pro_fetch_360_time(p_customer_id,p_user_id);

	-- 更新
	update sys_sync_status set status = 2 where code = 'all_RenCaiPouXiang' and status = 1;

end;

