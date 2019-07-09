#=======================================================pro_fetch_dim_separation_risk=======================================================
-- 'demo','jxzhang'
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_dim_separation_risk`;
CREATE PROCEDURE pro_fetch_dim_separation_risk(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE srKey VARCHAR(100);
	DECLARE srParentKey VARCHAR(100);
	DECLARE srName VARCHAR(20);
	DECLARE curRefer text;
	DECLARE showIdx INT;
	
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP;
	DECLARE exist int;

  -- 遍历数据结束标志
	DECLARE done INT DEFAULT 0;
	-- 游标
	DECLARE s_cur CURSOR FOR
					SELECT 
						separation_risk_key srKey,
						separation_risk_parent_key srParentKey,
						separation_risk_name srName,
						refer curRefer,
						show_index showIdx
					FROM soure_dim_separation_risk t
					WHERE t.customer_id = customerId;
	
	-- 将结束标志绑定到游标
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
		BEGIN
			ROLLBACK; SHOW ERRORS ;
			-- SHOW WARNINGS;
			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
			VALUES(replace(UUID(),'-',''), customerId, optUserId, CONCAT("【离职风险-维度表】：", srKey, "数据失败"), startTime, now(), "error");
		END;

		SET startTime = now();

		OPEN s_cur;
		WHILE done != 1 DO
			FETCH s_cur INTO srKey, srParentKey, srName, curRefer, showIdx;
-- 			IF NOT done THEN
				START TRANSACTION;
				SELECT count(1) AS exist INTO exist FROM dim_separation_risk t
				WHERE t.separation_risk_key = srKey AND separation_risk_parent_key = srParentKey AND customer_id = customerId;

				IF exist>0 THEN
					UPDATE dim_separation_risk
					SET separation_risk_name = srName, refer = curRefer, show_index = showIdx
					WHERE separation_risk_key = srKey AND separation_risk_parent_key = srParentKey AND customer_id = customerId;
					
				ELSE
					INSERT INTO dim_separation_risk(separation_risk_id, customer_id,  separation_risk_key, 
																					separation_risk_parent_key, separation_risk_name, refer, show_index)
					VALUES(replace(UUID(),'-',''), customerId, srKey, srParentKey, srName, curRefer, showIdx);

				END IF;
-- 			END IF;
				
				-- 更新所有parentKey to parentId
				UPDATE dim_separation_risk  
					SET separation_risk_parent_id = fn_key_to_id(srParentKey, customerId, 'risk')
				WHERE separation_risk_key = srKey AND separation_risk_parent_key = srParentKey AND customer_id = customerId;

		END WHILE;
		CLOSE s_cur;

-- 		SET done = 0;
-- 		OPEN s_cur;
-- 		REPEAT
-- 			FETCH s_cur INTO srKey, srParentKey, srName, curRefer;
-- 			-- 更新所有parentKey to parentId
-- 			UPDATE dim_separation_risk  
-- 				SET separation_risk_parent_id = fn_parentKeyToParentId_risk(srParentKey, customerId)
-- 			WHERE separation_risk_key = srKey AND separation_risk_parent_key = srParentKey AND customer_id = customerId;
-- 
-- 		UNTIL done END REPEAT;
-- 		CLOSE s_cur;
-- 

-- 		INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
-- 		VALUES(replace(UUID(),'-',''), customerId, optUserId, "【离职风险-维度表】：数据成功", startTime, now(), "success");


END;
-- DELIMITER ;
