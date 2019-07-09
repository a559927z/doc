#=======================================================pro_fetch_manpower_cost_item=======================================================
DROP PROCEDURE IF EXISTS `pro_fetch_manpower_cost_item`;
CREATE PROCEDURE pro_fetch_manpower_cost_item(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), IN p_ym int(6))
BEGIN


		DECLARE 12Month INT(2) DEFAULT SUBSTR(p_ym FROM 5 FOR 6);
		DECLARE y INT(2) DEFAULT SUBSTR(p_ym FROM 1 FOR 4);

		DECLARE p_error INTEGER DEFAULT 0; 
		DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

		START TRANSACTION;
		DELETE from manpower_cost_item WHERE `YEAR_MONTH` = p_ym;

		-- 工资
		INSERT into manpower_cost_item (
			manpower_cost_item_id, customer_id, organization_id, item_id, item_name, item_cost,  `year_month`, show_index
		)
		SELECT replace(uuid(),'-',''), t.customer_id, t.organization_id, '8ee64d2f70eb48f2a4cc54864cbdb21e','薪酬', t.sum_salary, p_ym, 1
		from pay_collect t INNER JOIN dim_organization t1 on t.organization_id = t1.organization_id 
		INNER JOIN manpower_cost t2 on t2.organization_id = t.organization_id and t2.`year_month` = p_ym
		where t.`year_month` = p_ym and t1.full_path in('ZRW', 'ZRW_BJ', 'ZRW_SZ', 'ZRW_GZ', 'ZRW_SH')
		ORDER BY full_path;

		-- 福利
		INSERT into manpower_cost_item (
			manpower_cost_item_id, customer_id, organization_id, item_id, item_name, item_cost,  `year_month`, show_index
		)
		SELECT replace(uuid(),'-',''), t.customer_id, t.organization_id, '11227b19a6194b42a9d45dfba76fd85c','福利', t.sum_welfare, p_ym, 2
		from pay_collect t INNER JOIN dim_organization t1 on t.organization_id = t1.organization_id 
		INNER JOIN manpower_cost t2 on t2.organization_id = t.organization_id and t2.`year_month` = p_ym
		where t.`year_month` = p_ym and full_path in('ZRW', 'ZRW_BJ', 'ZRW_SZ', 'ZRW_GZ', 'ZRW_SH')
		ORDER BY full_path;


			IF 12Month = 12 THEN
				-- 培训
					INSERT into manpower_cost_item (
						manpower_cost_item_id, customer_id, organization_id, item_id, item_name, item_cost, `year_month`, show_index
					)
					SELECT
						replace(uuid(),'-',''), t.customer_id, t.organization_id, '8f9984323a14409d95176bd370d0f035', '培训', sum(t.outlay), p_ym, 3
					FROM `train_outlay` t INNER JOIN dim_organization t1 on t.organization_id = t1.organization_id and t1.is_single =1 where t.`year` = y GROUP BY t.organization_id order by t.organization_id;
      
       -- 招聘
				insert into manpower_cost_item
        select replace(uuid(),'-',''),customer_id,organization_id,'60b2215662a041b0a11f4b1f9391319e','招聘',outlay,p_ym,2 from recruit_value where year=cast(substr(p_ym,1,4) as int);
			END IF;




	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
			-- CALL pro_fetch_manpower_cost();
	END IF;

END;
-- DELIMITER ;


-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201401);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201402);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201403);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201404);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201405);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201406);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201407);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201408);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201409);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201410);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201411);
-- CALL pro_fetch_manpower_cost_item('b5c9410dc7e4422c8e0189c7f8056b5f','DBA', 201412);

