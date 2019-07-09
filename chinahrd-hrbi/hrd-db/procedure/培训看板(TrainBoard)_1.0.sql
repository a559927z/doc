-- 培训实际花费用
DROP PROCEDURE IF EXISTS pro_fetch_train_outlay;
CREATE  PROCEDURE `pro_fetch_train_outlay`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-培训实际花费用】';

	-- 每一次花费都记录下来
	INSERT INTO train_outlay(train_outlay_id,customer_id,organization_id,outlay,date,note,`year`)
	SELECT train_outlay_id,customerId,organization_id,outlay,date,note,`year`
	FROM `mup-source`.source_train_outlay a
	ON DUPLICATE KEY UPDATE
		organization_id=a.organization_id,
		outlay=a.outlay,
		date=a.date,
		note=a.note,
		`year`=a.`year`;
END;


-- 培训年度预算费用（年）
drop procedure if exists pro_fetch_train_value;
CREATE  PROCEDURE `pro_fetch_train_value`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-培训年度预算费用（年）】';

--	DECLARE p_day date DEFAULT fn_getYMD();
--	DECLARE p_year int DEFAULT fn_getY();
--	DECLARE p_total int;
--	-- 检查当前年有没有数据
--	SELECT count(*) INTO _toatl FROM train_value WHERE `year`= p_year;
--	IF _toatl = 0 THEN
		INSERT INTO train_value(train_value_id,customer_id,organization_id,budget_value,`year`)
	  	SELECT train_value_id,customerId,organization_id,budget_value,`year`
	  	FROM `mup-source`.source_train_value a
	  	ON DUPLICATE KEY UPDATE
			organization_id=a.organization_id,
			budget_value=a.budget_value,
			`year`=a.`year`;
--	END IF;

end;

-- 讲师授课
DROP PROCEDURE IF EXISTS pro_fetch_lecturer_course_speak;
CREATE PROCEDURE `pro_fetch_lecturer_course_speak`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【关系表-讲师授课】';
  
	INSERT INTO lecturer_course_speak(
		lecturer_course_speak_id,customer_id,lecturer_id,course_id,start_date,end_date,`year`)
	SELECT 
		lecturer_course_speak_id,customerId,lecturer_id,course_id,start_date,end_date,`year`
	FROM `mup-source`.source_lecturer_course_speak a
	ON DUPLICATE KEY UPDATE
		lecturer_id=a.lecturer_id,
		course_id=a.course_id,
		start_date=a.start_date,
		end_date=a.end_date,
		`year`=a.`year`;
END;

-- 培训计划
drop procedure if exists pro_fetch_train_plan;
CREATE PROCEDURE `pro_fetch_train_plan`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-培训计划】';

	INSERT INTO train_plan(
		train_plan_id,customer_id,organization_id,train_name,train_num,date,start_date,end_date,`year`,status)
	SELECT
		train_plan_id,customerId,organization_id,train_name,train_num,date,start_date,end_date,`year`,status
	FROM `mup-source`.source_train_plan a
	ON DUPLICATE KEY UPDATE
		organization_id=a.organization_id,
		train_name=a.train_name,
		train_num=a.train_num,
		date=a.date,
		start_date=a.start_date,
		end_date=a.end_date,
		`year`=a.`year`,
		status=a.status
	;
end;

-- 培训满意度
drop procedure if exists pro_fetch_train_satfac;
CREATE  PROCEDURE `pro_fetch_train_satfac`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;

	INSERT INTO train_satfac (
		train_satfac_id, customer_id, organization_id, soure, `year`
	)
	SELECT
		train_satfac_id, customerId, organization_id, soure, `year`
	FROM `mup-source`.source_train_satfac t
	WHERE t.customer_id = customerId
	ON DUPLICATE KEY UPDATE
		train_satfac_id    =t.train_satfac_id,
		customer_id        =t.customer_id    ,
		organization_id    =t.organization_id,
		soure              =t.soure          ,
		year               =t.year           
	;
