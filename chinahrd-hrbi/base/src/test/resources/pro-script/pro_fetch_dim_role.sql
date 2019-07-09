-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_role`;
CREATE PROCEDURE pro_fetch_dim_role(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE roleKey VARCHAR(20);
	DECLARE roleName VARCHAR(10);

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE exist int;
	DECLARE startTime TIMESTAMP;

  -- 遍历数据结束标志
	DECLARE done INT DEFAULT 0;
	-- 游标
	DECLARE s_cur CURSOR FOR
					SELECT sdr.role_key as roleKey, sdr.role_name as roleName
					FROM soure_dim_role sdr WHERE sdr.customer_id = customerId;
	
	-- 将结束标志绑定到游标
-- 	DECLARE CONTINUE HANDLER FOR NOT FOUND SET SET done = 1;
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN
			ROLLBACK; SHOW ERRORS;
			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
			VALUES(replace(UUID(),'-',''), customerId, optUserId, CONCAT("【角色-维度表】：", roleKey, "数据失败"), startTime, now(), "error");
		END;

		SET startTime = now();

		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO roleKey, roleName;
-- 			IF NOT done THEN
				START TRANSACTION;
				SELECT count(1) as exist into exist FROM dim_role
				WHERE role_key = roleKey AND customer_id = customerId;

				IF exist>0 THEN
-- INSERT INTO debug(exist, type) VALUES (exist, 'update');
					UPDATE dim_role 
					SET role_name = roleName,
							modify_user_id = optUserId, modify_time = now()
					WHERE role_key = roleKey AND customer_id = customerId;
	
-- 					INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
-- 					VALUES(replace(UUID(),'-',''), customerId, optUserId, concat("【角色-维度表】：", roleKey, "内容更新成功!"), startTime, now(), "success");
				ELSE
-- INSERT INTO debug(exist, type) VALUES (exist, 'insert');
					INSERT into dim_role(
									role_id, customer_id,
									role_key, role_name,
									create_user_id, create_time)
					VALUES(replace(UUID(),'-',''), customerId, roleKey, roleName, optUserId, now());
-- 				END IF;
			END IF;
		END WHILE;
		CLOSE s_cur;

-- 		INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
-- 		VALUES(replace(UUID(),'-',''), customerId, optUserId, "【角色-维度表】：数据成功", startTime, now(), "success");

END
-- DELIMITER ;
