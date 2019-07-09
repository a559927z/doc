-- 更新是否有了节点
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_update_has_children`;
-- CREATE PROCEDURE pro_update_has_children(in p_customer_key VARCHAR(50))
CREATE PROCEDURE pro_update_has_children(in customerId VARCHAR(32))
BEGIN
	-- 需要定义接收游标数据的变量 
	DECLARE orgId VARCHAR(32);
  DECLARE orgPid VARCHAR(32);

	-- 定义接收临时表数据的变量 
	DECLARE vorgId VARCHAR(32);
	DECLARE hasC INT;
-- 	DECLARE customerId VARCHAR(32);

  -- 遍历数据结束标志
  DECLARE done INT DEFAULT FALSE;
	-- 游标
	DECLARE cur CURSOR FOR 
	SELECT 
		org.organization_id as orgId, 
		org.organization_parent_id as orgPid
	FROM v_dim_organization org;

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
-- 	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
-- 		BEGIN
-- 			ROLLBACK; SHOW ERRORS;
-- 			INSERT INTO db_log(log_id, customer_id, user_id, text, start_time, end_time, type)
-- 			VALUES(replace(UUID(),'-',''), customerId, optUserId, "【是事有子节点-维度表】：更新数据失败", startTime, now(), "error");
-- 		END;

-- 	set customerId = (SELECT dc.customer_id FROM dim_customer dc WHERE dc.customer_key=p_customer_key);

	-- 首先设置为0先。
	UPDATE dim_organization as o1 SET o1.has_children = 0 where o1.customer_id = customerId;

  OPEN cur;
  read_loop: LOOP

    FETCH cur INTO orgId, orgPid;
    IF done THEN
      LEAVE read_loop;
    END IF;
-- 		START TRANSACTION;

		UPDATE dim_organization as o1 
			SET o1.has_children = 1
		WHERE o1.organization_id in (
			select t.organization_id from (
				SELECT o.organization_id FROM dim_organization o where o.organization_id = orgPid and o.customer_id = customerId) t
			where t.organization_id is not null)
		AND o1.customer_id = customerId;

  END LOOP;
  -- 关闭游标
  CLOSE cur;


END;
-- DELIMITER ;