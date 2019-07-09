-- 人力成本预算
drop procedure if exists pro_fetch_manpower_cost_value;
CREATE  PROCEDURE `pro_fetch_manpower_cost_value`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE startTime TIMESTAMP DEFAULT now();	 
		DECLARE p_message VARCHAR(10000) DEFAULT '【人力成本-1.人力成本预算】'; 

			INSERT INTO manpower_cost_value (
				manpower_cost_value_id,
				customer_id,
				organization_id,
				budget_value,
				`year`
			)
			SELECT 
				fn_getId(),
				customerId,
				t1.organization_id,
				t.budget_value,
				t.`year`
			FROM `mup-source`.source_manpower_cost_value t
			INNER JOIN dim_organization t1 on t.organization_key = t1.organization_key AND t1.customer_id = t.customer_id
			WHERE t.customer_id = customerId
			;
END;

-- 人力成本
drop procedure if exists pro_fetch_manpower_cost;
CREATE PROCEDURE `pro_fetch_manpower_cost`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), IN p_datetime DATETIME)
BEGIN
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE p_message VARCHAR(10000) DEFAULT '【业务表-人力成本】'; 

		DECLARE pCurDate TIMESTAMP DEFAULT p_datetime;
		DECLARE ym INT DEFAULT date_format(pCurDate,'%Y%m');

	DECLARE funId VARCHAR(50);
	DECLARE empRank VARCHAR(50);

				
	-- 人力成本ID
	SET funId = (SELECT function_id FROM dim_function WHERE function_key = 'RenLiChengBen' and customer_id = customerId );
	-- 配置表，人类型范围
	SET empRank = (SELECT config_val FROM config WHERE config_key = 'RLCB-personType' AND function_id = funId AND customer_id = customerId);


-- TODO empRank = 2，3，4 要带入history_emp_count_month.type 里条件
		REPLACE INTO manpower_cost (
			manpower_cost_id, customer_id, organization_id, cost, emp_num, cost_avg, cost_budget, cost_company, `year_month`
		)
		SELECT 
			fn_getId(), customerId, t.organization_id, t.cost, 
			t2.month_avg_sum AS empSum, 
			t.cost / t2.month_avg_sum AS costAvg, 
			t.cost_budget, t.cost_company, t.`year_month`
		FROM `mup-source`.source_manpower_cost t 
		INNER JOIN history_emp_count_month t2 on t2.organization_id = t.organization_id AND t2.`year_month` = ym AND t2.customer_id = t.customer_id
		WHERE t.customer_id = customerId
			AND t.`year_month` = ym;

		-- 如果本月是12月
		IF MONTH(pCurDate) = 12 THEN
			REPLACE INTO manpower_cost_value (
				manpower_cost_value_id,
				customer_id,
				organization_id,
				budget_value,
				`year`
			)
			SELECT fn_getId(), customerId, organization_id,
			SUM(cost_budget),
			YEAR(pCurDate)
			FROM manpower_cost t where t.`year_month` like CONCAT(YEAR(pCurDate),'%')
			GROUP BY YEAR(pCurDate), organization_id
			;
		END IF;
END;



-- 人力成本结构
drop procedure if exists pro_fetch_manpower_cost_item;
CREATE PROCEDURE `pro_fetch_manpower_cost_item`(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), IN p_ym int(6))
BEGIN
	
		DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
		DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
		DECLARE p_message VARCHAR(10000) DEFAULT '【人力成本-人力成本结构】'; 

		DECLARE 12Month INT(2) DEFAULT SUBSTR(p_ym FROM 5 FOR 6);
		DECLARE y INT(2) DEFAULT SUBSTR(p_ym FROM 1 FOR 4);
		
		INSERT INTO manpower_cost_item(
			manpower_cost_item_id,
			customer_id,
			organization_id,
			item_id,
			item_name,
			item_cost,
			`year_month`,
			show_index)
		SELECT 
			manpower_cost_item_id,
			customer_id,
			organization_id,
			item_id,
			item_name,
			item_cost,
			`year_month`,
			show_index
		FROM `mup-source`.source_manpower_cost_item t 
		WHERE t.customer_id = customerId
		ON DUPLICATE KEY UPDATE
			organization_id = t.organization_id,
			item_id = t.item_id,
			item_name = t.item_name,
			item_cost = t.item_cost,
			`year_month` = t.`year_month`,
			show_index = t.show_index
		;
/*
		DELETE from manpower_cost_item WHERE `YEAR_MONTH` = p_ym;

		-- 工资
		INSERT into manpower_cost_item (
			manpower_cost_item_id, customer_id, organization_id, item_id, item_name, item_cost,  `year_month`, show_index
		)
		SELECT fn_getId(), t.customer_id, t.organization_id, '8ee64d2f70eb48f2a4cc54864cbdb21e','薪酬', t.sum_salary, p_ym, 1 -- 万元
		from pay_collect t
		INNER JOIN dim_organization t1 on t.organization_id = t1.organization_id 
-- 		INNER JOIN manpower_cost t2 on t2.organization_id = t.organization_id and t2.`year_month` = p_ym
		where t.`year_month` = p_ym and t1.full_path in('ZRW', 'ZRW_BJ', 'ZRW_SZ', 'ZRW_GZ', 'ZRW_SH')
		ORDER BY full_path;

		-- 福利
		INSERT into manpower_cost_item (
			manpower_cost_item_id, customer_id, organization_id, item_id, item_name, item_cost,  `year_month`, show_index
		)
		SELECT fn_getId(), t.customer_id, t.organization_id, '11227b19a6194b42a9d45dfba76fd85c','福利', t.sum_welfare, p_ym, 2 -- 万元
		from pay_collect t INNER JOIN dim_organization t1 on t.organization_id = t1.organization_id 
-- 		INNER JOIN manpower_cost t2 on t2.organization_id = t.organization_id and t2.`year_month` = p_ym
		where t.`year_month` = p_ym and full_path in('ZRW', 'ZRW_BJ', 'ZRW_SZ', 'ZRW_GZ', 'ZRW_SH')
		ORDER BY full_path;


		IF 12Month = 12 THEN
			-- 培训
			INSERT into manpower_cost_item (
				manpower_cost_item_id, customer_id, organization_id, item_id, item_name, item_cost, `year_month`, show_index
			)
			SELECT
				fn_getId(), t.customer_id, t.organization_id, '8f9984323a14409d95176bd370d0f035', '培训', sum(t.outlay), p_ym, 3 -- 万元
				FROM `train_outlay` t INNER JOIN dim_organization t1 on t.organization_id = t1.organization_id and t1.is_single =1 where t.`year` = y GROUP BY t.organization_id order by t.organization_id;
  
   			-- 招聘
			insert into manpower_cost_item(
					manpower_cost_item_id, customer_id, organization_id, item_id, item_name, item_cost, `year_month`, show_index
			)
	        select fn_getId(), customer_id, organization_id,'60b2215662a041b0a11f4b1f9391319e','招聘',outlay,p_ym,4 -- 万元
				from recruit_value where `year`=substr(p_ym,1,4);
		END IF;
-- 			CALL proc_manpower_cost(p_ym);
*/
	
END;
