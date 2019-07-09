-- 从某节点向下遍历子节点，递归生成临时表数据
DROP PROCEDURE IF EXISTS mup.pro_create_childlist;
CREATE PROCEDURE mup.pro_create_childlist(IN rootId VARCHAR(32), IN nDepth INT, in customerId VARCHAR(32))  
BEGIN  
      DECLARE done INT DEFAULT 0;  
      DECLARE b VARCHAR(32);  

      DECLARE cur1 CURSOR FOR 
					SELECT vo.organization_id 
						FROM v_dim_organization vo
					LEFT JOIN dim_organization_type dot ON dot.organization_type_id = vo.organization_type_id
					WHERE vo.organization_parent_id = rootId AND vo.customer_id = customerId
					ORDER BY dot.organization_type_level; 

      DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;  

      SET max_sp_recursion_depth=12;  

      INSERT INTO tmpLst VALUES (NULL, rootId, nDepth);  


      OPEN cur1;  
      FETCH cur1 INTO b;  
      WHILE done=0 DO  
			
              CALL mup.pro_create_childlist(b, nDepth+1, customerId);  
              FETCH cur1 INTO b;  
      END WHILE;  
-- SELECT * FROM tmpLst;
      CLOSE cur1;  
END;