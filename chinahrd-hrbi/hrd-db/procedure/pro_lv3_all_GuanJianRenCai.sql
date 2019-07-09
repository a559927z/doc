drop procedure if exists pro_lv3_all_GuanJianRenCai;
CREATE PROCEDURE `pro_lv3_all_GuanJianRenCai`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
top:begin 
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();
		
  	update sys_sync_status set status = 2 where code = 'pro_lv3_all_GuanJianRenCai';

	-- 是否关键人才
	CALL pro_update_key_talent(customerId, optUserId,startTime);
	
	-- 自动标签
	call pro_fetch_key_talent_tags_auto(p_customer_id,p_user_id,startTime);
	-- 更新
	update sys_sync_status set status = 3 where code = 'pro_lv3_all_GuanJianRenCai' ;

	-- INSERT INTO log VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_lv3_all_GuanJianRenCai', "【关键人才-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenLiChengBen , showIndex);
end;

