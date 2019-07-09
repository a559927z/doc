DROP PROCEDURE IF EXISTS update_data_manpower_cost;
CREATE PROCEDURE update_data_manpower_cost ()
BEGIN

	DECLARE id VARCHAR(32);
	DECLARE orgKey VARCHAR(20);
	DECLARE   ct,cb,cc DOUBLE(6,2);
	
	DECLARE  ym INT(6);
	
	
	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR

		SELECT id, organization_key orgKey, cost ct, cost_budget cb, cost_company cc, `YEAR_MONTH` ym  FROM soure_manpower_cost;

	
-- 4e301608d90d4327aa9962f0d18eef8d
-- 4f4deacb06ae457dad7ab8db27dea35e
-- 9f570168958c4f23a7d2b86a52f8b79b
-- 2dfe821b6e2011e5b75208606e0aa89a
-- fcb4d31b3470460f93be81cf1dd64cf0
-- 423238847d2311e58aee08606e0aa89a


	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

	OPEN s_cur;
		
		WHILE done != 1 DO
			FETCH s_cur INTO   id, orgKey, ct,cb, cc, ym;

				SET @v1 = ct * 0.5;
				SET @v2 = cb * 0.5;
				SET @v3 = cc * 0.5;
			
UPDATE soure_manpower_cost SET cost =@v1, cost_budget =@v2, cost_company=@v3
WHERE organization_key = orgKey and `year_month` =ym;
				
		
		END WHILE;
		CLOSE s_cur;



-- 	CALL pro_fetch_manpower_cost_item("b5c9410dc7e4422c8e0189c7f8056b5f", "DBA");
END;


 CALL update_data_manpower_cost ();

