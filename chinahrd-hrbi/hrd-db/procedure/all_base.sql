-- 员工信息表
drop procedure if exists pro_fetch_v_dim_emp;
CREATE PROCEDURE `pro_fetch_v_dim_emp`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_refresh timestamp(6))
BEGIN
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE p_message VARCHAR(10000) DEFAULT '【员工信息表】';
		DECLARE refresh TIMESTAMP DEFAULT p_refresh;

		INSERT INTO v_dim_emp (
			v_dim_emp_id,customer_id,emp_id,emp_key,user_name,user_name_ch,emp_hf_id,emp_hf_key, /* report_relation,*/ email,img_path,
			passtime_or_fulltime, contract,blood,
			age,company_age,
			is_key_talent,sex,nation,degree_id,degree,native_place,country, birth_place, birth_date,
			national_id,national_type,marry_status,patty_name,
			position_id,position_name,organization_parent_id,organization_parent_name,organization_id,organization_name,
			sequence_id,sequence_name,sequence_sub_id,sequence_sub_name,performance_id,performance_name,
			ability_id,job_title_id,ability_lv_id,ability_name,job_title_name,ability_lv_name,
-- 				rank_name,
			/*population_id,population_name,area_id,*/
			run_off_date,entry_date,
			tel_phone,qq,wx_code,address,
			contract_unit,work_place_id,work_place,regular_date, /*apply_type,*/ channel_id, speciality, is_regular,   mark
		)
		SELECT
			emp_id, customer_id, emp_id, emp_key, user_name, user_name_ch, emp_hf_id, emp_hf_key,  email, img_path,
			passtime_or_fulltime, contract, blood,
			(ROUND(TIMESTAMPDIFF(MONTH, birth_date, CURDATE()) / 12, 2)) AS age, (ROUND(TIMESTAMPDIFF(MONTH, entry_date, CURDATE()), 2)) AS company_age,
			is_key_talent, sex, nation, degree_id, degree, native_place, country, birth_place, birth_date,
			national_id, national_type, marry_status, patty_name,
			position_id, position_name, organization_parent_id, organization_parent_name, organization_id, organization_name,
			sequence_id, sequence_name, sequence_sub_id, sequence_sub_name, performance_id, performance_name, /*key_talent_type_id, key_talent_type_name,*/
			ability_id, job_title_id, ability_lv_id, ability_name, job_title_name, ability_lv_name,
			-- rank_name,  这个独立一个过程方法计算
			run_off_date, entry_date,
			tel_phone, qq, wx_code, address,
			contract_unit, work_place_id, work_place, regular_date, /*apply_type, apply_channel,*/ channel_id, speciality, is_regular,  mark
		FROM `mup-source`.source_v_dim_emp t
		ON DUPLICATE KEY UPDATE
			emp_id=t.emp_id, emp_key = t.emp_key, user_name = t.user_name, user_name_ch=t.user_name_ch, emp_hf_id=t.emp_hf_id, emp_hf_key=t.emp_hf_key,  email=t.email, img_path=t.img_path,
			passtime_or_fulltime=t.passtime_or_fulltime, contract=t.contract,blood=t.blood,
-- 						age,company_age, 这个独立一个过程方法计算
			is_key_talent=t.is_key_talent,sex=t.sex,nation=t.nation,degree_id=t.degree_id,degree=t.degree,native_place=t.native_place,country=t.country,birth_place=t.birth_place,birth_date=t.birth_date,
			national_id=t.national_id,national_type=t.national_type,marry_status=t.marry_status,patty_name=t.patty_name,
			position_id=t.position_id,position_name=t.position_name,organization_parent_id=t.organization_parent_id,organization_parent_name=t.organization_parent_name,organization_id=t.organization_id,organization_name=t.organization_name,
			sequence_id=t.sequence_id,sequence_name=t.sequence_name,sequence_sub_id=t.sequence_sub_id,sequence_sub_name=t.sequence_sub_name,performance_id=t.performance_id,performance_name=t.performance_name,
			ability_id=t.ability_id,job_title_id=t.job_title_id,ability_lv_id=t.ability_lv_id,ability_name=t.ability_name,job_title_name=t.job_title_name,ability_lv_name=t.ability_lv_name,
