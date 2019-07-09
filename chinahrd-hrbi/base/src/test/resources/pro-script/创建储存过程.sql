#=======================================================pro_welfare_cpm_total=======================================================
DROP PROCEDURE IF EXISTS `pro_welfare_cpm_total`;
CREATE PROCEDURE `pro_welfare_cpm_total`(_start_month int , _end_month int)
begin 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【企业福利（货币）-月统计】';
	DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_welfare_cpm_total');


	declare _total int;
  declare _organization_id varchar(32);
	declare done int default 0;
	declare cur cursor for select organization_id from dim_organization;
	declare exit handler for not found set done = 1;
	  open cur;
		while done = 0 do  
			fetch cur into _organization_id;
      select _organization_id;
      select count(*) into _total  FROM v_dim_emp vde inner JOIN dim_organization org ON vde.organization_id = org.organization_id AND vde.customer_id = org.customer_id WHERE vde.customer_id = customerId AND vde.run_off_date IS NULL AND vde.organization_id IN ( SELECT t1.organization_id FROM dim_organization t1 WHERE t1.customer_id = vde.customer_id AND locate(( SELECT t.full_path FROM dim_organization t WHERE t.customer_id = t1.customer_id AND t.organization_id = _organization_id ), t1.full_path ));
      insert into welfare_cpm_total
      select _organization_id,sci.customer_id,cpm_id,round(sum(wc.welfare_value), 2) AS cmp_total,count(DISTINCT vde.emp_id) as enjoin_cmp_total,_total,substr(`year_month`,1,4) from 
      soure_code_item sci
      INNER JOIN welfare_cpm wc ON sci.code_item_id = wc.cpm_id
      AND sci.customer_id = wc.customer_id
      AND wc.`year_month`  >= _start_month and wc.`year_month`<= _end_month
      AND wc.customer_id =customerId
      INNER JOIN v_dim_emp vde ON vde.emp_id = wc.emp_id
      AND wc.customer_id = vde.customer_id
      AND vde.organization_id IN ( SELECT t1.organization_id FROM dim_organization t1 WHERE t1.customer_id = vde.customer_id AND locate(( SELECT t.full_path FROM dim_organization t WHERE t.customer_id = t1.customer_id AND t.organization_id = _organization_id), t1.full_path ))
      WHERE
	     sci.code_group_id = 'cpm'
       GROUP BY cpm_id,_organization_id,sci.customer_id,substr(`year_month`,1,4),_total ;
		end  while;
		close cur;

		INSERT INTO db_log VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_welfare_cpm_total', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		set @XinChouKanBan_error = p_error; 

 end


#=======================================================pro_welfare_nfb_total=======================================================
DROP PROCEDURE IF EXISTS `pro_welfare_nfb_total`;
CREATE PROCEDURE `pro_welfare_nfb_total`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), IN _year int(4))
begin 

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【固定福利-月统计】';
	DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_welfare_nfb_total');

	declare _total int;
  declare _organization_id varchar(32);
	declare done int default 0;
	declare cur cursor for select organization_id from dim_organization;
	declare exit handler for not found set done = 1;

	START TRANSACTION;
	  open cur;
		while done = 0 do  
    fetch cur into _organization_id;
      select count(*) into _total 
			FROM v_dim_emp vde inner JOIN dim_organization org ON vde.organization_id = org.organization_id AND vde.customer_id = org.customer_id 
			WHERE vde.customer_id = customerId AND vde.run_off_date IS NULL AND vde.organization_id IN ( SELECT t1.organization_id FROM dim_organization t1 WHERE t1.customer_id = vde.customer_id AND locate(( SELECT t.full_path FROM dim_organization t WHERE t.customer_id = t1.customer_id AND t.organization_id = _organization_id ), t1.full_path ));
      insert into welfare_nfb_total
      select _organization_id,sci.customer_id,nfb_id,round(sum(wn.welfare_value), 2) AS nfb_total,
      (SELECT count(vde.emp_id) FROM welfare_nfb wn1 INNER JOIN v_dim_emp vde ON vde.emp_id = wn1.emp_id AND vde.customer_id = wn1.customer_id WHERE wn1.`year_month` = ( SELECT max(`year_month`) FROM welfare_nfb WHERE customer_id = customerId ) AND wn1.nfb_id = wn.nfb_id AND vde.organization_id IN ( SELECT t1.organization_id FROM dim_organization t1 WHERE t1.customer_id = vde.customer_id AND locate(( SELECT t.full_path FROM dim_organization t WHERE t.customer_id = t1.customer_id AND t.organization_id = _organization_id ), t1.full_path )) AND vde.customer_id = customerId ) enjoin_nfb_total,_total,substr(`year_month`,1,4)
			FROM
			soure_code_item sci
			INNER JOIN welfare_nfb wn ON sci.code_item_id = wn.nfb_id
			AND sci.customer_id = wn.customer_id
			AND substr(wn.`year_month`, 1, 4) = _year
			AND wn.customer_id =customerId
			INNER JOIN v_dim_emp vde ON vde.emp_id = wn.emp_id
			AND wn.customer_id = vde.customer_id
			AND vde.organization_id IN ( SELECT t1.organization_id FROM dim_organization t1 WHERE t1.customer_id = vde.customer_id AND locate(( SELECT t.full_path FROM dim_organization t WHERE t.customer_id = t1.customer_id AND t.organization_id = _organization_id ), t1.full_path ))
			WHERE
			sci.code_group_id = 'nfb'
			GROUP BY _organization_id,sci.customer_id,nfb_id,_total,substr(`year_month`,1,4);
		end while;


	
		INSERT INTO db_log VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_welfare_nfb_total', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		set @XinChouKanBan_error = p_error; 

end



#=======================================================pro_fetch_base_all=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_base_all`;
CREATE PROCEDURE pro_fetch_base_all(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_nowTime datetime)

top:begin 

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();
-- 		DECLARE p_nowTime  TIMESTAMP DEFAULT now();	-- 先硬编码
		DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_fetch_base_all');


		SET @bass_error_mgs = 0;

		-- 员工表
		IF @bass_error_mgs = 1 THEN 
			LEAVE top;
		ELSE
			CALL pro_fetch_v_dim_emp(customerId, optUserId);
		END IF;
		
		-- 年龄，司龄，rank_name
		IF @bass_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
			CALL pro_update_v_dim_emp(customerId, optUserId);
		END IF;
		-- 是否关键人才
		IF @bass_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
			CALL pro_update_key_talent(customerId, optUserId);
		END IF;
		-- 汇报链
		IF @bass_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
			CALL pro_update_emp_report_relation(customerId, optUserId);
		END IF;
		-- 我的下属（子孙）
		IF @bass_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
			CALL pro_fetch_underling(customerId, optUserId);
		END IF;
		-- 日、月表总人数
		IF @bass_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
-- 			CALL pro_fetch_history_emp_count(customerId, optUserId, _year);
			SELECT 1;
		END IF;
	
		-- 家庭关系
		IF @bass_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
			CALL pro_fetch_emp_family(customerId, optUserId);
		END IF;
		-- 所获职称
		IF @bass_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
			CALL pro_fetch_emp_prof_title(customerId, optUserId);
		END IF;
		-- 教育背景
		IF @bass_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
			CALL pro_fetch_emp_edu(customerId, optUserId);
		END IF;
		-- 奖惩信息
		IF @bass_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
			CALL pro_fetch_emp_bonus_penalty(customerId, optUserId);
		END IF;
		-- 联系人信息
		IF @bass_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
			CALL pro_fetch_emp_contact_person(customerId, optUserId);
		END IF;
		-- 过往履历（不是简历）
		IF @bass_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
			CALL pro_fetch_emp_past_resume(customerId, optUserId);
		END IF;
		-- 证书信息
		IF @bass_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
			CALL pro_fetch_emp_certificate_info(customerId, optUserId);
		END IF;










		-- 人群
		IF @bass_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
-- 			CALL pro_fetch_population_relation(customerId, optUserId);
			SELECT 1;
		END IF;

		-- 机构编制数
		IF @bass_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
			CALL pro_fetch_organization_emp_relation(customerId, optUserId);
		END IF;		
		-- 机构负责人
		IF @bass_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
			CALL pro_fetch_organization_emp_relation(customerId, optUserId);
		END IF;




		IF @bass_error_mgs = 0 THEN 
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, null, "【所有基础表】数据刷新完成", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 0 , showIndex);
		END IF;

end;
-- DELIMITER ;

-- CALL pro_fetch_base_all('b5c9410dc7e4422c8e0189c7f8056b5f','DBA','2015-12-17');







#=======================================================pro_update_v_dim_emp=======================================================
DROP PROCEDURE IF EXISTS `pro_update_v_dim_emp`;
CREATE PROCEDURE pro_update_v_dim_emp(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();	 
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000) DEFAULT '【员工信息表-年龄、司龄、rank_name】';  
		DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_update_v_dim_emp');

		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS; SET p_error = 1; end;

		START TRANSACTION;
				
			UPDATE v_dim_emp t1
			INNER JOIN 
			(
				SELECT emp_id, (ROUND(TIMESTAMPDIFF(MONTH, birth_date, CURDATE()) / 12, 2)) AS age, (ROUND(TIMESTAMPDIFF(MONTH, entry_date, CURDATE()) / 12, 2)) AS company_age, 
							concat(t1.curt_name, t2.curt_name, t3.curt_name, '.', t4.curt_name) rank_name
				FROM v_dim_emp t
				INNER JOIN dim_sequence t1 on t.sequence_id = t1.sequence_id and t.customer_id = t1.customer_id
				INNER JOIN dim_sequence_sub t2 on t.sequence_sub_id = t2.sequence_sub_id and t.customer_id = t2.customer_id
				INNER JOIN dim_ability t3 on t.ability_id = t3.ability_id and t.customer_id = t3.customer_id
				INNER JOIN dim_ability_lv t4 on t.ability_lv_id = t4.ability_lv_id and t.customer_id = t4.customer_id
			) t2 on t1.emp_id = t2.emp_id
			SET t1.age = t2.age, t1.company_age = t2.company_age, t1.rank_name = t2.rank_name
			;

			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_update_v_dim_emp', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		SET @bass_error_mgs = p_error;



END;
-- DELIMITER ;

-- CALL pro_update_v_dim_emp('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA');


#=======================================================pro_fetch_dim_population=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_population`;
CREATE PROCEDURE pro_fetch_dim_population(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

			DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
			DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
			DECLARE startTime TIMESTAMP DEFAULT now();
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【人群范围维】');
DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_fetch_dim_population');

		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

				LB_INSERT:BEGIN
					
						INSERT INTO dim_population (
							population_id,
							customer_id,
							population_key,
							population_name,
							
							c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							customer_id,
							population_key,
							population_name,
						
							c_id
						FROM soure_dim_population t 
						WHERE customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								population_key = t.population_key,
								population_name = t.population_name								
						;
				END LB_INSERT;

		INSERT INTO db_log 
		VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_dim_ability_lv', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		set @error_message = p_error;  


END;
-- DELIMITER ;

-- CALL pro_fetch_dim_population('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');





#=======================================================pro_fetch_dim_all=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_all`;
CREATE PROCEDURE pro_fetch_dim_all(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))

top:begin 

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

	-- 机构
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_organization(customerId, optUserId);
		end if; 

	-- 机构维-全路径，深度，是否有子节点
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_organization(customerId, optUserId);
		end if; 



	-- 岗位 
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_position(customerId, optUserId);
		end if; 

		-- 主、子序列；职衔；能力层职； 子职；人群范围
		set @error_message = 0;
		call pro_fetch_dim_sequence( customerId, optUserId, 'sequence');
		if @error_message = 1 then 
			leave top;
		else 
			call pro_fetch_dim_sequence_sub( customerId, optUserId, 'sequence_sub');
		end if;
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_job_title(customerId, optUserId,'title');
		end if;
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_ability(customerId, optUserId, 'ability');
		end if;
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_ability_lv(customerId, optUserId, 'ability_lv'); 
		end if;
	-- 关键人才库
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_key_talent_type(customerId, optUserId,'keyTalent');
		end if; 


	-- 课程
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_course( customerId, optUserId);	
		end if; 
	-- 课程类型
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_course_type( customerId, optUserId, 'courseType');	
		end if;  
	-- 流失
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_run_off(customerId, optUserId);
		end if;  
	-- 工资结构
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_structure(customerId, optUserId);	
		end if;  
	-- 项目类型
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_project_type(customerId, optUserId);
		end if;
	-- 项目投入费用类型  
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_project_input_type(customerId, optUserId);
		end if;  

 

	-- 异动类型
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_change_type(customerId, optUserId,'changeType');
		end if;  

	-- 招聘渠道
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_channel(customerId, optUserId, 'channel');
		end if;  
	-- 离职周期
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_dismission_week(customerId, optUserId);
		end if;  

	-- 激励要素
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_encourages(customerId, optUserId);	
		end if;  
	-- 绩效
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_performance(customerId, optUserId, 'performance');
		end if;  

	-- 人群
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_population(customerId, optUserId);
		end if; 

	
	-- 岗位能力素质维
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_quality(customerId, optUserId);
		end if;  
	-- 异动类型维
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_checkwork_type(customerId,  optUserId, 'checkwork');
		end if;  
		

	-- 证书信息
		if @error_message = 1 then 
			leave top;
		else 
			CALL pro_fetch_dim_certificate_info(customerId,  optUserId, 'certificate');
		end if;  
		

-- 	IF @error_message = 0 THEN
		INSERT INTO db_log 
		VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dimTables', "【所有维度表】数据刷新完成", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'sucess' , 200);
-- 	end IF;


end;
-- DELIMITER ;

CALL pro_fetch_dim_all('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');





#=======================================================pro_fetch_dim_dismission_week=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_dismission_week`;
CREATE PROCEDURE pro_fetch_dim_dismission_week(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【离职周期维度】：数据刷新完成');
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

				LB_INSERT:BEGIN
					
						INSERT INTO dim_dismission_week (
							dismission_week_id,
							customer_id,
							name,
							days,
							type,
							
							c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							customer_id,
							name,
							days,
							type,
						
							c_id
						FROM soure_dim_dismission_week t 
						WHERE customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								name = t.name,
								days = t.days,
								type = t.type								
						;
				END LB_INSERT;

			IF p_error = 1 THEN  
					ROLLBACK;  
 					SET p_message = concat("【离职周期维度】：数据刷新失败。操作用户：", optUserId);
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_dismission_week', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'error', 217);
			ELSE  
					COMMIT;  
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_dismission_week', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'sucess' , 217);
			END IF;  
			set @error_message = p_error;



END;
-- DELIMITER ;

-- CALL pro_fetch_dim_dismission_week('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');





#=======================================================pro_fetch_dim_channel=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_channel`;
CREATE PROCEDURE pro_fetch_dim_channel(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_work_key VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	
		DECLARE p_error INTEGER DEFAULT 0; 
DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【招聘渠道维】：数据刷新完成');
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

-- ======================================replace模板======================================
				LB_INSERT:BEGIN
					
						INSERT INTO dim_channel (
							channel_id,
							customer_id,
							channel_key,
							channel_name,
							show_index,
							
							c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							customer_id,
							code_item_key,
							code_item_name,
							show_index,
						
							code_item_id
						FROM soure_code_item t 
						WHERE t.code_group_id = p_work_key AND customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								channel_key = t.code_item_key,
								channel_name = t.code_item_name,
								show_index = t.show_index,
								c_id = code_item_id
						;
				END LB_INSERT;

			IF p_error = 1 THEN  
					ROLLBACK;  
 					SET p_message = concat("【招聘渠道维】：数据刷新失败。操作用户：", optUserId);
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_channel', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'error', 216);
			ELSE  
					COMMIT;  
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_channel', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'sucess' , 216);
			END IF;  
			set @error_message = p_error;



END;
-- DELIMITER ;

-- CALL pro_fetch_dim_channel('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'channel');





#=======================================================pro_fetch_manpower_cost_item=======================================================
DROP PROCEDURE IF EXISTS `pro_fetch_manpower_cost_item`;
CREATE PROCEDURE pro_fetch_manpower_cost_item(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), IN p_ym int(6))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();	 
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000) DEFAULT '【人力成本-人力成本结构】'; 
		DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_fetch_manpower_cost_item');

		DECLARE 12Month INT(2) DEFAULT SUBSTR(p_ym FROM 5 FOR 6);
		DECLARE y INT(2) DEFAULT SUBSTR(p_ym FROM 1 FOR 4);

		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
		DELETE from manpower_cost_item WHERE `YEAR_MONTH` = p_ym;

		-- 工资
		INSERT into manpower_cost_item (
			manpower_cost_item_id, customer_id, organization_id, item_id, item_name, item_cost,  `year_month`, show_index
		)
		SELECT replace(uuid(),'-',''), t.customer_id, t.organization_id, '8ee64d2f70eb48f2a4cc54864cbdb21e','薪酬', t.sum_salary, p_ym, 1 -- 万元
		from pay_collect t INNER JOIN dim_organization t1 on t.organization_id = t1.organization_id 
-- 		INNER JOIN manpower_cost t2 on t2.organization_id = t.organization_id and t2.`year_month` = p_ym
		where t.`year_month` = p_ym and t1.full_path in('ZRW', 'ZRW_BJ', 'ZRW_SZ', 'ZRW_GZ', 'ZRW_SH')
		ORDER BY full_path;

		-- 福利
		INSERT into manpower_cost_item (
			manpower_cost_item_id, customer_id, organization_id, item_id, item_name, item_cost,  `year_month`, show_index
		)
		SELECT replace(uuid(),'-',''), t.customer_id, t.organization_id, '11227b19a6194b42a9d45dfba76fd85c','福利', t.sum_welfare, p_ym, 2 -- 万元
		from pay_collect t INNER JOIN dim_organization t1 on t.organization_id = t1.organization_id 
-- 		INNER JOIN manpower_cost t2 on t2.organization_id = t.organization_id and t2.`year_month` = p_ym
		where t.`year_month` = p_ym and full_path in('ZRW', 'ZRW_BJ', 'ZRW_SZ', 'ZRW_GZ', 'ZRW_SH')
		ORDER BY full_path;


			IF 12Month = 12 THEN
				-- 培训
					INSERT into manpower_cost_item (
						manpower_cost_item_id, customer_id, organization_id, item_id, item_name, item_cost, `year_month`, show_index
					)
					SELECT
						replace(uuid(),'-',''), t.customer_id, t.organization_id, '8f9984323a14409d95176bd370d0f035', '培训', sum(t.outlay), p_ym, 3 -- 万元
					FROM `train_outlay` t INNER JOIN dim_organization t1 on t.organization_id = t1.organization_id and t1.is_single =1 where t.`year` = y GROUP BY t.organization_id order by t.organization_id;
      
       -- 招聘
				insert into manpower_cost_item(
						manpower_cost_item_id, customer_id, organization_id, item_id, item_name, item_cost, `year_month`, show_index
				)
        select replace(uuid(),'-',''),customer_id, organization_id,'60b2215662a041b0a11f4b1f9391319e','招聘',outlay,p_ym,4 -- 万元
				from recruit_value where `year`=substr(p_ym,1,4);
			END IF;

-- 	IF p_error = 1 THEN  
-- 			ROLLBACK;  
-- 	ELSE  
-- 			COMMIT;  
-- 			CALL proc_manpower_cost(p_ym);
-- 	END IF;

		INSERT INTO db_log 
		VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_manpower_cost_item', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		SET @RenLiChengBen_error_mgs = p_error;

END;
-- DELIMITER ;

-- 
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201401);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201402);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201403);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201404);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201405);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201406);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201407);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201408);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201409);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201410);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201411);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201412);
-- 
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201501);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201502);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201503);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201504);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201505);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201506);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201507);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201508);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201509);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201510);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201511);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201512);
-- 
-- 
-- SELECT * from manpower_cost_item


#=======================================================pro_fetch_quota_RenLiChengBen=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_quota_RenLiChengBen`;
CREATE PROCEDURE pro_fetch_quota_RenLiChengBen(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_nowTime datetime)

top:begin 

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();
-- 		DECLARE p_nowTime  TIMESTAMP DEFAULT now();	-- 先硬编码
		DECLARE ym INTEGER(6) DEFAULT date_format(p_nowTime, "%Y%m");
		DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_fetch_quota_RenLiChengBen');
		

		SET @RenLiChengBen_error_mgs = 0;
		

		-- 人力成本结构
		IF @RenLiChengBen_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
			CALL pro_fetch_manpower_cost_item(customerId, optUserId, ym);
		END IF;


		-- 人力成本
		IF @RenLiChengBen_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
			CALL pro_fetch_manpower_cost(customerId, optUserId, p_nowTime);
		END IF;


-- 			-- 明年预算
-- 		IF @RenLiChengBen_error_mgs = 1 THEN 
-- 			LEAVE top;
-- 		ELSE 
-- 			CALL pro_fetch_manpower_cost_value(customerId, optUserId);
-- 		END IF;


			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_quota_RenLiChengBen', "【人力成本-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenLiChengBen_error_mgs , showIndex);
-- 		IF @RenLiChengBen_error_mgs = 0 THEN 
-- 			
-- 		END IF;

end;
-- -- DELIMITER ;

-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-01-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-02-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-03-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-04-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-05-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-06-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-07-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-08-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-09-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-10-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-11-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-12-01');

-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-01-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-02-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-03-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-04-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-05-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-06-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-07-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-08-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-09-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-10-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-11-01');
-- CALL pro_fetch_quota_RenLiChengBen('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-12-01');




#=======================================================pro_fetch_manpower_cost_value=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_manpower_cost_value`;
CREATE PROCEDURE pro_fetch_manpower_cost_value(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();	 
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000) DEFAULT '【人力成本-1.人力成本预算】'; 
		DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_fetch_manpower_cost_value');


	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

			REPLACE INTO manpower_cost_value (
				manpower_cost_value_id,
				customer_id,
				organization_id,
				budget_value,
				`year`
			)
			SELECT 
				id,
				customerId,
				t1.organization_id,
				t.budget_value,
				t.`year`
			FROM soure_manpower_cost_value t
			INNER JOIN dim_organization t1 on t.organization_key = t1.organization_key AND t1.customer_id = t.customer_id
			WHERE t.customer_id = customerId ;
			

		INSERT INTO db_log 
		VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_manpower_cost_value', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		SET @RenLiChengBen_error_mgs = p_error;

END;


-- CALL pro_fetch_manpower_cost_value('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA');
-- SELECT * from manpower_cost_value;


#=======================================================pro_fetch_dim_days=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_days`;
CREATE PROCEDURE pro_fetch_dim_days(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

-- 		DECLARE pCurDate TIMESTAMP DEFAULT p_datetime;
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;


				LB_CURSOR:BEGIN
					
					DECLARE d DATETIME;

					DECLARE done INT DEFAULT 0;
					DECLARE s_cur CURSOR FOR
						SELECT 
							days d
						FROM days;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
								

					OPEN s_cur;
					FETCH s_cur INTO  d;

						WHILE1: WHILE done != 1 DO

							if (6 = month(d) and 30 = DAY(d)) or (12 = month(d) and 31 = DAY(d))  THEN 
								IF month(d) = 6 then 
									INSERT INTO dim_days 
									( `day`, `month`, `year`, `quarter`, ym, ymd, total, is_half_year )
									VALUES(d, month(d), year(d), QUARTER(d), CONCAT(year(d),"0", month(d)), CONCAT(year(d), "0", month(d), DAY(d)), TO_DAYS(d), 1);
								ELSE
									INSERT INTO dim_days 
									( `day`, `month`, `year`, `quarter`, ym, ymd, total, is_half_year )
									VALUES(d, month(d), year(d), QUARTER(d), CONCAT(year(d), month(d)), CONCAT(year(d),month(d), DAY(d)), TO_DAYS(d), 1);
								END if;
							ELSE 
								IF month(d) <10 AND day(d)<10 then 
									INSERT INTO dim_days 
									( `day`, `month`, `year`, `quarter`, ym, ymd, total, is_half_year )
									VALUES(d, month(d), year(d), QUARTER(d), CONCAT(year(d),"0", month(d)), CONCAT(year(d),"0", month(d), "0", DAY(d)), TO_DAYS(d), 0);
								ELSEIF  month(d) >=10 AND day(d)<10 THEN
									INSERT INTO dim_days 
									( `day`, `month`, `year`, `quarter`, ym, ymd, total, is_half_year )
									VALUES(d, month(d), year(d), QUARTER(d), CONCAT(year(d), month(d)), CONCAT(year(d), month(d), "0", DAY(d)), TO_DAYS(d), 0);
								ELSEIF  month(d) <10 AND day(d)>=10 THEN
									INSERT INTO dim_days 
									( `day`, `month`, `year`, `quarter`, ym, ymd, total, is_half_year )
									VALUES(d, month(d), year(d), QUARTER(d), CONCAT(year(d),"0", month(d)), CONCAT(year(d),"0", month(d),  DAY(d)), TO_DAYS(d), 0);
								ELSE
									INSERT INTO dim_days 
									( `day`, `month`, `year`, `quarter`, ym, ymd, total, is_half_year )
									VALUES(d, month(d), year(d), QUARTER(d), CONCAT(year(d), month(d)), CONCAT(year(d),month(d), DAY(d)), TO_DAYS(d), 0);
								END if;


							end if;


						
							FETCH s_cur INTO  d;
						END WHILE WHILE1;
				
				
					CLOSE s_cur;

				END LB_CURSOR;

-- SELECT * FROM dim_days;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
	END IF;


	
END;
-- DELIMITER ;
TRUNCATE table dim_days;
CALL pro_fetch_dim_days('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');
SELECT * from dim_days ;


#=======================================================pro_fetch_emp_attendance_month=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_attendance_month`;
CREATE PROCEDURE pro_fetch_emp_attendance_month(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_dt datetime)
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;

		DECLARE CURDATE TIMESTAMP DEFAULT p_dt; -- CURDATE();
		DECLARE firstCurDate DATE DEFAULT (SELECT DATE_SUB(CURDATE, INTERVAL extract( DAY FROM CURDATE)-1 DAY));	-- 本月第一天
		DECLARE lastCurDate DATE DEFAULT (SELECT DATE_SUB( DATE_ADD(CURDATE, INTERVAL 1 MONTH), INTERVAL DAY (CURDATE) DAY ));-- 本月最后一天
		DECLARE ymInt INT(6) DEFAULT DATE_FORMAT(CURDATE,	"%Y%m");
				

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

				DELETE FROM emp_attendance_month where `year_month` = ymInt;
	
				LB_INSERT:BEGIN
					INSERT INTO emp_attendance_month(
						emp_id,
						customer_id,
						orgainzation_id,
						at_hour,
						ot_hour,
						th_hour,
						`year_month`
					)
					SELECT t1.emp_id, customerId, t4.organization_id, IFNULL(sum(t1.hour_count),0) as at_hour , IFNULL(sum(t3.hour_count),0) as ot_hour, IFNULL(sum(t1.theory_hour_count),0) as th_hour, ymInt 
					FROM emp_attendance_day t1 
					INNER JOIN v_dim_emp t4 on t1.emp_id = t4.emp_id and t4.organization_id is not NULL
-- 					INNER JOIN theory_attendance t2 on t1.days = t2.days 
					LEFT JOIN emp_overtime_day t3 force index(index_days_eId) on t1.emp_id = t3.emp_id and t3.year_months = ymInt and t3.days BETWEEN firstCurDate and lastCurDate
					where t1.days BETWEEN firstCurDate and lastCurDate and t1.year_months = ymInt
					GROUP BY t4.emp_id;

				END LB_INSERT;




	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
	END IF;


	
END;
-- DELIMITER ;


		CALL pro_fetch_emp_attendance_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-01-02');
		CALL pro_fetch_emp_attendance_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-02-02');
		CALL pro_fetch_emp_attendance_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-03-02');
		CALL pro_fetch_emp_attendance_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-04-02');
		CALL pro_fetch_emp_attendance_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-05-02');
		CALL pro_fetch_emp_attendance_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-06-02');
		CALL pro_fetch_emp_attendance_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-07-02');
		CALL pro_fetch_emp_attendance_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-08-02');
		CALL pro_fetch_emp_attendance_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-09-02');
		CALL pro_fetch_emp_attendance_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-10-02');
		CALL pro_fetch_emp_attendance_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-11-02');
		CALL pro_fetch_emp_attendance_month('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-12-02');


#=======================================================pro_fetch_recruit_outlay=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_recruit_outlay`;
CREATE PROCEDURE pro_fetch_recruit_outlay(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_y int(4))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;


		START TRANSACTION;


		CREATE TEMPORARY TABLE tempOutlay AS
		SELECT t2.organization_id, SUM(t.outlay) o FROM recruit_channel t 
		INNER JOIN recruit_public t2 on t.recruit_public_id = t2.recruit_public_id
		where t2.`year` = p_y
		GROUP BY t2.organization_id
		;

			-- 已花
									LB_CURSOR:BEGIN
											DECLARE orgId VARCHAR(32);
											DECLARE done, p_error2 INT DEFAULT 0;
											DECLARE o DOUBLE(12,6);
											DECLARE s_cur CURSOR FOR
												SELECT t1.organization_id orgId, t1.o FROM tempOutlay t1;
											DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
											DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error2 = 1; END;
						
											OPEN s_cur;
											FETCH s_cur INTO orgId, o;
												WHILE1: WHILE done != 1 DO
													
													UPDATE recruit_value SET outlay = o/10000 where organization_id = orgId and `year` = p_y;
						
													FETCH s_cur INTO orgId, o;
												END WHILE WHILE1;
											CLOSE s_cur;
						
											IF p_error2 = 1 THEN  
												ROLLBACK;  
											ELSE  
												COMMIT; 
											END IF;
									END LB_CURSOR;
							DROP TABLE tempOutlay;

							--  汇总
							LB_CURSOR2:BEGIN
											DECLARE orgId, orgPId VARCHAR(32);
											DECLARE bv, ol DOUBLE(12,6);
											DECLARE done, p_error2 INT DEFAULT 0;
											DECLARE s_cur2 CURSOR FOR
												SELECT t1.organization_parent_id orgPId FROM dim_organization t1 WHERE t1.customer_id = customerId GROUP BY t1.organization_parent_id;
											DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
											DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error2 = 1; END;
						
											OPEN s_cur2;
											FETCH s_cur2 INTO orgPId;
												WHILE1: WHILE done != 1 DO
													SELECT sum(budget_value) as bv, sum(outlay) as ol
													INTO bv, ol
													FROM recruit_value t
													INNER JOIN dim_organization t1 on t.organization_id = t1.organization_id
													where `year` = p_y and t1.organization_parent_id = orgPId
													;

											IF ol > 0 THEN
											-- 	SELECT bv, ol;
												INSERT INTO recruit_value(recruit_value_id, customer_id, organization_id, budget_value, outlay, `year`) VALUES (REPLACE(uuid(),'-',''), customerId, orgPId, bv, ol, p_y);
											END IF;

-- 													SELECT sum(budget_value) as bv, sum(outlay) as ol
-- 													FROM recruit_value t
-- 													INNER JOIN dim_organization t1 on t.organization_id = t1.organization_id
-- 													where `year` = 2014 and t1.organization_parent_id = "fcb4d31b3470460f93be81cf1dd64cf0"
-- 


													FETCH s_cur2 INTO orgPId;
												END WHILE WHILE1;
											CLOSE s_cur2;
											IF p_error2 = 1 THEN  
												ROLLBACK;  
											ELSE  
												COMMIT; 
											END IF;
											
									END LB_CURSOR2;


-- 									LB_CURSOR:BEGIN
-- 											DECLARE orgId VARCHAR(32);
-- 											DECLARE done, p_error2 INT DEFAULT 0;
-- 											DECLARE o DOUBLE(12,6);
-- 											DECLARE s_cur CURSOR FOR
-- 												SELECT t1.organization_id orgId FROM dim_organization t1   WHERE t1.customer_id = customerId;
-- 											DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
-- 											DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error2 = 1; END;
-- 						
-- 											OPEN s_cur;
-- 											FETCH s_cur INTO orgId;
-- 												WHILE1: WHILE done != 1 DO
-- 													
-- 													SET o = (SELECT t2.organization_id, SUM(t.outlay) FROM recruit_channel t 
-- 																		INNER JOIN recruit_public t2 on t.recruit_public_id = t2.recruit_public_id
-- 																		where t2.customer_id = customerId AND t.year=p_y AND t2.organization_id = orgId
-- 																	);
-- 						
-- 													UPDATE recruit_value SET outlay = o
-- 													WHERE customer_id = customerId AND year=p_y AND organization_id = orgId;
-- -- 															SELECT o;
-- 						
-- 													FETCH s_cur INTO orgId;
-- 												END WHILE WHILE1;
-- 											CLOSE s_cur;
-- 						
-- 											IF p_error2 = 1 THEN  
-- 												ROLLBACK TO SAVEPOINT rv1;  
-- 											ELSE  
-- 												SAVEPOINT rv2;
-- 												COMMIT; 
-- 											END IF;
									-- 	END LB_CURSOR;

-- 						LB_CURSOR2:BEGIN
-- 								DECLARE sum_bv, sum_ol double(12,6);
-- 								DECLARE ext INT(1);
-- 								
-- 								DECLARE done2, p_error3 INT DEFAULT 0;
-- 								DECLARE orgId, pId VARCHAR(32);
-- 								DECLARE fp TEXT;
-- 								DECLARE s_cur2 CURSOR FOR
-- 									SELECT 
-- 										t1.organization_id orgId, t1.organization_parent_id pId, t1.full_path fp
-- 									FROM dim_organization t1
-- 									WHERE t1.customer_id = customerId 
-- 									GROUP BY t1.organization_id
-- 									ORDER BY t1.depth DESC, t1.full_path
-- 									;
-- 								DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done2 = 1;
-- 
-- 								OPEN s_cur2;
-- 								FETCH s_cur2 INTO orgId, pId, fp;
-- 
-- 									WHILE1: WHILE done2 != 1 DO
-- 										SELECT count(1) AS ext into ext FROM recruit_value t WHERE customer_id = customerId AND t.organization_id = orgId ;
-- 	
-- 										IF ext != 1 THEN
-- 											SELECT sum(t.budget_value) AS sum_bv, sum(t.outlay) AS sum_ol
-- 											INTO sum_bv, sum_ol
-- 											FROM dim_organization t1
-- 											LEFT JOIN recruit_value t ON t1.organization_id = t.organization_id AND t1.customer_id = t.customer_id
-- 											where 
-- 													LOCATE(fp,t1.full_path) 
-- 													AND t1.organization_parent_id = orgId;
-- 
-- 											IF sum_ol > 0 THEN 
-- 																					
-- 												INSERT INTO recruit_value 
-- 												(recruit_value_id, customer_id, organization_id, budget_value, outlay, YEAR, refresh) VALUES 
-- 												(REPLACE(UUID(),'-',''), customerId, orgId, sum_bv, sum_ol, p_y, now())
-- 												;
-- 											END IF;
-- 										END IF;
-- 
-- 
-- 										FETCH s_cur2 INTO orgId, pId, fp;
-- 									END WHILE WHILE1;
-- 								CLOSE s_cur2;
-- 
-- 								IF p_error3 = 1 THEN  
-- 									ROLLBACK TO SAVEPOINT rv2;  
-- 								ELSE  
-- 									COMMIT; 
-- 								END IF;
-- 						END LB_CURSOR2;
-- 
-- 	END IF;
END;
-- DELIMITER ;

CALL pro_fetch_recruit_outlay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2010);
CALL pro_fetch_recruit_outlay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2011);
CALL pro_fetch_recruit_outlay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2012);
CALL pro_fetch_recruit_outlay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2013);
CALL pro_fetch_recruit_outlay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2014);
CALL pro_fetch_recruit_outlay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2015);
-- SELECT * FROM recruit_value;




