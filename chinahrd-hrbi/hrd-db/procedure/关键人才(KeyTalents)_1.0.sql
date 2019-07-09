-- 关键人才自动标签表
drop procedure if exists pro_fetch_key_talent_tags_auto;
CREATE  PROCEDURE `pro_fetch_key_talent_tags_auto`(IN p_customer_id VARCHAR(32), IN p_user_id VARCHAR(32), IN p_refresh VARCHAR(32))
BEGIN 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-关键人才自动标签表】';
	DECLARE funKey VARCHAR(32) DEFAULT 'GuanJianRenCaiKu';
  DECLARE _done,_total,_level INT DEFAULT 0;
  DECLARE _key_talent_id,_emp_id,heightAB,funId,heightPerfman,_school_type VARCHAR(32);
  DECLARE cur CURSOR FOR  SELECT key_talent_id,emp_id FROM key_talent WHERE customer_id = customerId AND is_delete = 0;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET _done = 1;
	
  TRUNCATE TABLE key_talent_tags_auto;
  SET funId = (SELECT function_id  FROM dim_function WHERE function_key = funKey AND customer_id = p_customer_id);
	SET heightAB = (SELECT config_val FROM config WHERE function_id = funId AND config_key = 'GJRCK-heightAB' AND customer_id = p_customer_id);
	SET heightPerfman = (SELECT config_val FROM config WHERE function_id = funId AND config_key = 'GJRCK-heightPerfman' AND customer_id = p_customer_id);
	
	
  OPEN cur;
   WHILE _done = 0 DO 
    FETCH cur INTO _key_talent_id,_emp_id;
    -- 学历
    SET _total =(SELECT COUNT(1) FROM matching_school WHERE `name` IN (SELECT school_name FROM emp_edu WHERE emp_id = _emp_id)); 
    WHILE _total != 0 DO 
      SELECT school_type,`level` INTO _school_type,_level FROM (SELECT school_type, `level`,@rn:=@rn+1 rn  FROM matching_school a,(SELECT @rn:=0) b WHERE `name` IN (SELECT school_name FROM emp_edu WHERE emp_id = _emp_id) ORDER BY  school_type) a WHERE rn= _total;
      SET _total = _total -1;
      INSERT INTO key_talent_tags_auto VALUES ( REPLACE (UUID(), '-', ''), p_customer_id, _school_type, _key_talent_id, p_refresh); 
    END WHILE;   
   -- 高职级
   SET _total =(SELECT COUNT(1) FROM job_change WHERE emp_id = _emp_id AND ability_id IN ( SELECT ability_id FROM dim_ability WHERE customer_id = customerId AND ability_key BETWEEN (SELECT LEFT(heightAB,1)) AND (SELECT RIGHT(heightAB,1)))AND customer_id = p_customer_id);
   IF _total > 1 THEN 
     INSERT INTO key_talent_tags_auto VALUES ( REPLACE (UUID(), '-', ''), p_customer_id, CONCAT('高职级 ',_total), _key_talent_id, p_refresh);
	 END IF; 
	 -- 高绩效				
	 SET _total =(SELECT COUNT(1) FROM performance_change WHERE emp_id = _emp_id AND performance_id IN ( SELECT performance_id FROM dim_performance WHERE customer_id = p_customer_id AND performance_key BETWEEN (SELECT LEFT(heightPerfman,1)) AND (SELECT RIGHT(heightPerfman,1))) AND customer_id = p_customer_id);
	 IF _total>0 THEN
		 INSERT INTO key_talent_tags_auto VALUES (REPLACE (UUID(), '-', ''), p_customer_id, CONCAT('高绩效 ',_total), _key_talent_id, p_refresh);
	 END IF;
   -- 公司奖励
	 SET _total =(SELECT COUNT(1) FROM emp_bonus_penalty WHERE emp_id = _emp_id 	AND TYPE = 1	AND customer_id = p_customer_id);
   IF _total>0 THEN
		 INSERT INTO key_talent_tags_auto VALUES ( REPLACE (UUID(), '-', ''), p_customer_id, CONCAT('公司奖励 ',_toal), _key_talent_id, p_refresh);
	 END IF;
  					
		-- 入职十周年员工		
		SET _total =(SELECT COUNT(1) FROM v_dim_emp 	WHERE emp_id = _emp_id AND 10 <= TIMESTAMPDIFF(YEAR,entry_date, p_refresh)AND customer_id = p_customer_id);
		IF _total>0 THEN
			INSERT INTO key_talent_tags_auto VALUES ( REPLACE (UUID(), '-', ''), p_customer_id, '入职十周年员工 ', _key_talent_id, p_refresh);
		END IF;
						
		-- 入职五周年员工
		SET _total =(SELECT COUNT(1) FROM v_dim_emp WHERE emp_id = _emp_id AND 5 <= TIMESTAMPDIFF(YEAR,entry_date, p_refresh) AND customer_id = p_customer_id);
		IF _total>0 THEN
			INSERT INTO key_talent_tags_auto VALUES ( REPLACE (UUID(), '-', ''), customerId, '入职五周年员工 ', _key_talent_id, p_refresh);
		END IF;
 
END WHILE; 
END;


-- 是否关键人才 *********为了加快连接速度，建议在创建临时表时添加索引“idx_empid”
DROP PROCEDURE IF EXISTS `pro_update_key_talent`;

