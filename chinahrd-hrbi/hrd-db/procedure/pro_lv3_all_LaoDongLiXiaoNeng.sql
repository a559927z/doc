CREATE PROCEDURE `pro_lv3_all_LaoDongLiXiaoNeng`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
TOP:BEGIN 

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	update sys_sync_status set status = 2 where code = 'pro_lv3_all_LaoDongLiXiaoNeng';
		
	-- 数据导入权限(注：这由系统提交excel入库)
	--	CALL pro_fetch_permiss_import(p_customer_id,p_user_id);
	
	-- 业务表-打卡机(注：不在产标准里维护)
	--	CALL pro_fetch_emp_attendance(p_customer_id,p_user_id);
	
	-- 业务表-出勤记录
	CALL pro_fetch_emp_attendance_day(p_customer_id,p_user_id);
	
	-- 业务表-其他考勤记录
	CALL pro_fetch_emp_other_day(p_customer_id,p_user_id);
	
	-- 业务表-加班记录
	CALL pro_fetch_emp_overtime_day(p_customer_id,p_user_id);
	
	-- 应出勤记录 (注：在sysJob定时器维护数据)
	--	call pro_fetch_theory_attendance(p_customer_id,p_user_id);
	
	-- 业务表-考勤记录月
	CALL pro_fetch_emp_attendance_month(p_customer_id,p_user_id, null);
	

	
	update sys_sync_status set status = 3 where code = 'pro_lv3_all_LaoDongLiXiaoNeng';

	-- INSERT INTO log VALUES(fn_getId(), customerId, optUserId, 'pro_lv3_all_LaoDongLiXiaoNeng', "【劳动力效能-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenLiChengBen , showIndex);

END;