#=======================================================pro_fetch_out_talent=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_out_talent`;
CREATE PROCEDURE pro_fetch_out_talent(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

-- ======================================replace模板======================================
				LB_INSERT:BEGIN
					
						INSERT INTO out_talent (
							out_talent_id,
							customer_id,
							user_name_ch,
							user_name,
							email,
							age,
							sex,
							degree_id,
							url,
							school,
							tag,
							
							c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							customer_id,
							user_name_ch,
							user_name,
							email,
							age,
							sex,
							degree_id,
							url,
							school,
							tag,
						
							c_id
						FROM soure_out_talent t 
						WHERE customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								user_name_ch = t.user_name_ch,
								user_name = t.user_name,
								email = t.email,
								age = t.age,
								sex = t.sex,
								degree_id = t.degree_id,
								url = t.url,
								school = t.school,
								tag = t.tag	
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


END;
-- DELIMITER ;

CALL pro_fetch_out_talent('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');





#=======================================================pro_fetch_emp_pq_relation=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_pq_relation`;
CREATE PROCEDURE pro_fetch_emp_pq_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN
					
						INSERT INTO emp_pq_relation (
								emp_pq_relation_id, customer_id, emp_id, date, matching_soure_id, position_quality_id,  c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							t.customer_id,
							t1.emp_id,
							t.date,
							t2.matching_soure_id,
							t.position_quality_id,
						
							t.c_id
						FROM soure_emp_pq_relation t 
						INNER JOIN v_dim_emp t1 on t.emp_key = t1.emp_key
						INNER JOIN matching_soure t2 on t.soure = t2.v1
						WHERE t.customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								date = t.date,
								matching_soure_id = t2.matching_soure_id,
								position_quality_id = t.position_quality_id
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


END;
-- DELIMITER ;
TRUNCATE TABLE emp_pq_relation; 
CALL pro_fetch_emp_pq_relation('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');



#=======================================================pro_fetch_dim_quality=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_quality`;
CREATE PROCEDURE pro_fetch_dim_quality(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【岗位能力素质维】：数据刷新完成');

		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

				LB_INSERT:BEGIN
					
						INSERT INTO dim_quality (
							quality_id,
							customer_id,
							vocabulary,
							note,
							
							c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							customer_id,
							vocabulary,
							note,
						
							c_id
						FROM soure_dim_quality t 
						WHERE customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								vocabulary = t.vocabulary,
								note = t.note
						;
				END LB_INSERT;
			IF p_error = 1 THEN  
					ROLLBACK;  
 					SET p_message = concat("【岗位能力素质维】：数据刷新失败。操作用户：", optUserId);
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_quality', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'error', 221);
			ELSE  
					COMMIT;  
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_quality', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'sucess' , 221);
			END IF;  
			set @error_message = p_error;



END;
-- DELIMITER ;
-- TRUNCATE TABLE dim_quality;
CALL pro_fetch_dim_quality('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');




#=======================================================pro_fetch_position_quality=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_position_quality`;
CREATE PROCEDURE pro_fetch_position_quality(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

				LB_INSERT:BEGIN
					
						INSERT INTO position_quality (
							position_quality_id,
							customer_id,
							position_id,
							quality_id,
							matching_soure_id,
							show_index,
							
							c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							t.customer_id,
							t1.position_id,
							t2.quality_id,
							t3.matching_soure_id,
							t.show_index,
						
							t.c_id
						FROM soure_position_quality t 
						left JOIN dim_position t1 on t.position_name = t1.position_name 
						left JOIN dim_quality t2 on t2.vocabulary = t.quality_name
						left JOIN matching_soure t3 on t3.v1 = t.matching_soure_name
						WHERE t.customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								position_id = t1.position_id,
								quality_id = t2.quality_id,
								matching_soure_id = t3.matching_soure_id,
								show_index = t.show_index
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


END;
-- DELIMITER ;
-- TRUNCATE TABLE position_quality;
CALL pro_fetch_position_quality('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');



#=======================================================pro_fetch_matching_soure=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_matching_soure`;
CREATE PROCEDURE pro_fetch_matching_soure(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

-- ======================================replace模板======================================
				LB_INSERT:BEGIN
					
						INSERT INTO matching_soure (
							matching_soure_id,
							customer_id,
							v1,
							show_index,
							
							c_id
						)
						SELECT 
							matching_source_id,
							customer_id,
							v1,
							show_index,
						
						FROM soure_matching_source t 
						WHERE customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								v1 = t.v1,
								show_index = t.show_index
								
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


END;
-- DELIMITER ;

CALL pro_fetch_matching_soure('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');



#=======================================================pro_fetch_recruit_value=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_recruit_value`;
CREATE PROCEDURE pro_fetch_recruit_value(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_y int(4))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;


		START TRANSACTION;
			-- 预算
				LB_INSERT:BEGIN

						INSERT INTO recruit_value (
							recruit_value_id, customer_id, organization_id, budget_value, outlay, YEAR,  c_id
						)
						SELECT 
							REPLACE (UUID(), '-', ''), customer_id, organization_id, budget_value, 0, YEAR, c_id
						FROM soure_recruit_value t 
						WHERE customer_id = customerId AND year=p_y
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								organization_id = t.organization_id,
								budget_value = t.budget_value,
								year = t.year
								
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			SAVEPOINT rv1;
			COMMIT; 
			-- 已花
			LB_CURSOR:BEGIN
					DECLARE orgId VARCHAR(32);
					DECLARE done, p_error2 INT DEFAULT 0;
					DECLARE o DOUBLE(12,6);
					DECLARE s_cur CURSOR FOR
						SELECT t1.organization_id orgId FROM dim_position t1   WHERE t1.customer_id = customerId;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
					DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error2 = 1; END;

					OPEN s_cur;
					FETCH s_cur INTO orgId;
						WHILE1: WHILE done != 1 DO
							
							SET o = (SELECT SUM(t.outlay) FROM recruit_channel t 
												LEFT JOIN dim_position t1 ON t.position_id = t1.position_id 
												where t1.customer_id = customerId AND t.year=p_y AND t1.organization_id = orgId);

							UPDATE recruit_value SET outlay = o
							WHERE customer_id = customerId AND year=p_y AND organization_id = orgId;

							FETCH s_cur INTO orgId;
						END WHILE WHILE1;
					CLOSE s_cur;

					IF p_error2 = 1 THEN  
						ROLLBACK TO SAVEPOINT rv1;  
					ELSE  
						SAVEPOINT rv2;
						COMMIT; 
					END IF;
			END LB_CURSOR;


						LB_CURSOR2:BEGIN
								DECLARE sum_bv, sum_ol double(12,6);
								DECLARE ext INT(1);
								
								DECLARE done2, p_error3 INT DEFAULT 0;
								DECLARE orgId, pId VARCHAR(32);
								DECLARE fp TEXT;
								DECLARE s_cur2 CURSOR FOR
									SELECT 
										t1.organization_id orgId, t1.organization_parent_id pId, t1.full_path fp
									FROM dim_organization t1
									WHERE t1.customer_id = customerId 
									GROUP BY t1.organization_id
									ORDER BY t1.depth DESC, t1.full_path
									;
								DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done2 = 1;

								OPEN s_cur2;
								FETCH s_cur2 INTO orgId, pId, fp;

									WHILE1: WHILE done2 != 1 DO
										SELECT count(1) AS ext into ext FROM recruit_value t WHERE customer_id = customerId AND t.organization_id = orgId ;
	
										IF ext != 1 THEN
											SELECT sum(t.budget_value) AS sum_bv, sum(t.outlay) AS sum_ol
											INTO sum_bv, sum_ol
											FROM dim_organization t1
											LEFT JOIN recruit_value t ON t1.organization_id = t.organization_id AND t1.customer_id = t.customer_id
											where 
													LOCATE(fp,t1.full_path) 
													AND t1.organization_parent_id = orgId;

											IF sum_ol > 0 THEN 
																					
												INSERT INTO recruit_value 
												(recruit_value_id, customer_id, organization_id, budget_value, outlay, YEAR) VALUES 
												(REPLACE(UUID(),'-',''), customerId, orgId, sum_bv, sum_ol, p_y)
												;
											END IF;
										END IF;


										FETCH s_cur2 INTO orgId, pId, fp;
									END WHILE WHILE1;
								CLOSE s_cur2;

								IF p_error3 = 1 THEN  
									ROLLBACK TO SAVEPOINT rv2;  
								ELSE  
									COMMIT; 
								END IF;
						END LB_CURSOR2;



	END IF;
END;
-- DELIMITER ;

CALL pro_fetch_recruit_value('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2010);
CALL pro_fetch_recruit_value('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2011);
CALL pro_fetch_recruit_value('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2012);
CALL pro_fetch_recruit_value('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2013);
CALL pro_fetch_recruit_value('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2014);
CALL pro_fetch_recruit_value('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2015);





#=======================================================pro_fetch_recruit_channel=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_recruit_channel`;
CREATE PROCEDURE pro_fetch_recruit_channel(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN

						INSERT INTO recruit_channel (
							recruit_channel_id, customer_id, channel_id, employ_num, outlay, start_date, end_date,
							days, recruit_public_id,  YEAR,  c_id
						)
						SELECT 
							REPLACE (UUID(), '-', ''), customer_id, channel_id, employ_num, outlay, start_date, end_date, 
							TIMESTAMPDIFF(DAY, start_date, end_date) + 1, recruit_public_id, YEAR, now(), c_id
						FROM soure_recruit_channel t 
						WHERE customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								channel_id = t.channel_id,
								employ_num = t.employ_num,
								outlay = t.outlay,
								start_date = t.start_date,
								end_date = t.end_date,
								days = TIMESTAMPDIFF(DAY, t.start_date, t.end_date) +1,
								recruit_public_id = t.recruit_public_id,
								year = t.year
						;
				END LB_INSERT;



	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			
	END IF;


END;
-- DELIMITER ;
-- TRUNCATE TABLE recruit_channel;
CALL pro_fetch_recruit_channel('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');




#=======================================================pro_fetch_recruit_result=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_recruit_result`;
CREATE PROCEDURE pro_fetch_recruit_result(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN

						INSERT INTO recruit_result (
							recruit_result_id, customer_id, recruit_result_key, username, sex, age, degree_id, major, school,
							is_resume, is_interview, is_offer, is_entry, url, recruit_public_id, YEAR,  c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							customer_id,
							recruit_result_key,
							username,
							sex,
							age,
							degree_id,
							major,
							school,
							is_resume,
							is_interview,
							is_offer,
							is_entry,
							url,
							recruit_public_id,
							year,
						
							c_id
						FROM soure_recruit_result t 
						WHERE customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								recruit_result_key = t.recruit_result_key,
								username = t.username,
								sex = t.sex,
								age = t.age,
								degree_id = t.degree_id,
								major = t.major,
								school = t.school,
								is_resume = t.is_resume,
								is_interview = t.is_interview,
								is_offer = t.is_offer,
								is_entry = t.is_entry,
								url = t.url,
								recruit_public_id = t.recruit_public_id,
								year = t.year
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 

				-- 统计简历数量、面试数量、offer数量、入职数量
				LB_CURSOR:BEGIN
					DECLARE rpId VARCHAR(32);
					DECLARE done INT DEFAULT 0;
					DECLARE ir,ii,io,ie INT(4);
					DECLARE s_cur CURSOR FOR
						SELECT 
							recruit_public_id	rpId
						FROM recruit_public;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
								
					OPEN s_cur;
					FETCH s_cur INTO  rpId;

						WHILE1: WHILE done != 1 DO
							
						SET ir = (SELECT COUNT(is_resume) FROM recruit_result
											where  recruit_public_id = rpId and is_resume = 1 AND customer_id = customerId);
													
						SET ii = (SELECT COUNT(is_interview) FROM recruit_result
											where  recruit_public_id = rpId and is_interview = 1 AND customer_id = customerId);
													
						SET io = (SELECT COUNT(is_offer) FROM recruit_result
											where  recruit_public_id = rpId and is_offer = 1 AND customer_id = customerId);

						SET ie = (SELECT COUNT(is_entry) FROM recruit_result
											where  recruit_public_id = rpId and is_entry = 1 AND customer_id = customerId);

							UPDATE recruit_public SET employ_num = ie, resume_num = ir, interview_num = ii, offer_num = io, entry_num = ie
							WHERE recruit_public_id = rpId AND customer_id = customerId;

							FETCH s_cur INTO  rpId;
						END WHILE WHILE1;
					CLOSE s_cur;
				END LB_CURSOR;
			
	END IF;


END;
-- DELIMITER ;
-- TRUNCATE TABLE recruit_result;
CALL pro_fetch_recruit_result('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');




#=======================================================pro_fetch_recruit_public=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_recruit_public`;
CREATE PROCEDURE pro_fetch_recruit_public(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN

						INSERT INTO recruit_public (
							recruit_public_id, customer_id, organization_id, position_id, employ_num, plan_num, start_date, end_date, days, 
							resume_num, interview_num, offer_num, entry_num, is_public, YEAR,  c_id
						)
						SELECT 
							c_id, customer_id, organization_id, position_id, 0, plan_num, start_date, end_date, TIMESTAMPDIFF(DAY, start_date, end_date) + 1,
							0, 0, 0, 0, is_public, YEAR, now(), c_id
						FROM soure_recruit_public t 
						WHERE customer_id = customerId 
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								organization_id = t.organization_id,
								position_id = t.position_id,
								plan_num = t.plan_num,
								start_date = t.start_date,
								end_date = t.end_date,
								days = TIMESTAMPDIFF(DAY, t.start_date, t.end_date) +1,
								is_public = t.is_public,
								year = t.year
						;
				END LB_INSERT;



	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


END;
-- DELIMITER ;
-- TRUNCATE table recruit_public;
CALL pro_fetch_recruit_public('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');







#=======================================================pro_recruit_salary_statistics=======================================================
drop procedure if exists pro_recruit_salary_statistics;
create procedure `pro_recruit_salary_statistics`(_year_months INT, _years int,_customerid char(32))
begin 
			INSERT INTO recruit_salary_statistics(
				recruit_salary_statistics_id,
                                customer_id,
				position_id,
				position_name,
				rank_name,
				avg_sal,
				emp_total,
				year_months
      )
			SELECT 
				REPLACE(UUID(),'-',''),
                                _customerid, 
				rp.position_id AS positionId,
				dp.position_name AS positionName,
				vde.rank_name AS rankName,
				AVG(AES_DECRYPT(pay.pay_should, 'hrbi')) AS pay,COUNT(*) AS EMP_TOTAL, _year_months
			FROM recruit_public rp
			LEFT JOIN dim_position dp ON rp.customer_id = dp.customer_id AND dp.position_id = rp.position_id
			LEFT JOIN v_dim_emp vde ON vde.customer_id = dp.customer_id AND vde.position_id = dp.position_id
			LEFT JOIN pay ON pay.customer_id = vde.customer_id AND pay.emp_id = vde.emp_id AND pay.`year_month` = _year_months
			WHERE
				rp.customer_id = _customerid
				AND rp.`year` = _years
			GROUP BY rp.position_id, dp.position_name, vde.rank_name, REPLACE (UUID(), '-', ''),customer_id;
         commit;
end;





#=======================================================pro_fetch_emp_pq_relation=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_pq_relation`;
CREATE PROCEDURE pro_fetch_emp_pq_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN
					
						INSERT INTO emp_pq_relation (
								emp_pq_relation_id, customer_id, emp_id, date, matching_soure_id, position_quality_id,  c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							t.customer_id,
							t1.emp_id,
							t.date,
							t2.matching_soure_id,
							t.position_quality_id,
						
							t.c_id
						FROM soure_emp_pq_relation t 
						INNER JOIN v_dim_emp t1 on t.emp_key = t1.emp_key
						INNER JOIN matching_soure t2 on t.soure = t2.v1
						WHERE t.customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								date = t.date,
								matching_soure_id = t2.matching_soure_id,
								position_quality_id = t.position_quality_id
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


END;
-- DELIMITER ;
TRUNCATE TABLE emp_pq_relation; 
CALL pro_fetch_emp_pq_relation('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');





#=======================================================pro_fetch_position_quality=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_position_quality`;
CREATE PROCEDURE pro_fetch_position_quality(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

				LB_INSERT:BEGIN
					
						INSERT INTO position_quality (
							position_quality_id,
							customer_id,
							position_id,
							quality_id,
							matching_soure_id,
							show_index,
							
							c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							t.customer_id,
							t1.position_id,
							t2.quality_id,
							t3.matching_soure_id,
							t.show_index,
						
							t.c_id
						FROM soure_position_quality t 
						left JOIN dim_position t1 on t.position_name = t1.position_name 
						left JOIN dim_quality t2 on t2.vocabulary = t.quality_name
						left JOIN matching_soure t3 on t3.v1 = t.matching_soure_name
						WHERE t.customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								position_id = t1.position_id,
								quality_id = t2.quality_id,
								matching_soure_id = t3.matching_soure_id,
								show_index = t.show_index
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


END;
-- DELIMITER ;
-- TRUNCATE TABLE position_quality;
CALL pro_fetch_position_quality('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');






#=======================================================pro_fetch_organization_change=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_organization_change`;
CREATE PROCEDURE pro_fetch_organization_change(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE now TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;


-- 				LB_REPLACE:BEGIN	
-- 					REPLACE INTO organization_change(
-- 						organization_change_id, customer_id, emp_id, emp_key, change_date, change_type, change_type_id, 
-- 						organization_id, organization_name, organization_id_ed, organization_name_ed, 
-- 						position_id, position_name, sequence_id, sequence_name, ability_id, ability_name,  note
-- 					)
-- 					SELECT 
-- 						tt.id, tt.customer_id, t5.emp_id, t5.emp_key, tt.change_date, tt.change_type, tt.change_type_id,
-- 						t.organization_id, tt.organization_name, t7.organization_id, tt.organization_name_ed,
-- 						t1.position_id, tt.position_name, 
-- 						t2.sequence_id, tt.sequence_name, 
-- 						t3.ability_id, tt.ability_name, 
-- 					 tt.note
-- 					FROM soure_organization_change tt
-- 					LEFT JOIN dim_organization t on t.organization_key = tt.organization_key AND t.customer_id = tt.customer_id
-- 					LEFT JOIN dim_organization t7 on t7.organization_key = tt.organization_key_ed AND t.customer_id = tt.customer_id
-- 					LEFT JOIN dim_position t1 on t1.position_key = tt.position_key AND t1.customer_id = tt.customer_id
-- 					LEFT JOIN dim_sequence t2 on t2.sequence_key = tt.sequence_key AND t2.customer_id = tt.customer_id
-- 					LEFT JOIN dim_ability t3 on t3.ability_key = tt.ability_key AND t3.customer_id = tt.customer_id
-- -- 					LEFT JOIN dim_ability_lv t4 on t4.ability_lv_key = tt.ability_lv_key
-- -- 							AND t4.customer_id = tt.customer_id
-- 					LEFT JOIN v_dim_emp t5 on t5.emp_key = tt.emp_key AND t5.customer_id = tt.customer_id
-- 					
-- 					WHERE tt.customer_id = customerId
-- 				;
-- 			END LB_REPLACE;


			LB_CURSOR:BEGIN
					DECLARE proOrganId, proOrganName VARCHAR(32);
					DECLARE depth INT(1);
					DECLARE i INT(10);

					DECLARE eId,cTypeId,orgId,orgIdEd, position_id, position_name, sequence_id, sequence_name, ability_id, ability_name VARCHAR(32);
					DECLARE posId VARCHAR(32);
					DECLARE eKey VARCHAR(20);
					DECLARE cType INT(1);
					DECLARE cDate DATETIME;
					DECLARE orgName, orgNameEd VARCHAR(20);
					DECLARE fp, fpEd  VARCHAR(2500);
					DECLARE fuOrgaId, fuOrgaName, fuOrgaIdEd, fuOrgaNameEd VARCHAR(32);

					DECLARE done INT DEFAULT 0;
					DECLARE s_cur CURSOR FOR
						SELECT 
							t.emp_id eId,
							t.emp_key eKey,
							t.change_date cDate,
							t.change_type cType,
							t.change_type_id cTypeId,
							t.organization_id orgId,
							t.organization_name orgName,
							t.organization_id_ed orgIdEd,
							t.organization_name_ed orgNameEd,
							t1.full_path fp,
							t2.full_path fpEd,
							t.position_id, t.position_name,
							t.sequence_id, t.sequence_name,
							t.ability_id, t.ability_name
						FROM organization_change2 t 
						LEFT JOIN dim_organization t1 on t.organization_id = t1.organization_id
						LEFT JOIN dim_organization t2 on t.organization_id_ed = t2.organization_id
						;


					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;


					OPEN s_cur;
						FETCH s_cur INTO eId, eKey, cDate, cType, cTypeId, orgId, orgName, orgIdEd, orgNameEd, fp, fpEd, position_id, position_name, sequence_id, sequence_name, ability_id, ability_name ;

						WHILE1: WHILE done != 1 DO


 -- 入职，离职，退休
IF cType = 3 OR cType = 5 OR cType = 8 THEN

									INSERT INTO organization_change(organization_change_id, customer_id, emp_id, emp_key, change_date, change_type, change_type_id, 
													organization_id, organization_name, organization_id_ed, organization_name_ed,
													position_id, position_name, sequence_id, sequence_name, ability_id, ability_name,
													 note )
									SELECT replace(uuid(), '-', ''), customerId, eId, eKey, cDate, cType, cTypeId,
												tt.organization_id, tt.organization_name , NULL, NULL, 
												 position_id, position_name, sequence_id, sequence_name, ability_id, ability_name,
												NULL
									FROM dim_organization tt where LOCATE(tt.full_path,
										(SELECT t1.full_path fp
											FROM organization_change2 t 
											LEFT JOIN dim_organization t1 on t.organization_id = t1.organization_id
											where t.change_type = cType  and t.emp_key = eKey
															)
									);
ELSE

									SET @num = (SELECT fn_occurrence_num(fp,"_"));
									SET @numEd = (SELECT fn_occurrence_num(fpEd,"_"));

									loop1: LOOP				
										SET @tempFp = (SELECT SUBSTRING_INDEX(fp,"_", @num));
										SET @tempFpEd = (SELECT SUBSTRING_INDEX(fpEd,"_", @num));


-- =======================================================================
										-- 调入，调出
										IF cType = 2 OR cType = 4 THEN
												IF @tempFp != @tempFpEd THEN
															SELECT t.organization_id, t.organization_name INTO fuOrgaId, fuOrgaName FROM dim_organization t where t.organization_key = fn_last_str(@tempFp,"_");
															SELECT t.organization_id, t.organization_name INTO fuOrgaIdEd, fuOrgaNameEd FROM dim_organization t where t.organization_key = fn_last_str(@tempFpEd ,"_");

															INSERT INTO organization_change (organization_change_id, customer_id, emp_id, emp_key, change_date, change_type, change_type_id, 
																			organization_id, organization_name, organization_id_ed, organization_name_ed,
																			position_id, position_name, sequence_id, sequence_name, ability_id, ability_name,
																			 note )
															VALUES (
																	replace(uuid(), '-', ''), customerId, 
																	eId, eKey,cDate, cType, cTypeId, fuOrgaId, fuOrgaName, fuOrgaIdEd, fuOrgaNameEd, 
																	position_id, position_name, sequence_id, sequence_name, ability_id, ability_name,
																 NULL
															);
												END IF;
										END IF;
-- =======================================================================
										-- 离职，退休
-- 										IF cType = 5 OR cType = 8 THEN
-- 															SELECT t.organization_id, t.organization_name INTO fuOrgaId, fuOrgaName FROM dim_organization t where t.organization_key = fn_last_str(@tempFp,"_");
-- 															SELECT t.organization_id, t.organization_name INTO fuOrgaIdEd, fuOrgaNameEd FROM dim_organization t where t.organization_key = fn_last_str(@tempFpEd ,"_");
-- 
-- 															INSERT INTO organization_change (organization_change_id, customer_id, emp_id, emp_key, change_date, change_type, change_type_id, 
-- 																			organization_id, organization_name, organization_id_ed, organization_name_ed,
-- 																			position_id, position_name, sequence_id, sequence_name, ability_id, ability_name,
-- 																			 note )
-- 															VALUES (
-- 																	replace(uuid(), '-', ''), customerId, 
-- 																	eId, eKey,cDate, cType, cTypeId, fuOrgaId, fuOrgaName, fuOrgaIdEd, fuOrgaNameEd, 
-- 																	position_id, position_name, sequence_id, sequence_name, ability_id, ability_name,
-- 																 NULL
-- 															);
-- 										END IF;
-- =======================================================================
										SET @num = @num-1;

									IF @num = 0 THEN LEAVE loop1; END IF;										
									END LOOP loop1 ;

END IF;

									FETCH s_cur INTO eId, eKey, cDate, cType, cTypeId, orgId, orgName, orgIdEd, orgNameEd, fp, fpEd, position_id, position_name, sequence_id, sequence_name, ability_id, ability_name ;
						END WHILE WHILE1;

					CLOSE s_cur;
		END LB_CURSOR;


	IF p_error = 1 THEN ROLLBACK;
	ELSE COMMIT;
	END IF;

END;
-- DELIMITER ;

TRUNCATE TABLE organization_change;

CALL pro_fetch_organization_change('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');
SELECT * from organization_change ;



#=======================================================pro_fetch_dim_organization=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_organization`;
CREATE PROCEDURE pro_fetch_dim_organization(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【机构维】');
		DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_fetch_dim_organization');

		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
		START TRANSACTION;

			INSERT INTO dim_organization(
				organization_id, customer_id, organization_key, organization_name, organization_name_full,
				organization_parent_id, 
				organization_parent_key, organization_company_id, organization_type_id, note, is_single,
				full_path, has_children, depth,
				refresh_date, profession_id,
				c_id
			)
			SELECT 
				REPLACE (uuid(), '-', ''), sorg.customer_id, sorg.organization_key, sorg.organization_name, sorg.organization_name_full,
				fn_key_to_id(sorg.organization_parent_key, sorg.customer_id, 'org'),
				sorg.organization_parent_key,
				sorg.organization_company_id, ot.organization_type_id, sorg.note, sorg.is_single,
				NULL, NULL, null, 
				startTime, sorg.profession_id, 
				c_id
			FROM soure_dim_organization sorg
			INNER JOIN dim_organization_type ot on sorg.organization_type_key = ot.organization_type_key
			WHERE sorg.customer_id = customerId
			ORDER BY ot.organization_type_level
			ON DUPLICATE KEY UPDATE 
				organization_id = sorg.organization_key,
				organization_name = sorg.organization_name,
				organization_name_full = sorg.organization_name_full,
				organization_parent_id = fn_key_to_id(sorg.organization_parent_key, sorg.customer_id, 'org'),
				organization_parent_key = sorg.organization_parent_key,
				organization_type_id = ot.organization_type_id,
				note = sorg.note,
				is_single = sorg.is_single,
				refresh_date = startTime,
				profession_id = sorg.profession_id
			;


		INSERT INTO db_log 
		VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_dim_organization', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		set @error_message = p_error;  


	
END;
-- DELIMITER ;

CALL pro_fetch_dim_organization('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');
SELECT * from dim_organization;





#=======================================================pro_fetch_dim_organization_days=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_organization_days`;
CREATE PROCEDURE pro_fetch_dim_organization_days(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE now TIMESTAMP DEFAULT now()
	;

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

				DELETE FROM history_dim_organization where days = CURDATE() AND customer_id = customerId;

				INSERT INTO history_dim_organization (
					history_dim_organization_id, customer_id, 
					organization_id, organization_company_id, organization_type_id, organization_key, organization_parent_key, organization_parent_id, organization_name,
					organization_name_full, note, is_single, full_path, has_children, depth, refresh_date, profession_id, c_id, days, `year`
				)
				SELECT 
					replace(UUID(),'-',''),customer_id,
					organization_id, organization_company_id, organization_type_id, organization_key, organization_parent_key, organization_parent_id, organization_name,
					organization_name_full, note, is_single, full_path, has_children, depth, refresh_date, profession_id, c_id, now, YEAR(now)
				FROM dim_organization  
				WHERE customer_id = customerId
				;

	IF p_error = 1 THEN ROLLBACK;
	ELSE COMMIT;
	END IF;
	
END;
-- DELIMITER ;

CALL pro_fetch_dim_organization_days('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');



#=======================================================pro_update_full_path_org=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_update_full_path_org`;
CREATE PROCEDURE pro_update_full_path_org(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

				LB_CURSOR:BEGIN
		
					DECLARE oKey VARCHAR(20);

					DECLARE done INT DEFAULT 0;
					DECLARE s_cur CURSOR FOR
						SELECT organization_key oKey FROM dim_organization;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;

					OPEN s_cur;
					FETCH s_cur INTO  oKey;

						WHILE1: WHILE done != 1 DO

								SET @fp = SUBSTRING(fn_get_tree_list_dim_organization(oKey), 4);
								SET @depth = (SELECT LENGTH(@fp) - LENGTH(REPLACE(@fp,'_','')) as deept);

								UPDATE dim_organization SET full_path = @fp, depth = @depth where organization_key = oKey;
								
							FETCH s_cur INTO  oKey;
						END WHILE WHILE1;				
				
					CLOSE s_cur;
				END LB_CURSOR;

	IF p_error = 1 THEN  ROLLBACK;  
	ELSE COMMIT;
	END IF;


	
END;
-- DELIMITER ;

CALL pro_update_full_path_org('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');

SELECT full_path, depth from dim_organization;

#=======================================================fn_get_tree_list_dim_organization=======================================================
DROP FUNCTION IF EXISTS fn_get_tree_list_dim_organization;
CREATE FUNCTION `fn_get_tree_list_dim_organization` (p_key varchar(100)) 
RETURNS VARCHAR (1000)
BEGIN
	SET @temp = '';   
	SET @temps = p_key ;	
	WHILE @temps IS NOT NULL DO 
		SET @temp = CONCAT_WS('_',@temps, @temp);
		SET @_key = SUBSTRING_INDEX(@temp,'_',1);	
		SELECT GROUP_CONCAT(organization_parent_key) INTO @temps FROM dim_organization  WHERE organization_key = @_key;
	END WHILE ;
    
	RETURN  @temp;
END ;

SELECT fn_get_tree_list_dim_organization("bjTraining");



#=======================================================proc_sql_soure_v_dim_emp=======================================================
drop procedure if exists proc_sql_soure_v_dim_emp;
create procedure proc_sql_soure_v_dim_emp()
begin 
 start transaction;
 update soure_v_dim_emp a left join (select * from soure_code_item where code_group_id='degree') b on a.degree=b.code_item_name 
              left join dim_performance c on a.performance_name=c.performance_name
              left join dim_sequence_sub d on a.sequence_sub_name=d.sequence_sub_name 
              left join dim_sequence e on a.sequence_name=e.sequence_name
              left join dim_organization f on a.organization_parent_name=f.organization_name
              left join dim_job_title g on a.job_title_name=g.job_title_name
              left join dim_ability_lv h on  a.ability_lv_name=h.ability_lv_name
                         set a.degree_id=b.code_item_id,
                             a.performance_id=c.performance_id,
                             a.sequence_sub_id=d.sequence_sub_id,
                             a.sequence_id=e.sequence_id,
                             a.organization_parent_id=f.organization_id,
                             a.job_title_id=g.job_title_id,
                             a.ability_lv_id=h.ability_lv_id;


  update soure_v_dim_emp set customer_id='b5c9410dc7e4422c8e0189c7f8056b5f';
  
  update soure_v_dim_emp a set  a.position_id=(select position_id from dim_position b where a.position_name=b.position_name); 
 
  update soure_v_dim_emp SET organization_name = '上海业务部' where organization_parent_name = '上海中心' and organization_name ='业务部';
  update soure_v_dim_emp SET organization_name = '业务部tets' where organization_parent_name = '广州中心' and organization_name ='业务部';

  UPDATE soure_v_dim_emp a INNER JOIN dim_ability b ON a.ability_name = b.ability_name AND b.type = 1 SET a.ability_id = b.ability_id WHERE a.sequence_name = '管理序列';
  UPDATE soure_v_dim_emp a INNER JOIN dim_ability b ON a.ability_name = b.ability_name AND b.type = 0 SET a.ability_id = b.ability_id WHERE a.sequence_name != '管理序列';

  update soure_v_dim_emp a set a.organization_id=(select organization_id from dim_organization b  where a.organization_name=b.organization_name and a.organization_parent_id=b.organization_parent_id); 

  update soure_v_dim_emp a set emp_hf_id=(select emp_id from (select * from soure_v_dim_emp) b where a.emp_hf_key=b.emp_key);
  update soure_v_dim_emp a set emp_hf_id=(select emp_id from (select * from v_dim_emp) b where a.emp_hf_key=b.emp_key) where emp_hf_id is null;

  update soure_v_dim_emp a set user_name=(select emp_key from (select * from soure_v_dim_emp) b where a.emp_key=b.emp_key);
-- 分公司
  update soure_v_dim_emp a set branch_company_id=(select organization_parent_id from (select * from soure_v_dim_emp) b where a.emp_key=b.emp_key);

  update soure_v_dim_emp set ability_lv_name='1级' where ability_lv_name='级';
  commit;
end;




#=======================================================fn_get_tree_list_v_dim_emp=======================================================
DROP FUNCTION IF EXISTS fn_get_tree_list_v_dim_emp;
CREATE FUNCTION `fn_get_tree_list_v_dim_emp` (_emp_key varchar(100)) 
RETURNS VARCHAR (1000)
BEGIN
	SET @temp = '';   
	SET @temps = _emp_key ;	
	WHILE @temps IS NOT NULL DO 
		SET @temp = CONCAT_WS('/',@temps, @temp);
		SET @_emp_key1 = SUBSTRING_INDEX(@temp,'/',1);	
		SELECT GROUP_CONCAT(emp_hf_key) INTO @temps FROM v_dim_emp  WHERE emp_key = @_emp_key1;
	END WHILE ;
    
	RETURN  @temp;
END ;



#=======================================================pro_update_full_path=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_update_full_path`;
CREATE PROCEDURE pro_update_full_path(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

				LB_CURSOR:BEGIN
		
					DECLARE eKey VARCHAR(20);

					DECLARE done INT DEFAULT 0;
					DECLARE s_cur CURSOR FOR
						SELECT 
							emp_key eKey
						from soure_v_dim_emp;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
								

					OPEN s_cur;
					FETCH s_cur INTO  eKey;

						WHILE1: WHILE done != 1 DO

								INSERT INTO emp_report_relation (emp_report_id, emp_key, report_relation)
								VALUES(
									replace(uuid(),'-',''), eKey, fn_get_tree_list_v_dim_emp(eKey)
								);

							FETCH s_cur INTO  eKey;
						END WHILE WHILE1;				
				
					CLOSE s_cur;
				END LB_CURSOR;



	IF p_error = 1 THEN  ROLLBACK;  
	ELSE COMMIT;
	END IF;


	
END;
-- DELIMITER ;

truncate table emp_report_relation;
CALL pro_update_full_path('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');













#=======================================================pro_fetch_dim_posistion=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_posistion`;
CREATE PROCEDURE pro_fetch_dim_posistion(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

-- ======================================replace模板======================================
				LB_REPLACE:BEGIN
					
						INSERT INTO dim_position (
							position_id,
							customer_id,
							position_key,
							position_name,
							
							c_id
						)
						SELECT 
							replace(UUID(), '-', ''),
							customer_id,
							position_key,
							position_name, c_id
						FROM soure_dim_position t
						ON DUPLICATE KEY UPDATE 
							customer_id = t.customer_id,
							position_key = t.position_key,
							position_name = t.position_name
						;
								
				END LB_REPLACE;






IF p_error = 1 THEN ROLLBACK;
ELSE COMMIT;
END IF;
END;
-- DELIMITER ;

CALL pro_fetch_dim_posistion('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');





#=======================================================pro_fetch_budget_position_number=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_budget_position_number`;
CREATE PROCEDURE pro_fetch_budget_position_number(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

-- ======================================replace模板======================================
				LB_INSERT:BEGIN
					
						INSERT INTO budget_position_number (
							budget_position_number_id,
							customer_id,
							organization_id,
							position_id,
							number,
							year,
							
							c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							customer_id,
							organization_id,
							position_id,
							number,
							year,
						
							c_id
						FROM soure_budget_position_number t 
						WHERE customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								position_id = t.position_id,
								number = t.number,
								year = t.year
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


END;
-- DELIMITER ;

CALL pro_fetch_budget_position_number('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');




#=======================================================pro_fetch_emp_overtime_other_day=======================================================
DROP PROCEDURE IF EXISTS `pro_fetch_emp_overtime_other_day`;
create  procedure pro_fetch_emp_overtime_other_day(p_customer_id varchar(32),p_user varchar(10))
BEGIN
    DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

  START TRANSACTION;
   INSERT INTO emp_overtime_day (
    emp_overtime_day_id,
    customer_id,
    emp_id,
    emp_key,
    user_name_ch,
    hour_count,
    organization_id,
    checkwork_type_id,
    days,
    c_id)
    select replace(uuid(),'-',''),
           customer_id,
           emp_id,
           emp_key,
           user_name_ch,
           (TIMESTAMPDIFF(MINUTE,startdate,enddate) / 60) as hour_count,
           organization_id,
           checkwork_type_id,
           days,
           c_id
    from soure_emp_overtime_other_day t where checkwork_type_id='7f533380bf574dd9916972a08808e121' 
		on duplicate key update 
          emp_id = t.emp_id,
          emp_key=t.emp_key,
          user_name_ch = t.user_name_ch,
          hour_count= TIMESTAMPDIFF(MINUTE,startdate,enddate) / 60,
          organization_id =t.organization_id,
          checkwork_type_id=t.checkwork_type_id,
          days=t.days
   ;

    INSERT INTO emp_other_day (
    emp_other_day_id,
    customer_id,
    emp_id,
    emp_key,
    user_name_ch,
    hour_count,
    organization_id,
    checkwork_type_id,
    days,
    c_id)
    select replace(uuid(),'-',''),
           customer_id,
           emp_id,
           emp_key,
           user_name_ch,
           (TIMESTAMPDIFF(MINUTE,startdate,enddate) / 60) as hour_count,
           organization_id,
           checkwork_type_id,
           days,
           c_id
    from soure_emp_overtime_other_day t1 where checkwork_type_id<>'7f533380bf574dd9916972a08808e121' 
		on duplicate key update 
          emp_id = t1.emp_id,
          emp_key=t1.emp_key,
          user_name_ch = t1.user_name_ch,
          hour_count= TIMESTAMPDIFF(MINUTE,startdate,enddate) / 60,
          organization_id =t1.organization_id,
          checkwork_type_id=t1.checkwork_type_id,
          days=t1.days;

		IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
	END IF;

END;
CALL pro_fetch_emp_overtime_other_day('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');
-- TRUNCATE TABLE emp_overtime_day;
-- TRUNCATE TABLE emp_other_day;
 select *  from emp_other_day;




#=======================================================pro_fetch_theory_attendance=======================================================
DROP PROCEDURE IF EXISTS `pro_fetch_theory_attendance`;
CREATE PROCEDURE pro_fetch_theory_attendance(in p_customer_id VARCHAR(32), in p_years VARCHAR(4))
begin
	declare num INTEGER default 0;
	declare dat date;
  declare d varchar(2);
  declare uuid1 char(32);
  declare uuid2 char(32);
  declare wh int (1) DEFAULT 8;
  declare dayoffwh int (1) DEFAULT 0;
	START TRANSACTION;
	while num <= 364 do

						SELECT  DATE_ADD(concat(p_years,'-01-01'),INTERVAL num DAY) into dat from qzb_0608;
						select DATE_FORMAT(dat, '%w') into d from qzb_0608;
						select replace(uuid(),'-','') into uuid1 from qzb_0608;
						if  d='6'   then
							INSERT INTO theory_attendance ( theory_attendance_id, days, hour_count, customer_id, YEAR ) VALUES ( uuid1, dat, 0, p_customer_id, p_years);
						end if;
						if  d='0'   then
							INSERT INTO theory_attendance ( theory_attendance_id, days, hour_count, customer_id, YEAR ) VALUES ( uuid1, dat, 0, p_customer_id, p_years );
						end if;
						if  d='1'   then
							INSERT INTO theory_attendance ( theory_attendance_id, days, hour_count, customer_id, YEAR ) VALUES ( uuid1, dat, wh, p_customer_id, p_years );
						end if;
						if  d='2'   then
							INSERT INTO theory_attendance ( theory_attendance_id, days, hour_count, customer_id, YEAR ) VALUES ( uuid1, dat, wh, p_customer_id, p_years );					
						end if;
						if  d='3'   then
						 INSERT INTO theory_attendance ( theory_attendance_id, days, hour_count, customer_id, YEAR ) VALUES ( uuid1, dat, wh, p_customer_id, p_years );
						end if;
						if  d='4'   then
						 INSERT INTO theory_attendance ( theory_attendance_id, days, hour_count, customer_id, YEAR ) VALUES ( uuid1, dat, wh, p_customer_id, p_years );
						end if;
						if  d='5'   then
						 INSERT INTO theory_attendance ( theory_attendance_id, days, hour_count, customer_id, YEAR ) VALUES ( uuid1, dat, wh, p_customer_id, p_years );
						end if;

						set num = num + 1;

	end while ;



 COMMIT;
end;
-- TRUNCATE table theory_attendance;

CALL pro_fetch_theory_attendance('b5c9410dc7e4422cwhe01wh9c7fwh056b5f', '2010');
CALL pro_fetch_theory_attendance('b5c9410dc7e4422cwhe01wh9c7fwh056b5f', '2011');
CALL pro_fetch_theory_attendance('b5c9410dc7e4422cwhe01wh9c7fwh056b5f', '2012');
CALL pro_fetch_theory_attendance('b5c9410dc7e4422cwhe01wh9c7fwh056b5f', '2013');
CALL pro_fetch_theory_attendance('b5c9410dc7e4422cwhe01wh9c7fwh056b5f', '2014');
CALL pro_fetch_theory_attendance('b5c9410dc7e4422cwhe01wh9c7fwh056b5f', '2015');
CALL pro_fetch_theory_attendance('b5c9410dc7e4422cwhe01wh9c7fwh056b5f', '2016');

-- select year,count(*) from theory_attendance group by year;





#=======================================================pro_fetch_emp_attendance_day=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_attendance_day`;
CREATE PROCEDURE pro_fetch_emp_attendance_day(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

				LB_INSERT:BEGIN
					INSERT INTO emp_attendance_day(
						emp_attendance_day_id, customer_id, emp_key,emp_id, user_name_ch,population_id, hour_count,
						theory_hour_count, organization_id, checkwork_type_id, days, c_id
					)
					SELECT 
						replace(uuid(),'-',''), t.customer_id, emp_key, emp_id, user_name_ch,t.population_id,
						(TIMESTAMPDIFF(MINUTE ,clock_in, clock_out) / 60) -1 AS hour_count,
						t1.hour_count AS theory_hour_count, organization_id, checkwork_type_id, t.days, t.c_id
					FROM soure_emp_attendance_day t
					INNER JOIN theory_attendance t1 on t.days = t1.days
					ON DUPLICATE KEY UPDATE 
							customer_id = t.customer_id,
							emp_key = t.emp_key,
							emp_id = t.emp_id,
							user_name_ch = t.user_name_ch,
							hour_count = (TIMESTAMPDIFF(MINUTE ,clock_in, clock_out) / 60) -1,
							theory_hour_count = t1.hour_count,
							organization_id = t.organization_id,
							checkwork_type_id = t.checkwork_type_id,
							days = t.days
					;
				END LB_INSERT;


IF p_error = 1 THEN ROLLBACK;
ELSE COMMIT;
END IF;

	
END;
-- DELIMITER ;
TRUNCATE TABLE emp_attendance_day;
CALL pro_fetch_emp_attendance_day('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');


-- INSERT into soure_emp_attendance_day(
-- c_id, customer_id, emp_key, emp_id, user_name_ch, population_id, clock_in, clock_out, organization_id, checkwork_type_id, days
-- )
-- SELECT
-- REPLACE (uuid(), '-', ''), customer_id, emp_key, emp_id, user_name_ch, population_id, 
-- concat( DATE_FORMAT(days, '%Y-%m-%d'), " 09:00:00" ), concat( DATE_FORMAT(days, '%Y-%m-%d'), " 18:00:00" ), organization_id, 'b90bb95e3c01413b80899b49ba13392e', days
-- FROM dim_emp
-- where dim_emp.run_off_date is NULL
-- ;



#=======================================================pro_fetch_dim_checkwork_type=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_checkwork_type`;
CREATE PROCEDURE pro_fetch_dim_checkwork_type(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), IN p_key_work VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【异动类型维】：数据刷新完成');
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;


				LB_REPLACE:BEGIN
					
						REPLACE INTO dim_checkwork_type (
							checkwork_type_id,
							customer_id,
							checkwork_type_name,
							curt_name,
							show_index
						)
						SELECT 
							code_item_id,
							customer_id,
							code_item_name,
							curt_name,
							show_index
					FROM soure_code_item where customer_id = customerId and code_group_id = p_key_work;
								
				END LB_REPLACE;

			IF p_error = 1 THEN  
					ROLLBACK;  
 					SET p_message = concat("【异动类型维】：数据刷新失败。操作用户：", optUserId);
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_checkwork_type', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'error', 222);
			ELSE  
					COMMIT;  
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_checkwork_type', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'sucess' , 222);
			END IF;  
			set @error_message = p_error;




	
END;
-- DELIMITER ;

-- CALL pro_fetch_dim_checkwork_type('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', 'checkwork');







#=======================================================pro_fetch_dim_encourages=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_encourages`;
CREATE PROCEDURE pro_fetch_dim_encourages(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
			DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
			DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
			DECLARE startTime TIMESTAMP DEFAULT now();
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000) DEFAULT '【激励要素-维度表】';
DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_fetch_dim_encourages');
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

				LB_INSERT:BEGIN
					
						INSERT INTO dim_encourages (
							encourages_id,
							customer_id,
							encourages_key,
							content,
							c_id
						)
						SELECT 
							replace(UUID(),'-',''),
							customer_id,
							encourages_key,
							content,
							c_id
						FROM soure_dim_encourages t 
						WHERE customer_id = customerId
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								encourages_key = t.encourages_key,
								content = t.content
						;
				END LB_INSERT;

		INSERT INTO db_log 
		VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_dim_encourages', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		set @error_message = p_error;  


END;
-- DELIMITER ;

-- CALL pro_fetch_dim_encourages('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');




#=======================================================pro_fetch_project_input_detail=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_project_input_detail`;
CREATE PROCEDURE pro_fetch_project_input_detail(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN
					
						INSERT INTO project_input_detail (
							project_input_detail_id,
							customer_id,
							project_id,
							project_input_type_id,
							outlay,
							date, type
						)
						SELECT 
							id,
							customer_id,
							project_id,
							project_input_type_id,
							outlay,
							date, type
						FROM soure_project_input_detail t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								project_id = t.project_id,
								project_input_type_id = t.project_input_type_id,
								outlay = t.outlay,
								date = t.date,
								type = t.type
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			-- 去掉已删除数据
			DELETE FROM project_input_detail WHERE project_input_detail_id NOT IN 
			( SELECT t2.id FROM soure_project_input_detail t2 WHERE t2.customer_id = customerId); 
	END IF;

END;
-- DELIMITER ;
CALL pro_fetch_project_input_detail('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');


#=======================================================pro_fetch_project_cost=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_project_cost`;
CREATE PROCEDURE pro_fetch_project_cost(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN
					
						INSERT INTO project_cost (
							project_cost_id,
							customer_id,
							input,
							output,
							gain,
							project_id,
							date,type
						)
						SELECT 
							id,
							customer_id,
							input,
							output,
							gain,
							project_id,
							date,type
						FROM soure_project_cost t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								input = t.input,
								output = t.output,
								gain = t.gain,
								project_id = t.project_id,
								date = t.date,
								type = t.type
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			-- 去掉已删除数据
			DELETE FROM project_cost WHERE project_cost_id NOT IN 
			( SELECT t2.id FROM soure_project_cost t2 WHERE t2.customer_id = customerId); 

			CALL pro_fetch_project_input_detail(customerId,p_user_id);
	END IF;

END;
-- DELIMITER ;
TRUNCATE TABLE project_cost;
CALL pro_fetch_project_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');



#=======================================================pro_fetch_project_manpower=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_project_manpower`;
CREATE PROCEDURE pro_fetch_project_manpower(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN
					
						INSERT INTO project_manpower (
							project_manpower_id,
							customer_id,
							emp_id,
							input,
							note,
							project_id,
							project_sub_id,
							date, type
						)
						SELECT 
							id,
							customer_id,
							emp_id,
							input,
							note,
							project_id,
							project_sub_id,
							date, type
						FROM soure_project_manpower t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								emp_id = t.emp_id,
								input = t.input,
								note = t.note,
								project_id = t.project_id,
								project_sub_id = t.project_sub_id,
								date = t.date,
								type = t.type
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			-- 去掉已删除数据
			DELETE FROM project_manpower WHERE project_manpower_id NOT IN 
			( SELECT t2.id FROM soure_project_manpower t2 WHERE t2.customer_id = customerId); 
	END IF;

END;
-- DELIMITER ;
TRUNCATE TABLE project_manpower;
CALL pro_fetch_project_manpower('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');


#=======================================================pro_fetch_project=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_project`;
CREATE PROCEDURE pro_fetch_project(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN
					
						INSERT INTO project (
							project_id,
							customer_id,
							project_key,
							project_name,
							emp_id,
							organization_id,
							project_type_id,
							project_progress_id,
							project_parent_id,
							full_path,
							has_chilren,
							start_date,
							end_date
						)
						SELECT 
							id,
							customer_id,
							project_key,
							project_name,
							emp_id,
							organization_id,
							project_type_id,
							project_progress_id,
							project_parent_id,
							full_path,
							has_chilren,
							start_date,
							end_date
						FROM soure_project t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								project_key = t.project_key,
								project_name = t.project_name,
								emp_id = t.emp_id,
								organization_id = t.organization_id,
								project_type_id = t.project_type_id,
								project_progress_id = t.project_progress_id,
								project_parent_id = t.project_parent_id,
								full_path = t.full_path,
								has_chilren = t.has_chilren,
								start_date = t.start_date,
								end_date = t.end_date
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			-- 去掉已删除数据
			DELETE FROM project WHERE project_id NOT IN 
			( SELECT t2.id FROM soure_project t2 WHERE t2.customer_id = customerId); 
	END IF;

END;
-- DELIMITER ;

-- TRUNCATE dim_project;
CALL pro_fetch_project('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');


#=======================================================pro_fetch_dim_project_input_type=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_project_input_type`;
CREATE PROCEDURE pro_fetch_dim_project_input_type(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【项目投入费用类型】：数据刷新完成');
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN
					
						INSERT INTO dim_project_input_type (
							project_input_type_id,
							customer_id,
							project_input_type_name,
							show_index
						)
						SELECT 
							id,
							customer_id,
							project_input_type_name,
							show_index
						FROM soure_dim_project_input_type t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								project_input_type_name = t.project_input_type_name,
								show_index = t.show_index
						;
				END LB_INSERT;

			IF p_error = 1 THEN  
					ROLLBACK;  
 					SET p_message = concat("【项目投入费用类型】：数据刷新失败。操作用户：", optUserId);
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_project_input_type', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'error', 213);
			ELSE  
					COMMIT;  
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_project_input_type', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'sucess' , 213);
			END IF;  
			set @error_message = p_error;


END;
-- DELIMITER ;

-- TRUNCATE dim_project_input_type;
-- CALL pro_fetch_dim_project_input_type('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');





#=======================================================pro_fetch_dim_project_type=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_project_type`;
CREATE PROCEDURE pro_fetch_dim_project_type(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN


	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	
		DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【项目类型】：数据刷新完成');
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

-- ======================================replace模板======================================
				LB_INSERT:BEGIN
					
						INSERT INTO dim_project_type (
							project_type_id,
							customer_id,
							project_type_name,
							show_index
						)
						SELECT 
							id,
							customer_id,
							project_type_name,
							show_index
						FROM soure_dim_project_type t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								project_type_name = t.project_type_name,
								show_index = t.show_index
						;
				END LB_INSERT;

			IF p_error = 1 THEN  
					ROLLBACK;  
 					SET p_message = concat("【项目类型】：数据刷新失败。操作用户：", optUserId);
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_project_type', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'error', 212);
			ELSE  
					COMMIT;  
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_project_type', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'sucess' , 212);
			END IF;  
			set @error_message = p_error;



END;
-- DELIMITER ;


-- CALL pro_fetch_dim_project_type('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');




#=======================================================pro_fetch_dim_structure=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_structure`;
CREATE PROCEDURE pro_fetch_dim_structure(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN


	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【工资结构】：数据刷新完成');
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

				LB_INSERT:BEGIN
						INSERT INTO dim_structure (
							structure_id, customer_id, structure_name, is_fixed
						)              
						SELECT 
							id, customer_id, structure_name, is_fixed
						FROM soure_dim_structure t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								structure_name = t.structure_name,
								is_fixed = t.is_fixed
						;
				END LB_INSERT;

			IF p_error = 1 THEN  
					ROLLBACK;  
 					SET p_message = concat("【工资结构】：数据刷新失败。操作用户：", optUserId);
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_structure', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'error', 211);
			ELSE  
					COMMIT;  
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_structure', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'sucess' , 211);
			END IF;  
			set @error_message = p_error;

END;
-- DELIMITER ;

-- CALL pro_fetch_dim_structure('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');



#=======================================================pro_update_pay_collect_year_scShare=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_update_pay_collect_year_scShare`;
CREATE PROCEDURE pro_update_pay_collect_year_scShare(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_y INTEGER(4))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE limitStart INT;
	
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;
							
			LB_CURSOR:BEGIN
					
					DECLARE sShare, cShare	INT;

					DECLARE pcId, orgId VARCHAR(32);
					DECLARE fp TEXT;
					DECLARE done INT DEFAULT 0;
					DECLARE s_cur CURSOR FOR
						SELECT 
							t2.pay_collect_year_id pcId, t2.organization_id orgId, t3.full_path fp
						FROM pay_collect_year t2 
						INNER JOIN dim_organization t3 on t2.organization_id = t3.organization_id
						WHERE t2.customer_id = customerId AND t2.`year` = p_y
						GROUP BY t2.organization_id
						;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;

					OPEN s_cur;
					FETCH s_cur INTO pcId, orgId, fp;
						WHILE1: WHILE done != 1 DO
							SELECT IFNULL(SUM(sum_share),0) sShare, IFNULL(sum(count_share),0) cShare 
							INTO	sShare, cShare
							FROM pay_collect_year t 
							INNER JOIN dim_organization t3 on t.organization_id = t3.organization_id
							WHERE t.customer_id = customerId
								AND LOCATE(fp, t3.full_path)
								AND `year` = p_y 
								-- AND locate(p_y, t.`year_month`) 
								;

							UPDATE pay_collect_year SET sum_share = sShare, count_share = cShare
							WHERE organization_id = orgId AND `year` = p_y AND customer_id = customerId;


							FETCH s_cur INTO pcId, orgId, fp;
						END WHILE WHILE1;
					CLOSE s_cur;
				END LB_CURSOR;


END;
-- DELIMITER ;


#=======================================================pro_fetch_pay_value=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_pay_value`;
CREATE PROCEDURE pro_fetch_pay_value(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_y INTEGER(4))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;
			
	-- 薪酬预算表
			LB_CURSOR:BEGIN
					
					DECLARE orgId VARCHAR(32);
					DECLARE fp TEXT;

					DECLARE done INT DEFAULT 0;
					DECLARE s_cur CURSOR FOR
						SELECT t2.organization_id orgId, t2.full_path fp
						FROM dim_organization t2 
						WHERE t2.customer_id = customerId
						;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;

					DELETE FROM pay_value WHERE `year` = p_y;

					OPEN s_cur;
					FETCH s_cur INTO orgId, fp;
						WHILE1: WHILE done != 1 DO
							-- 汇总父级，祖父级
							INSERT INTO pay_value (
								pay_value_id,
								customer_id,
								organization_id,
								pay_value,
								`year`
							)
							SELECT 
								REPLACE(UUID(),'-',''),
								t.customer_id,
								orgId,
								sum(pay_value),
								`year`
							FROM soure_pay_value t
							INNER JOIN dim_organization t1 on t.organization_id = t1.organization_id
							WHERE t.customer_id = customerId 
							AND t.`year` = p_y
							AND LOCATE(fp, t1.full_path)
							;

							FETCH s_cur INTO orgId, fp;
						END WHILE WHILE1;
					CLOSE s_cur;
				END LB_CURSOR;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;

END;
-- DELIMITER ;
TRUNCATE TABLE pay_value;
CALL pro_fetch_pay_value('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', 2011);
CALL pro_fetch_pay_value('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', 2012);
CALL pro_fetch_pay_value('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', 2013);
CALL pro_fetch_pay_value('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', 2014);
CALL pro_fetch_pay_value('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', 2015);
	


#=======================================================pro_update_pay_crValue=======================================================
	-- 应响的表：pay,pay_collect,pay_collect_year
	-- 'b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '解密key', 发工资年月
	-- DELIMITER //
	DROP PROCEDURE IF EXISTS `pro_update_pay_crValue`;
	CREATE PROCEDURE pro_update_pay_crValue(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key2 VARCHAR(50), in p_ym INTEGER(6))
	BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;

		
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				
		-- 更新cr
			LB_CURSOR_CR:BEGIN

						DECLARE orgId VARCHAR(32);
						DECLARE qv DOUBLE(10,4);
						
						DECLARE payId, empId VARCHAR(32);
						DECLARE cr DOUBLE(10,4);

						DECLARE done INT DEFAULT 0;
						DECLARE s_cur1 CURSOR FOR
							SELECT 
								organization_id orgId,
								quantile_value qv
							FROM pay_collect WHERE customer_id = customerId	AND `year_month` = p_ym;

						DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
									
						OPEN s_cur1;
						FETCH s_cur1 INTO orgId, qv;

							WHILE1: WHILE done != 1 DO
							
								INSERT INTO pay
								(
									pay_id,
									customer_id,
									emp_id,
									usre_name_ch,
									organization_id,
									full_path,
									postion_id,
									pay_contract,
									pay_should,
									pay_actual,
									salary_actual,
									welfare_actual,
									bonus,
									cr_value,
									`year_month`

								)
								SELECT  
									pay_id,customer_id, emp_id, usre_name_ch, organization_id, full_path, postion_id, pay_contract, pay_should, pay_actual, salary_actual, welfare_actual, bonus, cr, `year_month`
								FROM (
										SELECT 
											pay_id,
											customer_id,
											emp_id,
											usre_name_ch,
											organization_id,
											full_path,
											postion_id,
											pay_contract,
											pay_should,
											pay_actual,
											salary_actual,
											welfare_actual,
											bonus,
											AES_DECRYPT(pay_should, p_key2) / qv AS cr,
											`year_month`
										FROM pay 
										WHERE `year_month` = p_ym AND organization_id = orgId
								) t
							ON DUPLICATE KEY UPDATE
									cr_value = t.cr;
							
								FETCH s_cur1 INTO orgId, qv;
							END WHILE WHILE1;
						CLOSE s_cur1;

		END LB_CURSOR_CR;

		IF p_error = 1 THEN  
				ROLLBACK;  
		ELSE  
				COMMIT; 

				
		END IF;

	END;
	-- DELIMITER ;

		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201401);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201402);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201403);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201404);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201405);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201406);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201407);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201408);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201409);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201410);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201411);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201412);	

		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201501);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201502);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201503);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201504);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201505);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201506);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201507);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201508);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201509);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201510);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201511);	
		CALL pro_update_pay_crValue('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201512);	


		
	


#=======================================================pro_fetch_profession_quantile_relation=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_profession_quantile_relation`;
CREATE PROCEDURE pro_fetch_profession_quantile_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;


-- ======================================replace模板======================================
				LB_INSERT:BEGIN
					
						INSERT INTO profession_quantile_relation (
							profession_quantile_id,
							customer_id,
							profession_id,
							quantile_id,
							quantile_value,
							type,
							`year`
						)
						SELECT 
							id,
							customer_id,
							profession_id,
							quantile_id,
							quantile_value,
							type,
							`year`
						FROM soure_profession_quantile_relation t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								profession_id = t.profession_id,
								quantile_id = t.quantile_id,
								quantile_value = t.quantile_value,
								type = t.type,
								`year` = t.`year`
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			-- 去掉已删除数据
			DELETE FROM profession_quantile_relation WHERE profession_quantile_id NOT IN 
			( SELECT t2.id FROM soure_profession_quantile_relation t2 WHERE t2.customer_id = customerId); 
	END IF;


END;
-- DELIMITER ;

-- TRUNCATE TABLE profession_quantile_relation;

CALL pro_fetch_profession_quantile_relation('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');

#=======================================================pro_fetch_share_holding=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_share_holding`;
CREATE PROCEDURE pro_fetch_share_holding(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

	-- 删除六年前数据
			DELETE FROM soure_share_holding 
			WHERE customer_id = customerId
			AND	id in (
					SELECT id FROM (
-- 						SELECT id FROM soure_share_holding t where `year_month` = DATE_FORMAT(DATE_SUB(now(), INTERVAL 6 YEAR), '%Y%m')
						SELECT id FROM soure_share_holding t where `year_month` = DATE_SUB(now(), INTERVAL 6 YEAR)
					) t
			);
		

-- ======================================replace模板======================================
				LB_INSERT:BEGIN
					
						INSERT INTO share_holding (
							share_holding_id,
							customer_id,
							emp_id,
							usre_name_ch,
							organization_id,
							full_path,
							now_share,
							confer_share,
							price,
							hold_period,
							sub_num,
							sub_date
						)
						SELECT 
							id,
							customer_id,
							emp_id,
							usre_name_ch,
							organization_id,
							full_path,
							now_share,
							confer_share,
							price,
							hold_period,
							sub_num,
							sub_date
						FROM soure_share_holding t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								emp_id = t.emp_id,
								usre_name_ch = t.usre_name_ch,
								organization_id = t.organization_id,
								`full_path` = t.`full_path`,
								`now_share` = t.`now_share`,
								`confer_share` = t.`confer_share`,
								`price` = t.`price`,
								`hold_period` = t.`hold_period`,
								`sub_num` = t.`sub_date`,
								`sub_date` = t.`sub_date`
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			-- 去掉已删除数据
			DELETE FROM share_holding WHERE share_holding_id NOT IN 
			( SELECT t2.id FROM soure_share_holding t2 WHERE t2.customer_id = customerId); 
	END IF;


END;
-- DELIMITER ;

-- TRUNCATE TABLE share_holding;

CALL pro_fetch_share_holding('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');


#=======================================================pro_fetch_welfare_uncpm=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_welfare_uncpm`;
CREATE PROCEDURE pro_fetch_welfare_uncpm(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

	-- 删除二年前数据
			DELETE FROM soure_welfare_uncpm 
			WHERE customer_id = customerId
			AND	id in (
					SELECT id FROM (
						SELECT id FROM soure_welfare_uncpm t where `year_month` = DATE_FORMAT(DATE_SUB(now(), INTERVAL 2 YEAR), '%Y%m')
					) t
			);
		

-- ======================================replace模板======================================
				LB_INSERT:BEGIN
					
						INSERT INTO welfare_uncpm (
							welfare_uncpm_id,
							customer_id,
							emp_id,
							uncpm_id,
							note,
							date,
							`year_month`
						)
						SELECT 
							id,
							customer_id,
							emp_id,
							uncpm_id,
							note,
							date,
							`year_month`
						FROM soure_welfare_uncpm t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								emp_id = t.emp_id,
								uncpm_id = t.uncpm_id,
								note = t.note,
								`date` = t.`date`,
								`year_month` = t.`year_month`
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			-- 去掉已删除数据
			DELETE FROM welfare_uncpm WHERE welfare_uncpm_id NOT IN 
			( SELECT t2.id FROM soure_welfare_uncpm t2 WHERE t2.customer_id = customerId); 
	END IF;


END;
-- DELIMITER ;

-- TRUNCATE TABLE welfare_uncpm;

CALL pro_fetch_welfare_uncpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');



#=======================================================pro_fetch_welfare_cpm=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_welfare_cpm`;
CREATE PROCEDURE pro_fetch_welfare_cpm(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), IN p_ym INT(6))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

				LB_INSERT:BEGIN
					DELETE FROM welfare_cpm WHERE `year_month` = p_ym;
						INSERT INTO welfare_cpm (
							welfare_cpm_id,
							customer_id,
							emp_id,
							cpm_id,
							welfare_value,
							`date`,
							`year_month`
						)
						SELECT 
							id,
							customer_id,
							emp_id,
							cpm_id,
							welfare_value,
							`date`,
							`year_month`
						FROM soure_welfare_cpm t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								emp_id = t.emp_id,
								cpm_id = t.cpm_id,
								welfare_value = t.welfare_value,
								`date` = t.`date`,
								`year_month` = t.`year_month`
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


END;
-- DELIMITER ;

-- TRUNCATE TABLE welfare_cpm;

	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201401);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201402);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201403);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201404);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201405);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201406);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201407);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201408);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201409);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201410);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201411);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201412);	

	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201501);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201502);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201503);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201504);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201505);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201506);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201507);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201508);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201509);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201510);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201511);	
	CALL pro_fetch_welfare_cpm('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201512);





#=======================================================pro_fetch_welfare_nfb=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_welfare_nfb`;
CREATE PROCEDURE pro_fetch_welfare_nfb(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), IN p_ym INT(6))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

			DELETE FROM welfare_nfb WHERE `year_month` = p_ym;
				LB_INSERT:BEGIN
					
						INSERT INTO welfare_nfb (
							welfare_id,
							customer_id,
							emp_id,
							nfb_id,
							welfare_value,
							`date`,
							`year_month`
						)
						SELECT 
							id,
							customerId,
							emp_id,
							nfb_id,
							welfare_value,
							`date`,
							`year_month`
						FROM soure_welfare_nfb t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								emp_id = t.emp_id,
								nfb_id = t.nfb_id,
								welfare_value = t.welfare_value,
								`date` = t.`date`,
								`year_month` = t.`year_month`
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


