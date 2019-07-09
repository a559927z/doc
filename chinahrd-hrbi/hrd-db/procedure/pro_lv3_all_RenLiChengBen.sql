drop procedure if exists pro_lv3_all_RenLiChengBen;
CREATE PROCEDURE `pro_lv3_all_RenLiChengBen`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
top:begin 

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE curDate TIMESTAMP DEFAULT now();

	DECLARE p_ym INT(6) DEFAULT fn_getYM();
	DECLARE y INT DEFAULT SUBSTR(p_ym FROM 1 FOR 4);
	DECLARE m INT DEFAULT SUBSTR(p_ym FROM 5 FOR 6);

	update sys_sync_status set status = 2 where code = 'pro_lv3_all_RenLiChengBen';

	-- 人力成本结构
	CALL pro_fetch_manpower_cost_item(customerId, optUserId, p_ym);
	-- 人力成本
	CALL pro_fetch_manpower_cost(customerId, optUserId, curDate);
	
	CALL pro_fetch_manpower_cost_value(customerId, optUserId);

	-- 更新
	update sys_sync_status set status = 3 where code = 'pro_lv3_all_RenLiChengBen';

	
	-- INSERT INTO log VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_lv3_all_RenLiChengBen', "【人力成本-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenLiChengBen , showIndex);

end;

