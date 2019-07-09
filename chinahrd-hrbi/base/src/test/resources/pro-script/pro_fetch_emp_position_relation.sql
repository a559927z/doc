-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：dim_emp, dim_position , dim_organization表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_emp_position_relation`;
CREATE PROCEDURE pro_fetch_emp_position_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

-- 	DECLARE empKey, posKey, orgKey VARCHAR(20);
-- 	DECLARE empId, posId, orgId VARCHAR(32);
-- 	DECLARE isRegular, enabled int(1);
-- -- 	DECLARE effectDate, expiryDate DATETIME;
-- 	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
-- 	DECLARE exist int;
-- 	DECLARE startTime TIMESTAMP;
-- 
--   -- 遍历数据结束标志
-- 	DECLARE done INT DEFAULT 0;
-- 	-- 游标
-- 	DECLARE s_cur CURSOR FOR
-- 					SELECT 
-- 						t.emp_key empKey, t.position_key posKey, t.organization_key orgKey,
-- 						t1.emp_id empId, t2.position_id posId, t3.organization_id orgId,
-- 						t.is_regular isRegular -- , t.enabled, t.effect_date effectDate, t.expiry_date expiryDate
-- 					FROM soure_emp_position_relation t 
-- 					INNER JOIN dim_emp t1 on t.emp_key = t1.emp_key and t1.customer_id = t.customer_id AND now()>=t1.effect_date AND t1.expiry_date is null AND t1.enabled = 1
-- 					INNER JOIN dim_position t2 on  t.position_key = t2.position_key and t2.customer_id = t.customer_id
-- 					INNER JOIN dim_organization t3 on  t.organization_key = t3.organization_key and t3.customer_id = t.customer_id
-- 					WHERE t.customer_id = customerId;
-- 	
-- 	-- 将结束标志绑定到游标
-- 	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
-- 	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
-- 		BEGIN
-- 			ROLLBACK; SHOW ERRORS;
-- 			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
-- 			VALUES(replace(UUID(),'-',''), customerId, optUserId, CONCAT("【员工岗位-维度表】：", empKey, "和", posId, "数据失败"), startTime, now(), "error");
-- 		END;
-- 
-- 		SET startTime = now();
-- 		OPEN s_cur;
-- 		WHILE done != 1 DO
-- 			FETCH s_cur INTO empKey, posKey, orgKey, empId, posId, orgId, isRegular;-- , enabled, effectDate, expiryDate;
-- -- 			IF NOT done THEN
-- 				START TRANSACTION;
-- 				SELECT count(1) as exist into exist FROM emp_position_relation
-- 				WHERE emp_id = empId and position_id = posId and organization_id = orgId AND customer_id = customerId;
-- 
-- 				IF exist>0 THEN
-- 					UPDATE emp_position_relation 
-- 					SET is_regular = isRegular -- , effect_date = effectDate, expiry_date=expiryDate, enabled = enabled
-- 					WHERE emp_id = empId and position_id = posId and organization_id = orgId AND customer_id = customerId;
-- 
-- 				ELSE
-- 					INSERT into emp_position_relation(
-- 									emp_position_id, customer_id,
-- 									emp_id, position_id, organization_id, is_regular )
-- 									-- ,effect_date, expiry_date, enabled)
-- 					VALUES(replace(UUID(),'-',''), customerId, empId, posId, orgId, isRegular );-- , effectDate, expiryDate, enabled);
-- 				END IF;
-- -- 			END IF;
-- 		END WHILE;
-- 		CLOSE s_cur;

					DELETE FROM emp_position_relation WHERE customer_id = customerId;
					INSERT INTO emp_position_relation(
						emp_position_id, customer_id,
						emp_id, position_id, organization_id,
						is_regular
					)
					SELECT 
						REPLACE(uuid(), '-', ''), customerId,
						t1.emp_id empId, t2.position_id posId, t3.organization_id orgId,
						t.is_regular isRegular -- , t.enabled, t.effect_date effectDate, t.expiry_date expiryDate
					FROM soure_emp_position_relation t 
					INNER JOIN dim_emp t1 on t.emp_key = t1.emp_key and t1.customer_id = t.customer_id AND now()>=t1.effect_date AND t1.expiry_date is null AND t1.enabled = 1
					INNER JOIN dim_position t2 on  t.position_key = t2.position_key and t2.customer_id = t.customer_id
					INNER JOIN dim_organization t3 on  t.organization_key = t3.organization_key and t3.customer_id = t.customer_id
					WHERE t.customer_id = customerId;



END;
-- DELIMITER ;
