DELETE FROM role_organization_relation ;
INSERT INTO role_organization_relation
(role_organization_id, customer_id, organization_id, create_user_id, create_time)
-- SELECT * FROM(
-- 	SELECT replace(UUID(),'-',''), a.customer_id, a.role_id, b.organization_id, b.create_user_id, now()
-- 	FROM dim_role a JOIN dim_organization b 
-- ) as tb;
SELECT replace(UUID(),'-',''), b.customer_id, b.organization_id, b.create_user_id, now() FROM dim_organization b
WHERE  b.organization_id  NOT  IN  ( SELECT organization_id  FROM  role_organization_relation)
;
UPDATE role_organization_relation SET role_id = 'd9111f9dd7c54acd99d0664210a8e7c6' WHERE role_id is NULL;

SELECT count(1) FROM role_organization_relation;

INSERT into trade_profit_copy(
						trade_profit_id, customer_id,
						business_unit_id, organization_id, gain_amount, sales_amount, `year_month`,
						create_user_id, create_time)
		SELECT 	trade_profit_id, customer_id,
						business_unit_id, organization_id, gain_amount, sales_amount, `year_month`,
						create_user_id, create_time
		FROM 
					(SELECT 
						replace(UUID(),'-','') as trade_profit_id,
						'b5c9410dc7e4422c8e0189c7f8056b5f' as customer_id,
						t1.business_unit_id, 
						t2.organization_id,
						t.gain_amount,
						t.sales_amount, 
						t.`year_month`,
						'3cfd3db15ffc4c119e344e82eb8cbb19' as create_user_id,
						now() as create_time
					FROM soure_trade_profit t 
					INNER JOIN dim_business_unit t1 on t.business_unit_key = t1.business_unit_key and t1.customer_id = t.customer_id
					INNER JOIN dim_organization t2 on t.organization_key = t2.organization_key and t2.customer_id = t.customer_id
					WHERE t.customer_id = 'b5c9410dc7e4422c8e0189c7f8056b5f'
					ORDER BY t.`year_month`) soure
-- 		WHERE	business_unit_id = buUnitId AND organization_id = orgId AND customer_id = customerId AND `year_month` = ym
		WHERE `year_month` not IN (SELECT `YEAR_MONTH` FROM trade_profit_copy) 
			AND business_unit_id not IN (SELECT `business_unit_id` FROM trade_profit_copy);
;