-- 				rank_name, 这个独立一个过程方法计算
			run_off_date=t.run_off_date,entry_date=t.entry_date,
			tel_phone=t.tel_phone,qq=t.qq,wx_code=t.wx_code,address=t.address,
			contract_unit=t.contract_unit,work_place_id=t.work_place_id,work_place=t.work_place,regular_date=t.regular_date, channel_id=t.channel_id, speciality=t.speciality, is_regular=t.is_regular,
			mark=t.mark
		;
END;

-- 更新员工信息表-年龄、司龄、rank_name
drop procedure if exists pro_update_v_dim_emp;
CREATE PROCEDURE `pro_update_v_dim_emp`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE p_message VARCHAR(10000) DEFAULT '【员工信息表-年龄、司龄、rank_name】';

		UPDATE v_dim_emp t1
		INNER JOIN
		(
			SELECT emp_id,
				(ROUND(TIMESTAMPDIFF(MONTH, birth_date, CURDATE()) / 12, 2)) AS age,	-- 单位：年
				(ROUND(TIMESTAMPDIFF(MONTH, entry_date, CURDATE()) , 2)) AS company_age, -- 单位：月
				concat(t1.curt_name, t2.curt_name, t3.curt_name, '.', t4.curt_name) rank_name
			FROM v_dim_emp t
			INNER JOIN dim_sequence t1 on t.sequence_id = t1.sequence_id AND t.customer_id = t1.customer_id
			INNER JOIN dim_sequence_sub t2 on t.sequence_sub_id = t2.sequence_sub_id AND t.customer_id = t2.customer_id
			INNER JOIN dim_ability t3 on t.ability_id = t3.ability_id AND t.customer_id = t3.customer_id
			INNER JOIN dim_ability_lv t4 on t.ability_lv_id = t4.ability_lv_id AND t.customer_id = t4.customer_id
		) t2 on t1.emp_id = t2.emp_id
		SET t1.age = t2.age, t1.company_age = t2.company_age, t1.rank_name = t2.rank_name
		;
END;

-- 汇报链表
DROP PROCEDURE IF EXISTS pro_update_emp_report_relation;
CREATE PROCEDURE `pro_update_emp_report_relation`(IN p_customer_id VARCHAR(32), IN p_user_id VARCHAR(32), IN p_refresh TIMESTAMP(6))
BEGIN
		-- 强制依赖`mup-source`.source_v_dim_emp	 对当然发生变化的员工作处理
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE refresh TIMESTAMP DEFAULT p_refresh;
		DECLARE p_message VARCHAR(10000) DEFAULT '【汇报链表】';
		DECLARE _total int(10);
		
		DECLARE eKey,eId VARCHAR(32);
		DECLARE done INT DEFAULT 0;
		DECLARE s_cur CURSOR FOR SELECT t.emp_key eKey, t.emp_id eId from tmp1 t;
		DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
	
		SELECT COUNT(1) INTO _total FROM `mup-source`.source_v_dim_emp;
		
		IF _total > 0 THEN
			-- 创建临时表，准备要处理汇报链的员工名单
			DROP TABLE IF EXISTS tmp1;
			CREATE TABLE tmp1(emp_id varchar(32), emp_key varchar(32)) ENGINE=Aria;
			-- 插入源表员工
			INSERT INTO tmp1( emp_key, emp_id)
			SELECT t.emp_key, t.emp_id from `mup-source`.source_v_dim_emp t;
			-- 源表员工的直管也要加入到临时表
--			INSERT INTO tmp1( emp_key, emp_id)
--			SELECT t.emp_hf_key, t.emp_hf_id from `mup-source`.source_v_dim_emp t where t.emp_hf_id is not null;
			
			delete from emp_report_relation where emp_key in (select emp_key from tmp1);
			OPEN s_cur;
			FETCH s_cur INTO  eKey, eId;
				WHILE done != 1 DO
				
					INSERT INTO emp_report_relation ( emp_report_id,  emp_key, report_relation)
					VALUES(eId,  eKey,  fn_get_tree_list_v_dim_emp(eKey) );-- 自定义函数

					FETCH s_cur INTO  eKey, eId;
				END WHILE;
			CLOSE s_cur;
		END IF;
		-- 删除临时表
		DROP TABLE IF EXISTS tmp1;
