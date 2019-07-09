#=======================================================pro_fetch_train_value=======================================================
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 上报的预算日期
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_train_value`;
CREATE PROCEDURE pro_fetch_train_value(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), IN p_datetime DATETIME)
BEGIN

		DECLARE pCurDate TIMESTAMP DEFAULT p_datetime;
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;

			-- 	删除6年前今天以往的数据
-- 			DELETE FROM soure_train_outlay 
-- 			WHERE customer_id = customerId
-- 			AND	id in (
-- 					SELECT id FROM (
-- 						SELECT id FROM soure_train_value t where `year` < DATE_SUB(now(), INTERVAL 6 YEAR)
-- 					) t
-- 			);

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
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
								

					OPEN s_cur;
					FETCH s_cur INTO orgId, fp;

						WHILE1: WHILE done != 1 DO

						 SET @bv = (SELECT IFNULL(sum(t.budget_value),0) ol
												FROM dim_organization t1
												LEFT JOIN soure_train_value t ON t1.organization_id = t.organization_id 
												WHERE LOCATE(fp, t1.full_path)
												AND t.organization_id is not NULL
												AND t.`year` =  YEAR(pCurDate)
												ORDER BY t.`year`
												);

						IF @bv != 0 THEN
							INSERT INTO train_value VALUES (REPLACE(UUID(),'-',''), customerId, orgId, @bv, YEAR(pCurDate));
						END IF;

							FETCH s_cur INTO orgId, fp;
						END WHILE WHILE1;
				
					CLOSE s_cur;
				END LB_CURSOR;


	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
	COMMIT;  
		-- 外部机构
		REPLACE INTO train_value(
				train_value_id,
				customer_id,
				organization_id,
				budget_value,
				`year`
			)
			SELECT 
				id,
				customer_id,
				organization_id,
				sum(budget_value),
				`year`
			FROM soure_train_value
			where organization_id is NULL AND `year` =YEAR(pCurDate) ;
		
		
	END IF;
END;
-- DELIMITER ;
 TRUNCATE TABLE train_value;
	CALL pro_fetch_train_value('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2011-06-21 00:00:00');
	CALL pro_fetch_train_value('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2012-06-21 00:00:00');
	CALL pro_fetch_train_value('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2013-06-21 00:00:00');
	CALL pro_fetch_train_value('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2014-06-21 00:00:00');
	CALL pro_fetch_train_value('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', '2015-06-21 00:00:00');

