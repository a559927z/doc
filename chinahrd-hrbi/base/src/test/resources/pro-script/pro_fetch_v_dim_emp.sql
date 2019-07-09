#=======================================================pro_fetch_v_dim_emp=======================================================
-- DROP VIEW IF EXISTS v_dim_emp;
DROP TABLE IF EXISTS v_dim_emp;
CREATE TABLE v_dim_emp (
	v_dim_emp_id VARCHAR (32) NOT NULL,
	`customer_id` VARCHAR (32) DEFAULT NULL COMMENT '客户ID',
	`emp_id` VARCHAR (32) NOT NULL COMMENT '员工ID',
	`emp_key` VARCHAR (20) DEFAULT NULL,
	`user_name` VARCHAR (20) DEFAULT NULL,
	`user_name_ch` VARCHAR (5) DEFAULT NULL,
	`emp_hf_id` VARCHAR (32) NULL COMMENT '上级ID',
	`emp_hf_key` VARCHAR (20) DEFAULT NULL COMMENT '上级key',
	 emp_relation_key VARCHAR(255) DEFAULT NULL COMMENT '汇报连',
	 email VARCHAR (100) DEFAULT NULL,
	`img_path` VARCHAR (200) DEFAULT NULL,
	`passtime_or_fulltime` VARCHAR (1) DEFAULT NULL COMMENT 'p兼职，f全职',
	`contract` VARCHAR (20) DEFAULT NULL COMMENT '劳动合同（全)，劳动合同（非全），劳务派遣',
	`blood` VARCHAR (2)  DEFAULT NULL COMMENT '血型',
	`age` INT (3) DEFAULT NULL COMMENT '年龄',
	`company_age` INT (3) UNSIGNED ZEROFILL DEFAULT NULL COMMENT '司龄(月)',
	`is_key_talent` INT (1) UNSIGNED ZEROFILL NOT NULL DEFAULT '0' COMMENT '是否关键人才 1是，0否',
	`sex` VARCHAR (3) DEFAULT NULL COMMENT '性别',
	`nation` VARCHAR (9) DEFAULT NULL COMMENT '民族',
	`degree` VARCHAR (9) DEFAULT NULL COMMENT '学历',
	`native_place` VARCHAR (90) DEFAULT NULL COMMENT '籍贯',
	`country` VARCHAR (20) DEFAULT NULL COMMENT '国籍',
	`birth_place` VARCHAR (90) DEFAULT NULL COMMENT '出生地',
	`birth_date` datetime DEFAULT NULL COMMENT '出生日期',
	`national_id` VARCHAR (60) DEFAULT NULL COMMENT '证件号码',
	`national_type` VARCHAR (60) DEFAULT NULL COMMENT '证件类型',
	`marry_status` INT (1) UNSIGNED ZEROFILL NOT NULL COMMENT '婚姻状况',
	`patty_name` VARCHAR (20) DEFAULT NULL COMMENT '政治面貌',
	position_id VARCHAR (32),
	position_name VARCHAR (60) DEFAULT NULL COMMENT '角色名称',
	organization_parent_id VARCHAR (32),
	organization_parent_name VARCHAR (60),
	organization_id VARCHAR (32),
	organization_name VARCHAR (60),
	sequence_id VARCHAR (32),
	sequence_name VARCHAR (60),
	sequence_sub_id VARCHAR (32),
	sequence_sub_name VARCHAR (60),
	performance_id VARCHAR (32),
	performance_name VARCHAR (20),
	key_talent_type_id VARCHAR (32),
	key_talent_type_name VARCHAR(60),
	ability_id VARCHAR (32),
	job_title_id VARCHAR (32),
	ability_lv_id VARCHAR (32),
	ability_name VARCHAR (60),
	job_title_name VARCHAR (20),
	ability_lv_name VARCHAR (60),
	rank_name VARCHAR (5),
	run_off_date datetime,
	entry_date datetime,
	tel_phone VARCHAR (11),
	qq VARCHAR (20),
	wx_code VARCHAR (32),
	address VARCHAR (60),
	contract_unit VARCHAR (60),
	work_place VARCHAR (60),
	regular_date datetime,
	apply_type VARCHAR (32),
	apply_channel VARCHAR (32),
	speciality VARCHAR (60),
	is_regular VARCHAR (20),
	refresh_date datetime,
	PRIMARY KEY (v_dim_emp_id)
);
-- DROP INDEX IF EXISTS index_eKey ON v_dim_emp;
-- ALTER TABLE `v_dim_emp` ADD INDEX index_eKey ( `emp_key` );



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
-- 				CALL pro_update_report_relation_key(customerId, optUserId);

			-- 我的下属（子孙）
-- 				CALL pro_fetch_underling (customerId, optUserId);


				COMMIT;  
				INSERT INTO db_log  (log_id, customer_id, user_id, module, text, start_time, end_time, type)
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'v_dim_emp', p_message, startTime, now(), 'sucess' );

		END IF;

END;
-- DELIMITER ;

CALL pro_fetch_v_dim_emp('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA');



#=======================================================pro_update_report_relation_key=======================================================


-- 查出上级
DROP FUNCTION IF EXISTS mup.fn_query_parent_emp_hf_key;   
CREATE FUNCTION mup.fn_query_parent_emp_hf_key(p_eKey VARCHAR(20), p_customerId VARCHAR(32)) 
RETURNS VARCHAR(2000) CHARSET utf8   
BEGIN     
  DECLARE eKey VARCHAR(20) DEFAULT p_eKey;
	DECLARE customerId VARCHAR(32) DEFAULT p_customerId;
	RETURN (SELECT DISTINCT t.emp_hf_key FROM v_dim_emp t WHERE t.emp_key = eKey AND t.customer_id = customerId);
END;
	-- 创建临时表
	DROP TABLE IF EXISTS tmp_report_relation;
	CREATE TABLE `tmp_report_relation` (
		`pid` varchar(20) DEFAULT NULL,
		`fullPath` varchar(1000) DEFAULT NULL,
		`id` varchar(20) DEFAULT NULL
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;



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








