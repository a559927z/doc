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
