CREATE PROCEDURE `pro_lv3_all_PeiXunKanBan`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
top:begin 

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		-- 培训经历
		CALL pro_fetch_emp_train_experience(p_customer_id,p_user_id);
		-- 培训实际花费用
		CALL pro_fetch_train_outlay(p_customer_id,p_user_id);
		-- 培训年度预算费用（年）
		CALL pro_fetch_train_value(p_customer_id,p_user_id);
		-- 培训计划
		CALL pro_fetch_train_plan(p_customer_id,p_user_id);
		-- 课程安排记录
		CALL pro_fetch_course_record(p_customer_id,p_user_id);
		-- 讲师
		CALL pro_fetch_lecturer(p_customer_id,p_user_id);
		-- 讲师设计课
		CALL pro_fetch_lecturer_course_design(p_customer_id,p_user_id);
		-- 讲师授课
		CALL pro_fetch_lecturer_course_speak(p_customer_id,p_user_id);
		
		-- 培训满意度
		CALL pro_fetch_train_satfac(p_customer_id,p_user_id);

		
	-- 更新
	update sys_sync_status set status = 2 where code = 'all_PeiXunKanBan' and status = 1;

	INSERT INTO log 
	VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_lv3_all_PeiXunKanBan', "【培训看板-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenLiChengBen , showIndex);

end;

