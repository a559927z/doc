DROP FUNCTION IF EXISTS mup.fn_key_to_id;   
CREATE FUNCTION mup.fn_key_to_id(parentKey VARCHAR(20), customerId VARCHAR(32), p_type VARCHAR(60)) 
RETURNS VARCHAR(32) CHARSET utf8   
BEGIN     
  DECLARE parentId VARCHAR(32);

	IF parentKey = "-1" THEN
		SET parentId = "-1";
	ELSE

		IF p_type = "org" THEN
			SELECT org.organization_id INTO parentId FROM dim_organization org WHERE org.organization_key = parentKey and org.customer_id = customerId;
		END IF;

		IF p_type = "risk" THEN
			SELECT t.separation_risk_id INTO parentId FROM dim_separation_risk t WHERE t.separation_risk_key = parentKey and t.customer_id = customerId;
		END IF;


	END IF;

  RETURN parentId;
END;

#-----------------------------------------------------------------fn_tree_pathname-----------------------------------------------------------------
-- 调用函数输出name路径   
DROP FUNCTION IF EXISTS mup.fn_tree_pathname;   
CREATE FUNCTION mup.fn_tree_pathname(nid VARCHAR(32), delimit VARCHAR(10), customerId VARCHAR(32)) 
RETURNS VARCHAR(2000) CHARSET utf8   
BEGIN     
  DECLARE pathid VARCHAR(1000);
  SET @pathid='';
  CALL pro_create_pnlist(nid, delimit, customerId, @pathid);
  RETURN @pathid;
END;