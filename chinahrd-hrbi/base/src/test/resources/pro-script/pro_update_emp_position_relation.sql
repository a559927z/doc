-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：dim_organization表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_update_emp_position_relation`;
CREATE PROCEDURE pro_update_emp_position_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE eKey VARCHAR(20);
	DECLARE eId, organId, posId VARCHAR(32);

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE exist int;
	DECLARE startTime TIMESTAMP;

	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
					SELECT 
						t.emp_key eKey, t.emp_id eId,
						t.organization_id organId,
						t.position_id posId
					FROM job_change t
					WHERE MAX(t.change_date) AND t.customer_id = customerId;

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN
			ROLLBACK; SHOW ERRORS;
			SHOW ERRORS;
			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
			VALUES(replace(UUID(),'-',''), customerId, optUserId, CONCAT("【员工岗位-关系表】：", posKey, "数据更新失败"), startTime, now(), "error");
		END;

		SET startTime = now();
		
		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO eKey, eId, organId, posId;
				START TRANSACTION;

				-- 业务逻辑，一个员工只有一个主职岗位。emp_key和is_regular决定唯一
				SELECT count(1) AS exist INTO exist FROM emp_position_relation
				WHERE emp_key = eKey and is_regular = 1 AND customer_id = customerId;
				-- 不考虑，没有主岗位的更新操作

				SELECT count(1) AS exist INTO exist FROM dim_emp 
				WHERE emp_key = eKey and is_regular = 1 AND customer_id = customerId;
				
				IF exist>0 THEN

					UPDATE dim_position 
					SET position_name = posName,
							effect_date = effectDate, expiry_date = expiryDate, enabled = curEnabled
					WHERE position_key = posKey and organization_id = organId AND customer_id = customerId;

-- 					INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
-- 					VALUES(replace(UUID(),'-',''), customerId, optUserId, concat("【岗位-维度表】：", posKey, "_", organKey, "关系内容数据更新成功！"), startTime, now(), "success");
				ELSE

					INSERT into dim_position(
									position_id, customer_id,
									position_key, position_name, organization_id, sequence_id,
									effect_date, expiry_date, enabled)
					VALUES(replace(UUID(),'-',''), customerId, posKey, posName, organId, seqId, effectDate, expiryDate, curEnabled);
			END IF;
		END WHILE;
		CLOSE s_cur;

-- 		INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
-- 		VALUES(replace(UUID(),'-',''), customerId, optUserId, "【岗位-维度表】：数据成功", startTime, now(), "success");

END;
-- DELIMITER ;
