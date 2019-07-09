-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- 删除所有关所重新插入
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_user_role_relation`;
CREATE PROCEDURE pro_fetch_user_role_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE userId, roleId VARCHAR(32);
	DECLARE userKey, roleKey VARCHAR(20);

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP;
	DECLARE exist int;

  -- 遍历数据结束标志
	DECLARE done INT DEFAULT 0;
	-- 游标
	DECLARE s_cur CURSOR FOR
					SELECT 
						du.user_id as userId,	dr.role_id as roleId,
						t.user_key as userKey, t.role_key as roleKey
					FROM soure_user_role_relation t
					inner JOIN dim_user du on t.user_key = du.user_key and du.customer_id = t.customer_id AND du.sys_deploy = 0
 					inner JOIN dim_role dr on t.role_key = dr.role_key and dr.customer_id = t.customer_id
					WHERE t.customer_id = customerId;

	-- 将结束标志绑定到游标
		DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
		DECLARE EXIT HANDLER FOR SQLEXCEPTION 
			BEGIN
				ROLLBACK; SHOW ERRORS;
				INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
				VALUES(replace(UUID(),'-',''), customerId, optUserId, CONCAT("【用户角色-关系表】：", roleKey, "与", userKey, "数据失败"), startTime, now(), "error");
			END;

		SET startTime = now();
	
		DELETE FROM user_role_relation WHERE customer_id = customerId;

		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO userId, roleId, userKey, roleKey;
		START TRANSACTION;

-- 			IF NOT done THEN

-- 				SELECT count(1) as exist into exist FROM user_role_relation t
-- 				WHERE t.user_id = userId and t.role_id = roleId AND t.customer_id = customerId;
-- 
-- 				IF exist>0 THEN
-- 					INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
-- 					VALUES(replace(UUID(),'-',''), customerId, optUserId, concat("【用户角色关系表】：", roleKey, "和", userKey, "关系数据已存在。"), startTime, now(), "info");
-- 					
-- 				ELSE
					INSERT into user_role_relation(
									user_role_id,	customer_id, role_id, user_id,
									create_user_id, create_time)
					VALUES(replace(UUID(),'-',''), customerId, roleId, userId, optUserId, now());
-- 				END IF;
-- 			END IF;

		END WHILE;
		CLOSE s_cur;

-- 		INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
-- 		VALUES(replace(UUID(),'-',''), customerId, optUserId, "【用户角色-关系表】：数据成功", startTime, now(), "success");

END;
-- DELIMITER ;


-- CALL pro_fetch_user_role_relation('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA');