END;
-- DELIMITER ;

	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201401);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201402);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201403);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201404);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201405);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201406);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201407);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201408);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201409);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201410);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201411);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201412);	

	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201501);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201502);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201503);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201504);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201505);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201506);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201507);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201508);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201509);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201510);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201511);	
	CALL pro_fetch_welfare_nfb('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201512);	





#=======================================================pro_fetch_salary=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_salary`;
CREATE PROCEDURE pro_fetch_salary(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), IN p_ym INT(6))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
		
				DELETE FROM salary WHERE `year_month` = p_ym;
				LB_INSERT:BEGIN
					
						INSERT INTO salary (
							salary_id, customer_id, emp_id, structure_id, salary_value, `year_month`
						)
						SELECT 
							id, customer_id, emp_id, structure_id, salary_value, `year_month`
						FROM soure_salary t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								emp_id = t.emp_id,
								structure_id=t.structure_id,
								salary_value=t.salary_value,
								`year_month`=t.`year_month`
						;
				END LB_INSERT;



	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
	END IF;


	
END;
-- DELIMITER ;

-- TRUNCATE TABLE salary;


	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201401);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201402);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201403);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201404);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201405);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201406);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201407);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201408);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201409);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201410);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201411);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201412);	

	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201501);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201502);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201503);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201504);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201505);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201506);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201507);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201508);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201509);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201510);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201511);	
	CALL pro_fetch_salary('b5c9410dc7e4422c8e0189c7f8056b5f','DBA',  201512);	







#=======================================================pro_fetch_pay=======================================================
-- 应响的表：pay,pay_collect,pay_collect_year
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '解密key', 发工资年月
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_pay`;
CREATE PROCEDURE pro_fetch_pay(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key2 VARCHAR(50), in p_ym INTEGER(6))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	-- 硬编码
	DECLARE curDate DATE DEFAULT '2015-12-18'; --  CURDATE();
	
	DECLARE 5yearhAGo INT DEFAULT p_ym - 500;
	DECLARE limitStart INT;
	DECLARE y INT DEFAULT SUBSTR(p_ym FROM 1 FOR 4);
	DECLARE m INT DEFAULT SUBSTR(p_ym FROM 5 FOR 6);
	
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;
			
			-- 	删除五年前今天以往的数据
-- 			DELETE FROM soure_pay
-- 			WHERE customer_id = customerId AND `year_month` < 5yearhAGo;

			DELETE FROM pay WHERE `year_month` = p_ym;
			INSERT INTO pay (
				pay_id,
				customer_id,
				emp_id,
				usre_name_ch,
				organization_id,
				full_path,
				postion_id,
				pay_contract,
				pay_should,
				pay_actual,
				salary_actual,
				welfare_actual,
				bonus,
				cr_value,
				`year_month`
			)
			SELECT 
				id,
				customer_id,
				emp_id,
				usre_name_ch,
				organization_id,
				full_path,
				postion_id,
				AES_ENCRYPT(pay_contract, p_key2),
				AES_ENCRYPT(pay_should, p_key2),
				AES_ENCRYPT(pay_actual, p_key2),
				AES_ENCRYPT(salary_actual, p_key2),
				AES_ENCRYPT(welfare_actual, p_key2),
				AES_ENCRYPT(bonus, p_key2),
				cr_value,
				`year_month`
			FROM soure_pay t
			WHERE t.customer_id = customerId 
			AND t.`year_month` = p_ym
			;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 

		-- 薪酬汇总表
		LB_PAY_COLLECT :BEGIN

				CALL pro_fetch_pay_collect(customerId,'DBA', p_key2, p_ym);	

				-- 薪酬年汇总
				IF (m=12) THEN
					CALL pro_fetch_pay_collect_year(customerId,'DBA', y);
				END IF;

		END LB_PAY_COLLECT;

	END IF;

END;
-- DELIMITER ;
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201401);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201402);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201403);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201404);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201405);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201406);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201407);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201408);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201409);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201410);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201411);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201412);	

	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201501);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201502);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201503);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201504);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201505);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201506);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201507);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201508);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201509);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201510);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201511);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201512);	


	

#=======================================================pro_fetch_train_value=======================================================
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 上报的预算日期
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_train_value`;
CREATE PROCEDURE pro_fetch_train_value(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), IN p_datetime DATETIME)
BEGIN

		DECLARE pCurDate TIMESTAMP DEFAULT p_datetime;
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

			-- 	删除6年前今天以往的数据
-- 			DELETE FROM soure_train_outlay 
-- 			WHERE customer_id = customerId
-- 			AND	id in (
-- 					SELECT id FROM (
-- 						SELECT id FROM soure_train_value t where `year` < DATE_SUB(now(), INTERVAL 6 YEAR)
-- 					) t
-- 			);

-- ======================================游标模板======================================
				LB_CURSOR:BEGIN
					
					DECLARE orgId VARCHAR(32);
					DECLARE fp VARCHAR(1000);

					DECLARE done INT DEFAULT 0;
					DECLARE s_cur CURSOR FOR
						SELECT 
							organization_id orgId,
							full_path fp
						FROM dim_organization WHERE customer_id = customerId;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
								

					OPEN s_cur;
					FETCH s_cur INTO orgId, fp;

						WHILE1: WHILE done != 1 DO

						 SET @bv = (SELECT IFNULL(sum(t.budget_value),0) ol
												FROM dim_organization t1
												LEFT JOIN soure_train_value t ON t1.organization_id = t.organization_id 
												WHERE LOCATE(fp, t1.full_path)
												AND t.organization_id is not NULL
												AND t.`year` =  YEAR(pCurDate)
												ORDER BY t.`year`
												);

						IF @bv != 0 THEN
							INSERT INTO train_value VALUES (REPLACE(UUID(),'-',''), customerId, orgId, @bv, YEAR(pCurDate));
						END IF;

							FETCH s_cur INTO orgId, fp;
						END WHILE WHILE1;
				
					CLOSE s_cur;
				END LB_CURSOR;


	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
	COMMIT;  
		-- 外部机构
		REPLACE INTO train_value(
				train_value_id,
				customer_id,
				organization_id,
				budget_value,
				`year`
			)
			SELECT 
				id,
				customer_id,
				organization_id,
				sum(budget_value),
				`year`
			FROM soure_train_value
			where organization_id is NULL AND `year` =YEAR(pCurDate) ;
		
		
	END IF;
END;
-- DELIMITER ;
 TRUNCATE TABLE train_value;
	CALL pro_fetch_train_value('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2011-06-21 00:00:00');
	CALL pro_fetch_train_value('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2012-06-21 00:00:00');
	CALL pro_fetch_train_value('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2013-06-21 00:00:00');
	CALL pro_fetch_train_value('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-06-21 00:00:00');
	CALL pro_fetch_train_value('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-06-21 00:00:00');





