#=======================================================pro_fetch_sum_train_outlay=======================================================
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 上报的实际花费日期
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_sum_train_outlay`;
CREATE PROCEDURE pro_fetch_sum_train_outlay(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), IN p_datetime DATETIME)
BEGIN

		DECLARE pCurDate TIMESTAMP DEFAULT p_datetime;
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;


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
	END IF;


	
END;
-- DELIMITER ;

CALL pro_fetch_sum_train_outlay('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2011-01-04 00:00:00');





