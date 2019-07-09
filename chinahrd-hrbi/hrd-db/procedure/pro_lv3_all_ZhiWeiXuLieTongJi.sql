drop procedure `pro_lv3_all_ZhiWeiXuLie`;
CREATE PROCEDURE `pro_lv3_all_ZhiWeiXuLie`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
top:begin 

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();
    
	update sys_sync_status set status = 2 where code = 'pro_lv3_all_ZhiWeiXuLie';
		CALL pro_fetch_job_relation(customerId, optUserId);

	-- 更新
	update sys_sync_status set status = 3 where code = 'pro_lv3_all_ZhiWeiXuLie';

	-- INSERT INTO log VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_lv3_all_ZhiWeiXuLie', "【职位序列-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenLiChengBen , showIndex);

end;

