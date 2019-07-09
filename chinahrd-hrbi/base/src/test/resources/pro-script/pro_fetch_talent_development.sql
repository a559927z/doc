-- =======================================pro_fetch_talent_development============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证表存在：


-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_talent_development`;
CREATE PROCEDURE pro_fetch_talent_development(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE yearMonth int(6) DEFAULT date_format(DATE_ADD(now(), Interval 0 minute),'%Y%m');
	DECLARE eId VARCHAR(32);
	DECLARE rName VARCHAR(20);

	DECLARE rNameEd VARCHAR(5);

	DECLARE cDate DATETIME;


	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT CONCAT('【管理者首页-人才发展-业务表】：数据刷新完成');

	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
			SELECT  t.rank_name rName, MAX(change_date) cDate, t.emp_id eId FROM job_change t 
			WHERE t.change_type != 5 AND t.customer_id = customerId GROUP BY t.emp_id
			;


	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;

	-- 删除所有，重新写入。
			DELETE FROM talent_development WHERE customer_id = customerId;


			/*==============================================================*/
			/* 机构、员工key、岗位、主子序列、职位层职	                        */
			/*==============================================================*/
					INSERT INTO talent_development (
							talent_development_id, customer_id,
							organization_id, emp_key,emp_id,position_id,position_name,sequence_id,sequence_name,sequence_sub_id,sequence_sub_name,ability_id,ability_name,
							entry_date, promote_date, 
							rank_name, rank_name_ed, stay_time,
							train_num, train_time,
							ability_match, exam_date
					) 
					SELECT 
						replace(uuid(),'-',''), customerId,
						t.organization_id, t.emp_key, t.emp_id, t.position_id, t.position_name,
						t.sequence_id, t.sequence_name, t.sequence_sub_id, t.sequence_sub_name, t.ability_id, t.ability_name,
						t.entry_date, 
						NULL, -- promote_date -- (SELECT change_date FROM job_change WHERE emp_id = t.emp_id ORDER BY change_date desc LIMIT 1, 1),
						t.rank_name, 
						NULL, -- rank_name_ed -- (SELECT rank_name FROM job_change WHERE emp_id = t.emp_id ORDER BY change_date desc LIMIT 1, 1),
						0,
						0, NULL,
						0, NULL
					FROM v_dim_emp t
					
					;

	OPEN s_cur;
	WHILE done != 1 DO
		FETCH s_cur INTO rName, cDate, eId;
		IF NOT done THEN

			SET @rName = (SELECT rank_name FROM job_change WHERE emp_id = eId ORDER BY change_date desc LIMIT 1, 1);
			SET @cDate = (SELECT change_date FROM job_change WHERE customer_id = customerId AND emp_id = eId ORDER BY change_date desc LIMIT 1, 1);


			SET @abilityLv = (SELECT SUBSTRING(rName FROM 3));
			SET @abilityLvEd = (SELECT SUBSTRING(@rName FROM 3));

			SET rNameEd = NULL;

			IF 4 > 3 THEN 
				SET rNameEd = @rName;
			END IF;

			UPDATE talent_development 
			SET promote_date = @cDate,
					rank_name_ed = rNameEd,
					stay_time = (SELECT TIMESTAMPDIFF(MONTH, @cDate, cDate) / 12 )
			WHERE emp_id = eId;


		END IF;
	END WHILE;
	CLOSE s_cur;

	
		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【管理者首页-人才发展-业务表】：数据刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'talent_development', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'talent_development', p_message, startTime, now(), 'sucess' );
		END IF;
 


END;
-- DELIMITER ;
-- 		TRUNCATE tmp_stewards_under;
-- 		TRUNCATE warn_mgr;
		CALL pro_fetch_talent_development('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');
