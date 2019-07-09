#=======================================================pro_fetch_recruit_value=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_recruit_value`;
CREATE PROCEDURE pro_fetch_recruit_value(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_y int(4))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;


		START TRANSACTION;
			-- 预算
				LB_INSERT:BEGIN

						INSERT INTO recruit_value (
							recruit_value_id, customer_id, organization_id, budget_value, outlay, YEAR,  c_id
						)
						SELECT 
							REPLACE (UUID(), '-', ''), customer_id, organization_id, budget_value, 0, YEAR, c_id
						FROM soure_recruit_value t 
						WHERE customer_id = customerId AND year=p_y
						ON DUPLICATE KEY UPDATE
								customer_id = t.customer_id, 
								organization_id = t.organization_id,
								budget_value = t.budget_value,
								year = t.year								
						;
				END LB_INSERT;

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			SAVEPOINT rv1;
			COMMIT; 
			-- 已花
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


						LB_CURSOR2:BEGIN
								DECLARE sum_bv, sum_ol double(12,6);
								DECLARE ext INT(1);
								
								DECLARE done2, p_error3 INT DEFAULT 0;
								DECLARE orgId, pId VARCHAR(32);
								DECLARE fp TEXT;
								DECLARE s_cur2 CURSOR FOR
									SELECT 
										t1.organization_id orgId, t1.organization_parent_id pId, t1.full_path fp
									FROM dim_organization t1
									WHERE t1.customer_id = customerId 
									GROUP BY t1.organization_id
									ORDER BY t1.depth DESC, t1.full_path
									;
								DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done2 = 1;

								OPEN s_cur2;
								FETCH s_cur2 INTO orgId, pId, fp;

									WHILE1: WHILE done2 != 1 DO
										SELECT count(1) AS ext into ext FROM recruit_value t WHERE customer_id = customerId AND t.organization_id = orgId ;
	
										IF ext != 1 THEN
											SELECT sum(t.budget_value) AS sum_bv, sum(t.outlay) AS sum_ol
											INTO sum_bv, sum_ol
											FROM dim_organization t1
											LEFT JOIN recruit_value t ON t1.organization_id = t.organization_id AND t1.customer_id = t.customer_id
											where 
													LOCATE(fp,t1.full_path) 
													AND t1.organization_parent_id = orgId;

											IF sum_ol > 0 THEN 
																					
												INSERT INTO recruit_value 
												(recruit_value_id, customer_id, organization_id, budget_value, outlay, YEAR) VALUES 
												(REPLACE(UUID(),'-',''), customerId, orgId, sum_bv, sum_ol, p_y)
												;
											END IF;
										END IF;


										FETCH s_cur2 INTO orgId, pId, fp;
									END WHILE WHILE1;
								CLOSE s_cur2;

								IF p_error3 = 1 THEN  
									ROLLBACK TO SAVEPOINT rv2;  
								ELSE  
									COMMIT; 
								END IF;
						END LB_CURSOR2;



	END IF;
END;
-- DELIMITER ;

CALL pro_fetch_recruit_value('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 2015);

