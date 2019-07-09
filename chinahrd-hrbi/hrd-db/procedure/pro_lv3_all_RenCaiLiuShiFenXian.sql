drop procedure if exists pro_lv3_all_RenCaiLiuShiFenXian;
CREATE PROCEDURE `pro_lv3_all_RenCaiLiuShiFenXian`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
top:begin 

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();
		
		update sys_sync_status set status = 2 where code = 'pro_lv3_all_RenCaiLiuShiFenXian';

		-- 离职风险评估
		call pro_fetch_dimission_risk(p_customer_id,p_user_id);
  
		-- 离职风险评估细项
		call pro_fetch_dimission_risk_item(p_customer_id,p_user_id);
	
		update sys_sync_status set status = 3 where code = 'pro_lv3_all_RenCaiLiuShiFenXian';

-- INSERT INTO log VALUES(fn_getId(), customerId, optUserId, 'pro_lv3_all_RenCaiLiuShiFenXian', "【主动流失率-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenLiChengBen , showIndex);

end;

