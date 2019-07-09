drop procedure if exists pro_lv1_all;
CREATE PROCEDURE `pro_lv1_all`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32) )
BEGIN

	/*
	 * sys_sync_status
	 * 状态：
	 *  1-未开始  2-执行中  3-完成  4-报错
	 */
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	
	DECLARE getRefresh TIMESTAMP(6) DEFAULT	'2015-12-18 03:00:00';	-- now();
	-- 时间范围都拟当前时间-1天处理了 -- 
	DECLARE getY VARCHAR(4) DEFAULT 		'2015';					-- fn_getY();
	DECLARE getYM VARCHAR(6) DEFAULT 		'201512';				-- fn_getYM();
	DECLARE getYMD VARCHAR(8) DEFAULT 		'20151217';				-- fn_getYMD();
	DECLARE getYMD_line VARCHAR(8) DEFAULT	'2015-12-17';			-- fn_getYMD_li();
	-- 时间范围都拟当前时间-1天处理了 -- 
		
	
	-- 更新时间
	DECLARE refresh TIMESTAMP DEFAULT (SELECT FROM_UNIXTIME(unix_timestamp(now()), '%Y-%m-%d %H:%i:%S'));
	-- 定义事务回滚
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
	BEGIN 
		SHOW ERRORS;
		-- 所有过程-报错
		update sys_sync_status set status = 4 where code="pro_lv1_all";
		-- CALL ROLLBACK(refdate);
		ROLLBACK; 
	END;
	
	-- 所有过程-执行中
	update sys_sync_status set status = 2 where code="pro_lv1_all";
	
	-- 维度表
	CALL pro_lv2_all_dim(customerId, optUserId, getRefresh);
	-- 基本表
	CALL pro_lv2_all_base(customerId, optUserId, getRefresh, getYM);
	-- 指标 
	CALL pro_lv2_all_quota(customerId, optUserId, getRefresh, getYM);
	
	-- 所有过程-完成
	update sys_sync_status set status = 3 where code="pro_lv1_all";
	
	COMMIT;
END;
