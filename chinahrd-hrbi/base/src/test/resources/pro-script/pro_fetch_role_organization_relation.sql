-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
--  保证：dim_role, dim_organization表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_role_organization_relation`;
CREATE PROCEDURE pro_fetch_role_organization_relation(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE roleKey, organKey VARCHAR(20);
	DECLARE roleId, organId VARCHAR(32);

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP;
	DECLARE exist int;

  -- 遍历数据结束标志
	DECLARE done INT DEFAULT 0;
	-- 游标
	DECLARE s_cur CURSOR FOR
					SELECT 
						t.role_key as roleKey, t.organization_key as organKey,
						t1.role_id as roleId,
 						t2.organization_id as organId
					FROM soure_role_organization_relation t
					inner JOIN dim_role t1 on t.role_key = t1.role_key and t1.customer_id = t.customer_id
 					inner JOIN dim_organization t2 on t.organization_key = t2.organization_key and t2.customer_id = t.customer_id
					WHERE t.customer_id = customerId;


	-- 将结束标志绑定到游标
	-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET SET done = 1;
		DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
		DECLARE EXIT HANDLER FOR SQLEXCEPTION 
			BEGIN
				ROLLBACK; SHOW ERRORS;
				INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
				VALUES(replace(UUID(),'-',''), customerId, optUserId, CONCAT("【角色机构-关系表】：", roleKey, "与", organKey, "数据失败"), startTime, now(), "error");
			END;


		DELETE FROM role_organization_relation WHERE customer_id = customerId;

		SET startTime = now();
		OPEN s_cur;
		WHILE done != 1 DO
		
			FETCH s_cur INTO roleKey, organKey, roleId, organId;

			START TRANSACTION;

-- 			IF NOT done THEN
-- 				SELECT count(1) as exist into exist FROM role_organization_relation t
-- 				WHERE t.role_id = roleId and t.organization_id = organId AND t.customer_id = customerId;
-- 
-- 				IF exist>0 THEN
-- 					SELECT "【角色机构关系表】："+roleKey+"和"+organKey+"关系数据存在。";
-- 				ELSE
					INSERT INTO role_organization_relation(
									role_organization_id, customer_id, role_id, organization_id, half_check, create_user_id, create_time)
					VALUES(replace(UUID(),'-',''), customerId, roleId, organId, 0, optUserId, now());



-- 				END IF;
-- 			END IF;
		END WHILE;
		CLOSE s_cur;

-- 		INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
-- 		VALUES(replace(UUID(),'-',''), customerId, optUserId, "【角色机构-关系表】：数据成功", startTime, now(), "success");

END;
-- DELIMITER ;
