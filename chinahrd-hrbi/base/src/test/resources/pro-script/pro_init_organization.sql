#=======================================================pro_init_organization=======================================================
-- 全路径、是否有子节点  
DROP PROCEDURE IF EXISTS pro_init_organization;
CREATE PROCEDURE pro_init_organization(in p_customer_key VARCHAR(50))  
BEGIN  
	 -- 需要定义接收游标数据的变量
		DECLARE cid VARCHAR(32);
		DECLARE pathname VARCHAR(32);
		DECLARE depth INT;
		DECLARE orgPid VARCHAR(32);

		DECLARE customerId VARCHAR(32) DEFAULT (SELECT dc.customer_id FROM dim_customer dc WHERE dc.customer_key=p_customer_key);
		DECLARE startTime TIMESTAMP DEFAULT now();
		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE p_message VARCHAR(10000) DEFAULT '【机构-维度表】：全路径\是否有子节点刷新完成';
		DECLARE optUserId VARCHAR(10000) DEFAULT 'system-event-execut';


	 -- 遍历数据结束标志
		DECLARE done INT DEFAULT FALSE;
	 -- 游标
		DECLARE cur CURSOR FOR
			SELECT
			  org.organization_id as cid, fn_tree_pathname(org.organization_id, '_', customerId) as pathname, tmpLst.depth,
				org.organization_parent_id as orgPid
			FROM tmpLst, v_dim_organization org
			LEFT JOIN dim_organization_type dot ON dot.organization_type_id = org.organization_type_id
			WHERE tmpLst.id = org.organization_id  ORDER BY dot.organization_type_level;

	 -- 将结束标志绑定到游标
		DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	

	-- --------------------------------------------------------------------
	-- 全路径设置
		DROP TEMPORARY TABLE IF EXISTS tmpLst;  
		CREATE TEMPORARY TABLE IF NOT EXISTS tmpLst    
		 (sno INT PRIMARY KEY AUTO_INCREMENT, id VARCHAR(32), depth INT);        
		CALL mup.pro_create_childlist(-1, 0, customerId);

		SELECT
		 org.organization_id as cid, fn_tree_pathname(org.organization_id, '_', customerId) as pathname, tmpLst.depth 
		FROM tmpLst, v_dim_organization org
		LEFT JOIN dim_organization_type dot ON dot.organization_type_id = org.organization_type_id
		WHERE tmpLst.id = org.organization_id  ORDER BY dot.organization_type_level;

		OPEN cur;
		WHILE done != 1 DO
			FETCH cur INTO cid, pathname, depth, orgPid;
	

		START TRANSACTION;
			-- 更新全路径
			UPDATE dim_organization org 
				SET org.full_path = Substring(pathname, 1, LENGTH(pathname)-1),
						org.`depth` = depth
			where org.organization_id = cid AND org.customer_id = customerId;

			-- 更新是否有子节点
			UPDATE dim_organization as o1 
				SET o1.has_children = 1
			WHERE o1.organization_id in (
				SELECT t.organization_id from (
					SELECT o.organization_id FROM dim_organization o where o.organization_id = orgPid and o.customer_id = customerId) t
				WHERE t.organization_id is not null)
			AND o1.customer_id = customerId;


	 		END WHILE;
		CLOSE cur;
	-- --------------------------------------------------------------------

		IF p_error = 1 THEN  
				ROLLBACK;  
				SET p_message = concat("【机构-维度表】：全路径\是否有子节点刷新失败。操作用户：", optUserId);
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_organization.full_path', p_message, startTime, now(), 'error' );
		ELSE  
				COMMIT;  
				INSERT INTO db_log 
				VALUES(replace(uuid(), '-',''), customerId, optUserId, 'dim_organization.full_path', p_message, startTime, now(), 'sucess' );
		END IF;

END;