CREATE PROCEDURE `pro_update_key_talent`(IN p_customer_id VARCHAR(32), IN p_user_id VARCHAR(32), IN p_refresh VARCHAR(32))
BEGIN
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE p_message VARCHAR(10000) DEFAULT '【员工信息表-是否是关键人才】';
		DECLARE refresh TIMESTAMP DEFAULT p_refresh;
		DECLARE dels, adds, updates INTEGER DEFAULT 0;
			
		DROP TABLE IF EXISTS tmp_kt_self_TT;
		DROP TABLE IF EXISTS tmp_kt_del_TT;
		DROP TABLE IF EXISTS tmp_kt_add_TT;
		DROP TABLE IF EXISTS tmp_kt_update_TT;
		CREATE TABLE tmp_kt_self_TT AS
		SELECT t.emp_id FROM key_talent t WHERE t.is_delete = 0 AND t.is_sychron = 1 AND t.customer_id = customerId;
		
		CREATE INDEX tmp_kt_self_ind ON tmp_kt_self_TT(emp_id);
		-- 源表：这人的关键类型为null。关键人才表：存在。
		CREATE TABLE tmp_kt_del_TT AS
		SELECT t.emp_id, key_talent_type_key
		FROM `mup-source`.source_key_talent t
		INNER JOIN tmp_kt_self_TT t1 ON t1.emp_id = t.emp_id
		WHERE t.key_talent_type_key IS NULL OR t.key_talent_type_key = '';
		
		CREATE INDEX tmp_kt_del_ind ON tmp_kt_del_TT(emp_id);
		-- 源表：添加了新的关键人才。关键人才表：不在关键人才表里的人，如果在就用重复添加。
		CREATE TABLE tmp_kt_add_TT AS
		SELECT t.emp_id, key_talent_type_key
		FROM `mup-source`.source_key_talent t
		WHERE t.key_talent_type_key IS NOT NULL AND t.key_talent_type_key != '' AND t.emp_id
			NOT IN (SELECT emp_id FROM key_talent WHERE customer_id = p_customer_id);
			
		CREATE INDEX tmp_kt_add_ind ON tmp_kt_add_TT(emp_id);
		-- 源表：曾经是关键人才的人。关键人才表：存在过的人或者是同步过来的人。
		CREATE TABLE tmp_kt_update_TT AS
		SELECT t.emp_id, t.key_talent_type_key
		FROM `mup-source`.source_key_talent t
		INNER JOIN key_talent t1 ON t.emp_id = t1.emp_id 
		WHERE (t1.is_delete = 1 OR t1.is_sychron = 0 ) AND t.customer_id = t1.customer_id ;
		
		CREATE INDEX tmp_kt_update_ind ON tmp_kt_update_TT(emp_id);
		-- 源表：上次是关键人才这次关键人才值变更。关键人才表：存在的人并且是同步过来的人。
		INSERT INTO tmp_kt_update_TT
		SELECT t.emp_id, t.key_talent_type_key
		FROM `mup-source`.source_key_talent t
		INNER JOIN tmp_kt_self_TT t1 ON t1.emp_id = t.emp_id
		WHERE t.key_talent_type_key IS NOT NULL OR t.key_talent_type_key != '';
		SET dels = (SELECT COUNT(1) FROM tmp_kt_del_TT);
		SET adds = (SELECT COUNT(1) FROM tmp_kt_add_TT);
		SET updates = (SELECT COUNT(1) FROM tmp_kt_update_TT);
		
		IF dels > 0 THEN
			UPDATE v_dim_emp t INNER JOIN tmp_kt_del_TT t1 ON t.emp_id = t1.emp_id SET t.is_key_talent=0;
			UPDATE key_talent t INNER JOIN tmp_kt_del_TT t1 ON t.emp_id = t1.emp_id SET t.is_delete=1;
		END IF;
		
		IF adds > 0 THEN
			UPDATE v_dim_emp t INNER JOIN tmp_kt_add_TT t1 ON t.emp_id = t1.emp_id SET t.is_key_talent=1;
			INSERT INTO key_talent (
				key_talent_id,
				customer_id,
				emp_id,
				key_talent_type_id,
				is_sychron,
				is_delete,
				note,
				create_emp_id,
				create_time,
				modity_encourage_emp_id,  refresh_tag1, refresh_tag2, refresh_log, refresh_encourage
			) 
			SELECT fn_getId(), customerId, emp_id, t1.key_talent_type_id,1,0,NULL, optUserId, 
						 NULL,  NULL, NULL, NULL, NULL
			FROM tmp_kt_add_TT t 
			INNER JOIN dim_key_talent_type t1 ON t.key_talent_type_key = t1.key_talent_type_key;
		END IF;
		IF updates > 0 THEN
			UPDATE key_talent t 
			INNER JOIN tmp_kt_del_TT t1 ON t.emp_id = t1.emp_id 
			INNER JOIN dim_key_talent_type t2 ON t1.key_talent_type_key = t2.key_talent_type_key
			SET t.key_talent_type_id = t2.key_talent_type_id, t.is_delete=0, is_sychron = 1;
		END IF;
		DROP TABLE IF EXISTS tmp_kt_self_TT;
		DROP TABLE IF EXISTS tmp_kt_del_TT;
		DROP TABLE IF EXISTS tmp_kt_add_TT;
		DROP TABLE IF EXISTS tmp_kt_update_TT;
END;


