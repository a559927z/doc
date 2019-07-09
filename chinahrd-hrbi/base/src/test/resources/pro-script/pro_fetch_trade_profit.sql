#=======================================================pro_fetch_trade_profit=======================================================
-- DELIMITER //
DROP PROCEDURE IF EXISTS `pro_fetch_trade_profit`;
-- CREATE PROCEDURE pro_fetch_trade_profit(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32), in p_last_curdate datetime)
CREATE PROCEDURE pro_fetch_trade_profit(in p_customer_id VARCHAR(32), in p_user_id VARCHAR(32))
BEGIN

	DECLARE optUserId VARCHAR(32) DEFAULT p_user_id;
	DECLARE customerId VARCHAR(32) DEFAULT p_customer_id;

	-- 定义接收临时表数据的变量 
	DECLARE exist INT;
	DECLARE startTime TIMESTAMP DEFAULT now();
	DECLARE p_error INTEGER DEFAULT 0; 

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN SHOW ERRORS;	SET p_error = 1; END;

	START TRANSACTION;

		LB_INSERT:BEGIN
			INSERT INTO trade_profit(
				trade_profit_id,
				customer_id,
				organization_id,
				gain_amount,
				sales_amount,
				expend_amount,
				target_value,
				`year_month`
			)
			SELECT 
				id, 
				customerId,
				t2.organization_id,
				t.gain_amount,
				t.sales_amount, 
				t.target_value,
				t.expend_amount,
				t.`year_month`
			FROM soure_trade_profit t 
			INNER JOIN dim_organization t2 on t.organization_key = t2.organization_key and t2.customer_id = t.customer_id
			WHERE t.customer_id = customerId
			ON DUPLICATE KEY UPDATE
				customer_id = t.customer_id, 
				organization_id = t2.organization_id,
				gain_amount = t.gain_amount,
				sales_amount = t.sales_amount,
				target_value = t.target_value,
				expend_amount = t.expend_amount,
				`year_month` = t.`year_month`
			;
		END LB_INSERT;


		IF p_error = 1 THEN  
				ROLLBACK;  
		ELSE  
				COMMIT;  
		END IF;


END;
-- DELIMITER ;

-- SET @lastCurdate = ( SELECT DATE_SUB(CURDATE(),INTERVAL  1 MONTH));
CALL pro_fetch_trade_profit('b5c9410dc7e4422c8e0189c7f8056b5f','DBA' );
