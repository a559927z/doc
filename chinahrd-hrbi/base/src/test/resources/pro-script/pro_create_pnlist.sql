-- 递归过程输出某节点name路径
DROP PROCEDURE IF EXISTS mup.pro_create_pnlist;
CREATE PROCEDURE mup.pro_create_pnlist(IN nid VARCHAR(32), IN delimit VARCHAR(10), in customerId VARCHAR(32), INOUT pathstr VARCHAR(1000))
BEGIN                    
      DECLARE done INT DEFAULT 0;  
      DECLARE parentId VARCHAR(32) DEFAULT 0;        
      DECLARE cur1 CURSOR FOR    
      SELECT t.organization_parent_id as parentId, CONCAT(t.organization_key, delimit, pathstr)
        FROM v_dim_organization AS t WHERE t.organization_id = nid and t.customer_id = customerId;
      DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;  
      SET max_sp_recursion_depth=12;      
              
      OPEN cur1;  
      FETCH cur1 INTO parentId, pathstr;  

-- 			WHILE done=0 DO  
--               CALL mup.pro_create_pnlist(parentid, delimit,customerId, pathstr);  
--               FETCH cur1 INTO parentid, pathstr;  
--       END WHILE; 

			
			if done=0 THEN  
              CALL mup.pro_create_pnlist(parentId, delimit,customerId, pathstr);  
              FETCH cur1 INTO parentId, pathstr;  
      END if; 

      CLOSE cur1;   
END;