END;


-- 我的下属（子孙）
DROP PROCEDURE IF EXISTS pro_fetch_underling;
CREATE  PROCEDURE `pro_fetch_underling`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【我的下属】';

	DECLARE eHfKey VARCHAR(20);
	DECLARE eHfId VARCHAR(32);
	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
		SELECT DISTINCT emp_hf_id eHfId, emp_hf_key eHfKey FROM v_dim_emp WHERE emp_hf_id is NOT NULL AND emp_hf_id != "-1";

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	-- 清空
	TRUNCATE TABLE underling;
	OPEN s_cur;

		WHILE done != 1 DO
			FETCH s_cur INTO eHfId, eHfKey;
			IF NOT done THEN
				INSERT INTO underling
						(emp_relation_id, customer_id,
						emp_id, emp_key, underling_emp_id, underling_emp_key)
				SELECT
						fn_getId(), customerId,
						eHfId, eHfKey, emp_report_id, emp_key
				FROM emp_report_relation WHERE locate(eHfKey, report_relation) AND emp_key != eHfKey;
			END IF;
		END WHILE;
	CLOSE s_cur;
END;


-- 家庭关系
DROP PROCEDURE IF EXISTS pro_fetch_emp_family;
CREATE  PROCEDURE `pro_fetch_emp_family`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-家庭关系】';

	  INSERT INTO emp_family(
	  	emp_family_id,customer_id,emp_id,emp_family_name,`call`,is_child,work_unit,department_name,position_name,tel_phone,born_date,note)
	  SELECT
	  	emp_family_id,p_customer_id,emp_id,emp_family_name,`call`,is_child,work_unit,department_name,position_name,tel_phone,born_date,note
	  FROM `mup-source`.source_emp_family a
	  ON DUPLICATE KEY UPDATE
		  emp_family_name=a.emp_family_name,
		  emp_id=a.emp_id,
		  `call`=a.`call`,
		  is_child=a.is_child,
		  work_unit=a.work_unit,
		  department_name=a.department_name,
		  position_name=a.position_name,
		  tel_phone=a.tel_phone,
		  born_date=a.born_date,
		  note=a.note
		;
END;
-- 所获职称
DROP PROCEDURE IF EXISTS pro_fetch_emp_prof_title;
CREATE  PROCEDURE `pro_fetch_emp_prof_title`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-所获职称】';

	  INSERT INTO emp_prof_title (
	  		emp_prof_title_id,customer_id,emp_id,gain_date,emp_prof_title_name,prof_lv,award_unit,effect_date)
	  SELECT emp_prof_title_id,p_customer_id,emp_id,gain_date,emp_prof_title_name,prof_lv,award_unit,effect_date
	  FROM `mup-source`.source_emp_prof_title a
	  ON DUPLICATE KEY UPDATE
		  emp_id=a.emp_id,
		  gain_date=a.gain_date,
		  emp_prof_title_name=a.emp_prof_title_name,
		  prof_lv=a.prof_lv,
		  award_unit=a.award_unit,
		  effect_date=a.effect_date;
END;
-- 教育背景
DROP PROCEDURE IF EXISTS pro_fetch_emp_edu;
CREATE  PROCEDURE `pro_fetch_emp_edu`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【教育背景】';

	LB_INSERT:BEGIN
		INSERT INTO emp_edu (
			emp_edu_id, customer_id, emp_id, school_name, degree, degree_id, major,
			start_date, end_date, is_last_major, academic_degree, develop_mode, bonus, note
		)
		SELECT
			t.emp_edu_id, customer_id,
			emp_id, school_name, degree, degree_id, major,
			start_date, end_date, is_last_major, academic_degree, develop_mode, bonus, note
		FROM `mup-source`.source_emp_edu t
		WHERE customer_id = customerId
		ON DUPLICATE KEY UPDATE
				customer_id = t.customer_id,
				emp_id = t.emp_id,
				school_name = t.school_name,
				degree = t.degree,
				degree_id = t.degree_id,
				major = t.major,
				start_date = t.start_date,
				end_date = t.end_date,
				is_last_major = t.is_last_major,
				academic_degree = t.academic_degree,
				develop_mode = t.develop_mode,
				bonus = t.bonus,
				note = t.note
		;
	END LB_INSERT;