END;



-- 讲师设计课
DROP PROCEDURE IF EXISTS pro_fetch_lecturer_course_design;
CREATE  PROCEDURE `pro_fetch_lecturer_course_design`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
begin
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【关系表-讲师设计课】';

	INSERT INTO lecturer_course_design(
		lecturer_course_design_id,customer_id,lecturer_id,course_id)
	SELECT 
		lecturer_course_design_id,customerId,lecturer_id,course_id 
	FROM `mup-source`.source_lecturer_course_design a
	ON DUPLICATE KEY UPDATE
		lecturer_id=a.lecturer_id,
		course_id=a.course_id;
end;

-- 讲师
DROP PROCEDURE IF EXISTS pro_fetch_lecturer;
CREATE  PROCEDURE `pro_fetch_lecturer`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-讲师】';

	INSERT INTO lecturer(
		lecturer_id,customer_id,emp_id,lecturer_name,level_id,type)
	SELECT 
		lecturer_id,customerId,emp_id,lecturer_name,level_id,type 
	FROM `mup-source`.source_lecturer a
	ON DUPLICATE KEY UPDATE
		emp_id=a.emp_id,
		lecturer_name=a.lecturer_name,
		level_id=a.level_id,
		type=a.type;
END;


-- 课程安排记录
DROP PROCEDURE IF EXISTS pro_fetch_course_record;
CREATE PROCEDURE `pro_fetch_course_record`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-课程安排记录】';

	INSERT INTO course_record(
		course_record_id,customer_id,course_id,start_date,end_date,status,train_unit,train_plan_id,`year`)
	SELECT 
		course_record_id,customerId,course_id,start_date,end_date,status,train_unit,train_plan_id,`year` 
	FROM `mup-source`.source_course_record a
	ON DUPLICATE KEY UPDATE
		course_id=a.course_id,
		start_date=a.start_date,
		end_date=a.end_date,
		status=a.status,
		train_unit=a.train_unit,
		train_plan_id=a.train_plan_id,
		`year`=a.`year`
	;

END;


-- 培训经历
drop procedure if exists pro_fetch_emp_train_experience;
CREATE  PROCEDURE `pro_fetch_emp_train_experience`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;

	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【培训经历-业务表】：培训经历');

--			-- 	删除五年前今天以往的数据
--			DELETE FROM `mup-source`.source_emp_train_experience
--			WHERE customer_id = customerId
--			AND	id in (
--					SELECT id FROM (
--						SELECT id FROM `mup-source`.source_emp_train_experience t where `finish_date` < DATE_SUB(now(), INTERVAL 5 YEAR)
--					) t
--			);

	INSERT INTO emp_train_experience (
		train_experience_id, customer_id, emp_id, course_record_id, course_name, start_date, finish_date, train_time, `status`, result, train_unit, gain_certificate, lecturer_name, note, `year`,mark )
	SELECT
		tt.train_experience_id, customerId, tt.emp_id, tt.course_record_id, tt.course_name, tt.start_date, tt.finish_date, tt.train_time, tt.`status`, tt.result, tt.train_unit, tt.gain_certificate, tt.lecturer_name, tt.note, tt.`year`,mark
	FROM `mup-source`.source_emp_train_experience tt
	WHERE tt.customer_id = customerId
	ON DUPLICATE KEY UPDATE
		emp_id                 =tt.emp_id,
		course_record_id       =tt.course_record_id,
		course_name            =tt.course_name,
		start_date             =tt.start_date,
		finish_date            =tt.finish_date,
		train_time             =tt.train_time,
		status                 =tt.status,
		result                 =tt.result,
		train_unit             =tt.train_unit,
		gain_certificate       =tt.gain_certificate,
		lecturer_name          =tt.lecturer_name,
		note                   =tt.note,
		year                   =tt.year,
		mark                   =tt.mark
		;
END;
