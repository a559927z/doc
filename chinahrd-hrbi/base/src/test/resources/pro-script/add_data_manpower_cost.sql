DROP PROCEDURE IF EXISTS add_data_manpower_cost;
CREATE PROCEDURE add_data_manpower_cost(in p_ym INT)
BEGIN

		LB_CUR:BEGIN 
			DECLARE  orgKey VARCHAR(20);
			DECLARE done INT DEFAULT 0;
			DECLARE s_cur CURSOR FOR
					select organization_key orgKey FROM dim_organization t where t.is_single = 1 and  organization_key != "ZRW";

			DECLARE EXIT HANDLER FOR SQLSTATE '02000' SET done = 1;

			OPEN s_cur;
			FETCH s_cur INTO  orgKey;	

				-- 所有子机构 
				WHILE1:WHILE done != 1 DO
		
						SET @v1 = (select floor(30 + (rand() * 60)));
						SET @v2 = @v1 + (select floor(5 + (rand() * 10)));
						SET @v3 = @v2 + (select floor(5 + (rand() * 10)));
		
						INSERT into soure_manpower_cost VALUES
							(replace(uuid(), '-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', orgKey, @v1, @v2, @v3, p_ym);
					
					FETCH s_cur INTO  orgKey;	
					

				END WHILE WHILE1;
				CLOSE s_cur;
		END LB_CUR;

			SET @v1 = (SELECT sum(cost) v1 FROM soure_manpower_cost WHERE `year_month` = p_ym);
			SET @v2 = (SELECT sum(cost_budget)v2 FROM soure_manpower_cost WHERE `year_month` = p_ym);
			SET @v3 = (SELECT sum(cost_company)v3 FROM soure_manpower_cost WHERE `year_month` = p_ym);
		
			-- 顶层机构
			INSERT into soure_manpower_cost VALUES
					(replace(uuid(), '-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', "ZRW", @v1, @v2, @v3, p_ym);

END;
 CALL add_data_manpower_cost (201401);
 CALL add_data_manpower_cost (201402);
 CALL add_data_manpower_cost (201403);
 CALL add_data_manpower_cost (201404);
 CALL add_data_manpower_cost (201405);
 CALL add_data_manpower_cost (201406);
 CALL add_data_manpower_cost (201407);
 CALL add_data_manpower_cost (201408);
 CALL add_data_manpower_cost (201409);
 CALL add_data_manpower_cost (201410);
 CALL add_data_manpower_cost (201411);
 CALL add_data_manpower_cost (201412);

 CALL pro_fetch_manpower_cost("b5c9410dc7e4422c8e0189c7f8056b5f", "DBA", '2015-01-01');
 CALL pro_fetch_manpower_cost('b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19', '2015-01-01');

-- =============================================================================================================


DROP PROCEDURE IF EXISTS add_data_manpower_cost;
CREATE PROCEDURE add_data_manpower_cost(in p_ym INT)
BEGIN

	DECLARE orgId,  id VARCHAR(32);
	DECLARE ct DOUBLE(6,2);
	
	DECLARE  ym INT(6);
	
	
	DECLARE done INT DEFAULT 0;
	DECLARE s_cur CURSOR FOR

		SELECT organization_id orgId, manpower_cost_id id, cost ct, `YEAR_MONTH` ym  FROM manpower_cost where `YEAR_MONTH` = p_ym
			;

	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;


	OPEN s_cur;
		
		WHILE done != 1 DO
			FETCH s_cur INTO  orgId,  id, ct, ym;
			IF NOT done THEN

				
				IF(orgId = '4f4deacb06ae457dad7ab8db27dea35e') THEN
					
					SET @v1 = ct * 0.60;
					SET @v2 = ct * 0.1;
					SET @v3 = ct * 0.3;
				

					INSERT into manpower_cost_item VALUES(replace(uuid(), '-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', orgId, 'payment', '薪酬', @v1, id, ym, 1) 
					;
					INSERT into manpower_cost_item VALUES(replace(uuid(), '-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', orgId, 'recruit', '招聘', @v2, id, ym, 2)
					;
					INSERT into manpower_cost_item VALUES(replace(uuid(), '-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', orgId, 'benefits', '福利', @v3, id, ym, 3)
					;

				ELSE 

						SET @v1 = ct * 0.65;
						SET @v2 = ct * 0.05;
						SET @v3 = ct * 0.02;
						SET @v4 = ct * 0.27;
						SET @v5 = ct * 0.01;

					INSERT into manpower_cost_item VALUES(replace(uuid(), '-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', orgId, 'payment', '薪酬', @v1, id, ym, 1) 
					;
					INSERT into manpower_cost_item VALUES(replace(uuid(), '-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', orgId, 'recruit', '招聘', @v2, id, ym, 2)
					;
					INSERT into manpower_cost_item VALUES(replace(uuid(), '-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', orgId, 'train', '培训', @v3, id, ym, 3)
					;
					INSERT into manpower_cost_item VALUES(replace(uuid(), '-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', orgId, 'benefits', '福利', @v4, id, ym, 4)
					;
					INSERT into manpower_cost_item VALUES(replace(uuid(), '-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', orgId, 'runOff', '离职', @v5, id, ym, 5)
					;
				
				END IF;
		END IF;
		END WHILE;
		CLOSE s_cur;

END;

-- TRUNCATE TABLE manpower_cost_item;
 CALL add_data_manpower_cost (201401);
 CALL add_data_manpower_cost (201402);
 CALL add_data_manpower_cost (201403);
 CALL add_data_manpower_cost (201404);
 CALL add_data_manpower_cost (201405);
 CALL add_data_manpower_cost (201406);
 CALL add_data_manpower_cost (201407);
 CALL add_data_manpower_cost (201408);
 CALL add_data_manpower_cost (201409);
 CALL add_data_manpower_cost (201410);
 CALL add_data_manpower_cost (201411);
 CALL add_data_manpower_cost (201412);
 





-- 计算年预算
-- TRUNCATE TABLE soure_manpower_cost;
		replace into soure_manpower_cost_value(
			id, customer_id, organization_key, budget_value, `year`
		)
		SELECT replace(uuid(),'-',''),'b5c9410dc7e4422c8e0189c7f8056b5f',
						organization_key organKey, sum(cost_budget) cb,
						-- `year_month`
						2014
				from soure_manpower_cost 
				WHERE `year_month` like '2014%'
				GROUP BY organization_key
		ORDER BY organization_key
-- 		HAVING `year_month` like '2014%'
		;

		

