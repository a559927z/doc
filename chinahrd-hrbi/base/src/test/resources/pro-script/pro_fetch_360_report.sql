-- =======================================pro_fetch_360_report============================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：dim_emp、dim_organization、dim_position、dim_ability、dim_ability_lv、dim_sequence表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_360_report`;
CREATE PROCEDURE pro_fetch_360_report(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
 	
	DECLARE report_date DATETIME;
	DECLARE report_name VARCHAR(20);
	DECLARE path VARCHAR(100);
	DECLARE type INT(1);
	DECLARE emp_key VARCHAR(20);
	DECLARE emp_id VARCHAR(32);

	DECLARE timeId VARCHAR(32);
	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE exist int;
	DECLARE startTime TIMESTAMP;

  -- 遍历数据结束标志
	DECLARE done INT DEFAULT 0;
	-- 游标
	DECLARE s_cur CURSOR FOR
					SELECT 
						report_date, report_name, t.emp_id, tt.emp_key, type
					FROM soure_360_report tt
					INNER JOIN v_dim_emp t ON t.emp_key = tt.emp_key AND t.customer_id = tt.customer_id
					WHERE t.customer_id = customerId
					GROUP BY report_date;
	
	-- 将结束标志绑定到游标
-- 	DECLARE CONTINUE HANDLER FOR NOT FOUND SET SET done = 1;
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN
			ROLLBACK; SHOW ERRORS;
			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
			VALUES(replace(UUID(),'-',''), customerId, optUserId, CONCAT("【层级-维度表】：",ciKey, "数据失败"), startTime, now(), "error");
		END;

		SET startTime = now();

		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO report_date, report_name, path, emp_id, emp_key, type;
				
				START TRANSACTION;
				SELECT count(1) as exist into exist FROM 360_time
				WHERE report_date = report_date, emp_id = emp_id AND AND customer_id = customerId;

				IF exist>0 THEN
					-- 更新360_time的报告名、下载路径

					IF type = 1 THEN
						SET tempId = replace(UUID(),'-','');
						INSERT INTO 360_time
						VALUES(tempId, customerId, emp_id, report_date, report_name, path, type);

						INSERT INTO 360_report(replace(UUID(),'-',''), customerId, emp_id, 
									360_ability_id, 360_ability_name, 360_ability_lv_id, 360_ability_lv_name, score, tempId)
						SELECT t2.code_item_id 360_ability_id, t1.360_ability_name,
									 t3.code_item_id 360_ability_lv_id, t1.360_ability_lv_name, 
									 t1.score, 
									 tempId
						FROM soure_360_report t1
						INNER JOIN soure_code_item t2 ON t1.360_ability_name = t2.code_item_name AND t1.customer_id = t2.customer_id
						INNER JOIN soure_code_item t2 ON t1.360_ability_name = t2.code_item_name AND t1.customer_id = t3.customer_id
						;
	
					END IF;
					
					
				ELSE
					INSERT INTO 360_time
					VALUES(replace(UUID(),'-',''), customerId, emp_id, report_date, report_name, path, type);


				END IF;
		END WHILE;
		CLOSE s_cur;

 
END;
-- DELIMITER ;