END;
-- 奖惩
drop procedure if exists pro_fetch_emp_bonus_penalty;
CREATE  PROCEDURE `pro_fetch_emp_bonus_penalty`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【奖惩】';

	INSERT INTO emp_bonus_penalty(
			emp_bonus_penalty_id, 	customer_id,
			emp_id,
			bonus_penalty_name,
			type,
			grant_unit,
			witness_name,
			bonus_penalty_date
	)
	SELECT emp_bonus_penalty_id, customerId,
			emp_id,
			emp_bonus_penalty_name,
			type,
			grant_unit,
			witness_name,
			bonus_penalty_date
	FROM `mup-source`.source_emp_bonus_penalty tt
	WHERE tt.customer_id = customerId
	ON DUPLICATE KEY UPDATE
				emp_id = tt.emp_id,
				bonus_penalty_name = tt.emp_bonus_penalty_name,
				type = tt.type,
				grant_unit = tt.grant_unit,
				witness_name = tt.witness_name,
				bonus_penalty_date = tt.bonus_penalty_date
			;
END;
-- 联系人
drop procedure if exists pro_fetch_emp_contact_person;
CREATE PROCEDURE `pro_fetch_emp_contact_person`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【联系人】';

	REPLACE INTO emp_contact_person(
		emp_contact_person_id, customer_id, emp_id,
		emp_contact_person_name,
		tel_phone,
		`call`,
		is_urgent,
		create_time
	)
	SELECT
		emp_contact_person_id, customerId, emp_id,
		emp_contact_person_name,
		tel_phone,
		`call`,
		is_urgent,
		create_time
	FROM `mup-source`.source_emp_contact_person tt
	WHERE tt.customer_id = customerId;
END;
-- 过往履历
drop procedure if exists pro_fetch_emp_past_resume;
CREATE PROCEDURE `pro_fetch_emp_past_resume`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【过往履历】';

	  INSERT INTO
	  	emp_past_resume(emp_past_resume_id,customer_id,emp_id,work_unit,department_name,position_name,bonus_penalty_name,witness_name,witness_contact_info,change_reason,entry_date,run_off_date,mark)
	  SELECT
	  	emp_past_resume_id,p_customer_id,emp_id,work_unit,department_name,position_name,bonus_penalty_name,witness_name,witness_contact_info,change_reason,entry_date,run_off_date,mark
	  FROM `mup-source`.source_emp_past_resume a
	  ON DUPLICATE KEY UPDATE
		  work_unit=a.work_unit,
		  department_name=a.department_name,
		  position_name=a.position_name,
		  bonus_penalty_name=a.bonus_penalty_name,
		  witness_name=a.witness_name,
		  witness_contact_info=a.witness_contact_info,
		  change_reason=a.change_reason,
		  entry_date=a.entry_date,
		  run_off_date=a.run_off_date,
		  mark=a.mark;
END;
-- 员工证书信息
DROP PROCEDURE IF EXISTS pro_fetch_emp_certificate_info;
CREATE PROCEDURE `pro_fetch_emp_certificate_info`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE p_message VARCHAR(10000) DEFAULT '【员工证书信息】';

		INSERT INTO emp_certificate_info(
			emp_certificate_info_id,emp_id,customer_id,certificate_id,obtain_date)
		SELECT 
			emp_certificate_info_id,emp_id,p_customer_id,certificate_id,obtain_date
		FROM `mup-source`.source_emp_certificate_info a
		ON DUPLICATE KEY UPDATE
			certificate_id=a.certificate_id,
			obtain_date=a.obtain_date;