#=======================================================pro_fetch_train_outlay=======================================================
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 上报的实际花费日期
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_train_outlay`;
CREATE PROCEDURE pro_fetch_train_outlay(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), IN p_datetime DATETIME)
BEGIN


		DECLARE pCurDate TIMESTAMP DEFAULT p_datetime;
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

			-- 	删除五年前今天以往的数据
			DELETE FROM soure_train_outlay 
			WHERE customer_id = customerId
			AND	id in (
					SELECT id FROM (
						SELECT id FROM soure_train_outlay t where `date` < DATE_SUB(now(), INTERVAL 10 YEAR)
					) t
			);

-- ======================================游标模板======================================
				LB_CURSOR:BEGIN
					
					DECLARE orgId VARCHAR(32);
					DECLARE fp VARCHAR(1000);

					DECLARE done INT DEFAULT 0;
					DECLARE s_cur CURSOR FOR
						SELECT 
							organization_id orgId,
							full_path fp
						FROM dim_organization WHERE customer_id = customerId;
-- 					DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
								

					OPEN s_cur;
					FETCH s_cur INTO orgId, fp;

						WHILE1: WHILE done != 1 DO

						 SET @ol = (SELECT IFNULL(sum(t.outlay),0) ol
												FROM dim_organization t1
												LEFT JOIN soure_train_outlay t ON t1.organization_id = t.organization_id 
												WHERE LOCATE(fp, t1.full_path)
												AND t.organization_id is not NULL
												AND t.date = pCurDate
												ORDER BY t.date
												);

						IF @ol != 0 THEN
							INSERT INTO train_outlay VALUES (REPLACE(UUID(),'-',''), customerId, orgId, @ol, pCurDate, null, YEAR(pCurDate));
						END IF;

							FETCH s_cur INTO orgId, fp;
						END WHILE WHILE1;
				
					CLOSE s_cur;
				END LB_CURSOR;



	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
-- 			-- 去掉已删除数据
-- 			DELETE FROM train_outlay WHERE train_outlay_id NOT IN 
-- 			( SELECT t2.id FROM soure_train_outlay t2 WHERE t2.customer_id = customerId);

	-- 外部机构
			REPLACE INTO train_outlay (
				train_outlay_id,
				customer_id,
				organization_id,
				outlay,
				date,
				note,
				`year`
			)
			SELECT id,
				customer_id,
				organization_id,
				outlay,
				date,
				note,
				`year`
			FROM soure_train_outlay t2
			WHERE organization_id IS NULL AND `year` = YEAR(pCurDate)
			;
		
	END IF;
END;
-- DELIMITER ;
-- TRUNCATE TABLE train_outlay;
-- 	CALL pro_fetch_train_outlay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2011-06-21 00:00:00');
-- 	CALL pro_fetch_train_outlay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2011-06-21 00:00:00');


#=======================================================Main=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `Main`;
CREATE PROCEDURE Main()
BEGIN
	

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

-- ======================================游标模板======================================
				LB_CURSOR:BEGIN
-- 					
					DECLARE d TIMESTAMP;

					DECLARE done INT DEFAULT 0;
					DECLARE cur CURSOR FOR
						SELECT 
								DISTINCT t.date d
						FROM soure_train_outlay t  WHERE organization_id is NOT null -- AND customer_id = customerId
						ORDER BY date
						;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
								
					OPEN cur;
					FETCH cur INTO d;

						WHILE1: WHILE done != 1 DO
								CALL pro_fetch_train_outlay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', d);

							FETCH cur INTO d;
						END WHILE WHILE1;
				
					CLOSE cur;
				END LB_CURSOR;
-- 
-- 
	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  

			COMMIT;  
		

	END IF;
END;
-- DELIMITER ;
TRUNCATE TABLE train_outlay;
	CALL main();






#=======================================================pro_fetch_city=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_city`;
CREATE PROCEDURE pro_fetch_city(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

-- ======================================replace模板======================================
				LB_REPLACE:BEGIN
					
						REPLACE INTO dim_city (
							city_id, customer_id, city_key, city_name, province_id, show_index
						)
						SELECT 
							c_id,
							customer_id,
							city_key,
							city_name,
							province_id,
							show_index
						FROM soure_dim_city 
						WHERE customer_id = customerId;
								
				END LB_REPLACE;


	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
	END IF;


	
END;
-- DELIMITER ;

CALL pro_fetch_city('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'city');




#=======================================================pro_fetch_pay_collect=======================================================
-- 应响的表：pay,pay_collect,pay_collect_year
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '解密key', 发工资年月
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_pay_collect`;
CREATE PROCEDURE pro_fetch_pay_collect(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key2 VARCHAR(50), in p_ym INTEGER(6))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	-- 硬编码
	DECLARE curDate DATE DEFAULT '2015-12-17'; --  CURDATE();
	
	DECLARE 5yearhAGo INT DEFAULT p_ym - 500;
	DECLARE limitStart INT;
	DECLARE y INT DEFAULT SUBSTR(p_ym FROM 1 FOR 4);
	DECLARE m INT DEFAULT SUBSTR(p_ym FROM 5 FOR 6);
	DECLARE sp, ss, sw, sb double(10, 4);


	
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;
			
	-- 薪酬汇总表
		LB_CURSOR_SUM:BEGIN

					DECLARE orgId VARCHAR(32);
					DECLARE fp VARCHAR(1000);

					DECLARE done INT DEFAULT 0;
					DECLARE s_cur1 CURSOR FOR
						SELECT 
							organization_id orgId,
							full_path fp
						FROM dim_organization WHERE customer_id = customerId;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
								
					DELETE FROM pay_collect WHERE `year_month` = p_ym;

					OPEN s_cur1;
					FETCH s_cur1 INTO orgId, fp;

						WHILE1: WHILE done != 1 DO

								SELECT IFNULL(sum(sumPay),0) sp, 
											 IFNULL(sum(sumSalary),0) ss, IFNULL(sum(sumWelfare),0) sw,  IFNULL(sum(sumBonus),0) sb
								INTO sp,  ss, sw, sb
								-- SELECT sumPay, avgPay, sumSalary, sumWelfare, t2.full_path
								FROM dim_organization t2 
								LEFT JOIN 
									(
										-- 换为万完单位
										SELECT 
												t.organization_id,
												SUM(AES_DECRYPT(t.pay_should, p_key2)) / 10000 AS sumPay,				 -- 应发薪酬
												SUM(AES_DECRYPT(t.salary_actual, p_key2)) / 10000 AS sumSalary,  -- 应发工资
												SUM(AES_DECRYPT(t.welfare_actual, p_key2)) / 10000 AS sumWelfare,
												SUM(AES_DECRYPT(t.bonus, p_key2)) / 10000 AS sumBonus,
												t1.full_path
											FROM pay t
											INNER JOIN dim_organization t1 on t.organization_id = t1.organization_id
											WHERE  t.`year_month` = p_ym
											GROUP BY organization_id
								) t on t.organization_id = t2.organization_id
								WHERE LOCATE(fp, t2.full_path)
								;

								SET @mas = (SELECT month_avg_sum FROM history_emp_count_month t3 
														WHERE `year_month` = p_ym AND t3.organization_id = orgId AND t3.customer_id = customerId);
								

								INSERT INTO pay_collect (
									pay_collect_id,customer_id,organization_id,
									sum_pay, avg_pay, avg_emp_num,
									sum_salary, avg_salary,
									sum_welfare, avg_welfare,
									sum_bonus, quantile_value, `year_month`
		
								) VALUES(
										REPLACE(uuid(),'-',''), customerId, orgId, 
										sp, (sp / @mas), @mas, 
										ss, (ss / @mas),
										sw, (sw / @mas),
										sb, 0, p_ym);

							FETCH s_cur1 INTO orgId, fp;
						END WHILE WHILE1;
					CLOSE s_cur1;

	END LB_CURSOR_SUM;

				-- 50分位值计算：
				LB_CURSOR_50:BEGIN
					
					DECLARE orgId VARCHAR(50);
					DECLARE fp TEXT;


					DECLARE done INT DEFAULT 0;
					DECLARE s_cur CURSOR FOR
-- 						SELECT t2.organization_id orgId
-- 						FROM soure_pay t2 
-- 						WHERE t2.customer_id = customerId
-- 						AND t2.`year_month` = p_ym
-- 						GROUP BY t2.organization_id
-- 						;
							SELECT 
								organization_id orgId, full_path fp
							FROM dim_organization WHERE customer_id = customerId;


					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;

					OPEN s_cur;
					FETCH s_cur INTO orgId, fp;
						WHILE1: WHILE done != 1 DO

								-- 机构下总人数：
								SET @orgCountEmp = (SELECT emp_count_sum 
																		FROM history_emp_count t 
																		WHERE t.`day` = curDate AND type = 1 -- 当天全职的人数
																		AND t.organization_id = orgId);

								-- 奇数
								IF( @orgCountEmp % 2 = 1) THEN
									SET limitStart = (@orgCountEmp / 2) -0.5;

									UPDATE pay_collect SET quantile_value = (

										SELECT
												AES_DECRYPT(t2.pay_should, p_key2) / 10000 -- 应发薪酬
										FROM pay t2 
										WHERE t2.customer_id = customerId
										AND t2.`year_month` = p_ym
-- 										AND t2.organization_id in (
-- 												SELECT t1.organization_id FROM dim_organization t1 
-- 												WHERE locate(( SELECT t.full_path FROM dim_organization t WHERE t.organization_id = orgId AND t.customer_id = customerId ),
-- 																			t1.full_path )
-- 												)
										AND locate(fp, t2.full_path)
										ORDER BY t2.pay_should DESC
										LIMIT limitStart, 1

									)
									WHERE organization_id = orgId AND `year_month` = p_ym AND customer_id = customerId
									;
								-- 偶数
								ELSE
									SET limitStart = (@orgCountEmp / 2);
				
									UPDATE pay_collect SET quantile_value = (
										SELECT AVG(ps)  / 10000
										FROM (
													SELECT AES_DECRYPT(t2.pay_should, p_key2) ps
													FROM pay t2 
													WHERE t2.customer_id = customerId
													AND t2.`year_month` = p_ym
-- 													AND t2.organization_id in (
-- 														SELECT t1.organization_id FROM dim_organization t1 
-- 														WHERE locate(( SELECT t.full_path FROM dim_organization t WHERE t.organization_id = orgId AND t.customer_id = customerId ),
-- 																					t1.full_path )
-- 														)
													AND locate(fp, t2.full_path)
													ORDER BY t2.pay_should DESC
													LIMIT limitStart, 2
												) t3
									)
									WHERE organization_id = orgId AND `year_month` = p_ym AND customer_id = customerId
									;
								END IF;

							FETCH s_cur INTO orgId, fp;
						END WHILE WHILE1;
					CLOSE s_cur;
				END LB_CURSOR_50;
-- 			CALL pro_update_pay_quantile_value(customerId, optUserId, p_key2, p_ym);

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 

			CALL pro_update_pay_crValue(customerId, optUserId, p_key2, p_ym);	
	END IF;

END;
-- DELIMITER ;
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201401);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201402);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201403);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201404);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201405);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201406);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201407);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201408);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201409);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201410);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201411);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201412);	

	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201501);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201502);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201503);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201504);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201505);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201506);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201507);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201508);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201509);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201510);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201511);	
	CALL pro_fetch_pay_collect('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201512);	







#=======================================================pro_fetch_pay_collect_year=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_pay_collect_year`;
CREATE PROCEDURE pro_fetch_pay_collect_year(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_y INTEGER(4))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE limitStart INT;
	
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;
							
			DELETE FROM pay_collect_year WHERE `year` = p_y;
			INSERT INTO pay_collect_year (
				pay_collect_year_id,
				customer_id,
				organization_id,
				sum_pay,
				avg_pay,
				sum_salary,
				sum_welfare,
				sum_bonus,
				sum_share,
				count_share,
				`year`
			)
			SELECT 
				replace(UUID(), '-', ''),
				customer_id,
				organization_id,
				sum(sum_pay),
				sum(sum_pay) / avg(emp_count_sum), 
				sum(sum_salary),
				sum(sum_welfare),
				sum(sum_bonus),
				0,0,
				p_y,
				startTime
			FROM pay_collect t
			WHERE t.customer_id = customerId 
				AND	locate(p_y, t.`year_month`)
			GROUP BY t.organization_id
			;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 

			LB_CURSOR:BEGIN
					
					DECLARE sShare, cShare	INT;

					DECLARE orgId VARCHAR(50);
					DECLARE done INT DEFAULT 0;
					DECLARE s_cur CURSOR FOR
						SELECT t2.organization_id orgId
						FROM pay_collect_year t2 
						WHERE t2.customer_id = customerId
						GROUP BY t2.organization_id
						;
-- 					DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
								
					OPEN s_cur;
					FETCH s_cur INTO orgId;
						WHILE1: WHILE done != 1 DO
							-- 最后一次当前数量之和
							-- SELECT SUM(now_share) FROM share_holding t 
							-- WHERE t.organization_id = orgId AND `refresh` = (SELECT MAX(`refresh`) FROM share_holding WHERE customer_id = customerId);

							SELECT IFNULL(SUM(now_share),0) sShare, IFNULL(count(emp_id),0) cShare 
							INTO	sShare, cShare
							FROM share_holding t 
							WHERE t.organization_id = orgId AND locate(p_y, t.`year_month`) AND t.customer_id = customerId;

							UPDATE pay_collect_year SET sum_share = sShare, count_share = cShare
							WHERE organization_id = orgId AND `year` = p_y AND customer_id = customerId;


							FETCH s_cur INTO orgId;
						END WHILE WHILE1;
					CLOSE s_cur;
				END LB_CURSOR;
	END IF;

END;
-- DELIMITER ;
	CALL pro_fetch_pay_collect_year('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2014);	
	CALL pro_fetch_pay_collect_year('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2015);	


#=======================================================pro_fetch_pay=======================================================
-- 应响的表：pay,pay_collect,pay_collect_year
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '解密key', 发工资年月
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_pay`;
CREATE PROCEDURE pro_fetch_pay(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key2 VARCHAR(50), in p_ym INTEGER(6))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	-- 硬编码
	DECLARE curDate DATE DEFAULT '2015-12-18'; --  CURDATE();
	
	DECLARE 5yearhAGo INT DEFAULT p_ym - 500;
	DECLARE limitStart INT;
	DECLARE y INT DEFAULT SUBSTR(p_ym FROM 1 FOR 4);
	DECLARE m INT DEFAULT SUBSTR(p_ym FROM 5 FOR 6);
	
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;

			DELETE FROM pay WHERE `year_month` = p_ym;
			INSERT INTO pay (
				pay_id, customer_id, emp_id, usre_name_ch, organization_id, full_path, postion_id, pay_contract, pay_should, pay_actual, salary_actual, welfare_actual, bonus, cr_value, `year_month`,  `year`
			)
			SELECT 
				id,
				customer_id,
				emp_id,
				usre_name_ch,
				organization_id,
				full_path,
				postion_id,
				AES_ENCRYPT(pay_contract, p_key2),
				AES_ENCRYPT(pay_should, p_key2),
				AES_ENCRYPT(pay_actual, p_key2),
				AES_ENCRYPT(salary_actual, p_key2),
				AES_ENCRYPT(welfare_actual, p_key2),
				AES_ENCRYPT(bonus, p_key2),
				cr_value,
				`year_month`,
				
				y
			FROM soure_pay t
			WHERE t.customer_id = customerId AND t.`year_month` = p_ym
			;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
		-- 薪酬汇总表
		LB_PAY_COLLECT :BEGIN
				CALL pro_fetch_pay_collect(customerId,'DBA', p_key2, p_ym);	
				-- 薪酬年汇总
				IF (m=12) THEN
					CALL pro_fetch_pay_collect_year(customerId,'DBA', y);
				END IF;
		END LB_PAY_COLLECT;
	END IF;

END;
-- DELIMITER ;
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201401);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201402);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201403);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201404);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201405);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201406);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201407);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201408);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201409);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201410);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201411);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201412);	

	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201501);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201502);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201503);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201504);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201505);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201506);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201507);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201508);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201509);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201510);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201511);	
	CALL pro_fetch_pay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'hrbi', 201512);	


	
	
	
#=======================================================pro_fetch_course_record=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_course_record`;
CREATE PROCEDURE pro_fetch_course_record(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;
			-- 	删除五年前今天以往的数据
			DELETE FROM soure_course_record
			WHERE customer_id = customerId
			AND	id in (
					SELECT id FROM (
						SELECT id FROM soure_course_record t where `end_date` < DATE_SUB(now(), INTERVAL 5 YEAR)
					) t
			);

			REPLACE INTO course_record (
				course_record_id,
				customer_id,
				course_id,
				start_date,
				end_date,
				`status`,
				train_unit,
				train_plan_id,
				`year`
			)
			SELECT 
				id,
				customer_id,
				course_id,
				start_date,
				end_date,
				`status`,
				train_unit,
				train_plan_id,
				`year`
			FROM soure_course_record t
			WHERE t.customer_id = customerId 
			;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
			-- 去掉已删除数据
			DELETE FROM course_record WHERE course_record_id NOT IN 
			( SELECT t2.id FROM soure_course_record t2 WHERE t2.customer_id = customerId);

	END IF;
END;
-- DELIMITER ;
	CALL pro_fetch_course_record('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');	
	
#=======================================================pro_fetch_train_plan=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_train_plan`;
CREATE PROCEDURE pro_fetch_train_plan(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;
			-- 	删除五年前今天以往的数据
			DELETE FROM soure_train_plan 
			WHERE customer_id = customerId
			AND	id in (
					SELECT id FROM (
						SELECT id FROM soure_train_plan t where `date` < DATE_SUB(now(), INTERVAL 5 YEAR)
					) t
			);

			REPLACE INTO train_plan (
				train_plan_id,
				customer_id,
				organization_id,
				train_name,
				train_num,
				date,
				`year`
			)
			SELECT 
				id,
				customer_id,
				organization_id,
				train_name,
				train_num,
				date,
				`year`
			FROM soure_train_plan t
			WHERE t.customer_id = customerId 
			;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
			-- 去掉已删除数据
			DELETE FROM train_plan WHERE train_plan_id NOT IN 
			( SELECT t2.id FROM soure_train_plan t2 WHERE t2.customer_id = customerId);

	END IF;
END;
-- DELIMITER ;
	CALL pro_fetch_train_plan('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');	

#=======================================================pro_fetch_lecturer_course_design=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_lecturer_course_design`;
CREATE PROCEDURE pro_fetch_lecturer_course_design(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;


			REPLACE INTO lecturer_course_design (
				lecturer_course_design_id,
				customer_id,
				lecturer_id,
				course_id
			)
			SELECT 
				id,
				customer_id,
				lecturer_id,
				course_id
			FROM soure_lecturer_course_design t
			WHERE t.customer_id = customerId 
			;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
			-- 去掉已删除数据
			DELETE FROM lecturer_course_design WHERE lecturer_course_design_id NOT IN 
			( SELECT t2.id FROM soure_lecturer_course_design t2 WHERE t2.customer_id = customerId);


	END IF;


END;
-- DELIMITER ;
	CALL pro_fetch_lecturer_course_design('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');	
	
#=======================================================pro_fetch_lecturer_course_speak=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_lecturer_course_speak`;
CREATE PROCEDURE pro_fetch_lecturer_course_speak(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;

			-- 	删除五年前今天以往的数据
			DELETE FROM soure_lecturer_course_speak 
			WHERE customer_id = customerId
			AND	id in (
					SELECT id FROM (
						SELECT id FROM soure_lecturer_course_speak t where `end_date` < DATE_SUB(now(), INTERVAL 5 YEAR)
					) t
			);

			REPLACE INTO lecturer_course_speak (
				lecturer_course_speak_id,
				customer_id,
				lecturer_id,
				course_id,
				start_date,
				end_date,
				`year`
			)
			SELECT 
				id,
				customer_id,
				lecturer_id,
				course_id,
				's',
				end_date,
				`year`
			FROM soure_lecturer_course_speak t
			WHERE t.customer_id = customerId 
			;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
			-- 去掉已删除数据
			DELETE FROM lecturer_course_speak WHERE lecturer_course_speak_id NOT IN 
			( SELECT t2.id FROM soure_lecturer_course_speak t2 WHERE t2.customer_id = customerId);


-- 		删除五年前今天以往的数据
-- 		DELETE FROM lecturer_course_speak 
-- 		WHERE customer_id = customerId
-- 		AND	lecturer_course_speak_id in (
-- 				SELECT lecturer_course_speak_id FROM (
-- 					SELECT lecturer_course_speak_id FROM lecturer_course_speak t where `end_date` < DATE_SUB(now(), INTERVAL 5 YEAR)
-- 				) t;
-- 

	END IF;


END;
-- DELIMITER ;
	CALL pro_fetch_lecturer_course_speak('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');	
	
#=======================================================pro_fetch_lecturer=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_lecturer`;
CREATE PROCEDURE pro_fetch_lecturer(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

			REPLACE INTO lecturer (
				lecturer_id,
				customer_id,
				emp_id,
				lecturer_name,
				level_id,
				type
			)
			SELECT 
				id,
				customer_id,
				emp_id,
				lecturer_name,
				level_id,
				type
			FROM soure_lecturer t
			WHERE t.customer_id = customerId 
			;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
			-- 去掉已删除数据
			DELETE FROM lecturer where lecturer_id not in ( SELECT t2.id from soure_lecturer t2 where t2.customer_id = customerId);

	END IF;


END;
-- DELIMITER ;
	CALL pro_fetch_lecturer('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');	

#=======================================================pro_fetch_dim_course=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_course`;
CREATE PROCEDURE pro_fetch_dim_course(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【课程维】');
DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_fetch_dim_course');
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

			REPLACE INTO dim_course (
				course_id,
				customer_id,
				course_key,
				course_name,
				course_type_id
			)
			SELECT 
				id,
				customer_id,
				course_key,
				course_name,
				course_type_id
			FROM soure_dim_course t
			WHERE t.customer_id = customerId 
			;

		INSERT INTO db_log 
		VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_dim_course', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		set @error_message = p_error;

END;
-- DELIMITER ;
-- 	CALL pro_fetch_dim_course( 'b5c9410dc7e4422c8e0189c7f8056b5f','DBA');	




#=======================================================pro_fetch_dim_course_type=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_course_type`;
CREATE PROCEDURE pro_fetch_dim_course_type(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【课程类别维】：数据刷新完成');
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

			REPLACE INTO dim_course_type (
				course_type_id,
				customer_id,
				course_type_key,
				course_type_name,
				show_index
			)
			SELECT 
				code_item_id,
				customerId,
				code_item_key,
				code_item_name,
				show_index
			FROM soure_code_item t
			WHERE t.customer_id = customerId and t.code_group_id = p_key_work;

	
			IF p_error = 1 THEN  
					ROLLBACK;  
 					SET p_message = concat("【课程类别维】：数据刷新失败。操作用户：", optUserId);
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_course_type', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'error', 208);
			ELSE  
					COMMIT;  
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_course_type', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'sucess' , 208);
			END IF;  
			set @error_message = p_error;

END;
-- DELIMITER ;
-- 	CALL pro_fetch_dim_course_type( 'b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'courseType');



#=======================================================pro_fetch_dim_job_title=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_job_title`;
CREATE PROCEDURE pro_fetch_dim_job_title(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32) )
BEGIN


	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【职衔维】';
	DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_fetch_dim_job_title');

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

		REPLACE INTO dim_job_title (
			job_title_id,
			customer_id,
			job_title_key,
			job_title_name,
			curt_name,
			show_index
		)
		SELECT 
			code_item_id,
			customerId,
			code_item_key,
			code_item_name,
			curt_name,
			show_index
		FROM soure_code_item t
		WHERE t.customer_id = customerId and t.code_group_id = p_key_work;
		
  
		INSERT INTO db_log 
		VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_dim_job_title', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		set @error_message = p_error;  

END;
-- DELIMITER ;

-- CALL pro_fetch_dim_job_title('b5c9410dc7e4422c8e0189c7f8056b5f','DBA','title');





#=======================================================pro_fetch_dim_ability=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_ability`;
CREATE PROCEDURE pro_fetch_dim_ability(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32) )
BEGIN


	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【能力层级维】');
DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_fetch_dim_ability');

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

		REPLACE INTO dim_ability (
			ability_id,
			customer_id,
			ability_key,
			ability_name,
			curt_name,
			type,
			show_index
		)
		SELECT 
			code_item_id,
			customerId,
			code_item_key,
			code_item_name,
			curt_name cName,
			type,
			show_index
		FROM soure_code_item t
		WHERE t.customer_id = customerId and t.code_group_id = p_key_work;

		INSERT INTO db_log 
		VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_dim_ability', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		set @error_message = p_error;  


END;
-- DELIMITER ;

-- CALL pro_fetch_dim_ability('b5c9410dc7e4422c8e0189c7f8056b5f','DBA','ability');




#=======================================================pro_fetch_dim_ability_lv=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_ability_lv`;
CREATE PROCEDURE pro_fetch_dim_ability_lv(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32) )
BEGIN

	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【能力层级子级维】：数据刷新完成');

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;


	REPLACE INTO dim_ability_lv (
		ability_lv_id,customer_id,ability_lv_key,ability_lv_name,curt_name,show_index
	)
	SELECT 
		code_item_id,
		customerId,
		code_item_key,
		code_item_name,
		curt_name cName,
		show_index
	FROM soure_code_item t
	WHERE t.customer_id = customerId and t.code_group_id = p_key_work;

			IF p_error = 1 THEN  
					ROLLBACK;  
 					SET p_message = concat("【能力层级子级维】：数据刷新失败。操作用户：", optUserId);
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_ability_lv', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'error', 205);
			ELSE  
					COMMIT;  
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_ability_lv', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'sucess' , 205);
			END IF;  
			
			set @error_message = p_error;

END;
-- DELIMITER ;

-- CALL pro_fetch_dim_ability_lv('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19','ability_lv'); 



#=======================================================pro_fetch_dim_key_talent_type=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_key_talent_type`;
CREATE PROCEDURE pro_fetch_dim_key_talent_type(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32) )
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【关键人才库维】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

		REPLACE into dim_key_talent_type (key_talent_type_id,customer_id,key_talent_type_key,key_talent_type_name, show_index)
		SELECT code_item_id, customerId,
			code_item_key ,
			code_item_name ,
			show_index
		FROM soure_code_item t
		WHERE t.customer_id = customerId and t.code_group_id = p_key_work;

			IF p_error = 1 THEN  
					ROLLBACK;  
 					SET p_message = concat("【关键人才库维】：数据刷新失败。操作用户：", optUserId);
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_key_talent_type', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'error', 206);
			ELSE  
					COMMIT;  
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_key_talent_type', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'sucess' ,206);
			END IF;  
			set @error_message = p_error;

END;
-- DELIMITER ;
-- CALL pro_fetch_dim_key_talent_type('b5c9410dc7e4422c8e0189c7f8056b5f','DBA','keyTalent');


#=======================================================pro_fetch_dim_performance=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_performance`;
CREATE PROCEDURE pro_fetch_dim_performance(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32) )
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【绩效维度】：数据刷新完成');
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;


			REPLACE INTO dim_performance (
				performance_id,customer_id,performance_key,performance_name, curt_name, show_index
			)
			SELECT code_item_id, 	customerId,
				code_item_key ,
				code_item_name ,
				curt_name,
				show_index
			FROM soure_code_item t
			WHERE t.customer_id = customerId and t.code_group_id = p_key_work;
			

			IF p_error = 1 THEN  
					ROLLBACK;  
 					SET p_message = concat("【绩效维度】：数据刷新失败。操作用户：", optUserId);
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_performance', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'error', 219);
			ELSE  
					COMMIT;  
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_performance', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'sucess' , 219);
			END IF;  
			set @error_message = p_error;

	
END;
-- DELIMITER ;

-- CALL pro_fetch_dim_performance('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'performance');




#=======================================================pro_fetch_dim_grade=======================================================
-- 'demo','jxzhang'
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_grade`;
CREATE PROCEDURE pro_fetch_dim_grade(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32) )
BEGIN

 	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【能力等级-维度表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

		REPLACE INTO dim_grade (
			grade_id,customer_id,grade_key,grade_name,show_index
		)
		SELECT code_item_id, 	customerId,
			code_item_key ,
			code_item_name ,
			show_index
		FROM soure_code_item t
		WHERE t.customer_id = customerId and t.code_group_id = p_key_work;
	
		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【能力等级-维度表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_grade', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_grade', p_message, startTime, now(), 'sucess' );
		END IF;

END;
-- DELIMITER ;

-- CALL pro_fetch_dim_grade('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19','ability');


#=======================================================pro_fetch_dim_emp_days=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_emp_days`;
CREATE PROCEDURE pro_fetch_dim_emp_days(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
-- 	DECLARE now TIMESTAMP DEFAULT p_now -- '2013-06-16'
	DECLARE now TIMESTAMP DEFAULT now()
	;
	

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

		DELETE FROM dim_emp where days = CURDATE() AND customer_id = customerId;

					INSERT INTO dim_emp (
						dim_emp_id,customer_id,emp_id,emp_key,user_name,user_name_ch,emp_hf_id,emp_hf_key,
						report_relation,email,img_path,passtime_or_fulltime,contract,blood,age,company_age,is_key_talent,
						sex,nation,degree_id,degree,native_place,country,birth_place,birth_date,national_id,national_type,marry_status,
						patty_name,position_id,position_name,organization_parent_id,organization_parent_name,organization_id,organization_name,
						sequence_id,sequence_name,sequence_sub_id,sequence_sub_name,performance_id,performance_name,ability_id,job_title_id,ability_lv_id,
						ability_name,job_title_name,ability_lv_name,rank_name,population_id,population_name,area_id,run_off_date,entry_date,tel_phone,qq,wx_code,
						address,contract_unit,work_place_id,work_place,regular_date,apply_type,channel_id,speciality,is_regular,refresh_date,c_id, days, year
					)
					SELECT 
						replace(UUID(),'-',''),customer_id,emp_id,emp_key,user_name,user_name_ch,emp_hf_id,emp_hf_key,
						report_relation,email,img_path,passtime_or_fulltime,contract,blood,age,company_age,is_key_talent,
						sex,nation,degree_id,degree,native_place,country,birth_place,birth_date,national_id,national_type,marry_status,
						patty_name,position_id,position_name,organization_parent_id,organization_parent_name,organization_id,organization_name,
						sequence_id,sequence_name,sequence_sub_id,sequence_sub_name,performance_id,performance_name,ability_id,job_title_id,ability_lv_id,
						ability_name,job_title_name,ability_lv_name,rank_name,population_id,population_name,area_id,run_off_date,entry_date,tel_phone,qq,wx_code,
						address,contract_unit,work_place_id,work_place,regular_date,apply_type,channel_id,speciality,is_regular,refresh_date,c_id, now, YEAR(now)
					FROM v_dim_emp t 
					WHERE t.customer_id = customerId
					;
-- SELECT replace(UUID(),'-',''),customer_id,emp_id,emp_key,user_name,user_name_ch,emp_hf_id,emp_hf_key,
-- 						report_relation,email,img_path,passtime_or_fulltime,contract,blood,age,company_age,is_key_talent,
-- 						sex,nation,degree_id,degree,native_place,country,birth_place,birth_date,national_id,national_type,marry_status,
-- 						patty_name,position_id,position_name,organization_parent_id,organization_parent_name,organization_id,organization_name,
-- 						sequence_id,sequence_name,sequence_sub_id,sequence_sub_name,performance_id,performance_name,ability_id,job_title_id,ability_lv_id,
-- 						ability_name,job_title_name,ability_lv_name,rank_name,population_id,population_name,area_id,run_off_date,entry_date,tel_phone,qq,wx_code,
-- 						address,contract_unit,work_place_id,work_place,regular_date,apply_type,channel_id,speciality,is_regular,refresh_date,c_id, now, YEAR(now)
-- FROM (
-- 					SELECT 
-- 						customer_id,emp_id,emp_key,user_name,user_name_ch,emp_hf_id,emp_hf_key,
-- 						report_relation,email,img_path,passtime_or_fulltime,contract,blood,age,company_age,is_key_talent,
-- 						sex,nation,degree_id,degree,native_place,country,birth_place,birth_date,national_id,national_type,marry_status,
-- 						patty_name,position_id,position_name,organization_parent_id,organization_parent_name,organization_id,organization_name,
-- 						sequence_id,sequence_name,sequence_sub_id,sequence_sub_name,performance_id,performance_name,ability_id,job_title_id,ability_lv_id,
-- 						ability_name,job_title_name,ability_lv_name,rank_name,population_id,population_name,area_id,run_off_date,entry_date,tel_phone,qq,wx_code,
-- 						address,contract_unit,work_place_id,work_place,regular_date,apply_type,channel_id,speciality,is_regular,refresh_date,c_id, now, YEAR(now)
-- 					FROM v_dim_emp t 
-- 					WHERE t.customer_id = customerId
-- 						AND
-- 						t.entry_date >= '2010-01-01 00:00:00' AND
-- 						t.run_off_date <= p_now 
-- 					UNION 
-- 						SELECT 
-- 						customer_id,emp_id,emp_key,user_name,user_name_ch,emp_hf_id,emp_hf_key,
-- 						report_relation,email,img_path,passtime_or_fulltime,contract,blood,age,company_age,is_key_talent,
-- 						sex,nation,degree_id,degree,native_place,country,birth_place,birth_date,national_id,national_type,marry_status,
-- 						patty_name,position_id,position_name,organization_parent_id,organization_parent_name,organization_id,organization_name,
-- 						sequence_id,sequence_name,sequence_sub_id,sequence_sub_name,performance_id,performance_name,ability_id,job_title_id,ability_lv_id,
-- 						ability_name,job_title_name,ability_lv_name,rank_name,population_id,population_name,area_id,run_off_date,entry_date,tel_phone,qq,wx_code,
-- 						address,contract_unit,work_place_id,work_place,regular_date,apply_type,channel_id,speciality,is_regular,refresh_date,c_id, now, YEAR(now)
-- 					FROM v_dim_emp t 
-- 					WHERE  t.run_off_date is null 
-- ) t;

				IF p_error = 1 THEN ROLLBACK;
				ELSE COMMIT;
				END IF;
	
END;
-- DELIMITER ;

CALL pro_fetch_dim_emp_days('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');



#=======================================================pro_fetch_dim_emp=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_emp`;
CREATE PROCEDURE pro_fetch_dim_emp(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE empKey VARCHAR(32);
	DECLARE userName VARCHAR(10);
	DECLARE userNameCh VARCHAR(5);
	DECLARE imgPath VARCHAR(200);
	DECLARE curEnabled int(1);
	DECLARE isKeyTalent int(1) DEFAULT 1;
	DECLARE passtimeOrFulltime VARCHAR(1);
	DECLARE curAge, companyAge int(3);
	DECLARE curSex  VARCHAR(1);
	DECLARE curNation, curDegree  VARCHAR(9);
	DECLARE nativePlace  VARCHAR(90);
	DECLARE curCountry  VARCHAR(20);
	DECLARE birthPlace  VARCHAR(90);
	DECLARE birthDate  datetime;
-- 	DECLARE nationalId, nationalType, abName, keyTalentName, perName  VARCHAR(60);
	DECLARE nationalId, nationalType, keyTalentName VARCHAR(60);
	DECLARE marryStatus  INT(1);
	DECLARE pattyName  VARCHAR(20);
-- 	DECLARE abId, keyTalentId, perId VARCHAR(32);
	DECLARE keyTalentId VARCHAR(32);
	DECLARE effectDate, expiryDate, roDate , entryDate, regularDate DATETIME;
	DECLARE telPhone VARCHAR(11);
	DECLARE curQQ VARCHAR(20);
	DECLARE wxCode VARCHAR(32);
	DECLARE curAddress VARCHAR(60);
	DECLARE contractUnit VARCHAR(32);
	DECLARE workPlace VARCHAR(60);
	DECLARE applyType, applyChannel VARCHAR(32);
	DECLARE curSpeciality VARCHAR(60);
	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP;

	DECLARE exist int;

	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
					SELECT 
						emp_key	empKey,
						user_name userName,
						user_name_ch userNameCh,
						img_path imgPath,
						passtime_or_fulltime passtimeOrFulltime,
						age curAge,
						company_age companyAge,
						sex curSex,
						nation curNation,
						degree curDegree,
						native_place nativePlace,
						country curCountry,
						birth_place birthPlace,
						birth_date birthDate,
						national_id nationalId,
						national_type nationalType,
						marry_status marryStatus,
						patty_name pattyName,
-- 						t1.ability_id abId,
						t2.key_talent_type_id keyTalentId,
-- 						t3.performance_id perId,
-- 						t1.ability_id abName,
						t2.key_talent_type_id keyTalentName,
-- 						t3.performance_id perName,
						effect_date effectDate,
						expiry_date expiryDate,
						enabled curEnabled,
						is_key_talent isKeyTalent,
						run_off_date roDate,
						entry_date entryDate,
						tel_phone telPhone,
						qq curQQ,
						wx_code wxCode,
						address curAddress,
						contract_unit contractUnit,
						work_place workPlace,
						regular_date regularDate,
						apply_type applyType,
						apply_channel applyChannel,
						speciality curSpeciality
					FROM soure_dim_emp sue 
-- 					INNER JOIN dim_ability t1 on t1.ability_key = sue.ability_key AND t1.customer_Id = sue.customer_id
					INNER JOIN dim_key_talent_type t2 on t2.key_talent_type_key = sue.key_talent_type_key AND t2.customer_Id = sue.customer_id
-- 					INNER JOIN dim_performance t3 on t3.performance_key = sue.performance_key AND t3.customer_Id = sue.customer_id
					WHERE sue.customer_id = customerId;
	
-- 	DECLARE CONTINUE HANDLER FOR NOT FOUND SET SET done = 1;
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = null;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN
			ROLLBACK; SHOW ERRORS;
			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
			VALUES(replace(UUID(),'-',''), customerId, optUserId,  concat("【员工-维度表】：", empKey, "的员工数据失败！"), startTime, now(), "error");
		END;
		
		SET startTime = now();
		OPEN s_cur;
		WHILE(done IS NOT NULL) DO

			FETCH s_cur INTO empKey, userName, userNameCh,imgPath, passtimeOrFulltime,
											curAge,companyAge, curSex, curNation, curDegree, nativePlace, curCountry, birthPlace, birthDate, nationalId, nationalType,
											marryStatus, pattyName,
-- 											abId, keyTalentId, perId,
-- 											abName, keyTalentName, perName,
											keyTalentId, keyTalentName,
											effectDate, expiryDate, curEnabled, isKeyTalent, roDate,
										 entryDate,
										 telPhone,
										 curQQ,
										 wxCode,
										 curAddress,
										 contractUnit,
										 workPlace,
										 regularDate,
										 applyType,
										 applyChannel,
										 curSpeciality;

  			START TRANSACTION;
				SELECT count(1) as exist into exist FROM dim_emp emp
				WHERE emp.emp_key = empKey AND customer_id = customerId;

				IF exist>0 THEN
-- INSERT INTO debug(exist, type) VALUES (exist, 'update');
					UPDATE dim_emp
					SET user_name = userName, user_name_ch = userNameCh, img_path = imgPath, passtime_or_fulltime = passtimeOrFulltime,
							age = curAge, company_age = companyAge, sex = curSex, nation = curNation, degree = curDegree, native_place = nativePlace, country = curCountry,
							birth_place = birthPlace, birth_date = birthDate, national_id = nationalId, national_type = nationalType,
							marry_status = marryStatus, patty_name = pattyName,
							/*ability_name = abName,*/ key_talent_type_name = keyTalentName, /*performance_name = perName,*/
							effect_date = effectDate, expiry_date = expiryDate, enabled = curEnabled, is_key_talent = isKeyTalent, run_off_date = roDate,
							entry_date = entryDate,
							tel_phone = telPhone,
							qq = curQQ,
							wx_code = wxCode,
							address = curAddress,
							contract_unit = contractUnit,
							work_place = workPlace,
							regular_date = regularDate,
							apply_type = applyType,
							apply_channel = applyChannel,
							speciality = curSpeciality
					WHERE emp_key = empKey AND customer_id = customerId;
						
			-- INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type) VALUES(replace(UUID(),'-',''), customerId, optUserId, concat("【员工-维度表】：", empKey, "内容数据更新成功！"), startTime, now(), "success");
				ELSE
-- INSERT INTO debug(exist, type) VALUES (exist, 'insert');
					INSERT INTO dim_emp(
									emp_id, customer_id, emp_key, user_name, user_name_ch, img_path,
									passtime_or_fulltime,
									age, company_age, sex, nation, degree, native_place, country, birth_place, birth_date,
									national_id , national_type , marry_status , patty_name,
									/*ability_id,*/ key_talent_type_id, /*performance_id,*/
									ability_name, key_talent_type_name, performance_name,
									effect_date, expiry_date,enabled, is_key_talent, run_off_date,
									entry_date,tel_phone,qq, wx_code,address,contract_unit,work_place,regular_date,apply_type,apply_channel,speciality
									)
					VALUES(replace(UUID(),'-',''), customerId, empKey, userName, userNameCh, imgPath, passtimeOrFulltime,
									curAge, companyAge, curSex, curNation, curDegree, nativePlace, curCountry, birthPlace, birthDate,
									nationalId , nationalType , marryStatus , pattyName,
									/*abId,*/ keyTalentId, /*perId,*/
									/*abName,*/ keyTalentName, /*perName,*/
									effectDate, expiryDate,curEnabled, isKeyTalent, roDate,
									entryDate,
									telPhone,
									curQQ,
									wxCode,
									curAddress,
									contractUnit,
									workPlace,
									regularDate,
									applyType,
									applyChannel,
									curSpeciality
								);
			END IF;

-- 			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
-- 			VALUES(replace(UUID(),'-',''), customerId, optUserId, concat("【员工-维度表】：数据成功", IF(exist>0, "update", "insert") ), startTime, now(), "success");
-- 			set exist = 0;

		END WHILE;
		CLOSE s_cur;

END;
-- DELIMITER ;







#=======================================================pro_fetch_dim_user=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- 保证 dim_emp 表 完成
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_user`;
CREATE PROCEDURE pro_fetch_dim_user(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE empId,empKey, userKey VARCHAR(32);
	DECLARE userName VARCHAR(10);
	DECLARE userNameCh VARCHAR(5);	
	DECLARE pwd VARCHAR(20);
	DECLARE email VARCHAR(50);
	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP;
	DECLARE exist int;

	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
					SELECT 
						de.emp_id as empId, 
						t.emp_key as empKey,
						t.user_key as userKey,
						t.user_name as userName,
						t.user_name_ch as userNameCh,
						t.`password` as pwd,
						t.email as email
					FROM soure_dim_user t
					LEFT JOIN dim_emp de on de.emp_key = t.emp_key AND now()>=de.effect_date AND de.expiry_date is null AND de.enabled = 1
								and de.user_name = t.user_name and de.user_name_ch = t.user_name_ch
					WHERE t.customer_id = customerId;
	
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN
			ROLLBACK; SHOW ERRORS;
			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
			VALUES(replace(UUID(),'-',''), customerId, optUserId, CONCAT("【用户-维度表】：",empKey, "数据失败"), startTime, now(), "error");
		END;

		SET startTime = now();
		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO empId, empKey, userKey, userName, userNameCh, pwd, email;	-- empKey 没有用到
 			IF NOT done THEN
				START TRANSACTION;
				SELECT count(1) as exist into exist FROM dim_user du
				WHERE du.user_key = userKey AND customer_id = customerId;

				IF exist>0 THEN
					UPDATE dim_user
					SET emp_id = empId, user_name = userName, user_name_ch = userNameCh, `PASSWORD` = pwd, email = email,
							modify_user_id = optUserId, modify_time = now()
					WHERE user_key = userKey AND customer_id = customerId and sys_deploy = 0;
					
-- 					INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
-- 					VALUES(replace(UUID(),'-',''), customerId, optUserId, concat("【用户-维度表】：", userKey, "内容数据更新成功！"), startTime, now(), "success");
				ELSE
					INSERT into dim_user(
									user_id, customer_id, emp_id, user_key, user_name, user_name_ch,
									`PASSWORD`, email, sys_deploy,
									create_user_id, create_time)
					VALUES(replace(UUID(),'-',''), customerId, empId, userKey, userName, userNameCh, pwd,  email, 0, optUserId, now());
				END IF;
 			END IF;
		END WHILE;
		CLOSE s_cur;

-- 		INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
-- 		VALUES(replace(UUID(),'-',''), customerId, optUserId, "【用户-维度表】：数据成功", startTime, now(), "success");

END;
-- DELIMITER ;


#=======================================================pro_fetch_dim_role=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_role`;
CREATE PROCEDURE pro_fetch_dim_role(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE roleKey VARCHAR(20);
	DECLARE roleName VARCHAR(10);

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE exist int;
	DECLARE startTime TIMESTAMP;

	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
					SELECT sdr.role_key as roleKey, sdr.role_name as roleName
					FROM soure_dim_role sdr WHERE sdr.customer_id = customerId;
	
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN
			ROLLBACK; SHOW ERRORS;
			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
			VALUES(replace(UUID(),'-',''), customerId, optUserId, CONCAT("【角色-维度表】：", roleKey, "数据失败"), startTime, now(), "error");
		END;

		SET startTime = now();

		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO roleKey, roleName;
				START TRANSACTION;
				SELECT count(1) as exist into exist FROM dim_role
				WHERE role_key = roleKey AND customer_id = customerId;

				IF exist>0 THEN
					UPDATE dim_role 
					SET role_name = roleName,
							modify_user_id = optUserId, modify_time = now()
					WHERE role_key = roleKey AND customer_id = customerId;
	
				ELSE

					INSERT into dim_role(
									role_id, customer_id,
									role_key, role_name,
									create_user_id, create_time)
					VALUES(replace(UUID(),'-',''), customerId, roleKey, roleName, optUserId, now());

				END IF;
		END WHILE;
		CLOSE s_cur;

END;
-- DELIMITER ;

#=======================================================pro_fetch_dim_organization_type=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_organization_type`;
CREATE PROCEDURE pro_fetch_dim_organization_type(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE typeKey VARCHAR(32);
	DECLARE typeLevel VARCHAR(10);
	DECLARE typeName VARCHAR(50);

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE exist int;
	DECLARE startTime TIMESTAMP;

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【机构级别-维度表】：数据刷新完成'; 

  -- 遍历数据结束标志
	DECLARE done INT DEFAULT 0;
	-- 游标
	DECLARE s_cur CURSOR FOR
					SELECT sdot.organization_type_key as typeKey,
								 sdot.organization_type_level as typeLevel, 
								 sdot.organization_type_name as typeName
					FROM soure_dim_organization_type sdot WHERE sdot.customer_id = customerId;
	
	-- 将结束标志绑定到游标
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET p_error = 1;

		SET startTime = now();

		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO typeKey, typeLevel, typeName;
-- 			IF NOT done THEN
				START TRANSACTION;
				SELECT count(1) as exist into exist FROM dim_organization_type WHERE organization_type_key = typeKey AND customer_id = customerId;

				IF exist>0 THEN
					UPDATE dim_organization_type 
					SET organization_type_level = typeLevel, organization_type_name = typeName
					WHERE organization_type_key = typeKey AND customer_id = customerId;
				ELSE
					INSERT into dim_organization_type 
					VALUES(replace(UUID(),'-',''), customerId, typeKey, typeLevel, typeName);
				END IF;
-- 			END IF;
		END WHILE;
		CLOSE s_cur;


		IF p_error = 1 THEN  
			ROLLBACK; SHOW ERRORS;
			SET p_message = concat("【机构级别-维度表】：数据刷新失败。", "操作用户：", optUserId);
			INSERT INTO db_log
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_organization_type', p_message, startTime, now(), 'error' );
	 
		ELSE  
				COMMIT;  
				INSERT INTO db_log (log_id, customer_id, user_id, module, text, start_time, end_time, type)
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_organization_type', p_message, startTime, now(), 'sucess' );
		END IF;


END;
-- DELIMITER ;




#=======================================================pro_fetch_organization=======================================================
-- 'demo'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- 保证dim_business_unit、dim_organization_type数据存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_organization`;
CREATE PROCEDURE pro_fetch_organization(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	-- 定义接收游标数据的变量 
	DECLARE buId,oTypeId VARCHAR(32);
	DECLARE buKey, oTypeKey, orgKey, parentKey VARCHAR(20);
	DECLARE orgName VARCHAR(50);
	DECLARE isSingle, curEnabled INT(1);
	DECLARE effectDate TIMESTAMP;

	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;

	-- 定义接收临时表数据的变量 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE exist, buExist, oTypeExist INT;
	DECLARE startTime TIMESTAMP;

  -- 遍历数据结束标志
	DECLARE done INT DEFAULT 0;
	-- 游标
	DECLARE s_cur CURSOR FOR
									SELECT 
										bu.business_unit_id as buId,
										ot.organization_type_id as oTypeId,
										sorg.business_unit_key as buKey,
										sorg.organization_type_key as oTypeKey,
										sorg.organization_key as orgKey,
										sorg.organization_parent_key as parentKey,
										sorg.organization_name as orgName,
										sorg.is_single as isSingle,
										sorg.enabled as curEnabled,
										sorg.effect_date as effectDate
									FROM soure_dim_organization sorg
									INNER JOIN dim_business_unit bu on bu.business_unit_key = sorg.business_unit_key
									INNER JOIN dim_organization_type ot on sorg.organization_type_key = ot.organization_type_key
 									WHERE sorg.customer_id = customerId
									ORDER BY ot.organization_type_level;
	
	-- 将结束标志绑定到游标
	-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET SET done = 1;
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN
			ROLLBACK; SHOW ERRORS;
			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
			VALUES(replace(UUID(),'-',''), customerId, optUserId, CONCAT("【机构-维度表】：",buKey, "与", oTypeKey, "与", orgKey, "数据失败！"), startTime, now(), "error");
		END;


	-- 删除所有机构，准备重新写入。
 	-- delete from dim_organization where customer_id = customerId;

	SELECT count(1) as buExist INTO buExist FROM dim_business_unit WHERE customer_id = customerId;
	SELECT count(1) as oTypeExist INTO oTypeExist FROM dim_organization_type WHERE customer_id = customerId;

	IF buExist IS null THEN
		INSERT INTO db_log(log_id,customer_id,user_id, text, start_time, end_time, type)
		VALUES(replace(UUID(),'-',''), customerId, optUserId, "【业务单位-维度表】：不能为空", now(), now(), "error");
	ELSEIF oTypeExist is NULL THEN
		INSERT INTO db_log(log_id,customer_id,user_id, text, start_time, end_time, type)
		VALUES(replace(UUID(),'-',''), customerId, optUserId, "【机构级别-维度表】：不能为空", now(), now(), "error");
	ELSE
		SET startTime = now();
		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO buId,oTypeId, buKey,oTypeKey, orgKey,  parentKey, orgName, isSingle, curEnabled, effectDate;
-- 			IF NOT done THEN
				START TRANSACTION;
				SELECT count(1) as exist into exist 
					FROM dim_organization org
				WHERE org.organization_key = orgKey and org.business_unit_id = buId and org.organization_type_id= oTypeId AND org.customer_id = customerId;

				IF exist>0 THEN
					UPDATE dim_organization org1 
						SET org1.business_unit_id = buId, org1.organization_type_id = oTypeId,
								org1.organization_parent_id = parentKey,-- fn_parentKeyToParentId(parentKey, customerId),
								org1.organization_name = orgName, org1.is_single = isSingle,
								org1.effect_date = effectDate, org1.enabled = curEnabled
					WHERE org1.organization_key = orgKey and org1.business_unit_id = buId and org1.organization_type_id= oTypeId AND org1.customer_id = customerId;

				ELSE

					-- 默认是否有子节点为0
					INSERT into dim_organization(
									organization_id, customer_id, business_unit_id, organization_type_id, organization_key,
									organization_parent_id,
									organization_name, is_single, 
									has_children, effect_date, enabled)
					VALUES(replace(UUID(),'-',''), customerId, buId, oTypeId, orgKey,
									parentKey,-- fn_parentKeyToParentId(parentKey, customerId),
									orgName, isSingle, 0, effectDate, curEnabled);

				END IF;				
-- 			END IF;
	
			-- 更新所有parentKey to parentId
-- 			UPDATE dim_organization org1 
-- 				SET org1.organization_parent_id = fn_key_to_id(parentKey, customerId, 'org')
-- 			WHERE org1.organization_key = orgKey and org1.business_unit_id = buId and org1.organization_type_id= oTypeId AND org1.customer_id = customerId;

		END WHILE;

-- 			REPLACE INTO dim_organization(organization_id, customer_id, business_unit_id, organization_type_id, organization_key,
-- 									organization_parent_id,
-- 									organization_name, is_single, effect_date, enabled)
-- 			SELECT t1.organization_id, t1.customer_id, t1.business_unit_id, t1.organization_type_id, t1.organization_key,
-- 						 IFNULL(t2.organization_id, "-1") as organization_parent_id, 
-- 						 t1.organization_name, t1.is_single, t1.effect_date, t1.enabled
-- 			from dim_organization t1
-- 			LEFT JOIN dim_organization t2 on t1.organization_parent_id = t2.organization_key;
		CLOSE s_cur;


-- 		重置
		SET done = 0;
		OPEN s_cur;
		REPEAT
			FETCH s_cur INTO buId,oTypeId, buKey,oTypeKey, orgKey,  parentKey, orgName, isSingle, curEnabled, effectDate;
		-- 更新所有parentKey to parentId
			UPDATE dim_organization org1 
				SET org1.organization_parent_id = fn_key_to_id(parentKey, customerId, 'org')
			WHERE org1.organization_key = orgKey and org1.business_unit_id = buId and org1.organization_type_id= oTypeId AND org1.customer_id = customerId;

		UNTIL done END REPEAT;
		CLOSE s_cur;

-- 		INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
-- 		VALUES(replace(UUID(),'-',''), customerId, optUserId, "【机构-维度表】：基础数据构造成功", startTime, now(), "success");
	END IF;

END;
-- DELIMITER ;



#=======================================================pro_create_childlist=======================================================
-- 从某节点向下遍历子节点，递归生成临时表数据
DROP PROCEDURE IF EXISTS mup.pro_create_childlist;
CREATE PROCEDURE mup.pro_create_childlist(IN rootId VARCHAR(32), IN nDepth INT, in customerId VARCHAR(32))  
BEGIN  
      DECLARE done INT DEFAULT 0;  
      DECLARE b VARCHAR(32);  
      DECLARE cur1 CURSOR FOR 
				SELECT vo.organization_id FROM v_dim_organization vo
				LEFT JOIN dim_organization_type dot ON dot.organization_type_id = vo.organization_type_id
				WHERE vo.organization_parent_id = rootId AND vo.customer_id = customerId
				ORDER BY dot.organization_type_level; 
      DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;  
      SET max_sp_recursion_depth=12;  

      INSERT INTO tmpLst VALUES (NULL, rootId, nDepth);  
      OPEN cur1;  
      FETCH cur1 INTO b;  
      WHILE done=0 DO  
              CALL mup.pro_create_childlist(b, nDepth+1, customerId);  
              FETCH cur1 INTO b;  
      END WHILE;  
-- SELECT * FROM tmpLst;
      CLOSE cur1;  
END;


#=======================================================pro_create_pnlist=======================================================
-- 递归过程输出某节点name路径
DROP PROCEDURE IF EXISTS mup.pro_create_pnlist;
CREATE PROCEDURE mup.pro_create_pnlist(IN nid VARCHAR(32), IN delimit VARCHAR(10), in customerId VARCHAR(32), INOUT pathstr VARCHAR(1000))
BEGIN                    
      DECLARE done INT DEFAULT 0;  
      DECLARE parentid VARCHAR(32) DEFAULT 0;        
      DECLARE cur1 CURSOR FOR    
      SELECT t.organization_parent_id as parentid, CONCAT(t.organization_key, delimit, pathstr)
        FROM v_dim_organization AS t WHERE t.organization_id = nid and t.customer_id = customerId;
      DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;  
      SET max_sp_recursion_depth=12;      
              
      OPEN cur1;  
      FETCH cur1 INTO parentid, pathstr;  

-- 			WHILE done=0 DO  
--               CALL mup.pro_create_pnlist(parentid, delimit,customerId, pathstr);  
--               FETCH cur1 INTO parentid, pathstr;  
--       END WHILE; 
			if done=0 THEN  
              CALL mup.pro_create_pnlist(parentid, delimit,customerId, pathstr);  
              FETCH cur1 INTO parentid, pathstr;  
      END if; 

      CLOSE cur1;   
END;



#=======================================================pro_init_organization=======================================================
-- 全路径、是否有子节点  
DROP PROCEDURE IF EXISTS pro_init_organization;
CREATE PROCEDURE pro_init_organization(in p_customer_key VARCHAR(50))  
BEGIN  
	 -- 需要定义接收游标数据的变量
		DECLARE cid VARCHAR(32);
		DECLARE pathname VARCHAR(32);
		DECLARE depth INT;
		DECLARE orgPid VARCHAR(32);

		DECLARE customerId VARCHAR(32) DEFAULT (SELECT dc.customer_id FROM dim_customer dc WHERE dc.customer_key=p_customer_key);
		DECLARE startTime TIMESTAMP DEFAULT now();
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000) DEFAULT '【机构-维度表】：数据刷新完成';


	 -- 遍历数据结束标志
		DECLARE done INT DEFAULT FALSE;
	 -- 游标
		DECLARE cur CURSOR FOR
			SELECT
			 org.organization_id as cid, fn_tree_pathname(org.organization_id, '_', customerId) as pathname, tmpLst.depth,
				org.organization_parent_id as orgPid
			FROM tmpLst, v_dim_organization org
			LEFT JOIN dim_organization_type dot ON dot.organization_type_id = org.organization_type_id
			WHERE tmpLst.id = org.organization_id  ORDER BY dot.organization_type_level;

	 -- 将结束标志绑定到游标
		DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	

	-- --------------------------------------------------------------------
	-- 全路径设置
		DROP TEMPORARY TABLE IF EXISTS tmpLst;  
		CREATE TEMPORARY TABLE IF NOT EXISTS tmpLst    
		 (sno INT PRIMARY KEY AUTO_INCREMENT, id VARCHAR(32), depth INT);        
		CALL mup.pro_create_childlist(-1, 0, customerId);

		SELECT
		 org.organization_id as cid, fn_tree_pathname(org.organization_id, '_', customerId) as pathname, tmpLst.depth 
		FROM tmpLst, v_dim_organization org
		LEFT JOIN dim_organization_type dot ON dot.organization_type_id = org.organization_type_id
		WHERE tmpLst.id = org.organization_id  ORDER BY dot.organization_type_level;

		OPEN cur;
		WHILE done != 1 DO
			FETCH cur INTO cid, pathname, depth, orgPid;
			START TRANSACTION;
			

			-- 更新全路径
			UPDATE dim_organization org 
				SET org.full_path = Substring(pathname, 1, LENGTH(pathname)-1),
						org.`depth` = depth
			where org.organization_id = cid AND org.customer_id = customerId;

			-- 更新是否有子节点
			UPDATE dim_organization as o1 
				SET o1.has_children = 1
			WHERE o1.organization_id in (
				SELECT t.organization_id from (
					SELECT o.organization_id FROM dim_organization o where o.organization_id = orgPid and o.customer_id = customerId) t
				WHERE t.organization_id is not null)
			AND o1.customer_id = customerId;


	 		END WHILE;
		CLOSE cur;
	-- --------------------------------------------------------------------

		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【机构-维度表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_organization', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_organization', p_message, startTime, now(), 'sucess' );
		END IF;

END;







#=======================================================pro_fetch_dim_sequence=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_sequence`;
CREATE PROCEDURE pro_fetch_dim_sequence(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【主序列维】';
	DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_fetch_dim_sequence');

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

			REPLACE INTO dim_sequence (
				sequence_id,
				customer_id,
				sequence_key,
				sequence_name,
				curt_name,
				show_index
			)
			SELECT 
				code_item_id,
				customerId,
				code_item_key,
				code_item_name,
				curt_name,
				show_index
			FROM soure_code_item t
			WHERE t.customer_id = customerId and t.code_group_id = p_key_work;

  
		INSERT INTO db_log 
		VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_dim_sequence', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		set @error_message = p_error;  


END;
-- DELIMITER ;
-- 	CALL pro_fetch_dim_sequence( 'b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 'sequence');	-- 序列






#=======================================================pro_fetch_dim_sequence_sub=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_sequence_sub`;
CREATE PROCEDURE pro_fetch_dim_sequence_sub(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32) )
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【子序列维】';
	DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_fetch_dim_sequence');

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

			REPLACE INTO dim_sequence_sub (
				sequence_sub_id,customer_id, sequence_id,
				sequence_sub_key,sequence_sub_name, curt_name, show_index
			)
			SELECT code_item_id, customerId, seq.sequence_id,
				code_item_key ,
				code_item_name ,
				t.curt_name,
				t.show_index
			FROM soure_code_item t
			INNER JOIN dim_sequence seq on seq.sequence_key = (select SUBSTRING_INDEX(t.code_item_key,"_",1) )
			WHERE t.customer_id = customerId and t.code_group_id = p_key_work;

		INSERT INTO db_log 
		VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_dim_sequence_sub', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		set @error_message = p_error;  


END;
-- DELIMITER ;

-- CALL pro_fetch_dim_sequence_sub('b5c9410dc7e4422c8e0189c7f8056b5f','DBA','sequence_sub');





#=======================================================pro_fetch_dim_position=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_position`;
CREATE PROCEDURE pro_fetch_dim_position(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【岗位维】：数据刷新完成');
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;


					INSERT INTO dim_position (
						position_id,
						customer_id,
						position_key,
 						position_name,
						 
						c_id
					)
					SELECT 
						replace(uuid(),'-',''),
						customerId,
						t.position_key,
 						t.position_name,
					 
						t.c_id
					FROM soure_dim_position t 
					WHERE t.customer_id = customerId
					ON DUPLICATE KEY UPDATE 
						customer_id = t.customer_id, 
						position_key = t.position_key,
						position_name = t.position_name
					;


			IF p_error = 1 THEN  
					ROLLBACK;  
 					SET p_message = concat("【岗位维】：数据刷新失败。操作用户：", optUserId);
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_position', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'error', 214);
			ELSE  
					COMMIT;  
 					INSERT INTO db_log 
 					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_position', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'sucess' , 214);
			END IF;  
			set @error_message = p_error;
	
END;
-- DELIMITER ;

CALL pro_fetch_dim_position('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');





#=======================================================pro_fetch_emp_position_relation=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：dim_emp, dim_position , dim_organization表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_position_relation`;
CREATE PROCEDURE pro_fetch_emp_position_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE empKey, posKey, orgKey VARCHAR(20);
	DECLARE empId, posId, orgId VARCHAR(32);
	DECLARE isRegular int(1);
-- 	DECLARE effectDate, expiryDate DATETIME;
	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE exist int;
	DECLARE startTime TIMESTAMP;

	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
					SELECT 
						t.emp_key empKey, t.position_key posKey, t.organization_key orgKey,
						t1.emp_id empId, t2.position_id posId, t3.organization_id orgId,
						t.is_regular isRegular
					FROM soure_emp_position_relation t 
					INNER JOIN dim_emp t1 on t.emp_key = t1.emp_key and t1.customer_id = t.customer_id AND now()>=t1.effect_date AND t1.expiry_date is null AND t1.enabled = 1
					INNER JOIN dim_position t2 on  t.position_key = t2.position_key and t2.customer_id = t.customer_id
					INNER JOIN dim_organization t3 on  t.organization_key = t3.organization_key and t3.customer_id = t.customer_id
					WHERE t.customer_id = customerId;
	

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN
			ROLLBACK; SHOW ERRORS;
			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
			VALUES(replace(UUID(),'-',''), customerId, optUserId, CONCAT("【员工岗位-维度表】：", empKey, "和", posId, "数据失败"), startTime, now(), "error");
		END;

		SET startTime = now();
		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO empKey, posKey, orgKey, empId, posId, orgId, isRegular; -- , enabled, effectDate, expiryDate;
				START TRANSACTION;
				SELECT count(1) as exist into exist FROM emp_position_relation
				WHERE emp_id = empId and position_id = posId and organization_id = orgId AND customer_id = customerId;

				IF exist>0 THEN
					UPDATE emp_position_relation 
					SET is_regular = isRegular -- , effect_date = effectDate, expiry_date=expiryDate, enabled = enabled
					WHERE emp_id = empId and position_id = posId and organization_id = orgId AND customer_id = customerId;

				ELSE
					INSERT into emp_position_relation(
									emp_position_id, customer_id,
									emp_id, position_id, organization_id, is_regular
									-- ,effect_date, expiry_date, enabled
									)
					VALUES(replace(UUID(),'-',''), customerId, empId, posId, orgId, isRegular -- , effectDate, expiryDate, enabled
								);
				END IF;

		END WHILE;
		CLOSE s_cur;


END;
-- DELIMITER ;

#=======================================================pro_fetch_user_role_relation=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- 删除所有关所重新插入
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_user_role_relation`;
CREATE PROCEDURE pro_fetch_user_role_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE userId, roleId VARCHAR(32);
	DECLARE userKey, roleKey VARCHAR(20);

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP;
	DECLARE exist int;

  -- 遍历数据结束标志
	DECLARE done INT DEFAULT 0;
	-- 游标
	DECLARE s_cur CURSOR FOR
					SELECT 
						du.user_id as userId,	dr.role_id as roleId,
						t.user_key as userKey, t.role_key as roleKey
					FROM soure_user_role_relation t
					inner JOIN dim_user du on t.user_key = du.user_key and du.customer_id = t.customer_id AND du.sys_deploy = 0
 					inner JOIN dim_role dr on t.role_key = dr.role_key and dr.customer_id = t.customer_id
					WHERE t.customer_id = customerId;

	-- 将结束标志绑定到游标
		DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
		DECLARE EXIT HANDLER FOR SQLEXCEPTION 
			BEGIN
				ROLLBACK; SHOW ERRORS;
				INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
				VALUES(replace(UUID(),'-',''), customerId, optUserId, CONCAT("【用户角色-关系表】：", roleKey, "与", userKey, "数据失败"), startTime, now(), "error");
			END;

		SET startTime = now();
	
		DELETE FROM user_role_relation WHERE customer_id = customerId;

		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO userId, roleId, userKey, roleKey;
		START TRANSACTION;

-- 			IF NOT done THEN

-- 				SELECT count(1) as exist into exist FROM user_role_relation t
-- 				WHERE t.user_id = userId and t.role_id = roleId AND t.customer_id = customerId;
-- 
-- 				IF exist>0 THEN
-- 					INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
-- 					VALUES(replace(UUID(),'-',''), customerId, optUserId, concat("【用户角色关系表】：", roleKey, "和", userKey, "关系数据已存在。"), startTime, now(), "info");
-- 					
-- 				ELSE
					INSERT into user_role_relation(
									user_role_id,	customer_id, role_id, user_id,
									create_user_id, create_time)
					VALUES(replace(UUID(),'-',''), customerId, roleId, userId, optUserId, now());
-- 				END IF;
-- 			END IF;

		END WHILE;
		CLOSE s_cur;

-- 		INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
-- 		VALUES(replace(UUID(),'-',''), customerId, optUserId, "【用户角色-关系表】：数据成功", startTime, now(), "success");

END;
-- DELIMITER ;


-- CALL pro_fetch_user_role_relation('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA');


#=======================================================pro_fetch_role_organization_relation=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：dim_role, dim_organization表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_role_organization_relation`;
CREATE PROCEDURE pro_fetch_role_organization_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE roleKey, organKey VARCHAR(20);
	DECLARE roleId, organId VARCHAR(32);

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP;
	DECLARE exist int;

  -- 遍历数据结束标志
	DECLARE done INT DEFAULT 0;
	-- 游标
	DECLARE s_cur CURSOR FOR
					SELECT 
						t.role_key as roleKey, t.organization_key as organKey,
						t1.role_id as roleId,
 						t2.organization_id as organId
					FROM soure_role_organization_relation t
					inner JOIN dim_role t1 on t.role_key = t1.role_key and t1.customer_id = t.customer_id
 					inner JOIN dim_organization t2 on t.organization_key = t2.organization_key and t2.customer_id = t.customer_id
					WHERE t.customer_id = customerId;


	-- 将结束标志绑定到游标
		DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
		DECLARE EXIT HANDLER FOR SQLEXCEPTION 
			BEGIN
				ROLLBACK; SHOW ERRORS;
				INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
				VALUES(replace(UUID(),'-',''), customerId, optUserId, CONCAT("【角色机构-关系表】：", roleKey, "与", organKey, "数据失败"), startTime, now(), "error");
			END;

		DELETE FROM role_organization_relation WHERE customer_id = customerId;

		SET startTime = now();
		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO roleKey, organKey, roleId, organId;
			START TRANSACTION;

					INSERT INTO role_organization_relation(
									role_organization_id, customer_id, role_id, organization_id, half_check, create_user_id, create_time)
					VALUES(replace(UUID(),'-',''), customerId, roleId, organId, 0, optUserId, now());

		END WHILE;
		CLOSE s_cur;

END;
-- DELIMITER ;




#=======================================================pro_fetch_emp_organization_relation=======================================================
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- 'demo','jxzhang'
--  保证：dim_organization表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_organization_relation`;
CREATE PROCEDURE pro_fetch_emp_organization_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【数据权限-关系表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

					REPLACE INTO emp_organization_relation (
						emp_organization_relation_id,
						customer_id,
						emp_id,
						organization_id,
						half_check,
						create_user_id,
						create_time
					)
					SELECT 
						id,
						customerId,
						t2.emp_id,
						t1.organization_id,
						0,
						optUserId,
						CURDATE()
					FROM soure_emp_organization_relation t 
					INNER JOIN dim_organization t1 on t.organization_key = t1.organization_key and t1.customer_id = t.customer_id
					INNER JOIN v_dim_emp t2 on t.emp_key = t2.emp_key and t2.customer_id = t.customer_id
					WHERE t.customer_id = customerId;

	IF p_error = 1 THEN  
			ROLLBACK;  
			SET p_message = concat("【数据权限-关系表】：数据刷新失败。操作用户：", optUserId);
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_organization_relation', p_message, startTime, now(), 'error' );
	ELSE  
			COMMIT;  
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_organization_relation', p_message, startTime, now(), 'sucess' );
	END IF;


	
END;
-- DELIMITER ;

-- CALL pro_fetch_emp_organization_relation('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');



#=======================================================pro_fetch_target_benefit_value=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_target_benefit_value`;
CREATE PROCEDURE pro_fetch_target_benefit_value(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_nextYear datetime )
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【人均效益-1.明年目标人均效益】：数据刷新完成');

	DECLARE y int(4) DEFAULT DATE_FORMAT(DATE_ADD(p_nextYear, Interval 1 YEAR),'%Y');	-- 当前时间加一年





	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

SET @isNotNull = (SELECT count(1) from soure_target_benefit_value);
IF @isNotNull >1 THEN

	START TRANSACTION;

		REPLACE INTO target_benefit_value (
			target_benefit_value_id,
			customer_id,
			organization_id,
			target_value,
			`year`
		)
		SELECT 
			t.id,
			customerId,
			t1.organization_id,
			target_value,
			`year`
		FROM soure_target_benefit_value t
		INNER JOIN dim_organization t1 on t.organization_key = t1.organization_key
			AND t.customer_id = t1.customer_id
		WHERE t.customer_id = customerId and `year` = y;


	 	IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【人均效益-1.明年目标人均效益】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'target_benefit_value', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'error', 401);
		ELSE  
				COMMIT;
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'target_benefit_value', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'sucess' , 401);	  
		END IF;

		SET @RenJunXiaoYi_error_mgs = p_error;
END IF;
END;
-- DELIMITER ;

-- CALL pro_fetch_target_benefit_value('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');




#=======================================================pro_fetch_trade_profit=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_trade_profit`;
-- CREATE PROCEDURE pro_fetch_trade_profit(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_last_curdate datetime)
CREATE PROCEDURE pro_fetch_trade_profit(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_nowTime datetime)
BEGIN

	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【人均效益-营业利润】';
	DECLARE yearMonth int(6) DEFAULT date_format(DATE_ADD(p_nowTime, Interval 0 minute),'%Y%m');
	DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_fetch_trade_profit');

	-- 定义接收临时表数据的变量 
	DECLARE exist INT;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_error INTEGER DEFAULT 0; 

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;


SET @isNotNull = (SELECT count(1) from soure_trade_profit);
IF @isNotNull >1 THEN

	START TRANSACTION;
	DELETE FROM trade_profit WHERE `year_month` = yearMonth;
		LB_INSERT:BEGIN
			INSERT INTO trade_profit(
				trade_profit_id, customer_id, organization_id, gain_amount, sales_amount, expend_amount, target_value, `year_month`, c_id
			)
			SELECT 
				replace(uuid(), '-',''), 
				customerId,
				t2.organization_id,
				t.gain_amount,
				t.sales_amount, 
				t.target_value,
				(t.sales_amount - t.gain_amount) expend_amount, -- t.expend_amount,
				t.`year_month`,
				c_id
			FROM soure_trade_profit t 
			INNER JOIN dim_organization t2 on t.organization_key = t2.organization_key and t2.customer_id = t.customer_id
			WHERE t.customer_id = customerId
			ON DUPLICATE KEY UPDATE
				customer_id = t.customer_id, 
				organization_id = t2.organization_id,
				gain_amount = t.gain_amount,
				sales_amount = t.sales_amount,
				target_value = t.target_value,
				expend_amount = (t.sales_amount - t.gain_amount),
				`year_month` = t.`year_month`
			;
		END LB_INSERT;


		INSERT INTO db_log 
		VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_trade_profit', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;

		SET @RenJunXiaoYi_error_mgs = p_error;
END IF;

END;
-- DELIMITER ;

-- SET @lastCurdate = ( SELECT DATE_SUB(CURDATE(),INTERVAL  1 MONTH));
-- CALL pro_fetch_trade_profit('b5c9410dc7e4422c8e0189c7f8056b5f','DBA' );




#=======================================================pro_fetch_factfte=======================================================
-- '2015-06-11','demo'
-- 测试时输入上个月时间。（ 这里没有对上个月时间处理，上个月时间处理在事件里完成。）
-- 涉及的表和作用：
-- 		dim_organization  按机构分组
-- 		dim_emp				按正职和兼职
-- 		emp_overtime	加班时间
-- 		trade_profit	机构的利润
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_factfte`;
CREATE PROCEDURE pro_fetch_factfte(in p_customer_id VARCHAR(50), in p_user_id VARCHAR(32),in p_nowTime datetime)
top:BEGIN

			-- 定义接收游标数据的变量 
			DECLARE orgId VARCHAR(32);
			DECLARE orgName, orgKey VARCHAR(50);
			DECLARE fullpath text;

			-- 定义接收临时表数据的变量 
			DECLARE fulltimeSum, passtimeSum, overtimeSum, fteCalc DOUBLE(6,2) DEFAULT 0.0;
			DECLARE	gainAmount DECIMAL(10,2);
			DECLARE benefitValue, targetValue DOUBLE(6,2);


			-- 定义获得输入参数的最后一天变量 
			DECLARE lastDay datetime DEFAULT LAST_DAY(p_nowTime);
			DECLARE yearMonth int(6) DEFAULT date_format(DATE_ADD(p_nowTime, Interval 0 minute),'%Y%m');

			DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
			DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
			DECLARE startTime DATETIME DEFAULT now();

			DECLARE p_error INTEGER DEFAULT 0; 
			DECLARE p_message VARCHAR(10000) DEFAULT '【人均效益-3.等效员工数】：数据刷新完成';

			-- 遍历数据结束标志
			DECLARE done INT DEFAULT FALSE;
			-- 游标
			DECLARE cur CURSOR FOR
					SELECT 
						org.organization_id as orgId, 
						org.organization_name as orgName,
						org.organization_key as orgKey,
						org.full_path as fullpath
					FROM dim_organization org 
					WHERE org.is_single = 1 -- 独立核算
					;


			DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
			-- 将结束标志绑定到游标
			DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;


			START TRANSACTION;
			-- 营业利润
-- 			CALL pro_fetch_trade_profit(customerId, optUserId );


			-- 删除所有有关的月份数据，准备重新写入。
			DELETE FROM fact_fte WHERE `YEAR_MONTH` = yearMonth AND customer_id = customerId;
			-- 插入fact_fte
			REPLACE INTO fact_fte(
				fte_id, customer_id, organization_id, organization_name, 
				fulltime_sum, passtime_sum, overtime_sum,
				fte_value,
				`year_month`,
				sales_amount,	-- 收入
-- 				expend_amount,-- 支出
				gain_amount,	-- 利润
				target_value,	-- 目前人均效益
				benefit_value,-- 实际人均效益
				range_per			-- 变化幅度
			)
			SELECT 
				trade_profit_id,
				t.customer_id,
				t.organization_id, t1.organization_name,
				0, 0, 0, 0,		-- fulltime_sum, passtime_sum, overtime_sum, fte_value
				t.`year_month`,
				t.sales_amount,	-- 收入
-- 				t.expend_amount,-- 支出
				t.gain_amount,	-- 利润
				t.target_value,	-- 目前人均效益
				0, 0					-- benefit_value, range_per
			FROM trade_profit t
			INNER JOIN dim_organization t1 on t.organization_id = t1.organization_id AND t.customer_id = t1.customer_id
			WHERE `year_month` = yearMonth AND t.customer_id = customerId
			;


 -- 打开游标
  OPEN cur;
 -- 开始循环
  read_loop: LOOP
    -- 提取游标里的数据
    FETCH cur INTO orgId, orgName, orgKey, fullpath;
    -- 声明结束的时候
    IF done THEN
      LEAVE read_loop;
    END IF;


			-- 当前机构下所有子孙机构包括自己
			DROP TABLE IF EXISTS tmp_effectOrganId;   
			CREATE TABLE tmp_effectOrganId (
					SELECT t1.organization_id FROM dim_organization t1
					WHERE locate( orgKey, t1.full_path ) AND t1.customer_id =  customerId
			);

			-- 正职人员 * 1
-- ----------------------------------------------------------- 过时写法---------------
-- 			SET @fulltimeSum = (-- 过时写法
-- 							-- 当前父机构里总 正职人数
-- 							SELECT IFNULL(SUM(t.fSum),0)
-- 							FROM (
-- 							-- 当前父机构里，各个机构子孙的正职人数
-- 								SELECT
-- 									count(t1.emp_id) AS fSum
-- 								FROM v_dim_emp t1
-- 								INNER JOIN tmp_effectOrganId t2 on t2.organization_id = t1.organization_id
-- 								where t1.passtime_or_fulltime = 'f'
-- 									and (t1.run_off_date is NULL or t1.run_off_date > lastDay)		-- 离职时间
-- 								GROUP BY t2.organization_id
-- 						) t
-- 			);

			-- 兼职人员 * 0.5
-- ----------------------------------------------------------- 过时写法---------------
-- 			SET @passtimeSum = (
-- 							SELECT IFNULL(SUM(t.fSum) * 0.5, 0)
-- 							FROM (
-- 								SELECT
-- 									count(t1.emp_id) AS fSum
-- 								FROM v_dim_emp t1
-- 								INNER JOIN tmp_effectOrganId t2 on t2.organization_id = t1.organization_id
-- 								where t1.passtime_or_fulltime = 'p'
-- 									and (t1.run_off_date is NULL or t1.run_off_date > lastDay)		-- 离职时间
-- 								GROUP BY t2.organization_id
-- 						) t
-- 			);

			-- 目前使用硬编码。机构总人数应该是用day = p_nowTime
			SET @fulltimeSum = (SELECT emp_count_sum FROM history_emp_count t where `day` = p_nowTime AND type = 2 AND t.organization_id = orgId AND customer_id = customerId);
			SET @passtimeSum = IFNULL((SELECT emp_count_sum FROM history_emp_count t where `day` = p_nowTime AND type = 3 AND t.organization_id = orgId AND customer_id = customerId)* 0.5, 0);

			-- 共加班时间 /8
-- 			SET @overtimeSum = (
-- 							SELECT IFNULL(SUM(t.otSum),0)
-- 							FROM (
-- 								-- 当前父机构里，各个机构子孙的加班时数/8
-- 								SELECT IFNULL(sum(t3.hour_count),0.0) / 8 AS otSum
-- 								FROM
-- 									v_dim_emp t
-- 								INNER JOIN tmp_effectOrganId as t2 on t2.organization_id = t.organization_id
-- 								INNER JOIN emp_overtime t3 on t3.emp_id = t.emp_id AND t.customer_id = t3.customer_id
-- 								WHERE t3.`year_month` = yearMonth 
-- 								GROUP BY t2.organization_id
-- 							) t
-- 			);

			-- 共加班时间 /8
			SET @overtimeSum = (
						SELECT IFNULL(sum(t.ot_hour),0.0) / 8 AS otSum
						FROM emp_attendance_month t
						INNER JOIN tmp_effectOrganId t1 on t.organization_id = t1.organization_id
						WHERE t.`year_month` = yearMonth AND t.customer_id = customerId 
			);

			-- 人均效益 = (正职人员*1) + (兼职人员*0.5) + (共加班时间 /8)
			SET @fteValue = @fulltimeSum + @passtimeSum + @overtimeSum;

			UPDATE fact_fte SET 
					fulltime_sum = @fulltimeSum, 
					passtime_sum = @passtimeSum,
					overtime_sum =@overtimeSum,
					fte_value = @fteValue,
					benefit_value = round(gain_amount/@fteValue,4)
			WHERE organization_id = orgId AND `year_month` = yearMonth
			;


  END LOOP;
  -- 关闭游标
  CLOSE cur;
	

-- 		IF p_error = 1 THEN  
-- 				ROLLBACK;  
-- 		ELSE  
-- 				COMMIT;  
-- 				-- 变化幅度
-- 				CALL pro_update_ffRange(p_nowTime, customerId);
-- 		END IF;

		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【人均效益-3.等效员工数】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'fact_fte', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'error', 402);
		ELSE  
				COMMIT;
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'fact_fte', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'sucess' , 402);	  
		END IF;

SET @RenJunXiaoYi_error_mgs = p_error;
END;
-- DELIMITER ;
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2014-01-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2014-02-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2014-03-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2014-04-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2014-05-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2014-06-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2014-07-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2014-08-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2014-09-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2014-10-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2014-11-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2014-12-15', INTERVAL - 1 MONTH));

CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2015-01-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2015-02-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2015-03-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2015-04-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2015-05-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2015-06-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2015-07-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2015-08-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2015-09-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2015-10-15', INTERVAL - 1 MONTH));
CALL pro_fetch_factfte ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', DATE_ADD('2015-11-15', INTERVAL - 1 MONTH));



#=======================================================pro_update_ffRange=======================================================
-- '2015-06-11','b5c9410dc7e4422c8e0189c7f8056b5f'
-- 测试时输入上个月时间。（ 这里没有对上个月时间处理，上个月时间处理在event事件里完成。）
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_update_ffRange`;
CREATE PROCEDURE pro_update_ffRange(in p_customer_id VARCHAR(50), in p_user_id VARCHAR(32), in p_nowTime datetime)
BEGIN

			DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
			DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
			DECLARE startTime DATETIME DEFAULT now();
			DECLARE p_error INTEGER DEFAULT 0; 
			DECLARE p_message VARCHAR(10000) DEFAULT '【人均效益-4.变化幅度】：数据刷新完成';
			-- 定义接收游标数据的变量 
			DECLARE orgId VARCHAR(32);

			-- 定义接收临时表数据的变量 
			DECLARE	nowBv, bv, rangeVal DOUBLE(6,2);
			DECLARE p_time datetime DEFAULT DATE_ADD(p_nowTime, Interval -1 month);
			-- 当前传入来的月份
			DECLARE nowYearMonth int(6) DEFAULT date_format(DATE_ADD(p_nowTime, Interval 0 minute),'%Y%m');
			-- 上个月份时间
			DECLARE yearMonth int(6) DEFAULT date_format(DATE_ADD(p_nowTime, Interval -1 month),'%Y%m');

		-- 	DECLARE customerId VARCHAR(32);

			-- 遍历数据结束标志
			DECLARE done INT DEFAULT FALSE;
			-- 游标
			DECLARE cur CURSOR FOR SELECT ff.organization_id as orgId FROM fact_fte ff;	

			-- 将结束标志绑定到游标
			DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
			DECLARE EXIT HANDLER FOR SQLEXCEPTION BEGIN ROLLBACK; SHOW ERRORS; END;

			START TRANSACTION;
		 -- 打开游标
			OPEN cur;
		 -- 开始循环
			read_loop: LOOP
				-- 提取游标里的数据
				FETCH cur INTO orgId;
				-- 声明结束的时候
				IF done THEN
					LEAVE read_loop;
				END IF;

			-- （本期数－上期数）/上期数*100%
						SELECT 
								max(IF(ff.`year_month` = nowYearMonth, ff.benefit_value, 0)) nowBv,
								max(IF(ff.`year_month` = yearMonth, ff.benefit_value, 0)) bv
						INTO nowBv, bv
						from fact_fte ff
						WHERE ff.organization_id = orgId and (
								ff.`year_month` = nowYearMonth or ff.`year_month` = yearMonth);
					
					set rangeVal = ((nowBv-bv)/bv*100);

					UPDATE fact_fte ff SET ff.range_per = rangeVal
					WHERE ff.organization_id = orgId and ff.`year_month` = nowYearMonth and ff.customer_id = customerId;

			END LOOP;
			-- 关闭游标
			CLOSE cur;

			IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【人均效益-4.变化幅度】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'fact_fte.rangeVal', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'error', 403);
		ELSE  
				COMMIT;
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'fact_fte.rangeVal', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), 'sucess' , 403);	  
		END IF;


	SET @RenJunXiaoYi_error_mgs = p_error;
END;
-- DELIMITER ;



#=======================================================pro_fetch_dim_sales_product=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_sales_product`;
CREATE PROCEDURE pro_fetch_dim_sales_product(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32) )
BEGIN


	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【产品信息】';
	DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_fetch_dim_sales_product');

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

		

		INSERT INTO db_log 
		VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_dim_sales_product', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		set @error_message = p_error;  


END;
-- DELIMITER ;





#=======================================================pro_fetch_dim_sales_team=======================================================
DROP PROCEDURE IF EXISTS `pro_fetch_dim_sales_team`;
CREATE PROCEDURE pro_fetch_dim_sales_team(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_key_work VARCHAR(32) )
BEGIN


	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【团队信息】';
	DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_fetch_dim_sales_product');

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

		

		INSERT INTO db_log 
		VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_dim_sales_team', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		set @error_message = p_error;  


END;
-- DELIMITER ;





#=======================================================pro_fetch_quota_RenJunXiaoYi=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_quota_RenJunXiaoYi`;
CREATE PROCEDURE pro_fetch_quota_RenJunXiaoYi(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32),in p_nowTime datetime)

top:begin 

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();
-- 		DECLARE p_nowTime  TIMESTAMP DEFAULT now();	-- 先硬编码
		DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_fetch_quota_RenJunXiaoYi');

		SET @RenJunXiaoYi_error_mgs = 0;

-- 明年目标人均效益
		IF @RenJunXiaoYi_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
			CALL pro_fetch_target_benefit_value(customerId, optUserId, p_nowTime);
		END IF;

		-- 营业利润
		IF @RenJunXiaoYi_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
			CALL pro_fetch_trade_profit( customerId, optUserId, p_nowTime);
		END IF;

		-- 等效员工数
		IF @RenJunXiaoYi_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
			CALL pro_fetch_factfte(customerId, optUserId, p_nowTime);
		END IF;
		
		-- 等效员工数-变化幅度
		IF @RenJunXiaoYi_error_mgs = 1 THEN 
			LEAVE top;
		ELSE 
			CALL pro_update_ffRange(customerId, optUserId, p_nowTime);
		END IF;

			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_quota_RenJunXiaoYi', "【人均效益-指表】", TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), @RenJunXiaoYi_error_mgs , showIndex);


end;
-- DELIMITER ;

-- CALL pro_fetch_quota_RenJunXiaoYi('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');

CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2014-01-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2014-02-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2014-03-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2014-04-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2014-05-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2014-06-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2014-07-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2014-08-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2014-09-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2014-10-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2014-11-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2014-12-15');


CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2015-01-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2015-02-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2015-03-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2015-04-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2015-05-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2015-06-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2015-07-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2015-08-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2015-09-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2015-10-15');
CALL pro_fetch_quota_RenJunXiaoYi ('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', '2015-11-15');




#=======================================================pro_fetch_dim_separation_risk=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_separation_risk`;
CREATE PROCEDURE pro_fetch_dim_separation_risk(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
				LB_INSERT:BEGIN
					
						INSERT INTO dim_separation_risk (
							separation_risk_id, customer_id, separation_risk_key, separation_risk_parent_id, separation_risk_parent_key, separation_risk_name, refer, show_index
						)
						SELECT 
							id, customer_id, separation_risk_key, parent_id, parent_key, separation_risk_name, refer, show_index
						FROM soure_dim_separation_risk t
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								separation_risk_key = t.separation_risk_key,
								separation_risk_parent_id = t.parent_id,
								separation_risk_parent_key = t.parent_key,
								separation_risk_name = t.separation_risk_name,
								refer = t.refer,
								show_index = t.show_index
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT; 
			-- 去掉已删除数据
			DELETE FROM dim_separation_risk WHERE separation_risk_id NOT IN 
			( SELECT t2.id FROM soure_dim_separation_risk t2 WHERE t2.customer_id = customerId); 
	END IF;

END;
-- DELIMITER ;
CALL pro_fetch_dim_separation_risk('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');



#=======================================================pro_fetch_emp_relation=======================================================
-- 'demo','jxzhang'
-- CALL pro_fetch_dim_ability('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19','ability');
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_relation`;
CREATE PROCEDURE pro_fetch_emp_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE ehfId, eId VARCHAR(32);
	DECLARE ehfKey, eKey VARCHAR(20);
	DECLARE uName VARCHAR(60);
	DECLARE uNameCh VARCHAR(5);
	DECLARE effectDate, expiryDate DATETIME;
	DECLARE curEnabled INT;

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;

	DECLARE startTime TIMESTAMP;
	DECLARE exist int;
	DECLARE done INT DEFAULT 0;

	DECLARE s_cur CURSOR FOR
		SELECT 
				IFNULL(e2.emp_id, '-1') ehfId, tb.emp_id eId, tb.emp_hf_key ehfKey, tb.emp_key eKey,  tb.user_name uName, tb.user_name_ch uNameCh,
				tb.effectDate, tb.expiryDate, tb.enabled curEnabled
			FROM (
					SELECT 
						t.customer_id,
						t.emp_hf_key,
						e.emp_id,
						t.emp_key,
						t.effect_date effectDate,
						t.expiry_date expiryDate,
						t.enabled,
						e.user_name,
						e.user_name_ch
					FROM soure_emp_relation t
					LEFT JOIN dim_emp e on e.emp_key = t.emp_key AND t.customer_id = e.customer_id AND now()>=e.effect_date AND e.expiry_date is null and e.enabled = 1
					WHERE t.customer_id = customerId
				) tb
			LEFT JOIN dim_emp e2 on e2.emp_key = tb.emp_hf_key AND tb.customer_id = e2.customer_id AND now()>=e2.effect_date AND e2.expiry_date is null and e2.enabled = 1
			;
	
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN
			ROLLBACK; SHOW ERRORS;
			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
			VALUES(replace(UUID(),'-',''), customerId, optUserId, CONCAT("【员工链-业务表】：", ehfKey, "与", eKey, "数据失败"), startTime, now(), "error");
		END;

		SET startTime = now();

		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO ehfId, eId, ehfKey, eKey, uName, uNameCh, effectDate, expiryDate, curEnabled;

				START TRANSACTION;
				SELECT count(1) AS exist INTO exist FROM emp_relation t
				WHERE t.emp_hf_key = ehfKey AND t.emp_key = eKey AND customer_id = customerId;

				IF exist>0 THEN
					UPDATE emp_relation
					SET effect_date = effectDate, expiry_date = expiryDate, enabled = curEnabled
					WHERE t.emp_hf_key = ehfKey AND t.emp_key = eKey AND customer_id = customerId;
				ELSE
					INSERT INTO emp_relation(emp_relation_id, customer_id, 
																emp_hf_id, emp_id, emp_hf_key, emp_key, user_name, user_name_ch,
																effect_date, expiry_date, enabled)
					VALUES(replace(UUID(),'-',''), customerId, ehfId, eId, ehfKey, eKey, uName, uNameCh, effectDate, expiryDate, curEnabled);
			END IF;
		END WHILE;
		CLOSE s_cur;


END;


#=======================================================pro_fetch_dim_run_off=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_run_off`;
CREATE PROCEDURE pro_fetch_dim_run_off(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【流失维】';
DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_fetch_dim_run_off');
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;


		REPLACE INTO dim_run_off(run_off_id, customer_id, run_off_key, run_off_name, type)
		SELECT id, t2.customer_id, t2.run_off_key, t2.run_off_name, t2.type
			FROM soure_dim_run_off t2
		WHERE	 t2.customer_id = customerId;


		INSERT INTO db_log 
		VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_dim_run_off', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		set @error_message = p_error;  


END;
-- DELIMITER ;

-- CALL pro_fetch_dim_run_off('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');





#=======================================================pro_fetch_run_off_record=======================================================
-- 'demo','jxzhang'
-- 不保存历史
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_run_off_record`;
CREATE PROCEDURE pro_fetch_run_off_record(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN


	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE s_cur CURSOR FOR
					SELECT 
						t2.run_off_id roId,
						t1.emp_id eId,
						t.emp_key eKey,
						t.run_off_key roKey,
						t.where_abouts wAbouts,
						t.run_off_date roDate,
						t.re_hire reHire
					FROM soure_run_off_record t
					INNER JOIN v_dim_emp t1 on t.emp_key = t1.emp_key 
								AND t1.customer_id = t.customer_id 
-- 								AND t1.enabled = 1 AND t1.effect_date <=NOW() AND (t1.expiry_Date IS NULL OR t1.expiry_Date> NOW())
								AND t1.run_off_date IS NULL
					INNER JOIN dim_run_off t2 on t2.run_off_key = t.run_off_key AND t2.customer_id = t.customer_id
					WHERE t.customer_id = customerId;
	

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

		REPLACE INTO run_off_record(
			run_off_record_id,
			customer_id,
			emp_id,
			run_off_id,
			where_abouts,
			run_off_date,
			is_warn,
			re_hire
		)
		SELECT
				id,
				t2.customer_id,
				t1.emp_id,
				t3.run_off_id,
				where_abouts,
				t2.run_off_date,
				0,
				re_hire
		FROM soure_run_off_record t2
		INNER JOIN v_dim_emp t1 on t2.emp_key = t1.emp_key AND t2.customer_id = t1.customer_id
		INNER JOIN dim_run_off t3 on t3.run_off_key = t2.run_off_key AND t3.customer_id = t2.customer_id
		WHERE	 t2.customer_id = customerId;

		-- 同时更新员工的离职时间
		UPDATE soure_v_dim_emp t SET t.run_off_date = (SELECT t2.run_off_date FROM soure_run_off_record t2 WHERE t2.emp_key = t.emp_key);
		UPDATE v_dim_emp t SET t.run_off_date = (SELECT t2.run_off_date FROM soure_run_off_record t2 WHERE t2.emp_key = t.emp_key);

		-- 更新是否有预警
		UPDATE run_off_record t2 , (
				SELECT 
					t.emp_id
				FROM
					dimission_risk t
				INNER JOIN v_dim_emp t1 ON t.emp_id = t1.emp_id
				AND t1.customer_id = t.customer_id
				AND t1.run_off_date >= t.risk_date	-- 流失日期 => 评估时间
-- 				AND t1.enabled = 1 AND t1.effect_date <=NOW() AND (t1.expiry_Date IS NULL OR t1.expiry_Date> NOW())
			) t 
		SET t2.is_warn = 1
		where t2.emp_id = t.emp_id;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  	
	END IF;


END;
-- DELIMITER ;

-- CALL pro_fetch_run_off_record('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');


#=======================================================pro_fetch_monthly_emp_count=======================================================
-- 'demo','jxzhang'
-- CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-08-01', 1)
-- CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-08-31', 0)
-- 必须完成 dim_emp、dim_organization
-- 事件执行时必须先月头再月末参数
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_monthly_emp_count`;
CREATE PROCEDURE pro_fetch_monthly_emp_count(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_nowTime datetime, in p_mBegin_or_mEnd INT(1))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE yearMonth int(6) DEFAULT date_format(DATE_ADD(p_nowTime, Interval 0 minute),'%Y%m');

		-- 这个是月未调用时才用到，作用：月头第一天p_nowTime= '2015-08-31'， firstDay = '2015-08-01'
		DECLARE firstDay DATETIME DEFAULT  DATE_ADD(DATE_ADD(p_nowTime, Interval -1 month), INTERVAL 1 DAY);

		DECLARE be INT(1) DEFAULT p_mBegin_or_mEnd;	
		DECLARE exist INT(1) DEFAULT (SELECT count(1) FROM monthly_emp_count where `YEAR_MONTH` = yearMonth);
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000);
		
		DECLARE beginSUM, endSUM, actCountSum, unActCountSum INT(6);
		DECLARE monthCount, monthCountSum DOUBLE(6, 2);

		
		DECLARE done INT DEFAULT 0;
		DECLARE fullPath VARCHAR(32);
		DECLARE s_cur CURSOR FOR 
				SELECT organization_full_path fullPath from monthly_emp_count 
				WHERE customer_id = customerId AND `YEAR_MONTH` = yearMonth
				ORDER BY `YEAR_MONTH` ASC , organization_full_path ASC
				;

		DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET p_error = 1;

		IF be THEN
			IF exist<=0 THEN
			START TRANSACTION;
-- 				-- 月头的，先操作删除当前月。（这里是为了方便添加假数据先删除再添加使用，生产环境这句是多余的。）
-- 				DELETE FROM monthly_emp_count WHERE `YEAR_MONTH` = yearMonth;

				-- 月初
				INSERT INTO monthly_emp_count(
						monthly_emp_id, customer_id, organization_id, organization_full_path, month_begin, month_p_begin, month_f_begin, `YEAR_MONTH`
				) 
				SELECT replace(UUID(),'-',''), customerId, t.organization_id, t.full_path organization_full_path,
								IFNULL(tt.month_begin,0) month_begin, 
								IFNULL(ttp.month_begin,0) mbp, 
								IFNULL(ttf.month_begin,0) mbf, 
								yearMonth
				FROM dim_organization t
				LEFT JOIN (
					SELECT DISTINCT
						de.organization_id, count(1) month_begin
					FROM v_dim_emp de
					WHERE
						de.customer_id = customerId
-- 2013.05.01 <= 2015-01-01 AND (2015-02-01 > 2015-01-01 or NULL)
-- 作用：找出当前指定时间的在职员工。（员工入职日期少于当前时间，并且员工的离职日期大于当前时间或员工没有离职）
					AND de.entry_date <= p_nowTime AND ( de.run_off_date > p_nowTime or de.run_off_date IS NULL) 

					GROUP BY de.organization_id
				) tt on tt.organization_id = t.organization_id

				LEFT JOIN (
					SELECT DISTINCT
						de.organization_id, count(1) month_begin
					FROM v_dim_emp de
					WHERE
						de.customer_id = customerId
					AND de.entry_date <= p_nowTime AND ( de.run_off_date > p_nowTime or de.run_off_date IS NULL) 
					AND de.passtime_or_fulltime = 'p'
					GROUP BY de.organization_id
				) ttp on tt.organization_id = t.organization_id

				LEFT JOIN (
					SELECT DISTINCT
						de.organization_id, count(1) month_begin
					FROM v_dim_emp de
					WHERE
						de.customer_id = customerId
					AND de.entry_date <= p_nowTime AND ( de.run_off_date > p_nowTime or de.run_off_date IS NULL) 
					AND de.passtime_or_fulltime = 'f'
					GROUP BY de.organization_id
				) ttf on tt.organization_id = t.organization_id

				ORDER BY t.full_path;

			
			OPEN s_cur;
				WHILE done != 1 DO
					FETCH s_cur INTO fullPath;
						-- 月初-子孙
						SELECT sum(month_begin) INTO beginSUM FROM monthly_emp_count t
						INNER JOIN (
							SELECT t1.organization_id FROM v_dim_organization t1 WHERE locate(fullPath, t1.full_path ) AND t1.customer_id = customerId
						) t2 on t.organization_id = t2.organization_id
						WHERE t.`YEAR_MONTH` = yearMonth AND customer_id = customerId
						GROUP BY t.`YEAR_MONTH`;



						UPDATE monthly_emp_count SET month_begin_sum = beginSUM
						WHERE organization_full_path = fullPath AND customer_id = customerId AND `YEAR_MONTH` = yearMonth;
				END WHILE;
			CLOSE s_cur;
		END IF;


		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【月度总人数-", yearMonth, "月初】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'monthly_emp_count.organization_id、month_begin、month_begin_sum、year_month', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				SET p_message =  concat("【月度总人数-", yearMonth, "月初】：数据刷新完成");
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'monthly_emp_count.organization_id、month_begin、month_begin_sum、year_month', p_message, startTime, now(), 'sucess' );
		END IF;

	ELSE-- -------------------------上为月头、下为月末

		START TRANSACTION;
			-- 月末
			REPLACE INTO monthly_emp_count(
					monthly_emp_id, customer_id, organization_id, organization_full_path, 
					month_begin, month_end, month_count, 
					month_begin_sum, month_end_sum, month_count_sum,
					act_count, un_act_count,
					`YEAR_MONTH`) 
			SELECT 
					mec.monthly_emp_id, customerId, tt1.organization_id, tt1.organization_full_path,
					mec.month_begin, tt1.month_end, 0, 
					mec.month_begin_sum,0,0,
					0,0,
					yearMonth
			FROM monthly_emp_count mec
			LEFT JOIN (
					SELECT t.organization_id, customerId, t.full_path organization_full_path,
									IFNULL(tt.month_end,0) month_end, yearMonth
					FROM dim_organization t
					LEFT JOIN (
						SELECT DISTINCT
							de.organization_id,
							count(1) month_end
						FROM
							v_dim_emp de
						WHERE
							de.customer_id = customerId
						AND de.entry_date <= p_nowTime  AND ( de.run_off_date > p_nowTime or de.run_off_date IS NULL) 
						GROUP BY de.organization_id
					) tt on tt.organization_id = t.organization_id
					ORDER BY t.full_path
			) tt1 on tt1.organization_id = mec.organization_id
			WHERE mec.`YEAR_MONTH` = yearMonth
			ORDER BY mec.organization_full_path
			;

			OPEN s_cur;
			WHILE done != 1 DO
				FETCH s_cur INTO fullPath;

				-- 月末-子孙
				SELECT sum(month_end) INTO endSUM FROM monthly_emp_count t
				INNER JOIN (
					SELECT t1.organization_id FROM v_dim_organization t1 WHERE locate(fullPath, t1.full_path ) AND t1.customer_id = customerId
				) t2 on t.organization_id = t2.organization_id
				WHERE customer_id = customerId AND `YEAR_MONTH` = yearMonth;

				UPDATE monthly_emp_count
				SET month_end_sum = endSUM
				WHERE organization_full_path = fullPath AND customer_id = customerId AND `YEAR_MONTH` = yearMonth;
	
				-- 月度总数(本部门，子孙)
				SELECT (month_begin + month_end) / 2 , (month_begin_sum + month_end_sum) / 2 INTO monthCount, monthCountSum 
				FROM monthly_emp_count t WHERE organization_full_path = fullPath AND customer_id = customerId AND `YEAR_MONTH` = yearMonth;
				UPDATE monthly_emp_count
				SET month_count = monthCount, month_count_sum = monthCountSum
				WHERE organization_full_path = fullPath AND customer_id = customerId AND `YEAR_MONTH` = yearMonth;

			END WHILE;
			CLOSE s_cur;

			-- 	流失总人数
			REPLACE INTO monthly_emp_count(
				monthly_emp_id, customer_id, organization_id, organization_full_path, 
				month_begin, month_end, month_count, month_begin_sum, month_end_sum, month_count_sum,
				act_count, un_act_count,
				`YEAR_MONTH`
			)
			SELECT 
					t4.monthly_emp_id, t4.customer_id, t4.organization_id, t4.organization_full_path, 
					t4.month_begin, t4.month_end, t4.month_count, t4.month_begin_sum, t4.month_end_sum, t4.month_count_sum,
					IFNULL(tt.actCount, 0), IFNULL(tt1.unActCount, 0),
					yearMonth
			FROM monthly_emp_count t4 
			LEFT JOIN (
					SELECT count(1) actCount, t2.organization_id FROM run_off_record t1 
					left JOIN v_dim_emp t2 on t2.emp_id = t1.emp_id AND t1.customer_id = t2.customer_id
					left JOIN dim_run_off t3 on t1.run_off_id = t3.run_off_id AND t3.customer_id = t1.customer_id
					where 
						t1.run_off_date BETWEEN firstDay AND p_nowTime 
					AND t3.type = 1
					GROUP BY t2.organization_id
				) tt on tt.organization_id = t4.organization_id
			LEFT JOIN (
					SELECT count(1) unActCount, t2.organization_id FROM run_off_record t1 
					left JOIN v_dim_emp t2 on t2.emp_id = t1.emp_id AND t1.customer_id = t2.customer_id
					left JOIN dim_run_off t3 on t1.run_off_id = t3.run_off_id AND t3.customer_id = t1.customer_id
					where 
						t1.run_off_date BETWEEN firstDay AND p_nowTime 
					AND t3.type = 2
					GROUP BY t2.organization_id
				) tt1 on tt1.organization_id = t4.organization_id
			WHERE t4.`year_month` = yearMonth;


			-- 子孙 流失总人数
			SET done = 0;
			OPEN s_cur;
				WHILE done != 1 DO
					FETCH s_cur INTO fullPath;

						SELECT sum(act_count), sum(un_act_count) INTO actCountSum, unActCountSum  FROM monthly_emp_count t
						INNER JOIN (
							SELECT t1.organization_id FROM v_dim_organization t1 WHERE locate(fullPath, t1.full_path ) AND t1.customer_id = customerId
						) t2 on t.organization_id = t2.organization_id
						WHERE t.`YEAR_MONTH` = yearMonth AND customer_id = customerId
						GROUP BY t.`YEAR_MONTH`
						ORDER BY `YEAR_MONTH` ASC , organization_full_path ASC
						;

						UPDATE monthly_emp_count SET act_count_sum = actCountSum, un_act_count_sum = unActCountSum, 
						WHERE organization_full_path = fullPath AND customer_id = customerId AND `YEAR_MONTH` = yearMonth
						ORDER BY `YEAR_MONTH` ASC , organization_full_path ASC
						;

				END WHILE;
			CLOSE s_cur;


			IF p_error = 1 THEN  
					ROLLBACK;  
					SET p_message = concat("【月度总人数-", yearMonth, "月末】：数据刷新失败。操作用户：", optUserId);
					INSERT INTO db_log 
					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'monthly_emp_count.month_end、month_end_sum、month_count、month_count_sum、act_count、un_act_count', p_message, startTime, now(), 'error' );
			ELSE  
					COMMIT;  
					SET p_message =  concat("【月度总人数-", yearMonth, "月末】：数据刷新完成");
					INSERT INTO db_log 
					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'monthly_emp_count.month_end、month_end_sum、month_count、month_count_sum、act_count、un_act_count', p_message, startTime, now(), 'sucess' );
			END IF;


		END IF;

END;
-- DELIMITER ;

-- CALL pro_fetch_monthly_emp_count('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-12-18', 0);





-- =======================================pro_fetch_job_change============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：dim_emp、dim_organization、dim_position、dim_ability、dim_ability_lv、dim_sequence表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_job_change`;
CREATE PROCEDURE pro_fetch_job_change(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
 	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

			LB_REPLACE:BEGIN	
					REPLACE INTO job_change(emp_job_change_id, customer_id, emp_key,
						change_date, change_type,
						organization_name, position_name, ability_name, job_title_name, ability_lv_name, sequence_name, sequence_sub_name,
						organization_id, position_id, sequence_id, sequence_sub_id, ability_id, job_title_id, ability_lv_id, emp_id, rank_name, note)
					SELECT 
						tt.id,
						tt.customer_id,
						t5.emp_key,
						tt.change_date, tt.change_type,
						tt.organization_name, tt.position_name, tt.ability_name, tt.job_title_name, tt.ability_lv_name, tt.sequence_name, tt.sequence_sub_name,
						t.organization_id, t1.position_id, t2.sequence_id, t6.sequence_sub_id, t3.ability_id, t7.job_title_id, t4.ability_lv_id, t5.emp_id, 
						CONCAT(t2.curt_name, t6.curt_name, t3.curt_name, ".", t4.curt_name), tt.note
					FROM soure_job_change tt
					LEFT JOIN dim_organization t on t.organization_key = tt.organization_key
							AND t.customer_id = tt.customer_id
					LEFT JOIN dim_position t1 on t1.position_key = tt.position_key
							AND t1.customer_id = tt.customer_id
					LEFT JOIN dim_sequence t2 on t2.sequence_key = tt.sequence_key
							AND t2.customer_id = tt.customer_id
					LEFT JOIN dim_sequence_sub t6 on t6.sequence_sub_key = tt.sequence_sub_key
							AND t6.customer_id = tt.customer_id
					LEFT JOIN dim_ability t3 on t3.ability_key = tt.ability_key
							AND t3.customer_id = tt.customer_id
					LEFT JOIN dim_ability_lv t4 on t4.ability_lv_key = tt.ability_lv_key
							AND t4.customer_id = tt.customer_id
					LEFT JOIN v_dim_emp t5 on t5.emp_key = tt.emp_key
							AND t5.customer_id = tt.customer_id
					LEFT JOIN dim_job_title t7 on t7.job_title_key = tt.job_title_key
							AND t7.customer_id = tt.customer_id
					WHERE tt.customer_id = customerId
				;

			END LB_REPLACE;


		IF p_error = 1 THEN  
				ROLLBACK;  
		ELSE  
				COMMIT;
				
		END IF;
 
END;
-- DELIMITER ;

CALL pro_fetch_job_change('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');













-- =======================================pro_fetch_performance_change============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：dim_organization、dim_emp、dim_performance、dim_grade表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_performance_change`;
CREATE PROCEDURE pro_fetch_performance_change(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_nowTime datetime)
BEGIN

	 	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE yearMonth int(6) DEFAULT date_format(DATE_ADD(p_nowTime, Interval 0 minute),'%Y%m');


	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【绩效-历史表】：',yearMonth,'数据刷新完成');
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;


		START TRANSACTION;
			REPLACE INTO performance_change(
						performance_change_id, customer_id, 
						emp_key, emp_id,
						organization_name,  performance_name, grade_name,
						organization_id, performance_id, grade_id,
						organization_parent_id, organization_parent_name,
						position_id, position_name,
						score,
						rank_name,
						`type`,
						`year_month`
						)
			SELECT 
				tt.id, tt.customer_id,
				t2.emp_key, t2.emp_id,
				tt.organization_name, tt.performance_name, tt.grade_name, 
				t2.organization_id, t3.performance_id, t4.grade_id,
				t5.organization_id organization_parent_id, t5.organization_name organization_parent_name,
				t2.position_id, t2.position_name,
				tt.score,
				t7.rank_name,
				tt.`type`,
				tt.`year_month`
			FROM soure_performance_change tt
			INNER JOIN v_dim_emp t2 on t2.emp_key = tt.emp_key
					AND t2.customer_id = tt.customer_id
			INNER JOIN dim_performance t3 on t3.performance_key = tt.performance_key
					AND t3.customer_id = tt.customer_id
			INNER JOIN dim_grade t4 on t4.grade_key = tt.grade_key
					AND t4.customer_id = tt.customer_id
			INNER JOIN dim_organization t5 ON tt.organization_parent_key = t5.organization_key
					AND t5.customer_id = tt.customer_id
			INNER JOIN dim_position t6 ON t6.position_key = tt.position_key
					AND t6.customer_id = tt.customer_id
			INNER JOIN job_change t7 on t7.emp_key = tt.emp_key AND  t7.change_date = (SELECT MAX(change_date) FROM job_change WHERE emp_key= tt.emp_key) 
				AND t7.customer_id = tt.customer_id
			WHERE tt.customer_id = customerId
				AND	tt.`year_month` = yearMonth 
			;

		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【绩效-历史表】：",yearMonth,"数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'performance_change', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'performance_change', p_message, startTime, now(), 'sucess' );
		END IF;


END;
-- DELIMITER ;
SELECT count(1) FROM soure_performance_change;
SELECT count(1) FROM performance_change;
-- DELETE FROM performance_change where `YEAR_MONTH` = 201506
	truncate performance_change;
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2010-06-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2010-12-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2011-06-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2011-12-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2012-06-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2012-12-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-06-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2013-12-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-06-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-12-10'); -- 绩效信息（周期事件）
		CALL pro_fetch_performance_change('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-06-10'); -- 绩效信息（周期事件）


#=======================================================pro_fetch_v_dim_emp=======================================================
DROP PROCEDURE IF EXISTS `pro_fetch_v_dim_emp`;
CREATE PROCEDURE pro_fetch_v_dim_emp(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();	 
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000) DEFAULT '【员工信息表】';  
		DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_fetch_v_dim_emp');

		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS; SET p_error = 1; end;

		START TRANSACTION;
				
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
				contract_unit,work_place_id,work_place,regular_date, /*apply_type,*/ channel_id, speciality, is_regular, refresh_date, c_id, mark
			)
			SELECT 
				REPLACE(UUID(), '-',''), customer_id, emp_id, emp_key, user_name, user_name_ch, emp_hf_id, emp_hf_key,  email, img_path, 
				passtime_or_fulltime, contract, blood, 
				(ROUND(TIMESTAMPDIFF(MONTH, birth_date, CURDATE()) / 12, 2)) AS age, (ROUND(TIMESTAMPDIFF(MONTH, entry_date, CURDATE()) / 12, 2)) AS company_age,
				is_key_talent, sex, nation, degree_id, degree, native_place, country, birth_place, birth_date, 
				national_id, national_type, marry_status, patty_name, 
				position_id, position_name, organization_parent_id, organization_parent_name, organization_id, organization_name,
				sequence_id, sequence_name, sequence_sub_id, sequence_sub_name, performance_id, performance_name, /*key_talent_type_id, key_talent_type_name,*/
				ability_id, job_title_id, ability_lv_id, ability_name, job_title_name, ability_lv_name, 
				-- rank_name,  这个独立一个过程方法计算
				run_off_date, entry_date,
				tel_phone, qq, wx_code, address, 
				contract_unit, work_place_id, work_place, regular_date, /*apply_type, apply_channel,*/ channel_id, speciality, is_regular, startTime, c_id, mark
			FROM soure_v_dim_emp t
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
				contract_unit=t.contract_unit,work_place_id=t.work_place_id,work_place=t.work_place,regular_date=t.regular_date, channel_id=t.channel_id, speciality=t.speciality, is_regular=t.is_regular, refresh_date=startTime, mark=t.mark
			;


			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_v_dim_emp', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		SET @bass_error_mgs = p_error;



END;
-- DELIMITER ;

-- CALL pro_fetch_v_dim_emp('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA');
		
		
#=======================================================pro_fetch_v_dim_emp2=======================================================
DROP PROCEDURE IF EXISTS `pro_fetch_v_dim_emp`;
CREATE PROCEDURE pro_fetch_v_dim_emp(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();
	 
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000) DEFAULT '【员工信息表】：数据刷新完成';  
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS; SET p_error = 1; end;

		START TRANSACTION;
				REPLACE INTO v_dim_emp (
					v_dim_emp_id, customer_id, emp_id, emp_key, user_name, user_name_ch, emp_hf_id, emp_hf_key,  email, img_path, 
					passtime_or_fulltime, contract, blood, age, company_age, is_key_talent, sex, nation, degree, native_place, country, birth_place, birth_date, national_id, national_type,
					marry_status, patty_name, 
					position_id, position_name, organization_parent_id, organization_parent_name, organization_id, organization_name,
					sequence_id, sequence_name, sequence_sub_id, sequence_sub_name, performance_id, performance_name, key_talent_type_id, key_talent_type_name,
					ability_id, job_title_id, ability_lv_id, ability_name, job_title_name, ability_lv_name, rank_name, 
					run_off_date, entry_date,
					tel_phone, qq, wx_code, address, contract_unit, work_place, regular_date, apply_type, apply_channel, speciality, is_regular, refresh_date
				)
				SELECT 
					id, customer_id, emp_id, emp_key, user_name, user_name_ch, emp_hf_id, emp_hf_key,  email, img_path, 
					passtime_or_fulltime, contract, blood, (ROUND(TIMESTAMPDIFF(MONTH, birth_date, CURDATE()) / 12, 2)) AS age, company_age,
					is_key_talent, sex, nation, degree, native_place, country, birth_place, birth_date, national_id, national_type,
					marry_status, patty_name, 
					position_id, position_name, organization_parent_id, organization_parent_name, organization_id, organization_name,
					sequence_id, sequence_name, sequence_sub_id, sequence_sub_name, performance_id, performance_name, key_talent_type_id, key_talent_type_name,
					ability_id, job_title_id, ability_lv_id, ability_name, job_title_name, ability_lv_name, rank_name, 
					run_off_date, entry_date,
					tel_phone, qq, wx_code, address, contract_unit, work_place, regular_date, apply_type, apply_channel, speciality, is_regular, now()
				FROM soure_v_dim_emp
				;

		IF p_error = 1 THEN 
			ROLLBACK;
			SET p_message = concat("【员工信息表】：数据刷新失败。", "操作用户：", optUserId);
			INSERT INTO db_log
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'v_dim_emp', p_message, startTime, now(), 'error' );
		ELSE  
			-- 向上汇报链
				CALL pro_update_report_relation_key(customerId, optUserId);

			-- 我的下属（子孙）
				CALL pro_fetch_underling (customerId, optUserId);


				COMMIT;  
				INSERT INTO db_log  (log_id, customer_id, user_id, module, text, start_time, end_time, type)
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'v_dim_emp', p_message, startTime, now(), 'sucess' );

		END IF;

END;
-- DELIMITER ;

CALL pro_fetch_v_dim_emp('b5c9410dc7e4422c8e0189c7f8056b5f', '3cfd3db15ffc4c119e344e82eb8cbb19');





#=======================================================pro_update_report_relation_key=======================================================
-- 入口：游标把条件带入递归
DROP PROCEDURE IF EXISTS `pro_update_report_relation_key`;
CREATE PROCEDURE pro_update_report_relation_key(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【员工信息表-向上汇报链】：数据刷新完成';  

	DECLARE eKey VARCHAR(20);
	DECLARE peKey VARCHAR(20);

	DECLARE fullPath VARCHAR(10000);
	DECLARE done INT DEFAULT FALSE;
	DECLARE cur CURSOR FOR
		SELECT
			emp_key eKey
		FROM v_dim_emp
		WHERE customer_id = customerId;
		
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET p_error = 1;
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

	OPEN cur;
	WHILE done != 1 DO
		FETCH cur INTO eKey;

		IF NOT done THEN
	
				SET peKey = (SELECT fn_query_parent_emp_hf_key( eKey,  customerId));

				IF peKey = "-1" THEN
					SET fullPath = CONCAT("-1/", eKey);
					UPDATE v_dim_emp SET report_relation = fullPath WHERE emp_key = eKey;
				END IF;

				IF peKey != "-1" THEN

					SET fullPath = CONCAT(peKey, "/", eKey);
					SET @eKey = eKey;
					INSERT INTO tmp_report_relation VALUES(peKey, fullPath, @eKey);

					CALL pro_create_fullPath(customerId, fullPath, peKey, eKey);
				END IF;

		END IF;
		END WHILE;
	CLOSE cur;


		IF p_error = 1 THEN  
			SHOW ERRORS;
			ROLLBACK;
			SET p_message = concat("【员工信息表-向上汇报链】：数据刷新失败。", "操作用户：", optUserId);
			INSERT INTO db_log
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'v_dim_emp.report_relation', p_message, startTime, now(), 'error' );
	 
		ELSE  
				COMMIT;  
				INSERT INTO db_log  (log_id, customer_id, user_id, module, text, start_time, end_time, type)
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'v_dim_emp.report_relation', p_message, startTime, now(), 'sucess' );
			DELETE FROM tmp_report_relation;
		END IF;

