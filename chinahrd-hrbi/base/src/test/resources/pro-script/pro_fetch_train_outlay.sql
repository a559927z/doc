#=======================================================pro_fetch_train_outlay=======================================================
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 上报的实际花费日期
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_train_outlay`;
CREATE PROCEDURE pro_fetch_train_outlay(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), IN p_datetime DATETIME)
BEGIN


		DECLARE pCurDate TIMESTAMP DEFAULT p_datetime;
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

			-- 	删除五年前今天以往的数据
			DELETE FROM soure_train_outlay 
			WHERE customer_id = customerId
			AND	id in (
					SELECT id FROM (
						SELECT id FROM soure_train_outlay t where `date` < DATE_SUB(now(), INTERVAL 10 YEAR)
					) t
			);

-- ======================================游标模板======================================
				LB_CURSOR:BEGIN
					
					DECLARE orgId VARCHAR(32);
					DECLARE fp VARCHAR(1000);

					DECLARE done INT DEFAULT 0;
					DECLARE s_cur CURSOR FOR
						SELECT 
							organization_id orgId,
							full_path fp
						FROM dim_organization WHERE customer_id = customerId;
-- 					DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
								

					OPEN s_cur;
					FETCH s_cur INTO orgId, fp;

						WHILE1: WHILE done != 1 DO

						 SET @ol = (SELECT IFNULL(sum(t.outlay),0) ol
												FROM dim_organization t1
												LEFT JOIN soure_train_outlay t ON t1.organization_id = t.organization_id 
												WHERE LOCATE(fp, t1.full_path)
												AND t.organization_id is not NULL
												AND t.date = pCurDate
												ORDER BY t.date
												);

						IF @ol != 0 THEN
							INSERT INTO train_outlay VALUES (REPLACE(UUID(),'-',''), customerId, orgId, @ol, pCurDate, null, YEAR(pCurDate));
						END IF;

							FETCH s_cur INTO orgId, fp;
						END WHILE WHILE1;
				
					CLOSE s_cur;
				END LB_CURSOR;



	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
-- 			-- 去掉已删除数据
-- 			DELETE FROM train_outlay WHERE train_outlay_id NOT IN 
-- 			( SELECT t2.id FROM soure_train_outlay t2 WHERE t2.customer_id = customerId);

	-- 外部机构
			REPLACE INTO train_outlay (
				train_outlay_id,
				customer_id,
				organization_id,
				outlay,
				date,
				note,
				`year`
			)
			SELECT id,
				customer_id,
				organization_id,
				outlay,
				date,
				note,
				`year`
			FROM soure_train_outlay t2
			WHERE organization_id IS NULL AND `year` = YEAR(pCurDate)
			;
		
	END IF;
END;
-- DELIMITER ;
-- TRUNCATE TABLE train_outlay;
-- 	CALL pro_fetch_train_outlay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2011-06-21 00:00:00');
-- 	CALL pro_fetch_train_outlay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2011-06-21 00:00:00');


#=======================================================Main=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `Main`;
CREATE PROCEDURE Main()
BEGIN
	

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

-- ======================================游标模板======================================
				LB_CURSOR:BEGIN
-- 					
					DECLARE d TIMESTAMP;

					DECLARE done INT DEFAULT 0;
					DECLARE cur CURSOR FOR
						SELECT 
								DISTINCT t.date d
						FROM soure_train_outlay t  WHERE organization_id is NOT null -- AND customer_id = customerId
						ORDER BY date
						;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
								
					OPEN cur;
					FETCH cur INTO d;

						WHILE1: WHILE done != 1 DO
								CALL pro_fetch_train_outlay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', d);

							FETCH cur INTO d;
						END WHILE WHILE1;
				
					CLOSE cur;
				END LB_CURSOR;
-- 
-- 
	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  

			COMMIT;  
		

	END IF;
END;
-- DELIMITER ;
TRUNCATE TABLE train_outlay;
	CALL main();