END;
-- 人群日表
DROP PROCEDURE IF EXISTS pro_fetch_population_relation;
CREATE PROCEDURE `pro_fetch_population_relation`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE _day date DEFAULT fn_getYMD();
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【人群日表】';
	
	DELETE FROM population_relation WHERE days = _day;
	
  	INSERT INTO population_relation(	population_relation_id,
  	customer_id,
  	population_id,
  	emp_id,
  	organization_id,
  	days)
  	SELECT 
  	fn_getId(),
  	t.customer_id,
  	t.population_id,
  	t.emp_id,
  	t.organization_id,
  	t.days FROM `mup-source`.source_population_relation t;
END;
-- 人群月表
DROP PROCEDURE IF EXISTS `pro_fetch_population_relation_month`;
CREATE PROCEDURE `pro_fetch_population_relation_month`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
		DECLARE _day date DEFAULT fn_getYMD();
		DECLARE _year_month int DEFAULT fn_getYM();
		DECLARE _total int(10);
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE p_message VARCHAR(10000) DEFAULT '【人群月表】';

		-- 检查本月是否已有了
		SELECT count(*) INTO _total FROM population_relation_month WHERE `year_month`= _year_month;

		IF _total = 0 THEN
			INSERT INTO population_relation_month (
				population_relation_month_id,
				customer_id,
				population_id,
				emp_id,
				organization_id,
				`year_month`)
			SELECT 
				fn_getId(),
				customerId,
				population_id,
				emp_id,
				organization_id,
				_year_month
			FROM `mup-source`.source_population_relation a -- 这里从源表获取，数据量少比较快。
			WHERE a.days = _day
			;
		ELSE
				-- 如果有，先删除，再从日表获取
				DELETE FROM population_relation_month WHERE `year_month`=_year_month;
				INSERT INTO population_relation_month (
					population_relation_month_id,
					customer_id,
					population_id,
					emp_id,
					organization_id,
					`year_month`)
				SELECT 
					fn_getId(),
					customerId,
					population_id,
					emp_id,
					organization_id,
					_year_month
				FROM `mup-source`.source_population_relation a -- 这里从源表获取，数据量少比较快。
				WHERE a.days = _day
				;
		END IF;
END;
-- 机构负责人
DROP PROCEDURE IF EXISTS pro_fetch_organization_emp_relation;
CREATE PROCEDURE `pro_fetch_organization_emp_relation`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_refresh TIMESTAMP(6))
BEGIN
-- source_organization_emp_relation 必须全量，因为organization_emp_relation表全清空
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE refresh TIMESTAMP DEFAULT p_refresh;
	DECLARE p_message VARCHAR(10000) DEFAULT '【机构负责人】';

	LB_REPLACE:BEGIN
		-- 清空
		TRUNCATE TABLE organization_emp_relation;
		-- 每晚重新插入，因为可能存在有人不在是机构负责人所以不能用ON DUPLICATE KEY UPDATE
		INSERT INTO organization_emp_relation (
				organization_emp_relation_id, customer_id, organization_id, emp_id, sys_code_item_id)
		SELECT
				fn_getId(), customerId, organization_id, emp_id, sys_code_item_id
		FROM `mup-source`.source_organization_emp_relation a
		;
	END LB_REPLACE;
END;
-- 机构编制数
DROP PROCEDURE IF EXISTS pro_fetch_budget_emp_number;
CREATE  PROCEDURE `pro_fetch_budget_emp_number`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-机构编制数】';

	DECLARE _year VARCHAR(4) DEFAULT fn_getY();
	DECLARE _total INT;
	-- 本年有没有编制数
	SELECT count(1) INTO _total FROM budget_emp_number WHERE year = _year;
	IF _total = 0 THEN
		INSERT INTO budget_emp_number(
			budget_emp_number_id,customer_id,organization_id,number,`year`)
  		SELECT
  			budget_emp_number_id,customerId,organization_id,number,`year`
  		FROM `mup-source`.source_budget_emp_number a
   		ON DUPLICATE KEY UPDATE
			organization_id=a.organization_id,
			number=a.number,
			`year` = a.`year`
		;
	END IF;
END;

