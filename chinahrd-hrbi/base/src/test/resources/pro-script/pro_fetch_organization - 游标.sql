#=======================================================pro_fetch_organization=======================================================
-- 'demo'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- 保证dim_business_unit、dim_organization_type数据存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_organization`;
CREATE PROCEDURE pro_fetch_organization(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	-- 定义接收游标数据的变量 
	DECLARE buId,oTypeId VARCHAR(32);
	DECLARE buKey, oTypeKey, orgKey, parentKey VARCHAR(20);
	DECLARE orgName VARCHAR(50);
	DECLARE isSingle, curEnabled INT(1);
	DECLARE effectDate TIMESTAMP;

	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;

	-- 定义接收临时表数据的变量 
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE exist, buExist, oTypeExist INT;
	DECLARE startTime TIMESTAMP;

  -- 遍历数据结束标志
	DECLARE done INT DEFAULT 0;
	-- 游标
	DECLARE s_cur CURSOR FOR
									SELECT 
										bu.business_unit_id as buId,
										ot.organization_type_id as oTypeId,
										sorg.business_unit_key as buKey,
										sorg.organization_type_key as oTypeKey,
										sorg.organization_key as orgKey,
										sorg.organization_parent_key as parentKey,
										sorg.organization_name as orgName,
										sorg.is_single as isSingle,
										sorg.enabled as curEnabled,
										sorg.effect_date as effectDate
									FROM soure_dim_organization sorg
									INNER JOIN dim_business_unit bu on bu.business_unit_key = sorg.business_unit_key
									INNER JOIN dim_organization_type ot on sorg.organization_type_key = ot.organization_type_key
 									WHERE sorg.customer_id = customerId
									ORDER BY ot.organization_type_level;
	
	-- 将结束标志绑定到游标
	-- DECLARE CONTINUE HANDLER FOR NOT FOUND SET SET done = 1;
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN
			ROLLBACK; SHOW ERRORS;
			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
			VALUES(replace(UUID(),'-',''), customerId, optUserId, CONCAT("【机构-维度表】：",buKey, "与", oTypeKey, "与", orgKey, "数据失败！"), startTime, now(), "error");
		END;


	-- 删除所有机构，准备重新写入。
 	-- delete from dim_organization where customer_id = customerId;

	SELECT count(1) as buExist INTO buExist FROM dim_business_unit WHERE customer_id = customerId;
	SELECT count(1) as oTypeExist INTO oTypeExist FROM dim_organization_type WHERE customer_id = customerId;

	IF buExist IS null THEN
		INSERT INTO db_log(log_id,customer_id,user_id, text, start_time, end_time, type)
		VALUES(replace(UUID(),'-',''), customerId, optUserId, "【业务单位-维度表】：不能为空", now(), now(), "error");
	ELSEIF oTypeExist is NULL THEN
		INSERT INTO db_log(log_id,customer_id,user_id, text, start_time, end_time, type)
		VALUES(replace(UUID(),'-',''), customerId, optUserId, "【机构级别-维度表】：不能为空", now(), now(), "error");
	ELSE
		SET startTime = now();
		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO buId,oTypeId, buKey,oTypeKey, orgKey,  parentKey, orgName, isSingle, curEnabled, effectDate;
-- 			IF NOT done THEN
				START TRANSACTION;
				SELECT count(1) as exist into exist 
					FROM dim_organization org
				WHERE org.organization_key = orgKey and org.business_unit_id = buId and org.organization_type_id= oTypeId AND org.customer_id = customerId;

				IF exist>0 THEN
					UPDATE dim_organization org1 
						SET org1.business_unit_id = buId, org1.organization_type_id = oTypeId,
								org1.organization_parent_id = parentKey,-- fn_parentKeyToParentId(parentKey, customerId),
								org1.organization_name = orgName, org1.is_single = isSingle,
								org1.effect_date = effectDate, org1.enabled = curEnabled
					WHERE org1.organization_key = orgKey and org1.business_unit_id = buId and org1.organization_type_id= oTypeId AND org1.customer_id = customerId;

				ELSE

					-- 默认是否有子节点为0
					INSERT into dim_organization(
									organization_id, customer_id, business_unit_id, organization_type_id, organization_key,
									organization_parent_id,
									organization_name, is_single, 
									has_children, effect_date, enabled)
					VALUES(replace(UUID(),'-',''), customerId, buId, oTypeId, orgKey,
									parentKey,-- fn_parentKeyToParentId(parentKey, customerId),
									orgName, isSingle, 0, effectDate, curEnabled);

				END IF;				
-- 			END IF;
	
	
		END WHILE;


		CLOSE s_cur;


-- 		重置
		SET done = 0;
		OPEN s_cur;
		REPEAT
			FETCH s_cur INTO buId,oTypeId, buKey,oTypeKey, orgKey,  parentKey, orgName, isSingle, curEnabled, effectDate;
		-- 更新所有parentKey to parentId
			UPDATE dim_organization org1 
				SET org1.organization_parent_id = fn_key_to_id(parentKey, customerId, 'org')
			WHERE org1.organization_key = orgKey and org1.business_unit_id = buId and org1.organization_type_id= oTypeId AND org1.customer_id = customerId;

		UNTIL done END REPEAT;
		CLOSE s_cur;


	END IF;

END;
-- DELIMITER ;
