#=======================================================pro_fetch_manpower_cost_value=======================================================
-- 'b5c9410dc7e4422c8e0189c7f8056b5f','3cfd3db15ffc4c119e344e82eb8cbb19'
-- 'demo','jxzhang'
--  保证：dim_organization表存在
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_manpower_cost_value`;
CREATE PROCEDURE pro_fetch_manpower_cost_value(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;
	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE startTime TIMESTAMP DEFAULT now();

	DECLARE p_error INTEGER DEFAULT 0; 
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;
	START TRANSACTION;

			REPLACE INTO manpower_cost_value (
				manpower_cost_value_id,
				customer_id,
				organization_id,
				budget_value,
				`year`
			)
			SELECT 
				id,
				customerId,
				t1.organization_id,
				t.budget_value,
				t.`year`
			FROM soure_manpower_cost_value t
			INNER JOIN dim_organization t1 on t.organization_key = t1.organization_key AND t1.customer_id = t.customer_id
			WHERE t.customer_id = customerId ;
			

	IF p_error = 1 THEN  
			ROLLBACK;  
	ELSE  
			COMMIT;  
	END IF;

END;


CALL pro_fetch_manpower_cost_value('b5c9410dc7e4422c8e0189c7f8056b5f', 'DBA');