-- 岗位编制数
drop procedure if exists pro_fetch_budget_position_number;
CREATE PROCEDURE `pro_fetch_budget_position_number`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_refresh VARCHAR(32))
BEGIN
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE p_message VARCHAR(10000) DEFAULT '【岗位编制数】';
		DECLARE refresh TIMESTAMP DEFAULT p_refresh;
		
		DECLARE _year VARCHAR(4) DEFAULT fn_getY();
		DECLARE _total INT;
	
	-- 本年有没有编制数
	SELECT count(1) INTO _total FROM budget_position_number WHERE `year` = _year;
	IF _total = 0 THEN
		INSERT INTO budget_position_number(
			budget_position_number_id,customer_id,organization_id, position_id, number,`year`)
		SELECT 
			budget_position_number_id,customerId,organization_id,position_id,number,`year`
		FROM `mup-source`.source_budget_position_number a
		ON DUPLICATE KEY UPDATE
			position_id=a.position_id,
			number=a.number
		;
	END IF;
END;
-- 员工月切片
DROP PROCEDURE IF EXISTS pro_fetch_dim_emp_month;
CREATE PROCEDURE `pro_fetch_dim_emp_month`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_refresh timestamp(6))
BEGIN
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【人员历史表】');
		DECLARE refresh VARCHAR(10000) DEFAULT p_refresh;

		DECLARE _ymd VARCHAR(10) DEFAULT fn_getYMD();
		DECLARE _ym VARCHAR(10) DEFAULT fn_getYM();
		DECLARE _toatl INT;

		-- 检查本月是否已有了
	  	SELECT count(*) INTO _toatl FROM dim_emp_month WHERE `year_month`= _ym;

		IF _toatl = 0 THEN
			INSERT INTO dim_emp_month
			SELECT 
				fn_getId(), emp_id,customerId,emp_key,user_name,user_name_ch,emp_hf_id,emp_hf_key,
				passtime_or_fulltime,degree_id,degree,native_place,country,national_id,national_type,
				marry_status,patty_name,is_key_talent,
				organization_parent_id,organization_parent_name,organization_id,organization_name,
				position_id,position_name,sequence_id,sequence_name,sequence_sub_id,sequence_sub_name,ability_id,ability_name,job_title_id,job_title_name,ability_lv_id,ability_lv_name,
				rank_name,performance_id,performance_name,
				address,contract_unit,contract,work_place_id,work_place,is_regular,entry_date,run_off_date,
				_ym,null,null
			FROM v_dim_emp
			;
		 END IF;
END;

-- 机构历史表-日月
DROP PROCEDURE IF EXISTS pro_fetch_history_dim_organization_month;
CREATE PROCEDURE `pro_fetch_history_dim_organization_month`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_refresh timestamp(6))
BEGIN
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【机构历史表】');
		DECLARE refresh VARCHAR(10000) DEFAULT p_refresh;

		DECLARE _ymd VARCHAR(10) DEFAULT fn_getYMD();
		DECLARE _ym VARCHAR(10) DEFAULT fn_getYM();
		DECLARE _ymd_li VARCHAR(10) DEFAULT fn_getYMD_li();

		-- 日
		INSERT INTO history_dim_organization(
			history_dim_organization_id,
			organization_id,
			customer_id,
			organization_company_id,
			organization_type_id,
			organization_key,
			organization_parent_key,
			organization_parent_id,
			organization_name,
			organization_name_full,
			note,
			is_single,
			full_path,
			has_children,
			depth,
			
			profession_id,
			days)
		SELECT fn_getId(),organization_id,customer_id,organization_company_id,organization_type_id,
			organization_key,organization_parent_key,organization_parent_id,organization_name,organization_name_full,note,is_single,full_path,has_children,depth,
			 profession_id, _ymd_li
		FROM dim_organization;

		-- 月
		-- 本月具备昨天最新数据
	    DELETE FROM history_dim_organization_month WHERE `year_month`= _ym;
	    INSERT INTO history_dim_organization_month
		SELECT fn_getId(),organization_id,customer_id,organization_company_id,organization_type_id,
			organization_key,organization_parent_key,organization_parent_id,organization_name,organization_name_full,note,is_single,full_path,has_children,depth,
			profession_id, _ym,null,null
		FROM history_dim_organization
		WHERE days = _ymd_li;
