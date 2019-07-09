#=======================================================pro_fetch_run_off_record=======================================================
-- 'demo','jxzhang'
-- CALL pro_fetch_run_off_record('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19')
-- 条件：
-- 	保证 dim_emp 表已抽取
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_run_off_record`;
CREATE PROCEDURE pro_fetch_run_off_record(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE roId, eId VARCHAR(32);
	DECLARE eKey, roKey VARCHAR(20);
	DECLARE wAbouts VARCHAR(100);
	DECLARE roDate TIMESTAMP;
	DECLARE reHire INT(1);
	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP;
	DECLARE exist, exist2 int;

	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
-- 					SELECT 
-- 						t2.run_off_id roId,
-- 						t1.emp_id eId,
-- 						t.emp_key eKey,
-- 						t.run_off_key roKey,
-- 						t.where_abouts wAbouts,
-- 						t.run_off_date roDate,
-- 						t.re_hire reHire
-- 					FROM soure_run_off_record t
-- 					INNER JOIN dim_emp t1 on t.emp_key = t1.emp_key AND t1.customer_id = t.customer_id
-- 								-- is_post = 0不在职（enable = 1 or 0只代表员工的不同维度关系是否有效）
-- 								 AND t1.is_post = 0
-- 								-- 可能有离职后返聘所要加离职时间做唯一
-- 								 AND t1.run_off_date = t.run_off_date
-- 					INNER JOIN dim_run_off t2 on t2.run_off_key = t.run_off_key AND t2.customer_id = t.customer_id
-- 					WHERE t.customer_id = customerId;
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
	

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
		BEGIN
-- 			ROLLBACK; SHOW ERRORS ;
			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
			VALUES(replace(UUID(),'-',''), customerId, optUserId, CONCAT("【流失记录-业务表】：", eKey, "与" ,roKey, "数据失败"), startTime, now(), "error");
		END;

		SET startTime = now();

		OPEN s_cur;

		WHILE done != 1 DO
			FETCH s_cur INTO roId, eId, eKey, roKey, wAbouts, roDate, reHire;
			IF NOT done THEN	
-- 				START TRANSACTION;

				-- -- 这里使用emp_id。游标集里的dim_emp表条件一致
				SELECT count(1) AS exist INTO exist FROM run_off_record t 
				INNER JOIN dim_emp t1 on t.emp_id = t1.emp_id
							AND t1.customer_id = t.customer_id 
							AND t1.enabled = 1 AND t1.effect_date <=NOW() AND (t1.expiry_Date IS NULL OR t1.expiry_Date> NOW())
				WHERE t.run_off_date = roDate AND t.emp_id = eId AND t.customer_id = customerId;

				IF exist>0 THEN
					UPDATE run_off_record t
					SET where_abouts = wAbouts, re_hire = reHire
					WHERE t.emp_id = eId  AND t.run_off_date = roDate AND customer_id = customerId;
				ELSE
					INSERT INTO run_off_record
					VALUES(replace(UUID(),'-',''), customerId, eId, roId, wAbouts, roDate, 0, reHire);

					-- 更新员工的离职时间
					UPDATE soure_v_dim_emp SET run_off_date = roDate WHERE emp_id = eId AND emp_key = eKey AND customer_id = customerId;
				END IF;
				

			END IF;
			END WHILE;
		CLOSE s_cur;

		-- 更新是否有预警
		UPDATE run_off_record t2 , (
				SELECT 
					t.emp_id
				FROM
					dimission_risk t
				INNER JOIN dim_emp t1 ON t.emp_id = t1.emp_id
				AND t1.customer_id = t.customer_id
				AND t1.run_off_date >= t.risk_date	-- 流失日期 => 评估时间
				AND t1.enabled = 1 AND t1.effect_date <=NOW() AND (t1.expiry_Date IS NULL OR t1.expiry_Date> NOW())
			) t 
		SET t2.is_warn = 1
		where t2.emp_id = t.emp_id;

END;
-- DELIMITER ;

CALL pro_fetch_run_off_record('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19');