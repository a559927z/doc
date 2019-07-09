#=======================================================pro_fetch_history_emp_count=======================================================
-- 'demo','jxzhang'
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
			DELETE FROM history_emp_count where `day` = curDate;

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

END;
-- DELIMITER ;
CALL pro_fetch_history_emp_count( 'b5c9410dc7e4422c8e0189c7f8056b5f','DBA','2015-12-18');