END;


-- 每天总人数
DROP PROCEDURE IF EXISTS pro_fetch_history_emp_count;
CREATE  PROCEDURE `pro_fetch_history_emp_count`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_refresh timestamp(6))
BEGIN
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE p_message VARCHAR(10000) DEFAULT '【每天总人数】';
		DECLARE refresh VARCHAR(10000) DEFAULT p_refresh;

		DECLARE _total INT;
		DECLARE _day VARCHAR(10) DEFAULT fn_getYMD_li();
		DECLARE _ym VARCHAR(10) DEFAULT fn_getYM();
		DECLARE _year VARCHAR(10) DEFAULT date_format(_day,'%Y');

		DECLARE _organization_id varchar(32);
		DECLARE _done int(2) DEFAULT 0;
		-- 全部， 全职， 兼职
		DECLARE b_total_q, b_total_z, b_total_j int(5);
		DECLARE q_total_q, q_total_z, q_total_j int(5);

		DECLARE _full_path varchar(1000);
		-- declare cur cursor for SELECT days FROM days WHERE days>=str_to_date('20161201','%Y%m%d') AND  days<=str_to_date('20161231','%Y%m%d');
		DECLARE cur1 cursor for SELECT organization_id as _organization_id FROM dim_organization;
		DECLARE continue hANDler for not found set _done = 1;

--   open cur;
--   fetch cur into _day;
--     while _done = 0 do
	OPEN cur1;
	FETCH cur1 INTO _organization_id;
	WHILE _done = 0 do

		SELECT full_path INTO _full_path FROM dim_organization WHERE organization_id=_organization_id;
		-- 本机构总人数
		SELECT count(*) INTO b_total_q FROM v_dim_emp WHERE entry_date<=_day AND  ifnull(run_off_date,'2099-12-31')>=_day AND organization_id=_organization_id;
		SELECT count(*) into b_total_z FROM v_dim_emp WHERE entry_date<=_day AND  ifnull(run_off_date,'2099-12-31')>=_day AND organization_id=_organization_id AND passtime_or_fulltime='f';
		SELECT count(*) into b_total_j FROM v_dim_emp WHERE entry_date<=_day AND  ifnull(run_off_date,'2099-12-31')>=_day AND organization_id=_organization_id AND passtime_or_fulltime='p';
		-- 本机构总人数向下汇总
		SELECT count(*) into q_total_q FROM v_dim_emp WHERE entry_date<=_day AND  ifnull(run_off_date,'2099-12-31')>=_day AND organization_id in (SELECT t1.organization_id FROM dim_organization t1 WHERE locate((SELECT t.full_path	FROM	dim_organization t WHERE t.organization_id = _organization_id),t1.full_path));
		SELECT count(*) into q_total_z FROM v_dim_emp WHERE entry_date<=_day AND  ifnull(run_off_date,'2099-12-31')>=_day AND organization_id in (SELECT t1.organization_id FROM dim_organization t1 WHERE locate((SELECT t.full_path	FROM	dim_organization t WHERE t.organization_id = _organization_id),t1.full_path)) AND passtime_or_fulltime='f';
		SELECT count(*) into q_total_j FROM v_dim_emp WHERE entry_date<=_day AND  ifnull(run_off_date,'2099-12-31')>=_day AND organization_id in (SELECT t1.organization_id FROM dim_organization t1 WHERE locate((SELECT t.full_path	FROM	dim_organization t WHERE t.organization_id = _organization_id),t1.full_path)) AND passtime_or_fulltime='p';

		INSERT INTO history_emp_count SELECT fn_getId(), customerId, _organization_id, _full_path, 1,b_total_q,q_total_q,_day,null, _year,null,null;
		INSERT INTO history_emp_count SELECT fn_getId(), customerId, _organization_id, _full_path, 2,b_total_z,q_total_z,_day,null, _year,null,null;
		INSERT INTO history_emp_count SELECT fn_getId(), customerId, _organization_id, _full_path, 3,b_total_j,q_total_j,_day,null, _year,null,null;

		-- 当前月有没有数据
		SELECT count(1) INTO _total FROM history_emp_count_month WHERE `year_month` = _ym and organization_id = _organization_id;
		IF _total = 0 THEN
			INSERT INTO history_emp_count_month SELECT fn_getId(),customerId, _organization_id,_full_path,1,b_total_q,q_total_q,0,0,0,0,0,0, _ym, null,null,null;
			INSERT INTO history_emp_count_month SELECT fn_getId(),customerId, _organization_id,_full_path,2,b_total_z,q_total_z,0,0,0,0,0,0, _ym, null,null,null;
			INSERT INTO history_emp_count_month SELECT fn_getId(),customerId, _organization_id,_full_path,3,b_total_j,q_total_j,0,0,0,0,0,0, _ym, null,null,null;
