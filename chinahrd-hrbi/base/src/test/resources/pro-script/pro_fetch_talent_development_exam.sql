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
	DECLARE eId,orgId,tId VARCHAR(32);
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
			t1.360_time_id tId,
			t1.report_date rTime, t1.`year` ym
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
		FETCH s_cur INTO  eId, eKey, orgId, posName, seqName, seqSubName, tId, rTime , ym;
		IF NOT done THEN

			-- 匹配度=达标的能力项数/要求的总项数*100%

			SET @okCount = (SELECT count(1) FROM 360_report t WHERE t.360_time_id = tId AND t.gain_score >= t.standard_score);
			SET @itemCount = (SELECT count(1) FROM 360_report t WHERE t.360_time_id = tId);
			SET @abMatch = @okCount / @itemCount;
			

			INSERT INTO talent_development_exam(
					talent_development_exam_id, customer_id,
					organization_id, position_name, sequence_name, sequence_sub_name,
					emp_id, emp_key,
					ability_match,
					exam_date, `year` 
				)
			SELECT REPLACE(uuid(),'-',''), customerId,
						orgId, posName, seqName, seqSubName,
						eId, eKey, @abMatch, rTime, YEAR(rTime);



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