END;
-- DELIMITER ;


-- 递归上级
DROP PROCEDURE IF EXISTS `pro_create_fullPath`;
CREATE PROCEDURE pro_create_fullPath(in p_customer_id VARCHAR(32), INOUT p_fullPath VARCHAR(10000), INOUT p_peKey VARCHAR(20),  INOUT p_eKey VARCHAR(20))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;

	DECLARE ppeKey VARCHAR(20) DEFAULT "";
	DECLARE fp VARCHAR(10000) DEFAULT "";

	-- 树的深度不超过10，怎么可能深度出问题，于是百度了一下，网上给了解决方案，加上下面代码就ok：
	SET @@max_sp_recursion_depth = 100;
	
	UPDATE tmp_report_relation SET pid = p_peKey, fullpath = p_fullPath WHERE id = @eKey;

	IF (p_peKey != "-1") THEN
		SET ppeKey = (SELECT DISTINCT t.emp_hf_key FROM v_dim_emp t WHERE t.emp_key = p_peKey AND t.customer_id = customerId);
		SET fp = CONCAT(ppeKey, "/", p_fullPath);

	CALL pro_create_fullPath(customerId, fp, ppeKey, p_peKey);
	
	ELSE
		UPDATE v_dim_emp t SET t.report_relation = p_fullPath WHERE t.emp_key = @eKey;
	END IF;


