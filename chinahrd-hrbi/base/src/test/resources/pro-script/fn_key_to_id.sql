DROP FUNCTION IF EXISTS mup.fn_key_to_id;   
CREATE FUNCTION mup.fn_key_to_id(parentKey VARCHAR(20), customerId VARCHAR(32), p_type VARCHAR(60)) 
RETURNS VARCHAR(32) CHARSET utf8   
BEGIN     
  DECLARE parentId VARCHAR(32);

	IF parentKey = "-1" THEN
		SET parentId = "-1";
	ELSE

		IF p_type = "org" THEN
			SELECT org.id INTO parentId FROM soure_dim_organization org WHERE org.organization_key = parentKey and org.customer_id = customerId;
		END IF;

		IF p_type = "risk" THEN
			SELECT t.separation_risk_id INTO parentId FROM dim_separation_risk t WHERE t.separation_risk_key = parentKey and t.customer_id = customerId;
		END IF;


	END IF;

  RETURN parentId;
END;
