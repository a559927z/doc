-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- 保证 dim_emp 表 完成
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_user`;
CREATE PROCEDURE pro_fetch_dim_user(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE empId,empKey, userKey VARCHAR(32);
	DECLARE userName VARCHAR(10);
	DECLARE userNameCh VARCHAR(5);	
	DECLARE pwd VARCHAR(20);
	DECLARE email VARCHAR(50);
	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP;
	DECLARE exist int;

	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR
					SELECT 
						de.emp_id as empId, 
						t.emp_key as empKey,
						t.user_key as userKey,
						t.user_name as userName,
						t.user_name_ch as userNameCh,
						t.`password` as pwd,
						t.email as email
					FROM soure_dim_user t
					LEFT JOIN dim_emp de on de.emp_key = t.emp_key AND now()>=de.effect_date AND de.expiry_date is null AND de.enabled = 1
								and de.user_name = t.user_name and de.user_name_ch = t.user_name_ch
					WHERE t.customer_id = customerId;
	
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN
			ROLLBACK; SHOW ERRORS;
			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
			VALUES(replace(UUID(),'-',''), customerId, optUserId, CONCAT("【用户-维度表】：",empKey, "数据失败"), startTime, now(), "error");
		END;

		SET startTime = now();
		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO empId, empKey, userKey, userName, userNameCh, pwd, email;	-- empKey 没有用到
 			IF NOT done THEN
				START TRANSACTION;
				SELECT count(1) as exist into exist FROM dim_user du
				WHERE du.user_key = userKey AND customer_id = customerId;

				IF exist>0 THEN
					UPDATE dim_user
					SET emp_id = empId, user_name = userName, user_name_ch = userNameCh, `PASSWORD` = pwd, email = email,
							modify_user_id = optUserId, modify_time = now()
					WHERE user_key = userKey AND customer_id = customerId and sys_deploy = 0;
					
-- 					INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
-- 					VALUES(replace(UUID(),'-',''), customerId, optUserId, concat("【用户-维度表】：", userKey, "内容数据更新成功！"), startTime, now(), "success");
				ELSE
					INSERT into dim_user(
									user_id, customer_id, emp_id, user_key, user_name, user_name_ch,
									`PASSWORD`, email, sys_deploy,
									create_user_id, create_time)
					VALUES(replace(UUID(),'-',''), customerId, empId, userKey, userName, userNameCh, pwd,  email, 0, optUserId, now());
				END IF;
 			END IF;
		END WHILE;
		CLOSE s_cur;

-- 		INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
-- 		VALUES(replace(UUID(),'-',''), customerId, optUserId, "【用户-维度表】：数据成功", startTime, now(), "success");

END;
-- DELIMITER ;