END;
-- DELIMITER ;

CALL pro_update_report_relation_key('b5c9410dc7e4422c8e0189c7f8056b5f','dba');







-- =======================================pro_fetch_emp_train_experience============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：v_dim_emp
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_train_experience`;
CREATE PROCEDURE pro_fetch_emp_train_experience(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【培训经历-业务表】：数据刷新完成');

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;

			-- 	删除五年前今天以往的数据
			DELETE FROM soure_emp_train_experience 
			WHERE customer_id = customerId
			AND	id in (
					SELECT id FROM (
						SELECT id FROM soure_emp_train_experience t where `finish_date` < DATE_SUB(now(), INTERVAL 5 YEAR)
					) t
			);

			REPLACE INTO emp_train_experience (
				train_experience_id,
				customer_id,
				emp_id,
				course_name,
				start_date,
				finish_date,
				train_time,
				`status`,
				result,
				train_unit,
				gain_certificate,
				lecturer_name,
				note,
				`year`
			)
			SELECT 
				tt.id,
				customerId,
				t.emp_id,
				tt.course_name,
				tt.start_date,
				tt.finish_date,
				tt.train_time,
				tt.`status`,
				tt.result,
				tt.train_unit,
				tt.gain_certificate,
				tt.lecturer_name,
				tt.note,
				tt.`year`
			FROM soure_emp_train_experience tt
			INNER JOIN v_dim_emp t on t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
			WHERE t.customer_id = customerId;

		IF p_error = 1 THEN  
				ROLLBACK;  
		ELSE  
				COMMIT;  

			-- 去掉已删除数据
			DELETE FROM emp_train_experience WHERE train_experience_id NOT IN 
			( SELECT t2.id FROM soure_emp_train_experience t2 WHERE t2.customer_id = customerId);
		END IF;
 
END;
-- DELIMITER ;

-- CALL pro_fetch_emp_train_experience('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');




-- =======================================pro_fetch_emp_past_resume============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：v_dim_emp
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_past_resume`;
CREATE PROCEDURE pro_fetch_emp_past_resume(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
-- 	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
-- 	DECLARE exist int;
-- 	DECLARE startTime TIMESTAMP;

		DELETE FROM emp_past_resume WHERE customer_id = customerId;

		INSERT INTO emp_past_resume(emp_past_resume_id,customer_id, emp_id,
						work_unit,
						department_name,
						position_name,
						bonus_penalty_name,
						witness_name,
						witness_contact_info,
						change_reason,
						entry_date,
						run_off_date)
		SELECT replace(UUID(),'-',''), customerId, emp_id, 
						work_unit,
						department_name,
						tt.position_name,
						bonus_penalty_name,
						witness_name,
						witness_contact_info,
						change_reason,
						tt.entry_date,
						tt.run_off_date
		FROM soure_emp_past_resume tt
		INNER JOIN v_dim_emp t on t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
		WHERE t.customer_id = customerId;

END;
-- DELIMITER ;




-- =======================================pro_fetch_emp_edu============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：v_dim_emp
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_edu`;
CREATE PROCEDURE pro_fetch_emp_edu(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
-- 	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
-- 	DECLARE exist int;
-- 	DECLARE startTime TIMESTAMP;

		DELETE FROM emp_edu WHERE customer_id = customerId;

		INSERT INTO emp_edu(
					emp_edu_id,
					emp_id,
					customer_id,
					school_name,
					major,
					degree,
					academic_degree,
					develop_mode,
					bonus,
					note
		)
		SELECT replace(UUID(),'-',''), customerId, emp_id,
					school_name,
					major,
					tt.degree,
					academic_degree,
					develop_mode,
					bonus,
					note
		FROM soure_emp_edu tt
		INNER JOIN v_dim_emp t on t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
		WHERE t.customer_id = customerId;


END;
-- DELIMITER ;

-- =======================================pro_fetch_emp_family============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：v_dim_emp
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_family`;
CREATE PROCEDURE pro_fetch_emp_family(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
-- 	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
-- 	DECLARE exist int;
-- 	DECLARE startTime TIMESTAMP;

		DELETE FROM emp_family WHERE customer_id = customerId;

		INSERT INTO emp_family(
				emp_family_id,
				customer_id,
				emp_id,
				emp_family_name,
				`call`,
				is_child,
				work_unit,
				department_name,
				position_name,
				tel_phone,
				born_date,
				note
		)
		SELECT replace(UUID(),'-',''), customerId, emp_id,
				emp_family_name,
				`call`,
				is_child,
				work_unit,
				department_name,
				tt.position_name,
				tt.tel_phone,
				born_date,
				note
		FROM soure_emp_family tt
		INNER JOIN v_dim_emp t on t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
		WHERE t.customer_id = customerId;


END;
-- DELIMITER ;


-- =======================================pro_fetch_emp_bonus_penalty============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：v_dim_emp
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_bonus_penalty`;
CREATE PROCEDURE pro_fetch_emp_bonus_penalty(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN


	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【奖惩-业务表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

			REPLACE INTO emp_bonus_penalty(
					emp_bonus_penalty_id, customer_id,
					emp_id,
					bonus_penalty_name,
					type,
					grant_unit,
					witness_name,
					bonus_penalty_date
			)
			SELECT replace(UUID(),'-',''), customerId, emp_id,
					bonus_penalty_name,
					type,
					grant_unit,
					witness_name,
					bonus_penalty_date
			FROM soure_emp_bonus_penalty tt
			INNER JOIN v_dim_emp t on t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
			WHERE t.customer_id = customerId;

	IF p_error = 1 THEN  
			ROLLBACK;  
			SET p_message = concat("【奖惩-业务表】：数据刷新失败。操作用户：", optUserId);
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_bonus_penalty', p_message, startTime, now(), 'error' );
	ELSE  
			COMMIT;  
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_bonus_penalty', p_message, startTime, now(), 'sucess' );
	END IF;

END;
-- DELIMITER ;

-- CALL pro_fetch_emp_bonus_penalty('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');




-- =======================================pro_fetch_emp_prof_title============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：v_dim_emp
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_prof_title`;
CREATE PROCEDURE pro_fetch_emp_prof_title(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
-- 	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
-- 	DECLARE exist int;
-- 	DECLARE startTime TIMESTAMP;

		DELETE FROM emp_prof_title WHERE customer_id = customerId;

		INSERT INTO emp_prof_title(
				emp_bonus_penalty_id,
				customer_id,
				emp_id,
				bonus_penalty_name,
				type,
				type_name,
				grant_unit,
				witness_name
		)
		SELECT replace(UUID(),'-',''), customerId, emp_id,
				gain_date,
				emp_prof_title_name,
				prof_lv,
				award_unit,
				effect_date
		FROM soure_emp_prof_title tt
		INNER JOIN v_dim_emp t on t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
		WHERE t.customer_id = customerId;


END;
-- DELIMITER ;


-- =======================================pro_fetch_emp_contact_person============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：v_dim_emp
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_contact_person`;
CREATE PROCEDURE pro_fetch_emp_contact_person(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【联系人-业务表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

		REPLACE INTO emp_contact_person(
			emp_contact_person_id, customer_id, emp_id,
			emp_contact_person_name,
			tel_phone,
			`call`,
			is_urgent,
			create_time
		)
		SELECT 
			id, customerId, emp_id,
			emp_contact_person_name,
			tt.tel_phone,
			`call`,
			is_urgent,
			create_time
		FROM soure_emp_contact_person tt
		INNER JOIN v_dim_emp t on t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
		WHERE t.customer_id = customerId;

	IF p_error = 1 THEN  
			ROLLBACK;  
			SET p_message = concat("【联系人-业务表】：数据刷新失败。操作用户：", optUserId);
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_contact_person', p_message, startTime, now(), 'error' );
	ELSE  
			COMMIT;  
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_contact_person', p_message, startTime, now(), 'sucess' );
	END IF;


END;
-- DELIMITER ;





-- =======================================pro_fetch_budget_emp_number============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：v_dim_emp
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_budget_emp_number`;
CREATE PROCEDURE pro_fetch_budget_emp_number(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_nowTime datetime)
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
-- 		DECLARE `y` INT(4) DEFAULT date_format(DATE_ADD(p_nowTime, Interval 0 minute), '%Y');

		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_sqlcode	INT DEFAULT 0;  
		DECLARE p_status_message VARCHAR(50) DEFAULT '【编制员工人数表】：数据刷新完成';   	
		DECLARE EXIT HANDLER FOR SQLEXCEPTION 
			BEGIN ROLLBACK; SHOW ERRORS;
				SET p_status_message = concat("【编制员工人数表】：", p_sqlcode, "：数据刷新失败");
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'budget_emp_number', p_status_message, startTime, now(), 'error' );
			END;
	
		START TRANSACTION;
		REPLACE INTO budget_emp_number(
				budget_emp_number_id,
				customer_id,
				organization_id,
				number,
				`year`
		)
		SELECT id,
				tt.customer_id,
				organization_id,
				number,
				`year`
		FROM soure_budget_emp_number tt
		INNER JOIN dim_organization t on t.organization_key = tt.organization_key AND t.customer_id = tt.customer_id
		WHERE t.customer_id = customerId;
		
		-- 机构负责人
		DELETE FROM organization_emp_relation WHERE customer_id = customerId;
		REPLACE INTO organization_emp_relation(
			organization_emp_relation_id,
			customer_id,
			organization_id,
			emp_id
		)
		SELECT replace(UUID(),'-',''),
				tt.customer_id,
				t1.organization_id,
				emp_id
		FROM soure_budget_emp_number tt
		INNER JOIN dim_emp t on t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
		INNER JOIN dim_organization t1 on t1.organization_key = tt.organization_key AND t1.customer_id = tt.customer_id
		WHERE t.customer_id = customerId;

		INSERT INTO db_log 
		VALUES(replace(uuid(), '-',''), customerId, optUserId, 'budget_emp_number', p_status_message, startTime, now(), 'sucess' );


END;
-- DELIMITER ;

CALL pro_fetch_budget_emp_number('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19',now());




-- =======================================pro_fetch_job_relation============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：v_dim_emp
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_job_relation`;
CREATE PROCEDURE pro_fetch_job_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
-- 	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
-- 	DECLARE exist int;
-- 	DECLARE startTime TIMESTAMP;

-- 		DELETE FROM job_relation WHERE customer_id = customerId;

		REPLACE INTO job_relation(
				job_relation_id, customer_id,
				sequence_id,
				sequence_sub_id,
				ability_id,
				ability_lv_id,
				job_title_id,
				`year`,
				type
		)
		SELECT id, customerId,
				t.sequence_id,
				t1.sequence_sub_id,
				t2.ability_id,
				t3.ability_lv_id,
				t4.job_title_id, -- 职衔
				tt.`year`,
				tt.type
		FROM soure_job_relation tt
		left JOIN dim_sequence t on t.sequence_key = tt.sequence_key AND t.customer_id = tt.customer_id
		left JOIN dim_sequence_sub t1 on t1.sequence_sub_key = tt.sequence_sub_key AND t1.customer_id = tt.customer_id
		left JOIN dim_ability t2 on t2.ability_key = tt.ability_key AND t2.customer_id = tt.customer_id
		left JOIN dim_ability_lv t3 on t3.ability_lv_key = tt.ability_lv_key AND t3.customer_id = tt.customer_id
		left JOIN dim_job_title t4 on t4.job_title_key = tt.job_title_key AND t4.customer_id = tt.customer_id
		WHERE t.customer_id = customerId;


END;
-- DELIMITER ;

-- CALL pro_fetch_job_relation( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'); -- 职位关系



-- ============================================pro_fetch_emp_status========================================
DROP PROCEDURE IF EXISTS `pro_fetch_emp_status`;
CREATE PROCEDURE pro_fetch_emp_status(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN


	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【员工出勤情况-业务表】：数据刷新完成');

	-- 本月第一天
	DECLARE firstCurDate DATE DEFAULT (SELECT DATE_SUB(CURDATE(), INTERVAL extract( DAY FROM now())-1 DAY));
	-- 本月最后一天
	DECLARE lastCurDate DATE DEFAULT (SELECT DATE_SUB( DATE_ADD(CURDATE(), INTERVAL 1 MONTH), INTERVAL DAY (CURDATE()) DAY ));

	DECLARE lastYmOneInt int(6) DEFAULT date_format(DATE_ADD( DATE_ADD(firstCurDate,INTERVAL -1 DAY), Interval 0 minute),'%Y%m');


	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;
		-- 月头第一天，清掉过上个月所有数据
		IF(CURDATE() = firstCurDate) THEN
			DELETE FROM emp_status WHERE customer_id = customerId;
		END IF;

		REPLACE INTO emp_status (
			emp_status_id,
			customer_id,
			organization_id,
			emp_id,
			status_type,
			date
		)
		SELECT 
			t.id,
			customerId,
			t.organization_id,
			t1.emp_id,
			t.status_type,
			t.date
		FROM soure_emp_status t 
		INNER JOIN v_dim_emp t1 on t.customer_id = t1.customer_id AND t.emp_key = t1.emp_key
					AND t1.run_off_date IS NULL
		WHERE t.date between firstCurDate AND lastCurDate
		;


		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【员工出勤情况-业务表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_status', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_status', p_message, startTime, now(), 'sucess' );
		END IF;

END;
-- DELIMITER ;

-- 	CALL pro_fetch_emp_status('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');


#=======================================================pro_fetch_underling=======================================================
DROP PROCEDURE IF EXISTS pro_fetch_underling;
CREATE PROCEDURE pro_fetch_underling (in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【我的下属-业务表】：数据刷新完成';

	DECLARE  eHfKey VARCHAR(20);
	DECLARE  eHfId VARCHAR(32);
	
	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
		SELECT DISTINCT emp_hf_id eHfId, emp_hf_key eHfKey FROM v_dim_emp WHERE emp_hf_id is NOT NULL and emp_hf_id != "-1";

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;


	START TRANSACTION;
	DELETE FROM underling where customer_id = customerId;
	OPEN s_cur;
	

		WHILE done != 1 DO
			FETCH s_cur INTO eHfId, eHfKey;
			IF NOT done THEN
				INSERT INTO underling 
						(underling_id, customer_id, 
							emp_id, emp_key, underling_emp_id, underling_emp_key)
				SELECT REPLACE(uuid(), '-',''), customerId,
					eHfId, eHfKey, emp_id, emp_key
				FROM v_dim_emp WHERE locate(eHfKey, report_relation) AND emp_key != eHfKey;
			END IF;
		END WHILE;
		CLOSE s_cur;


		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【我的下属-业务表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'underling', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'underling', p_message, startTime, now(), 'sucess' );
		END IF;

END;


	CALL pro_fetch_underling ('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');


	
#=======================================================pro_fetch_emp_overtime_day=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- 保证 dim_emp 表 完成
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_overtime_day`;
CREATE PROCEDURE pro_fetch_emp_overtime_day(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_now VARCHAR(20) )
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE now TIMESTAMP DEFAULT STR_TO_DATE(p_now, '%Y-%m-%d %H:%i:%S');
	DECLARE startTime TIMESTAMP DEFAULT now;

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【员工加班记录（日）-业务表】：数据刷新完成');


	-- 本月第一天
	DECLARE firstCurDate DATETIME DEFAULT (SELECT DATE_SUB(now, INTERVAL extract( DAY FROM now)-1 DAY));
	-- 本月最后一天
	DECLARE lastCurDate DATE DEFAULT (SELECT DATE_SUB( DATE_ADD(now, INTERVAL 1 MONTH), INTERVAL DAY (now) DAY ));
	-- 本年月格式 如：201510
	DECLARE lastYmInt int(6) DEFAULT date_format(lastCurDate,'%Y%m');


 START TRANSACTION;

		REPLACE INTO emp_overtime_day (
			emp_overtime_day_id,
			customer_id,
			organization_id,
			emp_id,
			emp_key,
			hour_count,
			date
		)
		SELECT 
			t.id,
			customerId,
			t1.organization_id,
			t1.emp_id,
			t.emp_key,
			t.hour_count,
			t.date
		FROM soure_emp_overtime_day t
		INNER JOIN v_dim_emp t1 on t.emp_key = t1.emp_key AND t.customer_id = t1.customer_id
			AND t1.run_off_date IS NULL
		WHERE t.customer_id = customerId AND YEAR(t.date) = YEAR(now) AND MONTH(t.date) = MONTH(now)
		;

		
			IF p_error = 1 THEN  
					ROLLBACK;  
					SET p_message = concat("【员工加班记录-业务表】（日）：数据刷新失败。操作用户：", optUserId);
					INSERT INTO db_log 
					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_overtime_day', p_message, concat(TIMESTAMPDIFF(SECOND,startTime, now),'秒'), startTime, now, 'error' );
			ELSE  
					COMMIT;  
					INSERT INTO db_log 
					VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_overtime_day', p_message, concat(TIMESTAMPDIFF(SECOND,startTime, now),'秒'), startTime, now, 'sucess' );
			END IF;
	 

			-- 如果每月月尾，要把上个月所有值统计保存到emp_overtime;
			IF(now = lastCurDate) THEN

					REPLACE INTO emp_overtime (
						emp_overtime_id,
						customer_id,
						emp_id,
						emp_key,
						hour_count,
						`year_month`
					)
					SELECT 
						replace(uuid(), '-', ''), customerId, emp_id, emp_key, 
						sum(hour_count),
						lastYmInt
					FROM emp_overtime_day 
					WHERE date BETWEEN firstCurDate AND lastCurDate 
					GROUP BY emp_id;


					IF p_error = 1 THEN  
							ROLLBACK;  
							SET p_message = concat("【员工加班记录（月）-业务表】：数据刷新失败。操作用户：", optUserId);
							INSERT INTO db_log 
							VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_overtime', p_message, concat(TIMESTAMPDIFF(SECOND,startTime, now),'秒'), startTime, now, 'error' );
					ELSE  
							COMMIT;  
							SET p_message = CONCAT('【员工加班记录（月）-业务表】：数据刷新完成');
							INSERT INTO db_log 
							VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_overtime', p_message, concat(TIMESTAMPDIFF(SECOND,startTime, now),'秒'), startTime, now, 'sucess' );
					END IF;
			END IF;


		-- 删除一年前数据第天加班记录
		DELETE FROM emp_overtime_day where `date` NOT in (
			select date from (
				SELECT date FROM emp_overtime_day t where DATE_SUB(now, INTERVAL 1 YEAR) < `date`)
			t);
	

END;
-- DELIMITER ;

-- CALL pro_fetch_emp_overtime_day('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-12-18');









-- =======================================pro_fetch_warn_mgr============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证表存在：
-- 	organization_emp_relation
-- 	tmp_stewards_under
-- 	warn_rang
-- 	performance_change
-- 	dim_performance
-- 	emp_status
-- 	emp_overtime_day

-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_warn_mgr`;
CREATE PROCEDURE pro_fetch_warn_mgr(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE yearMonth int(6) DEFAULT date_format(DATE_ADD(now(), Interval 0 minute),'%Y%m');
	DECLARE orgId VARCHAR(32);

	DECLARE stewardsStr, underStr VARCHAR(5000);


	DECLARE roNum INT(4);
	DECLARE hPerNum INT(4);
	DECLARE lPerNum INT(4);
	DECLARE lNGNum INT(4);


-- 	DECLARE hPerLowId, hPerMaxId VARCHAR(32);


	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【管理者首页-预警-业务表】：数据刷新完成');

	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
			SELECT DISTINCT t.organization_id orgId FROM organization_emp_relation t WHERE t.customer_id = customerId;

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;
	-- 删除所有，重新写入。
	TRUNCATE tmp_stewards_under;
	DELETE FROM warn_mgr WHERE customer_id = customerId;
	
		-- 考察绩效周期
		SET @perCyc = (SELECT max_per FROM warn_rang WHERE type = 3);
		SET @perCycBegin = CONCAT(YEAR(DATE_ADD(CURDATE(),INTERVAL -@perCyc YEAR)),"12"); -- 201312
 		SET @perCycEnd = CONCAT(YEAR(DATE_ADD(CURDATE(),INTERVAL -1 YEAR)),"12");			-- 201412
-- 		SET @perCycEnd = (SELECT MAX(`YEAR_MONTH`) FROM performance_change WHERE customer_id = customerId);			-- 201412
		



		-- 考察加班周期
		SET @otCyc = (SELECT max_per FROM warn_rang WHERE type = 6);
		-- 考察周期里的假期数
		SET @holiDayNum = (SELECT count(1) FROM holiday 
										WHERE (type=1 or type=2 or type = 3)
											AND customer_id = customerId
											AND date BETWEEN DATE_ADD(CURDATE(),INTERVAL -@otCyc WEEK)
																	 AND DATE_ADD(CURDATE(),INTERVAL -1 DAY));
		-- 考察周期里的工作天数
		SET @workDayNum = (SELECT count(1) FROM holiday WHERE type=4  AND date BETWEEN DATE_ADD(CURDATE(),INTERVAL -@otCyc WEEK)
																	AND DATE_ADD(CURDATE(),INTERVAL -1 DAY));

		-- 平均加班时长
		SET @avgOtMax = (SELECT max_per FROM warn_rang WHERE type = 7);



	OPEN s_cur;
	WHILE done != 1 DO
		FETCH s_cur INTO orgId;
		IF NOT done THEN

			INSERT INTO tmp_stewards_under(
					organization_id, steward_emp_id, steward_emp_key, under_emp_id, under_emp_key)
			SELECT 
				DISTINCT t.organization_id, 
				t2.emp_id,  t2.emp_key, 
				t1.emp_id,  t1.emp_key
			FROM organization_emp_relation t 
			LEFT JOIN v_dim_emp t2 on t2.emp_id = t.emp_id AND t.customer_id = t2.customer_id AND t2.run_off_date IS NULL		-- 负责人
			LEFT JOIN v_dim_emp t1 on t1.emp_hf_id = t.emp_id AND t.customer_id = t1.customer_id AND t1.run_off_date IS NULL -- 直接下属
			WHERE t.organization_id = orgId;
				


			/*==============================================================*/
			/* 负责人Str、直接下属Str、离职风险Num	                        */
			/*==============================================================*/
					SELECT
								count(ro.emp_id) AS roNum,
								IFNULL(GROUP_CONCAT(DISTINCT t.steward_emp_key), NULL) AS stewardsStr,
								IFNULL(GROUP_CONCAT(DISTINCT t.under_emp_key) , NULL) AS underStr
					INTO roNum, stewardsStr, underStr
					FROM tmp_stewards_under t	
					-- 离职风险
					LEFT JOIN(
						SELECT t1.emp_id FROM dimission_risk t1 WHERE t1.customer_id = customerId
					) ro on ro.emp_id = t.under_emp_id
					WHERE t.organization_id = orgId
					;



			/*==============================================================*/
			/* 高绩没普升Num						                                    */
			/*==============================================================*/
					SELECT count(1) AS hPerNum
					INTO hPerNum
					FROM (
											SELECT t.performance_id, tmp.under_emp_id, t.rank_name
											FROM tmp_stewards_under tmp 			-- 直接下属
											INNER JOIN performance_change t on tmp.under_emp_id = t.emp_id AND  t.customer_id = customerId
													-- 高绩效范围
													AND t.performance_id IN
																				(
																					SELECT per.performance_id FROM dim_performance per
																					INNER JOIN warn_rang t1 on (per.performance_key = t1.min_per OR per.performance_key = t1.max_per) AND t1.type = 4
																					WHERE per.customer_id = customerId
																				)
													AND	t.`year_month` = @perCycBegin
											WHERE tmp.organization_id = orgId
					) tt
					INNER JOIN (
											SELECT t.performance_id, tmp.under_emp_id, t.rank_name
											FROM tmp_stewards_under tmp  			-- 直接下属
											INNER JOIN performance_change t on tmp.under_emp_id = t.emp_id AND  t.customer_id = customerId
													-- 高绩效范围
													AND t.performance_id IN
																				(
																					SELECT per.performance_id FROM dim_performance per
																					INNER JOIN warn_rang t1 on (per.performance_key = t1.min_per OR per.performance_key = t1.max_per) AND t1.type = 4
																					WHERE per.customer_id = customerId
																				)
													AND	t.`year_month` = @perCycEnd
											WHERE tmp.organization_id = orgId
					) ttt
					-- 周期里 高绩没变 职级没变
							on tt.under_emp_id = ttt.under_emp_id and tt.rank_name = ttt.rank_name
					INNER JOIN v_dim_emp t3 
					-- 周期里 职级没变
					on t3.rank_name = tt.rank_name AND t3.emp_id = tt.under_emp_id AND t3.customer_id = customerId
				;


			/*==============================================================*/
			/* 低绩岗位没变Num					                                    */
			/*==============================================================*/
					SELECT count(tt.performance_id) AS lPerNum
					INTO lPerNum
					FROM (
					
						SELECT t.performance_id, tmp.under_emp_id FROM
						tmp_stewards_under tmp					-- 直接下属
						INNER JOIN performance_change t on tmp.under_emp_id = t.emp_id AND  t.customer_id = customerId
								-- 低绩效范围
								AND t.performance_id IN
															(
																SELECT per.performance_id FROM dim_performance per
																INNER JOIN warn_rang t1 on (per.performance_key = t1.min_per OR per.performance_key = t1.max_per) AND t1.type = 5
																WHERE per.customer_id = customerId
															)
								AND	t.`year_month` = @perCycBegin
						WHERE tmp.organization_id = orgId
					) tt
					INNER JOIN 
						(
							SELECT t.performance_id, tmp.under_emp_id  FROM
							tmp_stewards_under tmp 					-- 直接下属
							INNER JOIN performance_change t on tmp.under_emp_id = t.emp_id AND  t.customer_id = customerId
									AND t.performance_id IN
																(
																	SELECT per.performance_id FROM dim_performance per
																	INNER JOIN warn_rang t1 on (per.performance_key = t1.min_per OR per.performance_key = t1.max_per) AND t1.type = 5
																	WHERE per.customer_id = customerId
																)
									AND	t.`year_month` = @perCycEnd
								WHERE tmp.organization_id = orgId 
							) ttt
						-- 周期里 低绩没变
					on tt.under_emp_id = ttt.under_emp_id
						-- 周期里 岗位没变
					WHERE tt.under_emp_id NOT IN (
								SELECT t2.emp_id FROM job_change t2 WHERE  t2.customer_id = customerId and t2.change_type != 3
							)
					;

			/*==============================================================*/
			/* 工作欠皆Num                                                  */
			/*==============================================================*/
					SELECT count(tt.emp_id) AS lNGNum
					INTO lNGNum
					FROM (
								SELECT 
										t.emp_id,
										t.organization_id,
										sum(t.hour_count) AS otHour, -- 加班周期里的加班时长
										@workDayNum AS workDayNum, 						-- 周期里的工作天数
										(IFNULL(sum(hour_count),0) / @workDayNum) AS avgOt	-- （近两周平均加班=近两周加班时数/近两周工作日天数）
								FROM emp_overtime_day t
								INNER JOIN tmp_stewards_under tmp 
												-- 直接下属
												on t.emp_id = tmp.under_emp_id
												and tmp.organization_id = orgId
								WHERE t.customer_id = customerId
									AND t.date BETWEEN DATE_ADD(CURDATE(),INTERVAL -@otCyc WEEK) AND CURDATE()
					 GROUP BY t.emp_id
					) tt
					WHERE tt.avgOt > 4
					;


		
			INSERT INTO warn_mgr (
				warn_mgr_id, customer_id, organization_id,
				steward_emp_key_str, under_emp_key_str, run_off_num,
				high_performance_num, low_performance_num, life_not_good_num
			) VALUES(
				replace(uuid(),'-',''), customerId, orgId, 
				stewardsStr, underStr, roNum,
				hPerNum, lPerNum, lNGNum
			);



		END IF;
	END WHILE;
	CLOSE s_cur;

	
		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【管理者首页-预警-业务表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'warn_mgr', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'warn_mgr', p_message, startTime, now(), 'sucess' );
		END IF;
 


END;
-- DELIMITER ;
-- 		TRUNCATE tmp_stewards_under;
-- 		TRUNCATE warn_mgr;
		CALL pro_fetch_warn_mgr('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');



-- =======================================pro_fetch_dept_per_exam_relation============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：dim_emp、dim_organization、dim_position、dim_ability、dim_ability_lv、dim_sequence表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dept_per_exam_relation`;
CREATE PROCEDURE pro_fetch_dept_per_exam_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
 	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【部门绩效目标-业务表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

					REPLACE INTO dept_per_exam_relation(
						dept_per_exam_relation_id,
						customer_id,
						organization_id,
						content,
						weight,
						progress
					)
					SELECT 
						id,
						customerId,
						organization_id,
						content,
						weight,
						progress
					FROM soure_dept_per_exam_relation
					WHERE customer_id = customerId
				;

		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【部门绩效目标-业务表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dept_per_exam_relation', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dept_per_exam_relation', p_message, startTime, now(), 'sucess' );
		END IF;
 
END;
-- DELIMITER ;
 CALL pro_fetch_dept_per_exam_relation('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');







-- =======================================pro_fetch_emp_per_exam_relation============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：dim_emp、dim_organization、
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_per_exam_relation`;
CREATE PROCEDURE pro_fetch_emp_per_exam_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
 	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【个人绩效目标-业务表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

					REPLACE INTO emp_per_exam_relation(
						emp_per_exam_relation_id,
						customer_id,
						emp_id,
						content,
						sub_content,
						weight,
						progress,
						emp_idp,
						idp
					)
					SELECT 
						id,
						customer_id,
						emp_id,
						content,
						sub_content,
						weight,
						progress,
						emp_idp,
						idp
					FROM soure_emp_per_exam_relation
					WHERE customer_id = customerId
				;

		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【个人绩效目标-业务表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_per_exam_relation', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_per_exam_relation', p_message, startTime, now(), 'sucess' );
		END IF;
 
END;
-- DELIMITER ;

-- CALL pro_fetch_emp_per_exam_relation('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');




-- =======================================pro_fetch_360============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_360`;
CREATE PROCEDURE pro_fetch_360(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
 	

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【360测评模块-业务表】：数据刷新完成');

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

		REPLACE INTO 360_time(
			360_time_id,
			customer_id,
			emp_id,
			report_date,
			report_name,
			path,
			type,
			`YEAR`
		)
		SELECT
			id,
			customerId,
			t.emp_id,
			report_date,
			report_name,
			path,
			type,
			`YEAR`
		FROM soure_360_time tt
		INNER JOIN v_dim_emp t ON t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
		WHERE t.customer_id = customerId ;


		REPLACE INTO 360_report(
			360_report_id,
			customer_id,
			emp_id,
			360_ability_id,
			360_ability_name,
			360_ability_lv_id,
			360_ability_lv_name,
			standard_score,
			gain_score,
			score,
			module_type,
			360_time_id
		)
		SELECT
			id,
			customerId,
			t.emp_id,
			t2.code_item_id 360_ability_id,
			tt.360_ability_name,
			t3.code_item_id 360_ability_lv_id,
			tt.360_ability_lv_name, 
			standard_score,
			gain_score,
			score,
			module_type,
			tt.360_time_id
		FROM soure_360_report tt
		INNER JOIN v_dim_emp t ON t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
		INNER JOIN soure_code_item t2 ON tt.360_ability_key = t2.code_item_key AND tt.customer_id = t2.customer_id
		INNER JOIN soure_code_item t3 ON tt.360_ability_key = t3.code_item_key AND tt.customer_id = t3.customer_id
		WHERE t.customer_id = customerId ;


			IF p_error = 1 THEN  
					ROLLBACK;  
					SET p_message = concat("【360测评模块-业务表】：数据刷新失败。操作用户：", optUserId);
					INSERT INTO db_log 
					VALUES(replace(uuid(), '-',''), customerId, optUserId, '360_time、360_report', p_message, startTime, now(), 'error' );
			ELSE  
					COMMIT;  
					INSERT INTO db_log 
					VALUES(replace(uuid(), '-',''), customerId, optUserId, '360_time、360_report', p_message, startTime, now(), 'sucess' );
			END IF;


 
END;
-- DELIMITER ;

-- CALL pro_fetch_360('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');

-- =======================================pro_fetch_organization_emp_relation============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：dim_emp、dim_organization、dim_position、dim_ability、dim_ability_lv、dim_sequence表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_organization_emp_relation`;
CREATE PROCEDURE pro_fetch_organization_emp_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
 	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【机构负责人-中间表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

					REPLACE INTO organization_emp_relation(
						organization_emp_relation_id,
						customer_id,
						organization_id,
						emp_id, refresh_date)
					SELECT 
						tt.id, customerId,
						t.organization_id, t5.emp_id, startTime
					FROM soure_organization_emp_relation tt
					LEFT JOIN dim_organization t on t.organization_key = tt.organization_key
							AND t.customer_id = tt.customer_id
					LEFT JOIN v_dim_emp t5 on t5.emp_key = tt.emp_key
							AND t5.customer_id = tt.customer_id AND t5.run_off_date IS NULL
				;

		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【机构负责人-中间表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'organization_emp_relation', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'organization_emp_relation', p_message, startTime, now(), 'sucess' );
		END IF;
 
END;
-- DELIMITER ;
-- DELETE FROM organization_emp_relation WHERE customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f';
-- CALL pro_fetch_organization_emp_relation('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');




#=======================================================pro_fetch_organization_bpemp_relation=======================================================
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- 'demo','jxzhang'
--  保证：dim_organization表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_organization_bpemp_relation`;
CREATE PROCEDURE pro_fetch_organization_bpemp_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000) DEFAULT '【机构BP员工-关系表】：数据刷新完成';
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

				LB_REPLACE:BEGIN
					
					REPLACE INTO organization_bpemp_relation(
						organization_bpemp_relation_id,
						customer_id,
						organization_id,
						emp_id,
						refresh_date)
					SELECT 
						tt.id, customerId,
						t.organization_id, t5.emp_id,
						startTime
					FROM soure_organization_bpemp_relation tt
					LEFT JOIN dim_organization t on t.organization_key = tt.organization_key
							AND t.customer_id = tt.customer_id
					LEFT JOIN v_dim_emp t5 on t5.emp_key = tt.emp_key
							AND t5.customer_id = tt.customer_id AND t5.run_off_date IS NULL
				;

				END LB_REPLACE;


	IF p_error = 1 THEN  
			ROLLBACK;  
			SET p_message = concat("【机构BP员工-关系表】：数据刷新失败。操作用户：", optUserId);
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'templet', p_message, startTime, now(), 'error' );
	ELSE  
			COMMIT;  
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'templet', p_message, startTime, now(), 'sucess' );
	END IF;


	
