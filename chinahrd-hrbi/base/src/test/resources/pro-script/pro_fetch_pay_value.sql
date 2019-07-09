#=======================================================pro_fetch_pay_value=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_pay_value`;
CREATE PROCEDURE pro_fetch_pay_value(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_y INTEGER(4))
BEGIN

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;
			
	-- 薪酬预算表
			LB_CURSOR:BEGIN
					
					DECLARE orgId VARCHAR(32);
					DECLARE fp TEXT;

					DECLARE done INT DEFAULT 0;
					DECLARE s_cur CURSOR FOR
						SELECT t2.organization_id orgId, t2.full_path fp
						FROM dim_organization t2 
						WHERE t2.customer_id = customerId
						;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;

					DELETE FROM pay_value WHERE `year` = p_y;

					OPEN s_cur;
					FETCH s_cur INTO orgId, fp;
						WHILE1: WHILE done != 1 DO
							-- 汇总父级，祖父级
							INSERT INTO pay_value (
								pay_value_id,
								customer_id,
								organization_id,
								pay_value,
								`year`
							)
							SELECT 
								REPLACE(UUID(),'-',''),
								t.customer_id,
								orgId,
								sum(pay_value),
								`year`
							FROM soure_pay_value t
							INNER JOIN dim_organization t1 on t.organization_id = t1.organization_id
							WHERE t.customer_id = customerId 
							AND t.`year` = p_y
							AND LOCATE(fp, t1.full_path)
							;

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
TRUNCATE TABLE pay_value;
CALL pro_fetch_pay_value('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', 2011);
CALL pro_fetch_pay_value('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', 2012);
CALL pro_fetch_pay_value('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', 2013);
CALL pro_fetch_pay_value('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', 2014);
CALL pro_fetch_pay_value('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA', 2015);