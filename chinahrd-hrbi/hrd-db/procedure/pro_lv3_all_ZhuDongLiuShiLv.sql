DROP procedure if EXISTS `pro_lv3_all_ZhuDongLiuShiLv`;
CREATE PROCEDURE `pro_lv3_all_ZhuDongLiuShiLv`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
TOP:BEGIN 

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();
     
	-- 流失记录
	CALL pro_fetch_run_off_record(customerId,optUserId);
	-- 流失率月度总人数
    CALL pro_fetch_run_off_total(customerId,optUserId);

	-- 更新
	update sys_sync_status set status = 2 where code = 'pro_lv3_all_ZhuDongLiuShiLv' and status = 1;

	-- INSERT INTO log VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_lv3_all_ZhuDongLiuShiLv', "【主动流失率-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenLiChengBen , showIndex);

END;