END;
-- DELIMITER ;

-- TRUNCATE TABLE manpower_cost;

CALL pro_fetch_organization_bpemp_relation('b5c9410dc7e4422c8e0189c7f8056b5f','DBA');





-- =======================================pro_fetch_talent_development_exam============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证表存在：
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_talent_development_exam`;
CREATE PROCEDURE pro_fetch_talent_development_exam(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

-- 	DECLARE yQ VARCHAR(6) DEFAULT CONCAT(YEAR(NOW()), 'Q', QUARTER(NOW()));
	DECLARE `year` VARCHAR(6) DEFAULT YEAR(NOW());
	DECLARE eId,orgId VARCHAR(32);
	DECLARE eKey VARCHAR(20);
	DECLARE posName, seqName, seqSubName VARCHAR(20);
	DECLARE ym INT(6);
	DECLARE rTime DATETIME;

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【管理者首页-人才发展(360测评)-业务表】：数据刷新完成');

	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
		SELECT t.emp_id eId, t.emp_key eKey,
			organization_id orgId, position_name posName, sequence_name seqName, sequence_sub_name seqSubName,
			t1.report_date rTime, t1.`year_month` ym
		FROM v_dim_emp t
		INNER JOIN 360_time t1 on t.emp_id = t1.emp_id	
				-- AND ( YEAR(NOW()) = YEAR(t1.report_date) AND QUARTER(NOW()) = QUARTER(t1.report_date)) 
					AND t1.type = 1
					AND t.customer_id = t1.customer_id
		WHERE t.customer_id = customerId AND
			 position_name is not null AND sequence_name is not null AND sequence_sub_name is not null;

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;

	-- 删除所有，重新写入。
		DELETE FROM talent_development_exam WHERE customer_id = customerId AND `year` = `year`;

		OPEN s_cur;
		WHILE done != 1 DO
		FETCH s_cur INTO  eId, eKey, orgId, posName, seqName, seqSubName, rTime , ym;
		IF NOT done THEN

			

			INSERT INTO talent_development_exam(
					talent_development_exam_id, customer_id,
					organization_id, position_name, sequence_name, sequence_sub_name,
					emp_id, emp_key, 
					exam_date, `year`
				)
			SELECT REPLACE(uuid(),'-',''), customerId,
						orgId, posName, seqName, seqSubName,
						eId, eKey, rTime, YEAR(rTime);


--  			IF (@rankName is not null) THEN
-- 				-- 在这里统计是因为只要晋升的人
-- 				SET @pomoteNum = (SELECT count(1) FROM job_change WHERE customer_id = customerId AND emp_id = eId AND change_type = 1 AND  change_date BETWEEN @entryChangeDate AND @changeDate);
-- 
-- 
-- 
-- 				UPDATE talent_development_promote set 
-- 					entry_rank_name = @entryRankName, entry_date = @entryChangeDate,
-- 					rank_name = @rankName, promote_date = @changeDate,
-- 					stay_time = @stayTime
-- 				WHERE emp_id = eId AND year_quarter = yQ
-- 				;
-- 
--  			END IF;


		END IF;
	END WHILE;
	CLOSE s_cur;
		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【管理者首页-人才发展(360测评)-业务表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'talent_development_exam', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'talent_development_exam', p_message, startTime, now(), 'sucess' );
		END IF;
 

END;
-- DELIMITER ;

		CALL pro_fetch_talent_development_exam('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');

		
		
-- =======================================pro_fetch_talent_development_promote============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证表存在：
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_talent_development_promote`;
CREATE PROCEDURE pro_fetch_talent_development_promote(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

-- 	DECLARE yearMonth int(6) DEFAULT date_format(DATE_ADD(now(), Interval 0 minute),'%Y%m');
	DECLARE yQ VARCHAR(6) DEFAULT CONCAT(YEAR(NOW()), 'Q', QUARTER(NOW()));

	DECLARE eId,orgId VARCHAR(32);
	DECLARE eKey VARCHAR(20);
	DECLARE posName, seqName, seqSubName VARCHAR(20);
	DECLARE one int(2);

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【管理者首页-人才发展(晋升)-业务表】：数据刷新完成');

	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
		SELECT emp_id eId, emp_key eKey,
			organization_id orgId, position_name posName, sequence_name seqName, sequence_sub_name seqSubName
		FROM v_dim_emp 
		WHERE customer_id = customerId AND
			 position_name is not null AND sequence_name is not null AND sequence_sub_name is not null;

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;

	-- 删除所有，重新写入。
			DELETE FROM talent_development_promote WHERE customer_id = customerId AND year_quarter = yQ;


	OPEN s_cur;
	WHILE done != 1 DO
		FETCH s_cur INTO  eId, eKey, orgId, posName, seqName, seqSubName;
		IF NOT done THEN

			-- 入职
			SET @entryRankName = (SELECT rank_name FROM job_change WHERE customer_id = customerId AND emp_id = eId  AND change_type = 3 ORDER BY change_date );
			SET @entryChangeDate = (SELECT change_date FROM job_change WHERE customer_id = customerId AND emp_id = eId  AND change_type = 3 ORDER BY change_date );


			-- 现在
			SET @rankName = (SELECT rank_name FROM job_change 
												WHERE customer_id = customerId AND emp_id = eId  AND change_type = 1
												AND ( YEAR(NOW()) = YEAR(change_date) AND QUARTER(NOW()) = QUARTER(change_date)) ORDER BY change_date );
			SET @changeDate = (SELECT change_date FROM job_change
												WHERE customer_id = customerId AND emp_id = eId  AND change_type = 1
												AND ( YEAR(NOW()) = YEAR(change_date) AND QUARTER(NOW()) = QUARTER(change_date)) ORDER BY change_date );

			-- 晋升所花时间
			SET @stayTime = (SELECT TIMESTAMPDIFF(MONTH, @entryChangeDate, @changeDate));


			-- 只要晋升的人
 			IF (@rankName is not null) THEN

				-- 上一次
				SET @rankNameEd = NULL;
				SET @changeDateEd = NULL;
				SELECT count(1) one INTO one FROM job_change WHERE emp_id = eId AND change_type != 5;
				
				IF (one = 1) THEN
					SET @rankNameEd = (SELECT rank_name FROM job_change WHERE emp_id = eId AND change_type != 5);
					SET @changeDateEd = (SELECT change_date FROM job_change WHERE emp_id = eId AND change_type != 5);
				ELSE

					SET @rankNameEd = (SELECT rank_name FROM job_change WHERE  emp_id = eId AND change_type !=5 ORDER BY change_date DESC LIMIT 1,1);
					SET @changeDateEd = (SELECT change_date FROM job_change WHERE  emp_id = eId AND change_type !=5 ORDER BY change_date DESC LIMIT 1,1);
				END IF;

				-- 上一次晋升所花时间
				SET @stayTimeEd = (SELECT TIMESTAMPDIFF(MONTH, @changeDateEd, @changeDate));



				-- 在这里统计是因为只要晋升的人
				SET @pomoteNum = (SELECT count(1) FROM job_change WHERE customer_id = customerId AND emp_id = eId AND change_type = 1 AND  change_date BETWEEN @entryChangeDate AND @changeDate);

				INSERT INTO talent_development_promote(
					talent_development_promote_id, customer_id,
					organization_id, position_name, sequence_name, sequence_sub_name,
					emp_id, emp_key, pomote_num, rank_name_ed, stay_time_ed, year_quarter
				)
				SELECT REPLACE(uuid(),'-',''), customerId,
							orgId, posName, seqName, seqSubName,
							eId, eKey, @pomoteNum, @rankNameEd, @stayTimeEd, yQ;

				UPDATE talent_development_promote set 
					entry_rank_name = @entryRankName, entry_date = @entryChangeDate,
					rank_name = @rankName, promote_date = @changeDate,
					stay_time = @stayTime
				WHERE emp_id = eId AND year_quarter = yQ
				;

 			END IF;


		END IF;
	END WHILE;
	CLOSE s_cur;

	
		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【管理者首页-人才发展(晋升)-业务表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'talent_development', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'talent_development', p_message, startTime, now(), 'sucess' );
		END IF;
 


