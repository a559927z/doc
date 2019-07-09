#=======================================================templet=======================================================
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- 'demo','jxzhang'
--  保证：dim_organization表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `templet`;
CREATE PROCEDURE templet(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), IN p_datetime DATETIME)
BEGIN

		DECLARE pCurDate TIMESTAMP DEFAULT p_datetime;
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000) DEFAULT '【templet-DEMO表】：数据刷新完成';
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

	-- 删除五年前数据
			DELETE FROM soure_train_plan 
			WHERE customer_id = customerId
			AND	id in (
					SELECT id FROM (
						SELECT id FROM soure_train_plan t where `date` < DATE_SUB(now(), INTERVAL 5 YEAR)
					) t
			);
-- 		DELETE FROM emp_overtime_day where `date` NOT in (
-- 			select date from (
-- 				SELECT date FROM emp_overtime_day t where DATE_SUB(now, INTERVAL 5 YEAR) < `date`)
-- 			t);


-- ======================================replace模板======================================
-- 				LB_REPLACE:BEGIN
-- 					
-- 						REPLACE INTO TAGER_TABLE_NAME (
-- 							COLUMN_NAME1,COLUMN_NAME2,COLUMN_NAME3.....COLUMN_NAMEn
-- 						)
-- 						SELECT * FROM SOURE_TABLE_NAME;
-- 								
-- 				END LB_REPLACE;
-- 
-- 				LB_INSERT:BEGIN
-- 					INSERT INTO a( id, v1,v2 ) 
-- 					SELECT id, v1,v2 FROM b
--  				ON DUPLICATE KEY UPDATE v1 = VALUES(v1), v2 = VALUES(v2);
-- 				END LB_INSERT;

-- ======================================游标模板======================================
				LB_CURSOR:BEGIN
					
					DECLARE proId VARCHAR(32);
					DECLARE proName , proKey VARCHAR(20);

					DECLARE done INT DEFAULT 0;
					DECLARE s_cur CURSOR FOR
						SELECT 
							profession_id  	proId,
							profession_name	proName,
							profession_key	proKey
						FROM dim_profession;
-- 					DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
								

					OPEN s_cur;
					FETCH s_cur INTO  proId, proName, proKey;

						WHILE1: WHILE done != 1 DO
							
							SELECT  proId, proName, proKey;
-- 							INSERT INTO dim_profession VALUES("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");




							FETCH s_cur INTO  proId, proName, proKey;
						END WHILE WHILE1;
				
				
					CLOSE s_cur;

				END LB_CURSOR;



	IF p_error = 1 THEN  
			ROLLBACK;  
			SET p_message = concat("【templet-DEMO表】：数据刷新失败。操作用户：", optUserId);
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

CALL templet('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-01-01');

