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
	CALL pro_fetch_key_talent_tags_auto('b5c9410dc7e4422c8e0189c7f8056b5f','e673c034589448a0bc05830ebf85c4d6');