--       end if;
--
--       if date_format(_day,'%Y-%m-%d')= date_format(LAST_DAY(_day),'%Y-%m-%d') then
		-- 有每晚更新当月人数量
        ELSE
			UPDATE history_emp_count_month SET
				month_end=b_total_q,
				month_end_sum=q_total_q,
				month_count=(month_begin+month_end)/2,
				month_count_sum=(month_begin_sum+month_end_sum)/2,
				month_avg=b_total_q/day(_day),
				month_avg_sum=q_total_q/day(_day)
			WHERE organization_id=_organization_id;
		END IF;

		FETCH cur1 INTO _organization_id;
	END WHILE;
	CLOSE CUR1;
--      set _done = 0;
--      fetch cur into _day;
--   end while;
END;


-- 等效全职员工数
DROP PROCEDURE if EXISTS pro_fetch_fact_fte;
CREATE  PROCEDURE `pro_fetch_fact_fte`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【业务表-等效全职员工数】');

	INSERT INTO fact_fte (
		fte_id,customer_id,organization_id,organization_name,fulltime_sum,passtime_sum,fte_value,gain_amount,sales_amount,target_value,benefit_value,range_per)
	SELECT 
		fte_id,customerId,organization_id,organization_name,fulltime_sum,passtime_sum,fte_value,gain_amount,sales_amount,target_value,benefit_value,range_per
	FROM `mup-source`.source_fact_fte a
	ON DUPLICATE KEY UPDATE
		organization_id=a.organization_id,
		organization_name=a.organization_name,
		fulltime_sum=a.fulltime_sum,
		passtime_sum=a.passtime_sum,
		fte_value=a.fte_value,
		gain_amount=a.gain_amount,
		sales_amount=a.sales_amount,
		target_value=a.target_value,
		benefit_value=a.benefit_value,
		range_per=a.range_per
		;
END;

-- 机构KPI
drop procedure if exists pro_fetch_dept_kpi;
CREATE PROCEDURE `pro_fetch_dept_kpi`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('机构KPI');

	INSERT INTO dept_kpi (dept_per_exam_relation_id,customer_id,organization_id,kpi_value,`year`)
	SELECT dept_per_exam_relation_id,customerId,organization_id,kpi_value,`year`
	FROM `mup-source`.source_dept_kpi a
	ON DUPLICATE KEY UPDATE
		organization_id=a.organization_id,
		kpi_value=a.kpi_value,
		`year` = a.`year`
	;
END;


-- 行业指标值
drop procedure if exists pro_fetch_profession_value	;
CREATE PROCEDURE `pro_fetch_profession_value`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_refresh timestamp(6))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【业务表-行业指标值】');
	DECLARE refresh VARCHAR(10000) DEFAULT p_refresh;

	INSERT INTO profession_value (profession_value,profession_name,profession_value_key,`value`,profession_id )
	SELECT profession_value,profession_name,profession_value_key,`value`,profession_id
	FROM `mup-source`.source_profession_value a
	on duplicate key update
		profession_name=a.profession_name,
		profession_value_key=a.profession_value_key,
		`value`=a.`value`,
		profession_id=a.profession_id;
END;
