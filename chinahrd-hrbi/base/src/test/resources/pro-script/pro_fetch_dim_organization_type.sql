#=======================================================pro_fetch_dim_organization_type=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_organization_type`;
CREATE PROCEDURE pro_fetch_dim_organization_type(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE typeKey VARCHAR(32);
	DECLARE typeLevel VARCHAR(10);
	DECLARE typeName VARCHAR(50);

	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE exist int;
	DECLARE startTime TIMESTAMP;

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE p_message VARCHAR(10000) DEFAULT '【机构级别-维度表】：数据刷新完成'; 

  -- 遍历数据结束标志
	DECLARE done INT DEFAULT 0;
	-- 游标
	DECLARE s_cur CURSOR FOR
					SELECT sdot.organization_type_key as typeKey,
								 sdot.organization_type_level as typeLevel, 
								 sdot.organization_type_name as typeName
					FROM soure_dim_organization_type sdot WHERE sdot.customer_id = customerId;
	
	-- 将结束标志绑定到游标
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET p_error = 1;

		SET startTime = now();

		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO typeKey, typeLevel, typeName;
-- 			IF NOT done THEN
				START TRANSACTION;
				SELECT count(1) as exist into exist FROM dim_organization_type WHERE organization_type_key = typeKey AND customer_id = customerId;

				IF exist>0 THEN
					UPDATE dim_organization_type 
					SET organization_type_level = typeLevel, organization_type_name = typeName
					WHERE organization_type_key = typeKey AND customer_id = customerId;
				ELSE
					INSERT into dim_organization_type 
					VALUES(replace(UUID(),'-',''), customerId, typeKey, typeLevel, typeName);
				END IF;
-- 			END IF;
		END WHILE;
		CLOSE s_cur;


		IF p_error = 1 THEN  
			ROLLBACK; SHOW ERRORS;
			SET p_message = concat("【机构级别-维度表】：数据刷新失败。", "操作用户：", optUserId);
			INSERT INTO db_log
			VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_organization_type', p_message, startTime, now(), 'error' );
	 
		ELSE  
				COMMIT;  
				INSERT INTO db_log (log_id, customer_id, user_id, module, text, start_time, end_time, type)
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_organization_type', p_message, startTime, now(), 'sucess' );
		END IF;


END;
-- DELIMITER ;