END;
-- DELIMITER ;

		CALL pro_fetch_talent_development_promote('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');

		
		

-- =======================================pro_fetch_talent_development_train============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证表存在：
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_talent_development_train`;
CREATE PROCEDURE pro_fetch_talent_development_train(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE yQ VARCHAR(6) DEFAULT CONCAT(YEAR(NOW()), 'Q', QUARTER(NOW()));

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【管理者首页-人才发展(培训)-业务表】：数据刷新完成');


	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;

	-- 删除所有，重新写入。
			DELETE FROM talent_development_train WHERE customer_id = customerId AND year_quarter = yQ;

			INSERT INTO talent_development_train(
				talent_development_train_id,
				customer_id,
				emp_id,
				emp_key,
				organization_id, position_name, sequence_name, sequence_sub_name,
				train_time,
				train_num,
				year_quarter
			)
			SELECT 
				REPLACE(uuid(),'-',''), customerId,
				t.emp_id eId, t1.emp_key eKey ,
				t1.organization_id, t1.position_name, t1.sequence_name, t1.sequence_sub_name,
				sum(t.train_time) tTime, count(t.emp_id) tNum,
				yQ
			FROM emp_train_experience t 
			INNER JOIN v_dim_emp t1 on t1.emp_id = t.emp_id and t.customer_id = t1.customer_id
							-- 不包括离职员工
							-- AND t1.run_off_date is null
			WHERE t.customer_id = customerId AND ( YEAR(NOW()) = YEAR(t.start_date) AND QUARTER(NOW()) = QUARTER(t.start_date)) 
			GROUP BY t.emp_id ORDER BY t.train_time;


		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【管理者首页-人才发展(培训)-业务表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'talent_development_train', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'talent_development_train', p_message, startTime, now(), 'sucess' );
		END IF;
 

END;
-- DELIMITER ;

		CALL pro_fetch_talent_development_train('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');







#=======================================================pro_fetch_manpower_cost=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_manpower_cost`;
CREATE PROCEDURE pro_fetch_manpower_cost(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), IN p_datetime DATETIME)
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();	 
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000) DEFAULT '【人力成本-人力成本】'; 
		DECLARE showIndex INTEGER DEFAULT (SELECT show_index from db_db_procedure_info where pro_name = 'pro_fetch_manpower_cost');

	DECLARE pCurDate TIMESTAMP DEFAULT p_datetime;

	-- 本月第一天
	DECLARE firstCurDate DATETIME DEFAULT (SELECT DATE_SUB(pCurDate, INTERVAL extract( DAY FROM pCurDate)-1 DAY));
	-- 本月最后一天
	-- DECLARE lastCurDate DATE DEFAULT (SELECT DATE_SUB( DATE_ADD(pCurDate, INTERVAL 1 MONTH), INTERVAL DAY (pCurDate) DAY ));

	-- 上月第一天
-- 	DECLARE firstCurDate DATE DEFAULT  (SELECT DATE_SUB(DATE_SUB(pCurDate, INTERVAL extract( DAY FROM pCurDate)-1 DAY), interval 1 MONTH));
	-- 上月最后一天
-- 	DECLARE lastCurDate DATE DEFAULT (SELECT DATE_SUB(DATE_SUB(pCurDate, INTERVAL extract( DAY FROM pCurDate)-1 DAY), INTERVAL 1 DAY));

	DECLARE ym INT DEFAULT date_format(pCurDate,'%Y%m');


	DECLARE funId VARCHAR(50);
	DECLARE empRank VARCHAR(50);

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

				DELETE FROM manpower_cost where `year_month` = ym;

				-- 人力成本ID
				SET funId = (SELECT function_id FROM dim_function WHERE function_key = 'RenLiChengBen' and customer_id = customerId );
				-- 配置表，人类型范围
				SET empRank = (SELECT config_val FROM config WHERE config_key = 'RLCB-personType' AND function_id = funId AND customer_id = customerId);


-- TODO empRank = 2，3，4 要带入history_emp_count_month.type 里条件
					REPLACE INTO manpower_cost (
						manpower_cost_id, customer_id, organization_id, cost, emp_num, cost_avg, cost_budget, cost_company, `year_month`
					)
-- 					SELECT 
-- 						t.c_id, customerId, t1.organization_id, t.cost, t2.month_avg_sum AS empSum, t.cost / t2.month_avg_sum AS costAvg, t.cost_budget, t.cost_company, t.`year_month`
-- 					FROM soure_manpower_cost t 
-- 					INNER JOIN dim_organization t1 on  t.organization_key = t1.organization_key and t1.customer_id = t.customer_id
-- 					INNER JOIN history_emp_count_month t2 on t2.organization_id = t1.organization_id AND t2.`year_month` = ym AND t2.customer_id = t1.customer_id
-- 					WHERE t.customer_id = customerId
-- 						AND t.`year_month` = ym;
					SELECT 
						replace(uuid(), '-',''), customerId,
						t1.organization_id, sum(t1.item_cost), 
						t2.month_avg_sum AS empSum, sum(t1.item_cost) / t2.month_avg_sum AS costAvg, 
						sum(t1.item_cost) + (sum(t1.item_cost) * 0.2)	as cost_budget, -- 这里先硬码
						sum(t1.item_cost) + (sum(t1.item_cost) * 0.4) as cost_company,  -- 这里先硬码
						t1.`year_month`
					from manpower_cost_item t1 
					INNER JOIN history_emp_count_month t2 on t2.organization_id = t1.organization_id
																								and t2.type = 1
																								AND t2.`year_month` = ym AND t2.customer_id = t1.customer_id
					WHERE t1.`year_month` = ym and t1.customer_id = customerId
					GROUP BY t1.organization_id
					;

				-- 明年预算
					IF MONTH(pCurDate) = 12 THEN
						REPLACE INTO manpower_cost_value (
							manpower_cost_value_id,
							customer_id,
							organization_id,
							budget_value,
							`year`
						)
						SELECT replace(UUID(), '-', ''), customerId, organization_id, SUM(cost_budget),YEAR(pCurDate)
						FROM manpower_cost t where t.`year_month` like CONCAT(YEAR(pCurDate),'%')
						GROUP BY YEAR(pCurDate), organization_id
						;
					END IF;


		INSERT INTO db_log 
		VALUES(replace(uuid(), '-',''), customerId, optUserId, 'pro_fetch_manpower_cost', p_message, TIMESTAMPDIFF(SECOND, startTime, now()), startTime, now(), p_error , showIndex);	  
		
		IF p_error = 1 THEN ROLLBACK; ELSE COMMIT; END IF;
		SET @RenLiChengBen_error_mgs = p_error;

	
END;
-- DELIMITER ;
-- TRUNCATE TABLE manpower_cost;
-- TRUNCATE TABLE manpower_cost_value;
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-01-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-02-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-03-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-04-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-05-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-06-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-07-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-08-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-09-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-10-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-11-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-12-01');

CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-01-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-02-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-03-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-04-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-05-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-06-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-07-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-08-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-09-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-10-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-11-01');
CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-12-01');

		
		
		


#=======================================================pro_fetch_key_talent=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19','sequence'
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_key_talent`;
CREATE PROCEDURE pro_fetch_key_talent(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【关键人才库-业务表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

			REPLACE INTO key_talent (
				key_talent_id,
				customer_id,
				emp_id,
				key_talent_type_id,
				is_sychron,
				is_delete,
				note,
				create_emp_id,
				refresh_tag1, refresh_tag2, refresh_log, refresh_encourage
			)
			SELECT 
				id,
				customerId,
				t1.emp_id,
				t2.key_talent_type_id,
				1,
				is_delete,
				NULL,
				NULL,
				NULL, NULL, NULL, NULL
			FROM soure_key_talent t
			INNER JOIN v_dim_emp t1 on t1.emp_key = t.emp_key AND t.customer_id = t1.customer_id
			LEFT JOIN dim_key_talent_type t2 on t2.key_talent_type_key = t.key_talent_type_key AND t.customer_id = t2.customer_id
			WHERE t.customer_id = customerId ;

	IF p_error = 1 THEN  
			ROLLBACK;  
			SET p_message = concat("【关键人才库-业务表】：数据刷新失败。操作用户：", optUserId);
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'key_talent', p_message, startTime, now(), 'error' );
	ELSE  
			COMMIT;  
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'key_talent', p_message, startTime, now(), 'sucess' );
	END IF;



END;
-- DELIMITER ;
--	CALL pro_fetch_key_talent('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');



#=======================================================pro_fetch_key_talent_tags_auto=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_key_talent_tags_auto`;
CREATE PROCEDURE pro_fetch_key_talent_tags_auto(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE `exist`, startLimit, endLimit INT;


	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【关键人才自动标签-业务表】：数据刷新完成';

	DECLARE funId, ktId, empId VARCHAR(32);
	DECLARE tmpSt, st VARCHAR(10);
	DECLARE comperLv, lv INT DEFAULT 10000;
	DECLARE funKey VARCHAR(50) DEFAULT 'GuanJianRenCaiKu';	-- 关键人才库
	DECLARE heightAB, heightPerfman VARCHAR(50);

  -- 遍历数据结束标志
	DECLARE done INT DEFAULT 0;
	-- 游标
	DECLARE s_cur CURSOR FOR
					SELECT key_talent_id ktId, emp_id empId
					FROM key_talent WHERE customer_id = customerId AND is_delete = 0;
	
	-- 将结束标志绑定到游标
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;


	START TRANSACTION;

		-- 删除所有
		DELETE FROM key_talent_tags_auto WHERE customer_id = customerId;

		-- 初始化参数
		SET startTime = now(),
				funId = (SELECT function_id FROM dim_function WHERE function_key = funKey AND customer_id = customerId),
				heightAB = (SELECT config_val FROM config WHERE function_id = funId AND config_key = 'GJRCK-heightAB' AND customer_id = customerId),
				heightPerfman = (SELECT config_val FROM config WHERE function_id = funId AND config_key = 'GJRCK-heightPerfman' AND customer_id = customerId)
				;


		OPEN s_cur;
		While1: WHILE done != 1 DO
			FETCH s_cur INTO ktId, empId;
 			IF NOT done THEN
			
						-- 查询教育背景和匹配表是否匹配
						SET `exist` := (SELECT count(1) FROM matching_school WHERE `name` IN (SELECT school_name FROM emp_edu WHERE emp_id = empId));
						IF exist>0 THEN
							-- 模拟next
							SET startLimit := 0, endLimit := 1;

							WHILE exist != 0 DO
								
								SELECT school_type, `level` INTO tmpSt, comperLv FROM matching_school 
								WHERE `name` IN 
									(SELECT school_name FROM emp_edu WHERE emp_id = empId) ORDER BY `level` LIMIT startLimit, endLimit;

								IF comperLv < lv THEN
									SET st := tmpSt, lv := comperLv;
								END IF;

								SET startLimit := startLimit + 1, endLimit := endLimit + 1, exist = exist - 1;
							END WHILE;
						
							INSERT INTO key_talent_tags_auto VALUES ( REPLACE (UUID(), '-', ''), customerId, st, ktId, now());
						END IF;


						-- 高职级
						SET `exist` := (
							SELECT count(1) from job_change 
							where emp_id = empId 
								AND ability_id in ( SELECT ability_id from dim_ability WHERE customer_id = customerId AND ability_key BETWEEN (SELECT LEFT(heightAB,1)) AND (SELECT RIGHT(heightAB,1)) )
								AND customer_id = customerId
						);
						IF exist>0 THEN
							INSERT INTO key_talent_tags_auto VALUES ( REPLACE (UUID(), '-', ''), customerId, CONCAT('高职级 ',exist), ktId, now());
						END IF;

						-- 高绩效
						SET `exist` := (
							SELECT count(1) from performance_change 
							where emp_id = empId 
								AND performance_id in ( SELECT performance_id from dim_performance WHERE customer_id = customerId AND performance_key BETWEEN (SELECT LEFT(heightPerfman,1)) AND (SELECT RIGHT(heightPerfman,1)) )
								AND customer_id = customerId
						);
						IF exist>0 THEN
							INSERT INTO key_talent_tags_auto VALUES ( REPLACE (UUID(), '-', ''), customerId, CONCAT('高绩效 ',exist), ktId, now());
						END IF;

						-- 公司奖励
						SET `exist` := (
							SELECT count(1) from emp_bonus_penalty 
							where emp_id = empId 
								AND type = 1
								AND customer_id = customerId
						);
						IF exist>0 THEN
							INSERT INTO key_talent_tags_auto VALUES ( REPLACE (UUID(), '-', ''), customerId, CONCAT('公司奖励 ',exist), ktId, now());
						END IF;

						-- 入职十周年员工
						SET `exist` := (
							SELECT count(1) from v_dim_emp 
							where emp_id = empId 
								AND 10 <= TIMESTAMPDIFF(YEAR,entry_date, now())
								AND customer_id = customerId
						);
						IF exist>0 THEN
							INSERT INTO key_talent_tags_auto VALUES ( REPLACE (UUID(), '-', ''), customerId, '入职十周年员工 ', ktId, now());
						END IF;
						
						-- 入职五周年员工
						SET `exist` := (
							SELECT count(1) from v_dim_emp 
							where emp_id = empId 
								AND 5 <= TIMESTAMPDIFF(YEAR,entry_date, now())
								AND customer_id = customerId
						);
						IF exist>0 THEN
							INSERT INTO key_talent_tags_auto VALUES ( REPLACE (UUID(), '-', ''), customerId, '入职五周年员工 ', ktId, now());
						END IF;

 			END IF;
		END WHILE While1;
		CLOSE s_cur;


			
	IF p_error = 1 THEN  
			ROLLBACK;  
			SET p_message = concat("【关键人才自动标签-业务表】：数据刷新失败。操作用户：", optUserId);
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'key_talent_tags_auto', p_message, startTime, now(), 'error' );
	ELSE  
			COMMIT;  
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'key_talent_tags_auto', p_message, startTime, now(), 'sucess' );
	END IF;



END;
-- DELIMITER ;
--	CALL pro_fetch_key_talent_tags_auto('b5c9410dc7e4422c8e0189c7f8056b5f','e673c034589448a0bc05830ebf85c4d6');
		
		
#=======================================================pro_fetch_history_emp_count=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19','sequence'
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_history_emp_count`;
CREATE PROCEDURE pro_fetch_history_emp_count(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_cur_date DATETIME)
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE curDate TIMESTAMP DEFAULT p_cur_date;
	
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【主序列-维度表】：数据刷新完成';

	DECLARE done INT DEFAULT 0;
	DECLARE fullPath VARCHAR(32);
	DECLARE organId VARCHAR(32);
	DECLARE cEmp, cPEmp, cFEmp INT(6);

	DECLARE s_cur CURSOR FOR 
			SELECT 	t.organization_id organId, t.full_path fullPath,
							IFNULL(tt.countEmp, 0) cEmp,
							IFNULL(ttp.countPEmp, 0) cPEmp,
							IFNULL(ttf.countFEmp, 0) cFEmp
			FROM dim_organization t
			LEFT JOIN (
				SELECT DISTINCT de.organization_id, count(de.emp_id) countEmp
				FROM v_dim_emp de
				WHERE 1=1
				AND de.entry_date <= curDate AND ( de.run_off_date > curDate or de.run_off_date IS NULL) 
				-- 主岗
				and is_regular = 1
				GROUP BY de.organization_id
			) tt on tt.organization_id = t.organization_id
			LEFT JOIN (
				SELECT DISTINCT de.organization_id, count(de.emp_id) countPEmp
				FROM v_dim_emp de
				WHERE 1=1
				AND de.entry_date <= curDate AND ( de.run_off_date > curDate or de.run_off_date IS NULL) 
				AND de.passtime_or_fulltime = 'p'
				-- 主岗
				and is_regular = 1
				GROUP BY de.organization_id
			) ttp on ttp.organization_id = t.organization_id
			LEFT JOIN (
				SELECT DISTINCT de.organization_id, count(de.emp_id) countFEmp
				FROM v_dim_emp de
				WHERE 1=1
				AND de.entry_date <= curDate AND ( de.run_off_date > curDate or de.run_off_date IS NULL) 
				AND de.passtime_or_fulltime = 'f'
				-- 主岗
				and is_regular = 1
				GROUP BY de.organization_id
			) ttf on ttf.organization_id = t.organization_id
			ORDER BY t.full_path;

	DECLARE s_cur1 CURSOR FOR 
			SELECT DISTINCT t.organization_id organId,  t.full_path fullPath
			FROM history_emp_count t
			;

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;


	START TRANSACTION;
			
			OPEN s_cur;
			FETCH s_cur INTO organId, fullPath, cEmp, cPEmp, cFEmp;

				WHILE done != 1 DO

						INSERT INTO history_emp_count 
									(history_emp_count_id, customer_id, organization_id, full_path, type,emp_count,emp_count_sum,`day`,note)
						VALUES( REPLACE(UUID(),"-",""), customerId, organId, fullPath, 1 , cEmp, 0, curDate, '全部员工（不包括离职）');

						INSERT INTO history_emp_count 
									(history_emp_count_id, customer_id, organization_id, full_path, type,emp_count,emp_count_sum,`day`,note)
						VALUES( REPLACE(UUID(),"-",""), customerId, organId, fullPath, 2 , cFEmp, 0, curDate, '正式员工');

						INSERT INTO history_emp_count 
									(history_emp_count_id, customer_id, organization_id, full_path, type,emp_count,emp_count_sum,`day`,note)
						VALUES( REPLACE(UUID(),"-",""), customerId, organId, fullPath, 3 , cPEmp, 0, curDate, '兼职员工');

					FETCH s_cur INTO organId, fullPath, cEmp, cPEmp, cFEmp;
				END WHILE;
			CLOSE s_cur;
		
			SET done = 0;
			OPEN s_cur1;
			FETCH s_cur1 INTO organId, fullPath;
				WHILE done != 1 DO

					SET @cEmpSum = (SELECT sum(emp_count) FROM history_emp_count
													WHERE organization_id IN (
														SELECT t1.organization_id FROM dim_organization t1
														WHERE locate(fullPath, t1.full_path ) AND t1.customer_id = customerId
													)
													AND type = 1 AND `day` = curDate);

					SET @cPEmpSum = (SELECT sum(emp_count) FROM history_emp_count
													WHERE organization_id IN (
														SELECT t1.organization_id FROM dim_organization t1
														WHERE locate(fullPath, t1.full_path ) AND t1.customer_id = customerId
													) AND type = 2 AND `day` = curDate);
				
					SET @cFEmpSum = (SELECT sum(emp_count) FROM history_emp_count
													WHERE organization_id IN (
														SELECT t1.organization_id FROM dim_organization t1
														WHERE locate(fullPath, t1.full_path ) AND t1.customer_id = customerId
													) AND type = 3 AND `day` = curDate);

					UPDATE history_emp_count SET emp_count_sum = @cEmpSum WHERE type = 1 AND organization_id = organId;			
					UPDATE history_emp_count SET emp_count_sum = @cFEmpSum WHERE type = 2 AND organization_id = organId;			
					UPDATE history_emp_count SET emp_count_sum = @cPEmpSum WHERE type = 3 AND organization_id = organId;

					FETCH s_cur1 INTO organId, fullPath;
				END WHILE;
			CLOSE s_cur1;

--	IF p_error = 1 THEN  
--			ROLLBACK;  
--			SET p_message = concat("【主序列-维度表】：数据刷新失败。操作用户：", optUserId);
--			INSERT INTO db_log 
--			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'history_emp_count', p_message, concat(TIMESTAMPDIFF(MINUTE, startTime,now()), '分'), startTime, now(), 'error' );
--	ELSE  
--			COMMIT;  
--			INSERT INTO db_log 
--			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'history_emp_count', p_message, concat(TIMESTAMPDIFF(MINUTE, startTime,now()), '分'), startTime, now(), 'sucess' );
--	END IF;


END;
-- DELIMITER ;
TRUNCATE TABLE history_emp_count;

	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-01-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-01-31');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-02-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-02-28');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-03-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-03-31');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-04-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-04-30');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-05-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-05-31');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-06-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-06-30');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-07-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-07-31');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-08-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-08-31');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-09-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-09-30');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-11-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-11-30');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-12-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2014-12-31');
	
	

	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-01-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-01-31');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-02-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-02-28');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-03-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-03-31');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-04-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-04-30');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-05-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-05-31');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-06-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-06-30');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-07-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-07-31');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-08-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-08-31');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-09-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-09-30');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-11-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-11-30');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-12-1');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-12-17');
	CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','DBA','2015-12-18');
	
		

#=======================================================pro_fetch_user_organization_relation=======================================================
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- 'demo','jxzhang'
--  保证：dim_organization表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_user_organization_relation`;
CREATE PROCEDURE pro_fetch_user_organization_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【数据权限-关系表】：数据刷新完成';
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

					REPLACE INTO user_organization_relation (
						user_organization_id,
						customer_id,
						user_id,
						organization_id,
						half_check,
						create_user_id,
						create_time
					)
					SELECT 
						id,
						customerId,
						t2.user_id,
						t1.organization_id,
						0,
						optUserId,
						CURDATE()
					FROM soure_user_organization_relation t 
					INNER JOIN dim_organization t1 on t.organization_key = t1.organization_key and t1.customer_id = t.customer_id
					INNER JOIN dim_user t2 on t.user_key = t2.user_key and t2.customer_id = t.customer_id
					WHERE t.customer_id = customerId;

	IF p_error = 1 THEN  
			ROLLBACK;  
			SET p_message = concat("【数据权限-关系表】：数据刷新失败。操作用户：", optUserId);
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_organization_relation', p_message, startTime, now(), 'error' );
	ELSE  
			COMMIT;  
			INSERT INTO db_log 
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'emp_organization_relation', p_message, startTime, now(), 'sucess' );
	END IF;


	
END;
-- DELIMITER ;

CALL pro_fetch_user_organization_relation('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');



































#=======================================================pro_init_fetch_data=======================================================
-- 'demo','jxzhang'
-- 试用说明：---------------------------------------------------
-- 1、保证dim_business_unit数据存在，
-- 2、插入【源表】时，抓取过来的数据要插入cutsomerId、上传上来的数据要插入
-- 2.1、抓取过来的数据cutsomerId获取：在复制数据时指定customerId = (SELECT dc.customer_id FROM dim_customer dc WHERE dc.customer_key = p_customer_key)
-- 2.2、上传上来的数据cutsomerId获取：在java controller层得getCustomerId()：dto.setCustomeId(getCustomerId())
-- 3、第二次之后所有数据有抓取过来的数据xxxKey不能有改动过。因为存在数据更新找不到旧数据，让xxxKey保护与客户连接桥梁。
-- -------------------------------------------------------------
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_init_fetch_data`;
CREATE PROCEDURE pro_init_fetch_data(in p_customer_key VARCHAR(50), in p_user_name VARCHAR(50))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT (SELECT dc.customer_id FROM dim_customer dc WHERE dc.customer_key = p_customer_key);
	DECLARE userId VARCHAR(32) DEFAULT (SELECT du.user_id FROM dim_user du WHERE du.user_name = p_user_name and du.customer_id = customerId);
	DECLARE startTime TIMESTAMP DEFAULT now();

	SELECT customerId, userId, startTime;

	-- 以下的存储过程都会必须用customerId做过滤条件，所以请保证【源表】数据已有customerId
	-- 以下的顺序不能调乱
	-- 维度表
	CALL pro_fetch_dim_ability(customerId, userId, 'ability');	-- 能力
	CALL pro_fetch_dim_ability_lv(customerId, userId,'ability_lv');	-- 职级
	CALL pro_fetch_dim_key_talent_type(customerId, userId, 'keyTalent');	-- 人员类别
	CALL pro_fetch_dim_performance(customerId, userId, 'performance');	-- 绩效
	CALL pro_fetch_dim_grade(customerId, userId,'grade');	-- 等级


	CALL pro_fetch_dim_emp(customerId, 'dba');
	CALL pro_fetch_dim_user(customerId, 'dba');
	
	CALL pro_fetch_dim_role(customerId, userId);
	
  CALL pro_fetch_organization_type(customerId, userId);
  CALL pro_fetch_organization(customerId, userId);
	CALL pro_init_organization('demo');		-- 初始机构，全路径、是否有子节点

	CALL pro_fetch_dim_sequence(customerId, userId, 'sequence');	-- 序列
	CALL pro_fetch_dim_sequence_sub(customerId, userId, 'sequence');	-- 序列
	CALL pro_fetch_dim_position(customerId, userId);
	CALL pro_fetch_emp_position_relation(customerId, userId);

	-- 关系表
	CALL pro_fetch_user_role_relation(customerId, userId);
	CALL pro_fetch_role_organization_relation(customerId, userId);

-- 生产力--------------------------------------------------------------------------
	-- 业务表
	CALL pro_fetch_emp_overtime_day(customerId, userId);	-- 加班时间
	CALL pro_fetch_trade_profit(customerId, userId);	-- 营业利润
	CALL pro_init_factfte(DATE_ADD(now(), Interval -1 month), p_customer_key);	-- 人均效益
-- --------------------------------------------------------------------------------


-- 驱动力--------------------------------------------------------------------------
	
	CALL pro_fetch_dim_separation_risk(customerId, userId);	-- 绩效
	CALL pro_fetch_emp_relation(customerId, userId);		-- 员工链
	CALL pro_fetch_dim_run_off(customerId, userId);  		-- 流失
	CALL pro_fetch_run_off_record(customerId, userId); 	-- 流失记录
		-- TODO 事件
		CALL pro_fetch_monthly_emp_count(customerId, userId, '2015-01-01', 1); -- 月度员工数
		CALL pro_fetch_monthly_emp_count(customerId, userId, '2015-01-31', 0);
		
-- 人才剖像-----------------------------------------------------------------------------
	CALL CALL pro_init_job_change(customeId, userId); -- 工作异动（初始化数据）
	CALL pro_fetch_job_change(customeId, userId); -- 工作异动（每晚事件）
	CALL pro_fetch_performance_change(customeId, userId, '2013-12-10'); -- 工作异动（每晚事件）


-- 业务表-人才剖像--------------------------------------------------------------------------


-- --------------------------------------------------------------------------------

END
-- DELIMITER ;




































#=======================================================pro_update_company_age=======================================================
-- 'demo'
-- 试用说明：---------------------------------------------------
-- 1、保证dim_emp数据存在
DROP PROCEDURE IF EXISTS `pro_update_company_age`;
CREATE PROCEDURE pro_update_company_age(in p_customer_key VARCHAR(50))
BEGIN

	DECLARE eId VARCHAR(32);
	DECLARE eKey VARCHAR(20);
	DECLARE compAge INT(3);

	DECLARE customerId VARCHAR(32) DEFAULT (SELECT dc.customer_id FROM dim_customer dc WHERE dc.customer_key=p_customer_key);

	DECLARE startTime TIMESTAMP;
	DECLARE exist int;
	DECLARE done INT DEFAULT 0;
	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【员工-业务表】：司龄刷新完成'; 

	DECLARE s_cur CURSOR FOR
		SELECT 
				t.emp_id eId, t.emp_key eKey, t.company_age compAge
		FROM v_dim_emp t 
		WHERE t.customer_id = customerId AND now()>= t.effect_date AND t.expiry_date IS NULL AND t.enabled = 1	
		;
	
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET p_error = 1;

		SET startTime = now();

		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO eId, eKey, compAge;
 				START TRANSACTION;

					UPDATE v_dim_emp
					SET company_age = compAge+1
					WHERE emp_id = eId AND emp_key = eKey AND customer_id = customerId;

		END WHILE;
		CLOSE s_cur;


		IF p_error = 1 THEN  
			ROLLBACK; SHOW ERRORS;
			SET p_message = concat("【员工-业务表】：司龄刷新失败。", "操作用户：", optUserId);
			INSERT INTO db_log
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'v_dim_emp.company_age', p_message, startTime, now(), 'error' );
	 
		ELSE  
				COMMIT;  
				INSERT INTO db_log (log_id, customer_id, user_id, module, text, start_time, end_time, type)
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'v_dim_emp.company_age', p_message, startTime, now(), 'sucess' );
		END IF;
END;