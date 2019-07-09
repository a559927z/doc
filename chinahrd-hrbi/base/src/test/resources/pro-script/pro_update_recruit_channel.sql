#=======================================================pro_update_recruit_channel=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_update_recruit_channel`;
CREATE PROCEDURE pro_update_recruit_channel(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_y int(4))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;


		START TRANSACTION;

			LB_CURSOR:BEGIN
					DECLARE orgId VARCHAR(32);
					DECLARE done, p_error2 INT DEFAULT 0;
					DECLARE o DOUBLE(12,6);
					DECLARE s_cur CURSOR FOR
						SELECT t1.organization_id orgId FROM dim_position t1   WHERE t1.customer_id = customerId;
					DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;
					DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error2 = 1; END;

					OPEN s_cur;
					FETCH s_cur INTO orgId;
						WHILE1: WHILE done != 1 DO
							
							SET o = (SELECT SUM(t.outlay) FROM recruit_channel t 
												LEFT JOIN dim_position t1 ON t.position_id = t1.position_id 
												where t1.customer_id = customerId AND t.year=p_y AND t1.organization_id = orgId);

							UPDATE recruit_value SET outlay = o
							WHERE customer_id = customerId AND year=p_y AND organization_id = orgId;

							FETCH s_cur INTO orgId;
						END WHILE WHILE1;
					CLOSE s_cur;


					IF p_error2 = 1 THEN  
						ROLLBACK TO SAVEPOINT rv1;  
					ELSE  
						SAVEPOINT rv2;
						COMMIT; 
					END IF;
			END LB_CURSOR;


END;
-- DELIMITER ;

-- CALL pro_update_recruit_channel('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2015);

